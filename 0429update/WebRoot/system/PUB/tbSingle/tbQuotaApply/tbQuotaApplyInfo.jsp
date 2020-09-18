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

    var combMap = {};

    function initComplete() {

        $.post("<%=path%>/tbTradeManger/lineOver/showComb.htm",
            {}, function (result) {
                combMap = result.combMap;
                var qaComb = $('#qaComb').val();
                $("#qaCombName").html(combMap[qaComb]);
            }, "json");
    }


</script>


<body>
<form id="form1">
    <table class="tableStyle" id="table1" width="80%" style="table-layout:fixed;word-wrap:break-word;word-break:break-all" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="qaId" name="qaId" value="${TbSingle.qaId}" hidden="hidden"/>
                ${TbSingle.qaMonth}
            </td>

            <td align="left">贷种组合名称：</td>
            <td>
                <input id="qaComb" name="qaComb" disabled="disabled" value="${TbSingle.qaComb}"
                       hidden="hidden"></input>
                <span id="qaCombName"></span>
            </td>
        </tr>

        <tr>
            <td align="right">调整额度：</td>
            <td>
                ${TbSingle.qaAmt}
            </td>
            <td align="right">借据编号/票据协议号：</td>
            <td>
                ${TbSingle.qaSingleId}
            </td>
        </tr>
        <tr >
            <td align="right">业务经办机构编号：</td>
            <td>
                ${TbSingle.qaSingleOrgan}
            </td>
            <td align="right">业务经办机构名称：</td>
            <td>
                ${TbSingle.qaSingleOrganName}
            </td>




        </tr>
        <tr>
            <td align="right">
                附件名称：
            </td>
            <td>
                <span id="fileName">${fileName}</span>
            </td>
            <td align="right">下载附件：</td>
            <td>
                <a href="<%=path%>/tbTradeManger/single/download.htm?qaFileId=${TbSingle.qaFileId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
            </td>

        </tr>
        <tr>
            <td align="right">
                单位：
            </td>
            <td>
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbSingle.unit}"/>
            </td>
        </tr>

        <tr>


            <td colspan="1" >事由：</td>
            <td colspan="3" style="word-break:break-all">
                ${TbSingle.qaReason}
            </td>
        </tr>
    </table>
</form>
</body>
</html>