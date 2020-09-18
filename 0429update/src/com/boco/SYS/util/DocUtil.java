package com.boco.SYS.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.boco.SYS.entity.WebReviewMain;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
	
	/**
	 * JAVA����word�ĵ�������
	 * @author �������
	 *
	 */
public class DocUtil {
	
	private Configuration configuration = null ;
	public DocUtil(){
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	/**
	 * ����ģ������word�ļ�
	 * @param dataMap ��Ҫ����ģ�������
	 * @param marker ģ������·��
	 * @param downloadType �ļ�����
	 * @param savePath ����·��
	 * @throws Exception 
	 */
	public void createDoc(Map<String,Object> dataMap,String marker,String downloadType,String savePath ){
		try{
		//������Ҫװ���ģ��
		Template template = null ;
		//����ģ���ļ�
		configuration.setDirectoryForTemplateLoading(new File(marker));
		//���ö����װ��
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		//�����쳣������
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		//����Template����ע��ģ������������downloadTypeҪһ��
		template = configuration.getTemplate(downloadType);
		//����ĵ�
		File outFile = new File(savePath);
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
		template.process(dataMap, out);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) throws Exception {
		Map dataMap = new HashMap();
		dataMap.put("first", "�ȵ��ȵ�");
		DocUtil docUtil = new DocUtil();
		docUtil.createDoc(dataMap,"d:/����", "���ڷ����������ǡ�ʢӯ2016���76���������Ʋ�Ʒ��֪ͨ.ftl", "d:/666.doc");
	}

}