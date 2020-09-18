package com.boco.SYS.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

/**
 * <p>资源文件操作工具类</p>
 * @author
 * @version 1.0
 * <p>日期：2012-07-03</p>
 */
public class ResourceUtils {
	/**
	 *  <p>从类加载路径中读取资源文件</p>
	 * @param filePath 文件路径
	 * @return 文件流
	 * @throws Exception 
	 */
	public static InputStream getClassPathResourceAsStream(String filePath) throws Exception{
		if(null==filePath){
			throw new Exception("文件路径为空");
		}
		InputStream in;
		ClassPathResource cpr =  new ClassPathResource(filePath);
		in = cpr.getInputStream();
		return in;
	}
	
	/**
	 * <p>从文件系统中读取资源文件</p>
	 * @param filePath 文件路径
	 * @return 文件流
	 * @throws Exception
	 */
	public static InputStream getFileSystemResourceAsStream(String filePath) throws Exception{
		if(null==filePath){
			throw new Exception("文件路径为空");
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
	 * <p>从类加载路径中读取资源文件</p>
	 * @param filePath 文件路径
	 * @return 文件绝对路径
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
