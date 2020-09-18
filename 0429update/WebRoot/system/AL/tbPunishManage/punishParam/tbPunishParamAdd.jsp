<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-7"/>
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
        var numBeyondList = [];
        var numIdleList = [];
        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            return;
        }
        for (var i = 1; i < 9; i++) {
            var min = $('#minnum' + i).val();
            var max = $('#maxnum' + i).val();
            var numArr = [];
            if (max - min <= 0) {
                top.Dialog.alert("最大值应大于最小值！");
                return;
            }
            numArr[0] = min;
            numArr[1] = max;
            if ($('#ppType' + i).val() == 0) {//0超出，1闲置
                numBeyondList[numBeyondList.length] = numArr;
            } else {
                numIdleList[numIdleList.length] = numArr;
            }
        }
        if (numBeyondList.length != 4 || numIdleList.length != 4) {
            top.Dialog.alert("闲置和超出应分别设置四个区间");
            return;
        }

        //数组添加完成后 需要校验逻辑可行性
        numBeyondList = sortArr(numBeyondList);
        numIdleList = sortArr(numIdleList);
        if (checkArr(numBeyondList) == "false" | checkArr(numBeyondList) == false) {
            top.Dialog.alert("超限逻辑不正确");
            return;
        }
        if (checkArr(numIdleList) == "false" | checkArr(numIdleList) == false) {
            top.Dialog.alert("闲置额逻辑不正确");
            return;
        }
        var data = new Array();

        for (var i = 1; i < 9; i++) {

            var map = {
                "type": $('#type').val(),
                "state": $('#state').val(),
                "ppType": $('#ppType' + i).val(),
                "collecttype": $('#collecttype' + i).val(),
                "minnum": $('#minnum' + i).val(),
                "maxnum": $('#maxnum' + i).val(),
                "interest": $('#interest' + i).val()
            }

            data.push(map);
        }

        $.post('<%=path%>/punishManger/punishParam/insert.htm',
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
    function checkArr(numBeyondList) {
        for (var j = 0; j < numBeyondList.length - 1; j++) {
            for (var k = 0; k < numBeyondList.length - 1 - j; k++) {
                if (numBeyondList[k][1] - numBeyondList[k + 1][0] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //二维数组排序
    function sortArr(numBeyondList) {
        for (var j = 0; j < numBeyondList.length - 1; j++) {
            for (var k = 0; k < numBeyondList.length - 1 - j; k++) {
                if (numBeyondList[k][0] - numBeyondList[k + 1][0] > 0) {
                    var temp = numBeyondList[k];
                    numBeyondList[k] = numBeyondList[k + 1];
                    numBeyondList[k + 1] = temp;
                }
            }
        }
        return numBeyondList;
    }


    function check(num) {
        if ($('#collecttype' + num).val() == 0) {
            $('#span' + num).html("%");
        } else {
            $('#span' + num).html("元");
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
                            required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right" colspan="2">
                状态:
            </td>
            <td colspan="3">
                <dic:select id="state" dicType="PENALTY_STATE" name="state" tgClass="validate[required]"
                            dicNo="0" required="true"></dic:select>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType1" dicType="PENALTY_TYPE" name="ppType1" tgClass="validate[required]"
                            dicNo="0" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype1" dicType="COLLECT_TYPE" name="collecttype1" tgClass="validate[required]"
                            onchange="check(1);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum1" name="minnum1" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"

                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum1" name="maxnum1" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest1" name="interest1" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span1" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType2" dicType="PENALTY_TYPE" name="ppType2" tgClass="validate[required]"
                            dicNo="0" required="true"></dic:select>
                <span class="star">*</span>
            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype2" dicType="COLLECT_TYPE" name="collecttype2" tgClass="validate[required]"
                            onchange="check(2);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum2" name="minnum2" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum2" name="maxnum2" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest2" name="interest2" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span2" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType3" dicType="PENALTY_TYPE" name="ppType3" tgClass="validate[required]"
                            dicNo="0" required="true"></dic:select>
                <span class="star">*</span>
            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype3" dicType="COLLECT_TYPE" name="collecttype3" tgClass="validate[required]"
                            onchange="check(3);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum3" name="minnum3" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum3" name="maxnum3" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest3" name="interest3" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span3" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType4" dicType="PENALTY_TYPE" name="ppType4" tgClass="validate[required]"
                            dicNo="0" required="true"></dic:select>
                <span class="star">*</span>
            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype4" dicType="COLLECT_TYPE" name="collecttype4" tgClass="validate[required]"
                            onchange="check(4);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum4" name="minnum4" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum4" name="maxnum4" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest4" name="interest4" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span4" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType5" dicType="PENALTY_TYPE" name="ppType5" tgClass="validate[required]"
                            dicNo="1" required="true"></dic:select>
                <span class="star">*</span>
            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype5" dicType="COLLECT_TYPE" name="collecttype5" tgClass="validate[required]"
                            onchange="check(5);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum5" name="minnum5" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum5" name="maxnum5" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest5" name="interest5" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span5" class="star"></span><span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType6" dicType="PENALTY_TYPE" name="ppType6" tgClass="validate[required]"
                            dicNo="1" required="true"></dic:select>
                <span class="star">*</span>
            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype6" dicType="COLLECT_TYPE" name="collecttype6" tgClass="validate[required]"
                            onchange="check(6);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum6" name="minnum6" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum6" name="maxnum6" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest6" name="interest6" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span6" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType7" dicType="PENALTY_TYPE" name="ppType7" tgClass="validate[required]"
                            dicNo="1" required="true"></dic:select>
                <span class="star">*</span>
            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype7" dicType="COLLECT_TYPE" name="collecttype7" tgClass="validate[required]"
                            onchange="check(7);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum7" name="minnum7" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum7" name="maxnum7" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest7" name="interest7" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span7" class="star"></span><span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="left">罚息类型：</td>
            <td>
                <dic:select id="ppType8" dicType="PENALTY_TYPE" name="ppType8" tgClass="validate[required]"
                            dicNo="1" required="true"></dic:select>
                <span class="star">*</span>
            </td>

            <td align="right">
                收取类型：
            </td>
            <td>
                <dic:select id="collecttype8" dicType="COLLECT_TYPE" name="collecttype8" tgClass="validate[required]"
                            onchange="check(8);" required="true"></dic:select>
                <span class="star">*</span>
            </td>
            <td align="right">
                最小值：
            </td>
            <td>
                <input type="text" id="minnum8" name="minnum8" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                最大值:
            </td>
            <td>
                <input type="text" id="maxnum8" name="maxnum8" class="validate[required]" inputMode="numberOnly"
                       maxlength="2"
                       required="true"/><span>% </span><span class="star">*</span>
            </td>
            <td align="right">
                罚息:
            </td>
            <td>
                <input type="text" id="interest8" name="interest8" class="validate[required]" inputMode="numberOnly"
                       maxlength="20"
                       required="true"/><span id="span8" class="star"></span><span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td colspan="10">
                <div align="center">
                    <button type="button" onclick="submitInfo()" class="saveButton" colspan="5"/>
                    <button type="button" onclick="return cancel()" class="cancelButton" colspan="5"/>
                </div>
                <div class="staticTip" style="width: 1300px;">
                    提示：1.最大值、最小值输入框中请输入非负整数，最多支持2位。2.同一行数据中，最大值需大于最小值。3.同一罚息类型
                    中，下一行的最小值应大于等于上一行的最大值。4.罚息输入框中请输入非负整数，最多支持20位。
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>