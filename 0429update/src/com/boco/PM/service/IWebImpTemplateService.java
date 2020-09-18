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
 * WebImpTemplate业务服务层接口(父接口已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
public interface IWebImpTemplateService extends IGenericService<WebImpTemplate,java.lang.String>{
	/**
	 * 
	 *
	 * TODO 模板上传.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月10日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public Json upLoad(MultipartFile paramXsl,WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception;
	/**
	 * 
	 *
	 * TODO 模板下载.
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月10日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public void download(WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	 * 
	 *
	 * TODO 删除模板信息.
	 *
	 * @param webImpTemplate
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月10日    	  杜旭    新建
	 * </pre>
	 */
	public Json deleteImpTemplate(WebImpTemplate webImpTemplate,HttpServletRequest request,HttpServletResponse response) throws Exception;
}