<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<script type="text/javascript">
		var mainData={"form.paginate.pageNo":1,
				"rows":${appDataMap.gridData}};
	   	var grid = null;
	   	function initComplete() {
	   		grid = $("#dataBasic")
	   				.quiGrid(
	   						{
	   							columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '15%',
									align : 'center',
									frozen:true
								},{
									display : '产品名称',
									name : 'prodName',
									width : '25%',
									align : 'center',
									frozen:true
								},{
									display : '产品运作模式',
									name : 'prodOperModel',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "封闭式净值型";
										}
										if(value=="02"){
											return "封闭式非净值型";
										}
										if(value=="03"){
											return " 开放式净值型";
										}
										if(value=="04"){
											return " 开放式非净值型";
										}
									}
								},{
									display : '产品收益类型',
									name : 'profitType',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "保证收益";
										}
										if(value=="02"){
											return "保本浮动收益";
										}
										if(value=="03"){
											return " 非保本浮动收益";
										}
									}
								},{
									display : '产品风险等级',
									name : 'prodRiskLevel',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "一级（低）";
										}
										if(value=="2"){
											return "二级（中低）";
										}
										if(value=="3"){
											return "三级（中）";
										}
										if(value=="4"){
											return "四级（中高）";
										}
										if(value=="5"){
											return "五级（高）";
										}
									}
								},{
									display : '是否自主平衡',
									name : 'isAutoBalance',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "是";
										}
										if(value=="0"){
											return "否";
										}
									}
								}],
								data:mainData, 
	   							sortName : '',
	   							rownumbers : true,
	   							height : '100%',
	   						 	usePager: false
	   						});
	   		}	
	   	function doSubmit(formId,url){
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			if (valid) {
				top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
					var param = $("#"+formId).formToArray();
	   				var map = {};
	   				map.name = "prodCodes";
	   				var data=grid.getData();
	   		   		var prodCodes="";
	   		   		$.each(data,function(i,v){
	   		   			prodCodes=prodCodes+","+v.prodCode;
	   		   		});
	   		   		prodCodes=prodCodes.substring(1);
	   		   		map.value=prodCodes;
	   				param.push(map);
					$.post(url, param, function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, function() {
								top.frmright.window.location.reload(true);
								top.Dialog.close();
							});
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			}else{
				top.Dialog.alert("验证未通过！");
			}
		}
	   	</script>
	</head>
	<body>
		<div class="basicTabModern">
    		<div id="dataBasic" name="产品列表"></div>
    		<div name="复核信息" style="width:100%;height:80px;">
    		<form id="form1">
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
								<button type="button" onclick="return doSubmit('form1','<%=path%>/gfSaleNotifyInfo/doApproval.htm')" class="saveButton"/>
								<button type="button" onclick="return cancel()" class="cancelButton" />	
							</div>
						</td>
					</tr>
				</table>
		</form>
			</div>
	   	</div>
	</body>
</html>