<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/common_list.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!--数据表格start-->
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <!--数据表格end-->
    <!--表单验证start-->
    <script src="${path}/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="${path}/libs/js/form/validation.js" type="text/javascript"></script>
    <!--表单验证end-->
    <!--表单异步提交start-->
    <script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
    <!--表单异步提交end-->
    <!-- 日期选择框start -->
    <script src="<%=path%>/libs/js/form/datePicker/WdatePicker.js" type="text/javascript"></script>
    <!-- 日期选择框end -->
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
    <!-- 树组件end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
</head>
<body>
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <input type="hidden" id="isLeader" name="isLeader"/>
        <table class="tableStyle" mode="list" formMode="line" style="width: 97%;">
            <tr>
                <td width="16%" align="right">
                    交易日期：
                </td>
                <td width="16%">
                    <input type="text" id="tradeDate" name="tradeDate" class="date validate[length[0,8]]"
                           dateFmt="yyyyMMdd" maxlength="8"/>
                </td>
                <td width="16%" align="right">
                    操作员代码：
                </td>
                <td width="16%">
                    <input type="text" id="operCode" name="operCode" class="validate[custom[onlyNumber],length[0,11]]"
                           maxlength="20"/>
                </td>
            </tr>
            <tr>
                <td width="16%" align="right">
                    操作员姓名：
                </td>
                <td width="16%">
                    <input type="text" id="operName" name="operName" class="validate[length[0,200]]" maxlength="200"/>
                </td>
                <td colspan="2" width="32%"></td>
                <td colspan="2">
                    <div align="center">
                        <button type="button" onclick="searchHandler()">
                            <span class="icon_find">查询</span>
                        </button>
                        <button type="button" onclick="resetSearch()">
                            <span class="icon_reload">重置</span>
                        </button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div id="dataBasic"></div>
</div>
<script type="text/javascript">
    var grid = null;

    function initComplete() {
        //当提交表单刷新本页面时关闭弹窗
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '交易日期',
                            name: 'tradeDate',
                            width: '14%',
                            align: 'center',
                            type: 'D_TRADE_TIME'
                        }, {
                            display: '操作员代码',
                            name: 'operCode',
                            width: '14%',
                            align: 'center'
                        }, {
                            display: '操作员姓名',
                            name: 'operName',
                            width: '14%',
                            align: 'center'
                        }, {
                            display: '功能名称',
                            name: 'moduleName',
                            width: '14%',
                            align: 'center'
                        }, {
                            display: '传递参数',
                            name: 'argu',
                            width: '14%',
                            align: 'center'
                        }, {
                            display: '执行结果',
                            name: 'runningResult',
                            width: '14%',
                            align: 'center'
                        }],
                    url: '<%=path%>/webLogInfo/findPage.htm',
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

    //查看详细
    function onInfo() {
        if (selectOneRow(grid) == true) {
            var rows = grid.getSelectedRows();
            var url = "<%=path%>/webLogInfo/infoUI.htm?id=" + rows[0].id;
            showDialog(url, "详细信息", 550, 360);
        }
    }

    //查询
    function searchHandler() {
        var valid = $("#queryForm").validationEngine({
            returnIsValid: true
        });
        if (valid) {
            var query = $("#queryForm").formToArray();
            $.post("<%=path%>/webLogInfo/checkSelectData.htm", query,
                function (result) {
                    if (result.success == "true" || result.success == true) {
                        var query = $("#queryForm").formToArray();
                        grid.setOptions({
                            params: query
                        });
                        //页号重置为1
                        grid.setNewPage(1);
                        grid.loadData();//加载数据
                    } else {
                        top.Dialog.alert(result.msg);
                    }
                }, "json");
        }
    }

    //重置查询
    function resetSearch() {
        $("#queryForm")[0].reset();
        $('#search').click();
    }

    //刷新表格数据并重置排序和页数
    function refresh(isUpdate) {
        if (!isUpdate) {
            //重置排序
            grid.options.sortName = 'appDate';
            grid.options.sortOrder = "desc";
            //页号重置为1
            grid.setNewPage(1);
        }
        grid.loadData();
    }
</script>
</body>
</html>
