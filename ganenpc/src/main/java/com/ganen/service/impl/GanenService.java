package com.ganen.service.impl;

import com.ganen.dao.GanenUserDao;
import com.ganen.entity.Company;
import com.ganen.entity.Employee;
import com.ganen.entity.EmployeeOrder;
import com.ganen.entity.GanenUser;
import com.ganen.service.IGanenSevice;
import com.ganen.util.LimitPageList;
import com.ganen.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("GanenService")
public class GanenService implements IGanenSevice {

    @Resource
    private GanenUserDao dao;


    //登录
    public GanenUser ganenLogin(String name, String pwd) {
        GanenUser ganenUser = dao.ganenLogin(name, pwd);
        return ganenUser;
    }
    //查看全部企业
    public LimitPageList getCompany(int pageSize, Integer pageNow,String companyAllName,Integer companyAdopt) {
        LimitPageList pageList = new LimitPageList();
        int totalCount = dao.getCompanyCount(companyAllName,companyAdopt);
        List<Company> companies = new ArrayList<Company>();
        Page page = null;

        if (pageNow != null) {
            page = new Page(pageSize, totalCount);
            if (page.getTotalCount() < pageNow) {
                page.setPageNow(page.getTotalPageCount());
            } else {
                page.setPageNow(pageNow);
            }
        } else {
            page = new Page(pageSize, totalCount);
            page.setPageNow(1);
        }
        companies = dao.getCompany(page.getStartPos(), page.getPageSize(),companyAllName,companyAdopt);
        pageList.setPage(page);
        pageList.setList(companies);
        return pageList;
    }
    //查看全部服务B
    public LimitPageList getService(int pageSize, Integer pageNow,String serviceCompanyAllName,Integer serviceAdopt) {
        LimitPageList pageList = new LimitPageList();
        int totalCount = dao.getServiceCount(serviceCompanyAllName,serviceAdopt);
        List<com.ganen.entity.Service> services = new ArrayList<com.ganen.entity.Service>();
        Page page = null;
        if (page != null) {
            page = new Page(pageSize, totalCount);
            if (page.getTotalCount() < pageNow) {
                page.setPageNow(page.getTotalPageCount());
            } else {
                page.setPageNow(pageNow);
            }
        } else {
            page = new Page(pageSize, totalCount);
            page.setPageNow(1);
        }
        services = dao.getSetvice(page.getStartPos(), page.getPageSize(),serviceCompanyAllName,serviceAdopt);
        pageList.setPage(page);
        pageList.setList(services);
        return pageList;
    }
    //根据ID查企业
    public Company companyByID(int companyID) {
        return dao.companyByID(companyID);
    }
    //根据ID查服务B
    public com.ganen.entity.Service serviceByID(int serviceID) {
        return dao.serviceByID(serviceID);
    }
    //通过审核企业
    public int updateCompanyAdopt(int companyID) {
        return dao.updateCompanyAdopt(companyID);
    }
    //通过审核B
    public int updateServiceAdopt(int serviceID) {
        return dao.updateServiceAdopt(serviceID);
    }
    //未通过审核
    @Override
    public int deleteCompanyAdopt(int companyID) {
        return dao.deleteCompanyAdopt(companyID);
    }
    //员工是否签了电子签
    @Override
    public List<EmployeeOrder> employeeIsContant() {
        return dao.employeeIsContant();
    }

    @Override
    public List<Employee> getEmployee(String companyOrderNumber) {
        return dao.getEmployee(companyOrderNumber);
    }

    @Override
    public List<EmployeeOrder> grantEmployee(String companyOrderNumber) {
        return dao.grantEmployee(companyOrderNumber);
    }

    @Override
    public List<EmployeeOrder> getEmployeesByID(String i) {
        return dao.getEmployeesByID(i);
    }


    @Override
    public List<EmployeeOrder> grant(String companyOrderNumber) {
        return dao.grant(companyOrderNumber);
    }

    @Override
    public int updateGrant(int employeeID) {
        return dao.updateGrant(employeeID);
    }

    @Override
    public int updateOrderState(String companyOrderNumber) {
        int i = dao.selectOrderState(companyOrderNumber);
         dao.updateOrderState(i);
         dao.updateCompanyOrder(companyOrderNumber);
        return 1;
    }

    @Override
    public List<Employee> getPDF() {
        return dao.getPDF();
    }


}
