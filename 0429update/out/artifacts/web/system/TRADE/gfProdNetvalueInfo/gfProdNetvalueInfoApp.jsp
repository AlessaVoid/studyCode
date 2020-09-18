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
				<div name="产品净值复核" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
						<tr>
							<td align="right" width="38%">
								产品代码:
							</td>
							<td>
								${appDataMap.prodCode}
								<input type="hidden" name="prodCode" id="prodCode" value="${appDataMap.prodCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								净值日期:
							</td>
							<td>
								${appDataMap.netvalueDate}
								<input type="hidden" name="netvalueDate" id="netvalueDate" value="${appDataMap.netvalueDate}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								净值:
							</td>
							<td>
								${appDataMap.netvalue}
								<input type="hidden" name="netvalue" id="netvalue" value="${appDataMap.netvalue}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								年化收益率(%):
							</td>
							<td>
								${appDataMap.profitRate}
								<input type="hidden" name="profitRate" id="profitRate" value="${appDataMap.profitRate}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								万份收益(元):
							</td>
							<td>
								${appDataMap.tenThousandthProfit}
								<input type="hidden" name="tenThousandthProfit" id="tenThousandthProfit" value="${appDataMap.tenThousandthProfit}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								成立以来年化(%):
							</td>
							<td>
								${appDataMap.latestProFitRate}
								<input type="hidden" name="latestProFitRate" id="latestProFitRate" value="${appDataMap.latestProFitRate}"/>
								<input type="hidden" name="countFlag" id="countFlag" value="${appDataMap.countFlag}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								状态:
							</td>
							<td>
								<input type="hidden" name="status" id="status" value="${appDataMap.status}" />
								<dic:out dicType="VALID_STATUS" dicNo="${appDataMap.status}" ></dic:out>
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
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfProdNetvalueInfo/doApproval.htm')" class="saveButton"/>
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