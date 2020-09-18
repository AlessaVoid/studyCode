<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<!-- 日期选择框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
		<!-- 日期选择框end -->
		<script type="text/javascript">
$(function(){
	changeCertOut();
	changeCertIn();
});
function changeCertOut(){
	var outCustType = $("#outCustType").val();
	if(outCustType == '1' || outCustType==''){//个人
		$("#corpDiv").hide();
		$("#custDiv").show();
		$("#corp").removeAttr("name");
		$("#corp").removeAttr("class");
	}else if(outCustType == '2'){//机构
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
function changeCertIn(){
	var inCustType = $("#inCustType").val();
	if(inCustType == '1' || inCustType==''){//个人
		$("#corpDivIn").hide();
		$("#custDivIn").show();
		$("#corpIn").removeAttr("name");
		$("#corpIn").removeAttr("class");
	}else if(inCustType == '2'){//机构
		$("#corpDivIn").show();
		$("#custDivIn").hide();
		$("#custIn").removeAttr("name");
		$("#custIn").removeAttr("class");
	}
	$("#corpDivIn").render();
	$("#custDivIn").render();
	$("#corpIn").render();
	$("#custIn").render();
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
//根据产品代码回显产品名称
function selectProdName(val){
	if(val.length==10){
		$.post("<%=path%>/taNoTransactionTransfer/selectProdName.htm?prodCode="+val,function(result){
			if (result.success == "true" || result.success == true) {
				$("#prodName").html(result.bean);
				$("#prodName").render();
			}else{
				top.Dialog.alert(result.msg);
			}
		},"json");
	}else if(val.length>0 && val.length!=10){
		top.Dialog.alert("请输入正确的产品代码！");
	}
}
//根据证件号码和产品代码查询回显可过户份额
var outCustCertCode,prodCode,outCustName;
function selectQuotientavailable(){
	outCustCertCode = $("#outCustCertCode").val();
	prodCode = $("#prodCode").val();
	outCustName = $("#outCustName").val();
	outCustName=escape(encodeURIComponent(outCustName));
	if(prodCode.length==10){
		$.post("<%=path%>/taNoTransactionTransfer/select_q.htm?prodCode="+prodCode+"&outCustCertCode="+outCustCertCode+"&outCustName="+outCustName,{},function(result){
			if (result.success == "true" || result.success == true) {
				$("#quotientavailable").html(result.bean);
				$("#quotientavailable_a").val(result.bean);
				$("#quotientavailable").render();
			}else{
				$("#quotientavailable").html(result.bean);
				$("#quotientavailable_a").val(result.bean);
				$("#quotientavailable").render();
			}
		},"json");
	}
}
function a(){
	var cust = $("#cust").val();
	$("#outCustCertType").val(cust);
}
function b(){
	var corp = $("#corp").val();
	$("#outCustCertType").val(corp);
}
function c(){
	var custIn = $("#custIn").val();
	$("#inCustCertType").val(custIn);
}
function d(){
	var corpIn = $("#corpIn").val();
	$("#inCustCertType").val(corpIn);
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
								<dic:select dicType="D_CUST_TYPE" id="outCustType" name="outCustType" onchange="changeCertOut();" tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件类型：
							</td>
							<td>
								<div id="custDiv">
									<dic:select id="cust" onchange="a();" dicNo="${appDataMap.outCustCertType}" dicType="D_CUST_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
								<div id="corpDiv">
									<dic:select id="corp" onchange="b();" dicNo="${appDataMap.outCustCertType}" dicType="D_CORP_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
								<input type="hidden" id="outCustCertType" name="outCustCertType"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件号码：
							</td>
							<td>
								<input type="text" id="outCustCertCode" name="outCustCertCode" class="validate[required]" maxlength="30"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								客户名称：
							</td>
							<td>
								<input type="text" id="outCustName" name="outCustName" class="validate[required]" maxlength="100"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品代码：
							</td>
							<td>
								<input type="text" id="prodCode" name="prodCode" class="validate[required]" onblur="selectProdName(this.value);selectQuotientavailable();" maxlength="10"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品名称：
							</td>
							<td>
								<span id="prodName" name="prodName"></span>
								<span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商代码：
							</td>
							<td>
								<input type="text" id="retailerCode" name="retailerCode" class="validate[required]" maxlength="10"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								交易类型：
							</td>
							<td>
								<dic:select dicType="D_TRANS_TYPE" id="transType" name="transType" tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方投资类型：
							</td>
							<td>
								<dic:select dicType="D_CUST_TYPE" id="inCustType" name="inCustType" onchange="changeCertIn();"  tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方证件类型：
							</td>
							<td>
								<div id="custDivIn">
									<dic:select id="custIn" onchange="c();" dicType="D_CUST_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
								<div id="corpDivIn">
									<dic:select id="corpIn" onchange="d();" dicType="D_CORP_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
								<input type="hidden" id="inCustCertType" name="inCustCertType"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方证件号码：
							</td>
							<td>
								<input type="text" id="inCustCertCode" name="inCustCertCode" class="validate[required]" maxlength="20"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方客户名称：
							</td>
							<td>
								<input type="text" id="inCustName" name="inCustName" class="validate[required]" maxlength="100"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								对方销售商代码：
							</td>
							<td>
								<input type="text" id="inRetailerCode" name="inRetailerCode" class="validate[required]" maxlength="10"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								可过户份额：
							</td>
							<td>
								<span id="quotientavailable"></span>
								<input type="hidden" id="quotientavailable_a" name="quotientavailable"/>
								<span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								过户份额：
							</td>
							<td>
								<input type="text" id="transferQuotient" name="transferQuotient" class="validate[required]" onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="16"/>
								<span id="star" class="star">*</span>							
							</td>
						</tr>
						<tr>
							<td align="right">
								过户原因：
							</td>
							<td>
								<textarea id="transferReason" name="transferReason" rows="5" cols="10" maxlength="100"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人姓名：
							</td>
							<td>
								<input type="text" name="handleName" class="validate[required]" maxlength="100"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件类型：
							</td>
							<td>
								<dic:select dicType="D_CUST_CERT_TYPE" id="handleCertType" name="handleCertType" tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件号码：
							</td>
							<td>
								<input type="text" name="handleCertCode" onblur="isCardNo($('#unfreezehandleCertType').val(),this.value);" class="validate[required,length[0,20],custom[onlyNumber]]" maxlength="20"/>
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
    			<div name="复核信息" style="width:100%;height:80px;">
					<div style="width: 98%">
						<input type="hidden" name="operDescribe" value="TA非交易过户(司法)" /> 
						<input type="hidden" name="appType" value="0" />
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
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=TA-06-01">
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
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfNoTradeTransferInfo/approval.htm')" class="saveButton"/>
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