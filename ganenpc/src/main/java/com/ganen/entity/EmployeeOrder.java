package com.ganen.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 员工订单表
 employeeOrderID int not null primary key auto_increment,
 employeeOrderTime datetime not null,
 employeeOrderState int not null,
 employeeSalary  DECIMAL(20,2) not null,
 employeePrice		DECIMAL(20,2)	not null,
 employeeTax			DECIMAL(20,2) not null,
 serviceOrderID int not null,
 employeeID int not null,
 */
public class EmployeeOrder {

    private int             employeeOrderID;        //ID
    private Timestamp       employeeOrderTime;      //时间
    private String          employeeOrderState;     //状态
    private BigDecimal      employeeSalary;         //应发发放
    private BigDecimal      employeePrice;          //实际发放
    private BigDecimal      employeeTax;            //税
    private ServiceOrder    serviceOrder;
    private Employee        employee;


    @Override
    public String toString() {
        return "EmployeeOrder{" +
                "employeeOrderID=" + employeeOrderID +
                ", employeeOrderTime=" + employeeOrderTime +
                ", employeeOrderState=" + employeeOrderState +
                ", employeeSalary=" + employeeSalary +
                ", employeePrice=" + employeePrice +
                ", employeeTax=" + employeeTax +
                ", serviceOrder=" + serviceOrder +
                ", employee=" + employee +
                '}';
    }

    public int getEmployeeOrderID() {
        return employeeOrderID;
    }

    public void setEmployeeOrderID(int employeeOrderID) {
        this.employeeOrderID = employeeOrderID;
    }

    public Timestamp getEmployeeOrderTime() {
        return employeeOrderTime;
    }

    public void setEmployeeOrderTime(Timestamp employeeOrderTime) {
        this.employeeOrderTime = employeeOrderTime;
    }


    public String getEmployeeOrderState() {
        return employeeOrderState;
    }

    public void setEmployeeOrderState(String employeeOrderState) {
        this.employeeOrderState = employeeOrderState;
    }

    public BigDecimal getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(BigDecimal employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public BigDecimal getEmployeePrice() {
        return employeePrice;
    }

    public void setEmployeePrice(BigDecimal employeePrice) {
        this.employeePrice = employeePrice;
    }

    public BigDecimal getEmployeeTax() {
        return employeeTax;
    }

    public void setEmployeeTax(BigDecimal employeeTax) {
        this.employeeTax = employeeTax;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
