<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/common/common_info.jsp"%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title></title>
</head>
<body>
		<table class="tableStyle" width="80%" mode="list" formMode="line"
			fixedCellHeight="true">
			<tr>
				<td align="right" width="38%">复核代码：</td>
				<td>${webReviewMainDTO.appNo}</td>
			</tr>

			<tr>
				<td align="right">功能编号：</td>
				<td>${webReviewMainDTO.tradeCode}</td>
			</tr>

			<tr>
				<td align="right">交易描述：</td>
				<td>${webReviewMainDTO.operDescribe}</td>
			</tr>
			<tr>
				<td align="right">维护代码：</td>
				<td>${webReviewMainDTO.operNo}</td>
			</tr>

			<tr>
				<td align="right">维护名称：</td>
				<td>${webReviewMainDTO.operName}</td>
			</tr>

			<tr>
				<td align="right">申请操作员代码：</td>
				<td>${webReviewMainDTO.appUser}</td>
			</tr>

			<tr>
				<td align="right">申请操作员姓名：</td>
				<td>${webReviewMainDTO.appOperName}</td>
			</tr>

			<tr>
				<td align="right">申请操作员所属机构代码：</td>
				<td>${webReviewMainDTO.appOrganCode}</td>
			</tr>

			<tr>
				<td align="right">申请操作员所属机构名称：</td>
				<td>${webReviewMainDTO.appOrganName}</td>
			</tr>

			<tr>
				<td align="right">申请操作员角色：</td>
				<td>${webReviewMainDTO.appRoleName}</td>
			</tr>

			<tr>
				<td align="right">申请日期：</td>
				<td>${fn:substring(webReviewMainDTO.appDate,0,4)}年${fn:substring(webReviewMainDTO.appDate,4,6)}月${fn:substring(webReviewMainDTO.appDate,6,8)}日</td>
			</tr>

			<tr>
				<td align="right">申请时间：</td>
				<td><c:if test="${not empty webReviewMainDTO.appTime}">${fn:substring(webReviewMainDTO.appTime,0,2)}:${fn:substring(webReviewMainDTO.appTime,2,4)}:${fn:substring(webReviewMainDTO.appTime,4,6)}</c:if></td>
			</tr>

			<tr>
				<td align="right">申请类型：</td>
				<td><dic:out dicType="D_APPTYPE"
						dicNo="${webReviewMainDTO.appType}"></dic:out></td>
			</tr>

			<tr>
				<td align="right">申请说明：</td>
				<td class="text_break">${webReviewMainDTO.appRemark}</td>
			</tr>
			<tr>
				<td align="right">复核状态：</td>
				<td><dic:out dicType="D_APPSTATUS"
						dicNo="${webReviewMainDTO.repStatus}"></dic:out></td>
			</tr>

			<tr>
				<td align="right">复核操作员代码：</td>
				<td>${webReviewMainDTO.repUserCode}</td>
			</tr>

			<tr>
				<td align="right">复核操作员姓名：</td>
				<td>${webReviewMainDTO.repUserName}</td>
			</tr>

			<tr>
				<td align="right">复核操作员所属机构代码：</td>
				<td>${webReviewMainDTO.repUserOrganCode}</td>
			</tr>

			<tr>
				<td align="right">复核操作员所属机构名称：</td>
				<td>${webReviewMainDTO.repUserOrganName}</td>
			</tr>

			<tr>
				<td align="right">复核操作员角色：</td>
				<td>${webReviewMainDTO.repRoleName}</td>
			</tr>

			<tr>
				<td align="right">复核日期：</td>
				<td><c:if test="${not empty webReviewMainDTO.repDate}">${fn:substring(webReviewMainDTO.repDate,0,4)}年${fn:substring(webReviewMainDTO.repDate,4,6)}月${fn:substring(webReviewMainDTO.repDate,6,8)}日</c:if></td>
			</tr>

			<tr>
				<td align="right">复核时间：</td>
				<td><c:if test="${not empty webReviewMainDTO.repTime}">${fn:substring(webReviewMainDTO.repTime,0,2)}:${fn:substring(webReviewMainDTO.repTime,2,4)}:${fn:substring(webReviewMainDTO.repTime,4,6)}</c:if></td>
			</tr>

			<tr>
				<td align="right">复核说明：</td>
				<td class="text_break">${webReviewMainDTO.repRemark}</td>
			</tr>
		</table>
</body>
</html>


