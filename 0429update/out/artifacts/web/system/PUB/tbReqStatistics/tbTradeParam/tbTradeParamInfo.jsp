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


    function initComplete() {
        //获取json数据
        var paramMode = ${TbTradeParam.paramMode};

        if (paramMode == 0) {
            $("#paramModeTwo").html("少分");
        } else if (paramMode == 1) {
            $("#paramModeTwo").html("多分");
        } else if (paramMode == 2) {
            $("#paramModeTwo").html("严格");
        }

    }
</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属周期：
            </td>
            <td>
                ${TbTradeParam.paramPeriod}
            </td>
            <td align="right">
                计划分配模式:
            </td>
            <td>
                <input id="paramMode" value="${TbTradeParam.paramMode}" hidden="hidden"/>
                <span id="paramModeTwo"></span>
            </td>

        </tr>
        <tr>
            <td align="right">
                罚息开始时间:
            </td>
            <td>
                ${TbTradeParam.paramPunishStart}
            </td>

            <td align="right">
                罚息结束时间:
            </td>
            <td>
                ${TbTradeParam.paramPunsihEnd}
            </td>
        </tr>
        <tr>
            <td align="right">
                创建人员id:
            </td>
            <td>
                ${TbTradeParam.paramCreateuserid}
            </td>


            <td align="right">
                更新人员id:
            </td>
            <td>
                ${TbTradeParam.paramUpdateuserid}
            </td>
        </tr>

        <tr>
            <td align="right">
                创建时间:
            </td>
            <td>
                ${TbTradeParam.paramCreatetime}
            </td>
            <td align="right">
                更新时间:
            </td>
            <td>
                ${TbTradeParam.paramUpdatetime}
            </td>
        </tr>


        <tr>

            <td align="right" colspan="1">
                当月临时额度审批标准:
            </td>
            <td colspan="1">
                ${TbTradeParam.paramTempMount}亿元
            </td>

            <td align="right" colspan="1">
                当月单笔专项审批标准:
            </td>
            <td colspan="1">
                ${TbTradeParam.paramSingleMount}亿元
            </td>


        </tr>
        <tr>
            <td align="right" colspan="1">
                当月超限额审批标准:
            </td>
            <td colspan="1">
                ${TbTradeParam.paramOverMount}亿元
            </td>
            <td align="right" colspan="1">
                是否条线管控:
            </td>
            <td colspan="1">
                <dic:out dicType="PARAM_IS_LINE" dicNo="${TbTradeParam.paramIsLine}"/>
            </td>
        </tr>
        <tr>
        <td align="right" colspan="1">
        当月计划净增量:
        </td>
        <td colspan="1">
        ${TbTradeParam.paramMechIncrement}亿元
        </td>
        <%--<td align="right" colspan="1">--%>
        <%--当月条线计划净增量:--%>
        <%--</td>--%>
        <%--<td colspan="1">--%>
        <%--${TbTradeParam.paramLineIncrement}亿元--%>
        <%--</td>--%>
        <%--</tr>--%>

    </table>
</form>
</body>
</html>