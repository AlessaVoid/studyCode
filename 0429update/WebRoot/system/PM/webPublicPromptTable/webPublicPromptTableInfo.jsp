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
						id：
					</td>
					<td>
						${entity.id}
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						公告内容：
					</td>
					<td>
						${entity.content}
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						发布人：
					</td>
					<td>
						${entity.operCode}
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						发布日期：
					</td>
					<td>
						${entity.operDate}
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						发布时间：
					</td>
					<td>
						${entity.operTime}
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="38%">
						状态：
					</td>
					<td>
						<dic:out dicType="USE_STATUS" dicNo="${entity.useStatus}"></dic:out>
						<span class="star">*</span>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>