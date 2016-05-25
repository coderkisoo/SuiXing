package com.vehicle.suixing.suixing.util.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KiSoo on 2016/3/22.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static class DbHelperViewHolder{
        private static DbHelper helper;
        private static DbHelper getHelper(Context context){
            if (helper == null){
                helper = new DbHelper(context);
            }
            return helper;
        }
    }

    private DbHelper(Context context) {
        super(context, "data.db", null, 1);
    }

    /***
     * 单例
     * @param context
     * @return dbHelper
     */
    public static DbHelper getHelper(Context context){
        return DbHelperViewHolder.getHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VEHICLE_TABLE = "create table if not exists UserData(" +
                "username string(20)," +//用户名
                "name string(50)," +//车名
                "num string(10)," +//车牌号
                "framenum string(20)," +//车架号
                "percent string(10)," +//汽油量
                "size string(10)," +//车身级别
                "mileage string(10)," +//里程数
                "model string(10)," +//发动机型号
                "function string(10)," +//发动机性能
                "speed string(10)," +//变速器性能
                "light string(10)," +//车灯状况
                "url string(40))";//车辆的图片
        String CREATE_MUSIC_TABLE = "CREATE TABLE IF NOT EXISTS UserMusic" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " title STRING," +//音乐名称
                " artist STRING," +//歌手
                "album STRING," +//专辑名
                "duration INTEGER," +//时长
                "url STRING)";//音乐url
        db.execSQL(CREATE_VEHICLE_TABLE);
        db.execSQL(CREATE_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE info ADD COLUMN other STRING");
    }
}
