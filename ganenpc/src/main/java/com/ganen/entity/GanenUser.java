package com.ganen.entity;

/**
 * 感恩有你表
 */
public class GanenUser {
    private int ganenID;
    private String ganenName;
    private String ganenPwd;


    @Override
    public String toString() {
        return "GanenUser{" +
                "ganenID=" + ganenID +
                ", ganenName='" + ganenName + '\'' +
                ", ganenPwd='" + ganenPwd + '\'' +
                '}';
    }

    public GanenUser() {
    }

    public GanenUser(String ganenName, String ganenPwd) {
        this.ganenName = ganenName;
        this.ganenPwd = ganenPwd;
    }

    public int getGanenID() {
        return ganenID;
    }

    public void setGanenID(int ganenID) {
        this.ganenID = ganenID;
    }

    public String getGanenName() {
        return ganenName;
    }

    public void setGanenName(String ganenName) {
        this.ganenName = ganenName;
    }

    public String getGanenPwd() {
        return ganenPwd;
    }

    public void setGanenPwd(String ganenPwd) {
        this.ganenPwd = ganenPwd;
    }
}
