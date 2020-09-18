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
        <table class="tableStyle" mode="list" formMode="line" style="width: 97%;">
            <tr>
                <td width="16%" align="right">
                    报送要求编号：
                </td>
                <td width="20">
                    <div class="suggestion" id="alReqId" name="reqId" matchName="reqId"
                         url="<%=path%>/tbTradeManger/tbReqList/showReqId.htm" suggestMode="remote"></div>
                </td>
                <td>所属月份：</td>
                <td width="20">
                    <div class="suggestion" id="alReqMonth" name="reqMonth" matchName="reqMonth"
                         url="<%=path%>/tbTradeManger/tbReqList/showReqMonth.htm" placeholder="格式yyyyMM"
                         suggestMode="remote"></div>
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
                            display: '编号',
                            name: 'reqId',
                            width: '4%',
                            align: 'center'
                        }, {
                            display: '所属月份',
                            name: 'reqMonth',
                            width: '9%',
                            align: 'center'
                        }, {
                            display: '需求发布机构',
                            name: 'reqOragn',
                            width: '8%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" == value) {
                                    return "总行";
                                } else if ("1" == value) {
                                    return "一分";
                                } else if ("2" == value) {
                                    return "二分";
                                } else if ("3" == value) {
                                    return "一支";
                                }

                            }
                        },  {
                            display: '需求填报开始时间',
                            name: 'reqDateStart',
                            width: '12%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                var year = value.substring(0, 4);
                                var month = value.substring(4, 6);
                                var day = value.substring(6, 8);
                                return year + "-" + month + "-" + day;

                            }
                        }, {
                            display: '需求填报结束时间',
                            name: 'reqDateEnd',
                            width: '12%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                var year = value.substring(0, 4);
                                var month = value.substring(4, 6);
                                var day = value.substring(6, 8);
                                return year + "-" + month + "-" + day;

                            }
                        }, {
                            display: '填报附件id',
                            name: 'reqAttachmentId',
                            width: '8%',
                            align: 'center'
                        }, {
                            display: '当前状态',
                            name: 'reqState',
                            width: '8%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" == value) {
                                    return "已下发";
                                } else if ("2" == value) {
                                    return "待提交";
                                }else if ("4" == value) {
                                    return "已提交,待审批";
                                }
                            }
                        }, {
                            display: '填报说明',
                            name: 'reqNote',
                            width: '23%',
                            align: 'center'
                        },
                        {
                            display: '更新时间',
                            name: 'reqUpdatetime',
                            width: '8%',
                            align: 'center'
                        }, {
                            display: '最后修改用户',
                            name: 'reqUpdateOper',
                            width: '8%',
                            align: 'center'
                        }],
                    url: '<%=path%>/tbTradeManger/tbReqList/showFindPage.htm',
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


</script>
</body>
</html>