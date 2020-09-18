<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp"%>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <!-- 树组件start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path%>/libs/js/tree/ztree/ztree.css"></link>
    <!-- 树组件end -->
    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
    <title></title>
</head>
<script type="text/javascript">
    function initComplete() {

        //获取json数据
        $.post("<%=path%>/tbTradeManger/tbReqList/findComb.htm",
            {}, function (result) {
                //赋给data
                $("#warnComb").data("data", result)
                //刷新下拉框
                $("#warnComb").render();
            }, "json");

        $("#warnComb").setValue("${TbWarn.warnComb}");
    }
</script>


<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                预警参数名称：
            </td>
            <td>
                <input type="text" name="warnName" class="validate[required]"  disabled="disabled"  value="${TbWarn.warnName}" maxlength="20" required="true"/><span
                    class="star">*</span>
            </td>
            <td align="right">
                预警机构号:
            </td>
            <td>
                <input type="text" name="warnName" class="validate[required]"  disabled="disabled"  value="${TbWarn.warnOrgan}" maxlength="20" required="true"/><span
                    class="star">*</span>
                
            </td>

        </tr>
        <tr>
            <td align="left">预警贷种：</td>
            <td>
                <%--<input type="text" name="warnComb" class="validate[required]" disabled="disabled"  maxlength="20" value="${TbWarn.warnComb}"  required="true"/>--%>
                <select id="warnComb" name="warnComb" disabled="disabled"></select>
                <span
                    class="star">*</span>
            </td>
            <td align="left">预警线类型：</td>
            <td>
                <dic:select id="warnType" dicType="WARN_TYPE" name="warnType"  disabled="disabled"  dicNo="${TbWarn.warnType}"  tgClass="validate[required]"
                            required="true"></dic:select>
                
            </td>
            <%--<td colspan="2"></td>--%>
        </tr>
        <tr>
            <td align="left">
                一级预警线：
            </td>
            <td>
                <input type="text" id="warnOneLine" name="warnOneLine"   disabled="disabled"  value="${TbWarn.warnOneLine}" class="validate[required]"
                       maxlength="20"
                       placeholder="请输入数字" required="true"/>

                
                <%--<div class="selectTree validate[required]" id="deptCode" name="deptCode" keepDefaultStyle="true"></div>--%>
            </td>
            <td align="right">
                二级预警线：
            </td>
            <td>
                <input type="text" id="warnTwoLine" name="warnTwoLine" disabled="disabled"   value="${TbWarn.warnTwoLine}" class="validate[required]"
                       maxlength="20"
                       placeholder="请输入数字" required="true"/>

                
                <%--<input type="text" name="operCode"  class="validate[required],custom[onlyNumber]" maxlength="11"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right">
                三级预警线：
            </td>
            <td>
                <input type="text" id="warnThreeLine" name="warnThreeLine"  disabled="disabled" value="${TbWarn.warnThreeLine}"  class="validate[required]"
                       placeholder="请输入数字"
                       required="true"/>
            </td>
            <td align="right">
                四级预警线:
            </td>
            <td>
                <input type="text" id="warnFourLine" name="warnFourLine"  disabled="disabled"  value="${TbWarn.warnFourLine}"  class="validate[required]"
                       placeholder="请输入数字"
                       required="true"/>
            </td>
        </tr>
        <tr>
            <td align="right">
                五级预警线:
            </td>
            <td>
                <input type="text" id="warnFiveLine" name="warnFiveLine" disabled="disabled"  value="${TbWarn.warnFiveLine}" class="validate[required]"
                       placeholder="请输入数字"
                       required="true"/>
            </td>

            <td align="right">
                创建人员id:
            </td>
            <td>
                <input type="test" name="warnCreateOper"  value="${TbWarn.warnCreateOper}"   disabled="disabled"  class="validate[required]"  min="20" max="9999999999"/>
            </td>
        </tr>

        <tr>
            <td align="right">
                更新人员id:
            </td>
            <td>
                <input type="test" name="warnUpdateOper"  value="${TbWarn.warnUpdateOper}"   disabled="disabled"  class="validate[required]"  min="20" max="9999999999"/>
            </td>

            <td align="right">
                创建时间:
            </td>
            <td>
                <input type="test" name="warnCreateTime"  value="${TbWarn.warnCreateTime}"   disabled="disabled"  class="validate[required]"  min="20" max="9999999999"/>
            </td>
        </tr>

        <tr>
            <td align="right">
                更新时间:
            </td>
            <td>
                <input type="test" name="warnUpdateTime"  value="${TbWarn.warnUpdateTime}"   disabled="disabled"  class="validate[required]"  min="20" max="9999999999"/>
            </td>
        </tr>

    </table>
</form>
</body>
</html>