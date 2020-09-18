<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>预约购买参数</title> 
	</head>
	<body>
	<form id="form1">
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="19%">预约起始日：</td>
				<td width="31%">
					${entity.orderBeginDate }
				</td>
				<td align="right" width="19%">预约终止日：</td>
				<td width="31%">
					${entity.orderEndDate }
				</td>
			</tr>
			<tr>
				<td align="right" width="19%">预约最终有效日：</td>
				<td width="31%">
					${entity.orderFinalValidityDate }
				</td>
				<td align="right" width="19%">预约额度回收时间：</td>
				<td width="31%">
					${entity.orderTakeBackTime }
				</td>
			</tr>
		</table>
   	</form>
</body>
</html>