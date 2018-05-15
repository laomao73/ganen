package com.ganen.service;

import com.ganen.entity.*;

import java.util.List;
import java.util.Map;

public interface ICompanyService {
    //根据手机号得到ID
    public Map<String,Object> getCompanyID(String userphone);
    //修改密码
    public Map<String,Object> companyUpdatePwd(int id, String pwd);
    //分公司认证一
    public Map<String,Object> companyAuthOne(Company company);
    //分公司认证二
    public String companyAuthTwo(Company company);
    //获取开票信息
    public CompanyBilling companyBillingByID(int companyID);
    //创建企业订单
    public int newCompanyOrder(CompanyOrder companyOrder);
    //获取企业订单ID
    public Integer CompanyID();
    //获取订单
    public List<ServiceOrder> getCompanyOrder(int companyOrderID);
    //导出excel发放明细
    List<EmployeeOrder> expressGant(String companyOrderNumber);
}
