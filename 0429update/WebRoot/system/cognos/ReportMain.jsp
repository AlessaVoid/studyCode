<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<meta charset="utf-8">
		
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="<%=path%>/system/login/js/html5shiv.js"></script>
<script src="<%=path%>/system/login/js/respondjs"></script>
<![endif]-->
<link href="<%=path%>/system/login/css/main.css" rel="stylesheet">
<link href="<%=path%>/system/login/css/index.css" rel="stylesheet">

<script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
		<title>报表列表</title>
	</head>
		<body>
		<div class="clearfix">
  <div class="le-lis-menu">
    <ul>
    	<c:forEach items="${funList}" var="u">
    	 <li><a id="${u.menuNo }" href="${u.menuUrl}">${u.menuName}</a></li><!-- 加载菜单 -->
    	</c:forEach>
	   
	</ul>
  </div>
  <div class="ri-lis-search">
    <table class="tableStyle tableStyleWordWrap tab-hei-30">
	  <tr class="odd selected">
	    <td style="text-align: right; padding-top: 3px; padding-bottom: 3px;">报表编号：</td>
		<td style="text-align: right; padding-top: 3px; padding-bottom: 3px;"><input id="tableCode" name="tableCode" class="textinput"></td>
		<td style="text-align: right; padding-top: 3px; padding-bottom: 3px;">
		  <button class="button" onclick="getReportUrl($('#tableCode').val())" type="button" style="padding-left: 5px; min-width: auto;">
		    <span class="icon_find" style="font-family: 宋体; font-size: 12px; cursor: default;">查询</span>
		  </button>
		</td>
	  </tr>
	</table>
  </div>
</div>
<script src="js/jquery.min.js"></script>
<script>
	  var Hei=$(window).height();
	  var Wid=$(window).width();
	  var H=Hei;
	  $(".le-lis-menu").css("height",H +"px");
	  $(".ri-lis-search").css("height",H +"px");
	  
	function getReportUrl(ReportCode){
		if (ReportCode.substring(0,1)!="0"&& ReportCode<10) {
			ReportCode="0"+ReportCode;
		}
		if ($("#REPORT-"+ReportCode).attr("href")!= undefined) {
			window.location.href =$("#REPORT-"+ReportCode).attr("href");
		}else {
			top.Dialog.alert("报表编号错误或没有查看此报表的权限");
		}
	
	}  
	  
	  
</script>
</body>

</html>