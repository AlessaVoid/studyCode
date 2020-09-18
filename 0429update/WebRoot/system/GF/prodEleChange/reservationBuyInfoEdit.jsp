<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>预约购买参数</title>
		<script type="text/javascript">
		function sub(formId,url) {
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			if (valid&&checkData()) {
				top.Dialog.confirm("是否保存信息?", function() {
					$.post(url, $("#"+formId).serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg,function(){
								window.location.reload(true);
								parent.SetOrderFunc(result.bean.order);
								});
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			} 
		}
function checkData(){
	var beginDate = $("#orderBeginDate").val();
	var endDate = $("#orderEndDate").val();
	var finalDate = $("#orderFinalValidityDate").val();
	//预约起始日<=预约终止日<=预约最终有效日
	if(compareDate(beginDate, endDate)){
		top.Dialog.alert("预约起始日["+beginDate+"]不能晚于</br>预约终止日["+endDate+"]!");
		return false;
	}
	if(compareDate(endDate, finalDate)){
		top.Dialog.alert("预约终止日["+endDate+"]不能晚于</br>预约最终有效日["+finalDate+"]!");
		return false;
	}
	return true;
}
			
</script> 
	</head>
	<body>
	<form id="form1">
		<input  type="hidden" name="prodCode" value="${prodCode}"/>
			<input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
			<input type="hidden" id="order" name="order" value="${order}"/>
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="19%">预约起始日：</td>
				<td width="31%">
					<input type="text" id="orderBeginDate" name="orderBeginDate" class="date " dateFmt="yyyyMMdd" value="${entity.orderBeginDate }"/>
				</td>
				<td align="right" width="19%">预约终止日：</td>
				<td width="31%">
					<input type="text" id="orderEndDate" name="orderEndDate" class="date " dateFmt="yyyyMMdd" value="${entity.orderEndDate }"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="19%">预约最终有效日：</td>
				<td width="31%">
					<input type="text" id="orderFinalValidityDate" name="orderFinalValidityDate" class="date" dateFmt="yyyyMMdd" value="${entity.orderFinalValidityDate }"/>
				</td>
				<td align="right" width="19%">预约额度回收时间：</td>
				<td width="31%">
					<input type="text" name="orderTakeBackTime" class="date" dateFmt="HHmmss" value="${entity.orderTakeBackTime }"/>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">
					<div align="center">
						<button type="button" onclick="return sub('form1','<%=path%>/gfProdEleChange/compareReservationBuyInfo.htm')" class="saveButton"/>
					</div>
				</td>
			</tr>
		</table>
   	</form>
</body>
</html>