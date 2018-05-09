package com.ganen.controller;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.ganen.util.PoiWordToHtml;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @Autowired
    private HttpServletRequest request;

    // 下载劳务用工模板
    @ResponseBody
    @RequestMapping(value = "downloadAll.do", produces = "application/octet-stream;charset=UTF-8")
    public String downloadAll(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // 模拟文件
        String fileName = request.getSession().getServletContext().getRealPath("/excel/发放明细模板表-劳动用工.xlsx");
        // 获取输入流
        try {
            InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));

            // 假如以中文名下载的话
            String filename = "发放明细模板表-劳动用工.xlsx";
            // 转码，免得文件名中文乱码
            filename = URLEncoder.encode(filename, "UTF-8");
            // 设置文件下载头

            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"),"iso-8859-1"));
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
        session.setAttribute("userType", 2);
        return "下载成功";
    }

    // 下载灵活用工模板
    @ResponseBody
    @RequestMapping(value = "download.do", produces = "application/octet-stream;charset=UTF-8")
    public String download(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // 模拟文件
        String fileName = request.getSession().getServletContext().getRealPath("/excel/发放明细模板表-灵活用工.xlsx");
        // 获取输入流
        try {
            InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));

            // 假如以中文名下载的话
            String filename = "发放明细模板表-灵活用工.xlsx";
            // 转码，免得文件名中文乱码
            filename = URLEncoder.encode(filename, "UTF-8");
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
        session.setAttribute("userType", 1);
        return "下载成功";
    }


    // 上传excel jpg png word
    @ResponseBody
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        HttpSession session = request.getSession();
        // 如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            // 上传文件路径
            String path;
            // 上传文件名
            String filename = file.getOriginalFilename();
            if (filename.indexOf("xls") > 0 || filename.indexOf("xlsx") > 0) {
                path = request.getServletContext().getRealPath("/companyExcel/");
                session.setAttribute("excelPath", path + File.separator + filename);
            } else if (filename.indexOf("doc") > 0 || filename.indexOf("docx") > 0) {
                path = request.getServletContext().getRealPath("/companyWord/");
                session.setAttribute("wordPath", path + File.separator + filename);
            } else {
                path = request.getServletContext().getRealPath("/companyImage/");
                session.setAttribute("imagePath", path + File.separator + filename);
            }
            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            return "上传成功";
        } else {
            return "上传失败";
        }
    }


}
