<%@page import="com.boco.SYS.entity.WebMsg"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>首页</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="<%=path%>/system/login/js/html5shiv.js"></script>
<script src="<%=path%>/system/login/js/respondjs"></script>
<![endif]-->

<link href="<%=path%>/system/login/css/main.css" rel="stylesheet">
<link href="<%=path%>/system/login/css/index.css" rel="stylesheet">
<script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
<style type="text/css">
.k-lis{height:180px}

</style>


<script type="text/javascript">
function goModel(url){
		window.location.href="<%=path%>"+url;
}
</script>
</head>
<body>
<div class="ind-k-lis">
	<%
		Map<String, List<WebMsg>> mapList=(Map<String, List<WebMsg>>)request.getAttribute("mapList");
		for(String s:mapList.keySet()){
			String name=s.split("_")[0];
			String count=s.split("_")[1];
			String url = s.split("_")[2];
			List<WebMsg> list=mapList.get(s);
			if(Integer.parseInt(count)>99){
				count = "...";
			}
	%>
			<div class="k-lis">
	    		<div class="title"><i id="<%=url %>"  title="更多"  onclick="goModel(this.id);"><%=count %></i><%=name %></div>
	    		<ul>
				<%
					for(WebMsg w:list){
					if(w.getOperDescribe().length()>20){
						w.setOperDescribe(w.getOperDescribe().substring(0, 20)+"...");
					}
				%>
					<li><a href="<%=path%>/<%=w.getMsgUrl()%>"><%=w.getOperDescribe() %></a></li>
				<%} %>
				</ul>
			</div>
		<%}%>
</div>
</body>
</html>