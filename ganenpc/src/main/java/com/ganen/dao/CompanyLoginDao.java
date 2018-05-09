package com.ganen.dao;

import com.ganen.entity.Company;
import com.ganen.entity.CompanyBilling;
import com.ganen.entity.CompanyLogin;
import com.ganen.entity.CompanyTicket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyLoginDao {

    //注册企业
    public int companyLoginRegister(CompanyLogin companyLogin);
    //选择公司
    public Company chooseCompany(Integer companyID);
    //是否通过审核
    public Integer isAdopt(Integer companyID);
    //企业登录
    public CompanyLogin companyLogin(@Param("userPhone") String userPhone, @Param("userPwd") String userPwd);
    //手机号是否存在
    public int phoneIsExist(@Param("phone") String phone);
    //查看企业是否存在
    public Company companyIsExist(@Param("companyName") String companyName);
    //注册企业
    public int companyRegister(@Param("companyName") String company, @Param("id") int id);
    //获取登录对象
    public CompanyLogin getCompany(@Param("phone") String phone);
    //获取企业id
    public int getCompanyID(@Param("companyName") String companyName);
    //认证一
    public int companyAuthOne(Company company);
    //认证二
    public int companyAuthTwo(Company company);
    //添加收票信息
    public int companyInsertTicket(@Param("ticket") CompanyTicket ticket, @Param("id") int companyID);
    //添加开票信息
    public int companyInsertBilling(@Param("billing") CompanyBilling billing, @Param("id") int companyID);
    //得到开票信息
    public CompanyBilling companyBillingByID(Integer companyID);
    public CompanyTicket companyTicketByID(Integer companyID);
    //修改开票信息
    public int updateCompanyBilling(CompanyBilling companyBilling);
    public int updateCompanyTicket(CompanyTicket companyTicket);
    //修改手机号
    public int updatePhone(@Param("companyLoginID") int companyLoginID,@Param("phoneOne") String phoneOne);
}
