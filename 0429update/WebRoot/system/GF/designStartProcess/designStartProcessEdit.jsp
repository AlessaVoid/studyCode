<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>机构专户产品设计</title> 
		<script type="text/javascript" src="<%=path%>/system/layout/js/prodDesign/prodDesignSkip.js"></script>
		<script type="text/javascript">
		$(function(){
			//给div绑定事件，动态加载url
			bindUrl();
			//参数类型下拉框
			changeParam();
			//校验当前时间是否可以进行产品预研
			checkPower();
			//动态控制iframe的显示
			changeIframe();
		});
		//给div绑定事件，动态加载url
		function bindUrl(){
			$("div[id^='panel']").bind("stateChange",function(e,state){
				if(state == "show"){
					var panelId = $(this).attr("id");
					var index = panelId.substring(5);
					var oldIndex = $("#index").val();
					var buyIndex = $("#buyIndex").val();
					var prodType = $("#prodType").val();
					var url = $(this).attr("panelUrl") + "&prodCode=" + $("#prodCode").val()
				        +"&custType=" + $("#custType").val()
						+"&index="+index+"&oldIndex="+oldIndex+"&buyIndex="+buyIndex+"&prodType="+prodType;
					if($("#brandCode").val() != "" && index==1){
						url = url +"&brandCode="+$("#brandCode").val();
					}
					if($("#prodRiskLevel").val() != "" && index==5){
						url = url +"&prodRiskLevel="+$("#prodRiskLevel").val();
					}	
					if($("#prodOperModel").val() != "" && index==5){
						url = url +"&prodOperModel="+$("#prodOperModel").val();
					}
					$("#iframepage" + index).height($(top.document.body).height());
					$("#iframepage" + index).attr("src", url);
				}
			});
		}
		//校验当前时间是否可以进行产品预研
		function checkPower(){
			$.post("<%=path%>/designStartProcess/checkPower.htm?type=reSubmit&prodCode="+$("#prodCode").val(), function(result) {
				if (result.success == "true" || result.success == true) {
					$("#prodCode").val(result.msg);
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		}
		//基本参数维护后，将一些控制参数放到父页面里，用来控制其他参数子页面是否能维护
		function setBaseInfo(bean){
			$.each(bean,function(i,e){
				$("#"+i).val(e);
			});
		}
		function changeIframe(){
			var index = 1;
			for(var i=2;i<=22;i++){
				$("#panel"+i).hide();
				$("#panel"+i).render();
			}
			$("#panel"+index).box2Open();
		}
		</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" id="prodType" name="prodType" value="${entity.custType }" title="客户类型"/>
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode }"/>
			<input type="hidden" name="custType" id="custType" value="${entity.custType }"/>
			<input type="hidden" name="taskId" id="taskId" value="${taskId }"/>
			<input type="hidden" name="brandCode" id="brandCode"/>
			<input type="hidden" name="profitHandleFlag" id="profitHandleFlag" value="${entity.profitHandleFlag }" title="收益计算标识"/>
			<input type="hidden" name="sellFeeType" id="sellFeeType" value="${entity.sellFeeType }" title="销售手续费类型"/>
			<input type="hidden" name="oldProfitHandleFlag" id="oldProfitHandleFlag"  value="${entity.profitHandleFlag }"/>
			<input type="hidden" name="oldSellFeeType" id="oldSellFeeType" value="${entity.sellFeeType }"/>
			<input type="hidden" name="index" id="index" value="1"/>
			<input type="hidden" name="buyIndex" id="buyIndex"/>
			<input type="hidden" id="prodOperModel" title="产品运作模式"  value="${entity.prodOperModel }"/>
			<input type="hidden" id="isPoint" title="是否积分产品"  value="${entity.isPoint }"/>
			<input type="hidden" id="isCustProp" title="是否控制客户行内属性"  value="${entity.isCustProp }"/>
			<input type="hidden" id="isDuration" title="是否有存续周期"  value="${entity.isDuration }"/>
			<input type="hidden" id="isWhiteList" title="是否设置黑白名单"  value="${entity.isWhiteList }"/>
			<input type="hidden" id="isOrderAutoBuy" title="是否预约自动认购"  value="${entity.isOrderAutoBuy }"/>
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="15%">产品代码：</td>
				<td width="15%">${prodCode }</td>
				<td align="right" width="15%">产品名称：</td>
				<td width="15%">
					${entity.prodName }
				</td>
				<td align="right" width="15%">参数类型：</td>
				<td width="15%">
					<select id="paraType">
						<option selected="selected">--请选择--</option>
					</select>
				</td>
			</tr>
			</table>
			<div id="panel1" panelTitle="产品基本参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/baseInfo.htm?type=edit">
   				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel2" panelTitle="项目分类及投资比例" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/prjInfo.htm?type=edit">
   				<iframe id="iframepage2" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel3" panelTitle="产品销售渠道" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/prodChannel.htm?type=edit">
   				<iframe id="iframepage3" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel4" panelTitle="产品销售区域" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/issueAreaInfo.htm?type=edit">
   				<iframe id="iframepage4" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel5" panelTitle="申报登记参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/registerInfo.htm?type=edit">
				<iframe id="iframepage5" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel6" panelTitle="固定收益类参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/fixProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage6" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel7" panelTitle="固定期限收益类组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/fixPeriodProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage7" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel8" panelTitle="规模期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/scaleProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage8" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   				<div id="panel9" panelTitle="灵活期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/felPeriodSellRateDTOInfo.htm?type=edit">
   				<iframe name="iframepage9"  id="iframepage9" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel10" panelTitle="固定销售费率参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/fixSellFeeRateInfo.htm?type=edit">
   				<iframe id="iframepage10" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel11" panelTitle="认购参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/buyInfo.htm?type=edit">
   				<iframe id="iframepage11" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel12" panelTitle="分红参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/devidentInfo.htm?type=edit">
   				<iframe id="iframepage12" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel13" panelTitle="净值参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/netvalueInfo.htm?type=edit">
   				<iframe id="iframepage13" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel14" panelTitle="积分参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/pointInfo.htm?type=edit">
   				<iframe id="iframepage14" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel15" panelTitle="行内属性参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/custPropInfo.htm?type=edit">
   				<iframe id="iframepage15" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel16" panelTitle="开放式产品存续期参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/durationInfo.htm?type=edit">
   				<iframe id="iframepage16" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel17" panelTitle="统计口径参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/webProdStatisticsPara.htm?type=edit">
   				<iframe id="iframepage17" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel18" panelTitle="预约购买参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/reservationBuyInfo.htm?type=edit">
   				<iframe id="iframepage18" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel19" panelTitle="赎回参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/redeemInfo.htm?type=edit">
   				<iframe id="iframepage19" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel21" panelTitle="业绩比较基准参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/performanceBenchmarkInfo.htm?type=edit">
   				<iframe id="iframepage21" width="100%" height="80%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel22" panelTitle="审批参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designStartProcess/appUser.htm?type=edit">
   				<iframe id="iframepage22" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
	   	</form>
	</body>
</html>