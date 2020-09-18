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
		//changeCustType(0);
		var gridData=${gridData};
		//存续期参数
		durationGrid = $("#durationParam").quiGrid({
               columns: [
               
               { display: '申购日', name: 'purchaseDate', align: 'center', width: "12%",
            	   //editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.purchaseDate.replace(/-/gi, "") + '</div>';}
               },
               { display: '份额确认日', name: 'taCfmDate', align: 'center', width: "12%",
            	   //editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.taCfmDate.replace(/-/gi, "") + '</div>';}
               },
               /* { display: '投资周期结束日', name: 'invCycleEndDate', align: 'center', width: "10%",
            	   //editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.invCycleEndDate.replace(/-/gi, "") + '</div>';}
               }, */
               { display: '首次投资周期结束日', name: 'firstInvCycleEndDate', align: 'center', width: "12%",
            	   //editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
            	   render : function(rowdata) {
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.firstInvCycleEndDate.replace(/-/gi, "") + '</div>';}
               },
               { display: '开始时间（从）', name: 'beginTime', align: 'center', width: "15%",
            	   editor:{type:"text",dateFmt: "HHmmss",detailEdit:"false"},
            	   render : function(rowdata) {
            		   if(rowdata.beginTime==null){
           					return '<div style="margin-right: 45%;" align="right">' + "" + '</div>';
            		   }else{
        				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginTime.replace(/-/gi, "") + '</div>';};
            	   	   }
               },
               { display: '结束时间（到）', name: 'endTime', align: 'center', width: "15%",
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
               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "12%",
            	   //editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
            	   render : function(rowdata) {
	           		    if(rowdata.amtHandleFlag == "1") {
	           			   return "批量";
	           			}else if (rowdata.amtHandleFlag == "2"){
	           			   return "实时";
	           			}
         			}
               },
               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "12%",
            	   //editor:{type:'select',data:aqhandleFlag,selWidth:100,boxWidth:90},
            	   render : function(rowdata) {
	           		    if(rowdata.quotAffirmType == "1") {
	           			   return "批量";
	           			}else if (rowdata.quotAffirmType == "2"){
	           			   return "实时";
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
				items : [ /* {text : '增加行',click : appendDuration,iconClass : 'icon_add'}, {line : true}, */
						  {text : '删除行',click : deleteDuration,iconClass : 'icon_remove'}, {line : true}]
			}
     	});
		

		//四舍五入算法,dp代表保留小数点位数
		function formatRound(num,dp){
			 return (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
		}
	}
	 
	//编辑后事件
	function f_onAfterEdit(e){ 
			 
		return true;
	}
	//追加尾行
	function appendDuration(){
		var duration = {
		   purchaseDate: "${systemDate}",
		   taCfmDate: "${systemDate}",
		   invCycleEndDate: "${systemDate}",
		   firstInvCycleEndDate: "${systemDate}",
		   beginTime: "09:00",
		   endTime: "18:00",
           amtHandleFlag: "1",
           quotAffirmType : "1"
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
	var prodCode=$("#prodCode").val();
	$.post("<%=path%>/design/saveDurationFixedInfo.htm?prodCode="+prodCode,param,function(result) {
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
 


//生成
function createBox(){
	//生成数据之前，删除原有对应数据
	var data = durationGrid.records;
	for (var record in data){
		durationGrid.deleteRow(data[record]);
	}
	
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	
		if (valid) {
			var prodcode = $("#prodCode").val();
			var firstOpenDay = $("#firstOpenDay").val();
			var openType = $("#openType").val();
			var beginTime = $("#beginTime").val();
			var endTime = $("#endTime").val();
			var amtHandleFlag = $("#amtHandleFlag").val();
			var quotAffirmType =  $("#quotAffirmType").val();
			var limit = $("#limit").val();
			var invdays = $("#invdays").val();
			$.post("<%=path%>/design/getDurationFixedDate.htm", {
					prodCode : prodcode,
					firstOpenDay : firstOpenDay,
					openType : openType,
					beginTime : beginTime,
					endTime : endTime,
					amtHandleFlag : amtHandleFlag,
					quotAffirmType : quotAffirmType,
					limit : limit,
					invdays : invdays
				}, function(result) {
					for (var i = 0; i < result.length; i++) {
						var duration = {
							purchaseDate : result[i].purchaseDate,
							taCfmDate : result[i].taCfmDate,
							invCycleEndDate : result[i].invCycleEndDate,
							firstInvCycleEndDate : result[i].firstInvCycleEndDate,
							beginTime : result[i].beginTime,
							endTime : result[i].endTime,
							amtHandleFlag : result[i].amtHandleFlag,
							quotAffirmType : result[i].quotAffirmType,
							limit : result[i].limit
						};
						durationGrid.add(duration);
					}
				});
			} else {
				top.Dialog.alert("验证未通过！");
			}
		
	}
</script>
	</head>
	<body onclick="fmtText()">
		<form id="form1">
 			<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
 			<input type="hidden" id="index" name="index" value="${index }"/>
 			<input type="hidden" id="oldIndex" name="oldIndex" value="${oldIndex }"/>
 			<input type="hidden" id="buyIndex" name="buyIndex" value="${buyIndex }"/>
 			<input type="hidden" id="openType" name="openType" value="1"/>
   			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td>投资周期开始日:</td>
					<td>
					<input type="text" name="firstOpenDay"   id="firstOpenDay" class="date validate[required]" dateFmt="yyyyMMdd"  value="${webProdInvCycleRule.firstOpenDay}"/>
					<span class="star">*</span>
					</td>
					<td>投资封闭期:(天)</td>
					<td>
						<input type="text" id="invdays" name="invdays" value="${webProdInvCycleRule.invdays}" onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="14"/>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>开始时间(从):</td>
					<td>
					<input type="text" name="beginTime"   id="beginTime" class="date" dateFmt="HHmmss"  class=" " value="${webProdInvCycleRule.beginTime}"/>
					<span class="star">*</span>
					</td>
					<td>结束时间(到):</td>
					<td>
					<input type="text" name="endTime"   id="endTime" class="date" dateFmt="HHmmss"  class=" " value="${webProdInvCycleRule.endTime}"/>
					<span class="star">*</span>
					</td> 
				</tr>
				<tr>
				    <td>资金处理方式：</td>
					<td>
					<dic:select dicType="purchase_Amt_Handle_Flag" dicNo="${webProdInvCycleRule.amtHandleFlag}" tgClass="validate[required]" id="amtHandleFlag" name="amtHandleFlag" readOnly="readOnly"></dic:select>
					<span class="star">*</span>
					</td>
					<td>份额确认方式：</td>
					<td>
					<dic:select dicType="purchase_Quot_Affirm_Type" dicNo="${webProdInvCycleRule.quotAffirmType}" tgClass="validate[required]" id="quotAffirmType" name="quotAffirmType"></dic:select>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
				</tr>
				<tr>
					<td>开放期额度:(元)</td>
					<td>
						<input type="text" id="limit" name="limit" value="${webProdInvCycleRule.limit}"  onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="14"/>
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