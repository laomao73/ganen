package com.ganen.util;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Tool {

    // 通知短信发送接口的http地址
    private static String URL_SEND_TZ = "https://api.dingdongcloud.com/v1/sms/notice/send";

    // 编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";


    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8) ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }

    public static String executePostByUsual( HashMap<String, String> parameters){
        String response = "";
        try{
            URL url = new URL(URL_SEND_TZ);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //发送post请求需要下面两行
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");;
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求数据内容
            String requestContent = "";
            Set<String> keys = parameters.keySet();
            for(String key : keys){
                requestContent = requestContent + key + "=" + parameters.get(key) + "&";
            }
            requestContent = requestContent.substring(0, requestContent.lastIndexOf("&"));
            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            //使用write(requestContent.getBytes())是为了防止中文出现乱码
            ds.write(requestContent.getBytes());
            ds.flush();
            try{
                //获取URL的响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String s = "";
                String temp = "";
                while((temp = reader.readLine()) != null){
                    s += temp;
                }
                response = s;
                reader.close();
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("No response get!!!");
            }
            ds.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Request failed!");
        }
        return response;
    }



    //下载PDF
    public static void addFile(byte[] bfile, String fileURI) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = null;
        try {
            dir = new File(fileURI.substring(0, fileURI.lastIndexOf("/") + 1).replace("/", "\\"));
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(fileURI);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //公章
    public static String GetImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String replace = encoder.encode(data).replace("\r", "");
        String replace1 = replace.replace("\n", "");
        return replace1;// 返回Base64编码过的字节数组字符串
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNextDay() throws ParseException {
        Date date = new Date();//设置日期格式
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value = String.valueOf(df.parse(df.format(date)).getTime() / 1000);
        return value;
    }

    public static String getNextYear() throws ParseException {
        Date date = new Date();//设置日期格式
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +1);//+1今年的时间加一年
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String value = String.valueOf(df.parse(df.format(date)).getTime() / 1000);
        return value;
    }

    //加密 sha-1
    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //非空
    public static boolean isFull(String str) {
        if (!str.equals("") && str != null) {
            return true;
        }
        return false;
    }

    public static String EncoderByMD5() {
        return "";
    }

    //验证营业执照号
    public static boolean isCredit(String credit) {
        Pattern pattern1 = Pattern.compile("^[1-9A-GY]{1}[1239]{1}[1-5]{1}[0-9]{5}[0-9A-Z]{10}$");
        Matcher matcher = pattern1.matcher(credit);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    //验证手机
    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    //验证邮箱
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //身份证验证粗略
    public static boolean is18ByteIdCard(String idCard) {
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$"); //粗略的校验
        Matcher matcher = pattern1.matcher(idCard);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    //身份证验证精确
    public static boolean isCard(String idCard) {
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");
        Matcher matcher = pattern1.matcher(idCard);
        int[] prefix = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int[] suffix = new int[]{1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        if (matcher.matches()) {
            Map<String, String> cityMap = initCityMap();
            if (cityMap.get(idCard.substring(0, 2)) == null) {
                return false;
            }
            int idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (int i = 0; i < 17; i++) {
                idCardWiSum += Integer.valueOf(idCard.substring(i, i + 1)) * prefix[i];
            }

            int idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            String idCardLast = idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod == 2) {
                if (idCardLast.equalsIgnoreCase("x")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast.equals(suffix[idCardMod] + "")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    //身份证城市
    private static Map<String, String> initCityMap() {
        Map<String, String> cityMap = new HashMap<String, String>();
        cityMap.put("11", "北京");
        cityMap.put("12", "天津");
        cityMap.put("13", "河北");
        cityMap.put("14", "山西");
        cityMap.put("15", "内蒙古");

        cityMap.put("21", "辽宁");
        cityMap.put("22", "吉林");
        cityMap.put("23", "黑龙江");

        cityMap.put("31", "上海");
        cityMap.put("32", "江苏");
        cityMap.put("33", "浙江");
        cityMap.put("34", "安徽");
        cityMap.put("35", "福建");
        cityMap.put("36", "江西");
        cityMap.put("37", "山东");

        cityMap.put("41", "河南");
        cityMap.put("42", "湖北");
        cityMap.put("43", "湖南");
        cityMap.put("44", "广东");
        cityMap.put("45", "广西");
        cityMap.put("46", "海南");

        cityMap.put("50", "重庆");
        cityMap.put("51", "四川");
        cityMap.put("52", "贵州");
        cityMap.put("53", "云南");
        cityMap.put("54", "西藏");

        cityMap.put("61", "陕西");
        cityMap.put("62", "甘肃");
        cityMap.put("63", "青海");
        cityMap.put("64", "宁夏");
        cityMap.put("65", "新疆");

//          cityMap.put("71", "台湾");
//          cityMap.put("81", "香港");
//          cityMap.put("82", "澳门");
//          cityMap.put("91", "国外");
//          System.out.println(cityMap.keySet().size());
        return cityMap;
    }


    public static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
