<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>

    <%--table冻结行列end--%>
    <title>服务数监控</title>
</head>
<body>

<form id="form1">

    <div id="scrollContent" class="border_gray" style="height: 300px">

        <table id="plan" class="tableStyle">

            <tr>
                <td width="50%">IP地址：</td>
                <td width="50%">
                    <input type="text" id="moIp" name="moIp" value="${monitor.moIp}"/>
                </td>
            </tr>

            <tr>
                <td width="50%">服务节点编号：</td>
                <td width="50%">
                    <input type="text" id="moSvrId" name="moSvrId" value="${monitor.moSvrId}"/>
                </td>
            </tr>


            <tr>
                <td width="50%">服务类型：</td>
                <td width="50%">
                    <input type="text" id="moSvrType" name="moSvrType" value="${monitor.moSvrType}"/>
                </td>
            </tr>


            <tr>
                <td width="50%">程序路径：</td>
                <td width="50%">
                    <input type="text" id="moSvrPath" name="moSvrPath" value="${monitor.moSvrPath}"/>
                </td>
            </tr>

            <tr>
                <td width="50%">程序端口：</td>
                <td width="50%">
                    <input type="text" id="moSvrPort" name="moSvrPort" value="${monitor.moSvrPort}"/>
                </td>
            </tr>

            <tr>
                <td width="50%">程序运行状态：</td>
                <td width="50%">
                    <input type="text" id="moSvrRunStatus" name="moSvrRunStatus" value="${monitor.moSvrRunStatus}"/>
                </td>
            </tr>

            <tr>
                <td width="50%">程序端口状态：</td>
                <td width="50%">
                    <input type="text" id="moSvrPortStatus" name="moSvrPortStatus" value="${monitor.moSvrPortStatus}"/>
                </td>
            </tr>

            <tr>
                <td width="50%">最后采集时间：</td>
                <td width="50%">
                    <input type="text" id="moCollectTime" name="moCollectTime" value="${monitor.moCollectTime}"/>
                </td>
            </tr>

        </table>

    </div>
    <div align="center" style="alignment: center;margin:20px;">
        <button type="button" onclick="return sub()" class="saveButton" id="updateTbPlan"></button>
        <button type="button" onclick="return cancel()" class="cancelButton" id="cancelUpdateTbPlan"></button>
    </div>
</form>
</body>

<script>


    //提交
    function sub() {

        var moIp = $('#moIp').val();
        var moSvrId = $('#moSvrId').val();
        var moSvrType = $('#moSvrType').val();
        var moSvrPath = $('#moSvrPath').val();
        var moSvrPort = $('#moSvrPort').val();
        var moSvrRunStatus = $('#moSvrRunStatus').val();
        var moSvrPortStatus = $('#moSvrPortStatus').val();
        var moCollectTime = $('#moCollectTime').val();


        if (!moIp || !moSvrId || !moSvrType || !moSvrPath || !moSvrPort || !moSvrRunStatus || !moSvrPortStatus || !moCollectTime) {
            top.Dialog.alert("参数不能为空！");
            return;
        }

        $('#updateTbPlan').attr('disabled', true);
        $('#cancelUpdateTbPlan').attr('disabled', true);
        var data = $("#form1").serialize();
        $.ajax({
            type: "POST",
            url: "<%=path%>/tbMonitor/update.htm",
            data: data,
            dataType: "json",
            success: function (result) {
                if (result.success == true || result.success == "true") {
                    $('#updateTbPlan').attr('disabled', false);
                    $('#cancelUpdateTbPlan').attr('disabled', false);
                    top.Dialog.alert("修改成功!", function () {
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
                    $('#updateTbPlan').attr('disabled', false);
                    $('#cancelUpdateTbPlan').attr('disabled', false);
                    top.Dialog.alert(result.message);
                }
            },
            error: function (result) {
                $('#updateTbPlan').attr('disabled', false);
                $('#cancelUpdateTbPlan').attr('disabled', false);
                top.Dialog.alert("请求异常");
            }
        });

    }


</script>

</html>