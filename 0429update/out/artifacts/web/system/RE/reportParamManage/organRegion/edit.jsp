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
<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                机构号：
            </td>
            <td>
                <input type="text" id="organcode" name="organcode" maxlength="30" hidden="hidden"
                       value="${FdReportOrgan.organcode}"/>
                <span>${FdReportOrgan.organcode}</span>
            </td>

            <td align="right">
                机构名称：
            </td>
            <td>
                <input type="text" id="organname" name="organname" maxlength="30" value="${FdReportOrgan.organname}"/>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right">
                地理区域划分：
            </td>
            <td>
                <select id="type1" name="type1">
                    <option value="1" ${FdReportOrgan.type1==1?'selected':''}>总行本部</option>
                    <option value="2" ${FdReportOrgan.type1==2?'selected':''}>华北地区</option>
                    <option value="3" ${FdReportOrgan.type1==3?'selected':''}>东北地区</option>
                    <option value="4" ${FdReportOrgan.type1==4?'selected':''}>华东地区</option>
                    <option value="5" ${FdReportOrgan.type1==5?'selected':''}>华中地区</option>
                    <option value="6" ${FdReportOrgan.type1==6?'selected':''}>华南地区</option>
                    <option value="7" ${FdReportOrgan.type1==7?'selected':''}>西南地区</option>
                    <option value="8" ${FdReportOrgan.type1==8?'selected':''}>西北地区</option>
                    <option value="0" ${FdReportOrgan.type1==0?'selected':''}>暂不分组</option>
                </select>
            </td>

            <td align="right">
                银行同业划分：
            </td>
            <td>
                <select id="type2" name="type2">
                    <option value="1" ${FdReportOrgan.type2==1?'selected':''}>总行本部</option>
                    <option value="2" ${FdReportOrgan.type2==2?'selected':''}>长三角</option>
                    <option value="3" ${FdReportOrgan.type2==3?'selected':''}>珠三角</option>
                    <option value="4" ${FdReportOrgan.type2==4?'selected':''}>环渤海</option>
                    <option value="5" ${FdReportOrgan.type2==5?'selected':''}>中部地区</option>
                    <option value="6" ${FdReportOrgan.type2==6?'selected':''}>西部地区</option>
                    <option value="7" ${FdReportOrgan.type2==7?'selected':''}>东北地区</option>
                    <option value="0" ${FdReportOrgan.type2==0?'selected':''}>暂不分组</option>
                </select>
            </td>

        </tr>

        <tr>
            <td align="right">
                财务考核划分：
            </td>
            <td>
                <select id="type3" name="type3">
                    <option value="1" ${FdReportOrgan.type3==1?'selected':''}>总行本部</option>
                    <option value="2" ${FdReportOrgan.type3==2?'selected':''}>第一组</option>
                    <option value="3" ${FdReportOrgan.type3==3?'selected':''}>第二组</option>
                    <option value="4" ${FdReportOrgan.type3==4?'selected':''}>第三组</option>
                    <option value="5" ${FdReportOrgan.type3==5?'selected':''}>第四组</option>
                    <option value="0" ${FdReportOrgan.type3==0?'selected':''}>暂不分组</option>
                </select>
            </td>

            <td align="right">
                机构排序：
            </td>
            <td>
                <input type="text" id="organorder" name="organorder" maxlength="30"
                       value="${FdReportOrgan.organorder}"/>
                <span class="star">*</span>
            </td>


        </tr>


        <tr>
            <td colspan="8">
                <div align="center">
                    <button type="button" onclick="return doSubmit('form1','<%=path%>/fdReportOrgan/update.htm')"
                            class="saveButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>