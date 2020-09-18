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
                    <input name="qaMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;" />

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

        $.post("<%=path%>/tbTradeManger/lineOver/showComb.htm",
            {}, function (result) {
                combMap = result.combMap;
                grid.loadData();
            }, "json");

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
                            name: 'qaMonth',
                            width: '15%',
                            align: 'center'
                        }, {
                            display: '机构id',
                            name: 'qaOrgan',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '机构名称',
                            name: 'qaOrgan',
                            width: '15%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return organMap[value];
                            }
                        }, {
                            display: '贷种组合',
                            name: 'qaComb',
                            width: '14%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return combMap[value];
                            }
                        }, {
                            display: '调整额度',
                            name: 'qaAmt',
                            width: '19%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (rowdata.unit == 1) {
                                    return value + " 万元";
                                } else if (rowdata.unit == 2) {
                                    return value + "亿元";
                                }
                            }
                        }, {
                            display: '状态',
                            name: 'qaState',
                            width: '17%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "草稿";
                                } else if ("1" == value) {
                                    return "新增,待提交";
                                } else if ("2" == value) {
                                    return "已填写,待提交";
                                } else if ("4" == value) {
                                    return "已提交,待审批";
                                } else if ("8" == value) {
                                    return "审批中";
                                } else if ("16" == value) {
                                    return "审批通过，已完结";
                                } else if ("32" == value) {
                                    return "已驳回";
                                }
                            }
                        }],
                    url: '<%=path%>/tbTradeManger/lineTbQuotaApply/findPage.htm',
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


    function onSubmit() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].qaState >= 4) {
                top.Dialog.alert("该申请已提交,本次操作无效！", null, null, null, 5);
                return;
            }
            if (rows[0].qaState < 1) {
                top.Dialog.alert("该申请未完善,本次操作无效！", null, null, null, 5);
                return;
            }

            showDialog("<%=path%>/tbTradeManger/lineTbLineTempCommit/commitTbQuotaUI.htm?qaId=" + rows[0].qaId,
                "提交审批", 1000, 480);

            grid.loadData();
        }


    }


    //新增
    function onInsert() {
        var rows = grid.getSelectedRows();
        showDialog("<%=path%>/tbTradeManger/lineTbQuotaApply/insertUI.htm", "录入信息", 1060, 480);
        grid.loadData();
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {

            var rows = grid.getSelectedRows();
            if (rows[0].qaState <= 2) {
                showDialog("<%=path%>/tbTradeManger/lineTbQuotaApply/updateUI.htm?qaId=" + rows[0].qaId,
                    "修改信息", 1000, 480);
            } else if (rows[0].qaState >= 4) {
                top.Dialog.alert("该需求已提交,无法在做修改！", null, null, null, 5);
                return;
            }
        }
        grid.loadData();
    }

    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbTradeManger/lineTbQuotaApply/infoUI.htm?qaId=" + rows[0].qaId,
                "详细信息", 1000, 480);
        }
    }


</script>
</body>
</html>