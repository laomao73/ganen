package com.ganen.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ganen.bean.PaymentRequestBean;
import com.ganen.bean.PaymentResponseBean;
import com.ganen.constant.PaymentConstant;
import com.ganen.constant.RetCodeEnum;
import com.ganen.entity.*;
import com.ganen.service.impl.GanenService;
import com.ganen.service.impl.LimitPageService;
import com.ganen.service.impl.ServiceExpressService;
import com.ganen.service.impl.SignUpService;
import com.ganen.util.*;
import com.lianlianpay.security.utils.LianLianPaySecurity;
import com.lianpay.api.util.TraderRSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ganen.util.PaymentApiTest.queryPaymentAndDealBusiness;

@Controller
@CrossOrigin
@RequestMapping("/ganen")
public class GanenController {
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
    @Autowired
    HttpServletRequest request;
    @Autowired
    private GanenService ganenService;
    @Autowired
    private LimitPageService pageService;
    @Autowired
    private ServiceExpressService expressService;
    @Autowired
    private SignUpService signUpService;


    /**
     * 平台登录
     *
     * @param userName 用户名
     * @param pwd      密码
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> ganenLogin(@RequestParam("userName") String userName, @RequestParam("userPwd") String pwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Tool.isFull(userName) && Tool.isFull(pwd)) {
            GanenUser ganenUser = ganenService.ganenLogin(userName, pwd);
            if (ganenUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", ganenUser);
                map.put("result", 1);
                map.put("content", "登录成功");
            } else {
                map.put("result", 0);
                map.put("content", "登录失败");
            }
            return map;
        }
        map.put("result", 0);
        map.put("content", "输入内容不包含空项，请您重新输入。");
        return map;
    }

    /**
     * 企业
     *
     * @param pageNow        当前页
     * @param companyAllName 公司名
     * @param companyAdopt   状态 0未审核 1审核成功 2 审核失败
     * @return
     */
    @RequestMapping(value = "/getCompany.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCompany(@RequestParam("pageNow") Integer pageNow, @RequestParam("companyAllName") String companyAllName, @RequestParam("companyAdopt") Integer companyAdopt) {
        LimitPageList company = ganenService.getCompany(10, pageNow, companyAllName.trim(), companyAdopt);
        Map<String, Object> map = new HashMap<String, Object>();
        if (company.getList().size() > 0) {
            map.put("result", 1);
            map.put("totalPageCount", company.getPage().getTotalPageCount());
            Map<String, Map<String, Object>> m2 = new HashMap<String, Map<String, Object>>();
            Map<String, Object> map1 = new HashMap<String, Object>();
            for (int i = 0; i < company.getList().size(); i++) {
                Company company1 = (Company) company.getList().get(i);
                map1.put("companyID", company1.getCompanyID());
                map1.put("companyName", company1.getCompanyAllName());
                if (company1.getCompanyAdopt() == 0) {
                    map1.put("companyAdopt", "审核中");
                } else if (company1.getCompanyAdopt() == 1) {
                    map1.put("companyAdopt", "审核成功");
                } else if (company1.getCompanyAdopt() == 2) {
                    map1.put("companyAdopt", "审核失败");
                }
                m2.put("company" + i, map1);
                map1 = new HashMap<String, Object>();
            }
            map.put("companyInfo", m2);
        } else {
            map.put("result", 0);
            map.put("content", "暂无企业");
        }
        return map;
    }

    /**
     * 查看服务B
     *
     * @param pageNow               当前页
     * @param serviceCompanyAllName 公司名
     * @param serviceAdopt          状态
     * @return
     */
    @RequestMapping(value = "/getService.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getService(@RequestParam("pageNow") Integer pageNow, @RequestParam("serviceAllName") String serviceCompanyAllName, @RequestParam("serviceAdopt") Integer serviceAdopt) {
        LimitPageList service = ganenService.getService(1, pageNow, serviceCompanyAllName, serviceAdopt);
        List<Service> list = (List<Service>) service.getList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", 0);
        map.put("content", "暂无服务公司");
        return null;
    }


