package com.example.antrosgeor.barcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ANTROS on 23/6/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // String = First_Name, Last_Name, Username, Email, Password, job,
    // String static = Sex, Family,
    // Integer = ID => AUTOINCREMENT, Money, update => 0 OR 1, Age,

    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "INDATE";
    public static final String COL_4 = "NOTE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "USERNAME TEXT, " + "INDATE TEXT, " + "NOTE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String contents, String values, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, contents);
        contentValues.put(COL_3, values);
        contentValues.put(COL_4, notes);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    // afto doulevi kanonika
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    // afto skai prepi na do gt ?
    public Cursor getuserData(String user) {
        SQLiteDatabase db = this.getWritableDatabase();

        // edo prepi na pesi erotima sqlite... gia to user.
        // meta prepi na do pos perno ta dedomena apo afto gia na ta stilo se
        // global gia na mporeso na ta xrisimopiiso

        Cursor res = db.rawQuery("select * from " + TABLE_NAME
                + " where USERNAME = " + user, null);
        return res;
    }

    // afto doulevi kanonika
    // prepi omos na prostheso to jobs sto pinaka
    public boolean updateData(String username, String values, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, values);
        contentValues.put(COL_4, notes);
        db.update(TABLE_NAME, contentValues, "USERNAME = ?",
                new String[] { username });
        return true;
    }

    // afto doulevi kanonika
    public Integer deleteData(String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "USERNAME = ?", new String[] { user });
    }

    // get data user for bigin for log in and after.
    public Cursor getInformation(DatabaseHelper dop) {
        SQLiteDatabase db = dop.getReadableDatabase();
        String[] coloumns = { COL_1, COL_2, COL_3, COL_4};
        Cursor CR = db
                .query(TABLE_NAME, coloumns, null, null, null, null, null);
        return CR;
    }

}
