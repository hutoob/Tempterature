package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class newshuju extends AppCompatActivity implements View.OnClickListener {
    private List<TemperatureBean> mtemperatureBean;
    private DatabaseHelper mdatabaseHelper;
    private TemperatureAdapter mAdapter;
    private TemperatureBean stemperatureBean=new TemperatureBean();
    TextView timeTV;
    TextView name;
    EditText temperature;
    String yname;
    String phone;
    String tesu;
    RadioButton rtn1,rtn2,rtn3,rtn4,rtn5;
    EditText et1,et2,et3,et4;
    RadioGroup gp;
    String mtime;
    private static final String[] authBaseArr = {//申请类型
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int authBaseRequestCode = 1;
    //  弹出按钮
    private Button btnDiqu,cancel,ensure;
    //  省
    private List<ShengBean> options1Items = new ArrayList<ShengBean>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newshuju);
        yname=getIntent().getStringExtra("name");
        phone=getIntent().getStringExtra("phone");
        initview();
        setiniTime();
    }
    private void initview(){
        name=(TextView) findViewById(R.id.et_name);
        name.setText(yname);
        timeTV=(TextView)findViewById(R.id.tv_time);
        temperature=findViewById(R.id.et_temperature);
        btnDiqu=findViewById(R.id.btn_where);
        getwhere();
        gp=findViewById(R.id.gp);
        rtn1=findViewById(R.id.rtn1);
        rtn2=findViewById(R.id.rtn2);
        rtn3=findViewById(R.id.rtn3);
        rtn4=findViewById(R.id.rtn4);
        rtn5=findViewById(R.id.rtn5);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        et4=findViewById(R.id.et4);
        cancel=findViewById(R.id.new_cancel);
        ensure=findViewById(R.id.new_ensure);
        mdatabaseHelper=new DatabaseHelper(this);
        cancel.setOnClickListener(this);
        btnDiqu.setOnClickListener(this);
        ensure.setOnClickListener(this);
        gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==rtn1.getId()){
                    tesu=rtn1.getText().toString();
                    et1.setVisibility(View.GONE);
                    et2.setVisibility(View.GONE);
                    et3.setVisibility(View.GONE);
                    et4.setVisibility(View.GONE);
                }
                else if(checkedId==rtn2.getId()){
                    et1.setVisibility(View.VISIBLE);
                    et2.setVisibility(View.GONE);
                    et3.setVisibility(View.GONE);
                    et4.setVisibility(View.GONE);
                    tesu=rtn2.getText().toString()+et1.getText().toString();
                }
                else if(checkedId==rtn3.getId()){
                    et2.setVisibility(View.VISIBLE);
                    et1.setVisibility(View.GONE);
                    et3.setVisibility(View.GONE);
                    et4.setVisibility(View.GONE);
                    tesu=rtn3.getText().toString()+et2.getText().toString();
                }
                else if(checkedId==rtn4.getId()){
                    et3.setVisibility(View.VISIBLE);
                    et1.setVisibility(View.GONE);
                    et2.setVisibility(View.GONE);
                    et4.setVisibility(View.GONE);
                    tesu=rtn4.getText().toString()+et3.getText().toString();
                }
                else {
                    et1.setVisibility(View.GONE);
                    et2.setVisibility(View.GONE);
                    et3.setVisibility(View.GONE);
                    et4.setVisibility(View.VISIBLE);
                    tesu=et4.getText().toString();
                }
            }
        });
    }
    private void setiniTime(){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time=sdf.format(date);
        stemperatureBean.setTimeTv(time);
        timeTV.setText(time);
    }
   /* private void iniTemperatureData(){
        Cursor cursor=mdatabaseHelper.getAllTemperatureData();
        if(cursor!=null){
            while (cursor.moveToNext()){
                TemperatureBean temperatureBean=new TemperatureBean();
                temperatureBean.n_ame=cursor.getString(cursor.getColumnIndex("tem_name"));
                temperatureBean.timeTv=cursor.getString(cursor.getColumnIndex("tem_time"));
                temperatureBean.temperature=cursor.getString(cursor.getColumnIndex("tem_temperature"));
                temperatureBean.where=cursor.getString(cursor.getColumnIndex("tem_where"));
                mtemperatureBean.add(temperatureBean);
            }
            cursor.close();
        }
    }*/
    private boolean hasBasePhoneAuth() {
        PackageManager pm = getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    private void initNavi() {
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
    }

    public void locationUpdates(Location location){
        if(location != null){
            StringBuilder stringBuilder = new StringBuilder(); //构建一个字符串构建器，用于记录定位信息
            stringBuilder.append("经度：");
            stringBuilder.append(location.getLongitude());
            stringBuilder.append("\n纬度：");
            stringBuilder.append(location.getLatitude());
            String ab = getAddress(location.getLatitude(),location.getLongitude());
            btnDiqu.setText(ab);
        }
        else{
            btnDiqu.setText("GPS失效啦！");
        }
    }

    public String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = (Address) ((List) addresses).get(0);
                String data = address.toString();
                int startSpace = data.indexOf("\"") + ":".length();
                int endSpace = data.indexOf("\"", startSpace);
                String space=data.substring(startSpace,endSpace);
                return space;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "获取失败！";
    }
    public void setStemperatureBean(){
        Cursor cursor=mdatabaseHelper.getAllYonghuData();
        if(cursor!=null){
            while (cursor.moveToNext()){
                String yphone=cursor.getString(cursor.getColumnIndex("yh_phone"));
                if(phone.equals(yphone)){
                    stemperatureBean.num=cursor.getString(cursor.getColumnIndex("yh_num"));
                    stemperatureBean.banji=cursor.getString(cursor.getColumnIndex("yh_banji"));
                    stemperatureBean.phone=yphone;
                    break;
                }
            }
            cursor.close();
        }
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
    public void gettianbao(){
        Cursor cursor=mdatabaseHelper.getAllTemperatureData();
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String yangli=sdf.format(date);
        try{
            Date date1=sdf.parse(yangli);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    String time=cursor.getString(cursor.getColumnIndex("tem_time"));
                    String mphone=cursor.getString(cursor.getColumnIndex("yh_phone"));
                    Date date2=sdf.parse(time);
                    if(sameDate(date1,date2)&&phone.equals(mphone)){
                        mtime=time;
                    }
                }
            }
        }catch (Exception P){
            P.printStackTrace();
        }

    }
    /**
     * 解析数据并组装成自己想要的list
     */
