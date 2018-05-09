package com.ganen.entity;

import java.math.BigDecimal;

/**
 * 员工
 */
public class Employee {
    //    employeeID int not null primary key auto_increment,
//    employeeName varchar(10),
//    employeePhone char(11),
//    employeeCard char(18),
//    employeeCardImage varchar(60),
//    employeeCardType int,
//    employeePwd varchar(12),
//    employeeOpen varchar(30),
//    employeeOpenNumber	varchar(30),
//    employeeBankNumber	varchar(30),
//    employeeSalary  DECIMAL(20,2),
//    employeePrice		DECIMAL(20,2),
//    employeeTax			DECIMAL(20,2),
//    employeeContract 		varchar(20),
//    employeeContractState int,
//    companyID int,}
    private int employeeID;                     //ID
    private String employeeName;           //姓名
    private String employeePhone;          //电话
    private String employeeCard;           //身份证
    private int employeeCardType;       //证件类型
    private String employeeOpen;           //开户行
    private String employeeOpenNumber;     //开户行行号
    private String employeeBankNumber;     //账号
    private String employeeContract;       //合同号
    private int employeeContractState;     //签署状态
    private String employeeCardCN;
    private Company company;
    public String getEmployeeCardCN() {
        return employeeCardCN;
    }

    public void setEmployeeCardCN(String employeeCardCN) {
        this.employeeCardCN = employeeCardCN;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", employeePhone='" + employeePhone + '\'' +
                ", employeeCard='" + employeeCard + '\'' +
                ", employeeCardType=" + employeeCardType +
                ", employeeOpen='" + employeeOpen + '\'' +
                ", employeeOpenNumber='" + employeeOpenNumber + '\'' +
                ", employeeBankNumber='" + employeeBankNumber + '\'' +
                ", employeeContract='" + employeeContract + '\'' +
                ", employeeContractState=" + employeeContractState +
                ", company=" + company +
                '}';
    }

    /**
     * excel读取
     *
     * @param employeeName       姓名
     * @param employeeCard       身份证
     * @param employeeCardType   证件类型
     * @param employeeOpen       开户行
     * @param employeeOpenNumber 开户行行号
     * @param employeeBankNumber 账号
     * @param employeeSalary     应开工资
     */
    public Employee(String employeeName, String employeeCard, int employeeCardType, String employeeOpen, String employeeOpenNumber, String employeeBankNumber, BigDecimal employeeSalary) {
        this.employeeName = employeeName;
        this.employeeCard = employeeCard;
        this.employeeCardType = employeeCardType;
        this.employeeOpen = employeeOpen;
        this.employeeOpenNumber = employeeOpenNumber;
        this.employeeBankNumber = employeeBankNumber;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeCard() {
        return employeeCard;
    }

    public void setEmployeeCard(String employeeCard) {
        this.employeeCard = employeeCard;
    }


    public int getEmployeeCardType() {
        return employeeCardType;
    }

    public void setEmployeeCardType(int type) {
        this.employeeCardType = type;
    }



    public String getEmployeeOpen() {
        return employeeOpen;
    }

    public void setEmployeeOpen(String employeeOpen) {
        this.employeeOpen = employeeOpen;
    }

    public String getEmployeeOpenNumber() {
        return employeeOpenNumber;
    }

    public void setEmployeeOpenNumber(String employeeOpenNumber) {
        this.employeeOpenNumber = employeeOpenNumber;
    }

    public String getEmployeeBankNumber() {
        return employeeBankNumber;
    }

    public void setEmployeeBankNumber(String employeeBankNumber) {
        this.employeeBankNumber = employeeBankNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public String getEmployeeContract() {
        return employeeContract;
    }

    public void setEmployeeContract(String employeeContract) {
        this.employeeContract = employeeContract;
    }

    public int getEmployeeContractState() {
        return employeeContractState;
    }

    public void setEmployeeContractState(int employeeContractState) {
        this.employeeContractState = employeeContractState;
    }
}