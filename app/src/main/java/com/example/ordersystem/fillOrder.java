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

public class fillOrder extends AppCompatActivity {
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
        String Date=intent.getStringExtra("departDate");
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
//        System.out.println(MainActivity.LoganUserName);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(fillOrder.this, MainActivity.LoganUserName, Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
