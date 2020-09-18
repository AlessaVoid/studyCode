<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
    top.Dialog.confirm("确定要保存操作吗?|操作提示",function() {
	    $.post("<%=path%>/pmrgtRoleInfo/setCheckPermission.htm", {
	        "funNos": getCheckedNodes(),
	        "roleNo": "${roleNo}"
	    },
	    function(result) {
	        if (result.success == "true" || result.success == true) {
	            top.Dialog.alert(result.msg,function() {
	                closeWin();
	            });
	        } else {
	            top.Dialog.alert(result.msg);
	        }
	    },"json");
	});
}
</script>
	</head>
	<body>
		<div id="maindiv">
			<form id="form1">
				<fieldset>
					<legend>
						角色复核对照表信息
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
								<input type="button" value="保存" onclick="return sub()"/>
								<input type="reset" value="重置" />
							</td>
						</tr>
					</table>
				</div>
				<br />
				<br />
			</form>
		</div>
	</body>
</html>