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
						<td width="16%">
							产品名称：
						</td>
						<td width="12%">
							<div class="suggestion" name="prodName" matchName="prodName" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodName" suggestMode="remote"></div>
						</td>
						<td width="16%">产品成立日期：</td>
						<td>
							<input type="text" name="prodBeginDate"  id="prodBeginDate" class="date validate[required,length[0,8]]" dateFmt="yyyyMMdd"/>
						</td>
						<td>产品状态：</td>
					<td>
					<div class="selectTree" url="<%=path%>/gfDict/getPordStatusTreeDic.htm?dicType=PROD_STATUS" multiMode="true" allSelectable="true" exceptParent="true" name="prodStatus" selectedValue="${prodStatus }" params='{"prodStatus":"${prodStatus}"}'></div>
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
									width : '15%',
									align : 'center'
								},{
									display : '产品名称',
									name : 'prodName',
									width : '20%',
									align : 'center'
								},{
									display : '产品成立日',
									name : 'prodBeginDate',
									width : '15%',
									align : 'center'
								},{
									display : '币种',
									name : 'currency',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="156"){
										return "人民币元";
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
									width : '15%',
									align : 'center'
								},{
									display : '产品状态',
									name : 'prodStatus',
									width : '20%',
									align : 'center',
									type : 'PROD_STATUS'
								}],
						url : '<%=path%>/acProdRaiseAmt/findPage.htm',
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
	$.quiDefaults.Grid.formatters['PROD_STATUS'] = function (value, column) {
		if(value == '1'){
	    	return '成立';
		}else if (value == '5'){
	    	return '理财产品已到期';
		}else if(value=='4'){
			return '不成立';
		}
	};
 }
</script>
</body>
</html>
