<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_edit.jsp"%>
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
									width : '30%',
									align : 'center'
								},{
									display : '销售渠道',
									name : 'saleChl',
									width : '20%',
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
								}],
					    data:gridData, 
						sortName : '',
						rownumbers : true,
						height: '100%', 
						usePager:false,
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
	$.quiDefaults.Grid.formatters['IS_NIGHT_MARKET'] = function (value, column) {
		if(value == '0'){
	    	return '否';
	    }else if(value == '1'){
	    	return '是';
	    }
	};
	var data=eval(${list});
	$.each(data,function(i,v){
		var $tr=addNewRowHandler(v);
	    $("#checkboxTable").append($tr); 
	     $("#checkboxTable").render();
		});
	$.each(data,function(i,v){
		var $tr=addNewRowHandler(v);
	    $("#preCheckboxTable").append($tr); 
	     $("#preCheckboxTable").render();
		});
}

function addNewRowHandler(d){
    var $tr=$("<tr><td><input type=\"checkbox\" value=\""+d.scheduleCode+"\"/></td><td>"+d.scheduleCode+"</td><td>"+d.scheduleDesc+"</td><td>"+d.latestModifyDate+"</td></tr>");
    return $tr;
}
function createDesc(){
	var scheduleCodes="";
	var preScheduleCodes="";
	var prodCodes="";
	//本档期
    $("#checkboxTable input:checkbox").each(function(){
        if($(this).attr("checked")){
        	scheduleCodes += "," + $(this).val();
        }
    });
    if(scheduleCodes==""){
    	
    }else{
    	scheduleCodes = scheduleCodes.substring(1);
    }
    //上一档期
    $("#preCheckboxTable input:checkbox").each(function(){
        if($(this).attr("checked")){
        	preScheduleCodes += "," + $(this).val();
        }
    });
    if(preScheduleCodes==""){
    	top.Dialog.alert("上一档期信息至少选择一条记录！");
    }else{
    	preScheduleCodes = preScheduleCodes.substring(1);
	    //待生成档期产品
	    var rowdatas=grid.getData();
	    $.each(rowdatas,function(i,v){
	    	prodCodes+="," +v.prodCode;
	    });
	    prodCodes = prodCodes.substring(1);
	    $.post("<%=path%>/schedule/getScheduleDesc.htm",{
	    	prodCodes:prodCodes,
	    	scheduleCodes:scheduleCodes,
	    	preScheduleCodes:preScheduleCodes
	    	},function(result){
	    	var desc=result;
	    	$("#scheduleDesc").html(desc);
	    },"json");
    }
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
	<input type="hidden" name="taskIds" value="${taskIds }"/>
	<input type="hidden" name="lastUserType" value="${lastUserType }"/>
	<input type="hidden" name="isAgree" value="1"/>
	<div class="basicTabModern">
		<div name="档期信息" style="width:100%;height:300px;">
			<div id="dataBasic"></div>
		</div>
		<div name="现有档期信息情况" style="width:100%;height:300px;">
		拟合并说明档期：
		<table class="tableStyle"  useClick="false"  useCheckBox="true" sortMode="true" id="checkboxTable">
	    <tr>
	       <th width="25"></th>
	        <th>档期编号</th><th>档期说明</th><th>档期生成时间</th>
	    </tr>
		</table>
		上一档期情况：
		<table class="tableStyle"  useClick="false"  useCheckBox="true" sortMode="true" id="preCheckboxTable">
	    <tr>
	       <th width="25"></th>
	        <th>档期编号</th><th>档期说明</th><th>档期生成时间</th>
	    </tr>
		</table>
		<table  class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
		<tr>
			<td colspan="2">
				<div align="center">
				<button type="button" onclick="createDesc();" class="button"><span class='icon_add'>生成档期说明</span></button>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right" width="40%">档期描述：</td>
			<td width="90%">
				<textarea rows="30" cols="50" name="scheduleDesc" style="width: 500px; height: 179px;" id="scheduleDesc">
				</textarea>
			</td>
		</tr>
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
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" onclick="return doSubmit('form1','<%=path%>/schedule/schedule.htm')" class="saveButton"/>
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