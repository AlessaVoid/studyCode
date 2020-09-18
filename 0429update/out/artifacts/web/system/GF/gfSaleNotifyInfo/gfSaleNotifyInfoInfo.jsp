<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<script type="text/javascript">
		function doPrint(){
			//调用打印方法
			 bdhtml = window.document.body.innerHTML;
			sprnstr = "<!--startprint-->"; //开始打印标识字符串有17个字符
			eprnstr = "<!--endprint-->"; //结束打印标识字符串
			prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17); //从开始打印标识之后的内容
			prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
			window.document.body.innerHTML = sprnstr + prnhtml + eprnstr; //把需要打印的指定内容赋给body.innerHTML
			window.print(); //调用浏览器的打印功能打印指定区域
		}
		</script>
		<script type="text/javascript">
	   	var grid = null;
	   	function initComplete() {
	   		grid = $("#dataBasic")
	   				.quiGrid(
	   						{
	   							columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '15%',
									align : 'center',
									frozen:true
								},{
									display : '产品名称',
									name : 'prodName',
									width : '25%',
									align : 'center',
									frozen:true
								},{
									display : '产品运作模式',
									name : 'prodOperModel',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "封闭式净值型";
										}
										if(value=="02"){
											return "封闭式非净值型";
										}
										if(value=="03"){
											return " 开放式净值型";
										}
										if(value=="04"){
											return " 开放式非净值型";
										}
									}
								},{
									display : '产品收益类型',
									name : 'profitType',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "保证收益";
										}
										if(value=="02"){
											return "保本浮动收益";
										}
										if(value=="03"){
											return " 非保本浮动收益";
										}
									}
								},{
									display : '产品风险等级',
									name : 'prodRiskLevel',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "一级（低）";
										}
										if(value=="2"){
											return "二级（中低）";
										}
										if(value=="3"){
											return "三级（中）";
										}
										if(value=="4"){
											return "四级（中高）";
										}
										if(value=="5"){
											return "五级（高）";
										}
									}
								},{
									display : '是否自主平衡',
									name : 'isAutoBalance',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "是";
										}
										if(value=="0"){
											return "否";
										}
									}
								}],
	   							url : '<%=path%>/gfSaleNotifyInfo/findProd.htm?notifyCode=${entity.notifyCode}&ctype=${ctype}',
	   							sortName : '',
	   							rownumbers : true,
	   							checkbox : true,
	   							height : '100%',
	   						 	usePager: false,
	   							 percentWidthMode:true,
		   						toolbar : {
		 							items : [
										{text : '下载产品说明书', click : download, iconClass : 'icon_btn_down2'},
										{text : '产品详细信息', click : onInfo, iconClass : 'icon_list'}
		 								]
		 						}
	   						});
	   		}
	   	//下载产品说明书
	   	function download(){
	   		var rows = grid.getSelectedRows();
	   		var rowsLength = rows.length;
	   		if (rowsLength == 0) {
	   			top.Dialog.alert("请选中需要操作的记录!");
	   			return;
	   		} else if (rowsLength > 1) {
	   			var prodCodes="";
	   			for ( var r in rows) {
	   				prodCodes=prodCodes+","+rows[r].prodCode;
	   			}
	   			prodCodes=prodCodes.substring(1);
	   			top.Dialog.confirm("确定要下载该说明书?",function(){
	   				window.location.href = "<%=path%>/gfSaleNotifyInfo/batchDownload.htm?prodCodes="+prodCodes;
	   			});
	   		} else {
	   			showDialog("<%=path%>/gfSaleNotifyInfo/downloadListUI.htm?prodCode=" + rows[0].prodCode,"产品说明书列表",1280,680);
	   		}
	   	}
	   	function onInfo() {
	   		if(selectOneRow(grid)){
	   			var rows = grid.getSelectedRows();
	   			showDialog("<%=path%>/prodQuery/infoUI.htm?prodCode=" + rows[0].prodCode,"产品详细信息",1280,680);
	   		}
	   	}
	   	</script>
	</head>
	<body>
		<form id="form1">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
						<td colspan="2"><div align="left">
						<button type="button" onclick="doPrint()"><span class="icon_print">打印销售通知</span></button>
					</div></td>
						</tr>
	   					<tr>
							<td align="right" width="15%;">
								销售通知：
							</td>
							<td>
							<!--startprint-->
								${entity.contentByte}
							<!--endprint-->
							</td>
						</tr>
    				</table>
    				<div id="dataBasic"></div>
    				
	   	</form>
	</body>
</html>