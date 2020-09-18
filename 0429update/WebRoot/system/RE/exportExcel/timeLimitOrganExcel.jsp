<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <%--时间插件 begin--%>
    <link rel="stylesheet" media="screen"
          href="${path}/libs/bootstrap/sample%20in%20bootstrap%20v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <script type="text/javascript"
            src="${path}/libs/bootstrap/sample%20in%20bootstrap%20v2/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${path}/libs/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <%--时间插件 end--%>
    <title></title>
</head>
<body>


<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width: 100%;">
            <tr>

                <td>报表日期：</td>
                <td>
                    <input type="text" id="date" name="date" class="input-small inline form_datetime"
                           style="width: 160px;"/>
                </td>
<%--                <td>报表周期：</td>--%>
<%--                <td>--%>
<%--                    &lt;%&ndash;                    <dic:select dicType="REPORT_PARAM_DATE" name="cycel" id="cycel"></dic:select>&ndash;%&gt;--%>
<%--                    <select id="cycel" name="cycel">--%>
<%--                        <option value="1">年</option>--%>
<%--                        <option value="2">季</option>--%>
<%--                        <option value="3">月</option>--%>
<%--                        <option value="4">日</option>--%>
<%--                    </select>--%>
<%--                </td>--%>
                <input type="hidden" id="cycel" name="cycel" value="1"/>


                <td>贷种组合类型：</td>
                <td>
                    <%--                    <div id="combList" name="combList" url="<%=path%>/report/combLevelOneTreeList.htm"--%>
                    <%--                         multiMode="true" allSelectable="true" exceptParent="true"></div>--%>
                    <select id="combType" name="combType">
                        <option value="1">各项贷款(人行口径)</option>
                        <option value="2">各项贷款(银保监口径)</option>
                        <option value="4">实体贷款</option>
                    </select>

                </td>

                <td>报表单位：</td>
                <td>
                    <select id="unit" name="uit">
                        <option value="1">万元</option>
                        <option value="2" selected>亿元</option>
                    </select>
                </td>


<%--                <td>汇总维度：</td>--%>
<%--                <td>--%>
<%--                    <select id="dimension" name="dimension">--%>
<%--                        <option value="1">区域-地理区域划分</option>--%>
<%--                        <option value="2">区域-银行同业划分</option>--%>
<%--                        <option value="3">区域-财务考核划分</option>--%>
<%--                        &lt;%&ndash;                        <option value="8">分行</option>&ndash;%&gt;--%>
<%--                    </select>--%>
<%--                </td>--%>

                <input type="hidden" id="dimension" name="dimension" value="8" >

            </tr>

            <tr>
                <td colspan="10">
                    <div align="center">
                        <button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
                        <button type="button" onclick="resetSearch()"><span class="icon_reload">重置</span></button>
                        <button type="button" onclick="onDownload()"><span class="icon_btn_down">下载</span></button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

<!-- Grid位置 -->
<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div align="center" ><h3  style="display: inline;">期限表&nbsp;&nbsp;</h3> </div>
    <div id="dataBasic"></div>
</div>

</body>

