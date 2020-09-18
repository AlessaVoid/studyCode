<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
<style type="text/css"> 
.checkbox{vertical-align:middle; margin-top:0;} 
.zuo {
    float: left;
    width: 180px;
}
</style> 
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>发售区域参数</title> 
		<script type="text/javascript">
		$(function() {
			//修正由于使用了tab导致高度计算不准确
			if (broswerFlag == "IE6") {
				setTimeout(function() {
					top.iframeHeight('frmrightChild');
				}, 500);
			}
			createCheckBox();
			var flag = $("#flag").val();
			if(flag == 'false'){
				selectAllItem();
				$("#organ-all").attr("checked",true);
			}
		})
		function getCheckValue(){
		    var msg = "";
		    $("input:checkbox[name=provCode]").each(function(){
		        if($(this).attr("checked")){
		            msg += "," + $(this).val();
		        }
		    });
		    if(msg == ""){
		        msg = "无";
		    }else{
		        msg = msg.substring(1);
		    }
		    return msg;
		}


		function createCheckBox(){
			var provCode = ${provCode};
			if(provCode != null){
				$("#branchCodes").after(provCode);
			}
			$("#branchCodes").render();
		}
		
		function sub() {
			var valid = $("#form1").validationEngine( {
				returnIsValid : true
			});
			if (valid) {
				if(getCheckValue() == '无'){
					top.Dialog.alert("请选择发售区域");
				}else{
					top.Dialog.confirm("确定要保存操作吗?|操作提示",function() {
					    $.post("<%=path%>/webResearchAppInfo/saveIssueAreaInfo.htm", {
					        "branchCodes": getCheckValue(),
					        "prodCode": $("#prodCode").val()
					    },
					    function(result) {
					        if (result.success == "true" || result.success == true) {
					            top.Dialog.alert(result.msg,function() {
									parent.setBaseInfo(result.bean);
									if($("#index").val() != $("#oldIndex").val()){
										parent.changeDiv($("#index").val(),$("#oldIndex").val());
									}else{
										parent.changeDiv($("#index").val(),$("#index").val());
									}
					            });
					        } else {
					            top.Dialog.alert(result.msg);
					        }
					    },"json");
					});
				}
			}else{
				 top.Dialog.alert("验证未通过");
			}
		}
		//全部选中
		function selectAllItem(){
			 $("input:checkbox[name=provCode]").attr("checked",true);
		}
		//全部清除
		function clearAllItem(){
			 $("input:checkbox[name=provCode]").attr("checked",false);
		}
		//通过全选按钮的状态，控制下面所有机构的选中状态
		function changeBranchCodes(){
			 if($("#organ-all").attr("checked")=='checked'){
				 selectAllItem();
			 }else{
				 clearAllItem();
			 }
		}
		//动态修改全选按钮的选中状态
		function changeOrganAll(){
			var selectValue =  getCheckValue();
			var selectValues = getCheckValue().split(",");
			if(selectValues.length != 36){
				$("#organ-all").attr("checked",false);
			}else{
				$("#organ-all").attr("checked",true);
			}
		}
			</script>
	</head>
	<body>
	<form id="form1">
		<input type="hidden" name="index" id="index" value="${index }"/>
		<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
		<tr>
			<td align="right">
				发售区域：
			</td>
			<td>
				<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
				<input type="hidden" id="flag" name="flag" value="${flag }"/>
				 <div id="selectAll">
				 	<input type="checkbox" onclick="changeBranchCodes()" id="organ-all" name="organ-all" /><label for="organ-all" class="hand">全国</label>
				 </div>
				 <div id="branchCodes">
				 </div>
				 <span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div align="center">
					<button type="button" onclick="return sub()" class="saveButton"/>
				</div>
			</td>
		</tr>
		</table>
   	</form>
	</body>
</html>