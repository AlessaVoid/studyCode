package com.boco.SYS.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
/**
 * 
 * 
 *  sftp�������ļ�����
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��10��24��    	    ������    �½�
 * </pre>
 */
public class SftpUtil {
	static Log log = LogFactory.getLog(SftpUtil.class);
	static Session sshSession = null;
	static ChannelSftp sftp = null;

	public static void main(String[] args) {
		try {
			SftpUtil.connect("20.13.2.181", 22, "ptds", "boco123");
			SftpUtil.upload("/app/ptds/test/", "D:/log1.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 *
	 * TODO ����sftp������
	 *
	 * @param host ����IP
	 * @param port �˿ں�
	 * @param username	�û���
	 * @param password ����
	 * @throws JSchException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static void connect(String host, int port, String username, String password) throws JSchException {
		log.info("�����ļ���������:ip[" + host + "],port[" + port + "],username[" + username + "]");
		password=ThreeDes.decryptString(password, ThreeDes.KEY);
		File file = new File(SftpUtil.class.getClassLoader().getResource("/").getPath());
		connect(host, port, username, password, "/home/weblogic/.ssh/id_rsa");
	}
	/**
	 * 
	 *
	 * TODO ����sftp������
	 *
	 * @param host ����ip��ַ
	 * @param port �˿ں�
	 * @param username �û���
	 * @param password ����
	 * @param sshPrivateKeyFileName ֤��
	 * @throws JSchException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static void connect(String host, int port, String username, String password, String sshPrivateKeyFileName) throws JSchException {
		JSch jsch = new JSch();
		//jsch.getSession(username, host, port);
		sshSession = jsch.getSession(username, host, port);
		log.debug("Session created.");

		/** ���֤��·��Ϊ�գ����������ķ�ʽ */
		if (sshPrivateKeyFileName == null || "".equals(sshPrivateKeyFileName)) {
			sshSession.setPassword(password);
		} else {
			jsch.addIdentity(sshPrivateKeyFileName);
		}

		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		try {
			sshSession.connect();
			log.info("ssh��Կ�ɹ�");
		} catch (Exception JSchException) {
			// TODO: handle exception
			log.info("ssh��Կʧ�ܳ���ʹ����������");
			sshSession = jsch.getSession(username, host, port);
			log.debug("Session created.");
			sshSession.setPassword(password);
			sshSession.setConfig(sshConfig);
			sshSession.connect();
		}
		log.debug("Session connected.");
		log.debug("Opening Channel.");
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		log.debug("Connected to " + host + ".");
	}

	/**
	 * 
	 *
	 * TODO �Ͽ�����
	 *
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static void disconnect() {
		if (sftp != null && sftp.isConnected()) {
			sftp.disconnect();
			log.debug("sftp closed.");
		}
		if (sshSession != null && sshSession.isConnected()) {
			sshSession.disconnect();
			log.debug("sshSession closed.");
		}
	}
	/**
	 * 
	 *
	 * TODO �ϴ��ļ�
	 *
	 * @param remoteDir 	Ŀ��Ŀ¼
	 * @param uploadFile	Դ�ļ�Ŀ¼
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static boolean upload(String remoteDir, String uploadFile) {
		log.info("�ļ�����:Դ�ļ�Ŀ¼:" + uploadFile + ",Ŀ��Ŀ¼" + remoteDir);
		if (sftp == null) {
			log.debug("channel sftp is null");
			return false;
		}
		try {
			try {
				log.debug("sftpĬ��Ŀ¼��" + sftp.pwd());
				sftp.cd(remoteDir);
			} catch (Exception e) {
				log.error("��ȡ�ļ�·����" + remoteDir + "ʧ��" + e.getMessage());
				try {
					log.debug("����һ��Ŀ¼");
					sftp.mkdir(remoteDir);
					sftp.cd(remoteDir);
				} catch (Exception e1) {
					e1.printStackTrace();
					log.error("����Ŀ¼ʧ��" + e.getMessage());
					return false;
				}
			}
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
			disconnect();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 *
	 * TODO �����ļ�
	 *
	 * @param remoteDir ����Ŀ¼
	 * @param downloadFile	�����ļ�����
	 * @param saveFile		���ڱ��ص�·��
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static boolean download(String remoteDir, String downloadFile, String saveFile) {
		try {
			sftp.cd(remoteDir);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
			disconnect();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	/**
	 * 
	 *
	 * TODO   ɾ���ļ�
	 * @param remoteDir   Ҫɾ���ļ�����Ŀ¼
	 * @param deleteFile   Ҫɾ���ļ�
	 * @return
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static boolean delete(String remoteDir, String deleteFile) {
		try {
			sftp.cd(remoteDir);
			sftp.rm(deleteFile);
			disconnect();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	/**
	 * 
	 *
	 * TODO Ҫ�г���Ŀ¼
	 *
	 * @param remoteDir Ҫ�г���Ŀ¼
	 * @return
	 * @throws SftpException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static Vector listFiles(String remoteDir) throws SftpException {
		Vector<ChannelSftp> list = null;
		ChannelSftp obj = new ChannelSftp();
		list = sftp.ls(remoteDir);
		disconnect();
		return list;
	}
	/**
	 * 
	 *
	 * TODO �г�Ŀ¼�µ��ļ����Ͽ�����
	 *
	 * @param remoteDir	  Ҫ�г���Ŀ¼
	 * @return
	 * @throws SftpException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static Vector listFilesNoDis(String remoteDir) throws SftpException {
		Vector<ChannelSftp> list = null;
		ChannelSftp obj = new ChannelSftp();
		list = sftp.ls(remoteDir);
		return list;
	}
	/**
	 * 
	 *
	 * TODO�ϴ��ļ����Ͽ�����
	 *
	 * @param remoteDir Ŀ��Ŀ¼
	 * @param uploadFile �ϴ�Դ�ļ�
	 * @return
	 * @throws FileNotFoundException
	 * @throws SftpException
	 * @throws IOException
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2016��10��24��    	   ������  �½�
	 * </pre>
	 */
	public static boolean uploadNoDis(String remoteDir, String uploadFile) throws FileNotFoundException, SftpException, IOException {
		FileInputStream fileInputStream = null;
		try {
			sftp.cd(remoteDir);
			File file = new File(uploadFile);
			fileInputStream = new FileInputStream(file);
			sftp.put(fileInputStream, file.getName());
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("�ϴ��ļ��ر��ļ�������");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (SftpException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		return true;
	}

	/**
	 * �����ļ����Ͽ�����
	 * 
	 * @param remoteDir
	 *            ����Ŀ¼
	 * @param downloadFile
	 *            ���ص��ļ�
	 * @param saveFile
	 *            ���ڱ��ص�·��
	 * @param sftp
	 * @throws SftpException
	 * @throws FileNotFoundException
	 */
	public static boolean downloadNoDis(String remoteDir, String downloadFile, String saveFile) throws FileNotFoundException, SftpException, IOException {
		FileOutputStream fileOutputStream = null;
		try {
			sftp.cd(remoteDir);
			File fileDir = new File(saveFile.substring(0, saveFile.lastIndexOf(File.separator)));
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File file = new File(saveFile);
			fileOutputStream = new FileOutputStream(file);
			sftp.get(downloadFile, fileOutputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (SftpException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
		return true;
	}

}