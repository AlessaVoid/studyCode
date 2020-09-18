<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>创建条线</title>
    <!--树组件开始-->
    <link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <%--树组件结束--%>
    <script type="text/javascript">
        $(function () {
            var setting = {check: {enable: true}};
            var combUrl = "<%=path%>/webLoan/getLoanCombInfoByLevelAndOrganCode.htm?combLevel=2";
            $.ajax({
                type: 'GET',
                url: combUrl,
                success: function (data) {
                    var zNodes = data.data;
                    for (var i=0;i<zNodes.length;i++) {
                        var item=zNodes[i];
                        item.icon = "<%=path%>/libs/icons/item.gif";
                    }
                    ;
                    $.fn.zTree.init($("#tree"), setting, zNodes);
                }
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
            if (valid) {
                var lineName = $("#lineName").val();
                var lineDescription = $("#lineDescription").val();
                var lineCreator = $("#lineCreator").val();
                top.Dialog.confirm("确定要保存操作吗?|操作提示", function () {
                    $.post("<%=path%>/webLineProduct/insertProductLine.htm", {
                        "lineName": lineName,
                        "description": lineDescription,
                        "lineCreator": lineCreator,
                        "productIds": productIds
                    }, function (result) {
                        if (result.success === "true" || result.success === true) {
                            top.Dialog.alert("添加条线成功!", function () {
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
                            top.Dialog.alert("添加条线失败");
                        }
                    }, "json");
                });
            } else {
                top.Dialog.alert("验证未通过！");
            }
        }
    </script>
</head>
<body>
<form id="form1">
    <input type="hidden" name="lineId" value="${webLineProduct.lineId}"/>
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
                    <td align="right">条线名称:</td>
                    <td>
                        <input style="width: 250px" type="text" id="lineName" name="lineName" class="validate[required]"
                               maxlength="20"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">条线描述:</td>
                    <td>
                        <textarea rows="50" cols="100" id="lineDescription" name="lineDescription"
                                  maxlength="200" style="resize:none;"></textarea>
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