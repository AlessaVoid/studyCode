package com.boco.PM.service.impl;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boco.SYS.util.StringUtil;
import com.boco.util.JsonUtils;

import bea.jolt.JoltMessage;
import bea.jolt.JoltRemoteService;
import bea.jolt.JoltSession;
import bea.jolt.JoltSessionAttributes;
import bea.jolt.JoltTransaction;
import bea.jolt.SessionException;

public class ChannelService {
	private static final Logger log = Logger.getLogger(ChannelService.class);

	public String send2Tux(String ip, String port, String spareIp,
			String sparePort, Map channeldata, String servername) {
		String username = null;
		String userPasswd = null;
		String userRole = null;
		String retString = null;
		StringBuffer sbuff = new StringBuffer();
		String errMsg = "Service is not available:" + servername;
		JoltSessionAttributes attr = new JoltSessionAttributes();
		System.setProperty("bea.jolt.encoding", "GBK");

		JoltSession session = null;
		JoltRemoteService toupper = null;
		try {
			String json = JsonUtils.toJson(channeldata);
			Map map = JsonUtils.ToMap(json);
			log.info("-----------建立连接 ip:" + ip + "----端口：" + port + "-----------");
			String sendAddress = ip + ":" + port;
			attr.setString(attr.APPADDRESS, "//" + sendAddress);
			attr.setInt(attr.IDLETIMEOUT, 300);
			session = new JoltSession(attr, username, userRole, userPasswd, null);
			toupper = new JoltRemoteService(servername, session);
			for (Iterator localIterator = map.keySet().iterator(); localIterator.hasNext();) {
				Object e = localIterator.next();
				toupper.setStringItem(e.toString(), 0, StringUtil.doEmpty(map.get(e.toString())));
				sbuff.append(e.toString() + "=" + map.get(e.toString()) + ":");
			}
			JoltTransaction trans = new JoltTransaction(5, session);

			log.info("发送报文开始...");
			log.info("发送报文：" + json);
			toupper.call(null);
			trans.commit();
			JoltMessage jm = toupper.getOutputs();
			retString = jm.toString();
			log.info("接收返回报文成功");
		} catch (Exception ex) {
			try {
				log.error("ip:" + ip + "----端口：" + port + "连接失败：", ex);
				log.info("-----------尝试连接备用服务器-----------");
				String json = JsonUtils.toJson(channeldata);
				Map map = JsonUtils.ToMap(json);
				log.info("-----------建立连接 备用ip:" + spareIp + "----备用端口：" + sparePort + "-----------");
				String sendAddress = spareIp + ":" + sparePort;
				attr.setString(attr.APPADDRESS, "//" + sendAddress);
				attr.setInt(attr.IDLETIMEOUT, 300);
				session = new JoltSession(attr, username, userRole, userPasswd, null);
				toupper = new JoltRemoteService(servername, session);
				for (Iterator localIterator = map.keySet().iterator(); localIterator.hasNext();) {
					Object e = localIterator.next();
					toupper.setStringItem(e.toString(), 0, StringUtil.doEmpty(map.get(e.toString())));
					sbuff.append(e.toString() + "=" + map.get(e.toString()) + ":");
				}
				JoltTransaction trans = new JoltTransaction(5, session);

				log.info("发送报文开始...");
				log.info("发送报文：" + json);
				toupper.call(null);
				trans.commit();
				JoltMessage jm = toupper.getOutputs();
				retString = jm.toString();
				log.info("接收返回报文成功");
			} catch (SessionException e1) {
				log.error("渠道-SessionException连接异常，渠道管理平台通讯失败", e1);
				retString = "渠道-SessionException连接异常，渠道管理平台通讯失败";
			} catch (Exception e) {
				if (errMsg.equals(e.getMessage())) {
					log.error("渠道-接收返回报文失败,服务名错误，渠道管理平台通讯失败", e);
					retString = "渠道-接收返回报文失败,服务名错误，渠道管理平台通讯失败";
				} else {
					log.error("渠道-接收返回报文失败,其他异常，渠道管理平台通讯失败", e);
					retString = "渠道-接收返回报文失败,其他异常，渠道管理平台通讯失败";
				}
			}
		} finally {
			if (session != null) {
				session.endSession();
			}
		}
		log.info("接收报文：" + retString);
		log.info("-----------断开连接-----------");
		return retString;
	}
}