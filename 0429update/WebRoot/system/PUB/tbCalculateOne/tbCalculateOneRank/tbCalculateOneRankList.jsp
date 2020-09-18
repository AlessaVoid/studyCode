<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title></title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
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
                            display: '名称',
                            name: 'name',
                            width: '20%',
                            align: 'center'
                        },
                        {
                            display: '类型',
                            name: 'type',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" == value) {
                                    return "模式一存贷联动类";
                                } else if ("2" == value) {
                                    return "模式一结构优化类";
                                } else if ("4" == value) {
                                    return "模式一市场竞争类";
                                } else if ("8" == value) {
                                    return "模式一价值提升类";
                                }else if("16" == value){
                                    return "模式二排名系数";
                                }
                            }
                        },
                        {
                            display: '创建人员',
                            name: 'createOper',
                            width: '15%',
                            align: 'center'
                        },
                        {
                            display: '更新人员',
                            name: 'updateOper',
                            width: '15%',
                            align: 'center'
                        }, {
                            display: '创建时间',
                            name: 'createTime',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '更新时间',
                            name: 'updateTime',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '启停状态',
                            name: 'status',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" == value) {
                                    return "启用";
                                } else if ("2" == value) {
                                    return "停用";
                                } else {
                                    return "未知";
                                }
                            }
                        },],
                    url: '<%=path%>/tbCalculateOneRank/findPage.htm',
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
        showDialog("<%=path%>/tbCalculateOneRank/insertUI.htm", "新增信息", 888, 789);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbCalculateOneRank/updateUI.htm?id=" + rows[0].id,
                "修改信息", 888, 789);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/tbCalculateOneRank/delete.htm", {
                    id: rows[0].id
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
            showDialog("<%=path%>/tbCalculateOneRank/infoUI.htm?id=" + rows[0].id,
                "详细信息", 888, 789);
        }
    }


</script>
</body>
</html>