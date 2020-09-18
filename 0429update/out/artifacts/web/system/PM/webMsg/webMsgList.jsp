<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title>消息列表页面</title>
		<%@include file="/common/common_list.jsp" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<!--表单异步提交start-->
		<script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
		<!--表单异步提交end-->
		<!--基本选项卡start-->
		<script type="text/javascript" src="../libs/js/nav/basicTabModern.js"></script>
		<!--基本选项卡end-->
<script type="text/javascript">
function initComplete(){
	var listLength = document.getElementsByName("msgTypeCode");
	setUrl(listLength,0);
	
	$("#webMsgDiv").bind("actived",function(e,i){
		setUrl(listLength,i);
	});
}

function setUrl(listLength,i){
	var index = listLength[i].value;
	var url = '<%=path%>/system/PM/webMsg/webMsgInfo.jsp?msgTypeCode='+index;
	$("#panel" + index).attr("src", url);
}

</script>
	</head>
	<body>
		<div class="basicTabModern" id="webMsgDiv">
			<c:forEach items="${list }" var="list">
				<input type="hidden" name="msgTypeCode" value="${list.msgTypeCode}"/>
				<div name="${list.msgTypeName }">
					 <iframe height="550px" 
					 class="iframe-index" 
					 name="frmrightWebMsg" 
					 id="panel${list.msgTypeCode }" 
					 width="100%" 
					 frameborder="0">
 					 </iframe>
				</div>
			</c:forEach>
		</div>
	</body>
</html>