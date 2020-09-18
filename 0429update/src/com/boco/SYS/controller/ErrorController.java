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
 *  Filter��������.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��12��22��    	    ����    �½�
 * </pre>
 */
@Component
@RequestMapping(value = "/error/")
public class ErrorController extends BaseController {

	/**
	 * 
	 *
	 * TODO ��������.
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��22��    	    ����    �½�
	 * </pre>
	 */
	@RequestMapping(value = "error")
	@SystemLog(tradeName="ϵͳ����",funCode="system", funName="ϵͳ�쳣��ת",accessType=AccessType.READ,level=Debug.DEBUG)
	public String error() throws Exception {
		String msg = getParameter("msg");
		msg = EncodingUtils.GetBMZiFu(msg);
		setAttribute("ex", msg);
		return "/exception/error/error";
	}
}
