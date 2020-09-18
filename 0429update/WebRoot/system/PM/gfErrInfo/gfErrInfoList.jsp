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
							本系统错误码：
						</td>
						<td width="17%">
							<input type="text" name="gfRetCode"/>
						</td>
						<td width="10%" align="right">
							本系统错误信息：
						</td>
						<td width="17%">
							<input type="text" name="gfRetInfo"/>
						</td>
					</tr>
					<tr>
						<td width="10%" align="right">
							外系统错误码：
						</td>
						<td width="17%">
							<input type="text" name="otherRetCode"/>
						</td>
						<td width="10%" align="right">
							外系统错误信息：
						</td>
						<td width="17%">
							<input type="text" name="otherRetInfo"/>
						</td>
					</tr>
					<tr>
						<td colspan="4">
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
									display : '错误码',
									name : 'gfRetCode',
									width : '20%',
									align : 'center'
								},{
									display : '错误信息',
									name : 'gfRetInfo',
									width : '20%',
									align : 'center'
								},{
									display : '外系统代码',
									name : 'otherSysCode',
									width : '20%',
									align : 'center'
								},{
									display : '外系统错误码',
									name : 'otherRetCode',
									width : '20%',
									align : 'center'
								},{
									display : '外系统错误信息',
									name : 'otherRetInfo',
									width : '20%',
									align : 'center'
								}],
						url : '<%=path%>/gfErrInfo/findPage.htm',
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
	showDialog("<%=path%>/gfErrInfo/insertUI.htm?method=insert","新增错误信息",550,350);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfErrInfo/updateUI.htm?method=update&gfRetCode=" + rows[0].gfRetCode+"&gfSysCode="+rows[0].gfSysCode+"&otherSysCode="+rows[0].otherSysCode+"&otherRetCode="+rows[0].otherRetCode,
				"修改错误信息",550,350);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		top.Dialog.confirm("确定要删除该记录吗？", function() {
			//删除记录
			$.post("<%=path%>/gfErrInfo/delete.htm", {
				gfRetCode : rows[0].gfRetCode,
				gfSysCode : rows[0].gfSysCode,
				otherSysCode : rows[0].otherSysCode,
				otherRetCode : rows[0].otherRetCode
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
		showDialog("<%=path%>/gfErrInfo/infoUI.htm?gfRetCode=" + rows[0].gfRetCode+"&gfSysCode="+rows[0].gfSysCode+"&otherSysCode="+rows[0].otherSysCode+"&otherRetCode="+rows[0].otherRetCode,
				"错误详细信息",550,320);
	}
}
</script>
</body>
</html>
