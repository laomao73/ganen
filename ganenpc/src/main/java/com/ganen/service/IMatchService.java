package com.ganen.service;

import com.ganen.entity.CompanyOrder;
import com.ganen.entity.EmployeeOrder;
import com.ganen.entity.Service;

import java.util.List;
import java.util.Map;

public interface IMatchService {

    public Map<String,Object> matchServiceType1(CompanyOrder companyOrder, List<EmployeeOrder> employeeList);
    public Map<String,Object> matchServiceType2(CompanyOrder companyOrder, List<EmployeeOrder> employeeList);
}
