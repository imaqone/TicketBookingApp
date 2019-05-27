package com.example.ordersystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class registerActivity extends AppCompatActivity {
    private EditText Account;
    private EditText Password;
    private EditText Name;
    private EditText Phone;
    private EditText IDCard;
    private OkHttpClient okhttpClient;
    private EditText Password2;
    private String serviceURL = "http://10.21.105.141:8080/TomcatTest/servletTest";
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Account=findViewById(R.id.accountR);
        Password=findViewById(R.id.passwordR);
        Password2=findViewById(R.id.passwordRA);
        Name=findViewById(R.id.nameR);
        Phone=findViewById(R.id.phoneR);
        IDCard=findViewById(R.id.IDCardR);
    }
    public void register(View view){
        String ac=Account.getText().toString();
        String pw=Password.getText().toString();
        String pw2=Password2.getText().toString();
        String nm=Name.getText().toString();
        String ph=Phone.getText().toString();
        String ic=IDCard.getText().toString();
        String[] message = {ph,nm,ac,pw,ph,ic};
//        System.out.println("hello");
//        System.out.println(ac);
        if(ac.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(registerActivity.this, "账户名不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(pw.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(registerActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(nm.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(registerActivity.this, "名字不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(ph.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(registerActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(ic.isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(registerActivity.this, "卡号不能为空", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        if(pw.equals(pw2)==false){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(registerActivity.this, "两次输入密码不同", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("messageType", "register");
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
                                Toast.makeText(registerActivity.this, "连接服务失败，请联系管理员", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(registerActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(registerActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
//                String res = servlet.returnResult();
//                System.out.println(res);
            }
        }).start();
    }
}
