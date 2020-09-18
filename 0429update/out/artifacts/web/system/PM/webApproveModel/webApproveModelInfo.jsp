<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_info.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
			<title></title> 
<script>
</script>
	</head>
	<body>
		<form id="form1">
			<table class="tableStyle" width="80%" mode="list" formMode="line">
				<tr>
					<td width="38%" align="right">
						审批意见编码：
					</td>
					<td>
						${webApproveModelDTO.appCode }
					</td>
				</tr>
				<tr>
					<td>审批意见：</td>
					<td>
						${webApproveModelDTO.appAdvice }
					</td>
				<tr>
			</table>
	   	</form>
	</body>
</html>