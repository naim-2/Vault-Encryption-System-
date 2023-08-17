package com.example.aes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    public DatabaseHelper2(Context context) {

        super(context, "Merchantdetails.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table Merchantdetails(username TEXT, merchant_name TEXT  primary key, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Merchantdetails");
    }

    //CRUD OPERATIONS

    public Boolean insertuserdata(String username, String merchant_name, String email, String password){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("merchant_name",merchant_name);
        contentValues.put("email",email);
        contentValues.put("password",password);

        long result=DB.insert("Merchantdetails",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean updateuserdata(String username, String merchant_name, String email, String password){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("merchant_name",merchant_name);
        contentValues.put("email",email);
        contentValues.put("password",password);

        Cursor cursor=DB.rawQuery("Select * from Merchantdetails where merchant_name=?", new String[]{merchant_name});
        if(cursor.getCount()>0) {
            long result = DB.update("Merchantdetails", contentValues, "merchant_name=?", new String[]{merchant_name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }



    public Boolean deleteuserdata(String merchant_name){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Merchantdetails where merchant_name=?", new String[]{merchant_name});
        if(cursor.getCount()>0) {
            long result = DB.delete("Merchantdetails", "merchant_name=?", new String[]{merchant_name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getdata(String merchant_name){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Merchantdetails where merchant_name=?", new String[]{merchant_name});
        return cursor;


    }

    public Cursor getdata2(String username){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Merchantdetails where username=?", new String[]{username});
        return cursor;


    }
}
