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
                <input type="text" id="elementTypeName" name="elementTypeName"
                       value="${tbOrganRateParamList[0].elementTypeName}" maxlength="20"
                />
            </td>
            <td align="right" colspan="1">
                目标分值:
            </td>
            <td colspan="1">
                <input type="text" id="targetScore" name="targetScore" class="rateParam"
                       value="${tbOrganRateParamList[0].targetScore}" maxlength="20"
                       oninput='upperCase(this)'  />
            </td>
            <td align="right" colspan="1">
                最小分值:
            </td>
            <td colspan="1">
                <input type="text" id="minTargetScore" name="minTargetScore" class="rateParam"
                       value="${tbOrganRateParamList[0].minTargetScore}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>
            <td align="right" colspan="1">
                最大分值:
            </td>
            <td colspan="1">
                <input type="text" id="maxTargetScore" name="maxTargetScore" class="rateParam"
                       value="${tbOrganRateParamList[0].maxTargetScore}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>
        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="text" id="low1" name="low1" class="rateParam"
                       value="${tbOrganRateParamList[0].low}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="text" id="high1" name="high1" class="rateParam"
                       value="${tbOrganRateParamList[0].high}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="text" id="lowHighVar1" name="lowHighVar1" class="rateParam"
                       value="${tbOrganRateParamList[0].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="text" id="varScore1" name="varScore1" class="rateParam"
                       value="${tbOrganRateParamList[0].varScore}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="text" id="directScore1" name="directScore1" class="rateParam"
                       value="${tbOrganRateParamList[0].directScore}" maxlength="20"
                       oninput='upperCase(this)'  />
            </td>

        </tr>

        <%-----------------###########################----------------------%>
        <tr>
            <td align="right" colspan="1">
                区间左值:
            </td>
            <td colspan="1">
                <input type="text" id="low2" name="low2" class="rateParam"
                       value="${tbOrganRateParamList[1].low}" maxlength="20"
                       oninput='upperCase(this)'  />
            </td>

            <td align="right" colspan="1">
                区间右值:
            </td>
            <td colspan="1">
                <input type="text" id="high2" name="high2" class="rateParam"
                       value="${tbOrganRateParamList[1].high}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>

            <td align="right" colspan="1">
                扣分指标:
            </td>
            <td colspan="1">
                <input type="text" id="lowHighVar2" name="lowHighVar2" class="rateParam"
                       value="${tbOrganRateParamList[1].lowHighVar}" maxlength="20"
                       oninput='upperCase(this)' />
            </td>

            <td align="right" colspan="1">
                扣分值:
            </td>
            <td colspan="1">
                <input type="text" id="varScore2" name="varScore2" class="rateParam"
                       value="${tbOrganRateParamList[1].varScore}" maxlength="20"
                       oninput='upperCase(this)'  />
            </td>

            <td align="right" colspan="1">
                根据区间直接得分:
            </td>
            <td colspan="1">
                <input type="text" id="directScore2" name="directScore2" class="rateParam"
                       value="${tbOrganRateParamList[1].directScore}" maxlength="20"
                       oninput='upperCase(this)' />
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

<script type="text/javascript">

    var elementtype = '${elementtype}';

    //用户只能输入正负数与小数
    function upperCase(obj){
        if(isNaN(obj.value) && !/^-$/.test(obj.value)){
            obj.value="";
        }
        if(!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)){
            obj.value=obj.value.replace(/\.\d{2,}$/,obj.value.substr(obj.value.indexOf('.'),10));
        }
    }

    $(function(){

        //输入框获取焦点事件
        $(".rateParam").focus(function(){
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".rateParam").blur(function(){
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });
    })

    //修改
    function submitInfo() {
        var elementTypeName = $('#elementTypeName').val();
        if (elementTypeName.trim() == "") {
            top.Dialog.alert("参数名称不能为空！");
            return;
        }

        var paramCount = 3;
        var numList = [];

        for (var i = 1; i < paramCount; i++) {
            var min = $('#low' + i).val();
            var max = $('#high' + i).val();
            var numArr = [];
            if (max - min < 0) {
                top.Dialog.alert("区间右值不能小于区间左值！");
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

        for (var i = 1; i < paramCount; i++) {

            var map = {
                "elementType": elementtype,
                "elementTypeName": $('#elementTypeName').val(),
                "orderRate": $('#orderRate').val(),
                "targetScore": $('#targetScore').val(),
                "minTargetScore": $('#minTargetScore').val(),
                "maxTargetScore": $('#maxTargetScore').val(),
                "low": $('#low'+ i).val(),
                "high": $('#high'+ i).val(),
                "lowHighVar": $('#lowHighVar'+ i).val(),
                "varScore": $('#varScore'+ i).val(),
                "directScore": $('#directScore'+ i).val(),
                "adjCount": $('#adjCount'+ i).val(),
                "adjCountThree": $('#adjCountThree'+ i).val(),
                "adjCountTwo": $('#adjCountTwo'+ i).val(),
                "scaleThree": $('#scaleThree'+ i).val(),
                "scaleTwo": $('#scaleTwo'+ i).val()


            }

            data.push(map);
        }

        var paramStr = JSON.stringify(data);

        top.Dialog.confirm("确定要保存操作吗?|操作提示", function () {
            $.ajax({
                type: "POST",
                url: "<%=path%>/tbOrganRateParam/updateTbOrganRateParam.htm",
                data: {
                    "elementType": elementtype,
                    "paramStr": paramStr
                },
                dataType: "json",
                success: function (result) {
                    if (result.success == true || result.success == "true") {
                        $('#update').attr('disabled', false);
                        $('#cancelUpdate').attr('disabled', false);
                        top.Dialog.alert("修改成功!", function () {
                            var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                    } else {
                        $('#update').attr('disabled', false);
                        $('#cancelUpdate').attr('disabled', false);
                        if (result.code == '408') {
                            top.Dialog.alert(result.message);
                        } else {
                            top.Dialog.alert("修改失败");
                        }
                    }
                },
                error: function (result) {
                    $('#update').attr('disabled', false);
                    $('#cancelUpdate').attr('disabled', false);
                    top.Dialog.alert("请求异常");
                }
            });
        });
    }

    //校验二维数组逻辑合理性
    function checkArr(numList) {
        for (var j = 0; j < numList.length - 1; j++) {
            for (var k = 0; k < numList.length - 1 - j; k++) {
                if (numList[k][1] - numList[k + 1][0] > 0) {
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


</script>
</html>