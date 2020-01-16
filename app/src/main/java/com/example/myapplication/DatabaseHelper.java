package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends  SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "companyDatabase";
    private static final String TABLE_USERS = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMAIL + " TEXT " +
                    " )";

    public DatabaseHelper(Context context) {
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
    public Cursor fetch() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_USERS;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    public boolean addData(String text) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, text);
        SQLiteDatabase db = this.getWritableDatabase() ;

        long result = db.insert(TABLE_USERS, null, values);
        if(result == -1 ){
            return false;
        }else{
            return true;
        }
    }
    public void updateData(String newName, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_USERS + " SET " + COLUMN_EMAIL +
                " = '" + newName + "' WHERE " + COLUMN_ID + " = '" + id + "'" ;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }
    public int count() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_USERS;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }
}

