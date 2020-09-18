<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<!-- Grid位置 -->
		<div class="box2_custom"  boxType="box1" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
<script type="text/javascript">
var data={"pager.pageNo":1,"pager.totalRows":3,"rows":[

{"operName":"xxx","createTime":"2012-09-11","content":"xxx"},
{"operName":"xxx","createTime":"2012-09-11","content":"xxx"},
{"operName":"xxx","createTime":"2012-09-11","content":"xxx"}
]}

var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [
								{
									display : '发起人',
									name : 'operName',
									width : '30%',
									align : 'center'
								},{
									display : '发起时间',
									name : 'createTime',
									width : '30%',
									align : 'center'
								},{
									display : '通知内容',
									name : 'content',
									width : '40%',
									align : 'center'
								}],
						url : data,
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						toolbar : {
							items : [{
								text : '查看',
								click : onInfo,
								iconClass : 'icon_add'
								}]
						}
					});
}
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		
	}
}
</script>
</body>
</html>
