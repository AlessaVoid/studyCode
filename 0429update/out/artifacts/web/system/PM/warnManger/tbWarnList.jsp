<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title></title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width: 100%;">
            <tr>
                <td width="16%" align="right">
                    预警线编号：
                </td>
                <td width="20">
                    <div class="suggestion" id="warnId1" name="warnId1" matchName="warnId1"
                         url="<%=path%>/tbWarn/selectWarnId.htm" suggestMode="remote"></div>
                </td>
                <td>预警线名称：</td>
                <td width="20">
                    <div class="suggestion" id="warnName" name="warnName" matchName="warnName"
                         url="<%=path%>/tbWarn/selectWarnName.htm" suggestMode="remote"></div>
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
    var combMap = {};
    $.post("<%=path%>/tbTradeManger/tbReqList/showCombs.htm",
        {}, function (result) {
            combMap = result.combMap;
        }, "json");

    function initComplete() {
        //当提交表单刷新本页面时关闭弹窗
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '预警线名称',
                            name: 'warnName',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '预警线编号',
                            name: 'warnId',
                            width: '10%',
                            align: 'center'
                        },
                        // {
                        //     display: '机构级别',
                        //     name: 'warnOrgan',
                        //     width: '5%',
                        //     align: 'center',
                        //     render: function (rowdata, rowindex, value, column) {
                        //         if ("0" == value) {
                        //             return "总行";
                        //         } else if ("1" == value) {
                        //             return "一分";
                        //         } else if ("2" == value) {
                        //             return "二分";
                        //         } else if ("3" == value) {
                        //             return "一支";
                        //         }
                        //     }
                        // },
                        {
                            display: '预警贷种',
                            name: 'warnComb',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return combMap[value];
                            }
                        }, {
                            display: '预警线类型',
                            name: 'warnType',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "净增量绝对值";
                                } else if ("1" == value) {
                                    return "完成率相对值";
                                }
                            }
                        }, {
                            display: '一级预警线',
                            name: 'warnOneLine',
                            width: '8%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == rowdata.warnType) {
                                    value=value+"亿元";
                                } else if ("1" == rowdata.warnType) {
                                    value=value+"%";
                                }
                                return value;
                            }
                        }, {
                            display: '二级预警线',
                            name: 'warnTwoLine',
                            width: '8%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == rowdata.warnType) {
                                    value=value+"亿元";
                                } else if ("1" == rowdata.warnType) {
                                    value=value+"%";
                                }
                                return value;
                            }
                        }, {
                            display: '三级预警线',
                            name: 'warnThreeLine',
                            width: '8%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == rowdata.warnType) {
                                    value=value+"亿元";
                                } else if ("1" == rowdata.warnType) {
                                    value=value+"%";
                                }
                                return value;
                            }
                        }, {
                            display: '四级预警线',
                            name: 'warnFourLine',
                            width: '8%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == rowdata.warnType) {
                                    value=value+"亿元";
                                } else if ("1" == rowdata.warnType) {
                                    value=value+"%";
                                }
                                return value;
                            }
                        }, {
                            display: '五级预警线',
                            name: 'warnFiveLine',
                            width: '8%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == rowdata.warnType) {
                                    value=value+"亿元";
                                } else if ("1" == rowdata.warnType) {
                                    value=value+"%";
                                }
                                return value;
                            }
                        }, {
                            display: '更新时间',
                            name: 'warnUpdateTime',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '修改用户',
                            name: 'warnUpdateOper',
                            width: '10%',
                            align: 'center'
                        }],
                    url: '<%=path%>/tbWarn/findPage.htm',
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
        showDialog("<%=path%>/tbWarn/insertUI.htm", "新增信息", 600, 380);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbWarn/updateUI.htm?warnId=" + rows[0].warnId,
                "修改信息", 800, 380);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/tbWarn/delete.htm", {
                    warnId: rows[0].warnId
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
        }
    }

    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbWarn/infoUI.htm?warnId=" + rows[0].warnId,
                "详细信息", 800, 380);
        }
    }


</script>
</body>
</html>