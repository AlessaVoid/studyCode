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
            <td width="25%">
                机构编码：
            </td>
            <td width="25%">${fdOrgan.thiscode}</td>
            <td width="25%">
                机构名称：
            </td>
            <td width="25%">${fdOrgan.organname}</td>
        </tr>
        <tr>
            <td>上级机构编码：</td>
            <td>${fdOrgan.uporgan}</td>
            <td>基金全国中心代号：</td>
            <td>${fdOrgan.countrycode}</td>
        </tr>
        <tr>
            <td>省局代号：</td>
            <td>${fdOrgan.provincecode}</td>
            <td>地区代码：</td>
            <td>${fdOrgan.areacode}</td>
        </tr>
        <tr>
            <td>市,县局代号 ：</td>
            <td>${fdOrgan.citycode}</td>
            <td>基金全国中心代号 ：</td>
            <td>${fdOrgan.countrycode}</td>
        </tr>
        <tr>
            <td>支局机构代号 ：</td>
            <td>${fdOrgan.branchcode}</td>
            <td>机构级别 ：</td>
            <td>${fdOrgan.organlevel==0?"总行":(fdOrgan.organlevel==1?"一级分行":(fdOrgan.organlevel==2?"二级分行":(fdOrgan.organlevel==3?"一级支行":"网点")))}</td>
        </tr>
        <tr>
            <td>核算单位标志</td>
            <td>${fdOrgan.checkflag==1?'核算':'不核算'}</td>
            <td>机构地址 ：</td>
            <td>${fdOrgan.organaddr}</td>
        </tr>
        <tr>
            <td>维护人员姓名 ：</td>
            <td>${fdOrgan.maintainname}</td>
            <td>维护人员联系电话 ：</td>
            <td>${fdOrgan.maintainphone}</td>
        </tr>
        <tr>
            <td>所属二级出纳：</td>
            <td>${fdOrgan.cashiercode}</td>
            <td>维护人员姓名 ：</td>
            <td>${fdOrgan.maintainname}</td>
        </tr>
        <tr>
            <td>最后修改日期：</td>
            <td>${fdOrgan.modifydate}</td>
            <td>修改机构：</td>
            <td>${fdOrgan.modifyorgan}</td>
        </tr>
        <tr>
            <td>本机构状态</td>
            <td>${fdOrgan.organstate==0?'签到':(fdOrgan.organstate==1?'签退':'撤销')}</td>
            <td>最后操作员：</td>
            <td>${fdOrgan.modifyoper}</td>
        </tr>
    </table>
</form>
</body>
</html>