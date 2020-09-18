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

                        $("#" + qaComb + "_Num").html(qaAmt);
                    }
                }
            }
        });
        initComplete();
        initAuditOper();
        $(".submitButton").addClass("button");
        $(".submitButton").append("<span class='icon_save_draft'>提交</span>");
        $(".button").css("width", "90px");
        $(".button").css("cursor", "pointer");
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
        $("#span1").html(${oneNum}+"");
        $("#span2").html(${oneRate}+"%");
        $("#span3").html(${twoNum}+"");
        $("#span4").html(${twoRate}+"%");
        $("#span5").html(${threeNum}+"");
        $("#span6").html(${threeRate}+"%");
    }

    function initAuditOper() {
        //这里是个关键点
        $.ajax({
            url: "<%=path%>/tbTradeManger/lineOverCommit/getOperInfoListByRolecode.htm?rolecode=${rolecode}"+"&qaId="+qaId,
            method: "GET",
            async: false,
            success: function (result) {
                $("#auditOperList").data("data", result);
                $("#auditOperList").render();
            }
        });
    }


    function submitInfo() {
        var qaIdStr = $('#qaId').val();
        var auditOper = $("#auditOperList").val();

        if (auditOper == "") {
            top.Dialog.alert("请选择下级审批人!");
            return;
        }
        var comment =  $("#comment").val();
        if(comment==""){
            top.Dialog.alert("请填写备注!");
            return;
        }
        top.Dialog.confirm("确定要提交审批吗？", function () {
            $("#btn1").attr("disabled", "disabled");
            $("#btn2").attr("disabled", "disabled");
            $.ajax({
                url: '<%=path%>/tbTradeManger/lineOverCommit/startLoanQuotaApplyAudit.htm',
                method: 'GET',
                data: {
                    "auditOper": auditOper,
                    "qaId": qaIdStr,
                    "comment":comment
                }, success: function (res) {
                    if (res.success === 'true' || res.success === true) {
                        top.Dialog.alert("申请提交成功,等待审批结果", function () {
                            $("#btn1").removeAttr("disabled");
                            $("#btn2").removeAttr("disabled");
                            var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();

                        })
                        ;
                    } else {
                        top.Dialog.alert("申请提交失败");
                        $("#btn1").removeAttr("disabled");
                        $("#btn2").removeAttr("disabled");
                    }
                }
            })
            ;
        });

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
            <td align="right">全年计划进度：</td>
            <td>
                ${TbLineOver.qaYearRate}%
            </td>
        </tr>

        <tr>
            <td align="right">
                附件名称：
            </td>
            <td>
                <span id="fileName">${fileName}</span>
            </td>
            <td align="right">下载附件：</td>
            <td>
                <a href="<%=path%>/tbTradeManger/single/download.htm?qaFileId=${TbLineOver.qaFileId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
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

    </table>
</form>

<form id="form1">
    <div id="scrollContent" class="border_gray">
        <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <th trueWidth="150"></th>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <th align="center">
                            ${combAmountName.name}
                    </th>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <th> ${comb.combName}</th>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <span id="${comb.combCode}_${combAmountName.code}"></span>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            <tr>
                <td align="center">下一节点审批人:</td>
                <td colspan="7" align="center">
                    <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><textarea id="comment" rows="50" cols="50"></textarea></td>
            </tr>
            <tr>
                <td colspan="4">
                    <div align="center">
                        <button type="button" id="btn1" onclick="submitInfo()" class="submitButton"/>
                        <button type="button" id="btn2" onclick="return cancel()" class="cancelButton"/>
                    </div>
                </td>
            </tr>

        </table>
    </div>
</form>

</body>
</html>