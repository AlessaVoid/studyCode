package com.boco.SYS.util;

import java.io.File;
import org.apache.log4j.Logger;
import com.boco.SYS.cache.DicCache;

/**
 * 
 * 
 *  ������Ⱥ����sftp.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��5��14��    	     ����    �½�
 * </pre>
 */
public class ServerToServerSftp {
	static Logger log = Logger.getLogger(ServerToServerSftp.class);
	/**
	 * 
	 *
	 * TODO �ļ�����.
	 *
	 * @param filePath
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��5��14��    	     ����    �½�
	 * </pre>
	 */
	public static void fileSyn(String filePath) throws Exception{
		
		String remoteAddr1 = DicCache.getKeyByName_("SERVER_IP_1", "SYSTEM_PARAM");//Ŀ������1
		String remoteAddr2 = DicCache.getKeyByName_("SERVER_IP_2", "SYSTEM_PARAM");//Ŀ������2
		String port = DicCache.getKeyByName_("SERVER_PORT", "SYSTEM_PARAM");//�˿�  22
		String username = DicCache.getKeyByName_("SERVER_LOGIN_NAME", "SYSTEM_PARAM");//�û��� weblogic
		String password = DicCache.getKeyByName_("SERVER_LOGIN_PWD", "SYSTEM_PARAM");//���� weblogic
		String isOpen=DicCache.getKeyByName_("SERVER_FILE_SYN_STATE", "SYSTEM_PARAM");//�Ƿ���ļ�ͬ������ 0-������1-�ر�,Ĭ��0
		
		//�ж��ļ�ͬ�������Ƿ��
		if(isOpen=="0"||"0".equals(isOpen)){
			String locaIp=IPUtil.getLocalHostIpAddr();
			String ip=null;//Ŀ������ip
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
	 * TODO �ļ�ɾ��
	 *
	 * @param filePath
	 * @throws Exception
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��11��3��    	   ������  �½�
	 * </pre>
	 */
	public static void fileSynDel(String filePath)throws Exception {
		
		String remoteAddr1 = DicCache.getKeyByName_("SERVER_IP_1", "SYSTEM_PARAM");//Ŀ������1
		String remoteAddr2 = DicCache.getKeyByName_("SERVER_IP_2", "SYSTEM_PARAM");//Ŀ������2
		String port = DicCache.getKeyByName_("SERVER_PORT", "SYSTEM_PARAM");//�˿�  22
		String username = DicCache.getKeyByName_("SERVER_LOGIN_NAME", "SYSTEM_PARAM");//�û��� weblogic
		String password = DicCache.getKeyByName_("SERVER_LOGIN_PWD", "SYSTEM_PARAM");//���� weblogic
		String isOpen=DicCache.getKeyByName_("SERVER_FILE_SYN_STATE", "SYSTEM_PARAM");//�Ƿ���ļ�ͬ������ 0-������1-�ر�,Ĭ��0
		
		//�ж��ļ�ͬ�������Ƿ��
		if(isOpen=="0"||"0".equals(isOpen)){
			String locaIp=IPUtil.getLocalHostIpAddr();
			String ip=null;//Ŀ������ip
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
