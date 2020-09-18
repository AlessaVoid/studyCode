<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>固定期限收益类组合参数</title> 
		<script type="text/javascript">
    //数据表格初始化
    var fixPeriodProfitGrid;
	function initComplete(){
		var gridData=${gridData};
	//固定期限收益类组合参数
	fixPeriodProfitGrid = $("#fixPeriodProfitParam").quiGrid({
           columns: [
           { display: '存续周期', name: 'durationPeriod', align: 'center', width: "20%"
           },
           { display: '客户收益率%', name: 'profitRate', align: 'center', width: "20%",
        	   render : function(rowdata) {
    				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.profitRate,2) + '</div>';}
           },
           { display: '启用日期', name: 'beginDate', align: 'center', width: "20%"
           },
           { display: '客户类型', name: 'custType', align: 'center', width: "20%",
        	   render : function(rowdata) {
       		    if (rowdata.custType == "1") {
       				return "个人";
       			}else if (rowdata.custType == "2") {
       				return "机构";
       			}
     			}
           },
           { display: '启用状态', name: 'status', align: 'center', width: "20%",
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
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}
}
</script>
	</head>
	<body>
   		<div id="fixPeriodProfitParam" class="padding_right5"></div>
	</body>
</html>