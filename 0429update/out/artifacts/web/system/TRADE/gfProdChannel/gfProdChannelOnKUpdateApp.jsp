<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	</head>
	<body>
		<form id="form1">
		<div class="basicTabModern">
				<div name="渠道开通复核" style="width:100%;height:300px;">
					<div id="dataBasic"></div>
    			</div>
    			<div name="复核信息" style="width:100%;height:240px;">
				<input type="hidden" name="appType" value="${webReviewMain.appType }"/>
				<input type="hidden" name="appNo" value="${webReviewMain.appNo }"/>
				<input type="hidden" name="prodCode" id="prodCode" value="${appDataMap.prodCode}"/>
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
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfProdChannel/onKUpdateApproval.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
						</table>
					</div>
				</div>
	   	</form>
	</body>
	<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [
								{
									display : '产品代码',
									name : 'prodCode',
									width : '33%',
									align : 'center'
								},{
									display : '渠道代码',
									name : 'channelCode',
									width : '33%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="00"){
											return "柜台";
											}
										if(value=="01"){
											return "电话银行";
											}
										if(value=="02"){
											return "一卡通";
											}
										if(value=="03"){
											return "理财通";
											}
										if(value=="04"){
											return "短信银行";
											}
										if(value=="05"){
											return "深圳账户管家";
											}
										if(value=="07"){
											return "个人网银";
											}
										if(value=="08"){
											return "理财规划";
											}
										if(value=="09"){
											return "手机银行";
											}
										if(value=="10"){
											return "个人信贷";
											}
										if(value=="12"){
											return "电视银行";
											}
										if(value=="11"){
											return "公司网银";
											}
										if(value=="17"){
											return "VTM自助银行";
											}	
										if(value=="0"){
											return "全部";
											}
										if(value=="46"){
											return "公司信贷";
											}
										if(value=="47"){
											return "公司票据";
											}
										}
								},{
									display : '状态',
									name : 'status',
									width : '33%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "启用";
											}
										if(value=="2"){
											return "停用";
											}
									}
								}],
						url : '<%=path%>/gfProdChannel/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						params:[{name:"prodCode",value:$("#prodCode").val()}]
					});
}
</script>
</html>


