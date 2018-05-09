<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
    <link rel="stylesheet" href="dropzone/dropzone.css">
    <link rel="stylesheet" href="dropzone/basic.css">
    <script src="jquery.min.js"></script>
    <script src="dropzone/dropzone.js"></script>
</head>
<body>
忘记密码<br/>
<form action="/public/forgetPwd.do" >
<input type="text" name="type"/>
<input type="text" name="phone"/>
<input type="text" name="strCode"/>
<input type="submit">
</form>


登录<br/>
<form action="/public/login.do" method="post">
<input type="text" name="type" placeholder="登录类型">
<input type="text" name="userPhone" placeholder="手机号">
<input type="text" name="userPwd" placeholder="密码">
<input type="submit">
</form>
选择公司
<form action="/companyLogin/chooseCompany.do" method="post">
    <input type="text" name="companyID">
    <input type="submit">
</form>


注册<br/>
<form action="/public/register.do" method="post">
<input type="text" name="type" placeholder="类型"/>
<input type="text" name="companyName" placeholder="公司名"/>
<input type="text" name="contactsName" placeholder="联系人姓名"/>
<input type="text" name="contactsPhone" placeholder="电话"/>
<input type="text" name="code" placeholder="验证码"/>
<input type="text" name="contactsPwd" placeholder="密码">
<input type="submit"/>
</form>
发送验证码
<form action="/public/sendNumber.do" method="get">
    <input type="text" name="contactsPhone">
    <input type="submit" >
</form>

<%--<form action="/service/uploadWord.do">--%>
<%--<input type="text" name="file">--%>
<%--<input type="submit">--%>
<%--</form>--%>


<%--<form action="/ganen/getCompany.do" method="post">--%>
<%--<input type="text" name="companyAdopt" placeholder="电话"/>--%>
<%--<input type="text" name="companyAllName" placeholder="电话"/>--%>
<%--<input type="text" name="pageNow" placeholder="电话"/>--%>
<%--<input type="submit" />--%>
<%--&lt;%&ndash;</form>&ndash;%&gt;--%>
<%--<form action="/ganen/updateCompanyAdopt.do" method="post">--%>
<%--<input type="text" name="companyID">--%>
<%--<input type="submit">--%>
<%--</form>--%>


企业认证一<br/>
<form action="/company/authOne.do" method="post" enctype="multipart/form-data">
    <input type="text" name="companyAllName" placeholder="公司全称">
    <input type="text" name="CompanyBusinessNumber" placeholder="公司信用代码证">
    <input type="file" name="file" placeholder="营业执照照片">
    <input type="submit"/>
</form>

企业认证二<br/>
<form action="/company/authTwo.do" method="post" enctype="multipart/form-data">
    <input type="text" name="companyLegalName" placeholder="公司法人姓名">
    <input type="text" name="companyLegalPhone" placeholder="公司法人电话">
    <input type="text" name="companyLegalCard" placeholder="公司法人身份证">
    <input type="file" name="file" placeholder="公司法照片" multiple>
    <input type="submit"/>
</form>

服务认证一<br/>
<form action="/service/authOne.do" method="post" enctype="multipart/form-data">
<input type="text" name="serviceCompanyAllName" placeholder="公司全称">
<input type="text" name="serviceBusinessNumber" placeholder="信用代码证">
<input type="file" name="file" multiple>
<input type="text" name="serviceIndustry" placeholder="公司行业">
<input type="text" name="servicePeople" placeholder="服务人群">
<input type="text" name="serviceTicketType" placeholder="发票类型">
<input type="text" name="serviceTicketCategory" placeholder="发票类目"/>
<%--<div id="dropz" class="dropzone" enctype="multipart/form-data"/>--%>
<input type="submit">
</form>


认证二<br/>
<form action="/companyLogin/authTwo.do" method="post" enctype="multipart/form-data">
<input type="text" name="serviceLegalName" placeholder="法人电话">
<input type="text" name="serviceLegalPhone" placeholder="法人手机号">
<input type="text" name="serviceLegalCard"  placeholder="身份证号">
<input type="file" name="files" multiple>
<input type="submit">
</form>

选择服务类型<br/>
<form action="/company/chooseBear.do" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="text" name="type">
    <input type="text" name="userType">
    <input type="submit">
</form>

当前页
<form action="/company/checkInfo.do" method="post">
    <input type="submit">
