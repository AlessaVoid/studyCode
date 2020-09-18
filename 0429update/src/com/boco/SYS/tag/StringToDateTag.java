package com.boco.SYS.tag;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.boco.SYS.util.StringUtil;
/**
 * 
 * 
 *  将金额转换为大写
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年4月13日    	    谷立羊   新建
 * </pre>
 */
public class StringToDateTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	public Logger log;
	private String id;
	private String value = "";
	private String pattern = "";
	private String fromStyle="";
	private int flag = EVAL_PAGE;

	/*
	 * EVAL_BODY_BUFFERED 表示标签体的内容应该被处理，所有处理结果都将保存在BodyContent类中。 SKIP_BODY
	 * 表示忽略标签体内容，将操作权交给doEndTag()方法。 EVAL_BODY_AGAIN
	 * 表示重复执行标签体内容，会再次调用doAfterBody()方法，直到出现SKIP_BODY为止。 EVAL_BODY_INCLUDE
	 * 表示正常执行标签体操作，但不处理任何运算。 SKIP_PAGE 表示所有的JSP上的操作都将停止，会将所有的输出内容立刻显示在浏览器上。
	 * EVAL_PAGE 表示正常执行JSP页面。 EVAL_BODY_TAG
	 * 表示对标签之内主体进行赋值并把结果输出到流(保存在标签的主体内容属性中)。
	 */

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		StringBuffer tag = new StringBuffer();
		try {
			if (!StringUtil.isNull(value)) {
				SimpleDateFormat sdf1=new SimpleDateFormat(fromStyle);
				Date date=sdf1.parse(value);
				SimpleDateFormat sdf=new SimpleDateFormat(pattern);
				String resultStr=sdf.format(date);
				tag.append("<div align=\"left\"");
				if (this.id != null) {
					tag.append(" id=\"" + this.id + "\" ");
				}
				tag.append(">");
				tag.append(resultStr);
				tag.append("</div>");
			}
			out.print(tag);
			flag = super.doEndTag();
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error("创建标签失败[id],[fmtvalue]", e);
		}
		return flag;
	}
	public Logger getLog() {
		return log;
	}
	public void setLog(Logger log) {
		this.log = log;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getFromStyle() {
		return fromStyle;
	}

	public void setFromStyle(String fromStyle) {
		this.fromStyle = fromStyle;
	}

	@Override
	public int doStartTag() throws JspException {
		// this.log.info("doStartTag()");
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doAfterBody() throws JspException {
		// this.log.info("doAfterBody()");
		return SKIP_BODY;
	}
}