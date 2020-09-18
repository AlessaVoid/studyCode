<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
    <head>
        <title>首页</title>
        <%@include file="/common/common_list.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <script type="text/javascript" src="<%=path%>/libs/js/echarts.min.js"></script>
        <script type="text/javascript" src="<%=path%>/libs/js/index/indexData.js?ver=2"></script>
        <style>
            .data_left{
                width:70%;
            }
            .data_left .myChartDiv{
                width:100%;
                height:350px;
                margin:30px 0px;
            }

            .data_right1_top{
                width:30%;
                position:fixed;
                top:0px;
                right:0px;
                height:8%;
                background-color: #eeffed;
            }
            .data_right1{
                width:30%;
                position:fixed;
                top:0px;
                right:0px;
                overflow-y:auto;
                height:50%;
                background-color: #eeffed;
            }
            .data_right2_top{
                width:30%;
                position:fixed;
                top:50%;
                right:0px;
                height:8%;
                background-color: #eeffed;
            }
            .data_right2{
                width:30%;
                position:fixed;
                top:50%;
                right:0px;
                overflow-y:auto;
                height:50%;
                background-color: #eeffed;
            }
            .data_right1 table.dataTable, .data_right2 table.dataTable {
                table-layout: fixed;
                width: 100%;
                margin: 0 auto;
                clear: both;
                border-collapse: separate;
                border-spacing: 0;
            }
            .data_right table.dataTable, table.dataTable th, table.dataTable td {
                -webkit-box-sizing: content-box;
                box-sizing: content-box;
            }
            .data_right table.dataTable thead th, table.dataTable thead td {
                padding: 10px 0px;
                border-bottom: 1px solid #111;
                text-align: center;
            }
            .data_right table.dataTable, table.dataTable th, table.dataTable td {
                -webkit-box-sizing: content-box;
                box-sizing: content-box;
            }
            .data_right table.dataTable thead tr {
                background-color: #dedede;
            }
            .data_right table.dataTable tbody td {
                padding: 8px 10px;
                text-align: center;
                overflow: hidden;
                text-overflow:ellipsis;
                white-space: nowrap;
            }
            .data_right .c1{
                background-color: #a9ffc1;
            }
            .data_right .c2{
                background-color: #80c0e7;
            }
            .data_right .c3{
                background-color: #ffff00;
            }
            .data_right .c4{
                background-color: #FFB800;
            }
            .data_right .c5{
                background-color: #FF5722;
            }
        </style>
    </head>
    <body>
        <input type="hidden" id="hiddenOrganlevel" value="${organlevel}">

        <div class="data_left">
            <div align="right" style="margin-top: 10px;margin-right: 40px;">
                <span style="background-color: #eeffed">页面刷新时间：
                <input type="radio" id="time1" name="refreshTime" value="30" /><label for="time1" class="hand">30秒</label>
                <input type="radio" id="time2" name="refreshTime" value="60" /><label for="time2" class="hand">一分钟</label>
                <input type="radio" id="time3" name="refreshTime" value="300" /><label for="time3" class="hand">五分钟</label>
                <input type="radio" id="time4" name="refreshTime" value="10000000" checked="checked" /><label for="time4" class="hand">不刷新</label>
                    </span>
            </div>
        </div>

        <div class="data_left">
            <!--机构净增量（柱形图）-->
            <div class="myChartDiv" id="organIncrementBar"></div>
            <!--二级贷种净增量（柱形图）-->
            <div class="myChartDiv" id="loanIncrementBar"></div>
            <!--二级贷种净增量（折线图）-->
            <div class="myChartDiv" id="loanIncrementLine"></div>

<%--            <div class="myChartDiv" id="exampleChar"></div>--%>
        </div>
        <div class="data_right">

            <div class="data_right1">
                <div class="data_right1_top">
                    <table id ="plan" class="dataTable no-footer">
                        <thead>
                        <tr>
                            <th width="38%">预警名称</th>
                            <th width="22%" onclick="sortTableList1('planamount')">计划净增量<br>(亿元)</th>
                            <th width="22%" onclick="sortTableList1('realityamount')">实际净增量<br>(亿元)</th>
                            <th width="18%" onclick="sortTableList1('complete')">完成率</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <table id="tableList1" class="dataTable no-footer">
                    <thead>
                        <tr>
                            <th width="38%">预警名称</th>
                            <th width="22%" >计划净增量<br>(亿元)</th>
                            <th width="22%" >实际净增量<br>(亿元)</th>
                            <th width="18%" >完成率</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>


            <div class="data_right2">
                <div class="data_right2_top">
                    <table class="dataTable no-footer">
                        <thead>
                        <tr>
                            <th width="40%">预警名称</th>
                            <th width="60%" onclick="sortTableList2('realityamount')">实际净增量<br>(亿元)</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <table id="tableList2" class="dataTable no-footer">
                    <thead>
                        <tr>
                            <th width="40%">预警名称</th>
                            <th width="60%" >实际净增量<br>(亿元)</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>