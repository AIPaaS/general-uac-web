<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<title>长虹UP物联网单点登录</title>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" /> 
</head>
<body>
<%@include file="/inc/head.jsp" %>
	
<div class="container login-bj">
	<div id="msg" class="success" style="padding-top: 100px; text-align: center">
	    <h2><spring:message code="screen.logout.header" /></h2>
	    <p><spring:message code="screen.logout.success" /></p>
	    <p><spring:message code="screen.logout.security" /></p>
    </div>
</div>
</body>
</html>