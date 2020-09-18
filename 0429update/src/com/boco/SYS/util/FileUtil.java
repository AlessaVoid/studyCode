package com.boco.SYS.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {
	static Log log = LogFactory.getLog(FileUtil.class);

	/***
	 * �����ļ�
	 * @param rootDir�������ļ����ڵ�Ŀ¼
	 * @param file���ļ�
	 * @return
	 */
	public static Boolean saveFile(String rootDir, File file, String targetName){
		if(file == null){
			return false;
		}
		Boolean isSuccess = true;
		// FormFile����ָ����ȡ�ļ�������
		int i = 0;
		String type = file.getName();
		while (i != -1) {
			// �ҵ��ϴ��ļ������͵�λ�ã�����ط�����'.'
			i = type.indexOf(".");
			/**//* System.out.println(i); */
			/**//* ��ȡ�ϴ��ļ��ĺ�׺��,��ʱ�õ����ļ������� */
			type = type.substring(i + 1);
		}
		/*// �����ϴ�����Ϊjpg,txt,rar;
		if (!type.equals("jpg") && !type.equals("txt") && !type.equals("bmp")){
			// ���ϴ������Ͳ�Ϊ��������ʱ����ת������ҳ�档
			isSuccess = false;
		} else {
			
		}*/
		String fname = targetName;
		// InInputStream�����Դ��ض�����Դ��ȡ�ֽڵķ�����
		InputStream streamIn;
		// OutputStream������ĳ��Ŀ��д���ֽڵĳ����࣬����ط�д��Ŀ����path��ͨ�������FileOutputStreamȥд
		OutputStream streamOut;
		try {
			streamIn = new FileInputStream(file);
			// �õ����ֽ�������byte,���ǿ���ֱ����file.getFileSize(),Ҳ�����ڴ�����ȡ����ʱ��streamIn.available();
			// int ok=streamIn.available();
			int ok = (int)file.length();
			String strFee = null;
			// ����ط��Ǵ����ϴ���ΪM��λ����ʱ����һ������kb,����һ����byte;

			if (ok >= 1024 * 1024) {
				float ok1 = (((float) ok) / 1024f / 1024f);
				DecimalFormat myformat = new DecimalFormat("0.00");
				strFee = myformat.format(ok1) + "M";
				System.out.println(strFee + "M");
			} else if (ok > 1024 && ok <= 1024 * 1024) {
				double ok2 = ((double) ok) / 1024;
				DecimalFormat myformat = new DecimalFormat("0.00");
				strFee = myformat.format(ok2) + "kb";
				System.out.println(strFee + "kb");
			} else if (ok < 1024) {
				strFee = String.valueOf(ok) + "byte";
				System.out.println(strFee);

			}
			System.out.println(streamIn.available() + "�ļ���Сbyte");
			// �����io���µ��ϴ��ļ���
			File uploadFile = new File(rootDir); // ָ���ϴ��ļ���λ��
			if (!uploadFile.exists() || uploadFile == null) { // �ж�ָ��·��dir�Ƿ���ڣ��������򴴽�·��
				uploadFile.mkdirs();
			}
			// �ϴ���·��+�ļ���
			String path = uploadFile.getPath() + "/" + fname;
			streamOut = new FileOutputStream(path);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			// �����ݶ���byte�����һ���֣����ж����ֽ��������ֵ��8192��������ֽڽ��洢����buffer[0]��buffer[0+8190-1]�Ĳ�����
			// streamIn.read�������ص���ʵ�ʶ�ȡ�ֽ���Ŀ.�������ĩβ�򷵻�-1.���bytesRead����Ϊ0���ʾû�ж�ȡ�κ��ֽڡ�
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				// д��buffer�����һ���֣���buf[0]��ʼд�벢д��bytesRead���ֽڣ����write��������������ֱ���ֽ�д����ɡ�
				streamOut.write(buffer, 0, bytesRead);
			}
			// �ر����������,����File����
			streamOut.close();
			streamIn.close();
		} catch (FileNotFoundException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}
	/**
	 * 
	 *
	 * TODO ɾ��
	 *
	 * @param path
	 * @param fileName
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��12��20��    	   ������  �½�
	 * </pre>
	 */
	public static Boolean remFile(String path, String fileName){
		File  file  = new File(path+File.separator+fileName);
		if(!file.exists() || !file.isFile() || file == null){
			return false;
		}
		return file.delete();
	}
	/**
	 * 
	 *
	 * TODO ɾ��Ŀ¼��Ŀ¼���ļ�
	 *
	 * @param filePath
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��12��20��    	   ������  �½�
	 * </pre>
	 */
	public static Boolean remDirFile(String dirPath){
		File  file  = new File(dirPath);
		if(file.isDirectory()){
			File[] files=file.listFiles();
			for (File f : files) {
				if (f.exists()) {
					f.delete();
				}
			}
		}
		if (file.exists()) {
			file.delete();
		}
		return true;
	}
	/**
	 * 
	 *
	 * TODO �޸��ļ�����
	 *
	 * @param oldName �޸�ǰ�ļ���
	 * @param newName �޸ĺ��ļ���
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��12��20��    	   ������  �½�
	 * </pre>
	 * @throws IOException 
	 */
	public static Boolean renameTo(String oldName, String newName) throws IOException{
		File oldFile=new File(oldName);
		log.info("�޸�ǰ�ļ����ƣ�"+oldName);
		System.out.println("�޸�ǰ�ļ����ƣ�"+oldName);
		if (!oldFile.exists()) {
			oldFile.createNewFile();
		}
		String rootPath=oldFile.getParent();
		File newFile=new File(rootPath+File.separator+newName);
		log.info("�޸ĺ��ļ����ƣ�"+newName);
		System.out.println("�޸ĺ��ļ����ƣ�"+newName);
		if (oldFile.renameTo(newFile)) {
			return true;
		}else {
			return false;
			
		}
	}
	/**
	 * 
	 *
	 * TODO �ļ�����
	 *
	 * @param srcFileName Ҫ���Ƶ��ļ���
	 * @param destFileName ����Ŀ���ļ���
	 * @param overlay
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2017��1��10��    	   ������  �½�
	 * </pre>
	 */
	public static boolean copyFile(String srcFileName, String destFileName,boolean overlay) {
		File srcFile=new File(srcFileName);
		File destFile=new File(destFileName);
		int len=0;
		InputStream in=null;
		OutputStream out=null;
		try {
			in=new FileInputStream(srcFile);
			out =new FileOutputStream(destFile);
			byte[] buffer=new byte[1024];
			while ((len=in.read(buffer))!=-1) {
				out.write(buffer, 0, len);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if (out!=null) {
					out.close();
					}
					if (in!=null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return overlay;
	}
}
