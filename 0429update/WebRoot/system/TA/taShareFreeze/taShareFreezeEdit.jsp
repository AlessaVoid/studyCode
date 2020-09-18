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
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<script type="text/javascript">
//数据表格使用
var grid;
function initComplete() {
	grid = $("#multipledata")
			.quiGrid({
						columns : [
								{
									display : '合同号',
									name : 'contractNo',
									align : 'center',
									width : "33%"
								},
								{
									display : '可冻结份额',
									name : 'availableQuot',
									align : 'center',
									width : "33%"
								},
								{
									display : '冻结份额',
									name : 'freezeQuot',
									align : 'center',
									width : "33%",
					                editor:{
					            	   type:"text",
					            	   maxlength:7,
					            	   width:150
					               }
								}
								],
						data:[],
						rownumbers : true,
						checkbox : false,
						height : "100%",
						width : "100%",
						pageSize : 100,
						percentWidthMode : true,
						enabledEdit: true						
	});
	/**
	 * 切换tab时处理, loadData
	 */
	$("#tabCtn").bind("actived",function(e,i){
		var flag = $("#tabCtn").attr("afterInitTab")||'';
		if(1==i){
			if(flag.indexOf('1i')<0){
				
				if($("#certificatekind").val() != "" && $("#retailercode").val() != "" && 
						$("#prodcode").val() != "" && $("#certificatecode").val() != "" 
							){
					$.post('<%=path%>/taProdAssetList/findContractNo.htm',
							{"certificatecode":$("#certificatecode").val(),
							 "prodCode":$("#prodcode").val(),
							 "retailercode":$("#retailercode").val(),
							 "certificatekind":$("#certificatekind").val(),
							 "customername":$("#customername").val()
							}
							,function(data){
								if(data.rows.length==0){
									$("#cheackContractNo").val("false");
									grid.loadData(data);//加载数据
									top.Dialog.alert("此账户下没有合同，请核对后重新输入");  
								}else{
									$("#cheackContractNo").val("true");
									//页号重置为1
									grid.setNewPage(1);
									grid.loadData(data);//加载数据
									
								}
					});
				}else{
					grid.loadData();
				}
				$("#tabCtn").attr("afterInitTab",flag+":1i");
			}
		}
	});
}
$(function(){
	$("#customerkind").val("1");
	changeCert();
	$("#customerkind").render();
	$("#cheackFlag").val("true");
});

function changeCert(){
	var customerkind = $("#customerkind").val();
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
	var customerkind = $("#customerkind").val();
	if(customerkind =='1'){
		$("#certificatekind").val($("#cust").val());
	}else if(customerkind == '2'){//机构
		$("#certificatekind").val($("#corp").val());
	}
}
//校验输入的身份证是否合法
function isCardNo(certificatekind,certificatecode) {
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
   if(certificatekind=="0") {
		if(reg.test(certificatecode) === false){  
	       top.Dialog.alert("身份证输入不合法");  
	       $("#cheackFlag").val("false");
	       return false;  
		}else {
   			$("#cheackFlag").val("true");
		}  
   	}
}   

function changeCertkind(){
	var customerkind = $("#customerkind").val();
	if(customerkind == '1'){//个人
		var cust = $("#cust").val();
		$("#certificatekind").val(cust);
	}else if(customerkind == '2'){//机构
		var corp = $("#corp").val();
		$("#certificatekind").val(corp);
	}
	$("#certificatekind").render();
}

//提交表单公共方法
function sub(form,url){
	var valid = $("#"+form).validationEngine( {
		returnIsValid : true
	});
	
	var param = $("#form1").formToArray();
	var map = {};
	map.name = "gridData";
	map.value= JSON.stringify(grid.getData());
	param.push(map);
	if($("#cheackContractNo").val()=="true"){
		if($("#cheackFlag").val()=="true"){
			if (valid) {
				top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
					$(".money").each(function(){
						rmoney(this);
					});
					$.post(url, param, function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, function() {
								top.frmright.window.location.reload(true);
								top.Dialog.close();
							});
						} else {
							$(".money").each(function(){
								fmoney(this);
							});
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			}else{
				top.Dialog.alert("验证未通过！");
			}
		}else {
			top.Dialog.alert("身份证输入不合法");
		}
	}else{
		top.Dialog.alert("此账户下没有合同，请核对后重新输入");  
	}
	
}
</script>
	</head>
	<body>
		<form id="form1">
		<div id="tabCtn" class="basicTabModern">
				<div name="TA份额冻结" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<input id="cheackFlag" type="hidden"/>
	   					<input id="cheackContractNo" type="hidden"/>
						<tr>
							<td align="right">
								投资者类型：
							</td>
							<td>
								<dic:select onChange="changeCert()" dicType="D_CUST_TYPE" id="customerkind" name="customerkind" tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件类型：
							</td>
							<td>
								<div id="custDiv">
									<dic:select id="cust" dicNo="${appDataMap.certificatekind}" onChange="changeCertType(this.value)" dicType="D_CUST_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
								<div id="corpDiv">
									<dic:select id="corp" dicNo="${appDataMap.certificatekind}" onChange="changeCertType(this.value)" dicType="D_CORP_CERT_TYPE" tgClass="validate[required]"></dic:select>
								</div>
								<input type="hidden" id="certificatekind" name="certificatekind"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件号码：
							</td>
							<td>
								<input type="text" id="certificatecode" name="certificatecode" onblur="isCardNo($('#certificatekind').val(),this.value);"  class="validate[required]" maxlength="30"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								客户名称：
							</td>
							<td>
								<input type="text" id="customername" name="customername" class="validate[required]" maxlength="100"/>
							<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								产品代码：
							</td>
							<td>
								<input type="text" id="prodcode" name="prodcode" class="validate[required]" maxlength="10"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商代码：
							</td>
							<td>
								<input type="text" id="retailercode" name="retailercode" value="71093465-X" class="validate[required]" maxlength="10"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								冻结截止日期：
							</td>
							<td>
								<input type="text" name="freezedate" class="date" dateFmt="yyyyMMdd" maxlength="8"/>
								
							</td>
						</tr>
						<tr>
							<td align="right">
								冻结法律文书号：
							</td>
							<td>
								<input type="text" id="freezejudicialfilecode" name="freezejudicialfilecode" class="validate[required]" maxlength="50" />
							<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								冻结原因（司法冻结）：
							</td>
							<td>
								<textarea id="freezeReason" name="freezeReason" rows="5" cols="10"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人姓名：
							</td>
							<td>
								<input type="text" name="handlename" class="validate[required]" maxlength="50"/>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件类型：
							</td>
							<td>
								<dic:select dicType="D_CUST_CERT_TYPE" id="handlecerttype" name="handlecerttype" tgClass="validate[required]"></dic:select>
								<span id="star" class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								司法经办人证件号码：
							</td>
							<td>
								<input type="text" name="handlecertcode" onblur="isCardNo($('#handlecerttype').val(),this.value);" class="validate[required,length[0,20]]" maxlength="20"/>
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
				<div class="padding_right5" name="冻结份额合同信息" style="width:100%;height:80px;">
				  <div id="multipledata"></div>
				</div>
    			<div name="复核信息" style="width:100%;height:80px;">
					<div style="width: 98%">
						<input type="hidden" name="operDescribe" value="TA份额冻结" /> 
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
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=TA-05-01">
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
									<button type="button" onclick="return sub('form1','<%=path%>/taShareFreeze/approval.htm')" class="saveButton"/>
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