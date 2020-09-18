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

</script>

<body>
<form id="form1">
    <table class="tableStyle" width="80%" mode="list" formMode="line">

        <tr>
            <td align="left" colspan="1">
                名称：
            </td>
            <td colspan="3">
                ${TbCalculateOneRank.name}
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                类型：
            </td>
            <td colspan="1">
                <dic:out dicType="CALCULATE_ONE_RABK_TYPE" dicNo="${TbCalculateOneRank.type}"/>
            </td>
            <td align="left" colspan="1">
                启停状态：
            </td>
            <td colspan="1">
                <dic:out dicType="RANK_STATE" dicNo="${TbCalculateOneRank.status}"/>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名1：
            </td>
            <td colspan="1">
                <input id="id" name="id" value="" hidden="hidden"/>
                <input type="text" id="rank1" name="rank1" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank1}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名2：
            </td>
            <td colspan="1">
                <input type="text" id="rank2" name="rank2" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank2}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名3：
            </td>
            <td colspan="1">
                <input type="text" id="rank3" name="rank3" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank3}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名4：
            </td>
            <td colspan="1">
                <input type="text" id="rank4" name="rank4" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank4}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名5：
            </td>
            <td colspan="1">
                <input type="text" id="rank5" name="rank5" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank4}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名6：
            </td>
            <td colspan="1">
                <input type="text" id="rank6" name="rank6" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank6}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名7：
            </td>
            <td colspan="1">
                <input type="text" id="rank7" name="rank7" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank7}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名8：
            </td>
            <td colspan="1">
                <input type="text" id="rank8" name="rank8" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank8}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名9：
            </td>
            <td colspan="1">
                <input type="text" id="rank9" name="rank9" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank9}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名10：
            </td>
            <td colspan="1">
                <input type="text" id="rank10" name="rank10" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank10}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名11：
            </td>
            <td colspan="1">
                <input type="text" id="rank11" name="rank11" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank11}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名12：
            </td>
            <td colspan="1">
                <input type="text" id="rank12" name="rank12" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank12}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名13：
            </td>
            <td colspan="1">
                <input type="text" id="rank13" name="rank13" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank13}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名14：
            </td>
            <td colspan="1">
                <input type="text" id="rank14" name="rank14" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank14}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名15：
            </td>
            <td colspan="1">
                <input type="text" id="rank15" name="rank15" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank15}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名16：
            </td>
            <td colspan="1">
                <input type="text" id="rank16" name="rank16" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank16}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名17：
            </td>
            <td colspan="1">
                <input type="text" id="rank17" name="rank17" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank17}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名18：
            </td>
            <td colspan="1">
                <input type="text" id="rank18" name="rank18" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank18}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名19：
            </td>
            <td colspan="1">
                <input type="text" id="rank19" name="rank19" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank19}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名20：
            </td>
            <td colspan="1">
                <input type="text" id="rank20" name="rank20" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank20}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名21：
            </td>
            <td colspan="1">
                <input type="text" id="rank21" name="rank21" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank21}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名22：
            </td>
            <td colspan="1">
                <input type="text" id="rank22" name="rank22" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank22}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名23：
            </td>
            <td colspan="1">
                <input type="text" id="rank23" name="rank23" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank23}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名24：
            </td>
            <td colspan="1">
                <input type="text" id="rank24" name="rank24" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank24}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名25：
            </td>
            <td colspan="1">
                <input type="text" id="rank25" name="rank25" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank25}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名26：
            </td>
            <td colspan="1">
                <input type="text" id="rank26" name="rank26" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank26}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名27：
            </td>
            <td colspan="1">
                <input type="text" id="rank27" name="rank27" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank27}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名28：
            </td>
            <td colspan="1">
                <input type="text" id="rank28" name="rank28" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank28}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名29：
            </td>
            <td colspan="1">
                <input type="text" id="rank29" name="rank29" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank29}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名30：
            </td>
            <td colspan="1">
                <input type="text" id="rank30" name="rank30" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank30}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名31：
            </td>
            <td colspan="1">
                <input type="text" id="rank31" name="rank31" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank31}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名32：
            </td>
            <td colspan="1">
                <input type="text" id="rank32" name="rank32" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank32}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名33：
            </td>
            <td colspan="1">
                <input type="text" id="rank33" name="rank33" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank33}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名34：
            </td>
            <td colspan="1">
                <input type="text" id="rank34" name="rank34" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank34}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
        <tr>
            <td align="left" colspan="1">
                排名35：
            </td>
            <td colspan="1">
                <input type="text" id="rank35" name="rank35" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank35}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
            <td align="left" colspan="1">
                排名36：
            </td>
            <td colspan="1">
                <input type="text" id="rank36" name="rank36" AUTOCOMPLETE="off" readonly="readonly" disabled="disabled"
                       value="${TbCalculateOneRank.rank36}"
                       class=" validate[required,custom[onlyNumberWideSpecial]]"/>
                <span class="star">*</span>
            </td>
        </tr>
    </table>
</form>
</body>
</html>