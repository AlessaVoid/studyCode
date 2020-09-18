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
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
</head>

<script type="text/javascript">
    var reqId = '${reqId}';

    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbCalculateThreeResult/getReqDetailList.htm",
            data: {"reqId": reqId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbReqDetailList = result.tbReqDetailList;
                    for (var i = 0; i < tbReqDetailList.length; i++) {
                        var tbReqDetail = tbReqDetailList[i];
                        var reqdOrganCode = tbReqDetail.reqdOrgan;
                        var reqdCombCode = tbReqDetail.reqdCombCode;
                        var reqdReqnum = tbReqDetail.reqdReqnum;
                        var reqdRate = tbReqDetail.reqdRate;

                        $("#" + reqdOrganCode + "_" + reqdCombCode + "_reqNum").html(reqdReqnum);
                        $("#" + reqdOrganCode + "_" + reqdCombCode + "_rate").html(reqdRate);

                    }

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
                    万元
                </c:if>
                <c:if test="${TbReqListDTO.reqUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.reqTimeStart}
            </td>

            <td align="right">
                周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.reqTimeEnd}
            </td>
        </tr>


    </table>
</form>
<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td>机构名称</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td width="20%" style="word-break:break-all">
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
</html>