<%@ page language="java" pageEncoding="utf-8" %>
<%@taglib uri="http:/boco.com.cn/tags-dic" prefix="dic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http:/boco.com.cn/tags-fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fm" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    request.setAttribute("path", path);
%>
<style>
    .none {
        display: none;
    }
</style>
<!--框架必需start-->
<script type="text/javascript" src="${path}/libs/js/jquery.js"></script>
<script type="text/javascript" src="${path}/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${path}/libs/js/framework.js"></script>
<link href="${path}/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${path}/" splitMode="true"
      href="<%=path%>/libs/skins/blue/style.css"/>
<link rel="stylesheet" type="text/css" id="customSkin" href="${path}/system/layout/skin/style.css"/>

<!--框架必需end-->
<!--表单验证start-->
<script src="${path}/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="${path}/libs/js/form/validation.js" type="text/javascript"></script>
<!--表单验证end-->
<!--表单异步提交start-->
<script src="${path}/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期选择框start -->
<script type="text/javascript" src="${path}/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期选择框end -->
<!--基本选项卡start-->
<script type="text/javascript" src="${path}/libs/js/nav/basicTabModern.js"></script>
<!--基本选项卡end-->
<!-- 树组件start -->
<script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
<!-- 树组件end -->
<!-- 树形下拉框start -->
<script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
<!--自动提示框start-->
<script src="${path}/libs/js/form/suggestion.js" type="text/javascript"></script>
<!--自动提示框end-->
<script type="text/javascript" src="${path}/libs/js/money.js"></script>
<!--数据表格start-->
<script src="${path}/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
</head>
<script>
    //####################################################################################################################
    //Ajax全局变量
    //####################################################################################################################
    var jQ = $;
    var path = '${path}';
    jQ.ajaxSetup({
        type: "POST",
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            if (this.url.indexOf("?") != -1) {
                this.url = this.url + "&number=" + Math.random() + "";
            } else {
                this.url = this.url + "?number=" + Math.random() + "";
            }
            buttonluck();
        },
        complete: function (XMLHttpRequest, textStatus) {
            buttonUnluck();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            buttonUnluck();
            if (XMLHttpRequest.status == "10000") {
                top.Dialog.alert(XMLHttpRequest.responseText + " | 超时提示", function () {
                    window.parent.location.href = path + "/toLogin.htm";
                }, null, null, 5);
            } else {
                top.Dialog.alert(XMLHttpRequest.responseText);
            }
        }
    });
    //####################################################################################################################
    //编辑页面按钮、公共方法
    //####################################################################################################################
    function buttonluck() {
        $('.saveButton').attr('disabled', true);
    }

    function buttonUnluck() {
        $('.saveButton').attr('disabled', false);
    }

    //禁用回车键
    document.onkeypress = function () {
        if (event.keyCode == 13) {
            return false;
        }
    }
    $(function () {
        //修正由于使用了tab导致高度计算不准确
        if (broswerFlag == "IE6") {
            setTimeout(function () {
                top.iframeHeight('frmrightChild');
            }, 500);
        }
        //‘查询’按钮
        $(".queryButton").addClass("button");
        $(".queryButton").append("<span class='icon_find'>查询</span>");
        //追加
        $(".addButton").addClass("button");
        $(".addButton").append("<span class='icon_add'>追加</span>");
        //‘重置’按钮
        $(".resetButton").addClass("button");
        $(".resetButton").append("<span class='icon_reload'>重置</span>");
        //‘保存’按钮
        $(".saveButton").addClass("button");
        $(".saveButton").append("<span class='icon_save'>保存</span>");
        //‘取消’按钮
        $(".cancelButton").addClass("button");
        $(".cancelButton").append("<span class='icon_no'>取消</span>");

        //'同意'按钮
        $(".auditButton").addClass("button");
        $(".auditButton").append("<span class='icon_save'>同意</span>");
        //‘驳回’按钮
        $(".rejectButton").addClass("button");
        $(".rejectButton").append("<span class='icon_no'>驳回</span>");
        //'启动审批'按钮
        $(".auditStartButton").addClass("button");
        $(".auditStartButton").append("<span class='icon_save'>启动审批</span>");

        //'重新启动审批'按钮
        $(".auditReStartButton").addClass("button");
        $(".auditReStartButton").append("<span class='icon_save'>重新提交审批</span>");

        //‘驳回’按钮
        $(".auditPostButton").addClass("button");
        $(".auditPostButton").append("<span class='icon_no'>关闭</span>");
        //'保存草稿'按钮
        $(".saveScriptButton").addClass("button");
        $(".saveScriptButton").append("<span class='icon_export'>保存</span>");

        //首页
        $(".firstButton").addClass("button");
        $(".firstButton").append("<span class='icon_page_prev'>上一页</span>");
        $(".firstButton").attr("disabled", "disabled");
        //末页
        $(".lastButton").addClass("button");
        $(".lastButton").append("<span class='icon_page_next'>下一页</span>");
        $(".lastButton").attr("disabled", "disabled");
        //上一页
        $(".upButton").addClass("button");
        $(".upButton").append("<span class='icon_page_prev'>上一页</span>");
        $(".upButton").click(
            function () {
                $('.basicTabModern').basicTabModernSetIdx(parseInt($('.basicTabModern').attr('selectedIdx')) - parseInt(1));
            });

        //下一页
        $(".downButton").addClass("button");
        $(".downButton").append("<span class='icon_page_next'>下一页</span>");
        $(".downButton").click(
            function () {
                $('.basicTabModern').basicTabModernSetIdx(parseInt($('.basicTabModern').attr('selectedIdx')) + parseInt(1));
            });

        //同意按钮宽度
        $(".button").css("width", "90px");
        $(".button").css("cursor", "pointer");

        $(".txt").addClass("trans_bg");
        $(".txt").css("border-width", "0");
        $(".txt").css("width", "118px");
        $(".txt").attr("readonly", "readonly");
        //$(".selectbox").css("width", "98px");
    });

    //提交表单公共方法
    function doSubmit(form, url) {
        var valid = $("#" + form).validationEngine({
            returnIsValid: true
        });
        if (valid) {
            top.Dialog.confirm("确定要保存操作吗?|操作提示", function () {
                $(".money").each(function () {
                    rmoney(this);
                });
                $.post(url, $("#" + form).serialize(), function (result) {
                    if (result.success == "true" || result.success == true) {
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
                        $(".money").each(function () {
                            fmoney(this);
                        });
                        top.Dialog.alert(result.msg);
                    }
                }, "json");
            });
        } else {
            top.Dialog.alert("验证未通过！");
        }
    }

    //取消编辑公共方法
    function cancel() {
        top.Dialog.confirm("数据尚未保存，是否退出?|操作提示", function () {
            top.Dialog.close();
        });
    }

    //获取复核人员的详细信息
    $(function () {
        $("#repUserCode").on("change", function () {
            getUserInfo($("#repUserCode").val());
        })
    })

    //获取复核人员的详细信息
    function getUserInfo(userNo) {
        $.post("<%=path%>/fdOper/getUserInfo.htm?userNo=" + userNo, null, function (
            result) {
            $("#repUserName").val(result.operName);
            $("#repRoleName").val(result.roleName);
            $("#repUserOrganCode").val(result.organCode);
            $("#repUserOrganName").val(result.organName);
            $("#repUserName1").html(result.operName);
            $("#repRoleName1").html(result.roleName);
            $("#repUserOrganCode1").html(result.organCode);
            $("#repUserOrganName1").html(result.organName);
        }, "json");

    }

    //弹出增、删、改、查框
    function showDialog(url, title, width, height) {
        if (width == '') {
            width = 600;
        }
        if (height == '') {
            height == 480;
        }
        top.Dialog.open({
            URL: url,
            Title: title,
            Width: width,
            Height: height
        });
    }

    //比较日期大于
    function compareDate(beginDate, endDate) {
        var d1 = parseFloat(beginDate.replace(/\-/g, "\/"));
        var d2 = parseFloat(endDate.replace(/\-/g, "\/"));
        return d1 > d2;
    }

    //比较日期大于等于
    function compareDateE(beginDate, endDate) {
        var d1 = parseFloat(beginDate.replace(/\-/g, "\/"));
        var d2 = parseFloat(endDate.replace(/\-/g, "\/"));
        return d1 >= d2;
    }

    //处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
    function forbidBackSpace(e) {
        var ev = e || window.event; //获取event对象
        var obj = ev.target || ev.srcElement; //获取事件源
        var t = obj.type || obj.getAttribute('type'); //获取事件源类型
        //获取作为判断条件的事件类型
        var vReadOnly = obj.readOnly;
        var vDisabled = obj.disabled;
        //处理undefined值情况
        vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
        vDisabled = (vDisabled == undefined) ? true : vDisabled;
        //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
        //并且readOnly属性为true或disabled属性为true的，则退格键失效
        var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
        //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
        var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
        //判断
        if (flag2 || flag1) return false;
    }

    //禁止后退键 作用于Firefox、Opera
    document.onkeypress = forbidBackSpace;
    //禁止后退键  作用于IE、Chrome
    document.onkeydown = forbidBackSpace;

    function calDay(today, addMum) {
        var nowDay = new Date(today.substring(0, 4) + "-" + today.substring(4, 6) + "-" + today.substring(6, 8)).getTime();
        var destDay = new Date(nowDay + 24 * 60 * 60 * 1000);
        var year = destDay.getFullYear();
        var month = destDay.getMonth() + 1;
        if (month < 10) month = "0" + month;
        var date = destDay.getDate();
        if (date < 10) date = "0" + date;
        return year + "" + month + "" + date;
    }
</script>