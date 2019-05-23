package com.example.ordersystem.servlet;

public class responeTicket {
    public responeTicket(String ds,String as,String dt,String at,String tn){
        this.DepartStaion=ds;
        this.ArriveStation=as;
        this.DepartTime=dt;
        this.ArriveTime=at;
        this.TrainNum=tn;
    }
    public String DepartStaion;
    public String ArriveStation;
    public String DepartTime;
    public String ArriveTime;
    public String TrainNum;
}
