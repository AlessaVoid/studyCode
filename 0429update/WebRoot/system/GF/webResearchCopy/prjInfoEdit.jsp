<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<title>项目信息</title> 
		<!-- 树形下拉框end -->
		<script type="text/javascript">
		var prjGrid;
		function initComplete(){
		var dictNos = ["ASSETS","ASSETS_ClASS","ASSETS_TYPE"];
		var asset = [];
		var assetClass = [];
		$.post("<%=path%>/webResearchAppInfo/initPageDictValue.htm?dictNos="+dictNos,function(result) {
			if (result.success == "true" || result.success == true) {
				asset = result.ASSETS;
				assetClass = result.ASSETS_ClASS;
				assetType = result.ASSETS_TYPE;
				var gridData=${gridData};
				var bondAsset1 = ${bondAsset1};
				var bondAsset2 = ${bondAsset2};
				//alert(bondAsset1+"||"+bondAsset2);
				if(bondAsset1 == '1' && bondAsset2 == '1'){
					$("#checkbox1").attr("checked",true);
					$("#checkbox2").attr("checked",true);
				}else if(bondAsset2 == '1'){
					$("#checkbox2").attr("checked",true);
				}else if(bondAsset1 == '1'){
					$("#checkbox1").attr("checked",true);
				}
				/* aa = ${bondAssetValuatMethod}.toString();
				
				var arr = new Array();
				alert("1o");
				arr = aa.split(".");
				alert("2o");
				for(var a=0;a<arr.length;a++){
					alert(arr[a]);
				} */
				/* var bond = aa.split(".");
				alert(bond[0]);
				for(var a=0;a<bond.length;a++){
					alert(bond[a]);
				} */
				prjGrid = $("#prjInfoParam").quiGrid({
	           columns: [
	           { display: '资产分类', name: 'assetDictVal', align: 'center', width: "15%",type:"text",
	        	   render : function(rowdata) {
		        	   for(var i=0;i<asset.length;i++){
		        	   //alert(rowdata.assetDictVal+"||"+asset[i].value);
							if (rowdata.assetDictVal == asset[i].value) {
								return asset[i].key;
							}
		        	   }
	      			}
	           },
	           { display: '资产分类投资比例', name: 'assetInvestRatio', align: 'center', width: "10%",type:"text"},
	           { display: '资产类别', name: 'assetClassDictVal', align: 'center', width: "15%",type:"text",
	        	   render : function(rowdata) {
		        	   for(var i=0;i<assetClass.length;i++){
							if (rowdata.assetClassDictVal == assetClass[i].value) {
								return assetClass[i].key;
							}
		        	   }
	      			}
	           },
	           { display: '资产种类', name: 'assetTypeDictVal', align: 'center', width: "50%",type:"text",
	           		render : function(rowdata) {
		        	   for(var i=0;i<assetType.length;i++){
							if (rowdata.assetTypeDictVal == assetType[i].value) {
								return assetType[i].key;
							}
		        	   }
	      			}
	           },
	           { display: '资产种类投资比例', name: 'assetTypeInvestRatio', align: 'center', width: "10%",type:"text"}
	           ],
	           data:gridData, 
	           rownumbers : true,
	           usePager: false, 
	           checkbox:true,
	           percentWidthMode:true,
	           width:'100%',
	           toolbar : {
			   items : [ {text : '增加行',click : appendPrjInfoDialog,iconClass : 'icon_add'}, {line : true},
			   	{text : '删除行',click : deletePrjInfo,iconClass : 'icon_remove'}, {line : true}
			   ]
			   }
	   		});
		} else { 
			top.Dialog.alert(result.msg);
		}
	}, "json");
}
function appendPrjInfoDialog(){
	var diag = new top.Dialog();
    diag.Title = "选择投资项目及比例";
    diag.URL = "<%=path%>/system/GF/webResearch/choosePrjInfo.jsp";
    diag.OKEvent = function(){
        var param = diag.innerFrame.contentWindow.getPrjInfo();
        var ss = $("#assetsClass").val();
        var arr =param.split("&");
        for (var i = 0; i < arr.length; i++) {
			var tr = arr[i];
			//alert("tr"+tr);
		}
		var prodCode = $("#prodCode").val();
       	$.post("<%=path%>/webResearchAppInfo/findGfPrjInfo.htm?requestType=saveButton&prodCode="+prodCode, param, function(result) {
			if (result.success == "true" || result.success == true) {
				var list=result.bean;
				 for (var int = 0; int < list.length; int++) {
					var webPrjProDTO=list[int];
					appendPrjInfo(webPrjProDTO.assetDictVal,webPrjProDTO.assetInvestRatio,webPrjProDTO.assetClassDictVal,webPrjProDTO.assetTypeDictVal,webPrjProDTO.assetTypeInvestRatio);
			     }
				
			} else { 
				top.Dialog.alert(result.msg);
			}
		}, "json");
        	diag.close();
        };
    diag.show();
}
//添加
function appendPrjInfo(assetDictVal,assetInvestRatio,assetClassDictVal,assetTypeDictVal,assetTypeInvestRatio){
	var webPrjInfo = {
			assetDictVal: assetDictVal,
			assetInvestRatio: assetInvestRatio,
			assetClassDictVal: assetClassDictVal,
			assetTypeDictVal: assetTypeDictVal,
			assetTypeInvestRatio:assetTypeInvestRatio
          };
		prjGrid.add(webPrjInfo);
}
//删除选中行
function deletePrjInfo(){
    //选中一行或多行
    var rows = prjGrid.getSelectedRows();
    if (rows.length == 0) {
          top.Dialog.alert('请至少选择一行'); 
          return;
       }
    for(var index in rows){
    	prjGrid.deleteRow(rows[index]);
    }
}	
function sub(formId,url){
	var valid = $("#"+formId).validationEngine({
		returnIsValid : true
	});
	if (valid) {
		top.Dialog.confirm("是否保存信息?", function() {
			var param = $("#"+formId).formToArray();
			var map = {};
			map.name = "gridData";
			map.value= JSON.stringify(prjGrid.getData());
			param.push(map);
			$.post(url,param,function(result) {
				if (result.success == "true" || result.success == true) {
					top.Dialog.alert(result.msg, function() {
						parent.setBaseInfo(result.bean);
						parent.turnPage("panel2","panel3");
						if($("#index").val() != $("#oldIndex").val()){
							parent.changeDiv($("#index").val(),$("#oldIndex").val());
						}else{
							parent.changeDiv($("#index").val(),$("#index").val());
						}
					});
				} else {
					top.Dialog.alert(result.msg);
				}
			}, "json");
		});
	}else {
		top.Dialog.alert("验证未通过！");
	}
}
function getCheckValue(){
   var msg = "";
   $("input:checkbox[name=bondAssetValuatMethod]").each(function(){
       if($(this).attr("checked")){
           msg += "," + $(this).val();
       }
   });
   if(msg == ""){
       msg = "无";
   }else{
       msg = msg.substring(1);
   }
   return msg;
}
</script>
	</head>
	<body>
		<form id="form1" action="">
			<input type="hidden" name="prodCode" id="prodCode" value="${prodCode }"/>
			<input type="hidden" name="index" id="index" value="${index }"/>
			<input type="hidden" name="oldIndex" id="oldIndex" value="${oldIndex }"/>
			<div align="center">
		   <table class="tableStyle"  mode="list" formMode="line" style="width: 100%;">
		      <tr width="50%">
				<td>杠杆率：</td>
				<td>
				  <input type="text" id="leverRatio" name="leverRatio" value="${leverRatio }" class="money validate[length[0,16]]" maxlength="16" />
				</td>
				<td>债券类资产估值方法：</td>
				<td width="20%">
					<input type="checkbox" id="checkbox1" name="bondAssetValuatMethod" value="01"/>摊余成本法
					<input type="checkbox" id="checkbox2" name="bondAssetValuatMethod" value="02"/>公允价值法
				</td>
		      </tr>
		   </table>
		</div>
   			<div id="prjInfoParam" class="padding_right5"></div>
			<div align="center">
				<button type="button" onclick="return sub('form1','<%=path%>/webResearchAppInfo/insertWebPrjInfos.htm')" class="saveButton"/>
			</div>
	   	</form>
	</body>
</html>