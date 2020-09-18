<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>机构需求详情</title>
</head>
<script type="text/javascript">


    var reqId = '${reqId}';
    var tbReqDetailList = '${tbReqDetailList}';
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
                        $("#" + reqdCombCode + "_expNum_copy_" + level).html(reqdExpnum);
                        $("#" + reqdCombCode + "_reqNum_copy_" + level).html(reqdReqnum);
                        $("#" + reqdCombCode + "_rate_copy_" + level).html(reqdRate);
                        $("#" + reqdCombCode + "_balance_copy_" + level).html(reqdBalance);

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
    })

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
    function accMul(arg1, arg2)
    {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
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
            <td colspan="3"  style="word-break:break-all">
                ${TbReqListDTO.reqNote}
            </td>
        </tr>
    </table>
</form>

<form id="form1" style="overflow-x: auto;overflow-y: auto">
    <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
        <tr>
            <th trueWidth="150"></th>
            <c:forEach items="${combAmountNameList}" var="combAmountName">
                <th align="center">
                    <input type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                        ${combAmountName.name}
                </th>
            </c:forEach>
        </tr>

        <c:forEach items="${combList}" var="comb">
            <tr>
                <input type="hidden" class="planamonut_organ" id="${comb.combCode}"/>
                <th> ${comb.combName}</th>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <div>
                            <span value="0"
                                  type="text" id="${comb.combCode}_${combAmountName.code}_copy_${comb.combLevel}"/>
                        </div>
                        <div>
                            <input name="${comb.combCode}_${combAmountName.code}"
                                   type="hidden" class="planamonut ${comb.combCode } ${combAmountName.code}"
                                   id="${comb.combCode}_${combAmountName.code}_${comb.combLevel}" readOnly="readOnly"/>
                        </div>
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