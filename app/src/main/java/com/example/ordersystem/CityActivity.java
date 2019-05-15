package com.example.ordersystem;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.ordersystem.servlet.servlet;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;
/*
Find city from service and return
 */

public class CityActivity extends AppCompatActivity {
    private SearchView searchView;
    public static final String GET_CITY="com.exmple.ordersystem.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
//        Intent intent=getIntent();
        searchView = (SearchView) findViewById(R.id.search_view);
        // 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                servlet servlet=new servlet();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        servlet.sendRequest("city",string);

                    }
                }).start();
                Intent intent=new Intent();
                intent.putExtra("result",string);
                setResult(3,intent);
                finish();
            }
        });

        // 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });



    }
}