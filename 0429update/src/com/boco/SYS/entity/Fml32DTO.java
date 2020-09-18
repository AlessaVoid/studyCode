package com.boco.SYS.entity;

import java.util.HashMap;
import java.util.Map;

import com.boco.SYS.util.DataProcess;

public class Fml32DTO {
	/**
	 * 
	 * 
	 * ��ù�Աǩ����tuxedo���͵����ݼ���
	 * 
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2015-07-29   ����         �����½�
	 * </pre>
	 */
	public Map getSigninSendMap(){
		Map map = new HashMap();
		DataProcess dp = new DataProcess();
		
		map.put("PCODE", "612050");
		map.put("CHANNEL_TYPE", "29");  //29�����ⲿϵͳ  
		map.put("SEQNO", "299937000"+dp.getSeqno());
		map.put("LOCALTIME", dp.getFormatDate("HHmmss"));
		map.put("LOCALDATE", dp.getFormatDate("yyyyMMdd"));
		map.put("TRANSINST", "99370000000"); //����ϵͳ����   //��Ա��99700060000 ��ƣ�99370000000   ������99700040000
		map.put("SENDINST", "99370000000");//����ϵͳ����
		map.put("TERMID", "01");//�ն˱�ʶ��:
		map.put("EDITION", "");
		map.put("DESTINST", "99700040000");
		map.put("REP_SIGN_IN_STAT", "0");     //1:�����ظ�ǩ��
		map.put("PROCESS_ID", "");
		map.put("BUSS_SYS", "99370000000"); //��Ա���ڵ�ҵ��ϵͳ����
		
		return map;
	}
	
	/**
	 * 
	 * 
	 * ��ù�Աǩ����tuxedo���͵����ݼ���
	 * 
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2015-07-29   ����         �����½�
	 * </pre>
	 */
	public Map getSignoutSendMap(){
		Map map = new HashMap();
		DataProcess dp = new DataProcess();
		
		map.put("PCODE", "612070");
		map.put("CHANNEL_TYPE", "29");
		map.put("SEQNO", "299937000"+dp.getSeqno());
		map.put("LOCALTIME", dp.getFormatDate("HHmmss"));
		map.put("LOCALDATE", dp.getFormatDate("yyyyMMdd"));
		map.put("TRANSINST", "99700040000");//����ϵͳ����
		map.put("SENDINST", "99370000000");//����ϵͳ����
		map.put("EDITION", "");
		map.put("DESTINST", "99700040000");
		map.put("SIGN_OFF_STAT", "2");
		map.put("BUSS_SYS", "99370000000");//��Ա���ڵ�ҵ��ϵͳ����
		
		return map;
	}
	/**
	 * 
	 *  
	 * ��ù�Աǿ��ǩ����tuxedo���͵����ݼ���
	 * 
	 * <pre>
	 * �޸�����            �޸���      �޸�ԭ��
	 * 2015-07-29   ����         �����½�
	 * </pre>
	 */
	public Map getQzSignoutSendMap() {
		Map map = new HashMap();
		DataProcess dp = new DataProcess();

		map.put("PCODE", "612080");
		map.put("CHANNEL_TYPE", "29");
		map.put("SEQNO", "299937000"+dp.getSeqno());
		map.put("LOCALTIME", dp.getFormatDate("HHmmss"));
		map.put("LOCALDATE", dp.getFormatDate("yyyyMMdd"));
		map.put("TRANSINST", "99700040000");//����ϵͳ����
		map.put("SENDINST", "99370000000");//����ϵͳ����
		map.put("EDITION", "");
		map.put("DESTINST", "99700040000");
		map.put("BUSS_SYS", "99370000000");//��Ա���ڵ�ҵ��ϵͳ����
		map.put("ADD_INST", "");            //����ϵͳ����

		return map;
	}
}
