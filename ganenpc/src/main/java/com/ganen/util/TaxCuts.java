package com.ganen.util;

/**
 * 计算扣税
 */
public class TaxCuts {

    //正常税
    public static double basic(double money) {
        if (money <= 3500) {
            return 0;
        }
            money = money - 3500;
        if (money > 0 && money < 1500) {
            money = money * 0.03;
        } else if (money >= 1500 && money < 4500) {
            money = money * 0.1;
        } else if (money >= 4500 && money < 9000) {
            money = money * 0.2;
        } else if (money >= 9000 && money < 35000) {
            money = money * 0.25;
        } else if (money >= 35000 && money < 55000) {
            money = money * 0.30;
        } else if (money >= 55000 && money < 80000) {
            money = money * 0.35;
        } else {
            money = money * 0.45;
        }
        return money;
    }







    //优化税
    public static double optimize(double money) {
        return money * 0.02;
    }
}
