package com.ganen.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 企业订单
 * companyOrderID int not null primary key auto_increment,
 companyOrderNumber int not null,
 companyOrderCount	 int not null,
 companyOrderPrice 			 DECIMAL(20,2) not null,
 companyOrderTaxCount		 DECIMAL(20,2) not null,
 companyOrderPriceCount   DECIMAL(20,2) not null,
 companyOrderTime datetime not null,
 companyOrderState int not null,
 companyOrderType int not null,
 companyOrderTax	 int not null,
 companyID int not null,
 */
public class CompanyOrder {
    private int         companyOrderID;                     //ID
    private String      companyOrderNumber;                  //订单号
    private int         companyOrderCount;                  //总人数
    private BigDecimal  companyOrderPrice;                  //实发金额
    private BigDecimal  companyOrderTaxCount;               //税
    private BigDecimal  companyOrderPriceCount;             //付款金额
    private Timestamp   companyOrderTime;                   //时间
    private int         companyOrderType;                   //订单类型  1.灵活用工 2.企业用工
    private String      companyOrderState;                  //订单状态
    private int         companyOrderTax;                    //税承担   1. 企业 2.个人
    private Company     company;

    private List<ServiceOrder> serviceOrderList;


    @Override
    public String toString() {
        return "CompanyOrder{" +
                "companyOrderID=" + companyOrderID +
                ", companyOrderCount=" + companyOrderCount +
                ", companyOrderPrice=" + companyOrderPrice +
                ", companyOrderTaxCount=" + companyOrderTaxCount +
                ", companyOrderPriceCount=" + companyOrderPriceCount +
                ", companyOrderTime=" + companyOrderTime +
                ", companyOrderType=" + companyOrderType +
                ", companyOrderState=" + companyOrderState +
                ", companyOrderTax=" + companyOrderTax +
                ", company=" + company +
                ", serviceOrderList=" + serviceOrderList +
                '}';
    }

    public List<ServiceOrder> getServiceOrderList() {
        return serviceOrderList;
    }

    public void setServiceOrderList(List<ServiceOrder> serviceOrderList) {
        this.serviceOrderList = serviceOrderList;
    }

    public int getCompanyOrderID() {
        return companyOrderID;
    }

    public void setCompanyOrderID(int companyOrderID) {
        this.companyOrderID = companyOrderID;
    }

    public int getCompanyOrderCount() {
        return companyOrderCount;
    }

    public void setCompanyOrderCount(int companyOrderCount) {
        this.companyOrderCount = companyOrderCount;
    }

    public BigDecimal getCompanyOrderPrice() {
        return companyOrderPrice;
    }

    public void setCompanyOrderPrice(BigDecimal companyOrderPrice) {
        this.companyOrderPrice = companyOrderPrice;
    }

    public BigDecimal getCompanyOrderTaxCount() {
        return companyOrderTaxCount;
    }

    public void setCompanyOrderTaxCount(BigDecimal companyOrderTaxCount) {
        this.companyOrderTaxCount = companyOrderTaxCount;
    }

    public BigDecimal getCompanyOrderPriceCount() {
        return companyOrderPriceCount;
    }

    public void setCompanyOrderPriceCount(BigDecimal companyOrderPriceCount) {
        this.companyOrderPriceCount = companyOrderPriceCount;
    }

    public Timestamp getCompanyOrderTime() {
        return companyOrderTime;
    }

    public void setCompanyOrderTime(Timestamp companyOrderTime) {
        this.companyOrderTime = companyOrderTime;
    }

    public int getCompanyOrderType() {
        return companyOrderType;
    }

    public void setCompanyOrderType(int companyOrderType) {
        this.companyOrderType = companyOrderType;
    }

    public String getCompanyOrderNumber() {
        return companyOrderNumber;
    }

    public void setCompanyOrderNumber(String companyOrderNumber) {
        this.companyOrderNumber = companyOrderNumber;
    }

    public String getCompanyOrderState() {
        return companyOrderState;
    }

    public void setCompanyOrderState(String companyOrderState) {
        this.companyOrderState = companyOrderState;
    }

    public int getCompanyOrderTax() {
        return companyOrderTax;
    }

    public void setCompanyOrderTax(int companyOrderTax) {
        this.companyOrderTax = companyOrderTax;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
