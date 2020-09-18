<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/common_list.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<body onclick="fmtText()">
	<div class="box2_custom" boxType="box2" panelTitle="产品信息  单位（万）" id="searchPanel">
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
					<input type="hidden" id="beginDate" value="${requestScope.entity.beginDate}"/>
					<input type="hidden" id="endDate" value="${requestScope.entity.endDate}"/>
					<input type="hidden" id="limitType" value="${requestScope.limitType}"/>
				</td>
				<td>多条分配额度：</td>			
				<td>
					<input type="text" id="money" name="money"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="center">
						<button type="button" onclick="multiple()" /><span class="icon_cancel">多条分配</span></button>
						<button type="button" onclick="sub()"><span class="icon_save">保存</span></button>
						<button type="button" onclick="cancel()"><span class="icon_cancel">取消</span></button>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div class="box2_custom" boxType="box2" panelTitle="销售商额度列表   单位（万）">
		<div id="mainBasic"></div>
	</div>
	<script type="text/javascript">
var mainData={"form.paginate.pageNo":1,
			"rows":${requestScope.mainData}};

var mfsel={"list":[{"value":"0","key":"否"},{"value":"1","key":"是"}]};

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
					        	  width: "14%"
					          }, 
					          { display: '销售商名称',
					        	  name: 'retailerName', 
					        	  align: 'center', 
					        	  width: "16%"
					          }, 
					          { display: '额度类型',
					        	  name: 'limitType', 
					        	  align: 'center', 
					        	  width: "12%",
					        	  type: 'TA_LIMIT_TYPE'
					          }, {
									display : '币种',
									name : 'currency',
									width : '10%',
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
					        	  width: "12%",
					        	  type: 'IS_YES',
					        	  /* 下拉框 模式 */
					        	  editor:{
				        		  	 type:'select',
				        		  	 data:mfsel,
				        		  	 selWidth:100,
				        		  	 boxWidth:90
				        	  	  }
					          }, 
					          { display: '总额度',
					        	  name: 'retailerSumLimit', 
					        	  align: 'center', 
					        	  width: "12%",
					        	  render : function(rowdata) {
										return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.retailerSumLimit,4) + '</div>';
								  }
					          }, 
					          { display: '可销售额度',
					        	  name: 'retailerSellingLimit', 
					        	  align: 'center', 
					        	  width: "12%",
					        	  render : function(rowdata) {
										return '<div style="margin-right: 35%;" align="right">' + formatRound(rowdata.retailerSellingLimit,4) + '</div>';
								  }
					          }, 
					          { display: '分配额度',
					        	  name: 'varyamt', 
					        	  align: 'right', 
					        	  width: "12%",
					        	  editor:{
					        		  type:"text"
					        	  }	
					          }
			              ],

			    enabledEdit: true,
			    data:mainData, 
			    rownumbers: true, 
			    enabledSort: false,
			    checkbox: true,
			    isScroll: false,
			    percentWidthMode:true,
			    height: '100%', 
			    width:"100%",
			    pageSize: 100,
			    usePager: false,
			    pageSizeOptions: [100,50,25,10,5],
			    onBeforeEdit: f_onBeforeEdit,
			    onBeforeSubmitEdit: f_onBeforeSubmitEdit,
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
	
	$("#searchPanel").bind("stateChange", function(e, state) {
		mainGrid.resetHeight();
	});
	
	$("#mainPanel").bind("stateChange", function(e, state) {
		mainGrid.resetHeight();
	});
	
}

// 防多次弹框标志
var sumFlag = -1;
var mainFlag = -1;

//编辑前事件
function f_onBeforeEdit(e){ 
	// 可销售额度大于零的记录，不允许修改管理标志
	if(e.record.retailerSellingLimit>0 && e.column.name=='isManage'){
		top.Dialog.alert("请先把即将不管理的销售商的可销售额度调整为零");
		return false;
	}
	return true;
}

//提交编辑前事件
function f_onBeforeSubmitEdit(e){
	mainFlag = -mainFlag;
	//修改后 管理标志 如果为空值，则置 管理标志 为修改前状态
	if(e.column.name=='isManage' && (e.value==null || e.value=='')){
		e.value=e.record.isManage;
	}
	//动态调整管理额度、可销售额度及未分配额度，且调整后的未分配额度不能小于零
	if(e.column.name=='varyamt'){
		var varyamt_new;
		if(e.value==null||e.value==''){
			varyamt_new = 0;
		}else{
			varyamt_new = parseFloat(String(e.value).replace(/,/gi, ""));
		}
		var varyamt_old = parseFloat(String(e.record.varyamt).replace(/,/gi, ""));
		var varyamt_temp = varyamt_new - varyamt_old;
		if(varyamt_temp != 0){
                    		
			var retailerSumLimit_new = parseFloat(String(e.record.retailerSumLimit).replace(/,/gi, "")) + varyamt_temp;
			var retailerSellingLimit_new = parseFloat(String(e.record.retailerSellingLimit).replace(/,/gi, "")) + varyamt_temp;
			if(retailerSellingLimit_new<0){
				if(mainFlag==1){
					top.Dialog.alert("分配后的可销售额度不能小于零");
				}
				e.value = e.record.varyamt;
			}else{
				if(retailerSellingLimit_new>0){
					e.record.isManage = '1';
				}
				e.record.retailerSumLimit = retailerSumLimit_new;
				e.record.retailerSellingLimit = retailerSellingLimit_new;
			}
		}else{
			e.value = zh(varyamt_new,bit);
		}
	}
	
	return true;
}



