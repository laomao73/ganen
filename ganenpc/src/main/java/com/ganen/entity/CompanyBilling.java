package com.ganen.entity;

/**
 * 开票
 */
public class CompanyBilling {

//            companyBillingID int not null primary key auto_increment,
//            companyTaxNumber varchar(20) not null,
//    companyAddress varchar(40) not null,
//    companyPhone varchar(20) not null,
//    companyOpenBank varchar(50) not null,
//    companyBankCard varchar(20) not null,
//    companyID int ,
//    serviceID int,
//    foreign key (companyID) references company(companyID),
//    foreign key (serviceID) references service(serviceID)

    private int companyBillingID;           //开票id
    private String companyTaxNumber;        //纳税号
    private String companyAddress;          //公司地址
    private String companyPhone;            //公司电话
    private String companyOpenBank;         //开户行
    private String companyBankCard;         //银行账户
    private Company company;
    private Service service;


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "CompanyBilling{" +
                "companyBillingID=" + companyBillingID +
                ", companyTaxNumber='" + companyTaxNumber + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", companyOpenBank='" + companyOpenBank + '\'' +
                ", companyBankCard='" + companyBankCard + '\'' +
                ", company=" + company +
                ", service=" + service +
                '}';
    }

    public CompanyBilling() {
    }

    public CompanyBilling(String companyTaxNumber, String companyAddress, String companyPhone, String companyOpenBank, String companyBankCard) {
        this.companyTaxNumber = companyTaxNumber;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.companyOpenBank = companyOpenBank;
        this.companyBankCard = companyBankCard;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public int getCompanyBillingID() {
        return companyBillingID;
    }

    public void setCompanyBillingID(int companyBillingID) {
        this.companyBillingID = companyBillingID;
    }

    public String getCompanyTaxNumber() {
        return companyTaxNumber;
    }

    public void setCompanyTaxNumber(String companyTaxNumber) {
        this.companyTaxNumber = companyTaxNumber;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyOpenBank() {
        return companyOpenBank;
    }

    public void setCompanyOpenBank(String companyOpenBank) {
        this.companyOpenBank = companyOpenBank;
    }

    public String getCompanyBankCard() {
        return companyBankCard;
    }

    public void setCompanyBankCard(String companyBankCard) {
        this.companyBankCard = companyBankCard;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
