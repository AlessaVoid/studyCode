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
	           width:'100%'
	           /* toolbar : {
			   items : [ {text : '增加行',click : appendPrjInfoDialog,iconClass : 'icon_add'}, {line : true},
			   	{text : '删除行',click : deletePrjInfo,iconClass : 'icon_remove'}, {line : true}
			   ]
			   } */
	   		});
		} else { 
			top.Dialog.alert(result.msg);
		}
	}, "json");
}
		/* function initComplete(){
		var gridData=${gridData};
		//灵活期限收益组合参数
		prjGrid = $("#prjInfoParam").quiGrid({
	           columns: [
	           { display: '资产类别', name: 'assetsClass', align: 'center', width: "15%",type:"text",
	        	   render : function(rowdata) {
	        		   if (rowdata.assetsClass == "1") {
	        				return "债券及货币市场工具类资产";
	        			}else if (rowdata.assetsClass == "2"){
	        				return "非标准化债权类资产";
	        			}else if (rowdata.assetsClass == "3"){
	        				return "权益类资产";
	        			}else if (rowdata.assetsClass == "4"){
	        				return "流动性资产";
	        			}
	        		   
	      			}
	           },
	           { display: '资产种类', name: 'assetsType', align: 'center', width: "75%",type:"textarea"},
	           { display: '投资比例', name: 'investType', align: 'center', width: "10%",type:"text"},
	           ], 
	           data:gridData, 
	           rownumbers : true,
	           usePager: false, 
	           percentWidthMode:true,
	           width:'100%'
	   	});
	} */
	
</script>
	</head>
	<body>
		<form id="form1" action="">
			<div align="center">
		   <%-- <table class="tableStyle"  mode="list" formMode="line" style="width: 100%;">
		      <tr width="50%">
				<td>杠杆率：</td>
				<td>
				  <input type="text" id="leverRatio" name="leverRatio" value="${leverRatio }" class="money validate[length[0,16]]" maxlength="16" disabled="disabled"/>
				</td>
				<td>债券类资产估值方法：</td>
				<td width="20%">
					<c:if test="${bondAssetValuatMethod == '1'}">
						摊余成本法
					</c:if>
					<c:if test="${bondAssetValuatMethod == '2'}">
						公允价值法
					</c:if>
					<c:if test="${bondAssetValuatMethod == '1.2'}">
						摊余成本法,公允价值法
					</c:if>
				</td>
		      </tr>
		   </table> --%>
		   <table class="tableStyle" mode="list" useMultColor="true">
			<tr>
				<th width="10%">参数名称</th>
				<th width="40%">变更原参数值</th>
				<th width="40%">变更新参数值</th>
			</tr>
			<c:forEach items="${dataList}" var="data">
				<tr>
					<td >
						${data.zhParamName}
					</td>
					<td>
						${data.oldValue}
					</td>
					<td>
						${data.newValue}
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
   			<div id="prjInfoParam" class="padding_right5"></div>
	   	</form>
	</body>
</html>