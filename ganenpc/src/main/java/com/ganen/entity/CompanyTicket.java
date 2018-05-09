package com.ganen.entity;

/**
 * 收票
 */
public class CompanyTicket {

//
//            ticketID int not null primary key auto_increment,
//            ticketName varchar(10) not null,
//    ticketPhone char(11) not null,
//    ticketAddress varchar(30) not null,
//    companyID int ,
//    foreign key (companyID) references  company(companyID)

    private int ticketID;               //收票ID
    private String ticketName;          //收件人姓名
    private String ticketPhone;         //收件人电话
    private String ticketAddress;       //收件人地址
    private Company company;


    public CompanyTicket() {
    }

    @Override
    public String toString() {
        return "CompanyTicket{" +
                "ticketID=" + ticketID +
                ", ticketName='" + ticketName + '\'' +
                ", ticketPhone='" + ticketPhone + '\'' +
                ", ticketAddress='" + ticketAddress + '\'' +
                ", company=" + company +
                '}';
    }

    public CompanyTicket(String ticketName, String ticketPhone, String ticketAddress) {
        this.ticketName = ticketName;
        this.ticketPhone = ticketPhone;
        this.ticketAddress = ticketAddress;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketPhone() {
        return ticketPhone;
    }

    public void setTicketPhone(String ticketPhone) {
        this.ticketPhone = ticketPhone;
    }

    public String getTicketAddress() {
        return ticketAddress;
    }

    public void setTicketAddress(String ticketAddress) {
        this.ticketAddress = ticketAddress;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
