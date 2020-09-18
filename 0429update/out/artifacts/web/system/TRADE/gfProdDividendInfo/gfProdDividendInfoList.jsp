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
						<td width="10%" align="right">
							权益登记日：
						</td>
						<td width="15%">
						<input type="text" class="date" dateFmt="yyyyMMdd" name="registrationDate" />
						</td>
						<td width="10%" align="right">
							状态：
						</td>
						<td width="15%">
						<dic:select dicType="DIVIDEND_STATUS"  id="status" name="status"></dic:select>
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
									display : '除权日',
									name : 'exDividDate',
									width : '20%',
									align : 'center'
								},{
									display : '权益登记日',
									name : 'registrationDate',
									width : '20%',
									align : 'center'
								},{
									display : '每份分红金额',
									name : 'dividendPerUnit',
									width : '20%',
									align : 'center',
								},{
									display : '状态',
									name : 'status',
									width : '20%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="1"){
										return "未分红";
										}
									if(value=="2"){
										return "已分红";
										}
									}
								}],
						url : '<%=path%>/gfProdDividendInfo/findPage.htm',
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
	showDialog("<%=path%>/gfProdDividendInfo/insertUI.htm?method=insert&id=1","新增理财产品分红参数信息",500,450);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		var status=rows[0].status;
		if(status==2){
			top.Dialog.alert("产品已经分红禁止修改!");
			return;
		}
		showDialog("<%=path%>/gfProdDividendInfo/updateUI.htm?method=update&prodCode=" + rows[0].prodCode + "&exDividDate=" + rows[0].exDividDate,
				"修改理财产品分红参数信息",500,450);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		var rows = grid.getSelectedRows();
		var status=rows[0].status;
		if(status==2){
			top.Dialog.alert("产品已经分红禁止删除!");
			return;
		}
		showDialog("<%=path%>/gfProdDividendInfo/deleteUI.htm?method=delete&prodCode=" + rows[0].prodCode + "&exDividDate=" + rows[0].exDividDate,
				"删除理财产品分红参数信息",500,450);
	}
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdDividendInfo/infoUI.htm?prodCode=" + rows[0].prodCode + "&exDividDate=" + rows[0].exDividDate,
				"详细信息",350,200);
	}
}
</script>
</body>
</html>
