<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
	</head>
	<body>
		<!-- Grid位置 -->
		<form id="form1">
			<div id="dataBasic"></div>
		</form>
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
									display : '募集起始日期(从)',
									name : 'raisingBeginDate',
									width : '8%',
									align : 'center'
								},{
									display : '募集结束日期(到)',
									name : 'raisingEndDate',
									width : '8%',
									align : 'center'
								},{
									display : '产品成立日期',
									name : 'prodBeginDate',
									width : '8%',
									align : 'center'
								},{
									display : '产品终止日期',
									name : 'prodEndDate',
									width : '8%',
									align : 'center'
								},{
									display : '期限(天)',
									name : 'prodDurationDays',
									width : '8%',
									align : 'center'
								},{
									display : '计划募集金额（单位：万）',
									name : 'planIssueAmt',
									width : '8%',
									align : 'center',
									render : function(rowdata) {
										return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.planIssueAmt,2) + '</div>';},
								},{
									display : '客户收益率%',
									name : 'profitRate',
									width : '8%',
									align : 'center'
								},{
									display : '销售费率%',
									name : 'sellRate',
									width : '8%',
									align : 'center'
								},{
									display : '托管费%',
									name : 'prodControlFeeRate',
									width : '8%',
									align : 'center'
								},{
									display : '推荐费%',
									name : 'prodRecomRate',
									width : '8%',
									align : 'center'
								},{
									display : '超额收益费率%',
									name : 'overfulfilProfit',
									width : '8%',
									align : 'center'
								},{
									display : '投资收益率%',
									name : 'prodProfitRate',
									width : '8%',
									align : 'center'
								},{
									display : '项目名称',
									name : 'prjName',
									width : '8%',
									align : 'center'
								},{ 
									display: '审批意见',
									name: 'comment',
									align: 'center', 
									width: "25%",
						               editor:{
						            	   type:"select",
						            	   url:'<%=path%>/webApproveModel/getWebApproveModel.htm',
						            	   editable:true,
						            	   detailEdit:true,
						            	   selWidth:300,
						            	   boxWidth:600
						               }
						         }],
						url : '<%=path%>/designAudit/mutiAppFindPage.htm?taskId=${taskId}',
						sortName : '',
						enabledEdit : true,
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
							   {text : '通过', click : onOk, iconClass : 'icon_ok'},
							   {line : true},
							   {text : '不通过', click : onNo, iconClass : 'icon_delete'},
							   {line : true}
							]
						}
					});
	//四舍五入算法,dp代表保留小数点位数
	function formatRound(num,dp){
	    return   Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
	$.quiDefaults.Grid.formatters['D_ASSIGN'] = function (value, column) {
		roleCode = value;
		value = "";
	    return value;
	};
}
function getGridData(){
	return grid.getSelectedRows();
}
//通过
function onOk(){
	var rows = grid.getSelectedRows();
	if(rows.length == 0){
		top.Dialog.alert("请至少选择一条记录! | 操作提示");
		return false;
	}else{
		var param = $("#form1").formToArray();
		var map = {};
		map.name = "gridData";
		map.value= JSON.stringify(grid.getSelectedRows());
		param.push(map);
		map = {};
		map.name = "isAgree";
		map.value = "1";
		param.push(map);
		$.post("<%=path%>/designAudit/mutiAuditAppCheck.htm",param,function(result) {
			if (result.success == "true" || result.success == true) {
				var lastUserType = result.msg.split("-")[1];
				if(lastUserType == true){
					$.post("<%=path%>/designAudit/mutiAudit.htm",param,function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, function() {
								top.frmright.window.location.reload(true);
								top.Dialog.close();
							});
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				}else{
					var url = "<%=path%>/designAudit/mutiAuditAppUser.htm?isAgree=1"+"&roleCodeList="+result.msg;
					top.Dialog.open({
						URL : url,
						Title : "审批信息",
						Width : "500",
						Height : "480"
					});
				}
			} else {
				top.Dialog.alert(result.msg);
			}
		}, "json");
	}
}
//不通过
function onNo(){
	var rows = grid.getSelectedRows();
	if(rows.length == 0){
		top.Dialog.alert("请至少选择一条记录! | 操作提示");
		return false;
	}else{
	//	top.Dialog.confirm("数据尚未保存，是否退出?|取消确认", function() {
	//		top.Dialog.close();
	//	});
		var param = $("#form1").formToArray();
		var map = {};
		map.name = "gridData";
		map.value= JSON.stringify(grid.getSelectedRows());
		param.push(map);
		map = {};
		map.name = "isAgree";
		map.value = "0";
		param.push(map);
		$.post("<%=path%>/designAudit/mutiAudit.htm",param,function(result) {
			if (result.success == "true" || result.success == true) {
				top.Dialog.alert(result.msg, function() {
					top.frmright.refresh(true);
					top.frmright.closeWin();//关闭第一个弹窗
					top.Dialog.close();//关闭第二个弹窗
				});
			} else {
				top.Dialog.alert(result.msg);
			}
		}, "json");
 	} 
}
</script>
</body>
</html>