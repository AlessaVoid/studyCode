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
	var customerkind = $("#customerkind").val();
	if(customerkind == '1'){//个人
		$("#corpDiv").hide();
		$("#custDiv").show();
		$("#corp").removeAttr("name");
		$("#corp").removeAttr("class");
	}else if(customerkind == '2'){//机构
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
		<input type="hidden" name="retailerCode" value="${appDataMap.retailerCode}">
		<div class="basicTabModern">
				<div name="TA账户冻结" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="38%">
								投资者类型：
							</td>
							<td>
								<dic:out dicNo="${appDataMap.customerkind}" dicType="D_CUST_TYPE"></dic:out>
								<input type="hidden" name="customerkind" id="customerkind" value="${appDataMap.customerkind}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								证件类型：
							</td>
							<td>
								<div id="custDiv" >
									<dic:out dicNo="${appDataMap.certificatekind}" dicType="D_CUST_CERT_TYPE"></dic:out>
								</div>
								<div id="corpDiv">
									<dic:out dicNo="${appDataMap.certificatekind}" dicType="D_CORP_CERT_TYPE"></dic:out>
								</div>
								<input type="hidden" name="certificatekind" id="certificatekind" value="${appDataMap.certificatekind}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								证件号码：
							</td>
							<td>
								${appDataMap.certificatecode}
								<input type="hidden" name="certificatecode" id="certificatecode" value="${appDataMap.certificatecode}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								客户名称：
							</td>
							<td>
								${appDataMap.customername}
								<input type="hidden" name="customername" id="customername" value="${appDataMap.customername}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								冻结原因:
							</td>
							<td>
								${appDataMap.freezeReason}
								<input type="hidden" name="freezeReason" id="freezeReason" value="${appDataMap.freezeReason}" />
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								冻结截止日期:
							</td>
							<td>
								${appDataMap.autoUnfreezeDate}
								<input type="hidden" name="autoUnfreezeDate" id="autoUnfreezeDate" value="${appDataMap.autoUnfreezeDate}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								冻结法律文书号:
							</td>
							<td>
								${appDataMap.freezeJudicialFileCode}
								<input type="hidden" name="freezeJudicialFileCode" id="freezeJudicialFileCode" value="${appDataMap.freezeJudicialFileCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								司法经办人姓名:
							</td>
							<td>
								${appDataMap.handleName}
								<input type="hidden" name="handleName" id="handleName" value="${appDataMap.handleName}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								司法经办人证件类型:
							</td>
							<td>
								<dic:out dicType="D_CUST_CERT_TYPE" dicNo="${appDataMap.handleCertType}"></dic:out>
								<input type="hidden" name="handleCertType" id="handleCertType" value="${appDataMap.handleCertType}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								司法经办人证件号码:
							</td>
							<td>
								${appDataMap.handleCertCode}
								<input type="hidden" name="handleCertCode" id="handleCertCode" value="${appDataMap.handleCertCode}"/>
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
								<button type="button" onclick="return doSubmit('form1','<%=path%>/taAcctFreezeDTO/doApproval.htm')" class="saveButton"/>
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