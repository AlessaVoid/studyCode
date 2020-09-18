<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
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
						<td width="16%" align="right">
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
							资金类型：
						</td>
						<td width="12%">
							<dic:select dicType="D_FUND_TYPE" name="fundType"></dic:select>
						</td>
						<td>起始日期：</td>
						<td>
							<input type="text" name="bTradeDate"  id="bTradeDate" class="date validate[length[0,8]]" dateFmt="yyyyMMdd"/>
						</td>
						<td>结束日期：</td>
						<td>
							<input type="text" name="eTradeDate"  id="eTradeDate" class="date validate[length[0,8]]" dateFmt="yyyyMMdd"/>
						</td>
						</tr>
						<tr>
						<td colspan="10">
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
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5" >
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
									width : '18%',
									align : 'center'
								},{
									display : '产品名称',
									name : 'prodName',
									width : '20%',
									align : 'center'
								},{
									display : '资金类型',
									name : 'fundType',
									width : '17%',
									align : 'center',
									type:'D_FUND_TYPE'
								},{
									display : '金额(元)',
									name : 'amt',
									width : '15%',
									align : 'center'
								},{
									display : '起始日期',
									name : 'bTradeDate',
									width : '15%',
									align : 'center'
								},{
									display : '结束日期',
									name : 'eTradeDate',
									width : '15%',
									align : 'center'
								}],
						url : '<%=path%>/acPurchaseAndRedem/findPage.htm',
						sortName : '',
						rownumbers : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	 $.quiDefaults.Grid.formatters['D_FUND_TYPE'] = function(value, column) {
			if (value == "1") {
				return "申购";
			}else if (value == "2") {
				return "赎回";
			}else{
				return "净申购";
			}
		 };
 }
</script>
</body>
</html>
