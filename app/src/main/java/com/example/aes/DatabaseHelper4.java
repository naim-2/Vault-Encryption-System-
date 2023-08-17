package com.example.aes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper4 extends SQLiteOpenHelper {

    public DatabaseHelper4(Context context) {

        super(context, "Amount.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table Amount(session_id TEXT primary key, username TEXT, card_number TEXT, merchant_name TEXT, amount_charged TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Amount");
    }

    //CRUD OPERATIONS

    public Boolean insertuserdata(String session_id, String username, String card_number, String merchant_name, String amount_charged){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("session_id",session_id);
        contentValues.put("username",username);
        contentValues.put("card_number",card_number);
        contentValues.put("merchant_name",merchant_name);
        contentValues.put("amount_charged",amount_charged);

        long result=DB.insert("Amount",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getdata(String session_id){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Amount where session_id=?", new String[]{session_id});
        return cursor;
    }

    public Cursor getdata2(String merchant_name){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Amount where merchant_name=?", new String[]{merchant_name});
        return cursor;
    }
    public Boolean deleteuserdata(String card_number){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Amount where card_number=?", new String[]{card_number});
        if(cursor.getCount()>0) {
            long result = DB.delete("Amount", "card_number=?", new String[]{card_number});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }
}
