//给div绑定事件，动态加载url
function bindUrl(){
	$("div[id^='panel']").bind("stateChange",function(e,state){
		if(state == "show"){
			var panelId = $(this).attr("id");
			var index = panelId.substring(5);
			if($("#prodCode").val() == "" && index>1){
				top.Dialog.alert("请先维护基本参数!",function(){
					$("#" + panelId).box2Close();
					$("#panel1").box2Open();
				});
			}
			var url = $(this).attr("panelUrl") + "?copyType=copyType&prodCode=" + $("#prodCode").val()+"&copyProdCode="+$("#copyProdCode").val();
			$("#iframepage" + index).height($(document.body).height());
			$("#iframepage" + index).attr("src", url);
		}
	});
}
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
	for(var i=1;i<13;i++){
		$("#panel"+i).show();
		$("#panel"+i).render();
	}
	var profitHandleFlag = entity.profitHandleFlag;
	var sellFeeType = entity.sellFeeType;
	var prodOperModel = entity.prodOperModel;
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
	if(sellFeeType != ""){
		if(sellFeeType != '1'){
			$("#panel10").hide();
			$("#panel10").render();
		}
	}
	if(prodOperModel != ""){
		if(prodOperModel == '02'){
			$("#panel12").hide();
			$("#panel12").render();
		}else if(prodOperModel == '04'){
			$("#panel12").hide();
			$("#panel12").render();
		}
	}
}