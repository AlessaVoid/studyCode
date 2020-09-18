
function reqMyChart1(){
    initMyChart1({});
    $("#organIncrementBar").mask("正在查询……",null,true);
    $.ajax({
        type: "post",
        url: path + "/indexData/getIndexOrganBarInfo.htm",
        dataType: "json",
        success: function (result) {
            $("#organIncrementBar").unmask();
            if (result.success == true || result.success == "true") {
                var jsonData = result.data;
                if(jsonData){
                    initMyChart1(jsonData);
                }
            }else{
                top.Dialog.alert("机构净增量，请求异常");
            }
        },
        error: function (result) {
            $("#organIncrementBar").unmask();
        }
    });
}

function initMyChart1(jsonData){
    //动态展示x轴角度
    var mychart_xAxis_rotate = jsonData.length >= 10 ? 45 : 0;
    //机构-名称数组
    var mychart_xAxis_data = [];
    //机构-本月计划净增量数组
    var mychart_series_data1 = [];
    //机构-本月实际净增量数组
    var mychart_series_data2 = [];
    //机构-本月审批中数组
    var mychart_series_data3 = [];

    for(var i = 0; i < jsonData.length; i++){
        var data = jsonData[i];
        mychart_xAxis_data.push(data.organname);
        mychart_series_data1.push((data.planamount/100000000).toFixed(4));
        mychart_series_data2.push((data.realityamount/100000000).toFixed(4));
        mychart_series_data3.push((data.peinprogress/100000000).toFixed(4));
    }

    var myChart = echarts.init(document.getElementById('organIncrementBar'));
    var options={
        title:{
            text:'机构当月计划执行情况'
        },
        legend:{
            type:'scroll'
        },
        grid:{
            bottom:'100'
        },
        tooltip : {
            trigger : 'axis',
            showDelay : 0,
            axisPointer : {
                type : 'shadow'
            }
        },
        xAxis:{
            axisLabel:{
                rotate:mychart_xAxis_rotate,
                interval:0
            },
            name:'机构',
            data:mychart_xAxis_data
        },
        yAxis:{
            name:'净增量(亿元)'
        },
        series:[
            {
                name:'本月计划净增量',
                type:'bar',
                barMaxWidth:'100',
                data:mychart_series_data1
            },
            {
                name:'本月实际净增量',
                type:'bar',
                barMaxWidth:'100',
                data:mychart_series_data2
            },
            {
                name:'审批中',
                type:'bar',
                barMaxWidth:'100',
                data:mychart_series_data3
            }
        ]
    };
    // window.onresize = myChart.resize;
    myChart.setOption(options);
    window.addEventListener("resize",function(){
        myChart.resize();
    });
}

function reqMyChart2(){
    initMyChart2({});
    $("#loanIncrementBar").mask("正在查询……",null,true);
    $.ajax({
        type: "post",
        url: path + "/indexData/getIndexCombBarInfo.htm",
        dataType: "json",
        success: function (result) {
            $("#loanIncrementBar").unmask();
            if (result.success == true || result.success == "true") {
                var jsonData = result.data;
                if(jsonData){
                    initMyChart2(jsonData);
                }
            }else{
                top.Dialog.alert("二级贷种柱形图，请求异常");
            }
        },
        error: function (result) {
            $("#loanIncrementBar").unmask();
        }
    });
}

