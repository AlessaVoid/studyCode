<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<script type="text/javascript">
		/* function initComplete(){
		onChangeType();
		} */
		function getPrjInfo(){
			return $("#searchForm").serialize();
		}
		<%-- function onChangeType(){
			var type = $("#assetsClass")[0].value;
			document.getElementById("assetsType").value="";
			if(type==""||type==null){
				document.getElementById("assetsType").value="";
			}else{
				$.post("<%=path%>/webResearchAppInfo/findGfPrjInfo.htm?dictNo='ASSETS_TYPE'&requestType=choosePrjInfo&assetsClass="+type,function(result) {
					if (result.success == "true" || result.success == true) {
						document.getElementById("assetsType").value=result.msg;
					}else{
						document.getElementById("assetsType").value="";
					}
				},"json");
			}
		} --%>
		<%-- function onChangeType(){
			var dictKeyOut = $("#assetsClassTop")[0].value;
			document.getElementById("assetsType").value="";
			if(dictKeyOut==""||dictKeyOut==null){
				document.getElementById("assetsType").value="";
			}else{
				$.post("<%=path%>/webResearchAppInfo/dictLink.htm?dictNo='ASSETS_TYPE'&dictKeyOut="+dictKeyOut,function(result) {
					if (result.success == "true" || result.success == true) {
						document.getElementById("assetsType").value=result.msg;
					}else{
						document.getElementById("assetsType").value="";
					}
				},"json");
			}
		} --%>
/* 	$("#assetsType").bind("change",function(){
	    if(!$(this).attr("relValue")){
	        top.Dialog.alert("没有选择节点");
	    }else{
	        var node=$(this).data("selectedNode");
	        top.Dialog.alert(node.value);
	    }
	}) */
	function getMsg(){
		top.Dialog.alert($("#assetTypeDictVal option:checked").text(),350,400);
	}
</script>
</head>
	<body>
		<form id="searchForm">
		<table class="tableStyle tab-hei-30" width="80%" mode="list" formMode="line">
			<tr>
				<td align="right" width="50%">资产分类：</td>
				<td width="50%">
					<select id="assetDictVal" name="assetDictVal" url="<%=path%>/webResearchAppInfo/dictLink.htm?dictNo=ASSETS" prompt="请选择资产分类" 
         				childId="assetClassDictVal" childDataPath="<%=path%>/webResearchAppInfo/dictLink.htm?dictNo=ASSETS_ClASS&dictKeyOut=" ></select>
				</td>
			</tr>
			<tr>
				<td align="right" width="50%">资产分类投资比例(%)：</td>
				<td width="20%">
				  <input type="text" id="assetMinInvestRatio" name="assetMinInvestRatio" value="0" class="money validate[length[0,16]]" maxlength="16"/>
				  <span>-</span>
				  <input type="text" id="assetMaxInvestRatio" name="assetMaxInvestRatio" value="0" class="money validate[length[0,16]]" maxlength="16"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="50%">资产类别：</td>
				<td width="50%">
					<select id="assetClassDictVal" name="assetClassDictVal" prompt="请选择资产类别" childDataPath="<%=path%>/webResearchAppInfo/dictLink.htm?dictNo=ASSETS_TYPE&dictKeyOut=" childId="assetTypeDictVal" data='{"list":[]}'></select>
				</td>
			</tr>
			<tr>
				<td align="right" width="50%">资产种类：</td>
				<td width="50%" height="70%">
			    <select id="assetTypeDictVal" name="assetTypeDictVal" prompt="请选择资产种类"  data='{"list":[]}'></select>
				   <input id="details" type="button" value="详情" onclick="getMsg();"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="50%">资产种类投资比例(%)：</td>
				<td width="20%">
				  <input type="text" id="assetTypeMinInvestRatio" name="assetTypeMinInvestRatio" value="0" class="money validate[length[0,16]]" maxlength="16"/>
				  <span>-</span>
				  <input type="text" id="assetTypeMaxInvestRatio" name="assetTypeMaxInvestRatio" value="0" class="money validate[length[0,16]]" maxlength="16"/>
				</td>
			</tr>
			<!-- <tr>
				<td align="right" width="50%">债券类资产估值方法：</td>
				<td width="20%">
					<input type="checkbox" id="checkbox1" name="bondAssetValuatMethod" value="1"/>摊余成本法
					<input type="checkbox" id="checkbox2" name="bondAssetValuatMethod" value="2"/>公允价值法
				</td>
			</tr> -->
		</table>
		</form>
	</body>
</html>