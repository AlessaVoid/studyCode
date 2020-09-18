var paraTypeStr = "";
//系统路径
var path="/web";
function getRateIndex(profitHandleFlag,curIndex){
	var index;
	if(profitHandleFlag == '1'){
		index='6';
	}else if(profitHandleFlag == '2'){
		index='7';
	}else if(profitHandleFlag == '4'){
		index='8';
	}else if(profitHandleFlag == '3' && curIndex != '9'){
		index='9';
	}else{//如果是净值型产品的话，直接进入销售费率维护界面
		var sellFeeType = $("#sellFeeType").val();
		if(sellFeeType == '1'){
			index = '10';
		}else{
			index = '11';
		}
	}
	return index;
}
function getFeeIndex(sellFeeType){
	var index;
	if(sellFeeType == '1'){
		index='10';
	}else{
		index = '11';
	}
	return index;
}
function changeDiv(index,oldIndex){
	var index_o=index;
	if(index == oldIndex){
		if(index == ''){
			index = '1';
		}else if(index==5){
			var profitHandleFlag = $("#profitHandleFlag").val();
			if(profitHandleFlag == '1'){//固定
				index = '6';
			}else if(profitHandleFlag == '2'){//固定期限
				index = '7';
			}else if(profitHandleFlag == '4'){//规模期限期限
				index = '8';
			}else if(profitHandleFlag == '3'){//灵活期限
				index = '9';
			}else{//如果是净值型产品的话，直接进入销售费率维护界面
				var sellFeeType = $("#sellFeeType").val();
				if(sellFeeType == '1'){
					index = '10';
				}else{
					index = '11';
				}
			}
		}else if(index=='6' || index=='7' || index=='8' || index=='9'){
			var sellFeeType = $("#sellFeeType").val();
			var oldSellFeeType = $("#oldSellFeeType").val();
			var buyIndex = $("#buyIndex").val();//12、13
			if(sellFeeType == oldSellFeeType){//销售费率未进行修改
				if(buyIndex == "" || parseInt(buyIndex) <= parseInt(index)){
					index = getFeeIndex(sellFeeType);
				}else{
					index = buyIndex;
				}
			}else{//销售费率进行修改
				index = getFeeIndex(sellFeeType);
			}
		}else if(index=='10'){//12、13、14
			var buyIndex = $("#buyIndex").val();
			if(buyIndex != "" && buyIndex != '11'){//认购维护过之后会跳到该iframe
				if(parseInt(buyIndex) > 11){
					index = buyIndex;
				}else{
					index = '11';
				}
			}else{//认购没有维护过之后会跳到该iframe
				index = '11';
			}
		}else if(parseInt(index) >= 12 && parseInt(index) <= 19){
			index = getNextIndex();
		}else{
			index = parseInt(index)+1;
		}
	}else{
		var profitHandleFlag = $("#profitHandleFlag").val();
		var oldProfitHandleFlag = $("#oldProfitHandleFlag").val();
		if(''==oldProfitHandleFlag){//如果为空，赋值为0
			oldProfitHandleFlag='0';
		}
		var sellFeeType = $("#sellFeeType").val();
		if(oldIndex >= 6 && oldIndex <=9){
			index = getRateIndex(profitHandleFlag,index);
		}else if(oldIndex == 10){
			if(profitHandleFlag == oldProfitHandleFlag){//10/11-1之后，收益率未修改
				 index = getFeeIndex(sellFeeType);
			}else{//10/11-1之后，如果收益率进行修改之后，获取到当前收益率的iframe,跳转到iframe
				 index = getRateIndex(profitHandleFlag,index);
			}
		}else if(oldIndex >=11 && oldIndex < 12){//如果在返回产品基本信息修改信息之后，都要判断一下，收益率、销售费率是否已进行修改，规则如下
			//判断收益率是否相同，如果相同的话，跳到之前的维护页面；如果不相同的话，
			if(profitHandleFlag == oldProfitHandleFlag){//收益率未改
				 var oldSellFeeType = $("#oldSellFeeType").val();
				 var sellFeeType = $("#sellFeeType").val();
				 if(oldSellFeeType == sellFeeType){//销售费率修改	
					 index = oldIndex;
				 }else{//销售费率未改
					 index = getFeeIndex(sellFeeType);
				 }
			}else{//收益率进行修改的话
				 index = getRateIndex(profitHandleFlag,index);
			}
		}else if(oldIndex >=12){
			//判断收益率是否相同，如果相同的话，跳到之前的维护页面；如果不相同的话，
			if(profitHandleFlag == oldProfitHandleFlag){//收益率未改
				 var oldSellFeeType = $("#oldSellFeeType").val();
				 var sellFeeType = $("#sellFeeType").val();
				 if(oldSellFeeType == sellFeeType){//销售费率修改	
					 index = getNextIndex();
				 }else{//销售费率未改
					 index = getFeeIndex(sellFeeType);
				 }
			}else{//收益率进行修改的话
				 index = getRateIndex(profitHandleFlag,index);
				 if(index == '0'){
					 var oldSellFeeType = $("#oldSellFeeType").val();
					 var sellFeeType = $("#sellFeeType").val();
					 if(oldSellFeeType == sellFeeType){//销售费率未修改	
						 index = getNextIndex();
					 }else{//销售费率修改
						 index = getFeeIndex(sellFeeType);
					 }
				 }
			}
		}else{
			index = oldIndex;
		}
	}
	for(var i=1;i<=index;i++){
		$("#panel"+i).hide();
		$("#panel"+i).render();
	}
	$("#index").val(index);
	$("#panel"+index).show();
	$("#panel"+index).render();
	$("#panel"+index).box2Open();
	removeItem1(index);                                                  
	changeParaType(index_o);
}
/**
 * index12之后跳转
 * 判断是否已维护分红参数
 */
