package com.boco.SYS.biz;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import org.apache.log4j.Logger;
import com.boco.SYS.util.BocoUtils;


public class SoketBiz {
	Logger log = Logger.getLogger(SoketBiz.class);

	private String replaceData(String message) {
		String str = BocoUtils.getForamt("HHmmssS").format(new Date());
		str = str.substring(0, str.length() - 1);
		return message.replaceAll("batchSerial", str);
	}

	public String sendToPost(String ip, int port, String message) throws Exception {
		message = this.replaceData(message);
		String outMessage = "";
		int length = message.getBytes().length;
		String messageHead = "01" + String.format("%04d", length+1);
		message = messageHead +"|"+ message;
		log.info("报文格式=[" + message + "]");
		try {
			InputStream in = null;
			Socket client = null;
			client = new Socket(ip, port);
			client.setSoTimeout(60 * 1000);
			log.info("SOCKET连接完毕");
			/** 得到输出流并发送内容 */
			OutputStream out = client.getOutputStream();
			out.write(message.getBytes());
			out.flush();
			log.info("发送成功");
			/** 得到输入流并读取报文 */
			in = client.getInputStream();
			byte title[] = new byte[6];
			in.read(title);
			log.info("报文头=[" + new String(title) + "]");
			int len = Integer.parseInt(new String(title).substring(2, 6));
			log.info("响应报文长度=[" + len + "]");
			byte[] byteMessage = new byte[len];
			in.read(byteMessage);
			outMessage = new String(byteMessage);
			in.close();
			out.close();
			client.close();
		} catch (Exception e) {
			outMessage = "通讯失败";
			log.error("通讯失败");
			e.printStackTrace();
			throw new Exception("与后台服务器通讯失败");

		}
		log.info("响应信息=[" + outMessage + "]");
		return outMessage;
	}
}
