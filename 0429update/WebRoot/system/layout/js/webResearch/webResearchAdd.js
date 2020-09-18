//给div绑定事件，动态加载url
function bindUrl(){
	$("div[id^='panel']").bind("stateChange",function(e,state){
		if(state == "show"){
			var panelId = $(this).attr("id");
			var index = panelId.substring(5);
			var oldIndex = $("#index").val();
			var buyIndex = $("#buyIndex").val();
			var url = $(this).attr("panelUrl") + "&prodCode=" + $("#prodCode").val()
				+ "&prodName=" + $("#prodName").val()
				+ "&index="+index+"&oldIndex="+oldIndex+"&buyIndex="+buyIndex;
			if($("#brandCode").val() != "" && index==1){
				url = url +"&brandCode="+$("#brandCode").val();
			}
			if($("#prodOperModel").val() != "" && index==5){
				url = url +"&prodOperModel="+$("#prodOperModel").val();
			}
			$("#iframepage" + index).height($(top.document.body).height());
			$("#iframepage" + index).attr("src", url);
		}
	});
}
//基本参数维护后，将一些控制参数放到父页面里，用来控制其他参数子页面是否能维护
function setBaseInfo(bean){
	$.each(bean,function(i,e){
		$("#"+i).val(e);
	});
}

function changeIframe(){
	var index = 1;
	var prodCode = $("#prodCode").val();
	var prodOperModel =  $("#prodOperModel").val();
	if(prodCode == ""){
		for(var i=2;i<=13;i++){
			$("#panel"+i).hide();
			$("#panel"+i).render();
		}
		$("#panel"+index).box2Open();
	}else{
		var value = $("#paraType option:selected").val();
		$("#" + value).box2Open();
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