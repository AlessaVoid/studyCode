<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>评分参数列表页</title>
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
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '参数名称',
                            name: 'elementtypename',
                            width: '33%',
                            align: 'center'
                        }, {
                            display: '目标分值',
                            name: 'targetscore',
                            width: '33%',
                            align: 'center'
                        },
                        {
                            display: '修改时间',
                            name: 'updatetime',
                            width: '33%',
                            align: 'center'
                        }],
                    url: '<%=path%>/tbOrganRateParam/tbOrganRateParamIndexDate.htm',
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


    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbOrganRateParam/updateTbOrganRateParamPage.htm?elementtype=" + rows[0].elementtype,
                "修改信息", 1000, 400);
        }
    }



    //查看
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbOrganRateParam/tbOrganRateParamDetailPage.htm?elementtype=" + rows[0].elementtype,
                "详细信息", 1000, 400);
        }
    }






</script>
</body>
</html>