package com.tuyenvp.appthitracnghiem_001.score;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tuyenvp.appthitracnghiem_001.R;

public class ScoreAdapter extends CursorAdapter {
    public ScoreAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_list_score,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtScore= view.findViewById(R.id.txtScore);
        TextView txtPlayerName= view.findViewById(R.id.txtPlayerName);
        TextView txtDate= view.findViewById(R.id.txtTime);
        TextView txtNumTrue= view.findViewById(R.id.txtNumTrue);

        txtPlayerName.setText(cursor.getString(1));
        txtScore.setText(cursor.getInt(2)+" Điểm");
        txtDate.setText(cursor.getString(3));
        txtNumTrue.setText(cursor.getString(4));

    }
}
