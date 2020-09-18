<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head >
		<title></title>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
<body>
<form id="form1" >
	<div class="basicTabModern">
		<div name="发起确认请求" style="width:100%;height:300px;">
   				<table class="tableStyle" width="80%" mode="list" formMode="line">
   					<tr>
						<td align="left">
							交易时间:
						</td>
						<td>
						${appDataMap.tradeDate1}
						</td>
					</tr>
   					<tr>
						<td align="left" width="25%">
							产品代码:
						</td>
						<td width="25%">
							${appDataMap.prodCode}
						</td>
					</tr>
					<tr>
						<td align="left" width="25%">
							产品名称:
						</td>
						<td width="25%">
							${appDataMap.prodName}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款类型:
						</td>
						<td>
						${appDataMap.tradeKindName}
						</td>
					</tr>
					<tr>
						<td align="left">
							金额(元):
						</td>
						<td>
						${appDataMap.sumAmt}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款方账号:
						</td>
						<td>
						${appDataMap.outAcctCode}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款方账户:
						</td>
						<td>
						${appDataMap.outAcctName}
						</td>
					</tr>
					<tr>
						<td align="left">
							收款方账号:
						</td>
						<td>
						${appDataMap.inAcctCode}
						</td>
					</tr>
					<tr>
						<td align="left">
							收款方账户:
						</td>
						<td>
						${appDataMap.inAcctName}
						</td>
					</tr>
					
   				</table>
			</div>
	<div name="复核信息" style="width:100%;height:240px;">
				<input type="hidden" name="appType" value="${webReviewMain.appType }"/>
				<input type="hidden" name="appNo" value="${webReviewMain.appNo }"/>
				<input type="hidden" name="tradeDate1" value="${appDataMap.tradeDate1}"/>
				<input type="hidden" name="prodCode" value="${appDataMap.prodCode}"/>
				<input type="hidden" name="tradeKindCode" value="${appDataMap.tradeKindCode}"/>
				<input type="hidden" name="custType" value="${appDataMap.custType}"/>
				<input type="hidden" name="accountingFlag" value="${appDataMap.accountingFlag}"/>
				<table class="tableStyle" width="97%" mode="list" formMode="line" fixedCellHeight="true">
					<tr>
						<td width="38%">
							申请人员代码：
						</td>
						<td>
							${requestScope.webReviewMain.appUser}
						</td>
					</tr>
					<tr>
						<td width="38%">
							申请人员姓名：
						</td>
						<td>
							${requestScope.webReviewMain.appOperName}
						</td>
					</tr>
					<tr>
						<td>
							所属机构代码：
						</td>
						<td>
							${requestScope.webReviewMain.appOrganCode}
						</td>
					</tr>
					<tr>
						<td>
							所属机构名称：
						</td>
						<td>
							${requestScope.webReviewMain.appOrganName}
						</td>
					</tr>
					<tr>
						<td>
							申请人员角色：
						</td>
						<td>
							${requestScope.webReviewMain.appRoleName}
						</td>
					</tr>
					<tr>
						<td>申请时间：</td>
						<td>${requestScope.webReviewMain.appDate}</td>
					</tr>
					<tr>
						<td>申请说明：</td>
						<td class="text_break">
							${requestScope.webReviewMain.appRemark}
						</td>
					</tr>
					
					<tr>
						<td>
							复核人员代码：
						</td>
						<td>
							${requestScope.webReviewMain.repUserCode}
						</td>
					</tr>
					<tr>
						<td>
							复核人员姓名：
						</td>
						<td>
							${requestScope.webReviewMain.repUserName}
						</td>
					</tr>
					<tr>
						<td>
							所属机构代码：
						</td>
						<td>
							${requestScope.webReviewMain.repUserOrganCode}
						</td>
					</tr>
					<tr>
						<td>
							所属机构名称：
						</td>
						<td>
							${requestScope.webReviewMain.repUserOrganName}
						</td>
					</tr>
					
					<tr>
						<td>
							复核人员角色：
						</td>
						<td>
							${requestScope.webReviewMain.repRoleName}
						</td>
					</tr>
					<tr>
						<td>
							操作：
						</td>
						<td>
							<dic:radio dicType="REP_STATUS" name="repStatus" tgClass="validate[required]"></dic:radio>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>复核说明：</td>
						<td>
							<textarea class="textarea" style="width:80%;height:80px;" name="repRemark"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<div align="center">
								<button type="button" onclick="return doSubmit('form1','<%=path%>/taSkNotifyInfo/doApproval.htm')" class="saveButton"/>
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
