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
					<h1>(${appDataMap.tradeKindName})划款申请单</h1>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="right">
				${appDataMap.tradeDate1}
				</td>
			</tr>
			<tr>
				<td align="right">产品代码：</td>
				<td>${appDataMap.prodCode}</td>
				<td align="right">产品名称：</td>
				<td>${appDataMap.prodName}</td>
			</tr>
			<tr>
				<td align="right">产品成立日：</td>
				<td>${appDataMap.prodBeginDate}</td>
				<td align="right">产品到期日：</td>
				<td>${appDataMap.prodEndDate}</td>
			</tr>
			<tr>
				<td  align="right">本金：</td>
				<td>
				<fm:formatNumber value="${appDataMap.cost}"	type="currency" />
				</td>
				<td align="right">收益：</td>
				<td>
				<fm:formatNumber value="${appDataMap.profit}"	type="currency" />
				</td>
			</tr>
			<tr>
				<td align="right">总金额（小写）：</td>
				<td colspan="3"><fm:formatNumber value="${appDataMap.sumAmt}"
						type="currency" /></td>
			</tr>
			<tr>
				<td align="right">总金额（大写）：</td>
				<td colspan="3"><fmt:formatMoneyUp fmtValue="${appDataMap.sumAmt}" fmtClass="width:100%;"
						id="moneyUP" /></td>
			</tr>
			<tr>
				<td align="right">附言：</td>
				<td colspan="3">${appDataMap.prodName}(${appDataMap.tradeKindName})</td>
			</tr>
		</table>
		</div>
	<div name="复核信息" style="width:100%;height:240px;">
				<input type="hidden" name="appType" value="${webReviewMain.appType }"/>
				<input type="hidden" name="appNo" value="${webReviewMain.appNo }"/>
				<input type="hidden" name="batchSerial" value="${appDataMap.batchSerial }"/>
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
								<button type="button" onclick="return doSubmit('form1','<%=path%>/acCostAccConfirm/doApproval.htm')" class="saveButton"/>
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
