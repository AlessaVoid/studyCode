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
                <td width="16%" align="right">
                    角色编号：
                </td>
                <td width="20">
                    <div class="suggestion" id="gfRoleCode" name="roleCode" matchName="roleCode"
                         url="<%=path%>/webRoleInfo/selectRoleCode.htm" suggestMode="remote"></div>
                </td>
                <td>角色名称：</td>
                <td width="20">
                    <div class="suggestion" id="gfRoleName" name="roleName" matchName="roleName"
                         url="<%=path%>/webRoleInfo/selectRoleName.htm" suggestMode="remote"></div>
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
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '角色编号',
                            name: 'roleCode',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '角色名称',
                            name: 'roleName',
                            width: '30%',
                            align: 'center'
                        },
                         {
                            display: '最后修改日期',
                            name: 'latestModifyDate',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                var year = value.substring(0, 4);
                                var month = value.substring(4, 6);
                                var day = value.substring(6, 8);
                                return year + "-" + month + "-" + day;

                            }
                        }, {
                            display: '最后修改时间',
                            name: 'latestModifyTime',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                var hour = value.substring(0, 2);
                                var minute = value.substring(2, 4);
                                var second = value.substring(4, 6);
                                return hour + ":" + minute + ":" + second;
                            }
                        }, {
                            display: '最后修改用户',
                            name: 'latestOperCode',
                            width: '20%',
                            align: 'center'
                        }],
                    url: '<%=path%>/webRoleInfo/findPage.htm',
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
        showDialog("<%=path%>/webRoleInfo/insertUI.htm", "新增信息", 600, 380);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/webRoleInfo/updateUI.htm?roleCode=" + rows[0].roleCode,
                "修改信息", 600, 380);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/webRoleInfo/deleteWebRoleInfo.htm", {
                    roleCode: rows[0].roleCode
                }, function (result) {
                    if (result.success == "true" || result.success == true) {
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

    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/webRoleInfo/infoUI.htm?roleCode=" + rows[0].roleCode,
                "详细信息", 600, 380);
        }
    }

</script>
</body>
</html>