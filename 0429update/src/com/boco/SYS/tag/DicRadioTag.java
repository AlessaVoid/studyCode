package com.boco.SYS.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.boco.SYS.cache.DicCache;


/**
 * 
 * 
 * 字典表单选 jsp自定义标签
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-15    	     杨滔    新建
 * </pre>
 */
public class DicRadioTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String tgClass;
	private String fn;
	public Logger log = Logger.getLogger(this.getClass());

	private Object DicNo;

	@Override
	public int doEndTag() throws JspException {
		try {
			Map<String, List<Map<String,Object>>> map = DicCache.getDicMap();
			List<Map<String,Object>> list = (List<Map<String,Object>>) map.get(this.getDicType());
			String tag = "";
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> ontTag = (Map<String,Object>) list.get(i);

				tag += " <input type=\"radio\" ";
				if (this.id != null) {
					tag += " id=\"" + id + i + "\" ";
				}
				if (this.name != null) {
					tag += " name=\"" + name + "\" ";
				}
				if (this.tgClass != null) {
					tag += " class='dictag hand " + tgClass + "' ";
				}else {
					tag += " class='dictag hand' ";
				}
				
				if(this.fn != null){
					tag += fn;
				}

				tag += " value=\"" + ontTag.get("DICT_KEY_IN") + "\" ";
				if (ontTag.get("DICT_KEY_IN") != null && this.DicNo != null && ontTag.get("DICT_KEY_IN").toString().equals(this.DicNo.toString())) {
					tag += " checked=\"checked\" ";
				}

				tag += "/>";

				tag += "<label for=\"radio-" + i + "\" class=\"hand\">" + ontTag.get("DICT_VALUE_IN") + "</label>&nbsp";
			}
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
	
	public String getTgClass() {
		return tgClass;
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
