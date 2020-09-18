<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_list.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>固定收益类参数</title>
		<script type="text/javascript">
    //数据表格初始化
    var fixProfitGrid;
	function initComplete(){
	var gridData=${dataList};
	//固定收益参数
	fixProfitGrid = $("#fixProfitParam").quiGrid({
		 columns: [
		           { display: '收益率%', name: 'profitRate', align: 'center', width: "25%",
		        	   render : function(rowdata) {
		     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.profitRate,2) + '</div>';}
		           
		           },
		           { display: '启用日期', name: 'beginDate', align: 'center', width: "25%"
		           },
		           { display: '客户类型', name: 'custType', align: 'center', width: "25%",
		        	   render : function(rowdata) {
		        		    if (rowdata.custType == "1") {
		        				return "个人";
		        			}else if (rowdata.custType == "2"){
		        				return "机构";
		        			}
		      			}
		           },
		           { display: '启用状态', name: 'validStatus', align: 'center', width: "25%",
		        	   render : function(rowdata) {
		         		   if (rowdata.validStatus == "1") {
		         				return "有效";
		         			}else if (rowdata.validStatus == "2"){
		         				return "无效";
		         			}
		       			}
		           }
		           ], 
           data:gridData, 
           rownumbers : true,
           sortName: 'id', 
           percentWidthMode:true,
           height: '100%', 
           width:"100%"
   	});
	 $.quiDefaults.Grid.formatters['STARTFLAG'] = function(value, column) {
		if (value == "1") {
			return "有效";
		}else if (value == "2"){
			return "无效";
		}
	 };
	 $.quiDefaults.Grid.formatters['CUSTTYPE'] = function(value, column) {
		 if (value == "0") {
			return "不区分";
		}else if (value == "1") {
			return "个人";
		}else if (value == "2"){
			return "机构";
		}
	 };
}
</script> 
	</head>
	<body >
		<form id="form1">
   			<div id="fixProfitParam" class="padding_right5"></div>
	   	</form>
	</body>
</html>