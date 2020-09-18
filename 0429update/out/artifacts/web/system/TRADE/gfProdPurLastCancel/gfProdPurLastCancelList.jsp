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
									width : '20%',
									align : 'center'
								},{
									display : '产品名称',
									name : 'prodName',
									width : '20%',
									align : 'center'
								},{
									display : '产品成立日',
									name : 'prodBeginDate',
									width : '20%',
									align : 'center'
								},{
									display : '产品到期日',
									name : 'prodEndDate',
									width : '20%',
									align : 'center'
								},{
									
									display : '购买期最后一天允许撤单',
									name : 'cancelFlag',
									width : '20%',
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
						url : '<%=path%>/gfProdPurLastCancel/findPage.htm',
						sortName : 'prodCode',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [ {text : '修改',click : onUpdate,iconClass : 'icon_edit'}, {line : true},
								   	{text : '详细',click : onInfo,iconClass : 'icon_list'}, {line : true}]
						}
					});
	function formatRound(num,dp){
		 return Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
}

//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdPurLastCancel/updateUI.htm?prodCode="+rows[0].prodCode,"修改购买期最后一天允许撤单",600,400);
	}
}

//详细
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/design/infoUI.htm?prodCode=" + rows[0].prodCode,"产品详细信息",1280,680);
	}
}
</script>
</body>
</html>
