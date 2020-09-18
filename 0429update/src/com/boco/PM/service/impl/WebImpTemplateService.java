package com.boco.PM.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boco.PM.service.IWebImpTemplateService;

import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.WebImpTemplate;
import com.boco.SYS.exception.SystemException;
import com.boco.SYS.mapper.WebImpTemplateMapper;
import com.boco.SYS.service.IFdBusinessDateService;
import com.boco.SYS.util.BocoUtils;
import com.boco.SYS.util.Json;
import com.boco.SYS.util.ServerToServerSftp;

/**
 * 
 * 
 * WebImpTemplate业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class WebImpTemplateService extends GenericService<WebImpTemplate,java.lang.String> implements IWebImpTemplateService{
	@Autowired
	private WebImpTemplateMapper webImpTemplateMapper;

	@Autowired
	private IFdBusinessDateService fdBusinessDateService;

	private final static String filePath="";
	
	/**
	 * 
	 *
	 * TODO 模板上传.
	 * 不限制上传文件的格式
	 *
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月10日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json upLoad(MultipartFile paramXsl, WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(paramXsl == null){
			return json.returnMsg("false", getErrorInfo("w304"));
		}
		//获取模板名称，包含文件的后缀
		String uploadUrl = "";
		String fileName = paramXsl.getOriginalFilename();
		//获取项目存在文件的路径

		String getTransDate = fdBusinessDateService.getCommonDateDetails();
		uploadUrl = filePath;
		//判断文件路径是否存在
		File urlFile = new File(uploadUrl);
		if(!urlFile.exists()){
			urlFile.mkdirs();
		}
		// 拼接项目存在文件的完整路径
		String filePath = uploadUrl + "/" + fileName + getTransDate + BocoUtils.getNowTime();
		
		boolean check = checkUploadData(paramXsl,webImpTemplate,filePath,fileName);
		if(check == false){
			return json;
		}
		
		if (!paramXsl.isEmpty()) {
			FileOutputStream os = new FileOutputStream(filePath);
			InputStream in = paramXsl.getInputStream();
			int b = 0;
			while ((b = in.read()) != -1) {
				os.write(b);
			}
			os.flush();
			os.close();
			in.close();
		}
		webImpTemplate.setTemplateName(fileName);
		webImpTemplate.setTemplateCode(BocoUtils.getUUID());
		webImpTemplate.setTemplateUrl(filePath);
		int count = insertEntity(webImpTemplate);
		if(count != 1){
			throw new SystemException(getErrorInfo("w306"));
		}
		//修改人：秦海洲。原因：上传文件同步
		ServerToServerSftp.fileSyn(filePath);
		return json.returnMsg("true", "上传成功");
	}
	
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
	@Override
	public void download(WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response) throws SystemException,
			Exception {
		WebImpTemplate webImpTemplateDTO = webImpTemplateMapper.selectByPK(webImpTemplate.getTemplateCode());
		//获取下载路径
		String filePath = webImpTemplateDTO.getTemplateUrl();
		File file = new File(filePath);  
		if(!file.exists()){
			throw new SystemException("w643");//文件不存在
		}
		response.setCharacterEncoding("UTF-8");
		String fname=URLEncoder.encode(webImpTemplateDTO.getTemplateName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\"");
		response.setHeader("content-type", "application/octet-stream");
		ServletOutputStream out;   
		File filel = new File(filePath);  
		FileInputStream inputStream = new FileInputStream(filel);   
		 
		 out = response.getOutputStream();   
		 int b = 0;   
		 byte[] buffer = new byte[1024];   
		 while ((b = inputStream.read(buffer)) != -1){   
			 out.write(buffer,0,b);   
		 }   
		inputStream.close();   
		out.flush();   
		out.close();   
	}

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
	@Override
	public Json deleteImpTemplate(WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebImpTemplate webImpTemplateDTO = webImpTemplateMapper.selectByPK(webImpTemplate.getTemplateCode());
		File file = new File(webImpTemplateDTO.getTemplateUrl());
		//判断要删除的文件是否存在，存在，删除
		if(file.exists()){
			file.delete();
		}
		ServerToServerSftp.fileSynDel(webImpTemplateDTO.getTemplateUrl());
		deleteByPK(webImpTemplateDTO.getTemplateCode());
		return json.returnMsg("true", "删除成功");
	}
	/**
	 * 
	 *
	 * TODO 上传校验规则.
	 *
	 * @param webImpTemplate
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年3月10日    	  杜旭    新建
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkUploadData(MultipartFile paramXsl,WebImpTemplate webImpTemplate,String filePath,String fileName) throws Exception{
		//校验文件名称的长度
		if(StringUtils.isNotEmpty(fileName)){
			if(BocoUtils.getStrLength(fileName, 2) > 200){
				json.returnMsg("false", getErrorInfo("w308"));
				return false;
			}
		}
		//校验文件存储路径
		if(StringUtils.isNotEmpty(filePath)){
			if(filePath.length() > 200){
				json.returnMsg("false", getErrorInfo("w309"));
				return false;
			}
			//文件存储路径符合条件
			//校验文件的大小
			BigDecimal size = new BigDecimal(paramXsl.getSize());
			if(size.compareTo(new BigDecimal("10485760")) == 1){
				json.returnMsg("false", getErrorInfo("w310"));
				return false;
			}
		}else{
			json.returnMsg("false", getErrorInfo("w307"));
			return false;
		}
		//校验输入的备注信息
		if(StringUtils.isNotEmpty(webImpTemplate.getRemark())){
			if(BocoUtils.getStrLength(webImpTemplate.getRemark(), 2) > 200){
				json.returnMsg("false", getErrorInfo("w311"));
				return false;
			}
		}
		return true;
	}
}