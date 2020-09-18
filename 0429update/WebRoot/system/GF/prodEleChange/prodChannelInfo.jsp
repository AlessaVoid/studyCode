<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>发售销售渠道参数</title> 
	</head>
	<body>
	<form id="form1">
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
		<tr>
			<td align="right" width="38%">产品销售渠道：</td>
			<td>
				${selectedValue}
			</td>
		</tr>
		</table>
   	</form>
	</body>
</html>