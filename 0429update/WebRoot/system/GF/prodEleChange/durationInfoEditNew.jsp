<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--监听文字改变start-->
		<script type="text/javascript" src="<%=path%>/libs/js/method/typewatch.js"></script>
		<!--监听文字改变end-->
		
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
	var holidayMaintenanceFlag={"list":[{"value":"0","key":"不取消"},{"value":"1","key":"取消"},{"value":"2","key":"顺延"}]};
    //数据表格初始化
    var durationGrid;
    var copyType="${copyType}";
	function initComplete(){
    	/* $("#openPeriodNum").typeWatch({
	    	captureLength:2,
	    	highlight:false,
	    	wait:1000,
	    	callback:aa()
    	}); */
		changeCustType(0);
		createCheckBox("${webProdOpenDurationRule.openPeriod}");
		createChecked();
		changeOpenPeriod("${webProdOpenDurationRule.openPeriod}");
		var gridData=${gridData};
		//存续期参数
		durationGrid = $("#durationParam").quiGrid({
               columns: [
               { display: '参数类型', name: 'paraType', align: 'center', width: "5%",
            	   /* editor:{type:'select',data:paramType,selWidth:100,boxWidth:90}, */
            	   render : function(rowdata) {
	           		    if(rowdata.paraType == "1") {
	           			   return "申购";
	           			}else if (rowdata.paraType == "2"){
	           			   return "赎回";
	           			}
           		   }
               },
               { display: '起始日期', name: 'beginDate', align: 'center', width: "5%",
            	   /* editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"}, */
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginDate.replace(/-/gi, "") + '</div>';}
               },
               { display: '终止日期', name: 'endDate', align: 'center', width: "5%",
            	   /* editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"}, */
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.endDate.replace(/-/gi, "") + '</div>';}
               },
                { display: '开始时间', name: 'beginTime', align: 'center', width: "5%",
            	   /* editor:{type:"date",dateFmt: "HH:mm",detailEdit:"true"}, */
            	   editor:{type:"text",maxlength:6},
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginTime.replace(/-/gi, "") + '</div>';}
               },
               { display: '终止时间', name: 'endTime', align: 'center', width: "5%",
            	   /* editor:{type:"date",dateFmt: "HH:mm",detailEdit:"true"}, */
            	   editor:{type:"text",maxlength:6},
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.endTime.replace(/-/gi, "") + '</div>';}
               },
               { display: '开放期额度', name: 'limit', align: 'center', width: "12%",
                   editor:{type:"text",maxlength:14},
             	   render : function(rowdata) {
        				return '<div  style="margin-right: 45%;" align="right">' + formatRound(rowdata.limit,2)+ '</div>';}
              },
               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "5%",
            	   editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
            	   render : function(rowdata) {
	           		    if(rowdata.amtHandleFlag == "1") {
	           			   return "批量";
	           			}else if (rowdata.amtHandleFlag == "2"){
	           			   return "实时";
	           			}
         			}
               },
               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "5%",
            	   editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
            	   render : function(rowdata) {
	           		    if(rowdata.quotAffirmType == "1") {
	           			   return "批量";
	           			}else if (rowdata.quotAffirmType == "2"){
	           			   return "实时";
	           			}
         			}
               },
               { display: '净值日', name: 'quotAffirmDate', align: 'center', width: "5%",
            	   /* editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"} */
               },
               { display: '启用状态', name: 'status', align: 'center', width: "5%",type: 'STARTFLAG',
             	   editor:{type:'select',data:startFlag,selWidth:100,boxWidth:90},
            	   render : function(rowdata) {
            		   if (rowdata.status == "1") {
            				return "有效";
            			}else if (rowdata.status == "2"){
            				return "无效";
            			}
          			}
                },
                { display: '开放期模式', name: 'openFlag', align: 'center', width: "5%",
               	   render : function(rowdata) {
               		   if (rowdata.openFlag == "0") {
               				return "有规律";
               			}else if (rowdata.openFlag == "1"){
               				return "无规律";
               			}
             			}
                   },
                 { display: '节假日维护', name: 'holidayMaintenance', align: 'center', width: "5%",
                 	   editor:{type:'select',data:holidayMaintenanceFlag,selWidth:100,boxWidth:90},
                   	   render : function(rowdata) {
                   		   if (rowdata.holidayMaintenance == "0") {
                   				return "不取消";
                   			}else if (rowdata.holidayMaintenance == "1"){
                   				return "取消";
                   			}else if (rowdata.holidayMaintenance == "2"){
                   				return "顺延";
                   			}
                   			
                 		}
                 },
               { display: '周期起始日期', name: 'periodStartDate', align: 'center', width: "5%",
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.periodStartDate.replace(/-/gi, "") + '</div>';}
               },
               { display: '周期终止日期', name: 'periodEndDate', align: 'center', width: "5%",
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.periodEndDate.replace(/-/gi, "") + '</div>';}
               },
               { display: '开放日标记日', name: 'dateFlag', align: 'center', width: "5%",
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.dateFlag.replace(/-/gi, "") + '</div>';}
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
				items : [ /* {text : '增加行',click : appendDuration,iconClass : 'icon_add'}, {line : true}, */
						  {text : '删除行',click : deleteDuration,iconClass : 'icon_remove'}, {line : true},
						  {text : '重新生成',click : createNewDuration,iconClass : 'icon_add'}, {line : true}]
			}
     	});
		

		//四舍五入算法,dp代表保留小数点位数
		function formatRound(num,dp){
			 return (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
		}
}
	function changeCustType(type){
		 type = $("#openPhaseModel").val();
		if(type == '0'){
			$("#firstOpenDay").attr("disabled",false);
			$("#openType").attr("disabled",false);
			$("#amtHandleFlag").attr("disabled",false);
			$("#quotAffirmType").attr("disabled",false);
			$("#holidayMaintenance").attr("disabled",false);
			$("#limit").attr("disabled",false);
			$("#openPeriodNum").attr("disabled",false);
			$("#openPeriodNum").render();
			$("#toBeginNum").attr("disabled",false);
			$("#toBeginNum").render();
			$("#toEndNum").attr("disabled",false);
			$("#toEndNum").render();
			var openPeriodArr = document.getElementsByName("openPeriod");
			openPeriodArr[0].disabled=false;
		}else if(type == '1'){
			$("#firstOpenDay").attr("disabled",true);
			$("#openType").attr("disabled",true);
			$("#amtHandleFlag").attr("disabled",true);
			$("#quotAffirmType").attr("disabled",true);
			$("#holidayMaintenance").attr("disabled",true);
			$("#limit").attr("disabled",true);
			$("#openPeriodNum").attr("disabled",true);
			$("#openPeriodNum").render();
			$("#toBeginNum").attr("disabled",true);
			$("#toBeginNum").render();
			$("#toEndNum").attr("disabled",true);
			$("#toEndNum").render();
			var openPeriodArr = document.getElementsByName("openPeriod");
			openPeriodArr[0].disabled=true;
		}else{
			$("#firstOpenDay").attr("disabled",false);
			$("#openType").attr("disabled",false);
			$("#amtHandleFlag").attr("disabled",false);
			$("#quotAffirmType").attr("disabled",false);
			$("#holidayMaintenance").attr("disabled",false);
			$("#limit").attr("disabled",false);
			var openPeriodArr = document.getElementsByName("openPeriod");
			openPeriodArr[0].disabled=false;
		}
		$("#firstOpenDay").render();
		$("#openPeriod").render();
		$("#openType").render();
		$("#amtHandleFlag").render();
		$("#quotAffirmType").render();
		$("#holidayMaintenance").render();
		$("#limit").render();
		
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
           periodStartDate: "${systemDate}",
           periodEndDate: "${systemDate}",
           dateFlag: "${systemDate}",
           limit : "1000000.00",
           amtHandleFlag: "1",
           quotAffirmType : "1",
           quotAffirmDate : "${systemDate}",
           aqhandleFlag : "1",
           status : "1",
           openFlag: "0",
           holidayMaintenance : "0"
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
var modifiCreate = document.getElementsByName("modifiCreate")[0].value;
	var ms = '';
	if(modifiCreate=='1'){
		ms = '保存数据来自生成';
	}else if(modifiCreate=='2'){
		ms = '保存数据来自重新生成';
	}else if(modifiCreate=='3'){
		ms = '保存数据来自原数据修改';
	}else{
		top.Dialog.alert("请选择数据来源");
		return;
	}
	top.Dialog.confirm(ms,function() {
	//可以保存空值
		var param = $("#"+formId).formToArray();
		//原有生成列表的所有开放期数据
	    var dataRows = durationGrid.getData();
	    var reg = '^[0-9]*$';
	    for(var index in dataRows){
			var dataRow = dataRows[index];
			var rowIndex = Number(index)+Number(1);
			if(dataRow.limit.trim()==""){	 
				top.Dialog.alert('第'+rowIndex+'条额度不能为空');
				return;
			}
			if(dataRow.limit.trim()!=""){
				 var limits = parseFloat(dataRow.limit).toString();
				 var r = limits.match(reg);
				 if(r==null){
				 	 top.Dialog.alert('第'+rowIndex+'条额度格式不正确');
				 	 return;
				 }
			}
		}
		var map = {};
		map.name = "gridData";
		map.value= JSON.stringify(durationGrid.getData());
		param.push(map);
		//var openPeriod = $("#openPeriod").val();
		var openPeriod = document.getElementsByName("openPeriod")[0].value;
		var prodCode=$("#prodCode").val();
		$.post("<%=path%>/gfProdEleChange/compareDurationInfoNew.htm?openPeriod="+openPeriod+"&prodCode"+prodCode+"&modifiCreate"+modifiCreate,param,function(result) {
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
	 	});
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
/* for (var int = 1; int < 8; int++) {
		$cont= $cont+"<input type=\"checkbox\" name=\"openWeeks\"  id=\"openWeeks_"+int+"\" value=\""+int+"\"/>周"+int;
	} */
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
		/* for(var j = 0;j<checked.length;j++){
			$("#openWeeks_"+checked[j]).attr("checked",true);
		} */
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
			/* var openWeeks=""; */
			var period="";
			var openPeriod = document.getElementsByName("openPeriod")[0].value;
			var openType = $("#openType").val();
			var str1 =document.getElementsByName("period"); 
			/* var	str=document.getElementsByName("openWeeks"); */
			var str2 = document.getElementsByName("openQuarters");
			/* var objarr=str.length; */
			var toBeginNum=$("#toBeginNum").val();
			var toEndNum=$("#toEndNum").val();
			var openPeriodNum=$("#openPeriodNum").val();
			var objare=str1.length;
			var objaro=str2.length;
			var amtHandleFlag = $("#amtHandleFlag").val();
			var quotAffirmType =  $("#quotAffirmType").val();
			var beginTime =  $("#beginTime").val();
			var endTime =  $("#endTime").val();
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
			/* for(var i=0;i<objarr;i++){
				if(str[i].checked==true){
					openWeeks+=str[i].value;
				}
			} */
			var limit=$("#limit").val();
			//alert(prodCode+"||"+firstOpenDay+"||"+holidayMaintenance+"||"+period+"||"+limit+"||"+openPeriod+"||"+openType+"||"+amtHandleFlag+"||"+quotAffirmType+"||"+toBeginNum+"||"+toEndNum+"||"+openPeriodNum);
			$.post("<%=path%>/gfProdEleChange/getDurationDateNew.htm",{prodCode:prodcode,firstOpenDay:firstOpenDay,holidayMaintenance:holidayMaintenance,period:period,limit:limit,openPeriod:openPeriod,openType:openType,amtHandleFlag:amtHandleFlag,quotAffirmType:quotAffirmType,toBeginNum:toBeginNum,toEndNum:toEndNum,openPeriodNum:openPeriodNum,createOrModify:"0",dataCreateIdea:"design",beginTime:beginTime,endTime:endTime,createType:"1"},function(result){
				for(var i=0;i<result.length;i++){
					var duration = {
							   paraType:result[i].openType ,
							   beginDate:result[i].begin,
						       endDate:result[i].end,
						       beginTime:result[i].beginTime,
						       endTime:result[i].endTime,
						       periodStartDate:result[i].periodStartDate,
						       periodEndDate:result[i].periodEndDate,
						       dateFlag:result[i].dateFlag,
						       limit : limit,
						       amtHandleFlag: result[i].amtHandleFlag,
						       quotAffirmType : result[i].quotAffirmType,
						       quotAffirmDate : result[i].quotAffirmDate,
						       status : "1" ,
						       openFlag : result[i].openFlag,
						       holidayMaintenance:result[i].holidayMaintenance
						       
						    };
							durationGrid.add(duration);
				}
			});
		}else {
			top.Dialog.alert("验证未通过！");
		}
	}
}
//如果是日的则隐藏   周期的数目填写以及往前和往后推天数的填写
function changeOpenPeriod(openPeriod){
			//var openPeriod = $("#openPeriod").val();
			if(openPeriod==0){
				$("#openPeriodNum").attr("disabled","disabled");
				$("#openPeriodNum").render();
				$("#toBeginNum").attr("disabled","disabled");
				$("#toBeginNum").render();
				$("#toEndNum").attr("disabled","disabled");
				$("#toEndNum").render();
				//createCheckBox(openPeriod);
			}else{
				$("#openPeriodNum").removeAttr("disabled");
				$("#toBeginNum").removeAttr("disabled");
				$("#toEndNum").removeAttr("disabled");
				//createCheckBox(openPeriod);
			}
			
		}
