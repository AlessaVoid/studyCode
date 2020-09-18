<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	<script>
	function changePeriod(value) {
		if(value=="1"){
			$("#periodSpan").text("1-7");
			$("#periodDown").val("1");
			$("#periodUp").val("7");
			}
		if(value=="2"){
			$("#periodSpan").text("8-31");
			$("#periodDown").val("8");
			$("#periodUp").val("31");
			}
		if(value=="3"){
			$("#periodSpan").text("32-92");
			$("#periodDown").val("32");
			$("#periodUp").val("92");
			}
		if(value=="4"){
			$("#periodSpan").text("93-184");
			$("#periodDown").val("93");
			$("#periodUp").val("184");
			}
		if(value=="5"){
			$("#periodSpan").text("185-366");
			$("#periodDown").val("185");
			$("#periodUp").val("366");
			}
		if(value=="6"){
			$("#periodSpan").text("367-999999");
			$("#periodDown").val("367");
			$("#periodUp").val("999999");
		}
		$("#periodDown").render();
		$("#periodUp").render();
		}
		</script>
	</script>
	</head>
	
	<body>
		<form id="form1">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								期限：
							</td>
							<td>
								<dic:select onchange="changePeriod(this.value)" dicType="PERIODQ" name="period" tgClass="validate[required]" id="period" ></dic:select><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td>产品期限上下限(天)：</td>
							<td>
								<span id="periodSpan"></span>
								<input type="hidden" id="periodUp" name="periodUp"/>
								<input type="hidden" id="periodDown" name="periodDown"/>
							</td>
						</tr>	
						
						<tr>
							<td align="right">
								预期最低收益率：
							</td>
							<td>
								<input type="text" name="minimumRate"  class="validate[required],money" maxlength="5"/>%<span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/webPeriodMinRate/insert.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>