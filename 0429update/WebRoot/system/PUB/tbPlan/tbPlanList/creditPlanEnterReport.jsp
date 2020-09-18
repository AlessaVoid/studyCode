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
            var paramMode = $("#paramMode").val();


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
            fd.append('paramMode', paramMode);

            top.Dialog.confirm("确定要录入该计划吗?|操作提示", function () {
                $('#submit').attr('disabled', true);
                $.ajax({
                    url: '<%=path%>/creditPlan/enterReportPlanByMonth.htm',
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
                                if (menu_id == undefined) {
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

        //获取所属月份下拉数据
        function ajaxMonth() {
            $.post("<%=path%>/creditPlan/findTradeParam.htm", {}, function (result) {
                if (result) {
                    result.list.unshift({value: "", key: "--请选择--"});
                    $("#planMonth").data("data", result);
                    $("#planMonth").render();
                }
            }, "json");
        }

        //初始化表格数据
        function planMonthChange() {

            var planMonth = $("#planMonth").val();
            //判断该月份是否存在计划
            $.ajax({
                type: "get",
                url: "<%=path%>/creditPlan/creditPlanEnterJudgeMonth.htm",
                data: {"planMonth": planMonth},
                dataType: "json",
                success: function (result) {
                    if (result.success == true || result.success == "true") {
                    } else {
                        top.Dialog.alert(result.message);
                    }
                },
                error: function (result) {
                    top.Dialog.alert("请求异常");
                }

            });


            //初始化数据
            $.post("<%=path%>/creditPlan/getPlanTime.htm", {"planMonth": planMonth}, function (result) {
                //赋给data
                $("#paramMode").val(result.paramMode == 2 ? "严格" : result.paramMode);
                $("#increment").val(result.increment);
            }, "json");

            $('#paramMode').attr("disabled", "disabled");
            $('#increment').attr("disabled", "disabled");
        }

        $(function () {
            ajaxMonth();
            // planMonthChange();
        })

        //下载模板
        function downloadPlan() {
            var combLevel = $("#combLevel").val();
            if (!combLevel) {
                top.Dialog.alert("请选择贷种级别");
                return;
            }

            location = "<%=path%>/creditPlan/downloadPlanTemplate.htm?type=1&combLevel="+combLevel;
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

    <div>
        <label class="col-sm-2 control-label">录入计划模板下载：</label>
        <button class="btn btn-primary" type="button" onclick="downloadPlan()">
            下载
        </button>
    </div>
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
                <select id="planMonth" name="planMonth" onchange="planMonthChange();"></select>
                <span class="star">*</span>
            </td>
            <td align="left">单位：</td>
            <td>
                <dic:select id="planUnit" dicType="CURRENCY_UNIT" name="planUnit"
                            tgClass="validate[required]" dicNo="2"
                            required="true"></dic:select>
                <span class="star">*</span>
            </td>


            <%--<td align="right">--%>
            <%--本月计划净增量（亿元）：--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<input type="hidden" id="increment" name="increment"/>--%>
            <%--<span class="star">*</span>--%>
            <%--</td>--%>
            <input type="hidden" id="increment" name="increment"/>


            <td align="right">
                管控模式：
            </td>
            <td>
                <input type="text" id="paramMode" name="paramMode"/>
                <span class="star">*</span>
            </td>
        </tr>
    </table>
</form>

<div class="form-group">
    <label class="col-sm-2 control-label">录入文件</label>
    <div class="col-sm-10">
        <input type="file" onchange="fileChange(this);" class="file" id="search_key_file"
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

