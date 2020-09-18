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
			<form action="" id="queryForm" method="post">
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
						<td width="12%" align="right">
							发售渠道：
						</td>
						<td width="20%">
						<div class="selectTree" url="<%=path%>/gfDict/getTreeDic.htm?dicType=D_CHNL" multiMode="true" allSelectable="true" exceptParent="true" name="channelCode"></div>
						</td>
						<td width="12%" align="right">
							发售范围：
						</td>
						<td width="20%">
						<div class="selectTree" url="<%=path%>/fdOrgan/getProvince.htm" multiMode="true" allSelectable="true" exceptParent="true"  name="branchCode"></div>
						</td>
					</tr>
					<tr>
						<td>
							募集起始日期（从）：
						</td>
						<td>
							<input type="text" name="raisingBeginDate" class="date" dateFmt="yyyyMMdd" id="rBeginDate"/>
						</td>
						<td>
							募集结束日期（到）：
						</td>
						<td>
							<input type="text" name="raisingEndDate" class="date" dateFmt="yyyyMMdd" id="rEndDate"/>
						</td>
						<td>
							产品成立日期：
						</td>
						<td>
							<input type="text" name="prodBeginDate"  class="date" dateFmt="yyyyMMdd" id="beginDate"/>
						</td>
						<td>
							产品终止日期：
						</td>
						<td>
							<input type="text" name="prodEndDate"  class="date" dateFmt="yyyyMMdd" id="endDate"/>
						</td>
					</tr>
					<tr>
						<td>
							产品期限：
						</td>
						<td>
						<div class="selectTree" url="<%=path%>/gfDict/getTreeDic.htm?dicType=PERIOD" multiMode="true" allSelectable="true" exceptParent="true" name="prodPeriod"></div>
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
						<td>
							是否自主平衡：
						</td>
						<td>
						<dic:select dicType="IS_YES" id="isAutoBalance" name="isAutoBalance" tgClass="" selWidth="127"  ></dic:select>
						</td>
					</tr>
					<tr>
						<td>
							待续接标识：
						</td>
						<td>
						<dic:select  dicType="IS_NEED_CONT_PROD" name="isContProd" tgClass="" selWidth="127" >	</dic:select>
						</td>
						<td>
							认购起点：
						</td>
						<td>
							<input type="text" name="lowestInvestAmt"/>
						</td>
						<td>
							产品品牌：
						</td>
						<td>
							<div class="selectTree" url="<%=path%>/gfProdBrandInfo/getBrandDic.htm" multiMode="true" allSelectable="true" exceptParent="true"  name="brandCode"></div>
						</td>
						<td>
							币种：
						</td>
						<td>
							<dic:select dicType="TR_CURRENCY" name="currency" tgClass="" selWidth="127"></dic:select>
						</td>
					</tr>
					<tr>
						<td  colspan="8">
							<div align="center">
								<button type="button" onclick="search()"><span class="icon_find">查询</span></button>
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
	top.Dialog.close();
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
									display : '计划募集金额（单位：万）',
									name : 'planIssueAmt',
									width : '8%',
									align : 'center',
									render : function(rowdata) {
										return  formatRound(rowdata.planIssueAmt,2);
						  				}
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
								}],
						url : '<%=path%>/eleChangeAudited/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
}

//详细信息
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/eleChangeAudited/infoUI.htm?prodCode="+rows[0].prodCode+"&processInstanceId="+rows[0].processInstanceId,
				"详细信息",1280,680);
	}
}

//查看流程图
function onViewImg() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		if (null==rows[0].taskId||""==rows[0].taskId) {
			top.Dialog.alert("流程已结束");
		}else{
		showDialog("<%=path%>/workflow/imageUI.htm?taskId=" + rows[0].taskId + "&processInstanceId="+rows[0].processInstanceId,"当前流程图",1380,680);
	}
		}
}
//导出EXCEL
function onExportExcel() {
	top.Dialog.confirm('是否导出全部数据?',	function() {
		$("#queryForm").attr("action","<%=path%>/eleChangeAudited/exportFile.htm");
		$("#queryForm").submit();
	});		
}
function search(){
	if(check())
	var query = $("#queryForm").formToArray();
	grid.setOptions( {
		params : query
	});
	//页号重置为1
	grid.setNewPage(1);
	grid.loadData();//加载数据
}
function check(){
	var beginDate=$("#beginDate").val();
	var endDate=$("#endDate").val();
	if (''!=beginDate&&''!=endDate) {
		if(compareDateE(beginDate,endDate)){
			top.Dialog.alert("产品成立日必须小于产品终止日");
			return false;		
		}
	}
	var rBeginDate=$("#rBeginDate").val();
	var rEndDate=$("#rEndDate").val();
	if (''!=rBeginDate&&''!=rEndDate) {
		if(compareDateE(rBeginDate,rEndDate)){
			top.Dialog.alert("产品募集开始日必须小于产品募集终止日");
			return false;		
		}
	}
	if (''!=rEndDate&&''!=beginDate) {
		if(compareDateE(rEndDate,beginDate)){
			top.Dialog.alert("产品募集终止日必须小于产品成立日");
			return false;		
		}
	}
	if (''!=rBeginDate&&''!=beginDate) {
		if(compareDateE(rBeginDate,beginDate)){
			top.Dialog.alert("产品募集开始日必须小于产品成立日");
			return false;		
		}
	}
	if (''!=rBeginDate&&''!=endDate) {
		if(compareDateE(rBeginDate,endDate)){
			top.Dialog.alert("产品募集开始日必须小于产品终止日");
			return false;		
		}
	}
	if (''!=rEndDate&&''!=endDate) {
		if(compareDateE(rEndDate,endDate)){
			top.Dialog.alert("产品募集终止日必须小于产品终止日");
			return false;		
		}
	}
	return true;
}
</script>
</body>
</html>