package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class DaochuActivity extends AppCompatActivity {
    String name;
    String phone;
    DatabaseHelper databaseHelper;
    List<TemperatureBean> list=new ArrayList<>();
    String title="学生14天健康情况登记表";
    String baozheng="本人承诺：自觉履行疫情防控责任和义务，保证以上填报信息全部属实，如有隐瞒，自愿承担相应法律后果。";
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
        createExcel();
    }

    private void createExcel() {
        WritableWorkbook wb = null;
        int i=6,j=0;
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String yangli=sdf.format(date);
        gettem();
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "jiankang.xls");
            if (!file.exists()) {
                file.createNewFile();
            }
            wb = Workbook.createWorkbook(file);
            WritableSheet sheet = wb.createSheet(name, 0);
            //设置单元格样式
            //样式1
            WritableFont font1 = new WritableFont(WritableFont.createFont("黑体"), 18);
            WritableCellFormat format = new WritableCellFormat(font1);
            format.setAlignment(Alignment.CENTRE);
            format.setVerticalAlignment(VerticalAlignment.CENTRE);
            //样式2
            WritableFont font2 = new WritableFont(WritableFont.createFont("宋体"), 12,WritableFont.BOLD);
            WritableCellFormat format1 = new WritableCellFormat(font2);
            format1.setWrap(true);
            format1.setAlignment(Alignment.CENTRE);
            format1.setVerticalAlignment(VerticalAlignment.CENTRE);
            //样式3
            WritableFont font3 = new WritableFont(WritableFont.createFont("楷体"), 12);
            WritableCellFormat format2 = new WritableCellFormat(font3);
            format2.setWrap(true);
            format2.setAlignment(Alignment.CENTRE);
            format2.setVerticalAlignment(VerticalAlignment.CENTRE);
            //样式4
            WritableFont font4 = new WritableFont(WritableFont.createFont("仿宋"), 12);
            WritableCellFormat format3 = new WritableCellFormat(font4);
            format3.setWrap(true);
            format3.setAlignment(Alignment.CENTRE);
            format3.setVerticalAlignment(VerticalAlignment.CENTRE);
            //样式5
            WritableFont font5 = new WritableFont(WritableFont.createFont("楷体"), 11);
            WritableCellFormat format4 = new WritableCellFormat(font5);
            format4.setWrap(true);
            format4.setAlignment(Alignment.CENTRE);
            format4.setVerticalAlignment(VerticalAlignment.CENTRE);
            //合并单元格
            sheet.mergeCells(0, 0, 6, 0);
            sheet.mergeCells(5,1,6,1);
            sheet.mergeCells(1,2,3,2);
            sheet.mergeCells(5,2,6,2);
            sheet.mergeCells(1,3,3,3);
            sheet.mergeCells(5,3,6,3);
            sheet.mergeCells(0, 4, 6, 4);
            sheet.mergeCells(3, 5, 4, 5);
            sheet.mergeCells(5, 5, 6, 5);
            sheet.mergeCells(3, 6, 4, 6);
            sheet.mergeCells(5, 6, 6, 6);
            sheet.mergeCells(3, 7, 4, 7);
            sheet.mergeCells(5, 7, 6, 7);
            sheet.mergeCells(3, 8, 4, 8);
            sheet.mergeCells(5, 8, 6, 8);
            sheet.mergeCells(3, 9, 4, 9);
            sheet.mergeCells(5, 9, 6, 9);
            sheet.mergeCells(3, 10, 4, 10);
            sheet.mergeCells(5, 10, 6, 10);
            sheet.mergeCells(3, 11, 4, 11);
            sheet.mergeCells(5, 11, 6, 11);
            sheet.mergeCells(3, 12, 4, 12);
            sheet.mergeCells(5, 12, 6, 12);
            sheet.mergeCells(3, 13, 4, 13);
            sheet.mergeCells(5, 13, 6, 13);
            sheet.mergeCells(3, 14, 4, 14);
            sheet.mergeCells(5, 14, 6, 14);
            sheet.mergeCells(3, 15, 4, 15);
            sheet.mergeCells(5, 15, 6, 15);
            sheet.mergeCells(3, 16, 4, 16);
            sheet.mergeCells(5, 16, 6, 16);
            sheet.mergeCells(3, 17, 4, 17);
            sheet.mergeCells(5, 17, 6, 17);
            sheet.mergeCells(3, 18, 4, 18);
            sheet.mergeCells(5, 18, 6, 18);
            sheet.mergeCells(3, 19, 4, 19);
            sheet.mergeCells(5, 19, 6, 19);
            sheet.mergeCells(0, 20, 6, 20);
            sheet.mergeCells(0, 21, 1, 21);
            sheet.mergeCells(2, 21, 3, 21);
            sheet.mergeCells(5, 21, 6, 21);
            //创建单元格
            sheet.addCell(new Label(0, 0, title, format));
            sheet.addCell(new Label(0, 1, "单位名称:", format1));
            sheet.addCell(new Label(1, 1, "石家庄铁道大学", format1));
            sheet.addCell(new Label(4,1,"填表日期",format1));
            sheet.addCell(new Label(5,1,yangli,format1));
            sheet.addCell(new Label(0,2,"姓名",format2));
            sheet.addCell(new Label(4,2,"学号",format2));
            sheet.addCell(new Label(0,3,"目前健康状况",format2));
            sheet.addCell(new Label(4,3,"手机号",format2));
            sheet.addCell(new Label(1,2,name,format2));
            sheet.addCell(new Label(5,2,list.get(0).num,format2));
            sheet.addCell(new Label(1,3,"良好",format2));
            sheet.addCell(new Label(5,3,phone,format2));
            sheet.addCell(new Label(0, 4, "每日体温、健康状况监测（周期14天）", format));
            sheet.addCell(new Label(0,5,"日期",format2));
            sheet.addCell(new Label(1,5,"每日体温℃",format4));
            sheet.addCell(new Label(2,5,"健康状况",format2));
            sheet.addCell(new Label(3,5,"当日所在地",format2));
            sheet.addCell(new Label(5,5,"备注",format2));
            for(j=6;j<20;j++){
                sheet.addCell(new Label(0,j,"3月"+Integer.toString(j-1)+"日",format3));
            }
            for(TemperatureBean tem:list){
                sheet.addCell(new Label(1,i,tem.temperature,format2));
                sheet.addCell(new Label(2,i,panduan(tem.temperature),format2));
                sheet.addCell(new Label(3,i,tem.where,format2));
                sheet.addCell(new Label(5,i,tem.tesu,format2));
                i++;
            }
            sheet.addCell(new Label(0,20,baozheng,format2));
            sheet.addCell(new Label(0,21,"本人签字:",format2));
            sheet.addCell(new Label(2,21,name,format2));
            sheet.addCell(new Label(4,21,"签字日期:",format3));
            sheet.addCell(new Label(5,21,yangli,format3));
            //设置行高
            sheet.setRowView(0, 800);
            sheet.setRowView(1,500);
            sheet.setRowView(2,600);
            sheet.setRowView(3,1100);
            sheet.setRowView(4,800);
            sheet.setRowView(5,800);
            sheet.setRowView(6,800);
            sheet.setRowView(7,800);
            sheet.setRowView(8,800);
            sheet.setRowView(9,800);
            sheet.setRowView(10,800);
            sheet.setRowView(11,800);
            sheet.setRowView(12,800);
            sheet.setRowView(13,800);
            sheet.setRowView(14,800);
            sheet.setRowView(15,800);
            sheet.setRowView(16,800);
            sheet.setRowView(17,800);
            sheet.setRowView(18,800);
            sheet.setRowView(19,800);
            sheet.setRowView(20,1800);
            sheet.setRowView(21,700);
            wb.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wb != null) {
                try {
                    wb.close();// 关闭文件
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String panduan(String tiwen){
        String tiwen2=tiwen.substring(0,tiwen.length()-1);
        double p=Double.parseDouble(tiwen2);
        if(p>=37){
            return "不良";
        }
        else return "良好";
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
}
