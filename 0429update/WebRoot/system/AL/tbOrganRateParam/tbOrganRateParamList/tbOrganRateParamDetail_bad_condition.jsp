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
            <td align="right" colspan="1">
                目标分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="targetScore" name="targetScore" class="rateParam"
                       value="${tbOrganRateParamList[0].targetScore}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[0].targetScore}
            </td>
            <td align="right" colspan="1">
                最小分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="minTargetScore" name="minTargetScore" class="rateParam"
                       value="${tbOrganRateParamList[0].minTargetScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].minTargetScore}
            </td>
            <td align="right" colspan="1">
                最大分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="maxTargetScore" name="maxTargetScore" class="rateParam"
                       value="${tbOrganRateParamList[0].maxTargetScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].maxTargetScore}
            </td>
        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low1" name="low1" class="rateParam"
                       value="${tbOrganRateParamList[0].low}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high1" name="high1" class="rateParam"
                       value="${tbOrganRateParamList[0].high}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar1" name="lowHighVar1" class="rateParam"
                       value="${tbOrganRateParamList[0].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore1" name="varScore1" class="rateParam"
                       value="${tbOrganRateParamList[0].varScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[0].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore1" name="directScore1" class="rateParam"
                       value="${tbOrganRateParamList[0].directScore}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[0].directScore}
            </td>

        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low2" name="low2" class="rateParam"
                       value="${tbOrganRateParamList[1].low}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[1].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high2" name="high2" class="rateParam"
                       value="${tbOrganRateParamList[1].high}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[1].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar2" name="lowHighVar2" class="rateParam"
                       value="${tbOrganRateParamList[1].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[1].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore2" name="varScore2" class="rateParam"
                       value="${tbOrganRateParamList[1].varScore}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[1].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore2" name="directScore2" class="rateParam"
                       value="${tbOrganRateParamList[1].directScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[1].directScore}
            </td>

        </tr>
    </table>
</form>
</body>

<script type="text/javascript">


</script>
</html>