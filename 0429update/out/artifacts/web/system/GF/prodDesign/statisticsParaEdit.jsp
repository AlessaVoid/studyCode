<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
			<title></title> 
			<!--表单验证start-->
			<script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
			<script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
			<!--表单验证end-->
			<!-- 日期选择框start -->
			<script src="<%=path%>/libs/js/form/datePicker/WdatePicker.js" type="text/javascript"></script>
			<!-- 日期选择框end -->
			<!-- 树组件start -->
			<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
			<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
			<!-- 树组件end -->
			<!-- 树形下拉框start -->
			<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
			<!-- 树形下拉框end -->
			<script>
var copyType="${copyType}";
function sub() {
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	if (valid) {
		var b = true; 
		for(var i=0;i<5;i++){
			var a = $("#statisticsDutyCycle"+i).val();
			if(a>100){
				b=false;
			}
			if(a=='' || a==undefined){
			top.Dialog.alert("占比(%)不能为空！");
			}
		}
		if(b){
			top.Dialog.confirm("是否保存该条信息?", function() {
				//去除金额显示的逗号，防止后台数据不匹配出错
				rallmoney();
				buttonluck();
				$.post("<%=path%>/design/saveStatisticsPara.htm",$("#form1").serialize(),
				function(result) {
					buttonUnluck();
					if (result.success == "true"|| result.success == true) {
						top.Dialog.alert(result.msg,function() {
							if(copyType == "copyType"){
								parent.turnPage("panel17","panel18");
							}else{
								if($("#index").val() != $("#oldIndex").val()){
									parent.changeDiv($("#index").val(),$("#oldIndex").val());
								}else{
									parent.changeDiv($("#index").val(),$("#index").val());
								}
							} 
						});
					} else {
						top.Dialog.alert(result.msg);
					}
				}, "json");
			});
		}else{
			top.Dialog.alert("最大占比数为100！");
		}
	}else {
		//如果操作失败重新加上金额显示类
		fallmoney();
		top.Dialog.alert("验证未通过");
	}
}

function cancel() {
	top.Dialog.confirm("数据尚未保存，是否退出?|取消确认",function(){
		top.Dialog.close();
		}
	);
}
function initComplete(){
	var statisticsFlag = "${entity.statisticsFlag}";//统计口径标志
	var statisticsDutyCycle = "${entity.statisticsDutyCycle}";//占比
	var prodTrustBusinessType = "${entity.prodTrustBusinessType}";//按托管业务划分
	$("#prodTrustBusinessType").val(prodTrustBusinessType);
	$("#prodTrustBusinessType").render();
	var val5='';
	$.each(statisticsFlag.split(','),function(i,n){
		id = "statisticsFlag" + i;
		$("#"+id).val(n);
		$("#"+id).render();
		if (i>=4) {
			val5=val5+","+n;
			$("#statisticsFlag4").setValue(val5.substr(1));//树形下拉框赋值
			$("#statisticsFlag4").render();
		}
	});
	$.each(statisticsDutyCycle.split(','),function(i,n){
		id = "statisticsDutyCycle" + i;
			if(n==''||n==undefined){
				n="100.00";
			}
			$("#"+id).val(n);
			$("#"+id).render();
		if (i>=6) {
			return;
		}
	});
	
	if(statisticsDutyCycle.split(',')[4].trim().length==0){
		$("#statisticsDutyCycle4").val("100.00");
		$("#statisticsDutyCycle4").render();
	}
	$(":text").blur(function(){
		if(this.value>100){
			this.value=100;
		}
		//this.value=Math.round(this.value);
	});
}
</script>
	</head>
