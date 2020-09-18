package com.boco.SYS.tag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.boco.SYS.cache.DicCache;


/**
 * 
 * 
 * 字典表下拉列表框自定义标签
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-15    	     杨滔    新建
 * </pre>
 */
public class DicSelectTag extends BodyTagSupport implements DynamicAttributes {

	private static final long serialVersionUID = 1L;
	private String disabled;
	private String id;
	private String name;
	private String tgClass;
	private String fn;
	private Map<String, String> dynAttributes = new HashMap<String, String>();
	public Logger log = Logger.getLogger(this.getClass());

	private Object DicNo;

	@Override
	public int doEndTag() throws JspException {
		try {
			Map<String, List<Map<String, Object>>> map = DicCache.getDicMap();
			List<Map<String, Object>> list = null;
			if (StringUtils.isNotBlank(this.getDicType())) {
				list = (List<Map<String, Object>>) map.get(this.getDicType());
			} else if (dynAttributes.containsKey("data")) {
				list = (List<Map<String, Object>>) map.get(dynAttributes.get("data"));
			}

			String tag = "<select ";
			if (this.disabled != null && this.disabled.equals("disabled")) {
				tag += " disabled='disabled' ";
			}
			if (this.name != null) {
				tag += " name='" + name + "' ";
			}
			if (this.id != null) {
				tag += " id='" + id + "' ";
			}

			if (this.tgClass != null) {
				tag += " class='dictag " + tgClass + "' ";
			}else{
				tag += " class='dictag' ";
			}
			
			if(this.fn != null){
				tag += fn;
			}
			// 动态属性
			Map.Entry<String, String> et = null;
			Iterator<Map.Entry<String, String>> it = dynAttributes.entrySet().iterator();
			while (it.hasNext() && null != (et = it.next())) {

				// 下拉数据属性
				// if(StringUtils.equals(et.getKey(), "data")){
				// Map<String,List<Map<String,String>>> lMap = new
				// HashMap<String, List<Map<String,String>>>();
				// List<Map<String,String>> tmpL = new
				// ArrayList<Map<String,String>>();
				// for(Object o:list){
				// Map<String,String> m = (HashMap<String, String>) o;
				// Map<String,String> m2 = new HashMap<String, String>();
				//						
				// m2.put("value", m.get("DICT_KEY_IN"));
				// m2.put("key", m.get("DICT_VALUE_IN"));
				// tmpL.add(m2);
				// }
				// lMap.put("list", tmpL);
				// tag += " "+et.getKey()+"='"+JsonUtils.toJson(lMap)+"'";
				// }
				// 其他普通属性
				tag += " " + et.getKey() + "='" + et.getValue() + "'";
			}

			tag += " >";
			tag += "<option value=\"\">--请选择--</option>";
			if(list!=null){
				for (int i = 0; i < list.size(); i++) {
					Map ontTag = (Map) list.get(i);
					tag += "<option value='" + ontTag.get("DICT_KEY_IN") + "'";
					if (ontTag.get("DICT_KEY_IN") != null && this.DicNo != null && ontTag.get("DICT_KEY_IN").toString().trim().equals(this.DicNo.toString().trim())) {
						tag += " selected='selected'";
					}
					tag += ">" + ontTag.get("DICT_VALUE_IN") + "</option>";
				}
			}
			tag += "</select>";
			pageContext.getOut().write(tag);
			// 标签的返回值

		} catch (Exception e) {
			log.error("字典标签:" + DicType + "加载失败", e);
		}finally{
			this.DicNo=null;
		}
		return EVAL_PAGE;
	}

	public void setDicNo(Object value) throws JspException {
		if (value != null && !value.equals("")) {
			this.DicNo = ExpressionEvaluatorManager.evaluate("dicNo", value.toString(), Object.class, this, pageContext);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		if (DicCache.getDicMap() == null) {
			return EVAL_PAGE;
		}
		return EVAL_BODY_INCLUDE;

	}

	public void setDynamicAttribute(String arg0, String arg1, Object arg2) throws JspException {
		dynAttributes.put(arg1, arg2.toString());
	}
	
	public void setTgClass(String tgClass) {
		this.tgClass = tgClass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReadOnly(String disabled) {
		this.disabled = disabled;
	}

	private String DicType;

	public String getDicType() {
		return DicType;
	}

	public void setDicType(String dicType) {
		DicType = dicType;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}
}
