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
        $(".submitButton").addClass("button");
        $(".submitButton").append("<span class='icon_save_draft'>提交</span>");
        $(".button").css("width", "90px");
        $(".button").css("cursor", "pointer");
    });
    function initComplete() {

        $.post("<%=path%>/tbTradeManger/lineOver/showComb.htm",
            {}, function (result) {
                combMap = result.combMap;
                var qaComb = $('#qaComb').val();
                $("#qaCombName").html(combMap[qaComb]);
            }, "json");

        $("#span1").html(${oneNum}+"");
        $("#span2").html( ${oneRate}+"%");
        $("#span3").html(${twoNum}+"");
        $("#span4").html( ${twoRate}+"%");
        $("#span5").html( ${threeNum}+"");
        $("#span6").html( ${threeRate}+"%");
    }


    function initAuditOper() {
        //这里是个关键点
        $.ajax({
            url: "<%=path%>/tbTradeManger/singleCommit/getOperInfoListByRolecode.htm?rolecode=${rolecode}",
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
            $("#btn2").attr("disabled","disabled");
            $.ajax({
                url: '<%=path%>/tbTradeManger/singleCommit/startLoanQuotaApplyAudit.htm',
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
                        top.Dialog.alert("单一机构单笔专项申请提交失败");
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

<form id="form1">
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
                <a href="<%=path%>/tbTradeManger/single/download.htm?qaFileId=${TbSingle.qaFileId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
            </td>
        </tr>
        <tr>
            <td align="right">本月计划：</td>
            <td>
                ${TbSingle.qaPlanAmt}
            </td>
            <td align="right">本月超计划额度：</td>
            <td>
                ${TbSingle.qaOverAmt}
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
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbSingle.unit}"/>
            </td>

        </tr>
        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3" style="word-break:break-all">
                ${TbSingle.qaReason}
            </td>
        </tr>
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
</form>
</body>
</html>