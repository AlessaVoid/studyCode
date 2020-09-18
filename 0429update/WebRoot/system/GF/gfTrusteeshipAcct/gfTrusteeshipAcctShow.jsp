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
			<input type="hidden" id="fileName" name="fileName" value="${fileName}" />
			<div class="basicTabModern">
	   			<div name="导入托管账户信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
		   					<td width="10%">
		   						托管账户名称
		   					</td>
		   					<td width="6%">
		   						托管账户帐号
		   					</td>
		   					<td width="6%">
		   						开户银行行号
		   					</td>
		   					<td width="10%">
		   						开户银行名称
		   					</td>
		   					<td width="10%">
		   						产品名称
		   					</td>
		   					<td width="10%">
		   						产品托管子账户名称
		   					</td>
		   					<td width="8%">
		   						产品托管子账户帐号
		   					</td>
						</tr>
						<c:forEach items="${list}" var="index">
					<tr>
						<td >
							${index.trusteeshipName}
						</td>
						<td>
							${index.trusteeshipCode}
						</td>
						<td>
							${index.openBranchCode}
						</td>
						<td>
							${index.openBranchName}
						</td>
						<td>
							${index.prodName}
						</td>
						<td>
							${index.trusteeshipAcctName}
						</td>
						<td>
							${index.trusteeshipAcct}
						</td>
					</tr>
				</c:forEach>
						<tr>
							<td colspan="7">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfTrusteeshipAcct/gfTrusteeshipAcctRequest.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	   	</form>
	</body>
</html>