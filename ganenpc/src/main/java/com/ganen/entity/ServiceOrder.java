package com.ganen.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 服务公司订单
 * 	serviceOrderID int not null primary key auto_increment,
 serviceOrderCount int not null,
 serviceOrderPrice DECIMAL(20,2) not null,
 serviceOrderServicePrice DECIMAL(20,2) not null,
 serviceOrderTime datetime not null,
 serviceOrderState int not null,
 serviceOrderImage varchar(50) not null,
 serviceID int not null,
 foreign key (serviceID) references service(serviceID)
 serviceOrderTicketType varchar(100),
 serviceOrderTicketCategory varchar(100),
 */
public class ServiceOrder {
    private int        serviceOrderID;              //ID
    private int        serviceOrderCount;           //总人数
    private BigDecimal serviceOrderPrice;           //总金额
    private BigDecimal serviceOrderServicePrice;    //服务费
    private Timestamp  serviceOrderTime;            //时间
    private String     serviceOrderState;           //状态
    private String     serviceOrderImage;           //打款截图
    private String     serviceOrderTicketType;      //类型
    private String     serviceOrderTicketCategory;  //类目
    private Service    service;
    private CompanyOrder companyOrder;
    private ServiceExpress serviceExpress;

    private List<EmployeeOrder> employeeOrderList;

    public ServiceExpress getServiceExpress() {
        return serviceExpress;
    }

    public void setServiceExpress(ServiceExpress serviceExpress) {
        this.serviceExpress = serviceExpress;
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "serviceOrderID=" + serviceOrderID +
                ", serviceOrderCount=" + serviceOrderCount +
                ", serviceOrderPrice=" + serviceOrderPrice +
                ", serviceOrderServicePrice=" + serviceOrderServicePrice +
                ", serviceOrderTime=" + serviceOrderTime +
                ", serviceOrderState=" + serviceOrderState +
                ", serviceOrderImage='" + serviceOrderImage + '\'' +
                ", service=" + service +
                ", companyOrder=" + companyOrder +
                ", employeeOrderList=" + employeeOrderList +
                '}';
    }

    public List<EmployeeOrder> getEmployeeOrderList() {
        return employeeOrderList;
    }

    public void setEmployeeOrderList(List<EmployeeOrder> employeeOrderList) {
        this.employeeOrderList = employeeOrderList;
    }


    public String getServiceOrderTicketType() {
        return serviceOrderTicketType;
    }

    public void setServiceOrderTicketType(String serviceOrderTicketType) {
        this.serviceOrderTicketType = serviceOrderTicketType;
    }

    public String getServiceOrderTicketCategory() {
        return serviceOrderTicketCategory;
    }

    public void setServiceOrderTicketCategory(String serviceOrderTicketCategory) {
        this.serviceOrderTicketCategory = serviceOrderTicketCategory;
    }

    public int getServiceOrderID() {
        return serviceOrderID;
    }

    public void setServiceOrderID(int serviceOrderID) {
        this.serviceOrderID = serviceOrderID;
    }

    public int getServiceOrderCount() {
        return serviceOrderCount;
    }

    public void setServiceOrderCount(int serviceOrderCount) {
        this.serviceOrderCount = serviceOrderCount;
    }

    public BigDecimal getServiceOrderPrice() {
        return serviceOrderPrice;
    }

    public void setServiceOrderPrice(BigDecimal serviceOrderPrice) {
        this.serviceOrderPrice = serviceOrderPrice;
    }

    public BigDecimal getServiceOrderServicePrice() {
        return serviceOrderServicePrice;
    }

    public void setServiceOrderServicePrice(BigDecimal serviceOrderServicePrice) {
        this.serviceOrderServicePrice = serviceOrderServicePrice;
    }

    public Timestamp getServiceOrderTime() {
        return serviceOrderTime;
    }

    public void setServiceOrderTime(Timestamp serviceOrderTime) {
        this.serviceOrderTime = serviceOrderTime;
    }

    public String getServiceOrderState() {
        return serviceOrderState;
    }

    public void setServiceOrderState(String serviceOrderState) {
        this.serviceOrderState = serviceOrderState;
    }

    public String getServiceOrderImage() {
        return serviceOrderImage;
    }

    public void setServiceOrderImage(String serviceOrderImage) {
        this.serviceOrderImage = serviceOrderImage;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public CompanyOrder getCompanyOrder() {
        return companyOrder;
    }

    public void setCompanyOrder(CompanyOrder companyOrder) {
        this.companyOrder = companyOrder;
    }
}
