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
		<c:choose>
			<c:when test="${param.method=='update' }">
				<input type="hidden" name="opercode" value="${fdOper.opercode }"/>
				<input type="hidden" name="organcode" value="${fdOper.organcode }"/>
			</c:when>
		</c:choose>
			<div class="basicTabModern">
	   			<div name="人员信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								机构代码：
							</td>
							<td>
							<c:choose>
								<c:when test="${param.method=='update' }">
									${fdOper.organcode }
								</c:when>
								<c:otherwise>
									<input type="text" name="organcode" value="${fdOper.organcode }" class="validate[required]"/>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
	   					<tr>
							<td align="right">
								操作员编号：
							</td>
							<td>
							<c:choose>
								<c:when test="${param.method=='update' }">
									${fdOper.opercode }
								</c:when>
								<c:otherwise>
									<input type="text" name="opercode" value="${fdOper.opercode }" class="validate[required]"/>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
							<td align="right">
								操作员姓名：
							</td>
							<td>
								<input type="text" name="opername" value="${fdOper.opername }" class="validate[required]"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center">
									<button type="button" onclick="return doSubmit('form1','<%=path%>/fdOper/${param.method}.htm')" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
								</div>
							</td>
						</tr>
    				</table>
    			</div>
    		</div>
	   	</form>
	</body>
</html>