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
    <title>信贷计划详情页面</title>
</head>
<body>
<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="planMonth" name="planMonth"  type="hidden" value="${plan.planMonth}"> </input>
                ${plan.planMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="planUnit" name="planUnit"  type="hidden" value="${plan.planUnit}"> </input>
                <c:if test="${plan.planUnit ==1}">
                    万元
                </c:if>
                <c:if test="${plan.planUnit ==2}">
                    亿元
                </c:if>

            </td>


            <%--<td align="right">--%>
            <%--本月计划净增量（亿元）：--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<input type="hidden" id="increment" name="increment" value="${plan.planIncrement}"/>--%>
            <%--${plan.planIncrement}--%>
            <%--</td>--%>
            <input type="hidden" id="increment" name="increment" value="${plan.planIncrement}"/>


            <td align="right">
                管控模式：
            </td>
            <td>
                <input type="hidden" id="paramMode" name="paramMode" value="${tradeParam.paramMode}"/>
                <c:if test="${ tradeParam.paramMode  == 2}">
                    严格
                </c:if>
                <c:if test="${tradeParam.paramMode  != 2}">
                    ${tradeParam.paramMode}
                </c:if>

            </td>
        </tr>
    </table>
</form>

<form id="form1" >
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" >
            <tr>
                <td width="150"><div style="width: 150px">机构</div> </td>
                <c:forEach items="${combList}" var="comb">
                    <td width="150" align="center">
                        <div style="width: 150px">
                            <input  type="hidden" class="planamonut_comb" id="${comb.combcode}"/>
                                ${comb.combname}
                        </div>
                    </td>
                </c:forEach>
                <td ><div style="width: 150px"><div style="width: 150px">合计</div></div> </td>
            </tr>
            <c:forEach items="${organList}" var="organ">
                <tr>
                    <input  type="hidden" class="planamonut_organ" id="${organ.thiscode}"/>
                    <td> ${organ.organname }</td>
                    <c:forEach items="${combList}" var="comb">
                        <c:set var="plan_key" value="${organ.thiscode}_${comb.combcode}" />
                        <c:set var="planDetail" value="${planMap[plan_key]}" />
                        <td>
                            <input name="${organ.thiscode}_${comb.combcode}" value="${planDetail.pdAmount}" type="hidden" class="planamonut ${organ.thiscode } ${comb.combcode}" id="${organ.thiscode }_${comb.combcode}" readOnly="readOnly"/>
                            <c:if test="${empty planDetail.pdAmount }">
                                0
                            </c:if>
                            <c:if test="${!empty planDetail.pdAmount}">
                                <fm:formatNumber maxFractionDigits="10" value="${planDetail.pdAmount}"/>
<%--                                ${planDetail.pdAmount}--%>
                            </c:if>
                        </td>
                    </c:forEach>
                    <td> <span  id="${organ.thiscode }_row" >0</span>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td> 合计</td>
                <c:forEach items="${combList}" var="comb">
                    <td>
                        <span   id="${comb.combcode}_column"   >0</span>
                    </td>
                </c:forEach>
                <td> <span  id="row_column_count"  >0</span>
                </td>
            </tr>
        </table>

    </div>

</form>

<input type="hidden" id="comments" name="comments" value="${fn:length(comments)}"/>
<c:if test="${!empty comments }">
    <div id="panel23" panelTitle="审批进度" class="box2_custom" boxType="box2" startState="open">
        <table class="tableStyle tab-hei-30" width="80%" mode="list"
               style="table-layout:fixed;word-wrap:break-word;word-break:break-all">
            <tr>
                <td width="20%" align="left">
                    审批用户
                </td>
                <td width="20%" align="left">
                    审批时间
                </td>
                <td width="20%" align="left">
                    审批状态
                </td>
                <td width="40%" align="left">
                    审批意见
                </td>
            </tr>
            <c:forEach items="${comments}" var="comment">
                <tr>
                    <td> ${comment.userId }</td>
                    <td>
                        <c:choose>
                            <c:when test="${empty comment.time}">
                                ----
                            </c:when>
                            <c:otherwise>
                                <fm:formatDate value="${comment.time}" pattern="yyyyMMdd HH:mm:ss"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td> ${comment.type }</td>
                    <td style="word-break:break-all">${comment.fullMessage }</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

</body>

<style>
    input:disabled{
        opacity: 1;
        -webkit-text-fill-color: black;
    }
</style>
<script>
    var planId = '${planId}';


    $(function(){
        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(1, 0, 1, 0);
        //横竖加和
        countAmount();
                $(".planamonut").change(function(){
            var id=$(this).attr('id');
            var ids = id.split("_");
            var rowId = ids[0] + "_row";
            var columnId = ids[1] + "_column";
            var rowAmount = countClass(ids[0]);
            var columnAmount = countClass(ids[1]);
            var countAmount = addPlanAmonut();
            $('span[id=' + rowId + ']').html(rowAmount);
            $('span[id=' + columnId + ']').html(columnAmount);
            $('span[id=row_column_count]').html(countAmount);
        });

    });

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
    function accMul(arg1, arg2)
    {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    // 计算调整金额的总和
    function addPlanAmonut() {
        var amonutList = $(".planamonut");
        var amountCount = 0;
        $(amonutList).each(function(){
            amountCount= accAdd(amountCount,this.value);
        });
        return amountCount;
    }


    //计算所有class的值的和
    function countClass(str) {
        str = "."+str;
        var amonutList = $(str);
        var amount = 0;
        $(amonutList).each(function(){
            amount = accAdd(amount, this.value);
        });
        return amount;
    }

    //初始化合计数
    function countAmount() {
        //贷种组合求和
        var planamonut_comb = $(".planamonut_comb");
        $(planamonut_comb).each(function(){
            var id=$(this).attr('id');
            var columnId = id + "_column";
            var columnAmount = countClass(id);
            $('span[id=' + columnId + ']').html(columnAmount);

        });
        //机构求和
        var planamonut_organ = $(".planamonut_organ");
        $(planamonut_organ).each(function(){
            var id=$(this).attr('id');
            var rowId = id+ "_row";
            var rowAmount = countClass(id);
            $('span[id=' + rowId + ']').html(rowAmount);
        });
        //总和
        var countAmount = addPlanAmonut();
        $('span[id=row_column_count]').html(countAmount);
    }

</script>

</html>