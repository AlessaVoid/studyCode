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
    <!--框架必需start-->
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <!--数据表格start-->
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <!--数据表格end-->
    <!-- 表单验证start -->
    <script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
    <!-- 表单验证end -->
    <!-- 日期选择框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
    <!-- 日期选择框end -->
    <!-- 数字步进器start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/stepper.js"></script>
    <!-- 数字步进器end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <title>信贷计划调整详情页面</title>
</head>
<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="planMonth" name="planMonth" value="${tbPlanadj.planadjMonth}" type="hidden"/>
                <input id="planadjId" name="planadjId" value="${tbPlanadj.planadjId}" type="hidden"/>
                <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
                ${tbPlanadj.planadjMonth}
            </td>
            <td align="left">单位：</td>
            <td>
                <input id="planUnit" name="planUnit" value="${tbPlanadj.planadjUnit}" type="hidden"/>

                <c:if test="${tbPlanadj.planadjUnit ==1}">
                    万元
                </c:if>
                <c:if test="${tbPlanadj.planadjUnit ==2}">
                    亿元
                </c:if>
            </td>
        </tr>
        <tr>


            <%--<td align="right">--%>
            <%--本月计划净增量：--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<input  id="increment" name="increment" value="${tbPlanadj.planadjNetIncrement}" type="hidden" />--%>
            <%--${tbPlanadj.planadjNetIncrement}--%>
            <%--</td>--%>
            <input id="increment" name="increment" value="${tbPlanadj.planadjNetIncrement}" type="hidden"/>


            <td align="left">是否统一调整：</td>
            <td>
                <input type="radio" id="type1" name="planadjUnifiedType" value="1"/>
                <lable for="type1" class="type">是</lable>
                <input type="radio" id="type2" name="planadjUnifiedType" value="2"/>
                <lable for="type2" class="type">否</lable>
            </td>
        </tr>
    </table>

    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td rowspan="2">
                    <div style="width: 150px;">机构</div>
                </td>
                <c:forEach items="${combList}" var="comb">
                    <td colspan="4" align="center">
                            ${comb.combname}
                    </td>
                </c:forEach>
                <td colspan="4" align="center">合计</td>
            </tr>
            <tr>

                <c:forEach items="${combList}" var="comb">
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">原计划金额</div>
                    </td>
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">分行需求金额</div>
                    </td>
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">调整金额</div>

                    </td>
                    <td align="center" trueWidth="150">
                        <div style="width: 150px;">调整后金额</div>
                    </td>
                </c:forEach>
                <td align="center" trueWidth="150">
                    <div style="width: 150px;">原计划金额</div>
                </td>
                <td align="center" trueWidth="150">
                    <div style="width: 150px;">分行需求金额</div>
                </td>
                <td align="center" trueWidth="150">
                    <div style="width: 150px;">调整金额</div>

                </td>
                <td align="center" trueWidth="150">
                    <div style="width: 150px;">调整后金额</div>
                </td>
            </tr>
            <c:forEach items="${organList}" var="organ">
                <tr>
                    <td> ${organ.organname }</td>
                    <c:forEach items="${combList}" var="comb">
                        <c:set var="adj_key" value="${organ.thiscode }_${comb.combcode}"/>
                        <c:set var="adjDetail" value="${adjMap[adj_key]}"/>
                        <td>
                            <input name="${organ.thiscode }_${comb.combcode}_id" value="${adjDetail.planadjdId}"
                                   type="hidden"/>
                            <c:if test="${empty adjDetail.planadjdInitAmount }">
                                <input class="${organ.thiscode}_init ${comb.combcode}_init init"
                                       name="${organ.thiscode }_${comb.combcode}_init" oninput='upperCase(this)'
                                       value="0" type="hidden"/>
                                0
                            </c:if>
                            <c:if test="${!empty adjDetail.planadjdInitAmount}">
                                <input class="${organ.thiscode}_init ${comb.combcode}_init init"
                                       name="${organ.thiscode }_${comb.combcode}_init" oninput='upperCase(this)'
                                       value="${adjDetail.planadjdInitAmount}" type="hidden"/>
                                <fm:formatNumber maxFractionDigits="10" value="${adjDetail.planadjdInitAmount}"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${empty adjDetail.planadjdReqAmount }">
                                <input class="${organ.thiscode}_req ${comb.combcode}_req req"
                                       name="${organ.thiscode }_${comb.combcode}_req" oninput='upperCase(this)'
                                       value="0" type="hidden"/>
                                0
                            </c:if>
                            <c:if test="${!empty adjDetail.planadjdReqAmount}">
                                <input class="${organ.thiscode}_req ${comb.combcode}_req req"
                                       name="${organ.thiscode }_${comb.combcode}_req" oninput='upperCase(this)'
                                       value="${adjDetail.planadjdReqAmount}" type="hidden"/>
                                <fm:formatNumber maxFractionDigits="10" value="${adjDetail.planadjdReqAmount}"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${empty adjDetail.planadjdAdjAmount }">
                                <input name="${organ.thiscode }_${comb.combcode}_adj" oninput='upperCase(this)'
                                       class="${organ.thiscode}_adj ${comb.combcode}_adj adj plandadjamount" value="0"
                                       type="text"/>
                            </c:if>
                            <c:if test="${!empty adjDetail.planadjdAdjAmount}">
                                <input name="${organ.thiscode }_${comb.combcode}_adj" oninput='upperCase(this)'
                                       class="${organ.thiscode}_adj ${comb.combcode}_adj adj plandadjamount"
                                       value="${adjDetail.planadjdAdjAmount}" type="text"
                                       id="${organ.thiscode }_${comb.combcode}" class="plandadj"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${empty adjDetail.planadjdAdjedAmount }">
                                <input name="${organ.thiscode }_${comb.combcode}_final"
                                       class="${organ.thiscode}_final ${comb.combcode}_final final planadjAmonutFinal"
                                       oninput='upperCase(this)' value="0" type="hidden"/>
                                <span name="${organ.thiscode}_${comb.combcode}_final">0</span>
                            </c:if>
                            <c:if test="${!empty adjDetail.planadjdAdjedAmount}">
                                <input name="${organ.thiscode }_${comb.combcode}_final"
                                       class="${organ.thiscode}_final ${comb.combcode}_final final planadjAmonutFinal"
                                       oninput='upperCase(this)' value="${adjDetail.planadjdAdjedAmount}"
                                       type="hidden"/>
                                <span name="${organ.thiscode}_${comb.combcode}_final">${adjDetail.planadjdAdjedAmount}</span>
                            </c:if>

                        </td>
                    </c:forEach>

                    <td><span id="${organ.thiscode}_init_count" class="organ_init_count">0</span></td>
                    <td><span id="${organ.thiscode}_req_count">0</span></td>
                    <td><span id="${organ.thiscode}_adj_count">0</span></td>
                    <td><span id="${organ.thiscode}_final_count">0</span></td>
                </tr>
            </c:forEach>

            <tr>
                <td>合计</td>
                <c:forEach items="${combList}" var="comb">
                    <td>
                        <span id="${comb.combcode}_init_count" class="comb_init_count">0</span>
                    </td>
                    <td>
                        <span id="${comb.combcode}_req_count">0</span>
                    </td>
                    <td>
                        <span id="${comb.combcode}_adj_count">0</span>
                    </td>
                    <td>
                        <span id="${comb.combcode}_final_count">0</span>
                    </td>
                </c:forEach>
                <td>
                    <span id="init_count">0</span>
                </td>
                <td>
                    <span id="req_count">0</span>
                </td>
                <td>
                    <span id="adj_count">0</span>
                </td>
                <td>
                    <span id="final_count">0</span>
                </td>
            </tr>

        </table>

    </div>
    <div align="center" style="alignment: center">
        <button type="button" id="submit" onclick="return sub()" class="saveButton"></button>
        <button type="button" id="cancelSubmit" onclick="return cancel()" class="cancelButton"></button>
    </div>
