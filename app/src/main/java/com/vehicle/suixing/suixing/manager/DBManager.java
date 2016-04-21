package com.vehicle.suixing.suixing.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vehicle.suixing.suixing.helper.DBHelper;
import com.vehicle.suixing.suixing.model.WirelessQA;
import com.vehicle.suixing.suixing.model.info.MemberInfo;
import com.vehicle.suixing.suixing.model.info.Mp3Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * 向表info中增加一个成员信息   *
     * @param memberInfo
     */
    public void add(List<MemberInfo> memberInfo) {
        db.beginTransaction();
        try {
            for (MemberInfo info : memberInfo) {
                Log.i(WirelessQA.TAG, "------add memberInfo----------");
                Log.i(WirelessQA.TAG, info.name + "/" + info.age + "/" + info.website + "/" + info.weibo);
                // 向表info中插入数据
                db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.name, info.age, info.website,
                        info.weibo });
            }
            db.setTransactionSuccessful();// 事务成功
        } finally {
            db.endTransaction();//结束事务
        }
    }
    /**
     * @add
     * @param name
     * @param age
     * @param website
     * @param weibo
     */
    public void add(int _id, String name, int age, String website, String weibo) {
        Log.i(WirelessQA.TAG, "------add data----------");
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("name", name);
        cv.put("age", age);
        cv.put("website", website);
        cv.put("weibo", weibo);
        db.insert(DBHelper.DB_TABLE_NAME, null, cv);
        Log.i(WirelessQA.TAG, name + "/" + age + "/" + website + "/" + weibo);
    }

    /**
     * 删除一个   *
     * @param name
     */
    public void delData(String name) {
        // ExecSQL("DELETE FROM info WHERE name ="+"'"+name+"'");
        String[] args = { name };
        db.delete(DBHelper.DB_TABLE_NAME, "name=?", args);
        Log.i(WirelessQA.TAG, "delete data by " + name);
    }

    /**
     *清空
     */
    public void clearData() {
        ExecSQL("DELETE FROM info");
        Log.i(WirelessQA.TAG, "clear data");
    }

    /**
     * 搜索    *
     * @param name
     */
    public ArrayList<MemberInfo> searchData(final String name) {
        String sql = "SELECT * FROM info WHERE name =" + "'" + name + "'";
        return ExecSQLForMemberInfo(sql);
    }
    public ArrayList<MemberInfo> searchAllData() {
        String sql = "SELECT * FROM info";
        return ExecSQLForMemberInfo(sql);
    }
    /**
     * 更新数据
     *
     * @param raw
     * @param rawValue
     * @param whereName
     */
    public void updateData(String raw, String rawValue, String whereName) {
        String sql = "UPDATE info SET " + raw + " =" + " " + "'" + rawValue + "'" + " WHERE name =" + "'" + whereName
                + "'";
        ExecSQL(sql);
        Log.i(WirelessQA.TAG, sql);
    }

    /**
     * 执行SQL命令返回list
     *
     * @param sql
     * @return
     */
    private ArrayList<MemberInfo> ExecSQLForMemberInfo(String sql) {
        ArrayList<MemberInfo> list = new ArrayList<MemberInfo>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            MemberInfo info = new MemberInfo();
            info._id = c.getInt(c.getColumnIndex("_id"));
            info.name = c.getString(c.getColumnIndex("name"));
            info.age = c.getInt(c.getColumnIndex("age"));
            info.website = c.getString(c.getColumnIndex("website"));
            info.weibo = c.getString(c.getColumnIndex("weibo"));
            list.add(info);
        }
        c.close();
        return list;
    }

    /**
     * 执行一个SQL语句
     *
     * @param sql
     */
    private void ExecSQL(String sql) {
        try {
            db.execSQL(sql);
            Log.i("execSql: ", sql);
        } catch (Exception e) {
            Log.e("ExecSQL Exception", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 执行SQL，返回一个游标  *
     * @param sql
     * @return
     */
    private Cursor ExecSQLForCursor(String sql) {
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void closeDB() {
        db.close();
    }

    public void add1(List<Mp3Info> mp3) {
        db.beginTransaction();
        try {
            for (Mp3Info info : mp3) {
                // 向表info中插入数据
                db.execSQL("INSERT INTO info VALUES(null,?,?,?,?,?,?,?,?,?)", new Object[] { info.id, info.title, info.artist,
                        info.album,info.displayName,info.albumId,info.duration,info.size,info.url });
            }
            db.setTransactionSuccessful();// 事务成功
        } finally {
            db.endTransaction();//结束事务
        }
    }
    public ArrayList<Mp3Info> searchAllData1() {
        String sql = "SELECT * FROM info";
        return ExecSQLForMemberInfo1(sql);
    }
    private ArrayList<Mp3Info> ExecSQLForMemberInfo1(String sql) {
        ArrayList<Mp3Info> list = new ArrayList<Mp3Info>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            Mp3Info info = new Mp3Info();
            info.id= c.getInt(c.getColumnIndex("id"));
            info.title = c.getString(c.getColumnIndex("title"));
            info.artist=c.getString(c.getColumnIndex("artist"));
            info.album = c.getString(c.getColumnIndex("album"));
            info.displayName = c.getString(c.getColumnIndex("displayName"));
            info.albumId = c.getInt(c.getColumnIndex("albumId"));
            info.duration = c.getInt(c.getColumnIndex("duration"));
            info.artist = c.getString(c.getColumnIndex("artist"));
            info.url = c.getString(c.getColumnIndex("url"));
            list.add(info);
        }
        c.close();
        return list;
    }

}