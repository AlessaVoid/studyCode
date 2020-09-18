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
    function submitInfo() {

        var num1 = $("#warnOneLine").val();
        var num2 = $("#warnTwoLine").val();
        var num3 = $("#warnThreeLine").val();
        var num4 = $("#warnFourLine").val();
        var num5 = $("#warnFiveLine").val();

        var reqCombListStr = $("#warnComb").attr("relValue");
        // if (reqCombListStr.length < 2) {
        //     top.Dialog.alert("请选择贷种组合", null, null, null, 5);
        //     return
        // }
        // if (reqCombListStr.indexOf(',') !=-1) {
        //     top.Dialog.alert("请选择1种贷种组合", null, null, null, 5);
        //     return
        // }

        if ((num2!=0)&&(parseInt(num1*100000000) >= parseInt(num2*100000000))) {
            top.Dialog.alert("二级预警线不符合标准");
            return false;
        }
        if ((num3!=0)&&(parseInt(num2*100000000) >= parseInt(num3*100000000))) {
            top.Dialog.alert("三级预警线不符合标准");
            return false;
        }
        if ((num4!=0)&&(parseInt(num3*100000000) >= parseInt(num4*100000000))) {
            top.Dialog.alert("四级预警线不符合标准");
            return false;
        }
        if ((num5!=0)&&(parseInt(num4*100000000) >= parseInt(num5*100000000))) {
            top.Dialog.alert("五级预警线不符合标准");
            return false;
        }



        return doSubmit('form1', '<%=path%>/tbWarn/insert.htm');
    }


    function initComplete() {
        $("#warnComb").selectTreeRender(setting);
    }

    var setting = {
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
        }

    };

    function check() {
        if ($('#warnType').val() == 1) {
            $('#span' + 1).html("%");
            $('#span' + 2).html("%");
            $('#span' + 3).html("%");
            $('#span' + 4).html("%");
            $('#span' + 5).html("%");
        }
        if ($('#warnType').val() == 0) {
            $('#span' + 1).html("亿元");
            $('#span' + 2).html("亿元");
            $('#span' + 3).html("亿元");
            $('#span' + 4).html("亿元");
            $('#span' + 5).html("亿元");
        }
    }

</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                预警参数名称：
            </td>
            <td>
                <input type="text" name="warnName" class="validate[required]" maxlength="20" required="true"/><span
                    class="star">*</span>
            </td>
            <td align="left">预警贷种：</td>
            <td>
                <div id="warnComb" name="warnComb" url="<%=path%>/tbTradeManger/tbReqList/selectCombForWarn.htm"
                     multiMode="true" allSelectable="true" exceptParent="false"></div>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left">预警线类型：</td>
            <td>
                <dic:select id="warnType" dicType="WARN_TYPE" name="warnType" tgClass="validate[required]"
                            onchange="check()"
                            required="true"></dic:select>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left">
                一级预警线：
            </td>
            <td>
                <input type="text" id="warnOneLine" name="warnOneLine" value="0"
                       class=" validate[required,custom[onlyNumberWide]] float_left"
                       placeholder="请输入数字" required="true"/>
                <span id="span1" class="star"></span>
                <span class="star">*</span>
            </td>
            <td align="right">
                二级预警线：
            </td>
            <td>
                <input type="text" id="warnTwoLine" name="warnTwoLine" value="0"
                       class=" validate[required,custom[onlyNumberWide]] float_left"
                       placeholder="请输入数字" required="true"/>
                <span id="span2" class="star"></span>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="right">
                三级预警线：
            </td>
            <td>
                <input type="text" id="warnThreeLine" name="warnThreeLine" value="0"
                       class=" validate[required,custom[onlyNumberWide]] float_left"
                       placeholder="请输入数字"
                       required="true"/>
                <span id="span3" class="star"></span>
                <span class="star">*</span>
            </td>
            <td align="right">
                四级预警线：
            </td>
            <td>
                <input type="text" id="warnFourLine" name="warnFourLine" value="0"
                       class=" validate[required,custom[onlyNumberWide]] float_left"
                       placeholder="请输入数字"
                       required="true"/>
                <span id="span4" class="star"></span>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="right">
                五级预警线：
            </td>
            <td>
                <input type="text" id="warnFiveLine" name="warnFiveLine" value="0"
                       class=" validate[required,custom[onlyNumberWide]] float_left"
                       placeholder="请输入数字"
                       required="true"/>
                <span id="span5" class="star"></span>
                <span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td colspan="4">
                <div align="center">
                    <button type="button" onclick="submitInfo()" class="saveButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
                <div class="staticTip" style="width: 600px;">
                    提示：预警贷种仅允许单选。各级预警线大小为逐级递增，支持到小数点后四位。
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>