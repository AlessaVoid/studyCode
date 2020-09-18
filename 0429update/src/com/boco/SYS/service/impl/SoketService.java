package com.boco.SYS.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.SYS.exception.SystemException;
import com.boco.SYS.util.BocoUtils;

public class SoketService {
	Log log = LogFactory.getLog(this.getClass());

	private String replaceData(String message) {
		String str = BocoUtils.getForamt("HHmmssS").format(new Date());
		str = str.substring(0, str.length() - 1);
		return message.replaceAll("batchSerial", str);
	}

	public String sendToPost(String ip, int port, String message) throws Exception {
		message = this.replaceData(message);
		String outMessage = "";
		int length = message.getBytes().length;
		String messageHead = "01" + String.format("%04d", length);
		message = messageHead + message;
		log.info("���ĸ�ʽ=[" + message + "]");
		try {
			InputStream in = null;
			Socket client = null;
			client = new Socket(ip, port);
			client.setSoTimeout(60 * 1000);
			log.info("SOCKET�������");
			/** �õ���������������� */
			OutputStream out = client.getOutputStream();
			out.write(message.getBytes());
			out.flush();
			log.info("���ͳɹ�");
			/** �õ�����������ȡ���� */
			in = client.getInputStream();
			byte title[] = new byte[6];
			in.read(title);
			log.info("����ͷ=[" + new String(title) + "]");
			int len = Integer.parseInt(new String(title).substring(2, 6));
			log.info("��Ӧ���ĳ���=[" + len + "]");
			byte[] byteMessage = new byte[len];
			in.read(byteMessage);
			outMessage = new String(byteMessage);
			in.close();
			out.close();
			client.close();
		} catch (Exception e) {
			outMessage = "ͨѶʧ��";
			log.error("ͨѶʧ��");
			e.printStackTrace();
			throw new SystemException("w999","���̨������ͨѶʧ��");

		}
		log.info("��Ӧ��Ϣ=[" + outMessage + "]");
		return outMessage;
	}
}