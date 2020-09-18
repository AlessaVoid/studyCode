<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_info.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title></title> <!--基本选项卡start-->
	<script type="text/javascript"
		src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
	<!--基本选项卡end-->

	<!--表单验证start-->
	<script src="<%=path%>/libs/js/form/validationRule.js"
		type="text/javascript"></script>
	<script src="<%=path%>/libs/js/form/validation.js"
		type="text/javascript"></script>
	<!--表单验证end-->
	<script>
		$(function() {
			//修正由于使用了tab导致高度计算不准确
			if (broswerFlag == "IE6") {
				setTimeout(function() {
					top.iframeHeight('frmrightChild');
				}, 500);
			}

		})
	</script>
</head>
<body>
	<form id="form1">
		<input type="hidden" id="type" name="type" value="${type}" />
		<table class="tableStyle" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="38%">交易类型：</td>
				<td><dic:out dicType="D_PARAM_VALIDITY"
						dicNo="${webParamValidityDTO.tradeType}"></dic:out></td>
			</tr>
			<tr>
				<td align="right" width="38%">开始时间：</td>
				<td>${webParamValidityDTO.beginTime}</td>
			</tr>
			<tr>
				<td align="right" width="38%">终止时间：</td>
				<td>${webParamValidityDTO.endTime}</td>
			</tr>
			<tr>
				<td align="right" width="38%">上限(%)：</td>
				<td><fmt:out fmtvalue="${webParamValidityDTO.maxFeeRate}"
						fmtclass="money-2"></fmt:out></td>
			</tr>
			<tr>
				<td align="right" width="38%">下限(%)：</td>
				<td><fmt:out fmtvalue="${webParamValidityDTO.minFeeRate}"
						fmtclass="money-2"></fmt:out></td>
			</tr>
			<tr>
				<td align="right" width="38%">最低投资额(万元)：</td>
				<td><fmt:out fmtvalue="${webParamValidityDTO.lowestInvestAmt}"
						fmtclass="money-2"></fmt:out></td>
			</tr>
		</table>
	</form>
</body>
</html>