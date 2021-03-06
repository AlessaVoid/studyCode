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
	function sub() {
		var valid = $("#form1").validationEngine( {
			returnIsValid : true
		});
		if (valid) {
				if(getCheckValue() == '无'){
					top.Dialog.alert("请选择发售区域");
					}else{
		    top.Dialog.confirm("确定要保存操作吗?|操作提示",function() {
			    $.post("<%=path%>/webAreaOrganInfo/insert.htm", {
			        "organs": getCheckValue(),
			        "areaCode": $("#areaCode").val(),
			        "areaName": $("#areaName").val(),
			        "dictNo": $("#dictNo").val(),
			        "dictName": $("#dictName").val()
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
			}
		}else{
			 top.Dialog.alert("验证未通过");
		}
	}
	
$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
	createCheckBox();
})

function getCheckValue(){
    var msg = "";
    $("input:checkbox[name=provCode]").each(function(){
        if($(this).attr("checked")){
            msg += "," + $(this).val();
        }
    });
    if(msg == ""){
        msg = "无";
    }else{
        msg = msg.substring(1);
    }
    return msg;
}


function createCheckBox(){
	var provCode = ${provCode};
	if(provCode != null){
		$("#provCode").after(provCode);
	}
	$("#provCode").render();
}
</script>
	</head>
	<body>
		<form id="form1">
			<input  id="dictNo" type="hidden" name="dictNo" value="D_AREA_NAME"/>
			<input  id="dictName" type="hidden" name="dictName" value="地区管理"/>
 			<table class="tableStyle" width="80%" mode="list" formMode="line">
				<tr>
				    <td align="right" width="38%">地区编号：</td>
				    <td>
				    	<input type="text" id="areaCode" name="areaCode" class="validate[required,length[0,2],custom[noSpecialCaracters]]" maxlength="2"/>
				    	<span class="star">*</span>
				    </td>
				</tr>
				<tr>
				    <td align="right" width="38%">地区名称：</td>
				    <td>
				    	<input type="text" id="areaName" name="areaName" class="validate[required,length[0,256]]" maxlength="256"/>
				    	<span class="star">*</span>
				    </td>
				</tr>
				<tr>
		    	    <td align="right" width="38%">一级分行：</td>
				    <td>
				    	<div id="provCode"></div>
				    </td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" onclick="return sub();" class="saveButton"/>
							<button type="button" onclick="cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
 			</table>
	   	</form>
	</body>
</html>