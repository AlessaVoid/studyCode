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
 *  �����ת��Ϊ��д
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��4��13��    	    ������   �½�
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
	 * EVAL_BODY_BUFFERED ��ʾ��ǩ�������Ӧ�ñ��������д���������������BodyContent���С� SKIP_BODY
	 * ��ʾ���Ա�ǩ�����ݣ�������Ȩ����doEndTag()������ EVAL_BODY_AGAIN
	 * ��ʾ�ظ�ִ�б�ǩ�����ݣ����ٴε���doAfterBody()������ֱ������SKIP_BODYΪֹ�� EVAL_BODY_INCLUDE
	 * ��ʾ����ִ�б�ǩ����������������κ����㡣 SKIP_PAGE ��ʾ���е�JSP�ϵĲ�������ֹͣ���Ὣ���е��������������ʾ��������ϡ�
	 * EVAL_PAGE ��ʾ����ִ��JSPҳ�档 EVAL_BODY_TAG
	 * ��ʾ�Ա�ǩ֮��������и�ֵ���ѽ���������(�����ڱ�ǩ����������������)��
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
			this.log.error("������ǩʧ��[id],[fmtvalue]", e);
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