/*    private void parseData(){
        String jsonStr = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
//     数据解析
        Gson gson =new Gson();
        java.lang.reflect.Type type =new TypeToken<List<ShengBean>>(){}.getType();
        List<ShengBean>shengList=gson.fromJson(jsonStr, type);
//     把解析后的数据组装成想要的list
        options1Items = shengList;
//     遍历省
        for(int i = 0; i <shengList.size() ; i++) {
//         存放城市
            ArrayList<String> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
//         遍历市
            for(int c = 0; c <shengList.get(i).city.size() ; c++) {
//        拿到城市名称
                String cityName = shengList.get(i).city.get(c).name;
                cityList.add(cityName);

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (shengList.get(i).city.get(c).area == null || shengList.get(i).city.get(c).area.size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(shengList.get(i).city.get(c).area);
                }
                province_AreaList.add(city_AreaList);
            }
            *//**
             * 添加城市数据
             *//*
            options2Items.add(cityList);
            *//**
             * 添加地区数据
             *//*
            options3Items.add(province_AreaList);
        }

    }

    *//**
     * 展示选择器
     *//*
    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).name +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                stemperatureBean.setWhere(tx);
                //Toast.makeText(MainActivity.this, tx, Toast.LENGTH_SHORT).show();
                btnDiqu.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        *//*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*//*
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }*/
    public void getwhere(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        initNavi();

        //权限检查的代码
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,//指定GPS定位提供者
                1000,//指定数据更新的间隔时间
                1,//位置间隔的距离为1m
                new LocationListener() {//监听GPS信息是否改变
                    @Override
                    public void onLocationChanged(Location location) {//GPS信息发送改变时回调
                        Log.i("lgq","onLocationChanged===="+location.getProvider());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {//GPS状态发送改变时回调

                    }

                    @Override
                    public void onProviderEnabled(String provider) { //定位提供者启动时回调

                    }

                    @Override
                    public void onProviderDisabled(String provider) { //定位提供者关闭时回调

                    }
                }
        );
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//获取最新的定位信息
        if (location==null){
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,//指定GPS定位提供者
                    5000,//指定数据更新的间隔时间
                    10,//位置间隔的距离为1m
                    new LocationListener() {//监听GPS信息是否改变
                        @Override
                        public void onLocationChanged(Location location) {//GPS信息发送改变时回调
                            Log.i("lgq","onLocationChanged===="+location.getProvider());
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {//GPS状态发送改变时回调

                        }

                        @Override
                        public void onProviderEnabled(String provider) { //定位提供者启动时回调

                        }

                        @Override
                        public void onProviderDisabled(String provider) { //定位提供者关闭时回调

                        }
                    }
            );
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//获取最新的定位信息
        }
        locationUpdates(location);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_where:
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                initNavi();

                //权限检查的代码
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,//指定GPS定位提供者
                        1000,//指定数据更新的间隔时间
                        1,//位置间隔的距离为1m
                        new LocationListener() {//监听GPS信息是否改变
                            @Override
                            public void onLocationChanged(Location location) {//GPS信息发送改变时回调
                                Log.i("lgq","onLocationChanged===="+location.getProvider());
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {//GPS状态发送改变时回调

                            }

                            @Override
                            public void onProviderEnabled(String provider) { //定位提供者启动时回调

                            }

                            @Override
                            public void onProviderDisabled(String provider) { //定位提供者关闭时回调

                            }
                        }
                );
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//获取最新的定位信息
                if (location==null){
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,//指定GPS定位提供者
                            5000,//指定数据更新的间隔时间
                            10,//位置间隔的距离为1m
                            new LocationListener() {//监听GPS信息是否改变
                                @Override
                                public void onLocationChanged(Location location) {//GPS信息发送改变时回调
                                    Log.i("lgq","onLocationChanged===="+location.getProvider());
                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {//GPS状态发送改变时回调

                                }

                                @Override
                                public void onProviderEnabled(String provider) { //定位提供者启动时回调

                                }

                                @Override
                                public void onProviderDisabled(String provider) { //定位提供者关闭时回调

                                }
                            }
                    );
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//获取最新的定位信息
                }
                locationUpdates(location);
                break;
            case R.id.new_cancel:
                finish();
                break;
            case R.id.new_ensure:
                gettianbao();
                if(mtime!=null){
                    mdatabaseHelper.deletemore(mtime,phone);
                }
                setStemperatureBean();
                stemperatureBean.n_ame=name.getText().toString();
                stemperatureBean.temperature=temperature.getText().toString()+"℃";
                stemperatureBean.where=btnDiqu.getText().toString();
                stemperatureBean.tesu=tesu;
                mdatabaseHelper.insertTemperature(stemperatureBean);
                Intent it=new Intent(this,MainActivity.class);
                it.putExtra("phone",phone);
                startActivity(it);
                break;
        }
    }
}