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

    var reqresetId = '${reqresetId}';
    var one_oldTotalNum = ${one_oldTotalNum};
    var two_oldTotalNum = ${two_oldTotalNum};
    var three_oldTotalNum = ${three_oldTotalNum};

    //提交
    function sub(testNum) {
        //校验
        var reqresetUnit = $("#reqresetUnit").val();
        var reqresetId = $("#reqresetId").val();
        var tbreqresetDetail = $("#form1").serialize();
        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            top.Dialog.alert("请检查数据正确性");
            return;
        }
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqresetDetail/insert.htm",
            data: {
                "reqresetUnit": reqresetUnit,
                "tbreqresetDetail": tbreqresetDetail,
                "reqresetId": reqresetId,
                "state": 2,
                "testNum": testNum
            },
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("新增成功!", function () {
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
                    } else if (result.code == '500') {
                        top.Dialog.alert("当前批次的需求调整已录入，请转至菜单查看", function () {
                            var menu_id = parent.getCurrentTabId();
                            if (menu_id == undefined) {
                                top.Dialog.close();
                                return;
                            }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                    } else if (result.code == '505') {

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


    //用户只能输入正负数与小数
    function upperCase(obj, level) {
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }


        if (level == '1') {
            var one_newAmount = accAdd(one_oldTotalNum, addPlanAmonut(level));
            $("#one_newNum").html(one_newAmount);
            $("#one_num").html(addPlanAmonut(level));
        } else if (level == '2') {
            var two_newAmount = accAdd(two_oldTotalNum, addPlanAmonut(level));
            $("#two_newNum").html(two_newAmount);
            $("#two_num").html(addPlanAmonut(level));
        } else if (level == '3') {
            var three_newAmount = accAdd(three_oldTotalNum, addPlanAmonut(level));
            $("#three_newNum").html(three_newAmount);
            $("#three_num").html(addPlanAmonut(level));
        }

        var thisId = obj.id.split("_")[0];
        var oldNum = document.getElementById(thisId + "_oldNum").innerHTML;
        var newNum = accAdd(obj.value, oldNum);
        $("#" + thisId + "_newNum").html(newNum);


    }


    // 计算调整金额的总和
    function addPlanAmonut(level) {
        var amonutList = $(".planAmount");
        var amountCount = 0;
        $(amonutList).each(function () {
            var id = this.id;
            if (id.split("_")[1] == "oldNum" || id.split("_")[1] == "newNum") {
            } else {
                if (id.split("_")[2] == level) {
                    amountCount = accAdd(amountCount, this.value);
                }
                var thisId = this.id.split("_")[0];
                var oldNum = document.getElementById(thisId + "_oldNum").innerHTML;
                var newNum = accAdd(this.value, oldNum);
                $("#" + thisId + "_newNum").html(newNum);
            }
        });
        if ("" == amountCount) {
            return 0;
        }
        return amountCount;
    }

    $(function () {
        sub(9999);
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqresetDetail/getReqDetailList.htm",
            data: {"reqresetId": reqresetId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbreqresetDetailList = result.tbreqresetDetailList;
                    for (var i = 0; i < tbreqresetDetailList.length; i++) {
                        var tbreqresetDetail = tbreqresetDetailList[i];
                        var reqresetdCombCode = tbreqresetDetail.reqdresetCombCode;
                        var level = tbreqresetDetail.reqdresetRefId;
                        var reqdresetNum = tbreqresetDetail.reqdresetNum;
                        $("#" + reqresetdCombCode + "_Num_" + level).val(reqdresetNum);

                        var thisId = obj.id.split("_")[0];
                        var oldNum = document.getElementById(thisId + "_oldNum").innerHTML;
                        var newNum = accAdd(obj.value, oldNum);
                        $("#" + thisId + "_newNum").html(newNum);

                    }
                }
                initPlanAmount();
                $("#plan").FrozenTable(1, 0, 1, 0);
            }
        });
    });

    function initPlanAmount() {
        var one_newAmount = accAdd(one_oldTotalNum, addPlanAmonut(1));
        $("#one_newNum").html(one_newAmount);
        $("#one_num").html(addPlanAmonut(1));
        var two_newAmount = accAdd(two_oldTotalNum, addPlanAmonut(2));
        $("#two_newNum").html(two_newAmount);
        $("#two_num").html(addPlanAmonut(2));
        var three_newAmount = accAdd(three_oldTotalNum, addPlanAmonut(3));
        $("#three_newNum").html(three_newAmount);
        $("#three_num").html(addPlanAmonut(3));
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
        } else if (amountStr.indexOf("-") == 0) {

            if (amountStr.substr(1).indexOf("-") > 0) {
                amountStr = '-0' + String(Number(amountStr.substr(1)) + 1).substr(1);
            }
        }
        return amountStr;
    }


    //js计算乘法
    function accMul(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length;
        } catch (e) {
        }
        try {
            m += s2.split(".")[1].length;
        } catch (e) {
        }

        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }

    //JS计算除法
    function accDiv(arg1, arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length;
        } catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length;
        } catch (e) {
        }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""));
            r2 = Number(arg2.toString().replace(".", ""));
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
                <input id="reqresetId" name="reqresetId" type="hidden" value="${TbreqresetListDTO.reqresetId}"/>
                <input id="reqresetName" name="reqresetName" type="hidden"
                       value="${TbreqresetListDTO.reqresetName}"> </input>
                ${TbreqresetListDTO.reqresetName}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="reqresetUnit" name="reqresetUnit" type="hidden"
                       value="${TbreqresetListDTO.reqresetUnit}"> </input>
                <c:if test="${TbreqresetListDTO.reqresetUnit ==1}">
                    万元
                </c:if>
                <c:if test="${TbreqresetListDTO.reqresetUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                周期开始时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetTimeStart}
            </td>

            <td align="right">
                周期结束时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetTimeEnd}
            </td>
        </tr>

        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${TbreqresetListDTO.reqresetNote}
            </td>
        </tr>
    </table>
