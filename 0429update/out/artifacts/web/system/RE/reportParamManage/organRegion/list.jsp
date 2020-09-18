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
                            display: '机构号',
                            name: 'organcode',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '机构名称',
                            name: 'organname',
                            width: '20%',
                            align: 'center'
                        }, {
                            display: '地理区域划分',
                            name: 'type1',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == 1) {
                                    return "总行本部";
                                } else if (value == 2) {
                                    return "华北地区";
                                } else if (value == 3) {
                                    return "东北地区";
                                } else if (value == 4) {
                                    return "华东地区";
                                } else if (value == 5) {
                                    return "华中地区";
                                } else if (value == 6) {
                                    return "华南地区";
                                } else if (value == 7) {
                                    return "西南地区";
                                } else if (value == 8) {
                                    return "西北地区";
                                } else {
                                    return "暂未分组";
                                }
                            }
                        }, {
                            display: '银行同业划分',
                            name: 'type2',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == 1) {
                                    return "总行本部";
                                } else if (value == 2) {
                                    return "长三角";
                                } else if (value == 3) {
                                    return "珠三角";
                                } else if (value == 4) {
                                    return "环渤海";
                                } else if (value == 5) {
                                    return "中部地区";
                                } else if (value == 6) {
                                    return "西部地区";
                                } else if (value == 7) {
                                    return "东北地区";
                                } else {
                                    return "暂未分组";
                                }
                            }
                        }, {
                            display: '财务考核划分',
                            name: 'type3',
                            width: '20%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == 1) {
                                    return "总行本部";
                                } else if (value == 2) {
                                    return "第一组";
                                } else if (value == 3) {
                                    return "第二组";
                                } else if (value == 4) {
                                    return "第三组";
                                } else if (value == 5) {
                                    return "第四组";
                                } else {
                                    return "暂未分组";
                                }
                            }
                        },{
                            display: '排序',
                            name: 'organorder',
                            width: '10%',
                            align: 'center'
                        }
                    ],
                    url: '<%=path%>/fdReportOrgan/findPage.htm',
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
        showDialog("<%=path%>/fdReportOrgan/insertUI.htm", "新增信息", 800, 280);
    }

    //修改
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/fdReportOrgan/updateUI.htm?organcode=" + rows[0].organcode,
                "修改信息", 800, 480);
        }
    }

    //删除
    function onDelete() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            top.Dialog.confirm("确定要删除该记录吗？", function () {
                //删除记录
                $.post("<%=path%>/fdReportOrgan/delete.htm", {
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