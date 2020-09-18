<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>对账管理</title>
<%--    <%@include file="/common/common_edit.jsp" %>--%>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>


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

                <td align="right">借据状态：</td>
                <td>
                    <select name="moSvrType">
                        <option value="">--请选择--</option>
                        <option value="ap">采集</option>
                        <option value="ctrl">管控</option>
                        <option value="monitor">监控</option>
                        <option value="batch">批处理</option>
                        <option value="web">WEB</option>
                    </select>
                </td>


                <td align="right">程序运行状态：</td>
                <td>
                    <select name="moSvrRunStatus">
                        <option value="">--请选择--</option>
                        <option value="0">运行中</option>
                        <option value="1">未运行</option>
                        <option value="2">不适用</option>
                    </select>
                </td>

<%--                <td align="right">程序端口状态：</td>--%>
<%--                <td>--%>
<%--                    <select name="moSvrPortStatus">--%>
<%--                        <option value="">--请选择--</option>--%>
<%--                        <option value="0">启用</option>--%>
<%--                        <option value="1">未启用</option>--%>
<%--                        <option value="2">不适用</option>--%>
<%--                    </select>--%>
<%--                </td>--%>


                <td align="right">页面刷新时间(分钟)：</td>
                <td>
                    <select id="flushTime" name="flushTime">
                        <option value="1">1</option>
                        <option value="2" selected>2</option>
                        <option value="5">5</option>
                        <option value="10">10</option>
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

    <input type="hidden" id="monitorList" name="monitorList" value="${fn:length(monitorList)}"/>
    <c:if test="${!empty monitorList }">

    <div align="center">
        <c:forEach items="${monitorList}" var="monitor">
            <span >
                <span class="red font_bold">
                    <c:if test="${monitor.mosvrtype == 'ap' }">
                        采集
                    </c:if>
                    <c:if test="${monitor.mosvrtype == 'ctrl' }">
                        管控
                    </c:if>
                    <c:if test="${monitor.mosvrtype == 'monitor' }">
                        监控
                    </c:if>
                    <c:if test="${monitor.mosvrtype == 'batch' }">
                        批处理
                    </c:if>
                    <c:if test="${monitor.mosvrtype == 'web' }">
                        WEB
                    </c:if>
                </span>
                服务器一共有
                <span class="red font_bold">${monitor.allcount }</span>
                台,运行中的服务器有
                <span class="red font_bold">${monitor.runcount }</span>
                台。&nbsp;&nbsp;&nbsp;
            </span>
        </c:forEach>
    </div>

    </c:if>
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
                            display: 'IP地址',
                            name: 'moIp',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '服务节点编号',
                            name: 'moSvrId',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '服务类型',
                            name: 'moSvrType',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("ap" === value ) {
                                    return "采集";
                                }else if ("ctrl" === value ) {
                                    return "管控";
                                } else if ("monitor" === value ) {
                                    return "监控";
                                } else if ("batch" === value ) {
                                    return "批处理";
                                } else if ("web" === value ) {
                                    return "WEB";
                                }
                            }
                        },{
                            display: '服务名',
                            name: 'moSvrName',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '程序路径',
                            name: 'moSvrPath',
                            width: '20%',
                            align: 'center'
                        },{
                            display: '程序端口',
                            name: 'moSvrPort',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '程序运行状态',
                            name: 'moSvrRunStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                var result = "";
                                if ("0" === value || 0 === value) {
                                    return "运行中";
                                }else if ("1" === value || 1 === value) {
                                    // return "未运行";
                                    return  '<div class="red">' + "未运行"+ '</div>';
                                } else if ("2" === value || 2 === value) {
                                    return "不适用";
                                }else{
                                    return value;
                                }
                            }
                        },{
                            display: '程序端口状态',
                            name: 'moSvrPortStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                var result="";
                                var status=[];
                                var resultStatus = 1;
                                status = value.split(",");
                                for (var i = 0; i < status.length; i++) {
                                    var sta = status[i];
                                    if ("0" === sta || 0 === sta) {
                                        result= result+",启用";
                                    }else if ("1" === sta || 1 === sta) {
                                        result= result+",未启用";
                                        resultStatus = 2;
                                    } else if ("2" === sta || 2 === sta) {
                                        result= result+ ",不适用";
                                    }else{
                                        result= result+","+ sta;
                                    }
                                }
                                result= result.substr(1);
                                if (resultStatus === 1) {
                                    return result;
                                } else {
                                    return '<div class="red">' + result+ '</div>';
                                }
                            }
                        },
                        {
                            display: '最后采集时间',
                            name: 'moCollectTime',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                var status = timeRed(value);
                                var time = getTime(value);
                                if (status === 1) {
                                    return time;
                                } else {
                                    return '<div class="red">' + time+ '</div>';
                                }

                            }
                        }],
                    url: '<%=path%>/tbMonitor/findPage.htm',
                    sortName: '',
                    rownumbers: true,
                    checkbox: true,
                    height: '100%',
                    width: "100%",
                    pageSize: 100,
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


    //更新
    function onUpdate() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/tbMonitor/updateUI.htm?moIp=" + rows[0].moIp + "&moSvrId=" + rows[0].moSvrId, "修改", 600, 400);
        }
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

    //判断时间串到现在的时间是否需要标红
    function timeRed(timeStr) {
        if (!timeStr || (timeStr.length != 14 )) {
            return 1;
        }

        var year = timeStr.substring(0, 4);
        var month = timeStr.substring(4, 6);
        var day = timeStr.substring(6, 8);
        var hour = timeStr.substring(8, 10);
        var minute = timeStr.substring(10, 12);
        var secode = timeStr.substring(12, 14);
        var moTime =  new Date(year+"/"+month+"/"+day+" "+hour+":"+minute+":"+secode).getTime();

        var time = new Date().getTime();
        //比较毫秒值，超过5分钟的就标红
        if (time - moTime > 300000) {
            return 2;
        }
        return 1;
    }

    //-------自动刷新页面begin
    var t=2000*60;
    function flushTime() {
        var flushTime = $("#flushTime").val();
        t = flushTime*1000*60;
        flush();
    }

    var set1 = setInterval(flush, t);

    function flush() {
        clearInterval(set1);
        grid.loadData();
        set1 = setInterval(flush, t);
    }
    //-------自动刷新页面end

$(function () {
    //绑定刷新页面的时间
    $("#flushTime").change(function () {
        flushTime();
    });
})




</script>
</body>
</html>