package com.example.aes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper3 extends SQLiteOpenHelper {

    public DatabaseHelper3(Context context) {

        super(context, "Carddetails.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table Carddetails(card_number TEXT primary key, card_name TEXT, username TEXT, expiration_date TEXT, security_code TEXT, billing_address TEXT, date_created TEXT, amount TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Carddetails");
    }

    //CRUD OPERATIONS

    public Boolean insertuserdata(String card_number, String card_name, String username, String expiration_date, String security_code, String billing_address, String date_created, String amount){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("card_number",card_number);
        contentValues.put("card_name",card_name);
        contentValues.put("username",username);
        contentValues.put("expiration_date",expiration_date);
        contentValues.put("security_code",security_code);
        contentValues.put("billing_address",billing_address);
        contentValues.put("date_created",date_created);
        contentValues.put("amount",amount);

        long result=DB.insert("Carddetails",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean updateuserdata(String card_number, String card_name, String username, String expiration_date, String security_code, String billing_address, String date_created){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("card_number",card_number);
        contentValues.put("card_name",card_name);
        contentValues.put("username",username);
        contentValues.put("expiration_date",expiration_date);
        contentValues.put("security_code",security_code);
        contentValues.put("billing_address",billing_address);
        contentValues.put("date_created",date_created);

        Cursor cursor=DB.rawQuery("Select * from Carddetails where card_number=?", new String[]{card_number});
        if(cursor.getCount()>0) {
            long result = DB.update("Carddetails", contentValues, "card_number=?", new String[]{card_number});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean updateuserdata2(String card_number, String amount){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("card_number",card_number);
        contentValues.put("amount",amount);

        Cursor cursor=DB.rawQuery("Select * from Carddetails where card_number=?", new String[]{card_number});
        if(cursor.getCount()>0) {
            long result = DB.update("Carddetails", contentValues, "card_number=?", new String[]{card_number});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean deleteuserdata(String card_number){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Carddetails where card_number=?", new String[]{card_number});
        if(cursor.getCount()>0) {
            long result = DB.delete("Carddetails", "card_number=?", new String[]{card_number});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getdata(String username){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Carddetails where username=?", new String[]{username});
        return cursor;


    }

    public Cursor getdata3(String card_number){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Carddetails where card_number=?", new String[]{card_number});
        return cursor;


    }
}
