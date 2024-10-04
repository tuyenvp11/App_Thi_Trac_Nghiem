package com.tuyenvp.appthitracnghiem_001.congthuc;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tuyenvp.appthitracnghiem_001.R;

public class ListViewItem extends AppCompatActivity {
    TextView txtItemCongThuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Cursor cursor;
        setContentView(R.layout.item_list_congthuc);
        txtItemCongThuc = findViewById(R.id.txtItemCongThuc);
    }
}
