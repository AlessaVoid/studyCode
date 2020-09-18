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

    function initComplete() {
        //初始化span
        for (var num = 1; num < 9; num++) {
            if ($('#collecttype' + num).val() == 0) {
                $('#span' + num).html("%");
            } else {
                $('#span' + num).html("元");
            }
        }


    }
</script>

<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="left" colspan="2">
                时间类型：
            </td>
            <td colspan="3">
                <dic:select id="type" dicType="PENALTY_TIME_TYPE" name="type" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[0].type}" required="true" disabled="disabled"></dic:select>

            </td>
            <td align="right" colspan="2">
                状态:
            </td>
            <td colspan="3">
                <dic:select id="state" dicType="PENALTY_STATE" name="state" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[0].state}" required="true" disabled="disabled"></dic:select>

            </td>
        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType1" dicType="PENALTY_TYPE" name="ppType1" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[0].ppType}" required="true" disabled="disabled"></dic:select>

            </td>
            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype1" dicType="COLLECT_TYPE" name="collecttype1" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[0].collecttype}" onchange="check(1);"
                            required="true" disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum1" name="minnum1" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[0].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum1" name="maxnum1" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[0].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest1" name="interest1" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[0].interest}"
                       required="true" disabled="disabled"/><span id="span1" class="star"></span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType2" dicType="PENALTY_TYPE" name="ppType2" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[1].ppType}" required="true" disabled="disabled"></dic:select>

            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype2" dicType="COLLECT_TYPE" name="collecttype2" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[1].collecttype}" onchange="check(2);" required="true"
                            disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum2" name="minnum2" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[1].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum2" name="maxnum2" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[1].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest2" name="interest2" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[1].interest}"
                       required="true" disabled="disabled"/><span id="span2" class="star"></span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType3" dicType="PENALTY_TYPE" name="ppType3" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[2].ppType}" required="true" disabled="disabled"></dic:select>

            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype3" dicType="COLLECT_TYPE" name="collecttype3" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[2].collecttype}" onchange="check(3);" required="true"
                            disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum3" name="minnum3" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[2].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum3" name="maxnum3" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[2].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest3" name="interest3" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[2].interest}"
                       required="true" disabled="disabled"/><span id="span3" class="star"></span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType4" dicType="PENALTY_TYPE" name="ppType4" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[3].ppType}" required="true" disabled="disabled"></dic:select>

            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype4" dicType="COLLECT_TYPE" name="collecttype4" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[3].collecttype}" onchange="check(4);" required="true"
                            disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum4" name="minnum4" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[3].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum4" name="maxnum4" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[3].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest4" name="interest4" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[3].interest}"
                       required="true" disabled="disabled"/><span id="span4" class="star"></span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType5" dicType="PENALTY_TYPE" name="ppType5" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[4].ppType}" required="true" disabled="disabled"></dic:select>

            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype5" dicType="COLLECT_TYPE" name="collecttype5" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[4].collecttype}" onchange="check(5);" required="true"
                            disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum5" name="minnum5" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[4].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum5" name="maxnum5" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[4].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest5" name="interest5" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[4].interest}"
                       required="true" disabled="disabled"/><span id="span5" class="star"></span>
            </td>

        </tr>

        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType6" dicType="PENALTY_TYPE" name="ppType6" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[5].ppType}" required="true" disabled="disabled"></dic:select>

            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype6" dicType="COLLECT_TYPE" name="collecttype6" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[5].collecttype}" onchange="check(6);" required="true"
                            disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum6" name="minnum6" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[5].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum6" name="maxnum6" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[5].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest6" name="interest6" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[5].interest}"
                       required="true" disabled="disabled"/><span id="span6" class="star"></span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType7" dicType="PENALTY_TYPE" name="ppType7" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[6].ppType}" required="true" disabled="disabled"></dic:select>

            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype7" dicType="COLLECT_TYPE" name="collecttype7" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[6].collecttype}" onchange="check(7);" required="true"
                            disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum7" name="minnum7" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[6].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum7" name="maxnum7" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[6].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest7" name="interest7" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[6].interest}"
                       required="true" disabled="disabled"/><span id="span7" class="star"></span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType8" dicType="PENALTY_TYPE" name="ppType8" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[7].ppType}" required="true" disabled="disabled"></dic:select>

            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype8" dicType="COLLECT_TYPE" name="collecttype8" tgClass="validate[required]"
                            dicNo="${TbPunishParamDTOList[7].collecttype}" onchange="check(8);" required="true"
                            disabled="disabled"></dic:select>

            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum8" name="minnum8" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[7].minnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum8" name="maxnum8" class="validate[required]" inputMode="numberOnly"
                       maxlength="2" value="${TbPunishParamDTOList[7].maxnum}"
                       required="true" disabled="disabled"/><span>% </span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest8" name="interest8" class="validate[required]" inputMode="numberOnly"
                       maxlength="20" value="${TbPunishParamDTOList[7].interest}"
                       required="true" disabled="disabled"/><span id="span8" class="star"></span>
            </td>
        </tr>

    </table>
</form>
</body>
</html>