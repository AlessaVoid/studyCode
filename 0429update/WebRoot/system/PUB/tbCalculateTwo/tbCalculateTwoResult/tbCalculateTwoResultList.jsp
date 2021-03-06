<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title></title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" media="screen"
          href="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <script type="text/javascript"
            src="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>


</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width: 100%;">
            <tr>

                <td>所属月份：</td>
                <td width="20">

                    <input name="month" type="text" id="sys_time" class="input-small inline form_datetime"
                           style="width: 160px;"/>

                </td>

                <td>
                    <div align="center">
                        <button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
                        <button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- Grid位置 -->
<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div id="dataBasic"></div>
</div>
<script type="text/javascript">
    var grid = null;
    $(function () {
        //日期选择框
        $('#sys_time').datetimepicker({
            format: 'yyyymm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
            forceParse: false,
            language: 'zh-CN'
        });
        $('#sys_time').datetimepicker('update', new Date());
    })

    function initComplete() {

        //当提交表单刷新本页面时关闭弹窗
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [

                        {
                            display: '所属月份',
                            name: 'month',
                            width: '50%',
                            align: 'center'
                        }, {
                            display: '当月计划净增总量',
                            name: 'totalNum',
                            width: '50%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value+"亿元";
                            }

                        }
                    ],
                    url: '<%=path%>/tbCalculateTwoResult/findPage.htm',
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
                    },
                    onCheckRow: function (rowdata, rowindex, rowDomElement) {
                        for (var i = 0; i < grid.getData().length; i++) {
                            var row = grid.getRow(i);
                            if (row != rowindex) {
                                grid.unselect(row);
                            }
                        }
                    }
                });
    }


    //新增
    function onInsert() {
        showDialog("<%=path%>/tbCalculateTwoResult/enterUI.htm", "导入数据", 800, 480);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbCalculateTwoResult/updateUI.htm?month=" + rows[0].month,
                "修改信息", 1680, 680);
        }
    }


    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbCalculateTwoResult/infoUI.htm?month=" + rows[0].month,
                "详细信息", 1680, 780);
        }
    }


</script>
</body>
</html>