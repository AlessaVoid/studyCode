<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>发售区域参数</title> 
		<style type="text/css"> 
		.checkbox{vertical-align:middle; margin-top:0;} 
		.zuo {
		    float: left;
		    width: 180px;
		}
		</style> 
		<script type="text/javascript">
		$(function() {
			//修正由于使用了tab导致高度计算不准确
			if (broswerFlag == "IE6") {
				setTimeout(function() {
					top.iframeHeight('frmrightChild');
				}, 500);
			}
			createCheckBox();
			var flag = $("#flag").val();
			if(flag == 'false'){
				selectAllItem();
				$("#organ-all").attr("checked",true);
			}
		});
		function getCheckValue(){
		    var msg = "";
		    $("input:checkbox[name=provCode]").each(function(){
		        if($(this).attr("checked")){
		            msg += "," + $(this).val();
		        }
		    });
		    if(msg == ""){
		        msg = "无";
		    }else{
		        msg = msg.substring(1);
		    }
		    $("#branchCode").val(msg);
		    return msg;
		}
		function createCheckBox(){
			var provCode = ${provCode};
			if(provCode != null){
				$("#branchCodes").after(provCode);
			}
			$("#branchCodes").render();
		}
		//提交方法
		function sub(formId,url) {
			var valid = $("#"+formId).validationEngine( {
				returnIsValid : true
			});
			if(valid){
			var ids=getCheckValue();
			if('无'==ids){
				top.Dialog.alert("发售区域不可为空！");
				return false;
			}
				top.Dialog.confirm("是否保存区域信息?", function() {
					$.post(url, $("#"+formId).serialize(), function(result) {
						if (result.success == "true" || result.success == true) {
							top.Dialog.alert(result.msg,function(){
								window.location.reload(true);
								parent.SetOrderFunc(result.bean.order);
								});
						} else {
							top.Dialog.alert(result.msg);
						}
					}, "json");
				});
			} else {
				top.Dialog.alert("验证未通过！");
			}
		}
		//全部选中
		function selectAllItem(){
			 $("input:checkbox[name=provCode]").attr("checked",true);
		}
		//全部清除
		function clearAllItem(){
			 $("input:checkbox[name=provCode]").attr("checked",false);
		}
		//通过全选按钮的状态，控制下面所有机构的选中状态
		function changeBranchCodes(){
			 if($("#organ-all").attr("checked")=='checked'){
				 selectAllItem();
			 }else{
				 clearAllItem();
			 }
		}
		//动态修改全选按钮的选中状态
		function changeOrganAll(){
			var selectValues = getCheckValue().split(",");
			if(selectValues.length != 36){
				$("#organ-all").attr("checked",false);
			}else{
				$("#organ-all").attr("checked",true);
			}
		}
		</script>
	</head>
	<body>
	<form id="form1">
				<input  type="hidden" name="prodCode" value="${prodCode}"/>
			<input type="hidden" id="uuid" name="uuid" value="${uuid}"/>
			<input type="hidden" id="order" name="order" value="${order}"/>
			<input type="hidden" id="branchCode" name="branchCode" />
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right">
					发售区域：
				</td>
				<td>
					<input type="hidden" id="prodCode" name="prodCode" value="${prodCode }"/>
					<input type="hidden" id="flag" name="flag" value="${flag }"/>
					<!-- 区域树 -->
					 <div id="selectAll">
					 	<input type="checkbox" onclick="changeBranchCodes()" id="organ-all" name="organ-all" value="00000000" /><label for="organ-all" class="hand">全国</label>
					 </div>
					 <div id="branchCodes">
					 </div>
					 <span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="center">
						<button type="button" onclick="return sub('form1','<%=path%>/gfProdEleChange/compareIssueAreaInfo.htm')" class="saveButton"/>
					</div>
				</td>
			</tr>
		</table>
   	</form>
	</body>
</html>