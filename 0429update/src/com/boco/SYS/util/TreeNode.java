package com.boco.SYS.util;

import java.io.Serializable;
import java.util.Map;

public class TreeNode implements Serializable {
    private static final long serialVersionUID = -6540074649689171902L;

    private String id;

    private String parentId;

    private String oldParentId;

        private boolean isParent;

    private String name;

    private boolean open;

    private String url;

    private String target;

    private String iconClose;

    private String iconOpen;

        private String icon;

    private String menuType;

    private String desc;

    private String manager;

    private String phone;

    private boolean existed;

    private Map<Object, Object> infoMap = null;
    private Boolean checked;
    private Boolean nocheck;
    private Boolean chkDisabled;
    private Boolean drag;
    private Boolean drop;
    private Boolean clickExpand;
    private String click;


    public String getId() {
        return this.id;
    }

    public TreeNode setId(String id) {
        this.id = id;
        return this;
    }

    public String getParentId() {
        return this.parentId;
    }

    public TreeNode setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getOldParentId() {
        return this.oldParentId;
    }

    public TreeNode setOldParentId(String oldParentId) {
        this.oldParentId = oldParentId;
        return this;
    }

    public Boolean getIsParent() {
        return this.isParent;
    }

    public TreeNode setIsParent(Boolean isParent) {
        this.isParent = isParent;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TreeNode setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public TreeNode setOpen(Boolean open) {
        this.open = open;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public TreeNode setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTarget() {
        return this.target;
    }

    public TreeNode setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getIconClose() {
        return this.iconClose;
    }

    public TreeNode setIconClose(String iconClose) {
        this.iconClose = iconClose;
        return this;
    }

    public String getIconOpen() {
        return this.iconOpen;
    }

    public TreeNode setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
        return this;
    }

    public String getIcon() {
        return this.icon;
    }

    public TreeNode setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getMenuType() {
        return this.menuType;
    }

    public TreeNode setMenuType(String menuType) {
        this.menuType = menuType;
        return this;
    }

    public Map<Object, Object> getInfoMap() {
        return this.infoMap;
    }

    public TreeNode setInfoMap(Map<Object, Object> infoMap) {
        this.infoMap = infoMap;
        return this;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public TreeNode setChecked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public Boolean getNocheck() {
        return this.nocheck;
    }

    public TreeNode setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
        return this;
    }

    public Boolean getChkDisabled() {
        return this.chkDisabled;
    }

    public TreeNode setChkDisabled(Boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
        return this;
    }

    public Boolean getDrag() {
        return this.drag;
    }

    public TreeNode setDrag(Boolean drag) {
        this.drag = drag;
        return this;
    }

    public Boolean getDrop() {
        return this.drop;
    }

    public TreeNode setDrop(Boolean drop) {
        this.drop = drop;
        return this;
    }

    public Boolean getClickExpand() {
        return this.clickExpand;
    }

    public TreeNode setClickExpand(Boolean clickExpand) {
        this.clickExpand = clickExpand;
        return this;
    }

    public String getClick() {
        return this.click;
    }

    public TreeNode setClick(String click) {
        this.click = click;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public TreeNode setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getManager() {
        return manager;
    }

    public TreeNode setManager(String manager) {
        this.manager = manager;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public TreeNode setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Boolean getExisted() {
        return existed;
    }

    public TreeNode setExisted(Boolean existed) {
        this.existed = existed;
        return this;
    }

}
