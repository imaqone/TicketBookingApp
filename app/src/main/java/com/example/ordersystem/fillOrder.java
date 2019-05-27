package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ordersystem.servlet.responeTicket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class fillOrder extends AppCompatActivity {
    private String message;
    private String DepartStation;
    private String ArriveStation;
    private String DepartTime;
    private String ArriveTime;
    private String TrainNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_order);
        Intent intent=getIntent();
        message=intent.getStringExtra("passToOrder");
        try{
            JSONArray jsonAllTicket=new JSONArray(message);
            for(int i=0;i<jsonAllTicket.length();i++){
                JSONObject SingleTicket=jsonAllTicket.getJSONObject(i);
                String DepartStation=SingleTicket.getString("DepartStation");
                String ArriveStation=SingleTicket.getString("ArriveStation");
                String DepartTime=SingleTicket.getString("DepartTime");
                String ArriveTime=SingleTicket.getString("ArriveTime");
                String TrainNum=SingleTicket.getString("TrainNum");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
