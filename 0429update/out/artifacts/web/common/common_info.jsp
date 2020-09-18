<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http:/boco.com.cn/tags-fmt" prefix="fmt"%>
<%@taglib uri="http:/boco.com.cn/tags-dic" prefix="dic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fm"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<html>
<style>
.none {
	display: none;
}
</style>
<!--框架必需start-->
<script type="text/javascript" src="${path}/libs/js/jquery.js"></script>
<script type="text/javascript" src="${path}/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${path}/libs/js/framework.js"></script>
<link href="${path}/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="${path}/" splitMode="true" href="<%=path%>/libs/skins/blue/style.css"/>
<link rel="stylesheet" type="text/css" id="customSkin" href="${path}/system/layout/skin/style.css"/>

<!--框架必需end-->
<!--基本选项卡start-->
<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
<!--自动提示框end-->
<!--基本选项卡end-->
<script type="text/javascript" src="${path}/libs/js/money.js"></script>
<!-- 树组件start -->
	<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
	<!-- 树组件end -->
	<!-- 树形下拉框start -->
	<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
	<!-- 树形下拉框end -->
	<script type="text/javascript">
	//弹出增、删、改、查框
	function showDialog(url,title,width,height){
		if(width == ''){
			width = 600;
		}
		if(height == ''){
			height == 480;
		}
		top.Dialog.open({
			URL : url,
			Title : title,
			Width : width,
			Height : height
		});
	}
	</script>