<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <%--时间插件 begin--%>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/sample%20in%20bootstrap%20v2/bootstrap/css/bootstrap.min.css"></link>
    <link rel="stylesheet" media="screen" href="${path}/libs/bootstrap/css/bootstrap-datetimepicker.min.css"></link>
    <script type="text/javascript" src="${path}/libs/bootstrap/sample%20in%20bootstrap%20v2/bootstrap/js/bootstrap.min.js"></script>
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
                <td >
                    <input type="text" id="date" name="date" class="input-small inline form_datetime" style="width: 160px;" />
                </td>
                <td >报表周期：</td>
                <td >
<%--                    <dic:select dicType="REPORT_PARAM_DATE" name="cycel" id="cycel"></dic:select>--%>
                    <select id="cycel" name="cycel">
                        <option value="1">年</option>
                        <option value="2">季</option>
                        <option value="3">月</option>
                        <option value="4">日</option>
                    </select>
                </td>


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

            </tr>

            <tr>
            <td colspan="10">
                    <div align="center">
                        <button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
                        <button type="button" onclick="resetSearch()"><span class="icon_reload" >重置</span></button>
                        <button type="button" onclick="onDownload()"><span class="icon_btn_down">下载</span></button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

<!-- Grid位置 -->
<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div align="center" ><h3  style="display: inline;">产品表&nbsp;&nbsp;</h3> </div>
    <div></div>
    <div id="dataBasic"></div>
</div>

</body>

<script type="text/javascript">

    // var diag = new top.Dialog();

    var grid = null;

    function initComplete() {
        // top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '贷种',
                            name: 'name',
                            width: '20%',
                            id:'deptId',
                            align: 'left',
                            frozen: true
                        }, {
                            display: '余额',
                            name: 'balance',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'balanceRank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '余额占比',
                            name: 'balanceRatio',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return value+'%';
                                }
                            }
                        }, {
                            display: '余额占比较期初',
                            name: 'balanceRatioCompareDateBegin',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return value+'%';
                                }
                            }
                        }, {
                            display: '本期净增需求(条线)',
                            name: 'current_increase_req_line',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '本期净增需求(分行)',
                            name: 'current_increase_req_organ',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'current_increase_req_rank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '本期计划',
                            name: 'planAmount',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '当日净增',
                            name: 'day_increase_num',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '本期净增',
                            name: 'current_increase_num',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'current_increase_num_rank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '本期超计划',
                            name: 'current_over_plan_num',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '本期净增同比',
                            name: 'current_increase_num_yoy',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '本期净增占比',
                            name: 'current_increase_num_proportion',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return value+'%';
                                }
                            }
                        }, {
                            display: '本期计划完成率',
                            name: 'current_plan_completion_rate',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return value+'%';
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'current_plan_completion_rate_rank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '本期计划完成率同比',
                            name: 'current_plan_completion_rate_yoy',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return value+'%';
                                }
                            }
                        }, {
                            display: '本期增速',
                            name: 'current_increase_num_growth_rate',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return value+'%';
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'current_increase_num_growth_rate_rank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '本期增速同比',
                            name: 'current_increase_num_growth_rate_yoy',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return value+'%';
                                }
                            }
                        }, {
                            display: '本期发生量',
                            name: 'occ',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'occRank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '同比',
                            name: 'occYoy',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '本期预计到期量',
                            name: 'expireEstimate',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'expireEstimateRank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '同比',
                            name: 'expireEstimateYoy',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '本期已到期量',
                            name: 'expire',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'expireRank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '同比',
                            name: 'expireYoy',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '本期剩余预计到期量',
                            name: 'expireEstimateLeft',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }, {
                            display: '排名',
                            name: 'expireEstimateLeftRank',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '-';
                                } else {
                                    return value;
                                }
                            }
                        }, {
                            display: '同比',
                            name: 'expireEstimateLeftYoy',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if (value == null) {
                                    return '-';
                                } else if (value == 0) {
                                    return '0';
                                } else {
                                    return amountConversion(value);
                                }
                            }
                        }],
                    url: '<%=path%>/report/productExcel.htm',
                    sortName: '',
                    // rownumbers: true,
                    alternatingRow: false,
                    height: '100%',
                    width: "100%",
                    usePager: false,
                    tree: { columnId: 'deptId' },
                    toolbar: {
                        items: [
                            ${btnList}
                        ]
                    }
                });
    }




    //下载
    function onDownload() {
        //报表日期
        var date = $("#date").val();
        //报表周期
        var cycel = $("#cycel").val();
        //贷种组合类型 1 全部；2少拆放；4 实体
        var combType = $("#combType").val();
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
        if (!unit) {
            top.Dialog.alert("报表单位不可为空");
            return;
        }

        location = "<%=path%>/report/downloadProductExcel.htm?date=" + date + "&cycel=" + cycel + "&combType=" + combType + "&unit=" + unit;
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


</script>
</html>