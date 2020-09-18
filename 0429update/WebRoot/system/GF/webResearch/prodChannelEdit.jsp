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

		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>发售渠道参数</title> 
		<script type="text/javascript">
		//提交方法
		function sub(formId,url) {
			var valid = $("#"+formId).validationEngine( {
				returnIsValid : true
			});
			if(valid){
				top.Dialog.confirm("是否保存渠道信息?", function() {
					$.post(url, $("#"+formId).serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg, function() {
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
					}, "json");
				});
			} else {
				top.Dialog.alert("验证未通过！");
			}
		}
		</script>
	</head>
	<body>
	<form id="form1">
		<input type="hidden" name="prodCode" id="prodCode" value="${entity.prodCode }"/>
		<input type="hidden" name="index" id="index" value="${index }"/>
		<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
		<tr>
			<td align="right" width="10%">产品销售渠道：</td>
			<td width="15%">
				<div class="selectTree validate[required]" name="channelCode" selectedValue="${selectedValue }" url="<%=path%>/gfDict/getTreeDic.htm?dicType=D_CHNL" multiMode="true" allSelectable="true" exceptParent="true"></div>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div align="center">
					<button type="button" onclick="return sub('form1','<%=path%>/webResearchAppInfo/saveWebProdChannel.htm')" class="saveButton"/>
				</div>
			</td>
		</tr>
		</table>
   	</form>
	</body>
</html>