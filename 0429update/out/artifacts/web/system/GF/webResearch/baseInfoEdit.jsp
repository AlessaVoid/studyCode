<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>产品基本信息</title> 
		<!-- 日期选择框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
		<!-- 日期选择框end -->
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
		<script type="text/javascript" src="<%=path%>/system/layout/js/webResearch/baseInfoEdit.js"></script>
		<script type="text/javascript">
$(function(){
	setInvestment();//设置个人最低投资额和机构最低投资额默认值为50000.00
	custTypeChange("0");//根据投资者类型改变起点销售金额控件
	openIsProfitFeeToRmb();//人民币隐藏产品到期收益和手续费是否转人民币参数
	changeDisabled();//保本产品成本户类型  产品类型:个人;会计核算方式:表内;
	isAutoBalanceChange($("#isAutoBalance").val());//是否自主平衡
		
	var prodOperModel = $("#prodOperModel").val();
	if (prodOperModel=='') {
		operModelChange("02");
	}else{
		operModelChange(prodOperModel);	
	}
	//根据产品代码和产品品牌控制产品品牌是否可以选择
	var brandCodeValue = $("#brandCodeValue").val();
	var prodCode = $("#prodCode").val();
	if(prodCode != null && prodCode != "" && brandCodeValue != "" && brandCodeValue != null){
		$("#brandCode").attr("disabled","disabled");
		$("#brandCode").render();
	}
	changeProdtCharactType($("#prodtCharactType").val());//产品类型为投资周期，投资周期天数才显示
});
//提交保存方法
function sub(formId,url){
	$(".money").each(function(){rmoney(this);});
	var valid = $("#"+formId).validationEngine({
		returnIsValid : true
	});
	var valid_bak=true;
	var prodBeginDate = $("#prodBeginDate").val();
	var prodEndDate = $("#prodEndDate").val();
	var prodOperModel=$("#prodOperModel").val();
	var profitHandleFlag=$("#profitHandleFlag").val();
	var profitRate=$("#profitRate").val();
	if (prodOperModel=='02'||prodOperModel=='04') {
		var predictHighestProfit=$("#predictHighestProfit").val();
		var predictLowestProfit=$("#predictLowestProfit").val();
		if(predictHighestProfit=='0.00'||predictHighestProfit=='0'){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("非净值型产品预计客户最高年化收益率(%)'"+predictHighestProfit+"'必须大于0");
		}else if(predictLowestProfit=='0.00'||predictLowestProfit=='0'){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("非净值型产品预计客户最低年化收益率(%)'"+predictLowestProfit+"'必须大于0");
		}else if(predictHighestProfit-predictLowestProfit<0){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("非净值型产品预计客户最高年化收益率(%)'"+predictHighestProfit+"'不能小于预计客户最低年化收益率(%)'"+predictLowestProfit+"'");	
		}
		if(prodOperModel=='02'&&profitHandleFlag=='1'){
			if(!(profitRate==predictHighestProfit&&predictHighestProfit==predictLowestProfit)){
				valid_bak=false;
				valid=false;
				top.Dialog.alert("封闭式非净值型产品固定收益计算方式时，'预计客户最高年化收益率(%)'必须等于'预计客户最低年化收益率(%)'且与'客户收益率'相等!");
			}
		}
	}
	var currency=$("#currency").val();
	if(currency!="156" && currency!=null){
		var accountingFlag = $("#accountingFlag").val();
		var custType = $("#custType").val();
		if(custType!="1"){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("外币产品，投资者类型必须为【个人】");
		}else if(accountingFlag!="02"){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("会计核算方式必须为【表外】");
		}else if(prodOperModel!="02"){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("产品运作模式必须为【封闭式非净值型】");
		}else if(profitHandleFlag!="1"){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("收益计算标识必须为【固定收益计算】");
		}
	}
	if(compareDate(prodBeginDate, prodEndDate)){
		valid_bak=false;
		valid=false;
		top.Dialog.alert("产品成立日期["+prodBeginDate+"]不能晚于</br>产品到期日期["+prodEndDate+"]!");
	}
	var isAutoBalance=$("#isAutoBalance").val();
	var headAutoBalanceFlag=$("#headAutoBalanceFlag").val();
	if(isAutoBalance=='1'){
		if(headAutoBalanceFlag=='--请选择--'||headAutoBalanceFlag==''||headAutoBalanceFlag==undefined){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("自主平衡产品必须选择总行代为操作标识！");
		}
	}
			if (valid){
				$(".money").each(function(){rmoney(this);});
				$.post("<%=path%>/webResearchAppInfo/validateRate.htm",$("#"+formId).serialize(),function(result){
						if(result.success=='true'||result.success==true){
							top.Dialog.confirm(result.msg, function() {
								$.post(url, $("#"+formId).serialize(), function(result) {
									if (result.success == "true" || result.success == true) {
										top.Dialog.alert(result.msg, function() {
											parent.setBaseInfo(result.bean);
											if($("#index").val() != $("#oldIndex").val()){
												parent.changeDiv($("#index").val(),$("#oldIndex").val());
											}else{
												parent.changeDiv($("#index").val(),$("#index").val());
											}
											parent.changeRate();
										});
									} else {
										$(".money").each(function(){
											fmoney(this);
										});
										top.Dialog.alert(result.msg);
									};
								}, "json");
					});
				}else{
					$(".money").each(function(){
						fmoney(this);
					});
					top.Dialog.alert(result.msg);
					}
				}, "json");
			}else if(valid_bak) {
			top.Dialog.alert("验证未通过！");
		}
}
function checkIsHoliday(date){
	$.post("<%=path%>/design/checkIsHoliday.htm", 
		{"date":date}, function(result) {
		$(this).blur();
	if (result.success == "true" || result.success == true) {
		getPeriod();
	}else{
		top.Dialog.alert(result.msg,function(){
			getPeriod();
			});
		} 
}, "json");
}

