<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>流水管理</title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width:100%;">
            <tr>

                <td>交易产品编码：</td>
                <td width="20">
                    <input name="productCode" type="text"  />
                </td>

                <td align="right">交易机构：</td>
                <td>
                    <input type="text"  name="transOrgan" />
                </td>

                <td align="right">借据编码/交易id：</td>
                <td>
                    <input type="text"  name="paperCode" />
                </td>

                <td align="right">交易类型：</td>
                <td>
                    <select name="tradeType">
                        <option value="">--请选择--</option>
                        <option value="1">额度申请</option>
                        <option value="2">通知</option>
                        <option value="3">查询</option>
                    </select>
                </td>

                <td align="right">通知类型：</td>
                <td>
                    <select name="notifyType">
                        <option value="">--请选择--</option>
                        <option value="1">撤销</option>
                        <option value="2">失败</option>
                        <option value="3">还款</option>
                        <option value="4">展期</option>
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
                            display: '流水号',
                            name: 'ctlSeqNo',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '借据编码/交易id',
                            name: 'paperCode',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '贷款金额/还款金额',
                            name: 'amt',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '币种',
                            name: 'ccy',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            display: '交易机构',
                            name: 'transOrgan',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '交易产品编码',
                            name: 'productCode',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '交易类型',
                            name: 'tradeType',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" === value || 1 === value) {
                                    return "额度申请";
                                } else if ("2" === value || 2 === value) {
                                    return "通知";
                                }else if ("3" === value || 3 === value) {
                                    return "查询";
                                }
                            }
                        },{
                            display: '通知类型',
                            name: 'notifyType',
                            width: '10%',
                            align: 'center',
                            render: function (rowdata, rowindex, value, column) {
                                if ("1" === value || 1 === value) {
                                    return "撤销";
                                } else if ("2" === value || 2 === value) {
                                    return "失败";
                                }else if ("3" === value || 3 === value) {
                                    return "还款";
                                }else if ("4" === value || 4 === value) {
                                    return "展期";
                                }
                            }
                        },
                        {
                            display: '系统跟踪号ESB',
                            name: 'sysTrackNo',
                            width: '20%',
                            align: 'center'

                        },{
                            display: '消费方系统代码',
                            name: 'transInst',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '请求方系统代码',
                            name: 'sendInst',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '提供方系统代码',
                            name: 'destInst',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '交易日期',
                            name: 'transDate',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '交易时间',
                            name: 'transTime',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '会计日期',
                            name: 'accDate',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '业务系统平台号',
                            name: 'systemid',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '是否自动放款交易',
                            name: 'isAuto',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '放款日期',
                            name: 'loanDate',
                            width: '10%',
                            align: 'center'
                        }, {
                            display: '到期日期',
                            name: 'limitDate',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '返回码',
                            name: 'respCode',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '返回信息',
                            name: 'respInfo',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '备注',
                            name: 'remark',
                            width: '10%',
                            align: 'center'
                        },{
                            display: '额度具体扣减情况',
                            name: 'quotaDetail',
                            width: '10%',
                            align: 'center'
                        }],
                    url: '<%=path%>/tbTransSeq/findPage.htm',
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
            showDialog("<%=path%>/creditPlan/toDetailCreditPlan.htm?planId=" + rows[0].planid, "流水信息", 1000, 600);
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