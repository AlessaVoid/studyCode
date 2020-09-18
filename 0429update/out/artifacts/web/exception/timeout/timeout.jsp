<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>超时页面</title>
		<!--框架必需start-->
		<script type="text/javascript" src="${path}/libs/js/jquery.js"></script>
		<script type="text/javascript" src="${path}/libs/js/language/cn.js"></script>
		<script type="text/javascript" src="${path}/libs/js/framework.js"></script>
		<link href="${path}/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" id="skin" prePath="${path}/" scrollerY="false" />
		<link rel="stylesheet" type="text/css" id="customSkin" />
		<!--框架必需end-->
		<!--弹窗组件start-->
		<script type="text/javascript" src="<%=path%>/libs/js/popup/dialog.js"></script>
		<!--弹窗组件end-->
		<script>
		$(function() {
			top.Dialog.alert("未登陆或者会话超时! | 超时提示",function(){
				window.parent.location.href = "${path}/toLogin.htm";
			});
		});
		</script>
	</head>
	<body></body>
</html>