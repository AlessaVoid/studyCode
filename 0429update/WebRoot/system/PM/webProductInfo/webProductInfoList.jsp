<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title></title>
    <%@include file="/common/common_list.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body>
<!-- 查询位置 -->
<div class="box2_custom" boxType="box2" panelTitle="查询条件" id="searchPanel">
    <form action="" id="queryForm" method="post">
        <table class="tableStyle" mode="list" formMode="line" style="width: 100%;">
            <tr>
                <td width="8%" align="right">
                    产品编号：
                </td>
                <td width="17">
                    <div class="suggestion" id="productCode" name="productCode" matchName="productCode"
                         url="<%=path%>/webProduct/selectProductCode.htm" suggestMode="remote"></div>
                </td>
                <td width="8%" align="right">
                    产品名称：
                </td>
                <td width="17">
                    <div class="suggestion" id="productName" name="productName" matchName="productName"
                         url="<%=path%>/webProduct/selectProductName.htm" suggestMode="remote"></div>
                </td>
                <td width="8%" align="right">
                    产品级别:
                </td>
                <td width="17">
                    <dic:select id="productLevel" name="productLevel" matchName="productLevel"
                                dicType="PRODUCT_LEVEL"></dic:select>
                </td>
                <td width="8%" align="right">
                    所属业务系统名称:
                </td>
                <td width="17">
                    <dic:select id="productSystemId" name="productSystemId" matchName="productSystemId"
                                dicType="PRODUCT_SYSTEM"></dic:select>
                </td>
                <td width="8%" align="right">
                    产品状态:
                </td>
                <td width="17">
                    <dic:select id="productStatus" name="productStatus" matchName="productStatus"
                                dicType="PRODUCT_STATUS"></dic:select>
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
<div class="box2_custom" boxType="box2" panelTitle="数据列表" class="padding_right5">
    <div id="dataBasic"></div>
</div>
<script type="text/javascript">
    var grid = null;

    function initComplete() {
        top.Dialog.close();
        grid = $("#dataBasic").quiGrid(
            {
                columns: [
                    {
                        display: '产品编号',
                        name: 'productCode',
                        width: '20%',
                        align: 'center'
                    }, {
                        display: '产品名称',
                        name: 'productName',
                        width: '20%',
                        align: 'center'
                    },
                    {
                        display: '所属业务系统名称',
                        name: 'productSystemId',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if (value === 99460000000 || value === "99460000000") {
                                return "公司信贷系统";
                            } else if (value === 99470000000 || value === "99470000000") {
                                return "商业票据系统";
                            }else if (value === 99710670000 || value === "99710670000") {
                                return "信贷业务平台";
                            }else if (value === 99710720001 || value === "99710720001") {
                                return "资金业务平台";
                            }
                        }
                    },  {
                        display: '产品级别',
                        name: 'productLevel',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if (value === 1 || value === "1") {
                                return "一级";
                            } else if (value === 2 || value === "2") {
                                return "二级";
                            } else if (value === 3 || value === "3") {
                                return "三级";
                            }else if (value === 4 || value === "4") {
                                return "四级";
                            }else if (value === 5 || value === "5") {
                                return "五级";
                            }else if (value === 6 || value === "6") {
                                return "六级";
                            }
                        }
                    },
                    // {
                    //     display: '产品上级编号',
                    //     name: 'productUpCode',
                    //     width: '17%',
                    //     align: 'center',
                    // },
                    {
                        display: '产品状态',
                        name: 'productStatus',
                        width: '20%',
                        align: 'center',
                        render: function (rowdata, rowindex, value, column) {
                            if (value == 1 || value === '1') {
                                return "可组合";
                            } else if (value == 2 || value === '1') {
                                return "已组合";
                            } else if (value == -1 || value === '-1') {
                                return "已删除";
                            }
                        }
                    }],
                url: '<%=path%>/webProduct/listAll.htm',
                sortName: 'productCode',
                rownumbers: true,
                checkbox: true,
                height: '100%',
                width: "100%",
                pageSize: 10,
            });
    }

    //搜索操作
    function onSearch() {

    }

    //新增
    function onInsert() {
    }

    //修改
    function onUpdate() {
    }

    //删除
    function onDelete() {
    }

    //更新
    function onInfo() {

    }

    //查询
    function searchHandler() {
        var query = $("#queryForm").formToArray();
        //alert(JSON.stringify(query))
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
        $('#search').click();
    }

    //刷新表格数据并重置排序和页数
    function refresh(isUpdate) {
        if (!isUpdate) {
            //重置排序
            grid.options.sortName = 'productCode';
            grid.options.sortOrder = "desc";
            //页号重置为1
            grid.setNewPage(1);
        }
        grid.loadData();
    }
</script>
</body>
</html>
