package com.boco.SYS.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

/**
 * <p>��Դ�ļ�����������</p>
 * @author
 * @version 1.0
 * <p>���ڣ�2012-07-03</p>
 */
public class ResourceUtils {
	/**
	 *  <p>�������·���ж�ȡ��Դ�ļ�</p>
	 * @param filePath �ļ�·��
	 * @return �ļ���
	 * @throws Exception 
	 */
	public static InputStream getClassPathResourceAsStream(String filePath) throws Exception{
		if(null==filePath){
			throw new Exception("�ļ�·��Ϊ��");
		}
		InputStream in;
		ClassPathResource cpr =  new ClassPathResource(filePath);
		in = cpr.getInputStream();
		return in;
	}
	
	/**
	 * <p>���ļ�ϵͳ�ж�ȡ��Դ�ļ�</p>
	 * @param filePath �ļ�·��
	 * @return �ļ���
	 * @throws Exception
	 */
	public static InputStream getFileSystemResourceAsStream(String filePath) throws Exception{
		if(null==filePath){
			throw new Exception("�ļ�·��Ϊ��");
		}
		InputStream in = null;
		List<Object> repList =  new ArrayList<Object>();
		repList.add(filePath);
		try {
			File file = new File(filePath);
			if(file.exists() && file.isFile() && file.canRead())
				in = new FileInputStream(filePath);
		} catch (Exception e) {
			throw e;
		}finally{
			repList=null;
		}
		return  in;
		
	}
	/**
	 * <p>�������·���ж�ȡ��Դ�ļ�</p>
	 * @param filePath �ļ�·��
	 * @return �ļ�����·��
	 * @throws Exception
	 */
	public static URL getClassPathResourceAsURL(String filePath) throws Exception{
		URL url = null;
		List<Object> repList =  new ArrayList<Object>();
		repList.add(filePath);
		ClassPathResource cpr =  new ClassPathResource(filePath);
		url = cpr.getURL();
		return url;
	}

}
