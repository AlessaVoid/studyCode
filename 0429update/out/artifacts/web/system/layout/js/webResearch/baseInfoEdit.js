//系统路径
var path="/web";
//根据投资者类型改变起点销售金额控件
function custTypeChange(value){
	if(value == '2'){//机构
		$("#custType").val("");
		$("#custType").render();
		top.Dialog.alert("机构产品不需要预研，若要维护产品信息，请到产品设计进行维护!");
		$("#lowestInvestment").hide();
		$("#corpLowestInvestment").show();
	}else{//个人或不区分
		$("#lowestInvestment").show();
		$("#corpLowestInvestment").hide();
	}
	$("#lowestInvestment").render();
	$("#corpLowestInvestment").render();
}

//设置个人最低投资额和机构最低投资额默认值为10000.00
function setInvestment(){
	var lowestInvestment = $("#lowestInvestment").val();
	var corpLowestInvestment = $("#corpLowestInvestment").val();
	if(lowestInvestment == '0.00'){
		$("#lowestInvestment").val("10000.00");
	}
	if(corpLowestInvestment == '0.00'){
		$("#corpLowestInvestment").val("10000.00");
	}
}
//根据销售手续费类型删除指定产品代码的销售费率
function deleteSellFee(){
	var url,sellName;
	var prodCode = $("#prodCode").val();
	if(prodCode != ""){
		if(checkSellType()){
			var sellFeeType = $("#oldSellFeeType").val();
			if(sellFeeType == '1'){
				url = path + "/webProdFixSellFeeRate/deleteProdFixSellFeeRate.htm";
				sellName = "固定销售手续费";
			}else if(sellFeeType == '2'){
				url = path + "/webProdFelSellFeeRate/deleteProdFelSellFeeRate.htm";
				sellName = "期限机构组合销售手续费";
			}
			top.Dialog.confirm("如果改变销售手续费类型，则会删除之前维护的"+sellName+"数据,确定要改变操作吗?|操作提示", function() {
				$.post(url, {"prodCode":$("#prodCode").val()}, function(result) {
					if (result.success == "true" || result.success == true) {
					} else {
					}
				}, "json");
			});
		}
	}else{
		checkSellType();
	}
}
//根据收益计算标识删除指定产品代码的收益率
function deleteProfit(val){
	var prodOperModel=$("#prodOperModel").val();
	if((prodOperModel==''||prodOperModel=='02')&&val=='1'){
		$("#profitRate").removeAttr("disabled");
		$("#profitRate").addClass("validate[required]");
		$("#profitRate_span").remove();
		$("#profitRate").after("<span class=\"star\" id=\"profitRate_span\">*</span>");
		$("#sellRate").removeAttr("disabled");
		$("#sellRate").addClass("validate[required]");
		$("#sellRate_span").remove();
		$("#sellRate").after("<span class=\"star\" id=\"sellRate_span\">*</span>");
		$("#profitRate").render();
		$("#sellRate").render();
	}else {
		$("#sellRate_span").remove();
		$("#profitRate_span").remove();
		$("#profitRate").attr("disabled","disabled");
		$("#sellRate").attr("disabled","disabled");
		$("#profitRate").removeAttr("class");
		$("#sellRate").removeAttr("class");
		$("#profitRate").attr("class","money");
		$("#sellRate").attr("class","money");
		$("#profitRate").render();
		$("#sellRate").render();
	}
	var url,profitName;
	var prodCode = $("#prodCode").val();
	if(prodCode != ""){
		if(checkSellType()){
			var oldProfitHandleFlag = $("#oldProfitHandleFlag").val();
			if(oldProfitHandleFlag != '0'){
				if(oldProfitHandleFlag == '1'){
					url = path + "/webProdFixProfitRate/deleteFixProfitRate.htm";
					profitName = "固定收益计算";
				}else if(oldProfitHandleFlag == '2'){
					url = path + "/webProdFixPeriodProfitRat/deleteFixPeriodProfitRat.htm";
					profitName = "固定期限收益组合";
				}else if(oldProfitHandleFlag == '3'){
					url = path + "/webProdFelPeriodProfit/deleteFelPeriodProfit.htm";
					profitName = "灵活期限收益组合";
				}else if(oldProfitHandleFlag == '4'){
					url = path + "/webProdScaleProfitRate/deleteScaleProfitRate.htm";
					profitName = "规模收益计算";
				}
				top.Dialog.confirm("如果改变收益计算标识，则会删除之前维护的"+profitName+"数据,确定要改变操作吗?|操作提示", function() {
					$.post(url, {"prodCode":$("#prodCode").val()}, function(result) {
						if (result.success == "true" || result.success == true) {
						} else {
						}
					}, "json");
				});
			}
		}
	}else{
		checkSellType();
	}
}
//01 封闭式净值型;02	封闭式非净值型;03 开放式净值型;04	开放式非净值型;
//通过产品运作模式控制收益率的显示
function operModelChange(value){
	var profitHandleFlag=$("#profitHandleFlag").val();
	if(value == '01' || value == '03'){//01、03不可以选择
		$("#profitHandleFlag").attr("disabled","disabled");
		$("#profitHandleFlag").removeAttr("class");
		$("#profitHandleFlag").val("");
		$("#span_predictHighestProfit").remove();
		$("#predictHighestProfit").attr("disabled","disabled");
		$("#predictHighestProfit").removeAttr("class");
		$("#predictHighestProfit").val("");
		$("#span_predictLowestProfit").remove();
		$("#predictLowestProfit").attr("disabled","disabled");
		$("#predictLowestProfit").removeAttr("class");
		$("#predictLowestProfit").val("");
		$("#netvalueLastDay").removeAttr("disabled");
		$("#netvalueLastDay").attr("class","validate[required]");
	}else if(value == '02' || value == '04'){//02、04可以选择
		$("#profitHandleFlag").removeAttr("disabled");
		$("#profitHandleFlag").attr("class","validate[required]");
		$("#predictHighestProfit").removeAttr("disabled");
		$("#predictHighestProfit").attr("class","money validate[required]");
		$("#span_predictHighestProfit").remove();
		$("#predictHighestProfit").after("<span id='span_predictHighestProfit' class='star'>*</span>");
		$("#predictLowestProfit").removeAttr("disabled");
		$("#span_predictLowestProfit").remove();
		$("#predictLowestProfit").attr("class","money validate[required]");
		$("#predictLowestProfit").after("<span id='span_predictLowestProfit' class='star'>*</span>");
		$("#netvalueLastDay").attr("disabled","disabled");
		$("#netvalueLastDay").removeAttr("class");
	}
	if (value == '02' && profitHandleFlag =='1') {//封闭式预期收益率型
		$("#profitRate").removeAttr("disabled");
		$("#sellRate").removeAttr("disabled");
	}else {
		$("#profitRate").val("");
		$("#sellRate").val("");
		$("#profitRate").attr("disabled","disabled");
		$("#sellRate").attr("disabled","disabled");
		$("#profitRate").removeAttr("class");
		$("#sellRate").removeAttr("class");
		$("#profitRate").attr("class","money");
		$("#sellRate").attr("class","money");
	}
	$("#netvalueLastDay").render();
	$("#profitRate").render();
	$("#sellRate").render();
	$("#prodOperModel").val(value);
	$("#profitHandleFlag").render();
	$("#predictHighestProfit").render();
	$("#predictLowestProfit").render();
	checkSellType();
}
//校验销售费率
function checkSellType(){
	//除开放式非净值型的产品，其他三大类产品都是只能选择固定销售费率
	//开放式非净值型产品、收益计算标识为灵活期限的时候才可以选择期限机构组合销售费率
	//01 封闭式净值型;02	封闭式非净值型;03 开放式净值型;04	开放式非净值型;
	var profitHandleFlag = $("#profitHandleFlag").val();
	var prodOperModel = $("#prodOperModel").val();
	var sellFeeType = $("#sellFeeType").val();
	if(sellFeeType != "" && prodOperModel != null){
		if(profitHandleFlag == '3' && prodOperModel == '04'){//收益计算标识为灵活期限-3
			if(sellFeeType != '2'){
				top.Dialog.alert('由于您选择的产品运作模式和收益计算标识是“开放式非净值型”和“灵活期限收益率组合”，因此只能选择期限机构组合销售费率');
				$("#sellFeeType").val("");
				$("#sellFeeType").render();
				return false;
			}
		}else{
			if(sellFeeType != '1'){
				top.Dialog.alert('由于您选择的产品运作模式和收益计算标识不是“开放式非净值型”和“灵活期限收益率组合”，因此只能选择固定销售费率');
				$("#sellFeeType").val("");
				$("#sellFeeType").render();
				return false;
			}
		}
	}
	return true;
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
	}else{
		$("#prodDurationDays_s").html("0");
		$("#prodDurationDays_s").render();
	}
}
//是否自主平衡
function isAutoBalanceChange(val){
	if(val=='1'){//是
		$("#headAutoBalanceFlag").removeAttr("disabled");
		$("#headAutoBalanceFlag").attr("tgClass","validate[required]");
		$("#headAutoBalanceFlag").render();
	}else {
		$("#headAutoBalanceFlag").attr("disabled","disabled");
		$("#headAutoBalanceFlag").removeAttr("tgClass","validate[required]");
		$("#headAutoBalanceFlag").render();
	}
}
//保本产品成本户类型  产品类型:个人;会计核算方式:表内;
function changeDisabled(){
	var custType = $("#custType").val();
	var accountingFlag = $("#accountingFlag").val();
	if(accountingFlag=='01' && custType=='1'){
		$("#evenCostAssetType").attr("name","evenCostAssetType");
		$("#evenCostAssetType").addClass("validate[required]");
		$("#evenCostAssetType").removeAttr("disabled");
		$("#evenCostAssetType_span").remove();
		$("#evenCostAssetType").after("<span class=\"star\" id=\"evenCostAssetType_span\">*</span>");
	}else{
		$("#evenCostAssetType").removeAttr("name");
		$("#evenCostAssetType").removeAttr("class");
		$("#evenCostAssetType").attr("class","selectTree");
		$("#evenCostAssetType").attr("disabled","disabled");
		$("#evenCostAssetType_span").remove();
	}
	$("#evenCostAssetType").render();
}
//本币隐藏产品到期收益和手续费是否转人民币参数
function openIsProfitFeeToRmb(){
	var currency = $("#currency").val();
	if(currency=="156" || currency==""){
		$("#isProfitFeeToRmb").attr("disabled","disabled");
		$("#isProfitFeeToRmb").removeAttr("class");
		$("#isProfitFeeToRmb_span").remove();
	}else{
		$("#isProfitFeeToRmb").removeAttr("disabled");
		$("#isProfitFeeToRmb").addClass("validate[required]");
		$("#isProfitFeeToRmb_span").remove();
		$("#isProfitFeeToRmb").after("<span class=\"star\" id=\"isProfitFeeToRmb_span\">*</span>");
	}
	$("#isProfitFeeToRmb").render();
	
	//动态修改 起点销售金额 后面的币种字样
	var lowestCurrency;
	if(currency=="156"){
		lowestCurrency = "元";
	}else if(currency=="036"){
		lowestCurrency="澳元";
	}else if(currency=="124"){
		lowestCurrency="加元";
	}else if(currency=="344"){
		lowestCurrency="港元";
	}else if(currency=="392"){
		lowestCurrency="日元";
	}else if(currency=="826"){
		lowestCurrency="英镑";
	}else if(currency=="840"){
		lowestCurrency="美元";
	}else if(currency=="978"){
		lowestCurrency="欧元";
	}else{
		lowestCurrency="元";
	}
	$("#lowest").html("起点销售金额（"+lowestCurrency+"）：");
}
//产品为投资周期型，投资封闭期必输
function changeProdtCharactType(value){
	if(value == '03'){//03不可以选择
		$("#closeInvCycle").removeAttr("disabled");
		$("#closeInvCycle").addClass("validate[required,custom[onlyNumber]");
		$("#closeInvCycle").render();
	}else{
		$("#closeInvCycle").attr("disabled","disabled");
		$("#closeInvCycle").val("");
		$("#closeInvCycle").removeAttr("class");
		$("#closeInvCycle").render();
	}
	
}