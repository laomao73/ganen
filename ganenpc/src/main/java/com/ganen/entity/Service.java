package com.ganen.entity;

/**
 * 服务公司
 */
public class Service {
//    serviceID int not NULL primary key auto_increment,
//    serviceName varchar(20) not null,
//    servicePhone char(11) not NULL,
//    servicePwd varchar(14) not null,
//    serviceCompanyAllName varchar(30),
//    serviceCompanyName varchar(30) not null,
//    serviceBusinessNumber varchar(30),
//    serviceIndustry varchar(30),
//    servicePeople int,
//    serviceTicketType int,
//    serviceTicketCategory varchar(30),
//    serviceLegalName varchar(20),
//    serviceLegalPhone char(11),
//    serviceLegalCard char(18),
//    serviceLegalImage varchar(60)


    private int serviceID;                 //ID
    private String serviceName;            //联系人姓名
    private String servicePhone;           //联系人电话
    private String servicePwd;             //联系人密码
    private String serviceCompanyAllName;  //公司全称
    private String serviceCompanyName;     //公司名称
    private String serviceBusinessNumber;  //统一社会信用代码
    private String serviceBusinessImage;   //营业执照照片
    private String serviceIndustry;        //服务行业
    private int servicePeople;             //服务人员属性
    private String serviceTicketType;      //发票类型
    private String serviceTicketCategory;  //发票类目
    private String serviceLegalName;       //法人姓名
    private String serviceLegalPhone;      //法人电话
    private String serviceLegalCard;       //法人身份证号
    private String serviceLegalImage;      //身份证照片
    private String serviceOpenName;        //开户名称
    private String serviceOpen;            //开户行名称
    private String serviceOpenNumber;      //银行账户
    private String serviceContract;        //合同


    @Override
    public String toString() {
        return "Service{" +
                "serviceID=" + serviceID +
                ", serviceName='" + serviceName + '\'' +
                ", servicePhone='" + servicePhone + '\'' +
                ", servicePwd='" + servicePwd + '\'' +
                ", serviceCompanyAllName='" + serviceCompanyAllName + '\'' +
                ", serviceCompanyName='" + serviceCompanyName + '\'' +
                ", serviceBusinessNumber='" + serviceBusinessNumber + '\'' +
                ", serviceBusinessImage='" + serviceBusinessImage + '\'' +
                ", serviceIndustry='" + serviceIndustry + '\'' +
                ", servicePeople=" + servicePeople +
                ", serviceTicketType=" + serviceTicketType +
                ", serviceTicketCategory='" + serviceTicketCategory + '\'' +
                ", serviceLegalName='" + serviceLegalName + '\'' +
                ", serviceLegalPhone='" + serviceLegalPhone + '\'' +
                ", serviceLegalCard='" + serviceLegalCard + '\'' +
                ", serviceLegalImage='" + serviceLegalImage + '\'' +
                ", serviceOpenName='" + serviceOpenName + '\'' +
                ", serviceOpen='" + serviceOpen + '\'' +
                ", serviceOpenNumber='" + serviceOpenNumber + '\'' +
                ", serviceContract='" + serviceContract + '\'' +
                ", serviceAdopt=" + serviceAdopt +
                '}';
    }

    private int serviceAdopt;              //是否通过审核

    public int getServiceAdopt() {
        return serviceAdopt;
    }

    public void setServiceAdopt(int serviceAdopt) {
        this.serviceAdopt = serviceAdopt;
    }

    public Service(int serviceID, String serviceLegalName, String serviceLegalPhone, String serviceLegalCard, String serviceLegalImage) {
        this.serviceID = serviceID;
        this.serviceLegalName = serviceLegalName;
        this.serviceLegalPhone = serviceLegalPhone;
        this.serviceLegalCard = serviceLegalCard;
        this.serviceLegalImage = serviceLegalImage;
    }

    public Service(String serviceName, String servicePhone, String servicePwd, String serviceCompanyName) {
        this.serviceName = serviceName;
        this.servicePhone = servicePhone;
        this.servicePwd = servicePwd;
        this.serviceCompanyName = serviceCompanyName;
    }

