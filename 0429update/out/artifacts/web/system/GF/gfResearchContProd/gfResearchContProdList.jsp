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
				<table class="tableStyle"  mode="list" formMode="line" style="width: 100%;">
					<tr>
						<td width="12%" align="right">
							产品代码：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodCode" matchName="prodCode" id="prodCode"
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td width="12%">
							产品名称:
						</td>
						<td width="12%">
							<div class="suggestion" name="prodName" matchName="prodName" id="prodName"
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodName" suggestMode="remote"></div>
						</td>
						<td align="right">
							产品成立日期：
						</td>
						<td>
							<input type="text" name="prodBeginDate" id="prodBeginDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td>
							产品终止日期:
						</td>
						<td>
							<input type="text" name="prodEndDate" id="prodEndDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
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
									height: '16%',
									width : '15%',
									align : 'center',
									frozen:true
								},{
									display : '项目名称',
									name : 'prjName',
									width : '10%',
									height: '16%',
									align : 'center'
								},{
									display : '产品成立日期',
									name : 'prodBeginDate',
									width : '10%',
									height: '16%',
									align : 'center'
								},{
									display : '产品终止日期',
									name : 'prodEndDate',
									width : '10%',
									height: '16%',
									align : 'center'
								},{
									display: '建议预研期间',
									columns: [{ 
								        display: '开始日', name: 'adviseBeginDate', height: '8%',width : '10%' } ,
								      { display: '结束日', name: 'adviseEndDate', height: '8%',width : '10%' }]
								},{
									display: '最近申报募集首日',
									columns: [{ 
								        display: '开始日', name: 'raisingBeginDate', height: '8%',width : '10%' } ,
								      { display: '结束日', name: 'raisingEndDate', height: '8%',width : '10%' }]
								},{
									display : '续接状态',
									name : 'isNeedContProd',
									width : '11%',
									height: '16%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="3"){
											return "已续接安排";
										}
										if(value=="1"){
											return "待续接安排";
										}
									}
								}],
						url : '<%=path%>/gfResearchContProd/findPage.htm',
						sortName : 'prodCode',
						rownumbers : true,
						checkbox : true,
						multihead: true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
								{text: '新预研（个人）', click: onNewResearch, iconClass: 'icon_add'},
						        { line : true },
						        {text: '复制新增（个人）', click: onCopyAdd, iconClass: 'icon_add'},
						        { line : true },
						        {text: '新设计（机构）', click: onProdDesign, iconClass: 'icon_add'},
						        { line : true }
							]
						}
					});
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}
}

//新预研
function onNewResearch() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfResearchContProd/newResearchUI.htm?prodCode="+ rows[0].prodCode,"产品预研续接新预研",1280,680);
	}
}

//复制新增
function onCopyAdd() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfResearchContProd/copyResearchUI.htm?prodCode="+ rows[0].prodCode,"产品预研续接复制新增",1280,680);
	}
}

//新设计
function onProdDesign() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfResearchContProd/newDesignUI.htm?prodCode="+ rows[0].prodCode,"产品预研续接新设计",1280,680);
	}
}

</script>
</body>
</html>
