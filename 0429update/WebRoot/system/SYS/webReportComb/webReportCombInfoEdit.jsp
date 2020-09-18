<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>贷种维护</title>
    <!--树组件开始-->
    <link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <%--树组件结束--%>
    <!--数据表格start-->
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <script type="text/javascript">
        window.onload = function () {
            initLoanCombSelectedNodes();
        };

        function initLoanCombSelectedNodes() {
            var setting = {check: {enable: true}};
            var combUrl = "<%=path%>/webReportComb/listLoanCombByLevel.htm?combLevel=";
            var combCode = $('#combCode').val();
            var combLevel = $("#combLevel").val();
            var zNodes = [];
            var selectedCombUrl = "<%=path%>/webReportComb/listAllSelectedLoanCombByLevelAndCode.htm?combCode=" + combCode + "&combLevel=" + combLevel;
            $.ajax({
                type: 'GET',
                url: selectedCombUrl,
                async: false,
                success: function (data) {
                    var nodes = data.data;
                    for (var i = 0; i < nodes.length; i++) {
                        var item = nodes[i];
                        item.icon = "<%=path%>/libs/icons/item.gif";
                        zNodes.push(item);
                    }
                    ;
                }
            });

            $.ajax({
                    type: 'GET',
                    url: combUrl + combLevel + "&isLine=false",
                    async: false,
                    success: function (data) {
                        var nodes = data.data;
                        for (var i = 0; i < nodes.length; i++) {
                            var item = nodes[i];
                            var isExist = false;
                            for (var j = 0; j < zNodes.length; j++) {
                                var node = zNodes[j];
                                if (node.id == item.id) {
                                    isExist = true;
                                }
                            }
                            ;
                            if (!isExist) {
                                item.icon = "<%=path%>/libs/icons/item.gif";
                                zNodes.push(item);
                            }
                        }
                        ;
                    }
                }
            );
            $.fn.zTree.init($("#tree"), setting, zNodes);
        }

        function getProductCheckedNodes() {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var checkedNodes = treeObj.getCheckedNodes(true);
            var msg = "";
            for (var i = 0; i < checkedNodes.length; i++) {
                msg += "," + checkedNodes[i].id;
            }
            msg = msg.substring(1);
            return msg;
        }

        function sub() {
            var valid = $("#form1").validationEngine({
                returnIsValid: true
            });
            var productIds = getProductCheckedNodes();
            if (productIds.trim() === "") {
                productIds = "";
            }
            if (valid) {
                var combCode = $("#combCode").val();
                var combName = $("#combName").val();
                var combLevel = $("#combLevel").val();
                if (checkLoanCombInfo("NONE_FLAG", combName)) {
                    return;
                }
                $.post("<%=path%>/webReportComb/updateLoanCombInfo.htm", {
                    "productIds": productIds,
                    "combCode": combCode,
                    "combName": combName,
                    "combLevel": combLevel
                }, function (result) {
                    if (result.success === "true" || result.success === true) {
                        top.Dialog.alert("更新贷种组合成功!", function () {
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
                        top.Dialog.alert("更新贷种组合失败!");
                    }
                }, "json");
            } else {
                top.Dialog.alert("参数验证未通过!");
            }
        }

        function checkLoanCombInfo(combCode, combName) {
            var oldCombName = $("#oldCombName").val();
            if (combName.trim() === oldCombName) {
                return false;
            }
            var checkUrl = "<%=path%>/webReportComb/checkCombInfo.htm";
            var isExist = false;
            $.ajax({
                    type: 'POST',
                    url: checkUrl,
                    data: {
                        "combName": combName,
                        "combCode": combCode
                    },
                    async: false,
                    success: function (res) {
                        if (res.success === "false" || res.success === false) {
                            isExist = true;
                            if (res.code === 1 || res.code === "1") {
                                top.Dialog.alert("贷种编码已存在");
                            }
                            if (res.code === 2 || res.code === "2") {
                                top.Dialog.alert("贷种组合名称已存在");
                            }
                        }
                    }
                }
            );
            return isExist;
        }
    </script>
</head>
<body>
<form id="form1">
    <div class="basicTabModern">
        <input id="oldCombName" style="display: none" value="${webLoanComb.combName}">
        <div name="贷种基本信息" style="width:100%;height:100%;">
            <table class="tableStyle" width="100%" mode="list" formMode="line">
                <tr>
                    <td align="right">
                        贷种组合级别:
                    </td>
                    <td>
                        <dic:select disabled="disabled" id="combLevel" dicType="combLevel" name="combLevel"
                                    dicNo="${webLoanComb.combLevel}" tgClass="validate[required]"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        贷种编号：
                    </td>
                    <td>
                        <input type="text" id="combCode" disabled="disabled" name="combCode"
                               value="${webLoanComb.combCode}"
                               class="validate[required,custom[noSpecialCaracterswithoutAppoint]]" maxlength="15"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        贷种名称：
                    </td>
                    <td>
                        <input type="text" id="combName" name="combName" value="${webLoanComb.combName}"
                               class="validate[required,custom[illegalLetter]" maxlength="20"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div align="center">
                            <button type="button" class="firstButton"/>
                            <button type="button" class="downButton"/>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <%--第二页--%>
        <div name="贷种产品列表" style="width:100%;height:100%;">
            <fieldset>
                <div>
                    <ul id="tree" class="ztree"></ul>
                </div>
            </fieldset>
            <div class="height_15"></div>
            <div class="height_5"></div>
            <div class="padding_top10">
                <table class="tableStyle" formMode="transparent">
                    <tr>
                        <td colspan="4">
                            <button type="button" onclick="return sub()" class="saveButton"/>
                            <button type="button" onclick="return cancel()" class="cancelButton"/>
                        </td>
                    </tr>
                </table>
            </div>
            <br/>
            <br/>
        </div>
    </div>
</form>
</body>
</html>