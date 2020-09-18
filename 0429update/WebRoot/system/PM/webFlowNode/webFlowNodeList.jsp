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
                            display: '流程节点名称',
                            name: 'fnName',
                            width: '13%',
                            align: 'center'
                        },
                        {
                            display: '流程节点编号',
                            name: 'fnCode',
                            width: '13%',
                            align: 'center'
                        }, {
                            display: '流程审批种类',
                            name: 'fnKind',
                            width: '12%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return value;
                            }
                        }, {
                            display: '审批节点数量',
                            name: 'fnCount',
                            width: '12%',
                            align: 'center'
                        }, {
                            display: '创建人',
                            name: 'fnCreateOper',
                            width: '12%',
                            align: 'center'
                        }, {
                            display: '创建时间',
                            name: 'fnCreateTime',
                            width: '12%',
                            align: 'center',
                        }, {
                            display: '更新时间',
                            name: 'fnUpdateTime',
                            width: '13%',
                            align: 'center'
                        }, {
                            display: '最近更新者',
                            name: 'fnUpdateOper',
                            width: '13%',
                            align: 'center',
                        }],
                    url: '<%=path%>/webFlow/listAllFlowNode.htm',
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

    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/webFlow/updateUI.htm?fnCode=" + rows[0].fnCode,
                "修改流程节点信息", 600, 300);
        }
    }
</script>
</body>
</html>
