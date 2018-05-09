package com.ganen.controller;

import com.ganen.entity.Company;
import com.ganen.entity.CompanyBilling;
import com.ganen.entity.CompanyLogin;
import com.ganen.entity.CompanyTicket;
import com.ganen.service.ICompanyLoginService;
import com.ganen.util.Tool;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业
 */
@Controller
@CrossOrigin
@RequestMapping("/companyLogin")
public class CompanyLoginController {

    @Autowired
    private ICompanyLoginService service;
    @Autowired
    private HttpServletRequest request;

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
        if (strCode!=null && Tool.isFull(phoneOne) && Tool.isFull(phoneTwo)) {
            if(phoneOne.equals(phoneTwo)){
                if(Tool.isPhone(phoneOne)){
                    HttpSession session = request.getSession();
                    Integer code = (Integer) session.getAttribute("code");
                    System.out.println(code);
                    System.out.println(strCode);
                    if(code!=null){
                        if(code.equals(strCode)){
                            CompanyLogin companyLogin = (CompanyLogin) session.getAttribute("companyLogin");
                            int i = service.updatePhone(companyLogin.getCompanyLoginID(), phoneOne);
                            if(i!=0){
                                map.put("result",1);
                                map.put("content","修改成功");
                            }else{
                                map.put("result",0);
                                map.put("content","修改失败");
                            }
                        }else{
                            map.put("result", 0);
                            map.put("content", "验证码不正确,请重新输入");
                        }
                    }else{
                        map.put("result", 0);
                        map.put("content", "请发送验证码");
                    }
                }else{
                    map.put("result", 0);
                    map.put("content", "手机格式不正确,请重新输入");
                }
            }else{
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
     * 认证一
     *
     * @param companyAllName        公司全名
     * @param companyBusinessNumber 公司营业执照号
     * @param file                  公司营业执照照片
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
            int companyID = (Integer) session.getAttribute("companyID");

            String filename = file.getName();
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
            String p=File.separator+File.separator+"companyImage" +File.separator+ File.separator + filename;
            Company company = new Company(companyID, companyAllName, companyBusinessNumber, p);
            String result = service.companyAuthOne(company);
            map.put("result", result);
            return map;
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }

    /**
     * 认证二
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
                    int companyID = (Integer) session.getAttribute("companyID");

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
                    String p=File.separator +File.separator +"companyImage"+ File.separator + File.separator + filename;
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
     * 查看开票信息收票信息
     *
     * @return
     */
    @RequestMapping(value = "isAuthFinish.do")
    @ResponseBody
    public Map<String, Object> isFinish() {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Company company = (Company) session.getAttribute("company");
        CompanyBilling billing = service.companyBillingByID(company.getCompanyID());
        CompanyTicket ticket = service.companyTicketByID(company.getCompanyID());
        if (billing != null && ticket != null) {
            map.put("companyBilling", billing);
            map.put("companyTicket", ticket);
            session.setAttribute("companyBilling", billing);
            session.setAttribute("companyTicket", ticket);
        } else {
            session.setAttribute("companyBilling", null);
            session.setAttribute("companyTicket", null);
            map.put("result", "请先去创建");
        }
        return map;
    }


    //补全企业信息
    @RequestMapping(value = "authFinish.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> authFinish(@RequestParam("companyTaxNumber") String companyTaxNumber, @RequestParam("companyAddress") String companyAddress, @RequestParam("companyPhone") String companyPhone, @RequestParam("companyOpenBank") String companyOpenBank, @RequestParam("companyBankCard") String companyBankCard, @RequestParam("ticketName") String ticketName, @RequestParam("ticketPhone") String ticketPhone, @RequestParam("ticketAddress") String ticketAddress) {
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();
        Company company = (Company) session.getAttribute("company");
        if (Tool.isFull(companyTaxNumber) && Tool.isFull(companyAddress) && Tool.isFull(companyPhone) && Tool.isFull(companyOpenBank) && Tool.isFull(companyBankCard) && Tool.isFull(ticketName) && Tool.isFull(ticketPhone) && Tool.isFull(ticketAddress)) {
            String result = "";
            CompanyBilling billing = new CompanyBilling(companyTaxNumber, companyAddress, companyPhone, companyOpenBank, companyBankCard);
            CompanyTicket ticket = new CompanyTicket(ticketName, ticketPhone, ticketAddress);
            CompanyBilling companyBilling = (CompanyBilling) session.getAttribute("companyBilling");
            CompanyTicket companyTicket = (CompanyTicket) session.getAttribute("companyTicket");
            if (companyBilling != null && companyTicket != null) {
                billing.setCompanyBillingID(companyBilling.getCompanyBillingID());
                ticket.setTicketID(companyTicket.getTicketID());
                result = service.companyAuthUpdate(billing, ticket);
            } else {
                result = service.companyAuthFinish(billing, ticket, company.getCompanyID());
            }
            session.removeAttribute("companyBilling");
            session.removeAttribute("companyTicket");
            map.put("result", result);
            return map;
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }

    //选择公司
    @RequestMapping(value = "chooseCompany.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> chooseCompany(@RequestParam("companyID") int companyID) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String one = service.companyIsAuthOne(companyID);
        String two = service.companyIsAuthTwo(companyID);
        //无分公司
        if (one == null) {
            session.setAttribute("companyID", companyID);
            map.put("result", "请先认证一");
        } else if (two == null) {
            session.setAttribute("companyID", companyID);
            map.put("result", "请先认证二");
        } else if (one != null && two != null) {
            Company company = service.chooseCompany(companyID);
            session.setAttribute("company", company);
            Integer adopt = service.isAdopt(companyID);
            if (adopt != 0) {
                session.setAttribute("companyID", companyID);
                map.put("result", "登录成功");
            } else {
                map.put("result", "请等待审核");
            }
        }
        return map;
    }
}



