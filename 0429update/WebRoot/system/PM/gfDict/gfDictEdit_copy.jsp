<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	</head>
	<body>
		<form id="form1">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								分组英文名：
							</td>
							<td>
								${dict.dictNo}
								<input type="hidden" value="${dict.dictNo}" name="dictNo"/>
							</td>
							<td align="right">
								分组中文名：
							</td>
							<td>
								${dict.dictName}
								<input type="hidden" value="${dict.dictName}" name="dictName"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								键-内部 ：
							</td>
							<td>
								<input type="text" value="${dict.dictKeyIn}" name="dictKeyIn" class="validate[required]" maxlength="256"/>
							</td>
							<td align="right">
								值-内部：
							</td>
							<td>
								<input type="text" value="${dict.dictValueIn}" name="dictValueIn" class="validate[required]" maxlength="256" /><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								键-外部：
							</td>
							<td>
								<input type="text" value="${dict.dictKeyOut}" name="dictKeyOut" class="validate[custom[noSpecialCaracters]]" maxlength="64"/>
							</td>
							<td align="right">
								值-外部 ：
							</td>
							<td>
								<input type="text" value="${dict.dictValueOut}" name="dictValueOut"  maxlength="256"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								排序：
							</td>
							<td>
								<input type="text" value="${dict.dictNoOrder+1}" name="dictNoOrder" class="validate[required],custom[onlyNumber]" maxlength="7"/><span class="star">*</span>
							</td>
							<td align="right">
								创建人员：
							</td>
							<td>
								<input type="text" value="${dict.createOper}" name="createOper" class="validate[required]" maxlength="5"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								参数状态：
							</td>
							<td>
								<dic:select dicType="STATUS" dicNo="${dict.status}" id="status" name="status" class="validate[required]"></dic:select><span class="star">*</span>
							</td>
							<td align="right">
								字典描述：
							</td>
							<td>
								<textarea rows="" cols="" name="dictDesc"  maxlength="256">${dict.dictDesc}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return doSubmit_c('form1','<%=path%>/gfDict/insert.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
	   	</form>
	   	<script type="text/javascript">
	  //提交表单公共方法
	   	function doSubmit_c(form,url){
	   		var valid = $("#"+form).validationEngine( {
	   			returnIsValid : true
	   		});
	   		if (valid) {
	   			top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
	   				$.post(url, $("#"+form).serialize(), function(result) {
	   					if (result.success == "true" || result.success == true) {
	   						top.Dialog.alert(result.msg, function() {
	   							top.Dialog.close();
	   						});
	   					} else {
	   						$(".money").each(function(){
	   							fmoney(this);
	   						});
	   						top.Dialog.alert(result.msg);
	   					}
	   				}, "json");
	   			});
	   		}else{
	   			top.Dialog.alert("验证未通过！");
	   		}
	   	}
	   	</script>
	</body>
</html>