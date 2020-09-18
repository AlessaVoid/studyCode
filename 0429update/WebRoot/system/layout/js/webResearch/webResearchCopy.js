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
	$(".prod").empty();
	$(".prod").remove();
	$("#tr_t").prepend("<td align=\"right\" width=\"16%\" class=\"prod\">产品代码：</td><td class=\"prod\">"+bean.prodCode+"</td><td align=\"right\" class=\"prod\">产品名称：</td><td class=\"prod\">"+bean.prodName+"</td>");
}
function turnPage(p1,p2){
	$("#" + p1).box2Close();
	$("#" + p2).box2Open();
}