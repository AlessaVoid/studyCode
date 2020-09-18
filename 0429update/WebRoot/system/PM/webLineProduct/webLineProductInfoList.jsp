<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>部门条线管理</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width: 100%;">
            <tr>
                <td width="16%" align="right">条线编号:</td>
                <td width="20">
                    <div class="suggestion" id="lineId" name="lineId" matchName="lineId"
                         url="<%=path%>/webLineProduct/selectLineCode.htm" suggestMode="remote"></div>
                </td>
                <td>条线名称:</td>
                <td width="20">
                    <div class="suggestion" id="lineName" name="lineName" matchName="lineName"
                         url="<%=path%>/webLineProduct/selectLineName.htm" suggestMode="remote"></div>
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
<div class="box2_custom" boxType="box2" panelTitle="条线列表" class="padding_right5">
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
                            display: '条线编号',
                            name: 'lineId',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '条线名称',
                            name: 'lineName',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '条线描述',
                            name: 'lineDescription',
                            width: '20%',
                            align: 'center'
                        },{
                            display: '所属机构',
                            name: 'organCode',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '创建日期',
                            name: 'createTime',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '创建人',
                            name: 'lineCreator',
                            width: '10%',
                            align: 'center'
                        },  {
                            display: '最后修改日期',
                            name: 'updateTime',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '最后修改人',
                            name: 'lineUpdater',
                            width: '10%',
                            align: 'center'
                        }],
                    url: '<%=path%>/webLineProduct/listAllLineProduct.htm',
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

    function onInsert() {
        showDialog("<%=path%>/webLineProduct/insertUI.htm", "新增条线", 600, 450);
    }

    function onUpdate() {
        var rows = grid.getSelectedRows();
        if (selectOneRow(grid)) {
            showDialog("<%=path%>/webLineProduct/updateUI.htm?lineId=" + rows[0].lineId,
                "修改条线", 600, 450);
        }
    }

    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除吗？", function () {
                $.post("<%=path%>/webLineProduct/deleteLineProductInfo.htm", {
                    lineId: rows[0].lineId
                }, function (result) {
                    if (result.success === "true" || result.success === true) {
                        top.Dialog.alert("删除条线成功!");
                        grid.loadData();
                    } else {
                        top.Dialog.alert(result.message);
                    }
                }, "json");
                grid.loadData();
            });
        }
    }
function refreshPage(){
    grid.loadData();
}
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/webLineProduct/infoUI.htm?lineId=" + rows[0].lineId, "详细信息", 600, 450);
        }
    }
    function searchHandler() {
        var query = $("#queryForm").formToArray();
        //alert(JSON.stringify(query))
        grid.setOptions( {
            params : query
        });
        //页号重置为1
        grid.setNewPage(1);
        grid.loadData();//加载数据
    }
    //重置查询
    function resetSearch() {
        $("#queryForm")[0].reset();
        $('#search').click();
    }
</script>
</body>
</html>