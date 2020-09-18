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
        //获取json数据
        $.post("<%=path%>/tbTradeManger/tbReqList/findComb.htm",
            {}, function (result) {
                //赋给data
                $("#tpComb").data("data", result)
                //刷新下拉框
                $("#tpComb").render();
            }, "json");
        $("#tpComb").setValue("${tbEvaluateParamList[0].tpComb}");
    }
</script>

<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="left" colspan="1">
                评分名称：
            </td>
            <td colspan="1">
                <input type="text" id="tpName" name="tpName" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[0].tpName}" maxlength="20"
                       required="true"/>
                <span class="star">*</span>
            </td>
            <td align="right" colspan="1">
                贷种组合id:
            </td>
            <td colspan="1">
                <select id="tpComb" name="tpComb" disabled="disabled"></select>
                <span class="star">*</span>
            </td>
            <td align="right" colspan="1">
                满分值:
            </td>
            <td colspan="1">
                <input type="text" id="tpFullScore" name="tpFullScore" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[0].tpFullScore}" maxlength="5"
                       required="true"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum1" name="minnum1" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[0].tpMin}" maxlength="2"
                       disabled="disabled" required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum1" name="maxnum1" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[0].tpMax}" maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction1" name="tpDeduction1" class="validate[required]"
                       value="${tbEvaluateParamList[0].tpDeduction}" inputMode="numberOnly"
                       disabled="disabled" maxlength="20"
                       required="true"/><span id="span1" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum2" name="minnum2" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[1].tpMin}" maxlength="2"
                       disabled="disabled" required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum2" name="maxnum2" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[1].tpMax}" maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction2" name="tpDeduction2" class="validate[required]"
                       disabled="disabled" value="${tbEvaluateParamList[1].tpDeduction}" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span2" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum3" name="minnum3" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[2].tpMin}" maxlength="2"
                       disabled="disabled" required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum3" name="maxnum3" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[2].tpMax}" maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction3" name="tpDeduction3" class="validate[required]"
                       value="${tbEvaluateParamList[2].tpDeduction}" inputMode="numberOnly"
                       disabled="disabled" maxlength="20"
                       required="true"/><span id="span3" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum4" name="minnum4" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[3].tpMin}" maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum4" name="maxnum4" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[3].tpMax}" maxlength="2"
                       disabled="disabled" required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction4" name="tpDeduction4" class="validate[required]"
                       disabled="disabled" value="${tbEvaluateParamList[3].tpDeduction}" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span4" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum5" name="minnum5" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[4].tpMin}" maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum5" name="maxnum5" class="validate[required]" inputMode="numberOnly"
                       disabled="disabled" value="${tbEvaluateParamList[4].tpMax}" maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction5" name="tpDeduction5" class="validate[required]"
                       value="${tbEvaluateParamList[4].tpDeduction}" inputMode="numberOnly"
                       disabled="disabled" maxlength="20"
                       required="true"/><span id="span5" class="star"></span><span class="star">*</span>
            </td>

        </tr>

    </table>
</form>
</body>
</html>