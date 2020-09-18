<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--表单异步提交start-->
		<script src="<%=path%>/libs/js/form/form.js" type="text/javascript">
</script>
		<!--表单异步提交end-->
		<!--表单验证start-->
		<script src="<%=path%>/libs/js/form/validationRule.js"
			type="text/javascript">
</script>
		<script src="<%=path%>/libs/js/form/validation.js"
			type="text/javascript">
</script>
		<!--表单验证end-->
		<script type="text/javascript">
function sub() {
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	if (valid) {
		top.Dialog.confirm("是否关闭该申请记录?", function() {
			$.post("<%=path%>/webReviewMain/updateWebReviewMainInfo.htm",{"appNo" : $("#appNo").val(),"type":"close"},
				function(result) {
					if (result.success == "true"|| result.success == true) {
						top.Dialog.alert(result.msg,function() {
							top.frmright.refresh(true);
							top.Dialog.close();
						});
					} else {
						top.Dialog.alert(result.msg);
					}
			}, "json");
		});
	} else {
		top.Dialog.alert("验证未通过");
	}
}
//重置
function closeWin() {
	top.Dialog.close();
}
</script>
	</head>
	<body>
		<form id="form1" action="" method="post">
			<table class="tableStyle" mode="list">
				<tr>
					<td align="right">
						复核代码：
					</td>
					<td>
						<input type="text" id="appNo" name="appNo" class="validate[required,length[0,20],custom[onlyNumber]]" maxlength="20" style="width:130"/>
						<span class="star">*</span>
					</td>
				</tr>
			</table>
			<table class="tableStyle" formMode="transparent">
				<tr>
					<td colspan="4">
						<input type="button" value="提交" onclick="return sub()" />
						<input type="reset" value="重置" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