<script type="text/javascript">

    // var diag = new top.Dialog();

    var grid = null;

    var setting_copy = {
        check: {
            enable: true,
            chkboxType: {"Y": "s", "N": "s"}
        }

    };

    function initComplete() {
        $("#organList").selectTreeRender(setting_copy);


        // top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '机构',
                            name: 'name',
                            width: '20%',
                            id: 'deptId',
                            align: 'left',
                            frozen: true
                        },
                        {
                            display: '短期',
                            align: 'center',
                            columns: [
                                {
                                    display: '一个月以内',
                                    name: 'month1Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'month1Count',
                                    width: '10%',
                                    align: 'center',
                                },{
                                    display: '1-3月',
                                    name: 'month13Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'month13Count',
                                    width: '10%',
                                    align: 'center',
                                },
                                {
                                    display: '3-6月',
                                    name: 'month36Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'month36Count',
                                    width: '10%',
                                    align: 'center',
                                },{
                                    display: '6-12月',
                                    name: 'month612Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'month612Count',
                                    width: '10%',
                                    align: 'center',
                                },{
                                    display: '短期合计',
                                    name: 'monthBalanceTotal',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'monthCountTotal',
                                    width: '10%',
                                    align: 'center',
                                }
                            ]
                        },

                        {
                            display: '中长期',
                            align: 'center',
                            columns: [
                                {
                                    display: '1-2年',
                                    name: 'year12Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'year12Count',
                                    width: '10%',
                                    align: 'center',
                                },{
                                    display: '2-3年',
                                    name: 'year23Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'year23Count',
                                    width: '10%',
                                    align: 'center',
                                },
                                {
                                    display: '3-5年',
                                    name: 'year35Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'year35Count',
                                    width: '10%',
                                    align: 'center',
                                },{
                                    display: '5-10年',
                                    name: 'year510Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'year510Count',
                                    width: '10%',
                                    align: 'center',
                                },{
                                    display: '10年以上',
                                    name: 'year10Balance',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'year10Count',
                                    width: '10%',
                                    align: 'center',
                                },{
                                    display: '中长期合计',
                                    name: 'yearBalanceTotal',
                                    width: '10%',
                                    align: 'center',
                                    render: function (rowdata, rowindex, value, column) {
                                        return amountConversion(value);
                                    }
                                },{
                                    display: '笔数',
                                    name: 'yearCountTotal',
                                    width: '10%',
                                    align: 'center',
                                }
                            ]
                        },{
                            display: '总计',
                            name: 'balanceTotal',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                return amountConversion(value);
                            }
                        },{
                            display: '笔数',
                            name: 'countTotal',
                            width: '10%',
                            align: 'center',
                        }

                        ],
                    url: '<%=path%>/report/timeLimitOrganExcel.htm',
                    sortName: '',
                    // rownumbers: true,
                    alternatingRow: false,
                    height: '100%',
                    width: "100%",
                    usePager: false,
                    tree: {columnId: 'deptId'},
                    toolbar: {
                        items: [
                            ${btnList}
                        ]
                    }
                });
    }

    $(function () {
        //初始化树形选择器
        $("#combList").selectTreeRender(setting_copy);

        //日期选择框
        $('#date').datetimepicker({
            format: 'yyyymmdd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: false,
            language: 'zh-CN'
        });
        $('#date').datetimepicker('update', new Date());
    });


    //父子不关联选择
    var setting = {
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
        }
    };
    //父子关联选择
    var setting_copy = {
        check: {
            enable: true,
            chkboxType: {"Y": "s", "N": "s"}
        }
    };


    //重置查询条件
    function resetSearch() {
        var menu_id = parent.getCurrentTabId();
        if (menu_id == undefined) {
            top.Dialog.close();
            return;
        }
        var menu_frame_id = "page_" + menu_id;
        top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
        top.Dialog.close();
    }

    //查询
    function searchHandler() {
        //校验
        //报表日期
        var date = $("#date").val();
        //报表周期
        var cycel = $("#cycel").val();
        //贷种组合类型 1 全部；2少拆放；4 实体
        var combType = $("#combType").val();

        if (!date) {
            top.Dialog.alert("报表日期不可为空");
            return;
        }
        if (!cycel) {
            top.Dialog.alert("报表周期不可为空");
            return;
        }
        if (!combType) {
            top.Dialog.alert("贷种组合类型不可为空");
            return;
        }

        var query = $("#queryForm").formToArray();
        //alert(JSON.stringify(query))
        grid.setOptions({
            params: query
        });
        //页号重置为1
        grid.setNewPage(1);
        grid.loadData();//加载数据
    }


    /*---单位转换---*/
    function amountConversion(amount) {
        var conNum = 1;
        var unit = $("#unit").val();
        if (unit == 2) {
            conNum = 0.0001;
        }
        return accMul(amount,conNum)+'';
    }

    //js计算乘法
    function accMul(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length
        } catch (e) {
        }
        try {
            m += s2.split(".")[1].length
        } catch (e) {
        }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }


    //下载
    function onDownload() {
        //报表日期
        var date = $("#date").val();
        //报表周期
        var cycel = $("#cycel").val();
        //贷种组合类型 1 全部；2少拆放；4 实体
        var combType = $("#combType").val();
        //汇总维度
        var dimension = $("#dimension").val();
        //报表单位
        var unit = $("#unit").val();

        if (!date) {
            top.Dialog.alert("报表日期不可为空");
            return;
        }
        if (!cycel) {
            top.Dialog.alert("报表周期不可为空");
            return;
        }
        if (!combType) {
            top.Dialog.alert("贷种组合类型不可为空");
            return;
        }
        if (!dimension) {
            top.Dialog.alert("汇总维度不可为空");
            return;
        }
        if (!unit) {
            top.Dialog.alert("报表单位不可为空");
            return;
        }

        location = "<%=path%>/report/downloadTimeLimitOrganExcel.htm?date=" + date + "&cycel=" + cycel + "&combType=" + combType
            + "&dimension=" + dimension + "&unit=" + unit;
    }


</script>
</html>