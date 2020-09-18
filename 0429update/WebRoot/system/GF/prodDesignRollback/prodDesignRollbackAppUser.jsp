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
								top.frmright.window.location.reload(true);
								parent.close();
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
		</script>
	</head>
	<body>
		<form id="form1">
			<input type="hidden" name="taskId" value="${taskId }"/>
			<input type="hidden" name="isAgree" value="1"/>
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode }"/>
			<input type="hidden" name="prodName" id="prodName" value="${prodName }"/>
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
					<td>复核人员：</td>
					<td><select selWidth="127" 
						id="repUserCode" name="assignee" prompt="--请选择--" class="validate[required]"
						url="<%=path%>/fdOper/getAppUserListByRole.htm?funCode=GF-06-02-01&roleCode=${roleCode }">
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
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return sub('form1','<%=path%>/designRollback/audit.htm')" class="saveButton"/>
							<button type="button" onclick="return cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>