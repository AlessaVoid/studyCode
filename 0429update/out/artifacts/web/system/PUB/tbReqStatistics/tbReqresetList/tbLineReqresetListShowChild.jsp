<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <title>信贷需求详情审批</title>
</head>
<script type="text/javascript">


    var lineResetrefId = '${lineResetrefId}';
    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqresetList/lineResetReqDetailList.htm",
            data: {"lineResetrefId": lineResetrefId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbLineReqResetDetailDTOS = result.tbLineReqResetDetailDTOS;
                    for (var i = 0; i < tbLineReqResetDetailDTOS.length; i++) {
                        var tbReqDetail = tbLineReqResetDetailDTOS[i];
                        var reqdCombCode = tbReqDetail.lineCombCode;
                        var lineUpdateTime = tbReqDetail.lineUpdateTime;
                        var reqdNum = tbReqDetail.lineNum;
                        var oldPlan = tbReqDetail.oldPlan;
                        var newPlan = tbReqDetail.newPlan;

                        $("#" + reqdCombCode + "_updateTime").html(lineUpdateTime);
                        $("#" + reqdCombCode + "_num").html(reqdNum);
                        $("#" + reqdCombCode + "_oldPlan").html(oldPlan);
                        $("#" + reqdCombCode + "_newPlan").html(newPlan);
                    }
                    $("#plan").FrozenTable(1, 0, 1, 0);
                }
            }
        });
    })


</script>

<body>
<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line" >
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="lineResetrefId" name="lineResetrefId" type="hidden" value="${tbLineReqResetDetail.lineResetrefId}"/>
                <input id="lineReqresetId" name="lineReqresetId" type="hidden" value="${tbLineReqResetDetail.lineReqresetId}"/>
                <input id="lineReqMonth" name="lineReqMonth" type="hidden" value="${tbLineReqResetDetail.lineReqName}"> </input>
                ${tbLineReqResetDetail.lineReqMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="lineUnit" name="lineUnit" type="hidden" value="${tbLineReqResetDetail.lineUnit}"> </input>
                <c:if test="${tbLineReqResetDetail.lineUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbLineReqResetDetail.lineUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                周期开始时间：
            </td>
            <td>
                ${tbReqresetList.reqresetTimeStart}
            </td>

            <td align="right">
                周期结束时间：
            </td>
            <td>
                ${tbReqresetList.reqresetTimeEnd}
            </td>
        </tr>
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                ${tbLineReqResetDetail.lineReqName}
            </td>

            <td align="right">
                条线名称：
            </td>
            <td>
                ${tbLineReqResetDetail.lineName}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${tbLineReqResetDetail.lineReqNote}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                导出表格：
            </td>
            <td colspan="1">
                <a href="<%=path%>/tbTradeManger/tbReqresetList/exportresetLineExcel.htm?lineResetrefId=${lineResetrefId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
            </td>
        </tr>
    </table>
</form>

<form id="form1" style="overflow-x: auto;overflow-y: auto">
    <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
    <input id="where" style="display: none" value="${where}">
    <div id="panel22" panelTitle="详情" class="box2_custom" boxType="box2" startState="open"  style="height: 500px;">
        <table id="plan" class="tableStyle"  mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150">贷种组合名称</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                            ${combAmountName.name}
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span value="0"
                                  type="text" id="${comb.combCode}_${combAmountName.code}">未录入</span>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>

    </div>
</form>
</body>
</html>