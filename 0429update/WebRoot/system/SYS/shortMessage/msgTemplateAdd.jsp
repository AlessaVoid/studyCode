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

        <tr>
            <td align="left">模板ID：</td>
            <td>
                <input type="text" id="id" name="id"  value="${msg.id}">
            </td>
        </tr>

        <tr>
            <td align="right">模板名称：</td>
            <td width="50%">
                <input type="text" id="name" name="name" value="${msg.name}">
            </td>
        </tr>

        <tr >
            <td colspan="2" class="center">模板内容：</td>
        </tr>
        <tr>
            <td colspan="2"><textarea id="template" name="template" rows="50" cols="50" >${msg.template}</textarea></td>
        </tr>

    </table>

    <div align="center" style="alignment: center;margin:20px;height: 20px">
        <button type="button" onclick="return sub()" class="saveButton" id="save"></button>
        <button type="button" onclick="return cancel()" class="cancelButton" id="cancelSave"></button>
    </div>
</form>


</body>

<script>

    //保存
    function sub() {
        var id = $("#id").val();
        var name = $("#name").val();
        var template = $("#template").val();

        if (!id) {
            top.Dialog.alert("请输入模板ID!");
            return;
        }
        if (!name) {
            top.Dialog.alert("请输入模板名称!");
            return;
        }
        if (!template) {
            top.Dialog.alert("请输入模板内容!");
            return;
        }



        var data = $("#form1").serialize();
        $('#saveTbPlan').attr('disabled', true);
        $('#cancelSaveTbPlan').attr('disabled', true);
        $.ajax({
            type: "POST",
            url: "<%=path%>/msgTemplate/insert.htm",
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





</script>

</html>