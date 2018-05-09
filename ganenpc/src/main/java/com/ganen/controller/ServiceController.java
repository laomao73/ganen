package com.ganen.controller;

import com.ganen.dao.ServiceExpressDao;
import com.ganen.entity.*;
import com.ganen.service.impl.*;
import com.ganen.util.ExcelUtil;
import com.ganen.util.LimitPageList;
import com.ganen.util.Page;
import com.ganen.util.Tool;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("service")
public class ServiceController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ServerService serverService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LimitPageService pageService;

    @Autowired
    private CardTypeService cardTypeService;
    @Autowired
    private ServiceExpressService expressService;

    /**
     * 获取全部服务订单
     *
     * @param pageNow            当前页
     * @param companyOrderNumber 订单号
     * @param companyName        公司名
     * @param companyOrderState
     * @return
     */
    @RequestMapping(value = "orderAll.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderAll(@RequestParam("pageNow") Integer pageNow, @RequestParam("companyOrderNumber") String companyOrderNumber, @RequestParam("companyName") String companyName, @RequestParam("companyOrderState") String companyOrderState) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Service service = (Service) session.getAttribute("service");
        LimitPageList pageList = pageService.serviceOrderOne(service.getServiceID(), pageNow, companyOrderNumber, companyName, companyOrderState);
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
     * 根据ID获取订单
     *
     * @param serviceOrderID
     * @return
     */
    @RequestMapping(value = "/orderByID.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> orderByID(@RequestParam("serviceOrderID") int serviceOrderID) {
        Map<String, Object> map = new HashMap<String, Object>();
        ServiceOrder serviceOrder = serverService.serviceOrderByID(serviceOrderID);
        CompanyBilling billing = companyService.companyBillingByID(serviceOrder.getCompanyOrder().getCompany().getCompanyID());
        Map<String, Object> serviceChild = new HashMap<String, Object>();
        Map<String, Map<String, Object>> employee = new HashMap<String, Map<String, Object>>();
        Map<String, Object> employeeChild = new HashMap<String, Object>();
        serviceChild.put("companyAllName", serviceOrder.getCompanyOrder().getCompany().getCompanyAllName());
        serviceChild.put("companyTaxNumber", billing.getCompanyTaxNumber());
        serviceChild.put("companyAddress", billing.getCompanyAddress());
        serviceChild.put("companyPhone", billing.getCompanyPhone());
        serviceChild.put("companyOpenBank", billing.getCompanyOpenBank());
        serviceChild.put("companyBankCard", billing.getCompanyBankCard());
        serviceChild.put("companyOrderSalary", serviceOrder.getServiceOrderPrice().subtract(serviceOrder.getServiceOrderServicePrice()));
        serviceChild.put("companyOrderTaxCount", serviceOrder.getCompanyOrder().getCompanyOrderTaxCount());
        serviceChild.put("companyOrderPriceCount", serviceOrder.getServiceOrderPrice());
        serviceChild.put("serviceCompanyAllName", serviceOrder.getService().getServiceCompanyName());
        serviceChild.put("serviceOpenNumber", serviceOrder.getService().getServiceOpenNumber());
        serviceChild.put("serviceOpenName", serviceOrder.getService().getServiceOpenName());
        serviceChild.put("serviceTicketType", serviceOrder.getServiceOrderTicketType());
        serviceChild.put("serviceOrderImage", serviceOrder.getServiceOrderImage());
        serviceChild.put("serviceTicketCategory", serviceOrder.getServiceOrderTicketCategory());
        List<CardType> cardTypes = cardTypeService.selectCardTypeAll();
        for (int j = 0; j < serviceOrder.getEmployeeOrderList().size(); j++) {
            EmployeeOrder emps = serviceOrder.getEmployeeOrderList().get(j);
            employeeChild.put("employeePrice", emps.getEmployeePrice());
            employeeChild.put("employeeName", emps.getEmployee().getEmployeeName());
            employeeChild.put("employeePhone", emps.getEmployee().getEmployeePhone());
            employeeChild.put("employeeCard", emps.getEmployee().getEmployeeCard());
            employeeChild.put("employeeBankNumber", emps.getEmployee().getEmployeeBankNumber());
            employeeChild.put("employeeOpen", emps.getEmployee().getEmployeeOpen());
            employeeChild.put("employeeOpenNumber", emps.getEmployee().getEmployeeOpenNumber());
            employeeChild.put("serviceCompanyAllName", serviceOrder.getService().getServiceCompanyName());
            for (int i = 0; i < cardTypes.size(); i++) {
                CardType cardType = cardTypes.get(i);
                if (emps.getEmployee().getEmployeeCardType() == cardType.getCardTypeID()) {
                    emps.getEmployee().setEmployeeCardCN(cardType.getCardTypeName());
                }
            }
            employeeChild.put("employeeCardCN", emps.getEmployee().getEmployeeCardCN());
            employee.put("employee" + j, employeeChild);
            employeeChild = new HashMap<String, Object>();
        }
        map.put("serviceInfo", serviceChild);
        map.put("employeeInfo", employee);
//        map.put("se",serviceOrder);
        return map;
    }

    /**
     * 认证一
     *
     * @param serviceCompanyAllName 公司全名
     * @param serviceBusinessNumber 信用代码证
     * @param file                  营业执照截图
     * @param serviceIndustry       公司行业
     * @param servicePeople         服务人群
     * @param serviceTicketType     发票类型
     * @param serviceTicketCategory 发票类目
     * @return
     */
    @RequestMapping(value = "/authOne.do")
    @ResponseBody
    public Map<String, Object> autoOne(@RequestParam("serviceCompanyAllName") String serviceCompanyAllName, @RequestParam("serviceBusinessNumber") String serviceBusinessNumber, @RequestParam("file") MultipartFile file, @RequestParam("serviceIndustry") String serviceIndustry, @RequestParam("servicePeople") int servicePeople, @RequestParam("serviceTicketType") String serviceTicketType, @RequestParam("serviceTicketCategory") String serviceTicketCategory) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Tool.isFull(serviceCompanyAllName) && Tool.isFull(serviceBusinessNumber) && Tool.isFull(serviceIndustry) && Tool.isFull(serviceTicketCategory)) {
            HttpSession session = request.getSession();
            int serviceID = (Integer) session.getAttribute("ID");
            String filename = file.getOriginalFilename();
            System.out.println(filename);
            String path = request.getServletContext().getRealPath("/companyImage/");
            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            filename = serviceCompanyAllName + "营业执照.png";
            // 将上传文件保存到一个目标文件当中
            File file1 = new File(path + File.separator + filename);
            file.transferTo(file1);
            Service service = new Service(serviceID, serviceCompanyAllName, ("\\\\companyImage\\\\" + filename), serviceBusinessNumber, serviceIndustry, servicePeople, serviceTicketType, serviceTicketCategory);
            map = serverService.serviceAuthOne(service);
            return map;
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }


    /**
     * 认证二
     *
     * @param serviceLegalName  法人姓名
     * @param serviceLegalPhone 法人电话
     * @param serviceLegalCard  法人身份证
     * @param files             身份证照片
     * @return
     */
    @RequestMapping(value = "/authTwo.do")
    @ResponseBody
    public Map<String, Object> autoTwo(@RequestParam("serviceLegalName") String serviceLegalName, @RequestParam("serviceLegalPhone") String serviceLegalPhone, @RequestParam("serviceLegalCard") String serviceLegalCard, @RequestParam("files") MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Tool.isFull(serviceLegalName) && Tool.isFull(serviceLegalPhone) && Tool.isFull(serviceLegalCard) && files.length > 1) {
            if (Tool.isPhone(serviceLegalPhone)) {
                if (Tool.isCard(serviceLegalCard)) {
                    HttpSession session = request.getSession();
                    int serviceID = (Integer) session.getAttribute("ID");
                    String[] address = new String[2];
                    for (int i = 0; i < files.length; i++) {
                        String filename = files[i].getOriginalFilename();
                        System.out.println(filename);
                        String path = request.getServletContext().getRealPath("/companyImage/");
                        File filepath = new File(path, filename);
                        // 判断路径是否存在，如果不存在就创建一个
                        if (!filepath.getParentFile().exists()) {
                            filepath.getParentFile().mkdirs();
                        }
                        filename = serviceLegalName + i + "身份证.png";
                        // 将上传文件保存到一个目标文件当中
                        File file = new File(path + File.separator + filename);
                        files[i].transferTo(file);
                        address[i] = "\\\\companyImage\\\\" + filename;
                    }
                    Service service = new Service(serviceID, serviceLegalName, serviceLegalPhone, serviceLegalCard, address[0] + "-" + address[1]);
                    map = serverService.serviceAuthTwo(service);
                    return map;
                }
                map.put("result", "身份证格式不正确,请您重新输入");
                return map;
            }
            map.put("result", "手机号格式不正确，请您重新输入");
            return map;
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }

    /**
     * 查看企业信息
     *
     * @return
     */
    @RequestMapping(value = "/selectInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Service service = (Service) session.getAttribute("service");
        if (service.getServiceIndustry() == null) {
            map.put("result", 0);
            map.put("content", "请先去创建");
        } else {
            map.put("result", 1);
            map.put("content", service);
        }
        return map;
    }

    /**
     * 补全企业信息
     *
     * @param serviceIndustry
     * @param servicePeople
     * @param serviceTicketType
     * @param serviceTicketCategory
     * @param serviceOpenName
     * @param serviceOpen
     * @param serviceOpenNumber
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> updateInfo(@RequestParam("serviceIndustry") String serviceIndustry, @RequestParam("servicePeople") Integer servicePeople, @RequestParam("serviceTicketType") String serviceTicketType, @RequestParam("serviceTicketCategory") String serviceTicketCategory, @RequestParam("serviceOpenName") String serviceOpenName, @RequestParam("serviceOpen") String serviceOpen, @RequestParam("serviceOpenNumber") String serviceOpenNumber) throws IOException {
        HttpSession session = request.getSession();
        Service service = (Service) session.getAttribute("service");
        Map<String, Object> map = new HashMap<String, Object>();
        if (Tool.isFull(serviceIndustry) && servicePeople != null && Tool.isFull(serviceTicketType) && Tool.isFull(serviceTicketCategory) && Tool.isFull(serviceOpenName) && Tool.isFull(serviceOpen) && Tool.isFull(serviceOpenNumber)) {
            int result = 0;
            if (service.getServiceIndustry() != null) {
                result = 1;
            } else {
                result = 0;
            }
            service.setServiceIndustry(serviceIndustry);
            service.setServicePeople(servicePeople);
            service.setServiceTicketType(serviceTicketType);
            service.setServiceTicketCategory(serviceTicketCategory);
            service.setServiceOpenName(serviceOpenName);
            service.setServiceOpen(serviceOpen);
            service.setServiceOpenNumber(serviceOpenNumber);
            int i = serverService.updateInfo2(service);
            if (i != 0) {
                map.put("result", 1);
                if (result == 1) {
                    map.put("content", "修改成功");
                } else {
                    map.put("content", "保存成功");
                }
            } else {
                map.put("result", 0);
                if (result == 1) {
                    map.put("content", "修改失败");
                } else {
                    map.put("content", "保存失败");
                }
            }
        } else {
            map.put("result", 0);
            map.put("content", "输入内容不包含空项，请您重新输入。");
        }
        return map;
    }

    /**
     * 修改手机号
     *
     * @param phoneOne
     * @param phoneTwo
     * @param strCode
     * @return
     */
    @RequestMapping(value = "/updatePhone.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePhone(@RequestParam("phoneOne") String phoneOne, @RequestParam("phoneTwo") String phoneTwo, @RequestParam("strCode") Integer strCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (strCode != null && Tool.isFull(phoneOne) && Tool.isFull(phoneTwo)) {
            if (phoneOne.equals(phoneTwo)) {
                if (Tool.isPhone(phoneOne)) {
                    HttpSession session = request.getSession();
                    Integer code = (Integer) session.getAttribute("code");
                    System.out.println(code);
                    System.out.println(strCode);
                    if (code != null) {
                        if (code.equals(strCode)) {
                            Service service = (Service) session.getAttribute("service");
                            int i = serverService.updatePhone(service.getServiceID(), phoneOne.toString());
                            if (i != 0) {
                                map.put("result", 1);
                                map.put("content", "修改成功");
                            } else {
                                map.put("result", 0);
                                map.put("content", "修改失败");
                            }
                        } else {
                            map.put("result", 0);
                            map.put("content", "验证码不正确,请重新输入");
                        }
                    } else {
                        map.put("result", 0);
                        map.put("content", "请发送验证码");
                    }
                } else {
                    map.put("result", 0);
                    map.put("content", "手机格式不正确,请重新输入");
                }
            } else {
                map.put("result", 0);
                map.put("content", "两次手机号不同,请重新输入");
            }
        } else {
            map.put("result", 0);
            map.put("content", "输入内容不包含空项，请您重新输入。");
        }
        return map;
    }

    /**
     * 下载截图
     *
     * @param serviceOrdereID
     * @return
     */
    @RequestMapping(value = "/downloadImage.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> downloadImage(@RequestParam("serviceOrdereID") int serviceOrdereID, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String path = serverService.downloadImage(serviceOrdereID);
        HttpSession session = request.getSession();
        // 模拟文件
        String fileName = request.getSession().getServletContext().getRealPath(path);
        // 获取输入流
        try {
            InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));

            // 假如以中文名下载的话
            String filename = "打款截图.png";
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
     * 判断截图是否正确
     *
     * @return
     */
    @RequestMapping(value = "/deleteImage.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteImage(@RequestParam("serviceOrderID") int serviceOrderID) {
        Map<String, Object> map = new HashMap<String, Object>();
        String path = serverService.downloadImage(serviceOrderID);
        int i = serverService.deleteImage(serviceOrderID);
        if (i == 1) {
            map.put("result", 1);
            map.put("content", "删除成功");
            File file = new File(path);
            file.delete();
        } else {
            map.put("result", 0);
            map.put("content", "删除失败");
        }
        return map;
    }


    /**
     * 上传发票截图
     *
     * @param serviceOrderID
     * @param
     * @return
     */
    @RequestMapping(value = "/newExpress.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newExpress(@RequestParam("serviceOrderID") int serviceOrderID, @RequestParam("serviceExpress") MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 如果文件不为空，写入上传路径

        String filename = file.getName();
        String path = request.getServletContext().getRealPath("/companyImage/");
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        filename = serviceOrderID + "发票截图.png";
        // 将上传文件保存到一个目标文件当中
        File file1 = new File(path + File.separator + filename);
        int i = expressService.newExpress(serviceOrderID, ("\\companyImage\\" + filename));
        if(i>0){
            map.put("result",1);
            map.put("content","上传成功");
        }else{
            map.put("result",0);
            map.put("content","上传失败");
        }
        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("result",0);
            map.put("content","上传失败");
        }
        return map;
    }

    /**
     * 导出发放明细
     * @param response
     */
    @RequestMapping(value = "expressExcelGant.do", method = RequestMethod.GET)
    @ResponseBody
    public void expressExcelGant(HttpServletResponse response,@RequestParam("serviceOrderID") int serviceOrderID) {
        String[] headText=new String[]{"收款方账户名称","证件号","证件类型","银行账号","开户银行全称","手机号","开户行识别号","应发工资","实发工资","个税"};
        List<CardType> cardTypes = cardTypeService.selectCardTypeAll();
        ServiceOrder serviceOrder = serverService.serviceOrderByID(serviceOrderID);
        for (int i = 0; i < serviceOrder.getEmployeeOrderList().size(); i++) {
            EmployeeOrder employeeOrder = serviceOrder.getEmployeeOrderList().get(i);
            for (int j = 0; j < cardTypes.size(); j++) {
                CardType cardType = cardTypes.get(j);
                if(cardType.getCardTypeID()==employeeOrder.getEmployee().getEmployeeCardType()){
                    employeeOrder.getEmployee().setEmployeeCardCN(cardType.getCardTypeName());
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWordGrant(serviceOrder.getEmployeeOrderList(),headText).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("发放明细.xlsx").getBytes(), "iso-8859-1"));
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
     * @param
     */
    @RequestMapping(value = "expressExcelService.do",method = RequestMethod.GET)
    @ResponseBody
    public void expressExcelService(HttpServletResponse response,@RequestParam("serviceOrderID") int serviceOrder){
        String[] headText=new String[]{"甲方全称","甲方税号","实发总额","个税","服务费","付款总额","发票类目","乙方全称","乙方账户"};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpSession session = request.getSession();
        ServiceOrder serviceOrder1 = serverService.serviceOrderByID(serviceOrder);
        CompanyBilling billing = companyService.companyBillingByID(serviceOrder1.getCompanyOrder().getCompany().getCompanyID());
        List<ServiceOrder> slist=new ArrayList<ServiceOrder>();
        slist.add(serviceOrder1);
        try {
            ExcelUtil.createWorkService(billing,slist,headText).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("服务明细.xlsx").getBytes(), "iso-8859-1"));
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
