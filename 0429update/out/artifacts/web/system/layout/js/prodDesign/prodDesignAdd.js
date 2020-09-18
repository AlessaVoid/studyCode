//给div绑定事件，动态加载url
function bindUrl(){
	$("div[id^='panel']").bind("stateChange",function(e,state){
		if(state == "show"){
			var panelId = $(this).attr("id");
			var index = panelId.substring(5);
			var oldIndex = $("#index").val();
			var buyIndex = $("#buyIndex").val();
			var url = $(this).attr("panelUrl") + "&prodCode=" + $("#prodCode").val()+"&index="+index+"&oldIndex="+oldIndex+"&buyIndex="+buyIndex;
			if($("#brandCode").val() != "" && index==1){
				url = url +"&brandCode="+$("#brandCode").val();
			}
			if($("#prodRiskLevel").val() != "" && index==5){
				url = url +"&prodRiskLevel="+$("#prodRiskLevel").val();
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

