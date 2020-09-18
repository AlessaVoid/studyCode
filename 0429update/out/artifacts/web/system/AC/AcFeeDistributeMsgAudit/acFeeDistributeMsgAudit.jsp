<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
		<title></title>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
	</head>
	<body>
		<!-- 查询位置 -->
		<div class="basicTabModern">
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5" name="待分配手续费列表">
			<div id="dataBasic"></div>
		</div>
		<div name="审批流程记录">
		<table class="tableStyle tab-hei-30" width="80%" mode="list">
				<tr>
					<td width="30%" align="left">
						审批用户
					</td>
					<td width="30%" align="left">
						审批时间
					</td>
					<td width="40%" align="left">
						批注
					</td>
				</tr>
				<c:forEach items="${comments }" var="comment">
				<tr>
					<td>
						${comment.userId }
					</td>
					<td>
					<c:choose>
						<c:when test="${empty comment.time}">
						----
						</c:when>
						<c:otherwise>
							<fm:formatDate value="${comment.time }" pattern="yyyyMMdd HH:mm:ss"/>
						</c:otherwise>
					</c:choose>
					</td>
					<td>
						${comment.fullMessage }
					</td>
				</tr>
				</c:forEach>
				</table>
	   	</div>
		<div name="提交审批">
		<form id="form1">
			<input type="hidden" name="taskId" value="${taskId }"/>
			<input type="hidden" name="lastUserType" value="${lastUserType }"/>
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
				</c:if>
				<tr>
					<td>备注：</td>
					<td>
						<textarea id="comment" rows="50" cols="3" name="comment"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return sub('form1','<%=path%>/gfFeeDisAudit/audit.htm')" class="saveButton"/>
							<button type="button" onclick="return cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	   	</div>
	 </div>
<script type="text/javascript">
var grid = null;
function initComplete() {
	var gridData=${gridData};
	grid = $("#dataBasic")
	.quiGrid(
			{
				columns : [{
							display : '生成日期',
							name : 'tradedate',
							align : 'cennter',
							width : '10%'

						},{
							display : '产品代码',
							name : 'businesskindcode',
							align : 'cennter',
							width : '10%'
						},
						{
							display : '产品名称',
							name : 'businesskindname',
							align : 'cennter',
							width : '20%'
						},
						{
							display : '计费起始日',
							name : 'begindate',
							align : 'cennter',
							width : '10%'
						},
						{
							display : '计费终止日',
							name : 'enddate',
							align : 'cennter',
							width : '10%'
						},

						{
							display : '处理标记',
							name : 'fpflag',
							align : 'cennter',
							width : '10%',
							render : function(rowdata, rowindex, value,
									column) {
								if (value == 0 || value == "0") {
									value="未处理";
								} else if (value == 1 || value == "1") {
									value="待复核";
								} else if (value == 2 || value == "2") {
									value="等待后台处理";
								} else if (value == 3 || value == "3") {
									value="已处理";
								} else if (value == 4 || value == "4") {
									value="处理异常";
								}
								var title=rowdata.text;
								if(title=="undefined"||title==undefined){
									title="";
								}
								
								return '<div class="padding_top4 padding_left5">' 
								+ '<span  title="'+title+'">'+value+'</span>'
								+ '</div>';
							}
						},
						{
							display : '计费金额',
							name : 'amt',
							align : 'cennter',
							width : '15%',
							render : function(rowdata, rowindex, value,
									column) {
								return "<div class='money'>"
										+ zh(value, 2) + "</div>"
							}
						},
						{
							display : '实际分配金额',
							name : 'fpamt',
							align : 'cennter',
							width : '15%',
							totalSummary:{
								render:function(o){
									return "总分配金额："+zh(o.sum, 2)+ "元";
										},
								align : 'left'},
							render : function(rowdata, rowindex, value,
									column) {
								if(value==""||value==''||value==null){value="0";}
								return "<div class='money'>"+ zh(value, 2) + "</div>"
							}
						}],
						data:gridData, 
						rownumbers : true,
						height : '100%',
						usePager: false
					});
}

function sub(formId,url){
	var valid = $("#"+formId).validationEngine({
		returnIsValid : true
	});
    var valid_c=true;
	var value=$("#comment").val();
	if(value.length>100){
	valid=false;
	valid_c=false;
	}
if(valid) {
	var param = $("#"+formId).formToArray();
	var map = {};
	map.name = "gridData";
	map.value= JSON.stringify(grid.getData());
	param.push(map);
	$.post(url, param, function(result) {
		if (result.success == "true" || result.success == true) {
			top.Dialog.alert(result.msg, function() {
				top.window.location.reload(true);
				top.Dialog.close();
			});
		} else {
			top.Dialog.alert(result.msg);
		}
	}, "json");
			
		}else if(!valid_c){
		top.Dialog.alert("备注字数不能超过100！");

	}else{
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
</script>
</body>
</html>
