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
		<div class="box2_custom" boxType="box2" panelTitle="产品基本参数" id="searchPanel">
			<form action="" id="queryForm" method="post">
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="15%" align="right">
							产品代码：
						</td>
						<td width="20%">
							${prodCode }
						</td>
						<td width="15%" align="right">
							产品名称：
						</td>
						<td width="20%">
							${prodName }
						</td>
						<td>
							<div align="center"><button type="button" id="back">返回</button></div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- Grid位置 -->
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表  （单位：万）" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
<script type="text/javascript">
var grid = null;
function initComplete() {
	//返回按钮
	$("#back").click (function(){
		window.location.replace("<%=path%>/taRetailerLimitCtl/listUI.htm?menuNo=TA-02");
	});
	//当提交表单刷新本页面时关闭弹窗
	top.Dialog.close();
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [
								{
									display : '产品代码',
									name : 'prodCode',
									width : '15%',
									align : 'center'
								},{
									display : '启用日期',
									name : 'beginDate',
									width : '12%',
									align : 'center'
								},{
									display : '结束日期',
									name : 'endDate',
									width : '12%',
									align : 'center'
								},{
									display : '币种',
									name : 'currency',
									width : '12%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="156"){
										return "人民币";
										}
									if(value=="826"){
										return "英镑";
										}
									if(value=="978"){
										return "欧元";
										}
									if(value=="840"){
										return "美元";
									}
									if(value=="392"){
										return "日元";
									}
									if(value=="036"){
										return "澳大利亚元";
									}
									if(value=="702"){
										return "新元";
									}
									if(value=="643"){
										return "卢布";
									}
									if(value=="756"){
										return "瑞士法郎";
									}
									if(value=="124"){
										return "加元";
									}
									if(value=="344"){
										return "港币";
									}
									}
								},{
									display : '额度类型',
									name : 'paraType',
									width : '12%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "申购";
										}
										if(value=="2"){
											return "赎回";
										}
										if(value=="3"){
											return "认购";
										}
									}
								},{
									display : '开放标志',
									name : 'openFlag',
									width : '10%',
									align : 'center',
									hide: true ,
									render : function(rowdata, rowindex, value, column) {
										if(value=="0"){
											return "有规律";
										}
										if(value=="1"){
											return "无规律";
										}
									}
								},{
									display : '计划发售额度',
									name : 'limit',
									width : '20%',
									align : 'center',
									render : function(rowdata) {
										return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.limit,2) + '</div>';
									}
								},{
									display : '已初始化金额',
									name : 'alreadyPurchaseAmt',
									width : '20%',
									align : 'center',
									render : function(rowdata) {
										return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.alreadyPurchaseAmt,2) + '</div>';
									}
								}],
						url : '<%=path%>/taRetailerLimitCtl/prodEduCtl.htm?prodCode=<%=request.getParameter("prodCode")%>',
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
//额度初始化
function initEdu() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		if(rows[0].paraType==1 && rows[0].openFlag==0){
			top.Dialog.alert("申购有规律，不可以手动调整额度！");
		}else{
			showDialog("<%=path%>/taRetailerLimitCtl/initEduUI.htm?method=initEduUI&prodCode="+rows[0].prodCode+"&paraType="+rows[0].paraType+"&beginDate="+rows[0].beginDate+"&endDate="+rows[0].endDate+"&currency="+ rows[0].currency +"&prodName=<%=request.getParameter("prodName")%>","额度初始化",500,200);
		}
	}
}
//额度分配
function buyEduAsssign() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		if(rows[0].paraType==1 && rows[0].openFlag==0){
			top.Dialog.alert("申购有规律，不可以手动调整额度！");
		}else{
			showDialog("<%=path%>/taRetailerLimitCtl/buyEduAsssignUI.htm?prodCode=" + rows[0].prodCode+"&paraType="+rows[0].paraType+"&beginDate="+rows[0].beginDate+"&endDate="+rows[0].endDate+"&currency="+ rows[0].currency +"&prodName=<%=request.getParameter("prodName")%>",
					"额度分配",800,450);
		}
	}
}

//查看认购额度
function onBuyInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		var alreadyPurchaseAmt=rows[0].alreadyPurchaseAmt;
		if(alreadyPurchaseAmt==""||alreadyPurchaseAmt==0){
			top.Dialog.alert("未初始化额度，请先初始化额度！");
		}else{
			showDialog("<%=path%>/taRetailerLimitCtl/onBuyInfoUI.htm?prodCode=" + rows[0].prodCode+"&paraType="+rows[0].paraType+"&beginDate="+rows[0].beginDate+"&endDate="+rows[0].endDate+"&currency="+ rows[0].currency +"&prodName=<%=request.getParameter("prodName")%>",
					"销售商额度详细信息",800,450);
		}
	}
}

</script>
</body>
</html>
