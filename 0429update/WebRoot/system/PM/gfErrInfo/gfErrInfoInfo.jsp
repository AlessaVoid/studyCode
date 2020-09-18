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
						本系统错误码：
					</td>
					<td>
						${entity.gfRetCode }
					</td>
				</tr>
				<tr>
					<td align="right">
						本系统错误信息：
					</td>
					<td>
						${entity.gfRetInfo }
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						外系统代码：
					</td>
					<td>
						${entity.otherSysCode }
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						外系统错误码：
					</td>
					<td>
						${entity.otherRetCode }
					</td>
				</tr>
				<tr>
					<td align="right">
						外系统错误信息：
					</td>
					<td>
						${entity.otherRetInfo }
					</td>
				</tr>
				<tr>
					<td align="right">
						创建人：
					</td>
					<td>
						${entity.latestOperCode }
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>