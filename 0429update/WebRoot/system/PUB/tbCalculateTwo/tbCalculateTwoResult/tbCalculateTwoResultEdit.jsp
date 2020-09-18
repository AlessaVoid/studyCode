<%@page language="java" pageEncoding="UTF-8" %>
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
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <title></title>
</head>
<script type="text/javascript">
    var month = '${month}';
    $(function () {
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbCalculateTwoResult/getDetailList.htm",
            data: {"month": month},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbCalculateTwoResults = result.tbCalculateTwoResults;
                    for (var i = 0; i < tbCalculateTwoResults.length; i++) {
                        var tbTwo = tbCalculateTwoResults[i];
                        var organcode = tbTwo.organcode;
                        $("#" + organcode + "_code1").html(tbTwo.code1);
                        $("#" + organcode + "_code2").html(tbTwo.code2);
                        $("#" + organcode + "_code3").html(tbTwo.code3);
                        $("#" + organcode + "_code4").html(tbTwo.code4);
                        $("#" + organcode + "_code5").html(tbTwo.code5);
                        $("#" + organcode + "_code6").html(tbTwo.code6);
                        $("#" + organcode + "_code7").html(tbTwo.code7);
                        $("#" + organcode + "_code8").html(tbTwo.code8);
                        $("#" + organcode + "_code9").html(tbTwo.code9);
                        $("#" + organcode + "_code10").html(tbTwo.code10);
                        $("#" + organcode + "_code11").html(tbTwo.code11);
                        $("#" + organcode + "_code12").html(tbTwo.code12);
                        $("#" + organcode + "_code13").html(tbTwo.code13);
                        $("#" + organcode + "_code14").html(tbTwo.code14);
                        $("#" + organcode + "_code15").html(tbTwo.code15);
                        $("#" + organcode + "_code16").html(tbTwo.code16);
                        $("#" + organcode + "_code17").html(tbTwo.code17);
                        $("#" + organcode + "_code18").html(tbTwo.code18);
                        $("#" + organcode + "_code20").html(tbTwo.code20);
                        $("#" + organcode + "_code21").html(tbTwo.code21);
                        $("#" + organcode + "_code21").val(tbTwo.code21);
                        $("#" + organcode + "_code22").val(tbTwo.code22);
                        $("#" + organcode + "_code23").html(tbTwo.code23);

                    }

                    //冻结行列 行首 行末 列首 列末
                    $("#plan").FrozenTable(1, 0, 1, 0);

                }
            }
        });
    });

    //提交
    function sub() {

        var tbDetail = $("#form1").serialize();

        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            top.Dialog.alert("请检查数据正确性");
            return;
        }

        $.ajax({
            type: "POST",
            url: "<%=path%>/tbCalculateTwoResult/update.htm",
            data: {"month": month, "tbDetail": tbDetail},
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("更新成功!", function () {
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
                        top.Dialog.alert("修改失败");
                    } else {
                        top.Dialog.alert("修改失败");
                    }
                }
            },
            error: function (result) {
                top.Dialog.alert("请求异常");
            }
        });
    }

    //用户只能输入正负数与小数
    function upperCase(obj, code) {
        obj.value = obj.value.replace(/[^\d.]/g, "");
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g, "");
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g, ".");
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }

        var beforeNum = $("#" + code + "_code21").val();
        var afterNum = Number(beforeNum) + Number(obj.value);
        $("#" + code + "_code23").html(afterNum);

    }


</script>
<body>
<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 680px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150">机构名称</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <input type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                            ${combAmountName.name}
                    </td>
                </c:forEach>
                <td>
                    调整值
                </td>
                <td>
                    最终月度计划额度
                </td>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <input type="hidden" class="planamonut_organ" id="${comb.combCode}"/>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                        <span value="0"
                              type="text" id="${comb.combCode}_${combAmountName.code}"/>
                        </td>
                    </c:forEach>

                    <td>
                        <input name="${comb.combCode}_code21" hidden="hidden"
                               type="text" id="${comb.combCode}_code21"  value="0"/>
                        <input name="${comb.combCode}_code22" AUTOCOMPLETE="off"
                               class="planamonut ${comb.combCode } ${combAmountName.code}"
                               onkeyup='upperCase(this,"${comb.combCode}")' value="0" maxlength="16"
                               type="text" id="${comb.combCode}_code22"/>
                    </td>
                    <td>
                        <span id="${comb.combCode}_code23">0</span>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>
    <div align="center">
        <button type="button" onclick="sub()" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</form>
</body>
</html>