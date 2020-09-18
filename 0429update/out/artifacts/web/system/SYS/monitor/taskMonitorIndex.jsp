<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>任务监控页</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/highcharts.js"></script>
    <style type="text/css">
        .tipDiv{
            width: 100%;
            font-weight: 600;
            font-size: 14px;
            padding: 2px 2px;
            color: red;
        }
    </style>
</head>
<body>
    <div id="errorDiv" class="tipDiv">日终日期：<span id="dateSpan"></span></div>
    <div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
        <div id="inform"></div>
    </div>
</body>
<script>
    var grid = null;
    function initForm() {
        grid = $("#inform").quiGrid(
            {
                columns: [
                    {
                        display: '任务名称',
                        name: 'taskname',
                        width: '20%',
                        align: 'center'
                    }, {
                        display: '任务执行情况',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if(typeof(rowdata.taskstatus) == 'undefined'){
                                return "未执行";
                            }else{
                                return "已执行";
                            }
                        }
                    },{
                        display: '任务状态',
                        name: 'taskstatus',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if(typeof(rowdata.taskstatus) == 'undefined'){
                                return "-";
                            }else{
                                if(rowdata.taskstatus == '0'){
                                    return "成功";
                                }else if(rowdata.taskstatus == '1'){
                                    return "运行中";
                                }else if(rowdata.taskstatus == '2'){
                                    return "失败";
                                }
                            }
                        }
                    },  {
                        display: '任务开始时间',
                        name: 'starttime',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if(typeof(rowdata.taskstatus) == 'undefined'){
                                return "-";
                            }else{
                                return value;
                            }
                        }
                    },{
                        display: '任务结束时间',
                        name: 'endtime',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if(typeof(rowdata.taskstatus) == 'undefined'){
                                return "-";
                            }else{
                                return value;
                            }
                        }
                    }],
                sortName: '',
                rownumbers: true,
                checkbox: false,
                width: "100%",
                height: '100%',
                usePager:false,
                headerRowHeight:26
            });
    }

    function loadData() {
        $.ajax({
            method : 'POST',
            url : path + "/webTaskMonitor/selectAll.htm",
            async : false,
            dataType : 'json',
            success : function(result) {
                if(result){
                    if(result.code == '200'){
                        var data = result.data;
                        var list = data.list;
                        var date = data.date;

                        //日终日期
                        $("#dateSpan").html(date);
                        //加载列表
                        grid.loadData(list);
                    }
                }
            },
            error : function() {
                // top.Dialog.alert("数据加载失败！",null,null,null,3)
            }
        });
    };

    $(function() {
        initForm();
        loadData();
        window.setInterval(loadData, 300000);
    });
</script>
</html>