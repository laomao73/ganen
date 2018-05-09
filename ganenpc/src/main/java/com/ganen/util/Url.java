package com.ganen.util;

public class Url {


    public  static final String ONE_TEST_SIGN_API="http://open.enjoysign.com/api/exportwidgets.shtml";

    public static final String APPID = "1S15240432932338ae482006c";
    public static final String APPSECRET = "04fb2104c2072796a3fc727a200c1b3e6abf45b2";
    public static final String TEMPLATEID = "38815241021761615";
    public static final String ORDERID = "update-version04-doc-88";
    public static final String VERSION = "04";
    public static String TIMESTAMP = String.valueOf(System.currentTimeMillis());
    public static String signature = "appId=" + Url.APPID +
            "&orderId=" + Url.ORDERID +
            "&templateId=" + Url.TEMPLATEID +
            "&timestamp=" + Url.TIMESTAMP +
            "&version=" + Url.VERSION +
            "&appsecret=" + Url.APPSECRET;



}
