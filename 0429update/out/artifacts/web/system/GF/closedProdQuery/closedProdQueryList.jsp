<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<!-- 查询位置 -->
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="<%=path%>/webResearchAudit/findPage.htm" id="queryForm" method="post">
				<input type="hidden" id="sys" value="${sys }"/> 
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td align="right">查询类型：</td>
						<td>
							<dic:select dicType="QUERY_TYPE" name="queryType" id="queryType" selWidth="127" tgClass="validate[required]"></dic:select>
							<span class="star">*</span>
						</td>
						<td>
							起始日期：
						</td>
						<td>
							<input type="text" id="beginDate" name="beginDate" class="date validate[required]" dateFmt="yyyyMMdd"/>
							<input type="text" id="beginMonth" name="beginDate" class="date validate[required]" dateFmt="yyyyMM"/>
							<span class="star">*</span>
						</td>
						<td>
							终止日期：
						</td>
						<td>
							<input type="text" id="endDate" name="endDate" class="date validate[required]" dateFmt="yyyyMM"/>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>
							收益类别：
						</td>
						<td>
							<dic:select dicType="PROD_OPER_MODEL" name="prodOperModel" selWidth="127"></dic:select>
						</td>
						<td align="right">
							收益类型：
						</td>
						<td>
							<dic:select dicType="PROFIT_TYPE" name="profitType" selWidth="127"></dic:select>
						</td>
						<td>
							结构类型：
						</td>
						<td>
							<dic:select dicType="IS_QUANTO" name="isQuanto" selWidth="127"></dic:select>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div align="center">
								<button type="button" onclick="checkQuery()"><span class="icon_find">查询</span></button>
								<button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- Grid位置 -->
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
<script type="text/javascript">
var grid = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	top.Dialog.close();
	grid = $("#dataBasic")
			.quiGrid(
					{
						columns : [{
									display : '期限(天)',
									name : 'period',
									width : '20%',
									align : 'center'
								},{
									display : '产品数量(只)',
									name : 'amount',
									width : '20%',
									align : 'center'
								},{
									display : '数量占比(%)',
									name : 'amountPercent',
									width : '20%',
									align : 'center',
									type : 'amountPercent'
								},{
									display : '产品规模',
									name : 'scale',
									width : '20%',
									align : 'center'
								},{
									display : '规模占比(%)',
									name : 'scalePercent',
									width : '20%',
									align : 'center',
									type : 'scalePercent'
								}],
						url : '<%=path%>/closedProdQuery/findPage.htm',
						sortName : '',
						rownumbers : true,
						checkbox : true,
						height : '100%',
						width : "100%",
						pageSize : 10,
						toolbar : {
							items : [
								${btnList}
							]
						}
					});
	$.quiDefaults.Grid.formatters['PERIOD'] = function(value, column) {
		if (value == "1") {
			return "7天（含）以内";
		} else if (value == "2") {
			return "7天-1个月（含）";
		} else if(value == "3"){
			return "1-3个月（含）";
		} else if(value == "4"){
			return "3-6个月（含）";
		} else if(value == "5"){
			return "6-12个月（含）";
		} else if(value == "6"){
			return "1年以上";
		}
	};
	
	$.quiDefaults.Grid.formatters['amountPercent'] = function (value, column) {
   		return value*100;
	};
	$.quiDefaults.Grid.formatters['scalePercent'] = function (value, column) {
   		return value*100;
	};
	
	$("#beginMonth").hide();
	$("#queryType").change(function(){
		var type = $(this).val();
		if(type == "1"){
			$("#endDate").attr("disabled",true);
			$("#endDate").removeClass("validate[required]");
			$("#beginDate").show();
			$("#beginMonth").hide();
			$("#beginMonth").attr("disabled",true);
			$("#beginMonth").removeClass("validate[required]");
			$("#beginDate").attr("disabled",false);
			$("#beginDate").addClass("validate[required]");
		}else {
			$("#endDate").attr("disabled",false);
			$("#endDate").addClass("validate[required]");
			$("#beginDate").hide();
			$("#beginDate").attr("disabled",true);
			$("#beginDate").removeClass("validate[required]");
			$("#beginMonth").attr("disabled",false);
			$("#beginMonth").addClass("validate[required]");
			$("#beginMonth").show();
		}
	});
}

