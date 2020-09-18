<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<input type="hidden" id="msgNo" value="${msgNo }"/>
		<!-- Grid位置 -->
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	top.Dialog.close();
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
						url : '<%=path%>/scheduleAudit/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
}

//档期安排
function onAudit() {
	var rows = grid.getSelectedRows();
	var length = rows.length;
	if(length != 1){
		top.Dialog.alert("请选择一个档期!");
		return;
	}
	showDialog("<%=path%>/scheduleAudit/auditUI.htm?taskIds=" + rows[0].taskIds + "&scheduleCode=" + rows[0].scheduleCode,"档期审批",1380,680);
}
//详细信息
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/scheduleAudit/infoUI.htm?taskIds=" + rows[0].taskIds + "&scheduleCode=" + rows[0].scheduleCode + "&processInstanceIds="+rows[0].processInstanceIds,"档期详细信息",1280,680);
	}
}
//查看流程图
function onViewImg() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/schedule/imageUI.htm?taskIds=" + rows[0].taskIds + "&processInstanceIds="+rows[0].processInstanceIds,"当前流程图",1380,680);
	}
}
//待签收任务列表
function onRevTask(){
	showDialog("<%=path%>/scheduleAudit/revTaskUI.htm?msgNo="+$("#msgNo").val(),"待签收任务",1280,580);
}
//档期审批参考表
function onReferenceTable(){
if(selectOneRow(grid)){
	var rows = grid.getSelectedRows();
		var taskId = rows[0].taskIds;
		showDialog("<%=path%>/scheduleAudit/scheduleRefTable.htm?taskIds=" + rows[0].taskIds, "到期参考表",1280,680);
	}
}
</script>
</body>
</html>
