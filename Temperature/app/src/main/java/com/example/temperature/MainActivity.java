package com.example.temperature;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<TemperatureBean> mtemperatureBean;
    private DatabaseHelper mdatabaseHelper;
    private TemperatureAdapter mAdapter;
    private TemperatureBean stemperatureBean=new TemperatureBean();
    public String phone;
    public String yname,banji;
    ListView listView;
    TextView timeTV;
    Button bar,map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone=getIntent().getStringExtra("phone");
        mdatabaseHelper=new DatabaseHelper(this);
        mtemperatureBean=new ArrayList<>();
        listView=(ListView)findViewById(R.id.lv_main);
        iniTemperatureData();
        mAdapter=new TemperatureAdapter(this,mtemperatureBean);
        listView.setAdapter(mAdapter);
        Button te_insert=findViewById(R.id.te_insert);
        te_insert.setOnClickListener(this);
        Button back=findViewById(R.id.te_tuichu);
        back.setOnClickListener(this);
        Button up=findViewById(R.id.main_up);
        Button daochu=findViewById(R.id.main_daochu);
        bar=findViewById(R.id.bar);
        up.setOnClickListener(this);
        daochu.setOnClickListener(this);
        bar.setOnClickListener(this);
        map=findViewById(R.id.map);
        map.setOnClickListener(this);
    }
    public void getYname(){
        Cursor cursor=mdatabaseHelper.getAllYonghuData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                String gname=cursor.getString(cursor.getColumnIndex("yh_name"));
                String gphone=cursor.getString(cursor.getColumnIndex("yh_phone"));
                String gbanji=cursor.getString(cursor.getColumnIndex("yh_banji"));
                if(gphone.equals(phone)){
                    yname=gname;
                    banji=gbanji;
                }
            }
        }

    }
    private void iniTemperatureData(){
        Cursor cursor=mdatabaseHelper.getAllTemperatureData();
        getYname();
        if(cursor!=null){
            while (cursor.moveToNext()){
                String gname=cursor.getString(cursor.getColumnIndex("tem_name"));
                if(gname.equals(yname)) {
                    TemperatureBean temperatureBean = new TemperatureBean();
                    temperatureBean.n_ame = gname;
                    temperatureBean.timeTv = cursor.getString(cursor.getColumnIndex("tem_time"));
                    temperatureBean.temperature = cursor.getString(cursor.getColumnIndex("tem_temperature"));
                    temperatureBean.where = cursor.getString(cursor.getColumnIndex("tem_where"));
                    temperatureBean.tesu=cursor.getString(cursor.getColumnIndex("tem_tesu"));
                    mtemperatureBean.add(temperatureBean);
                }
            }
            cursor.close();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.te_insert:
                Intent itl=new Intent(this,newshuju.class);
                itl.putExtra("name",yname);
                itl.putExtra("phone",phone);
                startActivity(itl);
                break;
            case R.id.te_tuichu:
                Intent it=new Intent(this,LoginActivity.class);
                startActivity(it);
                break;
            case R.id.main_up:
                Intent it2=new Intent(this,UpActivity.class);
                it2.putExtra("name",yname);
                it2.putExtra("phone",phone);
                it2.putExtra("banji",banji);
                startActivity(it2);
                break;
            case R.id.main_daochu:
                Intent it3=new Intent(this,DaochuActivity.class);
                it3.putExtra("name",yname);
                it3.putExtra("phone",phone);
                startActivity(it3);
                break;
            case R.id.bar:
                Intent it4=new Intent(this,BarActivity.class);
                it4.putExtra("phone",phone);
                startActivity(it4);
                break;
            case R.id.map:
                Intent it5=new Intent(this,MapActivity.class);
                it5.putExtra("phone",phone);
                startActivity(it5);
                break;
        }
    }
   /* @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }*/
}