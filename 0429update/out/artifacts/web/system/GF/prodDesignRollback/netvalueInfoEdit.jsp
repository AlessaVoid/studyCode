<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>机构专户产品设计</title> 
		<script type="text/javascript">
	//启用标志下拉框
	var startFlag={"list":[{"value":"1","key":"有效"},{"value":"2","key":"无效"}]};
    //数据表格初始化
    var netvalueGrid;
	function initComplete(){
		var gridData=${gridData};
		//净值参数
		netvalueGrid = $("#netvalueParam").quiGrid({
	           columns: [
	           { display: '净值日期', name: 'netvalueDate', align: 'center', width: "33%",
	        	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"},
	        	   render : function(rowdata) {
	     				return '<div style="margin-right: 45%;" align="right">' + rowdata.netvalueDate.replace(/-/gi, "") + '</div>';}
	           },
	           { display: '净值', name: 'netvalue', align: 'center', width: "33%",
	               editor:{type:"text",maxlength:12},
	         	   render : function(rowdata) {
	      				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.netvalue,8) + '</div>';}
	           },
	           { display: '状态', name: 'status', align: 'center', width: "33%",type: 'STARTFLAG',
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
			   items : [ {text : '增加行',click : appendNetvalue,iconClass : 'icon_add'}, {line : true},
			   	{text : '删除行',click : deleteNetvalue,iconClass : 'icon_remove'}, {line : true}]
			   }
	   	});
		//四舍五入算法,dp代表保留小数点位数
		function formatRound(num,dp){
		    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
		}
	}
	//追加尾行
	function appendNetvalue(){
		var netvalue = {
			netvalueDate: "${systemDate}",
			netvalue: "1.00000000",
            status: "1"
         };
		netvalueGrid.add(netvalue);
	}
	//删除选中行
	function deleteNetvalue(){
	    //选中一行或多行
	    var rows = netvalueGrid.getSelectedRows();
	    if (rows.length == 0) {
           top.Dialog.alert('请至少选择一行'); 
           return;
        }
	    for(var index in rows){
	    	netvalueGrid.deleteRow(rows[index]);
	    }
	}

	//即时编辑输入格式化	
	function fmtText(){
		if($(".l-grid-row-cell-inner .textinput_click").size()>0){
			//获取maxlength属性用于确定是否收益率输入框
			var length = $(".l-grid-row-cell-inner .textinput_click").attr("maxlength");
			if(length == 16){
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
			}
		}
		$(".l-grid-editor .textinput_click").blur(function(){
		    netvalueGrid.endEdit();
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
				map.value= JSON.stringify(netvalueGrid.getData());
				param.push(map);
				$.post(url,param,function(result) {
					top.Dialog.alert(result.msg);
				}, "json");
			});
		} else {
			top.Dialog.alert("验证未通过");
		}
	}
	function skip(){
		if($("#index").val() != $("#oldIndex").val()){
			parent.changeDiv($("#index").val(),$("#oldIndex").val());
		}else{
			parent.changeDiv($("#index").val(),$("#index").val());
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
		<div id="netvalueParam" class="padding_right5"></div>
	   	<div align="center">
			<button type="button" onclick="return sub('form1','<%=path%>/designRollback/updateNetvalueInfo.htm')" class="saveButton"/>
			<button type="button" onclick="skip()" name="跳过" class="button"><span class='icon_page_next'>跳过</span></button>
		</div>
   	</form>
	</body>
</html>