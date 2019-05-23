package com.example.ordersystem.servlet;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.ordersystem.MainActivity;
import com.example.ordersystem.searchTicket;

import org.json.JSONArray;
import org.json.JSONException;
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

import static android.support.v4.content.ContextCompat.startActivity;

public class servlet {

    private OkHttpClient okhttpClient;
    private String serviceURL="http://10.21.105.141:8080/TomcatTest/servletTest";
    private String result;
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8
    public void sendRequest(final String messageType, String []message){
//        startActivity(intent);
        Map<String,String[]>map=new HashMap<>();
        map.put(messageType,message);
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
                e.printStackTrace();
            }

            /*
            解析数据
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("连接成功");
                String res=response.body().string();
//                System.out.println(res);
                result=res;
            }
        });
    }
    public String returnResult(){
        return this.result;
    }
}
