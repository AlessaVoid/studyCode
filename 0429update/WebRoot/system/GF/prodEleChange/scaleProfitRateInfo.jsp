<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>规模期限收益组合参数</title> 
	</head>
 <script type="text/javascript">
    //数据表格初始化
    var scaleProfitGrid;
	function initComplete(){
		var gridData=${gridData};
	//规模收益类组合参数
	scaleProfitGrid = $("#scaleProfitParam").quiGrid({
           columns: [
               { display: '客户类型', name: 'custType', align: 'center', width: "16%",
            	   render : function(rowdata) {
            		    if (rowdata.custType == "1") {
            				return "个人";
            			}else if (rowdata.custType == "2"){
            				return "机构";
            			}
          			}
               },
           { display: '金额下限(含)', name: 'lowAmt', align: 'center', width: "18%"
           },
           { display: '金额上限', name: 'highAmt', align: 'center', width: "18%"
           },
           { display: '客户收益率%', name: 'profitRate', align: 'center', width: "16%",
        	   render : function(rowdata) {
   				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.profitRate,2) + '</div>';}
           },
           { display: '启用日期', name: 'beginDate', align: 'center', width: "16%"
           },
           { display: '启用状态', name: 'status', align: 'center', width: "16%",
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
           width:"100%"
   	});
	 $.quiDefaults.Grid.formatters['STARTFLAG'] = function(value, column) {
		if (value == "1") {
			return "启用";
		}else if (value == "2"){
			return "停用";
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
 		//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}

</script>
<body>
   	<div id="scaleProfitParam" class="padding_right5"></div>
	</body>
</html>