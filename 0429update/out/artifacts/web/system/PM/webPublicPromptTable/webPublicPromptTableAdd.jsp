<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	</head>
	<body>
		<form id="form1">
  			<table class="tableStyle" width="80%" mode="list" formMode="line">
 				<tr>
					<td align="right" width="38%">
						公告内容：
					</td>
					<td>
						<textarea name="content" class="validate[required]" rows="10" cols="5">
						</textarea><span class="star">*</span>	

					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" onclick="return doSubmit('form1','<%=path%>/webPublicPromptTable/insert.htm')" class="saveButton"/>
							<button type="button" onclick="return cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>