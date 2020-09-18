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
									width : '50%',
									align : 'center'
								},{
									display : '节假日净赎回限额(元)',
									name : 'holidayRedeemLimit',
									width : '50%',
									align : 'center',
									 render : function(rowdata) {
					        				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.holidayRedeemLimit,2)+ '</div>';}
								}],
						url : '<%=path%>/webProdRedeemInfo/findPage.htm',
						sortName : 'prodCode',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	function formatRound(num,dp){
		 return Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
}

//新增
function onInsert() {
	showDialog("<%=path%>/webProdRedeemInfo/insertUI.htm","新增产品节假日净赎回限额",600,400);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webProdRedeemInfo/updateUI.htm?prodCode="+rows[0].prodCode,
				"修改产品节假日净赎回限额",600,400);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webProdRedeemInfo/deleteUI.htm?prodCode="+rows[0].prodCode,
				"删除产品节假日净赎回限额",600,400);
	}
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webProdRedeemInfo/infoUI.htm?prodCode=" + rows[0].prodCode,
				"产品节假日净赎回限额详细信息",600,400);
	}
}
</script>
</body>
</html>
