<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form webReviewMain="<%=path%>/webReviewMain/approvalHistoryList.htm" id="queryForm" method="post">
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="16%" align="right">
							复核状态：
						</td>
						<td width="20%">
							<dic:select selWidth="127" dicType="D_APPSTATUS" name="repStatus"></dic:select>
						</td>
						<td width="16%" align="right">
							开始时间：
						</td>
						<td width="17%">
							<input type="text" id="appBeginDate" name="appBeginDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td>截止时间：</td>
						<td>
							<input type="text" id="appEndDate" name="appEndDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
					</tr>
					<tr>
						<td width="16%" align="right">
							复核类型：
						</td>
						<td width="20%">
							<dic:select selWidth="127" dicType="D_APPTYPE" name="appType"></dic:select>
						</td>
						<td>查询类型：</td>
						<td>
							<select id="queryType" selWidth="127" name="queryType">
								<option>请选择</option>
								<option value="0">申请记录</option>
								<option value="1">复核记录</option>
							</select>
						</td>
						<td>复核代码：</td>
						<td><input type="text" name="appNo"/></td>
					</tr>
					<tr>
						<td colspan="4"><div></div></td>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="searchHandler()">
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
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
		<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	top.Dialog.close();

	grid = $("#dataBasic").quiGrid(
			{
				columns : [
						{
							display : '交易描述',
							align : 'center',
							width : '20%',
							render: function(rowdata){
								return '<a href="#" onclick="onView(' + "'" +rowdata.appNo+"'"+')">'+rowdata.operDescribe+'</a>';
							}
						},
						{
							display : '维护代码',
							name : 'operNo',
							align : 'center',
							width : '15%'
						},
						{
							display : '维护名称',
							name : 'operName',
							align : 'center',
							width : '25%'
						},
						{
							display : '申请日期',
							name : 'appDate',
							align : 'center',
							width : '15%'
						},
						{
							display : '维护类型',
							name : 'appType',
							align : 'center',
							type : 'APP_TYPE',
							width : '15%'
						},
						{
							display : '复核状态',
							name : 'repStatus',
							align : 'center',
							type : 'REP_STATUS',
							width : '10%'
						},
						{
							display : '操作',
							isAllowHide : false,
							align : 'center',
							width : 100,
							render : function(rowdata, rowindex, value, column) {
								var revocation = "";//操作按钮（撤销、重编辑、完成）
								var closediv = "";//关闭按钮
								//判断当前用户是否申请用户，只有申请用户可以做撤销、重编辑等操作
								var currentOper = "${sessionScope.sessionUser.opercode}";
								var appUser = rowdata.appUser;
								//自己提交的复核记录显示操作、关闭按钮，可做撤销、重编辑、关闭等操作；别人提交自己复核的记录只显示查看按钮
								if(currentOper==appUser){
									var repStatus = rowdata.repStatus;
									if(repStatus=="1"){//待复核的时候显示撤销按钮
										revocation = '<span class="img_remove hand" title="撤销" onclick="onRevocation(' + "'" +rowdata.appNo+"'" + ',' + "'" +rowdata.repStatus+"'" + ')"></span>';
									}
									else if(repStatus=="3" || repStatus=="5"){//复核通过或关闭的记录
										revocation = '<span class="img_ok hand" title="复核结束"></span>';
									}
									else{//待复核或撤销状态的记录
										//如果是删除申请，或者没有重编辑地址的记录，显示结束按钮
										if(rowdata.appType=="2" || rowdata.reeditUrl==null || ""==rowdata.reeditUrl){
											revocation = '<span class="img_ok hand" title="复核结束"></span>';
										}
										else {
											revocation = '<span class="img_reply hand" title="重编辑" onclick="onEdit(' + "'" +rowdata.appNo+"'" + ','+"'"+rowdata.operDescribe+"'"+','+"'"+rowdata.appType+"'"+')"></span>';
										}
									}
								}
								closediv = '<span class="img_no hand" title="关闭" onclick="onClose(' + "'" +rowdata.appNo+"'" + ',' + "'" +rowdata.repStatus+"'" + ')"></span>';
								/* return '<div class="padding_top4 padding_left5">' + detail +'&nbsp;'+ revocation + '&nbsp;' + closediv + '</div>'; */
								return '<div class="padding_top4 padding_left5">'+ revocation + '&nbsp;' + closediv + '</div>';
							}
						} ],
				url : '<%=path%>/webReviewMain/approvalHistoryList.htm',
				sortName : 'appDate',
				rownumbers : true,
				checkbox : true,
				height : '100%',
				width : "100%",
				pageSize : 10,
				percentWidthMode : true,

				toolbar : {
					items : [
					         ${btnList}
					         ]
				}
			});

	$.quiDefaults.Grid.formatters['REP_STATUS'] = function(value, column) {
		if (value == "1") {
			return "待复核";
		} else if (value == "2") {
			return "撤销申请";
		} else if (value == "3") {
			return "复核通过";
		} else if (value == "4"){
			return "复核未通过";
		} else if (value == "5"){
			return "关闭申请";
		}
	};
	$.quiDefaults.Grid.formatters['APP_TYPE'] = function(value, column) {
		if (value == "0") {
			return "新增";
		} else if (value == "1") {
			return "修改";
		} else  if (value == "2"){
			return "删除";
		} 
	};
	//监听查询框的回车事件
	$("#searchInput").keydown(function(event) {
		if (event.keyCode == 13) {
			searchHandler();
		}
	});

	$("#searchPanel").bind("stateChange", function(e, state) {
		grid.resetHeight();
	});
}
//查看详细
function onInfo(){
	var rows = grid.getSelectedRows();
	if(selectOneRow(grid) == true){
		showDialog("<%=path%>/webReviewMain/infoUI.htm?appNo="+rows[0].appNo,"详细信息",700,500);
	}
}



