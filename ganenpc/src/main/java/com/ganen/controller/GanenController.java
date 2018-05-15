package com.ganen.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ganen.bean.BusinessNoticeBean;
import com.ganen.bean.NotifyResponseBean;
import com.ganen.bean.PaymentRequestBean;
import com.ganen.bean.PaymentResponseBean;
import com.ganen.constant.PaymentConstant;
import com.ganen.constant.PaymentStatusEnum;
import com.ganen.constant.RetCodeEnum;
import com.ganen.entity.*;
import com.ganen.service.impl.GanenService;
import com.ganen.service.impl.LimitPageService;
import com.ganen.service.impl.ServiceExpressService;
import com.ganen.service.impl.SignUpService;
import com.ganen.util.*;
import com.ganen.vo.RetBean;
import com.lianlianpay.security.utils.LianLianPaySecurity;
import com.lianpay.api.util.TraderRSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ganen.util.PaymentApiTest.queryPaymentAndDealBusiness;

@Controller
@CrossOrigin
@RequestMapping("/ganen")
public class GanenController {
    //开发者ID
    private static String developerId = "1987371269309334113";
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
            paymentRequestBean.setNotify_url("http://47.93.187.226:8080/ganenpc/ganen/receiveNotify.do");
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



    /**
     * 支付平台异步通知更新   用这个demo需要xml配置json转化格式，不然回调接收异常，具体配置参考类注释
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/receiveNotify.do", method = RequestMethod.POST)
    @ResponseBody
    public RetBean receiveNotify(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        resp.setCharacterEncoding("UTF-8");
        System.out.println("进入支付异步通知数据接收处理");
        RetBean retBean = new RetBean();
        String reqStr = YinTongUtil.readReqStr(req);
        if (YinTongUtil.isnull(reqStr))
        {
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            return retBean;
        }
        System.out.println("接收支付异步通知数据：【" + reqStr + "】");
        try
        {
            if (!YinTongUtil.checkSign(reqStr, PaymentConstant.PUBLIC_KEY_ONLINE,
                    PaymentConstant.SIGN_TYPE))
            {
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
                System.out.println("支付异步通知验签失败");
                return retBean;
            }
        } catch (Exception e)
        {
            System.out.println("异步通知报文解析异常：" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            return retBean;
        }
        retBean.setRet_code("0000");
        retBean.setRet_msg("交易成功");
        resp.getWriter().write(JSON.toJSONString(retBean));
        resp.getWriter().flush();
        System.out.println("支付异步通知数据接收处理成功");
        //解析异步通知对象
        // TODO:更新订单，发货等后续处理
        return retBean;
    }


    @RequestMapping(value = "/getPDF.do",method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getPDF(){
        List<Employee> employees=ganenService.getPDF();
        return employees;
    }
}
