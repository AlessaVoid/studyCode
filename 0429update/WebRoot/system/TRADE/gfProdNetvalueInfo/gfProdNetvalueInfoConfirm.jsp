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
							<td align="right" width="38%" >
								产品代码:
							</td>
							<td>
							${entity.prodCode}
							<input type="hidden" name="prodCode" value="${entity.prodCode}" />
							</td>
						</tr>
						<tr>
							<td align="right">
								净值日期:
							</td>
							<td>
								${entity.netvalueDate}
								<input type="hidden" name="netvalueDate" value="${entity.netvalueDate}" />
							</td>
						</tr>
						<tr>
							<td align="right">
								净值:
							</td>
							<td>
								${entity.netvalue}
							</td>
						</tr>
						<tr>
							<td align="right">
								年化收益率(%):
							</td>
							<td>
								<fmt:out fmtvalue="${entity.profitRate}" fmtclass="money"></fmt:out>	 
							</td>
						</tr>
						<tr>
							<td align="right">
								万份收益(元):
							</td>
							<td>
								${entity.tenThousandthProfit}
							</td>
						</tr>
						<tr>
							<td align="right">
								成立以来年化(%):
							</td>
							<td>
								<fmt:out fmtvalue="${entity.latestProFitRate}" fmtclass="money"></fmt:out>	 
							</td>
						</tr>
						<tr>
							<td align="right">
								状态:
							</td>
							<td>
								<dic:out dicType="VALID_STATUS" dicNo="${entity.status}"></dic:out>
								<input type="hidden" name="status" value="${entity.status}" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
 									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfProdNetvalueInfo/updateConfirm.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>