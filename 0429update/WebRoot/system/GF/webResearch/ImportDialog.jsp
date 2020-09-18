<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品导入</title>
<%@include file="/common/common_edit.jsp"%>
<script type="text/javascript" src="<%=path%>/libs/js/jquery-form.js"></script>
	<!--框架必需end-->
	<!--表单异步提交start-->
	<script src="${path}/libs/js/form/form.js" type="text/javascript"></script>
	<!--表单异步提交end-->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="<%=path%>/libs/js/popup/drag.js"></script>
	<script type="text/javascript" src="<%=path%>/libs/js/popup/dialog.js"></script>
	<!--引入弹窗组件end-->
<script type="text/javascript">
	function sub(){
		var valid = $("#form1").validationEngine( {
			returnIsValid : true
		});
		if (valid) {
			var form = $("#form1");  
	        var options  = {    
            url:'<%=path%>/webResearchImport/import.htm',    
            type:'post',   
            beforeSend : function(XMLHttpRequest) {
				buttonluck();
				if (this.url == "") {
					return;
				}
				if (this.url.indexOf("?") != -1) {
					this.url = this.url + "&number=" + Math.random() + "";
				} else {
					this.url = this.url + "?number=" + Math.random() + "";
				}
			},
            success:function(result)    
            {    
            	buttonUnluck();
                if(result.success == "true" || result.success==true){ 
                	top.Dialog.confirm(result.msg, function() {
                	var prodCode = result.bean.prodCode;
                	var prodName = result.bean.prodName;
               		commitApp(prodCode,prodName);
                	});
                }else{
                	top.Dialog.alert(result.msg,function(){
                		var prodCode = result.bean.prodCode;
                		$.post("<%=path%>/webResearchImport/delimport.htm",
                				{prodCode:prodCode},
                				function(result){
                			top.Dialog.alert(result.msg);
                			});
                		});
                	}  
           		 },
            error:function(error){
            	buttonUnluck();
            	if (error.status == "10000") {
					top.Dialog.alert(error.responseText,function(){
						window.parent.location.href = "<%=path%>/toLogin.htm";
					},null,null,3);
				} else{
					top.Dialog.alert(error.responseText);
				}
            }
	        };    
	        form.ajaxSubmit(options); 
		} else {
			top.Dialog.alert("验证未通过");
		}
	}
	
	function commitApp(prodCode,prodName){
		top.Dialog.open({
			URL : "<%=path%>/webResearchAppInfo/importAppUser.htm?prodCode="+prodCode+"&prodName="+prodName,
			Title : "审批信息",
			Width : 600,
			Height : 450
		});
	}
</script>	
</head>
<body>
	<form method="post" id="form1" enctype="multipart/form-data">
		<table class="tableStyle"  mode="list" formMode="line" style="width: 100%;">
			<tr>
				<td align="right" width="38%">参数模板： </td>
				<td>
					<input type="file" name="file" id="file" class="validate[required]"  keepDefaultStyle="true"/>
					<span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="ali02">
					<div align="center">
						<button type="button" class="saveButton" onclick="return sub()"/>
						<button type="reset" class="resetButton"></button>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>

