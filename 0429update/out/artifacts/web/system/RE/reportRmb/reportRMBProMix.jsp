<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta charset="UTF-8" />
    <title>人民币贷款情况报表管理 - 产品结构报表</title>
    <%@include file="/common/common_edit.jsp" %>
    <link rel="stylesheet" href="${path}/libs/css/report/defaultTheme.css" />
    <link href="${path}/libs/css/report/report.css" rel="stylesheet"/>
    <script type="text/javascript" src="${path}/libs/js/jquery.fixedheadertable.js"></script>
    <style>
        .bigTitle{
            padding: 10px;
            font-size: 14px;
            font-weight: 600;
            color: red;
        }
        #first{
            vertical-align: middle;
        }
    </style>
</head>
<body>
    <div>
        <div class="divider">
            <div class="bigTitle">人民币贷款情况报表管理 - 产品结构报表</div>
            <table class="fancyTable" id="myTable" cellpadding="0" cellspacing="0" style="table-layout:fixed;word-wrap:break-word;">
                <thead>
                    <tr>
                        <th align="center" style="width:200px" rowspan="2" id="first">产品</th>
                        <th align="center" style="width:300px" colspan="3">余额</th>
                        <th align="center" style="width:1100px" colspan="10">年度</th>
                        <th align="center" style="width:1200px" colspan="11">季度</th>
                        <th align="center" style="width:1300px" colspan="13">月度</th>
                    </tr>
                    <tr>
                        <!-- 余额 -->
                        <td style="width:100px" align="center">余额</td>
                        <td style="width:100px" align="center">余额占比</td>
                        <td style="width:100px" align="center">比年初</td>
                        <!-- 年度 -->
                        <td style="width:100px" align="center">年计划</td>
                        <td style="width:100px" align="center">年净增</td>
                        <td style="width:100px" align="center">年内已到期量</td>
                        <td style="width:100px" align="center">年内预计到期量</td>
                        <td style="width:100px" align="center">年增占比</td>
                        <td style="width:100px" align="center">年增同比</td>
                        <td style="width:100px" align="center">年计划完成率</td>
                        <td style="width:100px" align="center">年计划完成率同比</td>
                        <td style="width:100px" align="center">年增速</td>
                        <td style="width:100px" align="center">年增速同比</td>
                        <!-- 季度 -->
                        <td style="width:100px" align="center">季计划</td>
                        <td style="width:100px" align="center">季净增</td>
                        <td style="width:100px" align="center">季内已到期量</td>
                        <td style="width:100px" align="center">季内预计到期量</td>
                        <td style="width:100px" align="center">季增占比</td>
                        <td style="width:100px" align="center">季增同比</td>
                        <td style="width:100px" align="center">季增环比</td>
                        <td style="width:100px" align="center">季计划完成率</td>
                        <td style="width:100px" align="center">季增速</td>
                        <td style="width:100px" align="center">季增速同比</td>
                        <td style="width:100px" align="center">季增速环比</td>
                        <!-- 月度 -->
                        <td style="width:100px" align="center">月计划</td>
                        <td style="width:100px" align="center">月净增</td>
                        <td style="width:100px" align="center">超计划</td>
                        <td style="width:100px" align="center">月内已到期量</td>
                        <td style="width:100px" align="center">月内预计到期量</td>
                        <td style="width:100px" align="center">月增占比</td>
                        <td style="width:100px" align="center">月增同比</td>
                        <td style="width:100px" align="center">月增环比</td>
                        <td style="width:100px" align="center">月计划完成率</td>
                        <td style="width:100px" align="center">月增速</td>
                        <td style="width:100px" align="center">周净增</td>
                        <td style="width:100px" align="center">周增环比</td>
                        <td style="width:100px" align="center" class="lastTd">日净增</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${loanTypeList}" var="loanType">
                        <tr>
                            <td> ${loanType.alias }</td>
                            <c:forEach var="i" begin="1" end="37" varStatus="status">
                                <td align="right">  </td>  <!-- TODO -->
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
            height:$(window).height() - 44,
        });

        //解决火狐滚动条重置问题
        $("#myTable").closest(".fht-tbody").scroll(function() {
            sl = this.scrollLeft;
            st = this.scrollTop;
        });

        //最后一个单元格，增加一个滚动条的宽度
        var scrollbarWidth = getScrollbarWidth();
        $(".lastTd").css("width",100 + scrollbarWidth);
    });

    //解决火狐滚动条重置问题
    function scrollBar(){
        var $div = $("#myTable").closest(".fht-tbody");
        $div.scrollTop(st).scrollLeft(sl).scroll();
    }

    //获取滚动条宽度
    function getScrollbarWidth() {
        var odiv = document.createElement('div'),
            styles = {
                width: '100px',
                height: '100px',
                overflowY: 'scroll'
            }, i, scrollbarWidth;
        for (i in styles) odiv.style[i] = styles[i];
        document.body.appendChild(odiv);
        scrollbarWidth = odiv.offsetWidth - odiv.clientWidth;
        odiv.remove();
        return scrollbarWidth;
    }
</script>
</html>
