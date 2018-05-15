package com.ganen.dao;

import com.ganen.entity.*;
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


    public List<EmployeeOrder> employeeIsContant();

    List<Employee> getEmployee(@Param("companyOrderNumber") String companyOrderNumber);

    List<EmployeeOrder> grantEmployee(@Param("companyOrderNumber") String companyOrderNumber);

    List<EmployeeOrder> getEmployeesByID(@Param("companyOrderNumber") String i);

    List<EmployeeOrder> grant(@Param("companyOrderNumber") String companyOrderNumber);

    int updateGrant(@Param("employeeID") int employeeOrderID);
    int updateCompanyOrder(@Param("companyOrderNumber") String companyOrderNumber);

    int updateOrderState(@Param("serviceOrderID") int serviceOrderID);
    int selectOrderState(@Param("companyOrderNumber") String companyOrderNumber);

    List<Employee> getPDF();
}
