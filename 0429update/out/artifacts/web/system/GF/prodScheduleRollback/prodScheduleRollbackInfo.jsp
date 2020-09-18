<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_info.jsp"%>
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
								checkbox : false,
								height : '100%',
								pageSize:100
							});
			//四舍五入算法,dp代表保留小数点位数
			function formatRound(num,dp){
			    return   Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
			}
		}
		function onView(prodCode,taskId,processInstanceId){
			showDialog("<%=path%>/webProdQuery/infoUI.htm?prodCode="+ prodCode+"&taskId="+taskId+"&processInstanceId="+processInstanceId,"产品详细信息",1280,680);
		}
		</script>
	</head>
	<body>
	<form id="form1">
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
			<div name="档期批注信息" style="width:100%;height:300px;">
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
		</div>
	</form>
</body>
</html>