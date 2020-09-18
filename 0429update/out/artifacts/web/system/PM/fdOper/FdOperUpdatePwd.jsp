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

        function sub() {
            var ss = $("#newword").val();
            // var pa = /^(?=.*[0-9])(?=.*[~!@#$%^&*_+`|;':<>?,.-=])(?=.*[a-z])(?=.*[A-Z])(.{12,16})$/;
            // var pattern=/^[0-9a-zA-Z]\d{5,8}$/;

            //长度6~8 密码中必须包含字母、数字,不能有特殊字符
            // var pattern= new RegExp('(?=.*[0-9])(?=.*[a-zA-Z]).{6,8}');
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
                    var operPassword=$("#operpassword").val();
                    var password = $("#newword").val();
                    var repassword = $("#renewword").val();
                    if (password !== repassword) {
                        top.Dialog.alert("请确保新旧密码一致");
                        return;
                    }
                    $.post("<%=path%>/fdOper/operUpdatePwd.htm", $("#form1")
                        .serialize(), function (result) {
                        if (result.success == "true" || result.success == true) {
                            top.Dialog.alert(result.msg, function () {
                                // top.frmright.window.location.reload(true);
                                window.location.reload();
                                // top.Dialog.close();
                                // window.location.href;
                            });
                            // top.Dialog.show();
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
<form id="form1" action="" method="post" target="frmright">
    <div class="basicTabModern">
        <div name="密码修改" style="width: 100%; height: 150px;">
            <table class="tableStyle" mode="list">
                <tr>
                    <td align="right" width="50%" >
                        原密码：
                    </td>
                    <td>
                        <input type="password" class="dl-put-class" minlength="6" id="operpassword" maxlength="8"
                               name="operpassword"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        新密码：
                    </td>
                    <td>
                        <input type="password" class="dl-put-class" id="newword" minlength="6" maxlength="8" name="password"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        重复密码：
                    </td>
                    <td>
                        <input type="password" class="dl-put-class" id="renewword" minlength="6" maxlength="8" name="password"/>
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
