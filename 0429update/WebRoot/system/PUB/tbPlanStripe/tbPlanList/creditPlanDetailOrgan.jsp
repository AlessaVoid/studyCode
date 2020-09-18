<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/stepper.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <script type="text/javascript" src="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <title>条线计划详情页面</title>
</head>
<body>

<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>
                <td>所属月份：</td>
                <td width="20">
<%--                    <input name="planMonth" id="planMonth" type="text"/>--%>
                    <input name="planMonth" type="text" id="planMonth" class="input-small inline form_datetime" style="width: 160px;" />

                <%--                    <select id="planMonth" name="planMonth">--%>
<%--                        <option value="">---请选择---</option>--%>
<%--                    </select>--%>
                </td>

                <td>
                    <div align="center">
                        <button type="button" target="_self" onclick="searchHandler()"><span class="icon_find">查询</span></button>
                        <button type="button" target="_self" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

<form id="form1" >
    <div id="scrollContent" class="border_gray" style="height: 610px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150"><div style="width: 150px">所属月份</div> </td>
                <c:forEach items="${combList}" var="comb">
                    <td width="20%">
                        <div style="width: 150px">${comb.combname}</div>

                    </td>
                </c:forEach>
                <td trueWidth="150"><div style="width: 150px">总金额</div></td>
                <td trueWidth="150"><div style="width: 150px">单位</div> </td>
            </tr>
            <c:forEach items="${tbPlans}" var="plan">
                <tr>
                    <td>
                        <input id="${plan.planMonth}" class="planMonth" type="hidden"/>
                            ${plan.planMonth }
                    </td>
                    <c:forEach items="${combList}" var="comb">
                        <c:set var="plan_key" value="${plan.planMonth}_${comb.combcode}" />
                        <c:set var="planDetail" value="${planMap[plan_key]}" />
                        <td>
                            <input name="${plan.planMonth}_${comb.combcode}" class="${plan.planMonth}_amount" value="${planDetail.pdAmount}" type="hidden" id="${plan.planMonth }_${comb.combcode}" readOnly="readOnly"/>
                            <c:if test="${empty planDetail.pdAmount }">
                                0
                            </c:if>
                            <c:if test="${!empty planDetail.pdAmount}">
                                <fm:formatNumber maxFractionDigits="10" value="${planDetail.pdAmount}"/>
<%--                                ${planDetail.pdAmount}--%>
                            </c:if>
                        </td>
                    </c:forEach>
                    <td>
                        <span name="${plan.planMonth}_amountCount">0</span>
                    </td>
                    <td>
                        <span>
                            <c:if test="${plan.planUnit ==1}">
                                万元
                            </c:if>
                            <c:if test="${plan.planUnit ==2}">
                                亿元
                            </c:if>
                        </span>
                    </td>


                </tr>
            </c:forEach>
        </table>

    </div>
</form>
</body>
<style>
    input:disabled{
        opacity: 1;
        -webkit-text-fill-color: black;
    }
</style>
<script>


    $(function(){
        //日期选择框
        $('#planMonth').datetimepicker({
            format: 'yyyymm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
            forceParse: false,
            language: 'zh-CN'
        });
        $('#planMonth').datetimepicker('update', new Date());
        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(1, 0, 1, 0);
        // ajaxMonth();
        amountFinal();
    })

    //js计算乘法
    function accMul(arg1, arg2)
    {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    //JS计算除法
    function accDiv(arg1, arg2)
    {
        var t1 = 0, t2 = 0, r1, r2;
        try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
        try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""))
            r2 = Number(arg2.toString().replace(".", ""))
            return (r1 / r2) * pow(10, t2 - t1);
        }
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
    //计算计划总金额
    function amountFinal() {
        var planMonthList = $(".planMonth");
        $(planMonthList).each(function () {

            var id = $(this).attr('id');
            var initName = id + "_amount";
            var finalName = id + "_amountCount";
            var queryInitName = "."+initName;
            var planMonthAmountList = $(queryInitName);
            var planMonthAmountCount = 0;
            $(planMonthAmountList).each(function () {
                planMonthAmountCount = accAdd(planMonthAmountCount, this.value);
            });
            $('span[name=' + finalName + ']').html(planMonthAmountCount);
        });
    }

    //获取所属月份下拉数据
    function ajaxMonth(){
        $.post("<%=path%>/creditPlanStripe/findTradeParam.htm", {}, function (result) {
            if(result){
                result.list.unshift({value:"", key:"--请选择--"});
                $("#planMonth").data("data", result);
                $("#planMonth").render();
            }
        }, "json");
    }

    //查询
    function searchHandler() {
        var planMonth = $("#planMonth").val();
        $('#page_PUB07-06', window.parent.document).attr("src", "<%=path%>/creditPlanStripe/toPlanDetail.htm?menuNo=PUB-07-06&planMonth=" + planMonth);
    }

    //重置查询
    function resetSearch() {
        $("#queryForm")[0].reset();
        $("#planMonth").render();
        $('#search').click();
    }


</script>

</html>