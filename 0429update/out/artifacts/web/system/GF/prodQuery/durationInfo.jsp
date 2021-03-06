<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>存续期参数</title> 
<script type="text/javascript">
 	//参数类型下拉框
	var paramType={"list":[{"value":"1","key":"申购"},{"value":"2","key":"赎回"}]};
	//处理方式下拉框
	var aqhandleFlag={"list":[{"value":"1","key":"批量"},{"value":"2","key":"实时"}]};
	//启用标志下拉框
	var startFlag={"list":[{"value":"1","key":"启用"},{"value":"2","key":"停用"}]};
    //数据表格初始化
    var durationGrid;
	function initComplete(){
		var gridData=${gridData};
		if(1 == "${isCurrProdt}"){
			//存续期参数
			durationGrid = $("#durationParam").quiGrid({
	               columns: [
	               { display: '参数类型', name: 'paraType', align: 'center', width: "10%",
	            	   render : function(rowdata) {
		           		    if(rowdata.paraType == "1") {
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
	               { display: '开放期额度', name: 'limit', align: 'center', width: "10%",
	             	   render : function(rowdata) {
	         				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.limit,2) + '</div>';}
	               },
	               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "10%",
	            	   render : function(rowdata) {
	            		    if(rowdata.amtHandleFlag == "1") {
	            			   return "批量";
	            			}else if (rowdata.amtHandleFlag == "2"){
	            			   return "实时";
	            			}
	          			}
	               },
	               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "10%",
	            	   render : function(rowdata) {
		           		    if(rowdata.quotAffirmType == "1") {
		           			   return "批量";
		           			}else if (rowdata.quotAffirmType == "2"){
		           			   return "实时";
		           			}
	         			}
	               },
	               { display: '净值日', name: 'quotAffirmDate', align: 'center', width: "10%"
	               },
	               { display: '启用状态', name: 'status', align: 'center', width: "10%",type: 'STARTFLAG',
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
		}else{
			//存续期参数
			durationGrid = $("#durationParam").quiGrid({
	               columns: [
	               { display: '参数类型', name: 'paraType', align: 'center', width: "10%",
	            	   render : function(rowdata) {
		           		    if(rowdata.paraType == "1") {
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
	               { display: '开放期额度', name: 'limit', align: 'center', width: "10%",
	             	   render : function(rowdata) {
	         				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.limit,2) + '</div>';}
	               },
	               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "10%",
	            	   render : function(rowdata) {
	            		    if(rowdata.amtHandleFlag == "1") {
	            			   return "批量";
	            			}else if (rowdata.amtHandleFlag == "2"){
	            			   return "实时";
	            			}
	          			}
	               },
	               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "10%",
	            	   render : function(rowdata) {
		           		    if(rowdata.quotAffirmType == "1") {
		           			   return "批量";
		           			}else if (rowdata.quotAffirmType == "2"){
		           			   return "实时";
		           			}
	         			}
	               },
	               { display: '份额确认日', name: 'quotAffirmDate', align: 'center', width: "10%"
	               },
	               { display: '启用状态', name: 'status', align: 'center', width: "10%",type: 'STARTFLAG',
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
		}
		
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
				fmoney(this,2);
			});
		}
	}
	$(".l-grid-editor .textinput_click").blur(function(){
		durationGrid.endEdit();
	});
}
</script>
	</head>
	<body>
		<form id="form1">
 			<div id="durationParam"></div>
	   	</form>
	</body>
</html>