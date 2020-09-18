<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"/>
    <!-- 树组件end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
    <!--框架必需start-->
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <!--数据表格start-->
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <!--数据表格end-->
    <!-- 表单验证start -->
    <script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
    <!-- 表单验证end -->
    <!-- 日期选择框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
    <!-- 日期选择框end -->
    <!-- 数字步进器start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/stepper.js"></script>
    <!-- 数字步进器end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <script type="text/javascript" src="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <title>计划调整详情页面</title>
</head>
<body>
<form id="form1" >
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <%--                <input id="planMonth" name="planMonth"  type="text" onchange="planMonthChange();" > </input>--%>
                <input name="planMonth" type="text" id="planMonth" onchange="planMonthChange();" class="input-small inline form_datetime" style="width: 160px;" />

            </td>
            <td align="left">单位：</td>
            <td>
                <input id="planUnit" name="planUnit"  type="hidden" > </input>
                <span name = "planUnit"></span>
            </td>
        </tr>

        <%--<td align="right">--%>
        <%--本月计划净增量 ：--%>
        <%--</td>--%>
        <%--<td>--%>
        <%--<input id="increment" name="increment"  type="hidden" value="${plan.planIncrement}"> </input>--%>
        <%--${plan.planIncrement}--%>
        <%--</td>--%>
        <input id="increment" name="increment"  type="hidden" value="${plan.planIncrement}"> </input>


    </table>

    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150" rowspan="2">贷种 </td>
                <c:forEach items="${organList}" var="organ">
                    <td colspan="4" align="center">
                            ${organ.organname}
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <c:forEach items="${organList}" var="organ">
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">原计划</div>
                    </td>
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">条线需求</div>
                    </td>
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">调整量</div>
                    </td>
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">调整后计划</div>
                    </td>
                </c:forEach>
            </tr>
            <c:forEach items="${combList}" var="comb">
                <tr>
                    <td align="center"> <span name="${comb.combcode}">${comb.combname }</span></td>
                    <c:forEach items="${organList}" var="organ">
                        <c:set var="adj_key" value="${organ.thiscode}_${comb.combcode}" />
                        <td>
                            <c:if test="${empty creditPlanList[adj_key] }">
                                <input name="${organ.thiscode}_${comb.combcode}_init"  oninput='upperCase(this)' value="0" type="hidden"
                                       class = "${organ.thiscode}_init ${comb.combcode}_init init" />
                                <span name="${organ.thiscode}_${comb.combcode}_init"  >0</span>
                            </c:if>
                            <c:if test="${!empty creditPlanList[adj_key]}">
                                <input name="${organ.thiscode}_${comb.combcode}_init"    oninput='upperCase(this)' value="${creditPlanList[adj_key]}" type="hidden"
                                       class = "${organ.thiscode}_init ${comb.combcode}_init init" />
                                <span name="${organ.thiscode}_${comb.combcode}_init"  >${creditPlanList[adj_key]}</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${empty reqList[adj_key] }">
                                <input name="${organ.thiscode}_${comb.combcode}_req"   oninput='upperCase(this)' value="0" type="hidden"
                                       class = "${organ.thiscode}_req ${comb.combcode}_req req" />
                                <span name="${organ.thiscode}_${comb.combcode}_req">0</span>
                            </c:if>
                            <c:if test="${!empty reqList[adj_key]}">
                                <input name="${organ.thiscode}_${comb.combcode}_req"  oninput='upperCase(this)' value="${reqList[adj_key]}" type="hidden"
                                       class = "${organ.thiscode}_req ${comb.combcode}_req req"/>
                                <span name="${organ.thiscode}_${comb.combcode}_req">${reqList[adj_key]}</span>
                            </c:if>
                        </td>
                        <td>
                            <input name="${organ.thiscode}_${comb.combcode}_adj" oninput='upperCase(this)' id="${organ.thiscode}_${comb.combcode}"
                                   class="${organ.thiscode}_adj ${comb.combcode}_adj adj plandadjamount" value="0" type="text" />
                        </td>
                        <td>
                            <input name="${organ.thiscode}_${comb.combcode}_final" class="${organ.thiscode}_final ${comb.combcode}_final final planadjAmonutFinal" value="0" type="hidden" />
                            <span name="${organ.thiscode}_${comb.combcode}_final"   >0</span>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>

            <tr>
                <c:forEach items="${organList}" var="organ">
                    <td align="center">合计</td>
                    <td><span id="${organ.thiscode}_init_count" class="organ_init_count">0</span></td>
                    <td><span id="${organ.thiscode}_req_count">0</span></td>
                    <td><span id="${organ.thiscode}_adj_count">0</span></td>
                    <td><span id="${organ.thiscode}_final_count">0</span></td>
                </c:forEach>
            </tr>

        </table>

    </div>
    <div align="center" style="alignment: center">
        <button type="button" id="submit" onclick="return sub()" class="saveButton"></button>
        <button type="button" id="cancelSubmit" onclick="return cancel()" class="cancelButton"></button>
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



    // 计算调整后金额
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

        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(2, 0, 1, 0);

        //判断当前月份有没有计划
        var planMonth = $("#planMonth").val();

        //初始化调整后金额
        // amountFinal();

        //初始化横竖求和
        // amountRowAndColumn();



        //输入框获取焦点事件
        $(".plandadjamount").focus(function(){
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".plandadjamount").blur(function(){
            if ($.trim(this.value) == "") {
                this.value = 0;
            }

            //计算调整后金额
            var id=$(this).attr('id');
            var initName=id+"_init";
            var adjName=id+"_adj";
            var finalName=id+"_final";
            var init=$('input[name='+initName+']').val();
            var adj=$('input[name='+adjName+']').val();
            $('input[name='+finalName+']').val(accAdd(parseFloat(init),parseFloat(adj)));
            $('span[name='+finalName+']').html(accAdd(parseFloat(init),parseFloat(adj)));

            // 计算横竖求和
            changeAmountRowAndColumn(id);

        });
    })



    //求和 行列
    function changeAmountRowAndColumn(id) {
        var ids = id.split("_");
        // 行求和
        // 调整金额
        var adjRowName=ids[0]+"_adj";
        var adjRowCount = countClass(adjRowName);
        $("span[id="+adjRowName+"_count"+"]").html(adjRowCount);
        // 调整后金额
        var finalRowName=ids[0]+"_final";
        var finalRowCount = countClass(finalRowName);
        $("span[id="+finalRowName+"_count"+"]").html(finalRowCount);
    }

    //初始化求和 行列
    function amountRowAndColumn() {
        // 行求和
        var rowList = $(".organ_init_count");
        $(rowList).each(function(){
            var id=$(this).attr('id');
            var ids = id.split("_");
            //原计划金额
            var initName=ids[0]+"_init";
            var initCount = countClass(initName);
            $("span[id="+initName+"_count"+"]").html(initCount);
            // 条线需求金额
            var reqName=ids[0]+"_req";
            var reqCount = countClass(reqName);
            $("span[id="+reqName+"_count"+"]").html(reqCount);
            // 调整金额
            var adjName=ids[0]+"_adj";
            var adjCount = countClass(adjName);
            $("span[id="+adjName+"_count"+"]").html(adjCount);
            // 调整后金额
            var finalName=ids[0]+"_final";
            var finalCount = countClass(finalName);
            $("span[id="+finalName+"_count"+"]").html(finalCount);

        });
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


    // 初始化调整后金额
    function amountFinal() {
        var amonutList = $(".plandadjamount");
        $(amonutList).each(function(){
            var id=$(this).attr('id');
            var initName=id+"_init";
            var adjName=id+"_adj";
            var finalName=id+"_final";
            var init=$('input[name='+initName+']').val();
            var adj=$('input[name='+adjName+']').val();
            $('input[name='+finalName+']').val(accAdd(parseFloat(init),parseFloat(adj)));
            $('span[name='+finalName+']').html(accAdd(parseFloat(init),parseFloat(adj)));
        });
    }

    //js计算乘法
    function accMul(arg1, arg2)
    {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
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
    function addPlanadjAmonut() {
        var amonutList = $(".plandadjamount");
        var amountCount = 0;
        $(amonutList).each(function(){
            amountCount= accAdd(amountCount,this.value);
        });
        return amountCount;
    }

    //js计算除法
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

    // 计算调整后金额的总和
    function addPlanadjAmonutFinalCountAmonut() {
        var planadjAmonutFinalList = $(".planadjAmonutFinal");
        var planadjAmonutFinalCount = 0;
        $(planadjAmonutFinalList).each(function(){
            planadjAmonutFinalCount= accAdd(planadjAmonutFinalCount,this.value);
        });
        return planadjAmonutFinalCount;
    }


    //计划调整提交
    function sub() {
        //校验
        var planMonth = $("#planMonth").val();
        var planUnit = $("#planUnit").val();

        var amountCount = addPlanadjAmonut();
        var unit = $("#planUnit").val() == 1?"万元":"亿元";

        if(!planMonth){
            top.Dialog.alert("所属月份不可为空");
            return;
        }
        if(!planUnit){
            top.Dialog.alert("单位不可为空");
            return;
        }

//调整后金额总量
        var planadjAmountFinalCount =  addPlanadjAmonutFinalCountAmonut();
        var increment = $("#increment").val();
        if (planUnit == 1) {
            planadjAmountFinalCount = accDiv(planadjAmountFinalCount, 10000);
        }

        //1分制定量必须等于计划净增量
        // if (organlevel == '1') {
        //     if (planadjAmountFinalCount != increment) {
        //         top.Dialog.alert("计划制定总额为：" + planadjAmountFinalCount + "亿元,本月计划净增量为：" + increment + "亿元，请调整！");
        //         return;
        //     }
        // }

        var nowData = formatDate(new Date(),'yyyyMM');
        if (nowData != planMonth) {
            top.Dialog.confirm("是否调整" + planMonth + "的计划?|操作提示", function () {
                submitPlanadj();
            });
        } else {
            submitPlanadj();
        }
        // if (planadjAmountFinalCount > increment) {
        //     top.Dialog.confirm("计划制定总额为：" + planadjAmountFinalCount + "亿元,本月计划净增量为：" + increment + "亿元，已超出！确定要保存操作吗?|操作提示", function () {
        //         submitPlanadj();
        //     });
        // } else {
        //     top.Dialog.confirm("本次调整金额一共为："+amountCount+unit+"。确定要保存操作吗?|操作提示", function () {
        //         submitPlanadj();
        //     });
        // }

    }

    function submitPlanadj() {
        $('#planMonth').attr('disabled', false);
        //信贷调整详情url
        var adjustUrl = "<%=path%>/tbPlanadjStripe/savePlanadj.htm";
        var data = $("#form1").serialize();
        //post方法,同样也可以调用ajax
        $('#submit').attr('disabled', true);
        $('#cancelSubmit').attr('disabled', true);
        $.ajax({
            type:'post',
            url:adjustUrl,
            cache: false,
            data:data,  //重点必须为一个变量如：data
            dataType:'json',
            success:function(result){
                if (result.success === "true" || result.success === true) {
                    $('#submit').attr('disabled', false);
                    $('#cancelSubmit').attr('disabled', false);
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
                    $('#submit').attr('disabled', false);
                    $('#cancelSubmit').attr('disabled', false);
                    if(result.code == '403'){
                        top.Dialog.alert("所属月份批量条线调整计划已存在");
                    } else if (result.code == '408'){
                        top.Dialog.alert(result.message);
                    }else{
                        top.Dialog.alert("新增失败");
                    }
                }
            },
            error:function(){
                $('#submit').attr('disabled', false);
                $('#cancelSubmit').attr('disabled', false);
                top.Dialog.alert("请求异常");
            }
        })

    }

    function planMonthChange() {
        var planMonth = $('#planMonth').val();

        //判断该月份是否存在计划
        $.ajax({
            type: "post",
            url: "<%=path%>/tbPlanadjStripe/tbPlanadjDetail.htm",
            data: {"planMonth": planMonth},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var plan = result.plan;
                    if (!plan) {
                        top.Dialog.alert("当前月份的机构计划还没有制定或计划还没有通过审批！", function () {
                            var menu_id = parent.getCurrentTabId();
                            if(menu_id==undefined){
                                top.Dialog.close();
                                return;
                            }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                        return;
                    }
                    var planadjStatus = result.planadjStatus;
                    if (planadjStatus == 0) {
                        top.Dialog.alert(" 所属月份批量条线调整计划已存在");
                    }else if (planadjStatus == 32) {
                        top.Dialog.alert(" 所属月份批量条线调整计划已存在且被驳回");
                    }else if (planadjStatus == 8) {
                        top.Dialog.alert(" 所属月份批量条线调整计划正在审批中");
                        return;
                    }

                    $('#planMonth').attr('disabled', true);

                    var creditPlanList = result.creditPlanList;
                    var reqList = result.reqList;
                    var organList = result.organList;
                    var combList = result.combList;
                    organlevel = result.organlevel;

                    //初始化
                    $("#planUnit").val(plan.planUnit);
                    $("#increment").val(plan.planIncrement);

                    var unit = "";
                    if (plan.planUnit == 1 || '1' == plan.planUnit) {
                        unit = "万元";
                    } else if (plan.planUnit == 2 || '2' == plan.planUnit) {
                        unit = "亿元";
                    }
                    $("span[name=planUnit]").html(unit);


                    //初始化creditPlanList
                    if (organList && combList) {
                        for (var i = 0; i < organList.length; i++) {
                            var organ = organList[i];
                            for (var j = 0; j < combList.length; j++) {

                                var comb = combList[j];
                                var organcode = organ.thiscode;
                                var combcode = comb.combcode;
                                var mapKey = organcode + "_" + combcode;
                                var planAmount = creditPlanList[mapKey];
                                var reqAmount = reqList[mapKey];

                                if ($.isNumeric(planAmount)) {
                                    $("input[name=" + mapKey + "_init" + "]").val(transFormat(planAmount));
                                    $("span[name=" + mapKey + "_init" + "]").html(transFormat(planAmount));
                                }

                                if ($.isNumeric(reqAmount)) {
                                    $("input[name=" + mapKey + "_req" + "]").val(transFormat(reqAmount));
                                    $("span[name=" + mapKey + "_req" + "]").html(transFormat(reqAmount));
                                }


                            }
                        }
                    }

                    //横竖加和
                    //初始化调整后金额
                    amountFinal();

                    //初始化横竖求和
                    amountRowAndColumn();
                }
            }

        });
    }

    function formatDate(date, format) {
        if (!date) return;
        if (!format) format = "yyyy-MM-dd";
        switch(typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
            return dict[arguments[0]];
        });
    }

</script>

</html>