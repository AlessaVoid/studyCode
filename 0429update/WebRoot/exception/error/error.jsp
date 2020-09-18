<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/common_info.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>错误界面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<style type="text/css">
#__01 {
	position: absolute;
	left: 0px;
	top: 0px;
	width: 900px;
	height: 550px;
}

#id-----01_ {
	position: absolute;
	left: 0px;
	top: 0px;
	width: 900px;
	height: 287px;
}

#id-----02_ {
	position: absolute;
	left: 0px;
	top: 287px;
	width: 450px;
	height: 263px;
}

#id-----03_ {
	position: absolute;
	left: 450px;
	top: 287px;
	width: 346px;
	height: 172px;
}

#id-----04_ {
	position: absolute;
	left: 796px;
	top: 287px;
	width: 104px;
	height: 263px;
}

#id-----05_ {
	position: absolute;
	left: 450px;
	top: 459px;
	width: 346px;
	height: 91px;
}
</style>
		<!-- End ImageReady Styles -->
	</head>
	<body style="background-color: #FFFFFF;">
		<!-- ImageReady Slices (错误界面.psd) -->
		<div id="__01">
			<div id="id-----01_">
				<img id="_____01"
					src="<%=request.getContextPath()%>/exception/error/errorPage_01.png"
					width="900" height="287" alt="" />
			</div>
			<div id="id-----02_">
				<img id="_____02"
					src="<%=request.getContextPath()%>/exception/error/errorPage_02.png"
					width="450" height="263" alt="" />
			</div>
			<div id="id-----03_">
				<font font face="楷体">${ex}</font>
				<img id="_____03"
					src="<%=request.getContextPath()%>/exception/error/errorPage_03.png"
					width="346" height="172" alt="" />
			</div>
			<div id="id-----04_">
				<img id="_____04"
					src="<%=request.getContextPath()%>/exception/error/errorPage_04.png"
					width="104" height="263" alt="" />
			</div>
			<div id="id-----05_">
				<img id="_____05"
					src="<%=request.getContextPath()%>/exception/error/errorPage_05.png"
					width="346" height="91" alt="" />
			</div>
		</div>
		<!-- End ImageReady Slices -->
	</body>
</html>