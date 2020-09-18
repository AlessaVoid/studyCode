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
 *  sftp服务器文件服务
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年10月24日    	    谷立羊    新建
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
	 * TODO 连接sftp服务器
	 *
	 * @param host 主机IP
	 * @param port 端口号
	 * @param username	用户名
	 * @param password 密码
	 * @throws JSchException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
	 * </pre>
	 */
	public static void connect(String host, int port, String username, String password) throws JSchException {
		log.info("建立文件传输链接:ip[" + host + "],port[" + port + "],username[" + username + "]");
		password=ThreeDes.decryptString(password, ThreeDes.KEY);
		File file = new File(SftpUtil.class.getClassLoader().getResource("/").getPath());
		connect(host, port, username, password, "/home/weblogic/.ssh/id_rsa");
	}
	/**
	 * 
	 *
	 * TODO 连接sftp服务器
	 *
	 * @param host 主机ip地址
	 * @param port 端口号
	 * @param username 用户名
	 * @param password 密码
	 * @param sshPrivateKeyFileName 证书
	 * @throws JSchException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
	 * </pre>
	 */
	public static void connect(String host, int port, String username, String password, String sshPrivateKeyFileName) throws JSchException {
		JSch jsch = new JSch();
		//jsch.getSession(username, host, port);
		sshSession = jsch.getSession(username, host, port);
		log.debug("Session created.");

		/** 如果证书路径为空，则采用密码的方式 */
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
			log.info("ssh验钥成功");
		} catch (Exception JSchException) {
			// TODO: handle exception
			log.info("ssh验钥失败尝试使用密码重试");
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
	 * TODO 断开连接
	 *
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
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
	 * TODO 上传文件
	 *
	 * @param remoteDir 	目标目录
	 * @param uploadFile	源文件目录
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
	 * </pre>
	 */
	public static boolean upload(String remoteDir, String uploadFile) {
		log.info("文件传输:源文件目录:" + uploadFile + ",目标目录" + remoteDir);
		if (sftp == null) {
			log.debug("channel sftp is null");
			return false;
		}
		try {
			try {
				log.debug("sftp默认目录：" + sftp.pwd());
				sftp.cd(remoteDir);
			} catch (Exception e) {
				log.error("获取文件路径：" + remoteDir + "失败" + e.getMessage());
				try {
					log.debug("创建一级目录");
					sftp.mkdir(remoteDir);
					sftp.cd(remoteDir);
				} catch (Exception e1) {
					e1.printStackTrace();
					log.error("创建目录失败" + e.getMessage());
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
	 * TODO 下载文件
	 *
	 * @param remoteDir 下载目录
	 * @param downloadFile	下载文件名称
	 * @param saveFile		存在本地的路径
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
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
	 * TODO   删除文件
	 * @param remoteDir   要删除文件所在目录
	 * @param deleteFile   要删除文件
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
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
	 * TODO 要列出的目录
	 *
	 * @param remoteDir 要列出的目录
	 * @return
	 * @throws SftpException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
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
	 * TODO 列出目录下的文件不断开连接
	 *
	 * @param remoteDir	  要列出的目录
	 * @return
	 * @throws SftpException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
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
	 * TODO上传文件不断开连接
	 *
	 * @param remoteDir 目标目录
	 * @param uploadFile 上传源文件
	 * @return
	 * @throws FileNotFoundException
	 * @throws SftpException
	 * @throws IOException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年10月24日    	   谷立羊  新建
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
				log.error("上传文件关闭文件流出错！");
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
	 * 下载文件不断开连接
	 * 
	 * @param remoteDir
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
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