package com.boco.SYS.util;

import java.io.File;
import org.apache.log4j.Logger;
import com.boco.SYS.cache.DicCache;

/**
 * 
 * 
 *  服务器群组内sftp.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年5月14日    	     代策    新建
 * </pre>
 */
public class ServerToServerSftp {
	static Logger log = Logger.getLogger(ServerToServerSftp.class);
	/**
	 * 
	 *
	 * TODO 文件传输.
	 *
	 * @param filePath
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年5月14日    	     代策    新建
	 * </pre>
	 */
	public static void fileSyn(String filePath) throws Exception{
		
		String remoteAddr1 = DicCache.getKeyByName_("SERVER_IP_1", "SYSTEM_PARAM");//目标主机1
		String remoteAddr2 = DicCache.getKeyByName_("SERVER_IP_2", "SYSTEM_PARAM");//目标主机2
		String port = DicCache.getKeyByName_("SERVER_PORT", "SYSTEM_PARAM");//端口  22
		String username = DicCache.getKeyByName_("SERVER_LOGIN_NAME", "SYSTEM_PARAM");//用户名 weblogic
		String password = DicCache.getKeyByName_("SERVER_LOGIN_PWD", "SYSTEM_PARAM");//密码 weblogic
		String isOpen=DicCache.getKeyByName_("SERVER_FILE_SYN_STATE", "SYSTEM_PARAM");//是否打开文件同步机制 0-开启，1-关闭,默认0
		
		//判断文件同步机制是否打开
		if(isOpen=="0"||"0".equals(isOpen)){
			String locaIp=IPUtil.getLocalHostIpAddr();
			String ip=null;//目标主机ip
			if(locaIp.equals(remoteAddr1)){
				ip=remoteAddr2;
			}else{
				ip=remoteAddr1;
			}
			String fileUrl=filePath.substring(0,filePath.lastIndexOf(File.separator));
			SftpUtil sftpUtil = new SftpUtil();
			sftpUtil.connect(ip.replaceAll("\"", ""), Integer.valueOf(port.replaceAll("\"", "")), username.replaceAll("\"", ""), password.replaceAll("\"", ""));
			sftpUtil.upload(fileUrl, filePath);
		}
	}
	/**
	 * 
	 *
	 * TODO 文件删除
	 *
	 * @param filePath
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年11月3日    	   谷立羊  新建
	 * </pre>
	 */
	public static void fileSynDel(String filePath)throws Exception {
		
		String remoteAddr1 = DicCache.getKeyByName_("SERVER_IP_1", "SYSTEM_PARAM");//目标主机1
		String remoteAddr2 = DicCache.getKeyByName_("SERVER_IP_2", "SYSTEM_PARAM");//目标主机2
		String port = DicCache.getKeyByName_("SERVER_PORT", "SYSTEM_PARAM");//端口  22
		String username = DicCache.getKeyByName_("SERVER_LOGIN_NAME", "SYSTEM_PARAM");//用户名 weblogic
		String password = DicCache.getKeyByName_("SERVER_LOGIN_PWD", "SYSTEM_PARAM");//密码 weblogic
		String isOpen=DicCache.getKeyByName_("SERVER_FILE_SYN_STATE", "SYSTEM_PARAM");//是否打开文件同步机制 0-开启，1-关闭,默认0
		
		//判断文件同步机制是否打开
		if(isOpen=="0"||"0".equals(isOpen)){
			String locaIp=IPUtil.getLocalHostIpAddr();
			String ip=null;//目标主机ip
			if(locaIp.equals(remoteAddr1)){
				ip=remoteAddr2;
			}else{
				ip=remoteAddr1;
			}
			String remoteDir=filePath.substring(0,filePath.lastIndexOf(File.separator));
			SftpUtil sftpUtil = new SftpUtil();
			sftpUtil.connect(ip.replaceAll("\"", ""), Integer.valueOf(port.replaceAll("\"", "")), username.replaceAll("\"", ""), password.replaceAll("\"", ""));
			sftpUtil.delete(remoteDir, filePath);
		}
	}
}
