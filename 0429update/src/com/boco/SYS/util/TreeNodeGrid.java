package com.boco.SYS.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeNodeGrid {
	private String id = null;

	private String parentId = null;

	private String name = null;

	private String menuNo = null;
	
	private String version = null;
	
	private List<TreeNodeGrid> children = new ArrayList<TreeNodeGrid>();

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<TreeNodeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeGrid> children) {
		this.children = children;
	}
	
}
