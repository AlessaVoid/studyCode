<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>基本参数</title> 
		<script type="text/javascript" src="<%=path%>/system/layout/js/prodDesign/baseInfoEdit.js"></script>
<script type="text/javascript">
function initComplete(){
	//外币规则
	currency();
	//保本产品成本户类型是否可选
	changeDisabled();
	//根据产品代码和产品品牌控制产品品牌是否可以选择
	brandIsDisabled();
	//是否有存续周期
	checkIsDuration();
	//初始化产品运作模式
	var prodOperModel = $("#prodOperModel").val();
	operModelChange(prodOperModel);	
	//控制个人/机构的最低投资额、最高投资额..
	var custType = $("#custType").val();
	custTypeChange(custType);
	//改变每月收益结算日期
	changeMouthProfitDate();
	//是否质押
	changeIsPledge();
	//定投开办:只有开放式产品才可以
	changeIsDateInvest();
};
function sub(formId,url){
	$(".money").each(function(){rmoney(this);});
	var valid = $("#"+formId).validationEngine({
		returnIsValid : true
	});
	var valid_bak=true;
	var prodBeginDate = $("#prodBeginDate").val();
	var prodEndDate = $("#prodEndDate").val();
	var prodOperModel=$("#prodOperModel").val();
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
			top.Dialog.alert("非净值型产品预计客户最高年化收益率(%)'"+predictHighestProfit+"'必须大于预计客户最低年化收益率(%)'"+predictLowestProfit+"'");	
		}else if(compareDate(prodBeginDate, prodEndDate)){
			valid_bak=false;
			valid=false;
			top.Dialog.alert("产品成立日期["+prodBeginDate+"]不能晚于</br>产品到期日期["+prodEndDate+"]!");
		}
	}
	var currency=$("#currency").val();
	if(currency!="156" && currency!=null){
		var accountingFlag = $("#accountingFlag").val();
		var custType = $("#custType").val();
		var profitHandleFlag=$("#profitHandleFlag").val();
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
	if(!checkPoint()){
		valid_bak=false;
		valid=false;
		top.Dialog.alert("积分产品必须是VIP客户且产品类型为个人、人民币、封闭式非净值型、固定收益计算型产品！");
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
								}
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
//判断是否是节假日
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
//计算存续周期(天)
function getPeriod(){
	//计算期限  每月按30天算
	var prodBeginDate = $("#prodBeginDate").val();
	var prodEndDate = $("#prodEndDate").val();
	if(prodBeginDate != null && prodBeginDate != "" && prodEndDate != null && prodEndDate != ""){
		var prodBeginDateTemp = prodBeginDate.substring(0,4)+"-"+prodBeginDate.substring(4,6)+"-"+prodBeginDate.substring(6,8);
		var prodEndDateTemp = prodEndDate.substring(0,4)+"-"+prodEndDate.substring(4,6)+"-"+prodEndDate.substring(6,8);
		var begintime_ms = new Date(prodBeginDateTemp); //prodBeginDate 为开始时间
		var endtime_ms = new Date(prodEndDateTemp);   // prodEndDate 为结束时间
		var between_ms = endtime_ms-begintime_ms; //时间差的毫秒数
		//计算出相差天数
		var days=Math.floor(between_ms/(24*3600*1000));
		days = parseInt(days);
		$("#prodDurationDays_s").html(days);
		$("#prodDurationDays_s").render();
		$("#prodDurationDays").val(days);
		$("#prodDurationDays").render();
	}else{
		$("#prodDurationDays_s").html("0");
		$("#prodDurationDays_s").render();
	}
}
</script>
</head>
<body>
<form id="form1">
	<input type="hidden" id="prodType" name="prodType" value="${prodType }" title="客户类型"/>
	<input type="hidden" name="oldProfitHandleFlag" id="oldProfitHandleFlag" value="${entity.profitHandleFlag }"/>
	<input type="hidden" id="oldIsPoint" value="${entity.isPoint }"/>
	<input type="hidden" id="oldICustProp" value="${entity.isCustProp }"/>
	<input type="hidden" id="oldProdOperModel" value="${entity.prodOperModel }"/>
	<input type="hidden" name="oldSellFeeType" id="oldSellFeeType" value="${entity.sellFeeType }"/>
	<input type="hidden" name="prodCode" id="prodCode" value="${entity.prodCode }"/>
	<input type="hidden" name="prodName" id="prodName" value="${entity.prodName }"/>
	<input type="hidden" name="index" id="index" value="${index }"/>
	<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
	<input type="hidden" id="brandCode" value="${brandCode }"/>
	<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
		<tr>
			<td align="right" width="10%">产品品牌：</td>
			<td width="10%">
				<c:choose>
					<c:when test="${prodType == '2' }">
						<select prompt="请选择" id="brandCode" name="brandCode" selectedValue="${brandCode }" url="<%=path%>/gfProdBrandInfo/getProdBrandInfo.htm" class="validate[required]"></select>
						<span class="star">*</span>
					</c:when>
					<c:otherwise>
						<select prompt="请选择" disabled="disabled" id="brandCode" name="brandCode" selectedValue="${brandCode }" url="<%=path%>/gfProdBrandInfo/getProdBrandInfo.htm" class="validate[required]"></select>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="right">
				产品名称后缀：
			</td>
			<td width="14%">
				<input type="text" name="prodNameSuffix" value="${entity.prodNameSuffix }" class="validate[length[0,20]]" maxlength="20"/>
			</td>
			<td align="right">
				投资者类型：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.custType}">
						<dic:select onchange="custTypeChange(this.value);changeDisabled();" id="custType" name="custType" dicType="CUST_TYPE" tgClass="validate[required]" dicNo="${entity.custType}"></dic:select>
					</c:when>
					<c:otherwise>
						<dic:out dicType="CUST_TYPE" dicNo="${entity.custType}"></dic:out>
						<input type="hidden" id="custType" name="custType" value="${entity.custType}"/>
					</c:otherwise>
				</c:choose>
				<span class="star">*</span>
			</td>
			<td align="right" width="9%">
				产品运作模式：
			</td>
			<td width="16%">
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
			<td align="right">
				产品收益类型：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.profitType}">
						<dic:select name="profitType" dicType="PROFIT_TYPE" dicNo="03" tgClass="validate[required]"></dic:select>
					</c:when>
					<c:otherwise>
						<dic:select name="profitType" dicNo="${entity.profitType}" dicType="PROFIT_TYPE" tgClass="validate[required]"></dic:select>
					</c:otherwise>
				</c:choose>
				<span class="star">*</span>
			</td>
			<td align="right">
				理财产品成立日期：
			</td>
			<td> 
				<input type="text" onfocus="WdatePicker({onpicked:function(dp){checkIsHoliday(dp.cal.getDateStr());}})" name="prodBeginDate"  id="prodBeginDate" class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd" maxlength="8" value="${entity.prodBeginDate}"/>
				<span class="star">*</span>
			</td>
			<td align="right">
				理财产品到期日期：
			</td>
			<td>
				<input type="text" onfocus="WdatePicker({onpicked:function(dp){checkIsHoliday(dp.cal.getDateStr());}})" name="prodEndDate" id="prodEndDate" class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd" maxlength="8" value="${entity.prodEndDate}"/>
				<span class="star">*</span>
			</td>
			<td align="right">
				产品存续期限(天)：
			</td>
			<td>
			<span id="prodDurationDays_s">${entity.prodDurationDays}</span>
				<input type="hidden" name="prodDurationDays" id="prodDurationDays" class="validate[required,length[0,8]]" maxlength="8" value="${entity.prodDurationDays}"/>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				产品名称规则：
			</td>
			<td>
				<dic:select id="prodNameRule"  name="prodNameRule" dicType="PROD_NAME_RULE" tgClass="validate[required]" dicNo="${entity.prodNameRule}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				
			</td>
			<td>
				
			</td>
			<td align="right" width="9%">
				
			</td>
			<td width="16%">
				
			</td>
			<td align="right" width="9%">
				
			</td>
			<td width="16%">
				
			</td>
		</tr>
		<tr>
			<td align="right">
				收益计算标识：
			</td>
			<td>
				<dic:select fn="onChange='deleteProfit(this.value);'" id="profitHandleFlag"  name="profitHandleFlag" dicType="PROFIT_FLAG" tgClass="validate[required]" dicNo="${entity.profitHandleFlag}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				销售手续费类型：
			</td>
			<td>
				<dic:select fn="onChange='deleteSellFee();'" id="sellFeeType" name="sellFeeType" dicType="SELLFEE_TYPE" tgClass="validate[required]" dicNo="${entity.sellFeeType}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right" width="9%">
				产品风险等级：
			</td>
			<td width="16%">
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
			<td align="right" width="9%">
				拟续接：
			</td>
			<td width="16%">
				<dic:out dicType="IS_NEED_CONT_PROD" dicNo="1"></dic:out>
				<input  type="hidden" name="isContProd" value="1"/>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right" width="9%">
				待续接：
			</td>
			<td width="16%">
			<c:choose>
					<c:when test="${empty entity.isNeedContProd}">
					<dic:select name="isNeedContProd" dicType="IS_NEED_CONT_PROD" tgClass="validate[required]" dicNo="0"></dic:select>
					</c:when>
					<c:otherwise>
					<dic:select name="isNeedContProd" dicType="IS_NEED_CONT_PROD" tgClass="validate[required]" dicNo="${entity.isNeedContProd}"></dic:select>
					</c:otherwise>
				</c:choose>
			
				<span class="star">*</span>
			</td>
			<td align="right">
				是否为POS理财产品：
			</td>
			<td>
			<c:choose>
					<c:when test="${empty entity.isPos}">
						<dic:select dicType="IS_YES" name="isPos" tgClass="validate[required]" dicNo="0"></dic:select>
					</c:when>
					<c:otherwise>
						<dic:select dicType="IS_YES" name="isPos" tgClass="validate[required]" dicNo="${entity.isPos }"></dic:select>
					</c:otherwise>
				</c:choose>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否自主平衡：
			</td>
			<td>
			<c:choose>
					<c:when test="${empty entity.isAutoBalance}">
					<dic:select name="isAutoBalance" dicType="IS_YES" tgClass="validate[required]" dicNo="0"></dic:select>
					</c:when>
					<c:otherwise>
						<dic:select name="isAutoBalance" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isAutoBalance}"></dic:select>
					</c:otherwise>
				</c:choose>
				<span class="star">*</span>
			</td>
			<td align="right">
				会计核算方式：
			</td>
			<td>
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
		</tr>
		<tr>
			<td align="right">
				夜市标志：
			</td>
			<td>
				<dic:select name="isNightMarket" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isNightMarket}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				计提方式：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.feeProvFlag}">
						<dic:select name="feeProvFlag" dicType="FEE_PROV_FLAG" dicNo="1" tgClass="validate[required]"></dic:select>
					</c:when>
					<c:otherwise>
						<dic:select name="feeProvFlag" dicType="FEE_PROV_FLAG" dicNo="${entity.feeProvFlag}" tgClass="validate[required]"></dic:select>
					</c:otherwise>
				</c:choose>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否结构性理财：
			</td>
			<td>
				<dic:select name="isQuanto" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isQuanto}"></dic:select>
				<span class="star">*</span>
			</td>
			
			<td align="right">
				每月收益结转日：
			</td>
			<td>
				<input id="mouthProfitDate" type="text" name="mouthProfitDate" maxlength="2" value="${entity.mouthProfitDate}" />
			</td>
		</tr>
		<tr>
		<td align="right">
				产品投资收益率(%)：
			</td>
			<td>
				<input type="text" name="prodProfitRate" class="money" maxlength="7" value="${entity.prodProfitRate}"/>
			</td>
		
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
			<td align="right">
				超额留存收益率(%)：
			</td>
			<td>
				<input type="text" name="overfulfilProfit" class="money" maxlength="7" value="${entity.overfulfilProfit}"/>
			</td>
		</tr>
		<tr>
			
			<td align="right">
				产品托管费率(%)：
			</td>
			<td>
			<c:choose>
				<c:when test="${empty entity.prodControlFeeRate }">
						<input type="text" name="prodControlFeeRate" class="money" maxlength="7" value="0.05"/>
				</c:when>
				<c:otherwise>
						<input type="text" name="prodControlFeeRate" class="money" maxlength="7" value="${entity.prodControlFeeRate}"/>
				</c:otherwise>
			</c:choose>
			</td>
			
			<td align="right">
				产品推荐费率(%)：
			</td>
			<td>
				<input type="text" name="prodRecomRate" class="money" maxlength="7" value="${entity.prodRecomRate}"/>
			</td>
			<td align="right">
				其他费率(%)：
			</td>
			<td>
				<input type="text" name="otherRate" class="money" maxlength="7" value="${entity.otherRate}"/>
			</td>
			 <td>总行管理费率(%)：</td>
			 <td>
			 <input type="text" name="headOfficeMagFee" class="money-2" maxlength="7" value="${entity.headOfficeMagFee}"/>
			</td>
		</tr>
		<tr>
			<td>浮动管理费率(%)：</td>
			 <td>
				 <input type="text" name="felMagFee" class="money-2" maxlength="7" value="${entity.felMagFee}"/>
			</td>
			<td>客户收益率(%)：</td>
					<td>
					<input type="text" id="profitRate" name="profitRate" value="${entity.profitRate}" class="money validate[length[0,16]]" maxlength="16"/>
			</td>
			<td>销售费率(%)：</td>
					<td>
						<input type="text" id="sellRate" name="sellRate" value="0.25" class="money validate[length[0,16]]" maxlength="16" />
					</td>
					<td align="right">
				VIP等级：
			</td>
			<td>
			<c:choose>
					<c:when test="${empty entity.vipGrade}">
						<dic:select tgClass="validate[required]" name="vipGrade" id="vipGrade"  dicType="VIP_GRADE" dicNo="0"></dic:select>
					</c:when>
					<c:otherwise>
				        <dic:select tgClass="validate[required]" name="vipGrade" id="vipGrade" dicType="VIP_GRADE" dicNo="${entity.vipGrade}"></dic:select>
					</c:otherwise>
				</c:choose>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				个人客户最高投资额：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.highestInvestment }">
							<input type="text" id="highestInvestment" name="highestInvestment" class="money" maxlength="16" value="9999999999"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="highestInvestment" name="highestInvestment" class="money" maxlength="16" value="${entity.highestInvestment}"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="right">
				个人客户最低投资额：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.lowestInvestment }">
							<input type="text" id="lowestInvestment" name="lowestInvestment" class="money" maxlength="16"  value="50000"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="lowestInvestment" name="lowestInvestment" class="money" maxlength="16"  value="${entity.lowestInvestment}"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="right">
				个人客户投资倍增金额：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.multiMoney }">
							<input type="text" id="multiMoney" name="multiMoney" class="money" maxlength="16"  value="10000"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="multiMoney" name="multiMoney" class="money" maxlength="16"  value="${entity.multiMoney}"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="right">
				个人客户最低持有份额(份)：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.holdMoney }">
							<input type="text" id="holdMoney" name="holdMoney" class="money" maxlength="16"  value="${entity.lowestInvestment}"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="holdMoney" name="holdMoney" class="money" maxlength="16"  value="${entity.holdMoney}"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td align="right">
				机构客户最大投资额：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.corpHighestInvestment }">
							<input type="text" id="corpHighestInvestment" name="corpHighestInvestment" class="money" maxlength="16"  value="9999999999"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="corpHighestInvestment" name="corpHighestInvestment" class="money" maxlength="16"  value="${entity.corpHighestInvestment}"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="right">
				机构客户最低投资额：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.corpLowestInvestment }">
							<input type="text" id="corpLowestInvestment" name="corpLowestInvestment" class="money" maxlength="16"  value="50000"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="corpLowestInvestment" name="corpLowestInvestment" class="money" maxlength="16"  value="${entity.corpLowestInvestment}"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="right">
				机构客户倍增金额：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.corpMultiMoney }">
							<input type="text" id="corpMultiMoney" name="corpMultiMoney" class="money" maxlength="16"   value="10000"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="corpMultiMoney" name="corpMultiMoney" class="money" maxlength="16"  value="${entity.corpMultiMoney}"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="right">
				机构客户最低持有份额(份)：
			</td>
			<td>
				<c:choose>
					<c:when test="${empty entity.corpHoldMoney }">
							<input type="text" id="corpHoldMoney" name="corpHoldMoney" class="money" maxlength="16"   value="50000"/>
					</c:when>
					<c:otherwise>
							<input type="text" id="corpHoldMoney" name="corpHoldMoney" class="money" maxlength="16"   value="${entity.corpHoldMoney}"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td align="right">
				是否设置黑白名单：
			</td>
			<td>
				<dic:select name="isWhiteList" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isWhiteList}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否积分产品：
			</td>
			<td>
				<dic:select name="isPoint" dicType="IS_YES" id="isPoint" tgClass="validate[required]" dicNo="${entity.isPoint}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否控制客户行内属性：
			</td>
			<td>
				<dic:select name="isCustProp" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isCustProp}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否允许质押：
			</td>
			<td>
				<dic:select id="isPledge" name="isPledge" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isPledge}"></dic:select>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				分红方式：
			</td>
			<td>
				<dic:select name="dividendFlag" dicType="DIVIDEND_FLAG" tgClass="validate[required]" dicNo="${entity.dividendFlag}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				默认分红方式：
			</td>
			<td>
			
			<dic:select name="defaultDividendFlag" id="defaultDividendFlag" dicType="DIVIDEND_FLAG" disabled="disabled" tgClass="validate[required]" dicNo="1"></dic:select>
				<span class="star">*</span>
				<input type="hidden" name="defaultDividendFlag" id="defaultDividendFlagHide" value="1"/>
			</td>
			<td align="right">
				是否允许分红方式变更：
			</td>
			<td>
				<dic:select name="isDividendAlter" id="isDividendAlter" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isDividendAlter}"></dic:select>
				<input type="hidden" id="HisDividendAlter" name="isDividendAlter" value="0" disabled="disabled"/>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否允许财产证明：
			</td>
			<td>
				<dic:select name="isAssetProve" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isAssetProve}"></dic:select>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				是否预约自动认购：
			</td>
			<td>
				<dic:select name="isOrderAutoBuy" id="isOrderAutoBuy" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isOrderAutoBuy}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否有存续周期：
			</td>
			<td>
				<dic:select id="isDuration" name="isDuration" dicType="IS_YES" dicNo="${entity.isDuration}"  tgClass="validate[required]"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				定投业务开办标志：
			</td>
			<td>
				<dic:select id="isDateInvest" name="isDateInvest" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.isDateInvest}"></dic:select>
				<span class="star">*</span>
			</td>
			<td align="right">
				转换标志：
			</td>
			<td>
				<c:choose>
				<c:when test="${empty entity.transitionFlag }">
					<dic:select name="transitionFlag" dicType="TRANSITION_FLAG" tgClass="validate[required]" dicNo="0"></dic:select>
				</c:when>
				<c:otherwise>
					<dic:select name="transitionFlag" dicType="TRANSITION_FLAG" tgClass="validate[required]" dicNo="${entity.transitionFlag}"></dic:select>
				</c:otherwise>
			</c:choose>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				币种：
			</td>
			<td>
				<dic:select tgClass="validate[required]" dicType="TR_CURRENCY" name="currency" dicNo="${entity.currency}"></dic:select>
			</td>
			<td>计息基础：</td>
		 	<td>
		 	<c:choose>
		 		<c:when test="${webProdAssetMutualInfo.calIntBmk == '03'}">
		 			<dic:select name="dayCountBasis" dicType="DAYCOUNTBA" tgClass="validate[required]" dicNo="03"></dic:select>
		 		</c:when>
		 		<c:otherwise>
		 			<dic:select name="dayCountBasis" dicType="DAYCOUNTBA" tgClass="validate[required]" dicNo="${entity.dayCountBasis}"></dic:select>
		 		</c:otherwise>
		 	</c:choose>
				<span class="star">*</span>
			</td>
			<td align="right">
				一次性费用：
			</td>
			<td>
				<input type="text" name="nonrecurringCharge" class="money" maxlength="14"  value="${entity.nonrecurringCharge}"/>
			</td>
			<td>总行代为操作分行自平衡产品标识：</td>
			<td><dic:select dicType="IS_YES" name="headAutoBalanceFlag" dicNo="${entity.headAutoBalanceFlag}" id="headAutoBalanceFlag"></dic:select></td>
		</tr>
		<tr>
			<td>保本产品成本户类型：</td>
			<td>
				<div class="selectTree" id="evenCostAssetType" selectedValue="${webProdAssetMutualInfo.evenCostAssetType }" url="<%=path%>/gfDict/getTreeDic.htm?dicType=COST_ASSET_TYPE_O" multiMode="true" allSelectable="true" exceptParent="true"></div>
			</td>
			<td>净值最晚延迟日：</td>
			<td>
				<dic:select name="netvalueLastDay" id="netvalueLastDay" dicType="LAST_DAY" dicNo="${webProdAssetMutualInfo.netvalueLastDay}"></dic:select>
			</td>
			<td>SPPI测试是否通过：</td>
			<td>
				<dic:select name="sppi" id="sppi" dicType="IS_YES" dicNo="${webProdAssetMutualInfo.sppi}"></dic:select>
				<span class="star">*</span>
			</td>
			<td>推荐标志</td>
			<td><dic:select name="isRecommend" id="isRecommend" dicType="IS_YES" dicNo="${webProdAssetMutualInfo.isRecommend}"></dic:select>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td>专属标志：</td>
				<td>
				    <dic:select name="exclusiveFlag" id="exclusiveFlag" dicType="EXCLUSIVE_FLAG" dicNo="${entity.exclusiveFlag}"></dic:select>
				</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="8">
				<div align="center">
					<button type="button" onclick="return sub('form1','<%=path%>/design/saveBaseInfo.htm')" class="saveButton"/>
				</div>
			</td>
		</tr>
	</table>
	</form>
	</body>
</html>