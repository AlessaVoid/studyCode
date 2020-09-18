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
    <title>额度管理</title>
</head>
<body>

<form id="form1">

    <div id="scrollContent" class="border_gray" style="height: 200px">

        <table id="plan" class="tableStyle" mode="list" fixedCellHeight="true">

            <input type="hidden" id="paId" name="paId" value="${tbQuotaAllcate.paId}"/>
            <tr>
                <td width="50%">月份：</td>
                <td width="50%">
                    <span>${tbQuotaAllcate.paMonth}</span>
                </td>
            </tr>
            <tr>
                <td width="50%">机构：</td>
                <td width="50%">
                    <span>${organName}</span>
                </td>
            </tr>
            <tr>
                <td width="50%">贷种：</td>
                <td width="50%">
                    <span>${combName}</span>
                </td>
            </tr>

            <tr>
                <td width="50%">可用额度：</td>
                <td width="50%">
                    <input type="text" id="paQuotaAvail" name="paQuotaAvail" value="${tbQuotaAllcate.paQuotaAvail}"
                           oninput='upperCase(this)'/>
                </td>
            </tr>
            <tr>
                <td width="50%">类型：</td>
                <td width="50%">
                    <c:if test="${tbQuotaAllcate.quotaType ==1}">
                        机构
                    </c:if>
                    <c:if test="${tbQuotaAllcate.quotaType ==2}">
                        条线
                    </c:if>
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
    $(function () {
        //输入框获取焦点事件
        $("#paQuotaAvail").focus(function () {
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $("#paQuotaAvail").blur(function () {
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });

    })


    //用户只能输入正负数与小数
    function upperCase(obj) {
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }
    }

    //提交
    function sub() {

        var paId = $('#paId').val();
        var paQuotaAvail = $('#paQuotaAvail').val();


        $('#updateTbPlan').attr('disabled', true);
        $('#cancelUpdateTbPlan').attr('disabled', true);
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbQuotaAllocate/update.htm",
            data: {"paId": paId, "paQuotaAvail": paQuotaAvail},
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