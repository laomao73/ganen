package com.ganen.service;

public interface IServiceExpressService {

    //创建快递单号
    public int newExpress(int serviceOrder,String serviceExpress);

    String downloadImage(int serviceOrdereID);
}
