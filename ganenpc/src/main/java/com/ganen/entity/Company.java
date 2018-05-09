package com.ganen.entity;

/**
 * 公司
 */
public class Company {
//    companyID int not null primary key auto_increment,
//    companyName varchar(30) not null,
//    companyAllName varchar(30) not null,
//    companyLegalName varchar(10) not null,
//    companyLegalPhone char(11) not null,
//    companyLegalCard char(18) not null,
//    companyBusinessNumber varchar(30) not null,
//    companyBusinessImage varchar(30) not null,
//    companyEmail varchar(20),
//    companyAdopt int,

    private int companyID;
    private String companyName;             //公司名称
    private String companyAllName;          //公司全称
    private String companyLegalName;        //法人姓名
    private String companyLegalPhone;       //法人电话
    private String companyLegalCard;        //法人身份证
    private String companyLegalImage;       //法人身份证照片
    private String companyBusinessNumber;   //营业执照号码
    private String companyBusinessImage;    //营业执照照片
    private String companyEmail;            //公司邮箱
    private int companyAdopt;            //公司是否通过审批
    private CompanyLogin companyLogin;
    private CompanyBilling companyBilling;
    private CompanyTicket companyTicket;

    public CompanyBilling getCompanyBilling() {
        return companyBilling;
    }

    public void setCompanyBilling(CompanyBilling companyBilling) {
        this.companyBilling = companyBilling;
    }

    public CompanyTicket getCompanyTicket() {
        return companyTicket;
    }

    public void setCompanyTicket(CompanyTicket companyTicket) {
        this.companyTicket = companyTicket;
    }

    public Company() {
    }


    @Override
    public String toString() {
        return "Company{" +
                "companyID=" + companyID +
                ", companyName='" + companyName + '\'' +
                ", companyAllName='" + companyAllName + '\'' +
                ", companyLegalName='" + companyLegalName + '\'' +
                ", companyLegalPhone='" + companyLegalPhone + '\'' +
                ", companyLegalCard='" + companyLegalCard + '\'' +
                ", companyLegalImage='" + companyLegalImage + '\'' +
                ", companyBusinessNumber='" + companyBusinessNumber + '\'' +
                ", companyBusinessImage='" + companyBusinessImage + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyAdopt=" + companyAdopt +
                ", companyLogin=" + companyLogin +
                '}';
    }

    public Company(int companyID) {
        this.companyID = companyID;
    }

    public Company(int companyID, String companyLegalName, String companyLegalPhone, String companyLegalCard, String companyLegalImage) {
        this.companyID=companyID;
        this.companyLegalName = companyLegalName;
        this.companyLegalPhone = companyLegalPhone;
        this.companyLegalCard = companyLegalCard;
        this.companyLegalImage = companyLegalImage;
    }

    public Company(int companyID,String companyAllName, String companyBusinessNumber, String companyBusinessImage) {
        this.companyID=companyID;
        this.companyAllName=companyAllName;
        this.companyBusinessNumber = companyBusinessNumber;
        this.companyBusinessImage = companyBusinessImage;
    }


    public Company(String companyAllName, String companyBusinessNumber, String companyBusinessImage,CompanyLogin companyLogin) {
        this.companyLogin=companyLogin;
        this.companyAllName=companyAllName;
        this.companyBusinessNumber = companyBusinessNumber;
        this.companyBusinessImage = companyBusinessImage;
    }


    public int getCompanyAdopt() {
        return companyAdopt;
    }

    public void setCompanyAdopt(int companyAdopt) {
        this.companyAdopt = companyAdopt;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAllName() {
        return companyAllName;
    }

    public void setCompanyAllName(String companyAllName) {
        this.companyAllName = companyAllName;
    }

    public String getCompanyLegalName() {
        return companyLegalName;
    }

    public void setCompanyLegalName(String companyLegalName) {
        this.companyLegalName = companyLegalName;
    }

    public String getCompanyLegalPhone() {
        return companyLegalPhone;
    }

    public void setCompanyLegalPhone(String companyLegalPhone) {
        this.companyLegalPhone = companyLegalPhone;
    }

    public String getCompanyLegalCard() {
        return companyLegalCard;
    }

    public void setCompanyLegalCard(String companyLegalCard) {
        this.companyLegalCard = companyLegalCard;
    }

    public String getCompanyLegalImage() {
        return companyLegalImage;
    }

    public void setCompanyLegalImage(String companyLegalImage) {
        this.companyLegalImage = companyLegalImage;
    }

    public String getCompanyBusinessNumber() {
        return companyBusinessNumber;
    }

    public void setCompanyBusinessNumber(String companyBusinessNumber) {
        this.companyBusinessNumber = companyBusinessNumber;
    }

    public String getCompanyBusinessImage() {
        return companyBusinessImage;
    }

    public void setCompanyBusinessImage(String companyBusinessImage) {
        this.companyBusinessImage = companyBusinessImage;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }


    public CompanyLogin getCompanyLogin() {
        return companyLogin;
    }

    public void setCompanyLogin(CompanyLogin companyLogin) {
        this.companyLogin = companyLogin;
    }
}
