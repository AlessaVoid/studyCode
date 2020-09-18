<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<!-- 日期选择框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
		<!-- 日期选择框end -->
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<script type="text/javascript">
//数据表格使用
var grid;
function initComplete() {
    var gridData={"form.paginate.pageNo":1,"form.paginate.totalRows":13,"rows":${requestScope.appDataMap.gridData}};
	grid = $("#multipledata")
			.quiGrid({
						columns : [
								{
									display : '合同号',
									name : 'contractNo',
									align : 'center',
									width : "33%"
								},
								{
									display : '可冻结份额',
									name : 'availableQuot',
									align : 'center',
									width : "33%"
								},
								{
									display : '冻结份额',
									name : 'freezeQuot',
									align : 'center',
									width : "33%"
								}
								],
						data:gridData,
						rownumbers : true,
						checkbox : false,
						height : "100%",
						width : "100%",
						pageSize : 100,
						percentWidthMode : true
	});
	
	/**
	 * 切换tab时处理, loadData
	 */
	$("#tabCtn").bind("actived",function(e,i){
		var flag = $("#tabCtn").attr("afterInitTab")||'';
		if(1==i){
			if(flag.indexOf('1i')<0){
				grid.loadData();
				$("#tabCtn").attr("afterInitTab",flag+":1i");
			}
		}
	});
}




</script>
	</head>
	<body>
		<form id="form1">
		<div id="tabCtn" class="basicTabModern">
				<div name="TA份额冻结" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<input type="hidden" id="gridData" name="gridData" value="${appDataMap.list}"/>
						<tr>
							<td align="right" width="38%">
								投资者类型：
							</td>
							<td>
								<dic:out dicNo="${appDataMap.customerkind}" dicType="D_CUST_TYPE"></dic:out>
								<input type="hidden" id="customerkind" name="customerkind" value="${appDataMap.customerkind}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								证件类型：
							</td>
							<td>
								<div id="custDiv">
									<dic:out dicNo="${appDataMap.certificatekind}" dicType="D_CUST_CERT_TYPE"></dic:out>
								</div>
								<div id="corpDiv">
									<dic:out dicNo="${appDataMap.certificatekind}" dicType="D_CORP_CERT_TYPE"></dic:out>
								</div>
								<input type="hidden" name="certificatekind" id="certificatekind" value="${appDataMap.certificatekind}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								证件号码：
							</td>
							<td>
								${appDataMap.certificatecode}
								<input type="hidden" name="certificatecode" value="${appDataMap.certificatecode}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								客户名称
							</td>
							<td>
								${appDataMap.customername}
								<input type="hidden" id="customername" name="customername" value="${appDataMap.customername}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								产品代码:
							</td>
							<td>
								${appDataMap.prodcode}
								<input type="hidden" id="prodcode" name="prodcode" value="${appDataMap.prodcode}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								销售商代码:
							</td>
							<td>
								${appDataMap.retailercode}
								<input type="hidden" name="retailercode" value="${appDataMap.retailercode}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								冻结截止日期:
							</td>
							<td>
								${appDataMap.freezedate}
								<input type="hidden" name="freezedate" value="${appDataMap.freezedate}" class="validate[required]" maxlength="8"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								冻结法律文书号:
							</td>
							<td>
								${appDataMap.freezejudicialfilecode}
								<input type="hidden" name="freezejudicialfilecode" value="${appDataMap.freezejudicialfilecode}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								冻结原因（司法冻结）:
							</td>
							<td>
								${appDataMap.freezeReason}
								<input type="hidden" name="freezeReason" value="${appDataMap.freezeReason}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								司法经办人姓名:
							</td>
							<td>
								${appDataMap.handlename}
								<input type="hidden" name="handlename" value="${appDataMap.handlename}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								司法经办人证件类型:
							</td>
							<td>
								<dic:out dicType="D_CUST_CERT_TYPE" dicNo="${appDataMap.handlecerttype}"/>
								<input type="hidden" name="handlecerttype" value="${appDataMap.handlecerttype}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="38%">
								司法经办人证件号码:
							</td>
							<td>
								${appDataMap.handlecertcode}
								<input type="hidden" name="handlecertcode" value="${appDataMap.handlecertcode}"/>
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
    			<div class="padding_right5" name="冻结份额合同信息" style="width:100%;height:80px;">
				    <div id="multipledata"></div>
				</div>
    			<div name="复核信息" style="width:100%;height:240px;">
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
							<td>
								${requestScope.webReviewMain.appDate}
								<input type="hidden" name="appDate" value="${webReviewMain.appDate }"/>
								<input type="hidden" name="appTime" value="${webReviewMain.appTime }"/>
							</td>
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
								<input type="hidden" name="repUserCode" value="${webReviewMain.repUserCode }"/>
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
								<button type="button" onclick="return doSubmit('form1','<%=path%>/taShareFreeze/doApproval.htm')" class="saveButton"/>
								<button type="button" onclick="return cancel()" class="cancelButton" />	
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