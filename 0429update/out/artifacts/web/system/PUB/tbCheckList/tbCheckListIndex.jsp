<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>对账管理</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>

                <td align="right">产品代码：</td>
                <td>
                    <input type="text"  name="productCode" />
                </td>

                <td align="right">交易机构：</td>
                <td>
                    <input type="text"  name="transOrgan" />
                </td>

                <td align="right">借据编码：</td>
                <td>
                    <input type="text"  name="paperCode" />
                </td>

                <td align="right">借据状态：</td>
                <td>
                    <select name="paperStatus">
                        <option value="">--请选择--</option>
                        <option value="0">正常</option>
                        <option value="1">结清</option>
                        <option value="2">核销</option>
                    </select>
                </td>

                <td align="right">对账状态：</td>
                <td>
                    <select name="checkStatus">
                        <option value="">--请选择--</option>
                        <option value="0">未对账</option>
                        <option value="1">已对账</option>
                        <option value="2">对账信息无效</option>
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
<script type="text/javascript">
    var grid = null;

    function initComplete() {
        top.Dialog.close();
        grid = $("#dataBasic")
            .quiGrid(
                {
                    columns: [
                        {
                            display: '数据日期',
                            name: 'transDate',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '业务系统',
                            name: 'systemid',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '借据编号',
                            name: 'paperCode',
                            width: '12%',
                            align: 'center'
                        },{
                            display: '合同号',
                            name: 'contractCode',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '交易机构',
                            name: 'transOrgan',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '贷款金额',
                            name: 'amt',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '贷款余额',
                            name: 'balance',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '币种',
                            name: 'ccy',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '借据状态',
                            name: 'paperStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" === value || 0 === value) {
                                    return "正常";
                                }else if ("1" === value || 1 === value) {
                                    return "结清";
                                } else if ("2" === value || 2 === value) {
                                    return "核销";
                                }
                            }
                        },{
                            display: '对账状态',
                            name: 'checkStatus',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("0" === value || 0 === value) {
                                    return "未对账";
                                }else if ("1" === value || 1 === value) {
                                    return "已对账";
                                } else if ("2" === value || 2 === value) {
                                    return "对账信息无效";
                                }
                            }
                        },
                        {
                            display: '授信编码',
                            name: 'grantCode',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '授信余额',
                            name: 'grantBalance',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '原始到期日',
                            name: 'oriLimit',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '放款时间',
                            name: 'loanDate',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '逾期天数',
                            name: 'overdueDays',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '到期日',
                            name: 'limitDate',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '产品代码',
                            name: 'productCode',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '客户编号',
                            name: 'custCode',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '客户姓名',
                            name: 'custName',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '证件类型',
                            name: 'certType',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '证件号码',
                            name: 'certNum',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '客户所属行业',
                            name: 'custIndustry',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '贷款用途',
                            name: 'loanUsage',
                            width: '15%',
                            align: 'center'
                        },{
                            display: '贷款投放行业',
                            name: 'loanIndustry',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '利率类型',
                            name: 'interestType',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '浮动利率',
                            name: 'floatingInterest',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '基准利率',
                            name: 'baseInterest',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '执行利率',
                            name: 'executeInterest',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '客户经理号',
                            name: 'managerCode',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '客户经理名',
                            name: 'managerName',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '备注信息',
                            name: 'remark',
                            width: '10%',
                            align: 'center'
                        }],
                    url: '<%=path%>/tbCheckList/findPage.htm',
                    sortName: '',
                    rownumbers: true,
                    checkbox: true,
                    height: '100%',
                    width: "100%",
                    pageSize: 10,
                   toolbar: {
                        items: [
                            ${btnList}
                        ]
                    },
                    onCheckRow: function (rowdata, rowindex, rowDomElement) {
                        for (var i = 0; i < grid.getData().length; i++) {
                            var row = grid.getRow(i);
                            if (row != rowindex) {
                                grid.unselect(row);
                            }
                        }
                    }
                });
    }


    //详情
    function onInfo() {
        if (selectOneRow(grid)) {
            var rows = grid.getSelectedRows();
            showDialog("<%=path%>/creditPlan/toDetailCreditPlan.htm?planId=" + rows[0].planid, "对账信息", 1000, 600);
        }
    }

    //查询
    function searchHandler() {
        var query = $("#queryForm").formToArray();
        grid.setOptions({
            params: query
        });
        //页号重置为1
        grid.setNewPage(1);
        grid.loadData();//加载数据
    }

    //重置查询
    function resetSearch() {
        $("#queryForm")[0].reset();
        $("#planMonth").render();
        $('#search').click();
    }




</script>
</body>
</html>