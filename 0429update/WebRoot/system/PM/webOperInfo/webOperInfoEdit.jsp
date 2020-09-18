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
        return doSubmit('form1', '<%=path%>/webOperInfo/update.htm');
    }
</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td>机构代码：</td>
            <td>${OperInfo.organCode}
                <input type="hidden" name="organCode" value="${OperInfo.organCode}"/>
                <span class="star">*</span></td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td align="right">
                柜员号：
            </td>
            <td>
                ${OperInfo.operCode}
                <input type="hidden" name="operCode" value="${OperInfo.operCode}"/>
            </td>
        </tr>
        <tr>
            <td align="right">
                姓名：
            </td>
            <td>
                ${OperInfo.operName}
                <input type="hidden" value="${OperInfo.operName}" name="operName" class="validate[required]"
                       maxlength="100"/>
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