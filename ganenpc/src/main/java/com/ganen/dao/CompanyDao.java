package com.ganen.dao;

import com.ganen.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {

    //通过电话号查找id
    public Integer getCompanyID(@Param("phone") String phone);
    //修改密码
    public Integer companyUpdatePwd(@Param("id") int id, @Param("pwd") String pwd);
    //是否认证一
    public String companyIsAuthOne(@Param("id") int id);
    //是否认证二
    public String companyIsAuthTwo(@Param("id") int id);
    //分公司认证一
    public int companyAuthOne(Company company);
    //获取分公司ID
    public int companyByName(@Param("name") String name);
    //分公司认证二
    public int companyAuthTwo(Company company);
    //获取开票信息
    public CompanyBilling companyBillingByID(@Param("companyID") int companyID);
    //创建企业订单
    public int newCompanyOrder(CompanyOrder companyOrder);
    //订单编号生产
    public Integer CompanyID();
    //根据订单号获取ID
    public int companyOrderByID(String companyOrderNumber);
    //根据订单号获取当前订单
    public List<ServiceOrder> getCompanyOrder(int companyOrderID);
    //根据公司订单ID得到全部订单
    public CompanyOrder orderByID(int companyOrderID);
    //导出excel发放明细
    List<EmployeeOrder> expressGant(String companyOrderNumber);

    Company getCompanyBillingByNumber(@Param("companyOrderID") int companyOrderID);

    ServiceExpress getServiceExpress(@Param("companyOrderID") int companyOrderID);
}
