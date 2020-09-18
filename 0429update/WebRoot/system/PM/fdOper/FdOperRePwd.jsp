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
                }, "json");
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

            var ss = $("#operpassword").val();
            // var pa = /^(?=.*[0-9])(?=.*[~!@#$%^&*_+`|;':<>?,.-=])(?=.*[a-z])(?=.*[A-Z])(.{6,8})$/;
            // var pattern= /^[0-9a-zA-Z]\d{5,8}$/;

            //长度6~8 密码中必须包含字母、数字
            //^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$
            var pattern=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,8}$/;
            top.Dialog.confirm("是否保存该条信息?", function () {
                var valid = $("#form1").validationEngine({
                    returnIsValid: true
                });
                if (valid) {
                    if(!pattern.test(ss)){
                        top.Dialog.alert("请注意：密码长度需为6到8位，必须同时包含字母和数字，并且不能含有特殊字符");
                        return;
                    }
                    var password = $('#operpassword').val();
                    var repassword = $('#operrepassword').val();
                    if (password !== repassword) {
                        top.Dialog.alert("两次密码不一致,请确保两次密码一致");
                        return;
                    }
                    $.post("<%=path%>/fdOper/operRePwd.htm", $("#form1")
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
                    } else if (!pa.exec(ss)) {
                        top.Dialog.alert("请注意：密码长度需为6到8位，必须同时包含字母和数字，并且不能含有特殊字符");
                    }
                }
            });
        }
    </script>
</head>
<body>
<form id="form1" action="" method="post">
    <div class="basicTabModern">
        <div name="密码重置" style="width: 100%; height: 150px;">
            <table class="tableStyle" mode="list">
                <tr>
                    <td align="right" width="50%">
                        柜员号：
                    </td>
                    <td>
                        <input type="text" id="operCode" maxlength="20" name="opercode" value=""class="validate[required],custom[onlyNumber]"
                                onblur="withdrawOper(this.value);"/><span
                            class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        柜员名称：
                    </td>
                    <td>
                        <input id="operName" disabled="disabled" type="text" name="opernName" watermark="请输入柜员号"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        机构名称：
                    </td>
                    <td>
                        <input id="organName" disabled="disabled" type="text" name="organName" watermark="请输入柜员号"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        密码：
                    </td>
                    <td>
                        <input type="password" class="dl-put-class" id="operpassword" minlength="6" maxlength="8"
                               name="operpassword"/><span
                            class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        重复密码：
                    </td>
                    <td>
                        <input type="password" class="dl-put-class" id="operrepassword" minlength="6" maxlength="8"
                               name="operrepassword"/><span
                            class="star">*</span>
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