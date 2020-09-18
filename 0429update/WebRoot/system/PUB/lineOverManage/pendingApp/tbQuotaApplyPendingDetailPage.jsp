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
    var qaId =${TbLineOver.qaId};
    $(function () {
        initComplete();
        initAuditOper();
        //输入框获取焦点事件
        $(".planamonut").focus(function () {
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".planamonut").blur(function () {
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });
    });
    function initComplete() {

        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/lineOver/getReqDetailList.htm",
            data: {"qaId": qaId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbOverDOS = result.tbOverDOS;
                    for (var i = 0; i < tbOverDOS.length; i++) {
                        var TbLineOverDO = tbOverDOS[i];
                        var qaComb = TbLineOverDO.qaComb;
                        var qaAmt = TbLineOverDO.qaAmt;
                        var qaOverAmt = TbLineOverDO.qaOverAmt;
                        $("#" + qaComb + "_AppNum").val(qaOverAmt);

                        $("#" + qaComb + "_Num").html(qaAmt);
                    }
                }
            }
        });
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
            url: "<%=path%>/lineOverApplyPendingApp/getOperInfoListByRolecode.htm?taskid=${taskid}&qaId=${TbLineOver.qaId}",
            method: "GET",
            async: false,
            success: function (result) {
                $("#auditOperList").data("data", result);
                $("#auditOperList").render();
            }
        });
    }

    //用户只能输入正负数与小数
    function upperCase(obj) {
        // obj.value = obj.value.replace(/[^\d.]/g,"");
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g,"");
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g,".");
        //保证.只出现一次，而不能出现两次以上
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 5));
        }
    }
    function onAudit(isAgree) {
        var tbOverDetail = $("#form1").serialize();
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
            top.Dialog.confirm("确定要驳回至原录入人员吗？", function () {
                var qaId = $("#qaId").val();
                var taskId = $("#taskId").val();

                var assignee = $("#auditOperList").val();
                if(assignee==""){
                    top.Dialog.alert("请选择下级审批人!" );
                    return;
                }

                $("#but1").attr("disabled","disabled");
                $("#but2").attr("disabled","disabled");
                $.ajax({
                    url: "<%=path%>/lineOverApplyPendingApp/auditLoanQuotaApplyRequest.htm",
                    data: {
                        "qaId": qaId,
                        "taskId": taskId,
                        "tbOverDetail":tbOverDetail,
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

                var qaId = $("#qaId").val();
                var taskId = $("#taskId").val();
                var assignee = $("#auditOperList").val();
                if(assignee==""){
                    top.Dialog.alert("请选择下级审批人!" );
                    return;
                }
                $("#but1").attr("disabled","disabled");
                $("#but2").attr("disabled","disabled");
                $.ajax({
                    url: "<%=path%>/lineOverApplyPendingApp/auditLoanQuotaApplyRequest.htm",
                    data: {
                        "qaId": qaId,
                        "taskId": taskId,
                        "tbOverDetail":tbOverDetail,
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

<form id="form2">
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="qaId" name="qaId" value="${TbLineOver.qaId}" hidden="hidden"/>
                ${TbLineOver.qaMonth}
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
                <a href="<%=path%>/tbTradeManger/single/download.htm?qaFileId=${TbLineOver.qaFileId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
            </td>
            <td align="right">全年计划进度：</td>
            <td>
                ${TbLineOver.qaYearRate}%
            </td>
        </tr>

        <tr>
            <td align="right">本月计划：</td>
            <td>
                ${TbLineOver.qaPlanAmt}
            </td>
            <td align="right">本月超计划额度：</td>
            <td>
                ${TbLineOver.qaOverAmt}
            </td>
        </tr>
        <tr>
            <td align="right">前第三个月超规模或闲置额度：</td>
            <td>
                <span id="span1"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span2"></span>
            </td>
        </tr>
        <tr>
            <td align="right">前第二个月超规模或闲置额度：</td>
            <td>
                <span id="span3"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span4"></span>
            </td>
        </tr>
        <tr>
            <td align="right">前第一个月超规模或闲置额度：</td>
            <td>
                <span id="span5"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span6"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                单位：
            </td>
            <td>
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbLineOver.unit}"/>
            </td>
        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3" style="word-break:break-all">
                ${TbLineOver.qaReason}
            </td>
        </tr>
        <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
    </table>
</form>
<form id="form1">

    <input id="where" style="display: none" value="${where}">
    <div id="panel22" panelTitle="详情" class="box2_custom" boxType="box2" startState="open">
        <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <th trueWidth="150"></th>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <th align="center">
                            ${combAmountName.name}
                    </th>
                </c:forEach>
                <th align="center">
                    审批通过额度
                </th>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <th> ${comb.combName}</th>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span id="${comb.combCode}_${combAmountName.code}"></span>
                        </td>
                    </c:forEach>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${comb.combCode}_AppNum" AUTOCOMPLETE="off"
                                   class="planamonut ${comb.combCode } ${combAmountName.code}"
                                   onkeyup='upperCase(this)' value="0"
                                   type="text" id="${comb.combCode}_AppNum" maxlength="16"/>
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

</body>
</html>