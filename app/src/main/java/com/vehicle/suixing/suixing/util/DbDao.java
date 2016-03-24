package com.vehicle.suixing.suixing.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.vehicle.suixing.suixing.bean.VehicleInformation;
import com.vehicle.suixing.suixing.common.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/3/22.
 */
public class DbDao {
    static String TAG = "DbDao";

    /**
     * 像数据库中添加车辆信息
     * */
    public static synchronized void add(Context context, String UserName, VehicleInformation vehicleInformation, String url) {
        if (!queryIsIn(context,vehicleInformation.getNum())){
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", UserName);
            values.put("name", vehicleInformation.getName());
            values.put("framenum",vehicleInformation.getFramenum());
            values.put("num", vehicleInformation.getNum());
            values.put("percent", vehicleInformation.getPercent());
            values.put("size", vehicleInformation.getSize());
            values.put("mileage", vehicleInformation.getMileage());
            values.put("model", vehicleInformation.getModel());
            values.put("function", vehicleInformation.getFunction());
            values.put("speed", vehicleInformation.getSpeed());
            values.put("light", vehicleInformation.getLight());
            values.put("url", url);
            database.insert(Config.tableName, null, values);
            database.close();
            Toast.makeText(context, "添加车辆成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"你似乎已经添加过这辆车了哦",Toast.LENGTH_SHORT).show();
        }
    }
    public static synchronized List<VehicleInformation> queryPart(Context context,String username) {
        List<VehicleInformation> result = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String SELECT = "select * from UserData where username = \"" +username+"\"";
        Cursor cursor = db.rawQuery(SELECT, new String[]{});
        Log.e(TAG, "查询到一共" + cursor.getCount() + "条数据");
        while (cursor.moveToNext()) {
            /**
             * 将从表中获取的信息依次赋值给vehicleInformation
             * */
            VehicleInformation info = new VehicleInformation();
            info.setName(cursor.getString(cursor.getColumnIndex("name")));
            info.setNum(cursor.getString(cursor.getColumnIndex("num")));
            info.setFramenum(cursor.getString(cursor.getColumnIndex("framenum")));
            info.setPercent(cursor.getString(cursor.getColumnIndex("percent")));
            info.setSize(cursor.getString(cursor.getColumnIndex("size")));
            info.setMileage(cursor.getString(cursor.getColumnIndex("mileage")));
            info.setModel(cursor.getString(cursor.getColumnIndex("model")));
            info.setFunction(cursor.getString(cursor.getColumnIndex("function")));
            info.setSpeed(cursor.getString(cursor.getColumnIndex("speed")));
            info.setLight(cursor.getString(cursor.getColumnIndex("light")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            result.add(info);
        }
        Log.e(TAG,"一共打印出"+result.size()+"条数据");
        cursor.close();
        db.close();
        return result;
    }
    public static Boolean queryIsIn(Context context,String num) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String SELECT = "select * from UserData where num = \"" +num+"\"";
        Cursor cursor = db.rawQuery(SELECT, new String[]{});
        int number = cursor.getCount();
        cursor.close();
        db.close();
        if (number>=1){
            /**
             * 查询当前车辆是否已经被添加
             * */
            return true;
        }else {
            return false;
        }
    }

}
