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
		//数据表格初始化
	   	var grid =null ;
	   	function initComplete() {
	   		grid = $("#dataBasic").quiGrid({
		               columns: [
		               { display: '参数类型', name: 'paraType', align: 'center', width: "8%",
		            	   render : function(rowdata) {
			           		    if(rowdata.paraType == "1") {
			           			   return "申购";
			           			}else if (rowdata.paraType == "2"){
			           			   return "赎回";
			           			}
		           		   }
		               },
		               { display: '起始日期', name: 'beginDate', align: 'center', width: "8%",
		            	   render : function(rowdata) {
		        				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginDate.replace(/-/gi, "") + '</div>';}
		               },
		               { display: '终止日期', name: 'endDate', align: 'center', width: "8%",
		            	   render : function(rowdata) {
		        				return '<div style="margin-right: 45%;" align="right">' + rowdata.endDate.replace(/-/gi, "") + '</div>';}
		               },
		               { display: '开始时间（从）', name: 'beginTime', align: 'center', width: "8%",
		             	   render : function(rowdata) {
		             		   if(rowdata.beginTime==null){
		            					return '<div style="margin-right: 45%;" align="right">' + "" + '</div>';
		             		   }else{
		         				return '<div style="margin-right: 45%;" align="right">' + rowdata.beginTime.replace(/-/gi, "") + '</div>';};
		             	   	   }
		                },
		                { display: '结束时间（到）', name: 'endTime', align: 'center', width: "8%",
		             	   render : function(rowdata) {
		                		if(rowdata.endTime==null){
		   						return '<div style="margin-right: 45%;" align="right">' + "" + '</div>';
		    		   			}else{
		 						return '<div style="margin-right: 45%;" align="right">' + rowdata.endTime.replace(/-/gi, "") + '</div>';};
		    	   	   			}
		                },
		               { display: '开放期额度', name: 'limit', align: 'center', width: "10%",
		             	   render : function(rowdata) {
		        				return '<div  style="margin-right: 45%;" align="right">' + formatRound(rowdata.limit,2)+ '</div>';}
		              },
		               { display: '资金处理方式', name: 'amtHandleFlag', align: 'center', width: "10%",
		            	   render : function(rowdata) {
			           		    if(rowdata.amtHandleFlag == "1") {
			           			   return "批量";
			           			}else if (rowdata.amtHandleFlag == "2"){
			           			   return "实时";
			           			}
		         			}
		               },
		               { display: '份额确认方式', name: 'quotAffirmType', align: 'center', width: "10%",
		            	   render : function(rowdata) {
			           		    if(rowdata.quotAffirmType == "1") {
			           			   return "批量";
			           			}else if (rowdata.quotAffirmType == "2"){
			           			   return "实时";
			           			}
		         			}
		               },
		               { display: '份额确认日', name: 'quotAffirmDate', align: 'center', width: "10%",
		               },
		               { display: '启用状态', name: 'status', align: 'center', width: "10%",type: 'STARTFLAG',
		            	   render : function(rowdata) {
		            		   if (rowdata.status == "1") {
		            				return "有效";
		            			}else if (rowdata.status == "2"){
		            				return "无效";
		            			}
		          			}
		                },
		                { display: '开放期模式', name: 'openFlag', align: 'center', width: "10%",
		              	   render : function(rowdata) {
		              		   if (rowdata.openFlag == "0") {
		              				return "有规律";
		              			}else if (rowdata.openFlag == "1"){
		              				return "无规律";
		              			}
		            			}
		                  }
		               ], 
		               data:mainData, 
		               rownumbers : true,
		               usePager: false, 
		               percentWidthMode:true,
		               sortName: 'id'
	   			});
	   		//四舍五入算法,dp代表保留小数点位数
			function formatRound(num,dp){
			 	return (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
			}
	 	}	
	   	function doSubmit(formId,url){
			var valid = $("#"+formId).validationEngine({
				returnIsValid : true
			});
			if (valid) {
				top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
					var param = $("#"+formId).formToArray();
					var map = {};
					map.name = "gridData";
					map.value= JSON.stringify(grid.getData());
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
								<button type="button" onclick="return doSubmit('form1','<%=path%>/webProdOpenDurationInfo/doNotifyApp.htm')" class="saveButton"/>
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