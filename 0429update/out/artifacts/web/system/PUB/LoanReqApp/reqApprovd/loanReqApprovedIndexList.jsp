<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>已审批的机构需求页面</title>
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

                    <input name="reqMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;" />


                </td>

                <td>需求状态:</td>
                <td width="20">
                    <dic:select id="auditStatus" dicType="AUDIT_STATUS" name="auditStatus"/>
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
        top.Dialog.close();
        grid = $("#dataBasic").quiGrid(
            {
                columns: [

                    {
                        display: '所属月份',
                        name: 'reqmonth',
                        width: '15%',
                        align: 'center'
                    }, {
                        display: '需求名称',
                        name: 'reqname',
                        width: '15%',
                        align: 'center'
                    }, {
                        display: '周期开始时间',
                        name: 'reqtimestart',
                        width: '15%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    }, {
                        display: '周期结束时间',
                        name: 'reqtimeend',
                        width: '15%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    },  {
                        display: '需求填报开始时间',
                        name: 'reqstart',
                        width: '15%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    }, {
                        display: '需求填报结束时间',
                        name: 'reqend',
                        width: '15%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                        }
                    }, {
                        display: '当前状态',
                        name: 'state',
                        width: '10%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if ("1" == value) {
                                return "已下发";
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
                url: '<%=path%>/TbLoanReqApproved/getApprovedAuditHistoryRecord.htm',
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

    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/TbLoanReqApproved/listReqApprovedDetailAuditUI.htm?reqId=" + rows[0].reqid + "&processInstanceId=" + rows[0].procinstid, "详细信息", 1280, 680);
        }
    }


    //查看流程图
    function onViewImg() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].state == 16) {
                top.Dialog.alert("该申请已完成,暂不能查看流程图！", null, null, null, 5);
                return;
            }
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