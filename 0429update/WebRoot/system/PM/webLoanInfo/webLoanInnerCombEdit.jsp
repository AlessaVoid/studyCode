<%@page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/common/common_edit.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>贷种内占用规则维护</title>
    <!--框架必需start-->
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <!--框架必需end-->

    <!--数据表格start-->
    <script src="<%=path%>/libs/js/table/quiGrid.js" type="text/javascript"></script>
    <!--数据表格end-->

    <!-- 日期选择框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/datePicker/WdatePicker.js"></script>
    <!-- 日期选择框end -->

    <!-- 数字步进器start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/stepper.js"></script>
    <!-- 数字步进器end -->

    <!-- 树形下拉框start -->
    <script type="text/javascript" src="<%=path%>/libs/js/form/selectTree.js"></script>
    <!-- 树形下拉框end -->
</head>
<body onload="flash()">
<div>
    <table class="tableStyle" width="100%" mode="list" formMode="line">
        <tr>
            <td align="right">
                贷种组合编号：
            </td>
            <td>
                <input disabled="disabled" type="text" id="parentCombCode" name="parentCombCode"
                       value="${webLoanComb.combCode}"
                       class="validate[required]" maxlength="15"/>
            </td>
        </tr>
        <tr>
            <td align="right">
                贷种组合id：
            </td>
            <td>
                <input type="text" disabled="disabled" id="combName" name="combName"
                       value="${webLoanComb.combName}"
                       class="validate[required]" maxlength="20"/>
            </td>
        </tr>
        <tr>
            <td align="right">
                组内占用类型:
            </td>
            <td>
                <dic:select id="takenType" dicType="TAKEN_TYPE" name="takenType"
                            tgClass="validate[required]"
                            dicNo="${InnerTakenType}"
                            required="true"></dic:select>
            </td>
        </tr>
    </table>
</div>
<div id="takenDetailList" style="display: none" class="padding_right5">
    <div id="maingrid"></div>
</div>
<div align="center" style="alignment: center">
    <button type="button" onclick="return sub()" class="saveButton"> 保存</button>
    <button type="button" onclick="return cancel()" class="cancelButton"> 取消</button>
