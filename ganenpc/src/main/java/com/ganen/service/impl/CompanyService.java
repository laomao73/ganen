package com.ganen.service.impl;

import com.ganen.dao.CompanyDao;
import com.ganen.entity.*;
import com.ganen.service.ICompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CompanyService")
public class CompanyService implements ICompanyService {

    @Resource
    private CompanyDao companyDao;

    //查看手机号是否存在
    public Map<String, Object> getCompanyID(String userphone) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer companyID = companyDao.getCompanyID(userphone);
        if (null != companyID) {
            map.put("result", "手机号存在");
            map.put("companyID", companyID);
            return map;
        }
        map.put("result", "手机号不存在");
        return map;
    }

    //修改密码
    public Map<String, Object> companyUpdatePwd(int id, String pwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer integer = companyDao.companyUpdatePwd(id, pwd);
        if (integer > 0) {
            map.put("result", 1);
            map.put("result", "修改成功");
            return map;
        }
        map.put("result", 0);
        map.put("result", "修改失败");
        return map;
    }

    //分公司认证一
    public Map<String, Object> companyAuthOne(Company company) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = companyDao.companyAuthOne(company);
        if (i != 0) {
            map.put("result", "分公司认证一成功");
            int i1 = companyDao.companyByName(company.getCompanyAllName());
            map.put("companyID", i1);
        } else {
            map.put("result", "分公司认证一成功失败");
        }
        return map;
    }

    //分公司认证二
    public String companyAuthTwo(Company company) {
        int i = companyDao.companyAuthTwo(company);
        if (i != 0) {
            return "分公司认证二成功";
        }
        return "分公司认证二失败";
    }

    //获取开票信息
    public CompanyBilling companyBillingByID(int companyID) {
        return companyDao.companyBillingByID(companyID);
    }

    //创建企业订单
    public int newCompanyOrder(CompanyOrder companyOrder) {
        return companyDao.newCompanyOrder(companyOrder);
    }

    public Integer CompanyID() {
        Integer companyID = companyDao.CompanyID();
        if (companyID == null) {
            companyID = 1;
        } else {
            companyID += 1;
        }
        return companyID;
    }
    //获取订单
    @Override
    public List<ServiceOrder> getCompanyOrder(int companyOrderID) {
        return companyDao.getCompanyOrder(companyOrderID);
    }
    //导出excel发放明细
    @Override
    public List<EmployeeOrder> expressGant(String companyOrderNumber) {
        return companyDao.expressGant(companyOrderNumber);
    }
}
