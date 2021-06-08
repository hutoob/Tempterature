package com.example.temperature;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TEM_NAME="tem_name";
    public static final String TEM_TEMPERATURE="tem_temperature";
    public static final String TEM_TIME="tem_time";
    public static final String TEM_WHERE="tem_where";
    public static final String TEM_TESU="tem_tesu";
    public static final String TEM_TABLE="temperature";
    public static final String Yonghu="yonghu";
    public DatabaseHelper(@Nullable Context context) {
        super(context,"imooc_temperature", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists temperature("+"id integer primary key,"+
                "tem_name varchar,"+"tem_temperature varchar,"+"tem_time varchar,"
                +"tem_where varchar,"+"tem_tesu varchar,"+"yh_num varchar,"+"yh_banji varchar,"+"yh_phone varchar)");
        String sql="create table yonghu(id integer primary key autoincrement,yh_name varchar,yh_num varchar,yh_banji varchar,yh_phone varchar)";
        db.execSQL(sql);
    }
    public void insertTemperature(TemperatureBean temperatureBean){
       SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TEM_NAME,temperatureBean.n_ame);
        cv.put(TEM_TEMPERATURE,temperatureBean.temperature);
        cv.put(TEM_TIME,temperatureBean.timeTv);
        cv.put(TEM_WHERE,temperatureBean.where);
        cv.put(TEM_TESU,temperatureBean.tesu);
        cv.put("yh_num",temperatureBean.num);
        cv.put("yh_banji",temperatureBean.banji);
        cv.put("yh_phone",temperatureBean.phone);
        database.insert(TEM_TABLE,null,cv);
    }
    public void insertYonghu(YonghuBean yonghuBean){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("yh_name",yonghuBean.name);
        cv.put("yh_num",yonghuBean.num);
        cv.put("yh_banji",yonghuBean.banji);
        cv.put("yh_phone",yonghuBean.phone);
        database.insert(Yonghu,null,cv);
    }
    public Cursor getAllYonghuData(){
        SQLiteDatabase database=getWritableDatabase();
        return database.query("yonghu",null,null,null,null,null,"yh_num",null);
    }
    public Cursor getAllTemperatureData(){
        SQLiteDatabase database=getWritableDatabase();
        return database.query("temperature",null,null,null,null,null,"tem_time",null);
    }
    public void deleAllData(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("temperature",null,null);
    }
    public void deletemore(String time,String phone){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("temperature","tem_time=? and yh_phone=?",new String[]{time,phone});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