function setSize(Width,Height) {
	diag.setSize(Width,Height);
}
var diag = new top.Dialog();

//查看
function onView(appNo) {
	diag.URL = "<%=path%>/webReviewMain/infoUI.htm?appNo=" + appNo;
	diag.Title = "详细信息";
	diag.Width = 700;
	diag.Height = 500;
	diag.show();
}
//撤销申请
function onRevocation(appNo,repStatus) {
	if("2"==repStatus){
		top.Dialog.alert("该记录已撤销");
		return ;
	}
	top.Dialog.confirm("是否撤销该申请记录?", function() {
		$.post("<%=path%>/webReviewMain/updateWebReviewMainInfo.htm",{"appNo" : appNo,"type":"revocation"},
			function(result) {
				if (result.success == "true"|| result.success == true) {
					top.Dialog.alert(result.msg,function() {
						top.frmright.refresh(true);
						top.Dialog.close();
					});
				} else {
					top.Dialog.alert(result.msg);
				}
		}, "json");
	});
}
//关闭申请
function onClose(appNo,repStatus) {
	if("5"==repStatus){
		top.Dialog.alert("该记录已关闭");
		return ;
	}else if("3"==repStatus){
		top.Dialog.alert("该记录已通过复核");
		return ;
	}
	top.Dialog.confirm("是否关闭该申请记录?", function() {
		$.post("<%=path%>/webReviewMain/updateWebReviewMainInfo.htm",{"appNo" : appNo,"type":"close"},
			function(result) {
				if (result.success == "true"|| result.success == true) {
					top.Dialog.alert(result.msg,function() {
						top.frmright.refresh(true);
						top.Dialog.close();
					});
				} else {
					top.Dialog.alert(result.msg);
				}
		}, "json");
	});
}

//强制关闭
function onForceClose() {
	showDialog("<%=path%>/system/PM/webReviewMain/ForceClose.jsp","强制关闭",450,150);
}

//重申请
function onEdit(rowid,operDescribe,appType) {
	diag.ID="d1";
	diag.URL = "<%=path%>/webReviewMain/seachWebReviewMain.htm?appNo=" + rowid + "&urlType=reEdit";
	diag.Title = "重编辑页面";
	diag.Width = 600;
	diag.Height = 360;
	diag.show();
}

//查询
function searchHandler() {
	if($("#appBeginDate").val() != "" && $("#appEndDate").val() != ""){
		if ($("#appBeginDate").val()-$("#appEndDate").val()>0) {
			top.Dialog.alert("开始日期应小于等于结束日期");
		}else{
			searchHandlerBasic();
		}
	}else{
		searchHandlerBasic();
	}
}
//查询
function searchHandlerBasic() {
	var query = $("#queryForm").formToArray();
	grid.setOptions( {
		params : query
	});
	//页号重置为1
	grid.setNewPage(1);
	grid.loadData();//加载数据
}
//重置查询
function resetSearch() {
	$("#queryForm")[0].reset();
	$('#search').click();
}

//刷新表格数据并重置排序和页数
function refresh(isUpdate) {
	if (!isUpdate) {
		//重置排序
		grid.options.sortName = 'appDate';
		grid.options.sortOrder = "desc";
		//页号重置为1
		grid.setNewPage(1);
	}
	grid.loadData();
}
</script>
	</body>
</html>
