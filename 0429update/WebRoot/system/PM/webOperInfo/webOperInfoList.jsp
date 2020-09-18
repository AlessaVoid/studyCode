<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title></title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width: 100%;">
            <tr>
                <td width="10%" align="right">
                    柜员号：
                </td>
                <td width="17">
                    <div class="suggestion" id="gfoperCode" name="operCode" matchName="operCode"
                         url="<%=path%>/webOperInfo/selectOperCode.htm" suggestMode="remote"></div>
                </td>
                <td width="10%" align="right">
                    姓名：
                </td>
                <td width="17">
                    <div class="suggestion" id="gfOperName" name="operName" matchName="operName"
                         url="<%=path%>/webOperInfo/selectOperName.htm" suggestMode="remote"></div>
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

    function initComplete() {
        //当提交表单刷新本页面时关闭弹窗
        top.Dialog.close();
        grid = $("#dataBasic").quiGrid({
            columns: [
                {
                    display: '柜员号',
                    name: 'operCode',
                    width: '10%',
                    align: 'center'
                }, {
                    display: '姓名',
                    name: 'operName',
                    width: '10%',
                    align: 'center'
                },
                {
                    display: '机构编码',
                    name: 'organCode',
                    width: '10%',
                    align: 'center'
                }, {
                    display: '拥有的角色',
                    name: 'roleName',
                    width: '40%',
                    align: 'center'
                }, {
                    display: '所属条线',
                    name: 'lineName',
                    width: '30%',
                    align: 'center'
                }],
            url: '<%=path%>/webOperInfo/findPage.htm',
            <%--url: '<%=path%>/webOperInfo/findList.htm',--%>
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

    //新增
    function onInsert() {
        showDialog("<%=path%>/webOperInfo/insertUI.htm?method=insert&id=1", "新增人员信息", 600, 300);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/webOperInfo/updateUI.htm?operCode=" + rows[0].operCode+"&organCode="+rows[0].organCode,
                "修改人员信息", 600, 300);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                $.post("<%=path%>/webOperInfo/delete.htm?operCode="+rows[0].operCode+"&organCode="+rows[0].organCode, {
                    deptCode: rows[0].deptCode,
                    organcode: rows[0].organcode
                }, function (result) {
                    if (result.success === "true" || result.success === true) {
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

    //绑定柜员角色
    function onRole() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/v1/oper/role/page.htm?operCode=" + rows[0].operCode + "&deptCode="
                + rows[0].deptCode + "&organCode=" + rows[0].organCode, "绑定人员角色", 600, 300);
        }
    }

    //绑定条线信息
    function onLine() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/v1/oper/line/edit.htm?operCode=" + rows[0].operCode + "&deptCode=" +
                rows[0].deptCode + "&organCode=" + rows[0].organCode, "保存", 600, 300);
        }
    }

    //柜员信息详情
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/webOperInfo/infoUI.htm?operCode=" + rows[0].operCode, "人员详细信息", 600, 300);
        }
    }
</script>
</body>
</html>
