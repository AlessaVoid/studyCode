<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
				<script type="text/javascript">
$(function(){
	top.frmright.setSize(600, 460);
	changeCert();
});

function changeCert(){
	var customerkind = $("#outCustType").val();
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
function changeCertType(value){//为身份证类型赋值
	$("#outCustCertType").val(value);
	$("#outCustCertType").render();
}
//校验输入的身份证是否合法
function isCardNo(outCustCertType,outCustCertCode) {
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
   if(outCustCertType=="0") {
	   if(reg.test(outCustCertCode) === false){  
       top.Dialog.alert("身份证输入不合法");  
       return false;  
   	  }  
   }
}  
//根据冻结编号查询冻结信息
function changeFreezeCode(){
	$.post("<%=path%>/taAcctUnFreeze/getGfProdFreezeNote.htm",{"freezeCode":$("#freezeCode").val()},function(result){
		$("#customerkind").val(result.customerkind);
		if(result.customerkind == '1'){//个人
			$("#cust").val(result.outCustCertType);
		}else if(result.customerkind == '2'){//机构
			$("#corp").val(result.outCustCertType);
		}
		$("#outCustCertCode").val(result.outCustCertCode);
		$("#outCustName").val(result.outCustName);
		$("#transferReason").val(result.transferReason);
		changeCert();
	}, "json");
}
</script>
	</head>
	<body>
		<form id="form1">
		<div class="basicTabModern">
				<div name="TA非交易过户(司法)" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
						<tr>
							<td align="right">
								投资者类型：
							</td>
							<td>
								<dic:select dicType="D_CUST_TYPE" dicNo="${appDataMap.outCustType}" id="outCustType" name="outCustType" tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件类型：
							</td>
							<td>
								<div id="custDiv">
									<dic:select id="cust" name="outCustCertType" dicNo="${appDataMap.outCustCertType}" dicType="D_CUST_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
								<div id="corpDiv">
									<dic:select id="corp" name="outCustCertType" dicNo="${appDataMap.outCustCertType}" dicType="D_CORP_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件号码：
							</td>
							<td>
								<input type="text" id="outCustCertCode" name="outCustCertCode" value="${appDataMap.outCustCertCode}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								客户名称：
							</td>
							<td>
								<input type="text" id="outCustName" name="outCustName" value="${appDataMap.outCustName}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品代码：
							</td>
							<td>
								<input type="text" id="prodCode" name="prodCode" value="${appDataMap.prodCode}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品名称：
							</td>
							<td>
							    <span id="prodName"></span>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商代码：
							</td>
							<td>
								<input type="text" name="retailerCode" value="${appDataMap.retailerCode}" class="validate[custom[noSpecialCaracters],length[0,50]]"  maxlength="50"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								交易类型：
							</td>
							<td>
								<dic:select dicType="D_TRANS_TYPE" dicNo="${appDataMap.transType}" id="transType" name="transType" tgClass="validate[required]"></dic:select>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方投资类型：
							</td>
							<td>
								<div id="corpDiv">
									<dic:select id="customer" name="inCustType" dicNo="${appDataMap.inCustType}" dicType="D_CUST_TYPE" tgClass="validate[required]"></dic:select>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方证件类型：
							</td>
							<td>
								<div id="corpDiv">
									<dic:select id="customer" name="inCustCertType" dicNo="${appDataMap.inCustCertType}" dicType="D_CUST_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方证件号码：
							</td>
							<td>
								<input type="text" name="inCustCertCode" value="${appDataMap.inCustCertCode}" class="validate[required]"  maxlength="20"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方客户名称：
							</td>
							<td>
								<input type="text" name="inCustName" value="${appDataMap.inCustName}" class="validate[required]"  maxlength="100"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方销售商代码：
							</td>
							<td>
								<input type="text" name="inRetailerCode" value="${appDataMap.inRetailerCode}" class="validate[required]"  maxlength="10"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								可过户份额：
							</td>
							<td>
								<span id="quotientavailable"></span>
							</td>
						</tr>
						<tr>
							<td align="right">
								过户份额：
							</td>
							<td>
								<input type="text" id="transferQuotient" value="${appDataMap.transferQuotient}" name="transferQuotient" class="validate[required]" maxlength="16"/>
								<span id="star" class="star">*</span>	
							</td>
						</tr>
						<tr>
							<td align="right">
								过户原因：
							</td>
							<td>
								<textarea id="transferReason" name="transferReason" rows="5" cols="10" maxlength="100">${appDataMap.transferReason}</textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人姓名：
							</td>
							<td>
								<input type="text" name="handleName" value="${appDataMap.handleName}" class="validate[required]" maxlength="100"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件类型：
							</td>
							<td>
								<dic:select dicType="D_CUST_CERT_TYPE" dicNo="${appDataMap.handleCertType}" id="handleCertType" name="handleCertType" tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件号码：
							</td>
							<td>
								<input type="text" name="handleCertCode" value="${appDataMap.handleCertCode}" onblur="isCardNo($('#unfreezehandleCertType').val(),this.value);" class="validate[required,length[0,20],custom[onlyNumber]]" maxlength="20"/>
								<span id="star" class="star">*</span>
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
    			<div name="复核申请" style="width:100%;">
					<input type="hidden" name="appNo" value="${webReviewMain.appNo }" />
					<input type="hidden" name="appType" value="1" /> 
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
								<input type="hidden" name="appOperName" value="${sessionScope.sessionUser.opername}" /></td>
						</tr>
						<tr>
							<td>所属机构代码：</td>
							<td>${sessionScope.sessionUser.organcode} 
								<input type="hidden" name="appOrganCode" value="${sessionScope.sessionUser.organcode}" /></td>
						</tr>
						<tr>
							<td>所属机构名称：</td>
							<td>${sessionScope.sessionOrgan.organname} 
								<input type="hidden" name="appOrganName" value="${sessionScope.sessionOrgan.organname}" /></td>
						</tr>
						<tr>
							<td>操作员角色：</td>
							<td>${sessionScope.sessionUserRole} 
								<input type="hidden" name="appRoleName" value="${sessionScope.sessionUserRole}" /></td>
						</tr>
						<tr>
							<td>复核人员：</td>
							<td><select selWidth="127" 
								id="repUserCode" name="repUserCode" prompt="--请选择--" class="validate[required]"
								url="<%=path%>/fdOper/getAppUserList.htm?funCode=TA-06-01">
							</select> <span class="star">*</span> 
							<input type="hidden" name="repUserName" id="repUserName"/></td>
						</tr>
						
						<tr>
							<td>所属机构代码：</td>
							<td><span id="repUserOrganCode1"></span> 
								<input type="hidden" id="repUserOrganCode" name="repUserOrganCode"/></td>
						</tr>
						<tr>
							<td>所属机构名称：</td>
							<td><span id="repUserOrganName1"></span> 
								<input type="hidden" id="repUserOrganName" name="repUserOrganName"/></td>
						</tr>

						<tr>
							<td>复核人员角色：</td>
							<td><span id="repRoleName1"></span> 
								<input type="hidden" id="repRoleName" name="repRoleName"/></td>
						</tr>
						<tr>
							<td>备注说明：</td>
							<td>
								<textarea class="textarea" style="width: 80%; height: 80px;" name="appRemark"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfNoTradeTransferInfo/approval.htm')" class="saveButton"/>
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