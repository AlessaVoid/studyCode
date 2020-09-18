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
		<div name="划款申请单">
		<table class="tableStyle" useMultColor="true" mode="list">
			<tr >
				<td colspan="4" align="center">
					<h1>(${entity.tradeKindName})划款申请单</h1>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="right">
				<fm:formatDate	value="${entity.tradeDate}" pattern="yyyy年MM月dd日" />
				</td>
			</tr>
			<tr>
				<td align="right">产品代码：</td>
				<td>${entity.prodCode}</td>
				<td align="right">产品名称：</td>
				<td>${entity.prodName}</td>
			</tr>
			<tr>
				<td align="right">产品成立日：</td>
				<td>${entity.prodBeginDate}</td>
				<td align="right">产品到期日：</td>
				<td>${entity.prodEndDate}</td>
			</tr>
			<tr>
				<td  align="right">本金：</td>
				<td>
				<fm:formatNumber value="${entity.cost}"	type="currency" />
				</td>
				<td align="right">收益：</td>
				<td>
				<fm:formatNumber value="${entity.profit}"	type="currency" />
				</td>
			</tr>
			<tr>
				<td align="right">总金额（小写）：</td>
				<td colspan="3"><fm:formatNumber value="${entity.sumAmt}"
						type="currency" /></td>
			</tr>
			<tr>
				<td align="right">总金额（大写）：</td>
				<td colspan="3"><fmt:formatMoneyUp fmtValue="${entity.sumAmt}" fmtClass="width:100%;"
						id="moneyUP" /></td>
			</tr>
			<tr>
				<td align="right">附言：</td>
				<td colspan="3">${entity.prodName}(${entity.tradeKindName})</td>
			</tr>
		</table>
		</div>
		<div name="复核信息" style="width:100%;height:80px;">
			<input type="hidden" name="operDescribe" value="(${entity.tradeKindName})到账确认" />
			<input type="hidden" name="appType" value="1" />
			<input type="hidden" name="prodCode" value="${entity.prodCode}" />
			<input type="hidden" name="tradeDate1" value="${entity.tradeDate1}" />
			<input type="hidden" name="tradeKindCode" value="${entity.tradeKindCode}" />
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
					<td>${sessionScope.sessionUser.opername} 
					<input	type="hidden" name="appOperName" value="${sessionScope.sessionUser.opername}" />
					</td>
				</tr>
				<tr>
					<td>所属机构代码：</td>
					<td>${sessionScope.sessionUser.organcode} 
					<input type="hidden" name="appOrganCode" value="${sessionScope.sessionUser.organcode}" /></td>
				</tr>
				<tr>
					<td>所属机构名称：</td>
					<td>${sessionScope.sessionOrgan.organname} 
					<input	type="hidden" name="appOrganName" value="${sessionScope.sessionOrgan.organname}" />
					</td>
				</tr>
				<tr>
					<td>操作员角色：</td>
					<td>${sessionScope.sessionUserRole} 
					<input type="hidden" name="appRoleName" value="${sessionScope.sessionUserRole}" />
					</td>
				</tr>
				<tr>
					<td>复核人员：</td>
					<td><select selWidth="127" id="repUserCode" name="repUserCode" prompt="--请选择--" class="validate[required]"	url="<%=path%>/fdOper/getHeadOfficeAppUserList.htm?funCode=AC-01-02"></select> <span class="star">*</span> 
					<input type="hidden" name="repUserName" id="repUserName"/></td>
				</tr>
				
				<tr>
					<td>所属机构代码：</td>
					<td><span id="repUserOrganCode1"></span> 
					<input type="hidden" id="repUserOrganCode" name="repUserOrganCode"/>
					</td>
				</tr>
				<tr>
					<td>所属机构名称：</td>
					<td><span id="repUserOrganName1"></span> 
					<input type="hidden" id="repUserOrganName" name="repUserOrganName"/>
						</td>
				</tr>

				<tr>
					<td>复核人员角色：</td>
					<td><span id="repRoleName1"></span> 
					<input type="hidden" id="repRoleName" name="repRoleName"/>
					</td>
				</tr>
				<tr>
					<td>备注说明：</td>
					<td><textarea class="textarea" style="width: 80%; height: 80px;" name="appRemark"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return doSubmit('form1','<%=path%>/acNotifyConfirm/doApply.htm')" class="saveButton"/>
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
