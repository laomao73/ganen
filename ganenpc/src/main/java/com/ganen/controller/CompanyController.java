package com.ganen.controller;


import com.ganen.entity.*;
import com.ganen.service.*;
import com.ganen.service.impl.CompanyService;
import com.ganen.service.impl.LimitPageService;
import com.ganen.service.impl.SignUpService;
import com.ganen.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.MalformedInputException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/company")
public class CompanyController {


    @Autowired
    private ICompanyService service;
    @Autowired
    private MatchService matchService;
    @Autowired
    private IServerService serverService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ICardTypeService cardTypeService;
    @Autowired
    private LimitPageService pageService;
    @Autowired
    private SignUpService signUpService;

    /**
     * 企业员工选择税务承担
     *
     * @param type 1 企业  2 个人
     * @return
     */
    @RequestMapping(value = "/chooseBear.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> chooseBear(@RequestParam("userType") Integer userType, @RequestParam("type") Integer type, @RequestParam("file") MultipartFile file) throws Exception {
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();
        Company company = (Company) session.getAttribute("company");
        String excelPath = "";
        if (!file.isEmpty()) {
            // 上传文件路径
            String path;
            // 上传文件名
            String filename = file.getOriginalFilename();
            if (filename.indexOf("xls") > 0 || filename.indexOf("xlsx") > 0) {
                path = request.getServletContext().getRealPath("/companyExcel/");
            } else if (filename.indexOf("doc") > 0 || filename.indexOf("docx") > 0) {
                path = request.getServletContext().getRealPath("/companyWord/");
            } else {
                path = request.getServletContext().getRealPath("/companyImage/");
            }
            File filepath = new File(path, filename);
            excelPath = path + File.separator + filename;
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
        } else {
            map.put("result", "excel上传失败");
            return map;
        }
        List<String[]> sheet1 = ReadExcel.readerExcel(excelPath, "sheet1", 8);
        File files = new File(excelPath);
        files.delete();
        List<EmployeeOrder> eolist = new ArrayList<EmployeeOrder>();
        boolean isUpdate = false;
        int index = 0;
        for (int i = 0; i < sheet1.size(); i++) {
            String[] strings = sheet1.get(i);
            //行
            EmployeeOrder employeeOrder = new EmployeeOrder();
            Employee employee = new Employee();
            for (int j = 0; j < strings.length; j++) {
                String value = strings[j];
                //title
                if (i == 0) {
                    switch (j) {
                        case 0:
                            if (value.indexOf("收款方账户名称") == -1) {
                                isUpdate = true;
                            }
                            break;

                        case 1:
                            if (value.indexOf("证件号") == -1) {
                                isUpdate = true;
                            }
                            break;
                        case 2:
                            if (value.indexOf("证件类型") == -1) {
                                isUpdate = true;
                            }
                            break;
                        case 3:
                            if (value.indexOf("银行账号") == -1) {
                                isUpdate = true;
                            }
                            break;
                        case 4:
                            if (value.indexOf("开户银行全称") == -1) {
                                isUpdate = true;
                            }
                            break;
                        case 5:
                            if (value.indexOf("开户行识别号") == -1) {
                                isUpdate = true;
                            }
                            break;
                        case 6:
                            if (value.indexOf("金额") == -1) {
                                isUpdate = true;
                            }
                            break;
                        case 7:
                            if (value == null) {
                                break;
                            }
                            if (value.indexOf("员工手机") == -1) {
                                isUpdate = true;
                            }
                            break;
                    }
                    if (isUpdate) {
                        map.put("result", "不可修改表格内容");
                        return map;
                    }

                } else {
                    switch (j) {
                        case 0:
                            //员工姓名
                            employee.setEmployeeName(value);
                            break;
                        case 1:
                            //证件号码
                            employee.setEmployeeCard(value);
                            break;
                        case 2:
                            //证件类型
                            if (value.equals("身份证")) {
                                employee.setEmployeeCardType(1);
                            } else if (value.equals("护照")) {
                                employee.setEmployeeCardType(2);
                            } else if (value.equals("回乡证")) {
                                employee.setEmployeeCardType(3);
                            } else if (value.equals("台胞证")) {
                                employee.setEmployeeCardType(4);
                            } else if (value.equals("军官证")) {
                                employee.setEmployeeCardType(5);
                            } else if (value.equals("警官证")) {
                                employee.setEmployeeCardType(6);
                            } else if (value.equals("士兵证")) {
                                employee.setEmployeeCardType(7);
                            } else if (value.equals("外国护照")) {
                                employee.setEmployeeCardType(8);
                            } else if (value.equals("临时身份证")) {
                                employee.setEmployeeCardType(9);
                            } else if (value.equals("户口本")) {
                                employee.setEmployeeCardType(10);
                            } else if (value.equals("外交人员身份证")) {
                                employee.setEmployeeCardType(11);
                            } else if (value.equals("外国人居留许可证")) {
                                employee.setEmployeeCardType(12);
                            } else if (value.equals("边民出入境通行证")) {
                                employee.setEmployeeCardType(13);
                            } else if (value.equals("香港永久性居民身份证")) {
                                employee.setEmployeeCardType(14);
                            } else if (value.equals("澳门居民身份证")) {
                                employee.setEmployeeCardType(15);
                            } else if (value.equals("台湾身份证")) {
                                employee.setEmployeeCardType(16);
                            } else {
                                map.put("result", "证件类型错误");
                                return map;
                            }
                            break;
                        case 3:
                            //银行卡号
                            employee.setEmployeeBankNumber(value);
                            break;
                        case 4:
                            //开户银行
                            employee.setEmployeeOpen(value);
                            break;
                        case 5:
                            //开户行识别号
                            employee.setEmployeeOpenNumber(value);
                            break;
                        case 6:
                            double d = Double.parseDouble(value);
                            BigDecimal moeny = BigDecimal.valueOf(d);
                            employeeOrder.setEmployeeSalary(moeny);
                            break;
                        case 7:
                            if (value == null) {
                                employee.setEmployeePhone("无");
                            } else {
                                employee.setEmployeePhone(value);
                            }
                            value=null;
                            break;
                    }
                }
            }

            if (i > 0) {
                index = index + 1;
                employee.setEmployeeID(index);
                employee.setCompany(company);
                employeeOrder.setEmployee(employee);
                eolist.add(employeeOrder);
            }
        }
        CompanyOrder companyOrder = new CompanyOrder();
        companyOrder.setCompanyOrderType(userType);
        companyOrder.setCompanyOrderTax(type);
        companyOrder.setCompanyOrderCount(eolist.size());
        companyOrder.setCompany(company);
        session.setAttribute("employeeList", eolist);
        session.setAttribute("compnayOrder", companyOrder);
        map.put("result", "读取成功");
        return map;
    }

