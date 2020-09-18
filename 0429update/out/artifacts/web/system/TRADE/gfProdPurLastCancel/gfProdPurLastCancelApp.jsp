<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<script type="text/javascript">
		function sub(formId,url){
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
							${appDataMap.prodCode}
							<input type="hidden" name="prodCode" id="prodCode" value="${appDataMap.prodCode}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							产品名称：
						</td>
						<td width="12%">
							${appDataMap.prodName}
							<input type="hidden" name="prodName" value="${appDataMap.prodName}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							产品成立日：
						</td>
						<td width="12%">
							${appDataMap.prodBeginDate}
							<input type="hidden" name="prodBeginDate" value="${appDataMap.prodBeginDate}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							产品到期日：
						</td>
						<td width="12%">
							${appDataMap.prodEndDate}
							<input type="hidden" name="prodEndDate" value="${appDataMap.prodEndDate}"/>
						</td>
					</tr>
					<tr>
	   					<td width="12%" align="right">
							购买期最后一天允许撤单：
						</td>
						<td width="12%">
							<dic:out dicType="IS_YES" dicNo="${appDataMap.cancelFlag}"></dic:out>
							<input type="hidden" name="cancelFlag" value="${appDataMap.cancelFlag}"/>
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
						<input type="hidden" name="appType" value="${webReviewMain.appType }"/>
						<input type="hidden" name="appNo" value="${webReviewMain.appNo }"/>
						<table class="tableStyle" width="97%" mode="list" formMode="line" fixedCellHeight="true">
					<tr>
								<td width="38%">
									申请人员代码：
								</td>
								<td>
									${requestScope.webReviewMain.appUser}
								</td>
							</tr>
							<tr>
								<td width="38%">
									申请人员姓名：
								</td>
								<td>
									${requestScope.webReviewMain.appOperName}
								</td>
							</tr>
							<tr>
								<td>
									所属机构代码：
								</td>
								<td>
									${requestScope.webReviewMain.appOrganCode}
								</td>
							</tr>
							<tr>
								<td>
									所属机构名称：
								</td>
								<td>
									${requestScope.webReviewMain.appOrganName}
								</td>
							</tr>
							<tr>
								<td>
									申请人员角色：
								</td>
								<td>
									${requestScope.webReviewMain.appRoleName}
								</td>
							</tr>
							<tr>
								<td>申请时间：</td>
								<td>${requestScope.webReviewMain.appDate}</td>
							</tr>
							<tr>
								<td>申请说明：</td>
								<td class="text_break">
									${requestScope.webReviewMain.appRemark}
								</td>
							</tr>
							
							<tr>
								<td>
									复核人员代码：
								</td>
								<td>
									${requestScope.webReviewMain.repUserCode}
								</td>
							</tr>
							<tr>
								<td>
									复核人员姓名：
								</td>
								<td>
									${requestScope.webReviewMain.repUserName}
								</td>
							</tr>
							<tr>
								<td>
									所属机构代码：
								</td>
								<td>
									${requestScope.webReviewMain.repUserOrganCode}
								</td>
							</tr>
							<tr>
								<td>
									所属机构名称：
								</td>
								<td>
									${requestScope.webReviewMain.repUserOrganName}
								</td>
							</tr>
							
							<tr>
								<td>
									复核人员角色：
								</td>
								<td>
									${requestScope.webReviewMain.repRoleName}
								</td>
							</tr>
							<tr>
								<td>
									操作：
								</td>
								<td>
									<dic:radio dicType="REP_STATUS" name="repStatus" tgClass="validate[required]"></dic:radio>
									<span class="star">*</span>
								</td>
							</tr>
							<tr>
								<td>复核说明：</td>
								<td>
									<textarea class="textarea" style="width:80%;height:80px;" name="repRemark"></textarea>
								</td>
							</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return sub('form1','<%=path%>/gfProdPurLastCancel/doApproval.htm')" class="saveButton"/>
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