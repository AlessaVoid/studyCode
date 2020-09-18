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
    <title>借据管理</title>
</head>
<body>

<form id="form1">

    <div id="scrollContent" class="border_gray" style="height: 200px">

        <table id="plan" class="tableStyle" mode="list" fixedCellHeight="true">

            <tr>
                <td width="50%">系统名称：</td>
                <td width="50%">
                    <dic:out dicType="SYSTEM_CTRL_STATUS_TYPE" dicNo="${tbSystemCtrlStatus.systemid}"></dic:out>
                </td>
            </tr>
            <tr>
                <td width="50%">系统编号：</td>
                <td width="50%">
                    <input type="hidden" id="systemid" value="${tbSystemCtrlStatus.systemid}"/>
                    <span>${tbSystemCtrlStatus.systemid}</span>
                </td>
            </tr>
            <tr>
                <td width="50%">管控状态：</td>
                <td width="50%" >
                    <select name="systemStatus" id="systemStatus"  size="1">
                        <option value="0" ${status==0?'selected':''}>管控</option>
                        <option value="1" ${status==1?'selected':''} >不管控</option>
                    </select>
                </td>
            </tr>
        </table>

    </div>
    <div align="center" style="alignment: center;margin:20px;">
        <button type="button" onclick="return sub()" class="saveButton" id="updateTbPlan"></button>
        <button type="button" onclick="return cancel()" class="cancelButton" id="cancelUpdateTbPlan"></button>
    </div>
</form>
</body>

<script>



    //提交
    function sub() {

        var systemid = $('#systemid').val();
        var systemStatus = $('#systemStatus').val();
        if (!systemid) {
            top.Dialog.alert("系统编号不可为空");
            return;
        }
        if (!systemStatus) {
            top.Dialog.alert("请选择管控状态");
            return;
        }

        $('#updateTbPlan').attr('disabled', true);
        $('#cancelUpdateTbPlan').attr('disabled', true);
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbSystemCtrlStatus/update.htm",
            data: {"systemid": systemid, "systemStatus": systemStatus},
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    $('#updateTbPlan').attr('disabled', false);
                    $('#cancelUpdateTbPlan').attr('disabled', false);
                    top.Dialog.alert("修改成功!", function () {
                        var menu_id = parent.getCurrentTabId();
                        if (menu_id == undefined) {
                            top.Dialog.close();
                            return;
                        }
                        var menu_frame_id = "page_" + menu_id;
                        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                        top.Dialog.close();
                    });
                } else {
                    $('#updateTbPlan').attr('disabled', false);
                    $('#cancelUpdateTbPlan').attr('disabled', false);
                    top.Dialog.alert(result.message);
                }
            },
            error: function (result) {
                $('#updateTbPlan').attr('disabled', false);
                $('#cancelUpdateTbPlan').attr('disabled', false);
                top.Dialog.alert("请求异常");
            }
        });

    }


</script>

</html>