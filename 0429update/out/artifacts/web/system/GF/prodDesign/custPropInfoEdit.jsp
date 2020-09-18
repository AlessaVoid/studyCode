<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>行内属性参数</title> 
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
		<script type="text/javascript">
		var copyType="${copyType}";
		var prodOperModel="${prodOperModel}";
		function sub() {
			top.Dialog.confirm("是否保存该条信息?", function() {
				var valid = $("#form1").validationEngine( {
					returnIsValid : true
				});
				if (valid) {
					$.post("<%=path%>/design/checkCustPropInfo.htm", $("#form1").serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							$.post("<%=path%>/design/saveCustPropInfo.htm", $("#form1").serialize(), function(result) {
								if (result.success == "true" || result.success == true) {
									top.Dialog.alert(result.msg, function() {
										if(copyType == "copyType"){
											if(prodOperModel == "03" || prodOperModel == "04"){
												parent.turnPage("panel15","panel16");
											}else{
												parent.turnPage("panel15","panel17");
											}
										}else{
											if($("#index").val() != $("#oldIndex").val()){
												parent.changeDiv($("#index").val(),$("#oldIndex").val());
											}else{
												parent.changeDiv($("#index").val(),$("#index").val());
											}
										}
									});
								} else {
									top.Dialog.alert(result.msg);
								}
							}, "json");
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				} else {
					top.Dialog.alert("验证未通过！");
				}
			});
		}
		</script>
	</head>
	<body>
		<form id="form1">
		<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
		<input type="hidden" name="index" id="index" value="${index }"/>
		<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td>与或关系类型：</td>
					<td>
						<dic:select dicType="Y_HREFER" name="relType" tgClass="validate[required]"  dicNo="${entity.relType }"></dic:select>
						 <span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>属性：</td>
					<td>
						<div class="selectTree validate[required]" name="propCode" selectedValue="${entity.propCode }" url="<%=path%>/gfDict/getTreeDic.htm?dicType=D_PROP_CODE" multiMode="true" allSelectable="true" exceptParent="true"></div>
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