//即时编辑输入格式化	
function fmtText(){
	if($(".l-grid-editor .textinput_click").size()>0){
		// 输入文本在右边界对齐
		$(".l-grid-editor .textinput_click").css("text-align", "right");
		// 清除文本中的','分隔符
		rmoney1($(".l-grid-editor .textinput_click")[0]);
		// 为输入框绑定失焦时间
		$(".l-grid-editor .textinput_click").bind("blur", function() {
			// 清除文本中的'-'号
			var value = $(this).val().replace(/-/gi, "");
			if(value == ""){
				$(this).val(0);
			}else{
				$(this).val(value);
			}
			rmoney(this,bit);
			fmoney(this,bit);
		});
	}
	$(".l-grid-editor .textinput_click").blur(function(){
		mainGrid.endEdit();
	});
}


function sub() {
	if($(".l-grid-editor").size()>0){
		top.Dialog.alert("页面尚有未确定的修改操作");
		return;
	}
	top.Dialog.confirm("是否保存该条信息?", function() {
		mainGrid.options.pageSize = mainGrid.options.total;
		mainGrid.changePage("first");
		mainGrid.loadData();
		
		$.post("<%=path%>/taRetailerLimitCtl/assignBuyEductl.htm", 
			{
				prodCode : $("#prodCode").val(),
				alreadyPurchaseAmt : $("#alreadyPurchaseAmt").val(),
				endDate : $("#endDate").val(),
				beginDate : $("#beginDate").val(),
				limitType : $("#limitType").val(),
				mainData: JSON.stringify(mainGrid.getData())
			}, function(result) {
			if (result.success == "true" || result.success == true) {
				top.Dialog.alert(result.msg, function() {
					top.frmright.refresh(true);
					top.Dialog.close();
				});
			} else {
				mainGrid.options.pageSize = 5;
				mainGrid.options.page = 1;
				mainGrid.loadData();
				top.Dialog.alert(result.msg);
			}
		}, "json");
	});

}

function cancel() {
	top.Dialog.confirm("数据尚未保存，是否退出?|取消确认",function(){
		top.Dialog.close();
		}
	);
}

//多条分配
function multiple() {
    mainFlag = -mainFlag;
	var rows = mainGrid.getSelectedRows();
	var money = $("#money").val();
	var alreadyPurchaseAmt = $("#alreadyPurchaseAmt").val();
	if(rows.length == 0){
		top.Dialog.alert("请勾选要分配的销售商！");
	}else if(rows.length == 1){
		top.Dialog.alert("请勾选多条记录！");
	}else if(money.length==0){
		top.Dialog.alert("请输入需要分配的额度！");
	}else{
		 for(var index in rows){
		   	var varyamt_new;
			if(rows[index].varyamt==null||rows[index].varyamt==''){
					varyamt_new = 0;
			}else{ 
					varyamt_new = parseFloat(String(money).replace(/,/gi, ""));
			}
			//判断
			var varyamt_old = parseFloat(String(rows[index].varyamt).replace(/,/gi, ""));
			var varyamt_temp = varyamt_new-varyamt_old;
			if(varyamt_temp != 0 && varyamt_temp<alreadyPurchaseAmt){
				var retailerSumLimit_new = parseFloat(String(rows[index].retailerSumLimit).replace(/,/gi, "")) + varyamt_temp;
				var retailerSellingLimit_new = parseFloat(String(rows[index].retailerSellingLimit).replace(/,/gi, "")) + varyamt_temp ;
				    if(retailerSellingLimit_new<0){
						if(mainFlag==1){
							top.Dialog.alert("分配后的可销售额度不能小于零");
						}
				   }else{
						if(retailerSellingLimit_new>0){
							rows[index].isManage = '1';
						}
						rows[index].retailerSumLimit = retailerSumLimit_new;
						rows[index].retailerSellingLimit = retailerSellingLimit_new;
						rows[index].varyamt=money;
				   }
			}else if(varyamt_temp>alreadyPurchaseAmt){
			    top.Dialog.alert("分配的可销售额度不能大于初始化额度");
			    rows[index].varyamt = 0;
			} 
			mainGrid.updateRow(rows[index]);
		 }
	    
	}
}
</script>
</body>
</html>
