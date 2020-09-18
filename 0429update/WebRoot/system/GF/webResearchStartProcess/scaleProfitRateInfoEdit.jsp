<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>规模期限收益组合参数</title>
		<script type="text/javascript">
	//启用标志下拉框
	var startFlag={"list":[{"value":"1","key":"启用"},{"value":"2","key":"停用"}]};
	//客户类型下拉框
	var custType={"list":[{"value":"1","key":"个人"},{"value":"2","key":"机构"}]};
	
    //数据表格初始化
    var scaleProfitGrid;
	function initComplete(){
	var gridData=${gridData};
	//规模收益类组合参数
	scaleProfitGrid = $("#scaleProfitParam").quiGrid({
           columns: [
           { display: '金额下限(含)', name: 'lowAmt', align: 'center', width: "20%",
        	   editor:{type:"text",maxlength:14},
        	   render : function(rowdata) {
   				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.lowAmt,2)+ '</div>';}
           },
           { display: '金额上限', name: 'highAmt', align: 'center', width: "20%",
        	   editor:{type:"text",maxlength:14},
        	   render : function(rowdata) {
   				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.highAmt,2) + '</div>';}
           },
           { display: '规模期限收益组合参数客户收益率(%)', name: 'profitRate', align: 'center', width: "20%",
        	   editor:{type:"text",maxlength:7},
        	   render : function(rowdata) {
    				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.profitRate,2) + '</div>';}
           },
           { display: '客户类型', name: 'custType', align: 'center', width: "10%",type: 'CUSTTYPE',
               editor:{type:'select',data:custType,selWidth:100,boxWidth:90},
        	   render : function(rowdata) {
        		    if (rowdata.custType == "1") {
        				return "个人";
        			}else if (rowdata.custType == "2"){
        				return "机构";
        			}
      			}
           },
           { display: '启用日期', name: 'beginDate', align: 'center', width: "20%",
        	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
        	   render : function(rowdata) {
   				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginDate.replace(/-/gi, "") + '</div>';}
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
           }
           ], 
           data:gridData, 
           rownumbers : true,
           enabledEdit: true,
           usePager: false, 
           sortName: 'id', 
           checkbox:true,
           percentWidthMode:true,
           width:"100%",
           toolbar : {
		   items : [ {text : '增加行',click : appendScaleProfit,iconClass : 'icon_add'}, {line : true},
		   	{text : '删除行',click : deleteScaleProfit,iconClass : 'icon_remove'}, {line : true}]
		   }
   	});
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}
}
	//追加尾行
	function appendScaleProfit(){
		var scaleProfit = {
			lowAmt: "0.00",
			highAmt: "10000.00",
			profitRate: "1.0000",
			beginDate: "${systemDate}",
			custType:"1",
            status: "1"
          };
		scaleProfitGrid.add(scaleProfit);
	}
	//删除选中行
	function deleteScaleProfit(){
	    //选中一行或多行
	    var rows = scaleProfitGrid.getSelectedRows();
	    if (rows.length == 0) {
           top.Dialog.alert('请至少选择一行'); 
           return;
        }
	    for(var index in rows){
	    	scaleProfitGrid.deleteRow(rows[index]);
	    }
	}
	
//即时编辑输入格式化	
function fmtText(){
	if($(".l-grid-row-cell-inner .textinput_click").size()>0){
		//获取maxlength属性用于确定是否收益率输入框
		var length = $(".l-grid-row-cell-inner .textinput_click").attr("maxlength");
		if(length == 7){
			// 输入文本在右边界对齐
			$(".l-grid-row-cell-inner .textinput_click").css("text-align", "right");
			// 清除文本中的','分隔符
			rmoney1($(".l-grid-row-cell-inner .textinput_click")[0]);
			// 为输入框绑定失焦时间
			$(".l-grid-row-cell-inner .textinput_click").bind("blur", function() {
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
		}else if(length == 14){
			// 输入文本在右边界对齐
			$(".l-grid-row-cell-inner .textinput_click").css("text-align", "right");
			// 清除文本中的','分隔符
			rmoney1($(".l-grid-row-cell-inner .textinput_click")[0]);
			// 为输入框绑定失焦时间
			$(".l-grid-row-cell-inner .textinput_click").bind("blur", function() {
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
	$(".l-grid-row-cell-inner .textinput_click").blur(function(){
	    scaleProfitGrid.endEdit();
	});
}
//提交方法
function sub(formId,url) {
	var valid = $("#"+formId).validationEngine( {
		returnIsValid : true
	});
	if (valid) {
		top.Dialog.confirm("是否保存信息?", function() {
			var param = $("#"+formId).formToArray();
			var map = {};
			map.name = "gridData";
			map.value= JSON.stringify(scaleProfitGrid.getData());
			param.push(map);
			$.post(url,param,function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg, function() {
						parent.setBaseInfo(result.bean);
						if($("#index").val() != $("#oldIndex").val()){
							parent.changeDiv($("#index").val(),$("#oldIndex").val());
						}else{
							parent.changeDiv($("#index").val(),$("#index").val());
						}
					});
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		});
	} else {
		top.Dialog.alert("验证未通过");
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
   			<div id="scaleProfitParam" class="padding_right5"></div>
		<div align="center">
			<button type="button" onclick="return sub('form1','<%=path%>/webResearchStartProcess/updateScaleProfitRate.htm')" class="saveButton"/>
		</div>
	   	</form>
	</body>
</html>