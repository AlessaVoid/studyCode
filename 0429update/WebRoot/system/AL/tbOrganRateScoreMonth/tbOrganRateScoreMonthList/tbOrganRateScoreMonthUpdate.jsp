<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validationRule.js" type="text/javascript"></script>
    <script src="<%=path%>/libs/js/form/validation.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/stepper.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <title>信贷计划修改页面</title>
</head>
<body>

<form id="form1" >
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" >

            <tr>

                <td ><div style="width: 150px;"> </div></td>
                <td ><div style="width: 100px;"></div> </td>
                <td ></td>

                <td colspan="4" align="center" >不良情况</td>

                <td colspan="5" align="center" >自营新增存贷比 </td>

                <td  colspan="4" align="center">新发生贷款利率 </td>

                <td colspan="7" align="center" >贷款结构 </td>

                <td colspan="4" align="center">超规模占用费和规模闲置费 </td>

                <td colspan="4" align="center">计划报送偏离度</td>

                <td colspan="5" align="center">月末月中计划执行偏离度</td>

                <td colspan="4" align="center">月末月初计划执行偏离度</td>

                <td colspan="2" align="center">调整频次 </td>

                <td colspan="2" align="center">其他 </td>

            </tr>

            <tr>
                <td ><div style="width: 150px;">评分机构列表 </div></td>
                <td ><div style="width: 100px;">月份</div> </td>
                <td >总分 </td>

                <td >不良情况得分 </td>
                <td >月末不良率 </td>
                <td >年初不良率 </td>
                <td >不良率 </td>

                <td >自营新增存贷比得分 </td>
                <td >各项贷款年增量 </td>
                <td >个人自营存款年增量 </td>
                <td >公司存款年增量 </td>
                <td >自营新增存贷比 </td>

                <td >新发生贷款利率得分 </td>
                <td >新发生贷款利率 </td>
                <td >全行平均贷款利率 </td>
                <td >贷款利率变化情况 </td>

                <td >贷款结构得分 </td>
                <td >总贷款 </td>
                <td >月初实体贷款余额 </td>
                <td >月末实体贷款余额 </td>
                <td >月初实体贷款余额占比 </td>
                <td >月末实体贷款余额占比 </td>
                <td >月末实体贷款余额占比变化 </td>

                <td >超规模占用费和规模闲置费得分 </td>
                <td >分行罚息金额 </td>
                <td >全行罚息金额 </td>
                <td >罚息金额占比 </td>

                <td >计划报送偏离度得分 </td>
                <td >月末实际月增量 </td>
                <td >月初报送需求 </td>
                <td >计划报送偏离幅度 </td>

                <td >月末月中计划执行偏离度得分 </td>
                <td >月末实际月增量 </td>
                <td >月中统一动态调整后计划 </td>
                <td >月末统一动态调整后计划 </td>
                <td >月末月中计划执行偏离幅度 </td>

                <td >月末月初计划执行偏离度得分 </td>
                <td >月末实际月增量 </td>
                <td >月初总行下达计划 </td>
                <td >月末月初偏离幅度 </td>

                <td >调整频次得分 </td>
                <td >调整频次次数 </td>

                <td >调整分数 </td>
                <td div style="width: 150px;">备注 </td>


            </tr>
            <c:forEach items="${organList}" var="organ">
                <tr>
                    <c:set var="rate_key" value="${organ.thiscode}"/>
                    <c:set var="rateScoreDetail" value="${rateScoreMap[rate_key]}"/>
                    <td> ${organ.organname }</td>
                    <td>
                        <input name="${organ.thiscode}_rateMonth"
                               value="${rateScoreDetail.rateMonth}"
                               class="rateScore" type="hidden"
                               id="${organ.thiscode }_rateMonth"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.rateMonth}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_rateScoreMonth"
                               value="${rateScoreDetail.rateScoreMonth}"
                               class="rateScore" type="hidden"
                               id="${organ.thiscode }_rateScoreMonth"
                               oninput='upperCase(this)'/>
                        <span name = "${organ.thiscode}_rateScoreMonth" >${rateScoreDetail.rateScoreMonth}</span>
                    </td>

                        <%--不良情况得分 不良率 = 月末不良率 - 年初不良率--%>
                    <td>
                        <input name="${organ.thiscode}_badConditionScore"
                               value="${rateScoreDetail.badConditionScore}"
                               id="${organ.thiscode }_badConditionScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_badConditionRatioMonthEnd"
                               value="${rateScoreDetail.badConditionRatioMonthEnd}"
                               id="${organ.thiscode }_badConditionRatioMonthEnd"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionRatioMonthEnd}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_badConditionRatioYearBegin"
                               value="${rateScoreDetail.badConditionRatioYearBegin}"
                               id="${organ.thiscode }_badConditionRatioYearBegin"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionRatioYearBegin}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_badConditionRatioChange"
                               value="${rateScoreDetail.badConditionRatioChange}"
                               id="${organ.thiscode }_badConditionRatioChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionRatioChange}
                    </td>


                    <%--自营新增存贷比得分 自营新增存贷比=各项贷款年增量 /（个人自营存款年增量 + 公司存款年增量）--%>
                    <td>
                        <input name="${organ.thiscode}_depositLoanRatioScore"
                               value="${rateScoreDetail.depositLoanRatioScore}"
                               id="${organ.thiscode }_depositLoanRatioScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.depositLoanRatioScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_loanYearAdd"
                               value="${rateScoreDetail.loanYearAdd}"
                               id="${organ.thiscode }_loanYearAdd"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.loanYearAdd}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_personalDepositYearIncrement"
                               value="${rateScoreDetail.personalDepositYearIncrement}"
                               id="${organ.thiscode }_personalDepositYearIncrement"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.personalDepositYearIncrement}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_companyDepositYearIncrement"
                               value="${rateScoreDetail.companyDepositYearIncrement}"
                               id="${organ.thiscode }_companyDepositYearIncrement"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.companyDepositYearIncrement}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_depositLoanRatioChange"
                               value="${rateScoreDetail.depositLoanRatioChange}"
                               id="${organ.thiscode }_depositLoanRatioChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.depositLoanRatioChange}
                    </td>


                    <%--新发生贷款利率得分  贷款利率差 = 新发生贷款利率 - 全行平均贷款利率--%>
                    <td>
                        <input name="${organ.thiscode}_newLoanRatioScore"
                               value="${rateScoreDetail.newLoanRatioScore}"
                               id="${organ.thiscode }_newLoanRatioScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.newLoanRatioScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_newLoanRatio"
                               value="${rateScoreDetail.newLoanRatio}"
                               id="${organ.thiscode }_newLoanRatio"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.newLoanRatio}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_bankAverageLoanRatio"
                               value="${rateScoreDetail.bankAverageLoanRatio}"
                               id="${organ.thiscode }_bankAverageLoanRatio"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.bankAverageLoanRatio}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_newLoanRatioChange"
                               value="${rateScoreDetail.newLoanRatioChange}"
                               id="${organ.thiscode }_newLoanRatioChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.newLoanRatioChange}
                    </td>


                    <%--贷款结构得分 月末实体贷款余额占比 = 月末实体贷款余额/总贷款 - 月初实体贷款余额/总贷款--%>

                    <td>
                        <input name="${organ.thiscode}_loanStructScore"
                               value="${rateScoreDetail.loanStructScore}"
                               id="${organ.thiscode }_loanStructScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.loanStructScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_loanCount"
                               value="${rateScoreDetail.loanCount}"
                               id="${organ.thiscode }_loanCount"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.loanCount}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthBeginEntityLoan"
                               value="${rateScoreDetail.monthBeginEntityLoan}"
                               id="${organ.thiscode }_monthBeginEntityLoan"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthBeginEntityLoan}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthEndEntityLoan"
                               value="${rateScoreDetail.monthEndEntityLoan}"
                               id="${organ.thiscode }_monthEndEntityLoan"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthEndEntityLoan}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthBeginEntityLoanChange"
                               value="${rateScoreDetail.monthBeginEntityLoanChange}"
                               id="${organ.thiscode }_monthBeginEntityLoanChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthBeginEntityLoanChange}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthEndEntityLoanChange"
                               value="${rateScoreDetail.monthEndEntityLoanChange}"
                               id="${organ.thiscode }_monthEndEntityLoanChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthEndEntityLoanChange}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthEndBeginEntityLoanChange"
                               value="${rateScoreDetail.monthEndBeginEntityLoanChange}"
                               id="${organ.thiscode }_monthEndBeginEntityLoanChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthEndBeginEntityLoanChange}
                    </td>
                    <%--超规模占用费和规模闲置费得分  分行罚息占比 =  分行罚息金额 / 全行罚息金额--%>

                    <td>
                        <input name="${organ.thiscode}_scaleAmountScore"
                               value="${rateScoreDetail.scaleAmountScore}"
                               id="${organ.thiscode }_scaleAmountScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_organScaleAmount"
                               value="${rateScoreDetail.organScaleAmount}"
                               id="${organ.thiscode }_organScaleAmount"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.organScaleAmount}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_bankScaleAmount"
                               value="${rateScoreDetail.bankScaleAmount}"
                               id="${organ.thiscode }_bankScaleAmount"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.bankScaleAmount}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_scaleAmountChange"
                               value="${rateScoreDetail.scaleAmountChange}"
                               id="${organ.thiscode }_scaleAmountChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountChange}
                    </td>

                    <%--计划报送偏离度得分  计划报送偏离幅度 = （月末实际月增量 - 月初报送需求） / 月末实际月增量--%>
                    <td>
                        <input name="${organ.thiscode}_planSubmitDeviationScore"
                               value="${rateScoreDetail.planSubmitDeviationScore}"
                               id="${organ.thiscode }_planSubmitDeviationScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planSubmitDeviationScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthEndIncrement"
                               value="${rateScoreDetail.monthEndIncrement}"
                               id="${organ.thiscode }_monthEndIncrement"
                               class="rateScore" type="hidden"
                               onchange="monthEndIncrementChange(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthEndIncrement}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthBeginReport"
                               value="${rateScoreDetail.monthBeginReport}"
                               id="${organ.thiscode }_monthBeginReport"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthBeginReport}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planSubmitDeviationChange"
                               value="${rateScoreDetail.planSubmitDeviationChange}"
                               id="${organ.thiscode }_planSubmitDeviationChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planSubmitDeviationChange}
                    </td>


                    <%--计划执行偏离度--月末月中偏离度得分   月末月中偏离幅度=（月末实际月增量-月中统一动态调整后计划）/月末统一动态调整后计划--%>

                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthMidScore"
                               value="${rateScoreDetail.planExecuteDeviationMonthMidScore}"
                               id="${organ.thiscode }_planExecuteDeviationMonthMidScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthMidScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthEndIncrement2"
                               value="${rateScoreDetail.monthEndIncrement}"
                               id="${organ.thiscode }_monthEndIncrement2"
                               class="rateScore" type="hidden"
                               onchange="monthEndIncrementChange(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthEndIncrement}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthMidPlan"
                               value="${rateScoreDetail.monthMidPlan}"
                               id="${organ.thiscode }_monthMidPlan"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthMidPlan}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthEndPlan"
                               value="${rateScoreDetail.monthEndPlan}"
                               id="${organ.thiscode }_monthEndPlan"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthEndPlan}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthMidChange"
                               value="${rateScoreDetail.planExecuteDeviationMonthMidChange}"
                               id="${organ.thiscode }_planExecuteDeviationMonthMidChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthMidChange}
                    </td>

                    <%--计划执行偏离度--月末月初偏离度得分 月末月初偏离幅度=（月末实际月增量-月初总行下达计划）/月初总行下达计划--%>


                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthInitScore"
                               value="${rateScoreDetail.planExecuteDeviationMonthInitScore}"
                               id="${organ.thiscode }_planExecuteDeviationMonthInitScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthInitScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthEndIncrement3"
                               value="${rateScoreDetail.monthEndIncrement}"
                               id="${organ.thiscode }_monthEndIncrement3"
                               class="rateScore" type="hidden"
                               onchange="monthEndIncrementChange(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthEndIncrement}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_monthBeginPlan"
                               value="${rateScoreDetail.monthBeginPlan}"
                               id="${organ.thiscode }_monthBeginPlan"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.monthBeginPlan}
                    </td>

                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthInitChange"
                               value="${rateScoreDetail.planExecuteDeviationMonthInitChange}"
                               id="${organ.thiscode }_planExecuteDeviationMonthInitChange"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthInitChange}
                    </td>

                    <%--调整频次得分--%>
                    <td>
                        <input name="${organ.thiscode}_adjCountScore"
                               value="${rateScoreDetail.adjCountScore}"
                               id="${organ.thiscode }_adjCountScore"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreMonth(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCountScore}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_adjCount"
                               value="${rateScoreDetail.adjCount}"
                               id="${organ.thiscode }_adjCount"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCount}
                    </td>
                    <%--其他--%>

                    <td>
                        <input name="${organ.thiscode}_addScore"
                               value="${rateScoreDetail.addScore}"
                               id="${organ.thiscode }_addScore"
                               class="rateScore" type="text"
                               oninput='upperCase(this)'
                               onchange="getRateScoreMonth(this)"
                        />
                    </td>
                    <td>
                        <input name="${organ.thiscode}_remark"
                               value="${rateScoreDetail.remark}"
                               id="${organ.thiscode }_remark"
                        />
                    </td>


                </tr>

            </c:forEach>

        </table>

    </div>

    <div align="center" style="alignment: center;margin:20px;">
        <button type="button" onclick="return sub()" class="saveButton" id="update"></button>
        <button type="button" onclick="return cancel()" class="cancelButton" id="cancelUpdate"></button>
    </div>
