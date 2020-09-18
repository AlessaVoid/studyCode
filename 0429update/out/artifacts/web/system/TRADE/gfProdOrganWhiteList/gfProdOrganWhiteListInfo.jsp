<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	</head>
	<body>
		<form id="form1">
  				<table class="tableStyle" width="80%" mode="list" formMode="line">
				<tr>
					<td align="right" width="38%">
						产品代码:
					</td>
					<td>
						${entity.prodCode }
					</td>
				</tr>
				<tr>
					<td align="right">
						机构代码:
					</td>
					<td>
					${entity.organCode }
					</td>
				</tr>
				<tr>
					<td align="right">
						机构级别:
					</td>
					<td>
					
					<dic:out dicType="D_ORGAN_LEVEL" dicNo="${entity.organLevel}"></dic:out>
					</td>
				</tr>
				<tr>
					<td align="right">
						状态 :
					</td>
					<td>
					<dic:out dicType="USE_STATUS" dicNo="${entity.status}"></dic:out>
					</td>
				</tr>
  				</table>
	   	</form>
	</body>
</html>