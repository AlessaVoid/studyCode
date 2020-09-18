<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<!-- 查询位置 -->
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="" id="queryForm" method="post">
			<input  name="accountingFlag" value="${accountingFlag }" id="accountingFlag" type="hidden"/>
			<input  name="tradeKindCode" value="${tradeKindCode }" id="tradeKindCode" type="hidden"/>
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="16%" align="right">
							产品代码：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="16%">
							产品名称：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodName" matchName="prodName" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodName" suggestMode="remote"></div>
						</td>
						<td>是否到账：</td>
						<td>
						<dic:select dicType="DZ_FLAG" name="costAccFlag"></dic:select>
						</td>
						<td>处理状态：</td>
						<td>
						<dic:select dicType="D_DEAL_STATUS" name="dealStatus"></dic:select>
						</td>
						</tr>
						<tr>
						<td colspan="8">
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
						columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '15%',
									align : 'center'
								},{
									display : '产品名称',
									name : 'prodName',
									width : '20%',
									align : 'center'
								},{
									display : '类型',
									name : 'tradeKindCode',
									width : '15%',
									align : 'center',
									type:'TRADE_KIND'
								},{
									display : '本金(元)',
									name : 'cost',
									width : '10%',
									align : 'center'
								},{
									display : '到账日',
									name : 'tradeDate',
									width : '15%',
									align : 'center',
									render:function(rowdata){
										return formatDate(rowdata.tradeDate,'yyyyMMdd');
									}
								},{
									display : '(待处理款款项户-成本)是否到账',
									name : 'costAccFlag',
									width : '15%',
									align : 'center',
									type:'DZ_FALG'
								},{
									display : '处理状态',
									name : 'dealStatus',
									width : '10%',
									align : 'center',
									type:'DEAL_STATUS'
								},{
									display : '交易类型',
									name : 'custType',
									width : '10%',
									align : 'center',
									hide : 'true'
								}],
						url : '<%=path%>/taSkNotifyInfo/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						params:[{name:"accountingFlag",value:$("#accountingFlag").val()},{name:"tradeKindCode",value:$("#tradeKindCode").val()}],
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	 $.quiDefaults.Grid.formatters['TRADE_KIND'] = function(value, column) {
			if (value == "124") {
				return "赎回款";
			}else if(value == "151"){
				return "到期款";
			}else{
				return "分红款";
			}
	};
	 $.quiDefaults.Grid.formatters['DZ_FALG'] = function(value, column) {
			if (value == "0") {
				return "未到账";
			}else{
				return "已到帐";
			}
		 };
	 $.quiDefaults.Grid.formatters['DEAL_STATUS'] = function(value, column) {
			if (value == "0") {
				return "未处理";
			}else if(value == "1"){
				return "划款处理中";
			}else if(value == "2"){
				return "处理成功";
			}else{
				return "处理失败";
			}
	};
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/taSkNotifyInfo/infoUI.htm?tradeDate=" + rows[0].tradeDate+"&accountingFlag=" + rows[0].accountingFlag+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,
				"详细信息",800,600);
	}
}
//审批
function onApply() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		if(rows[0].dealStatus=='1'){
			top.Dialog.alert("划款处理中!");
		}else if(rows[0].dealStatus=='2'){
			top.Dialog.alert("已处理成功!");
		}else{
		showDialog("<%=path%>/taSkNotifyInfo/applyUI.htm?tradeDate=" + rows[0].tradeDate+"&accountingFlag=" + rows[0].accountingFlag+"&costAccFlag="+rows[0].costAccFlag+"&prodCode="+rows[0].prodCode+"&prodName="+rows[0].prodName+"&cost="+rows[0].cost+"&custType="+rows[0].custType+"&dealStatus="+rows[0].dealStatus+"&tradeKindCode="+rows[0].tradeKindCode,
				"审批信息",500,500);
	}
	}
}
//导出
function onExport(){
	top.Dialog.confirm('是否导出全部数据?',	function() {
			$("#queryForm").attr("action","<%=path%>/taSkNotifyInfo/exportExcelFile.htm");
			$("#queryForm").submit();
		});									
}
//打印
function onPrint(){
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/taSkNotifyInfo/printUI.htm?&tradeDate=" + rows[0].tradeDate+"&accountingFlag=" + rows[0].accountingFlag+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,
				"详细信息",800,600);
	}								
}
</script>
</body>
</html>
