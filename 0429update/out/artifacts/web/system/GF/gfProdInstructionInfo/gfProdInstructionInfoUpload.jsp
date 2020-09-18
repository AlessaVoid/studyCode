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
			<script src="<%=path%>/libs/js/jquery-form.js" type="text/javascript"></script>
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

function sub(){
	top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
		var form = $("#form1");  
        var options  = {    
	        url:'<%=path%>/gfProdInstructionInfo/upLoad.htm',    
	        type:'post',
	        success:function(result)    
	        {    
	            if(result.success == "true" || result.success==true){  
	            	top.Dialog.alert(result.msg, function() {
	        			top.frmright.window.location.reload(true);
	        			top.Dialog.close();
	        		});
	            }else{ 
	            	buttonUnluck();
	            	top.Dialog.alert(result.msg);
	            }  
	        },
	        dataType:'json'
        };    
        form.ajaxSubmit(options);  
	});
}
</script>
	</head>
	<body>
		<form id="form1" action="" method="post">
			<table class="tableStyle" width="80%" mode="list" formMode="line" enctype="multipart/form-data">
				<tr>
					<td align="right">产品代码： </td>
					<td>
						<input type="text" value="" name="prodCode" id="prodCode" class="validate[required]"/>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="210">产品说明书： </td>
					<td>
						<input type="file" name="instruction" id="instruction" class="validate[required]" keepDefaultStyle="true" />
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return sub()" class="saveButton" />
							<button type="button" onclick="return cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>