<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>信贷计划列表页</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>
                <td>姓名：</td>
                <td width="20">
                    <input name="name" type="text" id="sys_time"  />

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

    var grid = null;

    function initComplete() {
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '姓名',
                            name: 'name',
                            width: '30%',
                            align: 'center'
                        },{
                            display: '手机号',
                            name: 'phoneNumber',
                            width: '30%',
                            align: 'center'
                        },  {
                            display: '状态',
                            name: 'status',
                            width: '40%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" === value || 1 === value) {
                                    return "启用";
                                } else if ("2" === value || 2 === value) {
                                    return "停用";
                                }
                            }
                        }],
                    url: '<%=path%>/msgPerson/findPage.htm',
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
                    }
                });
    }

    //新增
    function onInsert() {
        showDialog("<%=path%>/msgPerson/insertUI.htm", "新增", 600, 400);
    }



    //更新
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/msgPerson/updateUI.htm?id=" + rows[0].id, "修改", 600, 400);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除吗？", function () {
                $.post("<%=path%>/msgPerson/delete.htm", {
                    id: rows[0].id
                }, function (result) {
                    if (result.success === "true" || result.success === true) {
                        top.Dialog.alert("删除成功");
                        grid.loadData();
                    } else {
                        top.Dialog.alert(result.msg);
                    }
                }, "json");
            });
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



</script>
</body>
</html>