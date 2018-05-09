package com.ganen.dao;

import org.apache.ibatis.annotations.Param;

public interface ServiceExpressDao {

    //创建订单
    public int newExpress(@Param("serviceOrderID") int serviceOrderID, @Param("serviceExpress") String serviceExpress);

    String downloadImage(@Param("serviceOrderID") int serviceOrdereID);
}
