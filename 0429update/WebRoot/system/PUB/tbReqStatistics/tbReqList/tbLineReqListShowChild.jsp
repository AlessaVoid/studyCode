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


    var lineRefId = '${lineRefId}';
    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqList/lineReqDetailData.htm",
            data: {"lineRefId": lineRefId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbLineReqDetailDTOS = result.tbLineReqDetailDTOS;
                    for (var i = 0; i < tbLineReqDetailDTOS.length; i++) {
                        var tbReqDetail = tbLineReqDetailDTOS[i];
                        var reqdCombCode = tbReqDetail.lineCombCode;
                        var reqdExpnum = tbReqDetail.lineExpnum;
                        var updateTime = tbReqDetail.lineUpdateTime;
                        var reqdReqnum = tbReqDetail.lineReqnum;
                        var reqdRate = tbReqDetail.lineRate;
                        var reqdBalance = tbReqDetail.lineBalance;

                        $("#" + reqdCombCode + "_updateTime").html(updateTime);
                        $("#" + reqdCombCode + "_expNum").html(reqdExpnum);
                        $("#" + reqdCombCode + "_reqNum").html(reqdReqnum);
                        $("#" + reqdCombCode + "_rate").html(reqdRate);
                        $("#" + reqdCombCode + "_balance").html(reqdBalance);
                    }
                    $("#totalNum_rate").html("-");

                    //冻结行列 行首 行末 列首 列末
                    $("#plan").FrozenTable(1, 0, 1, 0);
                }
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
        <tr>
            <td align="right" colspan="1">
                导出表格：
            </td>
            <td colspan="1">
                <a href="<%=path%>/tbTradeManger/tbReqList/exportLineExcel.htm?lineRefId=${lineRefId}">
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
    <div id="panel22" panelTitle="详情" class="box2_custom" boxType="box2" startState="open" style="height: 500px;">
        <table id="plan" class="tableStyle"  mode="list" fixedCellHeight="true">
            <tr>
                <td >贷种组合名称</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td colspan="1">
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
                                  type="text" id="${comb.combCode}_${combAmountName.code}"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>

    </div>
</form>
</body>
</html>