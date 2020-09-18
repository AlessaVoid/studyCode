//系统路径
var path="/web";
//根据投资者类型改变起点销售金额控件
function custTypeChange(value){
	if(value == '2'){//机构
		$("#highestInvestment").attr("disabled","disabled");
		$("#lowestInvestment").attr("disabled","disabled");
		$("#multiMoney").attr("disabled","disabled");
		$("#holdMoney").attr("disabled","disabled");
		$("#corpHighestInvestment").removeAttr("disabled");
		$("#corpLowestInvestment").removeAttr("disabled");
		$("#corpMultiMoney").removeAttr("disabled");
		$("#corpHoldMoney").removeAttr("disabled");
	}else if(value == '1'){//个人
		$("#corpHighestInvestment").attr("disabled","disabled");
		$("#corpLowestInvestment").attr("disabled","disabled");
		$("#corpMultiMoney").attr("disabled","disabled");
		$("#corpHoldMoney").attr("disabled","disabled");
		$("#highestInvestment").removeAttr("disabled");
		$("#lowestInvestment").removeAttr("disabled");
		$("#multiMoney").removeAttr("disabled");
		$("#holdMoney").removeAttr("disabled");
	}else if(value == '0'){
		$("#corpHighestInvestment").removeAttr("disabled");
		$("#corpLowestInvestment").removeAttr("disabled");
		$("#corpMultiMoney").removeAttr("disabled");
		$("#corpHoldMoney").removeAttr("disabled");
		$("#highestInvestment").removeAttr("disabled");
		$("#lowestInvestment").removeAttr("disabled");
		$("#multiMoney").removeAttr("disabled");
		$("#holdMoney").removeAttr("disabled");
	}
	//个人
	$("#highestInvestment").render();
	$("#lowestInvestment").render();
	$("#multiMoney").render();
	$("#holdMoney").render();
	//机构
	$("#corpHighestInvestment").render();
	$("#corpLowestInvestment").render();
	$("#corpMultiMoney").render();
	$("#corpHoldMoney").render();
}
//根据销售手续费类型删除指定产品代码的销售费率
function deleteSellFee(){
	var url,sellName;
	var prodCode = $("#prodCode").val();
	if(prodCode != ""){
		if(checkSellType()){
			var sellFeeType = $("#sellFeeType_o").val();
			url = path + "/gfProdEleChange/deleteProdSellFeeRate.htm";
			if(sellFeeType == '1'){
				sellName = "固定销售手续费";
			}else if(sellFeeType == '2'){
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
//	$("#isRecommend").attr("disabled","disabled");
//	$("#isRecommend").removeAttr("class","validate[required]");
	//$("#isRecommend").val("0");
	if((prodOperModel==''||prodOperModel=='02')&&val=='1'){
		$("#profitRate").removeAttr("disabled");
		$("#profitRate").addClass("validate[required]");
		$("#sellRate").removeAttr("disabled");
		$("#sellRate").addClass("validate[required]");
		$("#profitRate").render();
		$("#sellRate").render();
//		if ($("#custType").val()=='1') {
//			$("#isRecommend").removeAttr("disabled");
//			$("#isRecommend").attr("class","validate[required]");
//		}	
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
//	if ($("#custType").val()=='1') {
//		$("#isRecommend").removeAttr("disabled");
//		$("#isRecommend").attr("class","validate[required]");
//	}
//	$("#isRecommend").render();
	changeMouthProfitDate();
	checkIsDuration();
	var url,profitName;
	var prodCode = $("#prodCode").val();
	var oldProfitHandleFlag=$("#profitHandleFlag_o").val();
	if(prodCode != ""){
		if(checkSellType()){
			if(oldProfitHandleFlag != '0'){
				url = path + "/gfProdEleChange/deleteProfitRate.htm";
				if(oldProfitHandleFlag == '1'){
					profitName = "固定收益计算";
				}else if(oldProfitHandleFlag == '2'){
					profitName = "固定期限收益组合";
				}else if(oldProfitHandleFlag == '3'){
					profitName = "灵活期限收益组合";
				}else if(oldProfitHandleFlag == '4'){
					profitName = "规模收益计算";
				}else {
					profitName="净值";
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
function validateProfitType(){
	var oldProfitType=$("#profitType_o").val();
	$.post(path + "/gfProdEleChange/validateProfit.htm", {"prodCode":$("#prodCode").val(),"profitType":oldProfitType}, function(result) {
		if (result.success == "true" || result.success == true) {
		} else {
			top.Dialog.alert(result.msg);
		}
	}, "json");
	
}

//01 封闭式净值型;02	封闭式非净值型;03 开放式净值型;04	开放式非净值型;
function operModelChange(value){
	$.post(path + "/gfProdEleChange/validateOperModel.htm", {"prodCode":$("#prodCode").val(),"prodOperModel":value}, function(result) {
		if (result.success == "true" || result.success == true) {
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
			if(value == '01' || value == '02'){
				//$("#ruleFlag").removeAttr("tgClass");
				$("#ruleFlag").attr("disabled","disabled");
				$("#ruleFlag").removeAttr("class","validate[required]");
			}else if(value == '03' || value == '04'){
				$("#ruleFlag").removeAttr("disabled");
				$("#ruleFlag").attr("class","validate[required]");
			}
			$("#isRecommend").attr("disabled","disabled");
			$("#isRecommend").removeAttr("class","validate[required]");
			//打开推荐标志，所有产品都可以进行推荐（机构除外）
			if ($("#custType").val()=='1' || $("#custType").val()=='0') {
				$("#isRecommend").removeAttr("disabled");
				$("#isRecommend").attr("class","validate[required]");
			}
			if (value == '02' && $("#profitHandleFlag").val()=='1') {//封闭式预期收益率型
				$("#profitRate").removeAttr("disabled");
				$("#sellRate").removeAttr("disabled");
//				if ($("#custType").val()=='1') {
//					$("#isRecommend").removeAttr("disabled");
//					$("#isRecommend").attr("class","validate[required]");
//				}
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
			if (value == '03'||value=='04') {//开放式产品可以选择默认分红方式
				$("#defaultDividendFlag").removeAttr("disabled");
				$("#defaultDividendFlagHide").attr("disabled","disabled");
			}else {
				$("#defaultDividendFlagHide").removeAttr("disabled");
				$("#defaultDividendFlag").attr("disabled","disabled");
			}
			var currency=$("#currency").val();
			if(value == '02' && currency=="156"){//封闭式非净值型
				$("#isOrderAutoBuy").removeAttr("disabled");
				$("#isOrderAutoBuy").attr("class","validate[required]");
			}else{
				$("#isOrderAutoBuy").val(null);//否
				$("#isOrderAutoBuy").attr("disabled","disabled");
				$("#isOrderAutoBuy").removeAttr("class");
			}
			$("#isOrderAutoBuy").render();
			$("#netvalueLastDay").render();
			$("#defaultDividendFlag").render();
			$("#defaultDividendFlagHide").render();
			$("#profitRate").render();
			$("#sellRate").render();
			$("#prodOperModel").val(value);
			$("#profitHandleFlag").render();
			$("#predictHighestProfit").render();
			$("#predictLowestProfit").render();
			$("#isRecommend").render();
			$("#ruleFlag").render();
			//是否质押
			changeIsPledge();
			//定投开办:只有开放式产品才可以
			changeIsDateInvest();
			checkIsDuration();
			var valll = $("#prodtCharactType").val();
			changeProdtCharactType(valll);
		} 
	}, "json");
	
}
function  checkData(){
	//产品成立日<产品到期日
	var prodBeginDate=$("#prodBeginDate").val();
	var prodEndDate=$("#prodEndDate").val();
	var nonrecurringCharge=$("#nonrecurringCharge").val();
	if(compareDateE(prodBeginDate, prodEndDate)){
		top.Dialog.alert("理财产品成立日期["+prodBeginDate+"]必须小于</br>理财产品到期日期["+prodEndDate+"]!");
		return false;
	}
	if (nonrecurringCharge-0<0) {
		top.Dialog.alert("一次性费用不允许为负数！");
	}
	return true;
}
//根据产品代码和产品品牌控制产品品牌是否可以选择
function brandIsDisabled(){
	var brandCodeValue = $("#brandCodeValue").val();
	var prodCode = $("#prodCode").val();
	if(prodCode != null && prodCode != "" && brandCodeValue != "" && brandCodeValue != null){
		$("#brandCode").attr("disabled","disabled");
		$("#brandCode").render();
	}
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
//是否有存续周期
function checkIsDuration(){
	//是否有存续周期
	var prodOperModel = $("#prodOperModel").val();
	var profitHandleFlag = $("#profitHandleFlag").val(); 
	//开放式、固定期限收益率是否有存续周期必须为是
	//由于开放式净值型产品无法维护收益率，因此在此处只允许开放式式非净值型、固定期限收益的产品维护即可
	if(prodOperModel == '04' && profitHandleFlag == '2'){//开放式非净值型、固定期限收益的产品
		$("#isDuration").val("1");
		$("#isDuration").attr("disabled","disabled");
		$("#isDurationHidden").remove();
		$("#isDuration").after("<input name=\"isDuration\" id=\"isDurationHidden\" value=\""+1+"\" type=\"hidden\"/>");
		$("#isDuration").removeAttr("tgClass");
	}else{
		$("#isDuration").val("0");
		$("#isDuration").attr("disabled","disabled");
		$("#isDurationHidden").remove();
		$("#isDuration").after("<input name=\"isDuration\" id=\"isDurationHidden\" value=\""+0+"\" type=\"hidden\"/>");
		$("#isDuration").removeAttr("tgClass");
	}
	$("#isDuration").render();
}
//改变每月收益结算日期
function changeMouthProfitDate(){
	var profitHandleFlag = $("#profitHandleFlag").val();
	if(profitHandleFlag == '1'){//固定收益类
		$("#mouthProfitDate").removeAttr("disabled");
	}else{
		$("#mouthProfitDate").attr("disabled","disabled");
	}
	$("#mouthProfitDate").render();
}

function changeIsPledge(){
	//是否质押
	var prodOperModel = $("#prodOperModel").val();
	var currency = $("#currency").val();
	if(prodOperModel == '02' && currency=='156'){//封闭式非净值型
		$("#isPledge").removeAttr("disabled");
		$("#isPledge").attr("tgClass","validate[required]");
		$("#HisPledge").removeAttr("name");
	}else{
		$("#isPledge").val("0");//否
		$("#isPledge").attr("disabled","disabled");
		$("#isPledge").removeAttr("tgClass");
	}
	$("#isPledge").render();
	$("#HisPledge").render();
}
function changeIsDateInvest(){
	//定投开办:只有开放式产品才可以
	var prodOperModel = $("#prodOperModel").val();
	if(prodOperModel == '03' || prodOperModel == '04'){//开放式产品
		$("#isDateInvest").removeAttr("disabled");
		$("#isDateInvest").attr("tgClass","validate[required]");
		$("#isDateInvestHidden").remove();
	}else{
		$("#isDateInvestHidden").remove();
		$("#isDateInvest").after("<input name=\"isDateInvest\" id=\"isDateInvestHidden\" value=\"0\" type=\"hidden\"/>");
		$("#isDateInvest").val("0");//否
		$("#isDateInvest").attr("disabled","disabled");
		$("#isDateInvest").removeAttr("tgClass");
	}
	$("#isDateInvest").render();
}
//是否自住平衡
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
//保本产品成本户类型是否可选
function changeDisabled(){
	var custType = $("#custType").val();
	var accountingFlag = $("#accountingFlag").val();
	//表内
	if(accountingFlag=='01' && custType=='1'){
		$("#evenCostAssetType").attr("name","evenCostAssetType");
		$("#evenCostAssetType").addClass("validate[required]");
		$("#evenCostAssetType").removeAttr("disabled");
		$("#evenCostAssetType_span").remove();
		$("#evenCostAssetType").after("<span class=\"star\" id=\"evenCostAssetType_span\">*</span>");
	}else if(accountingFlag=='01' && custType=='2'){
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
	if(accountingFlag=='01'){
		$("#sppi").removeAttr("disabled");
		$("#sppi").val("0");
		$("#sppi").attr("class","validate[required]");
	}else{
		$("#sppi").attr("disabled","disabled");
		$("#sppi").val(null);
		$("#sppi").removeAttr("class","validate[required]");
	}
	$("#sppi").render();
	$("#evenCostAssetType").render();
}
//外币：
/*是否积分产品  否
是否允许质押  否
是否预约自动认购 否
转换标志：不允许
是否允许分红方式变更 否*/
function currency(){
	var currency = $("#currency").val();
	if(currency!="156"){
		$("#isPoint").val("0");//是否积分产品  0-否 1-是
		$("#isOrderAutoBuy").val("0");//是否预约自动认购  0-否 1-是
		$("#transitionFlag").val("0");//转换标志  0-不允许 1-转入 2-转出 3-转入转出
		$("#transitionFlagNull").val("0");
		$("#isPledge").val("0");//是否允许质押  0-否 1-是
		$("#isDividendAlter").val("0");//是否允许分红方式变更  0-否 1-是
		//添加不可选标签
		$("#isPoint").attr("disabled","disabled");
		$("#isOrderAutoBuy").attr("disabled","disabled");
		$("#transitionFlag").attr("disabled","disabled");
		$("#transitionFlagNull").attr("disabled","disabled");
		$("#isPledge").attr("disabled","disabled");
		$("#isDividendAlter").attr("disabled","disabled");
		//去掉必选项标签
		$("#isPoint").removeAttr("class","validate[required]");
		$("#isOrderAutoBuy").removeAttr("class","validate[required]");
		$("#transitionFlag").removeAttr("class","validate[required]");
		$("#transitionFlagNull").removeAttr("class","validate[required]");
		$("#isPledge").removeAttr("class","validate[required]");
		$("#isDividendAlter").removeAttr("class","validate[required]");
		//去掉隐藏标签disabled属性
		$("#HisPoint").removeAttr("disabled");
		$("#HisOrderAutoBuy").removeAttr("disabled");
		$("#HtransitionFlag").removeAttr("disabled");
		$("#HisPledge").removeAttr("disabled");
		$("#HisDividendAlter").removeAttr("disabled");
		//重新渲染标签
		$("#isPoint").render();
		$("#isOrderAutoBuy").render();
		$("#transitionFlag").render();
		$("#transitionFlagNull").render();
		$("#isPledge").render();
		$("#isDividendAlter").render();
		$("#HisPoint").render();
		$("#HisOrderAutoBuy").render();
		$("#HtransitionFlag").render();
		$("#HtransitionFlagNull").render();
		$("#HisPledge").render();
		$("#HisDividendAlter").render();
	}
}
function checkPoint(){
	var currency=$("#currency").val();
	var custType=$("#custType").val();
	var isPoint=$("#isPoint").val();
	var vipGrade=$("#vipGrade").val();
	var profitHandleFlag=$("#profitHandleFlag").val();
	var prodOperModel = $("#prodOperModel").val();
	if (isPoint=='1') {
		if (currency=='156'&&custType=='1'&&prodOperModel=='02'&&profitHandleFlag=='1'&&vipGrade!='0'){
			return true;
		}else{
			return false;
		}
	}
	return true;
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
	
	//动态修改 金额 后面的币种字样
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
	$("#lowest").html("个人客户最低投资额（"+lowestCurrency+"）：");
	$("#highest").html("个人客户最高投资额（"+lowestCurrency+"）：");
	$("#multi").html("个人客户投资倍增金额（"+lowestCurrency+"）：");
}
//产品为投资周期型，投资封闭期必输
function changeProdtCharactType(value){
	var val = $("#prodOperModel").val();
	if(value == '03'){//03不可以选择
		$("#closeInvCycle").removeAttr("disabled");
		$("#closeInvCycle").addClass("validate[required,custom[onlyNumber]");
		$("#closeInvCycle").render();
		//$("#ruleFlag").val("01");
		$("#ruleFlag").attr("disabled","disabled");
		$("#ruleFlag").removeAttr("class","validate[required]");
		$("#ruleFlag").render();
	}else{
		$("#closeInvCycle").attr("disabled","disabled");
		$("#closeInvCycle").removeAttr("class");
		$("#closeInvCycle").val("");
		$("#closeInvCycle").render();
		if(val == '03' || val == '04'){
			$("#ruleFlag").removeAttr("disabled");
			$("#ruleFlag").attr("class","validate[required]");
			$("#ruleFlag").render();
		}
	}	
}
//判断是否在保存存续期后进行的产品类型变更
/*function validateProdtCharactType(value){
	$.post(path + "/gfProdEleChange/validateProdtCharactType.htm",{"prodCode":$("#prodCode").val(),"prodtCharactType":value},function(result){
		if(result.success == "true" || result.success == true){
			top.Dialog.alert(result.msg);
		}
	}, "json");
}*/