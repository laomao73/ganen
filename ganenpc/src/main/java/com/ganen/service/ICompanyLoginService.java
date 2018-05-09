package com.ganen.service;

import com.ganen.entity.Company;
import com.ganen.entity.CompanyBilling;
import com.ganen.entity.CompanyLogin;
import com.ganen.entity.CompanyTicket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


public interface ICompanyLoginService {

    //注册
    public Map<String, Object> companyRegister(CompanyLogin ICompanyLogin, String companyName);
    //登录
    public CompanyLogin companyLogin(String userPhone, String userPwd);

    public int companyAuth(int id, String companyName);

    public String companyAuthOne(Company company);

    public String companyAuthTwo(Company company);

    public String companyAuthFinish(CompanyBilling billing, CompanyTicket ticke, int companyID);

    public String companyAuthUpdate(CompanyBilling billing,CompanyTicket tickets);


    public String companyIsAuthOne(int userPhone);

    public String companyIsAuthTwo(int userPhone);
    //选择公司
    public Company chooseCompany(Integer companyID);
    //是否通过了审核
    public Integer isAdopt(Integer companyID);
    //是否有开票信息
    public CompanyBilling companyBillingByID(Integer companyID);
    //是否有收票信息
    public CompanyTicket companyTicketByID(Integer companyID);
    //修改手机号
    public int updatePhone(int companyLoginID, String phoneOne);
}
