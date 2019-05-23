package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ordersystem.servlet.responeTicket;

import java.util.ArrayList;

public class itemTicketAdater extends BaseAdapter {
    private ArrayList<responeTicket>data;
    private Context context;
    public itemTicketAdater(ArrayList mData,Context mContext){
        this.context=mContext;
        this.data=mData;
    }
    @Override
    public int getCount(){
        return data.size();
    }
    @Override
    public Object getItem(int position){
        return data.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_search_ticket,parent,false);

        }
        final TextView itemTime1=(TextView)convertView.findViewById(R.id.itemtime1);
        final TextView itemCity1=(TextView)convertView.findViewById(R.id.itemCity1);
        final TextView itemTime2=(TextView)convertView.findViewById(R.id.itemtime2);
        final TextView itemCity2=(TextView)convertView.findViewById(R.id.itemCity2);
        final TextView itemTrainNum=convertView.findViewById(R.id.itemtrainnum);
        responeTicket responeTicket;
        responeTicket=data.get(position);
        itemTime1.setText(responeTicket.DepartTime);
        itemCity1.setText(responeTicket.DepartStaion);
        itemTime2.setText(responeTicket.ArriveTime);
        itemCity2.setText(responeTicket.ArriveStation);
        itemTrainNum.setText(responeTicket.TrainNum);
        return convertView;
    }
}
