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
                            display: '时间类型',
                            name: 'type',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "季末月月中";
                                } else if ("1" == value) {
                                    return "季末月月末";
                                } else if ("2" == value) {
                                    return "非季末月月中";
                                } else if ("3" == value) {
                                    return "非季末月月末";
                                }
                            }
                        },
                        {
                            display: '应用机构',
                            name: 'ppOrgan',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "全行";
                                } else if ("1" == value) {
                                    return "河北分行";
                                } else if ("2" == value) {
                                    return "二分";
                                } else if ("3" == value) {
                                    return "一支";
                                } else {
                                    return value;
                                }
                            }
                        },
                        {
                            display: '状态',
                            name: 'state',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "启用";
                                } else {
                                    return "停用";
                                }
                            }
                        }, {
                            display: '更新时间',
                            name: 'updatetime',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '最后修改用户',
                            name: 'updateuserid',
                            width: '20%',
                            align: 'center'
                        }],
                    url: '<%=path%>/punishManger/punishParam/findPage.htm',
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
        showDialog("<%=path%>/punishManger/punishParam/insertUI.htm", "新增信息", 1270, 440);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/punishManger/punishParam/updateUI.htm?type=" + rows[0].type,
                "修改信息", 1280, 380);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/punishManger/punishParam/deleteTbPunishParam.htm", {
                    type: rows[0].type
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
            showDialog("<%=path%>/punishManger/punishParam/infoUI.htm?type=" + rows[0].type,
                "详细信息", 1270, 440);
        }
    }

    function onDeploy() {

        $.post("<%=path%>/punishManger/punishParam/deploy.htm", function (result) {
            if (result.success == "true" || result.success == true) {
                top.Dialog.alert(result.msg);
                grid.loadData();
            } else {
                top.Dialog.alert(result.msg);
            }
        }, "json");
    }

</script>
</body>
</html>