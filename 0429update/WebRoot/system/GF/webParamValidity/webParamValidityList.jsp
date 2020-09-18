<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<!--表单异步提交start-->
		<script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
		<!--表单异步提交end-->
		<!-- 日期选择框start -->
		<script src="<%=path%>/libs/js/form/datePicker/WdatePicker.js" type="text/javascript"></script>
		<!-- 日期选择框end -->
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
	</head>
	<body>
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="" id="queryForm" method="post">
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="25%" align="right">
							功能编码：
						</td>
						<td width="25%">
							<dic:select name="tradeType" dicType="D_PARAM_VALIDITY"></dic:select>
						</td>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="searchHandler()">
								<span class="icon_find">查询</span>
								</button>
								<button type="button" onclick="resetSearch()">
									<span class="icon_reload">重置</span>
								</button>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
		<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [
								{
									display : '交易类型',
									name : 'tradeType',
									width : '20%',
									align : 'center',
									type : 'D_PARAM_VALIDITY'
								},{
									display : '开始时间',
									name : 'beginTime',
									width : '16%',
									align : 'center'
								},{
									display : '终止时间',
									name : 'endTime',
									width : '16%',
									align : 'center'
								},{
									display : '手续费率上限(%)',
									name : 'maxFeeRate',
									width : '16%',
									align : 'center',
									type : 'D_FEE_RATE'
								},{
									display : '手续费率下限(%)',
									name : 'minFeeRate',
									width : '16%',
									align : 'center',
									type : 'D_FEE_RATE'
								},{
									display : '最低投资额（万）',
									name : 'lowestInvestAmt',
									width : '16%',
									align : 'center',
									type : 'D_LOWESTINVESTAMT'
								}],
						url : '<%=path%>/webParamValidity/findPage.htm',
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
	$.quiDefaults.Grid.formatters['D_FEE_RATE'] = function(value, column) {
		if(value != null && value != ''){
			return formatRound(value*100,2);
		}else{
			return '';
		}
	};
	$.quiDefaults.Grid.formatters['D_LOWESTINVESTAMT'] = function(value, column) {
		if(value != null && value != ''){
			return formatRound(value/10000,2);
		}else{
			return '';
		}
	};
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
	
	$.quiDefaults.Grid.formatters['D_PARAM_VALIDITY'] = function(value, column) {
		if(value == '1'){
			return '产品募集起始日';
		}else if(value == '2'){
			return '销售手续费';
		}else if(value == '3'){
			return '托管费';
		}else if(value == '4'){
			return '客户收益率';
		}else if(value == '5'){
			return '投资收益率';
		}else if(value == 'A'){
			return '产品风险等级(一级)';
		}else if(value == 'B'){
			return '产品风险等级(二级)';
		}else if(value == 'C'){
			return '产品风险等级(三级)';
		}else if(value == 'D'){
			return '产品风险等级(四级)';
		}else if(value == 'E'){
			return '产品风险等级(五级)';
		}
	};
}
//修改
function onUpdate() {
	if(selectOneRow(grid) == true){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webParamValidity/updateUI.htm?tradeType="+rows[0].tradeType,"修改信息",600,200);
	}
}
//查看详细
function onInfo() {
	if(selectOneRow(grid) == true){
		var rows = grid.getSelectedRows();
		var url = "<%=path%>/webParamValidity/infoUI.htm?tradeType="+rows[0].tradeType;
		showDialog(url,"详细信息",450,180);
	}
}
//查询
function searchHandler() {
	var query = $("#queryForm").formToArray();
	//alert(JSON.stringify(query))
	grid.setOptions( {
		params : query
	});
	//页号重置为1
	grid.setNewPage(1);
	grid.loadData();//加载数据
}
//重置查询
function resetSearch() {
	$("#queryForm")[0].reset();
	$('#search').click();
}

//刷新表格数据并重置排序和页数
function refresh(isUpdate) {
	if (!isUpdate) {
		//重置排序
		grid.options.sortName = 'appDate';
		grid.options.sortOrder = "desc";
		//页号重置为1
		grid.setNewPage(1);
	}
	grid.loadData();
}
</script>
	</body>
</html>