</form>
</body>

<script>
    var id = '${id}';

    //用户只能输入正负数与小数
    function upperCase(obj){
        if(isNaN(obj.value) && !/^-$/.test(obj.value)){
            obj.value="";
        }
        if(!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)){
            obj.value=obj.value.replace(/\.\d{2,}$/,obj.value.substr(obj.value.indexOf('.'),10));
        }
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



    //js计算乘法
    function accMul(arg1, arg2)
    {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    //JS计算除法
    function accDiv(arg1, arg2)
    {
        var t1 = 0, t2 = 0, r1, r2;
        try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
        try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""))
            r2 = Number(arg2.toString().replace(".", ""))
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }

    //提交
    function sub(){
        //校验
        // var planMonth = $("#planMonth").val();

        // if(!planMonth){
        //     top.Dialog.alert("所属月份不可为空");
        //     return;
        // }


        var rateSorceDetail = $("#form1").serialize();
        top.Dialog.confirm("确定要保存操作吗?|操作提示", function () {
            $('#update').attr('disabled', true);
            $('#cancelUpdate').attr('disabled', true);
            $.ajax({
                type: "POST",
                url: "<%=path%>/tbOrganRateScoreMonth/updateTbOrganRateScoreMonth.htm",
                data: {
                    "id": id,
                    "rateSorceDetail": rateSorceDetail
                },
                dataType: "json",
                success: function (result) {
                    if (result.success == true || result.success == "true") {
                        $('#update').attr('disabled', false);
                        $('#cancelUpdate').attr('disabled', false);
                        top.Dialog.alert("修改成功!", function () {
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
                        $('#update').attr('disabled', false);
                        $('#cancelUpdate').attr('disabled', false);
                        if (result.code == '408') {
                            top.Dialog.alert(result.message);
                        } else {
                            top.Dialog.alert("修改失败");
                        }
                    }
                },
                error: function (result) {
                    $('#update').attr('disabled', false);
                    $('#cancelUpdate').attr('disabled', false);
                    top.Dialog.alert("请求异常");
                }
            });
        });
    }




    $(function(){
        //冻结行列 行首 行末 列首 列末
        $("#plan").FrozenTable(2, 0, 1, 0);

        //输入框获取焦点事件
        $(".rateScore").focus(function(){
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".rateScore").blur(function(){
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });
    })

    //修改三处月末实际月增量保持一致
    function monthEndIncrementChange(obj) {
        if ($.trim(obj.value) == "") {
            var increMent = 0;
        }else{
            var increMent = obj.value;
        }
        var id=$(obj).attr('id');
        var ids = id.split("_");
        var id1 = ids[0] + "_monthEndIncrement";
        var id2 = ids[0] + "_monthEndIncrement2";
        var id3 = ids[0] + "_monthEndIncrement3";
        $("#"+id1).val(increMent);
        $("#"+id2).val(increMent);
        $("#"+id3).val(increMent);
    }

    // 求机构月度评分的总得分
    function getRateScoreMonth(obj) {
        if ($.trim(obj.value) == "") {
            var increMent = 0;
        }else{
            var increMent = obj.value;
        }

        var id=$(obj).attr('id');
        $("#" + id).val(increMent);

        var ids = id.split("_");
        var  rateScoreId = new Array();
         rateScoreId[0] = ids[0] + "_rateScoreMonth";
         rateScoreId[1] = ids[0] + "_badConditionScore";
         rateScoreId[2] = ids[0] + "_depositLoanRatioScore";
         rateScoreId[3] = ids[0] + "_newLoanRatioScore";
         rateScoreId[4] = ids[0] + "_loanStructScore";
         rateScoreId[5] = ids[0] + "_scaleAmountScore";
         rateScoreId[6] = ids[0] + "_planSubmitDeviationScore";
         rateScoreId[7] = ids[0] + "_planExecuteDeviationMonthMidScore";
         rateScoreId[8] = ids[0] + "_planExecuteDeviationMonthInitScore";
         rateScoreId[9] = ids[0] + "_adjCountScore";
         rateScoreId[10] = ids[0] + "_addScore";

        var rateScore = 0;
        for(var i = 1;i < 11;i++) {
            rateScore = accAdd(rateScore, $("#" + rateScoreId[i]).val());
        }
        $("#"+rateScoreId[0]).val(rateScore);
        $('span[name='+rateScoreId[0]+']').html(rateScore);

    }


</script>

</html>