package com.example.aes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper1 extends SQLiteOpenHelper {

    public DatabaseHelper1(Context context) {

        super(context, "Customerdetails.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table Customerdetails(username TEXT primary key, firstname TEXT, lastname TEXT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Customerdetails");
    }

    //CRUD OPERATIONS

    public Boolean insertuserdata(String username, String firstname, String lastname, String email, String password){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("email",email);
        contentValues.put("password",password);

        long result=DB.insert("Customerdetails",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean updateuserdata(String username, String firstname, String lastname, String email, String password){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("email",email);
        contentValues.put("password",password);

        Cursor cursor=DB.rawQuery("Select * from Customerdetails where username=?", new String[]{username});
        if(cursor.getCount()>0) {
            long result = DB.update("Customerdetails", contentValues, "username=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }



    public Boolean deleteuserdata(String username){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Customerdetails where username=?", new String[]{username});
        if(cursor.getCount()>0) {
            long result = DB.delete("Customerdetails", "username=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Customerdetails", null);
        return cursor;


    }
    public Cursor getdata(String username){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from Customerdetails where username=?", new String[]{username});
        return cursor;


    }
}
