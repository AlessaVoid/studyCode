<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>历史数据导入管理 - 人民币贷款日报汇总导入</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=path%>/libs/js/ajaxfileupload.js"></script>
</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>
                <td width="150px">统计日期：</td>
                <td width="400px">
                    <input type="text" name="statisticsDate" class="date validate[length[0,10]]" dateFmt="yyyy-MM-dd" />
                </td>

                <td width="150px">贷款类型：</td>
                <td width="400px">
                    <select id="loanType" name="loanType" selWidth="250">
                        <option value="">请选择</option>
                    </select>
                </td>

                <td>
                    <div align="center">
                        <button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
                        <button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- Grid位置 -->
<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div id="dataBasic"></div>
</div>
</body>
<script>
    var loanTypeMap = ${loanTypeMap}; //贷款类型Map
    var grid;
    function initTable() {
        grid = $("#dataBasic").quiGrid({
            columns: [
                {
                    display: '统计日期',
                    name: 'statisticsDay',
                    width: '7%',
                    align: 'center'
                }, {
                    display: '贷款类型',
                    name: 'loanType',
                    width: '17%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = loanTypeMap[value];
                        }
                        return result;
                    }
                }, {
                    display: '余额(万元)',
                    name: 'balance',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = parseFloat(value);
                        }
                        return result;
                    }
                }, {
                    display: '日增(万元)',
                    name: 'dayAdd',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = parseFloat(value);
                        }
                        return result;
                    }
                }, {
                    display: '月增(万元)',
                    name: 'monthlyAdd',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = parseFloat(value);
                        }
                        return result;
                    }
                }, {
                    display: '季增(万元)',
                    name: 'quarterAdd',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = parseFloat(value);
                        }
                        return result;
                    }
                }, {
                    display: '年增(万元)',
                    name: 'yearAdd',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = parseFloat(value);
                        }
                        return result;
                    }
                }, {
                    display: '年增幅',
                    name: 'annualGrowthRate',
                    width: '10%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = parseFloat(value) + '%';
                        }
                        return result;
                    }
                }, {
                    display: '创建时间',
                    name: 'createdTime',
                    width: '8%',
                    align: 'center'
                }, {
                    display: '修改时间',
                    name: 'updatedTime',
                    width: '8%',
                    align: 'center'
                }],
            url: '<%=path%>/reportHisImport/getPsbcRmbLoanSumData.htm',
            rownumbers: true,
            height: '100%',
            width: "100%",
            pageSize: 10,
            headerRowHeight: 26,
            toolbar: {
                items: [
                    ${btnList}
                ]
            }
        });
    }


    //上传
    function onUpload(){
        var dialog = Dialog.open({
            Title:"上传",
            ID:"c1",
            Width:600,
            Height:250
        });

        var $dialogDiv = $("#_DialogDiv_c1");
        var $container = $("#_Container_c1");
        var $div =       $('<div style="margin:20px;"></div>');
        var $divDate =   $('<div style="margin:20px 0px 30px 0px;">统计日期：<input type="text" class="date validate[length[0,10]]" dateFmt="yyyy-MM-dd" maxlength="8"/></div>');
        var $divSub =    $('<div style="margin-bottom:20px;font-weight: 600;">请选择上传文件：<span style="font-size:12px;color:gray">  (支持文件类型：xls、xlsx)</span></div>');
        var $divForm =   $('<form><input type="file" name="file" id="excelFile"/></form>');
        var $divTip =    $('<div style="color:red;font-weight: 600;padding-top:20px;max-height: 100px;overflow: auto;"></div>');
        $div.append($divDate).append($divSub).append($divForm).append($divTip);
        $container.append($div);

        //渲染日期控件
        $divDate.find("input").render();

        //上传onclick事件
        $divForm.find("input[type='file']").click(function(){
            var statisticsDay = $divDate.find("input").val();
            if(!statisticsDay){
                $divTip.html("请选择统计日期！");
                return false;
            }
        });

        //上传onchange事件
        $divForm.find("input[type='file']").change(function(){
            var file = this;
            top.Dialog.confirm("是否上传文件?", function() {
                onUploadFile(file, dialog, $dialogDiv, $divTip, $divForm, $divDate);
            });
        });
    }

    //上传 - onchange事件触发，上传后台
    function onUploadFile(file, dialog, $dialogDiv, $divTip, $divForm, $divDate){
        //清除错误提示
        $divTip.html('');
        var fileName = file.value;
        if(fileName){
            //验证文件格式
            var index = fileName.lastIndexOf(".");
            var suffix = fileName.substr(index + 1);
            if(!suffix || $.inArray(suffix.toLowerCase(), ['xls','xlsx']) == -1){
                $divTip.html("只可上传xls/xlsx文件，请重新选择！");
                return;
            }

            //校验统计日期
            var statisticsDay = $divDate.find("input").val();
            $.ajax({
                type: "post",
                url: '<%=path%>/reportHisImport/checkStatisticsDay.htm',
                data:{type:3, statisticsDay:statisticsDay},
                dataType: "json",
                success: function (result) {
                    if(result.status == '0000'){
                        if(result.isExist == true){
                            //消除加载loading
                            $dialogDiv.unmask();

                            top.Dialog.confirm("统计日期（"+statisticsDay+"）的信息已存在，是否覆盖上传？",
                                function(){
                                    $dialogDiv.mask("正在上传中",null,true);
                                    onUploadAjax(statisticsDay, dialog, $dialogDiv, $divTip, $divForm);
                                },
                                function(){
                                    //清空
                                    $divForm[0].reset();
                                }
                            );
                        }else{
                            onUploadAjax(statisticsDay, dialog, $dialogDiv, $divTip, $divForm);
                        }
                    }else{
                        alert(result.message);
                    }
                }
            });
        }
    }

    function onUploadAjax(statisticsDay, dialog, $dialogDiv, $divTip, $divForm){
        $.ajaxFileUpload({
            url:'<%=path%>/reportHisImport/upload.htm',
            secureuri:false,
            data:{type:3, statisticsDay:statisticsDay},
            fileElementId:'excelFile',
            dataType:'json',
            success:function(data){
                onUploadFileCallBack(data, dialog, $dialogDiv, $divTip, $divForm);
            },
            error:function(data){
                data = JSON.parse(data.responseText);
                onUploadFileCallBack(data, dialog, $dialogDiv, $divTip, $divForm);
            }
        });
    }

    //上传 - 回调
    function onUploadFileCallBack(data, dialog, $dialogDiv, $divTip, $divForm){
        //消除加载loading
        $dialogDiv.unmask();
        //清空
        $divForm[0].reset();

        if(data.status == '0000'){
            dialog.close();
            grid.loadData();
            top.Dialog.alert(data.message);
        }else{
            var errorStr = data.message;
            var errorList = data.errorList;
            if(errorList && errorList.length > 0){
                for(var i = 0; i < errorList.length; i++){
                    errorStr += "<br/>" + errorList[i];
                }
            }
            $divTip.html(errorStr);
        }
    }

    //下载模板
    function onDown(){
        openDownloadDialog('<%=path%>/libs/excel/temp/TempPsbcRmbLoanSum.xlsx', '人民币存贷款日报汇总表.xlsx');
    }

    //重置查询
    function resetSearch() {
        $("#queryForm")[0].reset();
        $("#loanType").render();
    }

    //初始化贷款类型下拉框
    function initLoanTypeSelect(){
        if(loanTypeMap){
            var loanTypeListSelect = [{value : "", key : "请选择"}];
            for(var key in loanTypeMap){
                loanTypeListSelect.push({value : key, key : loanTypeMap[key]});
            }
            $("#loanType").data("data", loanTypeListSelect);
            $("#loanType").render();
        }
    }

    $(function() {
        initLoanTypeSelect();
        initTable();
    });
</script>
</html>