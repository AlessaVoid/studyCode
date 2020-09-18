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
    <title>机构需求调整审批</title>
    <script type="text/javascript">
        window.onload = function () {
            initAuditOper();
        };
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

        function initAuditOper() {
            var where = $("#where").val();
            $.ajax({
                    url: "<%=path%>/TbReqresetPendingApp/getOperInfoListByRolecode.htm?taskid=${taskid}",
                    method: "GET",
                    async: false,
                    success: function(result) {
                        var list = [];
                        for (var i = 0; i < result.length; i++) {
                            var oper = result[i];
                            var item = {
                                "key": oper.opername,
                                "value": oper.opercode
                            };
                            list.push(item);
                        };
                        var selData = {
                            "list": list
                        };
                        $("#auditOperList").data("data", selData);
                        $("#auditOperList").render();
                    }
                }
            );
        }

        //更新信贷需求信息
        function submitInfo() {
            return doSubmit('form1', '<%=path%>/TbReqresetPendingApp/update.htm');
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
                    var taskId = $("#taskId").val();
                    $.ajax({
                        url: "<%=path%>/TbReqresetPendingApp/auditLoanReqRequest.htm",
                        data: {
                            "reqresetId": reqresetId,
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
                                $("#btn1").removeAttr("disabled");
                            }
                        }
                    });
                });
            }else {
                top.Dialog.confirm("确定要提交该记录吗？", function () {
                    $("#btn1").attr("disabled","disabled");
                    $("#btn2").attr("disabled","disabled");
                    var taskId = $("#taskId").val();
                    $.ajax({
                        url: "<%=path%>/TbReqresetPendingApp/auditLoanReqRequest.htm",
                        data: {
                            "reqresetId": reqresetId,
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
                                $("#btn1").removeAttr("disabled");
                            }
                        }
                    });
                });

            }





        }


        var reqresetId = '${reqresetId}';



        //提交
        function sub() {
            //校验
            var reqresetUnit = $("#reqresetUnit").val();

            var tbreqresetDetail = $("#form1").serialize();

            $.ajax({
                type: "POST",
                url: "<%=path%>/tbTradeManger/tbReqresetDetail/update.htm",
                data: {"reqresetUnit": reqresetUnit, "tbreqresetDetail": tbreqresetDetail, "reqresetId": reqresetId,"state":8},
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

        $(function () {
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
        })
        
    </script>
</head>
<body>
<form id="form2">
    <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                <input id="reqresetId" name="reqresetId" type="hidden" value="${TbreqresetListDTO.reqresetId}"/>
                <input id="reqresetName" name="reqresetName" type="hidden" value="${TbreqresetListDTO.reqresetName}"> </input>
                ${TbreqresetListDTO.reqresetName}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="reqresetUnit" name="reqresetUnit" type="hidden" value="${TbreqresetListDTO.reqresetUnit}"> </input>
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
        <button type="button"  id="btn1" onclick="onAudit('1')"><span class="icon_ok">通过</span></button>
        <button type="button" id="btn2"  onclick="onAudit('0')"><span class="icon_no">驳回</span></button>
    </div>
</div>
</form>
</body>
</html>