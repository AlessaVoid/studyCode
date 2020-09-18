<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>档期安排驳回意见</title>
		<style type="text/css">
		#comment{
		word-wrap:break-word;
		word-break:break-all;
		}
		</style>
		<script type="text/javascript">
		function sub(){
			var comment=$("#comment").val();
			top.frmright.window.onRollBackDeal(comment);
			top.Dialog.close();
		}
		function addComment(){
			var approveModel = $("#approveModel").attr("relText");
			$("#comment").val(approveModel);
			$("#comment").render();
		}
		</script>
	</head>
	<body>
		<form id="form1">
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line"  fixedCellHeight="true">
				<tr>
				<td>审批意见模板：</td>
					<td>
						<select onchange="addComment();" selWidth="127" id="approveModel" name="approveModel" prompt="--请选择--"
									url="<%=path%>/webApproveModel/getWebApproveModel.htm">
						</select>
					</td>	
					</tr>		
				<tr>
			
						<td>批注：</td>
					<td>
						<textarea id="comment" rows="50" cols="3" name="comment" class="validate[required]" wrap="physical"></textarea>
					<span class="star">*</span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<div align="center">
							<button type="button" onclick="sub();" class="saveButton"/>
							<button type="button" onclick="return cancel()" class="cancelButton" />	
						</div>
					</td>
				</tr>
		</table>
	   	</form>
	</body>
</html>