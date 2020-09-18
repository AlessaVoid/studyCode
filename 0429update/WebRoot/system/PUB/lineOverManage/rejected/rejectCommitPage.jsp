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
    <title></title>
</head>
<script type="text/javascript">

    var qaId =${TbLineOver.qaId};
    $(function () {
        initComplete();
        initAuditOper();
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
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbTradeManger/lineOver/getReqDetailList.htm",
            data: {"qaId": qaId},
            dataType: "json",
            success: function (result) {
                if (result) {
                    var tbOverDOS = result.tbOverDOS;
                    for (var i = 0; i < tbOverDOS.length; i++) {
                        var TbLineOverDO = tbOverDOS[i];
                        var qaComb = TbLineOverDO.qaComb;
                        var qaAmt = TbLineOverDO.qaAmt;

                        $("#" + qaComb + "_Num").val(qaAmt);
                    }
                }
            }
        });
    });
    function initComplete() {

        $.post("<%=path%>/tbTradeManger/tbQuotaApply/findComb.htm",
            {"organLevel":1}, function (result) {
                //赋给data
                $("#qaComb").data("data", result)
                //刷新下拉框
                $("#qaComb").render();
            }, "json");
        $("#span1").html(${oneNum}+"");
        $("#span2").html( ${oneRate}+"%");
        $("#span3").html(${twoNum}+"");
        $("#span4").html( ${twoRate}+"%");
        $("#span5").html( ${threeNum}+"");
        $("#span6").html( ${threeRate}+"%");

        $("#qaComb").setValue("${TbLineOver.qaComb}");

    }


    function initAuditOper() {
        //这里是个关键点
        $.ajax({
            url: "<%=path%>/lineOverApplyReject/getOperInfoListByRolecode.htm?taskid=${taskid}&qaId=${TbLineOver.qaId}",
            method: "GET",
            async: false,
            success: function (result) {
                $("#auditOperList").data("data", result);
                $("#auditOperList").render();
            }
        });
    }
    function submitInfo() {

        var qaReason = $("#qaReason").val();
        var tbOverDetail = $("#form1").serialize();
        if (qaReason == "") {
            top.Dialog.alert("请填写申请原因", null, null, null, 5);
            return
        }

        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if (!valid) {
            top.Dialog.alert("请检查数据正确性");
            return;
        }
        var file_obj = document.getElementById('search_key_file').files[0];
        var fd = new FormData();
        fd.append('file', file_obj);
        fd.append('tbOverDetail', tbOverDetail);
        fd.append('qaReason', qaReason);
        fd.append('qaId', qaId);

        if(!valid){
            top.Dialog.alert("请检查数据正确性");
            return;
        }
        $.ajax({
            url: '<%=path%>/lineOverApplyReject/update.htm',
            type: 'POST',
            data: fd,
            processData: false,
            contentType: false,
            success: function (result) {
                top.Dialog.alert("更新成功!",function(){
                });
            },
            error:function(error){
                top.Dialog.alert("上传失败");
            }
        });
    }
    function onAudit(isAgree) {
        top.Dialog.confirm("确定要提交该记录吗？", function () {
            var qaId = $("#qaId").val();
            var qaReason = $("#qaReason").val();
            var tbOverDetail = $("#form1").serialize();
            if (qaReason == "") {
                top.Dialog.alert("请填写申请原因", null, null, null, 5);
                return
            }
            var comment =  $("#comment").val();
            if(comment==""){
                top.Dialog.alert("请填写备注!");
                return;
            }
            var valid = $("#form1").validationEngine({
                returnIsValid: true
            });
            if (!valid) {
                top.Dialog.alert("请检查数据正确性");
                return;
            }
            var file_obj = document.getElementById('search_key_file').files[0];
            var fd = new FormData();
            fd.append('file', file_obj);
            fd.append('tbOverDetail', tbOverDetail);
            fd.append('qaReason', qaReason);
            fd.append('qaId', qaId);

            if(!valid){
                top.Dialog.alert("请检查数据正确性");
                return;
            }

                    $("#but1").attr("disabled","disabled");
                    $("#but2").attr("disabled","disabled");
                    $("#but3").attr("disabled","disabled");
                    var comment = $("#comment").val();
                    var taskId = $("#taskId").val();
                    var assignee = $("#auditOperList").val();
                    fd.append('taskId', taskId);
                    fd.append('comment', comment);
                    fd.append('isAgree', isAgree);
                    fd.append('assignee', assignee);
                    fd.append('lastUserType', ${lastUserType});
                    $.ajax({
                        url: "<%=path%>/lineOverApplyReject/auditLoanQuotaApplyRequest.htm",
                        data: fd,
                        method: 'POST',
                        processData: false,
                        contentType: false,
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
                                $("#but1").removeAttr("disabled");
                                $("#but2").removeAttr("disabled");
                                $("#but3").removeAttr("disabled");
                                top.Dialog.alert("操作失败!" + res.message);
                            }
                        }
                    });

        });

    }

    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    function fileChange(target,id) {
        var fileSize = 0;


        if (isIE && !target.files) {
            var filePath = target.value;
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
            if(!fileSystem.FileExists(filePath)){
                top.Dialog.alert("附件不存在，请重新输入！");
                return false;
            }
            var file = fileSystem.GetFile (filePath);
            fileSize = file.Size;
        } else {
            fileSize = target.files[0].size;
        }
        var size = fileSize / 1024;
        if(size<=0){
            top.Dialog.alert("附件大小不能为0M！");
            target.value ="";
            return false;
        }
        $("#fileName").html(target.files[0].name);
    }

    //用户只能输入正负数与小数
    function upperCase(obj) {
        // obj.value = obj.value.replace(/[^\d.]/g,"");
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g,"");
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g,".");
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
    <input type="hidden" id="taskId" name="taskId" value="${taskid}"/>
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input id="qaId" name="qaId" value="${TbLineOver.qaId}" hidden="hidden"/>
                ${TbLineOver.qaMonth}
            </td>

            <td align="right">
                附件名称：
            </td>
            <td>
                <span id="fileName">${fileName}</span>
            </td>
        </tr>

        <tr>
            <td align="right">下载附件：</td>
            <td>
                <a href="<%=path%>/tbTradeManger/single/download.htm?qaFileId=${TbLineOver.qaFileId}">
                    <button class="btn btn-primary" type="button">
                        下载
                    </button>
                </a>
            </td>

            <td align="right">上传附件：</td>
            <td>
                <input type="file" onchange="fileChange(this);" class="file" id="search_key_file" />
            </td>


        </tr>

        <tr>
            <td align="right">全年计划进度：</td>
            <td>
                ${TbLineOver.qaYearRate}%
            </td>

            <td align="right">
                单位：
            </td>
            <td>
                <dic:out dicType="CURRENCY_UNIT" dicNo="${TbLineOver.unit}"/>
            </td>

        <tr>
            <td align="right">本月计划：</td>
            <td>
                ${TbLineOver.qaPlanAmt}
            </td>
            <td align="right">本月超计划额度：</td>
            <td>
                ${TbLineOver.qaOverAmt}
            </td>
        </tr>
        <tr>
            <td align="right">前第三个月超规模或闲置额度：</td>
            <td>
                <span id="span1"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span2"></span>
            </td>
        </tr>
        <tr>
            <td align="right">前第二个月超规模或闲置额度：</td>
            <td>
                <span id="span3"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span4"></span>
            </td>
        </tr>
        <tr>
            <td align="right">前第一个月超规模或闲置额度：</td>
            <td>
                <span id="span5"></span>
            </td>
            <td align="right">百分比：</td>
            <td>
                <span id="span6"></span>
            </td>
        </tr>

        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3">
                <textarea id="qaReason" name="qaReason" AUTOCOMPLETE="off"   style="width:90%;" class="validate[required]">${TbLineOver.qaReason}</textarea>
            </td>
        </tr>
    </table>
