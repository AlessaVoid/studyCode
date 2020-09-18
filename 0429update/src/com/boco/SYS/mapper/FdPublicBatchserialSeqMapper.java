package com.boco.SYS.mapper;

public interface FdPublicBatchserialSeqMapper {
	/**
	 * 查询下一个序列
	 * @return
	 * @throws RuntimeException
	 */
	public Integer findNext() throws RuntimeException;
}
