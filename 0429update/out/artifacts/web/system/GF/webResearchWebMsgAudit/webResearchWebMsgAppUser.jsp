<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>产品预研</title> 
		<script type="text/javascript">
		function sub(formId,url){
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			if (valid) {
				top.Dialog.confirm("确定要保存吗?|操作提示", function() {
					$.post(url, $("#"+formId).serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, function() {
								top.window.location.reload(true);
								top.Dialog.close();
							});
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			}else {
				top.Dialog.alert("验证未通过！");
			}
		}
		//当不同意的时候，不能选择审批人信息
		function changeAssignee(){
			var isAgree = $("#isAgree").val();
			if(isAgree == '0'){//不同意
				$("#repUserCode").removeAttr("class");
				$("#repUserCode").attr("disabled","disabled");
			}else if(isAgree == '1'){//同意
				$("#repUserCode").removeAttr("disabled");
			}
			$("#repUserCode").render();
		}
		
		function addComment(){
			var approveModel = $("#approveModel").attr("relText");
			$("#comment").val(approveModel);
			$("#comment").render();
		}
		function cel() {
			top.Dialog.confirm("数据尚未保存，是否退出?|操作提示", function() {
				top.window.location.reload(true);
			});
		}
		</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="taskId" value="${taskId }"/>
			<input type="hidden" name="lastUserType" value="${lastUserType }"/>
			<input type="hidden" name="prodName" value="${prodName }"/>
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode }"/>
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line"  fixedCellHeight="true">
				<tr>
					<td width="38%">操作员代码：</td>
					<td>
						${sessionScope.sessionUser.opercode} 
						<input type="hidden" name="appUser" value="${sessionScope.sessionUser.opercode}" />
					</td>
				</tr>
				<tr>
					<td width="38%">操作员姓名：</td>
					<td>${sessionScope.sessionUser.opername} <input
						type="hidden" name="appOperName"
						value="${sessionScope.sessionUser.opername}" /></td>
				</tr>
				<tr>
					<td>所属机构代码：</td>
					<td>${sessionScope.sessionUser.organcode} <input
						type="hidden" name="appOrganCode"
						value="${sessionScope.sessionUser.organcode}" /></td>
				</tr>
				<tr>
					<td>所属机构名称：</td>
					<td>${sessionScope.sessionOrgan.organname} <input
						type="hidden" name="appOrganName"
						value="${sessionScope.sessionOrgan.organname}" /></td>
				</tr>
				<tr>
					<td>操作员角色：</td>
					<td>${sessionScope.sessionUserRole} <input type="hidden"
						name="appRoleName" value="${sessionScope.sessionUserRole}" /></td>
				</tr>
				<tr>
					<td>是否同意：</td>
					<td>
						<dic:select onChange="changeAssignee();" dicType="IS_YES" id="isAgree" name="isAgree" tgClass="validate[required]"></dic:select>
						<span class="star">*</span>
					</td>
				</tr>
				<c:if test="${lastUserType!=true }">
					<tr>
						<td>复核人员：</td>
						<td><select selWidth="127" 
							id="repUserCode" name="assignee" prompt="--请选择--" class="validate[required]"
							url="<%=path%>/fdOper/getAppUserListByRole.htm?funCode=GF-05-02-01&roleCode=${roleCode }">
						</select> <span class="star">*</span> 
						<input type="hidden" name="roleCode" value="${roleCode }"/>
						<input type="hidden" name="repUserName" id="repUserName"/></td>
					</tr>
					<tr>
						<td>所属机构代码：</td>
						<td><span id="repUserOrganCode1"></span> <input type="hidden"
							id="repUserOrganCode" name="repUserOrganCode"/></td>
					</tr>
					<tr>
						<td>所属机构名称：</td>
						<td><span id="repUserOrganName1"></span> <input type="hidden"
							id="repUserOrganName" name="repUserOrganName"/></td>
					</tr>
					<tr>
						<td>复核人员角色：</td>
						<td><span id="repRoleName1"></span> <input type="hidden"
							id="repRoleName" name="repRoleName"/></td>
					</tr>
				</c:if>
				<tr>
					<td>审批意见模板：</td>
					<td>
						<select onchange="addComment();" selWidth="127" id="approveModel" name="approveModel" prompt="--请选择--"
									url="<%=path%>/webApproveModel/getWebApproveModel.htm">
						</select>
					</td>
				</tr>
				<tr>
					<td>批注：</td>
					<td>
						<textarea id="comment" rows="50" cols="3" name="comment"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return sub('form1','<%=path%>/webResearchAudit/audit.htm')" class="saveButton"/>
							<button type="button" onclick="return cel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>