<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<!-- 查询位置 -->
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="<%=path%>/webResearchAudit/findPage.htm" id="queryForm" method="post">
				<input type="hidden" name="roleCode" value="${roleCode }"/>
				<input type="hidden" name="taskId" value="${taskId }"/>
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="12%" align="right">
							产品代码：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="12%">
							产品名称：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodName" matchName="prodName" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodName" suggestMode="remote"></div>
						</td>
						<td width="12%">
							发售渠道：
						</td>
						<td width="12%">
						<div class="selectTree" url="<%=path%>/gfDict/getTreeDic.htm?dicType=D_CHNL" multiMode="true" allSelectable="true" exceptParent="true" name="channelCode"></div>
						</td>
						<td width="12%">
							发售范围：
						</td>
						<td width="12%">
						<div class="selectTree" url="<%=path%>/fdOrgan/getProvince.htm" multiMode="true" allSelectable="true" exceptParent="true"  name="branchCode"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							募集起始日期（从）：
						</td>
						<td>
							<input type="text" name="raisingBeginDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td>
							募集结束日期（到）：
						</td>
						<td>
							<input type="text" name="raisingEndDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td align="right">
							产品成立日期：
						</td>
						<td>
							<input type="text" name="prodBeginDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td>
							产品终止日期：
						</td>
						<td>
							<input type="text" name="prodEndDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
					</tr>
					<tr>
						<td>
							产品期限：
						</td>
						<td>
						<div class="selectTree" url="<%=path%>/gfDict/getTreeDic.htm?dicType=PERIOD" multiMode="true" allSelectable="true" exceptParent="true" name="prodPeriod"></div>
						</td>
						<td align="right">
							产品研发人：
						</td>
						<td>
							<input type="text" name="prodOperCode"/>
						</td>
						<td>
							项目研发人：
						</td>
						<td>
							<input type="text" name="prjResearchStaff"/>
						</td>
						<td>
							项目名称：
						</td>
						<td>
							<input type="text" name="prjName"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							是否自主平衡：
						</td>
						<td>
						<dic:select dicType="IS_YES" id="isAutoBalance" name="isAutoBalance" tgClass="" selWidth="127"  ></dic:select>
						</td>
						<td>
							待续接标识：
						</td>
						<td>
						<dic:select  dicType="IS_NEED_CONT_PROD" name="isContProd" selWidth="127"></dic:select>
						</td>
						<td>
							认购起点：
						</td>
						<td>
							<input type="text" name="lowestInvestAmt"/>
						</td>
						<td align="right">
							产品品牌：
						</td>
						<td>
							<div class="selectTree" url="<%=path%>/gfProdBrandInfo/getBrandDic.htm" multiMode="true" allSelectable="true" exceptParent="true"  name="brandCode"></div>
						</td>
					</tr>
					<tr>
						<td>
							币种：
						</td>
						<td>
							<dic:select dicType="TR_CURRENCY" name="currency" tgClass="" selWidth="127"></dic:select>
						</td>
						<td colspan="6"></td>					
					</tr>
					<tr>
						<td  colspan="8">
							<div align="center">
								<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
								<button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- Grid位置 -->
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '6%',
									align : 'center',
									frozen:true
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
									display : '规模',
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
									display : '项目名称',
									name : 'prjName',
									width : '8%',
									align : 'center'
								},{
									display : '产品研发人',
									name : 'prodOperName',
									width : '8%',
									align : 'center'
								}],
						url : '<%=path%>/webResearchAudit/findPage.htm',
						sortName : 'prodCode',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						usePager : true ,
						toolbar : {
							items : [
								${btnList}
							]
						}
					}	
					);
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
}

//审批
function onAudit() {
	var rows = grid.getSelectedRows();
	if(rows.length == 0){
		top.Dialog.alert("请选择一条记录！");
	}else if(rows.length == 1){
		showDialog("<%=path%>/webResearchAudit/auditUI.htm?prodCode="+rows[0].prodCode+"&taskId=" + rows[0].taskId, "产品预研审批",1280,680);
	}else{
		var taskId = "";
		for(var i=0; i<rows.length; i++){
			taskId += "-"+rows[i].taskId;
		}
		taskId = taskId.substring(1, taskId.length);
		top.Dialog.open({
			URL : "<%=path%>/webResearchAudit/mutiAuditUI.htm?menuNo=GF-05-02&taskId="+taskId,
			Title : "产品预研多条审批",
			ID:'mutiApp',
			Width : "1280",
			Height : "680"
		});
	}
}
//详细信息
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/webResearchAudit/infoUI.htm?prodCode=" + rows[0].prodCode+"&taskId=" + rows[0].taskId,"产品详细信息",1280,680);
	}
}

//查看流程图
function onViewImg() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		top.Dialog.open({
			URL : "<%=path%>/workflow/imageUI.htm?taskId=" + rows[0].taskId + "&processInstanceId="+rows[0].processInstanceId,
			Title : "当前流程图",
			Width : 1280,
			Height : 680
		});
	}
}

//导出
function onExportExcel(){
	top.Dialog.confirm('是否导出全部数据?',	function() {
			$("#queryForm").attr("action","<%=path%>/webResearchAudit/exportProdparaFile.htm");
			$("#queryForm").submit();
	});									
}

//预研参考表
function onReferenceList(){
	var rows = grid.getSelectedRows();
	var prodCodes = "";
	if(rows.length == 0){
		top.Dialog.alert("请选择一条记录！");
	}else if(rows.length == 1){
		prodCodes = rows[0].prodCode;
		showDialog("<%=path%>/webResearchAudit/referenceMaList.htm?prodCodes="+prodCodes+"&currency="+rows[0].currency, "预研参考表",1280,680);
	}else{
		var currency = rows[0].currency;
		var iftrue = true;
		for(var i=0; i<rows.length; i++){
			if(currency!=rows[i].currency){
				top.Dialog.alert("请选择同一币种操作！");
				iftrue = false;
			}
			prodCodes += "-"+rows[i].prodCode;
		}
		prodCodes = prodCodes.substring(1, prodCodes.length);
		if(iftrue){
			showDialog("<%=path%>/webResearchAudit/referenceMaList.htm?prodCodes="+prodCodes+"&currency="+rows[0].currency, "预研参考表",1280,680);
		}
	}
}
//发行计划表
function onPlanTable(){
	showDialog("<%=path%>/webResearchAudit/planTableUI.htm","拟发行计划表",1280,580);
}
//待签收任务列表
function onRevTask(){
	showDialog("<%=path%>/webResearchAudit/revTaskUI.htm","待签收任务",1280,580);
}

//关闭窗口，用于多条审批成功之后
function closeWin(){
	setTimeout("top.Dialog.close()",500);
}
</script>
</body>
</html>