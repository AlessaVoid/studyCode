<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
		<script type="text/javascript">
		function getOrganCodes(){
			return $("#selectTree1").attr("relValue");
		}
		</script>
</head>
	<body>
		<form action="" id="searchForm" method="post">
			 	  <table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
							<tr>
				<td width="12%" align="right">机构代码：</td>
				<td width="12%">
			   		<div class="selectTree"  url="<%=path%>/fdOrgan/getProvince.htm" multiMode="true" allSelectable="true" exceptParent="true" id="selectTree1" params='{"Dzqd":"1"}'></div>
								</td>
								</tr>
						</table>
			</form>
	</body>
</html>