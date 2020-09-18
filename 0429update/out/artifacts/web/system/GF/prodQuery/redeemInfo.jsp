<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>赎回参数</title> 
	</head>
	<body>
		<form id="form1">
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="19%">个人最小存续期：</td>
				<td width="31%">
					${entity.lowDurationDays }
				</td>
				<td align="right" width="19%">个人客户单日最低赎回份额：</td>
				<td width="31%">
					${entity.lowDailyRedeemQuot }
				</td>
			</tr>
			<tr>
				<td align="right" width="19%">个人客户单日最大赎回份额：</td>
				<td width="31%">
					${entity.highDailyRedeemQuot }
				</td>
				<td align="right" width="19%">机构最小存续期：</td>
				<td width="31%">
					${entity.corpLowDurationDays }
				</td>
			</tr>
			<tr>
				<td align="right" width="19%">机构客户单日最低赎回份额：</td>
				<td width="31%">
					${entity.corpLowDailyRedeemQuot }
				</td>
				<td align="right" width="19%">机构客户单日最大赎回份额：</td>
				<td width="31%">
					${entity.corpHighDailyRedeemQuot }
				</td>
			</tr>
			<tr>
				<td align="right" width="19%">最大赎回比例：</td>
				<td width="31%">
					${entity.maxRedeemPer }
				</td>
				<td align="right" width="19%">是否巨额赎回：</td>
				<td width="31%">
					<dic:out dicType="IS_YES" dicNo="${entity.isHugeRedeem }"></dic:out>
				</td>
			</tr>
		</table>
   	</form>
	</body>
</html>