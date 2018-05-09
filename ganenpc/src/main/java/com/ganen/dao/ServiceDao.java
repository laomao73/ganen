package com.ganen.dao;

import com.ganen.entity.CompanyOrder;
import com.ganen.entity.Service;
import com.ganen.entity.ServiceOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceDao {

    //登录
    public Service serviceLogin(@Param("userPhone") String userPhone, @Param("userPwd") String userPwd);
    //注册
    public int serviceRegister(Service service);
    //手机号是否存在
    public int phoneIsExist(@Param("phone") String phone);
    //企业名是否重复
    public int serviceIsExist(@Param("company") String name);
    //得到服务对象
    public Service getService(@Param("phone") String phone);
    //认证一
    public int serviceAuthOne(Service service);
    //认证二
    public int serviceAuthTwo(Service service);
    //根据手机号得的ID
    public Integer getServiceID(@Param("phone") String phone);
    //修改密码
    public Integer serviceUpdatePwd(@Param("id") int id, @Param("pwd") String pwd);
    //是否认证一
    public String serviceIsAuthOne(@Param("userPhone") String userPhone);
    //是否认证二
    public String serviceIsAuthTwo(@Param("userPhone") String userPhone);
    //补全信息
    public int updateInfo(Service service);
    //根据名称获得服务公司
    public Service serviceByPeople(int serviePeople);
    //创建服务公司订单
    public int newServiceOrder(ServiceOrder serviceOrder);
    //获取订单号
    public Integer servieceID(@Param("serviceOrderNumber") String serviceOrderNumber,@Param("serviceID") int serviceID);
    //根据ID获取服务订单
    public ServiceOrder serviceOrderByID(int serviceOrderID);
    //上传打款截图
    public int uploadImage(@Param("serviceOrderID") int serviceOrderID, @Param("file") String file);
    //修改企业信息
    public int updateInfo2(Service service);
    //查看截图
    public String selectImage(@Param("serviceOrderID") int serviceOrderID);
    //获取服务B公司总金额
    public BigDecimal servicePriceCount(int serviceID);
    //修改手机号
    public int updatePhone(@Param("serviceID") int serviceID,@Param("phone") String phone);
    //下载截图
    public String downloadImage(@Param("serviceOrderID") int serviceOrderID);
    //删除截图
    public int deleteImage(@Param("serviceOrderID")int serviceOrderID);
    //导出excel服务明细
    List<ServiceOrder> expressExcelService(String companyOrderNumber);
}
