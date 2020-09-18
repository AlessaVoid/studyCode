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

    var lineReqresetId = '${lineReqresetId}';
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
    $(function () {
        initAuditOper();
        $(".submitButton").addClass("button");
        $(".submitButton").append("<span class='icon_save_draft'>提交</span>");
        $(".button").css("width", "90px");
        $(".button").css("cursor", "pointer");

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
                        $("#" + reqdCombCode + "_num").html(reqdNum);
                        $("#" + reqdCombCode + "_numCopy").val(reqdNum);
                    }
                }
                addPlanAmonut();
            }
        });

    });

    function initAuditOper() {
        $.ajax({
                url: "<%=path%>/TbLineReqResetApp/getOperInfoListByRolecode.htm?rolecode=${rolecode}"+"&lineReqresetId="+lineReqresetId,
                method: "GET",
                async: false,
                success: result => {
                    var list = [];
                    result.forEach(oper => {
                        var item = {
                            "key": oper.opername,
                            "value": oper.opercode
                        };
                        list.push(item);
                    });
                    var selData = {
                        "list": list
                    };
                    $("#auditOperList").data("data", selData);
                    $("#auditOperList").render();
                }
            }
        );
    }

    function submitInfo() {
        var auditOper = $("#auditOperList").val();
        if(auditOper==""){
            top.Dialog.alert("请选择下级审批人!" );
            return;
        }
        var comment =  $("#comment").val();
        if(comment==""){
            top.Dialog.alert("请填写备注!");
            return;
        }

        top.Dialog.confirm("确定要提交审批吗？", function () {
            $("#btn1").attr("disabled","disabled");
            $.ajax({
                url: '<%=path%>/TbLineReqResetApp/startLoanReqAudit.htm',
                method: 'GET',
                data: {
                    "auditOper": auditOper,
                    "lineReqresetId": lineReqresetId,
                    "comment":comment
                }, success: res => {
                    if (res.success === 'true' || res.success === true) {
                        top.Dialog.alert("条线需求调整提交成功,等待审批结果", () => {
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
                        $("#btn1").removeAttr("disabled");
                        top.Dialog.alert("条线需求调整提交失败");
                    }
                }
            });
        });

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
                <input id="lineResetrefId" name="lineResetrefId" type="hidden" value="${tbLineReqresetDetail.lineResetrefId}"/>
                <input id="lineReqresetId" name="lineReqresetId" type="hidden" value="${tbLineReqresetDetail.lineReqresetId}"/>
                <input id="lineReqMonth" name="lineReqMonth" type="hidden" value="${tbLineReqresetDetail.lineReqName}"> </input>
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
                        <div>
                            <span value="0"
                                  type="text" id="${comb.combCode}_${combAmountName.code}"/>
                        </div>
                        <div>
                            <input name="${comb.combCode}_${combAmountName.code}"
                                   class="planAmount"
                                   value="0" maxlength="16" AUTOCOMPLETE="off" hidden="hidden"
                                   type="text" id="${comb.combCode}_${combAmountName.code}Copy"/>
                        </div>
                    </td>
                </c:forEach>

                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <span id="${comb.combCode}_newNum">0</span>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="5">
                <div align="center">
                    <div style="float: left">下一节点审批人:</div>
                    <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
                </div>
            </td>

        </tr>
        <tr>
            <td>备注:</td>
            <td><textarea id="comment" rows="50" cols="50"></textarea></td>
        </tr>
        <tr>
            <td colspan="5">
                <div align="center">
                    <button type="button" id="btn1" onclick="submitInfo()" class="submitButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>