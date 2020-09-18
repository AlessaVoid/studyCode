`<%@page language="java" pageEncoding="UTF-8"%>
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
				<td width="16%" align="right">
					操作员编号：
				</td>
				<td width="20%">
					<input type="text" name="opercode"/>
				</td>
				<td>操作员姓名：</td>
				<td width="20%">
					<input type="text" name="opername"/>
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
									display : '机构代码',
									name : 'organcode',
									width : '25%',
									align : 'center'
								},{
									display : '操作员编号',
									name : 'opercode',
									width : '25%',
									align : 'center'
								},{
									display : '操作员姓名',
									name : 'opername',
									width : '25%',
									align : 'center'
								},{
									display : '最后修改时间',
									name : 'modifyDate',
									width : '25%',
									align : 'center'
								}],
							url : '<%=path%>/fdOper/findPage.htm',
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
		showDialog("<%=path%>/fdOper/insertUI.htm?method=insert&id=1","新增信息",600,380);
	}
	//修改
	function onUpdate() {
		if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			showDialog("<%=path%>/fdOper/updateUI.htm?method=update&opercode=" + rows[0].opercode + "&organcode=" + rows[0].organcode,
					"修改信息",600,380);
		}
	}
	//删除
	function onDelete() {
		if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			top.Dialog.confirm("确定要删除该记录吗？", function() {
				//删除记录
				$.post("<%=path%>/fdOper/delete.htm", {
					opercode : rows[0].opercode,
					organcode : rows[0].organcode
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
			showDialog("<%=path%>/fdOper/infoUI.htm?opercode=" + rows[0].opercode + "&organcode=" + rows[0].organcode,
					"详细信息",600,380);
		}
	}
</script>
</body>
</html>
