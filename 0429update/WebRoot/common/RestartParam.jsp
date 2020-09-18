<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/common_edit.jsp"%>
<html>
	<head>
		<title>Web系统后台集成管理</title>
	</head>

<script type="text/javascript">
$.post("<%=path%>/restartParam.htm", null, function(result) {
	if (result.success == 'noPower') {
		alert("您没有操作权限");
	} else {
		top.Dialog.alert(result.msg);
	}

}, "json");
</script>
	
<body>
</body>
</html>

