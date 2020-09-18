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
                    机构编号：
                </td>
                <td width="17">
                    <div class="suggestion" id="thiscode" name="thiscode" matchName="thiscode"
                         url="<%=path%>/WebOrganInfo/selectCode.htm" suggestMode="remote"></div>
                </td>
                <td width="10%" align="right">
                    机构名称：
                </td>
                <td width="17">
                    <div class="suggestion" id="organname" name="organname" matchName="organname"
                         url="<%=path%>/WebOrganInfo/selectName.htm" suggestMode="remote"></div>
                </td>
                <td>机构级别:</td>
                <td width="20">
                    <dic:select id="organlevel" dicType="D_ORGAN_LEVEL" name="organlevel"/>
                </td>
                <td width="10%" align="right">
                    上级机构：
                </td>
                <td width="17">
                    <div class="suggestion" id="uporgan" name="uporgan" matchName="organname"
                         url="<%=path%>/WebOrganInfo/selectName.htm" suggestMode="remote"></div>
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
                            display: '机构编号',
                            name: 'thiscode',
                            width: '17%',
                            align: 'center'
                        },{
                            display: '机构名称',
                            name: 'organname',
                            width: '17%',
                            align: 'center'
                        },{
                            display: '机构级别',
                            name: 'organlevel',
                            width: '15%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value === 0 || value === "0") {
                                    return "总行";
                                } else if (value === 1 || value === "1") {
                                    return "一级分行";
                                } else if (value === 2 || value === "2") {
                                    return "二级分行";
                                }else if (value === 3 || value === "3") {
                                    return "一级支行";
                                }else if (value === 4 || value === "4") {
                                    return "网点";
                                }
                            }
                        },
                         {
                            display: '地区机构名称',
                            name: 'areacode',
                            width: '17%',
                            align: 'center'
                        }, {
                            display: '市,县局机构名称',
                            name: 'citycode',
                            width: '17%',
                            align: 'center'
                        }, {
                            display: '上级机构',
                            name: 'uporgan',
                            width: '17%',
                            align: 'center'
                        }],
                    <%--url: '<%=path%>/WebOrganInfo/findPage.htm',--%>
                    url: '<%=path%>/WebOrganInfo/findList.htm',
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
        showDialog("<%=path%>/WebOrganInfo/.htm", "新增机构信息", 300, 150);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/WebOrganInfo/updateUI.htm?method=update&organCode=" + rows[0].thiscode,
                "修改机构信息", 300, 150);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/WebOrganInfo/delete.htm", {
                    organCode: rows[0].thiscode,
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
            showDialog("<%=path%>/WebOrganInfo/infoUI.htm?organCode=" + rows[0].thiscode,
                "机构详细信息", 600, 450);
        }
    }
    //查询
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
