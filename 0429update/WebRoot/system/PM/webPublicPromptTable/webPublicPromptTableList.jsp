<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<!-- 查询位置 -->
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="" id="queryForm" method="post">
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="10%" align="right">
							日期：
						</td>
						<td width="10%">
						<input type="text" name="operDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
								<button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
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
						columns : [
								{
									display : 'id',
									name : 'id',
									width : '25%',
									align : 'center'
								},{
									display : '公告内容',
									name : 'content',
									width : '25%',
									align : 'center'
								},{
									display : '创建时间',
									name : 'operDate',
									width : '25%',
									align : 'center'
								},{
									display : '状态',
									name : 'useStatus',
									width : '25%',
									align : 'center',
									  render : function(rowdata) {
						        		   if (rowdata.useStatus == "1") {
						        				return "启用";
						        			}else if (rowdata.useStatus == "2"){
						        				return "停用";
						        			}
						      			}
								}],
						url : '<%=path%>/webPublicPromptTable/findPage.htm',
						sortName : '',
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
//新增
function onInsert() {
	showDialog("<%=path%>/webPublicPromptTable/insertUI.htm?method=insert","新增公告提示信息",550,350);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webPublicPromptTable/updateUI.htm?id="+rows[0].id,
				"修改公告提示信息",550,350);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		top.Dialog.confirm("确定要删除该记录吗？", function() {
			//删除记录
			$.post("<%=path%>/webPublicPromptTable/delete.htm", {
				id : rows[0].id
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
//启动
function onRefresh() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
			$.post("<%=path%>/webPublicPromptTable/refresh.htm", {
				id : rows[0].id
			}, function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg);
					grid.loadData();
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
	}
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webPublicPromptTable/infoUI.htm?id="+rows[0].id,
				"公告提示详细信息",550,320);
	}
}
</script>
</body>
</html>