    public Service(int serviceID, String serviceName, String servicePhone,String serviceCompanyName) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.servicePhone = servicePhone;
        this.serviceCompanyName=serviceCompanyName;
    }

    public Service(int serviceID,String serviceCompanyAllName,String serviceBusinessNumber,String serviceBusinessImage, String serviceIndustry, int servicePeople, String serviceTicketType, String serviceTicketCategory) {
        this.serviceID=serviceID;
        this.serviceCompanyAllName=serviceCompanyAllName;
        this.serviceBusinessImage=serviceBusinessImage;
        this.serviceBusinessNumber = serviceBusinessNumber;
        this.serviceIndustry = serviceIndustry;
        this.servicePeople = servicePeople;
        this.serviceTicketType = serviceTicketType;
        this.serviceTicketCategory = serviceTicketCategory;
    }

    public Service() {
    }


    public String getServiceBusinessImage() {
        return serviceBusinessImage;
    }

    public void setServiceBusinessImage(String serviceBusinessImage) {
        this.serviceBusinessImage = serviceBusinessImage;
    }

    public String getServiceIndustry() {
        return serviceIndustry;
    }

    public void setServiceIndustry(String serviceIndustry) {
        this.serviceIndustry = serviceIndustry;
    }

    public int getServicePeople() {
        return servicePeople;
    }

    public void setServicePeople(int servicePeople) {
        this.servicePeople = servicePeople;
    }

    public String getServiceTicketType() {
        return serviceTicketType;
    }

    public void setServiceTicketType(String serviceTicketType) {
        this.serviceTicketType = serviceTicketType;
    }

    public String getServiceTicketCategory() {
        return serviceTicketCategory;
    }

    public void setServiceTicketCategory(String serviceTicketCategory) {
        this.serviceTicketCategory = serviceTicketCategory;
    }

    public String getServiceCompanyAllName() {
        return serviceCompanyAllName;
    }

    public void setServiceCompanyAllName(String serviceCompanyAllName) {
        this.serviceCompanyAllName = serviceCompanyAllName;
    }

    public String getServicePwd() {
        return servicePwd;
    }

    public void setServicePwd(String servicePwd) {
        this.servicePwd = servicePwd;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getServiceCompanyName() {
        return serviceCompanyName;
    }

    public void setServiceCompanyName(String serviceCompanyName) {
        this.serviceCompanyName = serviceCompanyName;
    }

    public String getServiceBusinessNumber() {
        return serviceBusinessNumber;
    }

    public void setServiceBusinessNumber(String serviceBusinessNumber) {
        this.serviceBusinessNumber = serviceBusinessNumber;
    }

    public String getServiceLegalName() {
        return serviceLegalName;
    }

    public void setServiceLegalName(String serviceLegalName) {
        this.serviceLegalName = serviceLegalName;
    }

    public String getServiceLegalPhone() {
        return serviceLegalPhone;
    }

    public void setServiceLegalPhone(String serviceLegalPhone) {
        this.serviceLegalPhone = serviceLegalPhone;
    }

    public String getServiceLegalCard() {
        return serviceLegalCard;
    }

    public void setServiceLegalCard(String serviceLegalCard) {
        this.serviceLegalCard = serviceLegalCard;
    }

    public String getServiceLegalImage() {
        return serviceLegalImage;
    }

    public void setServiceLegalImage(String serviceLegalImage) {
        this.serviceLegalImage = serviceLegalImage;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceOpenName() {
        return serviceOpenName;
    }

    public void setServiceOpenName(String serviceOpenName) {
        this.serviceOpenName = serviceOpenName;
    }

    public String getServiceOpen() {
        return serviceOpen;
    }

    public void setServiceOpen(String serviceOpen) {
        this.serviceOpen = serviceOpen;
    }

    public String getServiceOpenNumber() {
        return serviceOpenNumber;
    }

    public void setServiceOpenNumber(String serviceOpenNumber) {
        this.serviceOpenNumber = serviceOpenNumber;
    }

    public String getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(String serviceContract) {
        this.serviceContract = serviceContract;
    }
}
