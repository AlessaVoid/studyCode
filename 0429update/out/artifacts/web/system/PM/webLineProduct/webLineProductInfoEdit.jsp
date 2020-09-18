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
    <script type="text/javascript">
        window.onload = function () {
            initLoanCombSelectedNodes();
        };

        function initLoanCombSelectedNodes() {
            var setting = {check: {enable: true}};
            var lineUrl = "<%=path%>/webLoan/getLoanCombInfoByLevelAndOrganCode.htm?combLevel=2";
            var lineId = $('#lineId').val();
            var zNodes = [];
            var selectedCombUrl = "<%=path%>/webLineProduct/listAllSelectedProductLine.htm?lineId=" + lineId;
            $.ajax({
                type: 'GET', url: selectedCombUrl, async: false,
                success: function (data) {
                    var nodes = data.data;
                    for (var i=0;i<nodes.length;i++) {
                        var item=nodes[i];
                        item.icon = "<%=path%>/libs/icons/item.gif";
                        zNodes.push(item);
                    }
                    ;
                }
            });
            $.ajax({
                    type: 'GET', url: lineUrl, async: false,
                    success: function (res) {
                        var nodes = res.data;
                        for (var i=0;i<nodes.length;i++) {
                            var isExist = false;
                            var item=nodes[i];
                            for (var j=0;j<zNodes.length;j++) {
                                var node=zNodes[j];
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
            if (valid) {
                var lineId = $("#lineId").val();
                var lineName = $("#lineName").val();
                var lineDescription = $("#lineDescription").val();
                top.Dialog.confirm("确定要更新操作吗?|操作提示", function () {
                    $.post("<%=path%>/webLineProduct/updateLineProductInfo.htm", {
                        "lineId": lineId,
                        "lineName": lineName,
                        "lineDescription": lineDescription,
                        "productIds": productIds
                    }, function (result) {
                        if (result.success === "true" || result.success === true) {
                            top.Dialog.alert("更新条线成功!", function () {
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
                            top.Dialog.alert(result.message);
                        }
                    }, "json");
                });
            } else {
                top.Dialog.alert("参数验证未通过!");
            }
        }
    </script>
</head>
<body>
<form id="form1">
    <div class="basicTabModern">
        <div name="条线基本信息" style="width:100%;height:100%;">
            <table class="tableStyle" width="100%" mode="list" formMode="line">
                <tr>
                    <td align="right">
                        机构号：
                    </td>
                    <td>
                        ${organCode}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        机构名称：
                    </td>
                    <td>
                        ${organName}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        条线编号：
                    </td>
                    <td>
                        <input disabled="disabled" type="text" id="lineId" style="width: 250px" name="lineName"
                               value="${webLineProduct.lineId}"
                               class="validate[required]" maxlength="20"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        条线名称：
                    </td>
                    <td>
                        <input type="text" id="lineName" name="lineName" value="${webLineProduct.lineName}"
                               style="width: 250px" class="validate[required]" maxlength="20"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">条线描述:</td>
                    <td>
                        <textarea rows="50" cols="100" id="lineDescription" maxlength="200"
                                  name="lineDescription" style="resize:none;">${webLineProduct.lineDescription}</textarea>
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
        <div name="条线产品列表" style="width:100%;height:100%;">
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