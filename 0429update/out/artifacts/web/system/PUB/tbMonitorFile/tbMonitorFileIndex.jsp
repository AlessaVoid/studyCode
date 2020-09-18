<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>对账管理</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>

<%--                <td align="right">产品代码：</td>--%>
<%--                <td>--%>
<%--                    <input type="text"  name="productCode" />--%>
<%--                </td>--%>


                <td align="right">文件类型：</td>
                <td>
                    <select name="fileType">
                        <option value="">--请选择--</option>
                        <option value="prodSync">产品同步</option>
                        <option value="check">对账</option>
                        <option value="organ">机构</option>
                        <option value="oper">柜员</option>
                        <option value="a3701">报表</option>
                    </select>
                </td>



                <td align="right">文件接收状态：</td>
                <td>
                    <select name="fileRecvStatus">
                        <option value="">--请选择--</option>
                        <option value="0">成功</option>
                        <option value="1">失败</option>
                    </select>
                </td>

                <td align="right">文件内容状态：</td>
                <td>
                    <select name="fileProcessStatus">
                        <option value="">--请选择--</option>
                        <option value="0">成功</option>
                        <option value="1">失败</option>
                    </select>
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
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '文件日期',
                            name: 'fileDate',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return getTime(value);
                            }
                        },
                        {
                            display: '交易系统',
                            name: 'fileSystemid',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '文件类型',
                            name: 'fileType',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("prodSync" === value ) {
                                    return "产品同步";
                                }else if ("check" === value ) {
                                    return "对账";
                                } else if ("organ" === value ) {
                                    return "机构";
                                } else if ("oper" === value ) {
                                    return "柜员";
                                }else if ("a3701" === value ) {
                                    return "报表";
                                }
                            }
                        },{
                            display: '文件路径',
                            name: 'filePath',
                            width: '20%',
                            align: 'center'
                        },
                        {
                            display: '文件接收时间',
                            name: 'fileRecvTime',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return getTime(value);
                            }
                        },{
                            display: '文件接收状态',
                            name: 'fileRecvStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" === value || 0 === value) {
                                    return "成功";
                                }else if ("1" === value || 1 === value) {
                                    return "失败";
                                } else{
                                    return value;
                                }
                            }
                        },{
                            display: '文件入库开始时间',
                            name: 'fileProcessStart',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return getTime(value);
                            }
                        },{
                            display: '文件入库结束时间',
                            name: 'fileProcessFinish',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return getTime(value);
                            }
                        },
                        {
                            display: '文件内容状态',
                            name: 'fileProcessStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" === value || 0 === value) {
                                    return "成功";
                                }else if ("1" === value || 1 === value) {
                                    return "失败";
                                } else{
                                    return value;
                                }
                            }
                        }],
                    url: '<%=path%>/tbMonitorFile/findPage.htm',
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


    function getTime(timeStr) {
        //14 yyyymmddHHMMss
        //8 yyyymmdd
        //6 hhmmss
        if (!timeStr || (timeStr.length != 14 && timeStr.length != 8 && timeStr.length != 6)) {
            return "";
        }
        if (timeStr.length == 6) {
            var hour = timeStr.substring(0, 2);
            var minute = timeStr.substring(2, 4);
            var secode = timeStr.substring(4, 6);
            return  hour+":"+minute+":"+secode;
        }

        var year = timeStr.substring(0, 4);
        var month = timeStr.substring(4, 6);
        var day = timeStr.substring(6, 8);
        if (timeStr.length == 8) {
            return year+"-"+month+"-"+day;
        }
        var hour = timeStr.substring(8, 10);
        var minute = timeStr.substring(10, 12);
        var secode = timeStr.substring(12, 14);
        return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+secode;
    }

</script>
</body>
</html>