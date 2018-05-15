package com.ganen.service.impl;

import com.ganen.dao.CardTypeDao;
import com.ganen.dao.EmployeeDao;
import com.ganen.dao.SignUpDao;
import com.ganen.entity.CardType;
import com.ganen.entity.Employee;
import com.ganen.entity.EmployeeOrder;
import com.ganen.entity.ServiceOrder;
import com.ganen.service.ISignUpService;
import com.ganen.util.BestsignOpenApiClient;
import com.ganen.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.font.FontRenderContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SignUpService")
public class SignUpService implements ISignUpService {

    //开发者ID
    private static String developerId = "1987371269309334113";
    //开发者私钥
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCEO5v7rX8eFqIC3pwfrftFX1xRKv047zwzR0Aw0ZaAMMKXpDrf1aFVwSIzEIhJd8nqsMY+ZTUlvyricNVuWdl9NI9hMrdYeFbpXPkBEi4EsnOvuoW8TI8THzW7jbCDkwGmsQMrkrk1aaOFQqmllcN8wWLyqZdUJrsW5otTGkD+dOWRAJCew2pG32IJWVuSxpzsJPgHhULxuSan1IHjLMoUefqD3zuVqBJCpdSpsPA7v/5eF4W+YxcMctwDbnh46WKBca/jPcoCwhJtD+oq6nuCWCxBAGyo46jf71lUkb2iEQ7a4mHDtJBML1/LVYcPPTGyKDi4WJIBwB6orDeqvd8LAgMBAAECggEADCLocxX+Z8WOAxNpYuL1/+z7rRtz+EjEzrQIaTKjTIqtIXvaR3b1AsjiGau3xn5vEtI8r7dcRGWiSvwuKp2mf35WdoZQG5eX6n01AWsovBeg1I0a/yhLrWac6OTdQYY5jZILh3FE/GkAsBdpOKBhO88IBNuZ3wha/YQVX4A2XOQ984l1UVr/JeWO2MeIObO0owW6k+HUTu9pl/nknBbnc6VaKI4QIIKF+DkK/Y+JiFQ9zvoKJX6HDWw1v+ySHZXG4nY2bb17WP82+dRyJ6OMTfktHDaseEnDIgw7jVan9luFeHAg6B3gvD+bGWMaZZWDeqxsCAHok66JAjJEJ1TTcQKBgQC5Ay0aGlw0szn4Bj71hxy6TkgluFqnaopYDkiiOPGCFHI533iO4whOrWPs72td7k5AZxfN4ROeplvsnS4SPKs0BLvmLqARSV1gviJQdFeZVWAgFnNFmI3tBDHbxBi9lYc2AiXAWlR2h5a+tVDukn1pQnybEj2simRWqVI2eNnWiQKBgQC2+Cqs1P+5TihiCftUVJ1mkKdBUq5+YmFNtfS3ndMc/ae+sDkw81+FKG4oph5Izn8khpqMFv5TwasJnF/ka31HSiT1weYR/skCgNwmwRlKjcXocQpV94Mrlk8QSa+igD0+80+/JpuFZcHl3Rs0UFHupbni1WRfgQPShXKBJe6j8wKBgDzl8r6Y1qjyJa6iP68J3kbk5ZKk8BITW55IdVgQbk5m9IJgMIq36FAderrOxASTIGbKiGdYIuEh2Tg1NKkEF0Z0GHVX+5A1GetyKlFZOkxacd2eBituX6kYrGCuV4Zv5aW9Zze9g/fQAaqcfBtLVMSFAgb5JhvZIdX0vf74IJwhAoGAEzo7CdSMVwXOmpbf8Xm+3LNg0C2HJsLh5M+1OsJ8F/TT9ub/65P3ur+H6/+g+ReyJr16ajRUX6rfdXAYZ6GgFajF/4lzyvpSV1ABUpVCOiJx5L5UTrpm72KpRlXn/hSp2v6op7a/6yHJYYV9wV6bbY++3AZ+uMaPB0V8xfOdg18CgYEAkBWc46MuC8qfHojcAP8S/18/DHk1MSGYmGwItJUTy2R5oHtFwJSjecSSF/chAQ+V+THbWOtb1VZgO8CtWj2xTUxBLzXHiSyJHCAf8+esCABuCE7vSCqbuDDun/Nz0vnZHvQC2JsugxTfWU5fvITcxOAN8NVHZj2UBfTlxa9cNjM=";


    //公有云Server的完整HOST，需要根据不同的环境配置
    /**
     * 测试环境（http）：http://openapi.bestsign.info/openapi/v2
     * 测试环境（https）：https://openapi.bestsign.info/openapi/v2
     * 正式环境（https）：https://openapi.bestsign.cn/openapi/v2
     */
    private static String serverHost = "https://openapi.bestsign.info/openapi/v2";
    //已封装的HTTP接口调用的的client，您可以使用它来调用常规api和特殊api，您可以在此BestsignOpenApiClient中添加新的接口方法方便调用。

