<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>产品预研</title> 
		<script type="text/javascript">
		//提交表单公共方法
		function sub(){
			if($("#copyProdCode").val().length==10) {
				top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
					$.post("<%=path %>/webResearchAppInfo/copy.htm",$("#form1").serialize(),function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.open({
								URL : "<%=path%>/design/prodAddUI.htm?prodType=copy&copyProdCode="+$("#copyProdCode").val(),
								Title : "产品设计复制新增",
								Width : 1280,
								Height : 680
							});
						}else{
							top.Dialog.alert(result.msg);
						}
					}, "json");				
				});
			}else{
				top.Dialog.alert("验证未通过！");
			}
		}
		</script>
	</head>
	<body>
		<form id="form1">
			<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
				<tr>
					<td align="right" width="10%">产品代码：</td>
					<td width="15%">
						<input type="text" name="copyProdCode" id="copyProdCode" class="validate[required,length[0,10],custom[noSpecialCaracters]]" maxlength="10"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<button type="button" onclick="sub()" class="saveButton"/>
							<button type="button" onclick="cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
			</table>
	   	</form>
	</body>
</html>