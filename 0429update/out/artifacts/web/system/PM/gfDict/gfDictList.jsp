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
							分组英文名：
						</td>
						<td width="17">
							<div class="suggestion" id="gfDictNo" name="dictNo" matchName="dictNo" 
									url="<%=path%>/gfDict/selectDictNo.htm" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							分组中文名：
						</td>
						<td width="17">
							<div class="suggestion" id="gfDictName" name="dictName" matchName="dictName" 
									url="<%=path%>/gfDict/selectDictName.htm" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							创建人员：
						</td>
						<td width="17">
							<div class="suggestion" id="gfCreateOper" name="createOper" matchName="createOper" 
									url="<%=path%>/gfDict/selectCreateOper.htm" suggestMode="remote"></div>
						</td>
						<td>
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
									display : '分组英文名',
									name : 'dictNo',
									width : '20%',
									align : 'center'
								},{
									display : '分组中文名',
									name : 'dictName',
									width : '20%',
									align : 'center'
								},{
									display : '键-内部',
									name : 'dictKeyIn',
									width : '20%',
									align : 'center'
								},{
									display : '值-内部',
									name : 'dictValueIn',
									width : '20%',
									align : 'center'
								},{
									display : '创建人员',
									name : 'createOper',
									width : '20%',
									align : 'center'
								}],
						url : '<%=path%>/gfDict/findPage.htm',
						sortName : 'dict_No_Order',
						sortOrder:'desc',
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
//新增
function onInsert() {
	showDialog("<%=path%>/gfDict/insertUI.htm?method=insert&id=1","新增字典信息",650,300);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfDict/updateUI.htm?method=update&dictNo=" + rows[0].dictNo + "&dictName=" + rows[0].dictName + "&dictKeyIn=" + rows[0].dictKeyIn,
				"修改字典信息",650,300);
	}
}
//复制新增
function onCoyp() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfDict/coypUI.htm?dictNo=" + rows[0].dictNo + "&dictName=" + rows[0].dictName + "&dictKeyIn=" + rows[0].dictKeyIn,
				"复制新增字典信息",650,300);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		top.Dialog.confirm("确定要删除该记录吗？", function() {
			//删除记录
			$.post("<%=path%>/gfDict/delete.htm", {
				dictNo : rows[0].dictNo,
				dictName : rows[0].dictName,
				dictKeyIn : rows[0].dictKeyIn,
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
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfDict/infoUI.htm?dictNo=" + rows[0].dictNo + "&dictName=" + rows[0].dictName + "&dictKeyIn=" + rows[0].dictKeyIn,
				"字典详细信息",550,200);
	}
}
</script>
</body>
</html>
