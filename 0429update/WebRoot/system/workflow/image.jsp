<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http:/boco.com.cn/tags-dic" prefix="dic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http:/boco.com.cn/tags-fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fm" %>
<%
    String path = request.getContextPath();
    request.setAttribute("path", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
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
    <title>当前流程图</title>
</head>
<script>
    $(function () {
        var isNew = "${isNew}"
        if (isNew == "false") {
            top.Dialog.alert("流程状态已改变，请刷新页面!", function() {
                var menu_id = parent.getCurrentTabId();
                if(menu_id==undefined){
                    top.Dialog.close();
                    return;
                }
                var menu_frame_id = "page_" + menu_id;
                top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                top.Dialog.close();
            })
        }

    })
</script>
<body>
<form>


    <div style="height: 600px;overflow: auto">
        <!-- 画小红框 -->
        <div style="position: relative;border: red solid 1px;top: ${coordinate.y+70}px;left:${coordinate.x}px;height:${coordinate.height}px;width:${coordinate.width}px;"
             title="${coordinate.documentation}"></div>

        <!-- 流程图片 -->
        <img src="<%=path%>/workflow/loadImgByProcessInstance.htm?taskId=${taskId }&processInstanceId=${processInstanceId}&resourceType=image"
             alt="" style="overflow: auto"/>
        <%--			<img src="<%=path%>/workflow/loadImgByProcessInstance.htm?taskId=${taskId }&processInstanceId=${processInstanceId}&resourceType=image" alt="" style="position:absolute; left:0px; top:0px;"/>--%>
    </div>

    <div>
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
                            <td> ${comment.type }</td>
                            <td style="word-break:break-all">${comment.fullMessage }</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </div>
</form>
</body>

</html>
