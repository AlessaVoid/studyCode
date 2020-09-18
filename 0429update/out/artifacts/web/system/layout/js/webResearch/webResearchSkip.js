var paraTypeStr = "";
//系统路径
var path="/web";
function changeDiv(index,oldIndex){
	var profitHandleFlag = $("#profitHandleFlag").val();
	var oldProfitHandleFlag=$("#oldProfitHandleFlag").val();
	var sellFeeType=$("#sellFeeType").val();
	var prodOperModel=$("#prodOperModel").val();
	var index_o=index;
	$.post(path+"/researchTurn/changeDiv.htm",{
			index:index,
			oldIndex:oldIndex,
			profitHandleFlag:profitHandleFlag,
			oldProfitHandleFlag:oldProfitHandleFlag,
			sellFeeType:sellFeeType,
			prodOperModel:prodOperModel,
			paraTypeStr:paraTypeStr
			},function(result){
				index=result.msg;
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
			});
}

function changeRate(){
  var oldProfitHandleFlag = $("#oldProfitHandleFlag").val();
  var oldSellFeeType = $("#oldSellFeeType").val();
  var index = $("#index").val();
  if(oldProfitHandleFlag != "" && oldSellFeeType != "" && index >= 6){
	  var profitHandleFlag = $("#profitHandleFlag").val();
	  var sellFeeType = $("#sellFeeType").val();
	  if (oldSellFeeType!=sellFeeType) {
		  removeItem1("10");
	}
	  if (oldProfitHandleFlag!=profitHandleFlag) {
		  removeItem1(getRateIndex(oldProfitHandleFlag));
	  }
  }
}
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
		}else {
			index='10';
		}
	}
	return index;
}
function removeItem1(index){
	if(paraTypeStr.indexOf("panel"+index) != -1){
		paraTypeStr=paraTypeStr.replace("panel"+index, "");
		$("#paraType option[value='panel"+index+"']").remove();
		$("#paraType").render();
	}
}
function changeParaType(index){
	var profitHandleFlag = $("#profitHandleFlag").val();
	var sellFeeType = $("#sellFeeType").val();
	$.post(path+"/researchTurn/changeParaType.htm",{
		index:index,
		sellFeeType:sellFeeType,
		profitHandleFlag:profitHandleFlag
		},function(result){
			var pagePara=result.msg;
			if (pagePara!=undefined&&pagePara!='') {
			if (paraTypeStr.indexOf(pagePara.split("-")[0]) ==-1) {
				$("#paraType").append('<option value="'+pagePara.split("-")[0]+'">'+pagePara.split("-")[1]+'</option>');
				paraTypeStr+=pagePara.split("-")[0];
				$("#paraType").render();
				}
			}
		},"json");
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
