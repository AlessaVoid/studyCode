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
						<td width="8%" align="right">
							产品名称 ：
						</td>
						<td width="15">
							<div class="suggestion" id="gfprodName" name="prodName" matchName="prodName" 
									url="<%=path%>/gfTrusteeshipAcct/selectProdName.htm" suggestMode="remote"></div>
						</td>
						<td width="13%" align="right">
							产品托管子账户名称：
						</td>
						<td width="15">
							<div class="suggestion" id="gftrusteeshipAcctName" name="trusteeshipAcctName" matchName="trusteeshipAcctName" 
									url="<%=path%>/gfTrusteeshipAcct/selectTrusteeshipAcctName.htm" suggestMode="remote"></div>
						</td>
						<td width="13%" align="right">
							产品托管子账户账号：
						</td>
						<td width="15">
							<div class="suggestion" id="gftrusteeshipAcct" name="trusteeshipAcct" matchName="trusteeshipAcct" 
									url="<%=path%>/gfTrusteeshipAcct/selectTrusteeshipAcct.htm" suggestMode="remote"></div>
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
									display : '产品名称',
									name : 'prodName',
									width : '33%',
									align : 'center',
									frozen:true
								},{
									display : '产品托管子账户名称',
									name : 'trusteeshipAcctName',
									width : '33%',
									align : 'center'
								},{
									display : '产品托管子账户账号',
									name : 'trusteeshipAcct',
									width : '33%',
									align : 'center'
								}],
						url : '<%=path%>/gfTrusteeshipAcct/findPage.htm',
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
	showDialog("<%=path%>/gfTrusteeshipAcct/insertUI.htm?method=insert&id=1","新增托管账户信息",500,300);
}
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfTrusteeshipAcct/updateUI.htm?trusteeshipAcct=" + rows[0].trusteeshipAcct,
				"修改托管账户信息",500,300);
	}
}
//删除
function onDelete() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		top.Dialog.confirm("确定要删除该记录吗？", function() {
			//删除记录
			$.post("<%=path%>/gfTrusteeshipAcct/delete.htm", {
				trusteeshipAcct : rows[0].trusteeshipAcct,
				prodCode : rows[0].prodCode
			}, function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg);
					grid.loadData();
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
			//刷新表格
			grid.loadData();
		});
	}
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfTrusteeshipAcct/infoUI.htm?trusteeshipAcct=" + rows[0].trusteeshipAcct,
				"托管账户详细信息",500,280);
	}
}
//导入
function onShow() {
	showDialog("<%=path%>/gfTrusteeshipAcct/showUI.htm?method=show&id=1","导入托管账户信息",880,580);
}

//解析参数模板
function importParamsInfo(fileName) {
	top.Dialog.close();
	showDialog("<%=path%>/gfTrusteeshipAcct/readGfTrusteeshipAcctXsl.htm?fileName=" + fileName,
				"模板导入",1280,680);
}

</script>
</body>
</html>
