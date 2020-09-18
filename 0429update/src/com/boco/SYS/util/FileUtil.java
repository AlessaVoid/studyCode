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
	 * 保存文件
	 * @param rootDir，保存文件所在的目录
	 * @param file，文件
	 * @return
	 */
	public static Boolean saveFile(String rootDir, File file, String targetName){
		if(file == null){
			return false;
		}
		Boolean isSuccess = true;
		// FormFile用于指定存取文件的类型
		int i = 0;
		String type = file.getName();
		while (i != -1) {
			// 找到上传文件的类型的位置，这个地方的是'.'
			i = type.indexOf(".");
			/**//* System.out.println(i); */
			/**//* 截取上传文件的后缀名,此时得到了文件的类型 */
			type = type.substring(i + 1);
		}
		/*// 限制上传类型为jpg,txt,rar;
		if (!type.equals("jpg") && !type.equals("txt") && !type.equals("bmp")){
			// 当上传的类型不为上述类型时，跳转到错误页面。
			isSuccess = false;
		} else {
			
		}*/
		String fname = targetName;
		// InInputStream是用以从特定的资源读取字节的方法。
		InputStream streamIn;
		// OutputStream用于向某个目标写入字节的抽象类，这个地方写入目标是path，通过输出流FileOutputStream去写
		OutputStream streamOut;
		try {
			streamIn = new FileInputStream(file);
			// 得到是字节数，即byte,我们可以直接用file.getFileSize(),也可以在创建读取对象时用streamIn.available();
			// int ok=streamIn.available();
			int ok = (int)file.length();
			String strFee = null;
			// 这个地方是处理上传的为M单位计算时，下一个是以kb,在下一个是byte;

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
			System.out.println(streamIn.available() + "文件大小byte");
			// 这个是io包下的上传文件类
			File uploadFile = new File(rootDir); // 指定上传文件的位置
			if (!uploadFile.exists() || uploadFile == null) { // 判断指定路径dir是否存在，不存在则创建路径
				uploadFile.mkdirs();
			}
			// 上传的路径+文件名
			String path = uploadFile.getPath() + "/" + fname;
			streamOut = new FileOutputStream(path);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			// 将数据读入byte数组的一部分，其中读入字节数的最大值是8192，读入的字节将存储到，buffer[0]到buffer[0+8190-1]的部分中
			// streamIn.read方法返回的是实际读取字节数目.如果读到末尾则返回-1.如果bytesRead返回为0则表示没有读取任何字节。
			while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
				// 写入buffer数组的一部分，从buf[0]开始写入并写入bytesRead个字节，这个write方法将发生阻塞直至字节写入完成。
				streamOut.write(buffer, 0, bytesRead);
			}
			// 关闭输出输入流,销毁File流。
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
	 * TODO 删除
	 *
	 * @param path
	 * @param fileName
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月20日    	   谷立羊  新建
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
	 * TODO 删除目录及目录下文件
	 *
	 * @param filePath
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月20日    	   谷立羊  新建
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
	 * TODO 修改文件名称
	 *
	 * @param oldName 修改前文件名
	 * @param newName 修改后文件名
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年12月20日    	   谷立羊  新建
	 * </pre>
	 * @throws IOException 
	 */
	public static Boolean renameTo(String oldName, String newName) throws IOException{
		File oldFile=new File(oldName);
		log.info("修改前文件名称："+oldName);
		System.out.println("修改前文件名称："+oldName);
		if (!oldFile.exists()) {
			oldFile.createNewFile();
		}
		String rootPath=oldFile.getParent();
		File newFile=new File(rootPath+File.separator+newName);
		log.info("修改后文件名称："+newName);
		System.out.println("修改后文件名称："+newName);
		if (oldFile.renameTo(newFile)) {
			return true;
		}else {
			return false;
			
		}
	}
	/**
	 * 
	 *
	 * TODO 文件复制
	 *
	 * @param srcFileName 要复制的文件名
	 * @param destFileName 复制目标文件名
	 * @param overlay
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2017年1月10日    	   谷立羊  新建
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
