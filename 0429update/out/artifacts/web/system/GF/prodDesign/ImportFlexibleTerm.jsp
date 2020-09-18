<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<form action="<%=path%>/design/show.htm" method="post" enctype="multipart/form-data">
  			<table class="tableStyle" width="80%" mode="list" formMode="line">
  				<tr>
					<td align="right" width="25%">模板选择： </td>
					<td>
						<input type="file" name="paramXsl" id="paramXsl" class="validate[required]" keepDefaultStyle="true" />
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="submit" class="saveButton" />
							<button type="button" onclick="return cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
  			</table>
	   	</form>
	</body>
</html>