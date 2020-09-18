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
	 * JAVA生成word文档工具类
	 * @author 永丰基地
	 *
	 */
public class DocUtil {
	
	private Configuration configuration = null ;
	public DocUtil(){
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	/**
	 * 根据模板生成word文件
	 * @param dataMap 需要填入模板的数据
	 * @param marker 模板所在路径
	 * @param downloadType 文件名称
	 * @param savePath 保存路径
	 * @throws Exception 
	 */
	public void createDoc(Map<String,Object> dataMap,String marker,String downloadType,String savePath ){
		try{
		//加载需要装填的模板
		Template template = null ;
		//加载模板文件
		configuration.setDirectoryForTemplateLoading(new File(marker));
		//设置对象包装器
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		//设置异常处理器
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		//定义Template对象，注意模板类型名字与downloadType要一致
		template = configuration.getTemplate(downloadType);
		//输出文档
		File outFile = new File(savePath);
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
		template.process(dataMap, out);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) throws Exception {
		Map dataMap = new HashMap();
		dataMap.put("first", "先到先得");
		DocUtil docUtil = new DocUtil();
		docUtil.createDoc(dataMap,"d:/测试", "关于发售邮银财智·盛盈2016年第76期人民币理财产品的通知.ftl", "d:/666.doc");
	}

}