</form>


<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" tdTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150"></td>
                <td align="center">
                    原计划
                </td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                            ${combAmountName.name}
                    </td>
                </c:forEach>
                <td align="center">
                    调整后计划
                </td>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span id="${comb.combCode}_oldNum">${comb.oldNum}</span>
                        </td>
                    </c:forEach>

                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${comb.combCode}_${combAmountName.code}_${comb.combLevel}" AUTOCOMPLETE="off"
                                   class="planAmount"
                                   onkeyup='upperCase(this,"${comb.combLevel}")' value="0" maxlength="16"
                                   type="text" id="${comb.combCode}_${combAmountName.code}_${comb.combLevel}"/>
                        </td>
                    </c:forEach>

                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span id="${comb.combCode}_newNum">0</span>
                        </td>
                    </c:forEach>
                </tr>

            </c:forEach>
            <tr>
                <td>一级贷种组合合计</td>
                <td align="center"><span id="one_oldNum">${one_oldTotalNum}</span></td>
                <td align="center"><span id="one_num">0</span></td>
                <td align="center"><span id="one_newNum">0</span></td>
            </tr>
            <tr>
                <td>二级贷种组合合计</td>
                <td align="center"><span id="two_oldNum">${two_oldTotalNum}</span></td>
                <td align="center"><span id="two_num">0</span></td>
                <td align="center"><span id="two_newNum">0</span></td>
            </tr>
            <tr>
                <td>三级贷种组合合计</td>
                <td align="center"><span id="three_oldNum">${three_oldTotalNum}</span></td>
                <td align="center"><span id="three_num">0</span></td>
                <td align="center"><span id="three_newNum">0</span></td>
            </tr>
        </table>
    </div>
    <div align="center">
        <button type="button" onclick="sub(1)" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</form>
</body>
</html>