<body>
<form action="" id="form1">
<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
<input type="hidden" name="buyIndex" id="buyIndex" value="${buyIndex }"/>
<input type="hidden" name="index" id="index" value="${index }"/>
<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
<input type="hidden" id="statisticsType" name="statisticsType" value="5,6"/>
	<div align="left">
	   		<table class="tableStyle" width="80%" mode="list" formMode="line">
					<tr>
						 <td colspan="4"><div align="left">按人行统计划分：</div></td>
					</tr>
					<tr>
						<td>按人行统计划分：</td>
						<td>
								<dic:select selWidth="127" dicType="D_GF_RHTJFLAG" id="investType" name="investType" tgClass="validate[required]" dicNo="${entity.investType }"></dic:select>
								<span id="spaninvestType" class="star">*</span>
						</td>
						<td>按托管业务划分：</td>
						<td>
							<dic:select selWidth="127" dicType="D_GF_XTYEFLAG"  id="prodTrustBusinessType"  name="prodTrustBusinessType" tgClass="validate[required]" dicNo="${entity.prodTrustBusinessType }"></dic:select>
							<span id="spanprodTrustBusinessType" class="star">*</span>
						</td>
					</tr>
					<tr>
						 <td colspan="4"><div align="left">G06理财业务统计：</div></td>
					</tr>
					<tr>
						<td>按销售渠道划分：</td>
						<td>
							<dic:select selWidth="127" id="statisticsFlag0" name="statisticsFlag" tgClass="validate[required] gflag" dicType="D_TJKJFLAG_XSQD" dicNo="${entity.statisticsFlag }"></dic:select>
							<span class="star">*</span>
						</td>
						<td>
							占比(%)：
						</td>
						<td>
							<input type="text" id="statisticsDutyCycle0" name="statisticsDutyCycle" value="100"  maxlength="5" class="money"/>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>按收益特征划分：</td>
						<td>
							<dic:select selWidth="127"  id="statisticsFlag1" name="statisticsFlag" tgClass="validate[required] gflag" dicType="PROFIT_TYPE" dicNo="${entity.statisticsFlag }"></dic:select>
							<span class="star">*</span>
						</td>
						<td>
							占比(%)：
						</td>
						<td>
							<input type="text"  id="statisticsDutyCycle1"  name="statisticsDutyCycle" value="100" maxlength="5" class="money"/>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>按期限划分：</td>
						<td> 
							<dic:select selWidth="127" id="statisticsFlag2" name="statisticsFlag" tgClass="validate[required] gflag" dicType="D_TJKJFLAG_QX" dicNo="${entity.statisticsFlag }"></dic:select><span class="star">*</span>
						</td>
						<td>
							占比(%)：
						</td>
						<td>
							<input type="text" id="statisticsDutyCycle2" name="statisticsDutyCycle" value="100" maxlength="5" class="money"/>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>按产品币种划分：</td>
						<td>
							<dic:select selWidth="127" id="statisticsFlag3" name="statisticsFlag" tgClass="validate[required] gflag" dicType="D_TJKJFLAG_BZ" dicNo="${entity.statisticsFlag }"></dic:select><span class="star">*</span>
						</td>
						<td>
							占比(%)：
						</td>
						<td>
							<input type="text" id="statisticsDutyCycle3" name="statisticsDutyCycle" value="100" maxlength="5" class="money"/>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>期末理财资金投向：</td>
						<td>
						<div id="statisticsFlag4" name="statisticsFlag" class="selectTree gflag_1 validate[required]" selWidth="127"  exceptParent="true" 	allSelectable="true"
						 multiMode="true" data='{"treeNodes":[{ "id":"15", "parentId":"0", "name":"未投资头寸", "open": "true"},
						{ "id":"1", "parentId":"0", "name":"债券及货币市场工具", "open": "false","clickExpand":"true"},
						{ "id":"16", "parentId":"1", "name":"国债、央票、政策性金融债"},
						{ "id":"17", "parentId":"1", "name":"AA+(含)以上信用债券"},
						{ "id":"18", "parentId":"1", "name":"AA+以下信用债券"},
						{ "id":"19", "parentId":"1", "name":"正回购"},{ "id":"20", "parentId":"1", "name":"逆回购"},
						{ "id":"21", "parentId":"1", "name":"拆入"},
						{ "id":"22", "parentId":"1", "name":"拆出"},
						{ "id":"23", "parentId":"1", "name":"其他债券及货币市场工具"},
						{ "id":"2", "parentId":"0", "name":"存款", "open": "false","clickExpand":"true"},
						{ "id":"24", "parentId":"2", "name":"本行存款"},
						{ "id":"25", "parentId":"2", "name":"他行存款"},
						{ "id":"26", "parentId":"2", "name":"其他存款"},
						{ "id":"3", "parentId":"0", "name":"信贷类投资", "open": "false","clickExpand":"true"},
						{ "id":"27", "parentId":"3", "name":"债权类"},
						{ "id":"28", "parentId":"3", "name":"银行承兑汇票"},
						{ "id":"29", "parentId":"3", "name":"商业承兑汇票"},
						{ "id":"30", "parentId":"3", "name":"其他票据"},
						{ "id":"31", "parentId":"3", "name":"其他信贷类投资"},
						{ "id":"4", "parentId":"0", "name":"权益类投资", "open": "false","clickExpand":"true"},
						{ "id":"32", "parentId":"4", "name":"结构股权投资"},
						{ "id":"33", "parentId":"4", "name":"PE投资"},
						{ "id":"34", "parentId":"4", "name":"资本市场"},
						{ "id":"35", "parentId":"4", "name":"其他权益类投资"},
						{ "id":"36", "parentId":"0", "name":"衍生工具"},
						{ "id":"37", "parentId":"0", "name":"QDII"},
						{ "id":"38", "parentId":"0", "name":"另类投资"},
						{ "id":"39", "parentId":"0", "name":"其他"}
						]}'></div>
						<span class="star">*</span>
						</td>
						<td>
							占比(%)：
						</td>
						<td>
							<input type="text" id="statisticsDutyCycle4" name="statisticsDutyCycle" maxlength="5" value="100" class="money gflag_1"/>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<div align="center">
								<button type="button" onclick="return sub()" class="saveButton" />
							</div>
						</td>
					</tr>
				</table>
	</div>
  </form>
</body>
</html>