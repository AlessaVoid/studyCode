<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
			<!--框架必需end-->
	<!--表单异步提交start-->
	<script src="${path}/libs/js/form/form.js" type="text/javascript"></script>
	<!--表单异步提交end-->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="<%=path%>/libs/js/popup/drag.js"></script>
	<script type="text/javascript" src="<%=path%>/libs/js/popup/dialog.js"></script>
	<script type="text/javascript" src="<%=path%>/system/layout/js/prodDesign/prodDesignCopy.js"></script>
	<!--引入弹窗组件end-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>产品预研</title> 
		<script type="text/javascript">
		$(function(){
			//给div绑定事件，动态加载url
			bindUrl();
			//参数类型下拉框
			changeParam();
			//动态控制iframe的显示
			changeIframe();
			//默认加载基本信息页面
			openBaseInfo();
		});
		function openBaseInfo(){
			$("#panel1").box2Open();
		}
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
		//参数类型下拉框
		function changeParam(){
			$("#paraType").change(function(){
				var value = $("#paraType option:selected").val();
				$("div[id^='panel']").each(function(){
					$(this).box2Close();
				});
				$("#" + value).box2Open();
			});
		}
		function changeIframe(){
			var profitHandleFlag = "${entity.profitHandleFlag}";
			var prodOperModel = "${entity.prodOperModel}";
			var sellFeeType = "${entity.sellFeeType}";
			var isPoint = "${entity.isPoint}";
			var isCustProp = "${entity.isCustProp}";
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
		function param(entity){
			$("#tr_t").empty();
			var profitHandleFlag = entity.profitHandleFlag;
			var sellFeeType = entity.sellFeeType;
			var prodOperModel = entity.prodOperModel;
			var	isPoint = entity.isPoint;
			var isCustProp = entity.isCustProp;
			var $option="<td align=\"right\" width=\"16%\" class=\"prod\">产品代码：</td><td class=\"prod\">"+entity.prodCode+"</td><td align=\"right\" class=\"prod\">产品名称：</td><td class=\"prod\">"+entity.prodName+"</td>"+
						"<td align=\"right\" width=\"16%\">参数类型：</td>"+
						"<td width=\"16%\">"+
						"<select id=\"paraType\">"+
						"<option selected=\"selected\">--请选择--</option>"+
						"<option value=\"panel1\">产品基本参数</option>" +
						"<option value=\"panel2\">项目分类及投资比例</option>" +
						"<option value=\"panel3\">产品销售渠道</option>" +
						"<option value=\"panel4\">产品销售区域</option>" +
						"<option value=\"panel5\">申报登记参数</option>";
			if(profitHandleFlag==1){
				$option+="<option value=\"panel6\">固定收益类参数</option>";
			}else if (profitHandleFlag==2){
				$option+="<option value=\"panel7\">固定期限收益类组合参数</option>";
			}else if (profitHandleFlag==4){
				$option+="<option value=\"panel8\">规模期限收益组合参数</option>";
			}else if (profitHandleFlag==3){
				$option+="<option value=\"panel9\">灵活期限收益组合参数</option>";
			}
			if(sellFeeType==1) {
				$option+="<option value=\"panel10\">固定销售费率参数</option>";
			}
			$option+="<option value=\"panel11\">认购参数</option>" +
						"<option value=\"panel12\">分红参数</option>";
			if(prodOperModel==01 || prodOperModel==03){
				$option+="<option value=\"panel13\">净值参数</option>";
			}
			if(isPoint==1){
				$option+="<option value=\"panel14\">积分参数</option>";
			}
			if(isCustProp==1){
				$option+="<option value=\"panel15\">行内属性参数</option>";
			}
			if(prodOperModel==03 || prodOperModel==04){
				$option+="<option value=\"panel16\">开放式产品存续期参数</option>";
			}
			$option+="<option value=\"panel17\">统计口径参数</option>"+
						"<option value=\"panel18\">预约购买参数</option>";
			if(prodOperModel==03 || prodOperModel==04){
				$option+="<option value=\"panel19\">赎回参数</option>";
			}
			if(prodOperModel==01 || prodOperModel==03){
				$option+="<option value=\"panel21\">业绩基准比较参数</option>";
			}
			$option+="<option value=\"panel22\">审批参数</option>" +
					 "</select>"+
					 "</td>";
			
			$("#tr_t").prepend($option);
			$("#paraType").render();
			changeParam();
		}

		</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode}"/>
			<input type="hidden" name="copyProdCode" id="copyProdCode" value="${copyProdCode}"/>
			<table class="tableStyle tab-hei-30" width="50%" mode="list" formMode="line">
				<tr id="tr_t">
				</tr>
			</table>
			<div id="panel1" panelTitle="产品基本参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/baseInfo.htm">
   				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel2" panelTitle="项目分类及投资比例" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/prjInfo.htm">
   				<iframe id="iframepage2" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel3" panelTitle="产品销售渠道" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/prodChannel.htm">
   				<iframe id="iframepage3" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel4" panelTitle="产品销售区域" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/issueAreaInfo.htm">
   				<iframe id="iframepage4" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel5" panelTitle="申报登记参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/registerInfo.htm">
				<iframe id="iframepage5" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel6" panelTitle="固定收益类参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/fixProfitRateInfo.htm">
   				<iframe id="iframepage6" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel7" panelTitle="固定期限收益类组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/fixPeriodProfitRateInfo.htm">
   				<iframe id="iframepage7" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel8" panelTitle="规模期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/scaleProfitRateInfo.htm">
   				<iframe id="iframepage8" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel9" panelTitle="灵活期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/felPeriodSellRateDTOInfo.htm">
   				<iframe name="iframepage9"  id="iframepage9" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel10" panelTitle="固定销售费率参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/fixSellFeeRateInfo.htm">
   				<iframe id="iframepage10" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel11" panelTitle="认购参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/buyInfo.htm">
   				<iframe id="iframepage11" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel12" panelTitle="分红参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/devidentInfo.htm">
   				<iframe id="iframepage12" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel13" panelTitle="净值参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/netvalueInfo.htm">
   				<iframe id="iframepage13" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel14" panelTitle="积分参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/pointInfo.htm">
   				<iframe id="iframepage14" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel15" panelTitle="行内属性参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/custPropInfo.htm">
   				<iframe id="iframepage15" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel16" panelTitle="开放式产品存续期参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/durationInfo.htm">
   				<iframe id="iframepage16" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel17" panelTitle="统计口径参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/webProdStatisticsPara.htm">
   				<iframe id="iframepage17" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel18" panelTitle="预约购买参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/reservationBuyInfo.htm">
   				<iframe id="iframepage18" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel19" panelTitle="赎回参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/redeemInfo.htm">
   				<iframe id="iframepage19" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel21" panelTitle="业绩比较基准参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdDesignCont/performanceBenchmark.htm">
   				<iframe id="iframepage21" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel22" panelTitle="审批参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfResearchContProd/designAppUser.htm">
   				<iframe id="iframepage22" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
	   	</form>
	</body>
</html>