<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>存续期参数</title> 
<script type="text/javascript">
	var durationGrid;
	function initComplete() {
		var gridData = ${gridData};
		//存续期参数
		durationGrid = $("#durationParam").quiGrid({
			columns : [

			{
				display : '申购日',
				name : 'purchaseDate',
				align : 'center',
				width : "12%"
			},
			{
				display : '份额确认日',
				name : 'taCfmDate',
				align : 'center',
				width : "12%"
			},
			/* {
				display : '投资周期结束日',
				name : 'invCycleEndDate',
				align : 'center',
				width : "10%"
			}, */
			{
				display : '首次投资周期结束日',
				name : 'firstInvCycleEndDate',
				align : 'center',
				width : "12%"
			},
			{
				display : '开始时间（从）',
				name : 'beginTime',
				align : 'center',
				width : "15%"
			},
			{
				display : '结束时间（到）',
				name : 'endTime',
				align : 'center',
				width : "15%"
			},		
			{
				display : '开放期额度',
				name : 'limit',
				align : 'center',
				width : "10%"
			}, 
			{
				display : '资金处理方式',
				name : 'amtHandleFlag',
				align : 'center',
				width : "12%",
				render : function(rowdata) {
					if (rowdata.amtHandleFlag == "1") {
						return "批量";
					} else if (rowdata.amtHandleFlag == "2") {
						return "实时";
					}
				}
			}, 
			{
				display : '份额确认方式',
				name : 'quotAffirmType',
				align : 'center',
				width : "12%",
				render : function(rowdata) {
					if (rowdata.quotAffirmType == "1") {
						return "批量";
					} else if (rowdata.quotAffirmType == "2") {
						return "实时";
					}
				}
			}],
			data : gridData,
			rownumbers : true,
			usePager : false,
			sortName : 'oerderNum',
			percentWidthMode : true,
			height : '100%',
			width : "100%"
		});
		$.quiDefaults.Grid.formatters['HANDLEFLAG'] = function(value, column) {
			if (value == "1") {
				return "批量";
			} else if (value == "2") {
				return "实时";
			}
		};
 
	}
</script>
	</head>
	<body>
		<form id="form1">
 			<div id="durationParam"></div>
	   	</form>
	</body>
</html>