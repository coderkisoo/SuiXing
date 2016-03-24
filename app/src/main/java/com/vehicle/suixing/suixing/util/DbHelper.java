package com.vehicle.suixing.suixing.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KiSoo on 2016/3/22.
 */
public class DbHelper extends SQLiteOpenHelper{

    public DbHelper(Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table if not exists UserData(" +
                "username string(20)," +//用户名
                "name string(10)," +//车名
                "num string(10)," +//车牌号
                "framenum string(10)," +//车架号
                "percent string(10)," +//汽油量
                "size string(10)," +//车身级别
                "mileage string(10)," +//里程数
                "model string(10)," +//发动机型号
                "function string(10)," +//发动机性能
                "speed string(10)," +//变速器性能
                "light string(10)," +//车灯状况
                "url string(40))";//车辆的图片
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
