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
								<input type="text" name="dictNo" class="validate[required]" maxlength="64"/><span class="star">*</span>
							</td>
							<td align="right">
								分组中文名：
							</td>
							<td>
								<input type="text" name="dictName" class="validate[required]" maxlength="256"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								键-内部 ：
							</td>
							<td>
								<!-- <input type="text" name="dictKeyIn" class="validate[required],custom[noSpecialCaracters]" maxlength="16"/><span class="star">*</span> -->
								<input type="text" name="dictKeyIn" class="validate[required]" maxlength="64"/><span class="star">*</span>
							</td>
							<td align="right">
								值-内部：
							</td>
							<td>
								<input type="text" name="dictValueIn" class="validate[required]" maxlength="256"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								键-外部：
							</td>
							<td>
								<input type="text" name="dictKeyOut" class="validate[custom[noSpecialCaracters]]" maxlength="64"/>
							</td>
							<td align="right">
								值-外部 ：
							</td>
							<td>
								<input type="text" name="dictValueOut"  maxlength="256"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								排序：
							</td>
							<td>
								<input type="text" name="dictNoOrder" class="validate[required],custom[onlyNumber]" maxlength="7"/><span class="star">*</span>
							</td>
							<td align="right">
								创建人员：
							</td>
							<td>
								<input type="text" name="createOper" class="validate[required]" maxlength="20"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								参数状态：
							</td>
							<td>
								<dic:select dicType="STATUS" name="status" id="status" class="validate[required]"></dic:select><span class="star">*</span>
							</td>
							<td align="right">
								 内容描述：
							</td>
							<td>
								<textarea rows="" cols="" name="dictDesc"  maxlength="256"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/gfDict/insert.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>