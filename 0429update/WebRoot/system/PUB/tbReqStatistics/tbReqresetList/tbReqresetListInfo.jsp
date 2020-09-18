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

    var reqresetType = ${TbreqresetListDTO.reqresetType};

    function initComplete() {
        //获取json数据
        var reqresetType = ${TbreqresetListDTO.reqresetType};
        if (reqresetType == 0) {
            $("#paramModeTwo").html("机构");
        } else if (reqresetType == 1) {
            $("#paramModeTwo").html("条线");
        }

        $("#reqresetCombList").selectTreeRender(setting);
        var reqresetType = ${TbreqresetListDTO.reqresetType};
        if (reqresetType == 0) {
            $("#paramModeTwo").html("机构");
        } else if (reqresetType == 1) {
            $("#paramModeTwo").html("条线");
        }
        check();
    }


    function check() {
        if (reqresetType == 0) {
            // document.getElementById("reqresetProdLine").style.display = "none";
            document.getElementById("tr_2").style.display = "none";
            $("#tr_2 .selectTree").resetValue();
            document.getElementById("tr_1").style.display = "";
            // document.getElementById("reqresetCombList").style.display = "";
            // document.getElementById("reqresetOrganList").style.display = "";
        } else if (reqresetType == 1) {
            // document.getElementById("reqresetCombList").style.display = "none";
            // document.getElementById("reqresetOrganList").style.display = "none";
            $("#tr_1 .selectTree").resetValue();
            // $("#reqresetCombList").selectTreeRender(setting);
            document.getElementById("tr_1").style.display = "none";
            document.getElementById("tr_2").style.display = "";
            // document.getElementById("reqresetProdLine").style.display = "";
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
                ${TbreqresetListDTO.reqresetName}
            </td>
            <td align="right">
                单位：
            </td>
            <td>
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbreqresetListDTO.reqresetUnit}"/>
            </td>


        </tr>

        <tr>
            <td align="left">
                所属月份：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetMonth}
            </td>

            <td align="right">下发对象类型：</td>
            <td>
                <input id="reqresetType" value="${TbreqresetListDTO.reqresetType}" hidden="hidden"/>
                <span id="paramModeTwo"></span>
            </td>
        </tr>

        <tr>
            <td align="right">
                周期开始时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetTimeStart}
            </td>

            <td align="right">
                周期结束时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetTimeEnd}
            </td>
        </tr>

        <tr>
            <td align="left">
                需求录入开始时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetDateStart}

            </td>


            <td align="right">
                需求录入结束时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetDateEnd}

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

            <td colspan="1">
                录入说明:
            </td>
            <td colspan="3">
                <textarea id="reqresetNote" disabled="disabled" name="reqresetNote"
                          style="width:90%;">${TbreqresetListDTO.reqresetNote}</textarea>
            </td>

        </tr>

    </table>
</form>
</body>
</html>