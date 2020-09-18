package com.boco.SYS.aop;

import org.apache.log4j.Logger;

import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.global.SysParam;

/**
 * 
 * 
 *  ��־�����.
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2015��12��1��    	    ����    �½�
 * </pre>
 */
public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class);
	/**
	 * 
	 *
	 * TODO ��־�䷽��.
	 *
	 * @param level ��¼��־�ļ���
	 * @param log ��¼��־��Ϣ
	 * @param e �쳣��Ϣ
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��2��    	    ����    �½�
	 * </pre>
	 */
	public static void log(Debug level, WebLogInfo log){
		if(level.getValue()<=SysParam.DEBUG_LEVEL.getValue()){
			outLog(log, level);
		}
	}
	
	/**
	 * 
	 *
	 * TODO ������־���ݺͼ������log4j��־.
	 *
	 * @param log ��־��Ϣ
	 * @param level ��־����
	 *
	 * <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��1��    	    ����    �½�
	 * </pre>
	 */
	private static void outLog(WebLogInfo log, Debug level){
		switch(level){
		case DEBUG:
			debug(log);
			break;
		case INFO:
			info(log);
			break;
		case WARN:
			warn(log);
			break;
		case ERROR:
			error(log);
			break;
		default:
			break;
		}
	}
	
	private static void debug(WebLogInfo log){
		logger.debug("==========����ִ����Ϣ��ʼ==========");
		logger.debug("����ʱ�䣺" + log.getTradeDate() + log.getTradeTime());
		logger.debug("�߳�����:" + Thread.currentThread().getName());
		logger.debug("����Ա�˺�:" + log.getOperCode());
		logger.debug("����Ա����:" + log.getOperName());
		logger.debug("���ܺ�ʱ:" + log.getTradeConsumingTime() + "����");
        logger.debug("���󷽷�:" + log.getRunningMethod());
        logger.debug("����IP:" + log.getRequestIp());
        logger.debug("�������:" + log.getArgu());
        logger.debug("ִ�н��:" + log.getRunningResult());
        logger.debug("==========����ִ����Ϣ����==========");
	}
	
	private static void info(WebLogInfo log){
		logger.info("==========����ִ����Ϣ��ʼ==========");
		logger.info("����ʱ�䣺" + log.getTradeDate() + log.getTradeTime());
		logger.info("�߳�����:" + Thread.currentThread().getName());
		logger.info("����Ա�˺�:" + log.getOperCode());
		logger.info("����Ա����:" + log.getOperName());
		logger.info("���ܺ�ʱ:" + log.getTradeConsumingTime() + "����");
        logger.info("���󷽷�:" + log.getRunningMethod());
        logger.info("����IP:" + log.getRequestIp());
        logger.info("�������:" + log.getArgu());
        logger.info("ִ�н��:" + log.getRunningResult());
        logger.info("==========����ִ����Ϣ����==========");
	}
	
	private static void warn(WebLogInfo log){
		logger.warn("==========����ִ����Ϣ��ʼ==========");
		logger.warn("����ʱ�䣺" + log.getTradeDate() + log.getTradeTime());
		logger.warn("�߳�����:" + Thread.currentThread().getName());
		logger.warn("����Ա�˺�:" + log.getOperCode());
		logger.warn("����Ա����:" + log.getOperName());
		logger.warn("���ܺ�ʱ:" + log.getTradeConsumingTime() + "����");
        logger.warn("���󷽷�:" + log.getRunningMethod());
        logger.warn("����IP:" + log.getRequestIp());
        logger.warn("�������:" + log.getArgu());
        logger.warn("ִ�н��:" + log.getRunningResult());
        logger.warn("==========����ִ����Ϣ����==========");
	}
	
	private static void error(WebLogInfo log){
		logger.error("==========����ִ����Ϣ��ʼ==========");
		logger.error("����ʱ�䣺" + log.getTradeDate() + log.getTradeTime());
		logger.error("�߳�����:" + Thread.currentThread().getName());
		logger.error("����Ա�˺�:" + log.getOperCode());
		logger.error("����Ա����:" + log.getOperName());
		logger.error("���ܺ�ʱ:" + log.getTradeConsumingTime() + "����");
        logger.error("���󷽷�:" + log.getRunningMethod());
        logger.error("����IP:" + log.getRequestIp());
        logger.error("�������:" + log.getArgu());
        logger.error("ִ�н��:" + "�쳣");
        logger.error("�쳣��Ϣ:", log.getEx());
        logger.error("==========����ִ����Ϣ����==========");
	}
}
