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
			<input  name="feeType" value="${feeType }" id="feeType" type="hidden"/>
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
						<td>分配标识：</td>
						<td>
						<dic:select dicType="DISTRIBUTE_FLAG" name="distributeFlag"></dic:select>
						</td>
						</tr>
						<tr>
						<td>
							计提开始日期：
						</td>
						<td>
						<input name="beginDate" id="beginDate" class="date" dateFmt="yyyyMMdd" maxlength="8" type="text" />
						</td>
						<td>
							计提截止日期：
						</td>
						<td>
						<input name="tradeDate" id="tradeDate" class="date" dateFmt="yyyyMMdd" maxlength="8" type="text" />
						</td>
						<td colspan="2">
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
									width : '20%',
									align : 'center'
								},{
									display : '产品名称',
									name : 'prodName',
									width : '20%',
									align : 'center'
								},{
									display : '计提开始日期',
									name : 'beginDate',
									width : '15%',
									align : 'center'
								},{
									display : '计提截止日期',
									name : 'tradeDate',
									width : '15%',
									align : 'center'
								},{
									display : '管理费(元)',
									name : 'amt',
									width : '15%',
									align : 'center'
								},{
									display : '分配标识',
									name : 'distributeFlag',
									width : '15%',
									align : 'center',
									type:'DISTRIBUTE_FLAG'
								}],
						url : '<%=path%>/taSkManagerNotifyInfo/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						params:[{name:"feeType",value:$("#feeType").val()}],
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	 $.quiDefaults.Grid.formatters['DISTRIBUTE_FLAG'] = function(value, column) {
			if (value == "0") {
				return "未分配";
			}{
				return "已分配";
			}
		 };
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/taSkManagerNotifyInfo/infoUI.htm?prodCode=" + rows[0].prodCode+"&feeType="+rows[0].feeType+"&beginDate="+rows[0].beginDate,
				"详细信息",800,600);
	}
}
//审批
function onApply() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		if(rows[0].distributeFlag=='1'){
			top.Dialog.alert("已分配!");
		}else{
		showDialog("<%=path%>/taSkManagerNotifyInfo/applyUI.htm?prodCode=" + rows[0].prodCode+"&feeType="+rows[0].feeType+"&beginDate="+rows[0].beginDate,
				"审批信息",800,600);
	}
		}
}
//导出
function onExport(){
	top.Dialog.confirm('是否导出全部数据?',	function() {
			$("#queryForm").attr("action","<%=path%>/taSkManagerNotifyInfo/exportExcelFile.htm");
			$("#queryForm").submit();
		});									
}
//打印
function onPrint(){
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/taSkManagerNotifyInfo/printUI.htm?prodCode=" + rows[0].prodCode+"&feeType="+rows[0].feeType+"&beginDate="+rows[0].beginDate,
				"详细信息",800,600);
	}								
}
</script>
</body>
</html>
