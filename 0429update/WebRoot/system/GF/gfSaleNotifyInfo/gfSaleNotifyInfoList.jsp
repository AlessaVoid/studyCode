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
							销售通知内容：
						</td>
						<td width="20%">
							<input type="text" name="inotifyContent"/>
						</td>
						<td width="15%" align="right">
							&nbsp;
						</td>
						<td width="20%">
							&nbsp;
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
									display : '通知内容',
									name : 'inotifyContent',
									width : '40%',
									align : 'center'
								},{
									display : '操作员',
									name : 'latestOperCode',
									width : '20%',
									align : 'center'
								},{
									display : '日期',
									name : 'latestModifyDate',
									width : '20%',
									align : 'center'
								},{
									display : '时间',
									name : 'latestModifyTime',
									width : '20%',
									align : 'center'
								}],
						url : '<%=path%>/gfSaleNotifyInfo/findPage.htm',
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
var diag = new top.Dialog();
//查看详细	
function info() {
	var rows = grid.getSelectedRows();
	var rowsLength = rows.length;
	if (rowsLength == 0) {
		top.Dialog.alert("请选中需要操作的记录!");
		return;
	} else if (rowsLength > 1) {
		top.Dialog.alert("只能对一条记录进行编辑");
		return;
	} else {
		diag.URL ="<%=path%>/gfSaleNotifyInfo/infoUI.htm?notifyCode="+rows[0].notifyCode+"&ctype="+${ctype};
		diag.Title = "销售通知详细";
		diag.Width = 600;
		diag.Height = 400;
		diag.ShowMaxButton=true;
		diag.show();
	}
}

//生成产品说明书	
function add() {
	var url="<%=path%>/gfSaleNotifyInfo/insertUI.htm";
	showDialog(url,"生成产品销售通知",1000,600);
}
</script>
</body>
</html>
