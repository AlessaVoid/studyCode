<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title>
		 <!--自动提示框end-->
	<script type="text/javascript" src="${path}/js/money.js"></script>
	</head>
	<body>
		<form id="form1">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								期限：
							</td>
							<td>
								<dic:out dicType="PERIODQ" dicNo="${entity.period}"></dic:out>
								<input type="hidden" name="period" value="${entity.period}" id="period" />
							</td>
						</tr>
						<tr>
							<td>产品期限上下限(天)：</td>
							<td>
								<dic:out dicType="PERIODTIME" dicNo="${entity.period} "></dic:out>
							</td>
						</tr>	
						
						<tr>
							<td align="right">
								预期最低收益率：
							</td>
							<td>
								<input type="text" name="minimumRate"  value="${entity.minimumRate}" class="validate[required] money" maxlength="5"/>%<span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/webPeriodMinRate/update.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>