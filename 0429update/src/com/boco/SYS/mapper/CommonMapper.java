package com.boco.SYS.mapper;

import org.springframework.dao.DataAccessException;

/**
 * 
 * 
 * ����Mapper
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29      ����      �����½�
 * </pre>
 */
public interface CommonMapper {
	/**
	 * 
	 * 
	 * TODO ��ȡ��������.
	 * 
	 * @return
	 * @throws Exception
	 * 
	 *             <pre>
	 * �޸�����        �޸���    �޸�ԭ��
	 * 2015��12��7��    	    ����    �½�
	 * </pre>
	 */
	public String getNextId() throws DataAccessException;
}