package com.vehicle.suixing.suixing.util.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.bean.BmobBean.VehicleInformation;
import com.vehicle.suixing.suixing.bean.musicInfo.Mp3Info;
import com.vehicle.suixing.suixing.common.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KiSoo on 2016/3/22.
 */
public class DbDao {

    private static String TAG = "DbDao";

    /***
     * 向数据库中添加车辆信息
     * @param context
     * @param vehicliInfo
     */
    public static synchronized void add(Context context, VehicleInformation vehicliInfo) {
        if (!hasAdd(context, vehicliInfo.getNum())){
            SQLiteDatabase database = DbHelper.getHelper(context).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", vehicliInfo.getUsername());
            values.put("name", vehicliInfo.getName());
            values.put("framenum",vehicliInfo.getFramenum());
            Log.e(TAG,vehicliInfo.getFramenum());
            values.put("num", vehicliInfo.getNum());
            values.put("percent", vehicliInfo.getPercent());
            values.put("size", vehicliInfo.getSize());
            values.put("mileage", vehicliInfo.getMileage());
            values.put("model", vehicliInfo.getModel());
            values.put("function", vehicliInfo.getFunction());
            values.put("speed", vehicliInfo.getSpeed());
            values.put("light", vehicliInfo.getLight());
            values.put("url", vehicliInfo.getUrl());
            database.insert(Config.VEHICLE_TABLE_NAME, null, values);
            database.close();
        }else {
            update(context,vehicliInfo);
        }
    }
    /**
     * 查询出全部的本地信息
     * */
    public static synchronized List<VehicleInformation> queryPart(Context context,String username) {
        List<VehicleInformation> result = new ArrayList<>();
        SQLiteDatabase db = DbHelper.getHelper(context).getReadableDatabase();
        String SELECT = "select * from "+Config.VEHICLE_TABLE_NAME +" where username = \"" +username+"\"";
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

    /***
     * 是否已经添加
     * @param context
     * @param num 车牌号
     * @return
     */
    public static Boolean hasAdd(Context context, String num) {
        SQLiteDatabase db = DbHelper.getHelper(context).getReadableDatabase();
        String SELECT = "select * from "+Config.VEHICLE_TABLE_NAME +" where num = \"" +num+"\"";
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
    /***
     * 清除数据
     * @param context
     */
    public static synchronized void clearAll(Context context){
        SQLiteDatabase db = DbHelper.getHelper(context).getReadableDatabase();
        String DELETE = "delete from UserData";
        Cursor cursor = db.rawQuery(DELETE, new String[]{});
        Log.e(TAG,"清空数据");
        SuixingApp.infos = new ArrayList<>();
        cursor.close();
        db.close();
    }

    /***
     *
     * @param context
     * @param vehicleInformation
     */
    public static synchronized void update(Context context,VehicleInformation vehicleInformation){
        SQLiteDatabase db = DbHelper.getHelper(context).getWritableDatabase();
        String UPDATE_SQL = " update "+Config.VEHICLE_TABLE_NAME +
                " set username = '"+vehicleInformation.getUsername()+
                "', name = '" +vehicleInformation.getName()+
                "', framenum  = '" + vehicleInformation.getNum()+
                "', percent = '"+vehicleInformation.getPercent()+
                "', size = '"+vehicleInformation.getSize()+
                "', mileage = '"+ vehicleInformation.getMileage()+
                "', model = '"+vehicleInformation.getModel()+
                "', function = '"+vehicleInformation.getFunction()+
                "', speed = '"+vehicleInformation.getSpeed()+
                "', light = '"+vehicleInformation.getLight()+
                "', url = '"+vehicleInformation.getUrl()+
                "' where num = '"+vehicleInformation.getNum()+"'";
        Cursor cursor = db.rawQuery(UPDATE_SQL, new String[]{});
        cursor.close();
        db.close();
    }

    /***
     * 清除数据库
     * @param context
     */
    public static synchronized void clearData(Context context) {
        String sql = "DELETE FROM "+Config.MUSIC_TABLE_NAME;
        DbHelper.getHelper(context).getWritableDatabase().execSQL(sql);
    }

    /***
     * 批量添加音乐
     * @param infos
     * @param context
     */
    public static synchronized void addPartMusic(List<Mp3Info> infos,Context context) {
        SQLiteDatabase db = DbHelper.getHelper(context).getWritableDatabase();
        ContentValues values;
        for (Mp3Info info:infos){
            values = new ContentValues();
            values.put("title",info.getFilename());
            values.put("artist",info.getSingername());
            values.put("album",info.getAlbum_name());
            values.put("duration",info.getDuration());
            values.put("url",info.getUrl());
            db.insert(Config.MUSIC_TABLE_NAME,null,values);
        }
        db.close();
    }

    /***
     * 批量添加音乐
     * @param context
     * @return
     */
    public static synchronized List<Mp3Info> queryPartMusic(Context context) {
        String sql = "SELECT * FROM "+Config.MUSIC_TABLE_NAME;
        List<Mp3Info> list = new ArrayList<Mp3Info>();
        SQLiteDatabase db = DbHelper.getHelper(context).getReadableDatabase();
        Cursor cursor  = db.rawQuery(sql, null);
        Mp3Info info ;
        while (cursor.moveToNext()) {
            info = new Mp3Info();
            info.setFilename(cursor.getString(cursor.getColumnIndex("title")));
            info.setSingername(cursor.getString(cursor.getColumnIndex("artist")));
            info.setAlbum_name(cursor.getString(cursor.getColumnIndex("album")));
            info.setDuration(cursor.getInt(cursor.getColumnIndex("duration")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            list.add(info);
        }
        cursor.close();
        return list;
    }
}
