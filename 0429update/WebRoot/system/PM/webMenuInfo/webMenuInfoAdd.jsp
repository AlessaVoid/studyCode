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
			<!-- 树组件start -->
			<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
			<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
			<!-- 树组件end -->
			<!-- 树形下拉框start -->
			<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
			<!-- 树形下拉框end -->
	<script>
$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
})

function changeMenuType(){
	var menuType = $("#menuType").val();
	if(menuType == "1"){//菜单
		$("#isParent").removeAttr("disabled");
		changeMenuIsParent();
	}else if(menuType == "2"){//按钮
		$("#isParent").attr("disabled","disabled");//是否为叶子节点禁用
		$("#upMenuNo").removeAttr("disabled");
		$("#isParent").removeAttr("class");
	}
	$("#isParent").render();
	$("#upMenuNo").render();
}
function changeMenuIsParent(){
	var isParent = $("#isParent").val();//是否为叶节点
	if(isParent == "0"){//是
		$("#upMenuNo").removeAttr("disabled");
	}else if(isParent == "1"){//否
		$("#upMenuNo").attr("disabled","disabled");//不是叶子节点禁用
	}
	$("#upMenuNo").render();
}
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
						    	<dic:select id="menuType" onChange="changeMenuType();" dicType="D_MENU_TYPE" name="menuType" tgClass="validate[required]"></dic:select>
						    	<span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">是否为叶节点：</td>
						    <td>
						    	<dic:select id="isParent" onChange="changeMenuIsParent();"  dicType="D_IS_PARENT" name="isParent" tgClass="validate[required]"></dic:select>
						   		<span class="star">*</span>
						    </td>
						</tr>
	   					<tr>
						    <td align="right" width="38%">菜单编号：</td>
						    <td>
								<input id="menuNo" type="text" name="menuNo" class="validate[length[0,12]]"/>
								<span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单名称：</td>
						    <td>
								<input type="text" name="menuName" class="validate[required,length[0,255]]"/>
								<span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">上级菜单：</td>
						    <td>
								<div class="selectTree" id="upMenuNo" name="upMenuNo" selWidth="230" url="<%=path%>/webMenuInfo/selectUpMenuNo.htm?menuNo=${param.menuNo}"></div>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">排序：</td>
						    <td>
								<input type="text" name="orderNo" class="validate[length[0,10]]"/>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单UrL：</td>
						    <td>
								<textarea rows="3" cols="10" name="menuUrl" class="validate[length[0,255]]"></textarea>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单图标：</td>
						    <td>
						    	<dic:select dicType="D_MENU_ICON" name="menuIcon" tgClass="validate[required]"></dic:select>
						    	<span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">菜单状态：</td>
						    <td>
						  	  <dic:select dicType="D_MENU_STATUS" name="menuStatus" tgClass="validate[required]"></dic:select>
						  	  <span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">操作员姓名：</td>
						    <td>
						  	  <input type="text" name="latestOperCode" class="validate[required]"/>
						  	  <span class="star">*</span>
						    </td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center">
									<button type="button" onclick="doSubmit('form1','<%=path %>/webMenuInfo/insert.htm')" class="saveButton"/>
									<button type="button" onclick="cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	   	</form>
	</body>
</html>