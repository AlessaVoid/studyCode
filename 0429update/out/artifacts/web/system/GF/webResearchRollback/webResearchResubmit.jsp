<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>产品预研</title> 
		<!--表单异步提交start-->
	<script src="${path}/libs/js/form/form.js" type="text/javascript"></script>
	<!--表单异步提交end-->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="<%=path%>/libs/js/popup/drag.js"></script>
	<script type="text/javascript" src="<%=path%>/libs/js/popup/dialog.js"></script>
		<script type="text/javascript">
		$(function(){
			//动态控制iframe的显示
		    changeIframe();
			//给div绑定事件，动态加载url
			bindUrl();
			//校验当前时间是否可以进行产品预研
			checkPower();
			changeParam();
		});
		//校验当前时间是否可以进行产品预研
		function checkPower(){
			$.post("<%=path%>/webResearchRollback/checkPower.htm?type=reSubmit&prodCode="+$("#prodCode").val(), function(result) {
				if (result.success == "true" || result.success == true) {
					$("#prodCode").val(result.msg);
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		}
		function bindUrl(){
			$("div[id^='panel']").bind("stateChange",function(e,state){
				if(state == "show"){
					var panelId = $(this).attr("id");
					var index = panelId.substring(5);
					var url =$(this).attr("panelUrl");
					if(index=='panel12'){
						url=url+"&prodCode=" + $("#prodCode").val()+"&taskId=${taskId}";
					}
					var url = $(this).attr("panelUrl") + "&taskId=${taskId}&prodCode=" + $("#prodCode").val();
					$("#iframepage" + index).height($(top.document.body).height());
					$("#iframepage" + index).attr("src", url);
				}
			});
		}
		//基本参数维护后，将一些控制参数放到父页面里，用来控制其他参数子页面是否能维护
		function changeIframe(){
			var profitHandleFlag = "${entity.profitHandleFlag}";
			var sellFeeType = "${entity.sellFeeType}";
			var prodOperModel = "${entity.prodOperModel}";
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
		function turnPage(p1,p2){
			$("#" + p1).box2Close();
			$("#" + p2).box2Open();
		}
		function setBaseInfo(bean){
			$.each(bean,function(i,e){
				$("#"+i).val(e);
			});
			changeIframeCopy(bean);
			$(".prod").empty();
			$(".prod").remove();
			param(bean);
		}
		function param(entity){
			$("#tr").empty();
			var profitHandleFlag = entity.profitHandleFlag;
			var sellFeeType = entity.sellFeeType;
			var prodOperModel = entity.prodOperModel;
			var $option="<td align=\"right\" width=\"16%\" class=\"prod\">产品代码：</td>" +
						"<td class=\"prod\">"+entity.prodCode+"</td>" +
						"<td align=\"right\" class=\"prod\">产品名称：</td>" +
						"<td class=\"prod\">"+entity.prodName+"</td>"+
						"<td align=\"right\" width=\"16%\">参数类型：</td>"+
						"<td width=\"16%\">"+
						"<select id=\"paraType\">"+
						"<option selected=\"selected\">--请选择--</option>"+
						"<option value=\"panel1\">产品预研基本参数</option>" +
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
			$option+="<option value=\"panel11\">认购参数</option>";
			if(prodOperModel=='01' || prodOperModel=='03'){
				$option+="<option value=\"panel2\">业绩比较基准参数</option>";
			}
				$option+="<option value=\"panel13\">审批参数</option>" +
						 "</select>"+
						 "</td>";
				$("#tr").prepend($option);
				$("#paraType").render();
				changeParam();
		}
		function changeParam(){
			$("#paraType").change(function(){
				var value = $("#paraType option:selected").val();
				var panelIndex = value.substring(5);
				var index = $("#index").val();
				for(var i=1;i<=index;i++){
					if(i!=panelIndex){
						$("#panel"+i).hide();
						$("#panel"+i).render();
					}
				}
				$("#" + value).show();
				$("#" + value).render();
				$("#" + value).box2Open();
			});
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
		</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode}"/>
			<table class="tableStyle tab-hei-30" width="50%" mode="list" formMode="line">
				<tr id="tr">
					<td>产品代码：</td><td>${prodCode}</td>
					<td>产品名称：</td><td>${entity.prodName}</td>
					<td align="right" width="16%">参数类型：</td>
					<td width="16%">
						<select id="paraType">
						<option selected="selected">--请选择--</option>
						<option value="panel1">产品基本参数</option>
						<option value="panel2">项目分类及投资比例</option>
						<option value="panel3">产品销售渠道</option>
						<option value="panel4">产品发售区域</option>
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
						<c:if test="${entity.prodOperModel=='01' }">
						  <option value="panel12">业绩比较基准参数</option>
						</c:if>
						<c:if test="${entity.prodOperModel=='03' }">
						  <option value="panel12">业绩比较基准参数</option>
						</c:if>
						<option value="panel13">审批参数</option>
						<c:if test="${!empty comments }">
							<option value="panel14">批注</option>
						</c:if>
						</select>
					</td>
				</tr>
			</table>
			<div id="panel1" panelTitle="产品预研基本参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/baseInfo.htm?type=edit">
				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel2" panelTitle="项目分类及投资比例" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/prjInfo.htm?type=edit">
   				<iframe id="iframepage2" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel3" panelTitle="产品销售渠道" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/prodChannel.htm?type=edit">
   				<iframe id="iframepage3" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel4" panelTitle="产品销售区域" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/issueAreaInfo.htm?type=edit">
   				<iframe id="iframepage4" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel5" panelTitle="申报登记参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/registerInfo.htm?type=edit">
				<iframe id="iframepage5" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel6" panelTitle="固定收益类参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/fixProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage6" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel7" panelTitle="固定期限收益类组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/fixPeriodProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage7" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel8" panelTitle="规模期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/scaleProfitRateInfo.htm?type=edit">
   				<iframe id="iframepage8" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel9" panelTitle="灵活期限收益组合参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/felPeriodSellRateDTOInfo.htm?type=edit">
   				<iframe id="iframepage9" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel10" panelTitle="固定销售费率参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/fixSellFeeRateInfo.htm?type=edit">
   				<iframe id="iframepage10" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel11" panelTitle="认购参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/buyInfo.htm?type=edit">
   				<iframe id="iframepage11" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel12" panelTitle="业绩比较基准参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/performanceBenchmarkInfo.htm?type=edit">
   				<iframe id="iframepage12" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel13" panelTitle="审批参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/webResearchRollback/appUser.htm?type=edit">
   				<iframe id="iframepage13" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<input type="hidden" id="comments" name="comments" value="${fn:length(comments) }"/>
   			<c:if test="${!empty comments }">
   				<div id="panel14" panelTitle="批注" class="box2_custom" boxType="box2" startState="close">
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
	   	</form>
	</body>
</html>