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
    public static final String TEM_TABLE="temperature";

    public DatabaseHelper(@Nullable Context context) {
        super(context,"imooc_temperature", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists temperature("+"id integer primary key,"+
                "tem_name varchar,"+"tem_temperature varchar,"+"tem_time varchar,"
        +"tem_where varchar)");
    }

    public void insertTemperature(TemperatureBean temperatureBean){
       SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TEM_NAME,temperatureBean.n_ame);
        cv.put(TEM_TEMPERATURE,temperatureBean.temperature);
        cv.put(TEM_TIME,temperatureBean.timeTv);
        cv.put(TEM_WHERE,temperatureBean.where);
        database.insert(TEM_TABLE,null,cv);
    }
    public Cursor getAllTemperatureData(){
        SQLiteDatabase database=getWritableDatabase();
        return database.query("temperature",null,null,null,null,null,"tem_time",null);
    }
    public void deleAllData(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("temperature",null,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
