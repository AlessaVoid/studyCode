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
    <title>计划新增页面</title>
</head>
<body>
<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <select id="planMonth" name="planMonth" onchange="planMonthChange();"></select>
                <span class="star">*</span>
            </td>
            <td align="left">单位：</td>
            <td>
                <dic:select id="planUnit" dicType="CURRENCY_UNIT" name="planUnit"
                            tgClass="validate[required]" dicNo="2"
                            required="true"></dic:select>
                <span class="star">*</span>
            </td>


            <%--<td align="right">--%>
                <%--本月计划净增量（亿元）：--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<input type="text" id="increment" name="increment"/>--%>
                <%--<span class="star">*</span>--%>
            <%--</td>--%>
            <input type="hidden" id="increment" name="increment"/>


            <td align="right">
                管控模式：
            </td>
            <td>
                <input type="text" id="paramMode" name="paramMode" disabled/>
                <span class="star">*</span>
            </td>
        </tr>
    </table>
</form>

<form id="form1" >



    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list">
            <tr>
                <td width="150" align="center">贷种</td>
                <c:forEach items="${organList}" var="organ">
                    <td> ${organ.organname }</td>
                </c:forEach>
            </tr>


            <c:forEach items="${combList}" var="comb">
                <tr>
                    <td width="150" align="center">
                            ${comb.combname}
                    </td>
                    <c:forEach items="${organList}" var="organ">
                    <td>
                        <input name="${organ.thiscode}_${comb.combcode}" oninput='upperCase(this)' value="0"
                               class="planamonut" type="text" id="${organ.thiscode }_${comb.combcode}"/>

                        </c:forEach>
                    </td>
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

    <div align="center" style="alignment: center;margin:20px;">
        <button type="button" onclick="return sub()" class="saveButton" id="saveTbPlan"></button>
        <button type="button" onclick="return cancel()" class="cancelButton" id="cancelSaveTbPlan"></button>
    </div>
</form>
</body>

<script>

    var organlevel =  '${organlevel}';

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

        if(!planMonth){

            top.Dialog.alert("所属月份不可为空");
            return;
        }
        if(!planUnit){
            top.Dialog.alert("单位不可为空");
            return;
        }

        var amountCount = addPlanAmonut();


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

    //保存计划
    function submitplan() {
        var planMonth = $("#planMonth").val();
        var planUnit = $("#planUnit").val();
        var increment = $("#increment").val();
        var amountCount = addPlanAmonut();



        var planDetail = $("#form1").serialize();
        $('#saveTbPlan').attr('disabled', true);
        $('#cancelSaveTbPlan').attr('disabled', true);
        $.ajax({
            type: "POST",
            url: "<%=path%>/creditPlanStripe/creditPlanAdd.htm",
            data: {"planMonth": planMonth, "planUnit": planUnit, "increment": increment,  "amountCount": amountCount,"planDetail": planDetail},
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("新增成功!", function () {
                        var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                        var menu_frame_id = "page_" + menu_id;
                        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                        top.Dialog.close();
                    });
                } else {
                    $('#saveTbPlan').attr('disabled', false);
                    $('#cancelSaveTbPlan').attr('disabled', false);
                    top.Dialog.alert(result.message);
                }
            },
            error: function (result) {
                $('#saveTbPlan').attr('disabled', false);
                $('#cancelSaveTbPlan').attr('disabled', false);
                top.Dialog.alert("请求异常");
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

    //初始化表格数据
    function planMonthChange(){

        var planMonth = $("#planMonth").val();
        //判断该月份是否存在计划
        $.ajax({
            type: "get",
            url: "<%=path%>/creditPlanStripe/creditPlanJudgeMonth.htm",
            data: {"planMonth": planMonth},
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    // top.Dialog.alert(result.message);
                } else {
                        top.Dialog.alert(result.message);
                }
            },
            error: function (result) {
                top.Dialog.alert("请求异常");
            }

        });

        $.post("<%=path%>/creditPlanStripe/getPlanTime.htm", {"planMonth": planMonth}, function (result) {
            //赋给data
            $("#paramMode").val(result.paramMode==2?"严格":result.paramMode);
            $("#increment").val(result.increment);
        }, "json");

        $('#paramMode').attr("disabled", "disabled");
        $('#increment').attr("disabled", "disabled");
    }

    $(function(){
        //冻结行列 行首 行末 列首 列末
        // $("#plan").FrozenTable(1, 0, 1, 0);

        ajaxMonth();
        // planMonthChange();

        //金额加和
        $(".planamonut").change(function(){
            var amountCount = addPlanAmonut();
            $('span[id=row_column_count]').html(amountCount);
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

    //初始化金额求和
    function conuntAmount() {
        var amountCount = addPlanAmonut();
        $('span[id=row_column_count]').html(amountCount);
    }

</script>

</html>