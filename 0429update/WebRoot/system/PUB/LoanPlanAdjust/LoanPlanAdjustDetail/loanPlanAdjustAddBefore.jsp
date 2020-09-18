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
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <%--    <script type="text/javascript" src="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/js/bootstrap.min.js"></script>--%>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <title>信贷计划调整详情页面</title>
</head>
<body>
<form id="form1" >
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <th colspan="2">
                <span>请选择要调整计划的月份：</span>
            </th>
        </tr>
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <input name="planMonth" type="text" id="planMonth" onchange="planMonthChange();"
                       class="input-small inline form_datetime" style="width: 160px;"/>
            </td>

    </table>
    <br>
    <br>
    <br>

    <div align="center" style="alignment: center">
        <button type="button" onclick="return sub()" id="saveTbPlan"><span>调整计划</span></button>
        <button type="button" onclick="return cancel()" id="cancelSubmit" class="cancelButton"></button>
    </div>
</form>
</body>

<script>

    $(function() {

        //日期选择框
        $('#planMonth').datetimepicker({
            format: 'yyyymm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
            forceParse: false,
            language: 'zh-CN'
        });
    });



    function planMonthChange() {
        var planMonth = $('#planMonth').val();
        $('#saveTbPlan').attr('disabled', true);
        $('#cancelSubmit').attr('disabled', true);

        //判断该月份是否存在计划
        $.ajax({
            type: "post",
            url: "<%=path%>/tbPlanadj/tbPlanadjDetail.htm",
            data: {"planMonth": planMonth},
            dataType: "json",
            success: function(result){
                if(result){
                    var plan = result.plan;
                    if (!plan) {
                        top.Dialog.alert("当前月份的机构计划还没有制定或计划还没有通过审批！",function () {
                            // var menu_id = parent.getCurrentTabId();
                            // if(menu_id==undefined){
                            //     top.Dialog.close();
                            //     return;
                            // }
                            // var menu_frame_id = "page_" + menu_id;
                            // top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            // top.Dialog.close();
                        });
                        return;
                    }
                    var planadjStatus = result.planadjStatus;
                    if (planadjStatus == 0) {
                        top.Dialog.alert(" 所属月份批量机构调整计划已存在");
                        return;
                    }else if (planadjStatus == 32) {
                        top.Dialog.alert(" 所属月份批量机构调整计划已存在且被驳回");
                        return;
                    }else if (planadjStatus == 8) {
                        top.Dialog.alert(" 所属月份批量机构调整计划正在审批中");
                        return;
                    }

                }
                $('#saveTbPlan').attr('disabled', false);
                $('#cancelSubmit').attr('disabled', false);
            }

        });
    }

    var diag = new top.Dialog();

    //调整计划页面
    function sub() {
        //校验
        var planMonth = $("#planMonth").val();
        if (!planMonth) {
            top.Dialog.alert("计划月份不可为空");
            return;
        }

        diag.URL = "<%=path%>/tbPlanadj/loadLoanPlanadjDetailInfoInsertPage.htm?planMonth=" + planMonth,
            diag.Title = "新增批量机构调整计划";
        diag.Width = 1600;
        diag.Height = 700;

        diag.CancelEvent= function(){
            diag.close();
            //关闭计划页面是关闭此页面
            top.Dialog.close();
        };
        diag.show();

    }



</script>

</html>