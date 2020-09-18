<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<!--表单异步提交start-->
		<script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
		<!--表单异步提交end-->
		<!-- 日期选择框start -->
		<script src="<%=path%>/libs/js/form/datePicker/WdatePicker.js" type="text/javascript"></script>
		<!-- 日期选择框end -->
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
	</head>
	<body>
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="" id="queryForm" method="post">
				<input type="hidden" name="dictNo" value="D_AREA_NAME"/>
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="27%" align="right">
							地区编码：
						</td>
						<td width="26%">
							<input type="text" name="dictKeyIn"/>
						</td>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="searchHandler()">
								<span class="icon_find">查询</span>
								</button>
								<button type="button" onclick="resetSearch()">
									<span class="icon_reload">重置</span>
								</button>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
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
						columns : [
							{
								display : '地区名称',
								name : 'dictValueIn',
								width : '50%',
								align : 'center'
							},
								{
									display : '地区编码',
									name : 'dictKeyIn',
									width : '50%',
									align : 'center'
								}],
						url : '<%=path%>/webAreaOrganInfo/findPage.htm',
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
	
	//监听查询框的回车事件
	$("#searchInput").keydown(function(event) {
		if (event.keyCode == 13) {
			searchHandler();
		}
	});
}
//新增
function onInsert() {
	showDialog("<%=path%>/webAreaOrganInfo/insertUI.htm","新增信息",750,350);
}

//修改
function onUpdate() {
	if(selectOneRow(grid) == true){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webAreaOrganInfo/updateUI.htm?areaCode="+rows[0].dictKeyIn+"&areaName="+rows[0].dictValueIn,"修改信息",750,350);
	}
}
//查看详细
function onInfo() {
	if(selectOneRow(grid) == true){
		var rows = grid.getSelectedRows();
		var url = "<%=path%>/webAreaOrganInfo/infoUI.htm?areaName="+rows[0].dictValueIn+"&areaCode="+rows[0].dictKeyIn;
		showDialog(url,"详细信息",750,350);
	}
}
//删除
function onDelete() {
	var rows = grid.getSelectedRows();
	var result = selectOneRow(grid);
	if(result == true){
		top.Dialog.confirm("是否删除该条信息?", function() {
			$.post("<%=path%>/webAreaOrganInfo/deleteWebAreaOrgan.htm",
				{areaCode:rows[0].dictKeyIn,"dictNo":"D_AREA_NAME","dictName":"地区管理"},function(result) {
						if (result.success == "true"|| result.success == true) {
							top.Dialog.alert(result.msg, function() {
								top.frmright.window.location.reload(true);
								top.Dialog.close();
						});
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
		});
	}
}
//查询
function searchHandler() {
	var query = $("#queryForm").formToArray();
	//alert(JSON.stringify(query))
	grid.setOptions( {
		params : query
	});
	//页号重置为1
	grid.setNewPage(1);
	grid.loadData();//加载数据
}
//重置查询
function resetSearch() {
	$("#queryForm")[0].reset();
	$('#search').click();
}
//禁用回车键
document.onkeypress=function(){
   if(event.keyCode==13){
	   return false;
	}
}
//刷新表格数据并重置排序和页数
function refresh(isUpdate) {
	if (!isUpdate) {
		//重置排序
		grid.options.sortName = 'appDate';
		grid.options.sortOrder = "desc";
		//页号重置为1
		grid.setNewPage(1);
	}
	grid.loadData();
}
</script>
	</body>
</html>
