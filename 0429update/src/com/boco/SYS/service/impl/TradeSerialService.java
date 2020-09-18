package com.boco.SYS.service.impl;

import org.springframework.stereotype.Service;

import com.boco.SYS.biz.SoketBiz;
import com.boco.SYS.cache.DicCache;
import com.boco.SYS.service.ITradeSerialService;
/**
 * 
 * ��ȡ������ˮ�� 
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2016��4��28��    	    ������   �½�
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
