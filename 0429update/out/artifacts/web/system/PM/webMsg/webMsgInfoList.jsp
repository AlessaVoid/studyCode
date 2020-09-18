<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title>代办历史任务</title>
		<%@include file="/common/common_list.jsp" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript">
var grid = null;
function initComplete() {
	grid = $("#dataBasic").quiGrid({
				columns : [
						{display : '消息代码',name : 'msgNo',width : '10%',align : 'center'}
						,{display : '交易描述',name : 'operDescribe',width : '15%',align : 'center'}
						,{display : '交易代码',name : 'operNo',width : '15%',align : 'center'}
						,{display : '交易名称',name : 'operName',width : '15%',align : 'center'}
						,{display : '申请人姓名',name : 'appOperName',width : '15%',align : 'center'}
						,{display : '申请日期',name : 'appDate',width : '10%',align : 'center'}
						,{display : '申请时间',name : 'appTime',width : '10%',align : 'center'}
						,{display : '审批状态',name : 'webMsgStatus',width : '10%',align : 'center',type : 'WSG_STATUS'}
						,{display : '操作',width : '10%',align : 'center',frozen:true,
							render : function(rowdata, rowindex, value, column) {
								var webMsgStatus=rowdata.webMsgStatus;
								var div_head='<div class="padding_top4 padding_left5">' ;
								var div_boday='';
								if(webMsgStatus=='1'){
									div_boday='<span class="img_edit hand" title="办理" onclick="onView(' + "'" + rowdata.msgUrl + "'" +')"></span>';
								}
								div_boday=div_boday+'<span class="img_no hand" title="关闭" onclick="onClose(' + "'" + rowdata.msgNo + "'" +')"></span>';
								var div_end='</div>';
								return div_head+ div_boday+div_end;
							}
						}],
				url : '<%=path%>/webMsg/findPage.htm',
				sortName : '',
				rownumbers : true,
				checkbox : true,
				height : '100%',
				width : "100%",
				pageSize : 10
			});
	$.quiDefaults.Grid.formatters['WSG_STATUS'] = function (value, column) {
		if(value == '1'){
			return '待审批';
		}else if(value == '2'){
			return '撤销';
		}else if(value == '3'){
			return '审批通过';
		}else if(value == '4'){
			return '审批驳回';
		}else if(value == '5'){
			return '关闭申请';
		}
	};
};

var diag = new top.Dialog();
//操作
function onView(msgUrl){
	diag.URL = "<%=path%>/" + msgUrl,
	diag.Title = "任务办理";
	diag.Width = 1280;
	diag.Height = 680;
	diag.show();
};
function onClose(msgNo){
	$.post("<%=path%>/webMsg/onCloseMsg.htm", {
		msgNo : msgNo
	}, function(result) {
		top.Dialog.alert(result.msg);
		grid.loadData();
	},'json');
};
function setSize(Width,Height) {
	diag.setSize(Width,Height);
}
</script>
	</head>
	<body>
		<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
			<form action="" id="queryForm" method="post">
				<table class="tableStyle"  mode="list" formMode="line" style="width: 97%;">
					<tr>
						<td align="right">
							代办类型：
						</td>
						<td>
						<dic:select dicType="MSG_TYPE" id="msgType" name="msgType"></dic:select>
						</td>
						<td width="10%" align="right">
							交易代码：
						</td>
						<td width="10%">
							<input type="text" id="operNo" name="operNo" />
						</td>
						<td width="10%" align="right">
							申请开始时间：
						</td>
						<td width="10%">
							<input type="text" id="appDate" name="appDate" class="date" dateFmt="yyyyMMdd"/>
						</td>
						<td width="10%" align="right">
							复核状态：
						</td>
						<td width="10%">
							<select name="webMsgStatus">
							<option value="">--请选择--</option>
							<option value="1">待审批</option>
							<option value="2">撤销</option>
							<option value="3">审批通过</option>
							<option value="4">审批驳回</option>
							<option value="5">关闭申请</option>
							</select>
						</td>
						<td colspan="2">
							<div align="center">
								<button type="button" onclick="searchHandler()">
								<span class="icon_find">查询</span>
								</button>
								<button type="button" onclick="resetSearch()">
									<span class="icon_reload">重置</span>
								</button>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="box2_custom"  boxType="box2" panelTitle="数据列表" class="padding_right5">
			<div id="dataBasic"></div>
		</div>
	</body>
</html>