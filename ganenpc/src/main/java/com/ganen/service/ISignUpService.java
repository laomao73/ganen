package com.ganen.service;

import com.ganen.entity.Employee;
import com.ganen.entity.EmployeeOrder;

import java.util.List;
import java.util.Map;

public interface ISignUpService {
    //第一次发放电子签
    public String firstSignUp(String companyOrderNumber);

    void employeeIsContant(EmployeeOrder employee) throws Exception;

    String againEmployee(EmployeeOrder employee) throws Exception;
}
