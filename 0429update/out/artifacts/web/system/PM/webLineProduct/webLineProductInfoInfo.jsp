<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_info.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title></title>
    <!-- 树组件 -->
    <link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            var setting1 = {
                check: {enable: false}
            };
            var zNodes = [];
            var lineId = $('#lineId').val();
            var selectedCombUrl = "<%=path%>/webLineProduct/listAllSelectedProductLine.htm?lineId=" + lineId;
            $.ajax({
                    type: 'GET', url: selectedCombUrl, async: false,
                    success: function (data) {
                        var dataObject = JSON.parse(data);
                        var nodes = dataObject.data;
                        for(var i=0;i<nodes.length;i++){
                            var item=nodes[i];
                            item.icon = "<%=path%>/libs/icons/item.gif";
                            zNodes.push(item);
                        };
                    }
                }
            );
            $.fn.zTree.init($("#tree"), setting1, zNodes);
        };
    </script>
</head>
<body>
<form id="form1">
    <div class="basicTabModern">
        <div name="条线信息" style="width:100%;height:80%;">
            <input style="display: none;" id="lineId" type="text" value="${webLineProduct.lineId}"/>
            <table class="tableStyle" width="80%" mode="list" formMode="line">
                <tr>
                    <td align="right">
                        机构号：
                    </td>
                    <td>
                        ${organCode}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        机构名称：
                    </td>
                    <td>
                        ${organName}
                    </td>
                </tr>
                <tr>
                    <td align="right">条线编号：</td>
                    <td>
                        ${webLineProduct.lineId}
                    </td>
                </tr>
                <tr>
                    <td align="right" width="38%">条线名称:</td>
                    <td id="lineName">
                        ${webLineProduct.lineName }
                    </td>
                </tr>
                <tr>
                    <td align="right" width="38%">条线描述:</td>
                    <td>
                        <textarea disabled="true" rows="50" cols="150" id="lineDescription"
                                  name="lineDescription" style="resize:none;">${webLineProduct.lineDescription}</textarea>
                    </td>
                </tr>
                <tr>
                    <td align="right" id="lineUpdater">条线创建者：</td>
                    <td>${webLineProduct.lineCreator}</td>
                </tr>
                <tr>
                    <td align="right" id="createTime">创建日期：</td>
                    <td>
                        ${webLineProduct.createTime}
                    </td>
                </tr>
                <tr>
                    <td align="right">最后修改人：</td>
                    <td>
                        ${webLineProduct.lineUpdater}
                    </td>
                </tr>
                <tr>
                    <td align="right">最后修改时间：</td>
                    <td>
                        ${webLineProduct.updateTime}
                    </td>
                </tr>
            </table>
        </div>
        <div name="条线产品组合明细" style="width:100%;height:80%;">
            <fieldset>
                <legend>条线产品明细信息</legend>
                <div>
                    <ul id="tree" class="ztree"></ul>
                </div>
            </fieldset>
        </div>
    </div>
</form>
</body>
</html>