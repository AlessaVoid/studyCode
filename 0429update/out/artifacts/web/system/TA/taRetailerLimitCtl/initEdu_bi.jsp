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
								产品代码：
							</td>
							<td>
								${entity.prodCode}
								<input type="hidden" name="prodCode" value="${entity.prodCode}" />
							</td>
						</tr>
						<tr>
							<td align="right">
								产品名称：
							</td>
							<td>
								${prodName}
								<input type="hidden" name="prodName"  value="${prodName}"/>
								<input type="hidden" name="paraType" value="${entity.paraType }"/>
								<input type="hidden" name="beginDate" value="${entity.beginDate }"/>
								<input type="hidden" name="endDate" value="${entity.endDate }"/>
								<input type="hidden" name="currency"  value="${entity.currency}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								币种：
							</td>
							<td>
							<c:choose>
							<c:when test="${entity.currency==826}">
								英镑
								<input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==156}">
							         人民币
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==978}">
							         欧元
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==840}">
							         美元
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==392}">
							         日元
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==036}">
							        澳大利亚元
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==702}">
							         新元
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==643}">
							         卢布
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==756}">
							         瑞士法郎
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==124}">
							         加元
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
							<c:when test="${entity.currency==344}">
							        港币
							   <input type="hidden" name="currency"  value="${entity.currency}"/>
							</c:when>
						    </c:choose>
							</td>
						</tr>
						<tr>
							<td align="right">
								计划发售额度(万)：
							</td>
							<td>
								${entity.limit}
							</td>
						</tr>
						<tr>
							<td align="right">
								初始化额度(万)：
							</td>
							<td>
								<input type="text" name="initEdu"  class="validate[required] money" value="${entity.limit}" maxlength="100"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/taRetailerLimitCtl/initEdu.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</tr>
    				</table>
	   	</form>
	</body>
</html>