    <%@ page contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            <%String path = request.getContextPath();%>
            <%String menus=(String)request.getSession().getAttribute("menus");%>
        <!DOCTYPE html>
        <html lang="zh-CN">
        <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<%=path%>/system/login/css/main.css" rel="stylesheet">
        <link href="<%=path%>/system/login/css/index.css" rel="stylesheet">
        <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/" scrollerY="false"/>
        <link rel="stylesheet" type="text/css" id="customSkin" href="<%=path%>/libs/skins/blue/style.css"/>
        <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
        <script type="text/javascript" src="<%=path%>/system/layout/js/menu.js"></script>
        <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
            <!--动态选项卡start-->
            <script type="text/javascript" src="<%=path%>/libs/js/nav/dynamicTab.js"></script>
        <script type="text/javascript">
        $(document).ready(function(){
        var shortMenus=eval(${shortMenus});
        loadFirstMenu(shortMenus);
        },"Json");
        </script>
        </head>
        <body>
        <div class="le-menu" style="width:100%">
        <ul class="menu-lis" id="menus-lis">
        </ul>
        </div>
        </body>
        </html>