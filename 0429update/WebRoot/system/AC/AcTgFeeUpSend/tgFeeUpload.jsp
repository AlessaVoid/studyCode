<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head >
		<title></title>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--表单异步提交end-->
<script type="text/javascript">
function reset() {
	top.Dialog.confirm("数据尚未保存，是否重置?|操作提示", function() {
		$("#form1")[0].reset();
	});
}
function sub() {
		var valid = $("#form1").validationEngine( {
			returnIsValid : true
		});
		if (valid) {
			var form = $("#form1");  
			$(".money").each(function(){
				rmoney(this);
			});
	        var options  = {    
	        url:'<%=path%>/acTgFeeUpSend/uploadFile.htm',
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
	        		 top.Dialog.alert(result.msg,function(){
					 top.Dialog.close();
	        		 });
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
			if($("#fenpeiAmt").val()<=0){
				top.Dialog.alert("上送总金额必须大于0！");
				return false;
			}
			if($("#fenpeiconunt").val()<=0){
				top.Dialog.alert("上送总笔数必须大于0！");
				return false;
			}
			 top.Dialog.alert("正在处理,请耐心等待...");
	        form.ajaxSubmit(options); 
		} else {
			top.Dialog.alert("验证未通过！");
		}
}		

    </script>	
</head>
<body >
<form id="form1" method="post" enctype="multipart/form-data">
   	<table class="tableStyle" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right">批量上送文件名： </td>
				<td>
					<input type="file" name="batchFile" id="batchFile" class="validate[required]" keepDefaultStyle="true"/>
					<span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td align="right">上送总笔数： </td>
				<td>
					<input type="text" name="fenpeiconunt" class="validate[required,custom[onlyNumber]]" inputMode="numberOnly"/>
					<span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td align="right">上送总金额： </td>
				<td>
					<input type="text" name="fenpeiAmt" id="fenpeiAmt" class="money validate[required]"/>
					<span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="center">
						<button type="button" class="saveButton" onclick="sub()"/>
						<button type="button" class="resetButton" onclick="reset()"></button>
					</div>
				</td>
			</tr>
		</table>
		</form>
</body>
</html>

