<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>右键菜单</title>
    <!--框架必需start-->
    <script type="text/javascript" src="<%=path%>/libs/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/language/cn.js"></script>
    <script type="text/javascript" src="<%=path%>/libs/js/framework.js"></script>
    <link href="<%=path%>/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" id="skin" prePath="<%=path%>/"/>
    <link rel="stylesheet" type="text/css" id="customSkin"/>
    <!--框架必需end-->
    <!-- ztree start -->
    <script type="text/javascript" src="<%=path%>/libs/js/tree/ztree/ztree.js"></script>
    <link href="<%=path%>/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
    <!-- ztree end -->
    <!--箭头分页start-->
    <script type="text/javascript" src="<%=path%>/libs/js/nav/pageArrow.js"></script>
    <!--箭头分页end-->
</head>
<body>
<div class="b-m-mpanel" style="width: 150px;visibility:hidden;" id="rMenu">
</div>

<table class="tableStyle" width="80%" mode="list" formMode="line">
    <tr>
        <td>机构代码：</td>
        <td><input type="text" disabled="disabled" id="organCode" name="organCode" value="${organCode}"/></td>
        <td>柜员号：</td>
        <td><input type="text" disabled="disabled" id="operCode" name="operCode" value="${operCode}"/></td>
    </tr>
</table>
<div class="box1" panelWidth="500">
    <fieldset>
        <div>
            <ul id="tree-1" name='organ-all' class="ztree"></ul>
        </div>
    </fieldset>
    <div align="center">
        <button type="button" onclick="selectAllItem()" class="button">全选</button>
        <button type="button" onclick="cancelSelectAllItem()" class="button">取消全选</button>
        <button type="button" onclick="isCardNo()" class="saveButton"/>
        <button type="button" onclick="return cancel()" class="cancelButton"/>
    </div>
