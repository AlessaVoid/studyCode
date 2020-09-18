<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
		<title></title>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
	<table class="tableStyle" width="80%" mode="list" formMode="line">
   					<tr>
						<td align="left">
							交易时间:
						</td>
						<td>
						${entity.tradeDate1}
						</td>
					</tr>
   					<tr>
						<td align="left" width="25%">
							产品代码:
						</td>
						<td width="25%">
							${entity.prodCode}
						</td>
					</tr>
					<tr>
						<td align="left" width="25%">
							产品名称:
						</td>
						<td width="25%">
							${entity.prodName}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款类型:
						</td>
						<td>
						${entity.tradeKindName}
						</td>
					</tr>
					<tr>
						<td align="left">
							产品成立日:
						</td>
						<td>
						${entity.prodBeginDate}
						</td>
					</tr>
					<tr>
						<td align="left">
							金额:
						</td>
						<td>
						${entity.sumAmt}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款方账号:
						</td>
						<td>
						${entity.outAcctCode}
						</td>
					</tr>
					<tr>
						<td align="left">
							划款方账户:
						</td>
						<td>
						${entity.outAcctName}
						</td>
					</tr>
					<tr>
						<td align="left">
							收款方账号:
						</td>
						<td>
						${entity.inAcctCode}
						</td>
					</tr>
					<tr>
						<td align="left">
							收款方账户:
						</td>
						<td>
						${entity.inAcctName}
						</td>
					</tr>
					
   				</table>
</body>
</html>