    /**
     * 是否生产订单
     *
     * @return
     */
    @RequestMapping(value = "/checkAll.do")
    @ResponseBody
    public Map<String, Object> checkAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        //企业订单
        CompanyOrder compnayOrder = (CompanyOrder) session.getAttribute("compnayOrder");
        //员工订单
        List<EmployeeOrder> employeeList = (List<EmployeeOrder>) session.getAttribute("employeeList");
        //开票信息
        CompanyBilling billing = service.companyBillingByID(compnayOrder.getCompany().getCompanyID());
        Map<String, Map<String, Object>> service = new HashMap<String, Map<String, Object>>();
        Map<String, Object> serviceChild = new HashMap<String, Object>();
        Map<String, Map<String, Object>> employee = new HashMap<String, Map<String, Object>>();
        Map<String, Object> employeeChild = new HashMap<String, Object>();
        //返回订单详情
        if (compnayOrder.getCompanyOrderType() == 1) {
            Map<String, Object> lh = matchService.matchServiceType1(compnayOrder, employeeList);
            CompanyOrder companyOrder = (CompanyOrder) lh.get("companyOrder");
            List<ServiceOrder> serviceOrder = (List<ServiceOrder>) lh.get("serviceOrder");
            map.put("result", 1);
            //为前台整理数据
            for (int i = 0; i < serviceOrder.size(); i++) {
                ServiceOrder so = serviceOrder.get(i);
                serviceChild.put("companyAllName", companyOrder.getCompany().getCompanyAllName());
                serviceChild.put("companyTaxNumber", billing.getCompanyTaxNumber());
                serviceChild.put("companyAddress", billing.getCompanyAddress());
                serviceChild.put("companyPhone", billing.getCompanyPhone());
                serviceChild.put("companyOpenBank", billing.getCompanyOpenBank());
                serviceChild.put("companyBankCard", billing.getCompanyBankCard());
                serviceChild.put("companyOrderPrice", companyOrder.getCompanyOrderPrice());
                serviceChild.put("companyOrderTaxCount", companyOrder.getCompanyOrderTaxCount());
                serviceChild.put("companyOrderPriceCount", companyOrder.getCompanyOrderPriceCount());
                serviceChild.put("serviceCompanyAllName", so.getService().getServiceCompanyAllName());
                serviceChild.put("serviceOpenNumber", so.getService().getServiceOpenNumber());
                serviceChild.put("serviceOpen", so.getService().getServiceOpen());
                serviceChild.put("serviceTicketType", so.getService().getServiceTicketType());
                serviceChild.put("serviceID", i);
                String[] split = so.getService().getServiceTicketCategory().split("-");
                serviceChild.put("s", split);
                service.put("service" + i, serviceChild);

                for (int j = 0; j < so.getEmployeeOrderList().size(); j++) {
                    EmployeeOrder emps = so.getEmployeeOrderList().get(j);
                    employeeChild.put("employeePrice", emps.getEmployeePrice());
                    employeeChild.put("employeeTax", emps.getEmployeeTax());
                    employeeChild.put("employeeName", emps.getEmployee().getEmployeeName());
                    employeeChild.put("employeePhone", emps.getEmployee().getEmployeePhone());
                    employeeChild.put("employeeCard", emps.getEmployee().getEmployeeCard());
                    employeeChild.put("employeeCardCN", emps.getEmployee().getEmployeeCardCN());
                    employeeChild.put("employeeBankNumber", emps.getEmployee().getEmployeeBankNumber());
                    employeeChild.put("employeeOpen", emps.getEmployee().getEmployeeOpen());
                    employeeChild.put("employeeOpenNumber", emps.getEmployee().getEmployeeOpenNumber());
                    employeeChild.put("serviceCompanyAllName", so.getService().getServiceCompanyAllName());
                    employee.put("employee" + j, employeeChild);
                    employeeChild = new HashMap<String, Object>();
                }
                serviceChild = new HashMap<String, Object>();
            }
            session.setAttribute("serviceOrder", serviceOrder);
            session.setAttribute("companyOrder", companyOrder);
            map.put("result", 1);
            map.put("serviceInfo", service);
            map.put("employeeInfo", employee);

        } else {
            //全职
            Map<String, Object> qz = matchService.matchServiceType2(compnayOrder, employeeList);
            if (qz.size() > 2) {
                ServiceOrder serviceOrder = (ServiceOrder) qz.get("serviceOrder");
                List<EmployeeOrder> employeeOrder = (List<EmployeeOrder>) qz.get("employeeOrder");
                CompanyOrder companyOrder = (CompanyOrder) qz.get("companyOrder");
                serviceChild.put("companyAllName", companyOrder.getCompany().getCompanyAllName());
                serviceChild.put("companyTaxNumber", billing.getCompanyTaxNumber());
                serviceChild.put("companyAddress", billing.getCompanyAddress());
                serviceChild.put("companyPhone", billing.getCompanyPhone());
                serviceChild.put("companyOpenBank", billing.getCompanyOpenBank());
                serviceChild.put("companyBankCard", billing.getCompanyBankCard());
                serviceChild.put("companyOrderPrice", companyOrder.getCompanyOrderPrice());
                serviceChild.put("companyOrderTaxCount", companyOrder.getCompanyOrderTaxCount());
                serviceChild.put("companyOrderPriceCount", serviceOrder.getServiceOrderPrice());
                serviceChild.put("serviceCompanyAllName", serviceOrder.getService().getServiceCompanyAllName());
                serviceChild.put("serviceOpenNumber", serviceOrder.getService().getServiceOpenNumber());
                serviceChild.put("serviceOpen", serviceOrder.getService().getServiceOpen());
                serviceChild.put("serviceTicketType", serviceOrder.getService().getServiceTicketType());
                serviceChild.put("serviceID", 0);
                String[] split = serviceOrder.getService().getServiceTicketCategory().split("-");
                serviceChild.put("s", split);
                service.put("service", serviceChild);

                for (int j = 0; j < employeeOrder.size(); j++) {
                    EmployeeOrder emps = employeeOrder.get(j);
                    employeeChild.put("employeePrice", emps.getEmployeePrice());
                    employeeChild.put("employeeTax", emps.getEmployeeTax());
                    employeeChild.put("employeeName", emps.getEmployee().getEmployeeName());
                    employeeChild.put("employeePhone", emps.getEmployee().getEmployeePhone());
                    employeeChild.put("employeeCard", emps.getEmployee().getEmployeeCard());
                    employeeChild.put("employeeCardCN", emps.getEmployee().getEmployeeCardCN());
                    employeeChild.put("employeeBankNumber", emps.getEmployee().getEmployeeBankNumber());
                    employeeChild.put("employeeOpen", emps.getEmployee().getEmployeeOpen());
                    employeeChild.put("employeeOpenNumber", emps.getEmployee().getEmployeeOpenNumber());
                    employeeChild.put("serviceCompanyAllName", serviceOrder.getService().getServiceCompanyAllName());
                    employee.put("employee" + j, employeeChild);
                    employeeChild = new HashMap<String, Object>();
                }
                serviceOrder.setEmployeeOrderList(employeeOrder);
                List<ServiceOrder> slist = new ArrayList<ServiceOrder>();
                slist.add(serviceOrder);
                session.setAttribute("serviceOrder", slist);
                session.setAttribute("companyOrder", companyOrder);
                map.put("serviceInfo", service);
                map.put("employeeInfo", employee);
                serviceChild = new HashMap<String, Object>();
            } else {
                map = qz;
            }
        }
        return map;
    }


    /**
     * 创建订单
     */
    @RequestMapping(value = "newOrder.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> newOrder(@RequestParam("ids") String[] ids, @RequestParam("ticketType") String[] ticketType, @RequestParam("ticketCategory") String[] ticketCategory) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        List<ServiceOrder> serviceOrder = (List<ServiceOrder>) session.getAttribute("serviceOrder");
        CompanyOrder companyOrder = (CompanyOrder) session.getAttribute("companyOrder");
        for (int i = 0; i < ids.length; i++) {
            String[] split = ticketType[i].split("\"");
            String[] split1 = ticketCategory[i].split("\"");
            ServiceOrder serviceOrder1 = serviceOrder.get(i);
            serviceOrder1.setServiceOrderTicketType(split[1]);
            serviceOrder1.setServiceOrderTicketCategory(split1[1]);
        }

        boolean b = false;
//        企业订单
      //创建订单时间
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        companyOrder.setCompanyOrderTime(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Integer integer = service.CompanyID();
        String timeStr = df.format(timestamp);
        String time = timeStr.substring(2, timeStr.length());
        String id = "";
        for (int i = 0; i < serviceOrder.size(); i++) {
            ServiceOrder so = serviceOrder.get(i);
            id += so.getService().getServiceID();
        }

        time = time + companyOrder.getCompany().getCompanyID() + id + integer;
        companyOrder.setCompanyOrderNumber(time);
        int j = service.newCompanyOrder(companyOrder);
        for (int i = 0; i < serviceOrder.size(); i++) {
            ServiceOrder so = serviceOrder.get(i);
//            创建服务订单
            so.setServiceOrderTime(timestamp);
            so.setCompanyOrder(companyOrder);
            int i1 = serverService.newOrder(so);
//            创建员工订单
            int i2 = employeeService.newEmployeeService(so, timestamp, companyOrder.getCompanyOrderNumber());
            if (i2 != 1) {
                b = true;
            }
            if (i1 != 1) {
                b = true;
            }
        }
        session.removeAttribute("serviceOrder");
        session.removeAttribute("companyOrder");
        if (j == 1 && b == false) {
            map.put("result", 1);
            map.put("content", "订单创建成功");
            if(companyOrder.getCompanyOrderType()==1){
//                电子签地址
                String path = signUpService.firstSignUp(companyOrder.getCompanyOrderNumber());
            }
        } else {
            map.put("result", 0);
            map.put("content", "订单创建失败");

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

    @RequestMapping(value = "orderAll.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderAll(@RequestParam("pageNow") Integer pageNow, @RequestParam("companyOrderNumber") String companyOrderNumber, @RequestParam("companyName") String companyName, @RequestParam("companyOrderState") String companyOrderState) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Company company = (Company) session.getAttribute("company");
        LimitPageList pageList = pageService.companyOrderOne(pageNow, companyOrderNumber, companyName, companyOrderState, company.getCompanyID());
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
        Company company = (Company) session.getAttribute("company");
        CompanyBilling billing = service.companyBillingByID(company.getCompanyID());
        List<ServiceOrder> serviceOrders = service.getCompanyOrder(companyOrderID);

        Map<String, Object> serviceChild = new HashMap<String, Object>();
        Map<String, Map<String, Object>> employee = new HashMap<String, Map<String, Object>>();
        Map<String, Map<String, Object>> services = new HashMap<String, Map<String, Object>>();
        Map<String, Object> employeeChild = new HashMap<String, Object>();
        List<CardType> cardTypes = cardTypeService.selectCardTypeAll();
        for (int i = 0; i < serviceOrders.size(); i++) {
            ServiceOrder serviceOrder = serviceOrders.get(i);
            serviceChild.put("companyAllName", company.getCompanyAllName());
            serviceChild.put("companyTaxNumber", billing.getCompanyTaxNumber());
            serviceChild.put("companyAddress", billing.getCompanyAddress());
            serviceChild.put("companyPhone", billing.getCompanyPhone());
            serviceChild.put("companyOpenBank", billing.getCompanyOpenBank());
            serviceChild.put("companyBankCard", billing.getCompanyBankCard());
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
            services.put("service" + i, serviceChild);
            serviceChild = new HashMap<String, Object>();

            for (int j = 0; j < serviceOrder.getEmployeeOrderList().size(); j++) {
                EmployeeOrder emps = serviceOrder.getEmployeeOrderList().get(j

                );
                employeeChild.put("employeePrice", emps.getEmployeePrice());
                employeeChild.put("employeeName", emps.getEmployee().getEmployeeName());
                employeeChild.put("employeePhone", emps.getEmployee().getEmployeePhone());
                employeeChild.put("employeeCard", emps.getEmployee().getEmployeeCard());
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
     * 上传打款截图
     *
     * @param serviceOrderID 服务订单ID
     * @param file           照片
     * @return
     */
    @RequestMapping(value = "uploadImage.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("serviceOrderID") int serviceOrderID, @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 上传文件路径
        // 上传文件名
        if (!file.isEmpty()) {
            HttpSession session = request.getSession();
            CompanyOrder companyOrder = (CompanyOrder) session.getAttribute("companyOrder");

            String filename = file.getName();
            String path = request.getServletContext().getRealPath("/companyImage/");
            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            filename = companyOrder.getCompanyOrderNumber() + "" + serviceOrderID + ".png";
            // 将上传文件保存到一个目标文件当中
            File file1 = new File(path + File.separator + filename);
            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int result = serverService.uploadImage(serviceOrderID, "\\companyImage\\" + filename);
            if (result != 0) {
                map.put("result", 1);
                map.put("content", "上传成功");
            } else {
                map.put("result", 0);
                map.put("content", "上传失败");
            }
        } else {
            map.put("result", 0);
            map.put("content", "请选择图片");
        }
        return map;
    }


    /**
     * 分公司认证 创建分公司
     *
     * @param companyAllName        全称
     * @param companyBusinessNumber 信用代码证
     * @param file                  照片
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/authOne.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> authOne(@RequestParam("companyAllName") String companyAllName, @RequestParam("CompanyBusinessNumber") String companyBusinessNumber, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Tool.isFull(companyAllName) && Tool.isFull(companyBusinessNumber) && !file.isEmpty()) {
            // 上传文件路径
            // 上传文件名
            HttpSession session = request.getSession();
            int companyID = (Integer) session.getAttribute("companyLoginID");

            String filename = file.getOriginalFilename();
            String path = request.getServletContext().getRealPath("/companyImage/");
            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            filename = companyBusinessNumber + ".png";
            // 将上传文件保存到一个目标文件当中
            File file1 = new File(path + File.separator + filename);
            file.transferTo(file1);
            CompanyLogin login = new CompanyLogin(companyID);
            String p="\\companyImage\\" + filename;
            Company company = new Company(companyAllName, companyBusinessNumber, p, login);
            Map<String, Object> map1 = service.companyAuthOne(company);
            int companyID1 = (Integer) map1.get("companyID");
            session.setAttribute("companyLoginID", companyID1);
            map.put("result", map1.get("result"));
            return map;
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }

    /**
     * 分公司认证二
     *
     * @param companyLegalName  法人姓名
     * @param companyLegalPhone 法人电话
     * @param companyLegalCard  法人身份证
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "authTwo.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> authTow(@RequestParam("companyLegalName") String companyLegalName, @RequestParam("companyLegalPhone") String companyLegalPhone, @RequestParam("companyLegalCard") String companyLegalCard, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Tool.isFull(companyLegalName) && Tool.isFull(companyLegalPhone) && Tool.isFull(companyLegalCard) && !file.isEmpty()) {
            if (Tool.isPhone(companyLegalPhone)) {
                if (Tool.isCard(companyLegalCard)) {
                    HttpSession session = request.getSession();
                    int companyID = (Integer) session.getAttribute("companyLoginID");
                    String filename = file.getOriginalFilename();
                    String path = request.getServletContext().getRealPath("/companyImage/");
                    File filepath = new File(path, filename);
                    // 判断路径是否存在，如果不存在就创建一个
                    if (!filepath.getParentFile().exists()) {
                        filepath.getParentFile().mkdirs();
                    }
                    filename = companyLegalCard + ".png";
                    // 将上传文件保存到一个目标文件当中
                    File file1 = new File(path + File.separator + filename);
                    file.transferTo(file1);

                    CompanyLogin companyLogin = new CompanyLogin(companyID);
                    String p="\\companyImage\\" + filename;
                    Company company = new Company(companyID, companyLegalName, companyLegalPhone, companyLegalCard, p);
                    String result = service.companyAuthTwo(company);
                    map.put("result", result);
                    return map;
                }
                map.put("result", "身份证格式不正确,请您重新输入");
                return map;
            }
            map.put("result", "手机格式不正确,请您重新输入");
            return map;
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }

    /**
     * 检查公司上传的服务明细
     *
     * @return
     */
    @RequestMapping(value = "checkInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> checkInfo() {
        HttpSession session = request.getSession();
        List<EmployeeOrder> employeeList = (List<EmployeeOrder>) session.getAttribute("employeeList");
        Map<String, Object> map = new HashMap<String, Object>();
        List<CardType> cardTypes = cardTypeService.selectCardTypeAll();
        for (EmployeeOrder order : employeeList) {
            for (CardType c : cardTypes) {
                if (c.getCardTypeID() == order.getEmployee().getEmployeeCardType()) {
                    order.getEmployee().setEmployeeCardCN(c.getCardTypeName());
                }
            }
        }
        session.setAttribute("employeeList", employeeList);
        map.put("elist", employeeList);
        return map;
    }

    /**
     * 删除服务明细对象
     *
     * @param employeeID 删除ID
     * @return
     */
    @RequestMapping(value = "deleteEmployee.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteEmployee(@RequestParam("employeeID") int employeeID) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        List<EmployeeOrder> employeeList = (List<EmployeeOrder>) session.getAttribute("employeeList");
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmployee().getEmployeeID() == employeeID) {
                employeeList.remove(i);
                map.put("result", "删除成功");
                session.setAttribute("employeeList", employeeList);
                return map;
            }
        }
        map.put("result", "删除失败");
        return map;
    }

    /**
     * 查看服务明细员工具体项
     *
     * @param employeeID
     * @return
     */
    @RequestMapping(value = "/selectEmployee.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectEmployee(@RequestParam("employeeID") int employeeID) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        List<EmployeeOrder> employeeList = (List<EmployeeOrder>) session.getAttribute("employeeList");
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmployee().getEmployeeID() == employeeID) {
                map.put("object", employeeList.get(i));
                return map;
            }
        }
        map.put("object", null);
        return map;
    }


    /**
     * 修改服务明细中的人员
     *
     * @param id                 id
     * @param employeeName       姓名
     * @param employeeCard       证件号
     * @param employeeCardType   证件类型
     * @param employeeOpen       开户行
     * @param employeeOpenNumber 开户行行号
     * @param employeeBankNumber 银行账号
     * @param employeeSalary     应发工资
     * @return
     */
    @RequestMapping(value = "updateEmployee.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateEmployee(@RequestParam("id") Integer id, @RequestParam("employeeName") String employeeName, @RequestParam("employeeCard") String employeeCard, @RequestParam("employeeCardType") Integer employeeCardType, @RequestParam("employeeOpen") String employeeOpen, @RequestParam("employeeOpenNumber") String employeeOpenNumber, @RequestParam("employeeBankNumber") String employeeBankNumber, @RequestParam("employeeSalary") String employeeSalary) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (id != null && Tool.isFull(employeeName) && Tool.isFull(employeeCard) && Tool.isFull(employeeOpen) && Tool.isFull(employeeOpenNumber) && Tool.isFull(employeeBankNumber) && Tool.isFull(employeeSalary) && employeeCardType != null) {
            HttpSession session = request.getSession();
            List<EmployeeOrder> employeeList = (List<EmployeeOrder>) session.getAttribute("employeeList");
            for (EmployeeOrder e : employeeList) {
                if (e.getEmployee().getEmployeeID() == id) {
                    e.getEmployee().setEmployeeName(employeeName);
                    e.getEmployee().setEmployeeCard(employeeCard);
                    e.getEmployee().setEmployeeCardType(employeeCardType);
                    e.getEmployee().setEmployeeOpen(employeeOpen);
                    e.getEmployee().setEmployeeOpenNumber(employeeOpenNumber);
                    e.getEmployee().setEmployeeBankNumber(employeeBankNumber);
                    e.setEmployeeSalary(BigDecimal.valueOf(Double.valueOf(employeeSalary)));
                    map.put("result", "修改成功");
                    session.setAttribute("employeeList", employeeList);
                    return map;
                }
            }
        }
        map.put("result", "不能有空项");
        return map;
    }

    /**
     * 导出发放明细
     * @param response
     * @param companyOrderNumber
     */
    @RequestMapping(value = "expressExcelGant.do", method = RequestMethod.GET)
    @ResponseBody
    public void expressExcelGant(HttpServletResponse response,@RequestParam("companyOrderNumber") String companyOrderNumber) {
        String[] headText=new String[]{"收款方账户名称","证件号","证件类型","银行账号","开户银行全称","手机号","开户行识别号","应发工资","实发工资","个税"};
        List<CardType> cardTypes = cardTypeService.selectCardTypeAll();
        List<EmployeeOrder> employeeOrders = service.expressGant(companyOrderNumber);
        for (int i = 0; i < employeeOrders.size(); i++) {
            EmployeeOrder employeeOrder = employeeOrders.get(i);
            for (int j = 0; j < cardTypes.size(); j++) {
                CardType cardType = cardTypes.get(j);
                if(cardType.getCardTypeID()==employeeOrder.getEmployee().getEmployeeCardType()){
                    employeeOrder.getEmployee().setEmployeeCardCN(cardType.getCardTypeName());
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWordGrant(employeeOrders,headText).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((companyOrderNumber+"发放明细.xlsx").getBytes(), "iso-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            e.fillInStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出发放明细
     * @param response
     * @param companyOrderNumber
     */
    @RequestMapping(value = "expressExcelService.do",method = RequestMethod.GET)
    @ResponseBody
    public void expressExcelService(HttpServletResponse response,@RequestParam("companyOrderNumber") String companyOrderNumber){
        String[] headText=new String[]{"甲方全称","甲方税号","实发总额","个税","服务费","付款总额","发票类目","乙方全称","乙方账户"};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpSession session = request.getSession();
        Company company = (Company) session.getAttribute("company");
        CompanyBilling billing = service.companyBillingByID(company.getCompanyID());
        List<ServiceOrder> serviceOrders = serverService.expressExcelService(companyOrderNumber);

        try {
            ExcelUtil.createWorkService(billing,serviceOrders,headText).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((companyOrderNumber+"服务明细.xlsx").getBytes(), "iso-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            e.fillInStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
