package com.ganen.dao;


import com.ganen.entity.Company;
import com.ganen.entity.CompanyOrder;
import com.ganen.entity.EmployeeOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LimitDao {
    //--------------------------------------服务公司订单------------------------------------------
    //查询根据服务公司ID的所有订单数量
    Integer serviceOrderOneCount(@Param("serviceID") int serviceID, @Param("companyOrderNumber") String companyOrderNumber, @Param("companyName") String companyName, @Param("companyOrderState") String companyOrderState);

    //根据条件查询服务公司的企业订单
    List<CompanyOrder> serviceOrderOneAll(@Param("serviceID") int serviceID, @Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize, @Param("companyOrderNumber") String companyOrderNumber, @Param("companyName") String companyName, @Param("companyOrderState") String companyOrderState);


    //--------------------------------------企业公司订单------------------------------------------
    //查询根据企业公司ID的所有订单数量
    Integer companyOrderOneCount(@Param("companyID") int companyID, @Param("companyOrderNumber") String companyOrderNumber, @Param("companyName") String companyName, @Param("companyOrderState") String companyOrderState);

    //根据条件查询企业公司的企业订单
    List<CompanyOrder> companyOrderOneAll(@Param("companyID") int companyID, @Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize, @Param("companyOrderNumber") String companyOrderNumber, @Param("companyName") String companyName, @Param("companyOrderState") String companyOrderState);


    Integer ganenOrderCount(@Param("companyOrderNumber") String companyOrderNumber, @Param("companyName") String companyName, @Param("companyOrderState") String companyOrderState);

    List<CompanyOrder> ganenOrderAll(@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize, @Param("companyOrderNumber") String companyOrderNumber, @Param("companyName") String companyName, @Param("companyOrderState") String companyOrderState);

    Integer companyAllCount(@Param("companyName") String companyName);

    List<Company> companyAll(@Param("startPos") int startPos, @Param("pageSize")int pageSize,@Param("companyName") String companyName);

    Integer serviceAllCount(@Param("serviceName") String serviceName);

    List<Service> serviceAll(@Param("startPos") int startPos, @Param("pageSize") int pageSize, @Param("serviceName") String serviceName);
}
