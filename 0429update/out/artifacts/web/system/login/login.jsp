<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>限额管理系统-业务管理端</title>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="<%=path%>/system/login/js/html5shiv.js"></script>
    <script src="<%=path%>/system/login/js/respond.js"></script>
    <![endif]-->
    <link href="<%=path%>/system/login/css/main.css" rel="stylesheet">
    <link href="<%=path%>/system/login/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script src="<%=path%>/system/login/js/jQuery.md5.js"></script>
    <style type="text/css">
        .login_info {
            position: relative;
            top: -90px;
            left: 69px;
        }
    </style>
    <script type="text/javascript">
        //设置Cookie保存时间
        var time = 0;
        $(document).ready(function () {
            //获取Cookie保存的用户名和密码
            var opercode = getCookieValue("cookUser");
            var operpassword = getCookieValue("cookPass");
            //输入用户名 促发一个事件
            $("#opercode").keyup(function () {
                var userNow = $(this).val();
                if (userNow == opercode) { //判断现在输入的用户名  和 当时保存在cookie的用户名是否一致
                    $("#operpassword").val(operpassword);//一致 则把 第一次 保存在cookie的密码 自动填入
                    $("#rempwd").attr("checked", "checked");//记住密码选项默认选中
                }
            });
            $("#rempwd").click(function () {//记住密码
                if ($(this).attr("checked") == 'checked') {
                    time = 60 * 60 * 60;
                }
            });
        });

        //添加监听回车事件
        function keyLogin(event) {
            if (event.keyCode == 13)  //回车键的键值为13
                lc_login();
        }

        function validatePass() {
            var ss = $("#operpassword").val();
            var pa = /^(.{12,16})$/;
            // if (!pa.exec(ss)) {
            // 	alert("请注意:密码长度需为12到16位,请及时修改");
            // }
        }

        //登录
        function lc_login() {
            var errorMsg = "";
            var organcode = $("#organcode").val();
            var opercode = $("#opercode").val();
            var operpassword = $("#operpassword").val();
            validatePass();
            // if (!organcode) {
            //     errorMsg += "&nbsp;&nbsp;机构号不能为空!";
            // }
            if (!opercode) {
                errorMsg += "&nbsp;&nbsp;用户名不能为空!";
            }
            if (!operpassword) {
                errorMsg += "&nbsp;&nbsp;密码不能为空!";
            }
            // 页面md5加密
            // var salt = "dhjdfu34i34u34-zmew8732dfhjd-"+opercode+"dfhjdf8347sdhxcye-ehjcbeww34";
            // operpassword =$.md5(operpassword+"{"+salt+"}");

            if (errorMsg != "") {
                $(".login_info").html(errorMsg);
                $(".login_info").show();
            } else {
                $(".login_info").show();
                $(".login_info").css("color", "black");
                $(".login_info").html("&nbsp;&nbsp;正在登录中...");
                //登录处理
                $.post("<%=path%>/login.htm", {
                    organcode: organcode,
                    opercode: opercode,
                    operpassword: operpassword
                }, function (result) {
                    if (result == null) {
                        $(".login_info").html("&nbsp;&nbsp;登陆失败！");
                        return false;
                    }
                    if (result.success == "true" || result.success == true) {
                        //set 获取用户名和密码 传给cookie
                        setCookie('cookUser', opercode, time, '/');
                        setCookie('cookPass', operpassword, time, '/');
                        $(".login_info").html("&nbsp;&nbsp;" + result.msg);
                        window.location = "<%=path%>/menu.htm";
                    } else {
                        $(".login_info").css("color", "red");
                        $(".login_info").html("&nbsp;&nbsp;" + result.msg);
                    }
                }, "json");
            }
        }
    </script>
    <script type="text/javascript" src="<%=path%>/system/login/js/cookie.js"></script>
</head>
<body onkeydown="keyLogin(event);">
<div class="dl-bg">
    <form id="loginForm" action="login.do" class="login_form"
          method="post">
        <div class="dl-con">
            <div class="dl-title">
                <span><img src="<%=path%>/system/login/images/dl-logo.png"></span>
            </div>
            <div class="dl-text">
                <dl class="input-text clearfix">
<%--                    <dt>机构号</dt>--%>
<%--                    <dd>--%>
<%--                        <input type="text" class="dl-put-class" id="organcode" value="11005293">--%>
<%--                    </dd>--%>
                    <dt>用户名</dt>
                    <dd>
                        <input type="text" class="dl-put-class" id="opercode" value="20132364701">
                    </dd>
                    <dt>密码</dt>
                    <dd>
                        <input type="password" class="dl-put-class" id="operpassword" maxlength="8"
                               value="000000"/>
                    </dd>
                </dl>
            </div>
            <div class="dl-bot">
                <dl>
                    <dt>
                        <input type="checkbox" id="rempwd"> 记住密码
                    </dt>
                    <dd>
                        <input class="btn btn-dl" type="button" onclick="lc_login();" id="login" value="登 录"/>
                    </dd>
                </dl>
            </div>
            <div class="login_info" style="display: none"></div>
        </div>
    </form>
</div>
<script>
    var Hei = $(window).height();
    var H = Hei;
    $(".dl-bg").css("height", H + "px");
</script>
</body>
</html>