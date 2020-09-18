<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta charset="UTF-8" />
    <title>信贷规模日报表</title>
    <%@include file="/common/common_edit.jsp" %>
    <link rel="stylesheet" href="${path}/libs/css/report/defaultTheme.css" />
    <link href="${path}/libs/css/report/report.css" rel="stylesheet"/>
    <script type="text/javascript" src="${path}/libs/js/jquery.fixedheadertable.js"></script>
    <style>
        th {
            text-align: center;
            position: relative;
        }
        th[class=first]:before {
            content: "";
            position: absolute;
            width: 1px;
            height: 212px;
            top: 0;
            left: 0;
            background-color: #000;
            display: block;
            transform: rotate(-82deg);
            transform-origin: top;
            -ms-transform: rotate(-82deg);
            -ms-transform-origin: top;
        }
        th[class=first]:after {
            content: "";
            position: absolute;
            width: 1px;
            height: 230px;
            top: 0;
            left: 0;
            background-color: #000;
            display: block;
            transform: rotate(-60deg);
            transform-origin: top;
            -ms-transform: rotate(-60deg);
            -ms-transform-origin: top;
        }
        th[class=first] .title1{
            position: absolute;
            top: 0px;
            right:3px;
        }
        th[class=first] .title2{
            position: absolute;
            bottom: 0px;
            right:3px;
        }
        th[class=first] .title3{
            position: absolute;
            bottom: 0px;
            left:10px;
        }
    </style>
</head>
<body>
<div class="box2_custom" boxType="box2" panelTitle="信贷规模日报表" id="searchPanel">
    <form action="${path}/report/reportCreditScaleDaily.htm?menuNo=RE-01" id="queryForm" method="post" onsubmit="οnsubmitForm();">
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>
                <td width="150px">统计日期：</td>
                <td width="400px">
                    <input type="text" name="statisticsDate" id="statisticsDate" class="date validate[length[0,10]]" dateFmt="yyyy-MM-dd"  value="${statisticsDate}"/>
                </td>
                <td>
                    <div align="center">
                        <button type="submit"><span class="icon_find">查询</span></button>
                        <button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
                        <button type="button" onclick="download()"><span class="icon_btn_down">下载</span></button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

<div>
    <div class="divider">
        <table class="fancyTable" id="myTable" cellpadding="0" cellspacing="0" style="table-layout:fixed;word-wrap:break-word;">
            <thead>
            <tr>
                <th style="width:200px" class="first"><span class="title1">贷种组合</span><br><span class="title2">贷款余额(万元)</span><br><span class="title3">机构</span></th>
                <c:forEach items="${combList}" var="comb">
                    <td style="width:100px" align="center">
                            ${comb.combname}
                    </td>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${organList}" var="organ">
                <tr>
                    <td> ${organ.organname }</td>
                    <c:forEach items="${combList}" var="comb">
                        <c:set var="baseInfo_key" value="${organ.thiscode}_${comb.combcode}" />
                        <c:set var="balance" value="${baseInfoMap[baseInfo_key]}" />
                        <c:choose>
                            <c:when test="${!empty balance}">
                                <td align="right"><fm:formatNumber maxFractionDigits="4" value="${balance*0.0001}"></fm:formatNumber></td>
                            </c:when>
                            <c:otherwise>
                                <td align="right">0</td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
<script>
    var sl, st;
    $(function(){
        $('#myTable').fixedHeaderTable({
            altClass: 'odd',
            fixedColumns: 1,
            width:'100%',
            height:$(window).height() - 69,
        });

        //解决火狐滚动条重置问题
        $("#myTable").closest(".fht-tbody").scroll(function() {
            sl = this.scrollLeft;
            st = this.scrollTop;
        });
    });

    //解决火狐滚动条重置问题
    function scrollBar(){
        var $div = $("#myTable").closest(".fht-tbody");
        $div.scrollTop(st).scrollLeft(sl).scroll();
    }

    //重置查询
    function resetSearch() {
        $("#queryForm")[0].reset();
    }

    //onsubmit
    function οnsubmitForm(){
        $("body").mask("正在查询……",null,true);
    }

    //下载
    function download() {
        //报表日期
        var statisticsDate = $("#statisticsDate").val();
        if (!statisticsDate) {
            top.Dialog.alert("报表日期不可为空");
            return;
        }
        location = "<%=path%>/report/reportCreditScaleDailyDownload.htm?statisticsDate=" + statisticsDate ;
    }


</script>
</html>
