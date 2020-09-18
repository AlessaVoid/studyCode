<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_info.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
			<title></title> 
			<!--基本选项卡start-->
			<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
			<!--基本选项卡end-->
			
			<!--表单验证start-->
			<script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
			<script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
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
		<div style="overflow:auto;width:100%;">
			<table class="tableStyle" width="800%" mode="list" formMode="line">
				<tr>
				    <td align="right">线程号：</td>
				    <td>
				    	${webLogInfoDTO.threadCode}
				    </td>
				</tr>
				<tr>
				    <td align="right">交易日期：</td>
				    <td>
				    	${webLogInfoDTO.tradeDate}
				    </td>
				</tr>
				<tr>
				    <td align="right">交易时间：</td>
				    <td>
				    	${webLogInfoDTO.tradeTime}
				    </td>
				</tr>
				<tr>
				    <td align="right">机构编码：</td>
				    <td>
				    	${sessionScope.sessionOrgan.thiscode}
				    </td>
				</tr>
				<tr>
				    <td align="right">机构名称：</td>
				    <td>
				    	${sessionScope.sessionOrgan.organname}
				    </td>
				</tr>
				<tr>
				    <td align="right">操作员部门：</td>
				    <td>
						${webLogInfoDTO.operDeptName}
				    </td>
				</tr>
				<tr>
				    <td align="right">操作员代码：</td>
				    <td>
						${webLogInfoDTO.operCode}
				    </td>
				</tr>
				<tr>
				    <td align="right">操作员姓名：</td>
				    <td>
						${webLogInfoDTO.operName}
				    </td>
				</tr>
				<tr>
				    <td align="right">功能名称：</td>
				    <td>
						${webLogInfoDTO.moduleName}
				    </td>
				</tr>
				<tr>
				    <td align="right">交易耗时(ms)：</td>
				    <td>
						${webLogInfoDTO.tradeConsumingTime}
				    </td>
				</tr>
				<tr>
				    <td align="right">执行方法：</td>
				    <td>
						${webLogInfoDTO.runningMethod}
				    </td>
				</tr>
				<tr>
				    <td align="right">传递参数：</td>
				    <td>
						${webLogInfoDTO.argu}
				    </td>
				</tr>
				<tr>
				    <td align="right">请求IP：</td>
				    <td>
						${webLogInfoDTO.requestIp}
				    </td>
				</tr>
				<tr>
				    <td align="right">执行结果：</td>
				    <td>
						${webLogInfoDTO.runningResult}
				    </td>
				</tr>
				<tr>
				    <td align="right">机器号：</td>
				    <td>
						${webLogInfoDTO.serviceCode}
				    </td>
				</tr>
			</table>
			</div>
	   	</form>
	</body>
</html>