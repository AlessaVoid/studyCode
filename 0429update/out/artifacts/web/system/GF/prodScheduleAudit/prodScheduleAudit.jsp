<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<script type="text/javascript">
		var grid = null;
		function initComplete() {
			var gridData= ${gridData};
			//当提交表单刷新本页面时关闭弹窗
			grid = $("#dataBasic")
					.quiGrid(
							{
								columns : [{
									display : '产品代码',
									width : '6%',
									align : 'center',
									frozen:true,
									render: function(rowdata){
										return '<a href="#" onclick="onView(' + "'" +rowdata.prodCode+ "'"+ ','+"'" +rowdata.taskId+ "'" +','+"'" +rowdata.processInstanceId+ "'" +')">'+rowdata.prodCode+'</a>';
									}
										},{
											display : '产品名称',
											name : 'prodName',
											width : '15%',
											align : 'center',
											frozen:true
										},{
											display : '募集起始日期(从)',
											name : 'raisingBeginDate',
											width : '8%',
											align : 'center'
										},{
											display : '募集结束日期(到)',
											name : 'raisingEndDate',
											width : '8%',
											align : 'center'
										},{
											display : '产品成立日期',
											name : 'prodBeginDate',
											width : '8%',
											align : 'center'
										},{
											display : '产品终止日期',
											name : 'prodEndDate',
											width : '8%',
											align : 'center'
										},{
											display : '期限(天)',
											name : 'prodDurationDays',
											width : '8%',
											align : 'center'
										},{
											display : '币种',
											name : 'currency',
											width : '8%',
											align : 'center',
											render : function(rowdata, rowindex, value, column) {
												if("156"==value){
													return "人民币";
												}else if("036"==value) {
													return "澳大利亚元";
												}else if("124"==value) {
													return "加元";
												}else if("344"==value) {
													return "港币";
												}else if("392"==value) {
													return "日元";
												}else if("643"==value) {
													return "卢布";
												}else if("702"==value) {
													return "新元";
												}else if("756"==value) {
													return "瑞士法郎";
												}else if("826"==value) {
													return "英镑";
												}else if("840"==value) {
													return "美元";
												}else if("978"==value) {
													return "欧元";
												}
											}
										},{
											display : '计划募集金额（单位：万）',
											name : 'planIssueAmt',
											width : '8%',
											align : 'center',
											render : function(rowdata) {
												return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.planIssueAmt,2) + '</div>';},
										},{
											display : '客户收益率%',
											name : 'profitRate',
											width : '8%',
											align : 'center'
										},{
											display : '销售费率%',
											name : 'sellRate',
											width : '8%',
											align : 'center'
										},{
											display : '托管费%',
											name : 'prodControlFeeRate',
											width : '8%',
											align : 'center'
										},{
											display : '推荐费%',
											name : 'prodRecomRate',
											width : '8%',
											align : 'center'
										},{
											display : '超额收益费率%',
											name : 'overfulfilProfit',
											width : '8%',
											align : 'center'
										},{
											display : '投资收益率%',
											name : 'prodProfitRate',
											width : '8%',
											align : 'center'
										},{
											display : '总行管理费率%',
											name : 'headOfficeMagFee',
											width : '8%',
											align : 'center'
										},{
											display : '项目名称',
											name : 'prjName',
											width : '8%',
											align : 'center'
										},{
											display : '销售区域',
											name : 'saleArea',
											width : '8%',
											align : 'center'
										},{
											display : '销售渠道',
											name : 'saleChl',
											width : '8%',
											align : 'center'
										},{
											display : '是否夜市',
											name : 'isNightMarket',
											width : '8%',
											align : 'center',
											render : function(rowdata, rowindex, value, column) {
												if(value=="0"){
													return "否";
												}
												if(value=="1"){
													return "是";
												}
											}
										},{
											display : '认购起点',
											name : 'lowestInvestAmt',
											width : '8%',
											align : 'center',
											render : function(rowdata) {
												return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.lowestInvestAmt,2) + '</div>';},
										},{
											display : '产品研发人',
											name : 'prodOperName',
											width : '8%',
											align : 'center'
										},{ 
											display: '审批意见',
											name: 'comment',
											align: 'center', 
											width: '8%',
								               editor:{
								            	   type:"textarea",
								            	   editable:true,
								            	   detailEdit:true,
								            	   width:300,
								            	   height:600
								               }
								         }],
							    data:gridData, 
								sortName : '',
								rownumbers : true,
								enabledEdit:true,
								height : '100%',
								pageSize:100,
								toolbar : {
									items : [
											{text : '导出列表', click : onExportExcel, iconClass : 'icon_export'}
									]
								}
							});
			//四舍五入算法,dp代表保留小数点位数
			function formatRound(num,dp){
			    return   Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
			}
		}
		//查看产品详细信息	
		function viewProdInfo(prodCode){
				top.Dialog.open({
					URL : "<%=path%>/design/infoUI.htm?prodCode=" + prodCode,
					Title : "产品详细信息",
					Width : 1280,
					Height : 680
				});
			}
		//当不同意的时候，不能选择审批人信息 
		function changeAssignee(){
			var isAgree = $("#isAgree").val();
			if(isAgree == '0'){//不同意
				$("#repUserCode").removeAttr("class");
				$("#repUserCode").attr("disabled","disabled");
			}else if(isAgree == '1'){//同意
				$("#repUserCode").attr("class","validate[required]");
				$("#repUserCode").removeAttr("disabled");
			}
			$("#repUserCode").render();
		}
		
		function addComment(){
			var approveModel = $("#approveModel").attr("relText");
			$("#comment").val(approveModel);
			$("#comment").render();
		}
		function sub(formId,url){
			var valid = $("#"+formId).validationEngine( {
				returnIsValid : true
			});
			var valid_c=true;
			var value=$("#comment").val();
			createScheComment();
			var commentsVal=$("#comments").val();
			if(value.length>100){
			valid=false;
			valid_c=false;
			top.Dialog.alert("批注不能超过100个字");
			return false;
			}
			if(commentsVal.length>1000){
				valid=false;
				valid_c=false;
				top.Dialog.alert("档期审批意见：【"+commentsVal+"】不能超过1000个字!");
				return false;
				}
			if (valid) {
				top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
					$(".money").each(function(){
						rmoney(this);
					});
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
							$(".money").each(function(){
								fmoney(this);
							});
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			}else if(!valid_c){
			
			}else{
				top.Dialog.alert("验证未通过！");
			}
		}
