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
	var startFlag={"list":[{"value":"1","key":"有效"},{"value":"2","key":"无效"}]};
    //数据表格初始化
    var durationGrid;
    var copyType="${copyType}";
	function initComplete(){
		changeCustType(0);
		createCheckBox("${webProdOpenDurationRule.openPeriod}");
		createChecked();
		var gridData=${gridData};
		if(1!="${isCurrProdt}"){
			//存续期参数
			durationGrid = $("#durationParam").quiGrid({
	               columns: [
	               { display: '参数类型', name: 'paraType', align: 'center', width: "8%",
	            	   editor:{type:'select',data:paramType,selWidth:100,boxWidth:90},
	            	   render : function(rowdata) {
		           		    if(rowdata.paraType == "1") {
		           			   return "申购";
		           			}else if (rowdata.paraType == "2"){
		           			   return "赎回";
		           			}
	           		   }
	               },
	               { display: '起始日期', name: 'beginDate', align: 'center', width: "8%",
	            	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
	            	   render : function(rowdata) {
	        				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginDate.replace(/-/gi, "") + '</div>';}
	               },
	               { display: '终止日期', name: 'endDate', align: 'center', width: "8%",
	            	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
	            	   render : function(rowdata) {
	        				return '<div style="margin-right: 45%;" align="right">' + rowdata.endDate.replace(/-/gi, "") + '</div>';}
	               },
	               { display: '开始时间（从）', name: 'beginTime', align: 'center', width: "8%",
	             	   editor:{type:"text",dateFmt: "HHmmss",detailEdit:"false"},
	             	   render : function(rowdata) {
	             		   if(rowdata.beginTime==null){
	            					return '<div style="margin-right: 45%;" align="right">' + "" + '</div>';
	             		   }else{
	         				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginTime.replace(/-/gi, "") + '</div>';};
	             	   	   }
	                },
	                { display: '结束时间（到）', name: 'endTime', align: 'center', width: "8%",
	             	   editor:{type:"text",dateFmt: "HHmmss",detailEdit:"false"},
	             	   render : function(rowdata) {
	                		if(rowdata.endTime==null){
	   						return '<div style="margin-right: 45%;" align="right">' + "" + '</div>';
	    		   			}else{
	 						return '<div style="margin-right: 45%;" align="right">' + rowdata.endTime.replace(/-/gi, "") + '</div>';};
	    	   	   			}
	                },
	               { display: '开放期额度', name: 'limit', align: 'center', width: "10%",
	                   editor:{type:"text",maxlength:14},
	             	   render : function(rowdata) {
	        				return '<div  style="margin-right: 45%;" align="right">' + formatRound(rowdata.limit,2)+ '</div>';}
	              },
	               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "10%",
	            	   editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
	            	   render : function(rowdata) {
		           		    if(rowdata.amtHandleFlag == "1") {
		           			   return "批量";
		           			}else if (rowdata.amtHandleFlag == "2"){
		           			   return "实时";
		           			}
	         			}
	               },
	               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "10%",
	            	   editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
	            	   render : function(rowdata) {
		           		    if(rowdata.quotAffirmType == "1") {
		           			   return "批量";
		           			}else if (rowdata.quotAffirmType == "2"){
		           			   return "实时";
		           			}
	         			}
	               },
	               { display: '净值日', name: 'quotAffirmDate', align: 'center', width: "10%",
	            	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"}
	               },
	               { display: '启用状态', name: 'status', align: 'center', width: "10%",type: 'STARTFLAG',
	             	   editor:{type:'select',data:startFlag,selWidth:100,boxWidth:90},
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
	               data:gridData, 
	               rownumbers : true,
	               enabledEdit: true,
	               usePager: false, 
	               sortName: 'id', 
	               checkbox:true,
	               percentWidthMode:true,
	               onAfterEdit: f_onAfterEdit,
	               width:"100%",
	               toolbar : {
					items : [ {text : '增加行',click : appendDuration,iconClass : 'icon_add'}, {line : true},
							  {text : '删除行',click : deleteDuration,iconClass : 'icon_remove'}, {line : true}]
				}
	     	});
		}else{
			//存续期参数
			durationGrid = $("#durationParam").quiGrid({
	               columns: [
	               { display: '参数类型', name: 'paraType', align: 'center', width: "8%",
	            	   editor:{type:'select',data:paramType,selWidth:100,boxWidth:90},
	            	   render : function(rowdata) {
		           		    if(rowdata.paraType == "1") {
		           			   return "申购";
		           			}else if (rowdata.paraType == "2"){
		           			   return "赎回";
		           			}
	           		   }
	               },
	               { display: '起始日期', name: 'beginDate', align: 'center', width: "8%",
	            	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
	            	   render : function(rowdata) {
	        				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginDate.replace(/-/gi, "") + '</div>';}
	               },
	               { display: '终止日期', name: 'endDate', align: 'center', width: "8%",
	            	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
	            	   render : function(rowdata) {
	        				return '<div style="margin-right: 45%;" align="right">' + rowdata.endDate.replace(/-/gi, "") + '</div>';}
	               },
	               { display: '开始时间（从）', name: 'beginTime', align: 'center', width: "8%",
	             	   editor:{type:"text",dateFmt: "HHmmss",detailEdit:"false"},
	             	   render : function(rowdata) {
	             		   if(rowdata.beginTime==null){
	            					return '<div style="margin-right: 45%;" align="right">' + "" + '</div>';
	             		   }else{
	         				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginTime.replace(/-/gi, "") + '</div>';};
	             	   	   }
	                },
	                { display: '结束时间（到）', name: 'endTime', align: 'center', width: "8%",
	             	   editor:{type:"text",dateFmt: "HHmmss",detailEdit:"false"},
	             	   render : function(rowdata) {
	                		if(rowdata.endTime==null){
	   						return '<div style="margin-right: 45%;" align="right">' + "" + '</div>';
	    		   			}else{
	 						return '<div style="margin-right: 45%;" align="right">' + rowdata.endTime.replace(/-/gi, "") + '</div>';};
	    	   	   			}
	                },
	               { display: '开放期额度', name: 'limit', align: 'center', width: "10%",
	                   editor:{type:"text",maxlength:14},
	             	   render : function(rowdata) {
	        				return '<div  style="margin-right: 45%;" align="right">' + formatRound(rowdata.limit,2)+ '</div>';}
	              },
	               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "10%",
	            	   editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
	            	   render : function(rowdata) {
		           		    if(rowdata.amtHandleFlag == "1") {
		           			   return "批量";
		           			}else if (rowdata.amtHandleFlag == "2"){
		           			   return "实时";
		           			}
	         			}
	               },
	               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "10%",
	            	   editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
	            	   render : function(rowdata) {
		           		    if(rowdata.quotAffirmType == "1") {
		           			   return "批量";
		           			}else if (rowdata.quotAffirmType == "2"){
		           			   return "实时";
		           			}
	         			}
	               },
	               { display: '份额确认日', name: 'quotAffirmDate', align: 'center', width: "10%",
	            	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"}
	               },
	               { display: '启用状态', name: 'status', align: 'center', width: "10%",type: 'STARTFLAG',
	             	   editor:{type:'select',data:startFlag,selWidth:100,boxWidth:90},
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
	               data:gridData, 
	               rownumbers : true,
	               enabledEdit: true,
	               usePager: false, 
	               sortName: 'id', 
	               checkbox:true,
	               percentWidthMode:true,
	               onAfterEdit: f_onAfterEdit,
	               width:"100%",
	               toolbar : {
					items : [ {text : '增加行',click : appendDuration,iconClass : 'icon_add'}, {line : true},
							  {text : '删除行',click : deleteDuration,iconClass : 'icon_remove'}, {line : true}]
				}
	     	});
		}
		
		

		//四舍五入算法,dp代表保留小数点位数
		function formatRound(num,dp){
			 return (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
		}
}
	function changeCustType(type){
		 type = $("#openPhaseModel").val();
		 if(type == '0'){
				$("#firstOpenDay").attr("disabled",false);
				$("#openPeriod").attr("disabled",false);
				$("#openType").attr("disabled",false);
				$("#amtHandleFlag").attr("disabled",false);
				$("#quotAffirmType").attr("disabled",false);
				$("#holidayMaintenance").attr("disabled",false);
				$("#limit").attr("disabled",false);
				$("#beginTime").attr("disabled",false);
				$("#endTime").attr("disabled",false);
				
			}else if(type == '1'){
				$("#firstOpenDay").attr("disabled",true);
				$("#openPeriod").attr("disabled",true);
				$("#openType").attr("disabled",true);
				$("#amtHandleFlag").attr("disabled",true);
				$("#quotAffirmType").attr("disabled",true);
				$("#holidayMaintenance").attr("disabled",true);
				$("#limit").attr("disabled",true);
				$("#beginTime").attr("disabled",true);
				$("#endTime").attr("disabled",true);
				
			}else{
				$("#firstOpenDay").attr("disabled",false);
				$("#openPeriod").attr("disabled",false);
				$("#openType").attr("disabled",false);
				$("#amtHandleFlag").attr("disabled",false);
				$("#quotAffirmType").attr("disabled",false);
				$("#holidayMaintenance").attr("disabled",false);
				$("#limit").attr("disabled",false);
				$("#beginTime").attr("disabled",false);
				$("#endTime").attr("disabled",false);
			}
			$("#firstOpenDay").render();
			$("#openPeriod").render();
			$("#openType").render();
			$("#amtHandleFlag").render();
			$("#quotAffirmType").render();
			$("#holidayMaintenance").render();
			$("#limit").render();
			$("#beginTime").render();
			$("#endTime").render();
	}
	//编辑后事件
	function f_onAfterEdit(e){ 
		if(e.column.name=='quotAffirmDate'){
			var quotAffirmDate = parseFloat(e.record.quotAffirmDate.replace(/\-/g, "\/"));
			var endDate = parseFloat(e.record.endDate.replace(/\-/g, "\/"));
			//金额 确认方式是批量  份额 只能为 批量
			if(e.record.amtHandleFlag == 1){
				if(e.record.quotAffirmType == 2){
					top.Dialog.alert("由于资金确认方式为批量,份额确认方式必须为批量！");
					return false;
				}
			}
			if(e.record.quotAffirmType == 2){
				if(quotAffirmDate != endDate){
					top.Dialog.alert("由于份额确认方式为实时确认,因此份额确认应该与终止日期相同");
					return false;
				}
			}else{
				if(quotAffirmDate < endDate){
					top.Dialog.alert("份额确认应该大于等于结束日期");
					return false;
				}
			}
		}
		return true;
	}
	//追加尾行
	function appendDuration(){
		var duration = {
		   paraType: "1",
		   beginDate: "${systemDate}",
           endDate: "${systemDate}",
           beginTime: "090000",
           endTime: "170000",
           limit : "1000000.00",
           amtHandleFlag: "1",
           quotAffirmType : "1",
           quotAffirmDate : "${systemDate}",
           aqhandleFlag : "1",
           status : "1",
           openFlag: "1"
        };
		durationGrid.add(duration);
	}
	//删除选中行
	function deleteDuration(){
	    //选中一行或多行
	    var rows = durationGrid.getSelectedRows();
	    if (rows.length == 0) {
           top.Dialog.alert('请至少选择一行'); 
           return;
        }
	    for(var index in rows){
	    	durationGrid.deleteRow(rows[index]);
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

//提交方法
function sub(formId) {
	//可以保存空值
	var param = $("#"+formId).formToArray();
	var map = {};
	map.name = "gridData";
	map.value= JSON.stringify(durationGrid.getData());
	param.push(map);
	var openPeriod = $("#openPeriod").val();
	var prodCode=$("#prodCode").val();
	$.post("<%=path%>/design/saveDurationInfo.htm?openPeriod="+openPeriod+"&prodCode"+prodCode,param,function(result) {
		if (result.success == "true" || result.success == true) {
			top.Dialog.alert(result.msg, function() {
				parent.setBaseInfo(result.bean);
				if(copyType == "copyType"){
					parent.turnPage("panel16","panel17");
				}else{
				if($("#index").val() != $("#oldIndex").val()){
					parent.changeDiv($("#index").val(),$("#oldIndex").val());
				}else{
					parent.changeDiv($("#index").val(),$("#index").val());
				}
				}
			});
		} else {
			$(".money").each(function(){
				fmoney(this);
			});
			top.Dialog.alert(result.msg);
		}
	}, "json");
 	
}
function skip(){
	if($("#index").val() != $("#oldIndex").val()){
		parent.changeDiv($("#index").val(),$("#oldIndex").val());
	}else{
		parent.changeDiv($("#index").val(),$("#index").val());
	}
}
function createCheckBox(val){
var $cont="";
if(val=='1'){
for (var int = 1; int < 8; int++) {
		$cont= $cont+"<input type=\"checkbox\" name=\"openWeeks\"  id=\"openWeeks_"+int+"\" value=\""+int+"\"/>周"+int;
	}
}else if(val=='2'){
	for (var int = 1; int < 29; int++) {
		 $cont= $cont+"<input type=\"checkbox\" name=\"period\" id=\"period_"+int+"\" value=\""+int+"\"/>"+int;
	}
}
else if(val=='3'){
	for (var int = 1; int < 29; int++) {
		 $cont= $cont+"<input type=\"checkbox\" name=\"period\" id=\"period_"+int+"\" value=\""+int+"\"/>"+int;
	}
}else if(val=='4'){
	for (var int = 1; int < 29; int++) {
		 $cont= $cont+"<input type=\"checkbox\" name=\"period\" id=\"period_"+int+"\" value=\""+int+"\"/>"+int;
	}
}
$("#div_date").empty();
$("#div_date").append($cont);
}


function createChecked(){
	var val = $("#openPeriod").val();
	var openDays = $("#openDays").val();
	var open = openDays.split("");
	var checked = new Array();
	var n = 0 ;
	for(var i=0;i<open.length;i++){
		if(open[i]=='1'){
			checked[n]=i+1;
			n = n+1;
		}
	}
	if(val=='1'){
		for(var j = 0;j<checked.length;j++){
			$("#openWeeks_"+checked[j]).attr("checked",true);
		}
	}else if(val=='2'){
		for(var j = 0;j<checked.length;j++){
			$("#period_"+checked[j]).attr("checked",true);
		}
	}else if(val=='3'){
		for(var j = 0;j<checked.length;j++){
			$("#period_"+checked[j]).attr("checked",true);
		}
	}else if(val=='4'){
		for(var j = 0;j<checked.length;j++){
			$("#period_"+checked[j]).attr("checked",true);
		}
	}
	}
//生成
function createBox(){
	//生成数据之前，删除原有对应数据
	var data = durationGrid.records;
	var openType = $("#openType").val();
	for (var record in data){
		if(data[record]['paraType']==openType){
			durationGrid.deleteRow(data[record]);
		}
	}
	var openPhaseModel = $("#openPhaseModel").val();
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	if(openPhaseModel == '0'){ 
		if (valid) {
			var prodcode=$("#prodCode").val();
			var firstOpenDay=$("#firstOpenDay").val();
			var holidayMaintenance=$("#holidayMaintenance").val();
			var openWeeks="";
			var period="";
			var openPeriod = $("#openPeriod").val();
			var openType = $("#openType").val();
			var str1 =document.getElementsByName("period"); 
			var	str=document.getElementsByName("openWeeks");
			var str2 = document.getElementsByName("openQuarters");
			var objarr=str.length;
			var objare=str1.length;
			var objaro=str2.length;
			var amtHandleFlag = $("#amtHandleFlag").val();
			var quotAffirmType =  $("#quotAffirmType").val();
			var beginTime = $("#beginTime").val();
			var endTime = $("#endTime").val();
			for(var i=0;i<objaro;i++){
				if(str2[i].checked==true){
					period+=","+str2[i].value;
				}
			}
			for(var i=0;i<objare;i++){
				if(str1[i].checked==true){
					period+=","+str1[i].value;
				}
			}
			period=period.substring(1, period.length);
			for(var i=0;i<objarr;i++){
				if(str[i].checked==true){
					openWeeks+=str[i].value;
				}
			}
			var limit=$("#limit").val();
			$.post("<%=path%>/design/getDurationDate.htm",{prodCode:prodcode,firstOpenDay:firstOpenDay,holidayMaintenance:holidayMaintenance,openWeeks:openWeeks,period:period,limit:limit,openPeriod:openPeriod,openType:openType,amtHandleFlag:amtHandleFlag,quotAffirmType:quotAffirmType,beginTime:beginTime,endTime:endTime},function(result){
				for(var i=0;i<result.length;i++){
					var duration = {
							   paraType:result[i].openType ,
							   beginDate:result[i].begin,
						       endDate:result[i].end,
						       limit : limit,
						       amtHandleFlag: result[i].amtHandleFlag,
						       quotAffirmType : result[i].quotAffirmType,
						       quotAffirmDate : result[i].quotAffirmDate,
						       status : "1" ,
						       openFlag : result[i].openFlag,
						       beginTime:result[i].beginTime,
						       endTime:result[i].endTime
						       
						    };
							durationGrid.add(duration);
				}
			});
		}else {
			top.Dialog.alert("验证未通过！");
		}
	}
}


</script>
	</head>
	<body onclick="fmtText()">
		<form id="form1">
 			<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
			<input type="hidden" name="buyIndex" id="buyIndex" value="${buyIndex }"/>
			<input type="hidden" name="index" id="index" value="${index }"/>
			<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
			<input type="hidden" name="openPhaseModel" id="openPhaseModel_h" value="${openPhaseModel }"/>
			<input type="hidden" name="openDays" id="openDays" value = "${webProdOpenDurationRule.openDays}"/>
   				 <table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td>开放期模式:</td>
					<td>
					<dic:select dicType="OPEN_PHASE_MODEL" dicNo="${webProdOpenDurationRule.openPhaseModel}" tgClass="validate[required]" onChange="changeCustType(this.value)" id="openPhaseModel" name="openPhaseModel"></dic:select>
					<span class="star">*</span>
					</td>
					<td>首次开放日:</td>
					<td>
					<input type="text" name="firstOpenDay"   id="firstOpenDay" class="date validate[required]" dateFmt="yyyyMMdd" value="${webProdOpenDurationRule.firstOpenDay}"/>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>开始时间(从):</td>
					<td>
					<input type="text" name="beginTime"   id="beginTime" dateFmt="HHmmss"  class="date" value="${webProdOpenDurationRule.beginTime}"/>
					<span class="star">*</span>
					</td>
					<td>结束时间(到):</td>
					<td>
					<input type="text" name="endTime"   id="endTime" dateFmt="HHmmss"  class="date" value="${webProdOpenDurationRule.endTime}"/>
					<span class="star">*</span>
					</td> 
				</tr>
				<tr>
					<td>开放周期:</td>
					<td>
					<dic:select dicType="Open_Cycle" dicNo="${webProdOpenDurationRule.openPeriod}" id="openPeriod" name="openPeriod" tgClass="validate[required]"  onchange="createCheckBox(this.value)"></dic:select>
					<span class="star">*</span>
					</td>
					<td>开放类型:</td>
					<td>
					<dic:select dicType="OPEN_TYPE" dicNo="${webProdOpenDurationRule.openType}" tgClass="validate[required]" id="openType" name="openType"></dic:select>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
				<td>日期区间：</td>
				<td colspan="3">
				<span class="star">*</span>
				<div id="div_date">
				</div>
				</td>
				</tr>
				<tr>
					<td>资金处理方式：</td>
					<td>
					<dic:select dicType="purchase_Amt_Handle_Flag" dicNo="${webProdOpenDurationRule.amtHandleFlag}" tgClass="validate[required]" id="amtHandleFlag" name="amtHandleFlag"></dic:select>
					<span class="star">*</span>
					</td>
					<td>份额确认方式：</td>
					<td>
					<dic:select dicType="purchase_Quot_Affirm_Type" dicNo="${webProdOpenDurationRule.quotAffirmType}" tgClass="validate[required]" id="quotAffirmType" name="quotAffirmType"></dic:select>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>节假日维护:</td>
					<td>
					<dic:select  dicType="HOLIDAY_MAINTENANCE" dicNo="${webProdOpenDurationRule.holidayMaintenance}" tgClass="validate[required]" id="holidayMaintenance" name="holidayMaintenance"></dic:select>
					<span class="star">*</span>
					</td>
					<td>开放期额度:(元)</td>
					<td>
						<input type="text" id="limit" name="limit" value="${webProdOpenDurationRule.limit}"  onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="14"/>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td  colspan="8">
						<div align="center">
							<button type="button" onclick="createBox()"><span class="icon_find">生成</span></button>
						</div>
					</td>
				</tr>
				
				</table> 
   			<div id="durationParam"></div>
		<div align="center">
			<button type="button" onclick="return sub('form1')" class="saveButton"/>
			<button type="button" onclick="skip()" name="跳过" class="button"><span class='icon_page_next'>跳过</span></button>
		</div>
	   	</form>
	</body>
</html>