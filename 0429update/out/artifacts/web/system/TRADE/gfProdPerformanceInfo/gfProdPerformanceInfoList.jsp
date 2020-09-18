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
							产品代码：
						</td>
						<td width="17">
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/gfProdBaseInfo/selectGfProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							显示日期：
						</td>
						<td width="15%">
						<input type="text" class="date" dateFmt="yyyyMMdd" name="performanceDate" />
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
									width : '20%',
									align : 'center'
								},{
									display : '显示日期',
									name : 'performanceDate',
									width : '20%',
									align : 'center'
								},{
									display : '启用日期',
									name : 'performanceUseDate',
									width : '20%',
									align : 'center'
								},{
									display : '业绩比较基准值(%)',
									width : '20%',
									align : 'center',
									render : function(rowdata) {
					     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.performanceValue,2)+ '</div>';
					     			}
								},{
									display : '是否有挂钩标的',
									name : 'isPeg',
									width : '20%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="0"){
										return "否";
										}
									if(value=="1"){
										return "是";
										}
									}
								}],
						url : '<%=path%>/gfProdPerformanceInfo/findPage.htm',
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
	function formatRound(num,dp){
		return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));

	}
}
//新增
function onInsert() {
	showDialog("<%=path%>/gfProdPerformanceInfo/insertUI.htm?method=insert&id=1","新增业绩比较基准信息",500,450);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdPerformanceInfo/updateUI.htm?method=update&prodCode=" + rows[0].prodCode + "&performanceDate=" + rows[0].performanceDate,
				"修改业绩比较基准信息",500,450);
	}
}
//删除
// function onDelete() {
//	if(selectOneRow(grid)){
//		var rows = grid.getSelectedRows();
//		showDialog("<%=path%>/gfProdPerformanceInfo/deleteUI.htm?method=delete&prodCode=" + rows[0].prodCode + "&performanceDate=" + rows[0].performanceDate,
//				"删除业绩比较基准信息",500,450);
//   }
//  }
//删除
function onDelete() {
	if(selectOneRow(grid)){
	var rows = grid.getSelectedRows();
		top.Dialog.confirm("是否删除该条信息?", function() {
			$.post("<%=path%>/gfProdPerformanceInfo/deleteT.htm",{
					prodCode:rows[0].prodCode,
					performanceDate:rows[0].performanceDate
					},function(result) {
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
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdPerformanceInfo/infoUI.htm?prodCode=" + rows[0].prodCode + "&performanceDate=" + rows[0].performanceDate,
				"详细信息",250,200);
	}
}
</script>
</body>
</html>
