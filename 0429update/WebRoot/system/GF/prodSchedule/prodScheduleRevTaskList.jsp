<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
	</head>
	<body>
		<!-- Grid位置 -->
		<form id="form1">
			<div id="dataBasic"></div>
		</form>
<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [{
							display : '档期编号',
							name : 'scheduleCode',
							width : '30%',
							align : 'center'
						},
						{
							display : '档期描述',
							name : 'scheduleDesc',
							width : '40%',
							align : 'center'
						},
						{
							display : '设计日期',
							name : 'latestModifyDate',
							width : '30%',
							align : 'center'
						}],
				url : '<%=path%>/scheduleAudit/revTaskFindPage.htm',
				sortName : '',
				rownumbers : true,
				checkbox : true,
				height : '100%',
				width : "100%",
				pageSize : 300,
				toolbar : {
					items : [
					   {text : '签收', click : onRevTask, iconClass : 'icon_edit'},
					   {line : true}
					]
				}
			});
}
//任务签收
function onRevTask(){
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		$.post("<%=path%>/scheduleAudit/revTask.htm",
				{"taskIds":rows[0].taskIds},function(result) {
			if (result.success == "true" || result.success == true) {
				top.Dialog.alert(result.msg, function() {
					top.frmright.window.location.reload(true);
					top.Dialog.close();
				});
			} else {
				top.Dialog.alert(result.msg);
			}
		}, "json");
	}
}
</script>
</body>
</html>