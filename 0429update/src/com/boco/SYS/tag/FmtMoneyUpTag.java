package com.boco.SYS.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
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
public class FmtMoneyUpTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	public Logger log;
	private String id;
	private String fmtValue = "";
	private String fmtClass = "";
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
			if (null!=fmtValue&&!"".equals(fmtValue)) {
				String currency = "";
				if(fmtValue.indexOf(",")!=-1){
					currency = fmtValue.substring(fmtValue.length()-3, fmtValue.length());
					fmtValue = fmtValue.substring(0,fmtValue.length()-4);
				}
				Double value= Double.valueOf(fmtValue);
				String resultStr=this.getUpMoney(value,currency);
				tag.append("<div align=\"left\" style=\""+fmtClass+"\" ");
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
	public String getFmtValue() {
		return fmtValue;
	}
	public void setFmtValue(String fmtValue) {
		this.fmtValue = fmtValue;
	}
	public String getFmtClass() {
		return fmtClass;
	}
	public void setFmtClass(String fmtClass) {
		this.fmtClass = fmtClass;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
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
	public String getUpMoney(double n,String currency){
		String fraction[] = {"��", "��"};
        String digit[] = { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };
        String unit[][] = {{"Ԫ", "��", "��"},
                     {"", "ʰ", "��", "Ǫ"}};
        String head ="";
        if("".equals(currency)){
        	 head = n < 0? "��": "�����";
        }else{
        	 head = n < 0? "��": "";
        }
        n = Math.abs(n);
         
        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(��.)+", "");
        }
        if(s.length()<1){
            s = "��";    
        }
        int integerPart = (int)Math.floor(n);
 
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(��.)*��$", "").replaceAll("^$", "��") + unit[0][i] + s;
        }
        return head + s.replaceAll("(��.)*��Ԫ", "Ԫ").replaceFirst("(��.)+", "").replaceAll("(��.)+", "��").replaceAll("^��$", "��Ԫ��");
    }
}