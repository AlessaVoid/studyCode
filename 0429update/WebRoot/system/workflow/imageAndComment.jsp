<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>当前流程图</title>
</head>
<body>
<div id="panel" panelTitle="审批流程图" class="box2_custom" boxType="box2" startState="open" style="height: 700px;" showStatus="false">
    <!-- 流程图片 -->
    <img src="<%=path%>/workflow/loadImgByProcessInstance.htm?taskId=${taskId }&processInstanceId=${processInstanceId}&resourceType=image"
         alt="" style="position:absolute; left:10px; top:40px;"/>
    <!-- 画小红框 -->
    <div style="position: absolute;border: red solid 1px;top: ${coordinate.y+40}px;left:${coordinate.x+10}px;height:${coordinate.height}px;width:${coordinate.width}px;"
         title="${coordinate.documentation}"></div>
</div>


<div>
    <c:if test="${!empty comments }">
        <div id="panel23" panelTitle="审批进度" class="box2_custom" boxType="box2" startState="open">
            <table class="tableStyle tab-hei-30" width="80%" mode="list"
                   style="table-layout:fixed;word-wrap:break-word;word-break:break-all">
                <tr>
                    <td width="30%" align="left">
                        审批用户
                    </td>
                    <td width="30%" align="left">
                        审批时间
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
                        <td style="word-break:break-all">${comment.fullMessage }</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</div>


</body>
</html>