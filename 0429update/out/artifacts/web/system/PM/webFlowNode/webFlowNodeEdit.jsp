<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"/>
    <!-- 树组件end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
    <title></title>
</head>
<script type="text/javascript">
    function sub() {
        var fnUrl = "<%=path%>/webFlow/update.htm";
        var fnCount = $("#fnCount").val();
        var reg=/^\d+$/;
        if (!fnCount.match(reg)){
            fnCount=100;
            top.Dialog.alert("请选择审批节点数量");
            return;
        }
        if (null == fnCount || fnCount.trim() == "") {
            top.Dialog.alert("流程节点数量不能为空!");
        }
        var fnCode = $("#fnCode").val();
        $.ajax({
                type: 'POST', data: {"fnCount": fnCount, "fnCode": fnCode}, url: fnUrl, async: false, success: function(data){
                    if (data.success === true || data.success === "true") {
                        top.Dialog.alert("更新流程节点成功!", function () {
                            var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                    }
                }
            }
        );
    }
</script>
<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
            <input style="display: none;" id="fnCode" name="fnCode" value="${webFlowNode.fnCode}"/>
        <tr>
            <td>流程节点编号：</td>
            <td>${webFlowNode.fnCode}
            <td colspan="2"></td>
        </tr>
        <tr>
            <td align="right">流程节点名称：</td>
            <td>
                ${webFlowNode.fnName}
            </td>

        </tr>
        <tr>
            <td align="right">
                流程审批种类：
            </td>
            <td>
<%--                ${webFlowNode.fnKind}--%>

                    ${webFlowNode.fnKind eq 0 ? "信贷需求":""}
                    ${webFlowNode.fnKind eq 1 ? "信贷计划":""}
                    ${webFlowNode.fnKind eq 2 ? "信贷调整":""}
            </td>
        </tr>
        <tr>
            <td align="right">
                审批节点数量:
            </td>
            <td>
                <dic:select dicType="FN_COUNT" dicNo="${webFlowNode.fnCount}" id="fnCount"></dic:select>
            </td>
        </tr>
        <tr>
            <td align="right">
                创建人：
            </td>
            <td>
                ${webFlowNode.fnCreateOper}
            </td>
        </tr>
        <tr>
            <td align="right">
                创建时间:
            </td>
            <td>${webFlowNode.fnCreateTime}</td>
        </tr>
        <tr>
            <td align="right">
                更新时间:
            </td>
            <td>${webFlowNode.fnUpdateTime}</td>
        </tr>
        <tr>
            <td align="right">
                最近更新者:
            </td>
            <td>${webFlowNode.fnUpdateOper}</td>
        </tr>
    </table>
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
</form>
</body>
</html>