<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/common_edit.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<!--数据表格start-->
<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<title></title>
<script type="text/javascript">
		//数据表格初始化
var grid  ;   
function initComplete() {
	var gridData = ${gridData};
	grid = $("#dataBasic")
		.quiGrid(
			{
			columns : [
					{
						display : '申购日',
						name : 'purchaseDate',
						align : 'center',
						width : "12%",
						render : function(rowdata) {
							return '<div style="margin-right: 45%;" align="right">'
									+ rowdata.purchaseDate
											.replace(/-/gi, "")
									+ '</div>';
						}
					},
					{
						display : '份额确认日',
						name : 'taCfmDate',
						align : 'center',
						width : "12%",
						render : function(rowdata) {
							return '<div style="margin-right: 45%;" align="right">'
									+ rowdata.taCfmDate
											.replace(/-/gi, "")
									+ '</div>';
						}
					},
					{
						display : '首次投资周期结束日',
						name : 'firstInvCycleEndDate',
						align : 'center',
						width : "12%",
						render : function(rowdata) {
							return '<div style="margin-right: 45%;" align="right">'
									+ rowdata.firstInvCycleEndDate
											.replace(/-/gi, "")
									+ '</div>';
						}
					},
					{
						display : '开始时间（从）',
						name : 'beginTime',
						align : 'center',
						width : "15%",
						render : function(rowdata) {
							if (rowdata.beginTime == null) {
								return '<div style="margin-right: 45%;" align="right">'
										+ "" + '</div>';
							} else {
								return '<div style="margin-right: 45%;" align="right">'
										+ rowdata.beginTime
												.replace(/-/gi,
														"")
										+ '</div>';
							}
							;
						}
					},
					{
						display : '结束时间（到）',
						name : 'endTime',
						align : 'center',
						width : "15%",
						render : function(rowdata) {
							if (rowdata.endTime == null) {
								return '<div style="margin-right: 45%;" align="right">'
										+ "" + '</div>';
							} else {
								return '<div style="margin-right: 45%;" align="right">'
										+ rowdata.endTime
												.replace(/-/gi,
														"")
										+ '</div>';
							}
							;
						}
					},
					{
						display : '开放期额度',
						name : 'limit',
						align : 'center',
						width : "10%",
						render : function(rowdata) {
							return '<div  style="margin-right: 45%;" align="right">'
									+ formatRound(
											rowdata.limit, 2)
									+ '</div>';
						}
					},
					{
						display : '资金处理方式',
						name : 'amtHandleFlag',
						align : 'center',
						width : "12%",
						render : function(rowdata) {
							if (rowdata.amtHandleFlag == "1") {
								return "批量";
							} else if (rowdata.amtHandleFlag == "2") {
								return "实时";
							}
						}
					},
					{
						display : '份额确认方式',
						name : 'quotAffirmType',
						align : 'center',
						width : "12%",
						render : function(rowdata) {
							if (rowdata.quotAffirmType == "1") {
								return "批量";
							} else if (rowdata.quotAffirmType == "2") {
								return "实时";
							}
						}
					} ],
				data : gridData,
				rownumbers : true,
				usePager : false,
				percentWidthMode : true,
				sortName : 'id'
			});
		//四舍五入算法,dp代表保留小数点位数
		function formatRound(num, dp) {
			return (zh(Math.round(parseFloat(num.replace(/[^\d\.-]/g, ""))
					* Math.pow(10, dp))
					/ Math.pow(10, dp), dp));
		}
	}
	//生成
	function create() {
		//生成数据之前，删除原有对应数据
		var data = grid.records;
		for (var record in data){
			grid.deleteRow(data[record]);
		}
		$(".money").each(function() {
			rmoney(this);
		});
		$.post("<%=path%>/webProdOpenDurationInfo/createInvCycle.htm",$("#form1").formToArray(),function(result) {
			for(var i=0;i<result.length;i++){
					var duration = {
						purchaseDate : result[i].purchaseDate,
						taCfmDate : result[i].taCfmDate,
						invCycleEndDate : result[i].invCycleEndDate,
						firstInvCycleEndDate : result[i].firstInvCycleEndDate,
						beginTime : result[i].beginTime,
						endTime : result[i].endTime,
						amtHandleFlag : result[i].amtHandleFlag,
						quotAffirmType : result[i].quotAffirmType,
						limit : result[i].limit
				    };
					grid.add(duration);
				}
			});
	   	}
	  	//保存
	   	function doAppSubmit() {
	   		if (grid.rows.length == 0) {
	   			top.Dialog.alert("请先生成存续期!");
	   			return;
	   		}
	   		var valid = $("#form1").validationEngine( {
	   			returnIsValid : true
	   		});
	   		if (valid) {
	   			top.Dialog.confirm("确定要保存操作吗?|操作提示", function() {
	   				$(".money").each(function(){
	   					rmoney(this);
	   				});
	   				var param = $("#form1").formToArray();
	   				var map = {};
	   				map.name = "gridData";
	   				map.value= JSON.stringify(grid.rows);
	   				param.push(map);
	   				$.post("<%=path%>/webProdOpenDurationInfo/saveInvCycleApp.htm", param, function(result) {
	   					if (result.success == "true" || result.success == true) {
	   						top.Dialog.alert(result.msg, function() {
	   							top.frmright.window.location.reload(true);
	   							top.Dialog.close();
	   						});
	   					} else {
	   						$(".money").each(function(){
	   							fmoney(this);
	   						});
	   						top.Dialog.alert(result.msg);
	   					}
	   				}, "json");
	   			});
	   		}else{
	   			top.Dialog.alert("验证未通过！");
	   		}
	   	};
	   	</script>
