<%@page language="java" pageEncoding="UTF-8" %>
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
    function isCardNo() {
        var organErrorMsg = "";
        var operErrorMsg = "";
        var organCode = $("#organCode").val();
        if (organCode.length > 0) {
            $.ajaxSettings.async = false;
            $.get("<%=path%>/webOperInfo/check.htm?organcode=" + organCode, function (result) {
                if (result.success == "true" || result.success == true) {
                } else {
                    organErrorMsg = result.msg;
                }
            }, "json");
        }
        if (organErrorMsg.trim() !== "") {
            top.Dialog.alert(organErrorMsg);
            return;
        }
        var operCode = $("#operCode").val();
        $.post("<%=path%>/webOperInfo/checkAndGetOperName.htm?operCode=" + operCode, {}, function (result) {
            if (result.success === "true" || result.success === true) {
            } else {
                operErrorMsg = "查询不到该柜员!";
            }
        }, "json");
        if (operErrorMsg.trim() !== "") {
            top.Dialog.alert(operErrorMsg);
            return;
        }
        return doSubmit('form1', '<%=path%>/webOperInfo/insert.htm');
    }


    function withdrawOper(val) {
        if (val.length > 0) {
            var operCode = $("#operCode").val();
            $.post("<%=path%>/webOperInfo/checkAndGetOperName.htm?operCode=" + operCode, {}, function (result) {
                if (result.success === "true" || result.success === true) {
                    $("#operName").val(result.msg);
                } else {
                    $("#operName").val(result.msg);
                }
            }, "json");
        }
    }

</script>


<body>
<form id="form1">
    <table class="tableStyle" width="100%" mode="list" formMode="line">
        <tr>
            <td>机构代码：</td>
            <td><input type="text" name="organCode" class="validate[required]" style="width: 205px"
                       id="organCode"/><span class="star">*</span></td>
        </tr>
        <tr>
            <td align="right" width="30%">
                柜员号：
            </td>
            <td>
                <input id="operCode" type="text" name="operCode" class="validate[required],custom[onlyNumber]"
                       style="width: 205px" onblur="withdrawOper(this.value);"
                       maxlength="20"/><span
                    class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="right">
                柜员名称：
            </td>
            <td>
                <input id="operName" disabled="disabled" type="text" name="operName" style="width: 205px"/><span
                    class="star">*</span>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <div align="center">
                    <button type="button" onclick="isCardNo()" class="saveButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>