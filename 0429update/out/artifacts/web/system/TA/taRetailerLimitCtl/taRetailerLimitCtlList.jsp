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
						<td width="12%" align="right">
							产品代码：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="12%">
							产品名称：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodName" matchName="prodName" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodName" suggestMode="remote"></div>
						</td>
						<td width="12%">币种：</td>
						<td width="12%">
							<dic:select dicType="TR_CURRENCY" name="currency" tgClass="" selWidth="127"></dic:select>
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
									width : '13%',
									align : 'center',
									frozen:true
								},{
									display : '产品名称',
									name : 'prodName',
									width : '23%',
									align : 'center',
									frozen:true
								},{
									display : '币种',
									name : 'currency',
									width : '10%',
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
									display : '产品运作模式',
									name : 'prodOperModel',
									width : '13%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "封闭式净值型";
										}
										if(value=="02"){
											return "封闭式非净值型";
										}
										if(value=="03"){
											return " 开放式净值型";
										}
										if(value=="04"){
											return " 开放式非净值型";
										}
									}
								},{
									display : '产品收益类型',
									name : 'profitType',
									width : '13%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "保证收益";
										}
										if(value=="02"){
											return "保本浮动收益";
										}
										if(value=="03"){
											return " 非保本浮动收益";
										}
									}
								},{
									display : '产品风险等级',
									name : 'prodRiskLevel',
									width : '13%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "一级（低）";
										}
										if(value=="2"){
											return "二级（中低）";
										}
										if(value=="3"){
											return "三级（中）";
										}
										if(value=="4"){
											return "四级（中高）";
										}
										if(value=="5"){
											return "五级（高）";
										}
									}
								},{
									display : '是否自主平衡',
									name : 'isAutoBalance',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "是";
										}
										if(value=="0"){
											return "否";
										}
									}
								}],
						url : '<%=path%>/taRetailerLimitCtl/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						toolbar : {
							items : [
								{
									text : '额度管理',
									click : onEdit,
									iconClass : 'icon_edit'
								}, {
									line : true
								}]
						}
					});
}

//修改	
function onEdit() {
	var rows = grid.getSelectedRows();
	var rowsLength = rows.length;
	if (rowsLength == 0) {
		top.Dialog.alert("请选中需要操作的记录!");
		return;
	}else if (rowsLength > 1) {
		top.Dialog.alert("只能对一条记录进行编辑");
		return;
	}else {
		window.location.replace("<%=path%>/taRetailerLimitCtl/prodListUI.htm?prodCode=" + rows[0].prodCode +"&profitType="+ rows[0].profitType+"&prodOperModel="+ rows[0].prodOperModel +"&menuNo=TA-02");
	}
}
</script>
</body>
</html>
