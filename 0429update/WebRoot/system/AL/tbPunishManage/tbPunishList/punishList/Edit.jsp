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
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <!-- 树形下拉框end -->
    <title></title>
</head>
<script type="text/javascript">

    var id =${TbPunishList.id};
    function submitInfo() {
        var tbPunishListDetail = $("#form1").serialize();
        var name = $("#name").val();
        var collectEndTime = $("#collectEndTime").val();
        var note = $("#note").val();
        var id = $("#id").val()
        var month = ${TbPunishList.month};

        $.ajax({
            type: "POST",
            url: "<%=path%>//tbTradeManger/tbPunishList/update.htm",
            data: {"name": name, "collectEndTime": collectEndTime, "note": note, "tbPunishListDetail": tbPunishListDetail,"id":id,"month":month},
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    top.Dialog.alert("更新成功!", function () {
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
        $("#table_1").FrozenTable(1,0,1,0);
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/tbPunishList/getReqDetailList.htm",
            data: {"id": id},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbPunishResultList = result.tbPunishResultList;
                    for (var i = 0; i < tbPunishResultList.length; i++) {
                        var tbPunishResult = tbPunishResultList[i];
                        var organCode = tbPunishResult.organCode;
                        var state=tbPunishResult.state;
                        var stateStr ="";
                        if(state==2){
                            stateStr="未反馈";
                        }else if(state==8) {
                            stateStr="已反馈";
                        }else if(state==1) {
                            stateStr="未下发";
                        }
                        $("#" + organCode + "_" + "state").val(stateStr).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "planMount").val(tbPunishResult.planMount).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthVacancyAmt").val(tbPunishResult.monthVacancyAmt).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthVacancyRate").val(tbPunishResult.monthVacancyRate).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthFiveVacancy").val(tbPunishResult.monthFiveVacancy);
                        $("#" + organCode + "_" + "monthShitiPlanMount").val(tbPunishResult.monthShitiPlanMount).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthShitiOverAmt").val(tbPunishResult.monthShitiOverAmt).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthShitiOverRate").val(tbPunishResult.monthShitiOverRate).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthFiveShitiOver").val(tbPunishResult.monthFiveShitiOver);
                        $("#" + organCode + "_" + "monthPiapjuPlanMount").val(tbPunishResult.monthPiapjuPlanMount).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthPiaojuOverAmt").val(tbPunishResult.monthPiaojuOverAmt).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthPiaojuOverRate").val(tbPunishResult.monthPiaojuOverRate).attr("disabled", "disabled");
                        $("#" + organCode + "_" + "monthFivePiaojuOver").val(tbPunishResult.monthFivePiaojuOver);
                        $("#" + organCode + "_" + "monthTotalPunish").val(tbPunishResult.monthTotalPunish);
                        $("#" + organCode + "_" + "note").val(tbPunishResult.note);
                        $("#" + organCode + "_" + "note").attr("disabled", "disabled");

                    }
                }
            }
        });
    })



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

</script>

<body>
<form id="form2">
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="id" name="id" value="${TbPunishList.id}" hidden="hidden"/>
                    ${TbPunishList.month}

            </td>


            <td align="right">
                罚息名称：
            </td>
            <td>
                <input id="name" name="name" value="${TbPunishList.name}" class="validate[required] "/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="right">
                意见征集截止时间：
            </td>
            <td>
                <input type="text" id="collectEndTime" name="collectEndTime" class="validate[required] date"
                       AUTOCOMPLETE="off" value="${TbPunishList.collectEndTime}"
                       dateFmt="yyyyMM"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3">
                <textarea id="note" name="note" AUTOCOMPLETE="off" style="width:90%;"
                          class="validate[required]"> ${TbPunishList.note} </textarea>
            </td>
        </tr>


    </table>
</form>

<form id="form1">
    <div id="scrollContent" class="border_gray" style="height: 500px;" >
        <table id="table_1" class="tableStyle" thTrueWidth="true" mode="list"  >
            <tr>
                <td ></td>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <td >
                        <div style="width: 220px"> ${combAmountName.name}</div>
                    </td>
                </c:forEach>
            </tr>

            <c:forEach items="${organList}" var="organ">
                <tr>
                    <td> ${organ.organname}</td>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${organ.organcode}_${combAmountName.code}" AUTOCOMPLETE="off"
                                   class=" validate[required,custom[onlyNumberWideSpecial]]"
                                   onkeyup='upperCase(this)' value="0"
                                   type="text" id="${organ.organcode}_${combAmountName.code}" maxlength="16"/>

                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div align="center">
        <div align="center">
            <button type="button" onclick="submitInfo()" class="saveButton"/>
            <button type="button" onclick="return cancel()" class="cancelButton"/>
        </div>
    </div>
</form>


</body>
</html>