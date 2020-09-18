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
 * WebImpTemplateҵ������ʵ����(������ʵ������ɾ���ġ��������ֻ������ҵ��ʱ��Ҫ�Զ��巽��)
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
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
	 * TODO ģ���ϴ�.
	 * �������ϴ��ļ��ĸ�ʽ
	 *
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	@Override
	public Json upLoad(MultipartFile paramXsl, WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(paramXsl == null){
			return json.returnMsg("false", getErrorInfo("w304"));
		}
		//��ȡģ�����ƣ������ļ��ĺ�׺
		String uploadUrl = "";
		String fileName = paramXsl.getOriginalFilename();
		//��ȡ��Ŀ�����ļ���·��

		String getTransDate = fdBusinessDateService.getCommonDateDetails();
		uploadUrl = filePath;
		//�ж��ļ�·���Ƿ����
		File urlFile = new File(uploadUrl);
		if(!urlFile.exists()){
			urlFile.mkdirs();
		}
		// ƴ����Ŀ�����ļ�������·��
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
		//�޸��ˣ��غ��ޡ�ԭ���ϴ��ļ�ͬ��
		ServerToServerSftp.fileSyn(filePath);
		return json.returnMsg("true", "�ϴ��ɹ�");
	}
	
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
	@Override
	public void download(WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response) throws SystemException,
			Exception {
		WebImpTemplate webImpTemplateDTO = webImpTemplateMapper.selectByPK(webImpTemplate.getTemplateCode());
		//��ȡ����·��
		String filePath = webImpTemplateDTO.getTemplateUrl();
		File file = new File(filePath);  
		if(!file.exists()){
			throw new SystemException("w643");//�ļ�������
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
	 * TODO ɾ��ģ����Ϣ.
	 *
	 * @param webImpTemplate
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ����    �½�
	 * </pre>
	 */
	@Override
	public Json deleteImpTemplate(WebImpTemplate webImpTemplate,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebImpTemplate webImpTemplateDTO = webImpTemplateMapper.selectByPK(webImpTemplate.getTemplateCode());
		File file = new File(webImpTemplateDTO.getTemplateUrl());
		//�ж�Ҫɾ�����ļ��Ƿ���ڣ����ڣ�ɾ��
		if(file.exists()){
			file.delete();
		}
		ServerToServerSftp.fileSynDel(webImpTemplateDTO.getTemplateUrl());
		deleteByPK(webImpTemplateDTO.getTemplateCode());
		return json.returnMsg("true", "ɾ���ɹ�");
	}
	/**
	 * 
	 *
	 * TODO �ϴ�У�����.
	 *
	 * @param webImpTemplate
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��3��10��    	  ����    �½�
	 * </pre>
	 * @throws Exception 
	 */
	public boolean checkUploadData(MultipartFile paramXsl,WebImpTemplate webImpTemplate,String filePath,String fileName) throws Exception{
		//У���ļ����Ƶĳ���
		if(StringUtils.isNotEmpty(fileName)){
			if(BocoUtils.getStrLength(fileName, 2) > 200){
				json.returnMsg("false", getErrorInfo("w308"));
				return false;
			}
		}
		//У���ļ��洢·��
		if(StringUtils.isNotEmpty(filePath)){
			if(filePath.length() > 200){
				json.returnMsg("false", getErrorInfo("w309"));
				return false;
			}
			//�ļ��洢·����������
			//У���ļ��Ĵ�С
			BigDecimal size = new BigDecimal(paramXsl.getSize());
			if(size.compareTo(new BigDecimal("10485760")) == 1){
				json.returnMsg("false", getErrorInfo("w310"));
				return false;
			}
		}else{
			json.returnMsg("false", getErrorInfo("w307"));
			return false;
		}
		//У������ı�ע��Ϣ
		if(StringUtils.isNotEmpty(webImpTemplate.getRemark())){
			if(BocoUtils.getStrLength(webImpTemplate.getRemark(), 2) > 200){
				json.returnMsg("false", getErrorInfo("w311"));
				return false;
			}
		}
		return true;
	}
}