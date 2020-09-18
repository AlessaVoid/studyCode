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
						本系统错误码：
					</td>
					<td>
						<input type="text" name="gfRetCode" class="validate[required]" maxlength="4"/><span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						本系统错误信息：
					</td>
					<td>
						<textarea rows="10" cols="20" name="gfRetInfo" class="validate[required]" maxlength="50" ></textarea><span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						外系统代码：
					</td>
					<td>
						<input type="text" name="otherSysCode" maxlength="11"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						外系统错误码：
					</td>
					<td>
						<input type="text" name="otherRetCode" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						外系统错误信息：
					</td>
					<td>
						<textarea rows="10" cols="20" name="otherRetInfo"  maxlength="50" ></textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						创建人：
					</td>
					<td>
						<input type="text" name="latestOperCode" class="validate[required]" maxlength="4"/><span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return doSubmit('form1','<%=path%>/gfErrInfo/insert.htm')" class="saveButton"/>
							<button type="button" onclick="return cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>