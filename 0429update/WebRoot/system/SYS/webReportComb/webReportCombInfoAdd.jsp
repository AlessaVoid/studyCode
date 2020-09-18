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
        $(function () {
            var setting = {check: {enable: true}};
            var combUrl = "<%=path%>/webReportComb/listLoanCombByLevel.htm?combLevel=";
            $("#combLevel").change(function () {
                var combLevel = $("#combLevel").val();
                $.ajax({
                        type: 'GET',
                        url: combUrl + combLevel + "&isLine=false",
                        success: function (data) {
                            var zNodes = data.data;
                            for (var i = 0; i < zNodes.length; i++) {
                                var item = zNodes[i];
                                item.icon = "<%=path%>/libs/icons/item.gif";
                            }
                            $.fn.zTree.init($("#tree"), setting, zNodes);
                        }
                    }
                );
            });
        });

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
            if (productIds == null || productIds.trim() === "") {
                productIds = "";
            }

            if (valid) {
                var combCode = $("#combCode").val();
                var combName = $("#combName").val();
                var combLevel = $("#combLevel").val();
                if (checkLoanCombInfo(combCode, combName)) {
                    return;
                }
                $.post("<%=path%>/webReportComb/insertLoanCombInfo.htm", {
                    "combCode": combCode,
                    "combName": combName,
                    "combLevel": combLevel,
                    "productIds": productIds
                }, function (res) {
                    if (res.success === "true" || res.success === true) {
                        top.Dialog.alert("添加贷种组合成功!", function () {
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
                        top.Dialog.alert("添加贷种组合失败");
                    }
                }, "json");
            } else {
                top.Dialog.alert("贷种编号或贷种名称不合法！");
            }
        }

        function checkLoanCombInfo(combCode, combName) {
            var checkUrl = "<%=path%>/webReportComb/checkCombInfo.htm";
            var isExist = false;
            $.ajax({
                    type: 'POST',
                    url: checkUrl,
                    async: false, data: {
                        "combName": combName,
                        "combCode": combCode
                    },
                    success: function (res) {
                        if (res.success === "false" || res.success === false) {
                            isExist = true;
                            if (res.code === 1 || res.code === "1") {
                                top.Dialog.alert("贷种编码已存在");
                            }
                            if (res.code === 2 || res.code === "2") {
                                top.Dialog.alert("贷种组合id");
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
    <c:choose>
        <c:when test="${param.method=='update' }">
            <input type="hidden" name="roleCode" value="${webLoanInfo.roleCode }"/>
        </c:when>
    </c:choose>
    <div class="basicTabModern">
        <div name="贷种基本信息" style="width:100%;height:100%;">
            <table class="tableStyle" width="100%" mode="list" formMode="line">
                <tr>
                    <td align="right">
                        贷种组合级别:
                    </td>
                    <td>
                        <dic:select id="combLevel" dicType="combLevel" name="combLevel" tgClass="validate[required]"
                                    required="true"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        贷种编号：
                    </td>
                    <td>
                        <input type="text" id="combCode" name="combCode"
                               class="validate[required,custom[noSpecialCaracterswithoutAppoint]]" maxlength="8"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        贷种名称：
                    </td>
                    <td>
                        <input type="text" id="combName" name="combName"
                               class="validate[required,custom[illegalLetter]" maxlength="35"/>
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