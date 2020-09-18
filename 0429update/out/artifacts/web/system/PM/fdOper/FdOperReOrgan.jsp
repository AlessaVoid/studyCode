<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/common/common_edit.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!--表单异步提交start-->
    <script src="<%=path%>/libs/js/form/form.js" type="text/javascript"></script>
    <!--表单异步提交end-->
    <!--表单验证start-->
    <script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
    <!--表单验证end-->
    <script type="text/javascript">
        $(function () {
            $("select").attr("boxHeight", 90);
            $("select").render();
        });

        function withdrawOper(val) {
            if (val.length > 0) {
                var userNo = $("#operCode").val();
                $.post("<%=path%>/fdOper/getUserInfo.htm?userNo=" + userNo, {}, function (result) {
                    $("#operName").val(result.operName);
                    $("#organName").val(result.organName);
                    $("#organCode").val(result.organCode);
                }, "json");
            }
        }

        function newOpercode(val) {
            if (val.length > 0) {
                var userNo = $("#operCode").val();
                var newOrganCode = $('#newOrganCode').val();
                if (userNo.length == 11 && newOrganCode.length == 8) {
                    $.post("<%=path%>/fdOper/newOpercode.htm?userNo=" + userNo + "&newOrganCode=" + newOrganCode, {}, function (result) {
                        // $("#operName").val(result.operName);
                        // $("#organName").val(result.organName);
                        $("#newOperCode").val(result.newOperCode);
                    }, "json");
                }
            }
        }

        function sub() {
            var userNo = $("#operCode").val();
            $.post("<%=path%>/fdOper/getUserInfo.htm?userNo=" + userNo, {}, function (result) {
                if (result.success === "true" || result.success === true) {
                } else {
                    operErrorMsg = "查询不到该柜员!";
                }
            }, "json");

            top.Dialog.confirm("是否保存该条信息?", function () {

                var valid = $("#form1").validationEngine({
                    returnIsValid: true
                });
                if (valid) {
                    var newOrganCode = $('#newOrganCode').val();
                    var newOperCode = $('#newOperCode').val();
                    if (newOrganCode.length !== 8) {
                        top.Dialog.alert("请输入正确的机构号");
                        return;
                    }
                    $.post("<%=path%>/fdOper/operUpdateOrgan.htm?userNo=" + userNo + "&newOrganCode=" + newOrganCode + "&newOperCode=" + newOperCode, $("#form1")
                        .serialize(), function (result) {
                        if (result.success == "true" || result.success == true) {
                            top.Dialog.alert(result.msg, function () {
                                // top.frmright.window.location.reload(true);
                                window.location.reload();
                                // top.Dialog.close();

                            });
                        } else {
                            top.Dialog.alert(result.msg);
                        }
                    }, "json");
                } else {
                    if (!valid) {
                        top.Dialog.alert("验证未通过");
                    }
                }
            });
        }
    </script>
</head>
<body>
<form id="form1" action="" method="post">
    <div class="basicTabModern">
        <div name="柜员转岗" style="width: 100%; height: 150px;">
            <table class="tableStyle" mode="list">
                <tr>
                    <td align="right" width="50%">
                        柜员号：
                    </td>
                    <td>
                        <input type="text" id="operCode" maxlength="11" name="opercode" value=""
                               class="validate[required],custom[onlyNumber]"
                               onblur="withdrawOper(this.value);newOpercode(this.value);"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        柜员名称：
                    </td>
                    <td>
                        <input id="operName" disabled="disabled" type="text" name="operName" placeholder="请输入柜员号"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        原机构名称：
                    </td>
                    <td>
                        <input id="organName" disabled="disabled" type="text" name="organName" placeholder="请输入柜员号"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        原机构号：
                    </td>
                    <td>
                        <input id="organCode" disabled="disabled" type="text" name="organCode" placeholder="请输入柜员号"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        转岗机构号：
                    </td>
                    <td>
                        <input id="newOrganCode" maxlength="8" type="text" name="newOrganCode" placeholder="请输入转岗机构号"
                               class="validate[required]"
                               onblur="newOpercode(this.value);"
                        />
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        新柜员号：
                    </td>
                    <td>
                        <input id="newOperCode" disabled="disabled" type="text" name="newOperCode" placeholder="待生成"/>
                    </td>
                </tr>
            </table>
            <div class="padding_top10">
                <table class="tableStyle" formMode="transparent">
                    <tr>
                        <td colspan="4">
                            <input type="button" value="提交" onclick="return sub()"/>
                            <input type="reset" value="清空"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>