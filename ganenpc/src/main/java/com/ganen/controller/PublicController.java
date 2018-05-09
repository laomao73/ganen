package com.ganen.controller;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ganen.entity.CompanyLogin;
import com.ganen.entity.Service;
import com.ganen.service.ICompanyLoginService;
import com.ganen.service.ICompanyService;
import com.ganen.service.IServerService;
import com.ganen.util.SmsDemo;
import com.ganen.util.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 公共接口
 */
@Controller
@CrossOrigin
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private ICompanyLoginService companyLoginService;
    @Autowired
    private IServerService serviceService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private HttpServletRequest request;


    /**
     * 登录 企业 服务B 个人
     *
     * @param type      类型
     * @param userPhone 手机号
     * @param userPwd   密码
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestParam("type") String type, @RequestParam("userPhone") String userPhone, @RequestParam("userPwd") String userPwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        int i = new Integer(type);
        if (Tool.isFull(userPhone) && Tool.isFull(userPwd)) {
            switch (i) {
                case 1:
                    //企业
                    CompanyLogin login = companyLoginService.companyLogin(userPhone, userPwd);
                    if (login != null) {
                        session.setAttribute("companyLoginID", login.getCompanyLoginID());
                        //有分公司
                        if (login.getCompanyList().size() > 1) {
                            map.put("result", "请选择分公司");
                            map.put("elist", login.getCompanyList());
                        } else {
                            String one = companyLoginService.companyIsAuthOne(login.getCompanyList().get(0).getCompanyID());
                            String two = companyLoginService.companyIsAuthTwo(login.getCompanyList().get(0).getCompanyID());
                            //无分公司
                            if (one == null) {
                                session.setAttribute("companyID", login.getCompanyList().get(0).getCompanyID());
                                map.put("result", "请先认证一");
                            } else if (two == null) {
                                session.setAttribute("companyID", login.getCompanyList().get(0).getCompanyID());
                                map.put("result", "请先认证二");
                            } else if (one != null && two != null) {
                                session.setAttribute("companyLogin",login);
                                session.setAttribute("company", login.getCompanyList().get(0));
                                Integer adopt = companyLoginService.isAdopt(login.getCompanyList().get(0).getCompanyID());
                                if (adopt != 0) {
                                    map.put("result", "登录成功");
                                } else {
                                    map.put("result", "请等待审核");
                                }
                            }
                        }
                        return map;
                    }
                    map.put("result", "登录失败");
                    return map;
                case 2:
                    Service service = serviceService.serviceLogin(userPhone, userPwd);
                    if (service != null) {
                        session.setAttribute("service", service);
                        if(service.getServiceAdopt()!=0){
                            map.put("result", "登录成功");
                        }else{
                            map.put("result", "请等待审核");
                        }
                        return map;
                    }
                    map.put("result", "登录失败");
                    return map;
                case 3:
                    return null;
            }
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }


    /**
     * 注册
     *
     * @param type          类型
     * @param companyName   公司名
     * @param contactsName  联系人姓名
     * @param contactsPhone 联系人电话
     * @param code          验证码
     * @param contactsPwd   密码
     * @return
     */
    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> companyRegister(@RequestParam("type") Integer type, @RequestParam("companyName") String companyName, @RequestParam("contactsName") String contactsName, @RequestParam("contactsPhone") String contactsPhone, @RequestParam("code") Integer code, @RequestParam("contactsPwd") String contactsPwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (code != null && Tool.isFull(contactsName) && Tool.isFull(contactsPhone) && Tool.isFull(contactsPwd) && Tool.isFull(companyName)) {
            if (Tool.isPhone(contactsPhone)) {
                System.out.println(contactsPwd.length());
                if (contactsPwd.length() >7) {
                    HttpSession session = request.getSession();
                    map.put("code", session.getAttribute("code"));
                    if (session.getAttribute("code") != null) {
                        int code1 = (Integer) session.getAttribute("code");
                        if (code1 != code) {
                            map.put("result", 0);
                            map.put("content", "验证码错误");
                            return map;
                        }
                    } else {
                        map.put("result", 0);
                        map.put("content", "请点击发送验证码");
                        return map;
                    }
                    switch (type) {
                        case 1://企业
                            CompanyLogin login = new CompanyLogin(contactsName, contactsPhone, contactsPwd);
                            map = companyLoginService.companyRegister(login, companyName);
                            if (map.size() > 2) {
                                CompanyLogin cLogin = (CompanyLogin) map.get("object");
                                int companyID = companyLoginService.companyAuth(cLogin.getCompanyLoginID(), companyName);
                                session.setAttribute("loginUser", cLogin);
                                session.setAttribute("ID", companyID);
                            }
                            return map;
                        case 8427://服务
                            Service service = new Service(contactsName, contactsPhone, contactsPwd, companyName);
                            map = serviceService.serviceRegister(service);
                            if (map.size() > 1) {
                                Service obj = (Service) map.get("object");
                                session.setAttribute("loginUser", obj);
                                session.setAttribute("ID", obj.getServiceID());
                            }
                            return map;
                    }
                }
                map.put("result", 0);
                map.put("content", "密码长度小于8位，请重新输入");
                return map;
            }
            map.put("result", 0);
            map.put("content", "手机格式不正确,请您重新输入");
            return map;
        }
        map.put("result", 0);
        map.put("content", "输入内容不包含空项，请您重新输入。");
        return map;
    }


    /**
     * 忘记密码 一
     *
     * @param type    类型
     * @param phone   手机号
     * @param strCode 验证码
     * @return
     */
    @RequestMapping(value = "/forgetPwd.do")
    @ResponseBody
    public Map<String, Object> companyForgetPwd(@RequestParam("type") int type, @RequestParam("phone") String phone, @RequestParam("strCode") Integer strCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Integer code = (Integer) session.getAttribute("code");
        if (code == null) {
            map.put("result", "请点击发送验证码");
            return map;
        }
        if (Tool.isFull(phone) && strCode != null) {
            if (Tool.isPhone(phone)) {
                if (code.equals(strCode)) {
                    switch (type) {
                        case 1:
                            map = companyService.getCompanyID(phone);
                            break;
                        case 2:
                            map = serviceService.getServiceID(phone);
                            break;
                        case 3:
                            break;
                    }
                    if (map.size() > 1) {
                        int companyID = (Integer) map.get("companyID");
                        session.setAttribute("ID", companyID);
                    }
                    session.removeAttribute("code");
                    return map;

                }
                map.put("result", "验证码不正确,请您重新输入。");
                return map;
            }
            map.put("result", "手机格式不正确,请您重新输入。");
            return map;
        }
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }

    /**
     * 忘记密码 二
     *
     * @param type 类型
     * @param pwd  密码
     * @param pwd2 密码
     * @return
     */
    @RequestMapping(value = "/forgetPwd2.do")
    @ResponseBody
    public Map<String, Object> companyForgetPwd2(@RequestParam("type") int type, @RequestParam("pwd") String pwd, @RequestParam("pwd2") String pwd2) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("ID");
        if (Tool.isFull(pwd) && Tool.isFull(pwd2)) {
            if (pwd.equals(pwd2)) {
                if (pwd.length() >= 8) {
                    switch (type) {
                        case 1:
                            map = companyService.companyUpdatePwd(id, pwd);
                            break;
                        case 2:
                            map = serviceService.serviceUpdatePwd(id, pwd);
                            break;
                        case 3:
                            break;
                    }
                    session.removeAttribute("ID");
                } else {
                    map.put("result", 0);
                    map.put("result", "密码长度小于8位,请重新输入");
                }
                return map;
            }
            map.put("result", 0);
            map.put("result", "两次密码不一致,请重新输入");
            return map;
        }
        map.put("result", 0);
        map.put("result", "输入内容不包含空项，请您重新输入。");
        return map;
    }


    //发送验证码
    @RequestMapping(value = "/sendNumber.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> sendNumber(@RequestParam("contactsPhone") final String userPhone) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (Tool.isFull(userPhone)) {
            if (Tool.isPhone(userPhone)) {
                Random random = new Random();
                int code = (random.nextInt(9000) + 1000);
                SendSmsResponse response = null;
                response = SmsDemo.sendSms(userPhone, code);
                if (response.getCode() != null && response.getCode().equals("OK")) {
                    map.put("result", "发送成功");
                    HttpSession session = request.getSession();
                    session.setAttribute("code", code);
                    map.put("code", code);
                } else {
                    map.put("result", "发送失败");
                }
                return map;
            }
            map.put("result", "手机号格式不正确");
            return map;
        }
