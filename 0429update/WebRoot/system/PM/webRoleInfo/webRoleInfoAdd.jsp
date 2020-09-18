<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>角色维护</title>
    <!-- 树组件 -->
    <link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <!--数据表格start-->
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            //复核框
            var setting1 = {
                check: {
                    enable: true
                }
            };
            //树节点
            var zNodes = ${treeNodes};
            var treeObj = $.fn.zTree.init($("#tree"), setting1, zNodes);
        });

        //获取选中节点
        function getCheckedNodes() {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var checkedNodes = treeObj.getCheckedNodes(true);
            var msg = "";
            for (var i = 0; i < checkedNodes.length; i++) {
                msg += "," + checkedNodes[i].id;
            }
            if (msg == "") {
                msg = "无选择";
            } else {
                msg = msg.substring(1);
            }
            return msg;
        }

        function sub() {
            var valid = $("#form1").validationEngine({
                returnIsValid: true
            });
            if ($("#organLevel").val().trim() ==="") {
                top.Dialog.alert("请选择机构级别");
                return;
            }
            if (valid) {
                top.Dialog.confirm("确定要保存操作吗?|操作提示", function () {
                    $.post("<%=path%>/webRoleInfo/insert.htm", {
                            "funCodes": getCheckedNodes(),
                            "roleCode": $("#roleCode").val(),
                            "roleName": $("#roleName").val(),
                            "organLevel": $("#organLevel").val()
                        },
                        function (result) {
                            if (result.success == "true" || result.success == true) {
                                top.Dialog.alert("新增角色成功", function () {
                                    var menu_id = parent.getCurrentTabId();
                                    if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                                    var menu_frame_id = "page_" + menu_id;
                                    top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                                });
                            } else {
                                top.Dialog.alert(result.msg);
                                // top.Dialog.close();
                            }
                        }, "json");
                });
            } else {
                top.Dialog.alert("角色编码或角色名称不合法！");
            }
        }
    </script>
</head>
<body>
<form id="form1">
    <c:choose>
        <c:when test="${param.method=='update' }">
            <input type="hidden" name="roleCode" value="${webRoleInfoDTO.roleCode }"/>
        </c:when>
    </c:choose>
    <div class="basicTabModern">
        <div name="角色信息" style="width:100%;height:80%;">
            <table class="tableStyle" width="80%" mode="list" formMode="line">
                <tr>
                    <td align="right">
                        机构级别：
                    </td>
                    <td>
                        <dic:select id="organLevel" dicType="ORGAN_LEVEL" name="organLevel"
                                    tgClass="validate[required]"></dic:select>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        角色编码:
                    </td>
                    <td>
                        <input type="text" id="roleCode" name="roleCode" value="${webRoleInfoDTO.roleCode }"
                               class="validate[required,custom[onlyNumber],length[3,3]]"/>
                        <span class="star">*</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        角色名称：
                    </td>
                    <td>
                        <input type="text" id="roleName" name="roleName" value="${webRoleInfoDTO.roleName }"
                               class="validate[required,length[0,200],custom[chinese]" maxlength="100"/>
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
        <div name="权限分配" style="width:100%;height:80%;">
            <fieldset>
                <legend>
                    角色功能对照表信息
                </legend>
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