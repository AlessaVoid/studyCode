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

    var reqType = ${TbReqListDTO.reqType};

    function initComplete() {
        //获取json数据
        var reqType = ${TbReqListDTO.reqType};
        if (reqType == 0) {
            $("#paramModeTwo").html("机构");
        } else if (reqType == 1) {
            $("#paramModeTwo").html("条线");
        }

        $("#reqCombList").selectTreeRender(setting);
        var reqType = ${TbReqListDTO.reqType};
        if (reqType == 0) {
            $("#paramModeTwo").html("机构");
        } else if (reqType == 1) {
            $("#paramModeTwo").html("条线");
        }
        check();
    }


    function check() {
        if (reqType == 0) {
            // document.getElementById("reqProdLine").style.display = "none";
            document.getElementById("tr_2").style.display = "none";
            // $("#tr_2 .selectTree").resetValue();
            document.getElementById("tr_1").style.display = "";
            // document.getElementById("reqCombList").style.display = "";
            // document.getElementById("reqOrganList").style.display = "";
        } else if (reqType == 1) {
            // document.getElementById("reqCombList").style.display = "none";
            // document.getElementById("reqOrganList").style.display = "none";
            // $("#tr_1 .selectTree").resetValue();
            // $("#reqCombList").selectTreeRender(setting);
            document.getElementById("tr_1").style.display = "none";
            document.getElementById("tr_2").style.display = "";
            // document.getElementById("reqProdLine").style.display = "";
        }
    }

    var setting = {
        check: {
            enable: true,
            nocheckInherit: false,
            chkDisabled: true
        }
    };

</script>


<body>
<form id="form1">
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                ${TbReqListDTO.reqName}
            </td>
            <td align="right">
                单位：
            </td>
            <td>
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbReqListDTO.reqUnit}"/>
            </td>
        </tr>

        <tr>
            <td align="left">
                所属月份：
            </td>
            <td>
                ${TbReqListDTO.reqMonth}
            </td>

            <td align="right">下发对象类型：</td>
            <td>
                <input id="reqType" value="${TbReqListDTO.reqType}" hidden="hidden"/>
                <span id="paramModeTwo"></span>
            </td>
        </tr>

        <tr>
            <td align="right">
                净增量周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.reqTimeStart}
            </td>

            <td align="right">
                净增量周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.reqTimeEnd}
            </td>
        </tr>

        <tr>
            <td align="right">
                到期量周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.expTimeStart}
            </td>

            <td align="right">
                到期量周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.expTimeEnd}
            </td>
        </tr>

        <tr>
            <td align="right">
                利率周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.rateTimeStart}
            </td>

            <td align="right">
                利率周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.rateTimeEnd}
            </td>
        </tr>


        <tr>
            <td align="right">
                余额周期开始时间：
            </td>
            <td>
                ${TbReqListDTO.balanceTimeStart}
            </td>

            <td align="right">
                余额周期结束时间：
            </td>
            <td>
                ${TbReqListDTO.balanceTimeEnd}
            </td>
        </tr>

        <tr>
            <td align="left">
                需求录入开始时间：
            </td>
            <td>
                ${TbReqListDTO.reqDateStart}

            </td>


            <td align="right">
                需求录入结束时间：
            </td>
            <td>
                ${TbReqListDTO.reqDateEnd}

            </td>
        </tr>


        <tr id="tr_1">
            <td>
                下发贷种组合:
            </td>

            <td style="word-break:break-all">
                  ${combNameStr}
            </td>

            <td>
                下发机构:
            </td>

            <td style="word-break:break-all">
                    ${organNameStr}
            </td>
        </tr>

        <tr id="tr_2">
            <td>
                下发条线:
            </td>

            <td style="word-break:break-all">
                   ${lineNameStr}
            </td>

        </tr>

        <tr>
            <td>
                下发填写项:
            </td>

            <td style="word-break:break-all">
                ${numTypeStr}
            </td>

        </tr>

        <tr>

            <td colspan="1">
                录入说明:
            </td>
            <td colspan="3">
                <textarea id="reqNote" disabled="disabled" name="reqNote"
                          style="width:90%;">${TbReqListDTO.reqNote}</textarea>
            </td>

        </tr>

    </table>
</form>
</body>
</html>