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
    var month = '${month}';
    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbCalculateTwoResult/getDetailList.htm",
            data: {"month": month},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbCalculateTwoResults = result.tbCalculateTwoResults;
                    for (var i = 0; i < tbCalculateTwoResults.length; i++) {
                        var tbTwo = tbCalculateTwoResults[i];
                        var organcode = tbTwo.organcode;
                        $("#" + organcode + "_code1").html(tbTwo.code1);
                        $("#" + organcode + "_code2").html(tbTwo.code2);
                        $("#" + organcode + "_code3").html(tbTwo.code3);
                        $("#" + organcode + "_code4").html(tbTwo.code4);
                        $("#" + organcode + "_code5").html(tbTwo.code5);
                        $("#" + organcode + "_code6").html(tbTwo.code6);
                        $("#" + organcode + "_code7").html(tbTwo.code7);
                        $("#" + organcode + "_code8").html(tbTwo.code8);
                        $("#" + organcode + "_code9").html(tbTwo.code9);
                        $("#" + organcode + "_code10").html(tbTwo.code10);
                        $("#" + organcode + "_code11").html(tbTwo.code11);
                        $("#" + organcode + "_code12").html(tbTwo.code12);
                        $("#" + organcode + "_code13").html(tbTwo.code13);
                        $("#" + organcode + "_code14").html(tbTwo.code14);
                        $("#" + organcode + "_code15").html(tbTwo.code15);
                        $("#" + organcode + "_code16").html(tbTwo.code16);
                        $("#" + organcode + "_code17").html(tbTwo.code17);
                        $("#" + organcode + "_code18").html(tbTwo.code18);
                        $("#" + organcode + "_code20").html(tbTwo.code20);
                        $("#" + organcode + "_code21").html(tbTwo.code21);
                        $("#" + organcode + "_code22").html(tbTwo.code22);
                        $("#" + organcode + "_code23").html(tbTwo.code23);

                    }

                    //冻结行列 行首 行末 列首 列末
                    $("#plan").FrozenTable(1, 0, 1, 0);

                }
            }
        });
    });

</script>


<body>
<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 680px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150">机构名称</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <input type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                            ${combAmountName.name}
                    </td>
                </c:forEach>
                <td>
                    调整值
                </td>
                <td>
                    最终月度计划额度
                </td>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <input type="hidden" class="planamonut_organ" id="${comb.combCode}"/>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                        <span value="0"
                              type="text" id="${comb.combCode}_${combAmountName.code}"/>
                        </td>
                    </c:forEach>

                    <td>
                        <span id="${comb.combCode}_code22">0</span>
                    </td>
                    <td>
                        <span id="${comb.combCode}_code23">0</span>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>
</form>
</body>
</html>