package com.ganen.dao;

import com.ganen.entity.Employee;
import com.ganen.entity.EmployeeOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeDao {

    //电子签合同编号
    public int updateContants(@Param("employeeID") int employeeID,@Param("employeeContract") String employeeContract);
    //创建个人订单
    public int newEmployeeOrder(EmployeeOrder employeeOrder);
    //创建个人
    public int newEmployee(Employee employee);
    //通过身份查看此员工是否创建
    public Integer getEmployeeID(String employeeCard);
    //获取最大的ID 生产电子签合同号
    public Integer maxEmployeeID();
    //根据员工订单ID获取员工订单
    public List<EmployeeOrder> employeeOrderByID();
    //根据服务订单
    public List<EmployeeOrder> employeeOrderByServiceOrderID(int serviceOrderID);
    //获取员工工资金额一个季度
    public BigDecimal employeeSpring(String employeeCard);
    public BigDecimal employeeSummer(String employeeCard);
    public BigDecimal employeeFall(String employeeCard);
    public BigDecimal employeeWinter(String employeeCard);

    int updateContantsState(@Param("employeeID") int employeeID);
}
