package com.tuyenvp.appthitracnghiem_001.slide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuyenvp.appthitracnghiem_001.R;
import com.tuyenvp.appthitracnghiem_001.question.Question;
import com.tuyenvp.appthitracnghiem_001.score.ScoreController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TestDoneActivity extends AppCompatActivity {

    TextView tvTrue, tvFasle, tvNotAns, tvTotalPoint,txtTestDone;
    Button btnSave, btnAgain, btnExit;
    ImageView imvTestDone;
    ArrayList<Question> arr_QuesBegin = new ArrayList<Question>();
    int num_NotAns = 0;
    int num_True_Ans = 0;
    int num_Fasle_Ans = 0;
    int num_TotalPoint = 0;

    ScoreController scoreController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);

        scoreController= new ScoreController(TestDoneActivity.this);

        Intent intent = getIntent();
        arr_QuesBegin = (ArrayList<Question>) intent.getExtras().getSerializable("arr_Ques");
        begin();
        checkResult();
        showResult();
        Exit();
        saveScore();
        again();
    }

    private void again() {
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                finish();
                Intent intent2= new Intent(TestDoneActivity.this, ScreenSlidePagerActivity.class);
                intent2.putExtra("arr_Ques",arr_QuesBegin);
                intent2.putExtra("test","no");
                startActivity(intent2);
            }

            private void refresh() {
                for(int i=0;i<arr_QuesBegin.size();i++){
                    arr_QuesBegin.get(i).setTraloi("");
                }
            }
        });
    }

    private void saveScore() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//lấy ra user đang đăng nhập

                AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                LayoutInflater inflater = TestDoneActivity.this.getLayoutInflater();
                View view=inflater.inflate(R.layout.allert_dialog_save_score,null);
                builder.setView(view);

                EditText edtName=  view.findViewById(R.id.edtName);
                TextView txtResultNumTrue=  view.findViewById(R.id.txtResultNumTrue);
                TextView txtResultScore=  view.findViewById(R.id.txtResultScore);

                int numResultTotal= num_True_Ans*5;
                String numResultTrue= String.valueOf(num_True_Ans);

                edtName.setText(user.getDisplayName());

                txtResultNumTrue.setText(numResultTrue+"/20");

                txtResultScore.setText(numResultTotal+ " Điểm");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name=edtName.getText().toString();
                        String numResultTrue=txtResultNumTrue.getText().toString();

                        scoreController.insertScore(name,numResultTotal,numResultTrue);
                        //scoreController.insertScore(tên,điểm,số câu đúng);
                        Toast.makeText(TestDoneActivity.this, "Lưu điểm thành công!", Toast.LENGTH_LONG).show();
                        finish();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog b=builder.create();
                b.show();
            }
        });
    }

    private void Exit() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }

    private void showResult() {
        num_TotalPoint = num_True_Ans * 5;
        tvNotAns.setText("" + num_NotAns);
        tvFasle.setText("" + num_Fasle_Ans);
        tvTrue.setText("" + num_True_Ans);
        tvTotalPoint.setText("" + num_TotalPoint);
        if(num_True_Ans<=10){
            txtTestDone.setText("Trung bình");
            imvTestDone.setImageResource(R.drawable.image_92);
        }else if(num_True_Ans<=15){
            txtTestDone.setText("Khá");
            imvTestDone.setImageResource(R.drawable.quynh_aka);
        }else{
            txtTestDone.setText("Giỏi");
            imvTestDone.setImageResource(R.drawable.image_94);
        }

    }

    public void begin() {
        tvFasle =  findViewById(R.id.tvFalse);
        tvTrue =  findViewById(R.id.tvTrue);
        tvNotAns =  findViewById(R.id.tvNotAns);
        tvTotalPoint =  findViewById(R.id.tvTotalPoint);
        btnAgain =  findViewById(R.id.btnAgain);
        btnSave =  findViewById(R.id.btnSaveScore);
        btnExit =  findViewById(R.id.btnExit);
        txtTestDone =  findViewById(R.id.txtTestDone);
        imvTestDone =  findViewById(R.id.imvTestDone);
    }

    public void checkResult() {
        for (int i = 0; i < arr_QuesBegin.size(); i++) {
            if (arr_QuesBegin.get(i).getTraloi().equals("")) {//so sánh Biến trả lời với Rỗng
                num_NotAns++;
            } else if (arr_QuesBegin.get(i).getTraloi().equals(arr_QuesBegin.get(i).getResult()) ) {
                //so sánh biến trả lời với kết quả đúng
                num_True_Ans++;
            } else num_Fasle_Ans++;
        }
    }
}