//拼接档期意见
function createScheComment(){
	var rows =grid.getData();
	var comment="";
	$.each(rows,function(i,v){
		if(v.comment!=undefined){
			comment+=v.prodName+"("+v.prodCode+")["+v.comment+"];";
		}
	});
	var sche_c=$("#comment").val();//档期意见
	$("#comments").val(comment+sche_c);
} 
function onView(prodCode,taskId,processInstanceId){
	showDialog("<%=path%>/webProdQuery/infoUI.htm?prodCode="+ prodCode+"&taskId="+taskId+"&processInstanceId="+processInstanceId,"产品详细信息",1280,680);
}
function onExportExcel(){
	top.Dialog.confirm('是否导出全部数据?',	function() {
		$("#queryForm").attr("action","<%=path%>/webProdQuery/exportExcelByIds.htm");
		$("#queryForm").submit();
	});	
}
		</script>
	</head>
	<body>
	<form id="queryForm">
	<input type="hidden" name="taskIds" value="${taskIds }"/>
	</form>
	<form id="form1">
	<input type="hidden" name="msgNo" value="${msgNo }"/>
	<input type="hidden" name="taskIds" value="${taskIds }"/>
	<input type="hidden" name="lastUserType" value="${lastUserType }"/>
	<input type="hidden" name="scheduleCode" value="${entity.scheduleCode }"/>
	<input type="hidden" name="processInstanceIds" value="${processInstanceIds }"/>
	<input type="hidden" name="comments" id="comments"/>
		<div class="basicTabModern">
			<div name="档期信息" style="width:100%;height:300px;">
				<table class="tableStyle tab-hei-30" width="80%" mode="list">
					<tr>
						<td align="right" width="40%">档期编号：</td>
						<td width="60%">
							${entity.scheduleCode }
						</td>
					</tr>
					<tr>
						<td align="right" width="40%">档期描述：</td>
						<td width="60%">
							${entity.scheduleDesc }
						</td>
					</tr>
				</table>
				<div class="box2_custom"  boxType="box2" panelTitle="产品信息" class="padding_right5">
					<div id="dataBasic"></div>
				</div>
			</div>
			<div name="产品审批意见" style="width:100%;height:300px;">
				<c:forEach items="${commentMap }" var="commentMap">
				<div class="box2" panelWidth="100%" panelTitle="${commentMap.key }">
					<table class="tableStyle tab-hei-30" width="80%" mode="list">
										<tr>
											<td width="25%" align="left">
												审批用户
											</td>
											<td width="25%" align="left">
												审批时间
											</td>
											<td width="25%" align="left">
												批注
											</td>
										</tr>
						<c:forEach items="${commentMap.value }" var="comment">
							<tr>
								<td>
									${comment.userId }
								</td>
								<td>
									<fm:formatDate value="${comment.time }" pattern="yyyy年MM月dd日 HH时mm分ss秒"/>
								</td>
								<td>
									${comment.fullMessage }
								</td>
							</tr>
						</c:forEach>
					</table>
					</div>
				</c:forEach>
			</div>
			<div name="档期审批意见" style="width:100%;height:300px;">
				<table class="tableStyle tab-hei-30" width="80%" mode="list">
					<tr>
						<td width="25%" align="left">
							审批用户
						</td>
						<td width="25%" align="left">
							审批时间
						</td>
						<td width="25%" align="left">
							批注
						</td>
					</tr>
					<c:forEach items="${commentMap_sche }" var="commentMap_sche">
						<c:forEach items="${commentMap_sche.value }" var="comment">
							<tr>
								<td>
									${comment.userId }
								</td>
								<td>
									<fm:formatDate value="${comment.time }" pattern="yyyy年MM月dd日 HH时mm分ss秒"/>
								</td>
								<td>
									${comment.fullMessage }
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
			</div>
			<div name="复核信息" style="width:100%;height:80px;">
				<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
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
								url="<%=path%>/fdOper/getAppUserListByRole.htm?funCode=GF-09-02-01&roleCode=${roleCode }">
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
						<td align="right" width="40%">批注：</td>
						<td width="60%">
							<textarea id="comment" rows="50" cols="3" ></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="return sub('form1','<%=path%>/scheduleAudit/audit.htm')" class="saveButton"/>
								<button type="button" onclick="return cancel()" class="cancelButton" />	
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>