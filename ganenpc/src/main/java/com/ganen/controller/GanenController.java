package com.ganen.controller;

import com.ganen.entity.*;
import com.ganen.service.ICardTypeService;
import com.ganen.service.ICompanyService;
import com.ganen.service.impl.GanenService;
import com.ganen.service.impl.LimitPageService;
import com.ganen.service.impl.ServiceExpressService;
import com.ganen.util.LimitPageList;
import com.ganen.util.M;
import com.ganen.util.Page;
import com.ganen.util.Tool;
import javafx.geometry.Pos;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/ganen")
public class GanenController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    private GanenService ganenService;
    @Autowired
    private LimitPageService pageService;
    @Autowired
    private ServiceExpressService expressService;
    @Autowired
    private ICompanyService service;
    @Autowired
    private ICardTypeService cardTypeService;

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
     * 根据企业订单ID获得企业订单
     *
     * @param companyOrderID
     * @return
     */
    @RequestMapping(value = "orderByID.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> orderByID(@RequestParam("companyOrderID") int companyOrderID) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Company billing = service.getCompanyBillingByNumber(companyOrderID);
        List<ServiceOrder> serviceOrders = service.getCompanyOrder(companyOrderID);
        ServiceExpress serviceExpress=service.getServiceExpress(companyOrderID);
        Map<String, Object> serviceChild = new HashMap<String, Object>();
        Map<String, Map<String, Object>> employee = new HashMap<String, Map<String, Object>>();
        Map<String, Map<String, Object>> services = new HashMap<String, Map<String, Object>>();
        Map<String, Object> employeeChild = new HashMap<String, Object>();
        List<CardType> cardTypes = cardTypeService.selectCardTypeAll();
        for (int i = 0; i < serviceOrders.size(); i++) {
            ServiceOrder serviceOrder = serviceOrders.get(i);
            serviceChild.put("companyAllName", billing.getCompanyAllName());
            serviceChild.put("companyTaxNumber", billing.getCompanyBilling().getCompanyTaxNumber());
            serviceChild.put("companyAddress", billing.getCompanyBilling().getCompanyAddress());
            serviceChild.put("companyPhone", billing.getCompanyBilling().getCompanyPhone());
            serviceChild.put("companyOpenBank", billing.getCompanyBilling().getCompanyOpenBank());
            serviceChild.put("companyBankCard", billing.getCompanyBilling().getCompanyBankCard());
            serviceChild.put("companyOrderSalary", serviceOrder.getServiceOrderPrice().subtract(serviceOrder.getServiceOrderServicePrice()));
            serviceChild.put("companyOrderTaxCount", serviceOrder.getCompanyOrder().getCompanyOrderTaxCount());
            serviceChild.put("companyOrderPriceCount", serviceOrder.getServiceOrderPrice());
            serviceChild.put("companyOrderNumber", serviceOrder.getCompanyOrder().getCompanyOrderNumber());
            serviceChild.put("serviceCompanyAllName", serviceOrder.getService().getServiceCompanyName());
            serviceChild.put("serviceOpenNumber", serviceOrder.getService().getServiceOpenNumber());
            serviceChild.put("serviceOpenName", serviceOrder.getService().getServiceOpenName());
            serviceChild.put("serviceTicketType", serviceOrder.getServiceOrderTicketType());
            serviceChild.put("serviceTicketCategory", serviceOrder.getServiceOrderTicketCategory());
            serviceChild.put("serviceOrderImage", serviceOrder.getServiceOrderImage());
            serviceChild.put("serviceOrderID", serviceOrder.getServiceOrderID());
            if(serviceExpress!=null){
                serviceChild.put("serviceExpress", serviceExpress.getServiceExpress());
            }else{
                serviceChild.put("serviceExpress", null);
            }
            services.put("service" + i, serviceChild);
            serviceChild = new HashMap<String, Object>();

            for (int j = 0; j < serviceOrder.getEmployeeOrderList().size(); j++) {
                EmployeeOrder emps = serviceOrder.getEmployeeOrderList().get(j

                );
                employeeChild.put("employeePrice", emps.getEmployeePrice());
                employeeChild.put("employeeName", emps.getEmployee().getEmployeeName());
                employeeChild.put("employeePhone", emps.getEmployee().getEmployeePhone());
                employeeChild.put("employeeCard", emps.getEmployee().getEmployeeCard());
                employeeChild.put("employeeTax", emps.getEmployeeTax());
                employeeChild.put("employeeBankNumber", emps.getEmployee().getEmployeeBankNumber());
                employeeChild.put("employeeOpen", emps.getEmployee().getEmployeeOpen());
                employeeChild.put("employeeOpenNumber", emps.getEmployee().getEmployeeOpenNumber());
                employeeChild.put("serviceCompanyAllName", serviceOrder.getService().getServiceCompanyName());
                for (int h = 0; h < cardTypes.size(); h++) {
                    CardType cardType = cardTypes.get(h);
                    if (emps.getEmployee().getEmployeeCardType() == cardType.getCardTypeID()) {
                        emps.getEmployee().setEmployeeCardCN(cardType.getCardTypeName());
                    }
                }
                employeeChild.put("employeeCardCN", emps.getEmployee().getEmployeeCardCN());
                employee.put("employee" + j, employeeChild);
                employeeChild = new HashMap<String, Object>();
            }
        }
        map.put("serviceInfo", services);
        map.put("employeeInfo", employee);
        session.setAttribute("companyOrder", serviceOrders.get(0).getCompanyOrder());
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
        Map<String, Map<String,Object>> map1 = new HashMap<String, Map<String, Object>>();
        for (int i = 0; i < pageList.getList().size(); i++) {
            Map<String,Object> map2=new HashMap<String, Object>();
            Service service = (Service) pageList.getList().get(i);
            map2.put("serviceCompanyName",service.getServiceCompanyName());
            map2.put("serviceOpen",service.getServiceOpen());
            map2.put("serviceOpenNumber",service.getServiceOpenNumber());
            map1.put("service"+i,map2);
        }
        map.put("serviceInfo",map1);
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

}
