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
 * 字典表翻译输出 Jsp自定义标签
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-15    	     杨滔    新建
 * </pre>
 */
public class DicOutTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	public Logger log;
	private String DicType;
	private Object DicNo;

	private String tgClass;

	public String getTgClass() {
		return tgClass;
	}

	public void setTgClass(String tgClass) {
		this.tgClass = tgClass;
	}

	public DicOutTag() {
		this.log = Logger.getLogger(getClass());
	}

	public String getDicType() {
		return this.DicType;
	}

	public void setDicType(String dicType) {
		this.DicType = dicType;
	}

	public int doEndTag() throws JspException {
		try {
			Map map = DicCache.getDicMap();
			List list = (List) map.get(getDicType());
			String tag = "";
			if(list != null){
				for (int i = 0; i < list.size(); ++i) {
					Map ontTag = (Map) list.get(i);
					if ((ontTag.get("DICT_KEY_IN") != null) && (ontTag.get("DICT_KEY_IN").toString().trim().equals(this.DicNo.toString().trim())))
						tag = (String) ontTag.get("DICT_VALUE_IN");
				}
			}

			if (this.tgClass != null && this.tgClass.equals("text")) {
				tag = "<input type='text' readonly='readonly' value='" + tag + "'/>";
			}

			this.pageContext.getOut().write(tag);
		} catch (Exception e) {
			this.log.error("字典标签:" + this.DicType + "加载失败", e);
		} finally {
			this.DicNo = null;
		}
		return EVAL_PAGE;
	}

	public void setDicNo(Object value) throws JspException {
		if ((value != null) && (!(value.equals(""))))
			this.DicNo = ExpressionEvaluatorManager.evaluate("dicNo", value.toString(), Object.class, this, this.pageContext);
		this.DicNo = this.DicNo == null ? "" : this.DicNo;
	}

	public int doStartTag() throws JspException {
		if (DicCache.getDicMap() == null)
			return EVAL_PAGE;
		return EVAL_BODY_INCLUDE;
	}
}