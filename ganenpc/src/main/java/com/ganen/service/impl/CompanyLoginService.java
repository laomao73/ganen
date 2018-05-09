package com.ganen.service.impl;

import com.ganen.dao.CompanyDao;
import com.ganen.dao.CompanyLoginDao;
import com.ganen.entity.Company;
import com.ganen.entity.CompanyBilling;
import com.ganen.entity.CompanyLogin;
import com.ganen.entity.CompanyTicket;
import com.ganen.service.ICompanyLoginService;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("CompanyLoginService")
public class CompanyLoginService implements ICompanyLoginService {

    @Resource
    private CompanyLoginDao companyLoginDao;

    @Resource
    private CompanyDao companyDao;

    //注册企业
    public Map<String, Object> companyRegister(CompanyLogin companyLogin, String companyName) {
        Map<String, Object> map = new HashMap<String, Object>();
        int phoneIsExist = companyLoginDao.phoneIsExist(companyLogin.getContactsPhone());
        if (phoneIsExist > 0) {
            map.put("result",0);
            map.put("content", "手机号已存在");
            return map;
        }
        Company company1 = companyLoginDao.companyIsExist(companyName);
        if (company1 != null) {
            map.put("result",0);
            map.put("content", "企业已注册");
            return map;
        }
        int result = companyLoginDao.companyLoginRegister(companyLogin);
        if (result > 0) {
            CompanyLogin company = companyLoginDao.getCompany(companyLogin.getContactsPhone());
            map.put("result",1);
            map.put("content", "注册成功");
            map.put("object", company);
            return map;
        }
        map.put("result",0);
        map.put("content", "注册失败");
        return map;
    }
    //登录
    public CompanyLogin companyLogin(String userPhone, String userPwd) {
        CompanyLogin login = companyLoginDao.companyLogin(userPhone, userPwd);
        return login;
    }
    public int companyAuth(int id, String companyName) {
        int i = companyLoginDao.companyRegister(companyName, id);
        int companyID = companyLoginDao.getCompanyID(companyName);
        System.out.println(i);
        System.out.println(companyID);
        return companyID;
    }
    //认证1
    public String companyAuthOne(Company company) {

        int result = companyLoginDao.companyAuthOne(company);
        if (result > 0) {
            return "认证一成功";
        }
        return "认证一失败";
    }
    //认证二
    public String companyAuthTwo(Company company) {
        int result = companyLoginDao.companyAuthTwo(company);
        if (result > 0) {
            return "认证二成功";
        }
        return "认证二失败";
    }
    //补全信息
    public String companyAuthFinish(CompanyBilling billing, CompanyTicket ticket,int companyID) {
        int insertBilling = companyLoginDao.companyInsertBilling(billing,companyID);
        int insertTicket = companyLoginDao.companyInsertTicket(ticket,companyID);
        if(insertBilling>0&&insertTicket>0){
            return "保存成功";
        }
        return "保存失败";
    }
    //修改企业信息
    public String companyAuthUpdate(CompanyBilling billing, CompanyTicket tickets) {
        String result="";
        int i1 = companyLoginDao.updateCompanyBilling(billing);
        int i = companyLoginDao.updateCompanyTicket(tickets);
        if(i1!=0&&i!=0){
             result="修改成功";
        }else{
             result="修改失败";
        }
        return result;
    }
    //是否认证1
    public String companyIsAuthOne(int id) {
        return companyDao.companyIsAuthOne(id);
    }
    //是否认证2
    public String companyIsAuthTwo(int id) {
        return companyDao.companyIsAuthTwo(id);
    }
    //选择公司
    public Company chooseCompany(Integer companyID) {
        return companyLoginDao.chooseCompany(companyID);
    }
    //是否通过审核
    public Integer isAdopt(Integer companyID) {
        return companyLoginDao.isAdopt(companyID);
    }
    //是否有开票信息
    public CompanyTicket companyTicketByID(Integer companyID) {
        return companyLoginDao.companyTicketByID(companyID);
    }
    //修改手机号
    @Override
    public int updatePhone(int companyLoginID, String phoneOne) {
        return companyLoginDao.updatePhone(companyLoginID,phoneOne);
    }

    //是否有收票信息
    public CompanyBilling companyBillingByID(Integer companyID) {
        return companyLoginDao.companyBillingByID(companyID);
    }
}
