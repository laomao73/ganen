package com.ganen.entity;

/**
 * 证件类型
 */
public class CardType {
//    cardTypeID int not null primary key auto_increment,
//    cardTypeName varchar(15) not null,
//    cardType varchar(8) not null

    private int cardTypeID;             //ID
    private String cardTypeName;        //证件类型
    private String cardType;            //对应字段


    @Override
    public String toString() {
        return "CardType{" +
                "cardTypeID=" + cardTypeID +
                ", cardTypeName='" + cardTypeName + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }

    public CardType(int cardTypeID, String cardTypeName, String cardType) {
        this.cardTypeID = cardTypeID;
        this.cardTypeName = cardTypeName;
        this.cardType = cardType;
    }

    public CardType() {
    }

    public int getCardTypeID() {
        return cardTypeID;
    }

    public void setCardTypeID(int cardTypeID) {
        this.cardTypeID = cardTypeID;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
