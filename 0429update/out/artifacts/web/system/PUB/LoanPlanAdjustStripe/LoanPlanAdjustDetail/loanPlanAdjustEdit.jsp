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
                <input  id="planMonth" name="planMonth" value="${tbPlanadj.planadjMonth}" type="hidden" />
                <input  id="planadjId" name="planadjId" value="${tbPlanadj.planadjId}" type="hidden"/>
                ${tbPlanadj.planadjMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input  id="planUnit" name="planUnit" value="${tbPlanadj.planadjUnit}" type="hidden" />

                <c:if test="${tbPlanadj.planadjUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbPlanadj.planadjUnit ==2}">
                    亿元
                </c:if>
            </td>
        </tr>


        <%--<td align="right">--%>
        <%--本月计划净增量：--%>
        <%--</td>--%>
        <%--<td>--%>
        <%--<input  id="increment" name="increment" value="${tbPlanadj.planadjNetIncrement}" type="hidden" />--%>
        <%--${tbPlanadj.planadjNetIncrement}--%>
        <%--</td>--%>
        <input  id="increment" name="increment" value="${tbPlanadj.planadjNetIncrement}" type="hidden" />


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
                            <c:set var="adj_key" value="${organ.thiscode }_${comb.combcode}" />
                            <c:set var="adjDetail" value="${adjMap[adj_key]}" />
                            <td>
                                <input  name="${organ.thiscode }_${comb.combcode}_id" value="${adjDetail.planadjdId}" type="hidden" />
                                <c:if test="${empty adjDetail.planadjdInitAmount }">
                                    <input class = "${organ.thiscode}_init ${comb.combcode}_init init" name="${organ.thiscode }_${comb.combcode}_init"    oninput='upperCase(this)' value="0" type="hidden" />
                                    0
                                </c:if>
                                <c:if test="${!empty adjDetail.planadjdInitAmount}">
                                    <input class = "${organ.thiscode}_init ${comb.combcode}_init init" name="${organ.thiscode }_${comb.combcode}_init"    oninput='upperCase(this)' value="${adjDetail.planadjdInitAmount}" type="hidden" />
                                    <fm:formatNumber maxFractionDigits="10" value="${adjDetail.planadjdInitAmount}"/>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${empty adjDetail.planadjdReqAmount }">
                                    <input class = "${organ.thiscode}_req ${comb.combcode}_req req" name="${organ.thiscode }_${comb.combcode}_req"    oninput='upperCase(this)' value="0" type="hidden" />
                                    0
                                </c:if>
                                <c:if test="${!empty adjDetail.planadjdReqAmount}">
                                    <input class = "${organ.thiscode}_req ${comb.combcode}_req req" name="${organ.thiscode }_${comb.combcode}_req"    oninput='upperCase(this)' value="${adjDetail.planadjdReqAmount}" type="hidden" />
                                    <fm:formatNumber maxFractionDigits="10" value="${adjDetail.planadjdReqAmount}"/>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${empty adjDetail.planadjdAdjAmount }">
                                    <input name="${organ.thiscode }_${comb.combcode}_adj" oninput='upperCase(this)' class="${organ.thiscode}_adj ${comb.combcode}_adj adj plandadjamount" value="0" type="text" />
                                </c:if>
                                <c:if test="${!empty adjDetail.planadjdAdjAmount}">
                                    <input name="${organ.thiscode }_${comb.combcode}_adj" oninput='upperCase(this)' class="${organ.thiscode}_adj ${comb.combcode}_adj adj plandadjamount" value="${adjDetail.planadjdAdjAmount}" type="text" id="${organ.thiscode }_${comb.combcode}" class="plandadj" />
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${empty adjDetail.planadjdAdjedAmount }">
                                    <input name="${organ.thiscode }_${comb.combcode}_final" class="${organ.thiscode}_final ${comb.combcode}_final final planadjAmonutFinal"oninput='upperCase(this)' value="0" type="hidden" />
                                    <span name="${organ.thiscode}_${comb.combcode}_final"   >0</span>
                                </c:if>
                                <c:if test="${!empty adjDetail.planadjdAdjedAmount}">
                                    <input name="${organ.thiscode }_${comb.combcode}_final" class="${organ.thiscode}_final ${comb.combcode}_final final planadjAmonutFinal" oninput='upperCase(this)' value="${adjDetail.planadjdAdjedAmount}" type="hidden" />
                                    <span name="${organ.thiscode}_${comb.combcode}_final"   >${adjDetail.planadjdAdjedAmount}</span>
                                </c:if>

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


    $(function(){

        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(2, 0, 1, 0);

        //初始化横竖求和
        amountRowAndColumn();
        // 初始化调整后金额
        amountFinal();


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
            $('#'+id).val(transFormat(this.value));
        });
    }


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

        submitPlanadj();
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
        //信贷调整详情url
        var adjustUrl = "<%=path%>/tbPlanadjStripe/updatePlanadj.htm";
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

                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("修改成功!", function () {
                        var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                        var menu_frame_id = "page_" + menu_id;
                        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                        top.Dialog.close();
                    });
                }else{
                    $('#submit').attr('disabled', false);
                    $('#cancelSubmit').attr('disabled', false);
                    if(result.code == '403'){
                        top.Dialog.alert("所属月份批量条线调整计划已存在");
                    } else if (result.code == '408'){
                        top.Dialog.alert(result.message);
                    }else{
                        top.Dialog.alert("修改失败");
                    }
                }
            },
            error: function(result){
                $('#submit').attr('disabled', false);
                $('#cancelSubmit').attr('disabled', false);
                top.Dialog.alert("请求异常");
            }
        })
    }

</script>

</html>