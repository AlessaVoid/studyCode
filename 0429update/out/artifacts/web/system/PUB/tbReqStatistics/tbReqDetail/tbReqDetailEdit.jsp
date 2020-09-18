<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
    <!-- 树组件end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->

    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <title></title>
</head>
<script type="text/javascript">

    var reqId = '${reqId}';

    //用户只能输入正负数与小数
    function upperCase(obj, code, level) {
        if (code == "reqNum") {
        } else {
            obj.value = obj.value.replace(/[^\d.]/g, "");
            //必须保证第一个为数字而不是.
            obj.value = obj.value.replace(/^\./g, "");
            //保证只有出现一个.而没有多个.
            obj.value = obj.value.replace(/\.{2,}/g, ".");
        }
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }
        if (level == '1') {
            var one_num = addPlanAmonut(code, level);
            $("#" + "one_num_" + code).html(one_num);
        } else if (level == '2') {
            var two_num = addPlanAmonut(code, level);
            $("#" + "two_num_" + code).html(two_num);
        } else if (level == '3') {
            var three_num = addPlanAmonut(code, level);
            $("#" + "three_num_" + code).html(three_num);
        }
    }

    function initPlanAmount() {
        //expNum;reqNum;rate;balance
        var code = "expNum";
        var one_num = addPlanAmonut(code, 1);
        $("#" + "one_num_" + code).html(one_num);
        var two_num = addPlanAmonut(code, 2);
        $("#" + "two_num_" + code).html(two_num);
        var three_num = addPlanAmonut(code, 3);
        $("#" + "three_num_" + code).html(three_num);

        var code = "reqNum";
        var one_num = addPlanAmonut(code, 1);
        $("#" + "one_num_" + code).html(one_num);
        var two_num = addPlanAmonut(code, 2);
        $("#" + "two_num_" + code).html(two_num);
        var three_num = addPlanAmonut(code, 3);
        $("#" + "three_num_" + code).html(three_num);

        var code = "balance";
        var one_num = addPlanAmonut(code, 1);
        $("#" + "one_num_" + code).html(one_num);
        var two_num = addPlanAmonut(code, 2);
        $("#" + "two_num_" + code).html(two_num);
        var three_num = addPlanAmonut(code, 3);
        $("#" + "three_num_" + code).html(three_num);

    }

    //提交
    function sub() {
        //校验
        var reqUnit = $("#reqUnit").val();

        var tbReqDetail = $("#form1").serialize();

        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            top.Dialog.alert("请检查数据正确性");
            return;
        }

        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqDetail/update.htm",
            data: {"reqUnit": reqUnit, "tbReqDetail": tbReqDetail, "reqId": reqId, "state": 2},
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("更新成功!", function () {
                        var menu_id = parent.getCurrentTabId();
                        if (menu_id == undefined) {
                            top.Dialog.close();
                            return;
                        }
                        var menu_frame_id = "page_" + menu_id;
                        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                        top.Dialog.close();
                    });
                } else {
                    if (result.code == '403') {
                        top.Dialog.alert("所属月份计划已存在");
                    } else {
                        top.Dialog.alert("新增失败");
                    }
                }
            },
            error: function (result) {
                top.Dialog.alert("请求异常");
            }
        });
    }

    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqDetail/getReqDetailList.htm",
            data: {"reqId": reqId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbReqDetailList = result.tbReqDetailList;
                    for (var i = 0; i < tbReqDetailList.length; i++) {
                        var tbReqDetail = tbReqDetailList[i];
                        var reqdCombCode = tbReqDetail.reqdCombCode;
                        var reqdExpnum = tbReqDetail.reqdExpnum;
                        var reqdReqnum = tbReqDetail.reqdReqnum;
                        var reqdRate = tbReqDetail.reqdRate;
                        var reqdBalance = tbReqDetail.reqdBalance;
                        var level = tbReqDetail.reqdRefId;

                        $("#" + reqdCombCode + "_expNum_" + level).val(reqdExpnum);
                        $("#" + reqdCombCode + "_reqNum_" + level).val(reqdReqnum);
                        $("#" + reqdCombCode + "_rate_" + level).val(reqdRate);
                        $("#" + reqdCombCode + "_balance_" + level).val(reqdBalance);
                    }
                    initPlanAmount();
                    countAmount();
                }
            }
        });

        //横竖加和
        $(".planamonut").change(function () {
            var id = $(this).attr('id');
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
        $(".planamonut").focus(function () {
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".planamonut").blur(function () {
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });

        $("#plan").FrozenTable(1, 0, 1, 0);
    });


    //初始化合计数
    function countAmount() {
        //贷种组合求和
        var planamonut_comb = $(".planamonut_comb");
        $(planamonut_comb).each(function () {
            var id = $(this).attr('id');
            var columnId = id + "_column";
            var columnAmount = countClass(id);
            $('span[id=' + columnId + ']').html(columnAmount);

        });
        //机构求和
        var planamonut_organ = $(".planamonut_organ");
        $(planamonut_organ).each(function () {
            var id = $(this).attr('id');
            var rowId = id + "_row";
            var rowAmount = countClass(id);
            $('span[id=' + rowId + ']').html(rowAmount);
        });
        //总和
        var countAmount = addPlanAmonut();
        $('span[id=row_column_count]').html(countAmount);
    }


    //计算所有class的值的和
    function countClass(str) {
        str = "." + str;
        var amonutList = $(str);
        var amount = 0;
        $(amonutList).each(function () {
            var id = this.id;
            if (id.split("_")[1] == "rate") {
            } else {
                amount = accAdd(amount, this.value);
            }
        });
        return transFormat(amount);
    }

    function transFormat(amount) {
        var amountStr = String(amount);
        if (amountStr.indexOf("-") > 0) {
            amountStr = '0' + String(Number(amountStr) + 1).substr(1);
        } else if (amountStr.indexOf("-") == 0) {

            if (amountStr.substr(1).indexOf("-") > 0) {
                amountStr = '-0' + String(Number(amountStr.substr(1)) + 1).substr(1);
            }
        }
        return amountStr;
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
        var temp = (accMul(arg1, m) + accMul(arg2, m)) / m
        return temp;
    }


    // 计算调整金额的总和
    function addPlanAmonut(code, level) {
        //level 1;2;3
        //code expNum;reqNum;rate;balance
        var amonutList = $(".planamonut");
        var amountCount = 0;
        $(amonutList).each(function () {
            var id = this.id;
            if (id.split("_")[1] == "rate") {
            } else if (id.split("_")[1] == code) {
                if (id.split("_")[2] == level) {
                    amountCount = accAdd(amountCount, this.value);
                }
            }
        });
        return amountCount;
    }


    //js计算乘法
    function accMul(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length
        } catch (e) {
        }
        try {
            m += s2.split(".")[1].length
        } catch (e) {
        }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    //JS计算除法
    function accDiv(arg1, arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length
        } catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length
        } catch (e) {
        }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""))
            r2 = Number(arg2.toString().replace(".", ""))
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }


