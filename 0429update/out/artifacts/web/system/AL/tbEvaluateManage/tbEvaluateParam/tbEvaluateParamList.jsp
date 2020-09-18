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
    var combMap = {};

    function initComplete() {
        //当提交表单刷新本页面时关闭弹窗
        $.post("<%=path%>/tbTradeManger/tbReqList/showComb.htm",
            {}, function (result) {
                combMap = result.combMap;
            }, "json");
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '贷种组合id',
                            name: 'tpComb',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return combMap[value];
                            }
                        },
                        {
                            display: '满分值',
                            name: 'tpFullScore',
                            width: '20%',
                            align: 'center'
                        },
                        {
                            display: '状态',
                            name: 'tpState',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" == value) {
                                    return "新建";
                                } else if ("1" == value) {
                                    return "已部署";
                                }
                            }
                        }, {
                            display: '创建时间',
                            name: 'tpCreateTime',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '创建用户id',
                            name: 'tpCreateOper',
                            width: '20%',
                            align: 'center'
                        }],
                    url: '<%=path%>/evaluateManger/tbEvaluateParam/findPage.htm',
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
        showDialog("<%=path%>/evaluateManger/tbEvaluateParam/insertUI.htm", "新增信息", 888, 333);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/evaluateManger/tbEvaluateParam/updateUI.htm?tpComb=" + rows[0].tpComb,
                "修改信息", 888, 333);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/evaluateManger/tbEvaluateParam/delete.htm", {
                    tpComb: rows[0].tpComb
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
            showDialog("<%=path%>/evaluateManger/tbEvaluateParam/infoUI.htm?tpComb=" + rows[0].tpComb,
                "详细信息", 888, 333);
        }
    }

    function onDeploy() {

        $.post("<%=path%>/evaluateManger/tbEvaluateParam/deploy.htm", function (result) {
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