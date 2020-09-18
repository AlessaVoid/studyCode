<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
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
									display : '期限(天)',
									name : 'period',
									width : '25%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="1"){
										return "7天（含）以内";
										}
									if(value=="2"){
										return "7天-1个月（含）";
										}
									if(value=="3"){
										return "1-3个月（含）";
										}
									if(value=="4"){
										return "3-6个月（含）";
										}
									if(value=="5"){
										return "6-12个月（含）";
										}
									if(value=="6"){
										return "1年以上";
										}
									}
								},{
									display : '预期最低收益率(%)',
									name : 'minimumRate',
									width : '25%',
									align : 'center',
									render : function(rowdata) {
										return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.minimumRate,2) + '</div>';},
								},{
									display : '产品期限下限(天)',
									name : 'periodDown',
									width : '25%',
									align : 'center'
								},{
									display : '产品期限上限(天)',
									name : 'periodUp',
									width : '25%',
									align : 'center'
								}],
						url : '<%=path%>/webPeriodMinRate/findPage.htm',
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
	showDialog("<%=path%>/webPeriodMinRate/insertUI.htm?method=insert&id=1","新增期限收益率信息",300,150);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webPeriodMinRate/updateUI.htm?period=" + rows[0].period,
				"修改期限收益率信息",300,150);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		top.Dialog.confirm("确定要删除该记录吗？", function() {
			//删除记录
			$.post("<%=path%>/webPeriodMinRate/delete.htm", {
				period : rows[0].period
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
		showDialog("<%=path%>/gfProdBrandInfo/infoUI.htm?brandCode=" + rows[0].brandCode,
				"详细信息",600,380);
	}
}
</script>
</body>
</html>
