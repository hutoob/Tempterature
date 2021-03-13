package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DaochuActivity extends AppCompatActivity {
    String name;
    String phone;
    DatabaseHelper databaseHelper;
    List<TemperatureBean> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daochu);
        inidaochu();
    }
    public void inidaochu(){
        databaseHelper=new DatabaseHelper(this);
        name=getIntent().getStringExtra("name");
        phone=getIntent().getStringExtra("phone");
        gettem();
        HSSFWorkbook mWorkbook = new HSSFWorkbook();
        HSSFSheet mSheet = mWorkbook.createSheet(name);
        createExcelHead(mSheet);
        for (TemperatureBean temperatureBean : list) {
            //System.out.println(student.id + "," + student.name + "," + student.gender + "," + student.age);
            createCell(temperatureBean.n_ame,temperatureBean.num,temperatureBean.timeTv,temperatureBean.where,temperatureBean.temperature,temperatureBean.banji,temperatureBean.phone,mSheet);
        }
        File xlsFile = new File(Environment.getExternalStorageDirectory(), "excel.xls");
        try {
            if (!xlsFile.exists()) {
                xlsFile.createNewFile();
            }
            mWorkbook.write(xlsFile);// 或者以流的形式写入文件 mWorkbook.write(new FileOutputStream(xlsFile));
            mWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createExcelHead(HSSFSheet mSheet) {
        HSSFRow headRow = mSheet.createRow(0);
        headRow.createCell(0).setCellValue("姓名");
        headRow.createCell(1).setCellValue("学号");
        headRow.createCell(2).setCellValue("时间");
        headRow.createCell(3).setCellValue("地点");
        headRow.createCell(4).setCellValue("体温");
        headRow.createCell(5).setCellValue("班级");
        headRow.createCell(6).setCellValue("手机号");
    }
    public void gettem(){
        Cursor cursor=databaseHelper.getAllTemperatureData();
        if(cursor!=null){
            while (cursor.moveToNext()){
                String gphone=cursor.getString(cursor.getColumnIndex("yh_phone"));
                if(gphone.equals(phone)) {
                    TemperatureBean temperatureBean = new TemperatureBean();
                    temperatureBean.n_ame =cursor.getString(cursor.getColumnIndex("tem_name"));
                    temperatureBean.timeTv = cursor.getString(cursor.getColumnIndex("tem_time"));
                    temperatureBean.temperature = cursor.getString(cursor.getColumnIndex("tem_temperature"));
                    temperatureBean.where = cursor.getString(cursor.getColumnIndex("tem_where"));
                    temperatureBean.tesu=cursor.getString(cursor.getColumnIndex("tem_tesu"));
                    temperatureBean.num=cursor.getString(cursor.getColumnIndex("yh_num"));
                    temperatureBean.banji=cursor.getString(cursor.getColumnIndex("yh_banji"));
                    temperatureBean.phone=phone;
                    list.add(temperatureBean);
                }
            }
            cursor.close();
        }
    }
    private static void createCell(String name,String num,String time,String where,String temepature,String banji,String yphone,HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

        dataRow.createCell(0).setCellValue(name);
        dataRow.createCell(1).setCellValue(num);
        dataRow.createCell(2).setCellValue(time);
        dataRow.createCell(3).setCellValue(where);
        dataRow.createCell(4).setCellValue(temepature);
        dataRow.createCell(5).setCellValue(banji);
        dataRow.createCell(6).setCellValue(yphone);
    }
    public void saveFile(String fileName, Context context, int rawid) throws IOException {

        // 首先判断该目录下的文件夹是否存在
        File dir = new File(Environment.getExternalStorageDirectory() + "/inspection/");
        if (!dir.exists()) {
            // 文件夹不存在 ， 则创建文件夹
            dir.mkdirs();
        }

        // 判断目标文件是否存在
        File file1 = new File(dir, fileName);

        if (!file1.exists()) {
            file1.createNewFile(); // 创建文件

        }
        // 开始进行文件的复制
        InputStream input = context.getResources().openRawResource(rawid); // 获取资源文件raw
        // 标号
        try {

            FileOutputStream out = new FileOutputStream(file1); // 文件输出流、用于将文件写到SD卡中
            // -- 从内存出去
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = (input.read(buffer))) != -1) { // 读取文件，-- 进到内存

                out.write(buffer, 0, len); // 写入数据 ，-- 从内存出
            }

            input.close();
            out.close(); // 关闭流
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
