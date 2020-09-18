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
						<td width="15%" align="right">
							产品代码：
						</td>
						<td width="15%">
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="15%" align="right">
							产品说明书名称：
						</td>
						<td width="15%">
							<input type="text" name="instructionName" />
						</td>
						<td width="15%" align="right">
							是否确认：
						</td>
						<td width="15%">
							<dic:select dicType="IS_YES" name="isAffirm"></dic:select>
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
									width : '15%',
									align : 'center'
								},{
									display : '产品说明书名称',
									name : 'instructionName',
									width : '50%',
									align : 'center'
								},{
									display : '版本',
									name : 'version',
									width : '15%',
									align : 'center'
								},{
									display : '是否确认',
									name : 'isAffirm',
									width : '20%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="0"){
											return "否";
										}
										if(value=="1"){
											return "是";
										}
									}
								}],
						url : '<%=path%>/gfProdInstructionInfo/findPage.htm',
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

//下载	
function download() {
	var rows = grid.getSelectedRows();
	var rowsLength = rows.length;
	if (rowsLength == 0) {
		top.Dialog.alert("请选中需要操作的记录!");
		return;
	} else if (rowsLength > 1) {
		top.Dialog.alert("只能对一条记录进行编辑");
		return;
	} else {
		top.Dialog.confirm("确定要下载该说明书?",function(){
			window.location.href = "<%=path%>/gfProdInstructionInfo/download.htm?prodCode="+rows[0].prodCode+"&version="+rows[0].version;
		});
	}
}
//上传	
function upload() {
	showDialog("<%=path%>/gfProdInstructionInfo/uploadUI.htm","产品说明书上传",550,200);
}
//净值型产品说明书
function onCreate() {
	showDialog("<%=path%>/gfProdInstructionInfo/onCreateUI.htm","净值型产品说明书生成",550,200);
}
//确认	
function confirm() {
	var rows = grid.getSelectedRows();
	var rowsLength = rows.length;
	if (rowsLength == 0) {
		top.Dialog.alert("请选中需要操作的记录!");
		return;
	} else if (rowsLength > 1) {
		top.Dialog.alert("只能对一条记录进行编辑");
		return;
	} else {
		if(rows[0].isAffirm=='1'){
			top.Dialog.alert("产品说明书已经确认，请勿重复操作");
			return;
		}
		showDialog("<%=path%>/gfProdInstructionInfo/updateUI.htm?prodCode="+rows[0].prodCode+"&version="+rows[0].version,"产品说明书确认",550,450);
	}
}
</script>
</body>
</html>
