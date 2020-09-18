<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>机构专户产品设计</title> 
		<script type="text/javascript">
		$(function(){
			//动态控制iframe的显示
		    changeIframe();
			//给div绑定事件，动态加载url
			bindUrl();
			//参数类型下拉框
			changeParam();
		});
		//给div绑定事件，动态加载url
		function bindUrl(){
			$("div[id^='panel']").bind("stateChange",function(e,state){
				if(state == "show"){
					var panelId = $(this).attr("id");
					var index = panelId.substring(5);
					var url = $(this).attr("panelUrl") + "&prodCode=" + $("#prodCode").val() 
					    + "&prodName=" + $("#prodName").val() 
						+ "&taskId="+${taskId}+"&lastUserType="+$("#lastUserType").val();
						$("#iframepage" + index).height($(top.document.body).height());
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
			var profitHandleFlag = $("#profitHandleFlag").val();
			var sellFeeType = $("#sellFeeType").val();
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
				if(sellFeeType == '1'){
					index = '10';
				}else if(sellFeeType == '2'){
					index = '11';
				}
				for(var i=10;i<=11;i++){
					if(i != index){
						$("#panel"+i).hide();
						$("#panel"+i).render();
					}
				}
			}
			var prodOperModel = $("#prodOperModel").val();
			var isCustProp = $("#isCustProp").val();
			var isPoint = $("#isPoint").val();
			if(prodOperModel == '01' || prodOperModel == '03'){//01-03  净值显示
				$("#panel14").show();
				$("#panel14").render();
			}else{
				$("#panel14").hide();
				$("#panel14").render();
			}
			if(isPoint == '1'){
				$("#panel15").show();
				$("#panel15").render();
			}else{
				$("#panel15").hide();
				$("#panel15").render();
			}
			if(isPoint == '1'){
				$("#panel15").show();
				$("#panel15").render();
			}else{
				$("#panel15").hide();
				$("#panel15").render();
			}
			if(isCustProp == '1'){
				$("#panel16").show();
				$("#panel16").render();
			}else{
				$("#panel16").hide();
				$("#panel16").render();
			}
			if(prodOperModel == '03' || prodOperModel == '04'){
				$("#panel17").show();
				$("#panel17").render();
				$("#panel20").show();
				$("#panel20").render();
			}else{
				$("#panel17").hide();
				$("#panel17").render();
				$("#panel20").hide();
				$("#panel20").render();
			}
			
		}
		</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="msgNo" value="${msgNo }"/>
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode }"/>
			<input type="hidden" id="taskId" name="taskId" value="${taskId }"/>
			<input type="hidden" id="lastUserType" name="lastUserType" value="${lastUserType }"/>
			<input type="hidden" name="profitHandleFlag" id="profitHandleFlag" value="${entity.profitHandleFlag }"/>
			<input type="hidden" name="sellFeeType" id="sellFeeType" value="${entity.sellFeeType }"/>
			<input type="hidden" name="taskId" value="${taskId }"/>
			<input type="hidden" id="prodOperModel" name="prodOperModel" value="${entity.prodOperModel }"/>
			<input type="hidden" id="isCustProp" name="isCustProp" value="${entity.isCustProp }"/>
			<input type="hidden" id="isDuration" name="isDuration" value="${entity.isDuration }"/>
			<input type="hidden" id="isPoint" name="isPoint" value="${entity.isPoint }"/>
			<input type="hidden" id="isOrderAutoBuy" name="isOrderAutoBuy" value="${entity.isOrderAutoBuy }"/>
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="15%">产品编号：</td>
				<td width="15%">
					${prodCode }
					<input type="hidden" name="prodCode" value="${prodCode }"/>
				</td>
				<td align="right" width="15%">产品名称：</td>
				<td width="15%">
					${entity.prodName }
					<input type="hidden"id="prodName" name="prodName" value="${entity.prodName }"/>
				</td>
				<td align="right" width="15%">参数类型：</td>
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
						<option value="panel12">认购参数</option>
						<option value="panel13">分红参数</option>
						<c:if test="${entity.prodOperModel == '01' || entity.prodOperModel == '03' }">
							<option value="panel14">净值参数</option>
						</c:if>
						<c:if test="${entity.isPoint=='1'}">
							<option value="panel15">积分参数</option>
						</c:if>
						<c:if test="${entity.isCustProp=='1'}">
							<option value="panel16">行内属性参数</option>
						</c:if>
						<c:if test="${entity.prodOperModel == '03' || entity.prodOperModel == '04'}">
							<option value="panel17">开放式产品存续期参数</option>
						</c:if>
						<option value="panel18">统计口径参数</option>
							<option value="panel19">预约购买参数</option>
						<c:if test="${entity.prodOperModel == '03' || entity.prodOperModel == '04'}">
							<option value="panel20">赎回参数</option>
						</c:if>
						<option value="panel22">审批参数</option>
						<c:if test="${!empty comments }">
							<option value="panel23">批注</option>
						</c:if>
					</select>
				</td>
			</tr>
			</table>
			<div id="panel1" panelTitle="产品基本参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/baseInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel2" panelTitle="项目分类及投资比例" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/prjInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage2" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel3" panelTitle="产品销售渠道" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/prodChannelAuditUI.htm?type=audit">
   				<iframe id="iframepage3" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel4" panelTitle="产品销售区域" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/issueAreaInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage4" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel5" panelTitle="申报登记参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/registerInfoAuditUI.htm?type=audit">
				<iframe id="iframepage5" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel6" panelTitle="固定收益类参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/fixProfitRateInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage6" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel7" panelTitle="固定期限收益类组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/fixPeriodProfitRateInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage7" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel8" panelTitle="规模期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/scaleProfitRateInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage8" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel9" panelTitle="灵活期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/felPeriodProfitRateInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage9" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel10" panelTitle="固定销售费率参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/fixSellFeeRateInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage10" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel12" panelTitle="认购参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/buyInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage12" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel13" panelTitle="分红参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/devidentInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage13" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel14" panelTitle="净值参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/netvalueInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage14" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel15" panelTitle="积分参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/pointInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage15" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel16" panelTitle="行内属性参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/custPropInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage16" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel17" panelTitle="开放式产品存续期参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/durationInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage17" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel18" panelTitle="统计口径参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/webProdStatisticsParaAuditUI.htm?type=audit">
   				<iframe id="iframepage18" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel19" panelTitle="预约购买参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/reservationBuyInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage19" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel20" panelTitle="赎回参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/redeemInfoAuditUI.htm?type=audit">
   				<iframe id="iframepage20" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel22" panelTitle="审批参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/designAudit/appUser.htm?type=audit">
   				<iframe id="iframepage22" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<input type="hidden" id="comments" name="comments" value="${fn:length(comments) }"/>
   			<c:if test="${!empty comments }">
   				<div id="panel23" panelTitle="批注" class="box2_custom" boxType="box2" startState="close">
	  				<table class="tableStyle tab-hei-30" width="80%" mode="list">
						<tr>
							<td width="30%" align="left">
								审批用户 
							</td>
							<td width="30%" align="left">
								审批时间
							</td>
							<td width="40%" align="left">
								批注
							</td>
						</tr>
						<c:forEach items="${comments }" var="comment">
				<tr>
					<td>
						${comment.userId }
					</td>
					<td>
					<c:choose>
						<c:when test="${empty comment.time}">
						----
						</c:when>
						<c:otherwise>
							<fm:formatDate value="${comment.time }" pattern="yyyyMMdd HH:mm:ss"/>
						</c:otherwise>
					</c:choose>
					</td>
					<td>
						${comment.fullMessage }
					</td>
				</tr>
				</c:forEach>
					</table>
	   			</div>
	   		</c:if>
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" onclick="return doSubmit('form1','<%=path%>/designAudit/comfirmWebMsg.htm')" class="saveButton"/>
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>