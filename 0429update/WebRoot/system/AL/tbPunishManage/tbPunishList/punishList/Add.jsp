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
    function submitInfo() {

        return doSubmit('form1', '<%=path%>/tbTradeManger/tbPunishList/insert.htm');
    }


</script>

<body>
<form id="form1">
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input type="text" id="month" name="month" class="validate[required] date"  AUTOCOMPLETE="off"
                       dateFmt="yyyyMM"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                罚息名称：
            </td>
            <td>
                <input type="text" id="name" name="name" class="validate[required] "  />
                <span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td align="right">
                意见征集截止时间：
            </td>
            <td>
                <input type="text" id="collectEndTime" name="collectEndTime" class="validate[required] date"  AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3">
                <textarea id="note" name="note" AUTOCOMPLETE="off" style="width:90%;"
                          class="validate[required]"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <div align="center">
                    <button type="button" onclick="submitInfo()" class="saveButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>



    </table>
</form>


</body>
</html>