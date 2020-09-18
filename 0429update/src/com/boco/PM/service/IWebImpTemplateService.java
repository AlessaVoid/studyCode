package com.boco.PM.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.WebImpTemplate;
import com.boco.SYS.util.Json;

/**
 * 
 * 
 * WebImpTemplateҵ������ӿ�(���ӿ���ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface IWebImpTemplateService extends IGenericService<WebImpTemplate,java.lang.String>{
	/**
	 * 
	 *
	 * TODO ģ���ϴ�.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public Json upLoad(MultipartFile paramXsl,WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception;
	/**
	 * 
	 *
	 * TODO ģ������.
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public void download(WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	 * 
	 *
	 * TODO ɾ��ģ����Ϣ.
	 *
	 * @param webImpTemplate
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ����    �½�
	 * </pre>
	 */
	public Json deleteImpTemplate(WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception;
}