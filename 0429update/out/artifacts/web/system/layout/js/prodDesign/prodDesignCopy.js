//基本参数维护后，将一些控制参数放到父页面里，用来控制其他参数子页面是否能维护
function setBaseInfo(bean){
	$.each(bean,function(i,e){
		$("#"+i).val(e);
	});
	changeIframeCopy(bean);
	$(".prod").empty();
	$(".prod").remove();
	param(bean);
}
function turnPage(p1,p2){
	$("#" + p1).box2Close();
	$("#" + p2).box2Open();
}
function changeIframeCopy(entity){
	for(var i=1;i<22;i++){
		$("#panel"+i).show();
		$("#panel"+i).render();
	}
	var profitHandleFlag = entity.profitHandleFlag;
	var prodOperModel = entity.prodOperModel;
	var sellFeeType = entity.sellFeeType;
	var isPoint = entity.isPoint;
	var isCustProp = entity.isCustProp;
	var index = '';
	if(profitHandleFlag != ""){
		if(profitHandleFlag == '1'){
			index = '6';
		}else if(profitHandleFlag == '2'){
			index = '7';
		}else if(profitHandleFlag == '4'){
			index = '8';
		}else if(profitHandleFlag == '3'){
			index = '9';
		}
		for(var i=6;i<=9;i++){
			if(i != index){
				$("#panel"+i).hide();
				$("#panel"+i).render();
			}
		}
	}
	if(prodOperModel != ""){
		if(prodOperModel == '01'){
			$("#panel16").hide();
			$("#panel16").render();
			$("#panel19").hide();
			$("#panel19").render();
		}else if(prodOperModel == '02'){
			$("#panel13").hide();
			$("#panel13").render();
			$("#panel16").hide();
			$("#panel16").render();
			$("#panel19").hide();
			$("#panel19").render();
			$("#panel21").hide();
			$("#panel21").render();
		}else if(prodOperModel == '04'){
			$("#panel13").hide();
			$("#panel13").render();
			$("#panel21").hide();
			$("#panel21").render();
		}
	}
	if(sellFeeType != ""){
		if(sellFeeType != '1'){
			$("#panel10").hide();
			$("#panel10").render();
		}
	}
	if(isPoint != ""){
		if(isPoint != '1'){
			$("#panel14").hide();
			$("#panel14").render();
		}
	}
	if(isCustProp != ""){
		if(isCustProp != '1'){
			$("#panel15").hide();
			$("#panel15").render();
		}
	}
}