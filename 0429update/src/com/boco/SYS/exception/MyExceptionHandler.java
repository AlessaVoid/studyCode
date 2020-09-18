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
 * 统一异常处理
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月4日    	    杨滔    新建
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
				if("w100".equals(code)){//超时或未登录
					return new ModelAndView("redirect:/toLogin.htm", model);
				}else{
					HashMap<String, Object> pk = new HashMap<String, Object>();
					pk.put("gfSysCode", "99370000000");
					pk.put("gfRetCode", code);
					pk.put("otherSysCode", "99370000000");
					pk.put("otherRetCode", code);
					//自定义异常，通过异常代码获取异常信息
					GfErrInfo errInfo = gfErrInfoMapper.selectByPK(pk);
					if(errInfo == null || StringUtils.isBlank(errInfo.getGfRetInfo())){
						if(ex.getCause() != null){
							model.put("ex", ex.getCause().getLocalizedMessage());
						}else{
							model.put("ex", "没有找到对应错误信息，错误码：["+pk+"]");
						}
					}else{
						model.put("ex", MessageFormat.format(errInfo.getGfRetInfo(), ((SystemException) ex).getParams()));
					}
					return new ModelAndView("/exception/error/error", model);
				}
			} else if(ex instanceof NullPointerException){
				model.put("ex", "该条数据不存在!");
			} else if (ex instanceof BeanInstantiationException) {
				model.put("ex", "类初始化异常!");
			} else if (ex instanceof BindingException) {
				String msg = ex.getLocalizedMessage();
				if(msg!=null && msg.contains("(not found):")){
					model.put("ex", msg.substring(msg.indexOf("):")+2) + "未在xml里定义!");
				}
			} else {
				String msg = ex.getLocalizedMessage();
				if(msg!=null && msg.contains("ORA-12899")){
					model.put("ex", msg.substring(msg.indexOf("列"),msg.indexOf(")")+1) + "!");
				} else if(msg!=null && msg.contains("ORA-01400")){
					model.put("ex", msg.substring(msg.indexOf("无法将"),msg.indexOf(")")+1) + "!");
				} else if(msg!=null && msg.contains("ORA-00001")){
					model.put("ex", "违反唯一约束条件!");
				} else if(msg!=null && msg.contains("ORA-00942")){
					model.put("ex", "表或视图不存在!");
				}else if(msg!=null && msg.contains("ORA-00904")){
					model.put("ex", msg.substring(msg.indexOf("ORA-00904")+10, msg.indexOf("无效")+2) + "!");
				}else if(msg!=null && msg.contains("ORA-00936")){
					model.put("ex", "缺失表达式!");
				}
			}
			return new ModelAndView("/exception/error/error", model);
		} else {
			try {
				PrintWriter writer = response.getWriter();
				if(ex instanceof SystemException){
					String code = ((SystemException) ex).getCode();
					if("w100".equals(code)){//超时或未登录
						response.setStatus(10000);
					}
					HashMap<String, Object> pk = new HashMap<String, Object>();
					pk.put("gfSysCode", "99370000000");
					pk.put("gfRetCode", code);
					pk.put("otherSysCode", "99370000000");
					pk.put("otherRetCode", code);
					//自定义异常，通过异常代码获取异常信息
					GfErrInfo errInfo = gfErrInfoMapper.selectByPK(pk);
					if(errInfo == null || StringUtils.isBlank(errInfo.getGfRetInfo())){
						if(ex.getCause() != null){
							writer.write(ex.getCause().getLocalizedMessage());
						}else{
							writer.write("没有找到对应错误信息，错误码：["+pk+"]");
						}
					}else{
						writer.write(MessageFormat.format(errInfo.getGfRetInfo(), ((SystemException) ex).getParams()));
					}
				} else if(ex instanceof NullPointerException){
					writer.write("该条数据不存在!");
				} else if (ex instanceof BeanInstantiationException) {
					logger.error("类初始化异常:", ex);//不会进入AOP，在这里输出日志信息
					writer.write("类初始化异常!");
				} else if (ex instanceof BindingException) {
					String msg = ex.getLocalizedMessage();
					if(msg!=null && msg.contains("(not found):")){
						writer.write(msg.substring(msg.indexOf("):")+2) + "未在xml里定义!");
					}else{
						writer.write(msg);
					}
				} else {
					String msg = ex.getLocalizedMessage();
					if(msg!=null && msg.contains("ORA-12899")){
						writer.write(msg.substring(msg.indexOf("列"),msg.indexOf(")")+1) + "!");
					} else if(msg!=null && msg.contains("ORA-01400")){
						writer.write(msg.substring(msg.indexOf("无法将"),msg.indexOf(")")+1) + "!");
					} else if(msg!=null && msg.contains("ORA-00001")){
						writer.write("违反唯一约束条件!");
					} else if(msg!=null && msg.contains("ORA-00942")){
						writer.write("表或视图不存在!");
					}else if(msg!=null && msg.contains("ORA-00904")){
						writer.write(msg.substring(msg.indexOf("ORA-00904")+10, msg.indexOf("无效")+2) + "!");
					}else if(msg!=null && msg.contains("ORA-00936")){
						writer.write("缺失表达式!");
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
