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
		<div class="basicTabModern">
				<div name="销售商管理复核" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="38%">
								销售商代码：
							</td>
							<td  width="62%">
								${appDataMap.retailerCode}
								<input type="hidden" name="retailerCode" id="retailerCode" value="${appDataMap.retailerCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商名称：
							</td>
							<td>
								${appDataMap.retailerName}
								<input type="hidden" name="retailerName" id="retailerName" value="${appDataMap.retailerName}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商全称：
							</td>
							<td>
								${appDataMap.retailerAllName}
								<input type="hidden" name="retailerAllName" id="retailerAllName" value="${appDataMap.retailerAllName}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商英文名：
							</td>
							<td>
								${appDataMap.retailerEnName}
								<input type="hidden" name="retailerEnName" id="retailerEnName" value="${appDataMap.retailerEnName}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商类型:
							</td>
							<td>
								<dic:out dicType="retailer_type" dicNo="${appDataMap.retailerType}" ></dic:out>
								<input type="hidden" name="retailerType" id="retailerType" value="${appDataMap.retailerType}" />
							</td>
						</tr>
						<tr>
							<td align="right">
								传真号码:
							</td>
							<td>
								${appDataMap.fax}
								<input type="hidden" name="fax" id="fax" value="${appDataMap.fax}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								联系人姓名:
							</td>
							<td>
								${appDataMap.contactPersonName}
								<input type="hidden" name="contactPersonName" id="contactPersonName" value="${appDataMap.contactPersonName}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								联系人电话:
							</td>
							<td>
								${appDataMap.contactPersonTell}
								<input type="hidden" name="contactPersonTell" id="contactPersonTell" value="${appDataMap.contactPersonTell}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								电子邮件:
							</td>
							<td>
								${appDataMap.email}
								<input type="hidden" name="email" id="email" value="${appDataMap.email}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								通讯地址:
							</td>
							<td>
								${appDataMap.contactAddress}
								<input type="hidden" name="contactAddress" id="contactAddress" value="${appDataMap.contactAddress}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								状态:
							</td>
							<td>
								<input type="hidden" name="status" id="status" value="${appDataMap.status}" />
								<dic:out dicType="RETAILER_STATUS" dicNo="${appDataMap.status}" ></dic:out>
							</td>
						</tr>
						<tr>
									<td colspan="2">
										<button type="button" class="firstButton" />
										<button type="button" class="downButton" /></td>
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
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfRetailerBasicInfo/doApproval.htm')" class="saveButton"/>
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