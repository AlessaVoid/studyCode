<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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


</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                机构号：
            </td>
            <td>
                <input type="text" id="organcode" name="organcode" maxlength="30"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                机构名称：
            </td>
            <td>
                <input type="text" id="organname" name="organname" maxlength="30"/>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>

            <td align="right">
                机构排序：
            </td>
            <td>
                <input type="text" id="organorder" name="organorder" maxlength="30"/>
                <span class="star">*</span>
            </td>


        </tr>


        <tr>
            <td colspan="8">
                <div align="center">
                    <button type="button" onclick="return doSubmit('form1','<%=path%>/tbKeyReportOrgan/insert.htm')" class="saveButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>