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

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low3" name="low3" class="rateParam"
                       value="${tbOrganRateParamList[2].low}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[2].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high3" name="high3" class="rateParam"
                       value="${tbOrganRateParamList[2].high}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[2].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar3" name="lowHighVar3" class="rateParam"
                       value="${tbOrganRateParamList[2].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[2].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore3" name="varScore3" class="rateParam"
                       value="${tbOrganRateParamList[2].varScore}" maxlength="20"
                       oninput='upperCase(this)'      />
                ${tbOrganRateParamList[2].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore3" name="directScore3" class="rateParam"
                       value="${tbOrganRateParamList[2].directScore}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[2].directScore}
            </td>

        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low4" name="low4" class="rateParam"
                       value="${tbOrganRateParamList[3].low}" maxlength="20"
                       oninput='upperCase(this)'   />
                ${tbOrganRateParamList[3].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high4" name="high4" class="rateParam"
                       value="${tbOrganRateParamList[3].high}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[3].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar4" name="lowHighVar4" class="rateParam"
                       value="${tbOrganRateParamList[3].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[3].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore4" name="varScore4" class="rateParam"
                       value="${tbOrganRateParamList[3].varScore}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[3].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore4" name="directScore4" class="rateParam"
                       value="${tbOrganRateParamList[3].directScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[3].directScore}
            </td>

        </tr>


        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low5" name="low5" class="rateParam"
                       value="${tbOrganRateParamList[4].low}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[4].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high5" name="high5" class="rateParam"
                       value="${tbOrganRateParamList[4].high}" maxlength="20"
                       oninput='upperCase(this)'  />
                ${tbOrganRateParamList[4].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar5" name="lowHighVar5" class="rateParam"
                       value="${tbOrganRateParamList[4].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[4].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore5" name="varScore5" class="rateParam"
                       value="${tbOrganRateParamList[4].varScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[4].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore5" name="directScore5" class="rateParam"
                       value="${tbOrganRateParamList[4].directScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[4].directScore}
            </td>

        </tr>


        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low6" name="low6" class="rateParam"
                       value="${tbOrganRateParamList[5].low}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[5].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high6" name="high6" class="rateParam"
                       value="${tbOrganRateParamList[5].high}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[5].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar6" name="lowHighVar6" class="rateParam"
                       value="${tbOrganRateParamList[5].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[5].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore6" name="varScore6" class="rateParam"
                       value="${tbOrganRateParamList[5].varScore}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[5].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore6" name="directScore6" class="rateParam"
                       value="${tbOrganRateParamList[5].directScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[5].directScore}
            </td>

        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low7" name="low7" class="rateParam"
                       value="${tbOrganRateParamList[6].low}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[6].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high7" name="high7" class="rateParam"
                       value="${tbOrganRateParamList[6].high}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[6].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar7" name="lowHighVar7" class="rateParam"
                       value="${tbOrganRateParamList[6].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[6].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore7" name="varScore7" class="rateParam"
                       value="${tbOrganRateParamList[6].varScore}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[6].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore7" name="directScore7" class="rateParam"
                       value="${tbOrganRateParamList[6].directScore}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[6].directScore}
            </td>

        </tr>


        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="hidden" id="low8" name="low8" class="rateParam"
                       value="${tbOrganRateParamList[7].low}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[7].low}
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="hidden" id="high8" name="high8" class="rateParam"
                       value="${tbOrganRateParamList[7].high}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[7].high}
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="hidden" id="lowHighVar8" name="lowHighVar8" class="rateParam"
                       value="${tbOrganRateParamList[7].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[7].lowHighVar}
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="hidden" id="varScore8" name="varScore8" class="rateParam"
                       value="${tbOrganRateParamList[7].varScore}" maxlength="20"
                       oninput='upperCase(this)'/>
                ${tbOrganRateParamList[7].varScore}
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="hidden" id="directScore8" name="directScore8" class="rateParam"
                       value="${tbOrganRateParamList[7].directScore}" maxlength="20"
                       oninput='upperCase(this)' />
                ${tbOrganRateParamList[7].directScore}
            </td>

        </tr>


    </table>
</form>
</body>

<script type="text/javascript">


</script>
</html>