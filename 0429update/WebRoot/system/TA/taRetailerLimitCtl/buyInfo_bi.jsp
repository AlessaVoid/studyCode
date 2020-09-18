<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body onclick="fmtText()">
	<div class="box2_custom" boxType="box2" panelTitle="产品信息 (单位:万)" id="searchPanel">
		<table class="tableStyle" mode="list" formMode="line" style="width: 97%;">
			<tr>
				<td style="width: 20%;">理财产品代码：</td>
				<td style="width: 20%;">
					${requestScope.entity.prodCode}
					<input type="hidden" id="prodCode" value="${requestScope.entity.prodCode}" />
				</td>
				<td style="width: 20%;">产品名称：</td>
				<td>
					${requestScope.prodName}
					<input type="hidden" id="prodName" value="${requestScope.prodName}" />
				</td>
			</tr>
			<tr>
			<td>币种：</td>			
				<td>
					<c:choose>
							<c:when test="${requestScope.entity.currency==826}">
								英镑
								<input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==156}">
							         人民币
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==978}">
							         欧元
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==840}">
							         美元
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==392}">
							         日元
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==036}">
							        澳大利亚元
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==702}">
							         新元
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==643}">
							         卢布
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==756}">
							         瑞士法郎
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==124}">
							         加元
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
							<c:when test="${requestScope.entity.currency==344}">
							        港币
							   <input type="hidden" name="currency"  value="${requestScope.entity.currency}"/>
							</c:when>
						</c:choose>
				</td>
				<td>计划发售金额：</td>
				<td>
					${requestScope.entity.limit}
					<input type="hidden" id="limit"  value="${requestScope.entity.limit}" />
				</td>
			</tr>
			<tr>
			   <td>已初始化金额：</td>
				<td>
					${requestScope.entity.alreadyPurchaseAmt}
					<input type="hidden" id="alreadyPurchaseAmt"  value="${requestScope.entity.alreadyPurchaseAmt}" />
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div class="box2_custom" boxType="box2" panelTitle="销售商额度列表   (单位:万)">
		<div id="mainBasic"></div>
	</div>
	<script type="text/javascript">
var mainData={"form.paginate.pageNo":1,
			"rows":${requestScope.mainData}};

var bit = 4;

function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	//top.Dialog.close();
	mainGrid = $("#mainBasic").quiGrid(
			{
				columns : [
				          { display: '销售商代码',
				        	  name: 'retailerCode', 
				        	  align: 'center', 
				        	  width: "16%"
				          }, 
				          { display: '销售商名称',
				        	  name: 'retailerName', 
				        	  align: 'center', 
				        	  width: "17%"
				          }, 
				          { display: '额度类型',
				        	  name: 'limitType', 
				        	  align: 'center', 
				        	  width: "14%",
				        	  type: 'TA_LIMIT_TYPE'
				          }, {
									display : '币种',
									name : 'currency',
									width : '12%',
									align : 'center',
									render : function(rowdata, rowindex, value, column) {
									if(value=="156"){
										return "人民币";
										}
									if(value=="826"){
										return "英镑";
										}
									if(value=="978"){
										return "欧元";
										}
									if(value=="840"){
										return "美元";
									}
									if(value=="392"){
										return "日元";
									}
									if(value=="036"){
										return "澳大利亚元";
									}
									if(value=="702"){
										return "新元";
									}
									if(value=="643"){
										return "卢布";
									}
									if(value=="756"){
										return "瑞士法郎";
									}
									if(value=="124"){
										return "加元";
									}
									if(value=="344"){
										return "港币";
									}
									}
						  }, 
				          { display: '是否管理',
				        	  name: 'isManage', 
				        	  align: 'center', 
				        	  width: "14%",
				        	  type: 'IS_YES'
				          }, 
				          { display: '总额度',
				        	  name: 'retailerSumLimit', 
				        	  align: 'center', 
				        	  width: "14%",
				        	  render : function(rowdata) {
									return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.retailerSumLimit,4) + '</div>';
							  }
				          }, 
				          { display: '可销售额度',
				        	  name: 'retailerSellingLimit', 
				        	  align: 'center', 
				        	  width: "14%",
				        	  render : function(rowdata) {
									return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.retailerSellingLimit,4) + '</div>';
							  }
				          }
			              ],
			    enabledEdit: true,
			    data:mainData, 
			    rownumbers: true, 
			    enabledSort: false,
			    checkbox: false,
			    isScroll: false,
			    percentWidthMode:true,
			    height: '100%', 
			    width:"100%",
			    usePager: false
			});
	
	$.quiDefaults.Grid.formatters['IS_YES'] = function(value, column) {
		if (value == "0") {
			return "否";
		}else if (value == "1"){
			return "是";
		}
	};
	
	$.quiDefaults.Grid.formatters['TA_LIMIT_TYPE'] = function(value, column) {
		if (value == "1"){
			return "认购";
		}else if(value == "2"){
			return "申购";
		}
	};
	
	mainGrid.loadData();
}


</script>
</body>
</html>
