<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
	</head>
	<body>
		<form id="form1">
			<div class="basicTabModern">
	   			<div name="人员信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right" width="38%">
								机构代码：
							</td>
							<td>
								${fdOper.organcode }
							</td>
						</tr>
	   					<tr>
							<td align="right" width="38%">
								操作员编号：
							</td>
							<td>
								${fdOper.opercode }
							</td>
						</tr>
						<tr>
							<td align="right">
								操作员姓名：
							</td>
							<td>
								${fdOper.opername }
							</td>
						</tr>
					</table>
				</div>
    		</div>
	   	</form>
	</body>
</html>