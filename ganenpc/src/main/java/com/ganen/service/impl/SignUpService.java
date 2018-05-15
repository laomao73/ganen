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
    private static String developerId = "1992567055739519558";
    //开发者私钥
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDuL5EZvg8n20cPyXTfJcirgXBJCV/mMsm9w2ZEBsnSRv1eOLi8tbNFFXgYacYgXjZNUGuDyHgHnBLYWhvtAJiRqP+CRnxIYia+o2h0INwnqnFUrbZKhuAJ/lgzYE7a+dWBYJR4CuQtsyG4qXxPWiqkm8RqNZxHj86v+1Z2VoVP2UIkRbb7Y1QUt1O7wEikA+vmJNtMYtQKDJtp8Bs4vXbeaP1ZHpXGvZCJn0FaOeemrG2UrJoeGN5G4aGWy34WhgyoHYyOw3iWAme+eJpJRPii6GZ3YK72ucIrHFiapv/hZR0OcWNuo9+Eu7GqoHM8/Gl1fEcw3f0gt5hZQaiAtnvZAgMBAAECggEBAMH+2A3BcRKjVHOVP61oVtLpcAzsp8EUaKA4gBiz9rlfvIl7ZWu+Ci8gV1p6/DXsxgmekJMbOjfodKjma4xTSfCJVuJCioNh/fCTo0XCZc2g0wGHIsv8oolBMZFWxaMwjSb9BK/pHwPhvHts/6SZ6dV8UTO4Sns2s7891KQ5wAbRePvUnwLQmdILH73aj5zPAQBImfwoOIXolYk4+jCnpS+XC4EavQuCNzs3uJf6qB+mD3HgTuUNQrO37nndLuCM0qP9q7JdqDVQ+WdGRQ/dW6ZUNeS1u/1yx8kWVtR8l7MB6kI9MKlLTfdfkqDXxalyCA7VUSi1OfvqVaX1nvtGrCECgYEA+Whm8jCMlKloOOF4emGLj1em8okTo7YgevXQYXxKZ2V8MPto3yhb4wS8IekaliYhennd6dsPuEiEt9nhHDXHo7tK9IFNxls1KGNbVwqFPaBnWSe/K798Msyi/WDIge5yIwojU6YCbzZUTYAConBWJB5goWix7QRUq6miBeGpOJUCgYEA9Hs7WKowjeSMA9FR9OFDIIYetSoCcLB12dQx0Zy/sQE3VQ0FYi3tyzCULoz4rnJiV3JTXtkrH9SztbZMec+m/+/mcPSeocjOHe09adBS/5CoYMQvR0oCHNnnIwXTbAYHhrEdz0tq0OTk+phlBOgfUuij67KZEoNTbRBBrWrkcTUCgYAQ+RXobX1jsJOzK0Tzrye8PY7S+pknjwFu3MOtnrPBjZqCCtn8NdbrACt9c0un5DhrCVgczhenxuehS2aScOaBMgHL2fDdCt/3O6u/pKukogHmg1nzNDjTjDGbZLbAgOWrlMkKrEWGJABgtdF4FIUIODkDQwi4xI6ukADCvbSycQKBgGAaeQ+3hvKgHrH5fIAIdLy6PCP26ZKmqwz/rr25IllQIxIZ1j3r1AgFhppt6+uXJzU8ya0FLUCL+kC58oIHsJv3D9zjK+Bo8S0ubu/DZnzzvlybggJCA2aX+ZX42k0iaZ72zMMJ4GlRMPmZIky1emq67ITgZe+zE/5RX5/t3vLpAoGAKS+D590p4+rWrsgYSZyP490RuWy314MUdgWYgPVAcR7oqzj+A2BqSXYfqvFHUoxse8+kPXqtaPO3Ph8bBvYsVXJVMHDKRGzJL8f9dBlp5MPb/5ciDyKfIrdGxHn4RWLajxuWbeyUtZCAP+Hn4n3UwXuS3kpanFXSg0BLeDkjzi0=";


    //公有云Server的完整HOST，需要根据不同的环境配置
    /**
     * 测试环境（http）：http://openapi.bestsign.info/openapi/v2
     * 测试环境（https）：https://openapi.bestsign.info/openapi/v2
     * 正式环境（https）：https://openapi.bestsign.cn/openapi/v2
     */
    private static String serverHost = "https://openapi.bestsign.cn/openapi/v2";
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
                        String templateToken = openApiClient.createContractPdf(account1, so.getService().getServiceContract(), eo.getEmployee().getEmployeeName(),so.getService().getServiceContract(),so.getService().getServiceCompanyName(),so.getCompanyOrder().getCompany().getCompanyAllName());
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
            String s = openApiClient.create("xuyuheng@52ganen.com", employee.getServiceOrder().getService().getServiceCompanyName());
            String data = openApiClient.template(employee.getEmployee().getEmployeeContract(), employee.getServiceOrder().getService().getServiceContract());
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
