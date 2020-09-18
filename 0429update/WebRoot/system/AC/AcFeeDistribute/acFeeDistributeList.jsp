<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	</head>
	<body>
		<!-- 查询位置 -->
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="" id="queryForm" method="post">
			<input  name="businesstypecode" value="${businesstype}" id="businesstypecode" type="hidden"/>
				<input  name="feetype" value="${feeType}" id="feetype" type="hidden"/>
				<table class="tableStyle" mode="list" formMode="line"
					style="width: 100%;">
					<tr>
						<td width="10%" align="right">
							产品代码：
						</td>
						<td width="12%">
							<div class="suggestion" name="businesskindcode" matchName="prodCode" 
									url="<%=path%>/gfProdBaseInfo/selectGfProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							产品名称：
						</td>
						<td width="12%">
							<div class="suggestion" name="businesskindname" matchName="prodName" 
									url="<%=path%>/gfProdBaseInfo/selectGfProdBaseInfo.htm?type=prodName" suggestMode="remote"></div>
						</td>
						<td class="ali03">
							起始日期：
						</td>
						<td>
							<input type="text" id="begindate" name="begindate" class="date custom[date]" value=""
								datefmt="yyyyMMdd" />
						</td>
						<td class="ali03">
							结束日期 ：
						</td>
						<td>
							<input type="text" id="enddate" name="enddate"	class="date custom[date]" datefmt="yyyyMMdd" />
						</td>
						<td>
							处理标志：
						</td>
						<td>
							<dic:select dicType="D_FPFLAG" name="fpflag" selwidth="127"></dic:select>
						</td>
						<td >
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
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5" >
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
							display : '唯一标识',
							name : 'jtfeecode',
							align : 'cennter',
							width : '15%'

						},{
							display : '生成日期',
							name : 'tradedate',
							align : 'cennter',
							width : '7%'

						},{
							display : '产品代码',
							name : 'businesskindcode',
							align : 'cennter',
							width : '7%'
						},{display : '币种',
							name : 'currency',
							width : '7%',
							align : 'center'
						},
						{
							display : '产品名称',
							name : 'businesskindname',
							align : 'cennter',
							width : '15%'
						},
						{
							display : '计费起始日',
							name : 'begindate',
							align : 'cennter',
							width : '7%'
						},
						{
							display : '计费终止日',
							name : 'enddate',
							align : 'cennter',
							width : '7%'
						},{
							display : '是否转人民币标志',
							name : 'isProfitFeeToRmb',
							align : 'center',
							width : '9%',
							hide : true
						},
						{
							display : '处理标记',
							name : 'fpflag',
							align : 'cennter',
							width : '8%',
							render : function(rowdata, rowindex, value,
									column) {
								if (value == 0 || value == "0") {
									value="未处理";
								} else if (value == 1 || value == "1") {
									value="审批流程中";
								} else if (value == 2 || value == "2") {
									value="等待后台处理";
								} else if (value == 3 || value == "3") {
									value="已处理";
								} else if (value == 4 || value == "4") {
									value="处理异常";
								}else if (value == 5 || value == "5") {
									value="审批驳回";
								}
								var title=rowdata.text;
								if(title=="undefined"||title==undefined){
									title="";
								}
								
								return '<div class="padding_top4 padding_left5">' 
								+ '<span  title="'+title+'">'+value+'</span>'
								+ '</div>';
							}
						},
						{
							display : '计费金额',
							name : 'amt',
							align : 'cennter',
							width : '7%',
							render : function(rowdata, rowindex, value,
									column) {
								return "<div class='money'>"
										+ zh(value, 2) + "</div>"
							}
						},
						{
							display : '实际分配金额',
							name : 'fpamt',
							align : 'cennter',
							width : '7%',
							render : function(rowdata, rowindex, value,
									column) {
								if(value==""||value==''||value==null){value="0";}
								return "<div class='money'>"+ zh(value, 2) + "</div>"
							}
						} ,{
							display : '实际分配金额是否转人民币',
							name : 'tormbfpamt',
							align : 'cennter',
							width : '7%',
							render : function(rowdata, rowindex, value, column) {
								   
								 if(rowdata.currency!="人民币"){
									 if(rowdata.isProfitFeeToRmb=='0'){
									    	return "否";
									 }
									 if(rowdata.isProfitFeeToRmb=='1'){
									    	return '<div style="margin-right: 10%;" align="right">' + rowdata.tormbfpamt + '(人民币)'+'</div>';
									 }
								 }else{
									   return "--";
								 }
							}
						},
						{
							display : '备注',
							name : 'remark',
							align : 'cennter',
							width : '20%'
						}],
						url : '<%=path%>/acFeeDistribute/findPage.htm',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						params:[{name:"businesstypecode",value:$("#businesstypecode").val()},
						        {name:"feetype",value:$("#feetype").val()}],
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
}
function getSelectId(grid) {
	var selectedRows = grid.getSelectedRows();
	var selectedRowsLength = selectedRows.length;
	var ids = "";
	var first = true;
	for ( var i = 0; i < selectedRowsLength; i++) {
		if (!first) {
			ids += ",";
		}
		if (selectedRows[i].fpflag != 0 && selectedRows[i].fpflag != 4&&selectedRows[i].fpflag != 5) {
			top.Dialog.alert("产品代码:" + selectedRows[i].businesskindcode
					+ "<br>产品名称:" + selectedRows[i].businesskindname
					+ "<br>为已处理记录.", null, null, null, null);
			return "error";
		}
		ids += selectedRows[i].jtfeecode + "|"
				+ rmoneyStr(selectedRows[i].fpamt);
		first = false;

	}
	return ids;
}
function onDistribute() {
	var selectedRows = grid.getSelectedRows();
	var selectedRowsLength = selectedRows.length;
	var moneySum = 0;
	var isSum = true;
	for ( var i = 0; i < selectedRows.length; i++) {
		  if(selectedRowsLength>1 && selectedRows[i].currency!='人民币'){
			top.Dialog.alert("外币产品只能选择一条记录！");
			return; 
		  }
	}
	for ( var i = 0; i < selectedRowsLength; i++) {
		moneySum = moneySum+rmoneyStr(selectedRows[i].fpamt);
	}
	for ( var i = 0; i < selectedRowsLength; i++) {
		if (selectedRows[i].fpamt == "0") {
			top.Dialog.alert("产品代码:" + selectedRows[i].businesskindcode
					+ "<br>产品名称:" + selectedRows[i].businesskindname
					+ "<br>金额不能为0.00", null, null, null, null);
			isSum = false;
		}
	}
	if (grid.getSelectedRows().length > 0 && getSelectId(grid) != "error" && isSum) {
		top.Dialog.confirm('是否发起手续费分配请求:<br>总笔数:'+ grid.getSelectedRows().length + "<br>总金额:" + zh(moneySum, 2),
		function() {
			showDialog("<%=path%>/acFeeDistribute/distributeUI.htm?jsonStr=" +getSelectId(grid)+"&totalMoney="+zh(moneySum, 2),"手续费审批信息",1000,600);
		});
	}
}

//导出
function onExport(){
	top.Dialog.confirm('是否导出全部数据?',	function() {
			$("#queryForm").attr("action","<%=path%>/acFeeDistribute/exportFile.htm");
			$("#queryForm").submit();
		});									
}
</script>
</body>
</html>
