<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>参数变更</title> 
		<script type="text/javascript">
		
		//给div绑定事件，动态加载url
		function bindUrl(){
			$("div[id^='panel']").bind("stateChange",function(e,state){
				if(state == "show"){
					var panelId = $(this).attr("id");
					var index = panelId.substring(5);
					var url = $(this).attr("panelUrl") + "?prodCode=" + $("#prodCode").val()+"&processInstanceId="+$("#processInstanceId").val();
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
	function initComplete(){
			//给div绑定事件，动态加载url
			bindUrl();
			//参数类型下拉框
			changeParam();
		}
	function confirm(){
		var msgNo=$("#msgNo").val();
		$.post("<%=path%>/eleChangeSubmited/eleComfirm.htm",{msgNo:msgNo},function(result){
			if (result.success == "true" || result.success == true) {
				top.Dialog.alert(result.msg,function(){
					top.window.location.reload(true);
					top.Dialog.close();
				});
			}
		},"json");
		
		
	}
		</script>
	</head>
	<body>
		<form id="form1">
		<input type="hidden" name="processInstanceId" value="${processInstanceId }" id="processInstanceId"/>
		<input type="hidden" name="msgNo" value="${msgNo }" id="msgNo"/>
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
				<td width="15%" id="pt">
				</td>
				<td colspan="2">
				<div align="center">
						<button type="button" onclick="confirm()" class="saveButton"/>
					</div>
				</td>
			</tr>
			</table>
   			<div id="panel1" panelTitle="基础参数" class="box2_custom" boxType="box2"  startState="close" panelUrl="<%=path%>/eleChangeAudit/baseInfo.htm">
   				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
			<div id="panel5" panelTitle="固定收益类参数" class="box2_custom" boxType="box2"   startState="close" panelUrl="<%=path%>/eleChangeAudit/fixProfitRateInfo.htm">
   				<iframe id="iframepage5" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel6" panelTitle="固定期限收益类组合参数" class="box2_custom" boxType="box2"   startState="close" panelUrl="<%=path%>/eleChangeAudit/fixPeriodProfitRateInfo.htm">
   				<iframe id="iframepage6" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel7" panelTitle="规模期限收益组合参数" class="box2_custom" boxType="box2"    startState="close" panelUrl="<%=path%>/eleChangeAudit/scaleProfitRateInfo.htm">
   				<iframe id="iframepage7" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel8" panelTitle="灵活期限收益组合参数" class="box2_custom" boxType="box2"   startState="close" panelUrl="<%=path%>/eleChangeAudit/felPeriodProfitRateInfo.htm">
   				<iframe id="iframepage8" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel9" panelTitle="分红参数" class="box2_custom" boxType="box2"   startState="close" panelUrl="<%=path%>/eleChangeAudit/devidedInfo.htm">
   				<iframe id="iframepage9" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel10" panelTitle="净值参数" class="box2_custom" boxType="box2"   startState="close" panelUrl="<%=path%>/eleChangeAudit/netvalueInfo.htm">
   				<iframe id="iframepage10" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel14" panelTitle="开放式产品存续期参数" class="box2_custom" boxType="box2"   startState="close" panelUrl="<%=path%>/eleChangeAudit/durationInfo.htm">
   				<iframe id="iframepage14" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel15" panelTitle="固定销售费率参数" class="box2_custom" boxType="box2"   startState="close" panelUrl="<%=path%>/eleChangeAudit/fixSellFeeRateInfo.htm">
   				<iframe id="iframepage15" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel16" panelTitle="统计口径参数" class="box2_custom" boxType="box2" startState="close" panelUrl="<%=path%>/eleChangeAudit/statisticsInfo.htm">
   				<iframe id="iframepage16" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel21" panelTitle="期限机构组合销售费率参数" class="box2_custom" boxType="box2"    startState="close" panelUrl="<%=path%>/eleChangeAudit/felSellFeeRateInfo.htm">
   				<iframe id="iframepage21" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel22" panelTitle="批注" class="box2_custom" boxType="box2"   startState="open">
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
	   	</form>
	   	<script type="text/javascript">
	  //动态获取修改选项卡
		loadPanel();
		function loadPanel(){
			var data=eval(${optionsList});
		        var $selbox = $('<select  prompt="--请选择--" id="paraType"></select>');
		        //赋给data属性
		        $selbox.data("data",${optionsList});
		        //将下拉框加到按钮的后面
		        $("#pt").append($selbox);   
		        //渲染下拉框 
		        $selbox.render();  
		       	//隐藏所有子iframe
		        $("[id^=panel]").hide();
		        for (var int = 0; int < data.list.length; int++) {
		        	var _panle=data.list[int].value;
		       	 //显示修改项所属iframe
		        	$("#"+_panle).show();
				}
		};
</script>
	</body>
</html>