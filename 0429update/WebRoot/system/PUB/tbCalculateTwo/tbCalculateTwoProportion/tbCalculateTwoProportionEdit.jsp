<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
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

    //用户只能输入正负数与小数
    function upperCase(obj) {
        if (isNaN(obj.value) && !/^-$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }
    }

    $(function () {
        //输入框获取焦点事件
        $(".weight").focus(function () {
            if (this.value == 0) {
                this.value = "";
            }
        });
        //输入框失去焦点事件
        $(".weight").blur(function () {
            if ($.trim(this.value) == "") {
                this.value = 0;
            }
        });

    })




    function submitInfo() {

        return doSubmit('form1', '<%=path%>/tbCalculateTwoProportion/update.htm');
    }

</script>


<body>


<form id="form1" class="ali02" >
    <div id="scrollContent" class="border_gray" style="height: 500px;">
        <table class="tableStyle" width="80%" mode="list">


            <tr>
                <td width="25%">
                    <div align="center">序号</div>
                </td>
                <td width="25%">
                    <div align="center">参数名称</div>
                </td>
                <td width="25%">
                    <div align="center">顺序类别</div>
                </td>
                <td width="25%">
                    <div align="center">权重系数</div>
                </td>
            </tr>


            <tr>
                <td style="display: table-cell;vertical-align: middle">1</td>
                <td colspan="1">
                    <input id="code_0" name="code_0" value="${TbCalculateTwoProportion_0.code}" hidden="hidden"/>
                    <input id="name_0" name="name_0" value="${TbCalculateTwoProportion_0.name}" hidden="hidden"/>
                    <span>${TbCalculateTwoProportion_0.name}</span>
                </td>
                <td align="center" colspan="1">
                    <dic:select id="sortType_0" dicType="SORT_TYPE" name="sortType_0"
                                dicNo="${TbCalculateTwoProportion_0.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td align="center" colspan="1">
                    <input id="weight_0" class="weight" oninput='upperCase(this)' name="weight_0" value="${TbCalculateTwoProportion_0.weight}"/>
                </td>
            </tr>


            <tr>
                <td>2</td>
                <td colspan="1">
                    <input id="code_1" name="code_1" value="${TbCalculateTwoProportion_1.code}" hidden="hidden"/>
                    <input id="name_1" name="name_1" value="${TbCalculateTwoProportion_1.name}" hidden="hidden"/>
                    <span>${TbCalculateTwoProportion_1.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_1" dicType="SORT_TYPE" name="sortType_1"
                                dicNo="${TbCalculateTwoProportion_1.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_1" class="weight" oninput='upperCase(this)' name="weight_1" value="${TbCalculateTwoProportion_1.weight}"/>
                </td>
            </tr>

            <tr>
                <td>3</td>
                <td colspan="1">
                    <input id="code_2" name="code_2" value="${TbCalculateTwoProportion_2.code}" hidden="hidden"/>
                    <input id="name_2" name="name_2" value="${TbCalculateTwoProportion_2.name}" hidden="hidden"/>
                    <span>${TbCalculateTwoProportion_2.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_2" dicType="SORT_TYPE" name="sortType_2"
                                dicNo="${TbCalculateTwoProportion_2.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_2" class="weight" oninput='upperCase(this)' name="weight_2" value="${TbCalculateTwoProportion_2.weight}"/>
                </td>
            </tr>


            <tr>
                <td>4</td>
                <td colspan="1">
                    <input id="code_3" name="code_3" value="${TbCalculateTwoProportion_3.code}" hidden="hidden"/>
                    <input id="name_3" name="name_3" value="${TbCalculateTwoProportion_3.name}" hidden="hidden"/>
                    <span>${TbCalculateTwoProportion_3.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_3" dicType="SORT_TYPE" name="sortType_3"
                                dicNo="${TbCalculateTwoProportion_3.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_3" class="weight" oninput='upperCase(this)' name="weight_3" value="${TbCalculateTwoProportion_3.weight}"/>
                </td>
            </tr>


            <tr>
                <td colspan="10">
                    <div align="center">
                        <button type="button" onclick="submitInfo()" class="saveButton" colspan="5"/>
                        <button type="button" onclick="return cancel()" class="cancelButton" colspan="5"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>
</body>

</html>