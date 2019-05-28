package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.servlet.responeTicket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class fillOrder extends AppCompatActivity {
    private String Date;
    private String message;
    private int index;
    private String DepartStation;
    private String ArriveStation;
    private String DepartTime;
    private String ArriveTime;
    private String passName;
    private String passNum;
    private String passPhone;
    private String TrainNum;
    private TextView departDate;
    private TextView detail;
    private TextView trainNum;
    private EditText name;
    private EditText id;
    private EditText phone;
    private String serviceURL = "http://10.21.105.141:8080/TomcatTest/servletTest";
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8
    private OkHttpClient okhttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_order);
        Intent intent=getIntent();
        message=intent.getStringExtra("passToOrder");
        index=Integer.parseInt(intent.getStringExtra("index"));
        System.out.println(index);
        try{
            JSONArray jsonAllTicket=new JSONArray(message);
            for(int i=0;i<jsonAllTicket.length();i++){
                if(i==index) {
                    JSONObject SingleTicket = jsonAllTicket.getJSONObject(i);
                    DepartStation = SingleTicket.getString("DepartStation");
                    ArriveStation = SingleTicket.getString("ArriveStation");
                    DepartTime = SingleTicket.getString("DepartTime");
                    ArriveTime = SingleTicket.getString("ArriveTime");
                    TrainNum = SingleTicket.getString("TrainNum");
                    break;
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        departDate=findViewById(R.id.departDateOrder);
        detail=findViewById(R.id.details);
        trainNum=findViewById(R.id.tranNum);
        name=findViewById(R.id.passengerName);
        id=findViewById(R.id.passengerID);
        phone=findViewById(R.id.passengerPhone);
        Date=intent.getStringExtra("departDate");
        String DateTemp=Date.substring(4,6)+"月"+Date.substring(6)+"日";
        departDate.setText(DateTemp);
        String strDetail=DepartTime+" "+DepartStation+" - "+ArriveTime+" "+ArriveStation;
        detail.setText(strDetail);
        trainNum.setText(TrainNum);

    }
    public void summitOrder(View view){
        passName=name.getText().toString();
        passNum=id.getText().toString();
        passPhone=phone.getText().toString();
        String[]jsonArray={MainActivity.LoganUserName,passName,passNum,passPhone,DepartStation,ArriveStation,DepartTime,ArriveTime,TrainNum, Date};
        if(MainActivity.LoginOrNot==false){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(fillOrder.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(passName.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(fillOrder.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(passNum.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(fillOrder.this, "身份证不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(passPhone.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(fillOrder.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("messageType", "summitOrder");
                map.put("message", jsonArray);
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
                                Toast.makeText(fillOrder.this, "连接服务失败，请联系管理员", Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("数据库连接成功");
                        String res = response.body().string();
                        System.out.println(res);
                        if (res.equals("success")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(fillOrder.this, "购买成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(fillOrder.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
//                String res = servlet.returnResult();
//                System.out.println(res);
            }
        }).start();
//        System.out.println(MainActivity.LoganUserName);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(fillOrder.this, MainActivity.LoganUserName, Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
