<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	<script type="text/javascript">
	//根据产品代码回显渠道名称
	function  selectChannelCode(val){
		$("#channelCode").empty();
		$("#tradeCode").empty();
	 	var channelCode=$("#channelCode").val();
		var tradeCode=$("#tradeCode").val();
		var prodCode=$("#prodCode").val();
 			 $.post("<%=path%>/gfProdTradeCtl/selectChannelCode.htm?prodCode="+val,function(result){
 				if(result.success=="false"){
 					top.Dialog.alert(result.msg);
 					return;
 				}
 				 for(var i=0;i<result.length;i++){
 					 $("#channelCode").append("<option value='"+result[i].dictKeyIn+"'>"+result[i].dictValueIn+"</option>");
 				 }
 				selectTradeCode();
 				if(prodCode.length==0 ){
 					window.location.reload();
 				} 
 				$("#channelCode").render();
 			},"json");
			
	}
	//根据产品代码回显交易代码
	function selectTradeCode(){
		$("#tradeCode").html("");
		var channelCode=$("#channelCode").val();
		var prodCode=$("#prodCode").val();
			$.post("<%=path%>/gfProdTradeCtl/queryTradeCode.htm?prodCode="+prodCode+"&channelCode="+channelCode,function(result){
				if(result.success=="false"){
 					top.Dialog.alert(result.msg);
 					return;
 				}
				 $("#tradeCode").append("<option value='0'>--请选择--</option>");
				for(var i=0;i<result.length;i++){
					 $("#tradeCode").append("<option value='"+result[i].dictKeyIn+"'>"+result[i].dictValueIn+":"+result[i].dictKeyIn+"</option>");
				 }
				 $("#tradeCode").render();
			},"json");
			 
	}
	
	</script>
	</head>
	<body>
		<form id="form1" action="">
		<div class="basicTabModern">
				<div name="交易参数暂停" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								产品代码：
							</td>
							<td>
								<input type="text"  id="prodCode"name="prodCode" class="validate[required],custom[noSpecialCaracters]"  onblur="selectChannelCode(this.value);" maxlength="10"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								渠道代码：
							</td>
							<td>
 								<select id="channelCode" name="channelCode" onchange="selectTradeCode()"> 
 									<option value="">--请选择--</option> 
 								</select> 
 								<span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								交易代码：
							</td>
							<td>
							  <select id="tradeCode" name="tradeCode"  selected="true" > 
 									<option value="">--请选择--</option> 
 								</select> 
 								<!-- <span class="star">*</span> -->
							</td>
						</tr>
						<tr>
							<td align="right">
								状态：
							</td>
							<td>
								<label>停用</label>
								<input type="hidden" name="status"  value="2"/>
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
						<input type="hidden" name="operDescribe" value="暂停交易参数" /> 
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
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=TRADE-01-05">
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
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfProdTradeCtl/insert.htm')" class="saveButton"/>
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