function getNextIndex(){
	var index = "";
	if(paraTypeStr.indexOf("panel11") != -1){
		index = getNetValue("panel12");
	}else{
		index = '12';
	}
	return index;
}
/**
 * 判断是否需要维护净值参数
 * panel13
 * 
 */
function getNetValue(p){
	var index = "";
	var prodOperModel = $("#prodOperModel").val();
	if(prodOperModel == '01' || prodOperModel == '03'){//开放式净值型、封闭式净值型
		if(paraTypeStr.indexOf(p) != -1){
			p="panel13";
			index = getIsPoint(p);
		}else{
			index = '13';
		}
	}else{//封闭式非净值型、开放式非净值型
		index = getIsPoint(p);
	}
	return index;
}
/**
 * 
 * 判断是否已维护积分参数
 * panel14
 */
function getIsPoint(p){
	var index = "";
	if(checkIsPoint()){
		if(paraTypeStr.indexOf(p) != -1){
			p="panel14";
			index = getIsCustProp(p);
		}else{
			index = '14';
		};
	}else{
		index = getIsCustProp(p);
	}
	return index;
}
/**
 * 判断是否维护控制客户行内属性
 * panel15
 */
function getIsCustProp(p){
	var index = "";
	if(checkIsCustProp()){//如果是否控制客户行内属性
		if(paraTypeStr.indexOf(p) != -1){//已维护
			p="panel15";
			index = getIsDuration(p);
		}else{//未维护
			index = '15';
		};
	}else{
		index = getIsDuration(p);
	}
	return index;
}
/**
 * 判断是否维护开始方式存续期参数
 * panel16
 */
function getIsDuration(p){
	var index = "";
	if(checkIsDuration()){
		if(paraTypeStr.indexOf(p) != -1){
			p="panel16";
			index = getStatisticsPara(p);
		}else{
			index = '16';
		};
	}else{
		index = getStatisticsPara(p);
	}
	return index;
}
/**
 * 判断是否已维护统计口径
 * panel17
 */
function getStatisticsPara(p){
	var index = "";
	if(paraTypeStr.indexOf(p)!= -1){
		p="panel17";
		index = getIsOrderAutoBuy(p);
	}else{
		index = '17';
	}
	return index;
}
/**
*判断是否已维护预约自动认购
*panel18
 * 
 */
function getIsOrderAutoBuy(p){
	var index = "";
	if(paraTypeStr.indexOf(p) != -1){
		p="panel18";
		index = getIsRedeem(p);
	}else{//不存在的话
		index = '18';
	}
	return index;
}
/**
 * 判断是否跳转赎回页面 
 * panel19
 */
function getIsRedeem(p){
	var index = "";
	if(checkIsRedeem()){//可以，则进入赎回收缩页面
		if(paraTypeStr.indexOf(p) != -1){
			index = '20';
		}else{
			index = '19';
		};
	}else{
		index = '20';
	}
	return index;
}
//判断是否可以进入赎回页面
function checkIsRedeem(){
	var prodOperModel = $("#prodOperModel").val();
	//判断产品运作模式
	if(prodOperModel == '03' || prodOperModel == '04'){//开放式净值型、开放式非净值型
		return true;
	}else if(prodOperModel == '01' || prodOperModel == '02'){//封闭式净值型、封闭式非净值型
		return false;	
	};
}
function checkIsPoint(){
	var isPoint = $("#isPoint").val();
	//判断是否是积分产品，
	if(isPoint == '1'){//如果是的话，跳转到积分收缩页维护；
		return true;
	}else{//如果不是,判断是否控制客户行内属性；
		return false;
	};
}

