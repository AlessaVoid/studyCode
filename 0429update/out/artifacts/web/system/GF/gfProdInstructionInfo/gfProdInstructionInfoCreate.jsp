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
function doCreate(formId,url){
	top.Dialog.confirm("确定要生成产品说明说明书吗?|操作提示", function() {
		$.post(url,$("#"+formId).serialize(),function(result){
			 if(result.success == "true" || result.success==true){  
	            	top.Dialog.alert(result.msg, function() {
	        			top.frmright.window.location.reload(true);
	        			top.Dialog.close();
	        		});
	            }else{ 
	            	buttonUnluck();
	            	top.Dialog.alert(result.msg);
	            }  
		},"json");
	});
}
</script>
	</head>
	<body>
		<form id="form1" action="" method="post">
			<table class="tableStyle" width="80%" mode="list" formMode="line">
				<tr>
					<td align="right">产品代码： </td>
					<td>
						<div class="suggestion" name="prodCode" matchName="prodCode" url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
								<button type="button" onclick="return doCreate('form1','<%=path%>/gfProdInstructionInfo/createFile.htm')"><span class="icon_ok">生成</span></button>
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>