//系统路径
var path = "/web";

//加载菜单
function loadMenu(menudata) {
    for (var i = 0; i < menudata.length; i++) {
        var m = menudata[i];
        if (m.upMenuNo != 'REPORT') {
            if (m.menuNo == 'GF-03') {
                $("#jy").val("GF-03");
                $("#jy").render();
            }
            if (m.menuNo == 'GF-04') {
                $("#cs").val("GF-04");
                $("#cs").render();
            }
            if (m.menuNo == 'PM-21') {
                $("#mb").val("PM-21");
                $("#mb").render();
            }
            if ('1' == m.isParent) {//父节点
                $("#top_menu").append("<li class='" + m.menuIcon + "' id='" + m.menuNo + "'><a href='javascript:void(0)' id='" + m.menuUrl + "' name='" + m.menuNo + "'>" + m.menuName + "</a></li>");
            }
            if ('0' == m.isParent) {//子节点
                var pid = m.upMenuNo;
                //判断是否已拼接子菜单
                var isHave = $("#" + pid + "").has("dl").length;
                if (0 == isHave) {//没有
                    if (m.menuNo.length > 5) {
                        $("#" + pid + "").append("<dl class='erji'></dl>");
                    } else {
                        $("#" + pid + "").append("<dl></dl>");
                    }
                }
                if (undefined == m.menuUrl ) {
                    if ($("#" + pid + "").has("dl.erji").length > 0) {
                        $("#" + pid + " dl:not(.erji)").append("<dd id='" + m.menuNo + "'><a href='#'>" + m.menuName + "</a></dd>");
                    } else {
                        $("#" + pid + " dl").append("<dd id='" + m.menuNo + "'><a href='#'>" + m.menuName + "</a></dd>");
                    }
                } else {
                    if (m.menuUrl.indexOf("?") != -1) {
                        $("#" + pid + " dl").append("<dd id='" + m.menuNo + "'><a href='javascript:void(0)' id='" + m.menuUrl + "&menuNo=" + m.menuNo + "'>" + m.menuName + "</a></dd>");
                    }  else if(m.menuUrl == ""){
                        $("#" + pid + " dl").append("<dd id='" + m.menuNo + "'><a href='javascript:void(0)'>" + m.menuName + "</a></dd>");
                    } else {
                        $("#" + pid + " dl").append("<dd id='" + m.menuNo + "'><a href='javascript:void(0)' id='" + m.menuUrl + "?menuNo=" + m.menuNo + "'>" + m.menuName + "</a></dd>");
                    }
                }
            }
        }
    }
    $("dd a").live("click", function () {
        var menuUrl = $(this).attr("id");
        if (undefined == menuUrl) {
            return;
        } else {
            var menuName = $(this).text();
            var menuNo = menuUrl.split("?menuNo=");
            // $(window.parent.document).find("#iframe-index").attr("src", path + menuUrl);
            window.parent.addFloatTab(menuNo[1],(path + menuUrl), menuName);
            // $("#iframe-index").attr("src", path + menuUrl);
        }
    });

    $("li a").live("click", function () {
        var menuUrl = $(this).attr("id");
        var menuNo = $(this).attr("name");
        if (undefined == menuUrl) {
            return;
        } else {
            if ("REPORT" == menuNo) {
                $("#iframe-index").attr("src", path + menuUrl);
            } else {
                return;
            }

        }
    });

}

//加载快捷菜单
function loadFirstMenu(menudata) {
    for (var i = 0; i < menudata.length; i++) {
        var m = menudata[i];
        if (0 == m.isParent) {
            $("#menus-lis")
                .append("<li id='" + m.menuNo + "'><a href='javascript:void(0)' id='" + m.menuUrl + "?menuNo=" + m.menuNo + "'>" + m.menuName + "</a></li>");
            $("#menus-lis li a").live("click", function() {
                var menuUrl = $(this).attr("id");
                var menuName = $(this).text();
                var menuNo = menuUrl.split("?menuNo=");
                // $(window.parent.document).find("#iframe-index").attr("src", path + menuUrl);
              parent.addFloatTab(menuNo[1],(path + menuUrl), menuName);
            });
        }
    }
}