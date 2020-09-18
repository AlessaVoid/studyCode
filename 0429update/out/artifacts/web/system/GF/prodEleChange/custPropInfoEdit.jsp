<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<!-- 树组件start -->
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
		<!-- 树组件end -->
		<!-- 树形下拉框start -->
		<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
		<!-- 树形下拉框end -->
		<title>行内属性参数</title> 
		<script type="text/javascript">
		function sub(formId,url) {
			top.Dialog.confirm("是否保存该条信息?", function() {
				var valid = $("#form1").validationEngine( {
					returnIsValid : true
				});
				if (valid) {
					$.post("<%=path%>/design/checkCustPropInfo.htm", $("#form1").serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							$.post(url, $("#"+formId).serialize(), function(result) {
								if (result.success == "true" || result.success == true) {
									top.Dialog.alert(result.msg,function(){
										window.location.reload(true);
										parent.SetOrderFunc(result.bean.order);
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
		<input  type="hidden" name="prodCode" value="${prodCode}"/>
		<input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
		<input type="hidden" id="order" name="order" value="${order}"/>
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td>与或关系类型：</td>
					<td>
						<dic:select dicType="Y_HREFER" name="relType" tgClass="validate[required]" dicNo="${entity.relType }"></dic:select>
						<span class="star">*</span>
					</td>
					<td>属性：</td>
					<td>
						<div class="selectTree validate[required]" name="propCode" selectedValue="${entity.propCode }" url="<%=path%>/gfDict/getTreeDic.htm?dicType=D_PROP_CODE" multiMode="true" allSelectable="true" exceptParent="true"></div>
						<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<dic:select dicType="CUSTPROP_TYPE" name="status" tgClass="validate[required]" dicNo="${entity.status }"></dic:select>
						<span class="star">*</span>
					</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="center">
							<button type="button" onclick="return sub('form1','<%=path%>/gfProdEleChange/compareCustPropInfo.htm')" class="saveButton"/>
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>