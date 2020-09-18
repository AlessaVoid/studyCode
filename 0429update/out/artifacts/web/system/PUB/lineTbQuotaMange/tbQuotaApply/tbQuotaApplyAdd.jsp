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
    function submitInfo() {


        var qaComb =$("#qaComb").val();
        var unit =$("#unit").val();
        var qaAmt =$("#qaAmt").val();
        var qaExpDate =$("#qaExpDate").val();
        var qaStartDate =$("#qaStartDate").val();
        var qaReason =$("#qaReason").val();

        if (qaAmt == "") {
            top.Dialog.alert("请选择调整额度", null, null, null, 5);
            return
        }
        if (qaAmt == 0) {
            top.Dialog.alert("调整额度不能为0", null, null, null, 5);
            return
        }
        if ($("#qaStartDate").val() - $("#qaExpDate").val() > 0) {
            top.Dialog.alert("生效应小于失效时间");
            return
        }
        if (qaReason == "") {
            top.Dialog.alert("请填写申请原因", null, null, null, 5);
            return
        }

        var valid = $("#form1").validationEngine({
            returnIsValid: true
        });
        if(!valid){
            top.Dialog.alert("请检查数据正确性");
            return;
        }
        var file_obj = document.getElementById('search_key_file').files[0];
        if (typeof (file_obj) == "undefined") {
            top.Dialog.alert("请选择需要导入的文件");
            return;
        }

        var fd = new FormData();
        fd.append('file', file_obj);
        fd.append('unit', unit);
        fd.append('qaComb', qaComb);
        fd.append('qaAmt', qaAmt);
        fd.append('qaExpDate', qaExpDate);
        fd.append('qaStartDate', qaStartDate);
        fd.append('qaReason', qaReason);


        if(!valid){
            top.Dialog.alert("请检查数据正确性");
            return;
        }
        $.ajax({
            url: '<%=path%>/tbTradeManger/lineTbQuotaApply/insert.htm',
            type: 'POST',
            data: fd,
            processData: false,
            contentType: false,
            success: function (result) {
                if(result.success == "true" || result.success==true){
                    top.Dialog.alert(result.msg, function() {
                        var menu_id = parent.getCurrentTabId();
 if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                        var menu_frame_id = "page_" + menu_id;
                        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                        top.Dialog.close();
                    });
                }else{
                    top.Dialog.alert(result.msg);
                }
            },
            error:function(error){
                top.Dialog.alert("上传失败");
            }
        });
    }


    var maxDateStr="";
    function initComplete() {
        var now = new Date()
        var year = now.getFullYear(); //得到年份
        var month = now.getMonth() + 1;//得到月份
        if (month < 10) month = "0" + month;
        var time = "" + year + "-" + month;
        var d = new Date(year,month,0);
        var days=d.getDate();
        maxDateStr=year + "-" + month+"-"+days;
        $("#timeSpan").html(time);

        //获取json数据
        $.post("<%=path%>/tbTradeManger/lineTbQuotaApply/findComb.htm",
            {}, function (result) {
                //赋给data
                $("#qaComb").data("data", result)
                //刷新下拉框
                $("#qaComb").render();
            }, "json");
    }


    //用户只能输入正负数与小数
    function upperCase(obj) {
         if (!/^[+-]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 9));
        }
            obj.value = obj.value.replace(/[^\d.]/g,"");
            //必须保证第一个为数字而不是.
            obj.value = obj.value.replace(/^\./g,"");
            //保证只有出现一个.而没有多个.
            obj.value = obj.value.replace(/\.{2,}/g,".");
            //保证.只出现一次，而不能出现两次以上
            obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    }
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    function fileChange(target,id) {
        var fileSize = 0;

        if (isIE && !target.files) {
            var filePath = target.value;
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
            if(!fileSystem.FileExists(filePath)){
                top.Dialog.alert("附件不存在，请重新输入！");
                return false;
            }
            var file = fileSystem.GetFile (filePath);
            fileSize = file.Size;
        } else {
            fileSize = target.files[0].size;
        }

        var size = fileSize / 1024;
        if(size<=0){
            top.Dialog.alert("附件大小不能为0M！");
            target.value ="";
            return false;
        }
    }

</script>

<body>
<form id="form1">
    <table class="tableStyle" id="table1" width="80%" mode="list" formMode="line">
        <tr>
            <td align="right">
                所属月份：
            </td>
            <td>
                <span id="timeSpan"></span>
            </td>

            <td align="left">贷种组合名称：</td>
            <td>
                <select id="qaComb" name="qaComb"></select>
                <span class="star">*</span>
            </td>
        </tr>

        <tr>
            <td align="right">调整额度：</td>
            <td>
                <input type="text" id="qaAmt" name="qaAmt"  AUTOCOMPLETE="off"  class=" validate[required,custom[onlyNumberWideSpecial]] float_left"
                       onblur='upperCase(this)' maxlength="16" required="true">
                <span class="star">(金额大于零)</span>
            </td>
            <td align="right">上传附件：</td>
            <td>
                <input type="file" onchange="fileChange(this);" class="file" id="search_key_file" />
            </td>
            </td>
        </tr>
        <tr >


            <td align="right">生效日期：</td>
            <td>

                <input type="text" id="qaStartDate" name="qaStartDate" class="validate[required] date" AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd"  onfocus="WdatePicker({skin:themeColor,minDate:'%y-%M-%d',maxDate:maxDateStr})"/>
                <span class="star">*</span>


            <td align="right">失效日期：</td>
            <td>

                <input type="text" id="qaExpDate" name="qaExpDate" class="validate[required] date" AUTOCOMPLETE="off"
                       dateFmt="yyyyMMdd"  onfocus="WdatePicker({skin:themeColor,minDate:'%y-%M-%d',maxDate:maxDateStr})"/>
                <span class="star">*</span>
            </td>

        </tr>

        <tr>
            <td align="right">
                单位：
            </td>
            <td>
                <dic:select id="unit" dicType="CURRENCY_UNIT" name="unit"
                            tgClass="validate[required]" required="true"></dic:select>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="1">事由：</td>
            <td colspan="3">
                <textarea id="qaReason" name="qaReason" AUTOCOMPLETE="off"  style="width:90%;" class="validate[required]"></textarea>
            </td>
        </tr>
    </table>
    <table class="tableStyle" id="table2" width="80%" mode="list" formMode="line">
        <tr>
            <td colspan="4">
                <div align="center">
                    <button type="button" onclick="submitInfo()" class="saveButton"/>
                    <button type="button" onclick="return cancel()" class="cancelButton"/>
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>