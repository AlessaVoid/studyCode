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
				<input  name="tradeKindCode" value="${ accountingFlag}" id="accountingFlag" type="hidden"/>
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
							产品成立日期：
						</td>
						<td>
						<input name="prodBeginDate" id="prodBeginDate" class="date" dateFmt="yyyyMMdd" maxlength="8" type="text" />
						</td>
						<td colspan="2">
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
							display : '产品代码',
							name : 'prodCode',
							width : '15%',
							align : 'center'
						},{display : '产品名称',
							name : 'prodName',
							width : '20%',
							align : 'center'
						},{
							display : '产品到期收益是否转人民币',
							name : 'isProfitFeeToRMB',
							width : '12%',
							align : 'center',
							hide : 'true'
						 },{
							display : '划款类型',
							name : 'tradeKindCode',
							width : '10%',
							align : 'center',
							hide : 'true'
						 },{display : '币种',
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
							display : '成本',
							name : 'cost',
							width : '15%',
							align : 'center'
						},{
							display : '收益',
							name : 'profit',
							width : '15%',
							align : 'center',
							render: function(rowdata){
							         if(rowdata.tradeKindCode==201){
									         if(rowdata.isProfitFeeToRMB==1){
										        if(rowdata.currency=="156"){
													return rowdata.profit;
											    }
										        if(rowdata.currency=="826"){
													return rowdata.profit+"(人民币元)";
													}
												if(rowdata.currency=="978"){
													return rowdata.profit+"(人民币元)";
													}
												if(rowdata.currency=="840"){
													return rowdata.profit+"(人民币元)";
												}
												if(rowdata.currency=="392"){
													return rowdata.profit+"(人民币元)";
												}
												if(rowdata.currency=="036"){
													return rowdata.profit+"(人民币元)";
												}
												if(rowdata.currency=="702"){
													return rowdata.profit+"(人民币元)";
												}
												if(rowdata.currency=="643"){
													return rowdata.profit+"(人民币元)";
												}
												if(rowdata.currency=="756"){
													return rowdata.profit+"(人民币元)";
												}
												if(rowdata.currency=="124"){
													return rowdata.profit+"(人民币元)";
												}
												if(rowdata.currency=="344"){
													return rowdata.profit+"(人民币元)";
												}
				                            }else{
				                               return rowdata.profit;
				                            }
				                          }else{
				                               return rowdata.profit;
				                          }
				                         }
						},{
							display : '处理状态',
							name : 'dealStatus',
							width : '20%',
							align : 'center',
							type:'DEAL_STATUS'
						}],
						url : '<%=path%>/taHkNotifyInfoF/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						params:[{name:"tradeKindCode",value:$("#tradeKindCode").val()},{name:"accountingFlag",value:$("#accountingFlag").val()}],
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
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
//查看
function onInfo() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/taHkNotifyInfoF/infoUI.htm?tradeDate=" + rows[0].tradeDate+"&accountingFlag=" + rows[0].accountingFlag+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,
				"详细信息",800,600);
	}
}
//审批
function onApply() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		if(rows[0].dealStatus=='1'){
			top.Dialog.alert("划款处理中!");
		}else if(rows[0].dealStatus=='2'){
			top.Dialog.alert("已处理成功!");
		}else{
		showDialog("<%=path%>/taHkNotifyInfoF/applyUI.htm?tradeDate=" + rows[0].tradeDate+"&accountingFlag=" + rows[0].accountingFlag+"&costAccFlag="+rows[0].costAccFlag+"&prodCode="+rows[0].prodCode+"&prodName="+rows[0].prodName+"&cost="+rows[0].cost+"&custType="+rows[0].custType+"&dealStatus="+rows[0].dealStatus+"&tradeKindCode="+rows[0].tradeKindCode,
				"审批信息",800,600);
			}
}
	}
//导出
function onExport(){
	top.Dialog.confirm('是否导出全部数据?',	function() {
			$("#queryForm").attr("action","<%=path%>/taHkNotifyInfoF/exportExcelFile.htm");
			$("#queryForm").submit();
		});									
}
//打印
function onPrint(){
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/taHkNotifyInfoF/printUI.htm?tradeDate=" + rows[0].tradeDate+"&accountingFlag=" + rows[0].accountingFlag+"&prodCode="+rows[0].prodCode+"&custType=0&tradeKindCode="+rows[0].tradeKindCode,
				"详细信息",800,600);
	}								
}
</script>
</body>
</html>
