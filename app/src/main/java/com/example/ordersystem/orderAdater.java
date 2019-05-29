//package com.example.ordersystem;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.ordersystem.servlet.responeTicket;
//
//import java.util.ArrayList;
//
//public class orderAdater extends BaseAdapter {
//    private ArrayList<requestOrder>data;
//    private Context context;
//    public orderAdater(ArrayList mData,Context mContext){
//        this.context=mContext;
//        this.data=mData;
//    }
//    @Override
//    public int getCount(){
//        return data.size();
//    }
//    @Override
//    public Object getItem(int position){
//        return data.get(position);
//    }
//    @Override
//    public long getItemId(int position){
//        return position;
//    }
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent){
//        if(convertView==null){
//            convertView= LayoutInflater.from(context).inflate(R.layout.fragment_item,parent,false);
//
//        }
//        final TextView orderItem1=(TextView)convertView.findViewById(R.id.order_item1);
//        final TextView orderItem2=(TextView)convertView.findViewById(R.id.order_item2);
//
//        requestOrder requestOrder;
//        requestOrder=data.get(position);
//        orderItem1.setText("hello1");
//        orderItem2.setText("hello2");
//        return convertView;
//    }
//}