    /**
     * 根据ID查询企业
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/companyById.do", method = RequestMethod.GET)
    @ResponseBody
    public Company companyByID(@RequestParam("companyID") int id) {
        return ganenService.companyByID(id);
    }

    /**
     * 根据ID查询服务B
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/serviceById.do", method = RequestMethod.POST)
    @ResponseBody
    public Service serviceyByID(@RequestParam("serviceID") int id) {
        return ganenService.serviceByID(id);
    }


    /**
     * 企业是否通过审核
     *
     * @param companyID
     * @return
     */
    @RequestMapping(value = "/updateCompanyAdopt.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateCompanyAdopt(@RequestParam("companyID") int companyID) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = ganenService.updateCompanyAdopt(companyID);
        if (i > 0) {
            map.put("result", 1);
            map.put("content", "状态修改成功");
        } else {
            map.put("result", 0);
            map.put("content", "修改状态失败");
        }
        return map;
    }

    /**
     * 未通过审核
     *
     * @param companyID
     * @return
     */
    @RequestMapping(value = "/deleteCompanyAdopt.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteCompanyAdopt(@RequestParam("companyID") int companyID) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = ganenService.deleteCompanyAdopt(companyID);
        if (i > 0) {
            map.put("result", 1);
            map.put("content", "状态修改成功");
        } else {
            map.put("result", 0);
            map.put("content", "修改状态失败");
        }
        return map;
    }

    /**
     * 服务是否通过审核
     *
     * @param serviceID
     * @return
     */
    @RequestMapping(value = "/updateServiceAdopt.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateServiceAdopt(@RequestParam("serviceID") int serviceID) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = ganenService.updateCompanyAdopt(serviceID);
        if (i > 0) {
            map.put("result", "通过审核");
        } else {
            map.put("result", "未通过审核");
        }
        return map;
    }


    /**
     * 查询全部订单
     *
     * @param pageNow
     * @param companyOrderNumber
     * @param companyName
     * @param companyOrderState
     * @return
     */

    @RequestMapping(value = "orderAll.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> orderAll(@RequestParam("pageNow") Integer pageNow, @RequestParam("companyOrderNumber") String companyOrderNumber, @RequestParam("companyName") String companyName, @RequestParam("companyOrderState") String companyOrderState) {
        Map<String, Object> map = new HashMap<String, Object>();
        LimitPageList pageList = pageService.ganenOrder(pageNow, companyOrderNumber, companyName, companyOrderState);
        System.out.println(pageList);
        List<CompanyOrder> companyOrders = (List<CompanyOrder>) pageList.getList();
        if (companyOrders.size() == 0) {
            map.put("result", 0);
            map.put("content", "暂无订单");
            return map;
        }
        Page page = pageList.getPage();
        map.put("result", 1);
        Map<String, Map<String, Object>> coAll = new HashMap<String, Map<String, Object>>();
        for (int i = 0; i < companyOrders.size(); i++) {
            Map<String, Object> co = new HashMap<String, Object>();
            CompanyOrder companyOrder = companyOrders.get(i);
            co.put("companyOrderID", companyOrder.getCompanyOrderID());
            co.put("companyOrderNumber", companyOrder.getCompanyOrderNumber());
            co.put("companyAllName", companyOrder.getCompany().getCompanyAllName());
            co.put("companyOrderCount", companyOrder.getCompanyOrderCount());
            co.put("companyOrderPriceCount", companyOrder.getCompanyOrderPriceCount());
            co.put("companyOrderTime", companyOrder.getCompanyOrderTime());
            co.put("companyOrderState", companyOrder.getCompanyOrderState());
            for (int j = 0; j < companyOrder.getServiceOrderList().size(); j++) {
                ServiceOrder serviceOrder = companyOrder.getServiceOrderList().get(j);
                co.put("serviceOrderID", serviceOrder.getServiceOrderID());
            }
            coAll.put("companyOrder" + i, co);
            co = new HashMap<String, Object>();
        }
        map.put("totalPageCount", page.getTotalPageCount());
        map.put("content", coAll);
        return map;
    }

