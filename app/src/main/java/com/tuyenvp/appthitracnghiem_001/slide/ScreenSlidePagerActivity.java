package com.tuyenvp.appthitracnghiem_001.slide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuyenvp.appthitracnghiem_001.R;
import com.tuyenvp.appthitracnghiem_001.question.Question;
import com.tuyenvp.appthitracnghiem_001.question.QuestionController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 20;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    TextView tvTimer, txtKiemTra, tvXemDiem, txtCurrentPosition, txtBack, txtNext;

    String test = "";


    QuestionController questionController;
    ArrayList<Question> arr_Ques;
    CounterClass timer;
    String subject;
    int num_exam;
    public int checkAns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_pager);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                String i=position+"";
//                Toast.makeText(ScreenSlidePagerActivity.this, i, Toast.LENGTH_SHORT).show();
                txtCurrentPosition.setText(position + 1 + "/20");
                if (position == 0) {// khi đang ở câu 1
                    txtBack.setVisibility(View.INVISIBLE);// ẩn
                    txtNext.setVisibility(View.VISIBLE);//hiện
                } else if (position == arr_Ques.size() - 1) {// khi đang ở câu 20
                    txtBack.setVisibility(View.VISIBLE);
                    txtNext.setVisibility(View.INVISIBLE);
                } else {
                    txtBack.setVisibility(View.VISIBLE);// hiện
                    txtNext.setVisibility(View.VISIBLE);//hiện
                }

            }

        });


        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        num_exam = intent.getIntExtra("num_exam", 0);
        test = intent.getStringExtra("test");

        timer = new CounterClass(5 * 60 * 1000, 1000);
        timer.start();
        questionController = new QuestionController(this);
        arr_Ques = new ArrayList<Question>();

        if (test.equals("yes")) { // lần đầu làm bài
            arr_Ques = questionController.getQuestion(num_exam, subject);

        } else {
            arr_Ques = (ArrayList<Question>) intent.getExtras().getSerializable("arr_Ques");
        }

        txtKiemTra = findViewById(R.id.tvKiemTra);
        tvTimer = findViewById(R.id.tvTimer);
        tvXemDiem = findViewById(R.id.tvScore);
        txtNext = findViewById(R.id.txtNext);
        txtBack = findViewById(R.id.txtBack);
        txtCurrentPosition = findViewById(R.id.txtCurentPosition);

        txtKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        tvXemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent(ScreenSlidePagerActivity.this, TestDoneActivity.class);
                intent1.putExtra("arr_Ques", arr_Ques);
                startActivity(intent1);
            }
        });
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (viewPager.getCurrentItem() == 0) {
            dialogExit();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public ArrayList<Question> getData() {
        return arr_Ques;
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ScreenSlidePageFragment.create(position, checkAns);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    public void checkAnswer() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_answer_dialog);
        dialog.setTitle("Danh sách câu trả lời");
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        // truyền giá arr_Ques cho dialog
        CheckAnswerAdapter answerAdapter = new CheckAnswerAdapter(arr_Ques, this);
        GridView gvLsQuestion = dialog.findViewById(R.id.gvLsQuestion);
        gvLsQuestion.setAdapter(answerAdapter);

        gvLsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //chuyển trang đến vị trí thứ position
                viewPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });
        Button btnCancel, btnFinish;
        btnCancel = dialog.findViewById(R.id.btn_Cancel);
        btnFinish = dialog.findViewById(R.id.btn_Finish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                result();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void result() {
        checkAns = 1;// thay đổi bên fragment
        if (viewPager.getCurrentItem() >= 5) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 4);
        } else if (viewPager.getCurrentItem() <= 5) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 4);
        } else
            tvXemDiem.setVisibility(View.VISIBLE);// hiện lên
        txtKiemTra.setVisibility(View.GONE);// ẩn đi
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");
            Toast.makeText(ScreenSlidePagerActivity.this, "Đã hết giờ làm bài", Toast.LENGTH_LONG);
            result();

        }
    }
    public void dialogExit(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(ScreenSlidePagerActivity.this);
        builder.setIcon(R.drawable.exit);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thoát hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
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


    // Phương thức xoa database viết vào hàm MainActivity
    //        try {
//            db.deleteDataBase();
//            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "bi loi rui", Toast.LENGTH_SHORT).show();
//        }
}