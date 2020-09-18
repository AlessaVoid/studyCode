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
						<td width="15%" align="right">
							销售商名称：
						</td>
						<td width="17">
							<div class="suggestion" id="gfRetailerName" name="retailerName" matchName="retailerName" 
									url="<%=path%>/gfRetailerBasicInfo/selectRetailerName.htm" suggestMode="remote"></div>
						</td>
						<td width="15%" align="right">
							销售商类型：
						</td>
						<td width="20%">
							<dic:select dicType="retailer_type" name = "retailerType" id="retailerType"></dic:select>
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
									display : '销售商代码',
									name : 'retailerCode',
									width : '25%',
									align : 'center'
								},{
									display : '销售商名称',
									name : 'retailerName',
									width : '25%',
									align : 'center'
								},{
									display : '销售商类型',
									name : 'retailerType',
									width : '25%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="2"){
										return "代销";
										}
									if(value=="1"){
										return "直销";
										}
									}
								},{
									display : '状态',
									name : 'status',
									width : '25%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="0"){
										return "未准入";
										}
									if(value=="1"){
										return "准入";
										}
									if(value=="2"){
										return "停用";
										}
									}
								}],
						url : '<%=path%>/gfRetailerBasicInfo/findPage.htm',
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
//新增
function onInsert() {
	showDialog("<%=path%>/gfRetailerBasicInfo/insertUI.htm?method=insert&id=1","新增销售商信息",500,450);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfRetailerBasicInfo/updateUI.htm?method=update&retailerCode=" + rows[0].retailerCode,
				"修改销售商信息",500,450);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfRetailerBasicInfo/deleteUI.htm?method=delete&retailerCode=" + rows[0].retailerCode,
				"删除销售商信息",500,450);
	}
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfRetailerBasicInfo/infoUI.htm?retailerCode=" + rows[0].retailerCode,
				"详细信息",500,450);
	}
}
</script>
</body>
</html>
