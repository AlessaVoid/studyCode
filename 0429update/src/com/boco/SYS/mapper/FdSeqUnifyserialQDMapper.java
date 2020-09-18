package com.boco.SYS.mapper;

/**
 * 
 * 
 * FdSeqUnifyserialQD数据访问层.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年2月24日    		杜旭    新建
 * </pre>
 */
public interface FdSeqUnifyserialQDMapper{
	/**
	 * 
	 *
	 * TODO 查询渠道-交易唯一标识号.
	 *
	 * @return
	 * @throws RuntimeException
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年2月24日    	  杜旭    新建
	 * </pre>
	 */
	public Integer findFdSeqUnifyserialQD() throws RuntimeException;
}