var paraTypeStr = "";
//系统路径
var path="/web";
function getRateIndex(profitHandleFlag,curIndex){
	var index=1;
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
		}
	}
	return index;
}
function changeDiv(index,oldIndex){
	var profitHandleFlag = $("#profitHandleFlag").val();
	var oldProfitHandleFlag=$("#oldProfitHandleFlag").val();
	var buyIndex=$("#buyIndex").val();
	var sellFeeType = $("#sellFeeType").val();
	var prodOperModel=$("#prodOperModel").val();
	var isPoint=$("#isPoint").val();
	var isCustProp=$("#isCustProp").val();
	var index_o=index;
	$.post(path+"/designTurn/changeDiv.htm",{
			index:index,
			oldIndex:oldIndex,
			profitHandleFlag:profitHandleFlag,
			oldProfitHandleFlag:oldProfitHandleFlag,
			buyIndex:buyIndex,
			sellFeeType:sellFeeType,
			prodOperModel:prodOperModel,
			paraTypeStr:paraTypeStr,
			isPoint:isPoint,
			isCustProp:isCustProp
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
				if (index_o=='1') {
					removeItemIS();
				}
				changeParaType(index_o);
			},"json");
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
		  removeItem1(getRateIndex(oldProfitHandleFlag,index));
	  }
  }
}

function removeItemIS(){
	var isPoint=$("#isPoint").val();
	var isCustProp=$("#isCustProp").val();
	var prodOperModel=$("#prodOperModel").val();
	var oldIsPoint=$("#oldIsPoint").val();
	var oldICustProp=$("#oldICustProp").val();
	var oldProdOperModel=$("#oldProdOperModel").val();
	var prodCode=$("#prodCode").val();
	if (oldProdOperModel!=null&&oldProdOperModel!=prodOperModel) {
		if (prodOperModel=='02'||prodOperModel=='04') {//收益率型去掉净值
			removeItem1(13);
			$.post(path+"/design/deleteNetValue.htm",{prodCode:prodCode},function(result){
				
			},"json");
		}
		if (prodOperModel=='01'||prodOperModel=='02') {//封闭式去掉存续期和赎回
			removeItem1(16);
			removeItem1(19);
			$.post(path+"/design/deleteOpenPeriod.htm",{prodCode:prodCode},function(result){
			},"json");
			$.post(path+"/design/deleteRedeem.htm",{prodCode:prodCode},function(result){
				
			},"json");
		}
	}
	if (oldIsPoint!=null&&oldIsPoint!=isPoint) {
		if (isPoint=='0') {//非积分，删除积分
			removeItem1(14);
			$.post(path+"/design/deletePoint.htm",{prodCode:prodCode},function(result){
				
			},"json");
		}
	}
	if (oldICustProp!=null&&oldIsCustProp!=isCustProp) {
		if (isCustProp=='0') {//非控制行内属性
			removeItem1(15);
			$.post(path+"/design/deleteCustProp.htm",{prodCode:prodCode},function(result){
				
			},"json");
		}
	}
}
function removeItem1(index){
	if(paraTypeStr.indexOf("panel"+index) != -1){
		paraTypeStr=paraTypeStr.replace("panel"+index, "");
		$("#paraType option[value='panel"+index+"']").remove();
		$("#paraType").render();
	}
}
//动态生成下拉框
function changeParaType(index){
	var profitHandleFlag = $("#profitHandleFlag").val();
	var sellFeeType = $("#sellFeeType").val();
	$.post(path+"/designTurn/changeParaType.htm",{
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
