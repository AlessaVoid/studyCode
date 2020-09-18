<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/common_edit.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>任务节点角色配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--数据表格start-->
<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
  <script type="text/javascript">
//机构级别下拉框
	var organLevel={"list":[{"value":"0","key":"总行"},{"value":"1","key":"一级分行"}]};
//客户类型下拉框
	var custType={"list":[{"value":"0","key":"不区分"},{"value":"1","key":"个人"},{"value":"2","key":"机构"}]};
//数据表格初始化
var taskRoleInfoGrid;
function initComplete(){
var gridData=${gridData};
//灵活期限收益组合参数
taskRoleInfoGrid = $("#taskRoleInfoParam").quiGrid({
       columns: [
       { display: '任务节点Id', name: 'taskId', align: 'center', width: "20%",
    	   editor:{type:'select',url:'${path}/workflow/getTaskIdByProcDefId.htm?procDefId=${procDefId}',selWidth:150,boxWidth:250}
       },
       { display: '驳回至任务节点Id', name: 'upTaskId', align: 'center', width: "20%",
    	   editor:{type:'select',url:'${path}/workflow/getTaskIdByProcDefId.htm?procDefId=${procDefId}',selWidth:150,boxWidth:250}
       },
       { display: '机构级别', name: 'organLevel', align: 'center', width: "20%",
    	   editor:{type:'select',data:organLevel,selWidth:150,boxWidth:250},
    	   render : function(rowdata) {
   		    if (rowdata.organLevel == "0") {
   				return "总行";
   			}else if (rowdata.organLevel == "1") {
   				return "一级分行";
   			}
 		}
       },
       { display: '客户类型', name: 'custType', align: 'center', width: "20%",
           editor:{type:'select',data:custType,selWidth:150,boxWidth:250},
    	   render : function(rowdata) {
    		    if (rowdata.custType == "1") {
    				return "个人";
    			}else if (rowdata.custType == "2") {
    				return "机构";
    			}else{
    				return "不区分";
    			}
  			}
       },
       { display: '审批后状态', name: 'appStatus', align: 'center', width: "20%",
    	   editor:{type:'select',url:'${path}/workflow/getProdStatus.htm',selWidth:150,boxWidth:250}
       }], 
       data:gridData, 
       rownumbers : true,
       enabledEdit: true,
       usePager: false, 
       sortName: 'taskId', 
       checkbox:true,
       percentWidthMode:true,
       toolbar : {
       items : [{text : '增加行',click : appendTaskRole,iconClass : 'icon_add'}, {line : true},
 			   	{text : '删除行',click : deleteTaskRole,iconClass : 'icon_remove'}, {line : true}]
 			   	}
	});
}

//追加尾行
function appendTaskRole(){
	var taskRoleInfo = {
		taskId: "appUser",
		roleCode: "000",
		organLevel: "0",
		custType:"1",
		appStatus:"0"
     };
	taskRoleInfoGrid.add(taskRoleInfo);
}
//删除选中行
function deleteTaskRole(){
    //选中一行或多行
    var rows = taskRoleInfoGrid.getSelectedRows();
    if (rows.length == 0) {
       top.Dialog.alert('请至少选择一行'); 
       return;
    }
    for(var index in rows){
    	taskRoleInfoGrid.deleteRow(rows[index]);
    }
}

//提交方法
function sub(formId,url) {
	var valid = $("#"+formId).validationEngine( {
		returnIsValid : true
	});
	if (valid) {
		top.Dialog.confirm("是否保存信息?", function() {
			var param = $("#"+formId).formToArray();
			var map = {};
			map.name = "gridData";
			map.value= JSON.stringify(taskRoleInfoGrid.getData());
			param.push(map);
			$.post(url,param,function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg,function(){
						window.location.reload(true);
						});
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		});
	} else {
		top.Dialog.alert("验证未通过");
	}
}
</script>
</head>
		<body>
		<form id="form1">
			<input  type="hidden" name="procDefId" value="${procDefId}"/>
			<div id="taskRoleInfoParam" class="padding_right5"></div>
   			<div align="center">
				<button type="button" onclick="return sub('form1','<%=path%>/webTaskRoleInfo/updateTaskRoleInfoNew.htm')" class="saveButton"/>
			</div>
	   	</form>
	</body>
</html>