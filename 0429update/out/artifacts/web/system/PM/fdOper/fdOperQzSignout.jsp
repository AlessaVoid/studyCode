<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--表单异步提交start-->
		<script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
		<!--表单异步提交end-->
		<!--表单验证start-->
		<script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
		<script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
		<!--表单验证end-->
		<script type="text/javascript">
$(function(){
	$("select").attr("boxHeight", 90);
	$("select").render();
});
function sub() {
	top.Dialog.confirm("是否强退该柜员?", function() {
		var valid = $("#form1").validationEngine( {
			returnIsValid : true
		});
		//alert($("#form1").serialize());
		if (valid) {
			$.post("<%=path%>/qzExitSignout.htm", $("#form1")
					.serialize(), function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg, function() {
						top.frmright.window.location.reload(true);
						top.Dialog.close();
					});
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		} else {
			top.Dialog.alert("验证未通过");
		}
	});
}
</script>
	</head>
	<body>
		<form id="form1" action="" method="post">
			<div class="basicTabModern">
				<div name="柜员强制签退" style="width: 100%; height: 150px;">
					<table class="tableStyle" mode="list">
						<tr>
						<td align="right">
								当前柜员号：
							</td>
							<td>
							<input type="text" id="operCode" maxlength="20" name="opercode" value="${opercode }" disabled="disabled"/>
							</td>
							<td align="right">
								被操作柜员号：
								<span class="star">*</span>
							</td>
							<td>
								<input type="text" id="operCode1" maxlength="20" name="opercode1" value="" tgClass="validate[required]"/>
							</td>
						</tr>
					</table>
					<div class="padding_top10">
						<table class="tableStyle" formMode="transparent">
							<tr>
								<td colspan="4">
									<input type="button" value="提交" onclick="return sub()" />
									<input type="reset" value="重置" />
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