function initMyChart2(jsonData){

    //动态展示x轴角度
    var mychart_xAxis_rotate = jsonData.length >= 10 ? 45 : 0;
    //二级贷种-名称数组
    var mychart_xAxis_data = [];
    //二级贷种-本月计划净增量数组
    var mychart_series_data1 = [];
    //二级贷种-本月实际净增量数组
    var mychart_series_data2 = [];
    //二级贷种-审批中数组
    var mychart_series_data3 = [];

    for(var i = 0; i < jsonData.length; i++){
        var data = jsonData[i];
        mychart_xAxis_data.push(data.combname);
        mychart_series_data1.push((data.planamount/100000000).toFixed(4));
        mychart_series_data2.push((data.realityamount/100000000).toFixed(4));
        mychart_series_data3.push((data.peinprogress/100000000).toFixed(4));
    }

    var myChart = echarts.init(document.getElementById('loanIncrementBar'));
    var options={
        title:{
            text:'各贷种当月计划及净增量'
        },
        legend:{
            type:'scroll'
        },
        grid:{
            bottom:'100'
        },
        tooltip : {
            trigger : 'axis',
            showDelay : 0,
            axisPointer : {
                type : 'shadow'
            }
        },
        xAxis:{
            axisLabel:{
                rotate:mychart_xAxis_rotate,
                interval:0
            },
            name:'二级贷种',
            data:mychart_xAxis_data
        },
        yAxis:{
            name:'净增量(亿元)'
        },
        series:[
            {
                name:'本月计划净增量',
                type:'bar',
                barMaxWidth:'100',
                data:mychart_series_data1
            },
            {
                name:'本月实际净增量',
                type:'bar',
                barMaxWidth:'100',
                data:mychart_series_data2
            },
            {
                name:'审批中',
                type:'bar',
                barMaxWidth:'100',
                data:mychart_series_data3
            }
        ]
    };
    // window.onresize = myChart.resize;
    myChart.setOption(options);
    window.addEventListener("resize",function(){
        myChart.resize();
    });
}

function reqMyChart3(){
    initMyChart3({});
    $("#loanIncrementLine").mask("正在查询……",null,true);
    $.ajax({
        type: "post",
        url: path + "/indexData/getIndexCombLineInfo.htm",
        dataType: "json",
        success: function (result) {
            $("#loanIncrementLine").unmask();
            if (result.success == true || result.success == "true") {
                var jsonData = result.data;
                if(jsonData){
                    initMyChart3(jsonData);
                }
            }else{
                top.Dialog.alert("二级贷种折线图，请求异常");
            }
        },
        error: function (result) {
            $("#loanIncrementLine").unmask();
        }
    });
}

function initMyChart3(jsonData){
    //动态展示x轴角度
    var mychart_xAxis_rotate = 45;
    //日期数组
    var mychart_xAxis_data = mGetDate();
    //series参数初始化
    var mychart_series_data = [];

    //贷种本月计划净增量总量
    var  planamountCount = 0 ;

    for(var i = 0; i < jsonData.length; i++){
        var data = jsonData[i];
        //贷种名称
        var combname = data.combname;
        //贷种本月计划净增量
        var planamount = data.planamount;
        //贷种每日本月实际净增量数组
        var realityAmountArr = data.realityamount;

        planamountCount = accAdd(planamountCount, planamount);

        //贷种每日完成率
        var completeArr = [];
        for(var j = 0; j < mychart_xAxis_data.length; j++){
            if(planamount == 0){
                completeArr[j] = 0;
            }else{
                completeArr[j] = (realityAmountArr[j].amount / planamount * 100).toFixed(2);
            }
        }

        mychart_series_data.push({
            name: combname,
            type: 'line',
            data: completeArr
        });
    }

    /*---渲染完成率合计-----*/
    var combname = "完成率合计";
    var completeArr = [];
    for(var j = 0; j < mychart_xAxis_data.length; j++){
        if(planamountCount == 0){
            completeArr[j] = 0;
        }else{
            //各个贷种某天的实际净增量合计
            var realityAmountAccount = realityAmountAdd(j, jsonData);
            completeArr[j] = (realityAmountAccount / planamountCount * 100).toFixed(2);
        }
    }
    mychart_series_data.push({
        name: combname,
        type: 'line',
        data: completeArr
    });




    var myChart = echarts.init(document.getElementById('loanIncrementLine'));
    var options={
        title:{
            text:'各贷种当月计划完成率'
        },
        legend:{
            type:'scroll',
            width:'50%'
        },
        grid:{
            bottom:'100'
        },
        tooltip : {
            trigger : 'axis',
            formatter : function (params) {
                var html = params[0].name+"<br>";
                for (var i = 0; i < params.length; i++) {
                    //添加图例
                    html += '<span style="display: inline-block;margin-right: 5px;border-radius: 10px;width: 10px;height: 10px;background-color: '+params[i].color+';"></span>';
                    //添加‘%’
                    html += params[i].seriesName + ":" + params[i].value + "%<br>";
                }
                return html;
            }

        },
        xAxis:{
            axisLabel:{
                rotate:mychart_xAxis_rotate,
                interval:0
            },
            name:'日期',
            data:mychart_xAxis_data
        },
        yAxis:{
            name:'完成率(%)'
        },
        series: mychart_series_data
    };
    // window.onresize = myChart.resize;
    myChart.setOption(options);
    window.addEventListener("resize",function(){
        myChart.resize();
    });
}