</div>
<script type="text/javascript">
    //全部选中
    function selectAllItem(){
        var treeObj = $.fn.zTree.getZTreeObj("tree-1");
        treeObj.checkAllNodes(true);
    }
    //取消全部选中
    function cancelSelectAllItem(){
        var treeObj = $.fn.zTree.getZTreeObj("tree-1");
        treeObj.checkAllNodes(false);
    }
    var selectedNodesMap = new Map();
    window.onload = function () {
        $(".saveButton").addClass("button");
        $(".saveButton").append("<span class='icon_ok'>保存</span>");
        $(".cancelButton").addClass("button");
        $(".cancelButton").append("<span class='icon_no'>取消</span>");
        $(".button").css("width", "90px");
        $(".button").css("cursor", "pointer");
        loadLineInfo();
    };
    var zTree;
    var setting = {
        view: {
            dblClickExpand: false
        },
        check: {
            enable: true
        },
        callback: {
            onCheck: onCheck3
        }
    };
    var currentPage = 1;

    function isCardNo() {
        var operCode = $("#operCode").val();
        var url = "<%=path%>/v1/oper/line/update.htm";
        var roleInfo = "";
        var zTree = $.fn.zTree.getZTreeObj("tree-1");
        var nodes = zTree.getCheckedNodes(true);
        for (var i = 0; i < nodes.length; i++) {
            var item = nodes[i];
            roleInfo += item.id + ",";
        }
        if (roleInfo.trim() != "") {
            roleInfo = roleInfo.substr(0, roleInfo.length - 1);
        }
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data: {
                "operCode": operCode,
                "lineInfoList": roleInfo
            },
            success: function (data) {
                if (data.success === true) {
                    if (data.code === 200) {
                        top.Dialog.alert("柜员拥有的条线更新成功!", function () {
                            var menu_id = parent.getCurrentTabId();
                                    if(menu_id==undefined){
                                        top.Dialog.close();
                                        return;
                                    }
                            var menu_frame_id = "page_" + menu_id;
                            top.document.getElementById(menu_frame_id).contentWindow.location.reload(true);
                            top.Dialog.close();
                        });
                        selectedNodesMap = new Map();
                    }
                } else {
                    top.Dialog.alert("柜员拥有的条线更新失败!");
                }
            }
        });
    }

    function goPage() {
        var zTree = $.fn.zTree.getZTreeObj("tree-1");
        var node = zTree.getNodeByParam("id", 0, null);
        zTree.reAsyncChildNodes(node, "refresh");
    }

    function initComplete(zNodes) {
        $.fn.zTree.init($("#tree-1"), setting, zNodes);
        $("#pager").attr("total", 100);
        $("#pager").render();
        $("#pager").bind("pageChange", function (e, idx) {
            currentPage = idx;
            goPage();
        })
    }

    function cancel() {
        top.Dialog.confirm("数据尚未保存，是否退出?|操作提示", function () {
            top.Dialog.close();
        });
    }

    /**后台加载所有角色数据*/
    function loadLineInfo() {
        var operRoleList = [];
        var operCode = $("#operCode").val();
        var operUrl = "<%=path%>/v1/oper/line/product.htm";
        $.ajax({
                url: operUrl,
                type: "POST",
                data: {"operCode": operCode},
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.success === false || data.code !== 200) {
                        top.Dialog.alert("加载条线数据失败!");
                        return;
                    }
                    for (var i = 0; i < data.data.length; i++) {
                        var item = data.data[i];
                        operRoleList.push(item.lineId);
                    }
                }
            }
        );
        var url = "<%=path%>/v1/oper/line/all.htm";
        var organCode = $("#organCode").val();
        $.ajax({
            url: url,
            type: "GET",
            async: false,
            data: {
                "organCode": organCode
            },
            dataType: "json",
            success: function (data) {
                if (null != data) {
                    var zNodes = [];
                    for (var i = 0; i < data.data.length; i++) {
                        var line = data.data[i];
                        var checked = false;
                        if (operRoleList.indexOf(line.lineId) !=-1) {
                            checked = true;
                        }
                        zNodes.unshift();
                        var nodeData = {
                            id: line.lineId,
                            parentId: 0,
                            name: line.lineName,
                            open: true,
                            noR: true,
                            checked: checked,
                            icon: "<%=path%>/libs/icons/user_group.gif"
                        };
                        zNodes.unshift();
                        zNodes.push(nodeData);
                    };
                    initComplete(zNodes);
                }
            }
        });
    }

    /**添加节点,初始化视图渲染调用*/
    function initComplete(zNodes) {
        $.fn.zTree.init($("#tree-1"), setting, zNodes);
    }

    //根据设置弹出不同的右键菜单
    function showRMenu(type, x, y) {
        $("#rMenu ul").show();
        $("#m_check").show();
        $("#m_unCheck").show();
        $("#rMenu").css({"top": y + "px", "left": x + "px", "visibility": "visible"});
    }

    //点击菜单外时隐藏菜单
    function onBodyMouseDown(event) {
        if (!(event.target.id === "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            $("#rMenu").css({"visibility": "hidden"});
        }
    }

    var addCount = 1;

    function onCheck3(event, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("tree-1");
        var maxValue = zTree.getCheckedNodes(true).length;
        // if (maxValue > 3) {
        //     top.Dialog.alert("用户最多被赋予3个条线");
        //     zTree.checkNode(treeNode, false, true, true);
        // }
    }

    function Map() {
        this.elements = new Array();
// 获取Map元素个数
        this.size = function () {
            return this.elements.length;
        },
// 判断Map是否为空
            this.isEmpty = function () {
                return (this.elements.length < 1);
            },
// 删除Map所有元素
            this.clear = function () {
                this.elements = new Array();
            },
// 向Map中增加元素（key, value)
            this.put = function (_key, _value) {
                if (this.containsKey(_key) == true) {
                    if (this.containsValue(_value)) {
                        if (this.remove(_key) == true) {
                            this.elements.push({
                                key: _key,
                                value: _value
                            });
                        }
                    } else {
                        this.elements.push({
                            key: _key,
                            value: _value
                        });
                    }
                } else {
                    this.elements.push({
                        key: _key,
                        value: _value
                    });
                }
            },
// 向Map中增加元素（key, value)
            this.set = function (_key, _value) {
                if (this.containsKey(_key) == true) {
                    if (this.containsValue(_value)) {
                        if (this.remove(_key) == true) {
                            this.elements.push({
                                key: _key,
                                value: _value
                            });
                        }
                    } else {
                        this.elements.push({
                            key: _key,
                            value: _value
                        });
                    }
                } else {
                    this.elements.push({
                        key: _key,
                        value: _value
                    });
                }
            },
// 删除指定key的元素，成功返回true，失败返回false
            this.remove = function (_key) {
                var bln = false;
                try {
                    for (i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key == _key) {
                            this.elements.splice(i, 1);
                            return true;
                        }
                    }
                } catch (e) {
                    bln = false;
                }
                return bln;
            },
// 删除指定key的元素，成功返回true，失败返回false
            this.delete = function (_key) {
                var bln = false;
                try {
                    for (i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key == _key) {
                            this.elements.splice(i, 1);
                            return true;
                        }
                    }
                } catch (e) {
                    bln = false;
                }
                return bln;
            },
