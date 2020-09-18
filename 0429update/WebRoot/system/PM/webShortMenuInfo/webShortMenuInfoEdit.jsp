<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>角色维护</title> 
		<!-- 树组件 -->
		<link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<script type="text/javascript">
$(function() {
	//复核框
	var setting1 = {
	    check: {
	        enable: true
	    }
	};
	//树节点
	var zNodes = ${treeNodes};
	var treeObj = $.fn.zTree.init($("#tree"), setting1, zNodes);
});

//获取选中节点
function getCheckedNodes(){
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	var checkedNodes = treeObj.getCheckedNodes(true);
	var msg = "";
	for(var i = 0; i < checkedNodes.length; i++){
		msg += "," + checkedNodes[i].id;
	}
	if(msg == ""){
		msg = "无选择";
	}else{
	    msg = msg.substring(1);
	}
	return msg;
}

function sub() {
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	if (valid) {
	    top.Dialog.confirm("确定要保存操作吗?|操作提示",function() {
		    $.post("<%=path%>/webShortMenuInfo/update.htm", {
		        "funCode": getCheckedNodes(),
		        "operCode": $("#opercode").val(),
		        "operName": $("#opername").val()
		    },
		    function(result) {
		        if (result.success == "true" || result.success == true) {
		            top.Dialog.alert(result.msg,function() {
		            	top.window.location.reload(true);
						top.Dialog.close();
		            });
		        } else {
		            top.Dialog.alert(result.msg);
		        }
		    },"json");
		});
	}
}
</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="opercode" id="opercode"  value="${sessionUser.opercode }"/>
			<input type="hidden" name="opername" id="opername"  value="${sessionUser.opername }"/>
			<div name="快捷菜单信息" style="width:100%;height:80%;">
   				<fieldset>
					<legend>
						快捷菜单信息
					</legend>
					<div>
						<ul id="tree" class="ztree"></ul>
					</div>
				</fieldset>
				<div class="height_15"></div>
				<div class="height_5"></div>
				<div class="padding_top10">
					<table class="tableStyle" formMode="transparent">
						<tr>
							<td colspan="4">
								<button type="button" onclick="return sub()" class="saveButton"/>
								<button type="button" onclick="return cancel()" class="cancelButton" />	
							</td>
						</tr>
					</table>
				</div>
				<br />
				<br />
   			</div>
	   	</form>
	</body>
</html>