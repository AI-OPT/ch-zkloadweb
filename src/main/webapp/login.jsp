<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String _base = request.getContextPath();
    request.setAttribute("_base", _base);
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "No-cache");
%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>配置中心登录</title>
<link href="${_base}/resources/baaspt/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${_base}/resources/baaspt/css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="${_base}/resources/baaspt/css/global.css" rel="stylesheet" type="text/css">
<link href="${_base}/resources/baaspt/css/login-regsiter.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${_base}/resources/baaspt/js/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="${_base}/resources/baaspt/js/bootstrap.js" ></script>
<script type="text/javascript" src="${_base}/resources/baaspt/js/datacheck.js" ></script>
<script type="text/javascript" src="${_base}/resources/baaspt/js/comp.js" ></script>
</head>
<script>
    var _base = "${_base}";
</script>
<script type="text/javascript">
			// 验证输入不为空的脚本代码
			 $("#loginform").submit( function () {
			     var usernameObj = document.getElementById("username");		
		         var passwordObj = document.getElementById("password");			
			 
				 if (isNull(usernameObj.value)) {
					//alert("请输入账号名");
					//usernameObj.focus();					
					//return false;
					window.location.href=_base+"/login?login_error=nulluser";
				 }		
			
				 if (isNull(passwordObj.value)) {
					//alert("请输入密码");
					//passwordObj.focus();
					//return false;
					 window.location.href=_base+"/login?login_error=nullpwd";
				 }			
				//GUCL:重写action
				$("#loginform").attr("action",_base+"/login");
				return true;
			
			}); 
			
			/* function logincheck(){
				var usernameObj = document.getElementById("username");		
		         var passwordObj = document.getElementById("password");			
			 
				 if (isNull(usernameObj.value)) {
					alert("请输入账号名");
					usernameObj.focus();
					return false;
				 }		
			
				 if (isNull(passwordObj.value)) {
					alert("请输入密码");
					passwordObj.focus();
					return false;
				 }			
			} */
			
		 </script>
<body>

  <div class="login-header" style="display:none;"><!--登录头部-->
     <div class="login-header-cnt" >
       <div class="login-header-cnt-logo">配置中心</div>
       <div class="login-header-cnt-mail">账户登录</div>
       </div>
     
     </div>
  <form id="loginform" action="${_base}/login"  method="POST">
   <div class="regsiter-wrapper">
       
       <div class="login-wrapper-cnt">
         <div class="login-wrapper-cnt-section">
         <ul>
         <c:if test="${param.login_error == 'nulluser'}">					        
		  <div class="login-note"> 请输入账号名!</div> 
		 </c:if>
		 <c:if test="${param.login_error == 'nullpwd'}">
		  <div class="login-note"> 请输入密码!</div>
		 </c:if>
		 <c:if test="${param.login_error == 'nouser'}">
		  <div class="login-note"> 账号不存在!</div>
		 </c:if>
		 <c:if test="${param.login_error == 'pwderror'}">
		  <div class="login-note">密码错误!</div>
		 </c:if>
		 <c:if test="${param.login_error == 'noauth'}">
		   <div class="login-note">该账号尚未分配权限</div>
		 </c:if>
		 <c:if test="${param.login_error == 'disable'}">
		   <div class="login-note">账号未激活</div>
		 </c:if>
		 <c:if test="${param.login_error == 'concurrent_error'}">					   					        
		    <div class="login-note"> 此账号已经登录!</div>					   
		 </c:if>		
		 <c:if test="${param.login_error == 'nullimgcode'}">
		   <div class="login-note">验证码为空</div>
		 </c:if>
		 <c:if test="${param.login_error == 'errorimgcode'}">					   					        
		    <div class="login-note"> 验证码错误!</div>				   
		 </c:if>		
         <li class="login-title">配置中心账户登录</li>
         <li class="user"><i class="icon-user"></i><input type="text" class="int-xlarge" id="username" name="username" placeholder="账号名"></li>
         <li class="password"><i class="icon-lock"></i><input type="password" class="int-xlarge-password" id="password" name="password" placeholder="密码" ></li>
         <li><input type="submit" class="login-btn" value="登 录"  ></li>
         <!-- <li class="Forget-password"><a href="身份验证-手机号.html">忘记密码？</a><a href="regsiter.html" class="right">立即注册</a></li> -->
         </ul>
         </div>
       
       </div>
    
     
    </div>
</form>
   <div class="footer-index">©2016 版权所有 亚信集团股份有限公司 </div>
   
  

</body>
</html>
