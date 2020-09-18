<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>认购参数</title> 
	</head>
	<body>
	<form id="form1">
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td width="16%">
					募集起始日期（从）：
				</td>
				<td width="34%">
					${entity.raisingBeginDate }
				</td>
				<td width="16%">
					募集起始时间（从）：
				</td>
				<td width="34%">
					${entity.raisingBeginTime }
				</td>
			</tr>
			<tr>
				<td>募集结束日期（到）：</td>
				<td>
					${entity.raisingEndDate }
				</td>
				<td>募集结束时间（到）：</td>
				<td>
					${entity.raisingEndTime }
				</td>
			</tr>
			<tr>
				<td>扣款方式：</td>
				<td>
					<dic:out dicType="AMT_HANDLE_FLAG" dicNo="${entity.chargeType }"></dic:out>
				</td>
				<td>份额确认方式：</td>
				<td>
					<dic:out dicType="QUOT_AFFIRM_TYPE" dicNo="${entity.quotAffirmType }"></dic:out>
				</td>
			</tr>
				<tr>
					<td align="right" width="10%">计划募集金额（${webProdBaseInfo.currency }）：</td>
					<td width="15%">
						<fmt:out fmtvalue="${entity.planIssueAmt }" fmtclass="money"></fmt:out>
					</td>
					<td align="right" width="10%">份额确认日</td>
					<td width="15%">
						${entity.quotAffirmDate }
					</td>
				</tr>
			<tr>
				<td>投资周期结束日：</td>
				<td>
					${entity.invCycleEndDate }
				</td>
				<td>初始投资周期结束日：</td>
				<td>
					${entity.firstInvCycleEndDate }
				</td>
			</tr>
		</table>
	</form>
	</body>
</html>