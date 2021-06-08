package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;

import java.util.ArrayList;
import java.util.List;

public class BarActivity extends AppCompatActivity {
    WebView webView;
    DatabaseHelper mdatabaseHelper;
    String phone;
    List<String> num,xaxi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        phone= getIntent().getStringExtra("phone");
        initBar();
    }
    public void initBar(){
        num=new ArrayList<>();
        xaxi=new ArrayList<>();
        mdatabaseHelper=new DatabaseHelper(this);
        getdate();
        webView=findViewById(R.id.barEcharts);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/echarts.html");
        webView.setWebViewClient(new WebViewClient() {
            public  void onPageFinished(WebView view, String url){
                Object[] x = new Object[xaxi.size()];
                for(int i=0;i<xaxi.size();i++){
                    x[i]=xaxi.get(i);
                }
                Object[] y = new Object[num.size()];
                for(int i=0;i<num.size();i++){
                    y[i]=num.get(i);
                }
                refreshEchartsWithOption(getBarChartOptions(x,y));
            }
        });

    }
    public void refreshEchartsWithOption(GsonOption option) {
        if (option == null) {
            return;
        }
        String optionString = option.toString();
        String call = "javascript:loadEcharts('" + optionString + "')";
        webView.loadUrl(call);
    }
    public static GsonOption getBarChartOptions(Object[] xAxis,Object[] num){
        GsonOption option=new GsonOption();
        option.title("体温变化图");
        option.legend("体温");
        option.tooltip().trigger(Trigger.axis);
        ValueAxis valueAxis=new ValueAxis();
        valueAxis.min(36);
        valueAxis.max(39);
        option.yAxis(valueAxis);
        CategoryAxis categoryAxis=new CategoryAxis();
        categoryAxis.axisLine().onZero(false);
        categoryAxis.axisLabel().interval(0).rotate(-30);
        categoryAxis.boundaryGap(true);
        categoryAxis.data(xAxis);
        option.xAxis(categoryAxis);
        Bar bar=new Bar();
        bar.name("温度").data(num);
        option.series(bar);
        return  option;
    }
    public void getdate(){
        Cursor cursor=mdatabaseHelper.getAllTemperatureData();
        if(cursor!=null){
            while (cursor.moveToNext()){
                String gphone=cursor.getString(cursor.getColumnIndex("yh_phone"));
                if(gphone.equals(phone)) {
                    String time=cursor.getString(cursor.getColumnIndex("tem_time"));
                    String time2=time.substring(5,7)+time.substring(7,10);
                    xaxi.add(time2);
                    String tem=cursor.getString(cursor.getColumnIndex("tem_temperature"));
                    String tem2=tem.substring(0,tem.length()-1);
                    num.add(tem2);
                }
            }
            cursor.close();
        }
    }
}