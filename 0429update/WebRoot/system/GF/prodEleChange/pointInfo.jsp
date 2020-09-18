<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>积分参数</title> 
	</head>
	<body>
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="19%">购买积分限额：</td>
				<td width="31%">
					${entity.buyPointQuot }
				</td>
				<td align="right" width="19%">预约积分限额：</td>
				<td width="31%">
					${entity.orderPointQuot }
				</td>
			</tr>
		</table>
	</body>
</html>