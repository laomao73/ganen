package com.ganen.service;

import com.ganen.entity.CompanyOrder;
import com.ganen.entity.Service;
import com.ganen.entity.ServiceOrder;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

public interface IServerService {
    //注册
    public Map<String,Object> serviceRegister(Service service);
    //登录
    public Service serviceLogin(String userPhone, String userPwd);
    //认证二
    public Map<String,Object> serviceAuthTwo(Service service);
    //认证一
    public Map<String,Object> serviceAuthOne(Service service);
    //得到ID
    public Map<String,Object> getServiceID(String phone);
    //修改密码
    public Map<String,Object> serviceUpdatePwd(int id, String pwd);
    //是否认证一
    public String serviceIsAuthOne(String userPhone);
    //是否认证二
    public String serviceIsAuthTwo(String userPhone);
    //补全信息
    public Map<String,Object> updateInfo(Service service);
    //同过手机号得到ID
    public int getServiceIDByPhone(String userPhone);
    //通过名字获取服务公司
    public Service serviceByPeople(int serviePeople);
    //创建服务公司订单
    public int newOrder(ServiceOrder serviceOrder);
    //根据ID获取服务订单
    public CompanyOrder orderByID(int companyOrderID);
    //上传打款截图
    public int uploadImage(int serviceOrderID,String file);
    //修改企业信息
    public int updateInfo2(Service service);
    //查看截图
    public String selectImage(int serviceOrderID);
    //下载截图
    public String downloadImage(int serviceOrderID);
    //修改手机号
    public int updatePhone(int serviceID,String phone);
    //获取服务订单
    public ServiceOrder serviceOrderByID(int serviceOrderID);
    //删除截图
    public int deleteImage(int serviceOrderID);
    //导出excel服务明细
    List<ServiceOrder> expressExcelService(String companyOrderNumber);
}
