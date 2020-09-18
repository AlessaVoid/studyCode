<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title></title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" media="screen"
          href="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <script type="text/javascript"
            src="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>



</head>
<body>
<!-- Grid位置 -->
<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div id="dataBasic"></div>
</div>
<script type="text/javascript">
    var grid = null;

    $(function () {
        //日期选择框
        $('#sys_time').datetimepicker({
            format: 'yyyymm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
            forceParse: false,
            language: 'zh-CN'
        });
        $('#sys_time').datetimepicker('update', new Date());
        initComplete();
    });
    function initComplete() {

        //当提交表单刷新本页面时关闭弹窗
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [{
                        display: '编号',
                        name: 'id',
                        width: '20%',
                        align: 'center'
                    },
                        {
                            display: '所属月份',
                            name: 'month',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '罚息名称',
                            name: 'name',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '意见征集截止时间',
                            name: 'collectEndTime',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8);
                            }
                        }, {
                            display: '状态',
                            name: 'state',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "草稿";
                                } else if ("1" == value) {
                                    return "新增待提交";
                                } else if ("2" == value) {
                                    return "已下发";
                                } else if ("4" == value) {
                                    return "已提交,待审批";
                                } else if ("8" == value) {
                                    return "审批中";
                                } else if ("16" == value) {
                                    return "审批通过，已上报";
                                } else if ("32" == value) {
                                    return "已驳回";
                                }
                            }
                        }],
                    url: '<%=path%>/tbTradeManger/tbPunishList/findPage.htm',
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


    function onSubmit() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            if (rows[0].state >= 4) {
                top.Dialog.alert("该申请已提交,本次操作无效！", null, null, null, 5);
                return;
            }
            if (rows[0].state < 1) {
                top.Dialog.alert("该申请未完善,本次操作无效！", null, null, null, 5);
                return;
            }

            showDialog("<%=path%>/tbTradeManger/punishListCommit/commitTbQuotaUI.htm?id=" + rows[0].id,
                "提交审批",1800, 680);

        }


    }


    //新增
    function onInsert() {
        var rows = grid.getSelectedRows();
        showDialog("<%=path%>/tbTradeManger/tbPunishList/insertUI.htm", "录入信息", 1060, 480);
        grid.loadData();
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {

            var rows = grid.getSelectedRows();
            if (rows[0].state <= 2) {
                showDialog("<%=path%>/tbTradeManger/tbPunishList/updateUI.htm?id=" + rows[0].id,
                    "修改信息", 1800, 680);
            } else if (rows[0].state >= 4) {
                top.Dialog.alert("该需求已提交,无法在做修改！", null, null, null, 5);
                return;
            }
        }
        grid.loadData();
    }

//下发
    function onIssued() {
        var rows = grid.getSelectedRows();
        if (rows[0].state == 2) {
            top.Dialog.alert("该罚息结果已下发,本次操作无效！", null, null, null, 5);
            return;
        }
        top.Dialog.confirm("确定要下发该记录吗？", function () {
            //删除记录
            $.post("<%=path%>/tbTradeManger/tbPunishList/issued.htm", {
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
        });
        grid.loadData();
    }


    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbTradeManger/tbPunishList/infoUI.htm?id=" + rows[0].id,
                "详细信息", 1800, 680);
        }
    }


</script>
</body>
</html>