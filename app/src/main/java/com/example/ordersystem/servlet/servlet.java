package com.example.ordersystem.servlet;

import android.app.Activity;
import android.util.Log;

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

public class servlet {
    private OkHttpClient okhttpClient;
    private String serviceURL="http://10.21.105.141:8080/TomcatTest/servletTest";
    private String result;
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"); //设置编码格式为UTF-8
    public void sendRequest(final String messageType, String message){
        Map map=new HashMap();
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


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("连接成功");
                String res=response.body().string();
                try{
                    JSONObject jsonObj=new JSONObject(res);
                    String data=jsonObj.getString(messageType);
                    System.out.println(data);
                    Log.d("message: ",data);
                    result=data;
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    public String returnResult(){
        return this.result;
    }
}
