<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title></title>
		<%@include file="/common/common_list.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--数据表格start-->
		<script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
		<!--数据表格end-->
	</head>
	<body>
		<!-- Grid位置 -->
   		<div class="box2_custom" boxType="box2" panelTitle="到期参考表">	
    		<div id="dataBasic" name="到期参考表" style="width:90%;">
    		</div>
    	</div>	
    	<div class="box2_custom" boxType="box2" panelTitle="期限结构对比表">
			<div id="dataBasicList" name="期限结构对比表" style="width:90%;">
   			</div>
   		</div>
<script type="text/javascript">
var grid = null;
var g = null;
function initComplete() {
	//当提交表单刷新本页面时关闭弹窗
	g = $("#dataBasicList")
			.quiGrid(
					{
						columns : [{
									display : '对比项',
									name : 'conItem',
									width : '20%',
									align : 'center'
									
								},{
									display : '产品数量',
									name : 'numbers',
									width : '20%',
									align : 'center'
								},{
									display : '规模',
									columns : [
										{
											display : '90日以内',
											name : 'fQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '91-180日',
											name : 'sQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '181-270日',
											name : 'tQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '271-365日',
											name : 'foQuarter',
											width : '12%',
											align : 'center'
											
										},{
											display : '366日及以上',
											name : 'years',
											width : '12%',
											align : 'center'
											
										}]           
								}],
						url : '<%=path%>/webResearchAudit/selectComparisonChart.htm',
						sortName : '',
						rownumbers : true,
						checkbox : false,
						params:[{name:"prodCodes",value:"${prodCodes}"},{name:"currency",value:"${currency}"}],
						usePager : false
					});
	
	
		grid = $("#dataBasic").quiGrid({
					columns : [{
								display : '产品代码',
								name : 'prodCode',
								width : '6%',
								align : 'center',
								frozen:true
							},{
								display : '产品名称',
								name : 'prodName',
								width : '18%',
								align : 'center'
								
							},{
								display : '产品代码',
								name : 'prodCode',
								width : '13%',
								align : 'center'
							},{
								display : '产品成立日期',
								name : 'prodBeginDate',
								width : '13%',
								align : 'center'
							},{
								display : '产品终止日期',
								name : 'prodEndDate',
								width : '13%',
								align : 'center'
							},{
								display : '币种',
								name : 'currency',
								width : '12%',
								align : 'center',
								render : function(rowdata, rowindex, value, column) {
									if("156"==value){
										return "人民币";
									}else if("036"==value) {
										return "澳大利亚元";
									}else if("124"==value) {
										return "加元";
									}else if("344"==value) {
										return "港币";
									}else if("392"==value) {
										return "日元";
									}else if("643"==value) {
										return "卢布";
									}else if("702"==value) {
										return "新元";
									}else if("756"==value) {
										return "瑞士法郎";
									}else if("826"==value) {
										return "英镑";
									}else if("840"==value) {
										return "美元";
									}else if("978"==value) {
										return "欧元";
									}
								}
							},{
								display : '实际募集金额（万）',
								name : 'planIssueAmt',
								width : '12%',
								align : 'center'
							},{
								display : '期限(天)',
								name : 'prodDurationDays',
								width : '12%',
								align : 'center'
							}],
					url : '<%=path%>/webResearchAudit/selectMaReferenceList.htm',
					sortName : '',
					rownumbers : true,
					checkbox : false,
					params:[{name:"prodCodes",value:"${prodCodes}"},{name:"currency",value:"${currency}"}],
					usePager : false
				});
}


</script>
</body>
</html>