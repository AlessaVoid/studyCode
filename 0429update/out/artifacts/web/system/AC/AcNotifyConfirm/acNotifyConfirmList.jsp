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
				<input  name="tradeKindCode" value="${ tradeKindCode}" id="tradeKindCode" type="hidden"/>
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
						<td>
							交易日期：
						</td>
						<td>
						<input name="tradeDate1" id="tradeDate1" class="date" dateFmt="yyyyMMdd" maxlength="8" type="text" />
						</td>
					</tr>
					<tr>
						<td>(成本)是否到账：</td>
						<td>
						<dic:select dicType="DZ_FLAG" name="costAccFlag"></dic:select>
						</td>
						<td>(收益)是否到账：</td>
						<td>
						<dic:select dicType="DZ_FLAG" name="profitAccFlag"></dic:select>
						</td>
						<td>处理状态：</td>
						<td>
						<dic:select dicType="D_DEAL_STATUS" name="dealStatus"></dic:select>
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
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5" >
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
									display : '批次号',
									name : 'batchSerial',
									width : '10%',
									align : 'center'
									},{
									display : '交易日期',
									name : 'tradeDate',
									width : '10%',
									align : 'center',
									render:function(rowdata){
										return formatDate(rowdata.tradeDate,'yyyyMMdd');
									}
								},{
									display : '产品代码',
									name : 'prodCode',
									width : '10%',
									align : 'center'
								},{
									display : '产品名称',
									name : 'prodName',
									width : '10%',
									align : 'center'
								},{
									display : '划款类型',
									name : 'tradeKindCode',
									width : '10%',
									align : 'center',
									type:'TRADE_KIND'
								},{
									display : '本金(元)',
									name : 'cost',
									width : '10%',
									align : 'center'
								},{
									display : '客户收益(元)',
									name : 'profit',
									width : '10%',
									align : 'center'
								},{
									display : '(成本)是否到账',
									name : 'costAccFlag',
									width : '10%',
									align : 'center',
									type:'DZ_FALG'
								},{
									display : '(收益)是否到账',
									name : 'profitAccFlag',
									width : '10%',
									align : 'center',
									type:'DZ_FALG'
								},{
									display : '处理状态',
									name : 'dealStatus',
									width : '10%',
									align : 'center',
									type:'DEAL_STATUS'
								}],
						url : '<%=path%>/acNotifyConfirm/findPage.htm',
						sortName : 'tradeDate',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						params:[{name:"tradeKindCode",value:$("#tradeKindCode").val()}],
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	 $.quiDefaults.Grid.formatters['DZ_FALG'] = function(value, column) {
			if (value == "0") {
				return "未到账";
			}else{
				return "已到帐";
			}
		 };
		 $.quiDefaults.Grid.formatters['TRADE_KIND'] = function(value, column) {
				if (value == "124") {
					return "赎回款";
				}else if(value == "151"){
					return "到期款";
				}else{
					return "分红款";
				}
		};
	 $.quiDefaults.Grid.formatters['DEAL_STATUS'] = function(value, column) {
			if (value == "0") {
				return "未处理";
			}else if(value == "1"){
				return "划款处理中";
			}else if(value == "2"){
				return "处理成功";
			}else{
				return "处理失败";
			}
	};
}
//确认-申请
function onApply() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		if(rows[0].dealStatus=='1'){
			top.Dialog.alert("划款处理中!");
		}else if(rows[0].dealStatus=='2'){
			top.Dialog.alert("已处理成功!");
		}else{
		var costAccFlag=rows[0].costAccFlag;
		var profitAccFlag=rows[0].profitAccFlag;
		var accountingFlag=rows[0].accountingFlag;
		var tradeKindCode=rows[0].tradeKindCode;
		if(accountingFlag=='02'){//表外
		if(costAccFlag=='0'&&profitAccFlag=='0'){
			top.Dialog.confirm('本金与收益均未到账，是否继续?',function() {
			showDialog("<%=path%>/acNotifyConfirm/apply.htm?&tradeDate=" + rows[0].tradeDate+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,"到账确认申请信息",500,400);
			});
		}else if(costAccFlag=='1'&&profitAccFlag=='0'){
			top.Dialog.confirm('收益均未到账，是否继续?',function() {
				showDialog("<%=path%>/acNotifyConfirm/apply.htm?&tradeDate=" + rows[0].tradeDate+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,"到账确认申请信息",500,400);
				});
		}else if(costAccFlag=='0'&&profitAccFlag=='1'){
			top.Dialog.confirm('本金未到账，是否继续?',function() {
				showDialog("<%=path%>/acNotifyConfirm/apply.htm?&tradeDate=" + rows[0].tradeDate+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,"到账确认申请信息",500,400);
				});
		}else{
			showDialog("<%=path%>/acNotifyConfirm/apply.htm?&tradeDate=" + rows[0].tradeDate+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,"到账确认申请信息",500,400);
			}
		}else{
			if(costAccFlag=='0'){
				if(tradeKindCode!='143'){
					top.Dialog.alert("保本型产品本金未到账,不可进行到账确认-申请！");
				}else{
					top.Dialog.confirm('本金均未到账，是否继续?',function() {
						showDialog("<%=path%>/acNotifyConfirm/apply.htm?&tradeDate=" + rows[0].tradeDate+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,"到账确认申请信息",500,400);
					});
				}
			}else if(profitAccFlag=='0'){
				top.Dialog.confirm('收益均未到账，是否继续?',function() {
					showDialog("<%=path%>/acNotifyConfirm/apply.htm?&tradeDate=" + rows[0].tradeDate+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,"到账确认申请信息",500,400);
				});
			}else{
				showDialog("<%=path%>/acNotifyConfirm/apply.htm?&tradeDate=" + rows[0].tradeDate+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,"到账确认申请信息",500,400);
			}
			
		}
	}
		}
}
</script>
</body>
</html>
