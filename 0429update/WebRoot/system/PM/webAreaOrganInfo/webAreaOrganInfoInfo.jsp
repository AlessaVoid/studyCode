<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
			<title></title> 
			<!--基本选项卡start-->
			<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
			<!--基本选项卡end-->
			<!--表单验证start-->
			<script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
			<script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
			<!--表单验证end-->
			<!--自动提示框start-->
			<script src="<%=path%>/libs/js/form/suggestion.js" type="text/javascript"></script>
			<!--自动提示框end-->
	</head>
	<body>
		<form id="form1">
		<%-- <div class="basicTabModern"> --%>			
   				<table class="tableStyle" width="80%" mode="list" formMode="line">
   					<tr>
					    <td align="right" width="38%">地区编号：</td>
					    <td>
					    	${webAreaOrganInfoDTO.areaCode }
					    </td>
					</tr>
						<tr>
					    <td align="right" width="38%">地区名称：</td>
					    <td>
					    	${webAreaOrganInfoDTO.areaName }
					    </td>
					</tr>
					<tr>
			    	    <td align="right" width="38%">一级分行：</td>
					    <td>
					    	${webAreaOrganInfoDTO.provCode}
					    </td>
					</tr>
   				</table>
    	<%--</div> --%>	
	   	</form>
	</body>
</html>