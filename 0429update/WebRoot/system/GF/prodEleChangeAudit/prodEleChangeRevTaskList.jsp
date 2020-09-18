<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
	</head>
	<body>
		<!-- Grid位置 -->
		<form id="form1">
			<div id="dataBasic"></div>
		</form>
<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '6%',
									align : 'center',
									frozen:true
								},{
									display : '产品名称',
									name : 'prodName',
									width : '15%',
									align : 'center',
									frozen:true
								},{
									display : '募集起始日期(从)',
									name : 'raisingBeginDate',
									width : '9%',
									align : 'center'
								},{
									display : '募集结束日期(到)',
									name : 'raisingEndDate',
									width : '9%',
									align : 'center'
								},{
									display : '产品成立日期',
									name : 'prodBeginDate',
									width : '8%',
									align : 'center'
								},{
									display : '产品终止日期',
									name : 'prodEndDate',
									width : '8%',
									align : 'center'
								},{
									display : '期限(天)',
									name : 'prodDurationDays',
									width : '8%',
									align : 'center'
								},{
									display : '币种',
									name : 'currency',
									width : '5%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if("156"==value){
											return "人民币";
										}else if("036"==value) {
											return "澳大利亚元";
										}else if("124"==value) {
											return "加元";
										}else if("344"==value) {
											return "港币";
										}else if("392"==value) {
											return "日元";
										}else if("643"==value) {
											return "卢布";
										}else if("702"==value) {
											return "新元";
										}else if("756"==value) {
											return "瑞士法郎";
										}else if("826"==value) {
											return "英镑";
										}else if("840"==value) {
											return "美元";
										}else if("978"==value) {
											return "欧元";
										}
									}
								},{
									display : '计划募集金额（单位：万）',
									name : 'planIssueAmt',
									width : '8%',
									align : 'center',
									render : function(rowdata) {
										return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.planIssueAmt,2) + '</div>';},
								},{
									display : '客户收益率%',
									name : 'profitRate',
									width : '8%',
									align : 'center'
								},{
									display : '销售费率%',
									name : 'sellRate',
									width : '8%',
									align : 'center'
								},{
									display : '托管费%',
									name : 'prodControlFeeRate',
									width : '8%',
									align : 'center'
								},{
									display : '推荐费%',
									name : 'prodRecomRate',
									width : '8%',
									align : 'center'
								},{
									display : '超额收益费率%',
									name : 'overfulfilProfit',
									width : '8%',
									align : 'center'
								},{
									display : '投资收益率%',
									name : 'prodProfitRate',
									width : '8%',
									align : 'center'
								},{
									display : '项目名称',
									name : 'prjName',
									width : '8%',
									align : 'center'
								}],
						url : '<%=path%>/eleChangeAudit/revTaskFindPage.htm',
						sortName : '',
						enabledEdit : true,
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
							   {text : '签收', click : onRevTask, iconClass : 'icon_edit'},
							   {line : true}
							]
						}
					});
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
}
//任务签收
function onRevTask(){
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		$.post("<%=path%>/eleChangeAudit/revTask.htm",
				{"taskId":rows[0].taskId},function(result) {
			if (result.success == "true" || result.success == true) {
				top.Dialog.alert(result.msg, function() {
					top.frmright.window.location.reload(true);
					top.Dialog.close();
				});
			} else {
				top.Dialog.alert(result.msg);
			}
		}, "json");
	}
}
</script>
</body>
</html>