<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>灵活期限参数导入</title>
<%@include file="/common/common_edit.jsp"%>
<script type="text/javascript" src="<%=path%>/libs/js/jquery-form.js"></script>
<script type="text/javascript">
function sub(){
	var valid = $("#form1").validationEngine( {
		returnIsValid : true
	});
	if (valid) {
		var form = $("#form1");  
        var options  = {    
        url:'<%=path%>/design/importLhqx.htm?prodCode='+$("#prodCode").val(),    
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
        		 top._DialogFrame_designAdd.iframepage9.window.location.reload(true);
				 top.Dialog.close();
            }else{
            	top.Dialog.alert(result.msg);
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
</script>	
</head>
<body>
	<div  class="box2_custom" showstatus="false" boxType="box2" panelTitle="灵活期限模板导入">
		<form id="form1" method="post" enctype="multipart/form-data">
		<table class="tableStyle"  mode="list" formMode="line" style="width: 100%;">
			<tr>
				<td align="right" width="38%">模板选择： </td>
				<td>
					<input type="hidden"  id="prodCode" name="prodCode" value="${param.prodCode }"/>
					<input type="file" name="file" id="file" class="validate[required]" keepDefaultStyle="true"/>
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
	</div>
</body>
</html>