    @Resource
    private SignUpDao signUpDao;
    @Resource
    private CardTypeDao cardTypeDao;
    @Resource
    private EmployeeDao employeeDao;
    @Autowired
    private HttpServletRequest request;

    //第一次发放电子签
    @Override
    public String firstSignUp(String companyOrderNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ServiceOrder> serviceOrder = signUpDao.getServiceOrder(companyOrderNumber);
        for (ServiceOrder so : serviceOrder) {
            for (EmployeeOrder eo : so.getEmployeeOrderList()) {
                try {
                    //员工注册
                    //用户注册
                    String account = eo.getEmployee().getEmployeePhone(); //账号
                    String name = eo.getEmployee().getEmployeeName(); //用户名称
                    String mail = ""; //用户邮箱
                    String mobile = eo.getEmployee().getEmployeePhone(); //用户手机号码

                    //设置个人实名信息
                    String identity = eo.getEmployee().getEmployeeCard(); //证件号码
                    String identityType = ""; //证件类型  0-身份证
                    String contactMail = ""; //联系邮箱
                    String contactMobile = ""; //联系电话
                    String province = ""; //所在省份
                    String city = ""; //所在城市
                    String address = ""; //联系地址
                    BestsignOpenApiClient openApiClient = new BestsignOpenApiClient(developerId, privateKey, serverHost);
                    String taskId = openApiClient.userPersonalReg(account, name, mail, mobile, identity, identityType, contactMail, contactMobile, province, city, address);
                    if (taskId != null) {
                        String status = openApiClient.userAsyncApplyCertStatus(account, taskId);
                        if (!status.equals("5")) {
                            taskId = openApiClient.userPersonalReg(account, name, mail, mobile, identity, identityType, contactMail, contactMobile, province, city, address);
                        }
                        String account1 = "xuyuheng@52ganen.com";
                        //生成合同
                        String templateToken = openApiClient.createContractPdf(account1, so.getService().getServiceContract(), eo.getEmployee().getEmployeeName());
                        //创建合同
                        String contractId = openApiClient.createByTemplate(account, so.getService().getServiceContract(), templateToken, "个人电子签");
                        int result = employeeDao.updateContants(eo.getEmployee().getEmployeeID(), contractId);
                        map.put(eo.getEmployee().getEmployeeID() + "", result);
                        //手动签署合同地址
                        String shortUrl = openApiClient.contractSendByTemplate(contractId, account, so.getService().getServiceContract(), eo.getEmployee().getEmployeePhone());
                        if (shortUrl != null) {
                            String[] split = shortUrl.split("//");
                            HashMap<String, String> map2 = new HashMap<String, String>();
                            String content = "【感恩有你】您有一份感恩有你的服务商宁波沃山发起的合同等待签署,请点击链接完成签约https://" + split[1] + " ,如有疑问请联系:010-59693898";
                            map2.put("apikey", "c82793705919e1619315ced5ac1aba37");
                            map2.put("mobile", eo.getEmployee().getEmployeePhone());
                            map2.put("content", content);
                            String results = Tool.executePostByUsual(map2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //企业签
    @Override
    public void employeeIsContant(EmployeeOrder employee) throws Exception {
        BestsignOpenApiClient openApiClient = new BestsignOpenApiClient(developerId, privateKey, serverHost);
        String signerStatus = openApiClient.getSignerStatus(employee.getEmployee().getEmployeeContract(), employee.getEmployee().getEmployeePhone());
        if (signerStatus.equals("2")) {
            employeeDao.updateContantsState(employee.getEmployee().getEmployeeID());
            String fileName = request.getSession().getServletContext().getRealPath("/contract/宁波.jpg");
            String image = Tool.GetImageStr(fileName);
            String data = openApiClient.template(employee.getEmployee().getEmployeeContract(), employee.getServiceOrder().getService().getServiceContract(), image);
            String getlock = openApiClient.getlock(employee.getEmployee().getEmployeeContract());
            String attachment = openApiClient.createAttachment(employee.getEmployee().getEmployeeContract());
        }
    }

    @Override
    public String againEmployee(EmployeeOrder employee) throws Exception {
        BestsignOpenApiClient openApiClient = new BestsignOpenApiClient(developerId, privateKey, serverHost);
        String shortUrl = openApiClient.contractSendByTemplate(employee.getEmployee().getEmployeeContract(), employee.getEmployee().getEmployeePhone(), employee.getServiceOrder().getService().getServiceContract(), employee.getEmployee().getEmployeePhone());
        if (shortUrl != null) {
            String[] split = shortUrl.split("//");
            HashMap<String, String> map2 = new HashMap<String, String>();
            String content = "【感恩有你】您有一份感恩有你的服务商宁波沃山发起的合同等待签署,请点击链接完成签约https://" + split[1] + " ,如有疑问请联系:010-59693898";
            map2.put("apikey", "c82793705919e1619315ced5ac1aba37");
            map2.put("mobile", employee.getEmployee().getEmployeePhone());
            map2.put("content", content);
            String results = Tool.executePostByUsual(map2);
            return results;
        }

        return null;

    }
}
