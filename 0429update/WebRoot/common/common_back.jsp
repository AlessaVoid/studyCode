<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<html>
	<!--框架必需start-->
	<script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
	<script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
	<style>
	.none {
		display: none;
	}
	</style>
	<!--框架必需start-->
	<script type="text/javascript" src="${path}/libs/js/jquery.js"></script>
	<script type="text/javascript" src="${path}/libs/js/language/cn.js"></script>
	<script type="text/javascript" src="${path}/libs/js/framework.js"></script>
	<link href="${path}/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" id="skin" prePath="${path}/" scrollerY="false" />
	<link rel="stylesheet" type="text/css" id="customSkin" />
	<!--框架必需end-->
	<!--数据表格start-->
	<script src="${path}/libs/js/table/quiGrid.js" type="text/javascript"></script>
	<!--数据表格end-->
	<!--表单异步提交start-->
	<script src="${path}/libs/js/form/form.js" type="text/javascript"></script>
	<!--表单异步提交end-->
 	<!-- 日期选择框start -->
	<script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
	<!-- 日期选择框end -->
	</head>
	<script>
//####################################################################################################################
//Ajax全局变量
//####################################################################################################################
var jQ = $;
var path = '<%=path%>';
jQ.ajaxSetup( {
			type : "POST",
			dataType : 'json',
			beforeSend : function(XMLHttpRequest) {
				if (this.url.indexOf("?") != -1) {
					this.url = this.url + "&number=" + Math.random() + "";
				} else {
					this.url = this.url + "?number=" + Math.random() + "";
				}

				if (this.id) {
					var $this = $("#" + this.id + "");
					$("#" + this.id + "~div").length > 0 ? $(
							"#" + this.id + "~div").show() : "";
					$this
							.after('<div class="om-widget-overlay" style="position:absolute;left:'
									+ ($this.position().left)
									+ 'px;top:'
									+ ($this.position().top)
									+ 'px;width:'
									+ $this.outerWidth(true)
									+ 'px;height:'
									+ $this.outerHeight(true)
									+ 'px;"/><div class="ajaxMask" style="position:absolute;left:'
									+ ($this.position().left + $this.width()
											/ 2 - 30)
									+ 'px;top:'
									+ ($this.position().top + $this.height()
											/ 2 - 6) + 'px;">请稍等...</div>');
				}
			},
			complete : function(xhr, settings) {
				if (xhr.success == "noLogin") {
					alert("您未登陆或者会话超时，将为您转到登陆页面");
					location.replace(path + "<%=path%>/system/login/login.jsp");
				}
				this.id ? $(
						"#" + this.id + "~div.ajaxMask,#" + this.id
								+ "~div.om-widget-overlay").hide() : "";
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.responseText == "noLogin") {
					alert("您未登陆或者会话超时，将为您转到登陆页面");
					location.replace(path
							+ "<%=path%>/system/touchdesk/login.jsp");
					return;
				} else if (XMLHttpRequest.responseText == "noPower") {
					alert("没有交易权限");
					location.replace(path + "<%=path%>/error.htm?msg=noPower");
					return;
				}
				if (textStatus == 'timeout') {
					alert("交易超时！超时时间:10S。");
				} else if (XMLHttpRequest.res == 'parsererror') {
					alert("返回数据异常！请查看交易日志！");
				} else if (textStatus == 'parsererror') {
					alert("返回数据异常！请查看交易日志！");
				} else {
					alert("交易处理失败！请查看交易日志！");
				}
				$("#" + this.id + "~div").hide();
			}
		});
//####################################################################################################################
//Grid全局方法
//####################################################################################################################
//处理查询按钮
function searchHandler() {
	var query = $("#queryForm").formToArray();
	//alert(JSON.stringify(query))
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
//禁用回车键
document.onkeypress=function(){
   if(event.keyCode==13){
	   return false;
	}
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
//删、改、查之前检查选中项
function validateCheck(grid){
	var rows = grid.getSelectedRows();
	var rowsLength = rows.length;
	if (rowsLength == 0) {
		top.Dialog.alert("请选中需要维护的记录!");
		return;
	} else if (rowsLength > 1) {
		top.Dialog.alert("只能维护一条记录");
		return;
	}
}
//弹出增、删、改、查框
function showDialog(url,title,width,height){
	if(width == ''){
		width = 600;
	}
	if(height == ''){
		height == 480;
	}
	top.Dialog.open({
		URL : url,
		Title : title,
		Width : width,
		Height : height
	});
}





//####################################################################################################################
//编辑页面按钮、公共方法
//####################################################################################################################
$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
	//‘查询’按钮
	$(".queryButton").addClass("button");
	$(".queryButton").append("<span class='icon_find'>查询</span>");
	//‘重置’按钮
	$(".resetButton").addClass("button");
	$(".resetButton").append("<span class='icon_reload'>重置</span>");
	//‘保存’按钮
	$(".saveButton").addClass("button");
	$(".saveButton").append("<span class='icon_save'>保存</span>");
	//‘取消’按钮
	$(".cancelButton").addClass("button");
	$(".cancelButton").append("<span class='icon_no'>取消</span>");
	//首页
	$(".firstButton").addClass("button");
	$(".firstButton").append("<span class='icon_page_prev'>上一页</span>");
	$(".firstButton").attr("disabled", "disabled");
	//末页
	$(".lastButton").addClass("button");
	$(".lastButton").append("<span class='icon_page_next'>下一页</span>");
	$(".lastButton").attr("disabled", "disabled");
	//上一页
	$(".upButton").addClass("button");
	$(".upButton").append("<span class='icon_page_prev'>上一页</span>");
	$(".upButton").click(
	function() {
		$('.basicTabModern').basicTabModernSetIdx(parseInt($('.basicTabModern').attr('selectedIdx')) - parseInt(1));
	});

	//下一页
	$(".downButton").addClass("button");
	$(".downButton").append("<span class='icon_page_next'>下一页</span>");
	$(".downButton").click(
	function() {
		$('.basicTabModern').basicTabModernSetIdx(parseInt($('.basicTabModern').attr('selectedIdx')) + parseInt(1));
	});

	//同意按钮宽度
	$(".button").css("width", "90px");
	$(".button").css("cursor", "pointer");

	$(".txt").addClass("trans_bg");
	$(".txt").css("border-width", "0");
	$(".txt").css("width", "118px");
	$(".txt").attr("readonly", "readonly");
	//$(".selectbox").css("width", "98px");
});
//提交表单公共方法
function doSubmit(form,url){
	var valid = $("#"+form).validationEngine( {
		returnIsValid : true
	});
	if (valid) {
		$(".money").each(function(){
			rmoney(this);
		});
		$.post(url, $("#"+form).serialize(), function(result) {
			if (result.success == "true" || result.success == true) {
				top.Dialog.alert(result.msg, function() {
					top.frmright.window.location.reload(true);
					top.Dialog.close();
				});
			} else {
				$(".money").each(function(){
					fmoney(this);
				});
				top.Dialog.alert(result.msg);
			}
		}, "json");
	}else{
		top.Dialog.alert("验证未通过！");
	}
}

//取消编辑公共方法
function cancel() {
	top.Dialog.confirm("数据尚未保存，是否退出?|取消确认", function() {
		top.Dialog.close();
	});
}

</script>