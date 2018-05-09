package com.ganen.dao;

import com.ganen.entity.Company;
import com.ganen.entity.CompanyLogin;
import com.ganen.entity.GanenUser;
import com.ganen.entity.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GanenUserDao {
    //登录
    public GanenUser ganenLogin(@Param("userName") String name, @Param("userPwd") String pwd);
    //查看全部企业
    public List<Company> getCompany(@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize, @Param("companyAllName") String companyAllName, @Param("companyAdopt") Integer companyAdopt);
    //企业总数量
    public int getCompanyCount(@Param("companyAllName") String companyAllName, @Param("companyAdopt") Integer companyAdopt);
    //根据ID得到企业
    public Company companyByID(@Param("companyID") int companyID);
    //通过审核
    public int updateCompanyAdopt(@Param("companyID") int companyID);



    //查看全部服务B
    public List<Service> getSetvice(@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize, @Param("serviceCompanyAllName") String companyAllName, @Param("serviceAdopt") Integer companyAdopt);
    //服务总数量
    public int getServiceCount(@Param("serviceCompanyAllName") String companyAllName, @Param("serviceAdopt") Integer companyAdopt);
    //根据ID得到服务
    public Service serviceByID(@Param("serviceID") int serviceID);
    //通过审核
    public int updateServiceAdopt(@Param("serviceID") int serviceID);

    int deleteCompanyAdopt(int companyID);
}
