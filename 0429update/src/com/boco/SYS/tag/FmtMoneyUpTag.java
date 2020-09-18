package com.boco.SYS.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
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
public class FmtMoneyUpTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	public Logger log;
	private String id;
	private String fmtValue = "";
	private String fmtClass = "";
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
		String fraction[] = {"角", "分"};
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = {{"元", "万", "亿"},
                     {"", "拾", "佰", "仟"}};
        String head ="";
        if("".equals(currency)){
        	 head = n < 0? "负": "人民币";
        }else{
        	 head = n < 0? "负": "";
        }
        n = Math.abs(n);
         
        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if(s.length()<1){
            s = "整";    
        }
        int integerPart = (int)Math.floor(n);
 
        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
}