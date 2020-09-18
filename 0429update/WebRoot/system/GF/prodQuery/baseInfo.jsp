<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>基本参数</title>
	</head>
	<body>
	<form id="form1">
	<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
		<tr>
			<td align="right">
				产品发行机构：
			</td>
			<td>
			  	<dic:out dicNo="${entity.productDistriButionAgency}" dicType="PRODUCT_DISTRIBUTION_AGENCY"></dic:out>
			</td>
			<td align="right" colspan='6'></td>
		</tr>
		<tr>
			<td align="right" width="10%">产品品牌：</td>
			<td width="10%">
				<select prompt="请选择" disabled="disabled" id="brandCode" name="brandCode" selectedValue="${brandCode }" url="<%=path%>/gfProdBrandInfo/getProdBrandInfo.htm" class="validate[required]"></select>
			</td>
			<td align="right">
				产品名称后缀：
			</td>
			<td width="14%">
				${entity.prodNameSuffix }
			</td>
			<td align="right">
				投资者类型：
			</td>
			<td>
				<dic:out dicType="CUST_TYPE" dicNo="${entity.custType}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right" width="9%">
				产品运作模式：
			</td>
			<td width="16%">
				<dic:out  dicType="PROD_OPER_MODEL" dicNo="${entity.prodOperModel}" ></dic:out>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				产品收益类型：
			</td>
			<td>
				<dic:out dicNo="${entity.profitType}" dicType="PROFIT_TYPE"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				理财产品成立日期：
			</td>
			<td> 
				${entity.prodBeginDate}
				<span class="star">*</span>
			</td>
			<td align="right">
				理财产品到期日期：
			</td>
			<td>
				${entity.prodEndDate}
				<span class="star">*</span>
			</td>
			<td align="right">
				产品存续期限(天)：
			</td>
			<td>
				${entity.prodDurationDays}
			</td>
		</tr>
		<tr>
			<td align="right">
				产品名称生成规则：
			</td>
			<td>
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
			<td align="right">产品类型：</td>
			<td>
			    <dic:out dicNo="${entity.prodtCharactType}" dicType="PRODT_CHARACT_TYPE"></dic:out>
			</td>
			<td align="right">投资封闭期:</td>
			<td>
			  ${entity.closeInvCycle}
			</td>
			<td align="right" width="9%">
				是否是货币型产品：
			</td>
			<td width="16%">
			  <dic:out dicType="IS_YES" dicNo="${entity.isCurrProdt}"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right">
				收益计算标识：
			</td>
			<td>
				<dic:out dicType="PROFIT_FLAG" dicNo="${entity.profitHandleFlag}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				销售手续费类型：
			</td>
			<td>
				<dic:out dicType="SELLFEE_TYPE"  dicNo="${entity.sellFeeType}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right" width="9%">
				产品风险等级：
			</td>
			<td width="16%">
			<dic:out dicType="PROD_RISK_LEVEL" dicNo="${entity.prodRiskLevel}" ></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right" width="9%">
				拟续接：
			</td>
			<td width="16%">
				<dic:out  dicType="IS_NEED_CONT_PROD" dicNo="${entity.isContProd}"></dic:out>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right" width="9%">
				待续接：
			</td>
			<td width="16%">
				<dic:out dicType="IS_NEED_CONT_PROD" dicNo="${entity.isNeedContProd}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否为POS理财产品：
			</td>
			<td>
			<dic:out dicType="IS_YES" dicNo="${entity.isPos }"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否自主平衡：
			</td>
			<td>
			<dic:out dicType="IS_YES" dicNo="${entity.isAutoBalance}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				会计核算方式：
			</td>
			<td>
			<dic:out dicType="ACCOUNTING_FLAG" dicNo="${entity.accountingFlag}"></dic:out>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				夜市标志：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isNightMarket}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				计提方式：
			</td>
			<td>
				<dic:out dicType="FEE_PROV_FLAG" dicNo="${entity.feeProvFlag}"></dic:out>
			</td>
			<td align="right">
				是否结构性理财：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isQuanto}"></dic:out>
				<span class="star">*</span>
			</td>
			
			<td align="right">
				每月收益结转日：
			</td>
			<td>
			${entity.mouthProfitDate}
			</td>
		</tr>
		<tr>
		<td align="right">
				产品投资收益率(%)：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.prodProfitRate}" fmtclass="money"></fmt:out>
			</td>
		
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
			<td align="right">
				超额留存收益率(%)：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.overfulfilProfit}" fmtclass="money"></fmt:out>
			</td>
		</tr>
		<tr>
			<td align="right">
				产品推荐费率(%)：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.prodRecomRate}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				其他费率(%)：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.otherRate}" fmtclass="money"></fmt:out>
			</td>
			 <td>总行管理费率(%)：</td>
			 <td>
			 <fmt:out fmtvalue="${entity.headOfficeMagFee}" fmtclass="money"></fmt:out>
			</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td>浮动管理费率(%)：</td>
			 <td>
			 <fmt:out fmtvalue="${entity.felMagFee}" fmtclass="money"></fmt:out>
			</td>
			<td>客户收益率(%)：</td>
			<td>
			<fmt:out fmtvalue="${entity.profitRate}" fmtclass="money"></fmt:out>
			</td>
			<td>销售费率(%)：</td>
			<td>
			<fmt:out fmtvalue="${entity.sellRate}" fmtclass="money"></fmt:out>
				</td>
			<td align="right">
				VIP等级：
			</td>
			<td>
				<dic:out dicType="VIP_GRADE" dicNo="${entity.vipGrade}"></dic:out>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				个人客户最高投资额：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.highestInvestment}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				个人客户最低投资额：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.lowestInvestment}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				个人客户投资倍增金额：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.multiMoney}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				个人客户最低持有份额(份)：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.holdMoney}" fmtclass="money"></fmt:out>
			</td>
		</tr>
		<tr>
			<td align="right">
				机构客户最大投资额：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.corpHighestInvestment}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				机构客户最低投资额：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.corpLowestInvestment}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				机构客户倍增金额：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.corpMultiMoney}" fmtclass="money"></fmt:out>
			</td>
			<td align="right">
				机构客户最低持有份额(份)：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.corpHoldMoney}" fmtclass="money"></fmt:out>
			</td>
		</tr>
		<tr>
			<td align="right">
				是否设置黑白名单：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isWhiteList}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否积分产品：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isPoint}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否控制客户行内属性：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isCustProp}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否允许质押：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isPledge}"></dic:out>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				分红方式：
			</td>
			<td>
				<dic:out  dicType="DIVIDEND_FLAG" dicNo="${entity.dividendFlag}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				默认分红方式：
			</td>
			<td>
			
			<dic:out dicType="DIVIDEND_FLAG"  dicNo="${entity.defaultDividendFlag}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否允许分红方式变更：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isDividendAlter}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否允许财产证明：
			</td>
			<td>
				<dic:out dicType="IS_YES" dicNo="${entity.isAssetProve}"></dic:out>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				是否预约自动认购：
			</td>
			<td>
				<dic:out  dicType="IS_YES" dicNo="${entity.isOrderAutoBuy}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				是否有存续周期：
			</td>
			<td>
				<dic:out  dicType="IS_YES" dicNo="${entity.isDuration}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				定投业务开办标志：
			</td>
			<td>
				<dic:out  dicType="IS_YES" dicNo="${entity.isDateInvest}"></dic:out>
				<span class="star">*</span>
			</td>
			<td align="right">
				转换标志：
			</td>
			<td>
				<dic:out dicType="TRANSITION_FLAG" dicNo="${entity.transitionFlag}"></dic:out>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				币种：
			</td>
			<td>
				<dic:out  dicType="TR_CURRENCY"  dicNo="${entity.currency}"></dic:out>
			</td>
			<td>计息基础：</td>
		 	<td>
		 	<c:choose>
		 		<c:when test="${webProdAssetMutualInfo.calIntBmk == '03' || gfProdAssetMutualInfo.calIntBmk == '03'}">
		 			<dic:out  dicType="DAYCOUNTBA" dicNo="03"></dic:out>
		 		</c:when>
		 		<c:otherwise>
		 			<dic:out  dicType="DAYCOUNTBA" dicNo="${entity.dayCountBasis}"></dic:out>
		 		</c:otherwise>
		 	</c:choose>
				<span class="star">*</span>
			</td>
			<td align="right">
				一次性费用：
			</td>
			<td>
			<fmt:out fmtvalue="${entity.nonrecurringCharge}" fmtclass="money"></fmt:out>	
			</td>
		<td>总行代为操作分行自平衡产品标识：</td>
		<td><dic:out dicType="IS_YES" dicNo="${entity.headAutoBalanceFlag}"></dic:out></td>
		</tr>
		<tr>
			<td>保本产品成本户类型：</td>
			<td>
			<c:choose>
				<c:when test="${entity.custType=='1'}">
					<dic:out dicType="COST_ASSET_TYPE_P" dicNo="${one}"></dic:out>
					<dic:out dicType="COST_ASSET_TYPE_P" dicNo="${two}"></dic:out>
				</c:when>
				<c:when test="${entity.custType=='2'}">
					<dic:out dicType="COST_ASSET_TYPE_O" dicNo="${one}"></dic:out>
					<dic:out dicType="COST_ASSET_TYPE_O" dicNo="${two}"></dic:out>
				</c:when>
				<c:otherwise>
					<span>无</span>
				</c:otherwise>
			</c:choose>
			</td>
			<td>净值最晚延迟日：</td>
			<td>
				<dic:out dicType="LAST_DAY" dicNo="${gfProdAssetMutualInfo.netvalueLastDay}"></dic:out>
			</td>
			<td align="right">
				SPPI测试是否通过：
			</td>
			<td>
				<dic:out  dicType="IS_YES" dicNo="${gfProdAssetMutualInfo.sppi}"></dic:out>
			</td>
			<td align="right">
				推荐标识：
			</td>
			<td>
				<dic:out  dicType="IS_YES" dicNo="${gfProdAssetMutualInfo.isRecommend}"></dic:out>
			</td>
		</tr>
		<tr>
			<td align="right">
				产品到期收益和手续费是否转人民币：
			</td>
			<td>
				<dic:out  dicType="IS_YES" dicNo="${entity.isProfitFeeToRmb}"></dic:out>
			</td>
			<td>专属标志：</td>
			<td>
			    <dic:out dicType="EXCLUSIVE_FLAG" dicNo="${entity.exclusiveFlag}"></dic:out>
			</td>
			<td>目标客户类型：</td>
			 <td>
			    <dic:out dicType="TARGET_CUSTOMER" dicNo="${entity.targetCustomer}"></dic:out>
			 </td>
			<td>投资性质：</td>
			<td>
			    <dic:out dicType="INVESTMENT_NATURE" dicNo="${entity.investmentNature}"></dic:out>
			</td>
		</tr>
		<tr>
		    <td>存续期规则</td>
			<td>
			   <dic:out dicType="RULE_FLAG" dicNo="${entity.ruleFlag}"></dic:out>
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	</form>
	</body>
</html>