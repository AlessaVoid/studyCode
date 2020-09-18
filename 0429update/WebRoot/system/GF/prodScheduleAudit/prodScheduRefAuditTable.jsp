<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
		<!--数据表格end-->
	</head>
	<body>
		<!-- Grid位置 -->
		<div  id="searchPanel">
			<form action="" id="queryForm" method="post">
			<input type="hidden" name="prodCodes" id="prodCodes" value="${prodCodes }"/>
			<input type="hidden" name="taskIds" id="taskIds" value="${taskIds }"/>
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td align="right">
							最早日（从）：
						</td>
						<td>
							<input id="pBeginDate" type="text" name="pBeginDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td>
							最晚日（到）：
						</td>
						<td>
							<input id="pEndDate"  type="text" name="pEndDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
					<tr>
						<td  colspan="8">
							<div align="center">
								<button type="button" onclick="searchList()"><span class="icon_find">查询</span></button>
								<button type="button" onclick="resetList()"><span class="icon_reload">重置</span></button>
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div class="box2_custom" boxType="box2" panelTitle="到期参考表" >
				<div id="dataBasic" name="到期参考表" style="width:97%;">
    			</div>
    		</div>
    		<div class="box2_custom" boxType="box2" panelTitle="期限结构对比表" >
				<div  id="dataBasicList" name="期限结构对比表"  style="width:97%;">
	   			</div>
			</div>
	    </div>
   			
<script type="text/javascript">
var grid = null;
var g = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	g = $("#dataBasicList")
			.quiGrid(
					{
						columns : [{
									display : '对比项',
									name : 'conItem',
									width : '20%',
									align : 'center'
									
								},{
									display : '产品数量',
									name : 'numbers',
									width : '20%',
									align : 'center'
								},{
									display : '规模(万)',
									columns : [
										{
											display : '90日以内',
											name : 'fQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '91-180日',
											name : 'sQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '181-270日',
											name : 'tQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '271-365日',
											name : 'foQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '366日及以上',
											name : 'years',
											width : '12%',
											align : 'center'
											
										}]           
								}],
						url : '<%=path%>/scheduleAudit/selectComparisonChart.htm',
						sortName : '',
						rownumbers : true,
						checkbox : false,
						params:[{name:"taskIds",value:"${taskIds}"}],
						usePager : false
					});
	
	
		grid = $("#dataBasic").quiGrid({
					columns : [{
								display : '产品代码',
								name : 'prodCode',
								width : '10%',
								align : 'center',
								frozen:true
							},{
								display : '产品名称',
								name : 'prodName',
								width : '22%',
								align : 'center'
								
							},{
								display : '产品成立日期',
								name : 'prodBeginDate',
								width : '15%',
								align : 'center'
							},{
								display : '产品终止日期',
								name : 'prodEndDate',
								width : '15%',
								align : 'center'
							},{
								display : '币种',
								name : 'currency',
								width : '10%',
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
								display : '实际募集金额(万)',
								name : 'planIssueAmt',
								width : '15%',
								align : 'center'
							},{
								display : '期限(天)',
								name : 'prodDurationDays',
								width : '15%',
								align : 'center'
							}],
					url : '<%=path%>/scheduleAudit/selectscheduleRefTable.htm',
					sortName : '',
					rownumbers : true,
					checkbox : false,
					params:[{name:"taskIds",value:"${taskIds}"}],
					usePager : false
				});
}
function searchList() {
	var pBeginDate = $("#pBeginDate").val().trim();
	var pEndDate = $("#pEndDate").val().trim();
	if(pBeginDate){
		if(!pEndDate){
			top.Dialog.alert("请输入最晚日  | 操作提示：",function(){
			});
			return false;
		}
		if(parseFloat(pBeginDate)>parseFloat(pEndDate)){
			top.Dialog.alert("最早日必须小于最晚日  | 操作提示：",function(){
			});
			return false;
		}
	}
	var query = $("#queryForm").formToArray();
	var g_query = $("#queryForm").formToArray();
	//alert(JSON.stringify(query))
	g.setOptions( {
		params : query
	});
	//页号重置为1
	g.setNewPage(1);
	g.loadData();//加载数据
	//alert(JSON.stringify(query))
	grid.setOptions( {
		params : g_query
	});
	//页号重置为1
	grid.setNewPage(1);
	grid.loadData();//加载数据
}
//重置查询
function resetList() {
	$("#pEndDate").val("");
	$("#pBeginDate").val("");
	$('#search').click();
}
</script>
</body>
</html>