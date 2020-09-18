package com.boco.SYS.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.boco.SYS.exception.SystemException;


public class ZipUtil {
	static Log log = LogFactory.getLog(ZipUtil.class);
	/**
	 * 
	 *
	 * TODO zip压缩
	 *
	 * @param zipFilePath zip文件的全路径
	 * @param unzipFilePath 要压缩的文件的路径
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月20日    	   谷立羊  新建
	 * </pre>
	 */
	public static void zip(String zipFilePath, String unzipFilePath) throws Exception   {  
		byte[] buffer=new byte[1024];
		ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipFilePath));
		out.setComment("产品说明书");
		File file=new File(unzipFilePath);
		if (file.isDirectory()) {
			File[] files=file.listFiles();
			for (File f : files) {
				FileInputStream fis=new FileInputStream(f);
				out.putNextEntry(new ZipEntry(f.getName()));
				int len;
				while ((len=fis.read(buffer))>0) {
					out.write(buffer,0,len);
				}
				out.closeEntry();
				fis.close();
				
			}
		}
		out.close();
	}
	
	/**
	 * 
	 *
	 * TODO zip解压文件
	 *
	 * @param zipFilePath zip文件的全路径
	 * @param unzipFilePath 解压后的文件保存的路径
	 * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月20日    	   谷立羊  新建
	 * </pre>
	 */
	public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception   {  
        if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath))      {  
           //{0}不能为空！
        	throw new SystemException("w554", "zip目录/解压文件路径");       
        }  
        File zipFile = new File(zipFilePath);  
        //如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径  
        if (includeZipFileName)  
        {  
            String fileName = zipFile.getName();  
            if (StringUtils.isNotEmpty(fileName))  
            {  
                fileName = fileName.substring(0, fileName.lastIndexOf("."));  
            }  
            unzipFilePath = unzipFilePath + File.separator + fileName;  
        }  
        //创建解压缩文件保存的路径  
        File unzipFileDir = new File(unzipFilePath);  
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory())  
        {  
            unzipFileDir.mkdirs();  
        }  
          
        //开始解压  
        ZipEntry entry = null;  
        String entryFilePath = null, entryDirPath = null;  
        File entryFile = null, entryDir = null;  
        int index = 0, count = 0, bufferSize = 1024;  
        byte[] buffer = new byte[bufferSize];  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        ZipFile zip = new ZipFile(zipFile);  
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.getEntries();  
        //循环对压缩包里的每一个文件进行解压       
        while(entries.hasMoreElements())  
        {  
            entry = entries.nextElement();  
            //构建压缩包中一个文件解压后保存的文件全路径  
            entryFilePath = unzipFilePath + File.separator + entry.getName();  
            //构建解压后保存的文件夹路径  
            index = entryFilePath.lastIndexOf(File.separator);  
            if (index != -1)  
            {  
                entryDirPath = entryFilePath.substring(0, index);  
            }  
            else  
            {  
                entryDirPath = "";  
            }             
            entryDir = new File(entryDirPath);  
            //如果文件夹路径不存在，则创建文件夹  
            if (!entryDir.exists() || !entryDir.isDirectory())  
            {  
                entryDir.mkdirs();  
            }  
              
            //创建解压文件  
            entryFile = new File(entryFilePath);  
            if (entryFile.exists())  
            {  
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException  
                SecurityManager securityManager = new SecurityManager();  
                securityManager.checkDelete(entryFilePath);  
                //删除已存在的目标文件  
                entryFile.delete();   
            }  
              
            //写入文件  
            bos = new BufferedOutputStream(new FileOutputStream(entryFile));  
            bis = new BufferedInputStream(zip.getInputStream(entry));  
            while ((count = bis.read(buffer, 0, bufferSize)) != -1)  
            {  
                bos.write(buffer, 0, count);  
            }  
            bos.flush();  
            bos.close();              
        }  
    }  
}
