<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>项目信息</title> 
		<script type="text/javascript">
		var prjGrid;
		function initComplete(){
		var gridData=${gridData};
		//灵活期限收益组合参数
		prjGrid = $("#prjInfoParam").quiGrid({
	           columns: [
	           { display: '项目编号', name: 'prjCode', align: 'center', width: "20%"},
	           { display: '项目名称', name: 'prjName', align: 'center', width: "20%"},
	           { display: '项目起始日期', name: 'prjBeginDate', align: 'center', width: "20%"},
	           { display: '项目终止日期', name: 'prjEndDate', align: 'center', width: "20%"},
	           { display: '项目类型', name: 'prjType', align: 'center', width: "10%", 
	        	  render : function(rowdata) {
	        		  if (rowdata.prjType == "C") {
		    				return "通道";
		    			}else if (rowdata.prjType == "T") {
		    				return "自定义资产";
		    			}else if (rowdata.prjType == "B") {
		    				return "母资管计划";
		    			}else if (rowdata.prjType == "O"){
		    				return "其他资产";
						}else if (rowdata.prjType == "C-T"){
		    				return "通道-自定义资产";
						}else if (rowdata.prjType == "C-B"){
		    				return "通道-母资管计划";
						}else if (rowdata.prjType == "T-B"){
		    				return "自定义资产-母资管计划";
						}else if (rowdata.prjType == "C-O"){
		    				return "通道-其他资产";
						}else if (rowdata.prjType == "B-O"){
		    				return "母资管计划-其他资产";
						}
	  			}},
	  			 { display: '联动项目编号', name: 'prePrjCode', align: 'center', width: "10%"}
	           ], 
	           data:gridData, 
	           rownumbers : true,
	           usePager: false, 
	           percentWidthMode:true,
	           width:'100%'
	   	});
	}
		</script>
	</head>
	<body>
		<form id="form1">
   			<div id="prjInfoParam" class="padding_right5"></div>
	   	</form>
	</body>
</html>