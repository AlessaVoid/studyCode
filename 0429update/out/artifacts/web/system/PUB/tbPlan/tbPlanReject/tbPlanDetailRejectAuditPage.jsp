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
    <title>信贷计划修改页面</title>
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
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
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
                        <td>
                            <input name="${organ.thiscode}_${comb.combcode}" oninput='upperCase(this)' value="0"  class="planamonut ${organ.thiscode } ${comb.combcode}"  type="text" id="${organ.thiscode }_${comb.combcode}" />
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
    <div align="center" style="alignment: center;margin:20px;">
        <button type="button" onclick="return sub()" id="updateTbPlan" class="saveButton"></button>
        <button type="button" onclick="return cancel()" id="cancelUpdateTbPlan" class="cancelButton"></button>
    </div>
</form>
<table class="tableStyle" width="100%" mode="list" formMode="line">
    <input type="hidden" id="comments" name="comments" value="${fn:length(comments)}"/>
    <c:if test="${!empty comments }">
    <div id="panel23" panelTitle="审批进度" class="box2_custom" boxType="box2" startState="close">
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
    <div>备注:</div>
    <div><textarea id="comment" rows="50" cols="50"></textarea></div>
    <table class="tableStyle" width="100%" mode="list" formMode="line">
        <c:if test="${false == lastUserType }">
            <div>
                <div align="left">下一节点审批人:</div>
                <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
            </div>
        </c:if>
        <tr>
            <td colspan="8">
                <div align="center">
                    <button id="planSubmitInfo" type="button"  onclick="onAudit('1')" style="margin-right: 10px"
                    ><span>提交</span></button>
                    <button id="cancelSubmitInfo" type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</body>

