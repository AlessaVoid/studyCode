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
				<div name="销售商新增复核" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								销售商代码：
							</td>
							<td>
								<input type="text" name="retailerCode" value="${appDataMap.retailerCode}" class="validate[required],custom[noSpecialCaracters]" maxlength="9"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商名称：
							</td>
							<td>
								<input type="text" name="retailerName" value="${appDataMap.retailerName}" class="validate[required]" maxlength="30"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商全称：
							</td>
							<td>
								<input type="text" name="retailerAllName" value="${appDataMap.retailerAllName}" class="validate[required]" maxlength="50"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商英文名：
							</td>
							<td>
								<input type="text" name="retailerEnName" value="${appDataMap.retailerEnName}" class="validate[required],custom[onlyLetter]" maxlength="100"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商类型:
							</td>
							<td>
								<dic:select dicType="retailer_type" dicNo="${appDataMap.retailerType}" id="retailerType" name="retailerType" tgClass="validate[required]"></dic:select><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								传真号码:
							</td>
							<td>
								<input type="text" name="fax" value="${appDataMap.fax}" class="validate[required]"  maxlength="20"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								联系人姓名:
							</td>
							<td>
								<input type="text" name="contactPersonName" value="${appDataMap.contactPersonName}" class="validate[required]" maxlength="10"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								联系人电话:
							</td>
							<td>
								<input type="text" name="contactPersonTell" value="${appDataMap.contactPersonTell}" class="validate[required]" maxlength="20"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								电子邮件:
							</td>
							<td>
								<input type="text" name="email" value="${appDataMap.email}" class="validate[required],custom[email]" maxlength="50"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								通讯地址:
							</td>
							<td>
								<input type="text" name="contactAddress" value="${appDataMap.contactAddress}" class="validate[required]" maxlength="100"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								状态:
							</td>
							<td>
								<dic:select dicType="RETAILER_STATUS" dicNo="${appDataMap.status}" id="status" name="status" tgClass="validate[required]"></dic:select><span class="star">*</span>
							</td>
						</tr>
						<tr>
									<td colspan="2">
										<button type="button" class="firstButton" />
										<button type="button" class="downButton" /></td>
							</tr>
    				</table>
    			</div>
    			<div name="复核信息" style="width:100%;height:80px;">
					<div style="width: 98%">
						<input type="hidden" name="operDescribe" value="销售商管理" /> 
						<input type="hidden" name="appNo" value="${webReviewMain.appNo }" /> 
						<input type="hidden" name="tradeCode" value="TA-01-01" />
						<table class="tableStyle" width="97%" mode="list" formMode="line">
							<tr>
								<td width="38%">操作员代码：</td>
								<td>
									${sessionScope.sessionUser.opercode} 
									<input type="hidden" name="appUser" value="${sessionScope.sessionUser.opercode}" />
								</td>
							</tr>
							<tr>
								<td width="38%">操作员姓名：</td>
								<td>${sessionScope.sessionUser.opername} <input
									type="hidden" name="appOperName"
									value="${sessionScope.sessionUser.opername}" /></td>
							</tr>
							<tr>
								<td>所属机构代码：</td>
								<td>${sessionScope.sessionUser.organcode} <input
									type="hidden" name="appOrganCode"
									value="${sessionScope.sessionUser.organcode}" /></td>
							</tr>
							<tr>
								<td>所属机构名称：</td>
								<td>${sessionScope.sessionOrgan.organname} <input
									type="hidden" name="appOrganName"
									value="${sessionScope.sessionOrgan.organname}" /></td>
							</tr>
							<tr>
								<td>操作员角色：</td>
								<td>${sessionScope.sessionUserRole} <input type="hidden"
									name="appRoleName" value="${sessionScope.sessionUserRole}" /></td>
							</tr>
							<tr>
								<td>复核人员：</td>
								<td><select selWidth="127" 
									id="repUserCode" name="repUserCode" prompt="--请选择--" class="validate[required]"
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=TA-01-05">
								</select> <span class="star">*</span> 
								<input type="hidden" name="repUserName" id="repUserName"/></td>
							</tr>
							
							<tr>
								<td>所属机构代码：</td>
								<td><span id="repUserOrganCode1"></span> <input type="hidden"
									id="repUserOrganCode" name="repUserOrganCode"/></td>
							</tr>
							<tr>
								<td>所属机构名称：</td>
								<td><span id="repUserOrganName1"></span> <input type="hidden"
									id="repUserOrganName" name="repUserOrganName"/></td>
							</tr>

							<tr>
								<td>复核人员角色：</td>
								<td><span id="repRoleName1"></span> <input type="hidden"
									id="repRoleName" name="repRoleName"/></td>
							</tr>
							<tr>
								<td>备注说明：</td>
								<td><textarea class="textarea"
										style="width: 80%; height: 80px;" name="appRemark"></textarea>
								</td>
							</tr>
							<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfRetailerBasicInfo/insert.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
						</table>
					</div>
				</div>
				</div>
	   	</form>
	</body>
</html>