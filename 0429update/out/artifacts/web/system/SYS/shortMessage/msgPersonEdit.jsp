<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/stepper.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>

    <title>短信参数修改</title>
</head>
<body>

<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <input type="hidden"  id="id" name="id"   value="${msg.id}">
        <tr>
            <td align="right">
                姓名：
            </td>
            <td width="50%" >
                <input type="text"  id="name" name="name"   value="${msg.name}">

            </td>

        </tr>
        <tr>
            <td align="left">手机号：</td>
            <td>
                <input type="text" id="phoneNumber" name="phoneNumber" inputMode="numberOnly" value="${msg.phoneNumber}">
            </td>
        </tr>
        <tr>

            <td align="left">状态：</td>
            <td>
                <select name="status" id="status"  size="1">
                    <option value="1" ${msg.status == '1'?'selected':''}>启用</option>
                    <option value="2" ${msg.status == '2'?'selected':''} >停用</option>
                </select>
            </td>
        </tr>
<%--        <tr>--%>
<%--            <td align="left">分组：</td>--%>
<%--            <td>--%>
<%--                <input type="text" id="groupEmp" name="groupEmp"  value="${msg.groupEmp}">--%>

<%--            </td>--%>

<%--        </tr>--%>
        <input type="hidden" id="groupEmp" name="groupEmp"  value="1"/>

    </table>

    <div align="center" style="alignment: center;margin:20px;height: 20px">
        <button type="button" onclick="return sub()" class="saveButton" id="save"></button>
        <button type="button" onclick="return cancel()" class="cancelButton" id="cancelSave"></button>
    </div>
</form>


</body>

<script>


    //用户只能输入正整数
    function upperCase(obj) {
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[0-9]+$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }
    }


    //保存
    function sub() {
        var name = $("#name").val();
        var phoneNumber = $("#phoneNumber").val();
        var status = $("#status").val();
        var groupEmp = $("#groupEmp").val();

        if (!name) {
            top.Dialog.alert("请输入姓名!");
            return;
        }
        if (!phoneNumber) {
            top.Dialog.alert("请输入手机号!");
            return;
        }else if (!(/^1[34578]\d{9}$/.test(phoneNumber))) {
            top.Dialog.alert("请输入正确的手机号!");
            return;
        }

        if (!status) {
            top.Dialog.alert("请输入状态!");
            return;
        }
        if (!groupEmp) {
            top.Dialog.alert("请输入分组!");
            return;
        }


        var data = $("#form1").serialize();
        $('#saveTbPlan').attr('disabled', true);
        $('#cancelSaveTbPlan').attr('disabled', true);
        $.ajax({
            type: "POST",
            url: "<%=path%>/msgPerson/update.htm",
            data: data,
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    $('#saveTbPlan').attr('disabled', false);
                    $('#cancelSaveTbPlan').attr('disabled', false);
                    top.Dialog.alert("操作成功!", function () {
                        var menu_id = parent.getCurrentTabId();
                        if (menu_id == undefined) {
                            top.Dialog.close();
                            return;
                        }
                        var menu_frame_id = "page_" + menu_id;
                        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                        top.Dialog.close();
                        // Dialog.close();
                    });
                } else {
                    $('#saveTbPlan').attr('disabled', false);
                    $('#cancelSaveTbPlan').attr('disabled', false);
                    top.Dialog.alert(result.msg);
                }
            },
            error: function (result) {
                $('#saveTbPlan').attr('disabled', false);
                $('#cancelSaveTbPlan').attr('disabled', false);
                top.Dialog.alert("请求异常");
            }

        });
    }




    $(function () {

        //输入框获取焦点事件
        $(".planamonut").focus(function () {
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".planamonut").blur(function () {
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });
    });




</script>

</html>