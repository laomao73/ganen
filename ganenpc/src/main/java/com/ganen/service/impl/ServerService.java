package com.ganen.service.impl;

import com.ganen.dao.CompanyDao;
import com.ganen.dao.ServiceDao;
import com.ganen.entity.CompanyOrder;
import com.ganen.entity.Service;
import com.ganen.entity.ServiceOrder;
import com.ganen.service.IServerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service("ServiceCompany")
public class ServerService implements IServerService {

    @Resource
    private ServiceDao dao;
    @Resource
    private CompanyDao companyDao;

    //注册
    public Map<String, Object> serviceRegister(Service service) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (dao.phoneIsExist(service.getServicePhone()) > 0) {
            map.put("result", 0);
            map.put("content", "手机已存在");
            return map;
        }
        System.out.println(service.getServiceCompanyName());
        System.out.println(dao.serviceIsExist(service.getServiceCompanyName()));
        if (dao.serviceIsExist(service.getServiceCompanyName()) > 0) {
            map.put("result", 0);
            map.put("content", "企业已注册");
            return map;
        }

        int i = dao.serviceRegister(service);
        if (i > 0) {
            Service service1 = dao.getService(service.getServicePhone());
            map.put("result", 1);
            map.put("content", "注册成功");
            map.put("object", service1);
            return map;
        }
        map.put("result", 0);
        map.put("content", "注册失败");
        return map;
    }
    //登录
    public Service serviceLogin(String userPhone, String userPwd) {
        Service service = dao.serviceLogin(userPhone, userPwd);
        return service;
    }
    //认证二
    public Map<String, Object> serviceAuthTwo(Service service) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = dao.serviceAuthTwo(service);
        if (i > 0) {
            map.put("result", "认证成功");
        } else {
            map.put("result", "认证失败");
        }
        return map;
    }
    //认证一
    public Map<String, Object> serviceAuthOne(Service service) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = dao.serviceAuthOne(service);
        if (i > 0) {
            map.put("result", "认证成功");
        } else {
            map.put("result", "认证失败");
        }
        return map;
    }
    //得到ID
    public Map<String, Object> getServiceID(String phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer serviceID = dao.getServiceID(phone);
        if (serviceID != null) {
            map.put("result", "手机号已存在");
            map.put("companyID", serviceID);
        } else {
            map.put("result", "手机号不存在");
        }
        return map;
    }
    //修改密码
    public Map<String, Object> serviceUpdatePwd(int id, String pwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer integer = dao.serviceUpdatePwd(id, pwd);
        if (integer > 0) {
            map.put("result",1);
            map.put("result", "修改成功");
        } else {
            map.put("result",0);
            map.put("result", "修改失败");
        }
        return map;
    }
    //是否认证一
    public String serviceIsAuthOne(String userPhone) {
        return dao.serviceIsAuthOne(userPhone);
    }
    //是否认证二
    public String serviceIsAuthTwo(String userPhone) {
        return dao.serviceIsAuthTwo(userPhone);
    }
    //补全信息
    public Map<String, Object> updateInfo(Service service) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = dao.updateInfo(service);
        if (i > 0) {
            map.put("result", "保存成功");
        } else {
            map.put("result", "保存失败");
        }
        return map;
    }
    //通过手机号得到ID
    public int getServiceIDByPhone(String userPhone) {
        return dao.getServiceID(userPhone);
    }
    //通过服务类型获取服务公司
    public Service serviceByPeople(int serviePeople) {
        if(serviePeople==2){
            serviePeople=3;
        }
        return dao.serviceByPeople(serviePeople);
    }
    //创建服务公司订单
    public int newOrder(ServiceOrder serviceOrder) {
        //企业订单id
        int id = companyDao.companyOrderByID(serviceOrder.getCompanyOrder().getCompanyOrderNumber());
        serviceOrder.getCompanyOrder().setCompanyOrderID(id);
        return dao.newServiceOrder(serviceOrder);
    }
    //根据ID获取服务订单
    @Override
    public CompanyOrder orderByID(int companyOrderID) {
        //企业订单 服务订单 服务东西信息
        CompanyOrder companyOrder = companyDao.orderByID(companyOrderID);
        return companyOrder;
    }
    //上传打款截图
    @Override
    public int uploadImage(int serviceOrderID, String file) {
        return dao.uploadImage(serviceOrderID,file);
    }
    //修改
    @Override
    public int updateInfo2(Service service) {
        return dao.updateInfo2(service);
    }
    //查看截图
    @Override
    public String selectImage(int serviceOrderID) {
        return dao.selectImage(serviceOrderID);
    }
    //下载截图
    @Override
    public String downloadImage(int serviceOrderID) {
        return dao.downloadImage(serviceOrderID);
    }

    //修改手机号
    @Override
    public int updatePhone(int serviceID, String phone) {
        return dao.updatePhone(serviceID,phone);
    }
    //获取服务订单
    @Override
    public ServiceOrder serviceOrderByID(int serviceOrderID) {
        return dao.serviceOrderByID(serviceOrderID);
    }

    //删除截图
    @Override
    public int deleteImage(int serviceOrderID) {
        return dao.deleteImage(serviceOrderID);
    }
    //导出excel服务明细
    @Override
    public List<ServiceOrder> expressExcelService(String companyOrderNumber) {
        return dao.expressExcelService(companyOrderNumber);
    }


}
