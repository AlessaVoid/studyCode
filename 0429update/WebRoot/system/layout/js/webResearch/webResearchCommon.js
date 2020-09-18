var paraTypeStr = "";
//系统路径
var path="/web";
function getRateIndex(profitHandleFlag){
	var index;
	if(profitHandleFlag == '1'){
		index='6';
	}else if(profitHandleFlag == '2'){
		index='7';
	}else if(profitHandleFlag == '4'){
		index='8';
	}else if(profitHandleFlag == '3'){
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
		index='11';
	}
	return index;
}
function changeDiv(index,oldIndex){
	var sellFeeType = $("#sellFeeType").val();
	var profitHandleFlag = $("#profitHandleFlag").val();
	if(index == oldIndex){
		if(index == ''){
			index = '1';
		}else if(index==5){
			if(profitHandleFlag == '1'){
				index = '6';
			}else if(profitHandleFlag == '2'){
				index = '7';
			}else if(profitHandleFlag == '4'){
				index = '8';
			}else if(profitHandleFlag == '3'){//收益率为灵活期限
				if(sellFeeType == '2'){//销售费率为期限机构
					index = '9';
				}
			}else{//如果是净值型产品的话，直接进入销售费率维护界面
				if(sellFeeType == '1'){
					index = '10';
				}
			}
		}else if(index=='6' || index=='7' || index=='8' || index=='9'){
			var sellFeeType = $("#sellFeeType").val();
			var oldSellFeeType = $("#oldSellFeeType").val();
			var buyIndex = $("#buyIndex").val();
			if(sellFeeType == oldSellFeeType){//销售费率未进行修改
				index = buyIndex;
			}else{//销售费率进行修改
				index = getFeeIndex(sellFeeType);
			}
		}else if(index=='10'){
			var buyIndex = $("#buyIndex").val();
			if(buyIndex == '12'){//认购维护过之后会跳到该iframe
				index = '12';
			}else{//认购没有维护过之后会跳到该iframe
				index = '11';
			}
		}else{
			index = parseInt(index)+1;
		}
	}else{
		
		var profitHandleFlag = $("#profitHandleFlag").val();
		var oldProfitHandleFlag = $("#oldProfitHandleFlag").val();
		var sellFeeType = $("#sellFeeType").val();
		if(oldIndex >= 6 && oldIndex <=9){
			index = getRateIndex(profitHandleFlag);
		}else if(oldIndex = 10){
			if(profitHandleFlag == oldProfitHandleFlag){//10/11-1之后，收益率未修改
				 index = getFeeIndex(sellFeeType);
			}else{//10/11-1之后，如果收益率进行修改之后，获取到当前收益率的iframe,跳转到iframe
				 index = getRateIndex(profitHandleFlag);
			}
		}else if(oldIndex == 11 || oldIndex == 12){
			//判断收益率是否相同，如果相同的话，跳到之前的维护页面；如果不相同的话，
			if(profitHandleFlag == oldProfitHandleFlag){//销售费率未改
				 var oldSellFeeType = $("#oldSellFeeType").val();
				 var sellFeeType = $("#sellFeeType").val();
				 if(oldSellFeeType == sellFeeType){//销售费率修改	
					 index = oldIndex;
				 }else{//销售费率未改
					 index = getFeeIndex(sellFeeType);
				 }
			}else{//收益率进行修改的话
				 index = getRateIndex(profitHandleFlag);
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
	changeParaType(index);
}

function changeRate(){
  var oldProfitHandleFlag = $("#oldProfitHandleFlag").val();
  var oldSellFeeType = $("#oldSellFeeType").val();
  var index = $("#index").val();
  if(oldProfitHandleFlag != "" && oldSellFeeType != "" && index >= 6){
	  var profitHandleFlag = $("#profitHandleFlag").val();
	  var sellFeeType = $("#sellFeeType").val();
	  removeItem1(getRateIndex(oldProfitHandleFlag));
	  removeItem1(getRateIndex(profitHandleFlag));
	  changeParaType(getRateIndex(profitHandleFlag));
	  removeItem1(getFeeIndex(sellFeeType));
	  removeItem1(getFeeIndex(oldSellFeeType));
	  changeParaType(getFeeIndex(sellFeeType));
  }
}
function removeItem1(index){
	if(paraTypeStr.indexOf("panel"+index) != -1){
		paraTypeStr.replace("panel"+index, "");
		$("#paraType option[value='panel"+index+"']").remove();
		$("#paraType").render();
	}
}
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
		}
	}
	if(index == '10'){
		var sellFeeType = $("#sellFeeType").val();
		if(sellFeeType == '1'){
			$("#paraType").append('<option value="panel10">固定销售费率参数</option>');
			paraTypeStr+="panel10";
		}
	}
	if(index == '11'){
		$("#paraType").append('<option value="panel11">认购参数</option>');
		paraTypeStr+="panel11";
	}
	if(index == '12'){
		$("#paraType").append('<option value="panel12">审批参数</option>');
		paraTypeStr+="panel12";
	}
	$("#paraType").render();
}
//参数类型下拉框
function changeParam(){
	$("#paraType").change(function(){
		var value = $("#paraType option:selected").val();
		var panelIndex = value.substring(5);
		var index = $("#index").val();
		for(var i=1;i<=index;i++){
			if(i!=panelIndex){
				$("#panel"+i).hide();
				$("#panel"+i).render();
			}
		}
		$("#" + value).show();
		$("#" + value).render();
		$("#" + value).box2Open();
	});
}

//打开指定的iframe,跳转下一页的时候用到
function openIframe(index){
	var oldIndex = index;
	changeDiv(index,oldIndex);
}