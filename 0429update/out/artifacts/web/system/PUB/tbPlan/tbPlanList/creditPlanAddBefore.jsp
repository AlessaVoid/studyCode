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

    <title>信贷计划新增-选择贷种页面</title>
</head>
<body>

<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <th colspan="2">
                <span>请选择制定计划的贷种级别</span>
            </th>
        </tr>
        <tr>
            <td>贷种级别：</td>
            <td>
                <select name="combLevel" id="combLevel">
                    <option value="1">一级贷种</option>
                    <option value="2">二级贷种</option>
                </select>
            </td>
        </tr>
    </table>
</form>
<br>
<br>
<br>

<div align="center" style="alignment: center;margin:20px;height: 20px">
    <button type="button" onclick="return sub()" id="saveTbPlan"><span>制定计划</span></button>
    <button type="button" onclick="return cancel()" class="cancelButton" id="cancelSaveTbPlan"></button>
</div>

</body>

<script>

    var diag = new top.Dialog();

    //制定计划
    function sub() {
        //校验
        var combLevel = $("#combLevel").val();
        if (!combLevel) {
            top.Dialog.alert("贷种级别不可为空");
            return;
        }

        diag.URL = "<%=path%>/creditPlan/toAddCreditPlan.htm?combLevel=" + combLevel,
        diag.Title = "新增机构计划";
        diag.Width = 1700;
        diag.Height = 680;

        diag.CancelEvent= function(){
            diag.close();
            //关闭计划页面是关闭此页面
            top.Dialog.close();
        };
        diag.show();
    }


</script>

</html>