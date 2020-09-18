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
	 * TODO zipѹ��
	 *
	 * @param zipFilePath zip�ļ���ȫ·��
	 * @param unzipFilePath Ҫѹ�����ļ���·��
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��12��20��    	   ������  �½�
	 * </pre>
	 */
	public static void zip(String zipFilePath, String unzipFilePath) throws Exception   {  
		byte[] buffer=new byte[1024];
		ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipFilePath));
		out.setComment("��Ʒ˵����");
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
	 * TODO zip��ѹ�ļ�
	 *
	 * @param zipFilePath zip�ļ���ȫ·��
	 * @param unzipFilePath ��ѹ����ļ������·��
	 * @param includeZipFileName ��ѹ����ļ������·���Ƿ����ѹ���ļ����ļ���
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��12��20��    	   ������  �½�
	 * </pre>
	 */
	public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception   {  
        if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath))      {  
           //{0}����Ϊ�գ�
        	throw new SystemException("w554", "zipĿ¼/��ѹ�ļ�·��");       
        }  
        File zipFile = new File(zipFilePath);  
        //�����ѹ����ļ�����·������ѹ���ļ����ļ�������׷�Ӹ��ļ�������ѹ·��  
        if (includeZipFileName)  
        {  
            String fileName = zipFile.getName();  
            if (StringUtils.isNotEmpty(fileName))  
            {  
                fileName = fileName.substring(0, fileName.lastIndexOf("."));  
            }  
            unzipFilePath = unzipFilePath + File.separator + fileName;  
        }  
        //������ѹ���ļ������·��  
        File unzipFileDir = new File(unzipFilePath);  
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory())  
        {  
            unzipFileDir.mkdirs();  
        }  
          
        //��ʼ��ѹ  
        ZipEntry entry = null;  
        String entryFilePath = null, entryDirPath = null;  
        File entryFile = null, entryDir = null;  
        int index = 0, count = 0, bufferSize = 1024;  
        byte[] buffer = new byte[bufferSize];  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        ZipFile zip = new ZipFile(zipFile);  
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.getEntries();  
        //ѭ����ѹ�������ÿһ���ļ����н�ѹ       
        while(entries.hasMoreElements())  
        {  
            entry = entries.nextElement();  
            //����ѹ������һ���ļ���ѹ�󱣴���ļ�ȫ·��  
            entryFilePath = unzipFilePath + File.separator + entry.getName();  
            //������ѹ�󱣴���ļ���·��  
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
            //����ļ���·�������ڣ��򴴽��ļ���  
            if (!entryDir.exists() || !entryDir.isDirectory())  
            {  
                entryDir.mkdirs();  
            }  
              
            //������ѹ�ļ�  
            entryFile = new File(entryFilePath);  
            if (entryFile.exists())  
            {  
                //����ļ��Ƿ�����ɾ�������������ɾ���������׳�SecurityException  
                SecurityManager securityManager = new SecurityManager();  
                securityManager.checkDelete(entryFilePath);  
                //ɾ���Ѵ��ڵ�Ŀ���ļ�  
                entryFile.delete();   
            }  
              
            //д���ļ�  
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
