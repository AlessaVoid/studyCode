<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
			<title></title> 
	<script>
$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
	changeParam();
});

function changeParam(){
	var tradeType=$("#tradeType").val();
	if(tradeType == '1'){//募集起始日
		$("#beginTime").removeAttr("disabled");
		$("#endTime").removeAttr("disabled");
		$("#beginTime").attr("class","date validate[required,length[0,8]]");
		$("#endTime").attr("class","date validate[required,length[0,8]]");
		
		$("#maxFeeRate").attr("disabled","disabled");
		$("#minFeeRate").attr("disabled","disabled");
		$("#maxFeeRate").attr("class","money validate[length[0,7]]");
		$("#minFeeRate").attr("class","money validate[length[0,7]]");
		
		$("#lowestInvestAmt").attr("disabled","disabled");
		$("#lowestInvestAmt").attr("class","money validate[length[0,17]]");
		
		$("#beginTimeStar").show();
		$("#endTimeStar").show();
		$("#maxFeeRateStar").hide();
		$("#minFeeRateStar").hide();
		$("#lowestInvestAmtStar").hide();
	}else if(tradeType > 1 && tradeType <= 5){//费率
		$("#beginTime").attr("disabled","disabled");
		$("#endTime").attr("disabled","disabled");
		$("#beginTime").attr("class","date validate[ength[0,8]]");
		$("#endTime").attr("class","date validate[length[0,8]]");
		
		$("#lowestInvestAmt").attr("disabled","disabled");
		$("#lowestInvestAmt").attr("class","money validate[length[0,17]]");
		
		$("#maxFeeRate").removeAttr("disabled");
		$("#minFeeRate").removeAttr("disabled");
		$("#maxFeeRate").attr("class","money validate[required,length[0,7]]");
		$("#minFeeRate").attr("class","money validate[required,length[0,7]]");
		
		$("#beginTimeStar").hide();
		$("#endTimeStar").hide();
		$("#lowestInvestAmtStar").hide();
		$("#maxFeeRateStar").show();
		$("#minFeeRateStar").show();
	}else if(tradeType >= 'A' &&  tradeType <= 'E'){//风险
		$("#beginTime").attr("disabled","disabled");
		$("#endTime").attr("disabled","disabled");
		$("#beginTime").attr("class","date validate[ength[0,8]]");
		$("#endTime").attr("class","date validate[length[0,8]]");
		
		$("#maxFeeRate").attr("disabled","disabled");
		$("#minFeeRate").attr("disabled","disabled");
		$("#maxFeeRate").attr("class","money validate[length[0,7]]");
		$("#minFeeRate").attr("class","money validate[length[0,7]]");
		
		$("#lowestInvestAmt").removeAttr("disabled");
		$("#lowestInvestAmt").attr("class","money validate[required,length[0,17]]");
		
		$("#beginTimeStar").hide();
		$("#endTimeStar").hide();
		$("#maxFeeRateStar").hide();
		$("#minFeeRateStar").hide();
		$("#lowestInvestAmtStar").show();
	}
	$("#beginTime").render();
	$("#endTime").render();
	$("#maxFeeRate").render();
	$("#minFeeRate").render();
	
	$("#beginTimeStar").render();
	$("#endTimeStar").render();
	$("#maxFeeRateStar").render();
	$("#minFeeRateStar").render();
}
function sub(){
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	if (valid) {
		$(".money").each(function(){
			rmoney(this);
		});
		top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
			$.post("<%=path %>/webParamValidity/update.htm", $("#form1").serialize(), function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg, function() {
						top.frmright.window.location.reload(true);
						top.Dialog.close();
					});
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		});
	}else{
		top.Dialog.alert("验证未通过！");
	}
}
</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" id="id" name="id" value="${webMenuInfoDTO.id}"/>
   				<table class="tableStyle" width="80%" mode="list" formMode="line">
   					<tr>
					    <td align="right" width="38%">交易类型：</td>
					    <td>
							<dic:out dicType="D_PARAM_VALIDITY" dicNo="${webParamValidityDTO.tradeType}"></dic:out>
					    	<input id="tradeType" type="hidden" name="tradeType" value="${webParamValidityDTO.tradeType}"/>
					    </td>
					</tr>
					<tr>
					    <td align="right" width="38%">开始时间：</td>
					    <td>
					    	<input id="beginTime" name="beginTime" type="text" class="date validate[length[0,8]]" value="${webParamValidityDTO.beginTime}" dateFmt="yyyyMMdd" maxlength="8"/>
					    	<span id="beginTimeStar" class="star">*</span>
					    </td>
					</tr>
   					<tr>
					    <td align="right" width="38%">终止时间：</td>
					    <td>
					    	<input id="endTime" name="endTime" type="text" class="date validate[length[0,8]]" value="${webParamValidityDTO.endTime}" dateFmt="yyyyMMdd" maxlength="8"/>
					    	<span id="endTimeStar" class="star">*</span>
					    </td>
					</tr>
					<tr>
					    <td align="right" width="38%">上限(%)：</td>
					    <td>
					    	<input id="maxFeeRate" type="text" name="maxFeeRate" class="money validate[length[0,7]]" value="${webParamValidityDTO.maxFeeRate}"/>
					   		<span id="maxFeeRateStar" class="star">*</span>
					    </td>
					</tr>
					<tr>
						<td align="right" width="38%">下限(%)：</td>
					    <td>
					    	<input id="minFeeRate" type="text" name="minFeeRate" class="money validate[length[0,7]]" value="${webParamValidityDTO.minFeeRate}"/>
					    	<span id="minFeeRateStar" class="star">*</span>
					    </td>
					</tr>
					<tr>
						<td align="right" width="38%">最低投资额(万元)：</td>
					    <td>
					    	<input id="lowestInvestAmt" type="text" name="lowestInvestAmt" class="money validate[length[0,17]]" value="${webParamValidityDTO.lowestInvestAmt}"/>
					   		<span id="lowestInvestAmtStar" class="star">*</span>
					    </td>
					</tr>
					<tr>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="return sub()" class="saveButton"/>
								<button type="button" onclick="cancel()" class="cancelButton" />	
							</div>
						</td>
					</tr>
   				</table>
	   	</form>
	</body>
</html>