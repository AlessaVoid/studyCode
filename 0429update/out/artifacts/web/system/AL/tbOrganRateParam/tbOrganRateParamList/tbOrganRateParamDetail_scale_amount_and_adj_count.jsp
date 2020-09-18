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
    <title>信贷计划修改页面</title>
</head>
<body>
<form id="form1">
    <input type="hidden" id="orderRate" name="orderRate"
           value="${tbOrganRateParamList[0].orderRate}" maxlength="20"
    />
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr><span >当扣分指标不为0时，根据指标计算得分；当扣分指标为0时，根据区间直接得分</span></tr>
        <tr>
            <td align="left" colspan="1">
                参数名称：
            </td>
            <td colspan="1">
                <input type="hidden" id="elementTypeName" name="elementTypeName"
                       value="${tbOrganRateParamList[0].elementTypeName}" maxlength="20"
                />
                ${tbOrganRateParamList[0].elementTypeName}
            </td>

        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                调整频次3次未扣分得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="adjCountThree1" name="adjCountThree1" class="rateParam"
                       value="${tbOrganRateParamList[0].adjCountThree}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].adjCountThree}
            </td>
        </tr>
        <tr>

            <td align="right" colspan="1">
                调整频次2次未扣分得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="adjCountTwo1" name="adjCountTwo1" class="rateParam"
                       value="${tbOrganRateParamList[0].adjCountTwo}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].adjCountTwo}
            </td>

        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                超规模占用费和规模闲置费3次未扣分得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="scaleThree1" name="scaleThree1" class="rateParam"
                       value="${tbOrganRateParamList[0].scaleThree}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].scaleThree}
            </td>

        </tr>
        <tr>

            <td align="right" colspan="1">
                超规模占用费和规模闲置费2次未扣分得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="scaleTwo1" name="scaleTwo1" class="rateParam"
                       value="${tbOrganRateParamList[0].scaleTwo}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].scaleTwo}
            </td>

        </tr>


    </table>
</form>
</body>

<script type="text/javascript">



</script>
</html>