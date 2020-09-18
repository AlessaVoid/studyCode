<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
			<title></title> 
			<!--表单验证start-->
			<script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
			<script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
			<!--表单验证end-->
	<script>
$(function() {
	//修正由于使用了tab导致高度计算不准确
	/* if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	} */
	//判断是否有挂钩描述
	changeIsPeg();
});
function changeIsPeg(){
	var isPeg = $("#isPeg").val();
	if(isPeg==0){
		$("#pegDescription").attr("disabled","disabled");
		$("#pegDescription").render();
	}else{
		$("#pegDescription").removeAttr("disabled");
	}
}
</script>
	</head>
	<body>
		<form id="form1">
		<!-- <input  name="" type="hidden" value=""/> -->
			<div class="basicTabModern">
	   			<div name="业绩比较基准信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
						    <td align="right" width="38%">产品代码：</td>
						    <td>
							    <div class="suggestion validate[required]" name="prodCode" matchName="prodCode"
										url="<%=path%>/gfProdBaseInfo/selectGfProdBaseInfo.htm?type=prodCode" suggestMode="remote">
								</div>
						    	<span class="star">*</span>
						    </td>
						</tr>
	   					<tr>
						    <td align="right" width="38%">显示日期：</td>
						    <td>
								<input type="text" name="performanceDate" class="validate[required] date" dateFmt="yyyyMMdd" maxlength="10"/>
								<span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">启用日期：</td>
						    <td>
								<input type="text" name="performanceUseDate" class="validate[required] date" dateFmt="yyyyMMdd" maxlength="10"/>
								<span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">业绩比较基准值(%)：</td>
						    <td>
						     <input type="text" name="performanceValue" class="money" maxlength="7"/>
						     <span class="star">*</span>
						    </td>
						</tr>
						<tr>
				            <td>是否挂钩标的：</td>
				             <td>
						     <dic:select onChange="changeIsPeg(this.value);" name="isPeg" id="isPeg" dicType="IS_YES" dicNo="${entity.isPeg}"></dic:select>
						     </td>
						</tr>
						<tr>
						      <td>挂钩描述：</td>
						      <td>
						     <input type="text" name="pegDescription" id="pegDescription" style="width:140px;" value="${entity.pegDescription}" maxlength="100" />
						     <!--  <td><textarea class="textarea"
										style="width:15px; height: 50px;left:middle;" name="pegDescription" id="pegDescription"></textarea>
						      </td> -->
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
						<input type="hidden" name="operDescribe" value="产品净值管理" /> 
						<input type="hidden" name="tradeCode" value="TRADE-03-01" />
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
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=TRADE-15-01">
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
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfProdPerformanceInfo/insert.htm')" class="saveButton"/>
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