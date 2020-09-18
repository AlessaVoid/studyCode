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
	//启用标志下拉框
	var startFlag={"list":[{"value":"1","key":"正常"},{"value":"2","key":"无效"}]};
	//客户类型下拉框
	var custType={"list":[{"value":"1","key":"个人"},{"value":"2","key":"机构"}]};
    //数据表格初始化
    var felPeriodProfitGrid;
	function initComplete(){
	var gridData=${gridData};
	//灵活期限收益组合参数
	felPeriodProfitGrid = $("#felPeriodProfitParam").quiGrid({
           columns: [
           { display: '机构代码', name: 'organCode', align: 'center', width: "12%",
        	   editor:{type:"text",maxlength:8}
           },
           { display: '期限下限(含,天)', name: 'periodLow', align: 'center', width: "12%",
        	   editor:{type:"text",maxlength:10,inputMode:"numberOnly"}
           },
           { display: '期限上限(天)', name: 'periodHigh', align: 'center', width: "12%",
        	   editor:{type:"text",maxlength:10,inputMode:"numberOnly"}
           },
           { display: '灵活期限参数客户收益率(%)', name: 'profitRate', align: 'center', width: "12%",
        	   editor:{type:"text",maxlength:7},
        	   render : function(rowdata) {
     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.profitRate,2) + '</div>';}
           },
           { display: '灵活期限参数销售费率(%)', name: 'sellRate', align: 'center', width: "12%",
        	   editor:{type:"text",maxlength:7},
        	   render : function(rowdata) {
    				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.sellRate,2) + '</div>';}
           },
           { display: '启用日期', name: 'beginDate', align: 'center', width: "12%",
        	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
        	   render : function(rowdata) {
      				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginDate.replace(/-/gi, "") + '</div>';}
           },
           { display: '客户类型', name: 'custType', align: 'center', width: "12%",type: 'CUSTTYPE',
               editor:{type:'select',data:custType,selWidth:100,boxWidth:90},
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
           { display: '启用状态', name: 'status', align: 'center', width: "15%",type: 'STARTFLAG',
        	   editor:{type:'select',data:startFlag,selWidth:100,boxWidth:90},
        	   render : function(rowdata) {
        		   if (rowdata.status == "1") {
        				return "正常";
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
           width:'100%',
           toolbar : {
		   items : [ {text : '增加行',click : appendFelPeriodProfit,iconClass : 'icon_add'}, {line : true},
		   	{text : '删除行',click : deleteFelPeriodProfit,iconClass : 'icon_remove'}, {line : true}]
		   }
   	});
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}
}
	
	//追加尾行
	function appendFelPeriodProfit(){
		var felPeriodProfit = {
			organCode : "${sessionOrgan.thiscode}",
			periodLow: "0",
			periodHigh: "7",
			profitRate: "1.00",
			sellRate : "0.25",
			beginDate: "${systemDate}",
			custType : "1",
            status: "1"
          };
		felPeriodProfitGrid.add(felPeriodProfit);
	}
	//删除选中行
	function deleteFelPeriodProfit(){
	    //选中一行或多行
	    var rows = felPeriodProfitGrid.getSelectedRows();
	    if (rows.length == 0) {
           top.Dialog.alert('请至少选择一行'); 
           return;
        }
	    for(var index in rows){
	    	felPeriodProfitGrid.deleteRow(rows[index]);
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
	    felPeriodProfitGrid.endEdit();
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
			map.value= JSON.stringify(felPeriodProfitGrid.getData());
			param.push(map);
			$.post(url,param,function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg,function(result){
						parent.turnPage("panel9","panel11");
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
			<input type="hidden" id="prodCode" value="${prodCode}" name="prodCode"/>
			<input type="hidden" id="copyProdCode" value="${copyProdCode}" name="copyProdCode"/>
   			<div id="felPeriodProfitParam" class="padding_right5"></div>
		<div align="center">
			<button type="button" onclick="return sub('form1','<%=path%>/webResearchAppInfo/insertFelPeriodSellRateDTO.htm')" class="saveButton"/>
		</div>
	   	</form>
	</body>
</html>