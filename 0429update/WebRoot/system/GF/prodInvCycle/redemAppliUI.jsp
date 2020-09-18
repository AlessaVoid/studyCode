<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<title></title>
<%@include file="/common/common_list.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	var grid = null;
	function initComplete() {
		top.Dialog.close();
		grid = $("#dataBasic").quiGrid({
			columns : [
			{
				display : '产品代码',
				name : 'prodCode',
				width : '20%',
				align : 'center',
				frozen : true
			}, {
				display : '产品名称',
				name : 'prodName',
				width : '20%',
				align : 'center',
				frozen : true
			}, {
				display : '投资周期结束日',
				name : 'eTradeDate',
				align : 'center',
				width : "30%",
				frozen : true
			}, {
				display : '赎回申请份额合计',
				name : 'tradeAccount',
				align : 'center',
				width : "30%",
				frozen : true
			} ],
			url : '<%=path%>/webProdInvCycleInfo/serchRedemAppli.htm',
			sortName : '',
			rownumbers : true,
			checkbox : true,
			height : '100%',
			width : "100%",
			pageSize : 10,
			toolbar : {
				items : [
					${btnList}
				]
			}
		});
	}
</script>
</head>
<body>
	<!-- 查询位置 -->
	<div class="box2_custom" boxType="box2" panelTitle="查询条件"
		id="searchPanel">
		<form action="<%=path%>/webProdInvCycleInfo/serchRedemAppli.htm" id="queryForm"
			method="post">
			<table class="tableStyle" mode="list" formMode="line"
				style="width: 97%;">
				<tr>
					<td width="16%" align="right">产品代码：</td>
					<td width="16%">
						<div class="suggestion" name="prodCode" matchName="prodCode"
							url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode"
							suggestMode="remote"></div>
					</td>
					<td width="16%">产品名称：</td>
					<td width="16%">
						<div class="suggestion" name="prodName" matchName="prodName"
							url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodName"
							suggestMode="remote"></div>
					</td>
					<td>投资周期结束日:</td>
					<td><input type="text" name="invCycleEndDate"
						id="invCycleEndDate" class="date validate[required]"
						dateFmt="yyyyMMdd" value="" /> <span class="star">*</span></td>
				</tr>

				<tr>
					<td colspan="8">
						<div align="center">
							<button type="button" onclick="check()">
								<span class="icon_find">查询</span>
							</button>
							<button type="button" onclick="resetSearch()">
								<span class="icon_reload">重置</span>
							</button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- Grid位置 -->
	<div class="box2_custom" boxType="box2" panelTitle="数据列表"
		class="padding_right5">
		<div id="dataBasic"></div>
	</div>
	<script type="text/javascript">
		function check(){
			var valid = $("#queryForm").validationEngine( {
				returnIsValid : true
			});
			if(valid){
				searchHandler();
			}else{
				top.Dialog.alert("验证未通过！");
			}
		}
	</script>
</body>
</html>
