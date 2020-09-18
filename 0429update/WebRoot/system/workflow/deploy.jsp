<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/common/common_edit.jsp"%>
	<script type="text/javascript" src="<%=path%>/libs/js/jquery-form.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<title>流程部署</title> 
	<script type="text/javascript">
	function doSubmit(){
		var valid = $("#deployform").validationEngine( {
			returnIsValid : true
		});
		if (valid) {
			top.Dialog.confirm("确定要部署该流程吗?|操作提示", function() {
				var form = $("#deployform");  
		        var options  = {    
		            url:'<%=path%>/workflow/deploy.htm',    
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
		                	top.Dialog.alert(result.msg, function() {
								top.frmright.window.location.reload(true);
								top.Dialog.close();
							});
		                }else{  
		                	top.Dialog.alert(result.msg);
		                }  
		            },
		            error:function(error){
		            	buttonUnluck();
		            	if (error.status == "10000") {
							top.Dialog.alert(error.responseText + " | 超时提示",function(){
								window.parent.location.href = "<%=path%>/toLogin.htm";
							},null,null,3);
						} else{
							top.Dialog.alert(error.responseText);
						}
		            }
		        };    
		        form.ajaxSubmit(options);  
			});
		}else{
			top.Dialog.alert("验证未通过！");
		}
	}
	</script>
</head>
<body>
	<form method="post" id="deployform" enctype="multipart/form-data">
	<div name="流程部署" style="width:100%;height:80%;">
		<table class="tableStyle"  mode="list" formMode="line" style="width: 100%;">
			<tr>
				<td align="right" width="38%">模板流程文件： </td>
				<td>
					<input type="file" name="file" class="validate[required]"/><span class="star">*</span>
					</br>(支持文件格式：zip、bar、bpmn、bpmn20.xml)
				</td>
			</tr>
			<tr>
				<td colspan="2" class="ali02">
					<div align="center">
						<button type="button" class="saveButton" onclick="return doSubmit()"></button>
						<button type="reset" class="resetButton"></button>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</body>
</html>

