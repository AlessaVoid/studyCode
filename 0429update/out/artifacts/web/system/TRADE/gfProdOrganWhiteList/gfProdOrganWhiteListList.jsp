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
							产品代码 ：
						</td>
						<td width="17">
							<div class="suggestion" id="gfProdCode" name="prodCode" matchName="prodCode" 
									url="<%=path%>/gfProdOrganWhiteList/selectProdCode.htm" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							机构代码：
						</td>
						<td width="17">
							<div class="suggestion" id="gfOrganCode" name="organCode" matchName="organCode" 
									url="<%=path%>/gfProdOrganWhiteList/selectOrganCode.htm" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							状态：
						</td>
						<td width="15%">
							<dic:select dicType="USE_STATUS" name="status" id="status"></dic:select>
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
									display : '产品代码',
									name : 'prodCode',
									width : '33%',
									align : 'center'
								},{
									display : '机构代码',
									name : 'organCode',
									width : '33%',
									align : 'center'
								},{
									display : '状态',
									name : 'status',
									width : '33%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="1"){
										return "启用";
										}
									if(value=="2"){
										return "停用";
										}
									}
								}],
						url : '<%=path%>/gfProdOrganWhiteList/findPage.htm',
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
	showDialog("<%=path%>/gfProdOrganWhiteList/insertUI.htm?method=insert&id=1","设置白名单信息",500,300);
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdOrganWhiteList/deleteUI.htm?method=delete&prodCode=" + rows[0].prodCode + "&organCode="+rows[0].organCode,
				"设置黑名单",500,450);
	}
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdOrganWhiteList/infoUI.htm?prodCode=" + rows[0].prodCode + "&organCode="+rows[0].organCode,
				"机构黑白名单信息",300,150);
	}
}
//导入
function onShow() {
	showDialog("<%=path%>/gfProdOrganWhiteList/showUI.htm?method=show&id=1","机构黑白名单信息",500,130);
}

//解析参数模板
function importParamsInfo(fileName) {
	top.Dialog.close();
	showDialog("<%=path%>/gfProdOrganWhiteList/gfProdOrganWhiteListXsl.htm?fileName=" + fileName,
				"模板导入",1200,1000);
}

</script>
</body>
</html>
