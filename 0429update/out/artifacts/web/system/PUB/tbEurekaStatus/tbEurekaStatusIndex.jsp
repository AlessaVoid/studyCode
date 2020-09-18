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

                <td align="right">服务名：</td>
                <td>
                    <input type="text"  name="serverName" />
                </td>


                <td align="right">服务注册配置：</td>
                <td>
                    <select name="serverStatus">
                        <option value="">--请选择--</option>
                        <option value="0">注册</option>
                        <option value="1">不注册</option>
                    </select>
                </td>

                <td align="right">服务注册状态：</td>
                <td>
                    <select name="serverRegStatus">
                        <option value="">--请选择--</option>
                        <option value="0">已注册</option>
                        <option value="1">未注册</option>
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
                            display: '服务名',
                            name: 'serverName',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '服务注册配置',
                            name: 'serverStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" === value || 0 === value) {
                                    return "注册";
                                }else if ("1" === value || 1 === value) {
                                    return "不注册";
                                } else{
                                    return value;
                                }
                            }
                        },{
                            display: '服务注册状态',
                            name: 'serverRegStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" === value || 0 === value) {
                                    return "已注册";
                                }else if ("1" === value || 1 === value) {
                                    return "未注册";
                                } else{
                                    return value;
                                }
                            }
                        },{
                            display: '服务注册IP',
                            name: 'serverRegIp',
                            width: '60%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return getIP(value);
                            }
                        },
                        {
                            display: '最后更新时间',
                            name: 'updateTime',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return getTime(value);
                            }
                        }],
                    url: '<%=path%>/tbEurekaStatus/findPage.htm',
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

    function getIP(IPStr) {
        var result="";
        var ips=[];
        ips = IPStr.split(",");
        for (var i = 0; i < ips.length; i++) {
            var ip = ips[i];
            result = result+" - "+ip;
        }
        result= result.substr(3);
        return result;
    }

</script>
</body>
</html>