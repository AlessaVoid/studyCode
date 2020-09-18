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
							<td align="right">
								产品托管子账户账号：
							</td>
							<td>
								<input type="text" name="trusteeshipAcct" style="width:300px" class="validate[required],custom[noSpecialCaracters]" maxlength="30"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品托管子账户名称：
							</td>
							<td>
								<input type="text" style="width:300px" name="trusteeshipAcctName"  class="validate[required]" maxlength="50"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品托管账户名称：
							</td>
							<td>
								<input type="text" name="trusteeshipName" style="width:300px" value="中国邮政储蓄银行股份有限公司理财户"  maxlength="50"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品托管账户帐号：
							</td>
							<td>
								<input type="text" name="trusteeshipCode" style="width:300px"  class="validate[custom[noSpecialCaracters]]" maxlength="30"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品代码：
							</td>
							<td>
								<input type="text" name="prodCode" class="validate[custom[noSpecialCaracters]]" style="width:300px" maxlength="10"/>
							</td>
						</tr>
	   					<tr>
							<td align="right">
								产品名称：
							</td>
							<td>
								<input type="text" name="prodName"  style="width:300px" class="validate[required]" maxlength="50"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								开户银行行号：
							</td>
							<td>
								<input type="text" name="openBranchCode" value="403100005223" style="width:300px" class="validate[required],custom[noSpecialCaracters]" maxlength="30"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								开户银行名称：
							</td>
							<td>
								<input type="text" name="openBranchName" style="width:300px" value="中国邮政储蓄银行股份有限公司北京金融街支行" class="validate[required]" maxlength="50"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfTrusteeshipAcct/insert.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>