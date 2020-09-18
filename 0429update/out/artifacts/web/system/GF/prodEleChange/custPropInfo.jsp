<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>行内属性参数</title> 
	</head>
	<body>
		<form id="form1">
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td width="100px">与或关系类型：</td>
					<td>
						<dic:out dicType="Y_HREFER" dicNo="${entity.relType}" ></dic:out>
						<span class="star">*</span>
					</td>
					<td width="100px">属性：</td>
					<td>
						${entity.propCode}
						<span class="star">*</span>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>