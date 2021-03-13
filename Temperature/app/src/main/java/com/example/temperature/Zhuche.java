package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Zhuche extends AppCompatActivity implements View.OnClickListener {
    YonghuBean yonghuBean=new YonghuBean();
    DatabaseHelper databaseHelper=new DatabaseHelper(this);
    EditText name;
    EditText num;
    EditText banji;
    EditText phone;
    Button zhuce;
    boolean log=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuche);
        initZhuce();
    }

    private void initZhuce() {
        name=(EditText) findViewById(R.id.name);
        num=(EditText) findViewById(R.id.num);
        banji=(EditText) findViewById(R.id.banji);
        phone=(EditText) findViewById(R.id.zphone);
        zhuce=findViewById(R.id.zhuce01);
        zhuce.setOnClickListener(this);
    }
    public void panding(){
        Cursor cursor=databaseHelper.getAllYonghuData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                String phonepanduan=cursor.getString(cursor.getColumnIndex("yh_phone"));
                System.out.println(phonepanduan);
                if(phone.getText().toString().equals(phonepanduan)){
                    log = false;
                    break;
                }
                else {
                    log=true;
                }
            }
            cursor.close();
        }
    }
    @Override
    public void onClick(View v) {
                panding();
                if(log){
                    yonghuBean.setName(name.getText().toString());
                    yonghuBean.setNum(num.getText().toString());
                    yonghuBean.setBanji(banji.getText().toString());
                    yonghuBean.setPhone(phone.getText().toString());
                    databaseHelper.insertYonghu(yonghuBean);
                    Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(this,"注册失败,该手机号已被注册",Toast.LENGTH_SHORT).show();
                }
    }
}