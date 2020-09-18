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
                <td>报表周期：</td>
                <td>
                    <%--                    <dic:select dicType="REPORT_PARAM_DATE" name="cycel" id="cycel"></dic:select>--%>
                    <select id="cycel" name="cycel">
                        <option value="1">年</option>
                        <option value="2">季</option>
                        <option value="3">月</option>
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
    <div align="center" ><h3  style="display: inline;">同业表&nbsp;&nbsp;</h3> </div>
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
                            display: '银行名称',
                            name: 'name',
                            width: '20%',
                            id: 'deptId',
                            align: 'left',
                            frozen: true
                        },
                        {
                            display: '各项贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'gxdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'gxdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'gxdkIncrementGrowthRatio',
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
                                        },/*{
                                            display: '余额占比',
                                            name: 'gxdkSelfBalanceRaito',
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
                                        },*//*{
                                            display: '余额占比较期初',
                                            name: 'gxdkSelfBalanceRaitoCompareDateBegin',
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
                                        },*//*{
                                            display: '净增占比',
                                            name: 'gxdkSelfIncrementRatio',
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
                                        }*/
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'gxdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'gxdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'gxdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '实体贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'stdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'stdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'stdkIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'stdkSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'stdkSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'stdkSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'stdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'stdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'stdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '境内个人贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'grdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'grdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'grdkIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'grdkSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'grdkSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'grdkSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'grdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'grdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'grdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '消费贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'xfdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'xfdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'xfdkIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'xfdkSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'xfdkSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'xfdkSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'xfdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'xfdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'xfdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '境内公司贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'gsdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'gsdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'gsdkIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'gsdkSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'gsdkSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'gsdkSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'gsdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'gsdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'gsdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '并购贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'bgdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'bgdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'bgdkIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'bgdkSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'bgdkSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'bgdkSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'bgdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'bgdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'bgdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '票据融资',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'pjrzBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'pjrzIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'pjrzIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'pjrzSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'pjrzSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'pjrzSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'pjrzAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'pjrzAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'pjrzAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '非存款类金融机构贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'fcdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'fcdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'fcdkIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'fcdkSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'fcdkSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'fcdkSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'fcdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'fcdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'fcdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '境外贷款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'jwdkBalance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'jwdkIncrement',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'jwdkIncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'jwdkSelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'jwdkSelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'jwdkSelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'jwdkAllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'jwdkAllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'jwdkAllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        },
                        {
                            display: '各项垫款',
                            align: 'center',
                            columns: [
                                {
                                    display: '本行',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额',
                                            name: 'gxdk2Balance',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '周期净增',
                                            name: 'gxdk2Increment',
                                            width: '10%',
                                            align: 'center',
                                            render: function (rowdata, rowindex, value, column) {
                                                return amountConversion(value);
                                            }
                                        },{
                                            display: '净增增速',
                                            name: 'gxdk2IncrementGrowthRatio',
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
                                        },{
                                            display: '余额占比',
                                            name: 'gxdk2SelfBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'gxdk2SelfBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'gxdk2SelfIncrementRatio',
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
                                        }
                                    ]
                                },
                                {
                                    display: '银行业',
                                    align: 'center',
                                    columns:[
                                        {
                                            display: '余额占比',
                                            name: 'gxdk2AllBalanceRaito',
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
                                        },{
                                            display: '余额占比较期初',
                                            name: 'gxdk2AllBalanceRaitoCompareDateBegin',
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
                                        },{
                                            display: '净增占比',
                                            name: 'gxdk2AllIncrementRatio',
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
                                        }
                                    ]

                                }
                            ]
                        }
                    ],
                    url: '<%=path%>/report/bankIndustryExcel.htm',
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
            format: 'yyyymm',
            weekStart: 1,
            autoclose: true,
            startView: 3,
            minView: 3,
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

        if (!date) {
            top.Dialog.alert("报表日期不可为空");
            return;
        }
        if (!cycel) {
            top.Dialog.alert("报表周期不可为空");
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
        if (!unit) {
            top.Dialog.alert("报表单位不可为空");
            return;
        }

        location = "<%=path%>/report/downloadBankIndustryExcel.htm?date=" + date + "&cycel=" + cycel + "&unit=" + unit;
    }



</script>
</html>