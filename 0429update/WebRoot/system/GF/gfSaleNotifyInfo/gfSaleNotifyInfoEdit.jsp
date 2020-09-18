<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title></title> 
		<!--在线编辑器 start-->
		<link href="${path}/libs/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css"  />
		<script src="${path}/libs/kindeditor/kindeditor.js" type="text/javascript" charset="utf-8"></script>
		<script src="${path}/libs/kindeditor/lang/zh_CN.js" type="text/javascript" charset="utf-8"></script>
	<!--在线编辑器end-->
	<script>
	var options={
			
	};
        var editor;
        KindEditor.ready(function(K) {
                editor = K.create('#contentByte_id',options);
        });
		</script>
	
		<script type="text/javascript">
	   	var grid = null;
	   	function initComplete() {
	   		grid = $("#dataBasic")
	   				.quiGrid(
	   						{
	   							columns : [{
									display : '产品代码',
									name : 'prodCode',
									width : '15%',
									align : 'center',
									frozen:true
								},{
									display : '产品名称',
									name : 'prodName',
									width : '25%',
									align : 'center',
									frozen:true
								},{
									display : '产品运作模式',
									name : 'prodOperModel',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "封闭式净值型";
										}
										if(value=="02"){
											return "封闭式非净值型";
										}
										if(value=="03"){
											return " 开放式净值型";
										}
										if(value=="04"){
											return " 开放式非净值型";
										}
									}
								},{
									display : '产品收益类型',
									name : 'profitType',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="01"){
											return "保证收益";
										}
										if(value=="02"){
											return "保本浮动收益";
										}
										if(value=="03"){
											return " 非保本浮动收益";
										}
									}
								},{
									display : '产品风险等级',
									name : 'prodRiskLevel',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "一级（低）";
										}
										if(value=="2"){
											return "二级（中低）";
										}
										if(value=="3"){
											return "三级（中）";
										}
										if(value=="4"){
											return "四级（中高）";
										}
										if(value=="5"){
											return "五级（高）";
										}
									}
								},{
									display : '是否自主平衡',
									name : 'isAutoBalance',
									width : '15%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
										if(value=="1"){
											return "是";
										}
										if(value=="0"){
											return "否";
										}
									}
								}],
	   							url : '<%=path%>/gfSaleNotifyInfo/findProd.htm?ctype=1',
	   							sortName : '',
	   							rownumbers : true,
	   							checkbox : true,
	   							height : '100%',
	   							width : "100%",
	   						 	usePager: false,
	   							toolbar : {
	   								items : [
									{text : '产品移除申请', click : doDeleteSubmit, iconClass : 'icon_export'}
	   									]
	   							}
	   						 	
	   						});
	   		}	
	   	function doDeleteSubmit(){
	   		var rows = grid.getSelectedRows();
	   		var rowsLength = rows.length;
	   		if (rowsLength == 0) {
	   			top.Dialog.alert("请选择产品信息!");
	   			return;
	   		}
	   		var data=grid.getSelectedRows();
	   		var prodCodes="";
	   		$.each(data,function(i,v){
	   			prodCodes=prodCodes+","+v.prodCode;
	   			
	   		});
	   		prodCodes=prodCodes.substring(1);
	   		showDialog("<%=path%>/gfSaleNotifyInfo/doApplyUI.htm?prodCodes="+prodCodes,"销售通知产品删除",800,600);
	   	}
		   	function doAppSubmit(){
		   		var rows = grid.getSelectedRows();
		   		var rowsLength = rows.length;
		   		if (rowsLength == 0) {
		   			top.Dialog.alert("请选择产品信息!");
		   			return;
		   		}
		   		var text = $("#contentByte_id").val();
		   		if (text.length == 0) {
		   			top.Dialog.alert("通知内容不能为空!");
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
		   				$("#inotifyContent_id").val(editor.text());
		   				editor.sync();
		   				var param = $("#form1").formToArray();
		   				var map = {};
		   				map.name = "gridData";
		   				map.value= JSON.stringify(grid.getSelectedRows());
		   				param.push(map);
		   				$.post("<%=path%>/gfSaleNotifyInfo/insert.htm", param, function(result) {
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
		   	}
	   	
	   	</script>
	</head>
	<body>
		<form id="form1">
		<div class="basicTabModern">
				<div name="销售通知" style="width:100%;height:300px;">
	   				<table class="tableStyle" width="80%" mode="list" formMode="line">
	   					<tr>
							<td align="right">
								通知内容：
							</td>
							<td>
								<textarea id="contentByte_id" class="textarea" style="width: 80%; height: 80px;" name="contentByte">${content}</textarea><span class="star">*</span>
								<input id="inotifyContent_id" name="inotifyContent" type="hidden"/>
							</td>
						</tr>
    				</table>
    				<div id="dataBasic"></div>
    			</div>
    			<div name="复核信息" style="width:100%;height:80px;">
					<div style="width: 98%">
						<input type="hidden" name="operDescribe" value="生成产品销售通知" /> 
						<table class="tableStyle" width="97%" mode="list" formMode="line">
							<tr>
								<td width="38%">操作员代码：</td>
								<td>
									${sessionScope.sessionUser.opercode} 
									<input type="hidden" name="appUser" value="${sessionScope.sessionUser.opercode}" />
								</td>
							</tr>
							<tr>
								<td width="38%">操作员姓名：</td>
								<td>${sessionScope.sessionUser.opername} <input
									type="hidden" name="appOperName"
									value="${sessionScope.sessionUser.opername}" /></td>
							</tr>
							<tr>
								<td>所属机构代码：</td>
								<td>${sessionScope.sessionUser.organcode} <input
									type="hidden" name="appOrganCode"
									value="${sessionScope.sessionUser.organcode}" /></td>
							</tr>
							<tr>
								<td>所属机构名称：</td>
								<td>${sessionScope.sessionOrgan.organname} <input
									type="hidden" name="appOrganName"
									value="${sessionScope.sessionOrgan.organname}" /></td>
							</tr>
							<tr>
								<td>操作员角色：</td>
								<td>${sessionScope.sessionUserRole} <input type="hidden"
									name="appRoleName" value="${sessionScope.sessionUserRole}" /></td>
							</tr>
							<tr>
								<td>复核人员：</td>
								<td><select selWidth="127" 
									id="repUserCode" name="repUserCode" prompt="--请选择--" class="validate[required]"
									url="<%=path%>/fdOper/getAppUserList.htm?funCode=GF-11-03">
								</select> <span class="star">*</span> 
								<input type="hidden" name="repUserName" id="repUserName"/></td>
							</tr>
							
							<tr>
								<td>所属机构代码：</td>
								<td><span id="repUserOrganCode1"></span> <input type="hidden"
									id="repUserOrganCode" name="repUserOrganCode"/></td>
							</tr>
							<tr>
								<td>所属机构名称：</td>
								<td><span id="repUserOrganName1"></span> <input type="hidden"
									id="repUserOrganName" name="repUserOrganName"/></td>
							</tr>

							<tr>
								<td>复核人员角色：</td>
								<td><span id="repRoleName1"></span> <input type="hidden"
									id="repRoleName" name="repRoleName"/></td>
							</tr>
							<tr>
								<td>备注说明：</td>
								<td><textarea class="textarea" style="width: 80%; height: 80px;" name="appRemark"></textarea>
								</td>
							</tr>
							<tr>
							<td colspan="4">
								<div align="center">
									<button type="button" onclick="doAppSubmit();" class="saveButton"/>
									<button type="button" onclick="return cancel()" class="cancelButton" />	
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