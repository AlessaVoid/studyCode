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
    <title></title>
</head>
<script type="text/javascript">
    var qaId =${TbLineOver.qaId};
    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/lineOver/getReqDetailList.htm",
            data: {"qaId": qaId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbOverDOS = result.tbOverDOS;
                    for (var i = 0; i < tbOverDOS.length; i++) {
                        var TbLineOverDO = tbOverDOS[i];
                        var qaComb = TbLineOverDO.qaComb;
                        var qaAmt = TbLineOverDO.qaAmt;

                        $("#" + qaComb + "_Num").html(qaAmt);
                    }
                }
            }
        });
    })


</script>


<body>
<form id="form2">
    <table class="tableStyle" id="table1" width="80%"
           style="table-layout:fixed;word-wrap:break-word;word-break:break-all" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="qaId" name="qaId" value="${TbLineOver.qaId}" hidden="hidden"/>
                ${TbLineOver.qaMonth}
            </td>

            <td align="right">
                附件名称：
            </td>
            <td>
                <span id="fileName">${fileName}</span>
            </td>

        </tr>

        <tr>
            <td align="right">下载附件：</td>
            <td>
                <a href="<%=path%>/tbTradeManger/single/download.htm?qaFileId=${TbLineOver.qaFileId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
            </td>

            <td align="right">
                单位：
            </td>
            <td>
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbLineOver.unit}"/>
            </td>
        </tr>

        <tr>
            <td>事由：</td>
            <td style="word-break:break-all">
                ${TbLineOver.qaReason}
            </td>
        </tr>
    </table>
</form>
<form id="form1">
    <div id="scrollContent" class="border_gray">
        <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <th trueWidth="150"></th>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <th align="center">
                            ${combAmountName.name}
                    </th>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <th> ${comb.combName}</th>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span id="${comb.combCode}_${combAmountName.code}"></span>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</form>

</body>
</html>