<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	</head>
	<body>
		<div align="left">
	   			<div name="理财产品状态详细信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="30%">
								产品代码：
							</td>
							<td>
								${entity.prodCode}
							</td>
						</tr>
						<tr>
							<td align="right">
								产品名称：
							</td>
							<td>
								${entity.prodName}
							</td>
						</tr>
						<tr>
							<td align="right">
								产品状态：
							</td>
							<td>
								<dic:out dicType="PROD_STATUS" dicNo="${entity.prodStatus}"></dic:out>
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	</body>
</html>