</form>
<input type="hidden" id="comments" name="comments" value="${fn:length(comments)}"/>
<c:if test="${!empty comments }">
    <div id="panel23" panelTitle="审批进度" class="box2_custom" boxType="box2" startState="open">
        <table class="tableStyle tab-hei-30" width="60%" mode="list">
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
<table class="tableStyle" width="100%" mode="list" formMode="line">
    <c:if test="${false == lastUserType }">
        <div>
            <div align="left">下一节点审批人:</div>
            <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
        </div>
    </c:if>
    <tr>
        <td colspan="8">
            <div align="center">
                <button id="planSubmitInfo" type="button" onclick="onAudit('1')" style="margin-right: 10px"
                ><span>提交</span></button>
                <button id="cancelSubmitInfo" type="button" onclick="return cancel()" class="cancelButton"/>
            </div>
        </td>
    </tr>
</table>
</body>

<script>
    var organlevel = '${organlevel}';
    //页面有修改，是否能重新提交
    var isSubmit = true;


    window.onload = function () {
        initAuditOper();
    };

    //用户只能输入正负数与小数
    function upperCase(obj) {
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }
    }


    $(function () {
        //初始化是否统一调整
        if ('${tbPlanadj.planadjUnifiedType}' == '1') {
            $('#type1').attr("checked", true);
        }else if ('${tbPlanadj.planadjUnifiedType}' == '2') {
            $('#type2').attr("checked", true);
        }

        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(2, 0, 1, 0);

        //初始化横竖求和
        amountRowAndColumn();
        // 初始化调整后金额
        amountFinal();



        //输入框获取焦点事件
        $(".plandadjamount").focus(function () {
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".plandadjamount").blur(function () {
            if ($.trim(this.value) == "") {
                this.value = 0;
            }

            //计算调整后金额
                isSubmit = false;
                var id = $(this).attr('id');
                var initName = id + "_init";
                var adjName = id + "_adj";
                var finalName = id + "_final";
                var init = $('input[name=' + initName + ']').val();
                var adj = $('input[name=' + adjName + ']').val();
                $('input[name=' + finalName + ']').val(accAdd(parseFloat(init), parseFloat(adj)));
                $('span[name=' + finalName + ']').html(accAdd(parseFloat(init), parseFloat(adj)));

                // 计算横竖求和
                changeAmountRowAndColumn(id);
        });
    })

    //求和 行列
    function changeAmountRowAndColumn(id) {
        var ids = id.split("_");
        // 行求和
        // 调整金额
        var adjRowName = ids[0] + "_adj";
        var adjRowCount = countClass(adjRowName);
        $("span[id=" + adjRowName + "_count" + "]").html(adjRowCount);
        // 调整后金额
        var finalRowName = ids[0] + "_final";
        var finalRowCount = countClass(finalRowName);
        $("span[id=" + finalRowName + "_count" + "]").html(finalRowCount);

        // 列求和
        // 调整金额
        var adjColumnName = ids[1] + "_adj";
        var adjColumnCount = countClass(adjColumnName);
        $("span[id=" + adjColumnName + "_count" + "]").html(adjColumnCount);
        // 调整后金额
        var finalColumeName = ids[1] + "_final";
        var finalColumeCount = countClass(finalColumeName);
        $("span[id=" + finalColumeName + "_count" + "]").html(finalColumeCount);

        // 总求和
        var adjAllCount = countClass("adj");
        $("#adj_count").html(adjAllCount);
        var finalAllCount = countClass("final");
        $("#final_count").html(finalAllCount);
    }

    //初始化求和 行列
    function amountRowAndColumn() {
        // 行求和
        var rowList = $(".organ_init_count");
        $(rowList).each(function () {
            var id = $(this).attr('id');
            var ids = id.split("_");
            //原计划金额
            var initName = ids[0] + "_init";
            var initCount = countClass(initName);
            $("span[id=" + initName + "_count" + "]").html(initCount);
            // 分行需求金额
            var reqName = ids[0] + "_req";
            var reqCount = countClass(reqName);
            $("span[id=" + reqName + "_count" + "]").html(reqCount);
            // 调整金额
            var adjName = ids[0] + "_adj";
            var adjCount = countClass(adjName);
            $("span[id=" + adjName + "_count" + "]").html(adjCount);
            // 调整后金额
            var finalName = ids[0] + "_final";
            var finalCount = countClass(finalName);
            $("span[id=" + finalName + "_count" + "]").html(finalCount);

        });

        // 列求和
        var columnList = $(".comb_init_count");
        $(columnList).each(function () {
            var id = $(this).attr('id');
            var ids = id.split("_");
            //原计划金额
            var initName = ids[0] + "_init";
            var initCount = countClass(initName);
            $("span[id=" + initName + "_count" + "]").html(initCount);
            // 分行需求金额
            var reqName = ids[0] + "_req";
            var reqCount = countClass(reqName);
            $("span[id=" + reqName + "_count" + "]").html(reqCount);
            // 调整金额
            var adjName = ids[0] + "_adj";
            var adjCount = countClass(adjName);
            $("span[id=" + adjName + "_count" + "]").html(adjCount);
            // 调整后金额
            var finalName = ids[0] + "_final";
            var finalCount = countClass(finalName);
            $("span[id=" + finalName + "_count" + "]").html(finalCount);

        });

        // 总的求和
        var initAllCount = countClass("init");
        $("#init_count").html(initAllCount);
        var reqAllCount = countClass("req");
        $("#req_count").html(reqAllCount);
        var adjAllCount = countClass("adj");
        $("#adj_count").html(adjAllCount);
        var finalAllCount = countClass("final");
        $("#final_count").html(finalAllCount);

    }

    //计算所有class的值的和
    function countClass(str) {
        str = "." + str;
        var amonutList = $(str);
        var amount = 0;
        $(amonutList).each(function () {
            amount = accAdd(amount, this.value);
        });
        return amount;
    }


    // 初始化调整后金额
    function amountFinal() {
        var amonutList = $(".plandadjamount");
        $(amonutList).each(function () {
            var id = $(this).attr('id');
            var initName = id + "_init";
            var adjName = id + "_adj";
            var finalName = id + "_final";
            var init = $('input[name=' + initName + ']').val();
            var adj = $('input[name=' + adjName + ']').val();
            $('input[name=' + finalName + ']').val(accAdd(parseFloat(init), parseFloat(adj)));
            $('span[name=' + finalName + ']').html(accAdd(parseFloat(init), parseFloat(adj)));
            $('#'+id).val(transFormat(this.value));
        });
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


    // 计算调整金额的总和
    function addPlanadjAmonut() {
        var amonutList = $(".plandadjamount");
        var amountCount = 0;
        $(amonutList).each(function () {
            amountCount = accAdd(amountCount, this.value);
        });
        return amountCount;
    }


    //js计算除法
    function accDiv(arg1, arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length
        } catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length
        } catch (e) {
        }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""))
            r2 = Number(arg2.toString().replace(".", ""))
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }

    // 计算调整后金额的总和
    function addPlanadjAmonutFinalCountAmonut() {
        var planadjAmonutFinalList = $(".planadjAmonutFinal");
        var planadjAmonutFinalCount = 0;
        $(planadjAmonutFinalList).each(function () {
            planadjAmonutFinalCount = accAdd(planadjAmonutFinalCount, this.value);
        });
        return planadjAmonutFinalCount;
    }

    //信贷计划调整提交
    function sub() {
        //校验
        var planMonth = $("#planMonth").val();
        var planUnit = $("#planUnit").val();

        var amountCount = addPlanadjAmonut();
        var unit = $("#planUnit").val() == 1 ? "万元" : "亿元";

        if (!planMonth) {
            top.Dialog.alert("所属月份不可为空");
            return;
        }
        if (!planUnit) {
            top.Dialog.alert("单位不可为空");
            return;
        }

        //调整后金额总量
        var planadjAmountFinalCount = addPlanadjAmonutFinalCountAmonut();
        var increment = $("#increment").val();
        if (planUnit == 1) {
            planadjAmountFinalCount = accDiv(planadjAmountFinalCount, 10000);
        }

        //1分制定量必须等于计划净增量
        // if (organlevel == '1') {
        //     if (planadjAmountFinalCount != increment) {
        //         top.Dialog.alert("计划制定总额为：" + planadjAmountFinalCount + "亿元,本月计划净增量为：" + increment + "亿元，请调整！");
        //         return;
        //     }
        // }

        submitPlanadj();
        // if (planadjAmountFinalCount > increment) {
        //     top.Dialog.confirm("计划制定总额为：" + planadjAmountFinalCount + "亿元,本月计划净增量为：" + increment + "亿元，已超出！确定要保存操作吗?|操作提示", function () {
        //         submitPlanadj();
        //     });
        // } else {
        //     top.Dialog.confirm("本次调整金额一共为："+amountCount+unit+"。确定要保存操作吗?|操作提示", function () {
        //         submitPlanadj();
        //     });
        // }
    }

    function submitPlanadj() {
        //信贷调整详情url
        var adjustUrl = "<%=path%>/tbPlanadj/updatePlanadj.htm";
        var data = $("#form1").serialize();
        //post方法,同样也可以调用ajax
        $('#submit').attr('disabled', true);
        $('#cancelSubmit').attr('disabled', true);
        $.ajax({
            type: 'post',
            url: adjustUrl,
            cache: false,
            data: data,  //重点必须为一个变量如：data
            dataType: 'json',
            success: function (result) {
                $('#submit').attr('disabled', false);
                $('#cancelSubmit').attr('disabled', false);
                if (result.success == true || result.success == "true") {
                    isSubmit = true;
                    top.Dialog.alert("修改成功!");
                } else {
                    if (result.code == '403') {
                        top.Dialog.alert("所属月份批量机构调整计划已存在");
                    } else if (result.code == '408') {
                        top.Dialog.alert(result.message);
                    } else {
                        top.Dialog.alert("修改失败");
                    }
                }
            },
            error: function (result) {
                $('#submit').attr('disabled', false);
                $('#cancelSubmit').attr('disabled', false);
                top.Dialog.alert("请求异常");
            }
        })
    }

    //初始化信贷计划审批人员
    function initAuditOper() {
        //初始化信贷计划审批人员
        $.ajax({
            url: "<%=path%>/tbPlanadjPendingApp/getOperInfoListByRolecode.htm?taskId=${taskid}",
            method: "GET",
            async: false,
            success: function (res) {
                var result = JSON.parse(res)
                var list = [];
                for (var i = 0; i < result.length; i++) {
                    var oper = result[i];
                    var item = {
                        "key": oper.opername,
                        "value": oper.opercode
                    };
                    list.push(item);
                    var selData = {
                        "list": list
                    };
                    $("#auditOperList").data("data", selData);
                    $("#auditOperList").render();
                }
            }
        })
    }

    //重新提交信贷计划调整
    function onAudit(isAgree) {
        var comment = $("#comment").val();
        if (comment == "") {
            top.Dialog.alert("请填写备注!");
            return;
        }
        var planadjId = $("#planadjId").val();
        var taskId = $("#taskId").val();
        var assignee = $("#auditOperList").val();
        if (assignee == "") {
            top.Dialog.alert("请选择下级审批人!");
            return;
        }

        if (isSubmit) {
            top.Dialog.confirm("确定要重新提交审批吗?", function () {
                submit(isAgree);
            });
        } else {
            top.Dialog.confirm("页面有未提交的数据，确定要重新提交审批吗?", function () {
                submit(isAgree);
            });
        }

    }

    //提交审批
    function submit(isAgree) {
        var comment = $("#comment").val();
        var planadjId = $("#planadjId").val();
        var taskId = $("#taskId").val();
        var assignee = $("#auditOperList").val();
        $('#planSubmitInfo').attr('disabled', true);
        $('#cancelSubmitInfo').attr('disabled', true);
        $.ajax({
            url: "<%=path%>/tbPlanadjReject/auditLoanTbPlanadjRequest.htm",
            data: {
                "planadjId": planadjId,
                "taskId": taskId,
                "comment": comment,
                "isAgree": isAgree,
                "assignee": assignee,
                "lastUserType":${lastUserType}
            },
            type: 'POST',
            success: function (result) {
                $('#planSubmitInfo').attr('disabled', false);
                $('#cancelSubmitInfo').attr('disabled', false);
                var res = JSON.parse(result);
                if (res.success === "true" || res.success === true) {
                    top.Dialog.alert("提交成功!", function () {
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
                    top.Dialog.alert("提交失败!" + res.message);
                }
            }
        });
    }

</script>

</html>