<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/common_list.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>基础参数变更</title>
<script type="text/javascript">
 var dividendGrid;
	function initComplete(){
		var gridData=${dataList};
	 	//分红参数
		dividendGrid = $("#dividendParam").quiGrid({
            columns: [
            { display: '权益登记日', name: 'registrationDate', align: 'center', width: "33%"
            },
            { display: '每份分红金额(元)', name: 'dividendPerUnit', align: 'center', width: "33%",
           	  render : function(rowdata) {
       				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.dividendPerUnit,4) + '</div>';
       			}
            },
            { display: '状态', name: 'validStatus', align: 'center', width: "33%",
            	 render : function(rowdata) {
            		   if (rowdata.validStatus == "1") {
            				return "有效";
            			}else if (rowdata.validStatus == "2"){
            				return "无效";
            			}
          		}
            }], 
            data:gridData, 
            rownumbers : true,
            usePager: false, 
            sortName: 'orderNum', 
            percentWidthMode:true,
            height: '100%', 
            width:"100%"
  	});
	 $.quiDefaults.Grid.formatters['VALIDFLAG'] = function(value, column) {
		if (value == "1") {
			return "有效";
		}else if (value == "2"){
			return "无效";
		}
	 };
}
</script>
</head>
<body>
	<form id="form1">
		<div id="dividendParam" class="padding_right5"></div>
	</form>
</body>
</html>