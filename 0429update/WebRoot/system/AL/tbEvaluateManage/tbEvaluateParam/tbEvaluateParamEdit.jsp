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
        var numList = [];
        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            return;
        }
        for (var i = 1; i < 6; i++) {
            var min = $('#minnum' + i).val();
            var max = $('#maxnum' + i).val();
            var numArr = [];
            if (max - min <= 0) {
                top.Dialog.alert("最大值应大于最小值！");
                return;
            }
            numArr[0] = min;
            numArr[1] = max;
            numList[numList.length] = numArr;
        }

        //数组添加完成后 需要校验逻辑可行性
        numList = sortArr(numList);
        if (checkArr(numList) == "false" | checkArr(numList) == false) {
            top.Dialog.alert("评价区间逻辑不正确");
            return;
        }
        var data = new Array();

        for (var i = 1; i < 6; i++) {

            var map = {
                "tpName": $('#tpName').val(),
                "tpComb": $('#tpComb').val(),
                "tpFullScore": $('#tpFullScore').val(),
                "tpMin": $('#minnum' + i).val(),
                "tpMax": $('#maxnum' + i).val(),
                "tpDeduction": $('#tpDeduction' + i).val()
            }

            data.push(map);
        }

        $.post('<%=path%>/evaluateManger/tbEvaluateParam/update.htm',
            {'data': JSON.stringify(data)},
            function (result) {
                if (result.success == "true" || result.success == true) {
                    top.Dialog.alert(result.msg, function () {
                        top.frmright.window.location.reload(true);
                        top.Dialog.close();
                    });
                } else {
                    $(".money").each(function () {
                        fmoney(this);
                    });
                    top.Dialog.alert(result.msg);
                }
            }, "json");


    }

    //校验二维数组逻辑合理性
    function checkArr(numList) {
        for (var j = 0; j < numList.length - 1; j++) {
            for (var k = 0; k < numList.length - 1 - j; k++) {
                if (numList[k][1] - numList[k + 1][0] >= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //二维数组排序
    function sortArr(numList) {
        for (var j = 0; j < numList.length - 1; j++) {
            for (var k = 0; k < numList.length - 1 - j; k++) {
                if (numList[k][0] - numList[k + 1][0] > 0) {
                    var temp = numList[k];
                    numList[k] = numList[k + 1];
                    numList[k + 1] = temp;
                }
            }
        }
        return numList;
    }


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
                <input type="text" id="tpName" name="tpName" class="validate[required]"
                       value="${tbEvaluateParamList[0].tpName}" maxlength="20"
                       required="true"/>
                <span class="star">*</span>
            </td>
            <td align="right" colspan="1">
                贷种组合id:
            </td>
            <td colspan="1">
                <select id="tpComb" name="tpComb"></select>
                <span class="star">*</span>
            </td>
            <td align="right" colspan="1">
                满分值:
            </td>
            <td colspan="1">
                <input type="text" id="tpFullScore" name="tpFullScore" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[0].tpFullScore}" maxlength="5"
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
                       value="${tbEvaluateParamList[0].tpMin}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum1" name="maxnum1" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[0].tpMax}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction1" name="tpDeduction1" class="validate[required]"
                       value="${tbEvaluateParamList[0].tpDeduction}" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span1" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum2" name="minnum2" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[1].tpMin}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum2" name="maxnum2" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[1].tpMax}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction2" name="tpDeduction2" class="validate[required]"
                       value="${tbEvaluateParamList[1].tpDeduction}" inputMode="numberOnly"
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
                       value="${tbEvaluateParamList[2].tpMin}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum3" name="maxnum3" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[2].tpMax}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction3" name="tpDeduction3" class="validate[required]"
                       value="${tbEvaluateParamList[2].tpDeduction}" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span3" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum4" name="minnum4" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[3].tpMin}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum4" name="maxnum4" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[3].tpMax}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction4" name="tpDeduction4" class="validate[required]"
                       value="${tbEvaluateParamList[3].tpDeduction}" inputMode="numberOnly"
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
                       value="${tbEvaluateParamList[4].tpMin}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum5" name="maxnum5" class="validate[required]" inputMode="numberOnly"
                       value="${tbEvaluateParamList[4].tpMax}" maxlength="3"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                扣分:
            </td>
            <td>
                <input type="text" id="tpDeduction5" name="tpDeduction5" class="validate[required]"
                       value="${tbEvaluateParamList[4].tpDeduction}" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span5" class="star"></span><span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td colspan="10">
                <div align="center">
                    <button type="button" onclick="submitInfo()" class="saveButton" colspan="5"/>
                    <button type="button" onclick="return cancel()" class="cancelButton" colspan="5"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>