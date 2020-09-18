package com.boco.SYS.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.binding.BindingException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.boco.SYS.entity.GfErrInfo;
import com.boco.SYS.mapper.GfErrInfoMapper;

/**
 * 
 * 
 * ͳһ�쳣����
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��11��4��    	    ����    �½�
 * </pre>
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
	private static Logger logger = Logger.getLogger(MyExceptionHandler.class);
	@Autowired
	private GfErrInfoMapper gfErrInfoMapper;
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			if (ex instanceof SystemException) {
				String code = ((SystemException) ex).getCode();
				if("w100".equals(code)){//��ʱ��δ��¼
					return new ModelAndView("redirect:/toLogin.htm", model);
				}else{
					HashMap<String, Object> pk = new HashMap<String, Object>();
					pk.put("gfSysCode", "99370000000");
					pk.put("gfRetCode", code);
					pk.put("otherSysCode", "99370000000");
					pk.put("otherRetCode", code);
					//�Զ����쳣��ͨ���쳣�����ȡ�쳣��Ϣ
					GfErrInfo errInfo = gfErrInfoMapper.selectByPK(pk);
					if(errInfo == null || StringUtils.isBlank(errInfo.getGfRetInfo())){
						if(ex.getCause() != null){
							model.put("ex", ex.getCause().getLocalizedMessage());
						}else{
							model.put("ex", "û���ҵ���Ӧ������Ϣ�������룺["+pk+"]");
						}
					}else{
						model.put("ex", MessageFormat.format(errInfo.getGfRetInfo(), ((SystemException) ex).getParams()));
					}
					return new ModelAndView("/exception/error/error", model);
				}
			} else if(ex instanceof NullPointerException){
				model.put("ex", "�������ݲ�����!");
			} else if (ex instanceof BeanInstantiationException) {
				model.put("ex", "���ʼ���쳣!");
			} else if (ex instanceof BindingException) {
				String msg = ex.getLocalizedMessage();
				if(msg!=null && msg.contains("(not found):")){
					model.put("ex", msg.substring(msg.indexOf("):")+2) + "δ��xml�ﶨ��!");
				}
			} else {
				String msg = ex.getLocalizedMessage();
				if(msg!=null && msg.contains("ORA-12899")){
					model.put("ex", msg.substring(msg.indexOf("��"),msg.indexOf(")")+1) + "!");
				} else if(msg!=null && msg.contains("ORA-01400")){
					model.put("ex", msg.substring(msg.indexOf("�޷���"),msg.indexOf(")")+1) + "!");
				} else if(msg!=null && msg.contains("ORA-00001")){
					model.put("ex", "Υ��ΨһԼ������!");
				} else if(msg!=null && msg.contains("ORA-00942")){
					model.put("ex", "�����ͼ������!");
				}else if(msg!=null && msg.contains("ORA-00904")){
					model.put("ex", msg.substring(msg.indexOf("ORA-00904")+10, msg.indexOf("��Ч")+2) + "!");
				}else if(msg!=null && msg.contains("ORA-00936")){
					model.put("ex", "ȱʧ���ʽ!");
				}
			}
			return new ModelAndView("/exception/error/error", model);
		} else {
			try {
				PrintWriter writer = response.getWriter();
				if(ex instanceof SystemException){
					String code = ((SystemException) ex).getCode();
					if("w100".equals(code)){//��ʱ��δ��¼
						response.setStatus(10000);
					}
					HashMap<String, Object> pk = new HashMap<String, Object>();
					pk.put("gfSysCode", "99370000000");
					pk.put("gfRetCode", code);
					pk.put("otherSysCode", "99370000000");
					pk.put("otherRetCode", code);
					//�Զ����쳣��ͨ���쳣�����ȡ�쳣��Ϣ
					GfErrInfo errInfo = gfErrInfoMapper.selectByPK(pk);
					if(errInfo == null || StringUtils.isBlank(errInfo.getGfRetInfo())){
						if(ex.getCause() != null){
							writer.write(ex.getCause().getLocalizedMessage());
						}else{
							writer.write("û���ҵ���Ӧ������Ϣ�������룺["+pk+"]");
						}
					}else{
						writer.write(MessageFormat.format(errInfo.getGfRetInfo(), ((SystemException) ex).getParams()));
					}
				} else if(ex instanceof NullPointerException){
					writer.write("�������ݲ�����!");
				} else if (ex instanceof BeanInstantiationException) {
					logger.error("���ʼ���쳣:", ex);//�������AOP�������������־��Ϣ
					writer.write("���ʼ���쳣!");
				} else if (ex instanceof BindingException) {
					String msg = ex.getLocalizedMessage();
					if(msg!=null && msg.contains("(not found):")){
						writer.write(msg.substring(msg.indexOf("):")+2) + "δ��xml�ﶨ��!");
					}else{
						writer.write(msg);
					}
				} else {
					String msg = ex.getLocalizedMessage();
					if(msg!=null && msg.contains("ORA-12899")){
						writer.write(msg.substring(msg.indexOf("��"),msg.indexOf(")")+1) + "!");
					} else if(msg!=null && msg.contains("ORA-01400")){
						writer.write(msg.substring(msg.indexOf("�޷���"),msg.indexOf(")")+1) + "!");
					} else if(msg!=null && msg.contains("ORA-00001")){
						writer.write("Υ��ΨһԼ������!");
					} else if(msg!=null && msg.contains("ORA-00942")){
						writer.write("�����ͼ������!");
					}else if(msg!=null && msg.contains("ORA-00904")){
						writer.write(msg.substring(msg.indexOf("ORA-00904")+10, msg.indexOf("��Ч")+2) + "!");
					}else if(msg!=null && msg.contains("ORA-00936")){
						writer.write("ȱʧ���ʽ!");
					} else {
						writer.write(msg);
					}
				}
			} catch (IOException e) {
				model.put("ex", e);
				return new ModelAndView("/exception/error/error", model);
			}
			return new ModelAndView();
		}
	}
}
