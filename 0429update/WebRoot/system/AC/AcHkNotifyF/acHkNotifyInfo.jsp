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
							币种:
						</td>
						<td>
					      <c:choose>
							<c:when test="${entity.currency==826}">
								英镑
							</c:when>
							<c:when test="${entity.currency==156}">
							         人民币
							</c:when>
							<c:when test="${entity.currency==978}">
							         欧元
							</c:when>
							<c:when test="${entity.currency==840}">
							         美元
							</c:when>
							<c:when test="${entity.currency==392}">
							         日元
							</c:when>
							<c:when test="${entity.currency==036}">
							        澳大利亚元
							</c:when>
							<c:when test="${entity.currency==702}">
							         新元
							</c:when>
							<c:when test="${entity.currency==643}">
							         卢布
							</c:when>
							<c:when test="${entity.currency==756}">
							         瑞士法郎
							</c:when>
							<c:when test="${entity.currency==124}">
							         加元
							</c:when>
							<c:when test="${entity.currency==344}">
							         港币
							</c:when>
						   </c:choose>
				       </td>
					</tr>
					<tr>
						<td align="left">
							金额:
						</td>
						<td>
					      <c:if test="${entity.tradeKindCode==201}">
							 <c:if test="${entity.isProfitFeeToRMB==1}">
								 <c:choose>
											<c:when test="${entity.currency==156}">
											     ${entity.sumAmt}
											</c:when>
											<c:when test="${entity.currency==826}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==978}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==840}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==392}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==036}">
											     ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==702}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==643}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==756}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==124}">
											   ${entity.sumAmt}(人民币元)
											</c:when>
											<c:when test="${entity.currency==344}">
											    ${entity.sumAmt}(人民币元)
											</c:when>
							      </c:choose>
							     </c:if>
						     <c:if test="${entity.isProfitFeeToRMB!=1}">
						          ${entity.sumAmt}
						     </c:if>
					    </c:if>
					     <c:if test="${entity.tradeKindCode!=201}">
					             ${entity.sumAmt}
					     </c:if>
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
