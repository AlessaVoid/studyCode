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
			<input  name="profitType" value="${profitType }" id="profitType" type="hidden"/>
			<input  name="tradeKindCode" value="${tradeKindCode }" id="tradeKindCode" type="hidden"/>
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
						<td>
							资金类型：
						</td>
						<td>
						<dic:select dicType="D_CAPITAL_TYPE" name="capitalType"></dic:select>
						</td>
						<td>批处理日期：</td>
						<td>
							<input type="text" name="batchDate"  id="batchDate" class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd"/>
						</td>
						<td>是否到账：</td>
						<td>
						<dic:select dicType="DZ_FLAG" name="accFlag"></dic:select>
						</td>
						</tr>
						<tr>
						<td colspan="8">
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
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表  单位（万）" class="padding_right5" >
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
									width : '13%',
									align : 'center'
								},{
									display : '产品名称',
									name : 'prodName',
									width : '15%',
									align : 'center'
								},{
									display : '资金类型',
									name : 'capitalType',
									width : '13%',
									align : 'center',
									type:'D_CAPITAL_TYPE'
								},{
									display : '币种',
									name : 'currency',
									width : '9%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="156"){
										return "人民币";
										}
									if(value=="826"){
										return "英镑";
										}
									if(value=="978"){
										return "欧元";
										}
									if(value=="840"){
										return "美元";
									}
									if(value=="392"){
										return "日元";
									}
									if(value=="036"){
										return "澳大利亚元";
									}
									if(value=="702"){
										return "新元";
									}
									if(value=="643"){
										return "卢布";
									}
									if(value=="756"){
										return "瑞士法郎";
									}
									if(value=="124"){
										return "加元";
									}
									if(value=="344"){
										return "港币";
									}
									}
								},{
									display : '金额',
									name : 'amt',
									width : '13%',
									align : 'center'
								},{
									display : '批处理日期',
									name : 'batchDate',
									width : '13%',
									align : 'center'
								},{
									display : '到账日期',
									name : 'accDate',
									width : '13%',
									align : 'center'
								},{
									display : '是否到账',
									name : 'accFlag',
									width : '13%',
									align : 'center',
									type:'DZ_FALG'
								}],
						url : '<%=path%>/gfRecvCapitalDataInfo/findPage.htm',
						sortName : '',
						rownumbers : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	 $.quiDefaults.Grid.formatters['DZ_FALG'] = function(value, column) {
			if (value == "0") {
				return "未到账";
			}{
				return "已到帐";
			}
		 };
 $.quiDefaults.Grid.formatters['D_CAPITAL_TYPE'] = function(value, column) {
		if (value == "1") {
			return "赎回本金";
		}else if(value == "2"){
			return "赎回收益";
		}else if(value == "3"){
			return "分红收益";
		}else if(value == "4"){
			return "到期本金";
		}else{
			return "到期收益";
	 };
}
 }
</script>
</body>
</html>
