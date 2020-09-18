<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<!-- Grid位置 -->
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5" >
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
				columns : [
						{
							display : '手续费审批批次号',
							name : 'feeSerialCode',
							align : 'cennter',
							width : '25%'

						},
						{
							display : '起始时间',
							name : 'beginDate',
							align : 'cennter',
							width : '15%'
						},
						{
							display : '截止时间',
							name : 'endDate',
							align : 'cennter',
							width : '15%'
						},{
							display : '手续费描述',
							name : 'feeSerialDesc',
							align : 'cennter',
							width : '15%'

						},{
							display : '分配金额',
							name : 'fpamt',
							align : 'cennter',
							width : '15%'
						},
						{
							display : '申请时间',
							name : 'latestModifyDate',
							align : 'cennter',
							width : '15%'
						}],
						url : '<%=path%>/gfFeeDisAudit/findPage.htm',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
}

//审批
function onAudit() {
	var rows = grid.getSelectedRows();
	if(rows.length != 1){
		top.Dialog.alert("请至少选择一条记录！");
	}else {
		showDialog("<%=path%>/gfFeeDisAudit/auditUI.htm?feeSerialCode="+rows[0].feeSerialCode+"&taskId=" + rows[0].taskId, "手续费审批",1280,680);
	}
}

//详细信息
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfFeeDisAudit/infoUI.htm?feeSerialCode="+rows[0].feeSerialCode+"&taskId=" + rows[0].taskId, "手续费分配详细",1280,680);
	}
}

//查看流程图
function onViewImg() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/workflow/imageUI.htm?taskId=" + rows[0].taskId + "&processInstanceId="+rows[0].processInstanceId,"当前流程图",1380,680);
	}
}
</script>
</body>
</html>
