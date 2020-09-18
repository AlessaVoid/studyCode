<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
			<title></title> 
<script>
</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="appCode" value="${webApproveModelDTO.appCode }"/>
			<table class="tableStyle" width="80%" mode="list" formMode="line">
				<tr>
					<td align="right">
						审批意见：
					</td>
					<td>
						<textarea rows="5" cols="10" name="appAdvice" class="validate[required,length[0,200]]" maxlength="characters">${webApproveModelDTO.appAdvice }</textarea>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" onclick="doSubmit('form1','<%=path %>/webApproveModel/update.htm')" class="saveButton"/>
							<button type="button" onclick="cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>