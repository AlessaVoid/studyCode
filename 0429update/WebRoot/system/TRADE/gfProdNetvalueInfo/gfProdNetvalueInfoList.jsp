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
									url="<%=path%>/gfProdNetvalueInfo/selectProdCode.htm" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							净值日期：
						</td>
						<td width="15%">
						<input type="text" class="date" dateFmt="yyyyMMdd" name="netvalueDate" />
						</td>
						<td width="10%" align="right">
							年化状态：
						</td>
						<td width="15%">
						<dic:select dicType="NET_AFFIRM_FLAG"  id="operConfirm" name="operConfirm"></dic:select>
						</td>
						<td width="10%" align="right">
							状态：
						</td>
						<td width="15%">
						<dic:select dicType="VALID_STATUS"  id="status" name="status"></dic:select>
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
									display : '净值日期',
									name : 'netvalueDate',
									width : '20%',
									align : 'center'
								},{
									display : '净值',
									width : '20%',
									align : 'center',
									render : function(rowdata) {
					     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.netvalue,8)+ '</div>';
					     			}
								},{
									display : '成立以来年化(%)',
									width : '20%',
									name : 'latestProFitRate',
									align : 'center',
									render : function(rowdata) {
					     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.latestProFitRate,2)+ '</div>';
					     			}
								},{
									display : '年化确认',
									name : 'operConfirm',
									width : '20%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="0"){
										return "未生成年化信息";
										}
									if(value=="1"){
										return "已生成未确认";
										}
									if(value=="2"){
										return "已生成已确认";
										}
									}
								},{
									display : '状态',
									name : 'status',
									width : '20%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="1"){
										return "正常";
										}
									if(value=="2"){
										return "无效";
										}
									}
								}],
						url : '<%=path%>/gfProdNetvalueInfo/findPage.htm',
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
	showDialog("<%=path%>/gfProdNetvalueInfo/insertUI.htm?method=insert&id=1","新增产品净值信息",500,450);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdNetvalueInfo/updateUI.htm?method=update&prodCode=" + rows[0].prodCode + "&netvalueDate=" + rows[0].netvalueDate + "&status=" + rows[0].status,
				"修改产品净值信息",500,450);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdNetvalueInfo/deleteUI.htm?method=delete&prodCode=" + rows[0].prodCode + "&netvalueDate=" + rows[0].netvalueDate + "&status=" + rows[0].status,
				"删除产品净值信息",500,450);
	}
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdNetvalueInfo/infoUI.htm?prodCode=" + rows[0].prodCode + "&netvalueDate=" + rows[0].netvalueDate + "&status=" + rows[0].status,
				"详细信息",250,200);
	}
}
//
function onConfirm() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdNetvalueInfo/updateConfirmUI.htm?prodCode=" + rows[0].prodCode + "&netvalueDate=" + rows[0].netvalueDate + "&status=" + rows[0].status,
				"手动确认",500,450);
	}
}
</script>
</body>
</html>
