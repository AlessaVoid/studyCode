<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>贷种组合管理</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
    <!-- 树组件end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
    <title></title>
</head>
<body>
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width: 100%;">
            <tr>
                <td width="16%" align="right">组合编号:</td>
                <td width="20">
                    <div class="suggestion" id="combCode" name="combCode" matchName="combCode"
                         url="<%=path%>/webReportComb/selectCombCode.htm" suggestMode="remote"></div>
                </td>
                <td>组合名称:</td>
                <td width="20">
                    <div class="suggestion" id="combName" name="combName" matchName="combName"
                         url="<%=path%>/webReportComb/selectCombName.htm" suggestMode="remote"></div>
                </td>
                <td>组合级别:</td>
                <td width="20">
                    <dic:select id="combLevel" dicType="combLevel" name="combLevel"/>
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
<div class="box2_custom" boxType="box2" panelTitle="贷种组合列表" class="padding_right5">
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
                            display: '组合编号',
                            name: 'combCode',
                            width: '15%',
                            align: 'center'
                        },{
                            display: '组合名称',
                            name: 'combName',
                            width: '35%',
                            align: 'center'
                        }, {
                            display: '组合级别',
                            name: 'combLevel',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value === 1) {
                                    return "一级";
                                } else if (value === 2) {
                                    return "二级";
                                } else if (value === 3) {
                                    return "三级";
                                }
                            }
                        },
                        {
                            display: '组合明细',
                            name: 'combChildren',
                            width: '40%',
                            align: 'center'
                        }],
                    url: '<%=path%>/webReportComb/listAllLoanComb.htm',
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
    // 插入贷种组合
    function onInsert() {
        showDialog("<%=path%>/webReportComb/insertUI.htm", "新增贷种组合", 600, 380);
    }

    //更新贷种组合
    function onUpdate() {
        if (selectOneRow(grid)) {
                var rows = grid.getSelectedRows();
            if (rows[0].combStatus === -1) {
                top.Dialog.alert("贷种已经删除,不支持编辑操作!");
                return;
            }
            if (selectOneRow(grid)) {
                showDialog("<%=path%>/webReportComb/updateUI.htm?combCode=" + rows[0].combCode,
                    "修改贷种组合", 600, 380);
            }
        }
    }

    //删除贷种组合
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].combStatus === -1) {
                top.Dialog.alert("贷种状态已经已删除,不能重复此操作!");
                return;
            }
            if (rows[0].combStatus === 1) {
                top.Dialog.alert("贷种已经被占用,不支持删除操作!");
                return;
            }
            top.Dialog.confirm("确定要删除吗？", function () {
                $.post("<%=path%>/webReportComb/deleteLoanCombInfo.htm", {
                    combCode: rows[0].combCode,
                    combLevel: rows[0].combLevel
                }, function (result) {
                    if (result.success === "true" || result.success === true) {
                        top.Dialog.alert("删除贷种成功!");
                        grid.loadData();
                    } else {
                        top.Dialog.alert("删除贷种组合失败");
                    }
                }, "json");
                grid.loadData();
            });
        }
    }
    function nodeReload(){
        grid.loadData();
    }
    //展示贷种信息详情
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/webReportComb/infoUI.htm?combCode=" + rows[0].combCode, "详细信息", 600, 380);
        }
    }

</script>
</body>
</html>