<script>
    var planId = '${planId}';
    var organlevel =  '${organlevel}';
    //页面有修改，是否能重新提交
    var isSubmit = true;

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

    //提交
    function sub(){
        //校验
        var planMonth = $("#planMonth").val();
        var planUnit = $("#planUnit").val();
        var increment = $("#increment").val();

        var amountCount = addPlanAmonut();

        if(!planMonth){
            top.Dialog.alert("所属月份不可为空");
            return;
        }
        if(!planUnit){
            top.Dialog.alert("单位不可为空");
            return;
        }

        if (planUnit == 1) {
            amountCount = accDiv(amountCount, 10000);
        }

        //1分制定量必须等于计划净增量
        // if (organlevel == '1') {
        //     if (amountCount != increment) {
        //         top.Dialog.alert("计划制定总额为：" + amountCount + "亿元,本月计划净增量为：" + increment + "亿元，请调整！");
        //         return;
        //     }
        // }

        submitplan();
        // if (amountCount > increment) {
        //     top.Dialog.confirm("计划制定总额为：" + amountCount + "亿元,本月计划净增量为：" + increment + "亿元，已超出！确定要保存操作吗?|操作提示", function () {
        //         submitplan();
        //     });
        // } else {
        //     top.Dialog.confirm("确定要保存操作吗?|操作提示", function () {
        //         submitplan();
        //     });
        // }
    }
    //提交
    function submitplan() {
        var planMonth = $("#planMonth").val();
        var planUnit = $("#planUnit").val();
        var increment = $("#increment").val();

        var amountCount = addPlanAmonut();


        var planDetail = $("#form1").serialize();
        $('#updateTbPlan').attr('disabled', true);
        $('#cancelUpdateTbPlan').attr('disabled', true);
            $.ajax({
                type: "POST",
                url: "<%=path%>/creditPlan/creditPlanUpdate.htm",
                data: {"amountCount":amountCount, "planId":planId, "planMonth":planMonth, "planUnit":planUnit, "increment":increment, "planDetail":planDetail},
                dataType: "json",
                success: function(result){
                    if (result.success == true || result.success == "true") {
                        isSubmit = true;
                        $('#updateTbPlan').attr('disabled', false);
                        $('#cancelUpdateTbPlan').attr('disabled', false);
                        top.Dialog.alert("修改成功!");
                    }else{
                        $('#updateTbPlan').attr('disabled', false);
                        $('#cancelUpdateTbPlan').attr('disabled', false);
                        if(result.code == '403'){
                            top.Dialog.alert("所属月份计划已存在");
                        } else if (result.code == '408'){
                            top.Dialog.alert(result.message);
                        }else{
                            top.Dialog.alert("修改失败");
                        }
                    }
                },
                error: function(result){
                    $('#updateTbPlan').attr('disabled', false);
                    $('#cancelUpdateTbPlan').attr('disabled', false);
                    top.Dialog.alert("请求异常");
                }
            });

    }

    //初始化表格数据
    function ajaxFormInit(){
        $("#increment").attr("disabled", "disabled");
        $("#paramMode").attr("disabled", "disabled");

        $.ajax({
            type: "POST",
            url: "<%=path%>/creditPlan/creditPlanDetailData.htm",
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
                    //横竖加和
                    countAmount();
                }
            }
        });
    }

    //获取所属月份下拉数据
    function ajaxMonth(){
        $.post("<%=path%>/creditPlan/findTradeParam.htm", {}, function (result) {
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
        $.post("<%=path%>/creditPlan/getPlanTime.htm", {"planMonth": planMonth}, function (result) {
            $("#paramMode").val(result.paramMode==2?"严格":result.paramMode);
            $("#increment").val(result.increment);
        }, "json");
    }


    $(function(){
        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(1, 0, 1, 0);

        ajaxMonth();
        ajaxFormInit();

        //横竖加和
        // countAmount();
        $(".planamonut").change(function(){
            isSubmit = false;
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
        //输入框获取焦点事件
        $(".planamonut").focus(function(){
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".planamonut").blur(function(){
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });
    })


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

    window.onload = function () {
        initAuditOper();
    };

    //初始化信贷计划审批人员
    function initAuditOper() {
        $.ajax({
                url: "<%=path%>/tbPlanReject/getOperInfoListByRolecode.htm?taskid=${taskid}",
                method: "GET",
                async: false,
                success: function(res){
                    var result = JSON.parse(res);
                    var list = [];
                    for (var i = 0; i < result.length; i++) {
                        var oper = result[i];
                        var item = {
                            "key": oper.opername,
                            "value": oper.opercode
                        };
                        list.push(item);
                    }
                    ;
                    var selData = {
                        "list": list
                    };
                    $("#auditOperList").data("data", selData);
                    $("#auditOperList").render();
                }
            }
        );
    }

    //提交
    function onAudit(isAgree) {
        // saveLoanPlanDetailScriptInfo();
        var comment = $("#comment").val();
        if(comment==""){
            top.Dialog.alert("请填写备注!");
            return;
        }
        var taskId = $("#taskId").val();
        var assignee = $("#auditOperList").val();
        if(assignee==""){
            top.Dialog.alert("请选择下级审批人!" );
            return;
        }

        if (isSubmit) {
            top.Dialog.confirm("确定要重新提交审批吗?", function () {
                submit(isAgree);
            });
        } else {
            top.Dialog.confirm("页面有未提交的数据，确定要重新提交审批吗?", function () {
                submit(isAgree);
            });
        }
    }

    //提交审批
    function submit(isAgree) {
        var comment = $("#comment").val();
        var taskId = $("#taskId").val();
        var assignee = $("#auditOperList").val();

        $('#planSubmitInfo').attr('disabled', true);
        $('#cancelSubmitInfo').attr('disabled', true);
        $.ajax({
            url: "<%=path%>/tbPlanReject/auditLoanTbPlanRequest.htm",
            data: {
                "planId": planId,
                "taskId": taskId,
                "comment": comment,
                "isAgree": isAgree,
                "assignee": assignee,
                "lastUserType":${lastUserType}
            },
            type: 'POST',
            success: function(result){
                var res=JSON.parse(result);
                if(res.success === "true" || res.success === true)
                {
                    $('#planSubmitInfo').attr('disabled', false);
                    $('#cancelSubmitInfo').attr('disabled', false);
                    top.Dialog.alert("提交成功!", function(){
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
                    $('#planSubmitInfo').attr('disabled', false);
                    $('#cancelSubmitInfo').attr('disabled', false);
                    top.Dialog.alert("提交失败!" + res.message);
                }
            }
        })
    }

</script>

</html>