<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>条线需求调整审批</title>
    <script type="text/javascript">
        window.onload = function () {
            initAuditOper();
        };

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
        var lineReqresetId = '${lineReqresetId}';

        function initAuditOper() {
            $.ajax({
                    url: "<%=path%>/TbLineReqResetPendingApp/getOperInfoListByRolecode.htm?taskid=${taskid}"+"&lineReqresetId="+lineReqresetId,
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

        function onAudit(isAgree) {
            var comment = $("#comment").val();
            if(isAgree==0){
                if(comment==""){
                    top.Dialog.alert("请填写驳回意见!" );
                    return;
                }
            }
            if(comment==""){
                top.Dialog.alert("请填写审批意见!");
                return;
            }
            var assignee = $("#auditOperList").val();
            if(assignee==""){
                top.Dialog.alert("请选择下级审批人!" );
                return;
            }

            if(0==isAgree){
                top.Dialog.confirm("确定要驳回至原录入员吗？", function () {
                    $("#btn1").attr("disabled","disabled");
                    $("#btn2").attr("disabled","disabled");
                    var lineReqresetId = $("#lineReqresetId").val();
                    var taskId = $("#taskId").val();
                    $.ajax({
                        url: "<%=path%>/TbLineReqResetPendingApp/auditLoanReqRequest.htm",
                        data: {
                            "lineReqresetId": lineReqresetId,
                            "taskId": taskId,
                            "comment": comment,
                            "isAgree": isAgree,
                            "assignee": assignee,
                            "lastUserType":${lastUserType}
                        },
                        method: 'POST',
                        success: res => {
                        if (res.success === "true" || res.success === true) {
                        top.Dialog.alert("操作成功!",()=>{
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
                        top.Dialog.alert("操作失败!" + res.message);
                        $("#btn1").removeAttr("disabled");
                        $("#btn2").removeAttr("disabled");
                    }
                }
                });
                });
            }else {

                top.Dialog.confirm("确定要提交该记录吗？", function () {
                    $("#btn1").attr("disabled","disabled");
                    $("#btn2").attr("disabled","disabled");
                    var lineReqresetId = $("#lineReqresetId").val();
                    var taskId = $("#taskId").val();
                    $.ajax({
                        url: "<%=path%>/TbLineReqResetPendingApp/auditLoanReqRequest.htm",
                        data: {
                            "lineReqresetId": lineReqresetId,
                            "taskId": taskId,
                            "comment": comment,
                            "isAgree": isAgree,
                            "assignee": assignee,
                            "lastUserType":${lastUserType}
                        },
                        method: 'POST',
                        success: res => {
                        if (res.success === "true" || res.success === true) {
                        top.Dialog.alert("操作成功!",()=>{
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
                        top.Dialog.alert("操作失败!" + res.message);
                        $("#btn1").removeAttr("disabled");
                        $("#btn2").removeAttr("disabled");
                    }
                }
                });
                });
            }








        }





        //用户只能输入正负数与小数
        function upperCase(obj) {
            if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
                obj.value = "";
            }
            if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
                obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
            }
        }



        var tbReqDetailList = '${tbReqDetailList}';
        $(function () {

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


        })


    </script>
</head>
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
<form id="form1" style="overflow-x: auto;overflow-y: auto">
    <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
    <input id="where" style="display: none" value="${where}">
    <div id="panel22" panelTitle="详情" class="box2_custom" boxType="box2" startState="open">
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
    </table>

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
<div>审批意见:</div>
<div><textarea id="comment" rows="50" cols="50"></textarea></div>
<div>

<c:if test="${false == lastUserType }">
<div>下一节点审批人:</div>
<div>
    <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
</div>
</c:if>
</div>
<div>
    <div align="center">
        <button type="button"  id="btn1" onclick="onAudit('1')"><span class="icon_ok">通过</span></button>
        <button type="button" id="btn2"  onclick="onAudit('0')"><span class="icon_no">驳回</span></button>
    </div>
</div>
</form>
</body>
</html>