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
				<div name="销售商详细信息" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="40%">
								销售商代码：
							</td>
							<td>
								${entity.retailerCode}
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商名称：
							</td>
							<td>
								${entity.retailerName}
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商全称：
							</td>
							<td>
								${entity.retailerAllName}
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商英文名：
							</td>
							<td>
								${entity.retailerEnName}
							</td>
						</tr>
						<tr>
							<td align="right">
								销售商类型:
							</td>
							<td>
								<dic:out dicType="retailer_type" dicNo="${entity.retailerType}"></dic:out>
							</td>
						</tr>
						<tr>
							<td align="right">
								传真号码:
							</td>
							<td>
								${entity.fax}
							</td>
						</tr>
						<tr>
							<td align="right">
								联系人姓名:
							</td>
							<td>
								${entity.contactPersonName}
							</td>
						</tr>
						<tr>
							<td align="right">
								联系人电话:
							</td>
							<td>
								${entity.contactPersonTell}
							</td>
						</tr>
						<tr>
							<td align="right">
								电子邮件:
							</td>
							<td>
								${entity.email}
							</td>
						</tr>
						<tr>
							<td align="right">
								通讯地址:
							</td>
							<td>
								${entity.contactAddress}
							</td>
						</tr>
						<tr>
							<td align="right">
								状态:
							</td>
							<td>
								<dic:out dicType="RETAILER_STATUS" dicNo="${entity.status}"></dic:out>
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	   	</form>
	</body>
</html>