<%@page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
			<title></title> 
			<!--基本选项卡start-->
			<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
			<!--基本选项卡end-->
			<!-- 日期选择框start -->
			<script src="<%=path%>/libs/js/form/datePicker/WdatePicker.js" type="text/javascript"></script>
			<!-- 日期选择框end -->
	<script>

$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
})
function doPrint(){
	//调用打印方法
	 bdhtml = window.document.body.innerHTML;
	sprnstr = "<!--startprint-->"; //开始打印标识字符串有17个字符
	eprnstr = "<!--endprint-->"; //结束打印标识字符串
	prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17); //从开始打印标识之后的内容
	prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
	window.document.body.innerHTML = sprnstr + prnhtml + eprnstr; //把需要打印的指定内容赋给body.innerHTML
	window.print(); //调用浏览器的打印功能打印指定区域
}
</script>
</head>
<body>
	<form id="form1">
   			<div name="理财产品设计信息" style="width:100%;height:80%;">
   			<!--startprint-->
   				<table class="tableStyle" width="80%" mode="list" formMode="line"  fixedCellHeight="true">
   					<tr>
   						<td colspan="5">
   							<div align="center">理财业务产品审核表</div>
   						</td>
   					</tr>
   					<tr>
						<td>
							<div align="center">产品代码</div>
						</td>	 
						<td>
							<div align="center">${webProdBaseInfoDTO.prodCode }</div>
						</td>  					
						<td>
							<div align="center">产品名称</div>
						</td>
						<td colspan="2">
							<div align="center">${webProdBaseInfoDTO.prodName }</div>
						</td>
   					</tr>
   					<tr>
						<td colspan="7">
							<div align="center">产品信息</div>
						</td>  					
   					</tr>
   					<tr>
   						<td rowspan="5">
   							<div align="center">基本信息</div>
   						</td>
   						<td>
   							<div align="center">产品成立日期</div>
   						</td>
   						<td>
   							<div align="left">${webProdBaseInfoDTO.prodBeginDate }</div>
   						</td>
   						<td width="20%">
   							<div align="center">拟募集金额(万)</div>
   						</td>
   						<td>
   							<div align="left">${webProdBaseInfoDTO.planIssueAmt }</div>
   						</td>
   					</tr>
   					<tr>
   						<td>
   							<div align="center">产品终止日期</div>
   						</td>
   						<td>
   							<div>${webProdBaseInfoDTO.prodEndDate }</div>
   						</td>
   						<td>
   							<div align="center">期限(天)</div>
   						</td>
   						<td>
   							<div>${webProdBaseInfoDTO.prodDurationDays }</div>
   						</td>
   					</tr>
   					<tr>
   						<td>
   							<div align="center">客户收益率(%)</div>
   						</td>
   						<td>
   							<div align="center">
   								<fmt:out fmtvalue="${webProdBaseInfoDTO.profitRate }" fmtclass="money"></fmt:out>
   							</div>
   						</td>
   						<td>
   							<div align="center">销售费率(%)</div>
   						</td>
   						<td>
   							<div align="center">
   								<fmt:out fmtvalue="${webProdBaseInfoDTO.sellRate }" fmtclass="money"></fmt:out>
   							</div>
   						</td>
   					</tr>
   					<tr>
   						<td>
   							<div align="center">产品托管费率(%)</div>
   						</td>
   						<td>
   							<div align="center"><fmt:out fmtvalue="${webProdBaseInfoDTO.prodControlFeeRate }" fmtclass="money"></fmt:out></div>
   						</td>
   						<td>
   							<div align="center">产品推荐费率(%)</div>
   						</td>
   						<td>
   							<div align="center">
   								<fmt:out fmtvalue="${webProdBaseInfoDTO.prodRecomRate }" fmtclass="money"></fmt:out>
   							</div>
   						</td>
   					</tr>
   					<tr>
   						<td width="22%">
   							<div align="center">超额留存收益率(%)</div>
   						</td>
   						<td>
   							<div align="center"><fmt:out fmtvalue="${webProdBaseInfoDTO.overfulfilProfit }" fmtclass="money"></fmt:out></div>
   						</td>
   					    <td></td>
   						<td></td>
   					</tr>
   					<tr>
   						<td width="15%" rowspan="4">
   							<div align="center">账户信息</div>
   						</td>
   						<td>
   							<div align="center">托管户名</div>
   						</td>
   						<td colspan="3">
   							<div align="center">${gfTrusteeshipAcct.trusteeshipName}</div>
   						</td>
   					</tr>
   					<tr>
   						<td>
   							<div align="center">托管账户</div>
   						</td>
   						<td colspan="5">
   							<div align="center">${gfTrusteeshipAcct.trusteeshipAcct }</div>
   						</td>
   					</tr>
   					<tr>
   						<td>
   							<div align="center">开户行</div>
   						</td>
   						<td colspan="5">
   							<div align="center">${gfTrusteeshipAcct.openBranchName }</div>
   						</td>
   					</tr>
					<tr>
   						<td>
   							<div align="center">支付行号</div>
   						</td>
   						<td colspan="5">
   							<div align="center">${gfTrusteeshipAcct.openBranchCode }</div>
   						</td>
   					</tr>
   					<%-- <tr>
						<td colspan="7">
							<div align="center">项目信息</div>
						</td>  	
   					</tr>
   			 	<c:forEach begin="0" end="${fn:length(assetList) }" varStatus="as" items="${assetList }"  step="1"  var="asset" > 
   					<tr>
   						<td>
   							<div align="center">项目${as.index+1 } 要素</div>
   						</td>
   						<td>
   							<div align="center">项目名称</div>
   						</td>
   						<td colspan="5">
   							<div align="center">${asset.assetName }</div>
   						</td>
   					</tr>
   					<tr>
   						<td></td>
   						<td>
   							<div align="center">项目推荐人</div>
   						</td>
   						<td>
   							<div align="center">${asset.assetRecommendationBranch }</div>
   						</td>
   						<td>
   							<div align="center">项目投资期间</div>
   						</td>
   						<td>
   							<div align="center">${asset.assetBeginDate }-${asset.assetEndDate }</div>
   						</td>
   					</tr>
   					<tr>
   						<td></td>
   						<td>
   							<div align="center">拟投资金额</div>
   							<div align="center"></div>
   						</td>
   						<td>
   							<div align="center">
   								<fmt:out fmtvalue="${asset.investAmt }" fmtclass="money"></fmt:out>亿元
   							</div>
   						</td>
   						<td>
   							<div align="center">投资收益率(%)</div>
   						</td>
   						<td>
   							<div align="center">
   								<fmt:out fmtvalue="${asset.profitRate }" fmtclass="money"></fmt:out>
   							</div>
   						</td>
   					</tr>
   				</c:forEach> --%>
   					<tr>
   						<td colspan="7">
   							<div align="center">意见列表</div>
   						</td>
   					</tr>
   					<c:forEach begin="0" end="${fn:length(commentMap) }" varStatus="as" items="${commentMap }"  step="1"  var="commentMap" > 
   						<tr>
	   						<td>
	   							<div align="center">${commentMap.key }</div>
	   						</td>
	   						<td colspan="4">
	   							<div align="center">${commentMap.value }</div>
	   						</td>
	   					</tr>
   					</c:forEach>
				</table>
				<!--endprint-->
				<div align="center">
					<button type="button" onclick="doPrint()"><span class="icon_print">打印</span></button>
				</div>
    		</div>
	   	</form>
	</body>
</html>