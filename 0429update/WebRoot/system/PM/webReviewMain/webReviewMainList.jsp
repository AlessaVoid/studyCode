<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<!--数据表格end-->
		<!--表单异步提交start-->
		<script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
		<!--表单异步提交end-->
		<!-- 日期选择框start -->
		<script src="<%=path%>/libs/js/form/datePicker/WdatePicker.js" type="text/javascript"></script>
		<!-- 日期选择框end -->
		<!--自动提示框start-->
		<script src="<%=path%>/libs/js/form/suggestion.js" type="text/javascript"></script>
		<!--自动提示框end-->
	</head>
	<body>
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="" id="queryForm" method="post">
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td width="16%" align="right">
							维护类型：
						</td>
						<td width="20%">
							<dic:select selWidth="127"  dicType="D_APPTYPE" name="appType"></dic:select>
						</td>
						<td width="16%" align="right">
							申请开始时间：
						</td>
						<td width="17%">
							<input type="text" id="appBeginDate" name="appBeginDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td>申请结束时间：</td>
						<td>
							<input type="text" id="appEndDate" name="appEndDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
					</tr>
					<tr>
						<td>复核代码：</td>
						<td><input type="text" name="appNo"/></td>
						<td colspan="2"><div></div></td>
						
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
								return '<a href="#" onclick="onView(' + "'" +rowdata.appNo+"'" + ','+"'"+rowdata.operDescribe+"'"+','+"'"+rowdata.appType+"'"+')">'+rowdata.operDescribe+'</a>';
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
							width : '18%'
						},
						{
							display : '申请用户',
							name : 'appUser',
							align : 'center',
							width : '12%'
						},
						{
							display : '申请日期',
							name : 'appDate',
							align : 'center',
							width : '10%'
						},
						{
							display : '维护类型',
							name : 'appType',
							align : 'center',
							type : 'APP_TYPE',
							width : '10%'
						},
						{
							display : '复核代码',
							name : 'appNo',
							align : 'center',
							width : '15%'
						},
						{
							display : '操作',
							isAllowHide : false,
							align : 'center',
							width : 80,
							render : function(rowdata, rowindex, value, column) {
								var detail = '<span class="img_edit hand" title="申请/复核内容" onclick="onView(' + "'" +rowdata.appNo+"'" + ','+"'"+rowdata.operDescribe+"'"+','+"'"+rowdata.appType+"'"+')"></span>';
								return '<div class="padding_top4 padding_left5">' + detail + '</div>';
							}
						} ],
				url : '<%=path%>/webReviewMain/findPage.htm',
				sortName : 'appDate',
				rownumbers : true,
				checkbox : true,
				height : '100%',
				width : "100%",
				pageSize : 10,
				percentWidthMode : true,
				toolbar : {
					items : []
				}
			});

	$.quiDefaults.Grid.formatters['APP_TYPE'] = function(value, column) {
		if (value == "0") {
			return "新增";
		} else if (value == "1") {
			return "修改";
		} else  if (value == "2"){
			return "删除";
		} else {
			return "异常";
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

var diag = new top.Dialog();
//查看
function onView(rowid,operDescribe,appType) {
	var type = "";
	if(appType=="0"){
		type = "新增";
	}else if(appType=="1"){
		type = "修改";
	}else if(appType=="2"){
		type = "删除";
	}
	var title = type + "【" +operDescribe + "】" + "复核";
	
	diag.URL = "<%=path%>/webReviewMain/seachWebReviewMain.htm?appNo=" + rowid + "&appType="+appType + "&urlType=check";
	diag.Title = title;
	diag.Width = 1200;
	diag.Height = 700;
	diag.ShowMaxButton=true;
	diag.show();
}

function setSize(Width,Height) {
	diag.setSize(Width,Height);
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
