package com.boco.SYS.service;
/**
 * 
 * 
 *   生成交易流水号服务接口
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年4月28日    	    谷立羊   新建
 * </pre>
 */
public interface ITradeSerialService {
	/**
	 * 
	 *
	 * TODO 获取c生成的流水号
	 *
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年4月28日    	    谷立羊   新建
	 * </pre>
	 */
	public long getTradeSerialNo() throws Exception;
}
