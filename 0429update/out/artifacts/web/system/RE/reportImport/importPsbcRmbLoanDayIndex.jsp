<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>历史数据导入管理 - 人民币贷款日报导入</title>
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
                    width: '6%',
                    align: 'center'
                }, {
                    display: '贷款类型',
                    name: 'loanType',
                    width: '15%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        var result = '';
                        if(value){
                            result = loanTypeMap[value];
                        }
                        return result;
                    }
                }, {
                    display: '机构',
                    name: 'organName',
                    width: '14%',
                    align: 'center',
                    render: function (rowdata, rowindex, value, column) {
                        if(rowdata.organCode == '1'){
                            return '全国';
                        }
                        return value;
                    }
                }, {
                    display: '组别',
                    name: 'grepCode',
                    width: '5%',
                    align: 'center'
                }, {
                    display: '余额(万元)',
                    name: 'balance',
                    width: '7%',
                    align: 'center'
                }, {
                    display: '日增(万元)',
                    name: 'dayAdd',
                    width: '8%',
                    align: 'center'
                }, {
                    display: '月增(万元)',
                    name: 'monthlyAdd',
                    width: '8%',
                    align: 'center'
                }, {
                    display: '季增(万元)',
                    name: 'quarterAdd',
                    width: '8%',
                    align: 'center'
                }, {
                    display: '年增(万元)',
                    name: 'yearAdd',
                    width: '8%',
                    align: 'center'
                }, {
                    display: '年增排名',
                    name: 'yearRank',
                    width: '5%',
                    align: 'center'
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
            url: '<%=path%>/reportHisImport/getPsbcRmbLoanDayData.htm',
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
            Height:350
        });

        var $dialogDiv = $("#_DialogDiv_c1");
        var $container = $("#_Container_c1");
        var $div =       $('<div style="margin:20px;"></div>');
        var $divDate =   $('<div style="margin:20px 0px 30px 0px;">统计日期：<input type="text" class="date validate[length[0,10]]" dateFmt="yyyy-MM-dd" maxlength="8"/></div>');
        var $divType =   $('<div style="margin-bottom:10px;font-weight: 600;">请选择模板类型：</div>' +
            '<div style="margin-bottom:40px;">' +
            '<select style="min-height: 25px;">' +
            '<option value="" >--请选择--</option>' +
            '<option value="4">人民币各项贷款日报表</option>' +
            '<option value="5">人民币各项贷款-个人贷款日报表</option>' +
            '<option value="6">人民币各项贷款-公司贷款日报表</option>' +
            '</select>' +
            '</div>');
        var $divSub =    $('<div style="margin-bottom:10px;font-weight: 600;">请选择上传文件：<span style="font-size:12px;color:gray">  (支持文件类型：xls、xlsx)</span></div>');
        var $divForm =   $('<form><input type="file" name="file" id="excelFile"/></form>');
        var $divTip =    $('<div style="color:red;font-weight: 600;padding-top:20px;max-height: 100px;overflow: auto;"></div>');
        $div.append($divDate).append($divType).append($divSub).append($divForm).append($divTip);
        $container.append($div);

        //渲染日期控件
        $divDate.find("input").render();

        var $select = $divType.find("select");
        $select.change(function(){
            if(this.value){
                $divTip.html('');
            }
        });

        //上传onclick事件
        $divForm.find("input[type='file']").click(function(){
            var statisticsDay = $divDate.find("input").val();
            if(!statisticsDay){
                $divTip.html("请选择统计日期！");
                return false;
            }

            var type = $select.val();
            if(!type){
                $divTip.html('请选择模板类型！');
                return false;
            }
        });

        //上传onchange事件
        $divForm.find("input[type='file']").change(function(){
            var type = $select.val();
            var file = this;
            top.Dialog.confirm("是否上传文件?", function() {
                onUploadFile(file,type, dialog, $dialogDiv, $divTip, $divForm, $divDate);
            });
        });
    }

    //上传 - onchange事件触发，上传后台
    function onUploadFile(file, type, dialog, $dialogDiv, $divTip, $divForm, $divDate){
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

            //上传服务器
            $dialogDiv.mask("正在上传中",null,true);

            //校验统计日期
            var statisticsDay = $divDate.find("input").val();
            $.ajax({
                type: "post",
                url: '<%=path%>/reportHisImport/checkStatisticsDay.htm',
                data:{type:type, statisticsDay:statisticsDay},
                dataType: "json",
                success: function (result) {
                    if(result.status == '0000'){
                        if(result.isExist == true){
                            //消除加载loading
                            $dialogDiv.unmask();

                            top.Dialog.confirm("统计日期（"+statisticsDay+"）的信息已存在，是否覆盖上传？",
                                function(){
                                    $dialogDiv.mask("正在上传中",null,true);
                                    onUploadAjax(type, statisticsDay, dialog, $dialogDiv, $divTip, $divForm);
                                },
                                function(){
                                    //清空
                                    $divForm[0].reset();
                                }
                            );
                        }else{
                            onUploadAjax(type, statisticsDay, dialog, $dialogDiv, $divTip, $divForm);
                        }
                    }else{
                        top.Dialog.alert(result.message);
                    }
                }
            });
        }
    }

    function onUploadAjax(type, statisticsDay, dialog, $dialogDiv, $divTip, $divForm){
        $.ajaxFileUpload({
            url:'<%=path%>/reportHisImport/upload.htm',
            secureuri:false,
            data:{type:type, statisticsDay:statisticsDay},
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
        var path1 = '<%=path%>/libs/excel/temp/TempPsbcRmbLoanDay.xlsx';
        var name1 = '人民币各项贷款日报表';
        var path2 = '<%=path%>/libs/excel/temp/TempPsbcRmbLoanDayGR.xlsx';
        var name2 = '人民币各项贷款-个人贷款日报表';
        var path3 = '<%=path%>/libs/excel/temp/TempPsbcRmbLoanDayGS.xlsx';
        var name3 = '人民币各项贷款-公司贷款日报表';

        var str = '';
        str += '<div style="margin:20px;">';
        str += '    <div onclick="openDownloadDialog(\'' + path1 + '\', \'' + name1 + '.xlsx\');" style="margin:30px;color:red;cursor:pointer;text-decoration:underline" title="点击下载模板">' + name1 + '模板</div>';
        str += '    <div onclick="openDownloadDialog(\'' + path2 + '\', \'' + name2 + '.xlsx\');" style="margin:30px;color:red;cursor:pointer;text-decoration:underline" title="点击下载模板">' + name2 + '模板</div>';
        str += '    <div onclick="openDownloadDialog(\'' + path3 + '\', \'' + name3 + '.xlsx\');" style="margin:30px;color:red;cursor:pointer;text-decoration:underline" title="点击下载模板">' + name3 + '模板</div>';
        str += '</div>';

        Dialog.open({
            Title:"下载模板",
            Width:400,
            Height:200,
            InnerHtml: str
        });
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