</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="oldProfitHandleFlag" id="oldProfitHandleFlag" value="${entity.profitHandleFlag }"/>
			<input type="hidden" name="oldSellFeeType" id="oldSellFeeType" value="${entity.sellFeeType }"/>
			<input type="hidden" name="prodCode" id="prodCode" value="${entity.prodCode }"/>
			<input type="hidden" name="prodName" id="prodName" value="${entity.prodName }"/>
			<input type="hidden" name="index" id="index" value="${index }"/>
			<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
			<input type="hidden" id="brandCodeValue" value="${brandCode }"/>
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td align="right" width="25%" colspan="2">产品发行机构：</td>
					<td width="25%" colspan="2">
						<dic:select id="productDistriButionAgency" name="productDistriButionAgency" dicNo="${entity.productDistriButionAgency}" dicType="PRODUCT_DISTRIBUTION_AGENCY" tgClass="validate[required]"></dic:select>
						<span class="star">*</span>
					</td>
					<!-- <td align="right" width="25%" colspan="2"></td> -->
					<!-- <td width="25%">
					</td> -->
				</tr>
				<tr>
					<td align="right" width="25%">产品品牌：</td>
					<td width="25%">
						<select prompt="请选择" id="brandCode" name="brandCode" selectedValue="${brandCode }" url="<%=path%>/gfProdBrandInfo/getProdBrandInfo.htm" class="validate[required]"></select>
						<span class="star">*</span>
					</td>
					<td align="right" width="25%">投资者类型：</td>
					<td width="25%">
						<dic:select id="custType" dicNo="${entity.custType}" onchange="custTypeChange(this.value);changeDisabled();" name="custType" dicType="CUST_TYPE" tgClass="validate[required]"></dic:select>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">产品名称生成规则：</td>
					<td width="25%">
						<dic:select id="prodNameRule" name="prodNameRule" dicType="PROD_NAME_RULE" dicNo="${entity.prodNameRule}"  tgClass="validate[required]"></dic:select>
						<span class="star">*</span>
						<%-- <c:choose>
							<c:when test="${empty entity.currency}">
								<dic:select name="currency" id="currency" onchange="openIsProfitFeeToRmb();" dicType="TR_CURRENCY" dicNo="156" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="currency" id="currency" onchange="openIsProfitFeeToRmb();" dicType="TR_CURRENCY" dicNo="${entity.currency}" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose> --%>
					</td>
					<td align="right" width="25%">产品类型</td>
					<td width="25%">
					    <dic:select onchange="changeProdtCharactType(this.value);" name="prodtCharactType" id="prodtCharactType" dicType="PRODT_CHARACT_TYPE" dicNo="${entity.prodtCharactType}"></dic:select>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">产品成立日期：</td>
					<td width="15%">
						<input type="text" onfocus="WdatePicker({onpicked:function(dp){checkIsHoliday(dp.cal.getDateStr());}})" id="prodBeginDate" name="prodBeginDate"  class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd" maxlength="8" value="${entity.prodBeginDate}"/>
						<span class="star">*</span>
					</td>
					<td align="right" width="10%">产品到期日期：</td>
					<td width="15%">
						<input type="text" onfocus="WdatePicker({onpicked:function(dp){checkIsHoliday(dp.cal.getDateStr());}})" name="prodEndDate" id="prodEndDate" class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd" maxlength="8" value="${entity.prodEndDate}"/>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						产品存续期限(天)：
					</td>
					<td>
					<span id="prodDurationDays_s">${entity.prodDurationDays}</span>
						<input type="hidden" name="prodDurationDays" id="prodDurationDays" class="validate[required,length[0,8]]" maxlength="8" value="${entity.prodDurationDays}"/>
						<span class="star">*</span>
					</td>
					<td align="right" width="10%">币种：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.currency}">
								<dic:select name="currency" id="currency" onchange="openIsProfitFeeToRmb();" dicType="TR_CURRENCY" dicNo="156" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="currency" id="currency" onchange="openIsProfitFeeToRmb();" dicType="TR_CURRENCY" dicNo="${entity.currency}" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">产品收益类型：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.profitType}">
								<dic:select name="profitType" dicType="PROFIT_TYPE" dicNo="03" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="profitType" dicNo="${entity.profitType}" dicType="PROFIT_TYPE" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose>
						<input type="hidden" value="${entity.profitType}"/>
						<span class="star">*</span>
					</td>
					<td align="right" width="10%">产品运作模式：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.prodOperModel}">
								<dic:select onChange="operModelChange(this.value);" name="prodOperModel" dicType="PROD_OPER_MODEL" dicNo="02" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select onChange="operModelChange(this.value);" name="prodOperModel" dicType="PROD_OPER_MODEL" dicNo="${entity.prodOperModel}" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose>
							<input type="hidden" id="prodOperModel" value="${entity.prodOperModel}"/>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">会计核算方式：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.accountingFlag}">
								<dic:select name="accountingFlag" id="accountingFlag" dicType="ACCOUNTING_FLAG" dicNo="02" onChange="changeDisabled();" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="accountingFlag" id="accountingFlag" dicType="ACCOUNTING_FLAG" dicNo="${entity.accountingFlag}" onChange="changeDisabled();" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose>
						<span class="star">*</span>
					</td>
					<td align="right" width="10%">产品风险等级：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.prodRiskLevel}">
								<dic:select name="prodRiskLevel" dicType="PROD_RISK_LEVEL" dicNo="2" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="prodRiskLevel" dicType="PROD_RISK_LEVEL" dicNo="${entity.prodRiskLevel}" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" id="lowest" width="10%">起点销售金额：</td>
					<td width="15%">
						<input type="text" id="lowestInvestment" name="lowestInvestment" value="${entity.lowestInvestment}" class="money validate[required,length[0,16]]" maxlength="16"/>
						<input type="text" id="corpLowestInvestment" name="corpLowestInvestment" value="${entity.corpLowestInvestment}" class="money validate[required,length[0,16]]" maxlength="16"/>
						<span class="star">*</span>
					</td>
					<td align="right" width="10%">投资收益率(%)：</td>
					<td width="15%">
						<input type="text" id="prodProfitRate" name="prodProfitRate" value="${entity.prodProfitRate}" class="money validate[length[0,16]]" maxlength="16"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">收益计算标识：</td>
					<td width="15%">
						<dic:select tgClass="validate[required]" fn="onChange='deleteProfit(this.value);'" id="profitHandleFlag" name="profitHandleFlag" dicNo="${entity.profitHandleFlag}" dicType="PROFIT_FLAG"></dic:select>
						<span class="star">*</span>
					</td>
					<td align="right" width="10%">销售手续费类型：</td>
					<td width="15%">
						<dic:select fn="onChange='deleteSellFee();'" name="sellFeeType" id="sellFeeType" dicNo="${entity.sellFeeType}" dicType="SELLFEE_TYPE" tgClass="validate[required]"></dic:select>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">托管费率(%)：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.prodControlFeeRate}">
								<input type="text" id="prodControlFeeRate" name="prodControlFeeRate" value="0.05" class="money validate[length[0,16]]" maxlength="16"/>
							</c:when>
							<c:otherwise>
								<input type="text" id="prodControlFeeRate" name="prodControlFeeRate" value="${entity.prodControlFeeRate}" class="money validate[length[0,16]]" maxlength="16"/>
							</c:otherwise>
						</c:choose>
						</td>
					<td align="right" width="10%">推荐费率(%)：</td>
					<td width="15%">
						<input type="text" id="prodRecomRate" name="prodRecomRate" value="${entity.prodRecomRate}" class="money validate[length[0,16]]" maxlength="16"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">其他费率(%)：</td>
					<td width="15%">
						<input type="text" id="otherRate" name="otherRate" value="${entity.otherRate}" class="money validate[length[0,16]]" maxlength="16"/>
					</td>
					<td align="right" width="10%">超额留存收益率(%)：</td>
					<td width="15%">
						<input type="text" id="overfulfilProfit" value="${entity.overfulfilProfit}" name="overfulfilProfit" class="money validate[length[0,16]]" maxlength="16"/>
					</td>
				</tr>
				<tr>
					<td align="right">
					预计客户最高年化收益率(%)：
					</td>
					<td>
						<input type="text" name="predictHighestProfit" class="money" maxlength="7" value="${entity.predictHighestProfit}" id="predictHighestProfit"/>
					</td>
					<td align="right">
						预计客户最低年化收益率(%)：
					</td>
					<td>
						<input type="text" name="predictLowestProfit" class="money" maxlength="7"  value="${entity.predictLowestProfit}" id="predictLowestProfit"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">拟续接：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.isContProd}">
								<dic:select name="isContProd" dicType="IS_NEED_CONT_PROD" dicNo="2" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="isContProd" dicNo="${entity.isContProd}" dicType="IS_NEED_CONT_PROD" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose>
						<span class="star">*</span>
					</td>
					<td align="right" width="10%">待续接：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.isNeedContProd}">
								<dic:select name="isNeedContProd" dicType="IS_NEED_CONT_PROD" dicNo="2" tgClass="validate[required]"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="isNeedContProd" dicNo="${entity.isNeedContProd}" dicType="IS_NEED_CONT_PROD" tgClass="validate[required]"></dic:select>
							</c:otherwise>
						</c:choose>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">是否自主平衡：</td>
					<td width="15%">
						<c:choose>
							<c:when test="${empty entity.isAutoBalance}">
								<dic:select name="isAutoBalance" dicType="IS_YES" dicNo="0" tgClass="validate[required]" id="isAutoBalance" fn="onChange='isAutoBalanceChange(this.value);'"></dic:select>
							</c:when>
							<c:otherwise>
								<dic:select name="isAutoBalance" dicNo="${entity.isAutoBalance}" dicType="IS_YES" tgClass="validate[required]" id="isAutoBalance" fn="onChange='isAutoBalanceChange(this.value);'"></dic:select>
							</c:otherwise>
						</c:choose>
						<span class="star">*</span>
					</td>
					<td>客户收益率(%)：</td>
					<td>
					<input type="text" id="profitRate" name="profitRate" value="${entity.profitRate}" class="money validate[length[0,16]]" maxlength="16"/>
					</td>
				</tr>
				<tr>
				<td>销售费率(%)：</td>
					<td>
					<c:choose>
							<c:when test="${empty entity.sellRate}">
								<input type="text" id="sellRate" name="sellRate" value="0.25" class="money validate[length[0,16]]" maxlength="16"/>
							</c:when>
							<c:otherwise>
								<input type="text" id="sellRate" name="sellRate" value="${entity.sellRate}" class="money validate[length[0,16]]" maxlength="16"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>总行代为操作分行自平衡产品标识：</td>
					<td><dic:select dicType="IS_YES" name="headAutoBalanceFlag" dicNo="${entity.headAutoBalanceFlag}" id="headAutoBalanceFlag"></dic:select></td>
				</tr>
				<tr>
					<td>保本产品成本户类型：</td>
					<td>
						<div class="selectTree" id="evenCostAssetType" selectedValue="${webProdAssetMutualInfo.evenCostAssetType }" url="<%=path%>/gfDict/getTreeDic.htm?dicType=COST_ASSET_TYPE_P" multiMode="true" allSelectable="true" exceptParent="true"></div>
					</td>
					<td>净值最晚延迟日：</td>
					<td>
						<dic:select name="netvalueLastDay" id="netvalueLastDay" dicType="LAST_DAY" dicNo="${webProdAssetMutualInfo.netvalueLastDay}"></dic:select>
					</td>
				</tr>
				<tr>
					<td>产品到期收益和手续费是否转人民币：</td>
					<td>
						<dic:select name="isProfitFeeToRmb" id="isProfitFeeToRmb" dicType="IS_YES" dicNo="${entity.isProfitFeeToRmb}"></dic:select>
					</td>
					<td>专属标志</td>
					<td>
					    <dic:select name="exclusiveFlag" id="exclusiveFlag" dicType="EXCLUSIVE_FLAG" dicNo="${entity.exclusiveFlag}"></dic:select>
					</td>
				</tr>
				<tr>
					<td>投资性质：</td>
					<td>
						<dic:select name="investmentNature" id="investmentNature" dicType="INVESTMENT_NATURE" dicNo="${entity.investmentNature}"></dic:select>
					</td>
					<td>目标客户类型：</td>
					<td>
						<dic:select name="targetCustomer" id="targetCustomer" dicType="TARGET_CUSTOMER" dicNo="${entity.targetCustomer}"></dic:select>
					</td>
				</tr>
				<tr>
				   <td>投资封闭期(天)：</td>
				   <td>
				      <input type="text" name="closeInvCycle" id="closeInvCycle" maxlength="17" value="${entity.closeInvCycle}"/>
				   </td>
				   <td align="right" width="9%">
						是否是货币型产品：
				   </td>
				   <td width="16%">
			  			<dic:select name="isCurrProdt" dicType="IS_YES" tgClass="" dicNo="${entity.isCurrProdt}"></dic:select>
				   </td>
				</tr>
				<tr>
				<td colspan="4">
					<div align="center" >
						<button type="button" onclick="return sub('form1','<%=path%>/webResearchAppInfo/saveBaseInfo.htm')" class="saveButton"/>
					</div>
				</td>
				</tr>	
			</table>
		</form>
	</body>
</html>