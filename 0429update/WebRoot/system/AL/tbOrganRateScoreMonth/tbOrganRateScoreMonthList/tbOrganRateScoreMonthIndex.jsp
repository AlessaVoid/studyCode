<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>月度计划评分列表页</title>
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
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>
                <td>所属月份：</td>
                <td width="20">

                    <input name="rateMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;"  />

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

    var grid = null;


    function initComplete() {
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '月份',
                            name: 'rateMonth',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '类型',
                            name: 'rateType',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" === value || 1 === value) {
                                    return "月度评分";
                                } else if ("2" === value || 2 === value) {
                                    return "季度评分";
                                }
                            }
                        },  {
                            display: '评分状态',
                            name: 'rateStatus',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("-1" === value || -1 === value) {
                                    return "已创建,待提交";
                                } else if ("2" === value || 2 === value) {
                                    return "已创建,待提交";
                                } else if ("0" === value || 0 === value) {
                                    return "已创建,待提交";
                                } else if ("8" === value || 8 === value) {
                                    return "审批中";
                                } else if ("16" === value || 16 === value) {
                                    return "已完成审批";
                                } else if ("32" === value || 32 === value) {
                                    return "已驳回";
                                }
                            }
                        },{
                            display: '创建时间',
                            name: 'createTime',
                            width: '20%',
                            align: 'center'
                        },
                        {
                            display: '修改时间',
                            name: 'updateTime',
                            width: '20%',
                            align: 'center'
                        }],
                    url: '<%=path%>/tbOrganRateScoreMonth/tbOrganRateScoreMonthData.htm',
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





    //更新
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].planStatus >= 8) {
                top.Dialog.alert("已提交审批的评分不允许更新");
                return;
            }
            showDialog("<%=path%>/tbOrganRateScoreMonth/updateTbOrganRateScoreMonthPage.htm?id=" + rows[0].id, "修改评分", 1800, 600);
        }
    }


    //提交
    function onSubmit() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbOrganRateScoreMonthApp/commiTbOrganRateScoreMonthUI.htm?id=" + rows[0].id, "提交评分", 1800, 600);
        }
    }

    //详情
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbOrganRateScoreMonth/tbOrganRateScoreMonthDetailPage.htm?id=" + rows[0].id, "评分详情信息", 1800, 600);
        }
    }


    //新增
    function onInsert() {
        var div = $("#dataBasic");
        top.Dialog.confirm("确定要生成评分吗？", function () {
            //加载loading
            div.mask("正在生成...",null,true);
            $.post("<%=path%>/tbOrganRateScoreMonth/addTbOrganRateScoreMonth.htm", {
            }, function (result) {
                //消除加载loading
                div.unmask();
                if (result.success === "true" || result.success === true) {
                    top.Dialog.alert("生成成功!");
                    grid.loadData();
                } else {
                    top.Dialog.alert(result.msg);
                }
            }, "json");
        });

    }



    //查询
    function searchHandler() {
        var query = $("#queryForm").formToArray();
        grid.setOptions({
            params: query
        });
        //页号重置为1
        grid.setNewPage(1);
        grid.loadData();//加载数据
    }

    //重置查询
    function resetSearch() {
        $("#queryForm")[0].reset();
        $("#planMonth").render();
        $('#search').click();
    }


</script>
</body>
</html>