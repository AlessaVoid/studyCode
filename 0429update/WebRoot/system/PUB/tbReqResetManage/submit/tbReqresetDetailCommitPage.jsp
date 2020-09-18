<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
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

    var one_oldTotalNum = ${one_oldTotalNum};
    var two_oldTotalNum = ${two_oldTotalNum};
    var three_oldTotalNum = ${three_oldTotalNum};

    // 计算调整金额的总和
    function addPlanAmonut(level) {
        var amonutList = $(".planAmount");
        var amountCount = 0;
        $(amonutList).each(function () {
            var id = this.id;
            if (id.split("_")[1] == "oldNum" || id.split("_")[1] == "newNum") {
            } else {
                if (id.split("_")[2] == level) {
                    amountCount = Number(amountCount) + Number(this.value);
                }
                var thisId = this.id.split("_")[0];
                var oldNum=  document.getElementById( thisId + "_oldNum").innerHTML;
                var newNum = Number(this.value ) + Number(oldNum);
                $("#" + thisId + "_newNum").html(newNum);
            }
        });
        return amountCount;
    }



    function initPlanAmount() {
        var one_newAmount = Number(one_oldTotalNum) + Number(addPlanAmonut(1));
        $("#one_newNum").html(one_newAmount);
        $("#one_num").html(Number(addPlanAmonut(1)));
        var two_newAmount = Number(two_oldTotalNum) + Number(addPlanAmonut(2));
        $("#two_newNum").html(two_newAmount);
        $("#two_num").html(Number(addPlanAmonut(2)));
        var three_newAmount = Number(three_oldTotalNum) + Number(addPlanAmonut(3));
        $("#three_newNum").html(three_newAmount);
        $("#three_num").html(Number(addPlanAmonut(3)));

    }

    var reqresetId = '${reqresetId}';
    $(function () {
        initAuditOper();
        $(".submitButton").addClass("button");
        $(".submitButton").append("<span class='icon_save_draft'>提交</span>");
        $(".button").css("width", "90px");
        $(".button").css("cursor", "pointer");

        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbReqresetDetail/getReqDetailList.htm",
            data: {"reqresetId": reqresetId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbreqresetDetailList = result.tbreqresetDetailList;
                    for (var i = 0; i < tbreqresetDetailList.length; i++) {
                        var tbreqresetDetail = tbreqresetDetailList[i];
                        var reqresetdCombCode = tbreqresetDetail.reqdresetCombCode;
                        var level = tbreqresetDetail.reqdresetRefId;
                        var reqresetdExpnum = tbreqresetDetail.reqdresetNum;

                        $("#" + reqresetdCombCode + "_Num_"+level).html(reqresetdExpnum);
                        $("#" + reqresetdCombCode + "_NumCopy_"+level).val(reqresetdExpnum);
                    }
                    initPlanAmount();
                    $("#plan").FrozenTable(1, 0, 1, 0);
                }
            }
        });

    });

    function initAuditOper() {
        $.ajax({
                url: "<%=path%>/tbReqResetSubmit/getOperInfoListByRolecode.htm?rolecode=${rolecode}",
                method: "GET",
                async: false,
                success: function (result) {
                    var list = [];
                    for (var i = 0; i < result.length; i++) {
                        var oper = result[i];
                        var item = {
                            "key": oper.opername,
                            "value": oper.opercode
                        };
                        list.push(item);
                    }
                    ;
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
                url: '<%=path%>/tbReqResetSubmit/startLoanReqAudit.htm',
                method: 'GET',
                data: {
                    "auditOper": auditOper,
                    "reqresetId": reqresetId,
                    "comment":comment
                }, success: function (res) {
                    if (res.success === 'true' || res.success === true) {
                        top.Dialog.alert("机构需求调整调整提交成功,等待审批结果", function () {
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
                        top.Dialog.alert("机构需求调整提交失败");
                        $("#btn1").attr("disabled", "");
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
                需求名称：
            </td>
            <td>
                <input id="reqresetId" name="reqresetId" type="hidden" value="${TbreqresetListDTO.reqresetId}"/>
                <input id="reqresetName" name="reqresetName" type="hidden"
                       value="${TbreqresetListDTO.reqresetName}"> </input>
                ${TbreqresetListDTO.reqresetName}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="reqresetUnit" name="reqresetUnit" type="hidden"
                       value="${TbreqresetListDTO.reqresetUnit}"> </input>
                <c:if test="${TbreqresetListDTO.reqresetUnit ==1}">
                    万元
                </c:if>
                <c:if test="${TbreqresetListDTO.reqresetUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                周期开始时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetTimeStart}
            </td>

            <td align="right">
                周期结束时间：
            </td>
            <td>
                ${TbreqresetListDTO.reqresetTimeEnd}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${TbreqresetListDTO.reqresetNote}
            </td>
        </tr>
    </table>
</form>

<form id="form1">
    <div id="scrollContent" class="border_gray"  style="height: 500px;">
        <table id="plan" class="tableStyle" tdTrueWidth="true" mode="list" fixedCellHeight="true" >
        <tr>
            <td trueWidth="150"></td>
            <td align="center">
                原计划
            </td>
            <c:forEach items="${combAmountNameList}" var="combAmountName">
                <td align="center">
                        ${combAmountName.name}
                </td>
            </c:forEach>
            <td align="center">
                调整后计划
            </td>
        </tr>

        <c:forEach items="${combList}" var="comb">
            <tr>
                <td> ${comb.combName}</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <span id="${comb.combCode}_oldNum">${comb.oldNum}</span>
                    </td>
                </c:forEach>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <div>
                            <span value="0"
                                  type="text" id="${comb.combCode}_${combAmountName.code}_${comb.combLevel}"/>
                        </div>
                        <div>
                            <input name="${comb.combCode}_${combAmountName.code}"
                                   class="planAmount"
                                   value="0" maxlength="16" AUTOCOMPLETE="off" hidden="hidden"
                                   type="text" id="${comb.combCode}_${combAmountName.code}Copy_${comb.combLevel}"/>
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
            <td>一级贷种组合合计</td>
            <td align="center"><span id="one_oldNum">${one_oldTotalNum}</span></td>
            <td align="center"><span id="one_num">0</span></td>
            <td align="center"><span id="one_newNum">0</span></td>
        </tr>
        <tr>
            <td>二级贷种组合合计</td>
            <td align="center"><span id="two_oldNum">${two_oldTotalNum}</span></td>
            <td align="center"><span id="two_num">0</span></td>
            <td align="center"><span id="two_newNum">0</span></td>
        </tr>
        <tr>
            <td>三级贷种组合合计</td>
            <td align="center"><span id="three_oldNum">${three_oldTotalNum}</span></td>
            <td align="center"><span id="three_num">0</span></td>
            <td align="center"><span id="three_newNum">0</span></td>
        </tr>
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
                    <button id="btn1" type="button" onclick="submitInfo()" class="submitButton"/>
                    <button id="btn2" type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
    </div>
</form>
</body>
</html>