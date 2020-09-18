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
    <title></title>
</head>
<script type="text/javascript">

    function submitInfo() {
        top.Dialog.confirm("确定要提交新增吗？", function () {

            var reqTimeStart = $("#reqTimeStart").val();
            var reqTimeEnd = $("#reqTimeEnd").val();
            var expTimeStart = $("#expTimeStart").val();
            var expTimeEnd = $("#expTimeEnd").val();
            var rateTimeStart = $("#rateTimeStart").val();
            var rateTimeEnd = $("#rateTimeEnd").val();
            var balanceTimeStart = $("#balanceTimeStart").val();
            var balanceTimeEnd = $("#balanceTimeEnd").val();

            var reqDateStart = $("#reqDateStart").val();
            var reqDateEnd = $("#reqDateEnd").val();
            var reqType = $("#reqType").val();
            var reqNote = $("#reqNote").val();
            var reqName = $("#reqName").val();
            var reqOrganListStr = $("#reqOrganList").attr("relValue");
            var reqCombListStr = $("#reqCombList").attr("relValue");
            var reqProdLineStr = $("#reqProdLine").attr("relValue");
            var numTypeStr = $("#numType").attr("relValue");
            var reqMonth = $("#reqMonth").val();
            var reqUnit = $("#reqUnit").val();
            if (reqType == '') {
                top.Dialog.alert("请选择下发对象类型", null, null, null, 5);
                return;
            }
            if (reqType == 0) {
                if (reqOrganListStr.length < 2) {
                    top.Dialog.alert("请选择下发机构", null, null, null, 5);
                    return
                }
                if (reqCombListStr.length < 2) {
                    top.Dialog.alert("请选择贷种组合", null, null, null, 5);
                    return
                }

            } else if (reqType == 1) {
                if (reqProdLineStr.length < 2) {
                    top.Dialog.alert("请选择下发条线", null, null, null, 5);
                    return
                }
            }

            if (numTypeStr.length < 1) {
                top.Dialog.alert("请选择下发填写数据项", null, null, null, 5);
                return
            }
            if (reqMonth == "") {
                reqMonth = "";
            }

            if (reqName == "") {
                top.Dialog.alert("请输入需求名称", null, null, null, 5);
                return
            }

            if (numTypeStr.indexOf("1") != -1) {
                if (expTimeStart == "") {
                    top.Dialog.alert("请选择到期量周期起始时间", null, null, null, 5);
                    return
                }
                if (expTimeEnd == "") {
                    top.Dialog.alert("请选择到期量周期结束时间", null, null, null, 5);
                    return
                }
            }
            if (numTypeStr.indexOf("2") != -1) {
                if (reqTimeStart == "") {
                    top.Dialog.alert("请选择净增量周期起始时间", null, null, null, 5);
                    return
                }
                if (reqTimeEnd == "") {
                    top.Dialog.alert("请选择净增量周期结束时间", null, null, null, 5);
                    return
                }
            }
            if (numTypeStr.indexOf("4") != -1) {

                if (rateTimeStart == "") {
                    top.Dialog.alert("请选择利率周期起始时间", null, null, null, 5);
                    return
                }
                if (rateTimeEnd == "") {
                    top.Dialog.alert("请选择利率周期结束时间", null, null, null, 5);
                    return
                }
            }
            if (numTypeStr.indexOf("8") != -1) {
                if (balanceTimeStart == "") {
                    top.Dialog.alert("请选择余额周期起始时间", null, null, null, 5);
                    return
                }
                if (balanceTimeEnd == "") {
                    top.Dialog.alert("请选择余额周期结束时间", null, null, null, 5);
                    return
                }
            }
            if (reqDateStart == "") {
                top.Dialog.alert("请选择录入起始时间", null, null, null, 5);
                return
            }
            if (reqDateEnd == "") {
                top.Dialog.alert("请选择录入结束时间", null, null, null, 5);
                return
            }

            if (reqDateStart != "" && reqDateEnd != "") {
                if (reqDateStart - reqDateEnd > 0) {
                    top.Dialog.alert("需求录入开始日期应小于等于需求录入结束日期", null, null, null, 5);
                    return
                }
            }
            if (reqTimeStart != "" && reqTimeEnd != "") {
                if (reqTimeStart - reqTimeEnd > 0) {
                    top.Dialog.alert("净增量周期开始日期应小于等于周期结束日期", null, null, null, 5);
                    return
                }
            }
            if (expTimeStart != "" && expTimeEnd != "") {
                if (expTimeStart - expTimeEnd > 0) {
                    top.Dialog.alert("到期量周期开始日期应小于等于周期结束日期", null, null, null, 5);
                    return
                }
            }

            if (rateTimeStart != "" && rateTimeEnd != "") {
                if (rateTimeStart - rateTimeEnd > 0) {
                    top.Dialog.alert("利率周期开始日期应小于等于周期结束日期", null, null, null, 5);
                    return
                }
            }

            if (balanceTimeStart != "" && balanceTimeEnd != "") {
                if (balanceTimeStart - balanceTimeEnd > 0) {
                    top.Dialog.alert("余额周期开始日期应小于等于周期结束日期", null, null, null, 5);
                    return
                }
            }


            if (reqDateEnd - reqTimeEnd > 0) {
                top.Dialog.alert("需求录入结束日期应小于等于净增量周期结束日期", null, null, null, 5);
                return
            }

            if (reqNote == "") {
                top.Dialog.alert("请完善录入说明", null, null, null, 5);
                return
            }
            if (reqUnit == "" || reqUnit == "请选择") {
                top.Dialog.alert("请选择单位", null, null, null, 5);
                return
            }

            $.post('<%=path%>/tbTradeManger/tbReqList/insert.htm',
                {
                    'reqMonth': reqMonth,
                    'reqType': reqType,
                    'reqDateStart': reqDateStart,
                    'reqDateEnd': reqDateEnd,
                    'reqTimeStart': reqTimeStart,
                    'reqTimeEnd': reqTimeEnd,
                    'expTimeStart': expTimeStart,
                    'expTimeEnd': expTimeEnd,
                    'rateTimeStart': rateTimeStart,
                    'rateTimeEnd': rateTimeEnd,
                    'balanceTimeStart': balanceTimeStart,
                    'balanceTimeEnd': balanceTimeEnd,
                    'reqNote': reqNote,
                    'reqName': reqName,
                    'reqCombListStr': reqCombListStr,
                    'reqOrganListStr': reqOrganListStr,
                    'reqProdLineStr': reqProdLineStr,
                    'reqUnit': reqUnit,
                    'numTypeStr': numTypeStr
                },
                function (result) {
                    if (result.success == "true" || result.success == true) {
                        top.Dialog.alert(result.msg, function () {
                            var menu_id = parent.getCurrentTabId();
                            if (menu_id == undefined) {
                                top.Dialog.close();
                                return;
                            }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                    } else {
                        $(".money").each(function () {
                            fmoney(this);
                        });
                        top.Dialog.alert(result.msg);
                    }
                }, "json");
        });

    }

    //comb
    var setting = {
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
        }

    };
    //organ line
    var setting_copy = {
        check: {
            enable: true,
            chkboxType: {"Y": "s", "N": "s"}
        }

    };


    function transDate() {
        var reqMonth = $("#reqMonth").val();
        if ("请选择" == reqMonth) {
            $("#reqTimeStart").val("");
            $("#reqTimeEnd").val("");
            $('#reqTimeStart').removeAttr("disabled");
            $('#reqTimeEnd').removeAttr("disabled");

            $("#expTimeStart").val("");
            $("#expTimeEnd").val("");
            $('#expTimeStart').removeAttr("disabled");
            $('#expTimeEnd').removeAttr("disabled");

            $("#rateTimeStart").val("");
            $("#rateTimeEnd").val("");
            $('#rateTimeStart').removeAttr("disabled");
            $('#rateTimeEnd').removeAttr("disabled");

            $("#balanceTimeStart").val("");
            $("#balanceTimeEnd").val("");
            $('#balanceTimeStart').removeAttr("disabled");
            $('#balanceTimeEnd').removeAttr("disabled");
        } else {
            var year = parseInt(reqMonth.slice(0, 4));
            var month = parseInt(reqMonth.slice(4, 6));
            var days = getDaysOfMonth(year, month);
            $("#reqTimeStart").val(reqMonth + "01");
            $("#reqTimeEnd").val(reqMonth + "" + days);
            $('#reqTimeStart').attr("disabled", "disabled");
            $('#reqTimeEnd').attr("disabled", "disabled");

            $("#expTimeStart").val(reqMonth + "01");
            $("#expTimeEnd").val(reqMonth + "" + days);
            $('#expTimeStart').attr("disabled", "disabled");
            $('#expTimeEnd').attr("disabled", "disabled");

            $("#rateTimeStart").val(reqMonth + "01");
            $("#rateTimeEnd").val(reqMonth + "" + days);
            $('#rateTimeStart').attr("disabled", "disabled");
            $('#rateTimeEnd').attr("disabled", "disabled");

            $("#balanceTimeStart").val(reqMonth + "01");
            $("#balanceTimeEnd").val(reqMonth + "" + days);
            $('#balanceTimeStart').attr("disabled", "disabled");
            $('#balanceTimeEnd').attr("disabled", "disabled");

        }
    }

    function trandDateTime() {
        var reqMonth = $("#reqMonth").val();
        var reqTimeStart = $("#reqTimeStart").val();
        var reqTimeEnd = $("#reqTimeEnd").val();
        var expTimeStart = $("#expTimeStart").val();
        var expTimeEnd = $("#expTimeEnd").val();
        var rateTimeStart = $("#rateTimeStart").val();
        var rateTimeEnd = $("#rateTimeEnd").val();
        var balanceTimeStart = $("#balanceTimeStart").val();
        var balanceTimeEnd = $("#balanceTimeEnd").val();

        if (("请选择" == reqMonth || "" == reqMonth) && ("" != reqTimeEnd || "" != reqTimeStart || "" != expTimeEnd || "" != expTimeStart || "" != rateTimeEnd || "" != rateTimeStart || "" != balanceTimeEnd || "" != balanceTimeStart)) {
            document.getElementById("reqMonth").disabled = true;
            $("#reqMonth").render();
        } else if ("" == reqTimeEnd && "" == reqTimeStart && "" == expTimeEnd && "" == expTimeStart && "" == rateTimeEnd && "" == rateTimeStart && "" == balanceTimeEnd && "" == balanceTimeStart) {
            document.getElementById("reqMonth").disabled = false;
            $.post("<%=path%>/tbTradeManger/tbTradeParam/findTradeParam.htm",
                {}, function (result) {
                    //赋给data
                    $("#reqMonth").data("data", result);
                    //刷新下拉框
                    $("#reqMonth").render();
                }, "json");
        }
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

    function check() {
        var reqType = $("#reqType").val();
        if (reqType == 0) {//机构
            document.getElementById("reqProdLine").style.display = "none";
            $("#reqProdLine").selectTreeRender(setting_copy);
            document.getElementById("tr_1").style.display = "";
            document.getElementById("tr_2").style.display = "none";
            document.getElementById("reqCombList").style.display = "";
            document.getElementById("reqOrganList").style.display = "";
        } else if (reqType == 1) {//条线
            document.getElementById("reqCombList").style.display = "none";
            document.getElementById("reqOrganList").style.display = "none";
            $("#reqCombList").selectTreeRender(setting);
            $("#reqOrganList").selectTreeRender(setting_copy);
            document.getElementById("tr_1").style.display = "none";
            document.getElementById("tr_2").style.display = "";
            document.getElementById("reqProdLine").style.display = "";
        }
    }


    function initComplete() {
        //获取json数据
        $.post("<%=path%>/tbTradeManger/tbTradeParam/findTradeParam.htm",
            {}, function (result) {
                //赋给data
                $("#reqMonth").data("data", result);
                //刷新下拉框
                $("#reqMonth").render();
            }, "json");
        $("#reqMonth").setValue("请选择");
        $("#reqCombList").selectTreeRender(setting);
        $("#reqOrganList").selectTreeRender(setting_copy);
        $("#reqProdLine").selectTreeRender(setting_copy);
        $("#numType").selectTreeRender(setting_copy);
        check();
    }


</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                <input type="text" id="reqName" name="reqName" maxlength="30"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                单位：
            </td>
            <td>
                <dic:select id="reqUnit" dicType="CURRENCY_UNIT" name="reqUnit"
                            tgClass="validate[required]" required="true"></dic:select>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <select id="reqMonth" name="reqMonth" onchange="transDate()"></select>
            </td>

            <td align="left">下发对象类型：</td>
            <td>
                <dic:select id="reqType" dicType="TB_REQ_TYPE" name="reqType" dicNo="0"
                            tgClass="validate[required]" onchange="check();"
                            required="true"></dic:select>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right">
                净增量周期开始时间：
            </td>
            <td>
                <input type="text" id="reqTimeStart" name="reqTimeStart" class="validate[required] date"
                       AUTOCOMPLETE="off" onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                净增量周期结束时间：
            </td>
            <td>
                <input type="text" id="reqTimeEnd" name="reqTimeEnd" class="validate[required] date" AUTOCOMPLETE="off"
                       onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>
        </tr>


        <tr>
            <td align="right">
                到期量周期开始时间：
            </td>
            <td>
                <input type="text" id="expTimeStart" name="expTimeStart" class="validate[required] date"
                       AUTOCOMPLETE="off" onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                到期量周期结束时间：
            </td>
            <td>
                <input type="text" id="expTimeEnd" name="expTimeEnd" class="validate[required] date" AUTOCOMPLETE="off"
                       onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>
        </tr>


        <tr>
            <td align="right">
                利率周期开始时间：
            </td>
            <td>
                <input type="text" id="rateTimeStart" name="rateTimeStart" class="validate[required] date"
                       AUTOCOMPLETE="off" onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                利率周期结束时间：
            </td>
            <td>
                <input type="text" id="rateTimeEnd" name="rateTimeEnd" class="validate[required] date"
                       AUTOCOMPLETE="off"
                       onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>
        </tr>


        <tr>
            <td align="right">
                余额周期开始时间：
            </td>
            <td>
                <input type="text" id="balanceTimeStart" name="balanceTimeStart" class="validate[required] date"
                       AUTOCOMPLETE="off" onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                余额周期结束时间：
            </td>
            <td>
                <input type="text" id="balanceTimeEnd" name="balanceTimeEnd" class="validate[required] date"
                       AUTOCOMPLETE="off"
                       onchange="trandDateTime()"
                       dateFmt="yyyyMMdd"/>
                <span class="star">*</span>
            </td>
        </tr>


        <tr>
            <td align="right">
                需求录入开始时间：
            </td>
            <td>
                <input type="text" id="reqDateStart" name="reqDateStart" class="validate[required] date"
                       AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd" onfocus="WdatePicker({skin:themeColor,minDate:'%y-%M-%d'})"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                需求录入结束时间：
            </td>
            <td>
                <input type="text" id="reqDateEnd" name="reqDateEnd" class="validate[required] date" AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd" onfocus="WdatePicker({skin:themeColor,minDate:'%y-%M-%d'})"/>
                <span class="star">*</span>
            </td>
        </tr>


        <tr id="tr_1">
            <td>
                选择下发贷种组合:
            </td>

            <td>
                <div id="reqCombList" url="<%=path%>/tbTradeManger/tbReqList/selectComb.htm"
                     multiMode="true" allSelectable="true" exceptParent="false"></div>
                <span class="star">*</span>
            </td>

            <td>
                选择下发机构:
            </td>

            <td>

                <div id="reqOrganList"
                     url="<%=path%>/tbTradeManger/tbReqList/selectOrganList.htm" multiMode="true"
                     allSelectable="true" exceptParent="true"></div>

                <span class="star">*</span>.
            </td>
        </tr>

        <tr id="tr_2">
            <td>
                选择下发条线:
            </td>

            <td>

                <div id="reqProdLine"
                     url="<%=path%>/tbTradeManger/tbReqList/selectLineList.htm" multiMode="true"
                     allSelectable="true" exceptParent="true"></div>

                <span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td>
                选择下发填写项:
            </td>
            <td>
                <div id="numType"
                     url="<%=path%>/tbTradeManger/tbReqList/selectNumType.htm" multiMode="true"
                     allSelectable="true" exceptParent="true"></div>

                <span class="star">*</span>
            </td>

        </tr>
        <tr>
            <td colspan="1">
                录入说明:
            </td>
            <td colspan="3">
                <textarea id="reqNote" name="reqNote" AUTOCOMPLETE="off" style="width:90%;"></textarea>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td colspan="8">
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