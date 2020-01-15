package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "companyDatabase";
    public static final String TABLE_USERS = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL(TABLE_CREATE);
    }

}
