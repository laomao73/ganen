package com.ganen.entity;
//	serviceExpressID int not null primary key auto_increment,
//            serviceExpress varchar(40)not null,
//            serviceOrderID int not null,

public class ServiceExpress {
    private int serviceEcpressID;
    private String serviceExpress;
    private ServiceOrder serviceOrder;

    @Override
    public String toString() {
        return "ServiceExpress{" +
                "serviceEcpressID=" + serviceEcpressID +
                ", serviceExpress='" + serviceExpress + '\'' +
                ", serviceOrder=" + serviceOrder +
                '}';
    }

    public int getServiceEcpressID() {
        return serviceEcpressID;
    }

    public void setServiceEcpressID(int serviceEcpressID) {
        this.serviceEcpressID = serviceEcpressID;
    }

    public String getServiceExpress() {
        return serviceExpress;
    }

    public void setServiceExpress(String serviceExpress) {
        this.serviceExpress = serviceExpress;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }
}
