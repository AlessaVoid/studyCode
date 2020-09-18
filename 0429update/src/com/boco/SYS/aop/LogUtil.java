package com.boco.SYS.aop;

import org.apache.log4j.Logger;

import com.boco.SYS.entity.WebLogInfo;
import com.boco.SYS.global.Dic.Debug;
import com.boco.SYS.global.SysParam;

/**
 * 
 * 
 *  日志输出类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年12月1日    	    杨滔    新建
 * </pre>
 */
public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class);
	/**
	 * 
	 *
	 * TODO 日志输方法.
	 *
	 * @param level 记录日志的级别
	 * @param log 记录日志信息
	 * @param e 异常信息
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月2日    	    杨滔    新建
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
	 * TODO 根据日志内容和级别输出log4j日志.
	 *
	 * @param log 日志信息
	 * @param level 日志级别
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年12月1日    	    杨滔    新建
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
		logger.debug("==========方法执行信息开始==========");
		logger.debug("交易时间：" + log.getTradeDate() + log.getTradeTime());
		logger.debug("线程名称:" + Thread.currentThread().getName());
		logger.debug("操作员账号:" + log.getOperCode());
		logger.debug("操作员姓名:" + log.getOperName());
		logger.debug("功能耗时:" + log.getTradeConsumingTime() + "毫秒");
        logger.debug("请求方法:" + log.getRunningMethod());
        logger.debug("请求IP:" + log.getRequestIp());
        logger.debug("请求参数:" + log.getArgu());
        logger.debug("执行结果:" + log.getRunningResult());
        logger.debug("==========方法执行信息结束==========");
	}
	
	private static void info(WebLogInfo log){
		logger.info("==========方法执行信息开始==========");
		logger.info("交易时间：" + log.getTradeDate() + log.getTradeTime());
		logger.info("线程名称:" + Thread.currentThread().getName());
		logger.info("操作员账号:" + log.getOperCode());
		logger.info("操作员姓名:" + log.getOperName());
		logger.info("功能耗时:" + log.getTradeConsumingTime() + "毫秒");
        logger.info("请求方法:" + log.getRunningMethod());
        logger.info("请求IP:" + log.getRequestIp());
        logger.info("请求参数:" + log.getArgu());
        logger.info("执行结果:" + log.getRunningResult());
        logger.info("==========方法执行信息结束==========");
	}
	
	private static void warn(WebLogInfo log){
		logger.warn("==========方法执行信息开始==========");
		logger.warn("交易时间：" + log.getTradeDate() + log.getTradeTime());
		logger.warn("线程名称:" + Thread.currentThread().getName());
		logger.warn("操作员账号:" + log.getOperCode());
		logger.warn("操作员姓名:" + log.getOperName());
		logger.warn("功能耗时:" + log.getTradeConsumingTime() + "毫秒");
        logger.warn("请求方法:" + log.getRunningMethod());
        logger.warn("请求IP:" + log.getRequestIp());
        logger.warn("请求参数:" + log.getArgu());
        logger.warn("执行结果:" + log.getRunningResult());
        logger.warn("==========方法执行信息结束==========");
	}
	
	private static void error(WebLogInfo log){
		logger.error("==========方法执行信息开始==========");
		logger.error("交易时间：" + log.getTradeDate() + log.getTradeTime());
		logger.error("线程名称:" + Thread.currentThread().getName());
		logger.error("操作员账号:" + log.getOperCode());
		logger.error("操作员姓名:" + log.getOperName());
		logger.error("功能耗时:" + log.getTradeConsumingTime() + "毫秒");
        logger.error("请求方法:" + log.getRunningMethod());
        logger.error("请求IP:" + log.getRequestIp());
        logger.error("请求参数:" + log.getArgu());
        logger.error("执行结果:" + "异常");
        logger.error("异常信息:", log.getEx());
        logger.error("==========方法执行信息结束==========");
	}
}
