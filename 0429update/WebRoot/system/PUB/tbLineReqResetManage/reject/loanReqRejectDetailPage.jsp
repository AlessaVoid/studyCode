<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>条线需求调整详情</title>
</head>
<script type="text/javascript">


    var lineReqresetId = '${lineReqresetId}';
    // 计算调整金额的总和
    function addPlanAmonut() {
        var amonutList = $(".planAmount");
        var amountCount = 0;
        $(amonutList).each(function () {
            var id = this.id;
            if (id.split("_")[1] == "oldNum" || id.split("_")[1] == "newNum") {
            } else {

                amountCount = Number(amountCount) + Number(this.value);
                var thisId = this.id.split("_")[0];
                var oldNum=  document.getElementById( thisId + "_oldNum").innerHTML;
                var newNum = Number(this.value ) + Number(oldNum);
                $("#" + thisId + "_newNum").html(newNum);
            }
        });
        return amountCount;
    }
    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbLineReqResetDetail/getReqDetailList.htm",
            data: {"lineReqresetId": lineReqresetId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbLineReqResetDetailDTOS = result.tbLineReqResetDetailDTOS;
                    for (var i = 0; i < tbLineReqResetDetailDTOS.length; i++) {
                        var tbReqDetail = tbLineReqResetDetailDTOS[i];
                        var reqdCombCode = tbReqDetail.lineCombCode;
                        var reqdNum = tbReqDetail.lineNum;
                        var newPlan = tbReqDetail.newPlan;
                        var oldPlan = tbReqDetail.oldPlan;
                        $("#" + reqdCombCode + "_newNum").html(newPlan);
                        $("#" + reqdCombCode + "_oldNum").html(oldPlan);
                        $("#" + reqdCombCode + "_num").html(reqdNum);
                        $("#" + reqdCombCode + "_numCopy").val(reqdNum);
                    }
                }
                addPlanAmonut();
            }
        });
    })


</script>

<body>
<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="lineResetrefId" name="lineResetrefId" type="hidden" value="${tbLineReqresetDetail.lineResetrefId}"/>
                <input id="lineReqresetId" name="lineReqresetId" type="hidden" value="${tbLineReqresetDetail.lineReqresetId}"/>
                <input id="lineReqMonth" name="lineReqMonth" type="hidden" value="${tbLineReqresetDetail.lineReqName}"> </input>
                ${tbLineReqresetDetail.lineReqMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="lineUnit" name="lineUnit" type="hidden" value="${tbLineReqresetDetail.lineUnit}"> </input>
                <c:if test="${tbLineReqresetDetail.lineUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbLineReqresetDetail.lineUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                ${tbLineReqresetDetail.lineReqName}
            </td>

            <td align="right">
                条线名称：
            </td>
            <td>
                ${tbLineReqresetDetail.lineName}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${tbLineReqresetDetail.lineReqNote}
            </td>
        </tr>
    </table>
</form>

<form id="form1" style="overflow-x: auto;overflow-y: auto">
    <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
        <tr>
            <th trueWidth="150"></th>
            <th align="center">
                原计划
            </th>
            <c:forEach items="${combAmountNameList}" var="combAmountName">
                <th align="center">
                        ${combAmountName.name}
                </th>
            </c:forEach>
            <th align="center">
                调整后计划
            </th>
        </tr>

        <c:forEach items="${combList}" var="comb">
            <tr>
                <th> ${comb.combName}</th>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <span id="${comb.combCode}_oldNum">${comb.oldNum}</span>
                    </td>
                </c:forEach>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <div>
                            <span value="0"
                                  type="text" id="${comb.combCode}_${combAmountName.code}"/>
                        </div>
                        <div>
                            <input name="${comb.combCode}_${combAmountName.code}"
                                   class="planAmount"
                                   value="0" maxlength="16" AUTOCOMPLETE="off" hidden="hidden"
                                   type="text" id="${comb.combCode}_${combAmountName.code}Copy"/>
                        </div>
                    </td>
                </c:forEach>

                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <span id="${comb.combCode}_newNum">0</span>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
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