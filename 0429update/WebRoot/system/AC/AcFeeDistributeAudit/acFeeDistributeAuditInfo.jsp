<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head >
		<title></title>
		<%@include file="/common/common_edit.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="<%=path%>/libs/js/nav/basicTabModern.js"></script>
	</head>
	<body>
		<!-- 查询位置 -->
		<div class="basicTabModern">
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5" name="待分配手续费列表">
			<div id="dataBasic"></div>
		</div>
		<div name="审批流程记录">
		<table class="tableStyle tab-hei-30" width="80%" mode="list">
				<tr>
					<td width="30%" align="left">
						审批用户
					</td>
					<td width="30%" align="left">
						审批时间
					</td>
					<td width="40%" align="left">
						批注
					</td>
				</tr>
				<c:forEach items="${comments }" var="comment">
				<tr>
					<td>
						${comment.userId }
					</td>
					<td>
					<c:choose>
						<c:when test="${empty comment.time}">
						----
						</c:when>
						<c:otherwise>
							<fm:formatDate value="${comment.time }" pattern="yyyyMMdd HH:mm:ss"/>
						</c:otherwise>
					</c:choose>
					</td>
					<td>
						${comment.fullMessage }
					</td>
				</tr>
				</c:forEach>
				</table>
	   	</div>
	 </div>
<script type="text/javascript">
var grid = null;
function initComplete() {
	var gridData=${gridData};
	grid = $("#dataBasic")
	.quiGrid(
			{
				columns : [{
							display : '生成日期',
							name : 'tradedate',
							align : 'cennter',
							width : '10%'

						},{
							display : '产品代码',
							name : 'businesskindcode',
							align : 'cennter',
							width : '10%'
						},
						{
							display : '产品名称',
							name : 'businesskindname',
							align : 'cennter',
							width : '20%'
						},
						{
							display : '计费起始日',
							name : 'begindate',
							align : 'cennter',
							width : '10%'
						},
						{
							display : '计费终止日',
							name : 'enddate',
							align : 'cennter',
							width : '10%'
						},

						{
							display : '处理标记',
							name : 'fpflag',
							align : 'cennter',
							width : '10%',
							render : function(rowdata, rowindex, value,
									column) {
								if (value == 0 || value == "0") {
									value="未处理";
								} else if (value == 1 || value == "1") {
									value="待复核";
								} else if (value == 2 || value == "2") {
									value="等待后台处理";
								} else if (value == 3 || value == "3") {
									value="已处理";
								} else if (value == 4 || value == "4") {
									value="处理异常";
								}
								var title=rowdata.text;
								if(title=="undefined"||title==undefined){
									title="";
								}
								
								return '<div class="padding_top4 padding_left5">' 
								+ '<span  title="'+title+'">'+value+'</span>'
								+ '</div>';
							}
						},
						{
							display : '计费金额',
							name : 'amt',
							align : 'cennter',
							width : '15%',
							render : function(rowdata, rowindex, value,
									column) {
								return "<div class='money'>"
										+ zh(value, 2) + "</div>"
							}
						},
						{
							display : '实际分配金额',
							name : 'fpamt',
							align : 'cennter',
							width : '15%',
							totalSummary:{
								render:function(o){
									return "总分配金额："+zh(o.sum, 2)+ "元";
										},
								align : 'left'},
							render : function(rowdata, rowindex, value,
									column) {
								if(value==""||value==''||value==null){value="0";}
								return "<div class='money'>"+ zh(value, 2) + "</div>"
							}
						}],
						data:gridData, 
						rownumbers : true,
						height : '100%',
						usePager: false
					});
}

</script>
</body>
</html>
