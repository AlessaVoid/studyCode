<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>灵活期限收益组合参数</title> 
<script type="text/javascript">
    //数据表格初始化
    var felPeriodProfitGrid;
	function initComplete(){
	var gridData=${dataList};
	//灵活期限收益组合参数
	felPeriodProfitGrid = $("#felPeriodProfitParam").quiGrid({
           columns: [
           { display: '机构代码', name: 'organCode', align: 'center', width: "8%"
           },
           { display: '机构名称', name: 'organName', align: 'center', width: "10%"
           },
           { display: '期限下限(含,天)', name: 'periodLow', align: 'center', width: "10%"
           },
           { display: '期限上限(天)', name: 'periodHigh', align: 'center', width: "10%"
           },
           { display: '收益率(%)', name: 'profitRate', align: 'center', width: "10%",
        	   render : function(rowdata) {
      				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.profitRate,2) + '</div>';}
            },
            { display: '销售费率(%)', name: 'sellRate', align: 'center', width: "10%",
         	   render : function(rowdata) {
      				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.sellRate,2) + '</div>';}
            },
           { display: '启用日期', name: 'beginDate', align: 'center', width: "10%"
           },
           { display: '客户类型', name: 'custType', align: 'center', width: "10%",
        	   render : function(rowdata) {
        		    if (rowdata.custType == "1") {
        				return "个人";
        			}else if (rowdata.custType == "2") {
        				return "机构";
        			}
      			}
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
           sortName: 'id', 
           height: '100%',
   	});
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}
}
</script>
	</head>
	<body onclick="fmtText()">
		<form id="form1">
   			<div id="felPeriodProfitParam" class="padding_right5"></div>
	   	</form>
	</body>
</html>