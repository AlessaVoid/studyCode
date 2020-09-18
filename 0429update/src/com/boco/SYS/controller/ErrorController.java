package com.boco.SYS.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boco.SYS.annotation.SystemLog;
import com.boco.SYS.base.BaseController;
import com.boco.SYS.global.Dic.AccessType;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.util.EncodingUtils;


/**
 * 
 * 
 *  Filter错误处理类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年12月22日    	    杨滔    新建
 * </pre>
 */
@Component
@RequestMapping(value = "/error/")
public class ErrorController extends BaseController {

	/**
	 * 
	 *
	 * TODO 错误处理方法.
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月22日    	    杨滔    新建
	 * </pre>
	 */
	@RequestMapping(value = "error")
	@SystemLog(tradeName="系统功能",funCode="system", funName="系统异常跳转",accessType=AccessType.READ,level=Debug.DEBUG)
	public String error() throws Exception {
		String msg = getParameter("msg");
		msg = EncodingUtils.GetBMZiFu(msg);
		setAttribute("ex", msg);
		return "/exception/error/error";
	}
}
