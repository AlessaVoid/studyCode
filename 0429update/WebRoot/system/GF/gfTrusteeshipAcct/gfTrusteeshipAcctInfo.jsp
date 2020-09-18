<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	</head>
	
	<body>
		<form id="form1">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="30%">
								产品托管子账户账号：
							</td>
							<td>
								${entity.trusteeshipAcct}
							</td>
						</tr>
						<tr>
							<td align="right">
								产品托管子账户名称：
							</td>
							<td>
								${entity.trusteeshipAcctName}
							</td>
						</tr>
						<tr>
							<td align="right">
								产品托管账户帐号：
							</td>
							<td>
								${entity.trusteeshipCode}
							</td>
						</tr>
						<tr>
							<td align="right">
								产品托管账户名称：
							</td>
							<td>
								${entity.trusteeshipName}
							</td>
						</tr>
						
						<tr>
							<td align="right">
								产品代码：
							</td>
							<td>
								${entity.prodCode}
							</td>
						</tr>
	   					<tr>
							<td align="right">
								产品名称：
							</td>
							<td>
								${entity.prodName}
							</td>
						</tr>
						<tr>
							<td align="right">
								开户银行行号：
							</td>
							<td>
								${entity.openBranchCode}
							</td>
						</tr>
						<tr>
							<td align="right">
								开户银行名称：
							</td>
							<td>
								${entity.openBranchName}
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>