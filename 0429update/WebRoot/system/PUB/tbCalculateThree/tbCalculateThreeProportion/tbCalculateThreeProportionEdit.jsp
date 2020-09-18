<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
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
    <%--table冻结行列start--%>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <%--table冻结行列end--%>
    <title></title>

</head>
<script type="text/javascript">

    //用户只能输入正负数与小数
    function upperCase(obj, code) {
        if (code == "reqNum") {
        } else {
            obj.value = obj.value.replace(/[^\d.]/g, "");
            //必须保证第一个为数字而不是.
            obj.value = obj.value.replace(/^\./g, "");
            //保证只有出现一个.而没有多个.
            obj.value = obj.value.replace(/\.{2,}/g, ".");
        }
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 5));
        }
    }

    $(function () {

        $.ajax({
            type: "POST",
            url: "<%=path%>/tbCalculateThreeProportion/getDetailList.htm",
            data: {},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbReqDetailList = result.tbReqDetailList;
                    for (var i = 0; i < tbReqDetailList.length; i++) {
                        var tbReqDetail = tbReqDetailList[i];
                        var code = tbReqDetail.code;
                        var orderNum = tbReqDetail.orderNum;
                        var proportion = tbReqDetail.proportion;
                        var commonProportion = tbReqDetail.commonProportion;

                        $("#commonProportion").val(commonProportion);
                        $("#" + code + "_order").val(orderNum);
                        $("#" + code + "_proportion").val(proportion);
                    }

                }
            }
        });


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
        $("#plan").FrozenTable(1, 0, 1, 0);
    })


    function sub() {

        var commonProportion = $("#commonProportion").val();

        if (commonProportion == 0) {
            top.Dialog.alert("默认比例必须为非0数值", null, null, null, 5);
            return
        }
        if (-1000 > commonProportion) {
            top.Dialog.alert("默认比例下限为-1000", null, null, null, 5);
            return
        }
        if ( commonProportion >1000) {
            top.Dialog.alert("默认比例上限为1000", null, null, null, 5);
            return
        }


        return doSubmit('form1', '<%=path%>/tbCalculateThreeProportion/update.htm');
    }

</script>


<body>


<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table id="plan" class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <td trueWidth="150">贷种名称</td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td align="center">
                        <input type="hidden" class="planamonut_comb" id="${combAmountName.code}"/>
                            ${combAmountName.name}
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <input type="hidden" class="planamonut_organ" id="${comb.combCode}"/>
                    <td> ${comb.combName}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${comb.combCode}_${combAmountName.code}" AUTOCOMPLETE="off"
                                   class="planamonut ${comb.combCode } ${combAmountName.code}"
                                   onkeyup='upperCase(this,"${combAmountName.code}")' value="0" maxlength="16"
                                   type="text" id="${comb.combCode}_${combAmountName.code}"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            <tr>
                <td> 默认比例</td>
                <td>
                    <input type="text" id="commonProportion" name="commonProportion"
                           onkeyup='upperCase(this,"${combAmountName.code}")' value="0" maxlength="30"/>
                    <span>%</span>
                </td>
                </td>
            </tr>
        </table>
    </div>
    <div align="center">
        <button type="button" onclick="sub()" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</form>

</body>

</html>