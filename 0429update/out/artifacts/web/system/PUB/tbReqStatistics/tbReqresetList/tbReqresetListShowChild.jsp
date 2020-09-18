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
    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqresetList/getReqDetailList.htm",
            data: {"reqresetId": reqresetId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbReqresetDetails = result.tbReqresetDetails;
                    console.log(tbReqresetDetails);
                    for (var i = 0; i < tbReqresetDetails.length; i++) {
                        var tbReqresetDetail = tbReqresetDetails[i];
                        var oldPlan = tbReqresetDetail.oldPlan;
                        var newPlan = tbReqresetDetail.newPlan;
                        var reqdresetOrgan = tbReqresetDetail.reqdresetOrgan;
                        var reqdresetCombCode = tbReqresetDetail.reqdresetCombCode;
                        var reqdresetNum = tbReqresetDetail.reqdresetNum;
                        var time = tbReqresetDetail.reqdresetUpdatetime;
                        var totalOldPlan = tbReqresetDetail.totalOldPlan;
                        var totalNewPlan = tbReqresetDetail.totalNewPlan;
                        var totalNum = tbReqresetDetail.totalNum;
                        if (totalOldPlan == 0) {
                            $("#" + reqdresetOrgan + "_totalOldPlan").html("无");
                        } else {
                            $("#" + reqdresetOrgan + "_totalOldPlan").html(totalOldPlan);
                        }
                        $("#" + reqdresetOrgan + "_totalNewPlan").html(totalNewPlan);
                        $("#" + reqdresetOrgan + "_totalNum").html(totalNum);
                        $("#" + reqdresetOrgan + "_time").html(time);
                        $("#" + reqdresetOrgan + "_" + reqdresetCombCode).html(reqdresetNum);
                        if (oldPlan == 0) {
                            $("#" + reqdresetOrgan + "_" + reqdresetCombCode + "_" + "oldPlan").html("无");
                        } else {
                            $("#" + reqdresetOrgan + "_" + reqdresetCombCode + "_" + "oldPlan").html(oldPlan);
                        }
                        $("#" + reqdresetOrgan + "_" + reqdresetCombCode + "_" + "newPlan").html(newPlan);
                    }
                    $("#plan").FrozenTable(2, 0, 1, 0);
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
                ${tbReqresetList.reqresetMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="lineUnit" name="lineUnit" type="hidden" value="${tbReqresetList.reqresetUnit}"> </input>
                <c:if test="${tbReqresetList.reqresetUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbReqresetList.reqresetUnit ==2}">
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
                ${tbReqresetList.reqresetName}
            </td>

        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${tbReqresetList.reqresetNote}
            </td>
        </tr>

        <tr>
            <td align="right" colspan="1">
                导出表格：
            </td>
            <td colspan="1">
                <a href="<%=path%>/tbTradeManger/tbReqresetList/exportresetExcel.htm?reqresetId=${reqresetId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
            </td>
        </tr>
    </table>
</form>


<form id="form1">
    <div id="scrollContent" class="border_gray"  style="height: 500px;">
        <table id="plan" class="tableStyle" tdTrueWidth="true" mode="list" fixedCellHeight="true" >
            <tr>
                <td rowspan="2">
                    <div style="width: 150px;">贷种组合名称</div>
                </td>
                <td rowspan="2">
                    <div style="width: 150px;">上报时间</div>
                </td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td colspan="3" align="center">
                            ${combAmountName.name}
                    </td>
                </c:forEach>
                <td colspan="3" align="center">合计</td>
            </tr>
            <tr>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center" trueWidth="100">
                        <div style="width: 66px">原计划</div>
                    </td>
                    <td align="center" trueWidth="100">
                        <div style="width: 66px">调整量</div>
                    </td>
                    <td align="center" trueWidth="100">
                        <div style="width: 66px">调整后计划</div>
                    </td>
                </c:forEach>
                <td align="center" trueWidth="100">
                    <div style="width: 66px">原计划</div>
                </td>
                <td align="center" trueWidth="100">
                    <div style="width: 66px">调整量</div>
                </td>
                <td align="center" trueWidth="100">
                    <div style="width: 66px">调整后计划</div>
                </td>
            </tr>

            <c:forEach items="${organList}" var="organ">
                <tr>
                    <td> ${organ.organName}</td>
                    <td> <span value="时间"
                               type="text" id="${organ.organCode}_time"/></td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span value="0"
                                  type="text" id="${organ.organCode}_${combAmountName.code}_oldPlan"/>
                        </td>
                        <td align="center">
                            <span value="0"
                                  type="text" id="${organ.organCode}_${combAmountName.code}"/>
                        </td>
                        <td align="center">
                            <span value="0"
                                  type="text" id="${organ.organCode}_${combAmountName.code}_newPlan"/>
                        </td>
                    </c:forEach>
                    <td align="center">
                            <span value="0"
                                  type="text" id="${organ.organCode}_totalOldPlan"/>
                    </td>
                    <td align="center">
                            <span value="0"
                                  type="text" id="${organ.organCode}_totalNum"/>
                    </td>
                    <td align="center">
                            <span value="0"
                                  type="text" id="${organ.organCode}_totalNewPlan"/>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>
</form>

</body>
</html>