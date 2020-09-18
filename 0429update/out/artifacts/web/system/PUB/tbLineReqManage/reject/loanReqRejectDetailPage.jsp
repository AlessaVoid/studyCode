<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>条线需求详情</title>
</head>
<script type="text/javascript">


    var lineReqId = '${lineReqId}';
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
    })


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
        return transFormat( (accMul(arg1, m) + accMul(arg2, m)) / m)
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

<form id="form1" style="overflow-x: auto;overflow-y: auto">
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
                    <td>
                        <c:if test="${comment.type =='待审批'}">
                            驳回待提交
                        </c:if>
						<c:if test="${comment.type !='待审批'}">
                            ${comment.type}
                        </c:if>
                    </td>
                    <td style="word-break:break-all">${comment.fullMessage }</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>
</form>
</body>
</html>