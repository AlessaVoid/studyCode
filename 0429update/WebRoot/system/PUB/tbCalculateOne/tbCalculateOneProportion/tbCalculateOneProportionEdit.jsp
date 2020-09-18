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
        var ratio_1 = $("#ratio_1").val();
        var ratio_2 = $("#ratio_2").val();
        var ratio_4 = $("#ratio_4").val();
        var ratio_8 = $("#ratio_8").val();

        var totalNum = Number(ratio_1) + Number(ratio_2) + Number(ratio_4) + Number(ratio_8);
        if (totalNum != 1) {
            top.Dialog.alert("请填写正确占比", null, null, null, 5);
            return
        }

        return doSubmit('form1', '<%=path%>/tbCalculateOneProportion/update.htm');
    }

</script>


<body>


<form id="form1" class="ali02">
    <div id="scrollContent" class="border_gray">
        <table class="tableStyle" width="80%" mode="list">


            <tr>
                <td width="10%">
                    <div align="center">序号</div>
                </td>
                <td width="18%">
                    <div align="center">参数名称</div>
                </td>
                <td width="18%">
                    <div align="center">类别</div>
                </td>
                <td width="18%">
                    <div align="center">指标类别</div>
                </td>
                <td width="18%">
                    <div align="center">顺序类别</div>
                </td>
                <td width="18%">
                    <div align="center">权重系数</div>
                </td>
            </tr>


            <tr>
                <td style="display: table-cell;vertical-align: middle">1</td>
                <td colspan="1">
                    <input id="code_0" name="code_0" value="${TbCalculateOneProportion_0.code}" hidden="hidden"/>
                    <input id="name_0" name="name_0" value="${TbCalculateOneProportion_0.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_0.name}</span>
                </td>
                <td align="center" colspan="1" nowrap="nowrap">
                    <dic:select id="classType_0" dicType="CLASS_TYPE" name="classType_0"
                                dicNo="${TbCalculateOneProportion_0.classType}"
                                tgClass="validate[required]" required="true"></dic:select>

                </td>
                <td align="center" colspan="1">
                    <dic:select id="indexType_0" dicType="INDEX_TYPE" name="indexType_0"
                                dicNo="${TbCalculateOneProportion_0.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td align="center" colspan="1">
                    <dic:select id="sortType_0" dicType="SORT_TYPE" name="sortType_0"
                                dicNo="${TbCalculateOneProportion_0.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td align="center" colspan="1">
                    <input id="weight_0" class="weight" oninput='upperCase(this)' name="weight_0" value="${TbCalculateOneProportion_0.weight}"/>
                </td>
            </tr>


            <tr>
                <td>2</td>
                <td colspan="1">
                    <input id="code_1" name="code_1" value="${TbCalculateOneProportion_1.code}" hidden="hidden"/>
                    <input id="name_1" name="name_1" value="${TbCalculateOneProportion_1.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_1.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_1" dicType="CLASS_TYPE" name="classType_1"
                                dicNo="${TbCalculateOneProportion_1.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_1" dicType="INDEX_TYPE" name="indexType_1"
                                dicNo="${TbCalculateOneProportion_1.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_1" dicType="SORT_TYPE" name="sortType_1"
                                dicNo="${TbCalculateOneProportion_1.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_1" class="weight" oninput='upperCase(this)' name="weight_1" value="${TbCalculateOneProportion_1.weight}"/>
                </td>
            </tr>

            <tr>
                <td>3</td>
                <td colspan="1">
                    <input id="code_2" name="code_2" value="${TbCalculateOneProportion_2.code}" hidden="hidden"/>
                    <input id="name_2" name="name_2" value="${TbCalculateOneProportion_2.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_2.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_2" dicType="CLASS_TYPE" name="classType_2"
                                dicNo="${TbCalculateOneProportion_2.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_2" dicType="INDEX_TYPE" name="indexType_2"
                                dicNo="${TbCalculateOneProportion_2.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_2" dicType="SORT_TYPE" name="sortType_2"
                                dicNo="${TbCalculateOneProportion_2.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_2" class="weight" oninput='upperCase(this)' name="weight_2" value="${TbCalculateOneProportion_2.weight}"/>
                </td>
            </tr>


            <tr>
                <td>4</td>
                <td colspan="1">
                    <input id="code_3" name="code_3" value="${TbCalculateOneProportion_3.code}" hidden="hidden"/>
                    <input id="name_3" name="name_3" value="${TbCalculateOneProportion_3.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_3.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_3" dicType="CLASS_TYPE" name="classType_3"
                                dicNo="${TbCalculateOneProportion_3.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_3" dicType="INDEX_TYPE" name="indexType_3"
                                dicNo="${TbCalculateOneProportion_3.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_3" dicType="SORT_TYPE" name="sortType_3"
                                dicNo="${TbCalculateOneProportion_3.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_3" class="weight" oninput='upperCase(this)' name="weight_3" value="${TbCalculateOneProportion_3.weight}"/>
                </td>
            </tr>

            <tr>
                <td>5</td>
                <td colspan="1">
                    <input id="code_4" name="code_4" value="${TbCalculateOneProportion_4.code}" hidden="hidden"/>
                    <input id="name_4" name="name_4" value="${TbCalculateOneProportion_4.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_4.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_4" dicType="CLASS_TYPE" name="classType_4"
                                dicNo="${TbCalculateOneProportion_4.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_4" dicType="INDEX_TYPE" name="indexType_4"
                                dicNo="${TbCalculateOneProportion_4.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_4" dicType="SORT_TYPE" name="sortType_4"
                                dicNo="${TbCalculateOneProportion_4.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_4" class="weight" oninput='upperCase(this)' name="weight_4" value="${TbCalculateOneProportion_4.weight}"/>
                </td>
            </tr>


            <tr>
                <td>6</td>
                <td colspan="1">
                    <input id="code_5" name="code_5" value="${TbCalculateOneProportion_5.code}" hidden="hidden"/>
                    <input id="name_5" name="name_5" value="${TbCalculateOneProportion_5.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_5.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_5" dicType="CLASS_TYPE" name="classType_5"
                                dicNo="${TbCalculateOneProportion_5.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_5" dicType="INDEX_TYPE" name="indexType_5"
                                dicNo="${TbCalculateOneProportion_5.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_5" dicType="SORT_TYPE" name="sortType_5"
                                dicNo="${TbCalculateOneProportion_5.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_5" class="weight" oninput='upperCase(this)' name="weight_5" value="${TbCalculateOneProportion_5.weight}"/>
                </td>
            </tr>

            <tr>
                <td>7</td>
                <td colspan="1">
                    <input id="code_6" name="code_6" value="${TbCalculateOneProportion_6.code}" hidden="hidden"/>
                    <input id="name_6" name="name_6" value="${TbCalculateOneProportion_6.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_6.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_6" dicType="CLASS_TYPE" name="classType_6"
                                dicNo="${TbCalculateOneProportion_6.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_6" dicType="INDEX_TYPE" name="indexType_6"
                                dicNo="${TbCalculateOneProportion_6.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_6" dicType="SORT_TYPE" name="sortType_6"
                                dicNo="${TbCalculateOneProportion_6.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_6" class="weight" oninput='upperCase(this)' name="weight_6" value="${TbCalculateOneProportion_6.weight}"/>
                </td>
            </tr>


            <tr>
                <td>8</td>
                <td colspan="1">
                    <input id="code_7" name="code_7" value="${TbCalculateOneProportion_7.code}" hidden="hidden"/>
                    <input id="name_7" name="name_7" value="${TbCalculateOneProportion_7.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_7.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_7" dicType="CLASS_TYPE" name="classType_7"
                                dicNo="${TbCalculateOneProportion_7.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_7" dicType="INDEX_TYPE" name="indexType_7"
                                dicNo="${TbCalculateOneProportion_7.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_7" dicType="SORT_TYPE" name="sortType_7"
                                dicNo="${TbCalculateOneProportion_7.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_7" class="weight" oninput='upperCase(this)' name="weight_7" value="${TbCalculateOneProportion_7.weight}"/>
                </td>
            </tr>


            <tr>
                <td>9</td>
                <td colspan="1">
                    <input id="code_8" name="code_8" value="${TbCalculateOneProportion_8.code}" hidden="hidden"/>
                    <input id="name_8" name="name_8" value="${TbCalculateOneProportion_8.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_8.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_8" dicType="CLASS_TYPE" name="classType_8"
                                dicNo="${TbCalculateOneProportion_8.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_8" dicType="INDEX_TYPE" name="indexType_8"
                                dicNo="${TbCalculateOneProportion_8.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_8" dicType="SORT_TYPE" name="sortType_8"
                                dicNo="${TbCalculateOneProportion_8.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_8" class="weight" oninput='upperCase(this)' name="weight_8" value="${TbCalculateOneProportion_8.weight}"/>
                </td>
            </tr>


            <tr>
                <td>10</td>
                <td colspan="1">
                    <input id="code_9" name="code_9" value="${TbCalculateOneProportion_9.code}" hidden="hidden"/>
                    <input id="name_9" name="name_9" value="${TbCalculateOneProportion_9.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_9.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_9" dicType="CLASS_TYPE" name="classType_9"
                                dicNo="${TbCalculateOneProportion_9.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_9" dicType="INDEX_TYPE" name="indexType_9"
                                dicNo="${TbCalculateOneProportion_9.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_9" dicType="SORT_TYPE" name="sortType_9"
                                dicNo="${TbCalculateOneProportion_9.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_9" class="weight" oninput='upperCase(this)' name="weight_9" value="${TbCalculateOneProportion_9.weight}"/>
                </td>
            </tr>

            <tr>
                <td>11</td>
                <td colspan="1">
                    <input id="code_10" name="code_10" value="${TbCalculateOneProportion_10.code}" hidden="hidden"/>
                    <input id="name_10" name="name_10" value="${TbCalculateOneProportion_10.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_10.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_10" dicType="CLASS_TYPE" name="classType_10"
                                dicNo="${TbCalculateOneProportion_10.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_10" dicType="INDEX_TYPE" name="indexType_10"
                                dicNo="${TbCalculateOneProportion_10.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_10" dicType="SORT_TYPE" name="sortType_10"
                                dicNo="${TbCalculateOneProportion_10.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_10" class="weight" oninput='upperCase(this)' name="weight_10" value="${TbCalculateOneProportion_10.weight}"/>
                </td>
            </tr>


            <tr>
                <td>12</td>
                <td olspan="1">
                    <input id="code_11" name="code_11" value="${TbCalculateOneProportion_11.code}" hidden="hidden"/>
                    <input id="name_11" name="name_11" value="${TbCalculateOneProportion_11.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_11.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_11" dicType="CLASS_TYPE" name="classType_11"
                                dicNo="${TbCalculateOneProportion_11.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_11" dicType="INDEX_TYPE" name="indexType_11"
                                dicNo="${TbCalculateOneProportion_11.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_11" dicType="SORT_TYPE" name="sortType_11"
                                dicNo="${TbCalculateOneProportion_11.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_11" class="weight" oninput='upperCase(this)' name="weight_11" value="${TbCalculateOneProportion_11.weight}"/>
                </td>
            </tr>


            <tr>
                <td>13</td>
                <td colspan="1">
                    <input id="code_12" name="code_12" value="${TbCalculateOneProportion_12.code}" hidden="hidden"/>
                    <input id="name_12" name="name_12" value="${TbCalculateOneProportion_12.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_12.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_12" dicType="CLASS_TYPE" name="classType_12"
                                dicNo="${TbCalculateOneProportion_12.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_12" dicType="INDEX_TYPE" name="indexType_12"
                                dicNo="${TbCalculateOneProportion_12.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_12" dicType="SORT_TYPE" name="sortType_12"
                                dicNo="${TbCalculateOneProportion_12.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_12" class="weight" oninput='upperCase(this)' name="weight_12" value="${TbCalculateOneProportion_12.weight}"/>
                </td>
            </tr>


            <tr>
                <td>14</td>
                <td colspan="1">
                    <input id="code_13" name="code_13" value="${TbCalculateOneProportion_13.code}" hidden="hidden"/>
                    <input id="name_13" name="name_13" value="${TbCalculateOneProportion_13.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_13.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_13" dicType="CLASS_TYPE" name="classType_13"
                                dicNo="${TbCalculateOneProportion_13.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_13" dicType="INDEX_TYPE" name="indexType_13"
                                dicNo="${TbCalculateOneProportion_13.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_13" dicType="SORT_TYPE" name="sortType_13"
                                dicNo="${TbCalculateOneProportion_13.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_13" class="weight" oninput='upperCase(this)' name="weight_13" value="${TbCalculateOneProportion_13.weight}"/>
                </td>
            </tr>

            <tr>
                <td>15</td>
                <td colspan="1">
                    <input id="code_14" name="code_14" value="${TbCalculateOneProportion_14.code}" hidden="hidden"/>
                    <input id="name_14" name="name_14" value="${TbCalculateOneProportion_14.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_14.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_14" dicType="CLASS_TYPE" name="classType_14"
                                dicNo="${TbCalculateOneProportion_14.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_14" dicType="INDEX_TYPE" name="indexType_14"
                                dicNo="${TbCalculateOneProportion_14.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_14" dicType="SORT_TYPE" name="sortType_14"
                                dicNo="${TbCalculateOneProportion_14.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_14" class="weight" oninput='upperCase(this)' name="weight_14" value="${TbCalculateOneProportion_14.weight}"/>
                </td>
            </tr>


            <tr>
                <td>16</td>
                <td colspan="1">
                    <input id="code_15" name="code_15" value="${TbCalculateOneProportion_15.code}" hidden="hidden"/>
                    <input id="name_15" name="name_15" value="${TbCalculateOneProportion_15.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_15.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_15" dicType="CLASS_TYPE" name="classType_15"
                                dicNo="${TbCalculateOneProportion_15.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_15" dicType="INDEX_TYPE" name="indexType_15"
                                dicNo="${TbCalculateOneProportion_15.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_15" dicType="SORT_TYPE" name="sortType_15"
                                dicNo="${TbCalculateOneProportion_15.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_15" class="weight" oninput='upperCase(this)' name="weight_15" value="${TbCalculateOneProportion_15.weight}"/>
                </td>
            </tr>


            <tr>
                <td>17</td>
                <td colspan="1">
                    <input id="code_16" name="code_16" value="${TbCalculateOneProportion_16.code}" hidden="hidden"/>
                    <input id="name_16" name="name_16" value="${TbCalculateOneProportion_16.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_16.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_16" dicType="CLASS_TYPE" name="classType_16"
                                dicNo="${TbCalculateOneProportion_16.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_16" dicType="INDEX_TYPE" name="indexType_16"
                                dicNo="${TbCalculateOneProportion_16.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_16" dicType="SORT_TYPE" name="sortType_16"
                                dicNo="${TbCalculateOneProportion_16.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_16" class="weight" oninput='upperCase(this)' name="weight_16" value="${TbCalculateOneProportion_16.weight}"/>
                </td>
            </tr>


            <tr>
                <td>18</td>
                <td colspan="1">
                    <input id="code_17" name="code_17" value="${TbCalculateOneProportion_17.code}" hidden="hidden"/>
                    <input id="name_17" name="name_17" value="${TbCalculateOneProportion_17.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_17.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_17" dicType="CLASS_TYPE" name="classType_17"
                                dicNo="${TbCalculateOneProportion_17.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_17" dicType="INDEX_TYPE" name="indexType_17"
                                dicNo="${TbCalculateOneProportion_17.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_17" dicType="SORT_TYPE" name="sortType_17"
                                dicNo="${TbCalculateOneProportion_17.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_17" class="weight" oninput='upperCase(this)' name="weight_17" value="${TbCalculateOneProportion_17.weight}"/>
                </td>
            </tr>

            <tr>
                <td>19</td>
                <td colspan="1">
                    <input id="code_18" name="code_18" value="${TbCalculateOneProportion_18.code}" hidden="hidden"/>
                    <input id="name_18" name="name_18" value="${TbCalculateOneProportion_18.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_18.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_18" dicType="CLASS_TYPE" name="classType_18"
                                dicNo="${TbCalculateOneProportion_18.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_18" dicType="INDEX_TYPE" name="indexType_18"
                                dicNo="${TbCalculateOneProportion_18.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_18" dicType="SORT_TYPE" name="sortType_18"
                                dicNo="${TbCalculateOneProportion_18.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_18" class="weight" oninput='upperCase(this)' name="weight_18" value="${TbCalculateOneProportion_18.weight}"/>
                </td>
            </tr>


            <tr>
                <td>20</td>
                <td colspan="1">
                    <input id="code_19" name="code_19" value="${TbCalculateOneProportion_19.code}" hidden="hidden"/>
                    <input id="name_19" name="name_19" value="${TbCalculateOneProportion_19.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_19.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_19" dicType="CLASS_TYPE" name="classType_19"
                                dicNo="${TbCalculateOneProportion_19.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_19" dicType="INDEX_TYPE" name="indexType_19"
                                dicNo="${TbCalculateOneProportion_19.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_19" dicType="SORT_TYPE" name="sortType_19"
                                dicNo="${TbCalculateOneProportion_19.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_19" class="weight" oninput='upperCase(this)' name="weight_19" value="${TbCalculateOneProportion_19.weight}"/>
                </td>
            </tr>


            <tr>
                <td>21</td>
                <td colspan="1">
                    <input id="code_20" name="code_20" value="${TbCalculateOneProportion_20.code}" hidden="hidden"/>
                    <input id="name_20" name="name_20" value="${TbCalculateOneProportion_20.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_20.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_20" dicType="CLASS_TYPE" name="classType_20"
                                dicNo="${TbCalculateOneProportion_20.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_20" dicType="INDEX_TYPE" name="indexType_20"
                                dicNo="${TbCalculateOneProportion_20.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_20" dicType="SORT_TYPE" name="sortType_20"
                                dicNo="${TbCalculateOneProportion_20.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_20" class="weight" oninput='upperCase(this)' name="weight_20" value="${TbCalculateOneProportion_20.weight}"/>
                </td>
            </tr>


            <tr>
                <td>22</td>
                <td colspan="1">
                    <input id="code_21" name="code_21" value="${TbCalculateOneProportion_21.code}" hidden="hidden"/>
                    <input id="name_21" name="name_21" value="${TbCalculateOneProportion_21.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_21.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_21" dicType="CLASS_TYPE" name="classType_21"
                                dicNo="${TbCalculateOneProportion_21.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_21" dicType="INDEX_TYPE" name="indexType_21"
                                dicNo="${TbCalculateOneProportion_21.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_21" dicType="SORT_TYPE" name="sortType_21"
                                dicNo="${TbCalculateOneProportion_21.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_21" class="weight" oninput='upperCase(this)' name="weight_21" value="${TbCalculateOneProportion_21.weight}"/>
                </td>
            </tr>

            <tr>
                <td>23</td>
                <td colspan="1">
                    <input id="code_22" name="code_22" value="${TbCalculateOneProportion_22.code}" hidden="hidden"/>
                    <input id="name_22" name="name_22" value="${TbCalculateOneProportion_22.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_22.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_22" dicType="CLASS_TYPE" name="classType_22"
                                dicNo="${TbCalculateOneProportion_22.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_22" dicType="INDEX_TYPE" name="indexType_22"
                                dicNo="${TbCalculateOneProportion_22.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_22" dicType="SORT_TYPE" name="sortType_22"
                                dicNo="${TbCalculateOneProportion_22.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_22" class="weight" oninput='upperCase(this)' name="weight_22" value="${TbCalculateOneProportion_22.weight}"/>
                </td>
            </tr>


            <tr>
                <td>24</td>
                <td colspan="1">
                    <input id="code_23" name="code_23" value="${TbCalculateOneProportion_23.code}" hidden="hidden"/>
                    <input id="name_23" name="name_23" value="${TbCalculateOneProportion_23.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_23.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_23" dicType="CLASS_TYPE" name="classType_23"
                                dicNo="${TbCalculateOneProportion_23.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_23" dicType="INDEX_TYPE" name="indexType_23"
                                dicNo="${TbCalculateOneProportion_23.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_23" dicType="SORT_TYPE" name="sortType_23"
                                dicNo="${TbCalculateOneProportion_23.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_23" class="weight" oninput='upperCase(this)' name="weight_23" value="${TbCalculateOneProportion_23.weight}"/>
                </td>
            </tr>


            <tr>
                <td>25</td>
                <td colspan="1">
                    <input id="code_24" name="code_24" value="${TbCalculateOneProportion_24.code}" hidden="hidden"/>
                    <input id="name_24" name="name_24" value="${TbCalculateOneProportion_24.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_24.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_24" dicType="CLASS_TYPE" name="classType_24"
                                dicNo="${TbCalculateOneProportion_24.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_24" dicType="INDEX_TYPE" name="indexType_24"
                                dicNo="${TbCalculateOneProportion_24.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_24" dicType="SORT_TYPE" name="sortType_24"
                                dicNo="${TbCalculateOneProportion_24.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_24" class="weight" oninput='upperCase(this)' name="weight_24" value="${TbCalculateOneProportion_24.weight}"/>
                </td>
            </tr>


            <tr>
                <td>26</td>
                <td colspan="1">
                    <input id="code_25" name="code_25" value="${TbCalculateOneProportion_25.code}" hidden="hidden"/>
                    <input id="name_25" name="name_25" value="${TbCalculateOneProportion_25.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_25.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_25" dicType="CLASS_TYPE" name="classType_25"
                                dicNo="${TbCalculateOneProportion_25.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_25" dicType="INDEX_TYPE" name="indexType_25"
                                dicNo="${TbCalculateOneProportion_25.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_25" dicType="SORT_TYPE" name="sortType_25"
                                dicNo="${TbCalculateOneProportion_25.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_25" class="weight" oninput='upperCase(this)' name="weight_25" value="${TbCalculateOneProportion_25.weight}"/>
                </td>
            </tr>

            <tr>
                <td>27</td>
                <td colspan="1">
                    <input id="code_26" name="code_26" value="${TbCalculateOneProportion_26.code}" hidden="hidden"/>
                    <input id="name_26" name="name_26" value="${TbCalculateOneProportion_26.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_26.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_26" dicType="CLASS_TYPE" name="classType_26"
                                dicNo="${TbCalculateOneProportion_26.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_26" dicType="INDEX_TYPE" name="indexType_26"
                                dicNo="${TbCalculateOneProportion_26.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_26" dicType="SORT_TYPE" name="sortType_26"
                                dicNo="${TbCalculateOneProportion_26.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_26" class="weight" oninput='upperCase(this)' name="weight_26" value="${TbCalculateOneProportion_26.weight}"/>
                </td>
            </tr>

            <tr>
                <td>28</td>
                <td colspan="1">
                    <input id="code_27" name="code_27" value="${TbCalculateOneProportion_27.code}" hidden="hidden"/>
                    <input id="name_27" name="name_27" value="${TbCalculateOneProportion_27.name}" hidden="hidden"/>
                    <span>${TbCalculateOneProportion_27.name}</span>
                </td>
                <td colspan="1">
                    <dic:select id="classType_27" dicType="CLASS_TYPE" name="classType_27"
                                dicNo="${TbCalculateOneProportion_27.classType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="indexType_27" dicType="INDEX_TYPE" name="indexType_27"
                                dicNo="${TbCalculateOneProportion_27.indexType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <dic:select id="sortType_27" dicType="SORT_TYPE" name="sortType_27"
                                dicNo="${TbCalculateOneProportion_27.sortType}"
                                tgClass="validate[required]" required="true"></dic:select>
                </td>
                <td colspan="1">
                    <input id="weight_27" class="weight" oninput='upperCase(this)' name="weight_27" value="${TbCalculateOneProportion_27.weight}"/>
                </td>
            </tr>
            <tr>
                <td width="80">存款联动占比</td>
                <td width="40"><input id="ratio_1" name="ratio_1" value="${ratio_1}"/></td>
                <td width="80">结构优化占比</td>
                <td width="40"><input id="ratio_2" name="ratio_2" value="${ratio_2}"/></td>
            </tr>
            <tr>
                <td width="80">市场竞争占比</td>
                <td width="40"><input id="ratio_4" name="ratio_4" value="${ratio_4}"/></td>
                <td width="80">价值提升占比</td>
                <td width="40"><input id="ratio_8" name="ratio_8" value="${ratio_8}"/></td>

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