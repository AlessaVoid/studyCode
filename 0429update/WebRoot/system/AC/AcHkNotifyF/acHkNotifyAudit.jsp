<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<script type="text/javascript">
		function doSubmit(url){
			var valid = $("#form1").validationEngine({
				returnIsValid : true
			});
			if (valid) {
				top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
					$(".money").each(function(){
						rmoney(this);
					});
					$.post(url, $("#form1").serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, function() {
								top.frmright.window.location.reload(true);
								top.Dialog.close();
							});
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			}else{
				top.Dialog.alert("验证未通过！");
			}
		}
		</script>
	</head>
	<body>
		<div class="basicTabModern">
			<div name="发起确认请求" style="width:100%;height:300px;">
   				<table class="tableStyle" width="80%" mode="list" formMode="line">
   					<tr>
						<td align="left">
							交易时间:
						</td>
						<td>
						${entity.tradeDate1}
						</td>
					</tr>
   					<tr>
						<td align="left" width="25%">
							产品代码:
						</td>
						<td width="25%">
							${entity.prodCode}
						</td>
					</tr>
					<tr>
						<td align="left" width="25%">
							产品名称:
						</td>
						<td width="25%">
							${entity.prodName}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款类型:
						</td>
						<td>
						${entity.tradeKindName}
						</td>
					</tr>
					<tr>
						<td align="left">
							币种:
						</td>
						<td>
						  <c:choose>
							<c:when test="${entity.currency==826}">
								英镑
							</c:when>
							<c:when test="${entity.currency==156}">
							         人民币
							</c:when>
							<c:when test="${entity.currency==978}">
							         欧元
							</c:when>
							<c:when test="${entity.currency==840}">
							         美元
							</c:when>
							<c:when test="${entity.currency==392}">
							         日元
							</c:when>
							<c:when test="${entity.currency==036}">
							        澳大利亚元
							</c:when>
							<c:when test="${entity.currency==702}">
							         新元
							</c:when>
							<c:when test="${entity.currency==643}">
							         卢布
							</c:when>
							<c:when test="${entity.currency==756}">
							         瑞士法郎
							</c:when>
							<c:when test="${entity.currency==124}">
							         加元
							</c:when>
							<c:when test="${entity.currency==344}">
								港币
							</c:when>
						   </c:choose>
						</td>
					</tr>
					<tr>
						<td align="left">
							金额:
						</td>
						<td>
						<c:if test="${entity.tradeKindCode==201}">
							 <c:if test="${entity.isProfitFeeToRMB==1}">
								 <c:choose>
											<c:when test="${entity.currency==156}">
											     ${entity.sumAmt}
											</c:when>
											<c:when test="${entity.currency==826}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==978}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==840}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==392}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==036}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==702}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==643}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==756}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==124}">
											   ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==344}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
							      </c:choose>
							     </c:if>
						     <c:if test="${entity.isProfitFeeToRMB!=1}">
						          ${entity.sumAmt}
						     </c:if>
					    </c:if>
					     <c:if test="${entity.tradeKindCode!=201}">
					             ${entity.sumAmt}
					     </c:if>
						</td>
					</tr>
					<tr>
						<td align="left">
							划款方账号:
						</td>
						<td>
						${entity.outAcctCode}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款方账户:
						</td>
						<td>
						${entity.outAcctName}
						</td>
					</tr>
					<tr>
						<td align="left">
							收款方账号:
						</td>
						<td>
						${entity.inAcctCode}
						</td>
					</tr>
					<tr>
						<td align="left">
							收款方账户:
						</td>
						<td>
						${entity.inAcctName}
						</td>
					</tr>
   				</table>
			</div>
			<div name="复核信息" style="width:100%;height:80px;">
			<form id="form1">
				<input type="hidden" name="tradeDate1" value="${entity.tradeDate1}"/>
				<input type="hidden" name="prodCode" value="${entity.prodCode}"/>
				<input type="hidden" name="tradeKindCode" value="${entity.tradeKindCode}"/>
				<input type="hidden" name="custType" value="${entity.custType}"/>
				<input type="hidden" name="accountingFlag" value="${entity.accountingFlag}"/>
				<input type="hidden" name="currency"  value="${entity.currency}"/>
				<input type="hidden" name="appType" value="1" />
					<div style="width: 98%">
						<table class="tableStyle" width="97%" mode="list" formMode="line">
							<tr>
								<td width="38%">操作员代码：</td>
								<td>
									${sessionScope.sessionUser.opercode} 
									<input type="hidden" name="appUser" value="${sessionScope.sessionUser.opercode}"/>
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
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=AC-03-05">
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
									<button type="button" onclick="return doSubmit('<%=path%>/taHkNotifyInfoF/doApply.htm')" class="saveButton"/>
									<button type="button" onclick="return cal()" class="cancelButton" />	
								</div>
							</td>
						</tr>
						</table>
					</div>
				</form>
				</div>
			</div>
	</body>
</html>