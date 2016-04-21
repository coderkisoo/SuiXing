package com.vehicle.suixing.suixing.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vehicle.suixing.suixing.model.WirelessQA;

/**
 * Created by lenovo on 2016/4/20.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "wirelessqa.db";
    public static final String DB_TABLE_NAME = "info";
    private static final int DB_VERSION=1;
    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS info" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, title STRING, artist STRING,album STRING,displayName STRING,albumId INTEGER,duration INTEGER,size INTEGER,url STRING)");
        Log.i(WirelessQA.TAG, "create table");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE info ADD COLUMN other STRING");
        Log.i("WIRELESSQA", "update sqlite "+oldVersion+"---->"+newVersion);
    }

}