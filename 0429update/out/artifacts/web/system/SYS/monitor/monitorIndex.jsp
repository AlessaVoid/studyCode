<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>监控页</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/highcharts.js"></script>
    <style type="text/css">
        .chart {width: 50%;float: left;height: 300px;}
        .innerchart {width: 95%;height: 100%;margin: 0 auto;}
        .errorDiv{width: 100%;color: red;text-align: center;}
    </style>
</head>
<body>
    <div id="errorDiv" class="errorDiv">123</div>
        <div id="inform"></div>
    <div id="data"></div>
</body>
<script>
    var serieses = {};
    var totalNumber = 1000;
    var activity = [];
    var flag = false;
    var nowtime={};
    var grid = null;

    function initForm() {
        top.Dialog.close();
        grid = $("#inform").quiGrid(
        {
            columns: [
                {
                    display: '服务器名称',
                    name: 'name',
                    width: '10%',
                    align: 'center'
                }, {
                    display: 'IP',
                    name: 'ip',
                    width: '8%',
                    align: 'center'
                },{
                    display: '端口',
                    name: 'port',
                    width: '8%',
                    align: 'center'
                },  {
                    display: '类型',
                    name: 'type',
                    width: '8%',
                    align: 'center'
                },{
                    display: '状态',
                    name: 'reset',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        if ("1" === value || 1 === value) {
                            return "正常";
                        } else if ("2" === value || 2 === value) {
                            return "<span style='color:red'>监控未连接</span>";
                        }
                    }
                },{
                    display: '当天处理数量',
                    name: 'todayDealNumber',
                    width: '9%',
                    align: 'center'
                },
                {
                    display: '当天处理数量(成功)',
                    name: 'todayDealSuccessNumber',
                    width: '9%',
                    align: 'center'
                },
                {
                    display: '总处理数量',
                    name: 'totalDealNumber',
                    width: '9%',
                    align: 'center'
                },
                {
                    display: '总处理数量(成功)',
                    name: 'totalDealSuccessNumber',
                    width: '9%',
                    align: 'center'
                },
                {
                    display: '总超时数量',
                    name: 'totalTimeoutNumber',
                    width: '9%',
                    align: 'center'
                },
                {
                    display: '运行时长',
                    name: 'runTime',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        return Math.floor(value/24) + "天" + (parseInt(value)%24) + "时";
                    }
                }],
            sortName: '',
            rownumbers: true,
            checkbox: false,
            width: "100%",
            usePager:false,
            headerRowHeight:26
        });
    }

    function showData() {
        $('#data').html("");
        addChart("TPS（次/s）","tps",0, iniserieses("tps", "次/s" ),0);
        addChart("平均处理时间（μs）","averageDealTime",1, iniserieses("averageDealTime", "μs" ));
        addChart("最大处理时间（μs）","maxDealTime",0, iniserieses("maxDealTime", "μs" ));
        addChart("处理超时数量","timeoutNumber",0, iniserieses("timeoutNumber", "" ));
        addChart("交易成功率（%）","successRate",2, iniserieses("successRate", "" ));
    }

    function iniserieses(name, unit) {
        var ser = [];
        for (var i = 0; i < activity.length; i++) {
            ser[i] = {};
            ser[i].data = [];
            for (var j = 0; j < activity[i].data.length; j++) {
                ser[i].data.push({
                    x : activity[i].data[j].time,
                    y : activity[i].data[j][name]
                });
            }
            ser[i].name = activity[i].name;
            ser[i].type = activity[i].type;
            ser[i].color = Highcharts.getOptions().colors[i];
            ser[i].fillOpacity = 0.3;
            ser[i].tooltip = {
                valueSuffix : '  ' + unit
            }
        }
        return ser;
    }

    function addChart(title,name,n,ser,min) {
        var outchart = $('<div  class="chart"></div>').appendTo('#data');
        $('<div  class="innerchart"></div>').appendTo(outchart).highcharts({
            chart : {
                spacingTop : 20,
                spacingBottom : 10,
                events : {
                    load : function() {
                        serieses[name] = this.series;
                    }
                }
            },
            title : {
                y: 15,
                text : "系统"+title+"图表",
                margin:40
            },
            credits : {
                enabled : false
            },
            xAxis : {
                type : 'datetime',
                tickPixelInterval : 150
            },
            legend : {
                align : 'right',
                verticalAlign : 'top',
                y : 20,
                x : 0,
                floating : true
            },
            plotOptions: {
                series: {
                    marker: {
                        enabled: false
                    }
                }
            },
            yAxis : {
                title : {
                    text : title
                },
                lineWidth : 1,
                labels : {
                    align : 'left',
                    x : 3,
                    y : 16,
                    format : '{value:.,0f}'
                },
                min:min
            },
            tooltip : {
                formatter : function() {
                    return Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',this.x) + '<br/>' + Highcharts.numberFormat(this.y, n);
                }
            },
            series : ser
        });
    }

    function loadData() {
        $("#errorDiv").html('');
        $.ajax({
            method : 'POST',
            url : path + "/webMonitor/selectAll.htm",
            async : false,
            dataType : 'json',
            success : function(result) {
                if(result.code == '200'){
                    var data = result.data;
                    grid.loadData(data);
                    if(data.total > 0){
                        formateData(data.rows);
                    }
                }
            },
            error : function() {
                // $("#errorDiv").html('数据加载失败！');
            }
        });
    };

    function formateData(data) {
        if (flag == false) {
            flag = true;
            for (var i = 0; i < data.length; i++) {
                var chart = null;
                for (var j = 0; j < activity.legnth; j++) {
                    if (activity[j].name == (data[i].name)) {
                        chart = activity[j];
                    }
                }
                if (chart == null) {
                    chart = {
                        "name" : data[i].name,
                        "data" : [],
                        "type" : "line",
                        "valueDecimals" : 1
                    }
                    activity[activity.length] = chart;
                }
                chart.data[chart.data.length] = data[i];
                nowtime[data[i].name]={"time":data[i].time};
            }
            showData();
        } else {
            seriesesaddPoint(data);
        }
    }

    function seriesesaddPoint(data) {
        for (var i = 0; i < data.length; i++) {
            var keys=["maxDealTime","averageDealTime","timeoutNumber", "tps","successRate"];
            var monitor=data[i];
            var time=monitor.time;
            var name=monitor.name;
            if(nowtime[name]!=null&&nowtime[name].time!=time){
                for(var j=0;j<keys.length;j++){
                    var key=keys[j];
                    for (var k = 0; k < serieses[key].length; k++) {
                        if (serieses[key][k].name ==name) {
                            serieses[key][k].addPoint([ monitor.time, monitor[key] ],
                                true, serieses[key][k].data.length > totalNumber);
                        }
                    }
                }
                nowtime[name].time=time;
            }
        }
    }


    $(function() {
        Highcharts.setOptions({
            global : {
                useUTC : false
            }
        });
        initForm();
        loadData();
        window.setInterval(loadData, 5000);
    });
</script>
</html>