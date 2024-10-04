package com.tuyenvp.appthitracnghiem_001.monhoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tuyenvp.appthitracnghiem_001.R;

import java.util.ArrayList;

public class ExamAdapter extends ArrayAdapter<Exam> {
    public ExamAdapter(@NonNull Context context, ArrayList<Exam> exam) {
        super(context, 0, exam);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_gridview_exam, parent, false);
        }

        TextView txtName = convertView.findViewById(R.id.txtnumExam);
        ImageView imgIcon = convertView.findViewById(R.id.imgIcon);
        LinearLayout linExam= (LinearLayout) convertView.findViewById(R.id.linExam);

        Exam p = getItem(position);
        txtName.setText(p.getName());
        imgIcon.setImageResource(p.getLogo());
//        if (p != null) {
//           Exam p = getItem(position);
//            if(position%2==0){
//                linExam.setBackgroundColor(Color.parseColor("#ECE9E6"));
//            }else {
//                linExam.setBackgroundColor(Color.parseColor("#00d2ff"));
//            }
//
//        }

        return convertView;
    }
}
