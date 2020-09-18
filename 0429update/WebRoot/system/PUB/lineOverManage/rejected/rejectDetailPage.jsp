<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"/>
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
        initComplete();
    });

    function initComplete() {
        $("#span1").html(${oneNum}+"");
        $("#span2").html(${oneRate}+"%");
        $("#span3").html(${twoNum}+"");
        $("#span4").html(${twoRate}+"%");
        $("#span5").html(${threeNum}+"");
        $("#span6").html(${threeRate}+"%");
    }


</script>
<body>

<form id="form2">
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
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
            <td align="right">全年计划进度：</td>
            <td>
                ${TbLineOver.qaYearRate}%
            </td>
        </tr>

        <tr>
            <td align="right">本月计划：</td>
            <td>
                ${TbLineOver.qaPlanAmt}
            </td>
            <td align="right">本月超计划额度：</td>
            <td>
                ${TbLineOver.qaOverAmt}
            </td>
        </tr>
        <tr>
            <td align="right">前第三个月超规模或闲置额度：</td>
            <td>
                <span id="span1"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span2"></span>
            </td>
        </tr>
        <tr>
            <td align="right">前第二个月超规模或闲置额度：</td>
            <td>
                <span id="span3"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span4"></span>
            </td>
        </tr>
        <tr>
            <td align="right">前第一个月超规模或闲置额度：</td>
            <td>
                <span id="span5"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span6"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                单位：
            </td>
            <td>
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbLineOver.unit}"/>
            </td>
        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3" style="word-break:break-all">
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
</body>
</html>