function checkIsCustProp(){
	var isCustProp = $("#isCustProp").val();
	if(isCustProp == '1'){//如果是否控制客户行内属性为是的话，跳转到客户行内属性收缩页进行维护；
		return true;
	}else{//如果不是，判断是否有存续周期参数；
		return false;
	};
}
function checkIsDuration(){
	var prodOperModel = $("#prodOperModel").val();
	if(prodOperModel == '03' || prodOperModel == '04'){//如果产品运作模式为开放式净值型、开放式非净值型，跳转到存续期参数收缩页面进行维护；
		return true;
	}else{//如果不是的话，跳转到统计口径参数收缩页面(panel18)
		return false;
	};
}
function changeRate(){
  var oldProfitHandleFlag = $("#oldProfitHandleFlag").val();
  var oldSellFeeType = $("#oldSellFeeType").val();
  var index = $("#index").val();
  if(oldProfitHandleFlag != "" && oldSellFeeType != "" && index >= 6){
	  var profitHandleFlag = $("#profitHandleFlag").val();
	  var sellFeeType = $("#sellFeeType").val();
	  removeItem1(getRateIndex(oldProfitHandleFlag,index));
	  removeItem1(getRateIndex(profitHandleFlag,index));
	  changeParaType(getRateIndex(profitHandleFlag,index));
	  removeItem1(getFeeIndex(sellFeeType));
	  removeItem1(getFeeIndex(oldSellFeeType));
	  changeParaType(getFeeIndex(sellFeeType));
  };
}
function removeItem1(index){
	if(paraTypeStr.indexOf("panel"+index) != -1){
		$("#paraType option[value='panel"+index+"']").remove();
		$("#paraType").render();
	}
}
//动态生成下拉框
function changeParaType(index){
	if(index == '1'){
		$("#paraType").append('<option value="panel1">产品基本参数</option>');
		paraTypeStr+="panel1";
	}
	if(index == '2'){
		$("#paraType").append('<option value="panel2">项目参数</option>');
		paraTypeStr+="panel2";
	}
	if(index == '3'){
		$("#paraType").append('<option value="panel3">产品销售渠道</option>');
		paraTypeStr+="panel3";
	}
	if(index == '4'){
		$("#paraType").append('<option value="panel4">产品发售区域</option>');
		paraTypeStr+="panel4";
	}
	if(index == '5'){
		$("#paraType").append('<option value="panel5">申报登记参数</option>');
		paraTypeStr+="panel5";
	}
	if(index=='6' || index=='7' || index=='8' || index=='9'){
		var profitHandleFlag = $("#profitHandleFlag").val();
		if(profitHandleFlag == '1'){
			$("#paraType").append('<option value="panel6">固定收益类参数</option>');
			paraTypeStr+="panel6";
		}else if(profitHandleFlag == '2'){
			$("#paraType").append('<option value="panel7">固定期限收益类组合参数</option>');
			paraTypeStr+="panel7";
		}else if(profitHandleFlag == '4'){
			$("#paraType").append('<option value="panel8">规模期限收益组合参数</option>');
			paraTypeStr+="panel8";
		}else if(profitHandleFlag == '3'){
			$("#paraType").append('<option value="panel9">灵活期限收益组合参数</option>');
			paraTypeStr+="panel9";
		};
	}
	if(index == '10'){
		var sellFeeType = $("#sellFeeType").val();
		if(sellFeeType == '1'){
			$("#paraType").append('<option value="panel10">固定销售费率参数</option>');
			paraTypeStr+="panel10";
		};
	}
	if(index == '11'){
		$("#paraType").append('<option value="panel11">认购参数</option>');
		paraTypeStr+="panel11";
	}
	if(index == '12'){
	  $("#paraType").append('<option value="panel12">分红参数</option>');
	  paraTypeStr+="panel12";
	}
	if(index == '13'){
	  $("#paraType").append('<option value="panel13">净值参数</option>');
	  paraTypeStr+="panel13";
	}
	if(index == '14'){
	  $("#paraType").append('<option value="panel14">积分参数</option>');
	  paraTypeStr+="panel14";
	}
	if(index == '15'){
	  $("#paraType").append('<option value="panel15">行内属性参数</option>');
	  paraTypeStr+="panel15";
	}
	if(index == '16'){
	  $("#paraType").append('<option value="panel16">开放式产品存续期参数</option>');
	  paraTypeStr+="panel16";
	}
	if(index == '17'){
	  $("#paraType").append('<option value="panel17">统计口径参数</option>');
	  paraTypeStr+="panel17";
	}
	if(index == '18'){
	  $("#paraType").append('<option value="panel18">预约购买参数</option>');
	  paraTypeStr+="panel18";
	}
	if(index == '19'){
	  $("#paraType").append('<option value="panel19">赎回参数</option>');
	  paraTypeStr+="panel19";
	}
	if(index == '20'){
	  $("#paraType").append('<option value="panel20">资产基本参数</option>');
	  paraTypeStr+="panel20";
	}
	if(index == '21'){
	  $("#paraType").append('<option value="panel21">审批参数</option>');
	  paraTypeStr+="panel21";
	}
	$("#paraType").render();
}
//参数类型下拉框
function changeParam(){
	$("#paraType").change(function(){
		var value = $("#paraType option:selected").val();
		var panelIndex = value.substring(5);
		var index = $("#index").val();
		$("#buyIndex").val(index);
		for(var i=1;i<=index;i++){
			if(i!=panelIndex){
				$("#panel"+i).hide();
				$("#panel"+i).render();
			};
		}
		$("#" + value).show();
		$("#" + value).render();
		$("#" + value).box2Open();
	});
};