var compare1 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.complete);
    var val2 = parseFloat(obj2.complete);
    if (val1 < val2) {
        return 1;
    } else if (val1 > val2) {
        return -1;
    } else {
        return 0;
    }
}


var compare2 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.realityamountABS);
    var val2 = parseFloat(obj2.realityamountABS);
    if (val1 < val2) {
        return 1;
    } else if (val1 > val2) {
        return -1;
    } else {
        return 0;
    }
}



//预警线表格1
var tableData1 = null;
function reqMyTable1(){
    $.ajax({
        type: "post",
        url: path + "/indexData/getIndexWarnCompleteInfo.htm",
        dataType: "json",
        success: function (result) {
            if (result.success == true || result.success == "true") {
                var jsonData = result.data;
                if(jsonData){
                    var newJsonData = [];
                    for(var i = 0; i < jsonData.length; i++){
                        var data = jsonData[i];
                        var planamount = data.planamount;
                        var realityamount = data.realityamount;
                        //完成率
                        var complete;
                        if(planamount == 0){
                            complete = '0.00';
                        }else{
                            complete = (realityamount / planamount * 100).toFixed(2);
                        }
                        data['complete'] = complete;
                        complete = parseFloat(complete);
                        if(complete <= 0 || (complete >= 0 && complete < data.warnoneline)){
                            data['class1'] = 'c0';
                        }else if(complete >= data.warnoneline && complete < data.warntwoline){
                            data['class1'] = 'c1';
                        }else if(complete >= data.warntwoline && complete < data.warnthreeline){
                            data['class1'] = 'c2';
                        }else if(complete >= data.warnthreeline && complete < data.warnfourline){
                            data['class1'] = 'c3';
                        }else if(complete >= data.warnfourline && complete < data.warnfiveline){
                            data['class1'] = 'c4';
                        }else if(complete >= data.warnfiveline){
                            data['class1'] = 'c5';
                        }
                        newJsonData.push(data);
                    }
                    tableData1 = newJsonData;
                    newJsonData = newJsonData.sort(compare1);
                    initMyTable1(newJsonData);
                }
            }else{
                top.Dialog.alert("预警线完成率，请求异常");
            }
        },
        error: function (result) {
        }
    });
}

