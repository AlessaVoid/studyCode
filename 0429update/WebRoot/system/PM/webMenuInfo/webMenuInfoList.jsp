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
				<table class="tableStyle"  mode="list" formMode="line" style="width: 100%;">
					<tr>
						<td width="16%" align="right">
							菜单编号：
						</td>
						<td width="16%">
							<input type="text" name="menuNo"/>
						</td>
						<td width="16%" align="right">
							菜单名称：
						</td>
						<td width="16%">
							<input type="text" name="menuName"/>
						</td>
						<td width="16%">上级菜单：</td>
						<td width="16%">
							<div class="selectTree" id="upMenuNo" name="upMenuNo" selectedValue="${webMenuInfoDTO.upMenuNo }" selWidth="230" url="<%=path%>/webMenuInfo/selectUpMenuNo.htm"></div>
						</td>
					</tr>
					<tr>
						<td width="16%">是否禁用：</td>
						<td width="16%">
							<dic:select dicType="D_MENU_STATUS" name="menuStatus"></dic:select>
						</td>
						<td>操作员：</td>
						<td>
							<input type="text" name="latestOperCode"/>
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
			          { display: '菜单名称', name: 'name', id:'menuNo', width: '28%', align: 'left' },
			          { display: '菜单编号', name: 'id',width: '28%', type: 'int', align: 'left' },
			          { display: '上级菜单', name: 'parentId', width: '28%', align: 'left' },
					  {
						display : '操作',
						isAllowHide : false,
						align : 'center',
						width : '10%',
						render : function(rowdata, rowindex, value, column) {
							return '<div class="padding_top4 padding_left5">'
									+ '<span class="img_list hand" title="查看" onclick="onInfo('+"'"+ rowdata.version+ "'"+','+ "'"+ rowdata.parentId+ "'"+')"></span>'
									+ '</div>';
						}
					  }], 
			          url : '<%=path%>/webMenuInfo/findPage.htm',
			          height: '100%', 
			          width:"100%",
			          checkbox:true,
			          usePager: false,
			          tree: { columnId: 'menuNo' },
						toolbar : {
							items : [
								${btnList}
							]
						}
					});

}
//新增
function onInsert() {
	showDialog("<%=path%>/system/PM/webMenuInfo/webMenuInfoAdd.jsp","新增信息",600,450);
}
//修改
function onUpdate() {
	var rows = grid.getSelectedRows();
	showDialog("<%=path%>/webMenuInfo/updateUI.htm?id="+rows[0].version,"修改信息",600,450);
}
//删除
function onDelete() {
	var rows = grid.getSelectedRows();
	var result = selectOneRow(grid);
	if(result == true){
		top.Dialog.confirm("是否删除该条信息?", function() {
			$.post("<%=path%>/webMenuInfo/deleteWebMenuInfo.htm",
				{id:rows[0].version,menuNo:rows[0].id},function(result) {
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
//查看详细
function onInfo(version,parentId) {
	var rows = grid.getSelectedRows();
	var id= rows[0].version;
	var upMenuNo =  rows[0].parentId;
	var url = "<%=path%>/webMenuInfo/infoUI.htm?id="+id+"&upMenuNo="+upMenuNo;
	showDialog(url,"详细信息",600,450);
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
