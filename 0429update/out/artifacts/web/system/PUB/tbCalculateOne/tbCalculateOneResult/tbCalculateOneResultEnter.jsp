<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%--    <%@include file="/common/common_edit.jsp"%>--%>
    <%@include file="/common/common_list.jsp" %>
    <script type="text/javascript" src="<%=path%>/libs/js/jquery-form.js"></script>
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
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title>计划录入</title>
    <script type="text/javascript">

        var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
        function fileChange(target,id) {
            var fileSize = 0;
            var filetypes =[".xls"];
            var filepath = target.value;
            if(filepath){
                var isnext = false;
                var fileend = filepath.substring(filepath.indexOf("."));
                if(filetypes && filetypes.length>0){
                    for(var i =0; i<filetypes.length;i++){
                        if(filetypes[i]==fileend){
                            isnext = true;
                            break;
                        }
                    }
                }
                if(!isnext){
                    top.Dialog.alert("不接受此文件类型！");
                    target.value ="";
                    return false;
                }
            }else{
                return false;
            }
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

        //上传文件
        function xhrSubmit() {
            var file_obj = document.getElementById('search_key_file').files[0];
            if (typeof (file_obj) == "undefined") {
                top.Dialog.alert("请选择需要导入的文件");
                return;
            }
            // var planMonth = $("#planMonth").val();
            //
            // if(!planMonth){
            //     top.Dialog.alert("所属月份不可为空");
            //     return;
            // }



            var fd = new FormData();
            fd.append('file', file_obj);
            // fd.append('planMonth', planMonth);


            top.Dialog.confirm("确定要导入文件吗?|操作提示", function() {
                $('#submit').attr('disabled', true);
                $.ajax({
                    url: '<%=path%>/tbCalculateOneResult/enterReport.htm',
                    type: 'POST',
                    data: fd,
                    processData: false,  //tell jQuery not to process the data
                    contentType: false,  //tell jQuery not to set contentType
                    success: function (result) {
                        result = JSON.parse(result);
                        if(result.success == "true" || result.success==true){
                            $('#submit').attr('disabled', false);
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
                            $('#submit').attr('disabled', false);
                            top.Dialog.alert(result.msg);
                        }
                    },
                    error:function(error){
                        $('#submit').attr('disabled', false);
                        if (error.status == "10000") {
                            top.Dialog.alert(error.responseText + " | 超时提示",function(){
                                window.parent.location.href = "<%=path%>/toLogin.htm";
                            },null,null,3);
                        } else{
                            top.Dialog.alert(error.responseText);
                        }
                    }
                });
            });
        }

        //生成月度计划
        function calculateOneMonth() {
            top.Dialog.confirm("确定要生成测算模式一的结果吗？", function () {
                $.post("<%=path%>/tbCalculateOneResult/addTbCalculateOneResult.htm", null, function (result) {
                    if (result.success == 'noPower') {
                        alert("您没有操作权限");
                    } else {
                        top.Dialog.alert(result.msg);
                    }
                }, "json");
            });

        }

        function download() {
            openDownloadDialog('<%=path%>/libs/excel/tbCalculateOne/TbCalculateOne.xls', '测算模式一历史数据导入表样.xls');
        }

    </script>
</head>
<body>

<div class="form-group">
    <label class="col-sm-2 control-label">模板下载：</label>
<%--    <a href="<%=path%>/tbCalculateOneResult/downloadTemplate.htm">--%>
        <button class="btn btn-primary" type="button" onclick="download();">
            下载
        </button>
<%--    </a>--%>
</div>

</br>


<div class="form-group">

    <label class="col-sm-2 control-label">录入文件：</label>
    </br>
    (注意：导入主指标时请导入原始数据)
    <div class="col-sm-10">
        <input type="file" onchange="fileChange(this);" class="file" id="search_key_file"
               accept="application/vnd.ms-excel"/>
    </div>
    </br>(支持文件格式：xls)
</div>

<div class="form-group">
    <div class="col-sm-10 col-sm-offset-2" style="margin-top: 10px;">
        <button class="btn btn-primary" type="button" onclick="xhrSubmit();" id="submit">
            录入
        </button>
    </div>
</div>

</br></br>

<div class="form-group">
    <div class="col-sm-10 col-sm-offset-2" style="margin-top: 10px;">
        <button class="btn btn-primary" type="button" onclick="calculateOneMonth();" id="calculateOneMonth">
            生成月度测算结果
        </button>
    </div>
</div>

</body>
</html>