    /**
     * 获取全部企业
     *
     * @param pageNow
     * @param companyName
     * @return
     */
    @RequestMapping(value = "companyAll.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> companyAll(@RequestParam("pageNow") Integer pageNow, @RequestParam("companyName") String companyName) {
        LimitPageList pageList = pageService.companyAll(pageNow, companyName);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalPageCount", pageList.getPage().getTotalPageCount());
        Map<String, Map<String, Object>> map2 = new HashMap<String, Map<String, Object>>();
        for (int i = 0; i < pageList.getList().size(); i++) {
            Company company = (Company) pageList.getList().get(i);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("companyAllName", company.getCompanyAllName());
            map1.put("companyTaxNumber", company.getCompanyBilling().getCompanyTaxNumber());
            map1.put("companyAddress", company.getCompanyBilling().getCompanyAddress());
            map1.put("companyPhone", company.getCompanyBilling().getCompanyPhone());
            map1.put("companyOpenBank", company.getCompanyBilling().getCompanyOpenBank());
            map1.put("companyBankCard", company.getCompanyBilling().getCompanyBankCard());
            map1.put("ticketName", company.getCompanyTicket().getTicketName());
            map1.put("ticketPhone", company.getCompanyTicket().getTicketPhone());
            map1.put("ticketAddress", company.getCompanyTicket().getTicketAddress());
            map2.put("company" + i, map1);
        }
        map.put("companyInfo", map2);
        return map;
    }

    /**
     * 获取全部服务公司
     *
     * @param pageNow
     * @param serviceName
     * @return
     */
    @RequestMapping(value = "serviceAll.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> serviceAll(@RequestParam("pageNow") Integer pageNow, @RequestParam("serviceName") String serviceName) {
        LimitPageList pageList = pageService.serviceAll(pageNow, serviceName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalPageCount", pageList.getPage().getTotalPageCount());
        Map<String, Map<String, Object>> map1 = new HashMap<String, Map<String, Object>>();
        for (int i = 0; i < pageList.getList().size(); i++) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            Service service = (Service) pageList.getList().get(i);
            map2.put("serviceCompanyName", service.getServiceCompanyName());
            map2.put("serviceOpen", service.getServiceOpen());
            map2.put("serviceOpenNumber", service.getServiceOpenNumber());
            map1.put("service" + i, map2);
        }
        map.put("serviceInfo", map1);
        return map;
    }

    @RequestMapping(value = "/downloadImage.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> downloadImage(@RequestParam("serviceOrdereID") int serviceOrdereID, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String path = expressService.downloadImage(serviceOrdereID);
        HttpSession session = request.getSession();
        // 模拟文件
        String fileName = request.getSession().getServletContext().getRealPath(path);
        // 获取输入流
        try {
            InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));