</form>


<form id="form1">
    <div id="scrollContent" class="border_gray" >
        <table class="tableStyle" thTrueWidth="true" mode="list" fixedCellHeight="true">
            <tr>
                <th trueWidth="150"></th>
                <c:forEach items="${combAmountNameList}" var="combAmountName">
                    <th align="center">
                            ${combAmountName.name}
                    </th>
                </c:forEach>
            </tr>

            <c:forEach items="${combList}" var="comb">
                <tr>
                    <th> ${comb.combName}</th>
                    <c:forEach items="${combAmountNameList}" var="combAmountName">
                        <td align="center">
                            <input name="${comb.combCode}_${combAmountName.code}" AUTOCOMPLETE="off"
                                   class="planamonut ${comb.combCode } ${combAmountName.code}"
                                   onkeyup='upperCase(this)' value="0"
                                   type="text" id="${comb.combCode}_${combAmountName.code}" maxlength="16"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div align="center">
        <button type="button" onclick="submitInfo()" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
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
                    <td>
                        <c:if test="${comment.type =='待审批'}">
                            驳回待提交
                        </c:if>
						<c:if test="${comment.type !='待审批'}">
                            ${comment.type}
                        </c:if>
                    </td>
                    <td style="word-break:break-all">${comment.fullMessage }</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>
<div>备注:</div>
<div><textarea id="comment" rows="50" cols="50"></textarea></div>
<div>
<c:if test="${false == lastUserType }">
    <div>下一节点审批人:</div>
    <div>
        <dic:select id="auditOperList" name="auditOperList" dicType=""></dic:select>
    </div>
</c:if>
<div align="center">
    <div align="center">
        <button type="button" id="but1" onclick="onAudit('1')"><span class="icon_ok">提交</span></button>
    </div>
</div>



</body>
</html>