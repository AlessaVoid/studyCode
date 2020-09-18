<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>已提交的机构需求调整页面</title>
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
                <td width="16%" align="right">
                    所属月份：
                </td>
                <td>
                    <input name="reqresetMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;" />

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
    var organMap={};
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
    $(function () {
        //获取json数据
        $.post("<%=path%>/tbTradeManger/tbTradeParam/findTradeParam.htm",
            {}, function (result) {
                //赋给data
                $("#reqMonth").data("data", result);
                //刷新下拉框
                $("#reqMonth").render();
            }, "json");
        $("#reqMonth").setValue("请选择");
    });

    function initComplete() {
        top.Dialog.close();
        $.post("<%=path%>/tbTradeManger/tbReqList/showOrgan.htm",
            {}, function (result) {
                organMap = result.organMap;
                grid.loadData();
            }, "json");
        grid = $("#dataBasic").quiGrid(
            {
                columns:  [

                    {
                        display: '所属月份',
                        name: 'reqresetmonth',
                        width: '11%',
                        align: 'center'
                    }, {
                        display: '需求名称',
                        name: 'reqresetname',
                        width: '17%',
                        align: 'center'
                    }, {
                        display: '周期开始时间',
                        name: 'reqresettimestart',
                        width: '11%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    }, {
                        display: '周期结束时间',
                        name: 'reqresettimeend',
                        width: '11%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    }, {
                        display: '需求发布机构',
                        name: 'reqresetorgan',
                        width: '11%',
                        align: 'center'
                    }, {
                        display: '发布机构名称',
                        name: 'reqresetorgan',
                        width: '16%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return organMap[value];

                        }
                    },  {
                        display: '需求填报开始时间',
                        name: 'reqresetdatestart',
                        width: '11%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    }, {
                        display: '需求填报结束时间',
                        name: 'reqresetdateend',
                        width: '12%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    }
                ],
                url: '<%=path%>/TbReqresetPendingApp/getPendingAppReq.htm',
                sortName: '',
                rownumbers: true,
                checkbox: true,
                height: '100%',
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

    //信贷需求调整审批
    function onAudit() {
        var rows = grid.getSelectedRows();
        if (rows.length === 1) {
            showDialog("<%=path%>/TbReqresetPendingApp/listReqDetailAuditUI.htm?reqresetId=" + rows[0].reqresetid + "&taskid=" + rows[0].taskid , "审批信贷需求调整", 1280, 680);
        } else {
            top.Dialog.alert("请选择一条记录");
        }
    }

    //查看流程图
    function onViewImg() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/workflow/imageUI.htm?taskId=" + rows[0].taskid + "&processInstanceId="+rows[0].procinstid,"当前流程图",1380,680);
        }
    }

    function closeWin() {
        setTimeout("top.Dialog.close()", 500);
    }

    function searchHandler() {
        var query = $("#queryForm").formToArray();
        //alert(JSON.stringify(query))
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
        //重置多选下拉框中选中的值
        $(".selectTree").resetValue();
        //扩展click方法，重置JS动态生成的隐藏域的值
        $.each($("[id^=suggestion][id$=_input]"), function (i, v) {
            $(v).nextAll("input[type=hidden]").val(null);
        });
        $('#search').click();
    }
</script>
</body>
</html>