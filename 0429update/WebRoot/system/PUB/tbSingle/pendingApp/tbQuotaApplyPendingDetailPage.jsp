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
    $(function () {
        initComplete();
        initAuditOper();
    });
    function initComplete() {

        $.post("<%=path%>/tbTradeManger/lineOver/showComb.htm",
            {}, function (result) {
                combMap = result.combMap;
                var qaComb = $('#qaComb').val();
                $("#qaCombName").html(combMap[qaComb]);
                $("#span1").html(${oneNum}+"");
                $("#span2").html( ${oneRate}+"%");
                $("#span3").html(${twoNum}+"");
                $("#span4").html( ${twoRate}+"%");
                $("#span5").html( ${threeNum}+"");
                $("#span6").html( ${threeRate}+"%");
            }, "json");

    }


    function initAuditOper() {
        //这里是个关键点
        $.ajax({
            url: "<%=path%>/singleApplyPendingApp/getOperInfoListByRolecode.htm?taskid=${taskid}&qaId=${TbSingle.qaId}",
            method: "GET",
            async: false,
            success: function (result) {
                $("#auditOperList").data("data", result);
                $("#auditOperList").render();
            }
        });
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
        if(0==isAgree){
            top.Dialog.confirm("确定要驳回至原录入员吗？", function () {
                $("#but1").attr("disabled","disabled");
                $("#but2").attr("disabled","disabled");
                var qaId = $("#qaId").val();
                var taskId = $("#taskId").val();
                var assignee = $("#auditOperList").val();
                if(assignee==""){
                    top.Dialog.alert("请选择下级审批人!" );
                    return;
                }
                $.ajax({
                    url: "<%=path%>/singleApplyPendingApp/auditLoanQuotaApplyRequest.htm",
                    data: {
                        "qaId": qaId,
                        "taskId": taskId,
                        "comment": comment,
                        "isAgree": isAgree,
                        "assignee": assignee,
                        "lastUserType":${lastUserType}
                    },
                    method: 'POST',
                    success: function(res) {
                        if (res.success === "true" || res.success === true) {
                            top.Dialog.alert("操作成功!",function(){
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
                            $("#but1").removeAttr("disabled");
                            $("#but2").removeAttr("disabled");
                        }
                    }
                });
            });
        }else {
            top.Dialog.confirm("确定要提交该记录吗？", function () {
                $("#but1").attr("disabled","disabled");
                $("#but2").attr("disabled","disabled");
                var qaId = $("#qaId").val();
                var taskId = $("#taskId").val();
                var assignee = $("#auditOperList").val();
                if(assignee==""){
                    top.Dialog.alert("请选择下级审批人!" );
                    return;
                }
                $.ajax({
                    url: "<%=path%>/singleApplyPendingApp/auditLoanQuotaApplyRequest.htm",
                    data: {
                        "qaId": qaId,
                        "taskId": taskId,
                        "comment": comment,
                        "isAgree": isAgree,
                        "assignee": assignee,
                        "lastUserType":${lastUserType}
                    },
                    method: 'POST',
                    success: function(res) {
                        if (res.success === "true" || res.success === true) {
                            top.Dialog.alert("操作成功!",function(){
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
                            $("#but1").removeAttr("disabled");
                            $("#but2").removeAttr("disabled");
                        }
                    }
                });
            });

        }




    }

</script>
<body>

<form id="form1">
    <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
    <input id="where" style="display: none" value="${where}">
    <div id="panel22" panelTitle="详情" class="box2_custom" boxType="box2" startState="open">
        <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
            <tr>
                <td align="right">
                    所属月份：
                </td>
                <td>
                    <input id="qaId" name="qaId" value="${TbSingle.qaId}" hidden="hidden"/>
                    ${TbSingle.qaMonth}
                </td>

                <td align="left">贷种组合名称：</td>
                <td>
                    <input id="qaComb" name="qaComb" hidden="hidden" value="${TbSingle.qaComb}"/>
                    <span id="qaCombName"></span>
                </td>
            </tr>
            <tr>
                <td align="right">调整额度：</td>
                <td>
                    ${TbSingle.qaAmt}
                </td>
                <td align="right">借据编号/票据协议号：</td>
                <td>
                    ${TbSingle.qaSingleId}
                </td>
            </tr>
            <tr >
                <td align="right">业务经办机构编号：</td>
                <td>
                    ${TbSingle.qaSingleOrgan}
                </td>

                <td align="right">业务经办机构名称：</td>
                <td>
                    ${TbSingle.qaSingleOrganName}
                </td>


            </tr>
            <tr >
                <td align="right">全年计划进度：</td>
                <td>
                    ${TbSingle.qaYearRate}%
                </td>

                <td align="right">
                    附件名称：
                </td>
                <td>
                    <span id="fileName">${fileName}</span>
                </td>


            </tr>
            <tr>
                <td align="right">下载附件：</td>
                <td>
                    <a href="<%=path%>/tbTradeManger/single/download.htm?qaFileId=${TbSingle.qaFileId}">
                        <button class="btn btn-primary" type="button">
                            下载
                        </button>
                    </a>
                </td>

                <td align="right">本月计划：</td>
                <td>
                    ${TbSingle.qaPlanAmt}
                </td>

            </tr>
            <tr>
                <td align="right">本月超计划额度：</td>
                <td>
                    ${TbSingle.qaOverAmt}
                </td>

                <td align="right">前第三个月超规模或闲置额度：</td>
                <td>
                    <span id="span1"></span>
                </td>

            </tr>
            <tr>
                <td align="right">百分比：</td>
                <td>
                    <span id="span2"></span>
                </td>

                <td align="right">前第二个月超规模或闲置额度：</td>
                <td>
                    <span id="span3"></span>
                </td>

            </tr>
            <tr>
                <td align="right">百分比：</td>
                <td>
                    <span id="span4"></span>
                </td>

                <td align="right">前第一个月超规模或闲置额度：</td>
                <td>
                    <span id="span5"></span>
                </td>

            </tr>
            <tr>
                <td align="right">百分比：</td>
                <td>
                    <span id="span6"></span>
                </td>

                <td align="right">
                    单位：
                </td>
                <td>
                    <dic:out dicType="CURRENCY_UNIT" dicNo="${TbSingle.unit}"/>
                </td>

            </tr>
            <tr>
                <td align="right" colspan="1">事由：</td>
                <td colspan="3" style="word-break:break-all">
                    ${TbSingle.qaReason}
                </td>
            </tr>
        </table>
    </div>
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
<c:if test="${false == lastUserType }">
    <div>下一节点审批人:</div>
    <div>
        <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
    </div>
</c:if>
<div align="center">
    <div align="center">
        <button  id="but1" type="button" onclick="onAudit('1')"><span class="icon_ok">通过</span></button>
        <button id="but2" type="button" onclick="onAudit('0')"><span class="icon_no">驳回</span></button>
    </div>
</div>
</form>
</body>
</html>