function initMyTable1(jsonData){
    $("#tableList1 tbody").html("");
    var str = '';
    for(var i = 0; i < jsonData.length; i++){
        var data = jsonData[i];
        str += '<tr class="'+ data.class1 +'">';
        str += '<td title="'+ data.warnname +'">' + data.warnname + '</td>';
        str += '<td>' + parseFloat((data.planamount / 100000000).toFixed(4)) + '</td>';
        str += '<td>' + parseFloat((data.realityamount / 100000000).toFixed(4)) + '</td>';
        str += '<td>' + data.complete + '%</td>';
        str += '</tr>';
    }
    $("#tableList1 tbody").html(str);
}
//预警线表格
var tableData2 = null;
function reqMyTable2(){
    $.ajax({
        type: "post",
        url: path + "/indexData/getIndexWarnAbsInfo.htm",
        dataType: "json",
        success: function (result) {
            if (result.success == true || result.success == "true") {
                var jsonData = result.data;
                if(jsonData){
                    var newJsonData = [];
                    for(var i = 0; i < jsonData.length; i++){
                        var data = jsonData[i];
                        var realityamountABS = Math.abs(parseFloat((data.realityamount).toFixed(2))); //元 为单位
                        data['realityamountABS'] = realityamountABS;
                        if(realityamountABS <= 0 || (realityamountABS >= 0 && realityamountABS < data.warnoneline)){
                            data['class1'] = 'c0';
                        }else if(realityamountABS >= data.warnoneline && realityamountABS < data.warntwoline){
                            data['class1'] = 'c1';
                        }else if(realityamountABS >= data.warntwoline && realityamountABS < data.warnthreeline){
                            data['class1'] = 'c2';
                        }else if(realityamountABS >= data.warnthreeline && realityamountABS < data.warnfourline){
                            data['class1'] = 'c3';
                        }else if(realityamountABS >= data.warnfourline && realityamountABS < data.warnfiveline){
                            data['class1'] = 'c4';
                        }else if(realityamountABS >= data.warnfiveline){
                            data['class1'] = 'c5';
                        }

                        newJsonData.push(data);
                    }
                    tableData2 = newJsonData;
                    newJsonData = newJsonData.sort(compare2);
                    initMyTable2(newJsonData);
                }
            }else{
                top.Dialog.alert("预警线绝对值，请求异常");
            }
        },
        error: function (result) {
        }
    });
}

function initMyTable2(jsonData){
    $("#tableList2 tbody").html("");
    var str = '';
    for(var i = 0; i < jsonData.length; i++){
        var data = jsonData[i];
        str += '<tr class="'+ data.class1 +'">';
        str += '<td title="'+ data.warnname +'">' + data.warnname + '</td>';
        str += '<td>' + parseFloat((data.realityamount / 100000000).toFixed(4)) + '</td>';
        str += '</tr>';
    }
    $("#tableList2 tbody").html(str);
}

//获取当前月份所有日期
function mGetDate(){
    var dateArr = [];
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth();
    var currentMonth = month + 1;
    var d = new Date(year, currentMonth, 0);
    for(var i = 0; i < d.getDate(); i++){
        dateArr.push(currentMonth + '月' + (i+1) + '日');
    }
    return dateArr;
}


//js加法计算
function accAdd(arg1, arg2) {
            var r1, r2, m;
            try {
                r1 = arg1.toString().split(".")[1].length
            } catch (e) {
                r1 = 0
            }
            try {
                r2 = arg2.toString().split(".")[1].length
            } catch (e) {
                r2 = 0
            }
            m = Math.pow(10, Math.max(r1, r2))
            return transFormat((accMul(arg1, m) + accMul(arg2, m)) / m)
        }


 function transFormat(amount) {
        var amountStr = String(amount);
        if (amountStr.indexOf("-") > 0) {
            amountStr = '0' + String(Number(amountStr) + 1).substr(1);
        }else if(amountStr.indexOf("-") == 0){

            if(amountStr.substr(1).indexOf("-") > 0){
                amountStr = '-0' + String(Number(amountStr.substr(1)) + 1).substr(1);
            }
        }
        return amountStr;
    }



//js计算乘法
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

//各个贷种某天的实际净增量合计
function realityAmountAdd(i,jsonData) {
    var realityAmountAccount =0 ;
    for(var j = 0; j < jsonData.length; j++){
        var data = jsonData[j];
        //贷种每日本月实际净增量数组
        var realityAmountArr = data.realityamount;
        var realityAmount = realityAmountArr[i].amount;
        realityAmountAccount = accAdd(realityAmountAccount, realityAmount);
    }
    return realityAmountAccount;
}


