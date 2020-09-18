<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<script type="text/javascript">
		function sub(formId,url){
            var valid = $("#"+formId).validationEngine({
                returnIsValid : true
            });
            if(valid){
                if($("#cancelFlag").val()==""||$("#cancelFlag").val()==null){
                    top.Dialog.alert("验证未通过");
                    return;
                }
                top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
                    $.post(url, $("#"+formId).serialize(), function(result) {
                        if(result.success=='true'||result.success==true){
                            top.Dialog.alert(result.msg, function() {
                                top.frmright.window.location.reload(true);
                                top.Dialog.close();
                            });
                        }else{
                            top.Dialog.alert(result.msg);
                        }
                    }, "json");
                });
			}
		}
		</script>
	</head>
	<body>
		<form id="form1">
		<div class="basicTabModern">
			<div name="购买期最后一天允许撤单维护" style="width:100%;height:300px;">
	   			<table class="tableStyle" width="80%" mode="list" formMode="line">
	   				<tr>
	   					<td width="12%" align="right">
							产品代码：
						</td>
						<td width="12%">
							${entity.prodCode}
							<input type="hidden" name="prodCode" value="${entity.prodCode}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							产品名称：
						</td>
						<td width="12%">
							${entity.prodName}
							<input type="hidden" name="prodName" value="${entity.prodName}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							产品成立日：
						</td>
						<td width="12%">
							${entity.prodBeginDate}
							<input type="hidden" name="prodBeginDate" value="${entity.prodBeginDate}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							产品到期日：
						</td>
						<td width="12%">
							${entity.prodEndDate}
							<input type="hidden" name="prodEndDate" value="${entity.prodEndDate}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							购买期最后一天允许撤单：
						</td>
						<td width="12%">
							<dic:select name="cancelFlag" id="cancelFlag" dicType="IS_YES" tgClass="validate[required]" dicNo="${entity.cancelFlag}"></dic:select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button type="button" class="firstButton" />
							<button type="button" class="downButton" />
						</td>
					</tr>
	   			</table>
	   			</div>
	   			<div name="复核信息" style="width:100%;height:80px;">
					<div style="width: 98%">
						<input type="hidden" name="operDescribe" value="购买期最后一天允许撤单维护" /> 
						<input type="hidden" name="appType" value="0" />
						<table class="tableStyle" width="97%" mode="list" formMode="line">
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
									id="repUserCode" name="repUserCode" prompt="--请选择--" class="validate[required]"
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=TRADE-13-03">
								</select> <span class="star">*</span> 
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
								<td>备注说明：</td>
								<td><textarea class="textarea"
										style="width: 80%; height: 80px;" name="appRemark"></textarea>
								</td>
							</tr>
							<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return sub('form1','<%=path%>/gfProdPurLastCancel/approval.htm')" class="saveButton"/>
									<button type="button" onclick="return cal()" class="cancelButton" />	
								</div>
							</td>
						</tr>
						</table>
					</div>
				</div>
			</div>
	   	</form>
	</body>
</html>