</script>


<body>
<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                <input id="reqId" name="reqId" type="hidden" value="${TbReqListDTO.reqId}"/>
                <input id="reqName" name="reqName" type="hidden" value="${TbReqListDTO.reqName}"> </input>
                ${TbReqListDTO.reqName}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="reqUnit" name="reqUnit" type="hidden" value="${TbReqListDTO.reqUnit}"> </input>
                <c:if test="${TbReqListDTO.reqUnit ==1}">
                    万元/%
                </c:if>
                <c:if test="${TbReqListDTO.reqUnit ==2}">
                    亿元/%
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                净增量周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.reqTimeStart}
            </td>

            <td align="right">
                净增量周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.reqTimeEnd}
            </td>
        </tr>

        <tr>
            <td align="right">
                到期量周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.expTimeStart}
            </td>

            <td align="right">
                到期量周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.expTimeEnd}
            </td>
        </tr>


        <tr>
            <td align="right">
                利率周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.rateTimeStart}
            </td>

            <td align="right">
                利率周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.rateTimeEnd}
            </td>
        </tr>


        <tr>
            <td align="right">
                余额周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.balanceTimeStart}
            </td>

            <td align="right">
                余额周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.balanceTimeEnd}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${TbReqListDTO.reqNote}
            </td>
        </tr>
    </table>
</form>

<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150">贷种名称</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <input type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                            ${combAmountName.name}
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <input type="hidden" class="planamonut_organ" id="${comb.combCode}"/>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${comb.combCode}_${combAmountName.code}" AUTOCOMPLETE="off"
                                   class="planamonut ${comb.combCode } ${combAmountName.code}"
                                   onkeyup='upperCase(this,"${combAmountName.code}","${comb.combLevel}")' value="0"
                                   maxlength="16"
                                   type="text" id="${comb.combCode}_${combAmountName.code}_${comb.combLevel}"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            <tr>
                <td>一级贷种组合合计</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center"><span id="one_num_${combAmountName.code}">0</span></td>
                </c:forEach>

            </tr>
            <tr>
                <td>二级贷种组合合计</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center"><span id="two_num_${combAmountName.code}">0</span></td>
                </c:forEach>
            </tr>
            <tr>
                <td>三级贷种组合合计</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center"><span id="three_num_${combAmountName.code}">0</span></td>
                </c:forEach>
            </tr>
            <tr>
                <td> 合计</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <span id="${combAmountName.code}_column">0</span>
                    </td>
                </c:forEach>
                </td>
            </tr>
        </table>
    </div>
    <div align="center">
        <button type="button" onclick="sub()" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</form>
</body>
</html>