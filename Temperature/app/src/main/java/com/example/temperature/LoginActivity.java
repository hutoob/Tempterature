package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper databaseHelper=new DatabaseHelper(this);
    EditText phone;
    Button login,zhuce;
    boolean log=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intilogin();
    }

    private void intilogin() {
        phone=findViewById(R.id.phone);
        login=findViewById(R.id.login);
        zhuce=findViewById(R.id.zhuce);
        login.setOnClickListener(this);
        zhuce.setOnClickListener(this);
    }
    public void pandDuan(){
        Cursor cursor=databaseHelper.getAllYonghuData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                String phonepanduan=cursor.getString(cursor.getColumnIndex("yh_phone"));
                if(phone.getText().toString().equals(phonepanduan)){
                    log=true;
                }
            }
            cursor.close();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                pandDuan();
                if(log){
                    Intent iti=new Intent(this,MainActivity.class);
                    iti.putExtra("phone",phone.getText().toString() );
                    startActivity(iti);
                }
                else {
                    Toast.makeText(LoginActivity.this,"无此用户",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.zhuce:
                Intent it=new Intent(this,Zhuche.class);
                startActivity(it);
            break;
        }
    }
}