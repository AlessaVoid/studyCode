<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>基本参数</title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
	<form id="form1">
	<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
		<tr>
			<td align="right" width="25%">产品发行机构：</td>
			<td width="25%">
				<dic:out dicNo="${entity.productDistriButionAgency}" dicType="PRODUCT_DISTRIBUTION_AGENCY"></dic:out>
			</td>
			<td align="right" width="9%">
				是否是货币型产品：
			</td>
			<td width="16%">
			  <dic:out dicType="IS_YES" dicNo="${entity.isCurrProdt}"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="25%">产品品牌：</td>
			<td width="25%">
				${brandName }
			</td>
			<td align="right" width="25%">投资者类型：</td>
			<td width="25%">
				<dic:out dicNo="${entity.custType}" dicType="CUST_TYPE"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="25%">产品名称生成规则：</td>
			<td width="25%">
				<c:if test="${entity.prodNameRule == '1'}">
					不分期
				</c:if>
				<c:if test="${entity.prodNameRule == '2'}">
					按号
				</c:if>
				<c:if test="${entity.prodNameRule == '3'}">
					按XX年XX期
				</c:if>
			</td>
			<td align="right" width="25%">产品类型：</td>
			<td width="25%">
			    <dic:out dicNo="${entity.prodtCharactType}" dicType="PRODT_CHARACT_TYPE"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">产品成立日期：</td>
			<td width="15%">
				${entity.prodBeginDate}
			</td>
			<td align="right" width="10%">产品到期日期：</td>
			<td width="15%">
				${entity.prodEndDate}
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">产品收益类型：</td>
			<td width="15%">
				<dic:out dicType="PROFIT_TYPE" dicNo="${entity.profitType}"></dic:out>
			</td>
			<td align="right" width="10%">产品运作模式：</td>
			<td width="15%">
				<dic:out dicNo="${entity.prodOperModel}" dicType="PROD_OPER_MODEL"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">会计核算方式：</td>
			<td width="15%">
				<dic:out dicNo="${entity.accountingFlag}" dicType="ACCOUNTING_FLAG"></dic:out>
			</td>
			<td align="right" width="10%">产品风险等级：</td>
			<td width="15%">
				<dic:out dicNo="${entity.prodRiskLevel}" dicType="PROD_RISK_LEVEL"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">起点销售金额：</td>
			<td width="15%">
				<c:if test="${entity.custType == '1'||entity.custType == '0'}">
					<fmt:out fmtvalue="${entity.lowestInvestment}" fmtclass="money"></fmt:out>
				</c:if>
				<c:if test="${entity.custType == '2'}">
					<fmt:out fmtvalue="${entity.corpLowestInvestment}" fmtclass="money"></fmt:out>
				</c:if>
			</td>
			<td align="right" width="10%">投资收益率(%)：</td>
			<td width="15%">
				<fmt:out fmtvalue="${entity.prodProfitRate}" fmtclass="money"></fmt:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">收益计算标识：</td>
			<td width="15%">
				<dic:out dicNo="${entity.profitHandleFlag}" dicType="PROFIT_FLAG"></dic:out>
			</td>
			<td align="right" width="10%">销售手续费类型：</td>
			<td width="15%">
				<dic:out dicNo="${entity.sellFeeType}" dicType="SELLFEE_TYPE"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">托管费率(%)：</td>
			<td width="15%">
				<fmt:out fmtvalue="${entity.prodControlFeeRate}" fmtclass="money"></fmt:out>
			</td>
			<td align="right" width="10%">推荐费率(%)：</td>
			<td width="15%">
				<fmt:out fmtvalue="${entity.prodRecomRate}" fmtclass="money"></fmt:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">其他费率(%)：</td>
			<td width="15%">
				<fmt:out fmtvalue="${entity.otherRate}" fmtclass="money"></fmt:out>
			</td>
			<td align="right" width="10%">超额留存收益率(%)：</td>
			<td width="15%">
				<fmt:out fmtvalue="${entity.overfulfilProfit}" fmtclass="money"></fmt:out>
			</td>
		</tr>
		<tr>
			<td align="right">
				预计客户最高年化收益率(%)：
			</td>
			<td>
				<fmt:out fmtvalue="${entity.predictHighestProfit}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				预计客户最低年化收益率(%)：
			</td>
			<td>
				<fmt:out fmtvalue="${entity.predictLowestProfit}" fmtclass="money"></fmt:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">拟续接：</td>
			<td width="15%">
				<dic:out dicNo="${entity.isContProd}" dicType="IS_NEED_CONT_PROD"></dic:out>
			</td>
			<td align="right" width="10%">待续接：</td>
			<td width="15%">
				<dic:out dicNo="${entity.isNeedContProd}" dicType="IS_NEED_CONT_PROD"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%">是否自主平衡：</td>
			<td width="15%">
				<dic:out dicNo="${entity.isAutoBalance}" dicType="IS_YES"></dic:out>
			</td>
			<td align="right" width="10%">币种：</td>
			<td width="15%">
				<dic:out dicType="TR_CURRENCY" dicNo="${entity.currency}"></dic:out>
			</td>
		</tr>
		<tr>
		<td>客户收益率(%)：</td>
		<td>
		<fmt:out fmtvalue="${entity.profitRate}" fmtclass="money"></fmt:out>
		</td>
		<td>销售费率(%)：</td>
		<td>
		<fmt:out fmtvalue="${entity.sellRate}" fmtclass="money"></fmt:out>
		</td>
		</tr>
		<tr>
		<td>总行代为操作分行自平衡产品标识：</td>
		<td><dic:out dicType="IS_YES" dicNo="${entity.headAutoBalanceFlag}"></dic:out></td>
		<td>保本产品成本户类型：</td>
		<td>
			<dic:out dicType="COST_ASSET_TYPE_P" dicNo="${one}"></dic:out>
			<dic:out dicType="COST_ASSET_TYPE_P" dicNo="${two}"></dic:out>
		</td>
		</tr>
		<tr>
		<td>净值最晚延迟日：</td>
		<td>
			<dic:out dicType="LAST_DAY" dicNo="${webProdAssetMutualInfo.netvalueLastDay}"></dic:out>
		</td>
		<td>产品到期收益和手续费是否转人民币：</td>
		<td>
			<dic:out dicType="IS_YES" dicNo="${entity.isProfitFeeToRmb}"></dic:out>
		</td>
		</tr>
		<tr>
		<td>专属标志：</td>
		<td>
		 <dic:out dicType="EXCLUSIVE_FLAG" dicNo="${entity.exclusiveFlag}"></dic:out>
		</td>
		<td>目标客户类型：</td>
		 <td>
		    <dic:out dicType="TARGET_CUSTOMER" dicNo="${entity.targetCustomer}"></dic:out>
		 </td>   
		</tr>
		<tr>
			<td>投资性质：</td>
			<td>
			    <dic:out dicType="INVESTMENT_NATURE" dicNo="${entity.investmentNature}"></dic:out>
			</td>
			<td>投资封闭期</td>
			<td>
			  ${entity.closeInvCycle}
			</td>
			
		</tr>
		<%-- <tr>
			<td align="right" width="9%">
				是否是货币型产品：
			</td>
			<td width="16%">
			  <dic:out dicType="IS_YES" dicNo="${entity.isCurrProdt}"></dic:out>
			</td>
		</tr> --%>
	</table>
	</form>
	</body>
</html>