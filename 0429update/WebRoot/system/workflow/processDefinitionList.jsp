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
								display : '流程ID',
								name : 'id',
								align : 'center',
								width : '10%'
							},
							{
								display : '流程名称',
								name : 'name',
								align : 'center',
								width : '15%'
							},
							{
								display : '流程Key',
								name : 'key',
								align : 'center',
								width : '10%'
							},
							{
								display : '版本号',
								name : 'version',
								align : 'center',
								width : '7%'
							},
							{
								display : 'XML',
								align : 'center',
								width : '13%',
								render: function(rowdata){
									return '<a target="_blank" href="<%=path%>/workflow/showResource.htm?processDefinitionId='+ rowdata.id +'&resourceType=xml">'+rowdata.xml+'</a>';
								}
							},
							{
								display : '图片',
								name : 'image',
								align : 'center',
								width : '13%',
								render: function(rowdata){
									return '<a target="_blank" href="<%=path%>/workflow/showResource.htm?processDefinitionId='+ rowdata.id +'&resourceType=image">'+rowdata.image+'</a>';
								}
							},
							{
								display : '部署ID',
								name : 'deploymentId',
								align : 'center',
								width : '9%'
							},
							{
								display : '部署时间',
								name : 'deploymentTime',
								align : 'center',
								width : '15%'
							},
							{
								display : '状态',
								name : 'status',
								type : 'STATUS',
								align : 'center',
								width : '8%'
							}],
				url : '<%=path%>/workflow/findProcessDefinitionPage.htm',
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
			
			$.quiDefaults.Grid.formatters['STATUS'] = function(value, column) {
				var arr = value;
				if (value == "true" || value == true) {
					return "挂起";
				} else if (value == "false" || value == false) {
					return "正常";
				}
			};
		}
		
	//部署流程
	function onDeploy() {
		showDialog("<%=path%>/workflow/deployUI.htm","部署流程",600,380);
	}
	
	//删除流程
	function onDelete() {
		if(selectOneRow(grid)){
			top.Dialog.confirm("确定删除该流程吗？", function() {
				var rows = grid.getSelectedRows();
				//删除记录
				$.post("<%=path%>/workflow/delete.htm", {
					deploymentId : rows[0].deploymentId
				}, function(result) {
					if (result.success == "true" || result.success == true) {
						top.Dialog.alert(result.msg);
						grid.loadData();
					} else {
						top.Dialog.alert(result.msg);
					}
				}, "json");
				//刷新表格
				grid.loadData();
			});
		}
	}
	
	//挂起
	function onSuspend() {
		if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			if(rows[0].status == "true" || rows[0].status == true){
				top.Dialog.alert("流程状态已挂起，无需挂起！",function(){
					return;
				});
			}else{
				top.Dialog.confirm("确定挂起该流程吗？", function() {
					$.post("<%=path%>/workflow/changeStatus.htm", {
						processDefinitionId : rows[0].id,
						type : "suspend"
					}, function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, null, null, null, 5);
							grid.loadData();
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
					//刷新表格
					grid.loadData();
				});
			}
		}
	}
	
	//激活
	function onActive() {
		if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			if(rows[0].status == "false" || rows[0].status == false){
				top.Dialog.alert("流程状态正常，无需激活！",function(){
					return;
				});
			}else{
				top.Dialog.confirm("确定激活该流程吗？", function() {
					$.post("<%=path%>/workflow/changeStatus.htm", {
						processDefinitionId : rows[0].id,
						type : "active"
					}, function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, null, null, null, 5);
							grid.loadData();
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
					//刷新表格
					grid.loadData();
				});
			}
		}
	}
	//任务节点角色配置
	function onConfig() {
		if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/workflow/taskRoleConfigUI.htm?procDefId="+rows[0].id,"任务节点角色配置",800,600);
		}
	}
	//驳回角色节点配置
	function onConfigNew() {
		if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/workflow/taskRoleConfigNewUI.htm?procDefId="+rows[0].id,"驳回角色节点配置",800,600);
		}
	}
	function onNodeConfig() {
		if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		$.post("<%=path%>/workflow/nodeConfig.htm", {
			processDefinitionId : rows[0].id,
			key : rows[0].key
		}, function(result) {
			if (result.success == "true" || result.success == true) {
				top.Dialog.alert(result.msg);
			}else{
				top.Dialog.alert(result.msg);
			}
			},"json");
		}
	}
</script>
</body>
</html>