//        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        map.put("result", "手机号不能为空。");
        return map;
    }


    /**
     * 查看打款截图
     * @param serviceOrderID
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/image.do",method = RequestMethod.GET)
    @ResponseBody
    public String selectImage(@RequestParam("serviceOrderID") int serviceOrderID) throws UnsupportedEncodingException {
        String s = serviceService.selectImage(serviceOrderID);
        String address=new String(s.getBytes("gbk"),"iso-8859-1");
        return address;
    }

    //
    @ResponseBody
    @RequestMapping(value = "/download.do",method = RequestMethod.GET, produces = "application/octet-stream;charset=UTF-8")
    public String download(@RequestParam("serviceOrderID") int serviceOrderID,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String s = serviceService.selectImage(serviceOrderID);
        String address=new String(s.getBytes("utf8"),"iso-8859-1");
        HttpSession session = request.getSession();
        // 模拟文件
        String fileName = request.getSession().getServletContext().getRealPath(address);
        // 获取输入流
        try {
            InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));

            // 假如以中文名下载的话
            // 转码，免得文件名中文乱码
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" +  new String(fileName.getBytes("gbk"),"iso-8859-1"));
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
            return "下载失败";
        }
        return "下载成功";
    }


    //图形验证码
    @RequestMapping(value = "/imageCode.do")
    public void getAuthCode(HttpServletResponse response)
            throws IOException {
        int width = 63;
        int height = 37;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(Tool.getRandColor(200, 250));
        g.setFont(new Font("Times New Roman", 0, 28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for (int i = 0; i < 40; i++) {
            g.setColor(Tool.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            strCode = strCode + rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 28);
        }
        //将字符保存到session中用于前端的验证
        HttpSession session = request.getSession();
        session.setAttribute("strCode", strCode);
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }
}