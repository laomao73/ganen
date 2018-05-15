package com.ganen.constant;

public class PaymentConstant {

	/** 连连银通公钥（不需替换，这是连连公钥，用于报文加密和连连返回响应报文时验签，不是商户生成的公钥. */
	public static final String PUBLIC_KEY_ONLINE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";

	/** 商户私钥 商户通过openssl工具生成公私钥，公钥通过商户站上传，私钥用于加签，替换下面的值 . */
	public static final String BUSINESS_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKS6v5My2b0MwvHaqf/MclHOYgfZN2pCK5+96ErQ1lZPXSstMIzKNg7/5WmqPXzFlY5A7tX8/1b3nok0FcJcpc17XC3Fbtf5s7VrY4ZSEcDvytNkMkmBGWsooRnTzaVLtxD5S3t/vLNaGUgzRhZ13JORtWqa8U9PB51wpClfwCrjAgMBAAECgYBuzySRaxJnBk+Yi2Pxq2B3QSJWbzrEorBfq0q4ERUtSAO/SI2xF1EX8/EpjBgCd5Lr9yfyEFbe9IDTgJLmhJYOt8rc/RueCUq2S1g0QtYvIpjXMTib6Xn00rao+yFrqgJjslxrm71DvK1ZECdot6cCgU0RaBE1wwWr0AqzUET4aQJBANcTzYSuZnqHwVWseZzSW317Sn7NQzNol8ac2wOhoYKMe9Wxvwts9SsGs3ERHkPFKQjm85B27+3BSO2cwUOxCB0CQQDEEo1xYaxAjQTuNRYWBKLB+f/YdrNDbjrOi8Ipf4UmnhQ4WR9LMu0NXlQhSBY3ogWLLxysE5EsYGeDjexFPY7/AkBPHRatJqXFjqg8RnxZhjtnNfHaINEGWQPzaAFcHZlWdyyaF47pugOY9xcMkTprvVtxzdpsGoP5Fe8okE+7AaS1AkApiCyBycis27IhqwkEnPLE1+nDtlD+hYjbW2tDUwmnRnDOBsLEMQNZg5OagSSDoERAWThfkr17gugqS5OyjvmNAkBhaRFFCrthHBGJXJ1AkRm9+515Wgd4RVc7yLxfsXvL4bbh0OZUbC78CgJV+KPNW2+ClPXbjQJ52DMnZ/MYFX6Z";


	/** 商户号（商户需要替换自己的商户号）. */
	public static final String OID_PARTNER = "201804180001772116";

	/** 实时付款api版本. */
	public static final String API_VERSION = "1.0";

	/** 实时付款签名方式. */
	public static final String SIGN_TYPE = "RSA";

}
