package com.ganen.service;

import com.ganen.entity.Employee;
import com.ganen.entity.EmployeeOrder;
import com.ganen.entity.ServiceOrder;

import java.sql.Timestamp;
import java.util.List;

public interface IEmployeeService
{
    //新建员工订单
    public int newEmployeeService(ServiceOrder serviceOrder,Timestamp timestamp,String companyOrderNumber);
    //根据服务订单ID 获取相应订单
    public List<EmployeeOrder> employeeOrderByID(int serviceOrderID);
}
