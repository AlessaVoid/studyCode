<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>产品预研</title> 
		<script type="text/javascript" src="<%=path%>/system/layout/js/webResearch/webResearchSkip.js"></script>
		<script type="text/javascript">
		$(function(){
			//给div绑定事件，动态加载url
			bindUrl();
			//参数类型下拉框
			changeParam();
			//校验当前时间是否可以进行产品预研
			checkPower();
			//动态控制iframe
			changeIframe();
		});
		//校验当前时间是否可以进行产品预研
		function checkPower(){
			$.post("<%=path%>/webResearchStartProcess/checkPower.htm?type=reSubmit&prodCode="+$("#prodCode").val(), function(result) {
				if (result.success == "true" || result.success == true) {
					$("#prodCode").val(result.msg);
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		}
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
		//动态控制iframe的显示
		function changeIframe(){
			var index = 1;
			for(var i=2;i<=13;i++){
				$("#panel"+i).hide();
				$("#panel"+i).render();
			}
			$("#panel"+index).box2Open();
			changeParaType(index);
		}
		</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode }"/>
			<input type="hidden" name="prodName" id="prodName"/>
			<input type="hidden" name="brandCode" id="brandCode"/>
			<input type="hidden" name="profitHandleFlag" id="profitHandleFlag" value="${entity.profitHandleFlag }"/>
			<input type="hidden" name="sellFeeType" id="sellFeeType" value="${entity.sellFeeType }"/>
			<input type="hidden" name="oldProfitHandleFlag" id="oldProfitHandleFlag"/>
			<input type="hidden" name="oldSellFeeType" id="oldSellFeeType"/>
			<input type="hidden" name="index" id="index" value="1"/>
			<input type="hidden" id="prodOperModel" title="产品运作模式"/>
			<input type="hidden" name="buyIndex" id="buyIndex"/>
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td align="right" width="16%">产品代码：</td>
					<td>${prodCode }</td>
					<td align="right" width="16%">产品名称：</td>
					<td>${entity.prodName }</td>
					<td align="right" width="16%">参数类型：</td>
					<td width="16%">
						<select id="paraType">
							<option value="">--请选择--</option>
						</select>
					</td>
				</tr>
			</table>
			<div id="panel1" panelTitle="产品预研基本参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/baseInfo.htm?type=edit">
				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel2" panelTitle="项目分类及投资比例" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/prjInfo.htm?type=edit">
   				<iframe id="iframepage2" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel3" panelTitle="产品销售渠道" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/prodChannel.htm?type=edit">
   				<iframe id="iframepage3" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel4" panelTitle="产品销售区域" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/issueAreaInfo.htm?type=edit">
   				<iframe id="iframepage4" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel5" panelTitle="申报登记参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/registerInfo.htm?type=edit">
				<iframe id="iframepage5" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel6" panelTitle="固定收益类参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/fixProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage6" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel7" panelTitle="固定期限收益类组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/fixPeriodProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage7" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel8" panelTitle="规模期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/scaleProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage8" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel9" panelTitle="灵活期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/felPeriodSellRateDTOInfo.htm?type=edit">
   				<iframe id="iframepage9" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel10" panelTitle="固定销售费率参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/fixSellFeeRateInfo.htm?type=edit">
   				<iframe id="iframepage10" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel11" panelTitle="认购参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/buyInfo.htm?type=edit">
   				<iframe id="iframepage11" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel12" panelTitle="业绩比较基准参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/performanceBenchmarkInfo.htm?type=edit">
   				<iframe id="iframepage12" width="100%" height="80%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel13" panelTitle="审批参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchStartProcess/appUser.htm?type=edit">
   				<iframe id="iframepage13" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
	   	</form>
	</body>
</html>