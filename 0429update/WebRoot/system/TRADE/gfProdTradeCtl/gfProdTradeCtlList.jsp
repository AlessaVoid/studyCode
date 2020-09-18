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
							<div class="suggestion" id="gfProdCode" name="prodCode" matchName="prodCode" 
									url="<%=path%>/gfProdTradeCtl/selectProdCode.htm" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							渠道代码：
						</td>
						<td width="15%">
							<dic:select dicType="D_CHNL" name="channelCode" id="channelCode"></dic:select>
						</td>
						<td width="10%" align="right">
							交易代码：
						</td>
						<td width="17">
							<div class="suggestion" id="gfTradeCode" name="tradeCode" matchName="tradeCode" 
									url="<%=path%>/gfProdTradeCtl/selectTradeCode.htm" suggestMode="remote"></div>
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
									width : '25%',
									align : 'center'
								},{
									display : '渠道代码',
									name : 'channelCode',
									width : '25%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="00"){
											return "柜台";
											}
										if(value=="01"){
											return "电话银行";
											}
										if(value=="02"){
											return "一卡通";
											}
										if(value=="03"){
											return "理财通";
											}
										if(value=="04"){
											return "短信银行";
											}
										if(value=="05"){
											return "深圳账户管家";
											}
										if(value=="07"){
											return "个人网银";
											}
										if(value=="08"){
											return "理财规划";
											}
										if(value=="09"){
											return "手机银行";
											}
										if(value=="10"){
											return "个人信贷";
											}
										if(value=="12"){
											return "电视银行";
											}
										if(value=="11"){
											return "公司网银";
											}
										if(value=="17"){
											return "VTM自助银行";
											}
										if(value=="0"){
											return "全部";
											}
										if(value=="46"){
											return "公司信贷";
											}
										if(value=="47"){
											return "公司票据";
											}
										}
								},{
									display : '交易代码',
									name : 'tradeCode',
									width : '25%',
									align : 'center'
								},{
									display : '状态',
									name : 'status',
									width : '25%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "启用";
											}
										if(value=="2"){
											return "停用";
											}
										}
								}],
						url : '<%=path%>/gfProdTradeCtl/findPage.htm',
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
	showDialog("<%=path%>/gfProdTradeCtl/insertUI.htm?method=insert&id=1","交易维护暂停",500,450);
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdTradeCtl/deleteUI.htm?method=delete&prodCode=" + rows[0].prodCode + "&channelCode=" + rows[0].channelCode + "&tradeCode=" + rows[0].tradeCode,
				"交易维护开通",500,450);
	}
}
//暂停
function onUpdateZ() {
	if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			showDialog("<%=path%>/gfProdTradeCtl/updateUI.htm?prodCode=" + rows[0].prodCode + "&channelCode=" + rows[0].channelCode + "&tradeCode=" + rows[0].tradeCode,
					"暂停",500,450);
		}
}
//开启
function onUpdateK() {
	if(selectOneRow(grid)){
			var rows = grid.getSelectedRows();
			showDialog("<%=path%>/gfProdTradeCtl/updateKUI.htm?prodCode=" + rows[0].prodCode + "&channelCode=" + rows[0].channelCode + "&tradeCode=" + rows[0].tradeCode,
					"开启",500,450);
		}
	
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdTradeCtl/infoUI.htm?prodCode=" + rows[0].prodCode + "&channelCode=" + rows[0].channelCode + "&tradeCode=" + rows[0].tradeCode,
				"详细信息",300,200);
	}
}
</script>
</body>
</html>