            // 假如以中文名下载的话
            String filename = "发票.png";
            // 转码，免得文件名中文乱码
            filename = URLEncoder.encode(filename, "UTF-8");
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
            // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            int len = 0;
            while ((len = bis.read()) != -1) {
                out.write(len);
            }
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            map.put("result", 0);
            map.put("content", "下载失败");
        }
        map.put("result", 1);
        map.put("content", "下载成功");
        return map;
    }

    /**
     * 自动签企业
     *
     * @throws Exception
     */
    @RequestMapping(value = "/employeeIsContant.do", method = RequestMethod.GET)
    public void employeeIsContant() throws Exception {
        List<EmployeeOrder> employees = ganenService.employeeIsContant();
        for (int i = 0; i < employees.size(); i++) {
            EmployeeOrder employee = employees.get(i);
            if (employee.getEmployee().getEmployeeContract() != null && !employee.getEmployee().getEmployeeContract().equals("")) {
                signUpService.employeeIsContant(employee);
            }
        }
    }

    /**
     * 获取没签电子签的人
     *
     * @param companyOrderNumber
     * @return
     */
    @RequestMapping(value = "/getEmployee.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployee(@RequestParam("companyOrderNumber") String companyOrderNumber) {
        List<Employee> employees = ganenService.getEmployee(companyOrderNumber);
        return employees;
    }

    /**
     * 再次电子签
     *
     * @param companyOrderNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/againEmployee.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> againEmployee(@RequestParam("companyOrderNumber") String companyOrderNumber) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean is = true;

        List<EmployeeOrder> employees = ganenService.getEmployeesByID(companyOrderNumber);
        for (int i = 0; i < employees.size(); i++) {
            EmployeeOrder employeeOrder = employees.get(i);
            String resutl = signUpService.againEmployee(employeeOrder);
            if (resutl.indexOf("成功") == -1) {
                is = false;
                map.put("id", employeeOrder.getEmployee().getEmployeeID());
            }
        }
        if (!is) {
            map.put("result", "0");
            map.put("content", "短信全部发放失败");
        } else {
            map.put("result", "1");
            map.put("content", "短信全部发放成功");
        }
        return map;
    }

    /**
     * 可发放的员工
     *
     * @param companyOrderNumber
     * @return
     */
    @RequestMapping(value = "/grantEmployee.do", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployeeOrder> grantEmployee(@RequestParam("companyOrderNumber") String companyOrderNumber) {
        List<EmployeeOrder> employees = ganenService.grantEmployee(companyOrderNumber);
        if (employees.size() == 0) {
            ganenService.updateOrderState(companyOrderNumber);
        }
        return employees;
    }

    @RequestMapping(value = "/downloadPDF.do", method = RequestMethod.GET)
    @ResponseBody
    public void downloadPDF(@RequestParam("contractId") String contractId) throws Exception {
        BestsignOpenApiClient openApiClient = new BestsignOpenApiClient(developerId, privateKey, serverHost);
        String method = "/contract/downloadAttachment";
        byte[] bytes = openApiClient.contractDownload(contractId, method); // 设置输出的格式
        Tool.addFile(bytes, "C:\\Users\\ganenyouni\\Desktop\\" + contractId + "f.pdf");
        method = "/storage/contract/download/";
        byte[] bytea= openApiClient.contractDownload(contractId, method); // 设置输出的格式
        Tool.addFile(bytea, "C:\\Users\\ganenyouni\\Desktop\\" + contractId + ".pdf");
    }

    @RequestMapping(value = "/grant.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, Object>> grant(@RequestParam("companyOrderNumber") String companyOrderNumber) throws Exception {
        List<EmployeeOrder> employeeOrders = ganenService.grant(companyOrderNumber);
        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
        for (int i = 0; i < employeeOrders.size(); i++) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            EmployeeOrder employeeOrder = employeeOrders.get(i);
            PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
            paymentRequestBean.setNo_order(employeeOrder.getServiceOrder().getCompanyOrder().getCompanyOrderNumber() + employeeOrder.getEmployeeOrderID());
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            paymentRequestBean.setDt_order(df.format(day));
            paymentRequestBean.setMoney_order("0.01");
            paymentRequestBean.setCard_no(employeeOrder.getEmployee().getEmployeeBankNumber());
            paymentRequestBean.setAcct_name(employeeOrder.getEmployee().getEmployeeName());
//     paymentRequestBean.setBank_name("广发银行");
            paymentRequestBean.setInfo_order("1test测试");
            paymentRequestBean.setFlag_card("0");
            paymentRequestBean.setMemo("代付");
            // 填写商户自己的接收付款结果回调异步通知
            paymentRequestBean.setNotify_url("http://baidu.com");
            paymentRequestBean.setOid_partner(PaymentConstant.OID_PARTNER);
            // paymentRequestBean.setPlatform("test.com");
            paymentRequestBean.setApi_version(PaymentConstant.API_VERSION);
            paymentRequestBean.setSign_type(PaymentConstant.SIGN_TYPE);
            // 用商户自己的私钥加签
            paymentRequestBean.setSign(SignUtil.genRSASign(JSON.parseObject(JSON.toJSONString(paymentRequestBean))));
            String jsonStr = JSON.toJSONString(paymentRequestBean);
            // 用银通公钥对请求参数json字符串加密
            // 报Illegal key
            // size异常时，可参考这个网页解决问题http://www.wxdl.cn/java/security-invalidkey-exception.html
            String encryptStr = LianLianPaySecurity.encrypt(jsonStr, PaymentConstant.PUBLIC_KEY_ONLINE);
            if (StringUtils.isEmpty(encryptStr)) {
                // 加密异常
                map1.put("result", 0);
                map1.put("content", "加密异常");
            }
            JSONObject json = new JSONObject();
            json.put("oid_partner", PaymentConstant.OID_PARTNER);
            json.put("pay_load", encryptStr);
            String response = HttpUtil.doPost("https://instantpay.lianlianpay.com/paymentapi/payment.htm", json, "UTF-8");
            System.out.print("付款接口返回响应报文：" + response);
            if (StringUtils.isEmpty(response)) {
                // 出现异常时调用订单查询，明确订单状态，不能私自设置订单为失败状态，以免造成这笔订单在连连付款成功了，而商户设置为失败
                queryPaymentAndDealBusiness(paymentRequestBean.getNo_order());
            } else {
                PaymentResponseBean paymentResponseBean = JSONObject.parseObject(response, PaymentResponseBean.class);
                // 对返回0000时验证签名
                if (paymentResponseBean.getRet_code().equals("0000")) {
                    // 先对结果验签
                    boolean signCheck = TraderRSAUtil.checksign(PaymentConstant.PUBLIC_KEY_ONLINE,
                            SignUtil.genSignData(JSONObject.parseObject(response)), paymentResponseBean.getSign());
                    if (!signCheck) {
                        // 传送数据被篡改，可抛出异常，再人为介入检查原因
                        map1.put("result", 0);
                        map1.put("content", "返回结果验签异常,可能数据被篡改");
                        map.put("s", map1);
                        return map;
                    }
                    map1.put("result", 1);
                    map1.put("content", "发放成功");
                    ganenService.updateGrant(employeeOrder.getEmployee().getEmployeeID());
//                logger.info(paymentRequestBean.getNo_order() + "订单处于付款处理中");
                    // 已生成连连支付单，付款处理中（交易成功，不是指付款成功，是指跟连连流程正常），商户可以在这里处理自已的业务逻辑（或者不处理，在异步回调里处理逻辑）,最终的付款状态由异步通知回调告知
                } else if (paymentResponseBean.getRet_code().equals("4002")
                        || paymentResponseBean.getRet_code().equals("4004")) {
                    // 当调用付款申请接口返回4002，4003，4004,会同时返回验证码，用于确认付款接口
                    // 对于疑似重复订单，需先人工审核这笔订单是否正常的付款请求，而不是系统产生的重复订单，确认后再调用确认付款接口或者在连连商户站后台操作疑似订单，api不调用确认付款接口
                    // 对于疑似重复订单，也可不做处理，
                    // TODO
                    map1.put("result", 0);
                    map1.put("content", "疑似重复订单请去平台确认");
                } else if (RetCodeEnum.isNeedQuery(paymentResponseBean.getRet_code())) {
                    // 出现1002，2005，4006，4007，4009，9999这6个返回码时（或者对除了0000之后的code都查询一遍查询接口）调用付款结果查询接口，明确订单状态，不能私自设置订单为失败状态，以免造成这笔订单在连连付款成功了，而商户设置为失败
                    // 第一次测试对接时，返回{"ret_code":"4007","ret_msg":"敏感信息解密异常"},可能原因报文加密用的公钥改动了,demo中的公钥是连连公钥，商户生成的公钥用于上传连连商户站用于连连验签，生成的私钥用于加签
                    map1.put("result", 0);
                    map1.put("content", "该用户已发放过");
                    ganenService.updateGrant(employeeOrder.getEmployee().getEmployeeID());
                } else {
                    // 返回其他code时，可将订单置为失败
                    // TODO
                    System.out.println("failure");
                }
            }
            map.put("employeeID" + employeeOrder.getEmployee().getEmployeeID(), map1);
        }
        return map;
    }


    @RequestMapping(value = "/getPDF.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getPDF(){
        List<Employee> employees=ganenService.getPDF();
        return employees;
    }
}