/*------------预警线点击排序----↓↓↓↓↓↓↓↓↓↓↓--------*/
//计划净增量
var table1_planamount = 1;
var table1_realityamount = 1;
var table1_complete = -1;

var table2_realityamount = -1;



//预警线表一排序
function sortTableList1(str) {
    if (str == 'planamount') {
        if (table1_planamount == 1) {
            tableData1 = tableData1.sort(comparePlanamount1);
            initMyTable1(tableData1);
        } else {
            tableData1 = tableData1.sort(comparePlanamount2);
            initMyTable1(tableData1);
        }
        table1_planamount = table1_planamount * -1;
    } else if (str == 'realityamount') {
        if (table1_realityamount == 1) {
            tableData1 = tableData1.sort(compareRealityamount1);
            initMyTable1(tableData1);
        } else {
            tableData1 = tableData1.sort(compareRealityamount2);
            initMyTable1(tableData1);
        }
        table1_realityamount = table1_realityamount * -1;
    }else if (str == 'complete') {
        if (table1_complete == 1) {
            tableData1 = tableData1.sort(compareComplete1);
            initMyTable1(tableData1);
        } else {
            tableData1 = tableData1.sort(compareComplete2);
            initMyTable1(tableData1);
        }
        table1_complete = table1_complete * -1;
    }
}

//预警线表二排序
function sortTableList2(str) {
     if (str == 'realityamount') {
        if (table2_realityamount == 1) {
            tableData2 = tableData2.sort(compareRealityamount1);
            initMyTable2(tableData2);
        } else {
            tableData2 = tableData2.sort(compareRealityamount2);
            initMyTable2(tableData2);
        }
         table2_realityamount = table2_realityamount * -1;
    }
}


//计划净增量排序
var comparePlanamount1 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.planamount);
    var val2 = parseFloat(obj2.planamount);
    if (val1 < val2) {
        return 1;
    } else if (val1 > val2) {
        return -1;
    } else {
        return 0;
    }
}
var comparePlanamount2 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.planamount);
    var val2 = parseFloat(obj2.planamount);
    if (val1 < val2) {
        return -1;
    } else if (val1 > val2) {
        return 1;
    } else {
        return 0;
    }
}

//实际净增量排序
var compareRealityamount1 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.realityamount);
    var val2 = parseFloat(obj2.realityamount);

    if (val1 < val2) {
        return 1;
    } else if (val1 > val2) {
        return -1;
    } else {
        return 0;
    }
}
var compareRealityamount2 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.realityamount);
    var val2 = parseFloat(obj2.realityamount);
    if (val1 < val2) {
        return -1;
    } else if (val1 > val2) {
        return 1;
    } else {
        return 0;
    }
}

//完成率排序
var compareComplete1 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.complete);
    var val2 = parseFloat(obj2.complete);
    if (val1 < val2) {
        return 1;
    } else if (val1 > val2) {
        return -1;
    } else {
        return 0;
    }
}
var compareComplete2 = function (obj1, obj2) {
    var val1 = parseFloat(obj1.complete);
    var val2 = parseFloat(obj2.complete);
    if (val1 < val2) {
        return -1;
    } else if (val1 > val2) {
        return 1;
    } else {
        return 0;
    }
}


/*------------预警线点击排序----↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑-------*/

//加载首页图
function loadChartAndTable(){
    reqMyChart1();
    reqMyChart2();
    reqMyChart3();

    reqMyTable1();
    reqMyTable2();
}

//定时刷新
var set1  = null;
function refresh() {
    var time = $("input:radio[name=refreshTime]").filter("[checked]").val()*1000;
    if (set1 != null) {
        clearInterval(set1);
    }
    loadChartAndTable();
    set1 = setInterval(refresh, time);
}


$(function(){

    //加载首页图
    loadChartAndTable();

    //定时刷新首页图和预警线
    refresh();
    $("input:radio[name=refreshTime]").change(function () {
        refresh();
    });
});

