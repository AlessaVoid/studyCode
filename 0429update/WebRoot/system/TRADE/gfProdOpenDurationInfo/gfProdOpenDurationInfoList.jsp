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
									url="<%=path%>/gfProdBaseInfo/selectGfProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="10%" align="right">
							启用日期：
						</td>
						<td width="15%">
						<input type="text" class="date" dateFmt="yyyyMMdd" name="beginDate" />
						</td>
						<td width="10%" align="right">
							终止日期：
						</td>
						<td width="15%">
						<input type="text" class="date" dateFmt="yyyyMMdd" name="endDate" />
						</td>
						<td width="10%" align="right">
							参数类型：
						</td>
						<td width="15%">
							<dic:select dicType="OPEN_TYPE"  id="paraType" name="paraType"></dic:select>
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
									display : '启用日期',
									name : 'beginDate',
									width : '20%',
									align : 'center'
								},{
									display : '终止日期',
									name : 'endDate',
									width : '20%',
									align : 'center'
								},{
									display : '份额确认日',
									name : 'quotAffirmDate',
									width : '20%',
									align : 'center'
								},{
									display : '参数类型',
									name : 'paraType',
									width : '20%',
									align : 'center',
									render : function(rowdata) {
					           		    if(rowdata.paraType == "1") {
					           			   return "申购";
					           			}else if (rowdata.paraType == "2"){
					           			   return "赎回";
					           			}
				           		   }
								}],
						url : '<%=path%>/gfProdOpenDurationInfo/findPage.htm',
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
		showDialog("<%=path%>/gfProdOpenDurationInfo/updateUI.htm?method=update&prodCode=" + rows[0].prodCode + "&beginDate=" + rows[0].beginDate + "&endDate=" + rows[0].endDate+"&paraType="+rows[0].paraType,
				"修改份额确认日",500,450);
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
		showDialog("<%=path%>/gfProdOpenDurationInfo/infoUI.htm?prodCode=" + rows[0].prodCode + "&beginDate=" + rows[0].beginDate + "&endDate=" + rows[0].endDate+"&paraType="+rows[0].paraType,
				"详细信息",500,450);
	}
}
</script>
</body>
</html>
