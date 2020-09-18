<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>参数变更</title> 
		<script type="text/javascript">
		$(function(){
			//给div绑定事件，动态加载url
			bindUrl();
			//参数类型下拉框
			changeParam();
			changeIframe();
		});	
		//给div绑定事件，动态加载url
		function bindUrl(){
			$("div[id^='panel']").bind("stateChange",function(e,state){
				if(state == "show"){
					var panelId = $(this).attr("id");
					var index = panelId.substring(5);
					var url = $(this).attr("panelUrl") + "&prodCode=" + $("#prodCode").val();
					$("#iframepage" + index).height($(document.body).height());
					$("#iframepage" + index).attr("src", url);
				}
			});
		};
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
			var sellFeeType = "${entity.sellFeeType}";
			var prodOperModel = "${entity.prodOperModel}";
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
			if(sellFeeType != ""){
				if(sellFeeType != '1'){
					$("#panel10").hide();
					$("#panel10").render();
				}
			}
			if(prodOperModel=='02'||prodOperModel=='04'){
				$("#panel13").hide();
				$("#panel13").render();
			}
			if(isPoint!='1'){
				$("#panel14").hide();
				$("#panel14").render();
			}
			if(isCustProp!='1'){
				$("#panel15").hide();
				$("#panel15").render();
			}
			if(prodOperModel == '03' || prodOperModel == '04'){
				$("#panel16").show();
				$("#panel16").render();
				$("#panel19").show();
				$("#panel19").render();
			}else{
				$("#panel16").hide();
				$("#panel16").render();
				$("#panel19").hide();
				$("#panel19").render();
			}
		}
		</script>
	</head>
	<body>
		<form id="form1">
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td>产品编号：</td>
				<td>
				<input type="hidden" value="${prodCode}" name="prodCode" id="prodCode"/>
				${prodCode} 
				</td>
				<td>产品名称：</td>
				<td>
				<input type="hidden" value="${prodName}" id="prodName" name="prodName"/>
				${prodName}
				</td>
				<td align="right" width="10%">参数类型：</td>
				<td width="15%">
					<select id="paraType">
						<option selected="selected">--请选择--</option>
						<option value="panel1">产品基本参数</option>
						<option value="panel2">项目分类及投资比例</option>
						<option value="panel3">产品销售渠道</option>
						<option value="panel4">产品销售区域</option>
						<option value="panel5">申报登记参数</option>
						<c:if test="${entity.profitHandleFlag=='1' }">
							<option value="panel6">固定收益类参数</option>
						</c:if>
						<c:if test="${entity.profitHandleFlag=='2' }">
							<option value="panel7">固定期限收益类组合参数</option>
						</c:if>
						<c:if test="${entity.profitHandleFlag=='4' }">
							<option value="panel8">规模期限收益组合参数</option>
						</c:if>
						<c:if test="${entity.profitHandleFlag=='3' }">
							<option value="panel9">灵活期限收益组合参数</option>
						</c:if>
						<c:if test="${entity.sellFeeType=='1' }">
							<option value="panel10">固定销售费率参数</option>
						</c:if>	
						<option value="panel11">认购参数</option>
						<option value="panel12">分红参数</option>
						<c:if test="${entity.prodOperModel == '01' || entity.prodOperModel == '03' }">
							<option value="panel13">净值参数</option>
						</c:if>
						<c:if test="${entity.isPoint=='1'}">
							<option value="panel14">积分参数</option>
						</c:if>
						<c:if test="${entity.isCustProp == '1'}">
							<option value="panel15">行内属性参数</option>
						</c:if>
						<c:if test="${entity.prodOperModel == '03' || entity.prodOperModel == '04'}">
							<option value="panel16">开放式产品存续期参数</option>
						</c:if>
						<option value="panel17">统计口径参数</option>
						<option value="panel18">预约购买参数</option>
						<c:if test="${entity.prodOperModel == '03' || entity.prodOperModel == '04'}">
							<option value="panel19">赎回参数</option>
						</c:if>
					</select>
				</td>
			</tr>
			</table>
   			<div id="panel1" panelTitle="产品基本参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/baseInfo.htm?type=info">
   				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel2" panelTitle="项目分类及投资比例" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/prjInfoInfo.htm?type=info">
   				<iframe id="iframepage2" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel3" panelTitle="发售渠道参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/channelInfo.htm?type=info">
   				<iframe id="iframepage3" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel4" panelTitle="发售区域参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/issueAreaInfo.htm?type=info">
   				<iframe id="iframepage4" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel5" panelTitle="申报登记参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/registerInfo.htm?type=info">
				<iframe id="iframepage5" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel6" panelTitle="固定收益类参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/fixProfitRateInfo.htm?type=info">
   				<iframe id="iframepage6" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel7" panelTitle="固定期限收益类组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/fixPeriodProfitRateInfo.htm?type=info">
   				<iframe id="iframepage7" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel8" panelTitle="规模期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/scaleProfitRateInfo.htm?type=info">
   				<iframe id="iframepage8" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel9" panelTitle="灵活期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/felPeriodSellRateDTOInfo.htm?type=info">
   				<iframe id="iframepage9" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel10" panelTitle="固定销售费率参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/fixSellFeeRateInfo.htm?type=info">
   				<iframe id="iframepage10" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel11" panelTitle="认购参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/buyInfo.htm?type=info">
   				<iframe id="iframepage11" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel12" panelTitle="分红参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/devidentInfo.htm?type=info">
   				<iframe id="iframepage12" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel13" panelTitle="净值参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/netvalueInfo.htm?type=info">
   				<iframe id="iframepage13" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel14" panelTitle="积分参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/pointInfo.htm?type=info">
   				<iframe id="iframepage14" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel15" panelTitle="行内属性参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/custPropInfo.htm?type=info">
   				<iframe id="iframepage15" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel16" panelTitle="开放式产品存续期参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/durationInfo.htm?type=info">
   				<iframe id="iframepage16" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel17" panelTitle="统计口径参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/statisticsInfo.htm?type=info">
   				<iframe id="iframepage17" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel18" panelTitle="预约购买参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/reservationBuyInfo.htm?type=info">
   				<iframe id="iframepage18" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel19" panelTitle="赎回参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/gfProdEleChange/redeemInfo.htm?type=info">
   				<iframe id="iframepage19" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
	   	</form>
	</body>
</html>