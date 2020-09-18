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
	   			<div name="节假日净赎回限额详细信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="50%">
								产品代码：
							</td>
							<td>
								${entity.prodCode}
							</td>
						</tr>
						<tr>
							<td align="right">
								节假日净赎回限额(元)：
							</td>
							<td>
								${entity.holidayRedeemLimit}
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	</body>
</html>