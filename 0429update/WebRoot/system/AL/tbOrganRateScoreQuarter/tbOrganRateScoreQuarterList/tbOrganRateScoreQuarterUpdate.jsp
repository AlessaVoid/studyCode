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
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list">

            <tr>
                <td ><div style="width: 150px;"></div></td>
                <td ><div style="width: 100px;"> </div></td>
                <td > </td>
                <td > </td>

                <td colspan="4" align="center" >不良情况 </td>

                <td colspan="4" align="center" >自营新增存贷比 </td>

                <td colspan="4" align="center">新发生贷款利率 </td>

                <td colspan="4" align="center">贷款结构 </td>

                <td colspan="4" align="center">超规模占用费和规模闲置费 </td>

                <td colspan="4" align="center">计划报送偏离度 </td>

                <td colspan="4" align="center">月末月中计划执行偏离度 </td>

                <td colspan="4" align="center">月末月初计划执行偏离度 </td>

                <td colspan="4" align="center">调整频次 </td>

                <td colspan="2" align="center">未产生超规模占用费 </td>

                <td colspan="2" align="center">调整频次未扣分 </td>

                <td colspan="4" align="center">历史调整分数 </td>

                <td colspan="2" align="center">其他 </td>

            </tr>


            <tr>
                <td ><div style="width: 150px;">评分机构列表</div></td>
                <td ><div style="width: 100px;">月份 </div></td>
                <td >总分 </td>
                <td >季度评级 </td>

                <td >不良情况得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>


                <td >自营新增存贷比得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >新发生贷款利率得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >贷款结构得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >超规模占用费和规模闲置费得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >计划报送偏离度得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >月末月中计划执行偏离度得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >月末月初计划执行偏离度得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >调整频次得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>


                <td >未产生超规模占用费得分 </td>
                <td >未产生超规模占用费月份数 </td>

                <td >调整频次未扣分得分 </td>
                <td >调整频次未扣分月份数 </td>

                <td >历史调整分数得分 </td>
                <td >第一个月得分 </td>
                <td >第二个月得分 </td>
                <td >第三个月得分 </td>

                <td >调整分数 </td>
                <td >备注 </td>

            </tr>
            <c:forEach items="${organList}" var="organ">

                <tr>
                    <c:set var="rate_key" value="${organ.thiscode}"/>
                    <c:set var="rateScoreDetail" value="${rateScoreMap[rate_key]}"/>

                    <input name="${organ.thiscode}_rateRank"
                           value="${rateScoreDetail.rateRank}"
                           class="rateScore" type="hidden"
                           id="${organ.thiscode }_rateRank"
                    />


                    <input name="${organ.thiscode}_rateRatio"
                           value="${rateScoreDetail.rateRatio}"
                           class="rateScore" type="hidden"
                           id="${organ.thiscode }_rateRatio"
                    />

                    <td> <span style="width: 150px;">${organ.organname }</span></td>
                    <td>
                        <input name="${organ.thiscode}_rateMonth"
                               value="${rateScoreDetail.rateMonth}"
                               class="rateScore" type="hidden"
                               id="${organ.thiscode }_rateMonth"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.rateMonth}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_rateScoreQuarter"
                               value="${rateScoreDetail.rateScoreQuarter}"
                               class="rateScore" type="hidden"
                               id="${organ.thiscode }_rateScoreQuarter"
                               oninput='upperCase(this)'/>
                            <span name ="${organ.thiscode}_rateScoreQuarter"  > ${rateScoreDetail.rateScoreQuarter}</span>
                    </td>

                    <td>
                        <input name="${organ.thiscode}_rateLevel"
                               value="${rateScoreDetail.rateLevel}"
                               class="rateLevel" type="text"
                               id="${organ.thiscode }_rateLevel"
                        />


                    </td>

                        <%--不良情况得分 不良率 = 月末不良率 - 年初不良率--%>
                    <td>
                        <input name="${organ.thiscode}_badConditionScoreQuarter"
                               value="${rateScoreDetail.badConditionScoreQuarter}"
                               id="${organ.thiscode }_badConditionScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_badConditionScoreOne"
                               value="${rateScoreDetail.badConditionScoreOne}"
                               id="${organ.thiscode }_badConditionScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_badConditionScoreTwo"
                               value="${rateScoreDetail.badConditionScoreTwo}"
                               id="${organ.thiscode }_badConditionScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_badConditionScoreThree"
                               value="${rateScoreDetail.badConditionScoreThree}"
                               id="${organ.thiscode }_badConditionScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.badConditionScoreThree}
                    </td>



                        <%--自营新增存贷比得分 自营新增存贷比=各项贷款年增量 /（个人自营存款年增量 + 公司存款年增量）--%>

                    <td>
                        <input name="${organ.thiscode}_depositLoanRatioScoreQuarter"
                               value="${rateScoreDetail.depositLoanRatioScoreQuarter}"
                               id="${organ.thiscode }_depositLoanRatioScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.depositLoanRatioScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_depositLoanRatioScoreOne"
                               value="${rateScoreDetail.depositLoanRatioScoreOne}"
                               id="${organ.thiscode }_depositLoanRatioScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.depositLoanRatioScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_depositLoanRatioScoreTwo"
                               value="${rateScoreDetail.depositLoanRatioScoreTwo}"
                               id="${organ.thiscode }_depositLoanRatioScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.depositLoanRatioScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_depositLoanRatioScoreThree"
                               value="${rateScoreDetail.depositLoanRatioScoreThree}"
                               id="${organ.thiscode }_depositLoanRatioScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.depositLoanRatioScoreThree}
                    </td>



                        <%--新发生贷款利率得分  贷款利率差 = 新发生贷款利率 - 全行平均贷款利率--%>

                    <td>
                        <input name="${organ.thiscode}_newLoanRatioScoreQuarter"
                               value="${rateScoreDetail.newLoanRatioScoreQuarter}"
                               id="${organ.thiscode }_newLoanRatioScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.newLoanRatioScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_newLoanRatioScoreOne"
                               value="${rateScoreDetail.newLoanRatioScoreOne}"
                               id="${organ.thiscode }_newLoanRatioScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.newLoanRatioScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_newLoanRatioScoreTwo"
                               value="${rateScoreDetail.newLoanRatioScoreTwo}"
                               id="${organ.thiscode }_newLoanRatioScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.newLoanRatioScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_newLoanRatioScoreThree"
                               value="${rateScoreDetail.newLoanRatioScoreThree}"
                               id="${organ.thiscode }_newLoanRatioScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.newLoanRatioScoreThree}
                    </td>


                        <%--贷款结构得分 月末实体贷款余额占比 = 月末实体贷款余额/总贷款 - 月初实体贷款余额/总贷款--%>


                    <td>
                        <input name="${organ.thiscode}_loanStructScoreQuarter"
                               value="${rateScoreDetail.loanStructScoreQuarter}"
                               id="${organ.thiscode }_loanStructScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.loanStructScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_loanStructScoreOne"
                               value="${rateScoreDetail.loanStructScoreOne}"
                               id="${organ.thiscode }_loanStructScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.loanStructScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_loanStructScoreTwo"
                               value="${rateScoreDetail.loanStructScoreTwo}"
                               id="${organ.thiscode }_loanStructScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.loanStructScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_loanStructScoreThree"
                               value="${rateScoreDetail.loanStructScoreThree}"
                               id="${organ.thiscode }_loanStructScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.loanStructScoreThree}
                    </td>

                        <%--超规模占用费和规模闲置费得分  分行罚息占比 =  分行罚息金额 / 全行罚息金额--%>

                    <td>
                        <input name="${organ.thiscode}_scaleAmountScoreQuarter"
                               value="${rateScoreDetail.scaleAmountScoreQuarter}"
                               id="${organ.thiscode }_scaleAmountScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_scaleAmountScoreOne"
                               value="${rateScoreDetail.scaleAmountScoreOne}"
                               id="${organ.thiscode }_scaleAmountScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_scaleAmountScoreTwo"
                               value="${rateScoreDetail.scaleAmountScoreTwo}"
                               id="${organ.thiscode }_scaleAmountScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_scaleAmountScoreThree"
                               value="${rateScoreDetail.scaleAmountScoreThree}"
                               id="${organ.thiscode }_scaleAmountScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountScoreThree}
                    </td>

                        <%--计划报送偏离度得分  计划报送偏离幅度 = （月末实际月增量 - 月初报送需求） / 月末实际月增量--%>

                    <td>
                        <input name="${organ.thiscode}_planSubmitDeviationScoreQuarter"
                               value="${rateScoreDetail.planSubmitDeviationScoreQuarter}"
                               id="${organ.thiscode }_planSubmitDeviationScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planSubmitDeviationScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planSubmitDeviationScoreOne"
                               value="${rateScoreDetail.planSubmitDeviationScoreOne}"
                               id="${organ.thiscode }_planSubmitDeviationScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planSubmitDeviationScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planSubmitDeviationScoreTwo"
                               value="${rateScoreDetail.planSubmitDeviationScoreTwo}"
                               id="${organ.thiscode }_planSubmitDeviationScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planSubmitDeviationScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planSubmitDeviationScoreThree"
                               value="${rateScoreDetail.planSubmitDeviationScoreThree}"
                               id="${organ.thiscode }_planSubmitDeviationScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planSubmitDeviationScoreThree}
                    </td>

                        <%--计划执行偏离度--月末月中偏离度得分   月末月中偏离幅度=（月末实际月增量-月中统一动态调整后计划）/月末统一动态调整后计划--%>

                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthMidScoreQuarter"
                               value="${rateScoreDetail.planExecuteDeviationMonthMidScoreQuarter}"
                               id="${organ.thiscode }_planExecuteDeviationMonthMidScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthMidScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthMidScoreOne"
                               value="${rateScoreDetail.planExecuteDeviationMonthMidScoreOne}"
                               id="${organ.thiscode }_planExecuteDeviationMonthMidScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthMidScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthMidScoreTwo"
                               value="${rateScoreDetail.planExecuteDeviationMonthMidScoreTwo}"
                               id="${organ.thiscode }_planExecuteDeviationMonthMidScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthMidScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthMidScoreThree"
                               value="${rateScoreDetail.planExecuteDeviationMonthMidScoreThree}"
                               id="${organ.thiscode }_planExecuteDeviationMonthMidScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthMidScoreThree}
                    </td>


                        <%--计划执行偏离度--月末月初偏离度得分 月末月初偏离幅度=（月末实际月增量-月初总行下达计划）/月初总行下达计划--%>

                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthInitScoreQuarter"
                               value="${rateScoreDetail.planExecuteDeviationMonthInitScoreQuarter}"
                               id="${organ.thiscode }_planExecuteDeviationMonthInitScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthInitScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthInitScoreOne"
                               value="${rateScoreDetail.planExecuteDeviationMonthInitScoreOne}"
                               id="${organ.thiscode }_planExecuteDeviationMonthInitScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthInitScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthInitScoreTwo"
                               value="${rateScoreDetail.planExecuteDeviationMonthInitScoreTwo}"
                               id="${organ.thiscode }_planExecuteDeviationMonthInitScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthInitScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_planExecuteDeviationMonthInitScoreThree"
                               value="${rateScoreDetail.planExecuteDeviationMonthInitScoreThree}"
                               id="${organ.thiscode }_planExecuteDeviationMonthInitScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.planExecuteDeviationMonthInitScoreThree}
                    </td>


                        <%--调整频次得分--%>

                    <td>
                        <input name="${organ.thiscode}_adjCountScoreQuarter"
                               value="${rateScoreDetail.adjCountScoreQuarter}"
                               id="${organ.thiscode }_adjCountScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCountScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_adjCountScoreOne"
                               value="${rateScoreDetail.adjCountScoreOne}"
                               id="${organ.thiscode }_adjCountScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCountScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_adjCountScoreTwo"
                               value="${rateScoreDetail.adjCountScoreTwo}"
                               id="${organ.thiscode }_adjCountScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCountScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_adjCountScoreThree"
                               value="${rateScoreDetail.adjCountScoreThree}"
                               id="${organ.thiscode }_adjCountScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCountScoreThree}
                    </td>

                        <%--未产生超规模占用费--%>
                    <td>
                        <input name="${organ.thiscode}_scaleAmountMonthCountScoreQuarter"
                               value="${rateScoreDetail.scaleAmountMonthCountScoreQuarter}"
                               id="${organ.thiscode }_scaleAmountMonthCountScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountMonthCountScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_scaleAmountMonthCount"
                               value="${rateScoreDetail.scaleAmountMonthCount}"
                               id="${organ.thiscode }_scaleAmountMonthCount"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.scaleAmountMonthCount}
                    </td>

                        <%--调整频次未扣分--%>
                    <td>
                        <input name="${organ.thiscode}_adjCountMonthCountScoreQuarter"
                               value="${rateScoreDetail.adjCountMonthCountScoreQuarter}"
                               id="${organ.thiscode }_adjCountMonthCountScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCountMonthCountScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_adjCountMonthCount"
                               value="${rateScoreDetail.adjCountMonthCount}"
                               id="${organ.thiscode }_adjCountMonthCount"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.adjCountMonthCount}
                    </td>


                    <%--历史加分项--%>

                    <td>
                        <input name="${organ.thiscode}_addScoreQuarter"
                               value="${rateScoreDetail.addScoreQuarter}"
                               id="${organ.thiscode }_addScoreQuarter"
                               class="rateScore" type="hidden"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.addScoreQuarter}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_addScoreOne"
                               value="${rateScoreDetail.addScoreOne}"
                               id="${organ.thiscode }_addScoreOne"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.addScoreOne}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_addScoreTwo"
                               value="${rateScoreDetail.addScoreTwo}"
                               id="${organ.thiscode }_addScoreTwo"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.addScoreTwo}
                    </td>
                    <td>
                        <input name="${organ.thiscode}_addScoreThree"
                               value="${rateScoreDetail.addScoreThree}"
                               id="${organ.thiscode }_addScoreThree"
                               class="rateScore" type="hidden"
                               oninput='upperCase(this)'/>
                            ${rateScoreDetail.addScoreThree}
                    </td>

                        <%--加分项--%>
                    <td>
                        <input name="${organ.thiscode}_addScore"
                               value="${rateScoreDetail.addScore}"
                               id="${organ.thiscode }_addScore"
                               class="rateScore" type="text"
                               onchange="getRateScoreQuarter(this)"
                               oninput='upperCase(this)'/>
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
        <button type="button" onclick="return sub()" class="saveButton " id="update"></button>
        <button type="button" onclick="return cancel()" class="cancelButton " id="cancelUpdate"></button>
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
                url: "<%=path%>/tbOrganRateScoreQuarter/updateTbOrganRateScoreMonth.htm",
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

        //初始化评分等级

        var rowList = $(".rateLevel");
        $(rowList).each(function(){
            var value = this.value;
            value = $.trim(value);
            if(value == 1 || 'a' == value || 'A' == value){
                value = 'A';
            }else if(value == 2 || 'b' == value || 'B' == value){
                value = 'B';
            }else if(value == 3 || 'c' == value || 'C' == value){
                value = 'C';
            }else if(value == 4 || 'd' == value || 'D' == value){
                value = 'D';
            }

            $(this).val(value);
        });

        //修改评分
        $(".rateLevel").blur(function(){
            var value = $.trim(this.value);
            if(value == 1 || 'a' == value || 'A' == value){
                value = 'A';
            }else if(value == 2 || 'b' == value || 'B' == value){
                value = 'B';
            }else if(value == 3 || 'c' == value || 'C' == value){
                value = 'C';
            }else if(value == 4 || 'd' == value || 'D' == value){
                value = 'D';
            }else{
                value = 'D';
            }

            $(this).val(value);

        });


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


    // 求机构月度评分的总得分
    function getRateScoreQuarter(obj) {
        if ($.trim(obj.value) == "") {
            var increMent = 0;
        }else{
            var increMent = obj.value;
        }



        var id=$(obj).attr('id');
        $("#" + id).val(increMent);

        var ids = id.split("_");
        var  rateScoreId = new Array();
        rateScoreId[0] = ids[0] + "_rateScoreQuarter";
        rateScoreId[1] = ids[0] + "_badConditionScoreQuarter";
        rateScoreId[2] = ids[0] + "_depositLoanRatioScoreQuarter";
        rateScoreId[3] = ids[0] + "_newLoanRatioScoreQuarter";
        rateScoreId[4] = ids[0] + "_loanStructScoreQuarter";
        rateScoreId[5] = ids[0] + "_scaleAmountScoreQuarter";
        rateScoreId[6] = ids[0] + "_planSubmitDeviationScoreQuarter";
        rateScoreId[7] = ids[0] + "_planExecuteDeviationMonthMidScoreQuarter";
        rateScoreId[8] = ids[0] + "_planExecuteDeviationMonthInitScoreQuarter";
        rateScoreId[9] = ids[0] + "_adjCountScoreQuarter";
        rateScoreId[10] = ids[0] + "_scaleAmountMonthCountScoreQuarter";
        rateScoreId[11] = ids[0] + "_adjCountMonthCountScoreQuarter";
        rateScoreId[12] = ids[0] + "_addScoreQuarter";
        rateScoreId[13] = ids[0] + "_addScore";

        var rateScore = 0;
        for(var i = 1;i < 14;i++) {
            rateScore = accAdd(rateScore, $("#" + rateScoreId[i]).val());
        }
        $("#"+rateScoreId[0]).val(rateScore);
        $('span[name='+rateScoreId[0]+']').html(rateScore);

    }

</script>

</html>