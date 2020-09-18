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
    <title></title>
</head>
<script type="text/javascript">

    var lineReqId = '${lineReqId}';

    //用户只能输入正负数与小数
    function upperCase(obj, code) {

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
    }


    //提交
    function sub() {
        //校验
        var lineUnit = $("#lineUnit").val();
        var lineReqId = $("#lineReqId").val();
        var lineRefId = $("#lineRefId").val();
        var tbLineReqDetail = $("#form1").serialize();

        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            top.Dialog.alert("请检查数据正确性");
            return;
        }

        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbLineReqDetail/update.htm",
            data: {
                "lineUnit": lineUnit,
                "tbLineReqDetail": tbLineReqDetail,
                "lineReqId": lineReqId,
                "state": 2,
                "lineRefId": lineRefId
            },
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
            url: "<%=path%>/tbTradeManger/tbLineReqDetail/getReqDetailList.htm",
            data: {"lineReqId": lineReqId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbLineReqDetailDTOS = result.tbLineReqDetailDTOS;
                    for (var i = 0; i < tbLineReqDetailDTOS.length; i++) {
                        var tbReqDetail = tbLineReqDetailDTOS[i];
                        var reqdCombCode = tbReqDetail.lineCombCode;
                        var reqdExpnum = tbReqDetail.lineExpnum;
                        var reqdReqnum = tbReqDetail.lineReqnum;
                        var reqdRate = tbReqDetail.lineRate;
                        var reqdBalance = tbReqDetail.lineBalance;

                        $("#" + reqdCombCode + "_expNum").val(reqdExpnum);
                        $("#" + reqdCombCode + "_reqNum").val(reqdReqnum);
                        $("#" + reqdCombCode + "_rate").val(reqdRate);
                        $("#" + reqdCombCode + "_balance").val(reqdBalance);
                    }
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

    });

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
        return amount;
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
        $(amonutList).each(function () {
            var id = this.id;
            if (id.split("_")[1] == "rate") {
            } else {
                amountCount = accAdd(amountCount, this.value);
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
                所属月份：
            </td>
            <td>
                <input id="lineRefId" name="lineRefId" type="hidden" value="${TbLineReqDetail.lineRefId}"/>
                <input id="lineReqId" name="lineReqId" type="hidden" value="${TbLineReqDetail.lineReqId}"/>
                <input id="lineReqMonth" name="lineReqMonth" type="hidden"
                       value="${TbLineReqDetail.lineReqName}"> </input>
                ${TbLineReqDetail.lineReqMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="lineUnit" name="lineUnit" type="hidden" value="${TbLineReqDetail.lineUnit}"> </input>
                <c:if test="${TbLineReqDetail.lineUnit ==1}">
                    万元
                </c:if>
                <c:if test="${TbLineReqDetail.lineUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                ${TbLineReqDetail.lineReqName}
            </td>

            <td align="right">
                条线名称：
            </td>
            <td>
                ${TbLineReqDetail.lineName}
            </td>
        </tr>
        <tr>
            <td align="right">
                净增量周期开始时间：
            </td>
            <td>
                ${TbReqList.reqTimeStart}
            </td>

            <td align="right">
                净增量周期结束时间：
            </td>
            <td>
                ${TbReqList.reqTimeEnd}
            </td>
        </tr>

        <tr>
            <td align="right">
                到期量周期开始时间：
            </td>
            <td>
                ${TbReqList.expTimeStart}
            </td>

            <td align="right">
                到期量周期结束时间：
            </td>
            <td>
                ${TbReqList.expTimeEnd}
            </td>
        </tr>


        <tr>
            <td align="right">
                利率周期开始时间：
            </td>
            <td>
                ${TbReqList.rateTimeStart}
            </td>

            <td align="right">
                利率周期结束时间：
            </td>
            <td>
                ${TbReqList.rateTimeEnd}
            </td>
        </tr>


        <tr>
            <td align="right">
                余额周期开始时间：
            </td>
            <td>
                ${TbReqList.balanceTimeStart}
            </td>

            <td align="right">
                余额周期结束时间：
            </td>
            <td>
                ${TbReqList.balanceTimeEnd}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${TbLineReqDetail.lineReqNote}
            </td>
        </tr>
    </table>
</form>


<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150"></td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <input  type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                            ${combAmountName.name}
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${comb.combCode}_${combAmountName.code}" AUTOCOMPLETE="off"
                                   class="planamonut ${comb.combCode } ${combAmountName.code}"
                                   onkeyup='upperCase(this,"${combAmountName.code}")' value="0"
                                   type="text" id="${comb.combCode}_${combAmountName.code}" maxlength="16"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            <tr>
                <td> 合计</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td>
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