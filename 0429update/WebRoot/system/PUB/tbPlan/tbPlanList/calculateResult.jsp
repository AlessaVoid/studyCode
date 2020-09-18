<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/stepper.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>

</head>
<body>
<form id="form4">
    <div id="scrollContent1" class="border_gray"
         style="height: 500px;overflow:auto;line-height:150%;position:relative;z-index:0;">
        <table id="result_1" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td></td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td width="100" style="word-break:break-all">
                            ${combAmountName.name}
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${organList}" var="organ">
                <tr>
                    <td> ${organ.organName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                                    <span value="0"
                                          type="text" id="${organ.organCode}_${combAmountName.code}"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</form>


</body>

<script>

    var planMonth =${planMonth};
    //初始化表格数据
    $(function () {

        //------------------一下
        $.ajax({
            type: "POST",
            url: "<%=path%>/creditPlan/getCalculatelData.htm",
            data: {"planMonth": planMonth},
            dataType: "json",
            success: function (result) {
                if (result) {
                    $('#form4').render();
                    var tbCalculateOneResultList = result.tbCalculateOneResultList;
                    for (var i = 0; i < tbCalculateOneResultList.length; i++) {
                        var tbCalculateOneResult = tbCalculateOneResultList[i];
                        var organcode = tbCalculateOneResult.organcode;
                        var reqAmount = tbCalculateOneResult.reqAmount;
                        var planWeight = tbCalculateOneResult.planWeight;
                        var planAmount = tbCalculateOneResult.planAmount;

                        var depositWeight = tbCalculateOneResult.depositWeight;
                        var structWeight = tbCalculateOneResult.structWeight;
                        var marketWeight = tbCalculateOneResult.marketWeight;
                        var benefitWeight = tbCalculateOneResult.benefitWeight;


                        var code1Result = tbCalculateOneResult.code1Result;
                        var code2Result = tbCalculateOneResult.code2Result;
                        var code3Result = tbCalculateOneResult.code3Result;
                        var code4Result = tbCalculateOneResult.code4Result;
                        var code5Result = tbCalculateOneResult.code5Result;
                        var code6Result = tbCalculateOneResult.code6Result;
                        var code7Result = tbCalculateOneResult.code7Result;

                        var code8Result = tbCalculateOneResult.code8Result;
                        var code9Result = tbCalculateOneResult.code9Result;
                        var code10Result = tbCalculateOneResult.code10Result;
                        var code11Result = tbCalculateOneResult.code11Result;
                        var code12Result = tbCalculateOneResult.code12Result;
                        var code13Result = tbCalculateOneResult.code13Result;
                        var code14Result = tbCalculateOneResult.code14Result;

                        var code15Result = tbCalculateOneResult.code15Result;
                        var code16Result = tbCalculateOneResult.code16Result;
                        var code17Result = tbCalculateOneResult.code17Result;
                        var code18Result = tbCalculateOneResult.code18Result;
                        var code19Result = tbCalculateOneResult.code19Result;
                        var code20Result = tbCalculateOneResult.code20Result;
                        var code21Result = tbCalculateOneResult.code21Result;

                        var code22Result = tbCalculateOneResult.code22Result;
                        var code23Result = tbCalculateOneResult.code23Result;
                        var code24Result = tbCalculateOneResult.code24Result;
                        var code25Result = tbCalculateOneResult.code25Result;
                        var code26Result = tbCalculateOneResult.code26Result;
                        var code27Result = tbCalculateOneResult.code27Result;
                        var code28Result = tbCalculateOneResult.code28Result;

                        var code29Result = tbCalculateOneResult.code29Result;
                        var code30Result = tbCalculateOneResult.code30Result;
                        var code31Result = tbCalculateOneResult.code31Result;
                        var code32Result = tbCalculateOneResult.code32Result;
                        var code33Result = tbCalculateOneResult.code33Result;
                        var code34Result = tbCalculateOneResult.code34Result;
                        var code35Result = tbCalculateOneResult.code35Result;

                        $("#" + organcode + "_" + "reqAmount").html(reqAmount);
                        $("#" + organcode + "_" + "planWeight").html(planWeight);
                        $("#" + organcode + "_" + "planAmount").html(planAmount);

                        $("#" + organcode + "_" + "depositWeight").html(depositWeight);
                        $("#" + organcode + "_" + "structWeight").html(structWeight);
                        $("#" + organcode + "_" + "marketWeight").html(marketWeight);
                        $("#" + organcode + "_" + "benefitWeight").html(benefitWeight);


                        $("#" + organcode + "_" + "code1").html(code1Result);
                        $("#" + organcode + "_" + "code2").html(code2Result);
                        $("#" + organcode + "_" + "code3").html(code3Result);
                        $("#" + organcode + "_" + "code4").html(code4Result);
                        $("#" + organcode + "_" + "code5").html(code5Result);
                        $("#" + organcode + "_" + "code6").html(code6Result);
                        $("#" + organcode + "_" + "code7").html(code7Result);


                        $("#" + organcode + "_" + "code8").html(code8Result);
                        $("#" + organcode + "_" + "code9").html(code9Result);
                        $("#" + organcode + "_" + "code10").html(code10Result);
                        $("#" + organcode + "_" + "code11").html(code11Result);
                        $("#" + organcode + "_" + "code12").html(code12Result);
                        $("#" + organcode + "_" + "code13").html(code13Result);
                        $("#" + organcode + "_" + "code14").html(code14Result);


                        $("#" + organcode + "_" + "code15").html(code15Result);
                        $("#" + organcode + "_" + "code16").html(code16Result);
                        $("#" + organcode + "_" + "code17").html(code17Result);
                        $("#" + organcode + "_" + "code18").html(code18Result);
                        $("#" + organcode + "_" + "code19").html(code19Result);
                        $("#" + organcode + "_" + "code20").html(code20Result);
                        $("#" + organcode + "_" + "code21").html(code21Result);


                        $("#" + organcode + "_" + "code22").html(code22Result);
                        $("#" + organcode + "_" + "code23").html(code23Result);
                        $("#" + organcode + "_" + "code24").html(code24Result);
                        $("#" + organcode + "_" + "code25").html(code25Result);
                        $("#" + organcode + "_" + "code26").html(code26Result);
                        $("#" + organcode + "_" + "code27").html(code27Result);
                        $("#" + organcode + "_" + "code28").html(code28Result);


                        $("#" + organcode + "_" + "code29").html(code29Result);
                        $("#" + organcode + "_" + "code30").html(code30Result);
                        $("#" + organcode + "_" + "code31").html(code31Result);
                        $("#" + organcode + "_" + "code32").html(code32Result);
                        $("#" + organcode + "_" + "code33").html(code33Result);
                        $("#" + organcode + "_" + "code34").html(code34Result);
                        $("#" + organcode + "_" + "code35").html(code35Result);

                    }

                    //冻结行列 行首 行末 列首 列末
                    $("#result_1").FrozenTable(1, 0, 1, 0);

                }
            }
        });

        $("#result_1").FrozenTable(1, 0, 1, 0);
        //-------以上

    });


</script>

</html>