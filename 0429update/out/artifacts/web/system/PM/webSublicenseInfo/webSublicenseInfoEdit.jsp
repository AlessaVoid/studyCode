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
			<!-- 双向选择器start -->
			<script type="text/javascript" src="<%=path%>/libs/js/form/lister.js"></script>
			<!-- 双向选择器end -->
	<script>
$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
})

function getWebSub() {
	$.post("<%=path%>/webSublicenseInfo/selectWebSub.htm", {
		inOper : $("#inOper").val()
	}, function(result) {
		if (result.success == "true" || result.success == true) {
			$("#lister").setValue(result.msg);
			$("#lister").render();
		} else {
			$("#lister").setValue("");
			$("#lister").render();
			top.Dialog.alert(result.msg);
		}
	}, "json");
};

function sub() {
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	if (valid) {
	    top.Dialog.confirm("确定要保存操作吗?|操作提示",function() {
		    $.post("<%=path%>/webSublicenseInfo/outWebSub.htm", {
		        "outOper": $("#outOper").val(),
		        "inOper": $("#inOper").val(),
		        "lister": $("#lister").attr("relValue")
		    },
		    function(result) {
		        if (result.success == "true" || result.success == true) {
		            top.Dialog.alert(result.msg,function() {
		            	top.frmright.window.location.reload(true);
						top.Dialog.close();
		            });
		        } else {
		            top.Dialog.alert(result.msg);
		        }
		    },"json");
		});
	}else{
		top.Dialog.alert("验证未通过");
	}
}
</script>
	</head>
	<body>
		<form id="form1">
   			<div name="转授权信息" style="width:100%;height:80%;">
   				<table class="tableStyle" width="80%" mode="list" formMode="line">
   					<tr>
   						<td align="right" width="30%">转出柜员：</td>
   						<td>
   							${sessionUser.opercode }
   							<input id="outOper" name="outOper" type="hidden" value="${sessionUser.opercode }"/>
						</td>
					</tr>
					<tr>
   						<td align="right" width="30%">转入柜员：</td>
   						<td>
   							<input id="inOper" onblur="getWebSub()" name="inOper" type="text" class="validate[length[0,11]]" maxlength="20"/>
						</td>
					</tr>
   					<tr>
   						<td>操作：</td>
   						<td>
					    	<div fromTitle="可转出角色" toTitle="可收回角色" class="lister" id="lister" url="<%=path%>/webSublicenseInfo/findWebSub.htm"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="return sub()" class="saveButton"/>
								<button type="button" onclick="return cancel()" class="cancelButton" />		
							</div>
						</td>
					</tr>
   				</table>
   			</div>
	   	</form>
	</body>
</html>