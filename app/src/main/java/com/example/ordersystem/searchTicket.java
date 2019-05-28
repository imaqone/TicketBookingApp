package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ordersystem.servlet.responeTicket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/*
查询合适的车票，并列出来
 */
public class searchTicket extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<responeTicket>mdata=new ArrayList<>();
    private itemTicketAdater itemTicketAdater;
    private String DepartDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        System.out.println("!!!!!");
        setContentView(R.layout.activity_search_ticket);
        Intent intent=getIntent();
        String res=intent.getStringExtra("ticket");
        DepartDate=intent.getStringExtra("departDate");
//        System.out.println(res);
        getTicket(res);
        mListView=findViewById(R.id.ticketlist);
//        mListView.layout();
        itemTicketAdater=new itemTicketAdater(mdata,this);
        mListView.setAdapter(itemTicketAdater);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent2=new Intent(searchTicket.this,fillOrder.class);
                intent2.putExtra("passToOrder",res);
                intent2.putExtra("departDate",DepartDate);
                intent2.putExtra("index",String.valueOf(position));
//                System.out.println(position);
                startActivity(intent2);
            }
        });
    }
    /*
    用jsonObject解析收到的信息
     */
    public void getTicket(String res){

        /*
        解析jsonobject，后续根据数据库进行修改
         */
        try{
            JSONArray jsonAllTicket=new JSONArray(res);
            for(int i=0;i<jsonAllTicket.length();i++){
                JSONObject SingleTicket=jsonAllTicket.getJSONObject(i);
                String DepartStation=SingleTicket.getString("DepartStation");
                String ArriveStation=SingleTicket.getString("ArriveStation");
                String DepartTime=SingleTicket.getString("DepartTime");
                String ArriveTime=SingleTicket.getString("ArriveTime");
                String TrainNum=SingleTicket.getString("TrainNum");
                responeTicket responeTicket=new responeTicket(DepartStation,ArriveStation,DepartTime,ArriveTime,TrainNum);
                mdata.add(responeTicket);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
