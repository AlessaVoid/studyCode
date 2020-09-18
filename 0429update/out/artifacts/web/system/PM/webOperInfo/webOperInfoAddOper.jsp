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
		<title></title> 
	</head>
	<script type="text/javascript">
		function isCardNo() {  
			var certificatecode=$("#certificatecode").val();
			var certificatekind=$("#certificatekind").val();
		   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
		   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
			   if(certificatekind == "0"&&reg.test(certificatecode) == false){  
				   top.Dialog.alert("证件号码输入不合法");  
			       return  false;  
			   }
			   return doSubmit('form1','<%=path%>/webOperInfo/insert.htm');
		}  
	</script>
	
	
	<body>
		<form id="form1">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
	   						<td>机构代码：</td>
	   						<td>${OperInfo.organCode}
	   							<input type="hidden"  name="organCode" value="${OperInfo.organCode}"/><span class="star">*</span></td>
	   						<td colspan="2"></td>
	   					</tr>
	   					<tr>
							<td align="right">
								所属部门名称：
							</td>
							<td>
								<div class="selectTree validate[required]" url="<%=path%>/webDeptInfo/upDeptName.htm" id="deptCode" name="deptCode" selectedValue="${OperInfo.deptCode}"></div><span class="star">*</span>
							</td>
							<td align="right">
								柜员号：
							</td>
							<td>
								${OperInfo.operCode}
								<input type="hidden"  name="operCode" value="${OperInfo.operCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								证件类型：
							</td>
							<td>
								<dic:select dicType="D_CUST_CERT_TYPE"  dicNo="${OperInfo.certificatekind}"  id="certificatekind" name="certificatekind" ></dic:select><span class="star">*</span>
							</td>
							<td align="right">
								证件号码:
							</td>
							<td>
								<input type="text" id="certificatecode" name="certificatecode" class="validate[required]"   maxlength="30"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								手机:
							</td>
							<td>
								<input type="text" name="mobilePhone" class="validate[required,custom[mobilephone]]"/><span class="star">*</span>
							</td>
							<td align="right">
								固定电话:
							</td>
							<td>
								<input type="text" name="telephone"  class="validate[required]" maxlength="30"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								通讯地址:
							</td>
							<td>
								<input type="text" name="address" class="validate[required]" maxlength="50"/><span class="star">*</span>
							</td>
							<td align="right">
								电子邮箱:
							</td>
							<td>
								<input type="text" name="email" class="validate[required],custom[email]" maxlength="50"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
						<td align="right">
								是否为部门领导:
							</td>
							<td>
								<dic:select dicType="IS_LEADER" name="isLeader" tgClass="validate[required]" id="isLeader" ></dic:select><span class="star">*</span>
							</td>
							<td align="right">
								资质描述:
							</td>
							<td>
								<input type="text" name="qualifications" class="validate[required]" maxlength="250"/><span class="star">*</span>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="isCardNo()" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
	   	</form>
	</body>
</html>