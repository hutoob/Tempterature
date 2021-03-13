package com.example.temperature;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpActivity extends AppCompatActivity {
    String phone;
    private DatabaseHelper mdatabaseHelper;
    TextView tianbao,banjinum,notianbao,yichang;
    String banji;
    int bnum=0,tnum=0,nnum=0,ynum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);
        phone= getIntent().getStringExtra("phone");
        banji=getIntent().getStringExtra("banji");
        mdatabaseHelper=new DatabaseHelper(this);
        try {
            initup();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void initup() throws ParseException {
        tianbao=findViewById(R.id.tianbao);
        banjinum=findViewById(R.id.banjinum);
        notianbao=findViewById(R.id.notianbao);
        yichang=findViewById(R.id.yichang);
        gettianbao();
        getbanjinum();
        banjinum.setText(Integer.toString(bnum));
        nnum=bnum-tnum;
        tianbao.setText(Integer.toString(tnum));
        notianbao.setText(Integer.toString(nnum));
        yichang.setText(Integer.toString(ynum));
    }
    public void getbanjinum(){
        Cursor cursor=mdatabaseHelper.getAllYonghuData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                String gbanji=cursor.getString(cursor.getColumnIndex("yh_banji"));
                if(banji.equals(gbanji)){
                    bnum=bnum+1;
                }
            }
        }
    }
    public void gettianbao() throws ParseException {
        Cursor cursor=mdatabaseHelper.getAllTemperatureData();
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String yangli=sdf.format(date);
        Date date1=sdf.parse(yangli);
        if(cursor!=null){
            while(cursor.moveToNext()){
                String gbanji=cursor.getString(cursor.getColumnIndex("yh_banji"));
                String time=cursor.getString(cursor.getColumnIndex("tem_time"));
                String tem=cursor.getString(cursor.getColumnIndex("tem_temperature"));
                Date date2=sdf.parse(time);
                if(banji.equals(gbanji)&&sameDate(date1,date2)){
                     tnum=tnum+1;
                     if(panduanTemp(tem))
                         ynum=ynum+1;
                    }
                }
            }
        }
    public boolean panduanTemp(String tem){
        String tem2=tem.substring(0,tem.length()-1);
        if(Double.parseDouble(tem2)>=37){
            return true;
        }
        else return false;
    }
    public static boolean sameDate(Date d1, Date d2) {
        if(null == d1 || null == d2)
            return false;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        return  cal1.getTime().equals(cal2.getTime());
    }
}