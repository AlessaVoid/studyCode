<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>条线需求审批</title>
    <script type="text/javascript">

        var lineReqId = '${lineReqId}';
        window.onload = function () {
            initAuditOper();
        };

        function initAuditOper() {
            $.ajax({
                    url: "<%=path%>/TbLineReqPendingApp/getOperInfoListByRolecode.htm?taskid=${taskid}"+"&lineReqId="+lineReqId,
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
                    var lineReqId = $("#lineReqId").val();
                    var taskId = $("#taskId").val();
                    $.ajax({
                        url: "<%=path%>/TbLineReqPendingApp/auditLoanReqRequest.htm",
                        data: {
                            "lineReqId": lineReqId,
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
                    var lineReqId = $("#lineReqId").val();
                    var taskId = $("#taskId").val();
                    $.ajax({
                        url: "<%=path%>/TbLineReqPendingApp/auditLoanReqRequest.htm",
                        data: {
                            "lineReqId": lineReqId,
                            "taskId": taskId,
                            "comment": comment,
                            "isAgree": isAgree,
                            "assignee": assignee,
                            "lastUserType":${lastUserType}
                        },
                        method: 'POST',
                        success: res => {
                        if (res.success == "true" || res.success == true) {
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


        //提交
        function sub() {
            //校验
            var lineUnit = $("#lineUnit").val();
            var lineRefId = $("#lineRefId").val();
            var tbLineReqDetail = $("#form1").serialize();

            $.ajax({
                type: "POST",
                url: "<%=path%>/tbTradeManger/tbLineReqDetail/update.htm",
                data: {"lineUnit": lineUnit, "tbLineReqDetail": tbLineReqDetail, "lineReqId": lineReqId,"state":8,"lineRefId":lineRefId},
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

        var tbReqDetailList = '${tbReqDetailList}';
        $(function () {

            $.ajax({
                type: "POST",
                url: "<%=path%>/tbTradeManger/tbLineReqDetail/getReqDetailList.htm",
                data: {"lineReqId": lineReqId},
                dataType: "json",
                success: function (result) {
                    if (result) {
                        var tbLineReqDetailDTOS = result.tbLineReqDetailDTOS;
                        for (var i = 0; i < tbLineReqDetailDTOS.length; i++) {
                            var tbReqDetail = tbLineReqDetailDTOS[i];
                            var reqdCombCode = tbReqDetail.lineCombCode;
                            var reqdExpnum = tbReqDetail.lineExpnum;
                            var reqdReqnum = tbReqDetail.lineReqnum;
                            var reqdRate = tbReqDetail.lineRate;
                            var reqdBalance = tbReqDetail.lineBalance;


                            $("#" + reqdCombCode + "_expNum_copy").html(reqdExpnum);
                            $("#" + reqdCombCode + "_reqNum_copy").html(reqdReqnum);
                            $("#" + reqdCombCode + "_rate_copy").html(reqdRate);
                            $("#" + reqdCombCode + "_balance_copy").html(reqdBalance);

                            $("#" + reqdCombCode + "_expNum").val(reqdExpnum);
                            $("#" + reqdCombCode + "_reqNum").val(reqdReqnum);
                            $("#" + reqdCombCode + "_rate").val(reqdRate);
                            $("#" + reqdCombCode + "_balance").val(reqdBalance);
                        }
                    }
                    countAmount();
                }
            });


        })
        //js加法计算
        function accAdd(arg1, arg2) {
            var r1, r2, m;
            try {
                r1 = arg1.toString().split(".")[1].length
            } catch (e) {
                r1 = 0
            }
            try {
                r2 = arg2.toString().split(".")[1].length
            } catch (e) {
                r2 = 0
            }
            m = Math.pow(10, Math.max(r1, r2))
            return transFormat((accMul(arg1, m) + accMul(arg2, m)) / m)
        }


 function transFormat(amount) {
        var amountStr = String(amount);
        if (amountStr.indexOf("-") > 0) {
            amountStr = '0' + String(Number(amountStr) + 1).substr(1);
        }else if(amountStr.indexOf("-") == 0){

            if(amountStr.substr(1).indexOf("-") > 0){
                amountStr = '-0' + String(Number(amountStr.substr(1)) + 1).substr(1);
            }
        }
        return amountStr;
    }

        //js计算乘法
        function accMul(arg1, arg2) {
            var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
            try {
                m += s1.split(".")[1].length
            } catch (e) {
            }
            try {
                m += s2.split(".")[1].length
            } catch (e) {
            }
            return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
        }

        // 计算调整金额的总和
        function addPlanAmonut() {
            var amonutList = $(".planamonut");
            var amountCount = 0;
            $(amonutList).each(function () {
                var id = this.id;
                if (id.split("_")[1] == "rate") {
                } else {
                    amountCount = accAdd(amountCount, this.value);
                }
            });
            return amountCount;
        }


        //计算所有class的值的和
        function countClass(str) {
            str = "." + str;
            var amonutList = $(str);
            var amount = 0;
            $(amonutList).each(function () {
                var id = this.id;
                if (id.split("_")[1] == "rate") {
                } else {
                    amount = accAdd(amount, this.value);
                }
            });
            return amount;
        }

        //初始化合计数
        function countAmount() {
            //贷种组合求和
            var planamonut_comb = $(".planamonut_comb");

            $(planamonut_comb).each(function () {

                var id = $(this).attr('id');
                var columnId = id + "_column";
                var columnAmount = countClass(id);
                $('span[id=' + columnId + ']').html(columnAmount);

            });
            //机构求和
            var planamonut_organ = $(".planamonut_organ");
            $(planamonut_organ).each(function () {
                var id = $(this).attr('id');
                var rowId = id + "_row";
                var rowAmount = countClass(id);
                $('span[id=' + rowId + ']').html(rowAmount);
            });
            //总和
            var countAmount = addPlanAmonut();
            $('span[id=row_column_count]').html(countAmount);

        }

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
                <input id="lineRefId" name="lineRefId" type="hidden" value="${tbLineReqDetail.lineRefId}"/>
                <input id="lineReqId" name="lineReqId" type="hidden" value="${tbLineReqDetail.lineReqId}"/>
                <input id="lineReqMonth" name="lineReqMonth" type="hidden" value="${tbLineReqDetail.lineReqName}"> </input>
                ${tbLineReqDetail.lineReqMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="lineUnit" name="lineUnit" type="hidden" value="${tbLineReqDetail.lineUnit}"> </input>
                <c:if test="${tbLineReqDetail.lineUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbLineReqDetail.lineUnit ==2}">
                    亿元
                </c:if>

            </td>
        </tr>
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                ${tbLineReqDetail.lineReqName}
            </td>

            <td align="right">
                条线名称：
            </td>
            <td>
                ${tbLineReqDetail.lineName}
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">
                需求说明：
            </td>
            <td colspan="3" style="word-break:break-all">
                ${tbLineReqDetail.lineReqNote}
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
                <td trueWidth="150"></td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td trueWidth="100">
                        <input type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                            ${combAmountName.name}
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <div>
                            <span value="0"
                                  type="text" id="${comb.combCode}_${combAmountName.code}_copy"/>
                            </div>
                            <div>
                                <input name="${comb.combCode}_${combAmountName.code}"
                                       type="hidden" class="planamonut ${comb.combCode } ${combAmountName.code}"
                                       id="${comb.combCode}_${combAmountName.code}" readOnly="readOnly"/>
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            <tr>
                <td> 合计</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <span id="${combAmountName.code}_column">0</span>
                    </td>
                </c:forEach>
                </td>
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