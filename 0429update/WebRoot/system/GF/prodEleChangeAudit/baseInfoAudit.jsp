<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/common_list.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>基础参数变更</title>
</head>
<body>
	<form id="form1">
		<table class="tableStyle" mode="list" useMultColor="true">
			<tr>
				<th width="10%">参数名称</th>
				<th width="40%">变更原参数值</th>
				<th width="40%">变更新参数值</th>
			</tr>
			<c:forEach items="${dataList}" var="data">
				<tr>
					<td >
						${data.zhParamName}
					</td>
					<td>
						${data.oldValue}
					</td>
					<td>
						${data.newValue}
					</td>
				</tr>
				</c:forEach>
		</table>
	</form>

</script>
</body>
</html>