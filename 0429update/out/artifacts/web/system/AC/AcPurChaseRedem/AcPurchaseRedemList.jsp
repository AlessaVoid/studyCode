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
						<td width="6%" align="right">
							产品代码：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" suggestMode="remote"></div>
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
									width : '20%',
									align : 'center'
								},{
									display : '开放类型',
									name : 'fundType',
									width : '20%',
									align : 'center',
									type:'D_FUND_TYPE'
								},{
									display : '开始日期',
									name : 'bTradeDate',
									width : '20%',
									align : 'center'
								},{
									display : '结束日期',
									name : 'eTradeDate',
									width : '20%',
									align : 'center'
								},{
									display : '申购金额/赎回份额',
									name : 'tradeAccount',
									width : '20%',
									align : 'center',
									render : function(rowdata) {
					           		    if(rowdata.fundType == '1') {
					           		      if(rowdata.tradeAmt==null){
					           		    	return "-";
					           		      }else{
					           		    	return rowdata.tradeAmt;
					           		      }
					           			}else if(rowdata.fundType == '2'){
					           			  if(rowdata.tradeQuot==null){
					           				return "-";
					           			  }else{
					           				return rowdata.tradeQuot;
					           			  }
					           			}else{
					           				return "-";
					           			}
				           		   }  
								}],
						url : '<%=path%>/acPurchaseRedem/findPage.htm',
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
				return "-";
			}
		 };
 }
</script>
</body>
</html>
