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
        var reqresetTimeStart = $("#reqresetTimeStart").val();
        var reqresetTimeEnd = $("#reqresetTimeEnd").val();
        var reqresetDateStart = $("#reqresetDateStart").val();
        var reqresetDateEnd = $("#reqresetDateEnd").val();
        var reqresetType = $("#reqresetType").val();
        var reqresetNote = $("#reqresetNote").val();
        var reqresetName = $("#reqresetName").val();
        var reqresetOrganListStr = $("#reqresetOrganList").attr("relValue");
        var reqresetCombListStr = $("#reqresetCombList").attr("relValue");
        var reqresetProdLineStr = $("#reqresetProdLine").attr("relValue");
        var reqresetMonth = $("#reqresetMonth").val();
        var reqresetUnit = $("#reqresetUnit").val();

        if (reqresetType == 0) {
            if (reqresetOrganListStr.length < 2) {
                top.Dialog.alert("请选择下发机构", null, null, null, 5);
                return
            }
            if (reqresetCombListStr.length < 2) {
                top.Dialog.alert("请选择贷种组合", null, null, null, 5);
                return
            }

        } else if (reqresetType == 1) {
            if (reqresetProdLineStr.length < 2) {
                top.Dialog.alert("请选择下发条线", null, null, null, 5);
                return
            }
        }

        if (reqresetName == "") {
            top.Dialog.alert("请输入需求名称", null, null, null, 5);
            return
        }
        if (reqresetTimeStart == "") {
            top.Dialog.alert("请选择周期起始时间", null, null, null, 5);
            return4
        }
        if (reqresetTimeEnd == "") {
            top.Dialog.alert("请选择周期结束时间", null, null, null, 5);
            return
        }

        if (reqresetDateStart == "") {
            top.Dialog.alert("请选择录入起始时间", null, null, null, 5);
            return
        }
        if (reqresetDateEnd == "") {
            top.Dialog.alert("请选择录入结束时间", null, null, null, 5);
            return
        }

        if (reqresetDateStart != "" && reqresetDateEnd != "") {
            if (reqresetDateStart - reqresetDateEnd > 0) {
                top.Dialog.alert("需求录入开始日期应小于等于需求录入结束日期", null, null, null, 5);
                return
            }
        }
        if (reqresetTimeStart != "" && reqresetTimeEnd != "") {
            if (reqresetTimeStart - reqresetTimeEnd > 0) {
                top.Dialog.alert("周期开始日期应小于等于周期结束日期", null, null, null, 5);
                return
            }
        }
        if (reqresetDateEnd - reqresetTimeEnd > 0) {
            top.Dialog.alert("需求结束日期应小于等于周期结束日期", null, null, null, 5);
            return
        }


        if (reqresetNote == "") {
            top.Dialog.alert("请完善录入说明", null, null, null, 5);
            return
        }
        if (reqresetUnit == "" || reqresetNote == "请选择") {
            top.Dialog.alert("请选择单位", null, null, null, 5);
            return
        }

        top.Dialog.confirm("确定要提交新增吗？", function () {

            $.post('<%=path%>/tbTradeManger/tbReqresetList/insert.htm',
                {
                    'reqresetMonth': reqresetMonth,
                    'reqresetType': reqresetType,
                    'reqresetDateStart': reqresetDateStart,
                    'reqresetDateEnd': reqresetDateEnd,
                    'reqresetTimeStart': reqresetTimeStart,
                    'reqresetTimeEnd': reqresetTimeEnd,
                    'reqresetNote': reqresetNote,
                    'reqresetName': reqresetName,
                    'reqresetCombListStr': reqresetCombListStr,
                    'reqresetOrganListStr': reqresetOrganListStr,
                    'reqresetProdLineStr': reqresetProdLineStr,
                    'reqresetUnit': reqresetUnit
                },
                function (result) {
                    // clearInterval(showOrganInterval);
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
            chkboxType: {"Y": "ps", "N": "ps"}
        }

    };

    function transDate() {

        var reqresetMonth = $("#reqresetMonth").val();
        if ("请选择" == reqresetMonth) {
            $("#reqresetTimeStart").val("");
            $("#reqresetTimeEnd").val("");
            $('#reqresetTimeStart').removeAttr("disabled");
            $('#reqresetTimeEnd').removeAttr("disabled");

        } else {

            $("#reqresetTimeStart").val(reqresetMonth + "01");

            var year = parseInt(reqresetMonth.slice(0, 4));
            var month = parseInt(reqresetMonth.slice(4, 6));
            var days = getDaysOfMonth(year, month);
            $("#reqresetTimeEnd").val(reqresetMonth + "" + days);

            $('#reqresetTimeStart').attr("disabled", "disabled");
            $('#reqresetTimeEnd').attr("disabled", "disabled");
        }
    }

    function check() {
        var reqresetType = $("#reqresetType").val();
        if (reqresetType == 0) {//机构
            document.getElementById("reqresetProdLine").style.display = "none";
            $("#reqresetProdLine").selectTreeRender(setting_copy);
            document.getElementById("tr_1").style.display = "";
            document.getElementById("tr_2").style.display = "none";
            document.getElementById("reqresetCombList").style.display = "";
            document.getElementById("reqresetOrganList").style.display = "";
        } else if (reqresetType == 1) {//条线
            document.getElementById("reqresetCombList").style.display = "none";
            document.getElementById("reqresetOrganList").style.display = "none";
            $("#reqresetCombList").selectTreeRender(setting);
            $("#reqresetOrganList").selectTreeRender(setting_copy);
            document.getElementById("tr_1").style.display = "none";
            document.getElementById("tr_2").style.display = "";
            document.getElementById("reqresetProdLine").style.display = "";
        }
    }


    function initComplete() {
        //获取json数据
        $.post("<%=path%>/tbTradeManger/tbReqList/findTradeParam.htm",
            {}, function (result) {
                //赋给data
                $("#reqresetMonth").data("data", result);
                //刷新下拉框
                $("#reqresetMonth").render();
            }, "json");
        $("#reqresetMonth").setValue("请选择");
        $("#reqresetCombList").selectTreeRender(setting);
        $("#reqresetOrganList").selectTreeRender(setting_copy);
        $("#reqresetProdLine").selectTreeRender(setting_copy);
        check();
    }

    function trandDateTime() {
        var reqresetMonth = $("#reqresetMonth").val();
        var reqresetTimeStart = $("#reqresetTimeStart").val();
        var reqresetTimeEnd = $("#reqresetTimeEnd").val();

        if (("请选择" == reqresetMonth || "" == reqresetMonth) && ("" != reqresetTimeEnd || "" != reqresetTimeStart)) {
            document.getElementById("reqresetMonth").disabled = true;
            $("#reqresetMonth").render();
        } else if ("" == reqresetTimeEnd && "" == reqresetTimeStart) {
            document.getElementById("reqresetMonth").disabled = false;
            $.post("<%=path%>/tbTradeManger/tbReqList/findTradeParam.htm",
                {}, function (result) {
                    //赋给data
                    $("#reqresetMonth").data("data", result);
                    //刷新下拉框
                    $("#reqresetMonth").render();
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
</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                需求名称：
            </td>
            <td>
                <input type="text" id="reqresetName" name="reqresetName" AUTOCOMPLETE="off" maxlength="30"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                单位：
            </td>
            <td>
                <dic:select id="reqresetUnit" dicType="CURRENCY_UNIT" name="reqresetUnit"
                            tgClass="validate[required]" required="true"></dic:select>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <select id="reqresetMonth" name="reqresetMonth" onchange="transDate()"></select>
            </td>

            <td align="left">下发对象类型：</td>
            <td>
                <dic:select id="reqresetType" dicType="TB_REQ_TYPE" name="reqresetType" dicNo="0"
                            tgClass="validate[required]" onchange="check();"
                            required="true"></dic:select>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right">
                周期开始时间：
            </td>
            <td>
                <input type="text" id="reqresetTimeStart" name="reqresetTimeStart" class="validate[required] date"
                       AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd" onchange="trandDateTime()"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                周期结束时间：
            </td>
            <td>
                <input type="text" id="reqresetTimeEnd" name="reqresetTimeEnd" class="validate[required] date"
                       AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd" onchange="trandDateTime()"/>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right">
                需求录入开始时间：
            </td>
            <td>
                <input type="text" id="reqresetDateStart" name="reqresetDateStart" class="validate[required] date"
                       AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd" onfocus="WdatePicker({skin:themeColor,minDate:'%y-%M-%d'})"/>
                <span class="star">*</span>
            </td>

            <td align="right">
                需求录入结束时间：
            </td>
            <td>
                <input type="text" id="reqresetDateEnd" name="reqresetDateEnd" class="validate[required] date"
                       AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd" onfocus="WdatePicker({skin:themeColor,minDate:'%y-%M-%d'})"/>
                <span class="star">*</span>
            </td>
        </tr>

        <tr id="tr_1">
            <td>
                选择下发贷种组合:
            </td>

            <td>
                <div id="reqresetCombList" url="<%=path%>/tbTradeManger/tbReqList/selectComb.htm"
                     multiMode="true" allSelectable="true" exceptParent="false"></div>
                <span class="star">*</span>
            </td>

            <td>
                选择下发机构:
            </td>

            <td>
                <%--                <div class="selectTree  validate[required]" id="reqresetOrganList" onchange="organSelect()"--%>
                <%--                     url="<%=path%>/tbTradeManger/tbReqList/selectOrgan.htm" multiMode="true"--%>
                <%--                     allSelectable="true" exceptParent="true"></div>--%>

                <div id="reqresetOrganList"
                     url="<%=path%>/tbTradeManger/tbReqList/selectOrganList.htm" multiMode="true"
                     allSelectable="true" exceptParent="true"></div>

                <span class="star">*</span>
            </td>
        </tr>

        <tr id="tr_2">
            <td>
                选择下发条线:
            </td>

            <td>
                <%--                <div class="selectTree  validate[required]" id="reqresetProdLine" onchange="lineSelect()"--%>
                <%--                     url="<%=path%>/tbTradeManger/tbReqList/selectLine.htm" multiMode="true"--%>
                <%--                     allSelectable="true" exceptParent="true"></div>--%>

                <div id="reqresetProdLine"
                     url="<%=path%>/tbTradeManger/tbReqList/selectLineList.htm" multiMode="true"
                     allSelectable="true" exceptParent="true"></div>

                <span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td colspan="1">
                录入说明:
            </td>
            <td colspan="3">
                <textarea id="reqresetNote" name="reqresetNote" AUTOCOMPLETE="off" style="width:90%;"></textarea>
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