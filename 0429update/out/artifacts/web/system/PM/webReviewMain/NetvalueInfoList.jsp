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
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
<script type="text/javascript">

function initComplete() {
	var gridData=${gridData};
	gridData = $("#dataBasic")
			.quiGrid(
					{
						columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '35%',
									align : 'center',
								},{
									display : '净值',
									name : 'netvalue',
									width : '30%',
									align : 'center'
								},{
									display : '最新净值时间',
									name : 'netvalueDate',
									width : '35%',
									align : 'center'
								}],
						data:gridData,
						rownumbers : true,
						checkbox : false,
						height : '100%',
						usePager:true
					});
}

</script>
</body>
</html>
