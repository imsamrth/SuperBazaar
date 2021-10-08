package com.samarthappwork.superbazaar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.samarthappwork.superbazaar.objectclasses.customer;

public class last_acivity_data extends SQLiteOpenHelper {


    public static final String LOGIN_DATA = "LOGIN_DATA";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    public static final String COLUMN_PHONE = "COLUMN_PHONE";
    public static final String COLUMN_VALUE = "COLUMN_CARTVALUE";
    public static final String COLUMN_MRPVALUE= "COLUMN_CARTMRPVALUE";
    public static final String COLUMN_IS_LOGIN = "COULUMN_IS_LOGIN";

    private static final String LOGIN_DATABASE = "login.db";
    public last_acivity_data(@Nullable Context context) {
        super(context, LOGIN_DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createdatabase = "CREATE TABLE " + LOGIN_DATA + " ( " +
                COLUMN_ID + " TEXT , "  +
                COLUMN_USERNAME + " TEXT , " +
                COLUMN_PHONE + " TEXT , " +
                COLUMN_VALUE + " TEXT , " +
                COLUMN_MRPVALUE + " TEXT , " +
                COLUMN_IS_LOGIN +"  INT ) ";
        db.execSQL(createdatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized boolean updatedatabase(loggined_user_details applicationStatus) {
        SQLiteDatabase login_data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, 0);
        cv.put(COLUMN_USERNAME, applicationStatus.getName());
        cv.put(COLUMN_PHONE, applicationStatus.getName());
        cv.put(COLUMN_VALUE, applicationStatus.getCart_total());
        cv.put(COLUMN_MRPVALUE, applicationStatus.getCart_mrp_total());
        cv.put(COLUMN_IS_LOGIN,applicationStatus.isIslogined());

        long insert =  login_data.insert(LOGIN_DATA, null, cv);
        return  insert != -1 ;
    }

    public int islogined(){
        String querystring = "SELECT * FROM " + LOGIN_DATA;
        SQLiteDatabase login_data  = this.getReadableDatabase();
        Cursor cursor = login_data.rawQuery(querystring, null);
        int islogin =  0;

        if (cursor.moveToFirst()){
            String person_name = cursor.getString(1);
            islogin = cursor.getInt(4) ;
        }
        cursor.close();
        login_data.close();
        return islogin;
    }

    public void setuser() {
        loggined_user_details user = new loggined_user_details();

        // GET DATA FROM DATABASE

        String querystring = "SELECT * FROM " + LOGIN_DATA;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(querystring, null);

        if (cursor.moveToFirst()) {
            do {
                String person_id = cursor.getString(0);
                String person_name = cursor.getString(1);
                String personphone = cursor.getString(2);
                String mrp_value = cursor.getString(4) ;
                String total_value  = cursor.getString(3) ;
                customer newwishingmodel = new customer(person_name,personphone,"","","","",total_value,mrp_value);

               loggined_user_details.setCustomer(newwishingmodel);
            } while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        db.close();
    }

}

