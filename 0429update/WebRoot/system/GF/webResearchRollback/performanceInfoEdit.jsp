<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>业绩比较基准参数</title> 
		<script type="text/javascript">
		$(function(){
			changeIsPeg();
		});
		//判断是否有挂钩描述
		function changeIsPeg(){
			var isPeg = $("#isPeg").val();
			if(isPeg==0){
				$("#pegDescription").attr("disabled","disabled");
				$("#pegDescription").render();
			}else{
				$("#pegDescription").removeAttr("disabled");
			}
		}
	//启用标志下拉框
	var startFlag={"list":[{"value":"1","key":"启用"},{"value":"2","key":"停用"}]};
    //数据表格初始化
    var netvalueGrid;
	function initComplete(){
		var gridData=${gridData};
		//净值参数
		netvalueGrid = $("#performanceParam").quiGrid({
	           columns: [
	           { display: '启用日期', name: 'performanceDate', align: 'center', width: "50%",
	        	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"}
	           },
	           { display: '业绩比较基准（%）', name: 'performanceValue', align: 'center', width: "50%",
	               editor:{type:"text",maxlength:7},
	         	   render : function(rowdata) {
	     				return '<div style="margin-right: 45%;" align="right">' + formatRound(rowdata.performanceValue,2)+ '</div>';
	     			}
	           },
	           { display: '启用日期', name: 'performanceUseDate', align: 'center', width: "33%",
	        	   editor:{type:"date",dateFmt: "yyyyMMdd",detailEdit:"true"}
	           },
	           ], 
	           data:gridData, 
	           rownumbers : true,
	           enabledEdit: true,
	           usePager: false, 
	           sortName: 'id', 
	           checkbox:true,
	           percentWidthMode:true,
	           height: '100%', 
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
		var netValue = {
			performanceDate: "${systemDate}",
			performanceValue: "1.00",
         };
		netvalueGrid.add(netValue);
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
				$(".l-grid-editor .textinput_click").css("text-align", "right");
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
					if (result.success == "true" || result.success == true) {
						top.Dialog.alert(result.msg,function(result){
							parent.turnPage("panel12","panel13");
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
		<input  type="hidden" name="prodCode" value="${prodCode}"/>
			<input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
			<input type="hidden" id="order" name="order" value="${order}"/>
			<input type="hidden" name="index" id="index" value="${index }"/>
		    <input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
		<div align="center">
		   <table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
		      <tr>
		       <td>是否挂钩标的：</td>
		       <td>
				 <dic:select onChange="changeIsPeg(this.value);" name="isPeg" id="isPeg" dicType="IS_YES" dicNo="${entity.isPeg}"></dic:select>
				</td>
				<td>挂钩描述：</td>
				<td>
				  <input type="text" name="pegDescription" id="pegDescription" value="${entity.pegDescription}" maxlength="100" />
				</td>
		      </tr>
		   </table>
		</div>
		</div>
		<div id="performanceParam" class="padding_right5"></div>
		<div align="center">
			<button type="button" onclick="return sub('form1','<%=path%>/webResearchRollback/updatePerformanceBenchmark.htm')" class="saveButton"/>
		</div>
   	</form>
	</body>
</html>