<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_info.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
			<title></title> 
			<!--基本选项卡start-->
			<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
			<!--基本选项卡end-->
			
			<!--表单验证start-->
			<script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
			<script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
			<!--表单验证end-->
	<script>
$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
})

</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" id="type" name="type" value="${type}"/>
			<div class="basicTabModern">
	   			<div name="菜单信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
						    <td align="right" width="38%">菜单类型：</td>
						    <td>
						    	<dic:out dicType="D_MENU_TYPE" dicNo="${webMenuInfoDTO.menuType}"></dic:out>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">是否为叶节点：</td>
						    <td>
						    	<dic:out dicType="D_IS_PARENT" dicNo="${webMenuInfoDTO.isParent}"></dic:out>
						    </td>
						</tr>
	   					<tr>
						    <td align="right" width="38%">菜单编号：</td>
						    <td>
								${webMenuInfoDTO.menuNo}
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单名称：</td>
						    <td>
								${webMenuInfoDTO.menuName}
						    </td>
						</tr>
						<tr>
							<td align="right" width="38%">上级菜单：</td>
						    <td>
								${webMenuInfoDTO.upMenuName }
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">排序：</td>
						    <td>
								${webMenuInfoDTO.orderNo}
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单UrL：</td>
						    <td>
								${webMenuInfoDTO.menuUrl}
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单图标：</td>
						    <td>
								<dic:out dicType="D_MENU_ICON" dicNo="${webMenuInfoDTO.menuIcon }"></dic:out>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单状态：</td>
						    <td>
						    	<dic:out dicType="D_MENU_STATUS" dicNo="${webMenuInfoDTO.menuStatus }"></dic:out>
						    </td>
						</tr>
						<tr>
						  <td align="right" width="38%">最后操作员：</td>
						    <td>
								${webMenuInfoDTO.latestOperCode}
						    </td>
						</tr>
					</table>
				</div>
    		</div>
	   	</form>
	</body>
</html>