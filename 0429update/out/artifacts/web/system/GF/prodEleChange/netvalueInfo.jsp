<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>净值参数</title> 
	</head>
	
 <script type="text/javascript">
    //数据表格初始化
    var netvalueGrid;
	function initComplete(){
		var gridData=${gridData};
		//净值参数
		netvalueGrid = $("#netvalueParam").quiGrid({
	           columns: [
	           { display: '净值日期', name: 'netvalueDate', align: 'center', width: "33%"
	           },
	           { display: '净值', name: 'netvalue', align: 'center', width: "33%",
	        	   render : function(rowdata) {
	     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.netvalue,8)+ '</div>';
	     			}
	           },
	           { display: '状态', name: 'status', align: 'center', width: "33%",
	        	   render : function(rowdata) {
            		   if (rowdata.status == "1") {
            				return "有效";
            			}else if (rowdata.status == "2"){
            				return "无效";
            			}
          			}
	           }
	           ], 
	           data:gridData, 
	           rownumbers : true,
	           usePager: false, 
	           sortName: 'id', 
	           percentWidthMode:true,
	           height: '100%', 
	   	});
	   		//四舍五入算法,dp代表保留小数点位数
		function formatRound(num,dp){
		    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
		}
		$.quiDefaults.Grid.formatters['STARTFLAG'] = function(value, column) {
			if (value == "1") {
				return "启用";
			}else if (value == "2"){
				return "停用";
			}
		};
	}
</script>
<body>
	<div id="netvalueParam" class="padding_right5"></div>
	</body>
</html>