+<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<script type="text/javascript">
		function sub(){
			var valid = $("#form1").validationEngine( {
				returnIsValid : true
			});
			if (valid) {
			top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
				var form = $("#form1");  
		        var options  = {    
			        url:'<%=path%>/webResearchAppInfo/webProdFelPeriodSellRateDTORequest.htm',    
			        type:'post',
			        success:function(result)    
			        {    
			            if(result.success == "true" || result.success==true){  
			            	top.Dialog.alert(result.msg, function() {
			            		parent.window.location.reload(true);
			        		});
			            }else{  
			            	top.Dialog.alert(result.msg, function() {
			            		window.close();
			        		});
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
		<form id="form1">
			<input type="hidden" id="fileName" name="fileName" value="${fileName}" />
			<input type="hidden" id="prodCode" name="prodCode" value="${prodCode}"/>
			<div class="basicTabModern">
	   			<div name="导入灵活期限收益信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
		   					<td width="10%">
		   						机构代码
		   					</td>
		   					<td width="10%">
		   						期限下限(含,天)
		   					</td>
		   					<td width="10%">
		   						期限上限(天) 
		   					</td>
		   					<td width="10%">
		   						灵活期限参数客户收益率(%)
		   					</td>
		   					<td width="10%">
		   						灵活期限参数销售费率(%) 
		   					</td>
		   					<td width="10%">
		   						启用日期
		   					</td>
		   					<td width="10%">
		   						客户类型
		   					</td>
		   					<td width="10%">
		   						启用状态
		   					</td>
						</tr>
				<c:forEach items="${list}" var="index">
					<tr>
						<td >
							${index.organCode}
						</td>
						<td>
							${index.periodLow}
						</td>
						<td>
							${index.periodHigh}
						</td>
						<td>
							${index.profitRate}
						</td>
						<td>
							${index.sellRate}
						</td>
						<td>
							${index.beginDate}
						</td>
						<td>
							<dic:out dicNo="${index.custType}" dicType="D_CUST_TYPE"></dic:out>
						</td>
						<td>
							<dic:out dicNo="${index.status}" dicType="VALID_STATUS"></dic:out>
						</td>
					</tr>
				</c:forEach>
						<tr>
							<td colspan="8">
								<div align="center">
									<button type="button" onclick="return sub()" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	   	</form>
	</body>
</html>