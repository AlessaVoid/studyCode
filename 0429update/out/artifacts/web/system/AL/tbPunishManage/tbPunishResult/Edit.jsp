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
    function sub() {

        return doSubmit('form1', '<%=path%>/tbTradeManger/tbPunishResult/update.htm');
    }


</script>

<body>
<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 400px;">
        <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
            <tr>
                <td align="right">
                    机构名称：
                </td>
                <td>
                    <input id="punishId" name="punishId" value=" ${TbPunishResult.punishId}" hidden="hidden"/>
                    ${TbPunishResult.organName}

                </td>


                <td align="right">
                    罚息月份：
                </td>
                <td>
                    ${TbPunishResult.punishMonth}
                </td>
            </tr>
            <tr>
                <td align="right">
                    意见征集截止时间：
                </td>
                <td>
                    ${tbPunishList.collectEndTime}
                </td>

                <td align="right">
                    总考核计划：
                </td>
                <td>
                    ${TbPunishResult.planMount}亿元
                </td>

            </tr>

            <tr>
                <td align="right">
                    最后一天闲置额度：
                </td>
                <td>
                    ${TbPunishResult.monthVacancyAmt}亿元
                </td>

                <td align="right">
                    最后一天闲置率：
                </td>
                <td>
                    ${TbPunishResult.monthVacancyRate}%
                </td>

            </tr>


            <tr>
                <td align="right">
                    总闲置费：
                </td>
                <td>
                    ${TbPunishResult.monthFiveVacancy}万元
                </td>

                <td align="right">
                    实体考核计划：
                </td>
                <td>
                    ${TbPunishResult.monthShitiPlanMount}亿元
                </td>

            </tr>
            <tr>
                <td align="right">
                    实体月末超计划额度：
                </td>
                <td>
                    ${TbPunishResult.monthShitiOverAmt}亿元
                </td>

                <td align="right">
                    实体月末超计划幅度：
                </td>
                <td>
                    ${TbPunishResult.monthShitiOverRate}%
                </td>

            </tr>
            <tr>
                <td align="right">
                    实体超计划费：
                </td>
                <td>
                    ${TbPunishResult.monthFiveShitiOver}万元
                </td>

                <td align="right">
                    票据考核计划：
                </td>
                <td>
                    ${TbPunishResult.monthPiapjuPlanMount}亿元
                </td>

            </tr>


            <tr>
                <td align="right">
                    票据月末超计划额度：
                </td>
                <td>
                    ${TbPunishResult.monthPiaojuOverAmt}亿元
                </td>

                <td align="right">
                    票据月末超计划幅度：
                </td>
                <td>
                    ${TbPunishResult.monthPiaojuOverRate}%
                </td>

            </tr>


            <tr>
                <td align="right">
                    票据超计划费：
                </td>
                <td>
                    ${TbPunishResult.monthFivePiaojuOver}万元
                </td>

                <td align="right">
                    罚息总计：
                </td>
                <td>
                    ${TbPunishResult.monthTotalPunish}万元
                </td>

            </tr>

            <tr>
                <td align="right">
                    反馈意见：
                </td>
                <td colspan="3">
                <textarea id="note" name="note" AUTOCOMPLETE="off"
                          style="width:90%;">${TbPunishResult.note}</textarea>
                    <span class="star">*</span>
                </td>

            </tr>


        </table>
    </div>
    <div align="center">
        <button type="button" onclick="sub()" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</form>


</body>
</html>