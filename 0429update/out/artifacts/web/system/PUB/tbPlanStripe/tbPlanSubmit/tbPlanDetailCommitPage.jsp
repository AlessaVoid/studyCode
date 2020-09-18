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
    <title>计划详情页面</title>
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
                <input type="hidden" id="paramMode" name="paramMode"/>
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
    <div id="scrollContent" class="border_gray" style="height: 410px;">
        <table id="plan" class="tableStyle"  mode="list">
            <tr>
                <td width="150" align="center">贷种</td>
                <c:forEach items="${organList}" var="organ">
                    <td > ${organ.organname }</td>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <td  align="center">
                            ${comb.combname}
                    </td>
                    <c:forEach items="${organList}" var="organ">
                        <c:set var="plan_key" value="${organ.thiscode}_${comb.combcode}" />
                        <c:set var="planDetail" value="${planMap[plan_key]}" />
                        <td>
                            <input name="${organ.thiscode}_${comb.combcode}" value="${planDetail.pdAmount}" class="planamonut" type="hidden" id="${organ.thiscode }_${comb.combcode}" readOnly="readOnly"/>
                            <c:if test="${empty planDetail.pdAmount }">
                                0
                            </c:if>
                            <c:if test="${!empty planDetail.pdAmount}">
                                <fm:formatNumber maxFractionDigits="10" value="${planDetail.pdAmount}"/>
<%--                                ${planDetail.pdAmount}--%>
                            </c:if>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>

            <tr>
                <td width="200" align="center">
                    <div style="width: 150px;">合计</div>
                </td>
                <td><span id="row_column_count">0</span></td>
            </tr>

        </table>

    </div>

    <div>备注:</div>
    <div><textarea id="comment" rows="50" cols="50"></textarea></div>

    <table class="tableStyle" width="100%" mode="list" formMode="line">
        <div>
            <div align="left">下一节点审批人:</div>
            <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
        </div>
        <tr>
            <td colspan="8">
                <div align="center">
                    <button id="planSubmitInfo" type="button" onclick="submitInfo()" style="margin-right: 10px"
                    ><span>提交</span></button>
                    <button id="cancelSubmitInfo" type="button" onclick="return cancel()" >取消</button>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
<style>
    input:disabled{
        opacity: 1;
        -webkit-text-fill-color: black;
    }
</style>
<script>
    var planId = '${planId}';

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

    // 计算调整金额的总和
    function addPlanAmonut() {
        var amonutList = $(".planamonut");
        var amountCount = 0;
        $(amonutList).each(function(){
            amountCount= accAdd(amountCount,this.value);
        });
        return amountCount;
    }


    //加载管控模式等信息
    function planMonthChange(planMonth){
        $.post("<%=path%>/creditPlanStripe/getPlanTime.htm", {"planMonth": planMonth}, function (result) {
            $("#paramMode").val(result.paramMode);
        }, "json");
    }

    $(function(){
        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(1, 0, 1, 0);

        //金额加和
        conuntAmount();
        $(".planamonut").change(function(){
            var amountCount = addPlanAmonut();
            $('span[id=row_column_count]').html(amountCount);
        });
    })

    //初始化金额求和
    function conuntAmount() {
        var amountCount = addPlanAmonut();
        $('span[id=row_column_count]').html(amountCount);
    }

    window.onload = function () {
        initAuditOper();
    };
    // 提交计划
    function submitInfo() {
        var comment =  $("#comment").val();
        if(comment==""){
            top.Dialog.alert("请填写备注!");
            return;
        }

        var auditOper = $("#auditOperList").val();
        if(auditOper==""){
            top.Dialog.alert("请选择下级审批人!" );
            return;
        }
        top.Dialog.confirm("确定要提交审批吗？", function () {
            $('#planSubmitInfo').attr('disabled', true);
            $('#cancelSubmitInfo').attr('disabled', true);
            $.ajax({
                url: '<%=path%>/tbPlanStripe/startLoanPlanAudit.htm',
                method: 'GET',
                data: {
                    "auditOper": auditOper,
                    "comment": comment,
                    "planId": planId
                }, success: function(result) {
                    var res=JSON.parse(result);
                    if (res.success === 'true' || res.success === true) {
                        top.Dialog.alert("计划提交成功,等待审批结果", function() {
                            $('#planSubmitInfo').attr('disabled', false);
                            $('#cancelSubmitInfo').attr('disabled', false);
                            var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                    } else if (res.success === 'false' || res.success === false) {
                        top.Dialog.alert("计划提交失败", res.message);
                        $('#planSubmitInfo').attr('disabled', false);
                        $('#cancelSubmitInfo').attr('disabled', false);
                    }
                }
            });
        });
    }
    //初始化计划审批人员
    function initAuditOper() {
        //初始化计划审批人员
        $.ajax({
            url: "<%=path%>/tbPlanStripe/getOperInfoListByRolecode.htm?rolecode=${rolecode}",
            method: "GET",
            async: false,
            success: function(res){
                var result = JSON.parse(res)
                var list = [];
                for (var i = 0; i < result.length; i++) {
                    var oper = result[i];
                    var item = {
                        "key": oper.opername,
                        "value": oper.opercode
                    };
                    list.push(item);
                    var selData = {
                        "list": list
                    };
                    $("#auditOperList").data("data", selData);
                    $("#auditOperList").render();
                }
            }
        })
    }

</script>

</html>