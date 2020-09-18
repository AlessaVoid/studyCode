<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
	<title>消息子页面</title>
	<%@include file="/common/common_list.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<script type="text/javascript">
		var grid = null;

		function initComplete() {
			grid = $("#webMsg").quiGrid({
				columns: [
					<%--{display : '消息代码',name : 'msgNo',width : '15%',align : 'center'}--%>
					{display: '交易描述', name: 'operName', width: '35%', align: 'center'}
					// , {display: '交易描述', name: 'operDescribe', width: '17%', align: 'center'}
					// , {display: '上一操作人柜员号', name: 'operNo', width: '10%', align: 'center'}
					, {display: '上一操作人机构', name: 'appOrganName', width: '15%', align: 'center'}
					, {display: '上一操作人操作状态', name: 'appOperName', width: '20%', align: 'center',
						render: function (rowdata, rowindex, value, column) {
							return  rowdata.appOperName;
						}
					}

					, {
						display: '操作日期', name: 'appDate', width: '10%', align: 'center',
						render: function (rowdata, rowindex, value, column) {
							var year = value.substring(0, 4);
							var month = value.substring(4, 6);
							var day = value.substring(6, 8);
							return year + "-" + month + "-" + day;
						}
					}
					, {
						display: '操作时间', name: 'appTime', width: '10%', align: 'center',
						render: function (rowdata, rowindex, value, column) {
							var hour = value.substring(0, 2);
							var minute = value.substring(2, 4);
							var second = value.substring(4, 6);
							return hour + ":" + minute + ":" + second;
						}
					}
					, {
						display: '操作', width: '10%', align: 'center',
						render: function (rowdata, rowindex, value, column) {
							return '<div class="padding_top4 padding_left5">'
									+ '<span class="img_edit hand" title="办理" onclick="onViewAndUpdateStatus(' + "'" + rowdata.msgUrl + "'" + ',' + "'" + rowdata.msgNo + "'" + ')"></span>'
									+ '&nbsp;&nbsp;&nbsp;'
									+ '<span class="img_no hand" title="关闭通知" onclick="onClose(' + "'" + rowdata.msgNo + "'" + ')"></span>'
									+ '</div>';
						}
					}],
				url: '<%=path%>/webMsg/getWebMsgInfo.htm?msgTypeCode=${param.msgTypeCode}',
				sortName: '',
				rownumbers: true,
				checkbox: true,
				height: '100%',
				width: "100%",
				pageSize: 10
			});
		};

		var diag = new top.Dialog();



		//操作
		function onView(msgUrl) {
			diag.URL = "<%=path%>/" + msgUrl,
					diag.Title = "任务办理";
			diag.Width = 1200;
			diag.Height = 800;
			diag.show();
		};

		function onViewAndUpdateStatus(msgUrl, msgNo) {
			<%--$.post("<%=path%>/webMsg/onUpdateState.htm", {--%>
			<%--	msgNo: msgNo--%>
			<%--}, function (result) {--%>
			<%--	grid.loadData();--%>
			<%--}, 'json');--%>
			diag.URL = "<%=path%>/" + msgUrl,
					diag.Title = "任务办理";
			diag.Width = 1200;
			diag.Height = 800;

			diag.CancelEvent= function(){
				console.log("message reload");
				grid.loadData();
				diag.close();
			};
			diag.show();
		};

		//关闭
		function onClose(msgNo) {
			$.post("<%=path%>/webMsg/onCloseMsg.htm", {
				msgNo: msgNo
			}, function (result) {
				top.Dialog.alert(result.msg);
				grid.loadData();
			}, 'json');
		};

		function setSize(Width, Height) {
			diag.setSize(Width, Height);
		}


	</script>
</head>
<body>
<div id="webMsg"></div>

</body>
</html>