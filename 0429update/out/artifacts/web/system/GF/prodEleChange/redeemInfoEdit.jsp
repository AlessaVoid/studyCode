<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>赎回参数</title> 
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
			} else {
				//如果操作失败重新加上金额显示类
				fallmoney();
				top.Dialog.alert("验证未通过！");
			}
		}
	function initComplete(){
		var custType=${custType};
		if('1'==custType){//个人
			$("#p_7").removeClass("validate[required]");
			$("#p_8").hide();
			$("#p_9").removeClass("validate[required]");
			$("#p_10").hide();
			$("#p_11").removeClass("validate[required]");
			$("#p_12").hide();
		}
		if('2'==custType){//机构
			$("#p_1").removeClass("validate[required]");
			$("#p_2").hide();
			$("#p_3").removeClass("validate[required]");
			$("#p_4").hide();
			$("#p_5").removeClass("validate[required]");
			$("#p_6").hide();
		}
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
				<td align="right" width="15%">个人最小存续期：</td>
				<td width="15%">
					<input type="text" name="lowDurationDays" id ="p_1" class="validate[required]" inputMode="numberOnly" maxlength="8" value="${entity.lowDurationDays }"/>
					<span class="star" id ="p_2">*</span>
				</td>
				<td align="right" width="15%">个人客户单日最低赎回份额：</td>
				<td width="15%">
					<input type="text" name="lowDailyRedeemQuot" class="money validate[required]" id ="p_3" maxlength="14" value="${entity.lowDailyRedeemQuot }"/>
					<span class="star" id ="p_4">*</span>
				</td>
				<td align="right" width="15%">个人客户单日最大赎回份额：</td>
				<td width="15%">
					<input type="text" name="highDailyRedeemQuot"  id ="p_5" class="money validate[required]" maxlength="14" value="${entity.highDailyRedeemQuot }"/>
					<span class="star" id ="p_6">*</span>
				</td>
			</tr>
			<tr>
			<td align="right" width="15%">机构最小存续期：</td>
				<td width="15%">
					<input type="text" name="corpLowDurationDays" class="validate[required]" id ="p_7" maxlength="8" inputMode="numberOnly" value="${entity.corpLowDurationDays }"/>
					<span class="star" id ="p_8">*</span>
				</td>
				<td align="right" width="15%">机构客户单日最低赎回份额：</td>
				<td width="15%">
					<input type="text" name="corpLowDailyRedeemQuot"  id ="p_9" class="money validate[required]" maxlength="14" value="${entity.corpLowDailyRedeemQuot }"/>
					<span class="star" id ="p_10">*</span>
				</td>
				<td align="right" width="15%">机构客户单日最大赎回份额：</td>
				<td width="15%">
					<input type="text" name="corpHighDailyRedeemQuot"  id ="p_11"class="money validate[required]" maxlength="14" value="${entity.corpHighDailyRedeemQuot }"/>
					<span class="star" id ="p_12">*</span>
				</td>
			</tr>
			<tr>
				<td align="right" width="15%">最大赎回比例：</td>
				<td width="15%">
					<input type="text" name="maxRedeemPer" class="money validate[required]" maxlength="7" value="${entity.maxRedeemPer }"/>
					<span class="star">*</span>
				</td>
			</tr>
			
			<tr>
				<td colspan="6">
					<div align="center">
						<button type="button" onclick="return sub('form1','<%=path%>/gfProdEleChange/compareRedeemInfo.htm')" class="saveButton"/>
					</div>
				</td>
			</tr>
		</table>
   	</form>
	</body>
</html>