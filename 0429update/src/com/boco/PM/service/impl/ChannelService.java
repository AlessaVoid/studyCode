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
			log.info("-----------�������� ip:" + ip + "----�˿ڣ�" + port + "-----------");
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

			log.info("���ͱ��Ŀ�ʼ...");
			log.info("���ͱ��ģ�" + json);
			toupper.call(null);
			trans.commit();
			JoltMessage jm = toupper.getOutputs();
			retString = jm.toString();
			log.info("���շ��ر��ĳɹ�");
		} catch (Exception ex) {
			try {
				log.error("ip:" + ip + "----�˿ڣ�" + port + "����ʧ�ܣ�", ex);
				log.info("-----------�������ӱ��÷�����-----------");
				String json = JsonUtils.toJson(channeldata);
				Map map = JsonUtils.ToMap(json);
				log.info("-----------�������� ����ip:" + spareIp + "----���ö˿ڣ�" + sparePort + "-----------");
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

				log.info("���ͱ��Ŀ�ʼ...");
				log.info("���ͱ��ģ�" + json);
				toupper.call(null);
				trans.commit();
				JoltMessage jm = toupper.getOutputs();
				retString = jm.toString();
				log.info("���շ��ر��ĳɹ�");
			} catch (SessionException e1) {
				log.error("����-SessionException�����쳣����������ƽ̨ͨѶʧ��", e1);
				retString = "����-SessionException�����쳣����������ƽ̨ͨѶʧ��";
			} catch (Exception e) {
				if (errMsg.equals(e.getMessage())) {
					log.error("����-���շ��ر���ʧ��,������������������ƽ̨ͨѶʧ��", e);
					retString = "����-���շ��ر���ʧ��,������������������ƽ̨ͨѶʧ��";
				} else {
					log.error("����-���շ��ر���ʧ��,�����쳣����������ƽ̨ͨѶʧ��", e);
					retString = "����-���շ��ر���ʧ��,�����쳣����������ƽ̨ͨѶʧ��";
				}
			}
		} finally {
			if (session != null) {
				session.endSession();
			}
		}
		log.info("���ձ��ģ�" + retString);
		log.info("-----------�Ͽ�����-----------");
		return retString;
	}
}