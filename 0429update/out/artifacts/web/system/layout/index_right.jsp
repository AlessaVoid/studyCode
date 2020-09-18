<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index_right.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=basePath%>/libs/js/jquery.js"></script>
	<link href="<%=path%>/system/login/css/main.css" rel="stylesheet">
	<link href="<%=path%>/system/login/css/index.css" rel="stylesheet">
	<script type="text/javascript">
	
$(function(){
	//通过菜单码，判断交易时间节点和参数有效性是否显示
	var jyValue = parent.jy.value;
	var csValue = parent.cs.value;
	var mbValue = parent.mb.value;
	if(jyValue != 'GF-03'){//交易时间节点
		$("#jy").hide();
	}
	if(csValue != 'GF-04'){//参数有效性
		$("#cs").hide();
	}
	if(mbValue != 'PM-21'){//模板管理
		$("#mb").hide();
	}
})
function webTradeTime(){
	top.Dialog.open({
		ID:'webTradeTimeDialog',
		URL:"<%=path%>/webTradeTime/listUI.htm?menuNo=GF-03-02",
		Title:"交易时间节点",
		Width:850,
		Height:500
	});
}

function webParamValidity(){
	top.Dialog.open({
		ID:'webParamValidityDialog',
		URL:"<%=path%>/webParamValidity/listUI.htm?menuNo=GF-04-02",
		Title:"参数有效性",
		Width:900,
		Height:500
	});
}
function webImpTemplate(){
	top.Dialog.open({
		ID:'webImpTemplateDialog',
		URL:"<%=path%>/webImpTemplate/listUI.htm?menuNo=PM-21-02",
		Title:"模板下载",
		Width:700,
		Height:450
	});
}
	</script>
  </head>
  
  <body>
   <div class="ri-menu">
	  <ul>
	    <li class="rm-kj"><a onclick="top.Dialog.open({URL:'<%=path%>/webShortMenuInfo/updateUI.htm',Title:'快捷菜单配置',Width:700,Height:400});">快捷菜单</a></li>
		<li id="mb" class="rm-mb"><a onclick="webImpTemplate()">模板下载</a></li>
		<li id="jy" class="rm-jy"><a onclick="webTradeTime()">交易时间节点</a></li>
		<li id="cs" class="rm-cs"><a onclick="webParamValidity()">参数有效性</a></li>
	  </ul>
	</div>
  </body>
</html>
