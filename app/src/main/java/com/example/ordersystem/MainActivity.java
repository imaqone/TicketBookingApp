package com.example.ordersystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ordersystem.*;
import com.example.ordersystem.dummy.DummyContent;
import com.example.ordersystem.servlet.requestTicket;
import com.example.ordersystem.servlet.servlet;
//import com.example.ordersystem.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnFragmentInteractionListener, OrderFragment.OnListFragmentInteractionListener, personFragment.OnFragmentInteractionListener {
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
//    private requestTicket requestTicket;
//    private String DepartCity=null;
//    private String ArriveCity=null;
    private String DepartDate=null;
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        Fragment f1 = new MainFragment();
        Fragment f2 = new OrderFragment();
        Fragment f3 = new personFragment();
        fragmentList.add(f1);
        fragmentList.add(f2);
        fragmentList.add(f3);
        FragmentManager fragmentManager = getSupportFragmentManager();
        rFragmentPaperAdapter rfpa = new rFragmentPaperAdapter(fragmentManager, fragmentList);
        viewPager.setAdapter(rfpa);
        viewPager.setCurrentItem(0);
        f1.onAttach(this);
    }

    public void clickCity(View view) {
        Intent intent1 = new Intent(this, CityActivity.class);
//        startActivity(intent1);
//        TextView textView=findViewById(R.id.textView_setCity1);
//        textView.setText(intent1.getStringExtra(CityActivity.GET_CITY));
//        textView.setText("南宁");
        startActivityForResult(intent1, 1);
    }

    public void clickCity2(View view) {
        Intent intent2 = new Intent(this,CityActivity.class);
        startActivityForResult(intent2,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {

            String result = data.getStringExtra("result");
            TextView textView = findViewById(R.id.textView_setCity1);
//            DepartCity=result;
            textView.setText(result);
        }else if(requestCode == 2 && resultCode == 3){
            String result = data.getStringExtra("result");
            TextView textView = findViewById(R.id.textView_setCity2);
//            ArriveCity=result;
            textView.setText(result);
        }
    }

    Calendar ca = Calendar.getInstance();
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);

    public void clickTime(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        final String data = (month + 1) + "月" + dayOfMonth + "日 ";
                        TextView textView = findViewById(R.id.timeSet);
                        textView.setText(data);
                        if(month<9){
                            if(dayOfMonth<10){
                                DepartDate=year+"0"+(month+1)+"0"+dayOfMonth;
                            }else{
                                DepartDate=year+"0"+(month+1)+dayOfMonth;
                            }

                        }else{
                            if(dayOfMonth<10){
                                DepartDate=year+""+(month+1)+"0"+dayOfMonth;
                            }else{
                                DepartDate=year+""+(month+1)+dayOfMonth;
                            }

                        }

                    }
                },
                mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public void loginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private OkHttpClient okhttpClient;
    private String serviceURL="http://10.21.105.141:8080/TomcatTest/servletTest";
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8
    public void sendRequest(View view){
//        System.out.println("onclick");
        Intent intent=new Intent(this,searchTicket.class);
        TextView departCity=findViewById(R.id.textView_setCity1);
        TextView arriveCity=findViewById(R.id.textView_setCity2);
        String dp=(String)departCity.getText();
        String ac=(String)arriveCity.getText();
        String []message={dp,ac,DepartDate};
        servlet servlet=new servlet();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map=new HashMap<>();
                map.put("messageType","ticket");
                map.put("message",message);
                JSONObject jsonObject=new JSONObject(map);
                String jsonString=jsonObject.toString();
                RequestBody body=RequestBody.create(FORM_CONTENT_TYPE,jsonString);
                okhttpClient=new OkHttpClient();
                final Request request=new Request.Builder()
                        .url(serviceURL)
                        .post(body)
                        .build();
                Call call=okhttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("连接失败");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"连接服务失败，请联系管理员",Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    /*
                    解析数据
                     */
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("连接成功");
                        String res=response.body().string();
                        intent.putExtra("ticket",res);
                        startActivity(intent);
                    }
                });
                String res=servlet.returnResult();
                System.out.println(res);
            }
        }).start();
    }
}
