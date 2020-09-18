<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_info.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<script>
$(function() {
	//修正由于使用了tab导致高度计算不准确
	if (broswerFlag == "IE6") {
		setTimeout(function() {
			top.iframeHeight('frmrightChild');
		}, 500);
	}
});
</script>
	</head>
	<body>
		<%-- <form id="form1">
  				<table class="tableStyle" width="80%" mode="list" formMode="line">
  					<tr>
						    <td align="right" width="38%">产品代码：</td>
						    <td>
							    ${entity.prodCode}
						    	<span class="star">*</span>
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">启用日期：</td>
						    <td>
						    	${entity.beginDate}
								<span class="star">*</span>
						    </td>
						</tr>
	   					<tr>
						    <td align="right" width="38%">汇率：</td>
						    <td>
								${entity.transfParities}
								<span class="star">*</span>
						    </td>
						</tr>
  			</table>
	   	</form> --%>
	   	<div name="业绩比较基准信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
						    <td align="right" width="38%">产品代码：</td>
						    <td>
							    ${entity.prodCode}
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">显示日期：</td>
						    <td>
								${entity.performanceDate}
						    </td>
						</tr>
	   					<tr>
						    <td align="right" width="38%">启用日期：</td>
						    <td>
								${entity.performanceUseDate}
						    </td>
						</tr>
						<tr>
						    <td align="right" width="38%">业绩比较基准值(%)：</td>
						    <td>
						        ${entity.performanceValue}
						    </td>
						</tr>
						<tr>
				           <td>是否挂钩标的：</td>
				             <td>
						     <dic:out dicNo="${entity.isPeg}" dicType="IS_YES"></dic:out>
						    </td>
						</tr>
						<tr>
						      <td>挂钩描述：</td>
						      <td>
						       ${entity.pegDescription}
						      </td>
		                </tr>
    				</table>
    			</div>
	</body>
</html>