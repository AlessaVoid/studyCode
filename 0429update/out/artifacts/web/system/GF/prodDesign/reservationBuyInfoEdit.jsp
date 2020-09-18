<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>预约购买参数</title>
		<script type="text/javascript">
		var copyType="${copyType}";
		var prodOperModel="${prodOperModel}";
		function sub(formId,url) {
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			if (valid) {
				var beginDate = $("#orderBeginDate").val();
				var endDate = $("#orderEndDate").val();
				var finalDate = $("#orderFinalValidityDate").val();
				if(compareDate(beginDate, endDate)){
					top.Dialog.alert("预约起始日["+beginDate+"]不能晚于</br>预约终止日["+endDate+"]!");
				}else if(compareDate(endDate, finalDate)){
					top.Dialog.alert("预约终止日["+endDate+"]不能晚于</br>预约最终有效日["+finalDate+"]!");
				}else{
					top.Dialog.confirm("是否保存信息?", function() {
						$.post(url, $("#"+formId).serialize(), function(result) {
							if (result.success == "true" || result.success == true) {
								top.Dialog.alert(result.msg, function() {
									if(copyType == "copyType"){
										if(prodOperModel== "03" || prodOperModel == "04"){
											parent.turnPage("panel18","panel19");
										}else if(prodOperModel== "01" || prodOperModel == "03"){
											parent.turnPage("panel18","panel21");
										}else{
											parent.turnPage("panel18","panel22");
										}
									}else{
										if($("#index").val() != $("#oldIndex").val()){
											parent.changeDiv($("#index").val(),$("#oldIndex").val());
										}else{
											parent.changeDiv($("#index").val(),$("#index").val());
										}
									}
								});
							} else {
								top.Dialog.alert(result.msg);
							}
						}, "json");
					});
				}
			} else {
				top.Dialog.alert("验证未通过！");
			}
		}
function skip(){
	if(copyType =="copyType"){
		if(prodOperModel== "03" || prodOperModel == "04"){
			parent.turnPage("panel18","panel19");
		}else if(prodOperModel== "01" || prodOperModel == "03"){
			parent.turnPage("panel18","panel21");
		}else{
			parent.turnPage("panel18","panel22");
		}
	}else{
		if($("#index").val() != $("#oldIndex").val()){
			parent.changeDiv($("#index").val(),$("#oldIndex").val());
		}else{
			parent.changeDiv($("#index").val(),$("#index").val());
		}
	}
			
}
</script> 
	</head>
	<body>
	<form id="form1">
		<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
		<input type="hidden" name="buyIndex" id="buyIndex" value="${buyIndex }"/>
		<input type="hidden" name="index" id="index" value="${index }"/>
		<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
  		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="19%">预约起始日：</td>
				<td width="31%">
					<input type="text" id="orderBeginDate" name="orderBeginDate" class="date validate[length[0,8]]" dateFmt="yyyyMMdd" value="${entity.orderBeginDate }"/>
				</td>
				<td align="right" width="19%">预约终止日：</td>
				<td width="31%">
					<input type="text" id="orderEndDate" name="orderEndDate" class="date validate[length[0,8]]" dateFmt="yyyyMMdd" value="${entity.orderEndDate }"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="19%">预约最终有效日：</td>
				<td width="31%">
					<input type="text" id="orderFinalValidityDate" name="orderFinalValidityDate" class="date validate[length[0,8]]" dateFmt="yyyyMMdd" value="${entity.orderFinalValidityDate }"/>
				</td>
				<td align="right" width="19%">预约额度回收时间：</td>
				<td width="31%">
					<input type="text" name="orderTakeBackTime" class="date validate[length[0,30]]" dateFmt="HHmmss" value="${entity.orderTakeBackTime }"/>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
					<div align="center">
						<button type="button" onclick="return sub('form1','<%=path%>/design/saveReservationBuyInfo.htm')" class="saveButton"/>
						<button type="button" onclick="skip()" name="跳过" class="button"><span class='icon_page_next'>跳过</span></button>
					</div>
				</td>
			</tr>
		</table>
   	</form>
</body>
</html>