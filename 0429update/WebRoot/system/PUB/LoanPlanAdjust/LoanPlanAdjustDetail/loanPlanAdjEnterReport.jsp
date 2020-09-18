<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%--    <%@include file="/common/common_edit.jsp"%>--%>
    <%@include file="/common/common_edit.jsp" %>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-form.js"></script>
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
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-migrate-1.2.1.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/TableFreeze.js"></script>
    <link rel="stylesheet" media="screen"
          href="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
        <script type="text/javascript" src="${path}/libs/bootstrap/sample in bootstrap v2/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <title>计划录入</title>
    <script type="text/javascript">

        var isIE = /msie/i.test(navigator.userAgent) && !window.opera;

        function fileChange(target, id) {
            var fileSize = 0;
            var filetypes = [".xls"];
            var filepath = target.value;
            if (filepath) {
                var isnext = false;
                var fileend = filepath.substring(filepath.indexOf("."));
                if (filetypes && filetypes.length > 0) {
                    for (var i = 0; i < filetypes.length; i++) {
                        if (filetypes[i] == fileend) {
                            isnext = true;
                            break;
                        }
                    }
                }
                if (!isnext) {
                    top.Dialog.alert("不接受此文件类型！");
                    target.value = "";
                    return false;
                }
            } else {
                return false;
            }
            if (isIE && !target.files) {
                var filePath = target.value;
                var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
                if (!fileSystem.FileExists(filePath)) {
                    top.Dialog.alert("附件不存在，请重新输入！");
                    return false;
                }
                var file = fileSystem.GetFile(filePath);
                fileSize = file.Size;
            } else {
                fileSize = target.files[0].size;
            }

            var size = fileSize / 1024;
            if (size <= 0) {
                top.Dialog.alert("附件大小不能为0M！");
                target.value = "";
                return false;
            }
        }

        //上传文件
        function xhrSubmit() {
            var file_obj = document.getElementById('search_key_file').files[0];
            if (typeof (file_obj) == "undefined") {
                top.Dialog.alert("请选择需要导入的文件");
                return;
            }
            var planMonth = $("#planMonth").val();
            var planUnit = $("#planUnit").val();
            var increment = $("#increment").val();
            var planadjUnifiedType = $("#planadjUnifiedType").val();


            if (!planMonth) {
                top.Dialog.alert("所属月份不可为空");
                return;
            }
            if (!planUnit) {
                top.Dialog.alert("单位不可为空");
                return;
            }


            var fd = new FormData();
            fd.append('file', file_obj);
            fd.append('planMonth', planMonth);
            fd.append('planUnit', planUnit);
            fd.append('increment', increment);
            fd.append('planadjUnifiedType', planadjUnifiedType);

            top.Dialog.confirm("确定要录入"+planMonth+"的批量机构调整计划吗?|操作提示", function () {
                $('#submit').attr('disabled', true);
                $.ajax({
                    url: '<%=path%>/tbPlanadj/enterReportPlanadjByMonth.htm',
                    type: 'POST',
                    data: fd,
                    processData: false,  //tell jQuery not to process the data
                    contentType: false,  //tell jQuery not to set contentType
                    success: function (result) {
                        result = JSON.parse(result);
                        if (result.success == "true" || result.success == true) {
                            $('#submit').attr('disabled', false);
                            top.Dialog.alert(result.msg, function () {
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
                            $('#submit').attr('disabled', false);
                            top.Dialog.alert(result.msg);
                        }
                    },
                    error: function (error) {
                        $('#submit').attr('disabled', false);
                        if (error.status == "10000") {
                            top.Dialog.alert(error.responseText + " | 超时提示", function () {
                                window.parent.location.href = "<%=path%>/toLogin.htm";
                            }, null, null, 3);
                        } else {
                            top.Dialog.alert(error.responseText);
                        }
                    }
                });
            });
        }

        $(function () {
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


        // 初始化表格数据
        function planMonthChange() {
            var planMonth = $('#planMonth').val();

            //判断该月份是否存在计划
            $.ajax({
                type: "post",
                url: "<%=path%>/tbPlanadj/tbPlanadjDetail.htm",
                data: {"planMonth": planMonth},
                dataType: "json",
                success: function (result) {
                    if (result) {
                        var plan = result.plan;
                        if (!plan) {
                            top.Dialog.alert("当前月份的机构计划还没有制定或计划还没有通过审批！", function () {
                                var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                                var menu_frame_id = "page_" + menu_id;
                                top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                                top.Dialog.close();
                            });
                            return;
                        }
                        var planadjStatus = result.planadjStatus;
                        if (planadjStatus == 0) {
                            top.Dialog.alert(" 所属月份批量机构调整计划已经填写，可以导入");
                        } else if (planadjStatus == 32) {
                            top.Dialog.alert(" 所属月份批量机构调整计划已被驳回，可以导入");
                        } else if (planadjStatus == 8) {
                            top.Dialog.alert(" 所属月份批量机构调整计划正在审批中，不能导入");
                            return;
                        }


                        var creditPlanList = result.creditPlanList;
                        var reqList = result.reqList;
                        var organList = result.organList;
                        var combList = result.combList;
                        organlevel = result.organlevel;

                        //初始化
                        $("#planUnit").val(plan.planUnit);
                        $("#increment").val(plan.planIncrement);

                        var unit = "";
                        if (plan.planUnit == 1 || '1' == plan.planUnit) {
                            unit = "万元";
                        } else if (plan.planUnit == 2 || '2' == plan.planUnit) {
                            unit = "亿元";
                        }
                        $("span[name=planUnit]").html(unit);


                        //初始化creditPlanList
                        if (organList && combList) {
                            for (var i = 0; i < organList.length; i++) {
                                var organ = organList[i];
                                for (var j = 0; j < combList.length; j++) {

                                    var comb = combList[j];
                                    var organcode = organ.thiscode;
                                    var combcode = comb.combcode;
                                    var mapKey = organcode + "_" + combcode;
                                    var planAmount = creditPlanList[mapKey];
                                    var reqAmount = reqList[mapKey];

                                    $("input[name=" + mapKey + "_init" + "]").val(planAmount);
                                    $("span[name=" + mapKey + "_init" + "]").html(planAmount);

                                    $("input[name=" + mapKey + "req" + "]").val(reqAmount);
                                    $("span[name=" + mapKey + "req" + "]").html(reqAmount);

                                }
                            }
                        }

                        //横竖加和
                        //初始化调整后金额
                        amountFinal();

                        //初始化横竖求和
                        amountRowAndColumn();
                    }
                }
            });
        }

        //下载模板
        function downloadPlan() {
            var combLevel = $("#combLevel").val();
            if (!combLevel) {
                top.Dialog.alert("请选择贷种级别");
                return;
            }

            location = "<%=path%>/tbPlanadj/downPlanadjTemplate.htm?type=1&combLevel="+combLevel;
        }


    </script>
</head>
<body>

<div class="form-group">
    <table>
        <tr>
            <td>
                <span>请选择下载的模板的贷种级别：</span>
            </td>
            <td>
                <select id = "combLevel">
                    <option value="1">一级贷种</option>
                    <option value="2">二级贷种</option>
                </select>
            </td>
        </tr>
    </table>
    <label class="col-sm-2 control-label">录入计划模板下载：</label>
        <button class="btn btn-primary" type="button" onclick="downloadPlan()">
            下载
        </button>
</div>
<HR align=center width=300 color=#987cb9 SIZE=1>
</br>

<form id="form2">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <%--                <input id="planMonth" name="planMonth"  type="text" onchange="planMonthChange();"/>--%>
                <input name="planMonth" type="text" id="planMonth" onchange="planMonthChange();"
                       class="input-small inline form_datetime" style="width: 160px;"/>

            </td>
            <td align="left">单位：</td>
            <td>
                <input id="planUnit" name="planUnit" type="hidden"> </input>
                <span name="planUnit"></span>
            </td>
            <%--        </tr>--%>
            <%--        <tr>--%>
            <%--<td align="right">--%>
            <%--本月计划净增量 ：--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<input id="increment" name="increment"  type="hidden" value="${plan.planIncrement}"> </input>--%>
            <%--${plan.planIncrement}--%>
            <%--</td>--%>
            <input id="increment" name="increment" type="hidden" value="0"/>


            <td align="left">是否统一调整：</td>
            <td>
                <select name="planadjUnifiedType" size="1" id="planadjUnifiedType">
                    <option value="1">是</option>
                    <option value="2" selected>否</option>
                </select>
            </td>

        </tr>
    </table>
</form>

<div class="form-group">
    <label class="col-sm-2 control-label">录入文件</label>
    <div class="col-sm-10" >
        <input type="file" onchange="fileChange(this);" class="file " id="search_key_file"
               accept="application/vnd.ms-excel"/>
    </div>
    </br>(支持文件格式：xls)
</div>

<div class="form-group">
    <div class="col-sm-10 col-sm-offset-2" style="margin-top: 10px;">
        <button class="btn btn-primary" type="button" onclick="xhrSubmit();" id="submit">
            录入
        </button>
    </div>
</div>

</body>
</html>

