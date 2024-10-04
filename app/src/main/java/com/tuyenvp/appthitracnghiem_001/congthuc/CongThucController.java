package com.tuyenvp.appthitracnghiem_001.congthuc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tuyenvp.appthitracnghiem_001.question.DBHelper;

public class CongThucController {
    private DBHelper dbHelper;
    public CongThucController(Context context){
        dbHelper= new DBHelper(context);
    }
    public Cursor getCongThuc(String chude, String key) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM congthuc WHERE congthuc LIKE '%"+key+"%' " +
                "AND chude LIKE '%"+chude+"%'",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getCongThucTheoChuDe(String key) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM congthuc WHERE chude LIKE '%"+key+"%'",null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


//
//    public Cursor get1CongThucTheoViTri(int position) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor=db.rawQuery("SELECT * FROM congthuc WHERE congthuc ",null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
}
