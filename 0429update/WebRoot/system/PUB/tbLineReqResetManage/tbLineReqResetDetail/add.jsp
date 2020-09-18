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
    var lineReqresetId = '${lineReqresetId}';
    //提交
    function sub(testNum) {
        //校验
        var lineUnit = $("#lineUnit").val();
        var lineReqresetId = $("#lineReqresetId").val();

        var lineResetrefId = $("#lineResetrefId").val();
        var tbLineReqResetDetail = $("#form1").serialize();

        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            top.Dialog.alert("请检查数据正确性");
            return;
        }
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbLineReqResetDetail/insert.htm",
            data: {
                "lineUnit": lineUnit,
                "tbLineReqResetDetail": tbLineReqResetDetail,
                "lineReqresetId": lineReqresetId,
                "state": 2,
                "lineResetrefId": lineResetrefId,
                "testNum": testNum
            },
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("新增成功!", function () {
                        var menu_id = parent.getCurrentTabId();
                        if (menu_id == undefined) {
                            top.Dialog.close();
                            return;
                        }
                        var menu_frame_id = "page_" + menu_id;
                        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                        top.Dialog.close();
                    });
                } else {
                    if (result.code == '403') {
                        top.Dialog.alert("所属月份计划已存在");
                    } else if (result.code == '500') {
                        top.Dialog.alert("当前批次的条线需求调整已录入，请转至菜单查看", function () {
                            var menu_id = parent.getCurrentTabId();
                            if (menu_id == undefined) {
                                top.Dialog.close();
                                return;
                            }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                    } else if (result.code == '505') {

                    } else {
                        top.Dialog.alert("新增失败");
                    }
                }
            },
            error: function (result) {
                top.Dialog.alert("请求异常");
            }
        });
    }


    //用户只能输入正负数与小数
    function upperCase(obj) {
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }

        var thisId = obj.id.split("_")[0];
        var oldNum=  document.getElementById( thisId + "_oldNum").innerHTML;
        var newNum = Number(obj.value ) + Number(oldNum);
        $("#" + thisId + "_newNum").html(newNum);
    }

    $(function () {
        sub(9999);
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbLineReqResetDetail/getReqDetailList.htm",
            data: {"lineReqresetId": lineReqresetId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbLineReqResetDetailDTOS = result.tbLineReqResetDetailDTOS;
                    for (var i = 0; i < tbLineReqResetDetailDTOS.length; i++) {
                        var tbReqDetail = tbLineReqResetDetailDTOS[i];
                        var reqdCombCode = tbReqDetail.lineCombCode;
                        var reqdNum = tbReqDetail.lineNum;

                        var newPlan = tbReqDetail.newPlan;
                        var oldPlan = tbReqDetail.oldPlan;
                        $("#" + reqdCombCode + "_newNum").html(newPlan);
                        $("#" + reqdCombCode + "_oldNum").html(oldPlan);

                        $("#" + reqdCombCode + "_num").val(reqdNum);
                    }
                }
                addPlanAmonut();
            }
        });

    });

    // 计算调整金额的总和
    function addPlanAmonut() {
        var amonutList = $(".planAmount");
        var amountCount = 0;
        $(amonutList).each(function () {
            var id = this.id;
            if (id.split("_")[1] == "oldNum" || id.split("_")[1] == "newNum") {
            } else {
                amountCount = Number(amountCount) + Number(this.value);
                var thisId = this.id.split("_")[0];
                var oldNum=  document.getElementById( thisId + "_oldNum").innerHTML;
                var newNum = Number(this.value ) + Number(oldNum);
                $("#" + thisId + "_newNum").html(newNum);
            }
        });
        return amountCount;
    }
</script>

<body>

<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="lineResetrefId" name="lineResetrefId" type="hidden"
                       value="${tbLineReqresetDetail.lineResetrefId}"/>
                <input id="lineReqresetId" name="lineReqresetId" type="hidden"
                       value="${tbLineReqresetDetail.lineReqresetId}"/>
                <input id="lineReqMonth" name="lineReqMonth" type="hidden"
                       value="${tbLineReqresetDetail.lineReqMonth}"> </input>
                ${tbLineReqresetDetail.lineReqMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="lineUnit" name="lineUnit" type="hidden" value="${tbLineReqresetDetail.lineUnit}"> </input>
                <c:if test="${tbLineReqresetDetail.lineUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbLineReqresetDetail.lineUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                ${tbLineReqresetDetail.lineReqName}
            </td>

            <td align="right">
                条线名称：
            </td>
            <td>
                ${tbLineReqresetDetail.lineName}
            </td>
        </tr>
        <tr>
            <td align="right">
                周期起始时间：
            </td>
            <td>
                ${TbReqresetList.reqresetTimeStart}
            </td>

            <td align="right">
                周期结束时间：
            </td>
            <td>
                ${TbReqresetList.reqresetTimeEnd}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${tbLineReqresetDetail.lineReqNote}
            </td>
        </tr>
    </table>
</form>


<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <th trueWidth="150"></th>
                <th align="center">
                    原计划
                </th>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <th align="center">
                            ${combAmountName.name}
                    </th>
                </c:forEach>
                <th align="center">
                    调整后计划
                </th>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <th> ${comb.combName}</th>

                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span id="${comb.combCode}_oldNum">${comb.oldNum}</span>
                        </td>
                    </c:forEach>

                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${comb.combCode}_${combAmountName.code}" AUTOCOMPLETE="off"
                                   class=" validate[required,custom[onlyNumberWideSpecial]]"
                                   onkeyup='upperCase(this)' value="0"
                                   type="text" id="${comb.combCode}_${combAmountName.code}" maxlength="16"/>
                        </td>
                    </c:forEach>

                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span id="${comb.combCode}_newNum">0</span>
                        </td>
                    </c:forEach>

                </tr>
            </c:forEach>
        </table>
    </div>
    <div align="center">
        <button type="button" onclick="sub(1)" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</form>
</body>
</html>