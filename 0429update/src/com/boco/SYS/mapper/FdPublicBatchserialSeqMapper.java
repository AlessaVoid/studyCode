package com.boco.SYS.mapper;

public interface FdPublicBatchserialSeqMapper {
	/**
	 * ��ѯ��һ������
	 * @return
	 * @throws RuntimeException
	 */
	public Integer findNext() throws RuntimeException;
}
