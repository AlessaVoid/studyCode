<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>信贷计划列表页</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<body>

<!-- Grid位置 -->
<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div id="dataBasic"></div>
</div>
<script type="text/javascript">

    var grid = null;

    function initComplete() {
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '短信开关',
                            name: 'openStatus',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" === value || 1 === value) {
                                    return "打开";
                                } else if ("2" === value || 2 === value) {
                                    return "关闭";
                                }
                            }
                        },{
                            display: '每天最多发送短信的次数',
                            name: 'maxCount',
                            width: '20%',
                            align: 'center'
                        },  {
                            display: '服务开启时发送短信的次数',
                            name: 'openCount',
                            width: '20%',
                            align: 'center'
                        },{
                            display: '服务关闭时发送短信的次数',
                            name: 'closeCount',
                            width: '20%',
                            align: 'center'
                        },{
                            display: '今日已发送的次数',
                            name: 'todayCount',
                            width: '20%',
                            align: 'center'
                        }],
                    url: '<%=path%>/msgParam/findPage.htm',
                    sortName: '',
                    rownumbers: true,
                    checkbox: true,
                    height: '100%',
                    width: "100%",
                    pageSize: 10,
                    toolbar: {
                        items: [
                            ${btnList}
                        ]
                    }
                });
    }



    //更新
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/msgParam/updateUI.htm?id=" + rows[0].id, "修改", 500, 400);
        }
    }



</script>
</body>
</html>