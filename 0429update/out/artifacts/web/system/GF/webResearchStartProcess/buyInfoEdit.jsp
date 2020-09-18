<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>认购参数</title> 
		<script type="text/javascript">
		function initComplete(){
			var value = $("#prodtCharactType").val();
			if(value == '03'){
				$("#quotAffirmDate").removeAttr("disabled");
				$("#quotAffirmDate").render();
				$("#invCycleEndDate").removeAttr("disabled");
				$("#invCycleEndDate").render();
				$("#firstInvCycleEndDate").removeAttr("disabled");
				$("#firstInvCycleEndDate").render();
			}else{ //如果不是投资周期型产品，置为空
				$("#quotAffirmDate").attr("disabled","disabled");
				$("#quotAffirmDate").val("");
				$("#quotAffirmDate").render();
				$("#invCycleEndDate").attr("disabled","disabled");
				$("#invCycleEndDate").val("");
				$("#invCycleEndDate").render();
				$("#firstInvCycleEndDate").attr("disabled","disabled");
				$("#firstInvCycleEndDate").val("");
				$("#firstInvCycleEndDate").render();
			}
		}
		function sub(formId,url) {
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			if (valid) {
				var beginDate = $("#raisingBeginDate").val();
				var endDate = $("#raisingEndDate").val();
				var planIssueAmt = $("#planIssueAmt").val();
				if(planIssueAmt=="0.00" || planIssueAmt==undefined){
					top.Dialog.alert("计划募集金额必须大于0！");
					return false;
				}
				if(compareDate(beginDate, endDate)){
					top.Dialog.alert("募集起始日期["+beginDate+"]应小于等于</br>募集结束日期["+endDate+"]!");
				}else{
					if(beginDate == endDate){
						var beginTime = $("#raisingBeginTime").val();
						var endTime = $("#raisingEndTime").val();
						if(beginTime == ''){
							top.Dialog.alert("请输入募集开始时间!");
							return false;
						}else{
							if(endTime == ''){
								top.Dialog.alert("请输入募集结束时间!");
								return false;
							}
						}
						if(compareDate(beginTime, endTime)){
							top.Dialog.alert("募集起始时间["+beginTime+"]不能晚于</br>募集结束时间["+endTime+"]!");
						}else{
							subForm(url,formId);
						}
					}else{
						subForm(url,formId);
					}
				}
			} else {
				top.Dialog.alert("验证未通过！");
			}
		}
		function subForm(url,formId){
			top.Dialog.confirm("是否保存信息?", function() {
				$.post(url, $("#"+formId).serialize(), function(result) {
					if (result.success == "true" || result.success == true) {
						top.Dialog.alert(result.msg, function() {
							parent.setBaseInfo(result.bean);
							if($("#index").val() != $("#oldIndex").val()){
								parent.changeDiv($("#index").val(),$("#oldIndex").val());
							}else{
								parent.changeDiv($("#index").val(),$("#index").val());
							}
						});
					} else {
						top.Dialog.alert(result.msg);
					}
				}, "json");
			});
		}
		</script>
	</head>
	<body>
	<form id="form1">
		<input type="hidden" name="index" id="index" value="${index }"/>
		<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
		<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
		<input type="hidden" id="prodtCharactType" name="prodtCharactType" value="${webProdBaseInfo.prodtCharactType}"/>
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="19%">产品成立日期：</td>
				<td width="31%">
					${webProdBaseInfo.prodBeginDate }
				</td>
				<td align="right" width="19%">产品到期日期：</td>
				<td width="31%">
					${webProdBaseInfo.prodEndDate }
				</td>
			</tr>
			<tr>
				<td align="right" width="19%">募集起始日期（从）：</td>
				<td width="31%">
					<input type="text" id="raisingBeginDate" name="raisingBeginDate" class="date validate[required]" dateFmt="yyyyMMdd" maxlength="8" value="${entity.raisingBeginDate }"/>
					<span class="star">*</span>
				</td>
				<td align="right" width="19%">募集起始时间（从）：</td>
				<td width="31%">
					<c:choose>
						<c:when test="${empty entity.raisingBeginTime  }">
							<input type="text" id="raisingBeginTime" name="raisingBeginTime" class="date" dateFmt="HHmmss" maxlength="6" value="090000"/>
						</c:when>
						<c:otherwise>
							<input type="text" id="raisingBeginTime" name="raisingBeginTime" class="date" dateFmt="HHmmss" maxlength="6" value="${entity.raisingBeginTime }"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>募集结束日期（到）：</td>
				<td>
					<c:choose>
					<c:when test="${empty entity.raisingEndDate}">
					<input type="text" id="raisingEndDate" name="raisingEndDate" class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd" maxlength="8" value="${raisingEndDate }"/>
					</c:when>
					<c:otherwise>
					<input type="text" id="raisingEndDate" name="raisingEndDate" class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd" maxlength="8" value="${entity.raisingEndDate }"/>
					</c:otherwise>
					</c:choose>
					<span class="star">*</span>
				</td>
				<td>募集结束时间（到）：</td>
				<td>
					<c:choose>
						<c:when test="${empty entity.raisingEndTime  }">
								<input type="text" id="raisingEndTime" name="raisingEndTime" class="date" dateFmt="HHmmss" maxlength="6" value="170000"/>
						</c:when>
						<c:otherwise>
								<input type="text" id="raisingEndTime" name="raisingEndTime" class="date" dateFmt="HHmmss" maxlength="6" value="${entity.raisingEndTime }"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>扣款方式：</td>
				<td>
					<dic:select dicType="AMT_HANDLE_FLAG" name="chargeType" dicNo="${entity.chargeType }" tgClass="validate[required]"></dic:select>
					<span class="star">*</span>
				</td>
				<td>份额确认方式：</td>
				<td>
					<dic:select dicType="QUOT_AFFIRM_TYPE" name="quotAffirmType" dicNo="${entity.quotAffirmType }" tgClass="validate[required]"></dic:select>
					<span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td align="right" width="10%">计划募集金额（${webProdBaseInfo.currency }）：</td>
				<td width="15%">
							<input type="text" name="planIssueAmt" id ="planIssueAmt" class="money validate[require,length[0,20]]" value="${entity.planIssueAmt }" maxlength="20"/>
					<span class="star">*</span>
				</td>
				<td align="right" width="10%">份额确认日：</td>
				<td width="15%">
					<input type="text" id="quotAffirmDate" name="quotAffirmDate" class="date" dateFmt="yyyyMMdd" maxlength="8" value="${entity.quotAffirmDate }"/>
					<span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td align="right" width="10%">投资周期结束日：</td>
				<td width="15%">
					<input type="text" id="invCycleEndDate" name="invCycleEndDate" class="date" dateFmt="yyyyMMdd" maxlength="8" value="${entity.invCycleEndDate }"/>
					<span class="star">*</span>
				</td>
				<td align="right" width="10%">初始投资周期结束日：</td>
				<td width="15%">
					<input type="text" id="firstInvCycleEndDate" name="firstInvCycleEndDate" class="date" dateFmt="yyyyMMdd" maxlength="8" value="${entity.firstInvCycleEndDate }"/>
					<span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="center">
						<button type="button" onclick="return sub('form1','<%=path%>/webResearchStartProcess/updateBuyInfo.htm')" class="saveButton"/>
					</div>
				</td>
			</tr>
		</table>
	</form>
	</body>
</html>