</head>
<body>
	<form id="form1">
		<input type="hidden" id="prodCode" name="prodCode"
			value="${prodCode }" />
		<input type="hidden" id="openLimit" name="openLimit" value="${openLimit}"/>
		<div class="basicTabModern">
			<div name="存续期生成" style="width:100%;height:300px;">
				<table class="tableStyle" width="80%" mode="list" formMode="line">
					<tr align="center">
						<td align="left">开放期额度：<input type="text" value="${openLimit}"
							class="money" disabled="disabled"/>元
						</td>
					</tr>
					<tr align="center">
						<td align="left"><input type="button" value="生成"
							onclick="create();" class="saveButton" /></td>
					</tr>
				</table>
				<div id="dataBasic"></div>
			</div>
			<div name="复核信息" style="width:100%;height:80px;">
				<div style="width: 98%">
					<input type="hidden" name="operDescribe" value="生成存续期" />
					<table class="tableStyle" width="97%" mode="list" formMode="line">
						<tr>
							<td width="38%">操作员代码：</td>
							<td>${sessionScope.sessionUser.opercode}<input type="hidden"
								name="appUser" value="${sessionScope.sessionUser.opercode}" />
							</td>
						</tr>
						<tr>
							<td width="38%">操作员姓名：</td>
							<td>${sessionScope.sessionUser.opername}<input type="hidden"
								name="appOperName" value="${sessionScope.sessionUser.opername}" /></td>
						</tr>
						<tr>
							<td>所属机构代码：</td>
							<td>${sessionScope.sessionUser.organcode}<input
								type="hidden" name="appOrganCode"
								value="${sessionScope.sessionUser.organcode}" /></td>
						</tr>
						<tr>
							<td>所属机构名称：</td>
							<td>${sessionScope.sessionOrgan.organname}<input
								type="hidden" name="appOrganName"
								value="${sessionScope.sessionOrgan.organname}" /></td>
						</tr>
						<tr>
							<td>操作员角色：</td>
							<td>${sessionScope.sessionUserRole}<input type="hidden"
								name="appRoleName" value="${sessionScope.sessionUserRole}" /></td>
						</tr>
						<tr>
							<td>复核人员：</td>
							<td><select selWidth="127" id="repUserCode"
								name="repUserCode" prompt="--请选择--" class="validate[required]"
								url="<%=path%>/fdOper/getAppUserList.htm?funCode=TRADE-11">
							</select> <span class="star">*</span> <input type="hidden"
								name="repUserName" id="repUserName" /></td>
						</tr>

						<tr>
							<td>所属机构代码：</td>
							<td><span id="repUserOrganCode1"></span> <input
								type="hidden" id="repUserOrganCode" name="repUserOrganCode" /></td>
						</tr>
						<tr>
							<td>所属机构名称：</td>
							<td><span id="repUserOrganName1"></span> <input
								type="hidden" id="repUserOrganName" name="repUserOrganName" /></td>
						</tr>

						<tr>
							<td>复核人员角色：</td>
							<td><span id="repRoleName1"></span> <input type="hidden"
								id="repRoleName" name="repRoleName" /></td>
						</tr>
						<tr>
							<td>备注说明：</td>
							<td><textarea class="textarea"
									style="width: 80%; height: 80px;" name="appRemark"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">

									<button type="button" onclick="doAppSubmit();"
										class="saveButton" />
									<button type="button" onclick="return cancel()"
										class="cancelButton" />
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>