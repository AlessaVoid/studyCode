<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
				<script type="text/javascript">
$(function(){
	top.frmright.setSize(600, 580);
	changeCert();
});

function changeCert(){
	var outCustType = $("#outCustType").val();
	if(outCustType == '1'){//个人
		$("#corpDiv").hide();
		$("#custDiv").show();
		$("#corp").removeAttr("name");
		$("#corp").removeAttr("class");
	}else if(outCustType == '2'){//机构
		$("#corpDiv").show();
		$("#custDiv").hide();
		$("#cust").removeAttr("name");
		$("#cust").removeAttr("class");
	}
	$("#corpDiv").render();
	$("#custDiv").render();
	$("#corp").render();
	$("#cust").render();
}
</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" id="applicationCode" name="applicationCode" value="${appDataMap.applicationCode}"/>
		<div class="basicTabModern">
				<div name="TA非交易过户(司法)" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
						<tr>
							<td align="right" width="38%">
								投资者类型：
							</td>
							<td>
								<dic:out dicNo="${appDataMap.outCustType}" dicType="D_CUST_TYPE"></dic:out>
								<input type="hidden" id="outCustType" name="outCustType" value="${appDataMap.outCustType}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件类型：
							</td>
							<td>
								<div id="custDiv">
									<dic:out dicNo="${appDataMap.outCustCertType}" dicType="D_CUST_CERT_TYPE"></dic:out>
								</div>
								<div id="corpDiv">
									<dic:out dicNo="${appDataMap.outCustCertType}" dicType="D_CORP_CERT_TYPE"></dic:out>
								</div>
								<input type="hidden" name="outCustCertType" id="outCustCertType" value="${appDataMap.outCustCertType}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件号码：
							</td>
							<td>
								${appDataMap.outCustCertCode}
								<input type="hidden" name="outCustCertCode" value="${appDataMap.outCustCertCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								客户名称：
							</td>
							<td>
								${appDataMap.outCustName}
								<input type="hidden" id="outCustName" name="outCustName" value="${appDataMap.outCustName}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品代码：
							</td>
							<td>
								${appDataMap.prodCode}
								<input type="hidden" id="prodCode" name="prodCode" value="${appDataMap.prodCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商代码：
							</td>
							<td>
								${appDataMap.retailerCode}
								<input type="hidden" name="retailerCode" value="${appDataMap.retailerCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								交易类型：
							</td>
							<td>
								<dic:out dicType="D_TRANS_TYPE" dicNo="${appDataMap.transType}"></dic:out>
								<input type="hidden" id="transType" name="transType" value="${appDataMap.transType}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方投资类型：
							</td>
							<td>
								<dic:out dicNo="${appDataMap.inCustType}" dicType="D_CUST_TYPE"></dic:out>
								<input type="hidden" id="inCustType" name="inCustType" value="${appDataMap.inCustType}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方证件类型：
							</td>
							<td>
								<div id="custDiv">
									<dic:out dicNo="${appDataMap.inCustCertType}" dicType="D_CUST_CERT_TYPE"></dic:out>
								</div>
								<div id="corpDiv">
									<dic:out dicNo="${appDataMap.inCustCertType}" dicType="D_CORP_CERT_TYPE"></dic:out>
								</div>
								<input type="hidden" id="inCustCertType" name="inCustCertType" value="${appDataMap.inCustCertType}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方证件号码：
							</td>
							<td>
								${appDataMap.inCustCertCode}
								<input type="hidden" name="inCustCertCode" value="${appDataMap.inCustCertCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方客户名称：
							</td>
							<td>
								${appDataMap.inCustName}
								<input type="hidden" name="inCustName" value="${appDataMap.inCustName}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方销售商代码：
							</td>
							<td>
								${appDataMap.inRetailerCode}
								<input type="hidden" name="inRetailerCode" value="${appDataMap.inRetailerCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								可过户份额：
							</td>
							<td>
								${appDataMap.quotientavailable}
								<input type="hidden" name="quotientavailable" value="${appDataMap.quotientavailable}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								过户份额：
							</td>
							<td>
								${appDataMap.transferQuotient}
								<input type="hidden" name="transferQuotient" value="${appDataMap.transferQuotient}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								过户原因：
							</td>
							<td>
								${appDataMap.transferReason}
								<input type="hidden" name="transferReason" value="${appDataMap.transferReason}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人姓名：
							</td>
							<td>
								${appDataMap.handleName}
								<input type="hidden" name="handleName" value="${appDataMap.handleName}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件类型：
							</td>
							<td>
								<dic:out dicType="D_CUST_CERT_TYPE" dicNo="${appDataMap.handleCertType}"/>
								<input type="hidden" name="handleCertType" value="${appDataMap.handleCertType}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件号码：
							</td>
							<td>
								${appDataMap.handleCertCode}
								<input type="hidden" name="handleCertCode" value="${appDataMap.handleCertCode}"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="button" class="firstButton" />
								<button type="button" class="downButton" />
							</td>
						</tr>
    				</table>
    			</div>
    			<div name="复核信息" style="width:100%;height:240px;">
					<input type="hidden" name="appType" value="${webReviewMain.appType }"/>
					<input type="hidden" name="appNo" value="${webReviewMain.appNo }"/>
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
							<td>
								${requestScope.webReviewMain.appDate}
								<input type="hidden" name="appDate" value="${webReviewMain.appDate }"/>
								<input type="hidden" name="appTime" value="${webReviewMain.appTime }"/>
							</td>
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
								<input type="hidden" name="repUserCode" value="${webReviewMain.repUserCode }"/>
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
								<button type="button" onclick="return doSubmit('form1','<%=path%>/gfNoTradeTransferInfo/doApproval.htm')" class="saveButton"/>
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