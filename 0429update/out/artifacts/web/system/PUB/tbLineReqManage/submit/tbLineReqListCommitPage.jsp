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
    <title></title>
</head>
<script type="text/javascript">

    var lineReqId = '${lineReqId}';
    $(function () {
        initAuditOper();
        $(".submitButton").addClass("button");
        $(".submitButton").append("<span class='icon_save_draft'>提交</span>");
        $(".button").css("width", "90px");
        $(".button").css("cursor", "pointer");

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
                        $("#" + reqdCombCode + "_expNum_copy").html(reqdExpnum);
                        $("#" + reqdCombCode + "_reqNum_copy").html(reqdReqnum);
                        $("#" + reqdCombCode + "_rate_copy").html(reqdRate);
                        $("#" + reqdCombCode + "_balance_copy").html(reqdBalance);

                        $("#" + reqdCombCode + "_expNum").val(reqdExpnum);
                        $("#" + reqdCombCode + "_reqNum").val(reqdReqnum);
                        $("#" + reqdCombCode + "_rate").val(reqdRate);
                        $("#" + reqdCombCode + "_balance").val(reqdBalance);
                    }
                }
                countAmount();
            }
        });

    });

    function initAuditOper() {
        $.ajax({
                url: "<%=path%>/TbLineReqApp/getOperInfoListByRolecode.htm?rolecode=${rolecode}"+"&lineReqId="+lineReqId,
                method: "GET",
                async: false,
                success: result => {
                    var list = [];
                    result.forEach(oper => {
                        var item = {
                            "key": oper.opername,
                            "value": oper.opercode
                        };
                        list.push(item);
                    });
                    var selData = {
                        "list": list
                    };
                    $("#auditOperList").data("data", selData);
                    $("#auditOperList").render();
                }
            }
        );
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
    function submitInfo() {
        var auditOper = $("#auditOperList").val();
        if(auditOper==""){
            top.Dialog.alert("请选择下级审批人!" );
            return;
        }
        var comment =  $("#comment").val();
        if(comment==""){
            top.Dialog.alert("请填写备注!");
            return;
        }
        top.Dialog.confirm("确定要提交审批吗？", function () {
            $("#btn1").attr("disabled","disabled");
            $.ajax({
                url: '<%=path%>/TbLineReqApp/startLoanReqAudit.htm',
                method: 'GET',
                data: {
                    "auditOper": auditOper,
                    "lineReqId": lineReqId,
                    "comment":comment
                }, success: res => {
                    if (res.success === 'true' || res.success === true) {
                        top.Dialog.alert("条线需求提交成功,等待审批结果", () => {
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
                        $("#btn1").removeAttr("disabled");
                        top.Dialog.alert("条线需求提交失败");
                    }
                }
            });
        });

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
                <input id="lineRefId" name="lineRefId" type="hidden" value="${tbLineReqDetail.lineRefId}"/>
                <input id="lineReqId" name="lineReqId" type="hidden" value="${tbLineReqDetail.lineReqId}"/>
                <input id="lineReqMonth" name="lineReqMonth" type="hidden" value="${tbLineReqDetail.lineReqName}"> </input>
                ${tbLineReqDetail.lineReqMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="lineUnit" name="lineUnit" type="hidden" value="${tbLineReqDetail.lineUnit}"> </input>
                <c:if test="${tbLineReqDetail.lineUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbLineReqDetail.lineUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                ${tbLineReqDetail.lineReqName}
            </td>

            <td align="right">
                条线名称：
            </td>
            <td>
                ${tbLineReqDetail.lineName}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${tbLineReqDetail.lineReqNote}
            </td>
        </tr>
    </table>
</form>

<form id="form1">
    <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
        <tr>
            <td trueWidth="150"></td>
            <c:forEach items="${combAmountNameList}" var="combAmountName">
                <td trueWidth="100">
                    <input type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                        ${combAmountName.name}
                </td>
            </c:forEach>
        </tr>

        <c:forEach items="${combList}" var="comb">
            <tr>
                <td> ${comb.combName}</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <div>
                            <span value="0"
                                  type="text" id="${comb.combCode}_${combAmountName.code}_copy"/>
                        </div>
                        <div>
                            <input name="${comb.combCode}_${combAmountName.code}"
                                   type="hidden" class="planamonut ${comb.combCode } ${combAmountName.code}"
                                   id="${comb.combCode}_${combAmountName.code}" readOnly="readOnly"/>
                        </div>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        <td> 合计</td>
        <c:forEach items="${combAmountNameList}" var="combAmountName">
            <td align="center">
                <span id="${combAmountName.code}_column">0</span>
            </td>
        </c:forEach>
        </td>
        <tr>
            <td colspan="5">
                <div align="center">
                    <div style="float: left">下一节点审批人:</div>
                    <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
                </div>
            </td>
        </tr>
        <tr>
            <td>备注:</td>
            <td><textarea id="comment" rows="50" cols="50"></textarea></td>
        </tr>
        <tr>
            <td colspan="5">
                <div align="center">
                    <button type="button" id="btn1" onclick="submitInfo()" class="submitButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>