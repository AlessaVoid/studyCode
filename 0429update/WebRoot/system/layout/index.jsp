        <%@ page language="java" contentType="text/html; charset=UTF-8"
                 pageEncoding="UTF-8" %>
                <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                        <%
	String path = request.getContextPath();
	String menus=(String)request.getSession().getAttribute("menus");
%>
                <!DOCTYPE html>
                <html lang="zh-CN">
                <head>
                <meta charset="utf-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>限额管理系统</title>
                <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
                <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
                <!--[if lt IE 9]>
                <script src="<%=path%>/system/login/js/html5shiv.js"></script>
                <script src="<%=path%>/system/login/js/respond.js"></script>
                <![endif]-->
                <link href="<%=path%>/system/login/css/main.css" rel="stylesheet">
                <link href="<%=path%>/system/login/css/index.css" rel="stylesheet">
                <script type="text/javascript" src="<%=path%>/system/layout/js/menu.js"></script>
                <!--框架必需start-->
                <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
                <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
                <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
                <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/" splitMode="true" href="<%=path%>/libs/skins/blue/style.css"/>
                <link rel="stylesheet" type="text/css"  href="<%=path%>/system/layout/skin/style.css"/>
                <link rel="stylesheet" type="text/css"  href="<%=path%>/libs/css/jquery.marquee.css"/>
                <!--动态选项卡start-->
                <script type="text/javascript" src="<%=path%>/libs/js/nav/dynamicTab.js"></script>
                <!--动态选项卡end-->
                <script type="text/javascript" src="<%=path%>/libs/js/jquery.marquee.js"></script>
                <script type="text/javascript" src="<%=path%>/libs/js/main.js"></script>
                <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
                <!--框架必需end-->
                <!--弹窗组件start-->
                <script type="text/javascript" src="<%=path%>/libs/js/popup/drag.js"></script>
                <script type="text/javascript" src="<%=path%>/libs/js/popup/dialog.js"></script>
                <!--弹窗组件end-->
                <!--弹出式提示框start-->
                <script type="text/javascript" src="<%=path%>/libs/js/popup/messager.js"></script>
                <!--弹出式提示框end-->
                <!--浮动面板start-->
                <script type="text/javascript" src="<%=path%>/libs/js/popup/floatPanel.js"></script>
                <!--浮动面板end-->

                <script type="text/javascript">
                var  tab;
                var currentTabId;
                $(document).ready(function(){
                tab= new TabView({
                containerId :'tab_menu',
                pageid :'iframe_index',
                cid :'iframe_tab',
                position :"top"
                });
                $("body").bind("dynamicTabActived",function(e,tabId){
                currentTabId=tabId;
                })
                var menu=eval(<%=menus%>);
                loadMenu(menu);
                //初始化时查询消息数
                getAppCount();
                //获取刷新频率，按频率定时刷新
                $.post("<%=path%>/webMsg/refreshTime.htm",{},function(interval){
                refreshMsg(interval);
                },"Json");
                $("#marquee").marquee();
                //展出式弹窗
                showReviewMsgs();
                //添加浮动表
                } );
                function getCurrentTabId(){
                return currentTabId;
                }
                function addFloatTab(myId, myUrl, myTitle) {
                if (!myId||!myUrl||!myTitle||!tab){
                return;
                }
                var myFormatData=myId.replace("-","");
                var data={
                id: myFormatData, //todo 不能有特殊字符
                title: myTitle,
                url:myUrl,
                isClosed: true
                };
                tab.add(data);
                tab.activate(myFormatData);
                }
                function openWindow(){
                var diag = new top.Dialog();
                diag.URL = "<%=path%>/webReviewMain/NetvalueInfoList.htm";
                diag.Title = '净值维护提醒';
                diag.Width = 600;
                diag.Height = 400;
                diag.show();
                }
                function showReviewMsgs(){
                var $ul="<ul>";
                $.post("<%=path%>/webReviewMain/getReviewMsgs.htm",function(result){
                var array=eval("("+result.msg+")");
                $.each(array,function(i,n){
                $ul+="<li><a href=\"javascript:openWin("+n.appNo+",'"+n.operDescribe+"',"+n.appType+")\">"+n.operDescribe+"("+n.operName+")</span></a></li><div class=\"clear\"></div>";
                });
                <%--$.post("<%=path%>/webReviewMain/getLeftReviewMsgs.htm",function(result2){--%>
                <%--var array2=eval("("+result2.msg+")");--%>
                <%--if(array2.length>0){--%>
                <%--$ul+="<li><a href=\"javascript:openWindow()\">"+"净值维护提醒！"+"</a></li><div class=\"clear\"></div>";--%>
                <%--}--%>
                <%--&lt;%&ndash;$.post("<%=path%>/webReviewMain/getExchangeRate.htm",function(result3){&ndash;%&gt;--%>
                <%--&lt;%&ndash;var array3=eval("("+result3.msg+")");&ndash;%&gt;--%>
                <%--&lt;%&ndash;$.each(array3,function(i,n){&ndash;%&gt;--%>
                <%--&lt;%&ndash;$ul+="<li>请维护产品"+n.prodCode+"的汇率！</li><div class=\"clear\"></div>";&ndash;%&gt;--%>
                <%--&lt;%&ndash;});&ndash;%&gt;--%>
                <%--&lt;%&ndash;$ul+="</ul>";&ndash;%&gt;--%>
                <%--&lt;%&ndash;$.messager.lays(300, 200);&ndash;%&gt;--%>
                <%--&lt;%&ndash;$.messager.show('待办信息',$ul,'stay');&ndash;%&gt;--%>
                <%--&lt;%&ndash;},"json");&ndash;%&gt;--%>
                <%--},"json");--%>
                },"json");
                }
                function openWin(appNo,operDescribe,appType){
                var diag = new top.Dialog();
                var type = "";
                if(appType=="0"){
                type = "新增";
                }else if(appType=="1"){
                type = "修改";
                }else if(appType=="2"){
                type = "删除";
                }
                var title = type + "【" +operDescribe + "】" + "复核";

                diag.URL = "<%=path%>/webReviewMain/seachWebReviewMain.htm?appNo=" + appNo + "&appType="+appType +
                "&urlType=check";
                diag.Title = title;
                diag.Width = 600;
                diag.Height = 500;
                diag.show();
                }
                var iCount;

                //固定时间段刷新一次待办记录数
                function refreshMsg(interval){
                iCount=setInterval(getAppCount,interval);
                setInterval(showReviewMsgs,interval);
                }

                //获取待办记录数
                function getAppCount(){
                $.ajax({
                url: '<%=path%>/webMsg/getWebMsgCount.htm',
                type: 'POST',
                error: function(){
                clearInterval(iCount);
                },
                success: function(result){
                if(result.length>10){
                window.location.href='<%=path%>/timeout.htm';
                }else{
                if(result>99){
                $("#count").html("99+");
                }else{
                $("#count").html(result);
                }
                }
                }
                });
                }

                //退出系统
                function exitSystem(){
                window.location.href="<%=path%>/exit.htm";
                }

                //获取待办消息
                function getNotice(){
                var diag = new top.Dialog();
                diag.URL = "<%=path%>/webMsg/getWebMsgList.htm",
                diag.Title = "待办信息";
                diag.Width = 1400;
                diag.Height = 600;

                diag.CancelEvent= function(){
                    diag.close();
                    getAppCount();
                };
                diag.show();

                }

                //修改个人信息
                function insertOrUpdate(){
                top.Dialog.open({
                //发起请求，后台查询待办内容，并返回通知消息页面
                URL:"<%=path%>/webOperInfo/insertOrUpdate.htm",
                Title:"个人信息",
                Width:500,
                Height:300
                });
                }

                //转授权管理
                function webSublicenseInfo(){
                top.Dialog.open({
                URL:"<%=path%>/system/PM/webSublicenseInfo/webSublicenseInfoEdit.jsp",
                Title:"转授权管理",
                Width:750,
                Height:400
                });
                }
                //密码修改
                function userPasswordChange(){
                addFloatTab("PM-13","<%=path%>/system/PM/fdOper/FdOperUpdatePwd.jsp","柜员密码修改")
                }


                //验证柜员是否能够执行转授权管理交易
                function checkWebSublicenseInfo(){
                $.post("<%=path%>/webSublicenseInfo/checkRole.htm",
                function(result) {
                if (result. success == "true" || result. success == true) {
                webSublicenseInfo();
                } else {
                top.Dialog.alert(result.msg);
                }
                },"json");
                }
                </script>
                </head>
                <body>
                <!--header-->
                <input type="hidden" id="jy">
                <input type="hidden" id="cs">
                <input type="hidden" id="mb">
                <div class="header clearfix">
                <div class="logo">logo</div>
                <ul id="marquee" class="marquee">
                <c:forEach items="${msg}" var="ele">
                        <li><font size="4" color="red">${ele.content}</font></li>
                </c:forEach>
                </ul>
                <ul class="top-nav clearfix">
                <li>业务系统时间:${sysTime}</li>
                <li class="notice"><a href="#" title="待办信息" onclick="getNotice();" keepDefaultStyle="true"><i
                id="count"></i></a></li>
                <%--        <li class="audit"><a href="#" title="转授权" onclick="checkWebSublicenseInfo();" keepDefaultStyle="true"></a></li>--%>
                <li class="audit"><a href="#" title="柜员密码修改" onclick="userPasswordChange()" keepDefaultStyle="true"></a></li>

                <li class="gr-mess"><a href="#" title="个人中心" keepDefaultStyle="true" onclick="insertOrUpdate()"></a></li>
                <li class="exit"><a href="#" title="退出" keepDefaultStyle="true"
                onclick='top.Dialog.confirm("确定要退出本系统吗？",function(){window.location.href="<%=path%>/exitSignout.htm";});'></a></li>
                </ul>
                </div>
                <!--top-menu-->
                <div class="top-menu clear">
                <h1>限额管理系统</h1>
                <ul class="top-menu-lis clear" id="top_menu">
                </ul>
                </div>
                <!--左快捷栏 start-->
