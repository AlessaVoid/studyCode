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
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <!-- 树形下拉框end -->
    <title></title>
</head>
<script type="text/javascript">
    var id =${TbPunishList.id};

    function submitInfo() {

        return doSubmit('form2', '<%=path%>/tbTradeManger/tbPunishList/update.htm');
    }


    $(function () {

        $("#table_1").FrozenTable(1 ,0,1,0);
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbPunishList/getReqDetailList.htm",
            data: {"id": id},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbPunishResultList = result.tbPunishResultList;
                    for (var i = 0; i < tbPunishResultList.length; i++) {
                        var tbPunishResult = tbPunishResultList[i];
                        var organCode = tbPunishResult.organCode;
                        var state=tbPunishResult.state;
                        var stateStr ="";
                        if(state==2){
                            stateStr="未反馈";
                        }else if(state==8) {
                            stateStr="已反馈";
                        }else if(state==1) {
                            stateStr="未下发";
                        }
                        $("#" + organCode + "_" + "state").html(stateStr);
                        $("#" + organCode + "_" + "planMount").html(tbPunishResult.planMount);
                        $("#" + organCode + "_" + "monthVacancyAmt").html(tbPunishResult.monthVacancyAmt);
                        $("#" + organCode + "_" + "monthVacancyRate").html(tbPunishResult.monthVacancyRate);
                        $("#" + organCode + "_" + "monthFiveVacancy").html(tbPunishResult.monthFiveVacancy);
                        $("#" + organCode + "_" + "monthShitiPlanMount").html(tbPunishResult.monthShitiPlanMount);
                        $("#" + organCode + "_" + "monthShitiOverAmt").html(tbPunishResult.monthShitiOverAmt);
                        $("#" + organCode + "_" + "monthShitiOverRate").html(tbPunishResult.monthShitiOverRate);
                        $("#" + organCode + "_" + "monthFiveShitiOver").html(tbPunishResult.monthFiveShitiOver);
                        $("#" + organCode + "_" + "monthPiapjuPlanMount").html(tbPunishResult.monthPiapjuPlanMount);
                        $("#" + organCode + "_" + "monthPiaojuOverAmt").html(tbPunishResult.monthPiaojuOverAmt);
                        $("#" + organCode + "_" + "monthPiaojuOverRate").html(tbPunishResult.monthPiaojuOverRate);
                        $("#" + organCode + "_" + "monthFivePiaojuOver").html(tbPunishResult.monthFivePiaojuOver);
                        $("#" + organCode + "_" + "monthTotalPunish").html(tbPunishResult.monthTotalPunish);
                        $("#" + organCode + "_" + "note").html(tbPunishResult.note);

                    }
                }
            }
        });


    })


</script>

<body>
<form id="form2">
    <table class="tableStyle"  width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="id" name="id" value="${TbPunishList.id}" hidden="hidden"/>
                ${TbPunishList.month}
            </td>


            <td align="right">
                罚息名称：
            </td>
            <td>
                ${TbPunishList.name}
            </td>
        </tr>
        <tr>
            <td align="right">
                意见征集截止时间：
            </td>
            <td>
                ${TbPunishList.collectEndTime}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3" style="word-break:break-all">
                ${TbPunishList.note}
            </td>
        </tr>

    </table>
</form>

<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;" >
        <table id="table_1" class="tableStyle" thTrueWidth="true" mode="list"  >
            <tr>
                <td ></td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td >
                        <div style="width: 220px"> ${combAmountName.name}</div>
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${organList}" var="organ">
                <tr>
                    <td> ${organ.organname}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span value="0"
                                  type="text" id="${organ.organcode}_${combAmountName.code}"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</form>


</body>
</html>