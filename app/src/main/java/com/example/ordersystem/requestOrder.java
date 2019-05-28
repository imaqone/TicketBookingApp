package com.example.ordersystem;

public class requestOrder {
    public  String DepartStation;
    public  String ArriveStation;
    public  String trainNum;
    public  String DepartDate;
    public  String DepartTime;
    public  String ArriveTime;

    public requestOrder(String DepartStation, String ArriveStation, String trainNum,String DepartDate,String DepartTime,String ArriveTime) {
        this.DepartStation=DepartStation;
        this.ArriveStation=ArriveStation;
        this.trainNum=trainNum;
        this.DepartDate=DepartDate;
        this.DepartTime=DepartTime;
        this.ArriveTime=ArriveTime;
    }
}