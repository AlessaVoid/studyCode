<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http:/boco.com.cn/tags-dic" prefix="dic"%>
<%@taglib uri="http:/boco.com.cn/tags-fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
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
	<link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/" splitMode="true" href="<%=path%>/libs/skins/blue/style.css"/>
	<link rel="stylesheet" type="text/css" id="customSkin" href="<%=path%>/system/layout/skin/style.css"/>

	<!--框架必需end-->
	<!--表单异步提交start-->
	<script src="${path}/libs/js/form/form.js" type="text/javascript"></script>
	<!--表单异步提交end-->
	<!--数据表格start-->
	<script src="${path}/libs/js/table/quiGrid.js" type="text/javascript"></script>
	<!--数据表格end-->
 	<!-- 日期选择框start -->
	<script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
	<!-- 日期选择框end -->
	<!--自动提示框start-->
	<script src="<%=path%>/libs/js/form/suggestion.js" type="text/javascript"></script>
	<!--自动提示框end-->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="<%=path%>/libs/js/popup/drag.js"></script>
	<script type="text/javascript" src="<%=path%>/libs/js/popup/dialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="${path}/libs/js/money.js"></script>
	<!-- 树组件start -->
	<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
	<!-- 树组件end -->
	<!--表单验证start-->
	<script src="${path}/libs/js/form/validationRule.js" type="text/javascript"></script>
	<script src="${path}/libs/js/form/validation.js" type="text/javascript"></script>
	<!--表单验证end-->
	<!-- 树形下拉框start -->
	<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
	<!-- 树形下拉框end -->
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
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status == "10000") {
					top.Dialog.alert(XMLHttpRequest.responseText + " | 超时提示",function(){
						window.parent.location.href = path + "/toLogin.htm";
					});
				} else{
					top.Dialog.alert(XMLHttpRequest.responseText);
				}
			}
		});
$(function() {
	//校验当联想输入框text和value不一致时，将text的值赋给value
	$(".suggestion").bind("validate",function(e,value){
		if($(this).attr("relValue")!=$(this).attr("relText")){
			var name=$(this).attr("name");
			$("input[name='"+name+"']").val($(this).attr("relText"));
		}
	});
	//解决QUI自动提示框无法直接输入的问题
	$.each($("[id^=suggestion][id$=_input]"), function(i, v) {
		$(v).blur(function() {
			if ($(v).val() != '请输入') {
				$(v).nextAll("input[type=hidden]").val($(v).val());
			} else {
				$(v).nextAll("input[type=hidden]").val(null);
			}
		});
	});
	
});
//禁用回车键
document.onkeypress=function(){
   if(event.keyCode==13){
	   return false;
	}
}
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
	//重置多选下拉框中选中的值
	$(".selectTree").resetValue();
	//扩展click方法，重置JS动态生成的隐藏域的值
	$.each($("[id^=suggestion][id$=_input]"), function(i, v) {
		$(v).nextAll("input[type=hidden]").val(null);
	});
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
//点击删、改、查按钮检查选中项是否满足要求
function selectOneRow(grid){
	var rows = grid.getSelectedRows();
	var rowsLength = rows.length;
	if (rowsLength == 0) {
		top.Dialog.alert("请选择一条记录! | 操作提示");
		return false;
	} else if (rowsLength > 1) {
		top.Dialog.alert("只能选择一条记录! | 操作提示");
		return false;
	}
	return true;
}
//四舍五入算法,dp代表保留小数点位数
function formatRound(num,dp){
	if(isNaN(num)){
		return 0;
	}else{
		return Math.round(num* Math.pow(10,dp) )/ Math.pow(10,dp);
	}
}
//格式化费率，0.02=》2.00
//type 1：扩大2：缩小
function formatRate(num,dp,type){
	if(isNaN(num)){
		return "0.00";
	}else{
		if (1==type) {
			return num * Math.pow(10,dp);
		}else{
		return num / Math.pow(10,dp);
		}
	}
}
function formatDate(date, format) {   
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {
		"yyyy": date.getFullYear(),
		"M": date.getMonth() + 1,
		"d": date.getDate(),
		"H": date.getHours(),
		"m": date.getMinutes(),
		"s": date.getSeconds(),
		"MM": ("" + (date.getMonth() + 101)).substr(1),
		"dd": ("" + (date.getDate() + 100)).substr(1),
		"HH": ("" + (date.getHours() + 100)).substr(1),
		"mm": ("" + (date.getMinutes() + 100)).substr(1),
		"ss": ("" + (date.getSeconds() + 100)).substr(1)
	};
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
} 
//有必输项的查询页面，验证必输项，不通过不提交查询
function validateForm(containerId, okFunction, errorFunction) {
	var valid = $(containerId).validationEngine( {
		returnIsValid : true
	});
	if (valid == true) {
		if (okFunction) {
			okFunction();
		}
	} else {
		if (errorFunction) {
			errorFunction();
		}
	}
}
//比较日期大于等于
function compareDateE(beginDate, endDate){
	var d1 = parseFloat(beginDate.replace(/\-/g, "\/"));
	var d2 = parseFloat(endDate.replace(/\-/g, "\/"));
	return d1 >= d2;
}
//js对象转换url参数
var urlEncode = function (param, key, encode) {  
	  if(param==null) return '';  
	  var paramStr = '';  
	  var t = typeof (param);  
	  if (t == 'string' || t == 'number' || t == 'boolean') {  
	    paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);  
	  } else {  
	    for (var i in param) {  
	      var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);  
	      paramStr += urlEncode(param[i], k, encode);  
	    }  
	  }  
	  return paramStr.substring(1);  
	};
//文件下载兼容IE
function openDownloadDialog(url, saveName) {
	if (window.navigator.msSaveBlob) {
		try {
			//创建XMLHttpRequest对象
			var xhr = new XMLHttpRequest();
			//配置请求方式、请求地址以及是否同步
			xhr.open('POST', url, true);
			//设置请求结果类型为blob
			xhr.responseType = 'blob';
			//请求成功回调函数
			xhr.onload = function(e) {
				if (this.status == 200) {//请求成功
					//获取blob对象
					var blob = this.response;
					//获取blob对象地址，并把值赋给容器
					window.navigator.msSaveBlob(blob, saveName);
				}
			};
			xhr.send();
		}catch (e) {
			console.log(e);
		}
	}else {
		if (typeof url == 'object' && url instanceof Blob) {
			url = URL.createObjectURL(url); // 创建blob地址
		}
		var aLink = document.createElement('a');
		aLink.href = url;
		aLink.download = saveName || '';
		var event;
		if (window.MouseEvent) {
			event = new MouseEvent('click');
		}
		else {
			event = document.createEvent('MouseEvents');
			event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
		}
		aLink.dispatchEvent(event);
	}
}
</script>