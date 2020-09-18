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
            initLoanCombSelectedNodes();
        };

        function transferLoanState(status) {
            if (status === '-1' || status === -1) {
                return '已删除';
            } else if (status === '1' || status === 1) {
                return '机构已组合';
            } else if (status === 0 || status === "3") {
                return '条线-已组合';
            } else if (status === 4 || status === "4") {
                return '机构条线-条线-已组合';
            }
        }

        function initLoanCombSelectedNodes() {
            var setting = {check: {enable: false}};
            var combCode = $('#combCode').val();
            var combLevel = $("#combLevel").val();
            var zNodes = [];
            var selectedCombUrl = "<%=path%>/webLoan/listAllSelectedLoanCombByLevelAndCode.htm?combCode=" + combCode + "&combLevel=" + combLevel;
            $.ajax({
                type: 'GET', url: selectedCombUrl, async: false,
                success: function (data) {
                    var dataObject = JSON.parse(data);
                    var nodes = dataObject.data;
                    for (var i = 0; i < nodes.length; i++) {
                        var item = nodes[i];
                        item.icon = "<%=path%>/libs/icons/item.gif";
                        zNodes.push(item);
                    }
                }
            });
            $.fn.zTree.init($("#tree"), setting, zNodes);
        }
    </script>
</head>
<body>
<form id="form1">
    <div class="basicTabModern">
        <div name="贷种信息" style="width:100%;height:80%;">
            <input id="combCode" style="display: none" value="${webLoanComb.combCode}">
            <input id="combLevel" style="display: none" value="${webLoanComb.combLevel}">
            <table class="tableStyle" width="80%" mode="list" formMode="line">
                <tr>
                    <td align="right">
                        贷种编码：
                    </td>
                    <td>
                        ${webLoanComb.combCode}
                    </td>
                </tr>
                <tr>
                    <td align="right" width="38%">
                        贷种名称:
                    </td>
                    <td id="combName">
                        ${webLoanComb.combName }
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        贷种级别：
                    </td>
                    <td>
                        ${webLoanComb.combLevel==1?'一级':webLoanComb.combLevel==2?'二级':'三级'}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        贷种状态：
                    </td>
                    <td id="combStatus">
                        ${(webLoanComb.combStatus==-1)?'已删除':(webLoanComb.combStatus==1)?'已组合':'未组合'}
                    </td>
                </tr>
                <tr>
                    <td align="right" id="combCreator">
                        贷种创建者：
                    </td>
                    <td>
                        ${webLoanComb.combCreator}
                    </td>
                </tr>
                <tr>
                    <td align="right" id="createTime">
                        创建日期：
                    </td>
                    <td>
                        ${webLoanComb.combCreateTime}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        最后修改人：
                    </td>
                    <td>
                        ${webLoanComb.combUpdater}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        最后修改时间：
                    </td>
                    <td>
                        ${webLoanComb.combUpdateTime}
                    </td>
                </tr>
            </table>
        </div>
        <div name="贷种产品组合明细" style="width:100%;height:80%;">
            <fieldset>
                <legend>
                    贷种产品明细信息
                </legend>
                <div>
                    <ul id="tree" class="ztree"></ul>
                </div>
            </fieldset>
        </div>
    </div>
</form>
</body>
</html>