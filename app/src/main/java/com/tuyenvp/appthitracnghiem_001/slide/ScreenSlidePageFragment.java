package com.tuyenvp.appthitracnghiem_001.slide;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.tuyenvp.appthitracnghiem_001.R;
import com.tuyenvp.appthitracnghiem_001.question.Question;

import java.util.ArrayList;


public class ScreenSlidePageFragment extends Fragment {
    ArrayList<Question> arr_Ques;
    public static final String ARG_PAGE = "page";
    public static final String ARG_CHECKANSWER = "checkAnswer";
    private int mPageNumber;
    public int checkAns;

    TextView txtNum, txtQuesTion;
    ImageView imgIcon;
    RadioGroup radioGroup;
    RadioButton radA, radB, radC, radD;


    public ScreenSlidePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        txtNum = rootView.findViewById(R.id.tvNum);
        txtQuesTion = rootView.findViewById(R.id.tvQuestion);
        imgIcon = rootView.findViewById(R.id.ivIcon);
        radA = rootView.findViewById(R.id.radA);
        radB = rootView.findViewById(R.id.radB);
        radC = rootView.findViewById(R.id.radC);
        radD = rootView.findViewById(R.id.radD);
        radioGroup = rootView.findViewById(R.id.radGroup);


        return rootView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arr_Ques = new ArrayList<Question>();
        ScreenSlidePagerActivity slidePagerActivity = (ScreenSlidePagerActivity) getActivity();
        arr_Ques = slidePagerActivity.getData();
        mPageNumber = getArguments().getInt(ARG_PAGE);
        checkAns = getArguments().getInt(ARG_CHECKANSWER);
    }

    public static ScreenSlidePageFragment create(int pageNumber, int checkAnswer) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_CHECKANSWER, checkAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtNum.setText("Câu " + (mPageNumber + 1));
//        txtNum.setText("Câu" + (getItem(mPageNumber).getImage()));
        txtQuesTion.setText(arr_Ques.get(mPageNumber).getQuestion());//arr_Ques: lấy ra câu hỏi tại vị trí "mPageNumber"
        if (getItem(mPageNumber).getImage() == null) {
            imgIcon.setVisibility(View.GONE);
        } else {
            imgIcon.setImageResource(getResources().getIdentifier(getItem(mPageNumber).getImage() + "",
                    "drawable", "com.example.appthitracnghiem_001"));
        }
        radA.setText(getItem(mPageNumber).getAns_a());
        radB.setText(getItem(mPageNumber).getAns_b());
        radC.setText(getItem(mPageNumber).getAns_c());
        radD.setText(getItem(mPageNumber).getAns_d());

        if (checkAns != 0) {
            radA.setClickable(false);
            radB.setClickable(false);
            radC.setClickable(false);
            radD.setClickable(false);

            getCheckAns(getItem(mPageNumber).getResult(), getItem(mPageNumber).getTraloi());
            //getCheckAns(kết quả đúng trong db, kết quả đã trả lời);

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                getItem(mPageNumber).choiceID = checkedId;
                getItem(mPageNumber).setTraloi(getChoiceFromId(checkedId));//set giá trị đã chọn( trả lời) cho item thứ "mPageNumber"
//                Toast.makeText(getActivity(), "Đây là đáp án"+checkedId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Question getItem(int position) {
        return arr_Ques.get(position);
    }

    private String getChoiceFromId(int ID) {
        if (ID == R.id.radA) {
            return "A";
        } else if (ID == R.id.radB) {
            return "B";
        } else if (ID == R.id.radC) {
            return "C";
        } else if (ID == R.id.radD) {
            return "D";
        } else return "";
    }



    private void getCheckAns(String result, String ans) {
        if (result.equals(ans)) {
            if (result.equals("A")) {
                radA.setBackgroundColor(Color.GREEN);
            } else if (result.equals("B")) {
                radB.setBackgroundColor(Color.GREEN);
            } else if (result.equals("C")) {
                radC.setBackgroundColor(Color.GREEN);
            } else if (result.equals("D")) {
                radD.setBackgroundColor(Color.GREEN);
            } else ;
        } else if (result != ans) {
            if (result.equals("A")) {
                radA.setBackgroundColor(Color.RED);
            } else if (result.equals("B")) {
                radB.setBackgroundColor(Color.RED);
            } else if (result.equals("C")) {
                radC.setBackgroundColor(Color.RED);
            } else if (result.equals("D")) {
                radD.setBackgroundColor(Color.RED);
            } else ;

        }
    }
}
