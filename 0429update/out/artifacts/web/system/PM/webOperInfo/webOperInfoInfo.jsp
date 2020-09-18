<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title></title>
</head>
<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td>机构代码：</td>
            <td>${OperInfo.organCode}
                <span class="star"></span></td>
        </tr>
        <tr>
            <td align="right" width="30%">
                柜员号：
            </td>
            <td >
                ${OperInfo.operCode}
            </td>
        </tr>
        <tr>
            <td align="right">
                姓名：
            </td>
            <td>
                ${OperInfo.operName}
            </td>
        </tr>
        <tr>
            <td align="right">
                所属条线：
            </td>
            <td colspan="3">
                <textarea disabled="disabled" rows="50" cols="100" id="lineDescription" name="lineDescription">${lineNameList}</textarea>
            </td>
        </tr>
        <tr>
            <td align="right">
                最后修改日期:
            </td>
            <td>
                ${OperInfo.latestModifyDate}
            </td>
        </tr>
        <tr>
        <td align="right">最后修改时间:</td>
        <td>
            ${OperInfo.latestModifyTime}
        </td>
        </tr>
        <tr>
            <td align="right">
                最后操作员:
            </td>
            <td>
                ${OperInfo.latestOperCode}
            </td>
        </tr>

    </table>
</form>
</body>
</html>