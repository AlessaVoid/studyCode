<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/common_list.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>基础参数变更</title>
<script type="text/javascript">
var durationGrid;
function initComplete(){
	var gridData=${dataList};
	if(1!="${isCurrProdt}"){
		//存续期参数
		durationGrid = $("#durationParam").quiGrid({
			columns: [
		               { display: '参数类型', name: 'paraType', align: 'center', width: "10%",
		            	   render : function(rowdata) {
			           		   if (rowdata.paraType == "1") {
			           				return "申购";
			           			}else if (rowdata.paraType == "2"){
			           				return "赎回";
			           			}
			           		}
		               },
		               { display: '起始日期', name: 'beginDate', align: 'center', width: "10%"
		               },
		               { display: '终止日期', name: 'endDate', align: 'center', width: "10%"
		               },
		               {
		      				display : '开始时间（从）',
		      				name : 'beginTime',
		      				align : 'center',
		      				width : "10%"
		      				},
		      				{
		      				display : '结束时间（到）',
		      				name : 'endTime',
		      				align : 'center',
		      				width : "10%"
		      				},
		               { display: '开放期额度', name: 'limit', align: 'center', width: "10%"
		               },
		               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "10%",
			           	   render : function(rowdata) {
			           		   if (rowdata.amtHandleFlag == "1") {
			           				return "批量";
			           			}else if (rowdata.amtHandleFlag == "2"){
			           				return "实时";
			           			}
			           		}
		              	 },
		               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "10%",
		            	   render : function(rowdata) {
		               		   if (rowdata.quotAffirmType == "1") {
		               				return "批量";
		               			}else if (rowdata.quotAffirmType == "2"){
		               				return "实时";
		               			}
		               }
		              	 },
		               { display: '净值日', name: 'quotAffirmDate', align: 'center', width: "10%"
		               },
		               { display: '启用状态', name: 'validStatus', align: 'center', width: "10%",
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
	           usePager: false, 
	           sortName: 'oerderNum', 
	           percentWidthMode:true,
	           height: '100%', 
	           width:"100%"
	 	});
	}else{
		//存续期参数
		durationGrid = $("#durationParam").quiGrid({
			columns: [
		               { display: '参数类型', name: 'paraType', align: 'center', width: "10%",
		            	   render : function(rowdata) {
			           		   if (rowdata.paraType == "1") {
			           				return "申购";
			           			}else if (rowdata.paraType == "2"){
			           				return "赎回";
			           			}
			           		}
		               },
		               { display: '起始日期', name: 'beginDate', align: 'center', width: "10%"
		               },
		               { display: '终止日期', name: 'endDate', align: 'center', width: "10%"
		               },
		               {
		      				display : '开始时间（从）',
		      				name : 'beginTime',
		      				align : 'center',
		      				width : "10%"
		      				},
		      				{
		      				display : '结束时间（到）',
		      				name : 'endTime',
		      				align : 'center',
		      				width : "10%"
		      				},
		               { display: '开放期额度', name: 'limit', align: 'center', width: "10%"
		               },
		               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "10%",
			           	   render : function(rowdata) {
			           		   if (rowdata.amtHandleFlag == "1") {
			           				return "批量";
			           			}else if (rowdata.amtHandleFlag == "2"){
			           				return "实时";
			           			}
			           		}
		              	 },
		               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "10%",
		            	   render : function(rowdata) {
		               		   if (rowdata.quotAffirmType == "1") {
		               				return "批量";
		               			}else if (rowdata.quotAffirmType == "2"){
		               				return "实时";
		               			}
		               }
		              	 },
		               { display: '份额确认日', name: 'quotAffirmDate', align: 'center', width: "10%"
		               },
		               { display: '启用状态', name: 'validStatus', align: 'center', width: "10%",
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
	           usePager: false, 
	           sortName: 'oerderNum', 
	           percentWidthMode:true,
	           height: '100%', 
	           width:"100%"
	 	});
	}
	
 $.quiDefaults.Grid.formatters['HANDLEFLAG'] = function(value, column) {
	if (value == "1") {
		return "批量";
	}else if (value == "2"){
		return "实时";
	}
 };   
 $.quiDefaults.Grid.formatters['STARTFLAG'] = function(value, column) {
	if (value == "1") {
		return "启用";
	}else if (value == "2"){
		return "停用";
	}
};
}
</script>
</head>
<body>
	<form id="form1">
		<div id="durationParam" class="padding_right5"></div>
	</form>
</body>
</html>