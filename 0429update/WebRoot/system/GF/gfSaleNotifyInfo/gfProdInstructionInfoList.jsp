<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<input type="hidden" value="${prodCode}" id="prodCode"/>
		<!-- Grid位置 -->
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
<script type="text/javascript">
var grid = null;
function initComplete() {
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
									display : '产品说明书名称',
									name : 'instructionName',
									width : '50%',
									align : 'center'
								}],
						url : '<%=path%>/gfSaleNotifyInfo/findPage_Ins.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						params:[{name:"prodCode",value:$("#prodCode").val()},{name:"isAffirm",value:1}],
						pageSize : 10,
						toolbar : {
							items : [
								{text : '下载产品说明书', click : download, iconClass : 'icon_btn_down2'}
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
		var prodCodes="";
			var versions="";
			for ( var r in rows) {
				prodCodes=prodCodes+","+rows[r].prodCode;
				versions=versions+","+rows[r].version;
			}
			prodCodes=prodCodes.substring(1);
			versions=versions.substring(1);
			top.Dialog.confirm("确定要下载该说明书?",function(){
				window.location.href = "<%=path%>/gfSaleNotifyInfo/batchDownload.htm?prodCodes="+prodCodes+"&versions="+versions;
			});
	} else {
		top.Dialog.confirm("确定要下载该说明书?",function(){
			window.location.href = "<%=path%>/gfSaleNotifyInfo/download.htm?prodCode="+rows[0].prodCode+"&version="+rows[0].version;
		});
	}
}
</script>
</body>
</html>