//存续结构图
function onDurationImg() {
	var valid = $("#queryForm").validationEngine( {
		returnIsValid : true
	});
	if(valid){
		var queryType = $("#queryType").val();
		if(queryType == "2"){
			top.Dialog.alert("请选择存续结构查询! | 操作提示",function(){
				return false;
			});
		}else{
			showDialog("<%=path%>/closedProdQuery/durationImg.htm?" + $("#queryForm").serialize(),"存续结构图",1000,580);
		}
	}
}

//到期结构图
function onDueImg() {
	var valid = $("#queryForm").validationEngine( {
		returnIsValid : true
	});
	if(valid){
		var queryType = $("#queryType").val();
		if(queryType == "1"){
			top.Dialog.alert("请选择到期结构查询! | 操作提示",function(){
				return false;
			});
		}else if(queryType == "2"){
			var d1 = $("#beginMonth").val().replace(/\-/g, "\/");
			var d2 = $("#endDate").val().replace(/\-/g, "\/");
			if(parseFloat(d2)<parseFloat(d1)){
				top.Dialog.alert("终止日期必须大于起始日期! | 操作提示",function(){
					return false;
				});
			}
			var beginYear = d1.substring(0,4);
			var endYear = d2.substring(0,4);
			//年份不一样
			if(beginYear != endYear){
				if(parseFloat(endYear) - parseFloat(beginYear) > 1){
					top.Dialog.alert("时间跨度不能超过1年! | 操作提示",function(){
						return false;
					});
				}else{
					var beginMonth = d1.substring(4,6);
					var endMonth = d2.substring(4,6);
					if(parseFloat(endMonth) >= parseFloat(beginMonth)){
						top.Dialog.alert("时间跨度不能超过1年! | 操作提示",function(){
							return false;
						});
					}else{
						showDialog("<%=path%>/closedProdQuery/dueImg.htm?" + $("#queryForm").serialize(),"到期结构图",1200,580);
					}
				}
			}else{
				showDialog("<%=path%>/closedProdQuery/dueImg.htm?" + $("#queryForm").serialize(),"到期结构图",1200,580);
			}
		}
	}
}

//导出
function onExportExcel(){
	var valid = $("#queryForm").validationEngine( {
		returnIsValid : true
	});
	if(valid){
		top.Dialog.confirm('是否导出全部数据?',function() {
			$("#queryForm").attr("action","<%=path%>/closedProdQuery/exportExcel.htm");
			$("#queryForm").submit();
		});	
	}
}

//验证时间
function checkQuery(){
	var valid = $("#queryForm").validationEngine( {
		returnIsValid : true
	});
	if(valid){
		var queryType = $("#queryType").val();
		if(queryType == "1"){
			var sys= $ ("#sys").val();
			var beginDate = $("#beginDate").val().replace(/\-/g, "\/");
			if(parseFloat(sys)<=parseFloat(beginDate)){
				top.Dialog.alert("起始日期必须小于当前日期("+sys +")!| 操作提示：",function(){
				});
				return false;
			}
		}
		if(queryType == "2"){
			var d1 = $("#beginMonth").val().replace(/\-/g, "\/");
			var d2 = $("#endDate").val().replace(/\-/g, "\/");
			if(parseFloat(d2)<parseFloat(d1)){
				top.Dialog.alert("终止日期必须大于起始日期! | 操作提示",function(){
					return false;
				});
			}
			var beginYear = d1.substring(0,4);
			var endYear = d2.substring(0,4);
			//年份不一样
			if(beginYear != endYear){
				if(parseFloat(endYear) - parseFloat(beginYear) > 1){
					top.Dialog.alert("时间跨度不能超过1年! | 操作提示",function(){
						return false;
					});
				}else{
					var beginMonth = d1.substring(4,6);
					var endMonth = d2.substring(4,6);
					if(parseFloat(endMonth) >= parseFloat(beginMonth)){
						top.Dialog.alert("时间跨度不能超过1年! | 操作提示",function(){
							return false;
						});
					}else{
						searchHandler();
					}
				}
			}else{
				searchHandler();
			}
		}else{
			searchHandler();
		}
	}
}
</script>
</body>
</html>