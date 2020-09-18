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
		<div class="basicTabModern">
				<div name="详情" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
						<tr>
							<td align="right">
								产品代码:
							</td>
							<td>
								${entity.prodCode}
							</td>
						</tr>
						<tr>
							<td align="right">
								启用日期:
							</td>
							<td>
								${entity.beginDate}
							</td>
						</tr>
						<tr>
							<td align="right">
								终止日期:
							</td>
							<td>
								${entity.endDate}
							</td>
						</tr>
						<tr>
							<td align="right">
								开放类型:
							</td>
							<td>
								<c:if test="${entity.paraType==1}">申购</c:if>
								<c:if test="${entity.paraType==2}">赎回</c:if>
							</td>
						</tr>
						<tr>
							<td align="right">
								份额确认日:
							</td>
							<td>
								${entity.quotAffirmDate}
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	   	</form>
	</body>
</html>