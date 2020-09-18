<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
    <!-- 树组件end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
    <title></title>
</head>
<script type="text/javascript">

    var paramPeriod ="${TbTradeParam.paramPeriod}";
    function submitInfo() {
        if ($("#paramMode").val()==""||$("#paramMode").val()=="请选择") {
            top.Dialog.alert("请选择计划分配模式");
            return
        }

        if ($("#paramPunishStart").val() =="") {
            top.Dialog.alert("请选择罚息开始时间");
            return
        }
        if ($("#paramPunsihEnd").val()  =="") {
            top.Dialog.alert("请选择罚息结束时间");
            return
        }


        if ($("#paramPunishStart").val()  !="") {
            if ($("#paramPunishStart").val() - $("#paramPunsihEnd").val() > 0) {
                top.Dialog.alert("罚息开始日期应小于等于罚息结束日期");
                return
            }
        }

        if ($("#paramMechIncrement").val()  =="") {
            top.Dialog.alert("请填写当月计划净增量");
            return
        }
        // if ($("#paramLineIncrement").val()  =="") {
        //     top.Dialog.alert("请填写当月条线计划净增量");
        //     return
        // }
        if ($("#paramTempMount").val()  =="") {
            top.Dialog.alert("请填写当月临时额度审批标准");
            return
        }
        if ($("#paramTempMount").val()  <0) {
            top.Dialog.alert("临时额度审批标准要求非负");
            return
        }
        if ($("#paramSingleMount").val()  =="") {
            top.Dialog.alert("请填写当月单笔专项审批标准");
            return
        }
        if ($("#paramSingleMount").val()  <0) {
            top.Dialog.alert("单笔专项审批标准要求非负");
            return
        }
        if ($("#paramOverMount").val() =="") {
            top.Dialog.alert("请填写当月超限额审批标准");
            return
        }
        if ($("#paramOverMount").val()  <0) {
            top.Dialog.alert("超限额审批标准要求非负");
            return
        }
        if ($("#paramIsLine").val() =="") {
            top.Dialog.alert("请选择是否条线管控");
            return
        }
        return doSubmit('form1', '<%=path%>/tbTradeManger/tbTradeParam/update.htm');
    }
    //用户只能输入正负数与小数
    function upperCase(obj) {
        // obj.value = obj.value.replace(/[^\d.]/g,"");
        // //必须保证第一个为数字而不是.
        // obj.value = obj.value.replace(/^\./g,"");
        // //保证只有出现一个.而没有多个.
        // obj.value = obj.value.replace(/\.{2,}/g,".");
        //保证.只出现一次，而不能出现两次以上
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }
    }

    var minDateStr="";
    var maxDateStr="";
    function initComplete() {

        // var paramPeriod = $("#paramPeriod").val();
        var newDate = new Date().getFullYear() + "" + (new Date().getMonth() + 1);
        var year =parseInt(paramPeriod.slice(0,4));
        var month =parseInt(paramPeriod.slice(4,6));
        var days = getDaysOfMonth(year,month);
        maxDateStr=year + "-" + month+"-"+days;
        minDateStr=year + "-" + month+"-01";
    }
    function getDaysOfMonth(year, month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 29 : 28;
            default:
                return 0;
        }
    };

</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属周期：
            </td>
            <td><input type="hidden" name="paramId" value="${TbTradeParam.paramId}"/>
                ${TbTradeParam.paramPeriod}
            </td>
            <td align="right">
                计划分配模式：
            </td>
            <td>
                <dic:select id="paramMode" dicType="PLAN_DISTRIBUTION_MODE" dicNo="${TbTradeParam.paramMode}"
                            name="paramMode" tgClass="validate[required]"
                            required="true"></dic:select>
                <span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td align="right">
                罚息开始时间：
            </td>
            <td>
                <input type="text" id="paramPunishStart" name="paramPunishStart"  AUTOCOMPLETE="off"
                       value="${TbTradeParam.paramPunishStart}" class="validate[required] date"
                       dateFmt="yyyyMMdd" onfocus="WdatePicker({skin:themeColor,minDate:minDateStr,maxDate:maxDateStr})"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                罚息结束时间：
            </td>
            <td>
                <input type="text" id="paramPunsihEnd" name="paramPunsihEnd" value="${TbTradeParam.paramPunsihEnd}"  AUTOCOMPLETE="off"
                       class="validate[required] date" dateFmt="yyyyMMdd"  onfocus="WdatePicker({skin:themeColor,minDate:minDateStr,maxDate:maxDateStr})"/>
                <span class="star">*</span>
            </td>
        </tr>


        <tr>
            <td align="right" colspan="1">
                当月临时额度审批标准：
            </td>
            <td colspan="1">
                <input type="text" id="paramTempMount" name="paramTempMount"  class="validate[required,custom[onlyNumberWide]]"
                       onkeyup='upperCase(this)'  value="${TbTradeParam.paramTempMount}" AUTOCOMPLETE="off"
                       maxlength="16" required="true" >
                <span class="star">亿元*</span>
            </td>
            <td align="right" colspan="1">
                当月单笔专项审批标准：
            </td>
            <td colspan="1">
                <input type="text" id="paramSingleMount" name="paramSingleMount"  class="validate[required,custom[onlyNumberWide]]"
                       onkeyup='upperCase(this)'  value="${TbTradeParam.paramSingleMount}" AUTOCOMPLETE="off"
                       maxlength="16" required="true" >
                <span class="star">亿元*</span>
            </td>

        </tr>
        <tr>
            <td align="right" colspan="1">
                当月超限额审批标准：
            </td>
            <td colspan="1">
                <input type="text" id="paramOverMount" name="paramOverMount"  class="validate[required,custom[onlyNumberWide]]"
                       onkeyup='upperCase(this)'  value="${TbTradeParam.paramOverMount}" AUTOCOMPLETE="off"
                       maxlength="16" required="true" >
                <span class="star">亿元*</span>
            </td>

            <td align="right">
                是否条线管控：
            </td>
            <td>
                <dic:select id="paramIsLine" dicType="PARAM_IS_LINE" name="paramIsLine" dicNo="${TbTradeParam.paramIsLine}"
                            tgClass="validate[required]" required="true"></dic:select>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right" colspan="1">
                当月机构计划净增量：
            </td>
            <td colspan="1">
                <input type="text" id="paramMechIncrement" name="paramMechIncrement"  class="validate[required,custom[onlyNumberWide]]"
                       onkeyup='upperCase(this)'  value="${TbTradeParam.paramMechIncrement}" AUTOCOMPLETE="off"
                       maxlength="16" required="true" >
                <span class="star">亿元*</span>
            </td>
            <%--<td align="right" colspan="1">--%>
            <%--当月条线计划净增量：--%>
            <%--</td>--%>
            <%--<td colspan="1">--%>
            <%--<input type="text" id="paramLineIncrement" name="paramLineIncrement"  class="validate[required,custom[onlyNumberWide]]"--%>
            <%--onkeyup='upperCase(this)'  value="${TbTradeParam.paramLineIncrement}" AUTOCOMPLETE="off"--%>
            <%--maxlength="16" required="true" >--%>
            <%--<span class="star">亿元*</span>--%>
            <%--</td>--%>

        </tr>

            <td colspan="4">
                <div align="center">
                    <button type="button" onclick="submitInfo()" class="saveButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>