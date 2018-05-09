package com.ganen.entity;

import java.util.List;

/**
 * 企业登录表
 */
public class CompanyLogin {
    private int companyLoginID;
    private String contactsName;        //联系人姓名
    private String contactsPhone;       //联系人电话
    private String contactsPwd;         //登录密码
    private List<Company> companyList;

    public CompanyLogin() {
    }


    public CompanyLogin(int companyLoginID) {
        this.companyLoginID = companyLoginID;
    }

    public CompanyLogin(String contactsName, String contactsPhone, String contactsPwd) {
        this.contactsName = contactsName;
        this.contactsPhone = contactsPhone;
        this.contactsPwd = contactsPwd;
    }

    @Override
    public String toString() {
        return "CompanyLogin{" +
                "companyLoginID=" + companyLoginID +
                ", contactsName='" + contactsName + '\'' +
                ", contactsPhone='" + contactsPhone + '\'' +
                ", contactsPwd='" + contactsPwd + '\'' +
                ", companyList=" + companyList +
                '}';
    }

    public int getCompanyLoginID() {
        return companyLoginID;
    }

    public void setCompanyLoginID(int companyLoginID) {
        this.companyLoginID = companyLoginID;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getContactsPwd() {
        return contactsPwd;
    }

    public void setContactsPwd(String contactsPwd) {
        this.contactsPwd = contactsPwd;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }
}
