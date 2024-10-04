package com.tuyenvp.appthitracnghiem_001.monhoc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.tuyenvp.appthitracnghiem_001.R;
import com.tuyenvp.appthitracnghiem_001.slide.ScreenSlidePagerActivity;

import java.util.ArrayList;


public class TiengAnhFragment extends Fragment {

    Activity content;
    String monhoc;
    ExamAdapter examAdapter;
    GridView gvExam;
    Button btnXemDiem,btnXemCongThuc;
    ArrayList<Exam> arr_exam = new ArrayList<Exam>();
    ArrayList<NumExam> numExam = new ArrayList<NumExam>();
    public TiengAnhFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = getActivity();
        View root = inflater.inflate(R.layout.fragment_tienganh, container, false);
        return root;


    }
//    private void linkView(){
//        btnDethi= btnDethi.findViewById(R.id.btn_dethi);
//    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gvExam = content.findViewById(R.id.gvExam);


        arr_exam.add(new Exam("Python", R.drawable.python));
        arr_exam.add(new Exam("Tiếng Anh", R.drawable.eng));
        arr_exam.add(new Exam("Java", R.drawable.java));
        arr_exam.add(new Exam("Javascript", R.drawable.javascript));
        arr_exam.add(new Exam("HTML", R.drawable.html));
        arr_exam.add(new Exam("CSS", R.drawable.css));
        arr_exam.add(new Exam("React", R.drawable.react));
        arr_exam.add(new Exam("SQL", R.drawable.sql));

        numExam.add(new NumExam("Đề 1"));
        numExam.add(new NumExam("Đề 2"));
        numExam.add(new NumExam("Đề 3"));
        numExam.add(new NumExam("Đề 4"));

        examAdapter = new ExamAdapter(content, arr_exam);
        gvExam.setAdapter(examAdapter);
        gvExam.setVerticalSpacing(16);

        // bắt sự kiện khi click vào 1 item gridview
        gvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_num_exam);
                dialog.setTitle("Danh sách đề");

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setAttributes(lp);

                dialog.show();
                if(position==0){
                    monhoc="python";
                }else if(position==1){
                    monhoc="anh";
                }else if(position==2){
                    monhoc="java";
                }else{
                    monhoc="";
                }
                ChooseNumExam answerAdapter = new ChooseNumExam(numExam, getContext());

                GridView gvLsNumExam= dialog.findViewById(R.id.gvLsNumExam);
                gvLsNumExam.setAdapter(answerAdapter);

                // bắt sự kiện khi click vào 1 item dialog
                gvLsNumExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //gửi mã đề, tên môn, test qua màn hình ScreenSlidePagerActivity
                        Intent intent = new Intent(content, ScreenSlidePagerActivity.class);
                        intent.putExtra("num_exam", position+1);
                        intent.putExtra("subject", monhoc);
                        intent.putExtra("test", "yes");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

            }
        });

    }


}