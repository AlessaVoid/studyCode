<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>赎回参数</title> 
		<script type="text/javascript">
		var copyType="${copyType}";
		var prodOperModel="${prodOperModel}";
		function sub(formId,url) {
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			rallmoney();
			if (valid) {
				//去除金额显示的逗号，防止后台数据不匹配出错
				top.Dialog.confirm("是否保存信息?", function() {
					$.post(url, $("#"+formId).serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, function() {
								if(copyType == "copyType"){
									//parent.turnPage("panel19","panel20");
									if(prodOperModel== "01" || prodOperModel == "03"){
										parent.turnPage("panel19","panel21");
									}else{
										parent.turnPage("panel19","panel22");
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
			} else {
				//如果操作失败重新加上金额显示类
				fallmoney();
				top.Dialog.alert("验证未通过！");
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
					<td align="right" width="19%">个人最小存续期：</td>
					<td width="31%">
						<input type="text" name="lowDurationDays" class="" inputMode="numberOnly" maxlength="8" value="${entity.lowDurationDays }"/>
					</td>
					<td align="right" width="19%">个人客户单日最低赎回份额：</td>
					<td width="31%">
						<input type="text" name="lowDailyRedeemQuot" class="money " maxlength="14" value="${entity.lowDailyRedeemQuot }"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="19%">个人客户单日最大赎回份额：</td>
					<td width="31%">
						<input type="text" name="highDailyRedeemQuot" class="money" maxlength="14" value="${entity.highDailyRedeemQuot }"/>
					</td>
					<td align="right" width="19%">机构最小存续期：</td>
					<td width="31%">
						<input type="text" name="corpLowDurationDays" class="" maxlength="8" inputMode="numberOnly" value="${entity.corpLowDurationDays }"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="19%">机构客户单日最低赎回份额：</td>
					<td width="31%">
						<input type="text" name="corpLowDailyRedeemQuot" class="money" maxlength="14" value="${entity.corpLowDailyRedeemQuot }"/>
					</td>
					<td align="right" width="19%">机构客户单日最大赎回份额：</td>
					<td width="31%">
						<input type="text" name="corpHighDailyRedeemQuot" class="money" maxlength="14" value="${entity.corpHighDailyRedeemQuot }"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="19%">最大赎回比例(%)：</td>
					<td width="31%">
							<c:choose>
					<c:when test="${empty entity.maxRedeemPer }">
							<input type="text" name="maxRedeemPer" class="money-2" maxlength="7" value="20"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="maxRedeemPer" class="money-2" maxlength="7" value="${entity.maxRedeemPer }"/>
					</c:otherwise>
				</c:choose>
						
						
					</td>
					<td align="right" width="19%">是否巨额赎回：</td>
					<td width="31%">
						<dic:select dicType="IS_YES" tgClass="validate[required]" disabled="disabled" dicNo="0"></dic:select>
						<input type="hidden" name="isHugeRedeem" value="0"/>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return sub('form1','<%=path%>/design/saveRedeemInfo.htm')" class="saveButton"/>
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>