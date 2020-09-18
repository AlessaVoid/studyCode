<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>分红参数</title> 
<script type="text/javascript">
	//状态标志下拉框
	var validFlag={"list":[{"value":"1","key":"有效"},{"value":"2","key":"无效"}]};
    //数据表格初始化
    var dividendGrid;
	function initComplete(){
		var gridData=${gridData};
	 	//分红参数
		dividendGrid = $("#dividendParam").quiGrid({
            columns: [
            { display: '权益登记日', name: 'registrationDate', align: 'center', width: "33%"
            },
            { display: '每份分红金额', name: 'dividendPerUnit', align: 'center', width: "33%",
         	   render : function(rowdata) {
     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.dividendPerUnit,4) + '</div>';}
            },
            { display: '状态', name: 'status', align: 'center', width: "33%",type: 'VALIDFLAG',
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
            width:"100%",
            toolbar : {
				items : [{}]
			}
  	});
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}
}
	
//即时编辑输入格式化	
function fmtText(){
	if($(".l-grid-editor .textinput_click").size()>0){
		var length = $(".l-grid-editor .textinput_click").attr("maxlength");
		if(length == 16){
			// 输入文本在右边界对齐
			$(".l-grid-editor .textinput_click").css("text-align", "right");
			// 清除文本中的','分隔符
			rmoney1($(".l-grid-editor .textinput_click")[0]);
			// 为输入框绑定失焦时间
			$(".l-grid-editor .textinput_click").bind("blur", function() {
				// 清除文本中的'-'号
				var value = $(this).val().replace(/-/gi, "");
				if(value == ""){
					$(this).val(0);
				}else{
					$(this).val(value);
				}
				rmoney(this);
				fmoney(this,4);
			});
		}
	}
	$(".l-grid-editor .textinput_click").blur(function(){
		dividendGrid.endEdit();
	});
}
</script>
	</head>
	<body onclick="fmtText()">
		<form id="form1">
 			<div id="dividendParam" class="padding_right5"></div>
	   	</form>
	</body>
</html>