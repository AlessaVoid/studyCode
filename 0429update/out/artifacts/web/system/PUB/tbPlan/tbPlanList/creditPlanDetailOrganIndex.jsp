<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>信贷计划列表页</title>
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
                    <input name="planMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;" />

                    <%--                    <select id="planMonth" name="planMonth">--%>
                    <%--                        <option value="">---请选择---</option>--%>
                    <%--                    </select>--%>
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
                            display: '所属月份',
                            name: 'month',
                            width: '25%',
                            align: 'center'
                        },{
                            display: '计划净增量',
                            name: 'amount',
                            width: '25%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return transFormat(value)+'';
                            }
                        },  {
                            display: '单位',
                            name: 'unit',
                            width: '25%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" === value || 1 === value) {
                                    return "万元";
                                } else if ("2" === value || 2 === value) {
                                    return "亿元";
                                }
                            }
                        },{
                            display: '贷种级别',
                            name: 'comblevel',
                            width: '25%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" === value || 1 === value) {
                                    return "一级贷种";
                                } else if ("2" === value || 2 === value) {
                                    return "二级贷种";
                                }
                            }
                        }],
                    url: '<%=path%>/creditPlan/organPlanDetail.htm',
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


    //详情
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/creditPlan/toOrganPlanDetail.htm?planId=" + rows[0].planid, "详情信息", 1000, 600);
        }
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

    function transFormat(amount) {
        var amountStr = String(amount);
        if (amountStr.indexOf("-") > 0) {
            amountStr = '0' + String(Number(amountStr) + 1).substr(1);
        }else if(amountStr.indexOf("-") == 0){

            if(amountStr.substr(1).indexOf("-") > 0){
                amountStr = '-0' + String(Number(amountStr.substr(1)) + 1).substr(1);
            }
        }
        return amountStr;
    }



</script>
</body>
</html>