<%--                <div class="floatPanel" style="margin-top: 4%" panelWidth="200" panelHeight="800" direction="ml" init="hide"--%>
<%--                animatefirst="false" iframe="<%=path%>/system/layout/index_menu.jsp" beforeClickText="快捷菜单" afterClickText="快捷菜单">--%>
<%--                </div>--%>
                <!--左快捷栏 end-->
                <!--mid-->
                <div class="clearfix" style="width: 98%;margin-left: 2%">
                <div class="mid-con" style="width: 98%">
                <div id="tab_menu"></div>
                <div id="iframe_index" style="width:100%;height:94%;border:solid 1px #cccccc;"></div>
                </div>
                </div>
<%--                <div class="floatPanel" style="margin-top: 4%" panelWidth="110" panelHeight="500"--%>
<%--                direction="mr" init="hide" animatefirst="false"--%>
<%--                iframe="<%=path%>/system/layout/index_right.jsp"--%>
<%--                beforeClickText="工具箱" afterClickText="工具箱"></div>--%>
                <%--            </div>--%>
                <!--footer-->
                <%--        <div class="footer">版权所有：www.boco.com.cn</div>--%>

                <script>
                var Hei=$(window).height();
                var Wid=$(window).width();
                var H=Hei - 130;
                var Hs=Hei - 175;
                var W=Wid-260;
                $(".le-menu").css("height",H +"px");
                $(".menu-lis").css("height",Hs +"px");
                $(".mid-con").css("height",H +"px");

                $(function(){
                //加载首页
                $.ajax({
                method : 'POST',
                url : '<%=path%>/indexData/isShowIndexData.htm',
                dataType : 'json',
                success : function(result) {
                if(result == true){
                var data={
                id : 'index',
                title : '首页',
                url:'<%=path%>/indexData/toIndex.htm',
                isClosed:false
                };
                tab.add(data);
                tab.activate('index');
                }
                }
                });

                //报表管理解决火狐滚动条重置问题
                $("body").bind("dynamicTabActived",function(e,tabId){
                try{
                if(tabId == 'RE01' || tabId == 'RE02' || tabId.indexOf('RE04') >= 0){
                document.getElementById('page_'+tabId).contentWindow.scrollBar();
                }
                }catch(e){
                }
                })
                });
                </script>
                </body>
                </html>