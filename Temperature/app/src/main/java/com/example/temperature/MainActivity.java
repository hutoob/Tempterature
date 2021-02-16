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
    TextView timeTV;
    //  弹出按钮
    private Button btnDiqu;
    //  省
    private List<ShengBean> options1Items = new ArrayList<ShengBean>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdatabaseHelper=new DatabaseHelper(this);
        mtemperatureBean=new ArrayList<>();
        ListView temperaturelist=(ListView)findViewById(R.id.lv_main);
        iniTemperatureData();
        mAdapter=new TemperatureAdapter(this,mtemperatureBean);
        temperaturelist.setAdapter(mAdapter);
        FloatingActionButton te_insert=findViewById(R.id.te_insert);
        te_insert.setOnClickListener(this);
    }

    private void iniTemperatureData(){
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
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.te_insert:
                Intent itl=new Intent(this,newshuju.class);
                startActivity(itl);
                break;
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}