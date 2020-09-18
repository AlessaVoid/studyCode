package com.boco.SYS.tag;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

public class FmtOutTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	public Logger log;
	private String id;
	private String fmtclass;
	private Object fmtvalue = "";
	private String prefix = "";
	private String character = "";
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
		// this.log.info("doEndTag()");
		JspWriter out = this.pageContext.getOut();
		StringBuffer tag = new StringBuffer();
		try {
			if (fmtclass != null && fmtclass.indexOf("money") == 0) {
				int index = fmtclass.lastIndexOf("-") + 1;
				int bit = index == 0 ? 2 : Integer.parseInt(fmtclass.substring(index));
				String fmt = "#,##0";
				if (bit > 0) {
					fmt += ".";
					for (int i = 0; i < bit; i++) {
						fmt += "0";
					}
				}

				DecimalFormat df = new DecimalFormat(fmt + ";-" + fmt);
				if (fmtvalue == null || fmtvalue.equals("")) {
					fmtvalue = "0";
				}
				BigDecimal bd = new BigDecimal(fmtvalue.toString());
				String value = df.format(bd.doubleValue());

				if (!prefix.equals("")) {
					value = prefix + value;
				}

				if (!character.equals("")) {
					value = value + character;
				}

				tag.append("<div align=\"left\" style=\"width:135px\" ");
				if (this.id != null) {
					tag.append(" id=\"" + this.id + "\" ");
				}
				tag.append(">");

				tag.append(value);
				tag.append("</div>");
			} else if (fmtclass != null && fmtclass.equals("int")) {
				DecimalFormat df = new DecimalFormat("#,##0;-#,##0");
				if (fmtvalue == null || fmtvalue.equals("")) {
					fmtvalue = "0";
				}
				BigDecimal bd = new BigDecimal(fmtvalue.toString());
				String value = df.format(bd.doubleValue());

				if (!prefix.equals("")) {
					value = prefix + value;
				}

				if (!character.equals("")) {
					value = value + character;
				}

				tag.append("<div align=\"left\" style=\"width:135px\">");
				tag.append(value);
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

	public FmtOutTag() {
		super();
		this.log = Logger.getLogger(getClass());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFmtclass() {
		return fmtclass;
	}

	public void setFmtclass(String fmtclass) {
		this.fmtclass = fmtclass;
	}

	public Object getFmtvalue() {
		return fmtvalue;
	}

	public void setFmtvalue(Object fmtvalue) throws JspException {
		this.fmtvalue = fmtvalue;
	}

	public String getCharacter() {
		return character;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setCharacter(String character) {
		this.character = character;
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