package com.ganen.util;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.net.www.protocol.http.HttpURLConnection;


/**
 * 简易的 HTTP 请求工具, 一个静态方法完成请求, 支持 301, 302 的重定向, 支持自动识别 charset, 支持同进程中 Cookie 的自动保存与发送  <br/><br/>
 *
 * Demo:
 *
 * <pre>{@code
 *     // (可选) 设置默认的User-Agent是否为移动浏览器模式, 默认为false(PC浏览器模式)
 *     SimpleHttpUtils.setMobileBrowserModel(true);
 *     // (可选) 设置默认的请求头, 每次请求时都将会 添加 并 覆盖 原有的默认请求头
 *     SimpleHttpUtils.setDefaultRequestHeader("header-key", "header-value");
 *     // (可选) 设置 连接 和 读取 的超时时间, 连接超时时间默认为15000毫秒, 读取超时时间为0(即不检查超时)
 *     SimpleHttpUtils.setTimeOut(15000, 0);
 *
 *     // GET 请求, 返回响应文本
 *     String html = SimpleHttpUtils.get("http://blog.csdn.net/");
 *
 *     // GET 请求, 下载文件, 返回文件路径
 *     SimpleHttpUtils.get("http://blog.csdn.net/", new File("csdn.txt"));
 *
 *     // POST 请求, 返回响应文本
 *     String respText = SimpleHttpUtils.post("http://url", "body-data".getBytes());
 *
 *     // POST 请求, 上传文件, 返回响应文本
 *     SimpleHttpUtils.post("http://url", new File("aa.jpg"));
 *
 *     // 还有其他若干 get(...) 和 post(...) 方法的重载(例如请求时单独添加请求头), 详见代码实现
 * }</pre>
 *
 * @author xietansheng
 */
public class SimpleHttpUtils {


//
//    public static void main(String[] args) throws Exception {
//        // GET 请求, 返回响应文本
//        String sha1 = Tool.getSha1(Url.signature);
//        Map<String,String> map=new HashMap<String, String>();
//        map.put("appId", Url.APPID);
//        map.put("templateId", Url.TEMPLATEID);
//        map.put("version", Url.VERSION);
//        map.put("timestamp", Url.TIMESTAMP);
//        map.put("signature",sha1);
////        String html = SimpleHttpUtils.post();
//
////        System.out.println(html);
//    }
//
//
//    public static String post(String url,String data){
//        HttpURLConnection http = null;
//        PrintWriter out = null;
//        BufferedReader reader = null;
//        try {
//            //创建连接
//            URL urlPost = new URL(url);
//            http = (HttpURLConnection) urlPost
//                    .openConnection();
//            http.setDoOutput(true);
//            http.setDoInput(true);
//            http.setRequestMethod("POST");
//            http.setUseCaches(false);
//            http.setInstanceFollowRedirects(true);
//            http.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//
//            http.connect();
//
//            //POST请求
//            OutputStreamWriter outWriter = new OutputStreamWriter(http.getOutputStream(), "utf-8");
//            out = new PrintWriter(outWriter);
//            out.print(data);
//            out.flush();
//            out.close();
//            out = null;
//
//            //读取响应
//            reader = new BufferedReader(new InputStreamReader(
//                    http.getInputStream()));
//            String lines;
//            StringBuffer sb = new StringBuffer("");
//            while ((lines = reader.readLine()) != null) {
//                lines = new String(lines.getBytes(), "utf-8");
//                sb.append(lines);
//            }
//            reader.close();
//            reader = null;
//            System.out.println(sb.toString());
//            return sb.toString();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }finally {
//            if(null != http) http.disconnect();
//            if(null != out) out.close();
//            try{
//                if(null != reader) reader.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//
//    }


}