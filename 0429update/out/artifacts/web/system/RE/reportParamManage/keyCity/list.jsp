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

    function initComplete() {

        //当提交表单刷新本页面时关闭弹窗
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [

                        {
                            display: '机构号',
                            name: 'organcode',
                            width: '30%',
                            align: 'center'
                        }, {
                            display: '机构名称',
                            name: 'organname',
                            width: '40%',
                            align: 'center'
                        }, {
                            display: '排序字段',
                            name: 'organorder',
                            width: '30%',
                            align: 'center'
                        }
                    ],
                    url: '<%=path%>/tbKeyReportOrgan/findPage.htm',
                    sortName: '',
                    rownumbers: true,
                    checkbox: true,
                    height: '100%',
                    width: "100%",
                    usePager: false,
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
        showDialog("<%=path%>/tbKeyReportOrgan/insertUI.htm", "新增信息", 800, 280);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbKeyReportOrgan/updateUI.htm?organcode=" + rows[0].organcode,
                "修改信息", 800, 480);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/tbKeyReportOrgan/delete.htm", {
                    organcode: rows[0].organcode
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


</script>
</body>
</html>