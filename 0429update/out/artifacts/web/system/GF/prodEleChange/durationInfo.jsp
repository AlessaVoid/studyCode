<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>存续期参数</title> 
<script type="text/javascript">
    //数据表格初始化
    var durationGrid;
	function initComplete(){
		if(1!="${isCurrProdt}"){
			//存续期参数
			durationGrid = $("#durationParam").quiGrid({
	               columns: [
	               { display: '参数类型', name: 'paraType', align: 'center', width: "8%",
	            	   render : function(rowdata) {
	            		   if (rowdata.paraType == "1") {
	            				return "申购";
	            			}else if (rowdata.paraType == "2"){
	            				return "赎回";
	            			}
	          			}
	               },
	               { display: '起始日期', name: 'beginDate', align: 'center', width: "8%"
	               },
	               { display: '终止日期', name: 'endDate', align: 'center', width: "8%"
	               },
	               {
	      				display : '开始时间（从）',
	      				name : 'beginTime',
	      				align : 'center',
	      				width : "8%"
	      				},
	      				{
	      				display : '结束时间（到）',
	      				name : 'endTime',
	      				align : 'center',
	      				width : "8%"
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
	               { display: '启用状态', name: 'status', align: 'center', width: "10%",
	            	  render : function(rowdata) {
	        		   if (rowdata.status == "1") {
	        				return "有效";
	        			}else if (rowdata.status == "2"){
	        				return "无效";
	        			}
	      			}
	               },
	                { display: '开放期模式', name: 'openFlag', align: 'center', width: "10%",
	               	   render : function(rowdata) {
	               		   if (rowdata.openFlag == "0") {
	               				return "有规律";
	               			}else if (rowdata.openFlag == "1"){
	               				return "无规律";
	               			}
	             			}
	                   }
	               ], 
	               data:${gridData}, 
	               rownumbers : true,
	               usePager: false, 
	               sortName: 'id', 
	               percentWidthMode:true,
	               height: '100%', 
	               width:"100%"
	     	});
		}else{
			//存续期参数
			durationGrid = $("#durationParam").quiGrid({
	               columns: [
	               { display: '参数类型', name: 'paraType', align: 'center', width: "8%",
	            	   render : function(rowdata) {
	            		   if (rowdata.paraType == "1") {
	            				return "申购";
	            			}else if (rowdata.paraType == "2"){
	            				return "赎回";
	            			}
	          			}
	               },
	               { display: '起始日期', name: 'beginDate', align: 'center', width: "8%"
	               },
	               { display: '终止日期', name: 'endDate', align: 'center', width: "8%"
	               },
	               {
	      				display : '开始时间（从）',
	      				name : 'beginTime',
	      				align : 'center',
	      				width : "8%"
	      				},
	      				{
	      				display : '结束时间（到）',
	      				name : 'endTime',
	      				align : 'center',
	      				width : "8%"
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
	               { display: '启用状态', name: 'status', align: 'center', width: "10%",
	            	  render : function(rowdata) {
	        		   if (rowdata.status == "1") {
	        				return "有效";
	        			}else if (rowdata.status == "2"){
	        				return "无效";
	        			}
	      			}
	               },
	                { display: '开放期模式', name: 'openFlag', align: 'center', width: "10%",
	               	   render : function(rowdata) {
	               		   if (rowdata.openFlag == "0") {
	               				return "有规律";
	               			}else if (rowdata.openFlag == "1"){
	               				return "无规律";
	               			}
	             			}
	                   }
	               ], 
	               data:${gridData}, 
	               rownumbers : true,
	               usePager: false, 
	               sortName: 'id', 
	               percentWidthMode:true,
	               height: '100%', 
	               width:"100%"
	     	});
		}
		
	 $.quiDefaults.Grid.formatters['PARATYPE'] = function(value, column) {
		if (value == "1") {
			return "申购";
		}else if (value == "2"){
			return "赎回";
		}
	 }; 
	 $.quiDefaults.Grid.formatters['HANDLEFLAG'] = function(value, column) {
		if (value == "1") {
			return "批量";
		}else if (value == "2"){
			return "实时";
		}
	 };   
}
</script>
	</head>
	<body>
 		<div id="durationParam"></div>
	</body>
</html>