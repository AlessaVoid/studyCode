<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>已提交的计划页面</title>
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
                <td width="20">
                    <input name="planMonth" type="text" id="sys_time" class="input-small inline form_datetime" style="width: 160px;" />

<%--                    <select id="planMonth" name="planMonth">--%>
<%--                        <option value="">---请选择---</option>--%>
<%--                    </select>--%>
                </td>
                <td>状态:</td>
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
    });
    var grid = null;


    function initComplete() {
        top.Dialog.close();
        grid = $("#dataBasic").quiGrid(
            {

                columns: [
                    {
                        display: '所属月份',
                        name: 'planadjmonth',
                        width: '15%',
                        align: 'center'
                    },{
                        display: '原计划净增量(亿元)',
                        name: '',
                        width: '15%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            // return rowdata.planadjrealincrement - rowdata.planadjadjamount;
                            return accAdd(rowdata.planadjrealincrement, -rowdata.planadjadjamount)+'';
                        }
                    },{
                        display: '计划调整量(亿元)',
                        name: 'planadjadjamount',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return transFormat(value)+'';
                        }
                    },{
                        display: '调整后计划净增量(亿元)',
                        name: 'planadjrealincrement',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return transFormat(value)+'';
                        }
                    },
                    {
                        display: '状态',
                        name: 'planadjstatus',
                        width: '15%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if ("0" === value || 0 === value) {
                                return "已填写,待提交";
                            } else if ("2" === value || 2 === value) {
                                return "已填写,待提交";
                            } else if ("8" === value || 8 === value) {
                                return "审批中";
                            } else if ("16" === value || 16 === value) {
                                return "已完成审批,计划生效！";
                            } else if ("32" === value || 32 === value) {
                                return "已驳回";
                            }
                        }
                    } ,{
                        display: '创建时间',
                        name: 'planadjcreatetime',
                        width: '15%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            return value.substring(0, 10);
                        }
                    }],
                url: '<%=path%>/tbPlanadjStripeSub/getSubmitAuditHistoryRecord.htm',
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

    //查看审批详情
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbPlanadjStripeSub/listTbPlanSubmitDetailAuditUI.htm?planadjId=" + rows[0].planadjid + "&processInstanceId=" + rows[0].procinstid, "详细信息", 1280, 680);
        }
    }

    //查看流程图
    function onViewImg() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].planadjstatus == 16) {
                top.Dialog.alert("该申请已完成,暂不能查看流程图！", null, null, null, 5);
                return;
            }
            showDialog("<%=path%>/workflow/imageUIForPlan.htm?taskId=" + rows[0].taskId + "&processInstanceId=" + rows[0].procinstid, "当前流程图", 1380, 680);
        }
    }

    //下载
    function onDownload() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            location = "<%=path%>/tbPlanadjStripeSub/downloadPlan.htm?planadjId=" + rows[0].planadjid;
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

    //js计算乘法
    function accMul(arg1, arg2)
    {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    //JS计算除法
    function accDiv(arg1, arg2)
    {
        var t1 = 0, t2 = 0, r1, r2;
        try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
        try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""))
            r2 = Number(arg2.toString().replace(".", ""))
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }

    //js加法计算
    function accAdd(arg1, arg2) {
            var r1, r2, m;
            try {
                r1 = arg1.toString().split(".")[1].length
            } catch (e) {
                r1 = 0
            }
            try {
                r2 = arg2.toString().split(".")[1].length
            } catch (e) {
                r2 = 0
            }
            m = Math.pow(10, Math.max(r1, r2))
            return transFormat((accMul(arg1, m) + accMul(arg2, m)) / m)
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