</div>
</body>
<script type="text/javascript">
    var combList;
    var checkList = [];
    $(function () {
        setInterval(function () {
            for (var i = 0; i < deleteRows.size(); i++) {
                var item = deleteRows.values()[i];
                combTakenGrid.deleteRow(item);
            }
            deleteRows = new Set();
        }, 1000);
        $("#takenType").bind("change", function () {
            var parentCombCode = $("#parentCombCode").val();
            var takenTypeList = document.getElementById("takenType");
            var takenType = takenTypeList.options[takenTypeList.selectedIndex].value;
            var takenDetailList = document.getElementById("takenDetailList");
            if (takenType === '1') {
                takenDetailList.style.display = 'block';
                var initCombUrl = "<%=path%>/webLoan/selectLoanCombByParentId.htm?combParentCode=" + parentCombCode;
                testData.rows = [];
                $.ajax({
                        type: 'GET',
                        url: initCombUrl,
                        async: false,
                        success: function (res) {
                            var data = JSON.parse(res);
                            if (data.success === true || data.success === "true") {
                                var nodes = data.data;
                                for (var i = 0; i < nodes.length; i++) {
                                    var item = nodes[i];
                                    var selectItem = {
                                        "combId": item.combId,
                                        "combName":item.combName,
                                        "combTakenId": item.combTakenId,
                                        "combTakenName":item.combTakenName
                                    };
                                    testData.rows.push(selectItem);
                                }
                                ;
                                combTakenGrid.loadData();
                            }
                        }
                    }
                );
            } else {
                takenDetailList.style.display = 'none';
            }
        });
    });
    var combTakenGrid;
    var testData = {"form.paginate.pageNo": 1, "form.paginate.totalRows": 13, "rows": []};

    function sub() {
        top.Dialog.confirm("确定要保存操作吗?|操作提示", function () {
            var partUrl = "<%=path%>/webLoan/takeLoanCombInfo.htm";
            var takenTypeList = document.getElementById("takenType");
            var takenType = takenTypeList.options[takenTypeList.selectedIndex].value;
            var parentCombCode = $("#parentCombCode").val();
            top.Dialog.confirm("是否保存信息?", function () {
                var gridData = JSON.stringify(combTakenGrid.getData());
                $.post(partUrl, {
                    "gridData": gridData,
                    "parentCombCode": parentCombCode,
                    "takenType": takenType
                }, function (result) {
                    if (result.success === "true" || result.success === true) {
                        top.Dialog.alert("配置组内占用规则成功");
                    } else {
                        top.Dialog.alert("配置组内占用规则失败");
                    }
                }, "json");
            });
        });
    }

    function flash() {
        var parentCombCode = $("#parentCombCode").val();
        var takenTypeList = document.getElementById("takenType");
        var takenType = takenTypeList.options[takenTypeList.selectedIndex].value;
        var takenDetailList = document.getElementById("takenDetailList");
        if (takenType === '1') {
            takenDetailList.style.display = 'block';
            var initCombUrl = "<%=path%>/webLoan/selectLoanCombByParentId.htm?combParentCode=" + parentCombCode;
            testData.rows = [];
            $.ajax({
                    type: 'GET',
                    url: initCombUrl,
                    async: false,
                    success: function (res) {
                        var data = JSON.parse(res);
                        if (data.success === true || data.success === "true") {
                            var nodes = data.data;
                            for (var i = 0; i < nodes.length; i++) {
                                var item = nodes[i];
                                var selectItem = {
                                    "combId": item.combId,
                                    "combName":item.combName,
                                    "combTakenId": item.combTakenId,
                                    "combTakenName":item.combTakenName
                                };
                                testData.rows.push(selectItem);
                            }
                            ;
                            combTakenGrid.loadData();
                        }
                    }
                }
            );
        } else {
            takenDetailList.style.display = 'none';
        }
    }

    function initComplete() {
        var parentCombCode = $("#parentCombCode").val();
        var combUrl = "<%=path%>/webLoan/listAllSelectedLoanCombByLevelAndCode.htm?combLevel=2&combCode=" + parentCombCode;
        $.ajax({
                type: 'GET',
                url: combUrl,
                async: false,
                success: function (res) {
                    var data = JSON.parse(res);
                    if (data.success === true || data.success === "true") {
                        var nodes = data.data;
                        var selectList = [];
                        for (var i = 0; i < nodes.length; i++) {
                            var item = nodes[i];
                            var selectItem = {
                                "key": item.name,
                                "value": item.name
                            };
                            selectList.push(selectItem);
                            checkList.push(item.name);
                        }
                        ;
                        combList = {"list": selectList}
                    }
                }
            }
        );
        initGrid();
    }

    function initGrid() {
        combTakenGrid = $("#maingrid").quiGrid({
            columns: [
                {
                    display: '占用贷种组合',
                    name: 'combId',
                    align: 'left',
                    width: 150,
                    height: 50,
                    editor: {
                        type: 'select',
                        data: combList,
                        selWidth: 150,
                        link: true,
                        relation: "parent",
                        render: function (item) {
                            for (var i = 0; i < combList["list"].length; i++) {
                                if (combList["list"][i]['key'] === item.combId)
                                    return combList["list"][i]['key']
                            }
                            return item.combId;
                        }
                    },
                },
                {
                    display: '被占用贷种组合',
                    name: 'combTakenId',
                    align: 'left',
                    width: 165,
                    editor: {
                        type: 'select',
                        data: combList,
                        selWidth: 150,
                        link: true,
                        relation: "parent",
                        render: function (item) {
                            for (var i = 0; i < combList["list"].length; i++) {
                                if (combList["list"][i]['key'] === item.combTakenId)
                                    return combList["list"][i]['key']
                            }
                            return item.combId;
                        }
                    }
                }
            ],
            data: testData,
            rownumbers: true,
            pageSize: 10,
            dataAction: "server",
            usePager: false,
            height: "95%",
            width: "100%",
            checkbox: true,
            enabledEdit: true,
            isScroll: true,
            percentWidthMode: true,
            onBeforeSubmitEdit: onBeforeSubmitEdit,
            toolbar:
                {
                    items: [
                        {text: '新增', click: addUser, iconClass: 'icon_add'}, {line: true},
                        {text: '删除', click: deleteUser, iconClass: 'icon_remove'}, {line: true}
                    ]
                }
        });
    }

    var deleteRows = new Set();
    var lastRow = -1;

    //编辑提交前事件
    function onBeforeSubmitEdit(e) {
        if (checkList.indexOf(e.record.combId) !== -1 && checkList.indexOf(e.record.combTakenId) !== -1 && e.record.__status === "add") {
            var data = combTakenGrid.getData();
            var flag = false;
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                if (e.record.combId === item.combId &&
                    e.record.combTakenId === item.combTakenId &&
                    e.record.rowPosition !== item.rowPosition) {
                    flag = true;
                }
            }
            ;
            if (e.record.combId === e.record.combTakenId) {
                top.Dialog.alert("组合占用双方不能为同一贷种!");
                deleteRows.add(e.rowindex);
                return false;
            }
            // if (e.record.combId === "" || e.record.combTakenId === "") {
            //     top.Dialog.alert("请完善信息!");
            //     // deleteRows.add(e.rowindex);
            //     // return false;
            // }
            if (flag === true && e.record.__status === "add") {
                top.Dialog.alert("相同占用规则配置已存在!");
                deleteRows.add(e.rowindex);
                return false;
            }
        }
        return true;
    }



    //新增
    function addUser() {
        if (lastRow >= 0) {
            var row = combTakenGrid.getRow(lastRow);
            if (row != null && row !== "undefined") {
                if (row.__status !== "delete" && (row.combId === "" || row.combTakenId === "")) {
                    combTakenGrid.deleteRow(lastRow);
                    top.Dialog.alert('上一行记录不能有空值');
                    lastRow = -1;
                    return;
                }
            }
        }
        var rowData = {
            combId: "",
            takenCombId: ""
        };
        var rowdata = combTakenGrid.add(rowData);
        lastRow = rowdata.__index;
    }
    function deleteUser() {
        var rows = combTakenGrid.getSelectedRows();
        if (rows.length === 0) {
            top.Dialog.alert('请至少选择一行');
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            var index = rows[i];
            combTakenGrid.deleteRow(rows[i]);
            // top.Dialog.alert('请点击保存按钮以确认删除');
        }
    }

    function Set() {
        /**
                   * 集合元素的容器，以对象来表示
                   * @type {Object}
                   */
        var items = {};
        /**
                  * 检测集合内是否有某个元素
                  * @param {Any} value  要检测的元素
                  * @return {Boolean}    如果有，返回true
                  */
        this.has = function (value) {
            // hasOwnProperty的问题在于
            // 它是一个方法，所以可能会被覆写
            return items.hasOwnProperty(value)
        };
        /**
                      * 给集合内添加某个元素
                      * @param {Any} value 要被添加的元素
                      * @return {Boolean}    添加成功返回True。
                      */
        this.add = function (value) {
            //先检测元素是否存在。
            if (!this.has(value)) {
                items[value] = value;
                return true;
            }
            //如果元素已存在则返回false
            return false;
        };
        /**
                      * 移除集合中某个元素
                      * @param {Any} value 要移除的元素
                      * @return {Boolean}    移除成功返回True。
                      */
        this.remove = function (value) {
            //先检测元素是否存在。
            if (this.has(value)) {
                delete items[value];
                return true;
            }
            //如果元素不存在，则删除失败返回false
            return false;
        };
        /**
                      * 清空集合
                      */
        this.clear = function () {
            this.items = {};
        };
        /**
                      * 返回集合长度，只可用于IE9及以上
                      * @return {Number} 集合长度
                      */
        this.size = function () {
            // Object.keys方法能将对象转化为数组
            // 只可用于IE9及以上，但很方便
            return Object.keys(items).length;
        };
        /**
                      * 返回集合长度，可用于所有浏览器
                      * @return {Number} 集合长度
                      */
        this.sizeLegacy = function () {
            var count = 0;
            for (var prop in items) {
                if (items.hasOwnProperty(prop)) {
                    ++count;
                }
            }
            return count;
        };
        /**
                      * 返回集合转换的数组，只可用于IE9及以上
                      * @return {Array} 转换后的数组
                      */
        this.values = function () {
            return Object.keys(items);
        };

        /**
                      * 返回集合转换的数组，可用于所有浏览器
                      * @return {Array} 转换后的数组
                      */
        this.valuesLegacy = function () {
            var keys = [];
            for (var key in items) {
                keys.push(key)
            }
            return keys;
        };
        /**
                      * 返回两个集合的并集
                      * @param {Set} otherSet 要进行并集操作的集合
                      * @return {Set}     两个集合的并集
                      */
        this.union = function (otherSet) {
            //初始化一个新集合，用于表示并集。
            var unionSet = new Set();
            //将当前集合转换为数组，并依次添加进unionSet
            var values = this.values();
            for (var i = 0; i < values.length; i++) {
                unionSet.add(values[i]);
            }

            //将其它集合转换为数组，依次添加进unionSet。
            //循环中的add方法保证了不会有重复元素的出现
            values = otherSet.values();
            for (var i = 0; i < values.length; i++) {
                unionSet.add(values[i]);
            }

            return unionSet;
        };
        /**
                      * 返回两个集合的交集
                      * @param {Set} otherSet 要进行交集操作的集合
                      * @return {Set}     两个集合的交集
                      */
        this.intersection = function (otherSet) {
            //初始化一个新集合，用于表示交集。
            var interSectionSet = new Set();
            //将当前集合转换为数组
            var values = this.values();
            //遍历数组，如果另外一个集合也有该元素，则interSectionSet加入该元素。
            for (var i = 0; i < values.length; i++) {

                if (otherSet.has(values[i])) {
                    interSectionSet.add(values[i])
                }
            }

            return interSectionSet;
        };
        /**
                      * 返回两个集合的差集
                      * @param {Set} otherSet 要进行差集操作的集合
                      * @return {Set}     两个集合的差集
                      */
        this.difference = function (otherSet) {
            //初始化一个新集合，用于表示差集。
            var differenceSet = new Set();
            //将当前集合转换为数组
            var values = this.values();
            //遍历数组，如果另外一个集合没有该元素，则differenceSet加入该元素。
            for (var i = 0; i < values.length; i++) {
                if (!otherSet.has(values[i])) {
                    differenceSet.add(values[i])
                }
            }

            return differenceSet;
        };
        /**
                      * 判断该集合是否为传入集合的子集
                      * @param {Set} otherSet 传入的集合
                      * @return {Boolean}   是则返回True
                      */
        this.subset = function (otherSet) {
            // 第一个判定,如果该集合长度大于otherSet的长度
            // 则直接返回false
            if (this.size() > otherSet.size()) {
                return false;
            } else {
                // 将当前集合转换为数组
                var values = this.values();

                for (var i = 0; i < values.length; i++) {

                    if (!otherSet.has(values[i])) {
                        // 第二个判定。只要有一个元素不在otherSet中
                        // 那么则可以直接判定不是子集，返回false
                        return false;
                    }
                }

                return true;
            }
        };
    }
</script>

</html>