// 获取指定key的元素值value，失败返回null
            this.get = function (_key) {
                try {
                    for (i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key == _key) {
                            return this.elements[i].value;
                        }
                    }
                } catch (e) {
                    return null;
                }
            },
// set指定key的元素值value
            this.setValue = function (_key, _value) {
                var bln = false;
                try {
                    for (i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key == _key) {
                            this.elements[i].value = _value;
                            return true;
                        }
                    }
                } catch (e) {
                    bln = false;
                }
                return bln;
            },
// 获取指定索引的元素（使用element.key，element.value获取key和value），失败返回null
            this.element = function (_index) {
                if (_index < 0 || _index >= this.elements.length) {
                    return null;
                }
                return this.elements[_index];
            },
// 判断Map中是否含有指定key的元素
            this.containsKey = function (_key) {
                var bln = false;
                try {
                    for (i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key == _key) {
                            bln = true;
                        }
                    }
                } catch (e) {
                    bln = false;
                }
                return bln;
            },
// 判断Map中是否含有指定key的元素
            this.has = function (_key) {
                var bln = false;
                try {
                    for (i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].key == _key) {
                            bln = true;
                        }
                    }
                } catch (e) {
                    bln = false;
                }
                return bln;
            },
// 判断Map中是否含有指定value的元素
            this.containsValue = function (_value) {
                var bln = false;
                try {
                    for (i = 0; i < this.elements.length; i++) {
                        if (this.elements[i].value == _value) {
                            bln = true;
                        }
                    }
                } catch (e) {
                    bln = false;
                }
                return bln;
            },
// 获取Map中所有key的数组（array）
            this.keys = function () {
                var arr = new Array();
                for (i = 0; i < this.elements.length; i++) {
                    arr.push(this.elements[i].key);
                }
                return arr;
            },
// 获取Map中所有value的数组（array）
            this.values = function () {
                var arr = new Array();
                for (i = 0; i < this.elements.length; i++) {
                    arr.push(this.elements[i].value);
                }
                return arr;
            };
        /**
         * map遍历数组
         * @param callback [function] 回调函数；
         * @param context [object] 上下文；
         */
        this.forEach = function forEach(callback, context) {
            context = context || window;
//IE6-8下自己编写回调函数执行的逻辑
            var newAry = new Array();
            for (var i = 0; i < this.elements.length; i++) {
                if (typeof callback === 'function') {
                    var val = callback.call(context, this.elements[i].value, this.elements[i].key, this.elements);
                    newAry.push(this.elements[i].value);
                }
            }
            return newAry;
        }
    }
</script>
</body>
</html>