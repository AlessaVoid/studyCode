<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>积分参数</title> 
		<script type="text/javascript">
		function sub(formId,url) {
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			if (valid) {
				//去除金额显示的逗号，防止后台数据不匹配出错
				rallmoney();
				top.Dialog.confirm("是否保存信息?", function() {
					$.post(url, $("#"+formId).serialize(), function(result) {
						top.Dialog.alert(result.msg);
					}, "json");
				});
			} else {
				//如果操作失败重新加上金额显示类
				fallmoney();
				top.Dialog.alert("验证未通过！");
			}
		}
function cleanVal(val){
	if(val==1){
		$("#orderPointQuot").val(0);
	 }else{
		 $("#buyPointQuot").val(0);
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
				<td align="right" width="19%">购买积分限额：</td>
				<td width="31%">
					<input type="text"  name="buyPointQuot"  onfocus="cleanVal(1)" id="buyPointQuot" class="money"  value="${entity.buyPointQuot }"  maxlength="16"/>
				</td>
				<td align="right" width="19%">预约积分限额：</td>
				<td width="31%">
					<input type="text"  name="orderPointQuot" onfocus="cleanVal(2)"  id="orderPointQuot" class="money"  value="${entity.orderPointQuot }"  maxlength="16"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="center">
						<button type="button" onclick="return sub('form1','<%=path%>/designRollback/updatePointInfo.htm')" class="saveButton"/>
					</div>
				</td>
			</tr>
		</table>
   	</form>
	</body>
</html>