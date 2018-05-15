package com.ganen.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.scripts.JS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 上上签混合云SDK客户端
 */
public class BestsignOpenApiClient {

    private String developerId;

    private String privateKey;

    private String serverHost;

    private static String urlSignParams = "?developerId=%s&rtick=%s&signType=rsa&sign=%s";

    public BestsignOpenApiClient(String developerId, String privateKey,
                                 String serverHost) {
        this.developerId = developerId;
        this.privateKey = privateKey;
        this.serverHost = serverHost;
    }

    /**
     * 个人用户注册
     *
     * @param account  用户账号
     * @param name     姓名
     *                 //     * @param mail          用来接收通知邮件的电子邮箱
     *                 //     * @param mobile        用来接收通知短信的手机号码
     * @param identity 证件号码
     *                 //     * @param identityType  枚举值：0-身份证，目前仅支持身份证
     *                 //     * @param contactMail   电子邮箱
     *                 //     * @param contactMobile 手机号码
     *                 //     * @param province      省份
     *                 //     * @param city          城市
     *                 //     * @param address       地址
     * @return 异步申请任务单号
     * @throws IOException
     */
    public String userPersonalReg(String account, String name, String mail,
                                  String mobile, String identity, String identityType,
                                  String contactMail, String contactMobile, String province,
                                  String city, String address) throws Exception {
        String host = this.serverHost;
        String method = "/user/reg/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("name", name);
        requestBody.put("userType", "1");
        requestBody.put("mail", mail);
        requestBody.put("mobile", mobile);

        JSONObject credential = new JSONObject();
        credential.put("identity", identity);
        credential.put("identityType", identityType);
        credential.put("contactMail", contactMail);
        credential.put("contactMobile", contactMobile);
        credential.put("province", province);
        credential.put("city", city);
        credential.put("address", address);
        requestBody.put("credential", credential);
        requestBody.put("applyCert", "1");

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("taskId");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + ":"
                    + userObj.getString("errmsg"));
        }
    }


    /**
     * 查看证书申请状态
     *
     * @param account 账号
     * @param taskId
     * @return
     * @throws Exception
     */
    public String userAsyncApplyCertStatus(String account, String taskId) throws Exception {
        String host = this.serverHost;
        String method = "/user/async/applyCert/status/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("taskId", taskId);
        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("status");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }


    /**
     * 生成合同文件
     *
     * @param account 合同创建者账号
     * @param tid     模版编号
     * @return
     * @throws Exception
     */
    public String createContractPdf(String account, String tid, String name,String serviceContract,String serviceName,String companyName) throws Exception {
        String host = this.serverHost;
        String method = "/template/createContractPdf/";
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("tid", tid);
        JSONObject templateValue = new JSONObject();
        if(serviceContract.equals("152635431601000001")){
            templateValue.put("text", name);
            templateValue.put("text1", Tool.getNextYear());
        }else{
            templateValue.put("text", serviceName);
            templateValue.put("text1", companyName);
            templateValue.put("text2", serviceName);
            templateValue.put("text3", serviceName);
        }
        requestBody.put("templateValues", templateValue);
        // 组装请求参数，作为requestbody

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("templateToken");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }

    /**
     * 创建合同
     *
     * @param account       用户帐号
     * @param tid           模版编号
     * @param templateToken
     * @param title         合同标题
     *                      //     * @param description   合同描述
     *                      //     * @param expireTime        合同能够签署的到期时间
     * @return
     * @throws Exception
     */
    public String createByTemplate(String account, String tid, String templateToken, String title) throws Exception {
        String host = this.serverHost;
        String method = "/contract/createByTemplate/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("tid", tid);
        requestBody.put("templateToken", templateToken);
        requestBody.put("title", title);


        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("contractId");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }

    /**
     * 签署合同
     *
     * @param contractId 合同编号
     * @param tid        模版编号
     *                   //     * @param vars          模版变量值
     *                   //     * @param loginIp       登录IP
     *                   //     * @param loginTime     登录时间
     *                   //     * @param signIp        签署IP
     * @return
     * @throws Exception
     */
    public String template(String contractId, String tid) throws Exception {
        String host = this.serverHost;
        String method = "/contract/sign/template/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("tid", tid);
        requestBody.put("loginIp", "");
        requestBody.put("loginTime", "");
        requestBody.put("signIp", "");
        Map<String, Object> child = new HashMap<String, Object>();
        Map<String, Map<String, Object>> vars = new HashMap<String, Map<String, Object>>();
        child.put("account", "xuyuheng@52ganen.com");
        child.put("signatureImageData", "");
       if(tid.equals("152635431601000001")){
           vars.put("stamp", child);
           child = new JSONObject();
           child.put("account", "xuyuheng@52ganen.com");
           vars.put("date", child);
           child = new JSONObject();
           child.put("account", "xuyuheng@52ganen.com");
           vars.put("date1", child);
           requestBody.put("vars", vars);
       }else{
           vars.put("stamp", child);
           child = new JSONObject();
           child.put("account", "xuyuheng@52ganen.com");
           vars.put("date", child);
           requestBody.put("vars", vars);
       }
        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("data");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }


    /**
     * 手动签
     *
     * @param contractId 合同编号
     * @param signer     指定给哪个用户看
     *                   //     * @param dpi                               预览图片清晰度，枚举值：96-低清（默认），120-普清，160-高清，240-超清
     *                   //     * @param expireTime                        链接的过期时间
     * @param tid        模版ID
     *                   //     * @param varNames
     *                   //     * @param isAllowChangeSignaturePosition    是否允许拖动签名位置。取值1/0
     *                   //     * @param returnUrl                         手动签署时，当用户签署完成后，指定回跳的页面地址
     *                   //     * @param vcodeMobile
     *                   //     * @param isDrawSignatureImage              手动签署时是否手绘签名
     *                   //     * @param signatureImageName                签名/印章图片。取值default或者指定的印章名称
     *                   //     * @param pushUrl                           此处有配置则签署推送消息优先使用此配置，如果此处没有配置，则取开发者统一配置的异步推送地址
     *                   //     * @param version                           手动签的版本，默认为2，枚举值：
     * @return
     * @throws Exception
     */
    public String contractSendByTemplate(String contractId, String signer, String tid,String vcodeMobile) throws Exception {
        String host = this.serverHost;
        String method = "/contract/sendByTemplate/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        requestBody.put("signer", signer);
        requestBody.put("dpi", "120");
        requestBody.put("expireTime", Tool.getNextDay());
        requestBody.put("tid", tid);
        requestBody.put("varNames", "sign");
        requestBody.put("isAllowChangeSignaturePosition", "0");
        requestBody.put("returnUrl", "");
        requestBody.put("vcodeMobile", vcodeMobile);
        requestBody.put("isDrawSignatureImage", "1");
        requestBody.put("signatureImageName", "default");
        requestBody.put("version", "2");

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = com.ganen.util.HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("shortUrl");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }

    /**
     * 查看合同签署状态
     * @param contractId
     * @param phone
     * @return
     * @throws Exception
     */
    public String getSignerStatus(String contractId, String phone) throws Exception {
        String host = this.serverHost;
        String method = "/contract/getSignerStatus/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = com.ganen.util.HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString(phone);
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }

    /**
     * 锁定合同
     *
     * @param contractId
     * @return
     * @throws Exception
     */
    public String getlock(String contractId) throws Exception {
        String host = this.serverHost;
        String method = "/storage/contract/lock/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = com.ganen.util.HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("data");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }


    /**
     * 生产合同附件
     * @param contractId
     * @return
     * @throws Exception
     */
    public String createAttachment(String contractId) throws Exception {
        String host = this.serverHost;
        String method = "/contract/createAttachment";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("contractId", contractId);
        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = com.ganen.util.HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.getString("data");
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }


    /**
     * GET方法示例
     * 下载合同PDF文件
     *
     * @param contractId 合同编号
     * @return
     * @throws Exception
     */
    public byte[] contractDownload(String contractId,String method) throws Exception {
        String host = this.serverHost;

        // 组装url参数
        String urlParams = "contractId=" + contractId;

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, urlParams, null);
        // 签名参数追加为url参数
        urlParams = String.format(urlSignParams, this.developerId, rtick,
                paramsSign) + "&" + urlParams;
        // 发送请求
        byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
                urlParams);
        // 返回结果解析
        return responseBody;
    }


    public String create(String account, String text) throws Exception {
        String host = this.serverHost;
        String method = "/signatureImage/user/create/";

        // 组装请求参数，作为requestbody
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("text", text);

        // 生成一个时间戳参数
        String rtick = RSAUtils.getRtick();
        // 计算参数签名
        String paramsSign = RSAUtils.calcRsaSign(this.developerId,
                this.privateKey, host, method, rtick, null,
                requestBody.toJSONString());
        // 签名参数追加为url参数
        String urlParams = String.format(urlSignParams, this.developerId,
                rtick, paramsSign);
        // 发送请求
        String responseBody = com.ganen.util.HttpClientSender.sendHttpPost(host, method,
                urlParams, requestBody.toJSONString());
        // 返回结果解析
        JSONObject userObj = JSON.parseObject(responseBody);
        // 返回errno为0，表示成功，其他表示失败
        if (userObj.getIntValue("errno") == 0) {
            JSONObject data = userObj.getJSONObject("data");
            if (data != null) {
                return data.toString();
            }
            return null;
        } else {
            throw new Exception(userObj.getIntValue("errno") + userObj.getString("errmsg"));
        }
    }
}
