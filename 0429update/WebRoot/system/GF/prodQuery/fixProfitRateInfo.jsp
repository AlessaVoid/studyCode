<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>固定收益类参数</title>
		<script type="text/javascript">
	//有效标志下拉框
	var startFlag={"list":[{"value":"1","key":"有效"},{"value":"2","key":"无效"}]};
	//客户类型下拉框
	var custType={"list":[{"value":"0","key":"不区分"},{"value":"1","key":"个人"},{"value":"2","key":"机构"}]};
	
    //数据表格初始化
    var fixProfitGrid;
	function initComplete(){
	var gridData=${gridData};
	//固定收益参数
	fixProfitGrid = $("#fixProfitParam").quiGrid({
           columns: [
			{ display: '固定收益类客户收益率(%)', name: 'profitRate', align: 'center', width: "25%",
				   render : function(rowdata) {
					return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.profitRate,2) + '</div>';}
			},
           { display: '启用日期', name: 'beginDate', align: 'center', width: "25%",
        	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"}
           },
           { display: '客户类型', name: 'custType', align: 'center', width: "25%",
        	   render : function(rowdata) {
        		   if (rowdata.custType == "0") {
        				return "不区分";
        			}else if (rowdata.custType == "1") {
        				return "个人";
        			}else if (rowdata.custType == "2") {
        				return "机构";
        			}
      			}
           },
           { display: '启用状态', name: 'status', align: 'center', width: "25%",
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
		   items : [ {} ]
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
		//获取maxlength属性用于确定是否收益率输入框
		var length = $(".l-grid-editor .textinput_click").attr("maxlength");
		if(length == 7){
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
	    fixProfitGrid.endEdit();
	});
}
</script> 
	</head>
	<body onclick="fmtText()">
		<form id="form1">
   			<div id="fixProfitParam" class="padding_right5"></div>
	   	</form>
	</body>
</html>