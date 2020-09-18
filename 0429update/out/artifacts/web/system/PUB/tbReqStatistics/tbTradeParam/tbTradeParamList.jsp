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
                <td width="16%" align="right">所属月份：</td>
                <td width="20">
                    <input name="paramPeriod" type="text" id="sys_time" class="input-small inline form_datetime"
                           style="width: 160px;" />

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
                            name: 'paramPeriod',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '当月计划净增量',
                            name: 'paramMechIncrement',
                            width: '15%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value + "亿元";
                            }
                        },
                        {
                            display: '当月超限额审批标准',
                            name: 'paramOverMount',
                            width: '15%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value + "亿元";
                            }
                        }, {
                            display: '计划分配模式',
                            name: 'paramMode',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "多分";
                                } else if ("1" == value) {
                                    return "少分";
                                } else if ("2" == value) {
                                    return "严格";
                                } else {
                                    return "未知";
                                }
                            }
                        }, {
                            display: '是否条线管控',
                            name: 'paramIsLine',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "否";
                                } else if ("1" == value) {
                                    return "是";
                                }
                            }
                        },
                        {
                            display: '罚息开始时间',
                            name: 'paramPunishStart',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                            }
                        }, {
                            display: '罚息结束时间',
                            name: 'paramPunsihEnd',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                            }
                        }],
                    url: '<%=path%>/tbTradeManger/tbTradeParam/findPage.htm',
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
        showDialog("<%=path%>/tbTradeManger/tbTradeParam/insertUI.htm", "新增信息", 740, 360);
    }

    //修改
    function onUpdate() {
        var rows = grid.getSelectedRows();
        showDialog("<%=path%>/tbTradeManger/tbTradeParam/updateUI.htm?paramId=" + rows[0].paramId,
            "修改信息", 660, 380);
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();

            $.post("<%=path%>/tbTradeManger/tbTradeParam/checkDelete.htm",
                {"paramPeriod": rows[0].paramPeriod}, function (result) {
                    if (result.success == "true" || result.success == true) {

                        $.post("<%=path%>/tbTradeManger/tbTradeParam/checkUpdate.htm",
                            {"paramPeriod": rows[0].paramPeriod}, function (result) {
                                if (result.success == "true" || result.success == true) {
                                    top.Dialog.confirm("确定要删除该记录吗？", function () {
                                        //删除记录
                                        $.post("<%=path%>/tbTradeManger/tbTradeParam/delete.htm", {
                                            paramId: rows[0].paramId
                                        }, function (result) {
                                            if (result.success == "true" || result.success == true) {
                                                top.Dialog.alert(result.msg);
                                                grid.loadData();
                                            } else {
                                                top.Dialog.alert(result.msg);
                                            }
                                        }, "json");
                                        //刷新表格
                                        grid.loadData();
                                    });

                                } else {
                                    $(".money").each(function () {
                                        fmoney(this);
                                    });
                                    top.Dialog.alert(result.msg);
                                    return;
                                }
                            }, "json");
                    } else {
                        $(".money").each(function () {
                            fmoney(this);
                        });
                        top.Dialog.alert(result.msg);
                        return;
                    }
                }, "json");
        }
    }

    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbTradeManger/tbTradeParam/infoUI.htm?paramId=" + rows[0].paramId,
                "详细信息", 660, 380);
        }
    }


</script>
</body>
</html>