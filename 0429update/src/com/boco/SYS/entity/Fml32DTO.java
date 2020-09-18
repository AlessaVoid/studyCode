package com.boco.SYS.entity;

import java.util.HashMap;
import java.util.Map;

import com.boco.SYS.util.DataProcess;

public class Fml32DTO {
	/**
	 * 
	 * 
	 * 获得柜员签到向tuxedo发送的数据集合
	 * 
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2015-07-29   杜旭         批量新建
	 * </pre>
	 */
	public Map getSigninSendMap(){
		Map map = new HashMap();
		DataProcess dp = new DataProcess();
		
		map.put("PCODE", "612050");
		map.put("CHANNEL_TYPE", "29");  //29代表外部系统  
		map.put("SEQNO", "299937000"+dp.getSeqno());
		map.put("LOCALTIME", dp.getFormatDate("HHmmss"));
		map.put("LOCALDATE", dp.getFormatDate("yyyyMMdd"));
		map.put("TRANSINST", "99370000000"); //交易系统代码   //柜员：99700060000 理财：99370000000   渠道：99700040000
		map.put("SENDINST", "99370000000");//发信系统代码
		map.put("TERMID", "01");//终端标识码:
		map.put("EDITION", "");
		map.put("DESTINST", "99700040000");
		map.put("REP_SIGN_IN_STAT", "0");     //1:可以重复签到
		map.put("PROCESS_ID", "");
		map.put("BUSS_SYS", "99370000000"); //柜员所在的业务系统代码
		
		return map;
	}
	
	/**
	 * 
	 * 
	 * 获得柜员签退向tuxedo发送的数据集合
	 * 
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2015-07-29   杜旭         批量新建
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
		map.put("TRANSINST", "99700040000");//交易系统代码
		map.put("SENDINST", "99370000000");//发信系统代码
		map.put("EDITION", "");
		map.put("DESTINST", "99700040000");
		map.put("SIGN_OFF_STAT", "2");
		map.put("BUSS_SYS", "99370000000");//柜员所在的业务系统代码
		
		return map;
	}
	/**
	 * 
	 *  
	 * 获得柜员强制签退向tuxedo发送的数据集合
	 * 
	 * <pre>
	 * 修改日期            修改人      修改原因
	 * 2015-07-29   杜旭         批量新建
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
		map.put("TRANSINST", "99700040000");//交易系统代码
		map.put("SENDINST", "99370000000");//发信系统代码
		map.put("EDITION", "");
		map.put("DESTINST", "99700040000");
		map.put("BUSS_SYS", "99370000000");//柜员所在的业务系统代码
		map.put("ADD_INST", "");            //附加系统代码

		return map;
	}
}
