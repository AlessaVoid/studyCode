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
    <title>计划修改页面</title>
</head>
<body>
<form id="form2">
    <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
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
                <c:if test="${tradeParam.paramMode  == 2}">
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
<%--    <div align="center" style="alignment: center;margin:20px;">--%>
<%--        <button type="button" onclick="return sub()" class="saveButton" id="updateTbPlan"></button>--%>
<%--        <button type="button" onclick="return cancel()" class="cancelButton" id="cancelUpdateTbPlan"></button>--%>
<%--    </div>--%>
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
<div>审批意见:</div>
<div><textarea id="comment" rows="50" cols="50"></textarea></div>
<div>
    <c:if test="${false == lastUserType }">
        <div>
            <div align="left">下一节点审批人:</div>
            <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
        </div>
    </c:if>
</div>
<div align="center">
    <div align="center">
        <button type="button" id="submit" onclick="onAudit('1')"><span class="icon_ok">通过</span></button>
        <button type="button" id="cancelSubmit" onclick="onAudit('0')"><span class="icon_no">驳回</span></button>
    </div>
</div>

</body>

<script>
    var planId = '${planId}';

    //用户只能输入正负数与小数
    function upperCase(obj){
        if(isNaN(obj.value) && !/^-$/.test(obj.value)){
            obj.value="";
        }
        if(!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)){
            obj.value=obj.value.replace(/\.\d{2,}$/,obj.value.substr(obj.value.indexOf('.'),9));
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


    // 计算调整金额的总和
    function addPlanAmonut() {
        var amonutList = $(".planamonut");
        var amountCount = 0;
        $(amonutList).each(function(){
            amountCount= accAdd(amountCount,this.value);
        });
        return amountCount;
    }


    //js计算乘法
    function accMul(arg1, arg2)
    {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }


    //初始化表格数据
    function ajaxFormInit(){
        $("#increment").attr("disabled", "disabled");
        $("#paramMode").attr("disabled", "disabled");

        $.ajax({
            type: "POST",
            url: "<%=path%>/creditPlanStripe/creditPlanDetailData.htm",
            data: {"planId":planId},
            dataType: "json",
            success: function(result){
                if(result){
                    var plan = result.plan;
                    var planDetails = result.planDetails;
                    var planMonth = plan.planMonth;

                    //初始化plan值
                    $("#increment").val(plan.planIncrement);
                    $('#planMonth').setValue(planMonth);
                    $("#planMonth").render();
                    $("#planUnit").val(plan.planUnit);
                    $("#planUnit").render();
                    //加载管控模式
                    planMonthChange(planMonth);

                    //初始化planDetail
                    if(planDetails){
                        for(var i = 0; i < planDetails.length; i++){
                            var planDetail = planDetails[i];
                            var organCode = planDetail.pdOrgan;
                            var pdAmount = planDetail.pdAmount;
                            var combcode = planDetail.pdLoanType;
                            $("#"+organCode+"_"+combcode).val(transFormat(pdAmount));
                        }
                    }
                }
            }
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

    //所属月份change
    function planMonthChange(){
        var planMonth = $("#planMonth").val();
        $.post("<%=path%>/creditPlanStripe/getPlanTime.htm", {"planMonth": planMonth}, function (result) {
            $("#paramMode").val(result.paramMode==2?"严格":result.paramMode);
            $("#increment").val(result.increment);
        }, "json");
    }


    $(function(){

        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(1, 0, 1, 0);

        ajaxMonth();
        // ajaxFormInit();

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

    //初始化计划审批人员
    function initAuditOper() {
        //初始化计划审批人员
        $.ajax({
            url: "<%=path%>/tbPlanStripePendingApp/getOperInfoListByRolecode.htm?taskid=${taskid}",
            method: "GET",
            async: false,
            success: function(res) {
                var result=JSON.parse(res);
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

    //审批计划
    function onAudit(isAgree) {
        var comment = $("#comment").val();
        if(comment==""){
            top.Dialog.alert("请填写审批意见!");
            return;
        }
        var taskId = $("#taskId").val();
        var assignee = $("#auditOperList").val();
        if(assignee==""){
            top.Dialog.alert("请选择下级审批人!" );
            return;
        }

        top.Dialog.confirm("确定要提交该记录吗？", function () {
            $('#submit').attr('disabled', true);
            $('#cancelSubmit').attr('disabled', true);
            $.ajax({
                url: "<%=path%>/tbPlanStripePendingApp/auditLoanTbPlanRequest.htm",
                data: {
                    "planId": planId,
                    "taskId": taskId,
                    "comment": comment,
                    "isAgree": isAgree,
                    "assignee": assignee,
                    "lastUserType":${lastUserType}
                },
                type: 'POST',
                success: function(result) {
                    var res=JSON.parse(result);
                    if(res.success === "true" || res.success === true)
                    {
                        $('#submit').attr('disabled', false);
                        $('#cancelSubmit').attr('disabled', false);
                        top.Dialog.alert("操作成功!", function() {
                            var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        })
                        ;
                    }else{
                        $('#submit').attr('disabled', false);
                        $('#cancelSubmit').attr('disabled', false);
                        top.Dialog.alert("操作失败!" + res.message);
                    }
                }
            });

        });

    }

</script>



</html>