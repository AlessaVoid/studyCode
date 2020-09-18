<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title></title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <script type="text/javascript" src="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/js/bootstrap.min.js"></script>
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
                    <input name="reqresetMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;" />

                </td>
                <td width="13%" align="right">
                    下发状态：
                </td>
                <td width="20">
                    <dic:select dicType="TB_REQ_STATE" name="reqresetState"></dic:select>
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
    var organMap = {};

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
        $.post("<%=path%>/tbTradeManger/tbReqList/showOrgan.htm",
            {}, function (result) {
                organMap = result.organMap;
                grid.loadData();
            }, "json");

        //当提交表单刷新本页面时关闭弹窗
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '所属月份',
                            name: 'reqresetMonth',
                            width: '9%',
                            align: 'center'
                        },  {
                            display: '需求名称',
                            name: 'reqresetName',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '周期开始时间',
                            name: 'reqresetTimeStart',
                            width: '9%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                            }
                        }, {
                            display: '周期结束时间',
                            name: 'reqresetTimeEnd',
                            width: '9%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                            }
                        }, {
                            display: '需求发布机构',
                            name: 'reqresetOrgan',
                            width: '7%',
                            align: 'center'
                        }, {
                            display: '发布机构名称',
                            name: 'reqresetOrgan',
                            width: '16%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return organMap[value];

                            }
                        }, {
                            display: '下发对象类型',
                            name: 'reqresetType',
                            width: '11%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "机构";
                                } else if ("1" == value) {
                                    return "条线";
                                }

                            }
                        }, {
                            display: '需求填报开始时间',
                            name: 'reqresetDateStart',
                            width: '11%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                            }
                        }, {
                            display: '需求填报结束时间',
                            name: 'reqresetDateEnd',
                            width: '11%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                            }
                        }, {
                            display: '当前状态',
                            name: 'reqresetState',
                            width: '7%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "未下发";
                                } else {
                                    return "已下发";
                                }
                            }
                        }],
                    url: '<%=path%>/tbTradeManger/tbReqresetList/findPage.htm',
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

        $.post("<%=path%>/tbTradeManger/tbReqList/showOrgan.htm",
            {}, function (result) {
                organMap = result.organMap;
                grid.loadData();
            }, "json");
    }

    function onIssued() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].reqresetState >= 1) {
                top.Dialog.alert("该要求已下发,本次操作无效！", null, null, null, 5);
                return;
            }
            top.Dialog.confirm("确定要下发该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/tbTradeManger/tbReqresetList/issued.htm", {
                    reqresetId: rows[0].reqresetId
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


    //新增
    function onInsert() {
        showDialog("<%=path%>/tbTradeManger/tbReqresetList/insertUI.htm", "新增信息", 800, 380);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].reqresetState >= 1) {
                top.Dialog.alert("该要求已下发,暂不支持修改！", null, null, null, 5);
                return;
            }
            showDialog("<%=path%>/tbTradeManger/tbReqresetList/updateUI.htm?reqresetId=" + rows[0].reqresetId,
                "修改信息", 800, 380);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].reqresetState < 1) {
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/tbTradeManger/tbReqresetList/delete.htm", {
                    reqresetId: rows[0].reqresetId
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
                top.Dialog.alert("该要求已下发,删除操作无效！", null, null, null, 5);
                return;
            }
        }
    }

    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbTradeManger/tbReqresetList/infoUI.htm?reqresetId=" + rows[0].reqresetId,
                "详细信息", 10800, 680);
        }
    }


    //查看下级填报列表
    function onShowChild() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].reqresetState < 1) {
                top.Dialog.alert("该要求未下发,本次操作无效！", null, null, null, 5);
                return;
            }
            showDialog("<%=path%>/tbTradeManger/tbReqresetList/showChildUI.htm?reqresetId=" + rows[0].reqresetId,
                "详细信息", 1440, 840);
        }
    }

</script>
</body>
</html>