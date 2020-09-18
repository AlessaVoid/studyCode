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
						<td>
							产品代码：
						</td>
						<td>
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
						</td>
						<td>
							产品名称:
						</td>
						<td>
							<div class="suggestion" name="prodName" matchName="prodName" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodName" suggestMode="remote"></div>
						</td>
						<td>
							产品成立日期：
						</td>
						<td>
							<input type="text" name="prodBeginDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td>
							产品终止日期:
						</td>
						<td>
							<input type="text" name="prodEndDate" class="date" dateFmt="yyyyMMdd"/>
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
									width : '15%',
									align : 'center',
									frozen:true
								},{
									display : '项目名称',
									name : 'prjName',
									width : '20%',
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
									display : '建议发行日',
									name : 'proposalIssueDate',
									width : '10%',
									height: '16%',
									align : 'center'
								},{
									display : '最迟发行日',
									name : 'latestIssueDate',
									width : '10%',
									height: '16%',
									align : 'center'
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
						url : '<%=path%>/gfProdDesignCont/findPage.htm',
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
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, "")) * Math.pow(10,dp) )/ Math.pow(10,dp),dp));
	}
}

//复制新增
function onCopyAdd() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdDesignCont/copyDesignUI.htm?prodCode="+ rows[0].prodCode,"产品设计续接复制新增",1280,680);
	}
}

</script>
</body>
</html>
