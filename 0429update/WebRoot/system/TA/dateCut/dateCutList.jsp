<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
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
									display : '日切日期',
									name : 'dateCutDate',
									width : '33%',
									align : 'center'
								},
								{
									display : '日切时间',
									name : 'dateCutTime',
									width : '33%',
									align : 'center',
								},{
									display : '最后修改日期',
									name : 'updateTimeStamp',
									width : '33%',
									align : 'center'
								}],
						url : '<%=path%>/dateCut/findPage.htm?dateType=01',
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
//修改
function onUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/dateCut/updateUI.htm?method=update&dateType=01",
				"修改日切时间",500,450);
	}
}

</script>
</body>
</html>
