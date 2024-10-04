package com.tuyenvp.appthitracnghiem_001.congthuc;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuyenvp.appthitracnghiem_001.R;

public class CongThucAdapter extends CursorAdapter {
    public CongThucAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_list_congthuc,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtCongthuc=(TextView) view.findViewById(R.id.tvCongThuc);
        LinearLayout linCongthuc= (LinearLayout) view.findViewById(R.id.linCongthuc);

        if(cursor.getPosition()%2 == 0){
            linCongthuc.setBackgroundColor(Color.parseColor("#FFE2DFDF"));
        }else linCongthuc.setBackgroundColor(Color.parseColor("#ffffff"));
        txtCongthuc.setText(cursor.getString(1));



    }
}

