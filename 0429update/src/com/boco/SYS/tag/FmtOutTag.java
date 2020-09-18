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
	 * EVAL_BODY_BUFFERED ��ʾ��ǩ�������Ӧ�ñ��������д���������������BodyContent���С� SKIP_BODY
	 * ��ʾ���Ա�ǩ�����ݣ�������Ȩ����doEndTag()������ EVAL_BODY_AGAIN
	 * ��ʾ�ظ�ִ�б�ǩ�����ݣ����ٴε���doAfterBody()������ֱ������SKIP_BODYΪֹ�� EVAL_BODY_INCLUDE
	 * ��ʾ����ִ�б�ǩ����������������κ����㡣 SKIP_PAGE ��ʾ���е�JSP�ϵĲ�������ֹͣ���Ὣ���е��������������ʾ��������ϡ�
	 * EVAL_PAGE ��ʾ����ִ��JSPҳ�档 EVAL_BODY_TAG
	 * ��ʾ�Ա�ǩ֮��������и�ֵ���ѽ���������(�����ڱ�ǩ����������������)��
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
			this.log.error("������ǩʧ��[id],[fmtvalue]", e);
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