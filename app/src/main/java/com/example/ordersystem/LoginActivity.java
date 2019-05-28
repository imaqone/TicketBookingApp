package com.example.ordersystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.SQLite.SQLiteHelper;
import com.example.ordersystem.servlet.servlet;

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

public class LoginActivity extends AppCompatActivity {
    private OkHttpClient okhttpClient;
    private String serviceURL = "http://10.21.105.141:8080/TomcatTest/servletTest";
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8
    private EditText usernameTV;
    private EditText passwordTV;
    private static String db_name="UserLogin.db";
    private static int db_version=1;
    private Cursor cursor;
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTV = (EditText) findViewById(R.id.username);
        passwordTV = (EditText) findViewById(R.id.password);
//        final EditText usernameEditText = findViewById(R.id.username);
//        final EditText passwordEditText = findViewById(R.id.password);
//        final Button loginButton = findViewById(R.id.login);
//        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
    }

    public void clickLogin(View view) {
//        System.out.println("click");
        String username = usernameTV.getText().toString();
        String password = passwordTV.getText().toString();
        String[] message = {username, password};
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap<>();
                map.put("messageType", "login");
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
                                Toast.makeText(LoginActivity.this, "连接服务失败，请联系管理员", Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("登录数据库连接成功");
                        String res = response.body().string();
                        System.out.println(res);
                        if (res.equals("success")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            MainActivity.LoganUserName=username;
                            MainActivity.LoginOrNot=true;
                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
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

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, registerActivity.class);
        startActivity(intent);
    }
//    public void setLoginStatus(){
//        try{
//
//        }
//    }


}
