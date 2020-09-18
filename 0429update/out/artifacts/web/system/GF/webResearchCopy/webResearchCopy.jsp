<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>产品预研</title> 
		<script type="text/javascript">
		//提交表单公共方法
		function sub(form,url){
			var valid = $("#"+form).validationEngine( {
				returnIsValid : true
			});
			if (valid) {
				top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
					$.post(url, $("#"+form).serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							toCopy(result.msg);
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			}else{
				top.Dialog.alert("验证未通过！");
			}
		}
		//跳转到复制新增页面
		function toCopy(copyProdCode){
			top.Dialog.open({
				URL : "<%=path%>/webResearchAppInfo/copyEdit.htm?copyProdCode="+copyProdCode+"&copyType=copyType",
				Title : "复制新增维护",
				Width : "860",
				Height : "680"
			});
		}
		</script>
	</head>
	<body>
		<form id="form1">
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td align="right" width="10%">产品代码：</td>
					<td width="15%">
						<input type="text" name="copyProdCode" class="validate[required,length[0,10],custom[noSpecialCaracters]]" maxlength="10"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" onclick="sub('form1','<%=path %>/webResearchAppInfo/copy.htm')" class="saveButton"/>
							<button type="button" onclick="cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>