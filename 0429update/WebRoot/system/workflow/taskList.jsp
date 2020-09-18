<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程模板管理</title>
</head>

<body>
	<div class="padding_right5">
		<div class="box2_custom" boxType="box2" panelTitle="流程模板"
			class="padding_right5">
			<div id="dataBasic"></div>
		</div>
	</div>
<script type="text/javascript">
		var grid = null;
		function initComplete() {
			//当提交表单刷新本页面时关闭弹窗
			top.Dialog.close();
			grid = $("#dataBasic").quiGrid({
				columns : [{
								display : '任务ID',
								name : 'id',
								align : 'center',
								width : '20%'
							},
							{
								display : '任务名称',
								name : 'name',
								align : 'center',
								width : '20%'
							},
							{
								display : '创建时间',
								name : 'createTime',
								align : 'center',
								width : '20%'
							},
							{
								display : '办理者',
								name : 'assignee',
								align : 'center',
								width : '20%'
							},
							{
								display : '任务描述',
								name : 'description',
								align : 'center',
								width : '20%'
							}],
				url : '<%=path%>/workflow/findTaskPage.htm?processDefinitionKey=${processDefinitionKey}',
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
	
	//办理任务
	function onAudit() {
		if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			showDialog("<%=path%>/workflow/viewTaskForm.htm?taskId=" + rows[0].id,"任务办理",600,380);
		}
	}
	
	//查看当前流程图
	function onViewImg() {
		if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			showDialog("<%=path%>/workflow/imageUI.htm?taskId=" + rows[0].id + "&processInstanceId="+rows[0].processInstanceId,"当前流程图",600,380);
		}
	}
</script>
</body>
</html>