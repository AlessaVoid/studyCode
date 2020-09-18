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
                <td width="16%" align="right">所属月份：</td>
                <td width="20">

                    <input name="lineReqMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;" />


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
                            name: 'lineReqMonth',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '需求名称',
                            name: 'lineReqName',
                            width: '20%',
                            align: 'center'
                        },  {
                            display: '条线名称',
                            name: 'lineName',
                            width: '20%',
                            align: 'center'
                        },  {
                            display: '下发机构代码',
                            name: 'lineOrgan',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '下发机构名称',
                            name: 'lineOrgan',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return organMap[value];

                            }
                        },   {
                            display: '当前状态',
                            name: 'lineState',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" == value) {
                                    return "待录入";
                                } else if ("2" == value) {
                                    return "待提交";
                                } else if ("4" == value) {
                                    return "已提交,待审批";
                                } else if ("8" == value) {
                                    return "审批中";
                                } else if ("16" == value) {
                                    return "审批通过，已上报";
                                } else if ("32" == value) {
                                    return "已驳回";
                                }
                            }
                        }
                    ],
                    url: '<%=path%>/tbTradeManger/tbLineReqResetDetail/showFindPage.htm',
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
            if (rows[0].lineState >= 4) {
                top.Dialog.alert("该要求已提交,不允许重复提交！", null, null, null, 5);
                return;
            } else if (rows[0].lineState <= 1) {
                top.Dialog.alert("该需求未填写,本次操作无效！", null, null, null, 5);
                return;
            } else {
                showDialog("<%=path%>/TbLineReqResetApp/commitTbReqUI.htm?lineReqresetId=" + rows[0].lineReqresetId, "信贷需求提交页面", 800, 600);
            }
        }
    }

    //新增
    function onInsert() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].lineState == 1) {
                showDialog("<%=path%>/tbTradeManger/tbLineReqResetDetail/insertUI.htm?lineReqresetId=" + rows[0].lineReqresetId, "录入信息", 1080, 680);
            } else {
                top.Dialog.alert("该需求已录入,无法再次录入！", null, null, null, 5);
                return;
            }
        }
        grid.loadData();
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].lineState == 2) {
                showDialog("<%=path%>/tbTradeManger/tbLineReqResetDetail/updateUI.htm?lineReqresetId=" + rows[0].lineReqresetId,
                    "修改信息", 1080, 680);
            } else if (rows[0].lineState >= 4) {
                top.Dialog.alert("该需求已提交,无法在做修改！", null, null, null, 5);
                return;
            } else if (rows[0].lineState == 1) {
                top.Dialog.alert("该需求未填报,请先录入需求！", null, null, null, 5);
                return;
            }
        }
        grid.loadData();
    }

    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();

            if (rows[0].lineState == 1) {
                top.Dialog.alert("该需求未填报,请先录入需求！", null, null, null, 5);
                return;
            }
            showDialog("<%=path%>/tbTradeManger/tbLineReqResetDetail/infoUI.htm?lineReqresetId=" + rows[0].lineReqresetId,
                "详细信息", 900, 680);
        }
    }
</script>
</body>
</html>