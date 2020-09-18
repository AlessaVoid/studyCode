package com.boco.SYS.service.impl;

import org.springframework.stereotype.Service;

import com.boco.SYS.biz.SoketBiz;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.service.ITradeSerialService;
/**
 * 
 * 获取交易流水号 
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年4月28日    	    谷立羊   新建
 * </pre>
 */
@Service
public class TradeSerialService implements ITradeSerialService {

	@Override
	public long getTradeSerialNo() throws Exception {
		SoketBiz soketBiz = new SoketBiz();
		String ip = DicCache.getNameByKey("JJ_IP", "SYSTEM_PARAM");
		int port = Integer.valueOf(DicCache.getNameByKey("JJ_PORT", "SYSTEM_PARAM"));
		String tradeSerialno=soketBiz.sendToPost(ip, port,DicCache.getKeyByName_("ACCT_SERIAL", "SYSTEM_PARAM"));
		return Long.valueOf(tradeSerialno);
	}

}
