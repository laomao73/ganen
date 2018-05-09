package com.ganen.service.impl;

import com.ganen.dao.ServiceExpressDao;
import com.ganen.service.IServiceExpressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ServiceExpressService")
public class ServiceExpressService implements IServiceExpressService {

    @Resource
    private ServiceExpressDao expressDao;

    //创建快递单号
    @Override
    public int newExpress(int serviceOrder, String serviceExpress) {
        return expressDao.newExpress(serviceOrder,serviceExpress);
    }

    @Override
    public String downloadImage(int serviceOrdereID) {
        return expressDao.downloadImage(serviceOrdereID);
    }
}
