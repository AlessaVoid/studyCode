<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
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
				//if(value == "panel2"){
					$("div[id^='panel']").each(function(){
						$(this).box2Close();
					});
					$("#" + value).box2Open();
				//}
			});
		}
	function initComplete(){
			//给div绑定事件，动态加载url
			bindUrl();
			//参数类型下拉框
			changeParam();
		}
	function chAssignee(isAgree){
		if(isAgree=='0'){
			$("#assignee").removeClass("validate[required]");
		}
		if(isAgree=='1'){
			$("#assignee").addClass("validate[required]");
		}
	}
		</script>
	</head>
	<body>
		<form id="form1">
		<input type="hidden" name="taskId" value="${taskId }" id="taskId"/>
		<input type="hidden" name="processInstanceId" value="${processInstanceId }" id="processInstanceId"/>
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
				<td rowspan="2">
					<div align="center">
						<button type="button" onclick="return doSubmit('form1','<%=path%>/eleChangeAudit/audit.htm')" class="saveButton"/>
					</div>
				</td>
			</tr>
			<tr>
				<td>审批意见模板：</td>
					<td>
						<select onchange="addComment();" selWidth="127" id="approveModel" name="approveModel" prompt="--请选择--"
									url="<%=path%>/webApproveModel/getWebApproveModel.htm">
						</select>
					<textarea id="comment" rows="50" cols="3" name="comment"></textarea>
					</td>
				<c:if test="${not empty roleCode}">
				<td>审批人员：</td>
				<td>
					<input type="hidden" name="roleCode" value="${roleCode}"/>
					<select id="assignee" name="assignee" prompt="--请选择--"
					url="<%=path%>/fdOper/getAppUserListByRole.htm?funCode=GF-02-02-01&roleCode=${roleCode}"></select>
					<span class="star">*</span>
				</td>
				</c:if>
				<td>是否同意：</td>
				<td>
					<dic:select dicType="IS_YES" name="isAgree" tgClass="validate[required]" id="isAgree" fn ="onchange='chAssignee(this.value)'"></dic:select>
					<span class="star">*</span>
				</td>
			</tr>
			</table>
   			<div id="panel1" panelTitle="基础参数" class="box2_custom" boxType="box2"  startState="close" panelUrl="<%=path%>/eleChangeAudit/baseInfo.htm">
   				<iframe id="iframepage1" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
   			</div>
   			<div id="panel2" panelTitle="项目分类及投资比例" class="box2_custom" boxType="box2"  startState="close" panelUrl="<%=path%>/eleChangeAudit/prjInfoUI.htm">
   				<iframe id="iframepage2" width="100%" height="100%" frameborder=0 allowTransparency="true" scrolling="auto"></iframe>
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
		function addComment(){
			var approveModel = $("#approveModel").attr("relText");
			$("#comment").val(approveModel);
			$("#comment").render();
		}
</script>
	</body>
</html>