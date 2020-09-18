<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
		<title></title>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript">
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
	<!--startprint-->
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
							金额(元):
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
		<!--endprint-->
		<div align="center">
			<button type="button" onclick="doPrint()"><span class="icon_print">打印</span></button>
		</div>
</body>
</html>