//修改节假日维护
//修改节假日维护
function createNewDuration(){
	//生成   节假日   ID 维护 标记日 
	 //选中一行或多行
    var rows = durationGrid.getSelectedRows();
	var holidayMaintenance = $("#holidayMaintenance").val();
    if (rows.length != 1) {
       top.Dialog.alert('请选择一行'); 
       return;
    }
    var selectData =new Array();
    for(var index in rows){
   	   var prodOpenData = rows[index];
   	   if(prodOpenData.holidayMaintenance==holidayMaintenance){
   	     /* alert(prodOpenData.holidayMaintenance);
   	     alert(holidayMaintenance); */
   		 top.Dialog.alert('节假日维护相同，不能重新生成！');
   		 return;
   	   }
   	   selectData.push(prodOpenData);
    }
     var mapData = JSON.stringify(selectData);
    //原有生成列表的所有开放期数据
    var dataRows = durationGrid.getData();
     var reg = '([0-1]?[0-9]|2[0-3]):([0-5][0-9])';
    for(var index in dataRows){
   	   var dataRow = dataRows[index];
   	   var rowIndex = Number(index)+Number(1);
   	   if(dataRow.beginTime.trim()!=""){
   	   	 var r = dataRow.beginTime.match(reg);
   	   	 if(r==null){
   	   	 	 top.Dialog.alert('第'+rowIndex+'条开始时间格式不正确');
   	   	 	 return;
   	   	 }
   	   }
   	   if(dataRow.endTime.trim()!=""){
   	   	 var r = dataRow.endTime.match(reg);
   	   	 if(r==null){
   	   	 	 top.Dialog.alert('第'+rowIndex+'条结束时间格式不正确');
   	   	 	 return;
   	   	 }
   	   }
   	   if(dataRow.holidayMaintenance==""){
   		 top.Dialog.alert('第'+rowIndex+'条节假日维护不能为空');
   		 return;
   	   }
   	   if(dataRow.amtHandleFlag==""){
   		 top.Dialog.alert('第'+rowIndex+'条资金处理方式不能为空');
   		 return;
   	   }
   	   if(dataRow.quotAffirmType==""){
   		 top.Dialog.alert('第'+rowIndex+'条份额确认方式不能为空');
   		 return;
   	   }
   	   if(dataRow.status==""){
   		 top.Dialog.alert('第'+rowIndex+'条启用状态不能为空');
   		 return;
   	   }
    }
    
    
    
    var gridData = JSON.stringify(durationGrid.getData());
    var data = durationGrid.records;
    //var openType = $("#openType").val();
	for (var record in data){
		durationGrid.deleteRow(data[record]);
	}
	var openPhaseModel = $("#openPhaseModel").val();
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	//alert(valid);
	if(openPhaseModel == '0'){ 
		if (valid) {
			var prodcode=$("#prodCode").val();
			var openType=$("#openType").val();//开放类型
			var firstOpenDay=$("#firstOpenDay").val();
			var openPeriod = document.getElementsByName("openPeriod")[0].value;//开放周期
			var holidayMaintenance=$("#holidayMaintenance").val();
			var amtHandleFlag = $("#amtHandleFlag").val();//资金处理方式
			var quotAffirmType =  $("#quotAffirmType").val();//份额处理方式
			var toBeginNum=$("#toBeginNum").val();//往前推
			var toEndNum=$("#toEndNum").val();//往后推
			var openPeriodNum=$("#openPeriodNum").val();//周期数目
			var openPhaseModel=$("#openPhaseModel").val();//开放期模式
			var beginTime =  $("#beginTime").val();
			var endTime =  $("#endTime").val();
			var limit=$("#limit").val();
			$.post("<%=path%>/gfProdEleChange/getDurationDateNew.htm",{gridData:gridData,mapData:mapData,createOrModify:"1",dataCreateIdea:"design",createType:"2",
			prodCode:prodcode,openType:openType,firstOpenDay:firstOpenDay,openPeriod:openPeriod,limit:limit,
holidayMaintenance:holidayMaintenance,amtHandleFlag:amtHandleFlag,quotAffirmType:quotAffirmType,toBeginNum:toBeginNum,toEndNum:toEndNum,
openPeriodNum:openPeriodNum,openPhaseModel:openPhaseModel,beginTime:beginTime,endTime:endTime},function(result){
				for(var i=0;i<result.length;i++){
					var duration = {
							   paraType:result[i].openType ,
							   beginDate:result[i].begin,
						       endDate:result[i].end,
						       beginTime:result[i].beginTime,
						       endTime:result[i].endTime,
						       periodStartDate:result[i].periodStartDate,
						       periodEndDate:result[i].periodEndDate,
						       dateFlag:result[i].dateFlag,
						       limit : limit,
						       amtHandleFlag: result[i].amtHandleFlag,
						       quotAffirmType : result[i].quotAffirmType,
						       quotAffirmDate : result[i].quotAffirmDate,
						       status : "1" ,
						       openFlag : result[i].openFlag,
						       holidayMaintenance:result[i].holidayMaintenance
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
			<input type="hidden" id="order" name="order" value="${order}"/>
			<input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
			<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
			<%-- <input type="hidden" name="openPhaseModel" id="openPhaseModel_h" value="${openPhaseModel }"/> --%>
			<input type="hidden" name="openDays" id="openDays" value = "${webProdOpenDurationRule.openDays}"/>
   				 <table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td>开放期模式:</td>
					<td>
					<dic:select dicType="OPEN_PHASE_MODEL_NEW" dicNo="${webProdOpenDurationRule.openPhaseModel}" tgClass="validate[required]" onChange="changeCustType(this.value)" id="openPhaseModel" name="openPhaseModel"></dic:select>
					<span class="star">*</span>
					</td>
					<td>首次开放日:</td>
					<td>
					<input type="text" name="firstOpenDay"   id="firstOpenDay" class="date validate[required]" dateFmt="yyyyMMdd" value="${webProdOpenDurationRule.firstOpenDay}"/>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>开放周期:</td>
					<td>
					<input type="text" name="openPeriodNum" id="openPeriodNum" class="validate[required,custom[onlyNumber]"  maxlength="2" value="${webProdOpenDurationRule.openPeriodNum}" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
					
					<c:choose >
							<c:when test="${empty webProdOpenDurationRule.openPeriod}">
								<dic:select dicType="Open_Cycle_New" dicNo="${webProdOpenDurationRule.openPeriod}"  name="openPeriod"  keepDefaultStyle="true" tgClass="validate[required]" onChange="changeOpenPeriod(this.value);"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select dicType="Open_Cycle_New" dicNo="${webProdOpenDurationRule.openPeriod}"  name="openPeriod"  keepDefaultStyle="true" tgClass="validate[required]" onChange="changeOpenPeriod(this.value);"></dic:select>
							</c:otherwise>
					</c:choose>
					<span class="star">*</span>
					</td>
					<td>开放类型:</td>
					<td>
					<dic:select dicType="OPEN_TYPE" dicNo="${webProdOpenDurationRule.openType}" tgClass="validate[required]" id="openType" name="openType"></dic:select>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>往前推(日):</td>
					<td>
						<input type="text" id="toBeginNum" name="toBeginNum"  class="validate[custom[onlyNumber]]" value="${webProdOpenDurationRule.toBeginNum==0?0:(webProdOpenDurationRule.toBeginNum==null?0:webProdOpenDurationRule.toBeginNum)}"  onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="3"/>
						<span class="star">*</span>
					</td>
					<td>往后推(日):</td>
					<td>
						<input type="text" id="toEndNum" name="toEndNum" class="validate[custom[onlyNumber]]"  value="${webProdOpenDurationRule.toEndNum==0?0:(webProdOpenDurationRule.toEndNum==null?0:webProdOpenDurationRule.toEndNum)}"  onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="3"/>
						<span class="star">*</span>
					</td>
				</tr>
				<!-- <tr>
				<td>日期区间：</td>
				<td colspan="3">
				<span class="star">*</span>
				<div id="div_date">
				</div>
				</td>
				</tr> -->
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
					<dic:select  dicType="HOLIDAY_MAINTENANCE_NEW" dicNo="${webProdOpenDurationRule.holidayMaintenance}" tgClass="validate[required]" id="holidayMaintenance" name="holidayMaintenance"></dic:select>
					<span class="star">*</span>
					</td>
					<td>开放期额度:(元)</td>
					<td>
						<input type="text" id="limit" name="limit" value="${webProdOpenDurationRule.limit}"  onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="14"/>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>开始时间:</td>
					<td>
					<input type="text" name="beginTime"   id="beginTime" class="date" dateFmt="HH:mm" value="${webProdOpenDurationRule.beginTime}"/>
					</td>
					<td>结束时间:</td>
					<td>
					<input type="text" name="endTime"   id="endTime" class="date" dateFmt="HH:mm" value="${webProdOpenDurationRule.endTime}"/>
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
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td>数据生成方式:</td>
					<td>
					<dic:select  dicType="MODIFI_CREATE" id="modifiCreate" name="modifiCreate"></dic:select>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>数据生成方式说明:</td>
					<td>
						<p>生成：存续期规则发生变化，点击生成按钮生成数据</p>
						<p>重新生成：开放周期记录节假日操作改变后，点击重新生成按钮生成周期的新开放期</p>
						<p>原数据修改：额度修改，开始结束日期修改</p>
					</td>
				</tr>
			</table>
			<button type="button" onclick="return sub('form1')" class="saveButton"/>
			<button type="button" onclick="skip()" name="跳过" class="button"><span class='icon_page_next'>跳过</span></button>
		</div>
	   	</form>
	</body>
</html>