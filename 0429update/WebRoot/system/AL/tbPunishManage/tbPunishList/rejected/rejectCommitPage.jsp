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
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <!-- 树形下拉框end -->
    <title></title>
</head>
<script type="text/javascript">

    var id =${TbPunishList.id};
    $(function () {
        initAuditOper();
        $("#table_1").FrozenTable(1, 0, 1, 0);
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbPunishList/getReqDetailList.htm",
            data: {"id": id},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbPunishResultList = result.tbPunishResultList;
                    for (var i = 0; i < tbPunishResultList.length; i++) {
                        var tbPunishResult = tbPunishResultList[i];
                        var organCode = tbPunishResult.organCode;
                        var state = tbPunishResult.state;
                        var stateStr = "";
                        if (state == 2) {
                            stateStr = "暂无异议";
                        } else if (state == 8) {
                            stateStr = "有争议";
                        } else if (state == 1) {
                            stateStr = "未下发";
                        }
                        $("#" + organCode + "_" + "state").val(stateStr).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "planMount").val(tbPunishResult.planMount).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthVacancyAmt").val(tbPunishResult.monthVacancyAmt).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthVacancyRate").val(tbPunishResult.monthVacancyRate).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthFiveVacancy").val(tbPunishResult.monthFiveVacancy);
                        $("#" + organCode + "_" + "monthShitiPlanMount").val(tbPunishResult.monthShitiPlanMount).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthShitiOverAmt").val(tbPunishResult.monthShitiOverAmt).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthShitiOverRate").val(tbPunishResult.monthShitiOverRate).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthFiveShitiOver").val(tbPunishResult.monthFiveShitiOver);
                        $("#" + organCode + "_" + "monthPiapjuPlanMount").val(tbPunishResult.monthPiapjuPlanMount).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthPiaojuOverAmt").val(tbPunishResult.monthPiaojuOverAmt).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthPiaojuOverRate").val(tbPunishResult.monthPiaojuOverRate).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthFivePiaojuOver").val(tbPunishResult.monthFivePiaojuOver);
                        $("#" + organCode + "_" + "monthTotalPunish").val(tbPunishResult.monthTotalPunish);
                        $("#" + organCode + "_" + "note").val(tbPunishResult.note);
                        $("#" + organCode + "_" + "note").attr("disabled", "disabled");

                    }
                }
            }
        });
    });


    function initAuditOper() {
        //这里是个关键点
        $.ajax({
            url: "<%=path%>/punishApplyReject/getOperInfoListByRolecode.htm?taskid=${taskid}&id=${TbPunishList.id}",
            method: "GET",
            async: false,
            success: function (result) {
                $("#auditOperList").data("data", result);
                $("#auditOperList").render();
            }
        });
    }

    function submitInfo() {

        var tbPunishListDetail = $("#form1").serialize();
        var name = $("#name").val();
        var collectEndTime = $("#collectEndTime").val();
        var note = $("#note").val();
        var id = $("#id").val()
        var month = ${TbPunishList.month};
        $.ajax({
            type: "POST",
            url: "<%=path%>/punishApplyReject/update.htm",
            data: {
                "name": name,
                "collectEndTime": collectEndTime,
                "note": note,
                "tbPunishListDetail": tbPunishListDetail,
                "id": id,
                "month": month
            },
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("更新成功!", function () {
                    });
                } else {
                    if (result.code == '403') {
                        top.Dialog.alert("所属月份计划已存在");
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

    function onAudit(isAgree) {
        top.Dialog.confirm("确定要提交该记录吗？", function () {
            var id = $("#id").val();
            var tbOverDetail = $("#form1").serialize();

            var valid = $("#form1").validationEngine({
                returnIsValid: true
            });
            if (!valid) {
                top.Dialog.alert("请检查数据正确性");
                return;
            }
            $.ajax({
                type: "POST",
                url: "<%=path%>/punishApplyReject/update.htm",
                data: {
                    "tbreqresetDetail": tbreqresetDetail,
                    "id": id
                },
                dataType: "json",
                success: function (result) {
                    if (result.success == true || result.success == "true") {
                        top.Dialog.alert("更新成功!", function () {
                        });
                    } else {
                        if (result.code == '403') {
                            top.Dialog.alert("所属月份计划已存在");
                        } else {
                            top.Dialog.alert("新增失败");
                        }
                    }
                },
                error: function (result) {
                    top.Dialog.alert("请求异常");
                }
            });


            var comment = $("#comment").val();

            var taskId = $("#taskId").val();
            var assignee = $("#auditOperList").val();
            if (assignee == "") {
                top.Dialog.alert("请选择下级审批人!");
                return;
            }
            $("#but1").attr("disabled", "disabled");
            $("#but2").attr("disabled", "disabled");
            $("#but3").attr("disabled", "disabled");
            $.ajax({
                url: "<%=path%>/punishApplyReject/auditLoanQuotaApplyRequest.htm",
                data: {
                    "id": id,
                    "taskId": taskId,
                    "comment": comment,
                    "isAgree": isAgree,
                    "assignee": assignee,
                    "lastUserType":${lastUserType}
                },
                method: 'POST',
                success: function (res) {
                    if (res.success === "true" || res.success === true) {
                        top.Dialog.alert("操作成功!", function () {
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
                        $("#but1").removeAttr("disabled");
                        $("#but2").removeAttr("disabled");
                        $("#but3").removeAttr("disabled");
                        top.Dialog.alert("操作失败!" + res.message);
                    }
                }
            });
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
    }


</script>
<body>

<form id="form2">
    <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="id" name="id" value="${TbPunishList.id}" hidden="hidden"/>
                ${TbPunishList.month}

            </td>


            <td align="right">
                罚息名称：
            </td>
            <td>
                ${TbPunishList.name}
            </td>
        </tr>
        <tr>
            <td align="right">
                意见征集截止时间：
            </td>
            <td>
                ${TbPunishList.collectEndTime}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">事由：</td>
            <td style="word-break:break-all">
                ${TbPunishList.note}
            </td>
        </tr>


    </table>
</form>


<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="table_1" class="tableStyle" thTrueWidth="true" mode="list">
            <tr>
                <td></td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td>
                        <div style="width: 220px"> ${combAmountName.name}</div>
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${organList}" var="organ">
                <tr>
                    <td> ${organ.organname}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${organ.organcode}_${combAmountName.code}" AUTOCOMPLETE="off"
                                   class=" validate[required,custom[onlyNumberWideSpecial]]"
                                   onkeyup='upperCase(this)' value="0"
                                   type="text" id="${organ.organcode}_${combAmountName.code}" maxlength="16"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div align="center">
        <button type="button" onclick="submitInfo()" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</form>


<input type="hidden" id="comments" name="comments" value="${fn:length(comments)}"/>
<c:if test="${!empty comments }">
    <div id="panel23" panelTitle="审批进度" class="box2_custom" boxType="box2" startState="open">
        <table class="tableStyle tab-hei-30" width="80%" mode="list"
               style="table-layout:fixed;word-wrap:break-word;word-break:break-all">
            <tr>
                <td width="20%" align="left">
                    审批用户
                </td>
                <td width="20%" align="left">
                    审批时间
                </td>
                <td width="20%" align="left">
                    审批状态
                </td>
                <td width="40%" align="left">
                    审批意见
                </td>
            </tr>
            <c:forEach items="${comments}" var="comment">
                <tr>
                    <td> ${comment.userId }</td>
                    <td>
                        <c:choose>
                            <c:when test="${empty comment.time}">
                                ----
                            </c:when>
                            <c:otherwise>
                                <fm:formatDate value="${comment.time}" pattern="yyyyMMdd HH:mm:ss"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td> ${comment.type }</td>
                    <td style="word-break:break-all">${comment.fullMessage }</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>
<div>备注:</div>
<div><textarea id="comment" rows="50" cols="50"></textarea></div>
<div>
    <c:if test="${false == lastUserType }">
    <div>下一节点审批人:</div>
    <div>
        <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
    </div>
    </c:if>
    <div align="center">
        <div align="center">
            <button type="button" id="but1" onclick="onAudit('1')"><span class="icon_ok">提交</span></button>
        </div>
    </div>


</body>
</html>