</form>

补全信息<br/>

<form action="/companyLogin/authFinish.do" method="post">
<input type="text" name="companyTaxNumber">
<input type="text" name="companyAddress">
<input type="text" name="companyPhone">
<input type="text" name="companyOpenBank">
<input type="text" name="companyBankCard">
<input type="text" name="ticketName">
<input type="text" name="ticketPhone">
<input type="text" name="ticketAddress">
<%--<div id="dropz" class="dropzone" enctype="multipart/form-data"/>--%>
<input type="submit">
</form>
删除员工
<form action="/company/deleteEmployee.do" method="post">
    <input type="text" name="employeeID">
    <input type="submit">
</form>
选择员工
<form action="/company/selectEmployee.do" method="post">
    <input type="text" name="employeeID">
    <input type="submit">
</form>
<%--&lt;%&ndash;&lt;%&ndash;public Map<String, Object> updateEmployee(&ndash;%&gt;--%>
<%--@RequestParam("id") Integer id,--%>
<%--@RequestParam("employeeName") String employeeName,--%>
<%--@RequestParam("employeeCard") String employeeCard,--%>
<%--@RequestParam("employeeCardType") Integer employeeCardType,--%>
<%--@RequestParam("employeeOpen") String employeeOpen,--%>
<%--@RequestParam("employeeOpenNumber") String employeeOpenNumber,--%>
<%--@RequestParam("employeeBankNumber") String employeeBankNumber,--%>
<%--@RequestParam("employeeSalary") String employeeSalary&ndash;%&gt;--%>
员工修改
<form action="/company/updateEmployee.do" method="post">
<input type="text" name="id">
<input type="text" name="employeeName">
<input type="text" name="employeeCard">
<input type="text" name="employeeCardType">
<input type="text" name="employeeOpen">
<input type="text" name="employeeOpenNumber">
<input type="text" name="employeeBankNumber">
<input type="text" name="employeeSalary">
<input type="submit" >
</form>






--%>



<%--<script>--%>
<%--Dropzone.autoDiscover = false;--%>
<%--var myDropzone = new Dropzone("#dropz", {--%>
<%--url: "company/authone.do",//文件提交地址--%>
<%--method:"post",  //请求类型，两种：post和put--%>
<%--paramName:"file", //文件类型，默认为file--%>
<%--maxFiles:1,//一次性上传文件的数量--%>
<%--maxFilesize: 2, //每次上传文件的大小，单位：MB，不得超于此限制--%>
<%--acceptedFiles: ".jpg,.gif,.png,.jpeg,.xlsx", //上传文件支持的文件类型--%>
<%--addRemoveLinks:true, //是否添加一个删除按钮，true为添加，false为不添加--%>
<%--parallelUploads: 1,//一次性上传的文件数量，和上面对应，写一样的就行--%>
<%--//previewsContainer:"#preview",//上传图片的预览窗口--%>
<%--dictDefaultMessage:'拖动文件至此或者点击上传',//在没有文件上传时，上传区域显示的文字--%>
<%--dictMaxFilesExceeded: "您最多只能上传1个文件！",//在上传文件个数超过限制时，报错的提示文字--%>
<%--dictResponseError: '文件上传失败!',//上传文件失败时的提示文字--%>
<%--dictInvalidFileType: "文件类型只能是*.jpg,*.gif,*.png,*.jpeg,*.xlsx",//上传文件超过支持类型限制时提示的文字--%>
<%--dictFallbackMessage:"浏览器不受支持",//当浏览器不支持此框架时提示的文字--%>
<%--dictFileTooBig:"文件过大上传文件最大支持.",//当文件超出规定大小时提示的文字--%>
<%--dictRemoveLinks: "删除",//删除按钮上的文字--%>
<%--dictCancelUpload: "取消",//取消上传按钮上的文字--%>
<%--init:function(){--%>
<%--this.on("success",function(file,data){ //事件：当上传成功时做什么--%>
<%--alert('上传成功');--%>
<%--});--%>
<%--this.on("error",function (file,data) { //事件，当上传失败时做什么--%>
<%--console.log(file);--%>
<%--});--%>
<%--}--%>
<%--});--%>
<%--</script>--%>


<%--<button onclick="copyText()">Copy Text</button>--%>
<%--<script>--%>


<%--</script>--%>

</body>
</html>