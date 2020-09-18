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
						<td width="12%" align="right">
							产品代码：
						</td>
						<td width="20%">
							<div class="suggestion" name="prodCode" matchName="prodCode" 
									url="<%=path%>/webProdBaseInfo/selectWebProdBaseInfo.htm?type=prodCode" id ="prodCode"suggestMode="remote"></div>
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
						columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '15%',
									align : 'center',
									frozen:true
								},{
									display : '产品名称',
									name : 'prodName',
									width : '20%',
									align : 'center',
									frozen:true
								},{
									display : '产品成立日期',
									name : 'prodBeginDate',
									width : '15%',
									align : 'center'
								},{
									display : '产品终止日期',
									name : 'prodEndDate',
									width : '15%',
									align : 'center'
								},{
									display : '期限(天)',
									name : 'prodDurationDays',
									width : '15%',
									align : 'center'
								},{
									display : '下年度存续期',
									name : 'openStatus',
									width : '15%',
									align : 'center',
				            	    render : function(rowdata) {
					           		    if(rowdata.openStatus == "1") {
					           			   return "已生成";
					           			}else if (rowdata.openStatus == "2"){
					           			   return "未生成";
					           			}else if (rowdata.openStatus == "3"){
					           			   return "已生成待审核";
					           			}
				           		   }
								}],
						url : '<%=path%>/webProdOpenDurationInfo/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	function formatRound(num,dp){
		 return Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
}


//即时编辑输入格式化	
function fmtText(){
if($(".l-grid-editor .textinput_click").size()>0){
	//获取maxlength属性用于确定是否收益率输入框
	var length = $(".l-grid-editor .textinput_click").attr("maxlength");
	if(length == 16){
		// 输入文本在右边界对齐
		$(".l-grid-editor .textinput_click").css("text-align", "right");
		// 清除文本中的','分隔符
		rmoney1($(".l-grid-editor .textinput_click")[0]);
		// 为输入框绑定失焦时间
		$(".l-grid-editor .textinput_click").bind("blur", function() {
			// 清除文本中的'-'号
			var value = $(this).val().replace(/-/gi, "");
			if(value == ""){
				$(this).val(0);
			}else{
				$(this).val(value);
			}
			rmoney(this);
			fmoney(this,2);
		});
	}
}
$(".l-grid-editor .textinput_click").blur(function(){
	durationGrid.endEdit();
});
}
//生成下年度存续期
function onCreate() {
	var rows = grid.getSelectedRows();
	if(rows.length == 1){
		var openStatus = rows[0].openStatus;
		if(openStatus == '1'){
			top.Dialog.alert("存续期已生成！");
			return;
		}
		if(openStatus == '3'){
			top.Dialog.alert("存续期正在审核中！");
			return;
		}
		var prodCode = rows[0].prodCode;
		showDialog("<%=path%>/webProdOpenDurationInfo/createUI.htm?prodCode="+prodCode, "存续期生成",1280,680);
	}else{
		top.Dialog.alert("请选择一条记录！");
	};
}
//查询 
function onInfo() {
	var rows = grid.getSelectedRows();
	var rowsLength = rows.length;
	if (rowsLength == 0) {
		top.Dialog.alert("请选择一条记录！");
		return;
	} else if (rowsLength > 1) {
		top.Dialog.alert("请选择一条记录！");
		return;
	} else {
		var url="<%=path%>/webProdOpenDurationInfo/InfoUI.htm?prodCode="+rows[0].prodCode+"&openStatus"+rows[0].openStatus;
		showDialog(url,"查看存续期",1080,720);
	}
}


</script>
</body>
</html>
