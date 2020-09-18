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
	   				<table class="tableStyle" width="100%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="25%">
								分组英文名：
							</td>
							<td width="25%" >
								${dict.dictNo}
							</td>
							<td align="right" width="20%">
								分组中文名：
							</td>
							<td width="30%">
								${dict.dictName}
							</td>
						</tr>
						<tr>
							<td align="right">
								键-内部 ：
							</td>
							<td>
								${dict.dictKeyIn}
							</td>
							<td align="right">
								值-内部：
							</td>
							<td>
								${dict.dictValueIn}
							</td>
						</tr>
						<tr>
							<td align="right">
								键-外部：
							</td>
							<td>
								${dict.dictKeyOut}
							</td>
							<td align="right">
								值-外部 ：
							</td>
							<td>
								${dict.dictValueOut}
							</td>
						</tr>
						<tr>
							<td align="right">
								排序：
							</td>
							<td>
								${dict.dictNoOrder}
							</td>
							<td align="right">
								内容描述：
							</td>
							<td>
								${dict.dictDesc}
							</td>
						</tr>
						<tr>
							<td align="right">
								参数状态：
							</td>
							<td>
								<dic:out dicType="STATUS" dicNo="${dict.status}"></dic:out>
							</td>
							<td >
								创建人员：
							</td>
							<td>
								${dict.createOper}
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>