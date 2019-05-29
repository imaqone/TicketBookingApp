package com.example.ordersystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.ListView;
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
    public static boolean LoginOrNot = false;
    public static String LoganUserName = null;
    private String DepartDate = null;
    private Handler handler = new Handler();

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        ArrayList<requestOrder> orderslist = new ArrayList<>();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            orderslist=setOrder(MainActivity.LoganUserName);
//                        }
//                    });
//                    setOrder(LoganUserName);
//                    Handler handler=new Handler(Looper.getMainLooper());
//                    handler.post(new Runnable() {
//                       @Override
//                       public void run() {
////                           setOrder(LoganUserName);
//                       }
//                   });

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
        Intent intent2 = new Intent(this, CityActivity.class);
        startActivityForResult(intent2, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {

            String result = data.getStringExtra("result");
            TextView textView = findViewById(R.id.textView_setCity1);
//            DepartCity=result;
            textView.setText(result);
        } else if (requestCode == 2 && resultCode == 3) {
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
                        if (month < 9) {
                            if (dayOfMonth < 10) {
                                DepartDate = year + "0" + (month + 1) + "0" + dayOfMonth;
                            } else {
                                DepartDate = year + "0" + (month + 1) + dayOfMonth;
                            }

                        } else {
                            if (dayOfMonth < 10) {
                                DepartDate = year + "" + (month + 1) + "0" + dayOfMonth;
                            } else {
                                DepartDate = year + "" + (month + 1) + dayOfMonth;
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
    private String serviceURL = "http://10.21.105.141:8080/TomcatTest/servletTest";
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8

    public void sendRequest(View view) {
//        System.out.println("onclick");
        Intent intent = new Intent(this, searchTicket.class);
        TextView departCity = findViewById(R.id.textView_setCity1);
        TextView arriveCity = findViewById(R.id.textView_setCity2);
        String dp = (String) departCity.getText();
        String ac = (String) arriveCity.getText();
        String[] message = {dp, ac, DepartDate};
        servlet servlet = new servlet();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("messageType", "ticket");
                map.put("message", message);
                JSONObject jsonObject = new JSONObject(map);
                String jsonString = jsonObject.toString();
                RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, jsonString);
                okhttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(serviceURL)
                        .post(body)
                        .build();
                Call call = okhttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("连接失败");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "连接服务失败，请联系管理员", Toast.LENGTH_SHORT).show();
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
                        String res = response.body().string();
                        intent.putExtra("ticket", res);
                        intent.putExtra("departDate", DepartDate);
                        startActivity(intent);
                    }
                });
                String res = servlet.returnResult();
                System.out.println(res);
            }
        }).start();
    }

    //    public Context getContext(){
//        return this.getContext();
//    }
    public void setOrder(View view) {
//        ArrayList<requestOrder> requestOrders = new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Map map = new HashMap<>();
//                map.put("messageType", "requestOrder");
//                String[] temp = {MainActivity.LoganUserName};
//                map.put("message", temp);
//                JSONObject jsonObject = new JSONObject(map);
//                String jsonString = jsonObject.toString();
//                RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, jsonString);
//                okhttpClient = new OkHttpClient();
//                final Request request = new Request.Builder()
//                        .url(serviceURL)
//                        .post(body)
//                        .build();
//                Call call = okhttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println("连接失败");
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "连接服务失败，请联系管理员", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//
//                        System.out.println("登录数据库连接成功");
//                        String res = response.body().string();
//                        System.out.println(res);
//                        ListView mListView = findViewById(R.id.orderlist);
//                        try {
//                            JSONArray jsonAllTicket = new JSONArray(res);
//                            for (int i = 0; i < jsonAllTicket.length(); i++) {
//                                JSONObject SingleTicket = jsonAllTicket.getJSONObject(i);
//                                String DepartStation = SingleTicket.getString("DepartStation");
//                                String ArriveStation = SingleTicket.getString("ArriveStation");
//                                String DepartTime = SingleTicket.getString("DepartTime");
//                                String ArriveTime = SingleTicket.getString("ArriveTime");
//                                String TrainNum = SingleTicket.getString("TrainNum");
//                                String DepartDate = SingleTicket.getString("DepartDate");
//                                requestOrder responeOrder = new requestOrder(DepartStation, ArriveStation, TrainNum, DepartDate, DepartTime, ArriveTime);
//                                requestOrders.add(responeOrder);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        orderAdater orderAdater = new orderAdater(requestOrders, MainActivity.this);
//                        mListView.setAdapter(orderAdater);
//                    }
//                });
//            }
//        }).start();
//
//                String res = servlet.returnResult();
//                System.out.println(res);
    }
//}
}
