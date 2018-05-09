package com.ganen.service;

import com.ganen.entity.Company;
import com.ganen.entity.GanenUser;
import com.ganen.entity.Service;
import com.ganen.util.LimitPageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IGanenSevice {
    //登录
    public GanenUser ganenLogin(String name, String pwd);
    //查看全部企业
    public LimitPageList getCompany(int pageSize, Integer pageNow, String companyAllName, Integer companyAdopt);
    //查看全部服务B
    public LimitPageList getService(int pageSize, Integer pageNow, String serviceCompanyAllName, Integer serviceAdopt);
    //根据ID查企业
    public Company companyByID(int companyID);
    //根据ID查服务B
    public Service serviceByID(int serviceID);
    //通过审核企业
    public int updateCompanyAdopt(int companyID);
    //通过审核服务B
    public int updateServiceAdopt(int serviceID);

    int deleteCompanyAdopt(int companyID);
}
