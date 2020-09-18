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
						<td width="30%" align="right">
							产品代码：
						</td>
						<td width="30">
							<div class="suggestion validate[required]" id="gfProdCode" name="prodCode" matchName="prodCode"  
									url="<%=path%>/gfProdTradeCtl/selectProdCode.htm" suggestMode="remote"></div>
						</td>
						<td>
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
						columns : [
								{
									display : '产品代码',
									name : 'prodCode',
									width : '33%',
									align : 'center'
								},{
									display : '渠道代码',
									name : 'channelCode',
									width : '33%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="00"){
											return "柜台";
											}
										if(value=="01"){
											return "电话银行";
											}
										if(value=="02"){
											return "一卡通";
											}
										if(value=="03"){
											return "理财通";
											}
										if(value=="04"){
											return "短信银行";
											}
										if(value=="05"){
											return "深圳账户管家";
											}
										if(value=="07"){
											return "个人网银";
											}
										if(value=="08"){
											return "理财规划";
											}
										if(value=="09"){
											return "手机银行";
											}
										if(value=="10"){
											return "个人信贷";
											}
										if(value=="12"){
											return "电视银行";
											}
										if(value=="11"){
											return "公司网银";
											}
										if(value=="17"){
											return "VTM自助银行";
											}
                                        if(value=="46"){
                                            return "公司信贷";
                                        }
                                        if(value=="47"){
                                            return "公司票据";
                                        }
										if(value=="0"){
											return "全部";
											}
										}
								},{
									display : '状态',
									name : 'status',
									width : '33%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "启用";
											}
										if(value=="2"){
											return "停用";
											}
									}
								}],
						url : '<%=path%>/gfProdChannel/findPage.htm',
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
//暂停
function zupdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdChannel/zupdateUI.htm?prodCode=" + rows[0].prodCode + "&channelCode=" + rows[0].channelCode,
				"渠道交易维护暂停",500,450);
	}
}
//开启
function kUpdate() {
	if(selectOneRow(grid)){
		var rows = grid.getSelectedRows();
		showDialog("<%=path%>/gfProdChannel/kUpdateUI.htm?prodCode=" + rows[0].prodCode + "&channelCode=" + rows[0].channelCode,
				"渠道交易维护开通",500,450);
	}
}
//全部暂停
function onZUpdate() {
		var prodCode = $("#gfProdCode").val();
		if(grid.getData().length!=0){
			showDialog("<%=path%>/gfProdChannel/onZUpdateUI.htm?prodCode=" + prodCode,
					"渠道交易维护全部暂停",500,450);
		}else{
			top.Dialog.alert("数据列表没有产品不能全部暂停！");
		}
}
//全部开启
function onKUpdate() {
	var prodCode = $("#gfProdCode").val();
		if(grid.getData().length!=0){
			showDialog("<%=path%>/gfProdChannel/onKUpdateUI.htm?prodCode=" + prodCode,
				"渠道交易维护全部开通",500,450);
		}else{
			top.Dialog.alert("数据列表没有产品不能全部开启！");
		}
}
</script>
</body>
</html>
