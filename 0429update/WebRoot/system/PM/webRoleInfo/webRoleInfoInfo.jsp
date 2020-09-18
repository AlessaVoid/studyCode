<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_info.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<!-- 树组件 -->
		<link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
		<script type="text/javascript">
$(function() {
	//复核框
	var setting1 = {
	    check: {
	        enable: true
	    }
	};
	//树节点
	var zNodes = ${treeNodes};
	var treeObj = $.fn.zTree.init($("#tree"), setting1, zNodes);
});
</script>
	</head>
	<body>
		<form id="form1">
			<div class="basicTabModern">
	   			<div name="角色信息" style="width:100%;height:80%;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								机构级别：
							</td>
							<td>
								<dic:out dicType="ORGAN_LEVEL" dicNo="${webRoleInfoDTO.organLevel}"></dic:out>
							</td>
						</tr>
	   					<tr>
							<td align="right" width="50%">
								角色编号：
							</td>
							<td>
								${webRoleInfoDTO.roleCode }
							</td>
						</tr>
						<tr>
							<td align="right">
								角色名称：
							</td>
							<td>
								${webRoleInfoDTO.roleName}
							</td>
						</tr>
						<tr>
							<td align="right">
								最后修改日期：
							</td>
							<td>
								${webRoleInfoDTO.latestModifyDate }
							</td>
						</tr>
						<tr>
							<td align="right">
								最后修改时间：
							</td>
							<td>
								${webRoleInfoDTO.latestModifyTime }
							</td>
						</tr>
						<tr>
							<td align="right">
								最后修改用户：
							</td>
							<td>
								${webRoleInfoDTO.latestOperCode }
							</td>
						</tr>
					</table>
				</div>
				<div name="权限分配" style="width:100%;height:80%;">
    				<fieldset>
						<legend>
							角色功能对照表信息
						</legend>
						<div>
							<ul id="tree" class="ztree"></ul>
						</div>
					</fieldset>
    			</div>
    		</div>
	   	</form>
	</body>
</html>